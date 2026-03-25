<script setup>
import { onMounted, reactive, ref } from 'vue'
import { getUsers } from '../services/userService.js'

import {
  createServiceOrder,
  deleteServiceOrder,
  getServiceOrders,
  updateServiceOrderStatus,
} from '../services/OrderService.js'

const users = ref([])
const serviceOrders = ref([])

const serviceOrderForm = reactive({
  userId: '',
  description: '',
})

const statusForm = reactive({})

const message = defineModel('message')
const errorMessage = defineModel('errorMessage')

const clearMessages = () => {
  message.value = ''
  errorMessage.value = ''
}

const fetchUsers = async () => {
  try {
    users.value = await getUsers()
  } catch (error) {
    errorMessage.value = 'Erro ao buscar usuários para o select.'
    console.error(error)
  }
}

const fetchServiceOrders = async () => {
  try {
    serviceOrders.value = await getServiceOrders()
  } catch (error) {
    errorMessage.value = 'Erro ao buscar ordens de serviço.'
    console.error(error)
  }
}

const handleCreateServiceOrder = async () => {
  try {
    clearMessages()

    if (!serviceOrderForm.userId) {
      errorMessage.value = 'Selecione um usuário.'
      return
    }

    if (!serviceOrderForm.description.trim()) {
      errorMessage.value = 'Digite a descrição da ordem.'
      return
    }

    await createServiceOrder({
      userId: serviceOrderForm.userId,
      description: serviceOrderForm.description,
    })

    serviceOrderForm.description = ''
    message.value = 'Ordem de serviço criada com sucesso.'
    await fetchServiceOrders()
  } catch (error) {
    errorMessage.value =
      error?.response?.data?.message || 'Erro ao criar ordem de serviço.'
    console.error(error)
  }
}

const handleUpdateStatus = async (orderId) => {
  try {
    clearMessages()

    const newStatus = statusForm[orderId]

    if (!newStatus) {
      errorMessage.value = 'Selecione um novo status.'
      return
    }

    await updateServiceOrderStatus(orderId, {
      status: newStatus,
    })

    statusForm[orderId] = ''
    message.value = 'Status atualizado com sucesso.'
    await fetchServiceOrders()
  } catch (error) {
    errorMessage.value =
      error?.response?.data?.message || 'Erro ao atualizar status.'
    console.error(error)
  }
}

const handleDeleteServiceOrder = async (id) => {
  try {
    clearMessages()
    await deleteServiceOrder(id)
    message.value = 'Ordem excluída com sucesso.'
    await fetchServiceOrders()
  } catch (error) {
    errorMessage.value =
      error?.response?.data?.message || 'Erro ao excluir ordem.'
    console.error(error)
  }
}

const getAvailableStatuses = (currentStatus) => {
  if (currentStatus === 'OPEN') {
    return ['IN_PROGRESS', 'CANCELED']
  }

  if (currentStatus === 'IN_PROGRESS') {
    return ['FINISHED']
  }

  return []
}

onMounted(async () => {
  await fetchUsers()
  await fetchServiceOrders()
})
</script>

<template>
    <section class="card">
      <h2>Criar Ordem de Serviço</h2>
  
      <form @submit.prevent="handleCreateServiceOrder">
        <select v-model="serviceOrderForm.userId" class="select">
          <option disabled value="">Selecione um usuário</option>
          <option
            v-for="user in users"
            :key="user.id"
            :value="user.id"
          >
            {{ user.name }}
          </option>
        </select>
  
        <input
          v-model="serviceOrderForm.description"
          type="text"
          placeholder="Digite a descrição da ordem"
          class="input-order"
        />
  
        <button type="submit" class="button">Criar ordem</button>
      </form>
    </section>
  
    <section class="card">
      <h2>Ordens de Serviço</h2>
  
      <div v-if="serviceOrders.length === 0">Nenhuma ordem cadastrada.</div>
  
      <table v-else >
        <thead>
          <tr>
            <th>ID</th>
            <th>Descrição</th>
            <th>Status</th>
            <th>Usuário</th>
            <th>Novo status</th>
            <th>Ações</th>
          </tr>
        </thead>
  
        <tbody>
          <tr v-for="order in serviceOrders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.description }}</td>
            <td>{{ order.status }}</td>
            <td>{{ order.userName }}</td>
            <td>
              <select v-model="statusForm[order.id]">
                <option disabled value="">Selecione</option>
  
                <option
                  v-for="status in getAvailableStatuses(order.status)"
                  :key="status"
                  :value="status"
                >
                  {{ status }}
                </option>
              </select>
            </td>
            <td>
              <button @click="handleUpdateStatus(order.id)">
                Atualizar status
              </button>
              <button @click="handleDeleteServiceOrder(order.id)">
                Excluir
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
</template>
  
<style scoped>
.table-users {
  width: 590px;
}

.select {
  appearance: none;
  -webkit-appearance: none;
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: white;
  font-size: 16px;
  cursor: pointer;;

}

.input-order {
  border-radius: 12px;	width: 96%;
	margin: 0 auto;
	padding: 10px;
	margin-bottom: 10px;
  margin-right: 10px;
  margin-top: 10px;
}

.card table {
  height: 100px;
}

.button {
  background-color: #1a1e22;
  color: white;
  padding: 12px 24px;
  border: none;
  border-radius: 15px;
  font-size: 14px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.button:hover {
  background-color: #0056b3;
}

.test {
  margin-left: 300px;
}
</style>