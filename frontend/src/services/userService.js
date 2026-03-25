import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
})

export const getUsers = async () => {
  const response = await api.get('/api/users')
  return response.data
}

export const createUser = async (payload) => {
  const response = await api.post('/api/users', payload)
  return response.data
}

export const updateUser = async (id, payload) => {
  const response = await api.put(`/api/users/${id}`, payload)
  return response.data
}

export const deleteUser = async (id) => {
  const response = await api.delete(`/api/users/${id}`)
  return response.data
}

export const getServiceOrders = async () => {
  const response = await api.get('/api/service-orders')
  return response.data
}

export const createServiceOrder = async (payload) => {
  const response = await api.post('/api/service-orders', payload)
  return response.data
}

export const updateServiceOrderStatus = async (id, payload) => {
  const response = await api.put(`/api/service-orders/${id}/status`, payload)
  return response.data
}

export const deleteServiceOrder = async (id) => {
  const response = await api.delete(`/api/service-orders/${id}`)
  return response.data
}