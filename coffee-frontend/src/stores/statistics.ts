import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { DailyStatistics } from '@/types'
import { statisticsApi } from '@/services/api'

export const useStatisticsStore = defineStore('statistics', () => {
    const dailyStats = ref<DailyStatistics | null>(null)
    const todayCustomers = ref<any[]>([])
    const loading = ref(false)
    const error = ref<string | null>(null)

    const fetchDailyStatistics = async (date: string) => {
        loading.value = true
        error.value = null
        try {
            const response = await statisticsApi.getDaily(date)
            dailyStats.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch daily statistics'
        } finally {
            loading.value = false
        }
    }

    const fetchTodayCustomersWithBarista = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await statisticsApi.getTodayCustomersWithBarista()
            todayCustomers.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch today customers'
        } finally {
            loading.value = false
        }
    }

    return {
        dailyStats,
        todayCustomers,
        loading,
        error,
        fetchDailyStatistics,
        fetchTodayCustomersWithBarista
    }
})