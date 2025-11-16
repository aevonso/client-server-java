import axios from 'axios'
import type {
    CoffeeOrder,
    DessertOrder,
    Customer,
    Employee,
    EmployeeSchedule,
    DailyStatistics,
    DiscountStatistics,
    ApiResponse
} from '@/types'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
})


export const coffeeOrdersApi = {
    getAll: () => api.get<CoffeeOrder[]>('/coffee-orders'),
    getById: (id: number) => api.get<CoffeeOrder>(`/coffee-orders/${id}`),
    create: (data: any) => api.post<CoffeeOrder>('/coffee-orders', data),
    update: (id: number, data: any) => api.put<CoffeeOrder>(`/coffee-orders/${id}`, data),
    delete: (id: number) => api.delete(`/coffee-orders/${id}`),
    getByWaiter: (waiterName: string) => api.get<CoffeeOrder[]>(`/coffee-orders/waiter/${waiterName}`),
    getByCustomer: (customerName: string) => api.get<CoffeeOrder[]>(`/coffee-orders/customer/${customerName}`),
    getByCustomerPhone: (phone: string) => api.get<CoffeeOrder[]>(`/coffee-orders/customer/phone/${phone}`),
}


export const dessertOrdersApi = {
    getAll: () => api.get<DessertOrder[]>('/dessert-orders'),
    getById: (id: number) => api.get<DessertOrder>(`/dessert-orders/${id}`),
    create: (data: any) => api.post<DessertOrder>('/dessert-orders', data),
    update: (id: number, data: any) => api.put<DessertOrder>(`/dessert-orders/${id}`, data),
    delete: (id: number) => api.delete(`/dessert-orders/${id}`),
    getByDessert: (dessertId: number) => api.get<DessertOrder[]>(`/dessert-orders/dessert/${dessertId}`),
    getByWaiter: (waiterName: string) => api.get<DessertOrder[]>(`/dessert-orders/waiter/${waiterName}`),
    getByCustomer: (customerName: string) => api.get<DessertOrder[]>(`/dessert-orders/customer/${customerName}`),
}


export const customersApi = {
    getAll: () => api.get<Customer[]>('/customers'),
    getById: (id: number) => api.get<Customer>(`/customers/${id}`),
    create: (data: any) => api.post<Customer>('/customers', data),
    update: (id: number, data: any) => api.put<Customer>(`/customers/${id}`, data),
    delete: (id: number) => api.delete(`/customers/${id}`),
    getStatistics: () => api.get<DiscountStatistics>('/customers/discounts/statistics'),
    getMinDiscount: () => api.get<number>('/customers/discounts/min'),
    getMaxDiscount: () => api.get<number>('/customers/discounts/max'),
    getAverageDiscount: () => api.get<number>('/customers/discounts/average'),
    getCustomersWithMinDiscount: () => api.get<Customer[]>('/customers/discounts/min-customers'),
    getCustomersWithMaxDiscount: () => api.get<Customer[]>('/customers/discounts/max-customers'),
    getYoungest: () => api.get<Customer[]>('/customers/youngest'),
    getOldest: () => api.get<Customer[]>('/customers/oldest'),
    getWithBirthdayToday: () => api.get<Customer[]>('/customers/birthday-today'),
    getWithoutEmail: () => api.get<Customer[]>('/customers/without-email'),
}


export const employeesApi = {
    getAll: () => api.get<Employee[]>('/employees'),
    getById: (id: number) => api.get<Employee>(`/employees/${id}`),
    create: (data: any) => api.post<Employee>('/employees', data),
    update: (id: number, data: any) => api.put<Employee>(`/employees/${id}`, data),
    delete: (id: number) => api.delete(`/employees/${id}`),
    getByPosition: (position: string) => api.get<Employee[]>(`/employees/position/${position}`),
    getActiveBaristas: () => api.get<Employee[]>('/employees/baristas/active'),
    getWeeklySchedule: (employeeId: number, startDate: string) =>
        api.get<EmployeeSchedule[]>(`/employees/${employeeId}/weekly-schedule?startDate=${startDate}`),
    getBaristasWeeklySchedule: (startDate: string) =>
        api.get<any[]>(`/employees/baristas/weekly-schedule?startDate=${startDate}`),
    getAllWeeklySchedule: (startDate: string) =>
        api.get<any[]>(`/employees/all/weekly-schedule?startDate=${startDate}`),
}


export const scheduleApi = {
    getAll: () => api.get<any[]>('/schedule'),
    create: (data: any) => api.post<any>('/schedule', data),
    update: (id: number, data: any) => api.put<any>(`/schedule/${id}`, data),
    delete: (id: number) => api.delete(`/schedule/${id}`),
    getByDate: (date: string) => api.get<any>(`/schedule/date/${date}`),
    deleteByDate: (date: string) => api.delete(`/schedule/date/${date}`),
}


export const statisticsApi = {
    getDaily: (date: string) => api.get<DailyStatistics>(`/statistics/daily/${date}`),
    getOrdersByDate: (date: string) => api.get<any>(`/statistics/orders/date/${date}`),
    getOrdersBetweenDates: (startDate: string, endDate: string) =>
        api.get<any>(`/statistics/orders/range?startDate=${startDate}&endDate=${endDate}`),
    getDessertOrdersCount: (date: string) => api.get<number>(`/statistics/dessert-orders/count/${date}`),
    getCoffeeOrdersCount: (date: string) => api.get<number>(`/statistics/coffee-orders/count/${date}`),
    getTodayCustomersWithBarista: () => api.get<any[]>('/statistics/today-customers-with-barista'),
    getAverageOrderTotal: (date: string) => api.get<number>(`/statistics/average-order-total/${date}`),
    getMaxOrderTotal: (date: string) => api.get<number>(`/statistics/max-order-total/${date}`),
    getCustomerWithMaxOrder: (date: string) => api.get<any>(`/statistics/customer-max-order/${date}`),
}


export const coffeeTypesApi = {
    getAll: () => api.get<any[]>('/coffee-types'),
    create: (data: any) => api.post<any>('/coffee-types', data),
    update: (id: number, data: any) => api.put<any>(`/coffee-types/${id}`, data),
    updateName: (id: number, newName: string) =>
        api.patch<any>(`/coffee-types/${id}/name?newName=${newName}`),
}


export const dessertsApi = {
    getAll: () => api.get<any[]>('/desserts'),
    create: (data: any) => api.post<any>('/desserts', data),
    update: (id: number, data: any) => api.put<any>(`/desserts/${id}`, data),
}

export default api