<script setup>
      import { onMounted, reactive, ref } from 'vue'
        import { createUser, deleteUser, getUsers } from '../services/userService.js'
        
      const users = ref([])
      const userForm = reactive({
          name: '',
    })
        
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
            errorMessage.value = 'Erro ao buscar usuários.'
            console.error(error)
          }
      }
        
    const handleCreateUser = async () => {
          try {
            clearMessages()
        
            if (!userForm.name.trim()) {
              errorMessage.value = 'Digite um nome para o usuário.'
              return
            }
        
            await createUser({
              name: userForm.name,
            })
        
            userForm.name = ''
            message.value = 'Usuário criado com sucesso.'
            await fetchUsers()
          } catch (error) {
            errorMessage.value =
              error?.response?.data?.message || 'Erro ao criar usuário.'
            console.error(error)
          }
      }
        
    const handleDeleteUser = async (id) => {
          try {
            clearMessages()
            await deleteUser(id)
            message.value = 'Usuário excluído com sucesso.'
            await fetchUsers()
          } catch (error) {
            errorMessage.value =
              error?.response?.data?.message || 'Erro ao excluir usuário.'
            console.error(error)
          }
      }
        
    onMounted(async () => {
          await fetchUsers()
      })
</script>

<template>

  <!-- aqui eu crio o usuario -->
    <section class="card">
      <h2>Criar Usuário</h2>
  
      <form @submit="handleCreateUser">
        <input
          v-model="userForm.name"
          type="text"
          placeholder="Digite o nome do usuário"
          class="input"
        />
        <button type="submit" class="button">Criar usuário</button>
      </form>
    </section>
    <!-- aqui eu crio o usuario -->

  <!-- aqui eu listo os usuarios -->
    <section class="card">
      <h2>Usuários</h2>
  
      <div v-if="users.length === 0">Nenhum usuário cadastrado.</div>
  
      <table v-else class="table-users">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Status</th>
            <th>Ações</th>
          </tr>
        </thead>
  
        <tbody>
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.status }}</td>
            <td>
              <button @click="handleDeleteUser(user.id)" class="button-delete">Excluir</button>
            </td>
          </tr>
        </tbody>
      </table>
    </section>
  <!-- aqui eu listo os usuarios -->
  </template>

<style>
.card {
  max-width: 700px;
  margin: 60px auto;
  padding: 30px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
  font-family: Arial, sans-serif;
  margin-right: 480px;
}

.input {
  border-radius: 12px;	width: 70%;
	margin: 0 auto;
	padding: 10px;
	/* border: none; */
	margin-bottom: 10px;
	border-radius: 50px;
  margin-right: 10px;
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

.table-users {
  /* border-collapse: separate; */
  /* border-spacing: 10px; */
  width: 590px;
}

.table-users td {
  color: white;
  background-color: #1a1e22;
  width: 500px;

}

</style>
  
