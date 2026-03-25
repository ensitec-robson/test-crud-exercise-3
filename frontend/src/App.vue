<template>
  <main class="container">
    <h1>CRUD de Usuários e Ordens de Serviço</h1>

    <p v-if="message" class="success-message">{{ message }}</p>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>

    <UserSection v-model:message="message" v-model:errorMessage="errorMessage" />
    <ServiceOrderSection
      v-model:message="message"
      v-model:errorMessage="errorMessage"
    />
  </main>
</template>

<script setup>
import { ref } from 'vue'
import UserSection from './components/UserSection.vue'
import ServiceOrderSection from './components/ServiceOrderList.vue'

const message = ref('')
const errorMessage = ref('')
</script>

<style scoped>
.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 24px;
  font-family: Arial, sans-serif;
}

.success-message {
  color: green;
  font-weight: bold;
  margin-bottom: 16px;
}

.error-message {
  color: red;
  font-weight: bold;
  margin-bottom: 16px;
}
</style>