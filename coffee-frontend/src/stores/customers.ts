import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Customer, DiscountStatistics } from '@/types'
import { customersApi } from '@/services/api'

export const useCustomersStore = defineStore('customers', () => {
    const customers = ref<Customer[]>([])
    const statistics = ref<DiscountStatistics | null>(null)
    const loading = ref(false)
    const error = ref<string | null>(null)

    const fetchCustomers = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await customersApi.getAll()
            customers.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch customers'
        } finally {
            loading.value = false
        }
    }

    const fetchStatistics = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await customersApi.getStatistics()
            statistics.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch statistics'
        } finally {
            loading.value = false
        }
    }

    const createCustomer = async (customerData: any) => {
        loading.value = true
        error.value = null
        try {
            const response = await customersApi.create(customerData)
            customers.value.push(response.data)
            return response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to create customer'
            throw err
        } finally {
            loading.value = false
        }
    }

    const updateCustomer = async (id: number, customerData: any) => {
        loading.value = true
        error.value = null
        try {
            const response = await customersApi.update(id, customerData)
            const index = customers.value.findIndex(c => c.id === id)
            if (index !== -1) {
                customers.value[index] = response.data
            }
            return response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to update customer'
            throw err
        } finally {
            loading.value = false
        }
    }

    const deleteCustomer = async (id: number) => {
        loading.value = true
        error.value = null
        try {
            await customersApi.delete(id)
            customers.value = customers.value.filter(customer => customer.id !== id)
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to delete customer'
            throw err
        } finally {
            loading.value = false
        }
    }

    return {
        customers,
        statistics,
        loading,
        error,
        fetchCustomers,
        fetchStatistics,
        createCustomer,
        updateCustomer,
        deleteCustomer
    }
})