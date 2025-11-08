import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/HomeView.vue'),
        meta: {
            title: 'Главная - SuperCoffee'
        }
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/DashboardView.vue'),
        meta: {
            title: 'Дашборд - SuperCoffee'
        }
    },
    {
        path: '/orders',
        name: 'Orders',
        component: () => import('@/views/OrdersView.vue'),
        meta: {
            title: 'Заказы - SuperCoffee'
        }
    },
    {
        path: '/customers',
        name: 'Customers',
        component: () => import('@/views/CustomersView.vue'),
        meta: {
            title: 'Клиенты - SuperCoffee'
        }
    },
    {
        path: '/employees',
        name: 'Employees',
        component: () => import('@/views/EmployeesView.vue'),
        meta: {
            title: 'Сотрудники - SuperCoffee'
        }
    },
    {
        path: '/statistics',
        name: 'Statistics',
        component: () => import('@/views/StatisticsView.vue'),
        meta: {
            title: 'Статистика - SuperCoffee'
        }
    },
    {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: () => import('@/views/NotFoundView.vue'),
        meta: {
            title: 'Страница не найдена - SuperCoffee'
        }
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const title = to.meta?.title as string
    if (title) {
        document.title = title
    }
    next()
})

export default router