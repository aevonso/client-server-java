import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { CoffeeOrder, DessertOrder } from '@/types'
import { coffeeOrdersApi, dessertOrdersApi } from '@/services/api'

export const useOrdersStore = defineStore('orders', () => {
    const coffeeOrders = ref<CoffeeOrder[]>([])
    const dessertOrders = ref<DessertOrder[]>([])
    const loading = ref(false)
    const error = ref<string | null>(null)

    const fetchCoffeeOrders = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await coffeeOrdersApi.getAll()
            coffeeOrders.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch coffee orders'
        } finally {
            loading.value = false
        }
    }

    const fetchDessertOrders = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await dessertOrdersApi.getAll()
            dessertOrders.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch dessert orders'
        } finally {
            loading.value = false
        }
    }

    const createCoffeeOrder = async (orderData: any) => {
        loading.value = true
        error.value = null
        try {
            const response = await coffeeOrdersApi.create(orderData)
            coffeeOrders.value.push(response.data)
            return response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to create coffee order'
            throw err
        } finally {
            loading.value = false
        }
    }

    const deleteCoffeeOrder = async (id: number) => {
        loading.value = true
        error.value = null
        try {
            await coffeeOrdersApi.delete(id)
            coffeeOrders.value = coffeeOrders.value.filter(order => order.id !== id)
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to delete coffee order'
            throw err
        } finally {
            loading.value = false
        }
    }

    return {
        coffeeOrders,
        dessertOrders,
        loading,
        error,
        fetchCoffeeOrders,
        fetchDessertOrders,
        createCoffeeOrder,
        deleteCoffeeOrder
    }
})