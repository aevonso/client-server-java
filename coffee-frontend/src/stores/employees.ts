import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { Employee, EmployeeSchedule } from '@/types'
import { employeesApi } from '@/services/api'

export const useEmployeesStore = defineStore('employees', () => {
    const employees = ref<Employee[]>([])
    const schedules = ref<EmployeeSchedule[]>([])
    const loading = ref(false)
    const error = ref<string | null>(null)

    const fetchEmployees = async () => {
        loading.value = true
        error.value = null
        try {
            const response = await employeesApi.getAll()
            employees.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch employees'
        } finally {
            loading.value = false
        }
    }

    const fetchSchedules = async (employeeId: number, startDate: string) => {
        loading.value = true
        error.value = null
        try {
            const response = await employeesApi.getWeeklySchedule(employeeId, startDate)
            schedules.value = response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to fetch schedules'
        } finally {
            loading.value = false
        }
    }

    const createEmployee = async (employeeData: any) => {
        loading.value = true
        error.value = null
        try {
            const response = await employeesApi.create(employeeData)
            employees.value.push(response.data)
            return response.data
        } catch (err: any) {
            error.value = err.response?.data?.message || 'Failed to create employee'
            throw err
        } finally {
            loading.value = false
        }
    }

    return {
        employees,
        schedules,
        loading,
        error,
        fetchEmployees,
        fetchSchedules,
        createEmployee
    }
})