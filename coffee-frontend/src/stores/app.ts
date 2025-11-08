import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useAppStore = defineStore('app', () => {
    const isLoading = ref(false)
    const sidebarOpen = ref(false)
    const notifications = ref<Notification[]>([])

    const unreadNotificationsCount = computed(() =>
        notifications.value.filter(n => !n.read).length
    )

    const setLoading = (loading: boolean) => {
        isLoading.value = loading
    }

    const toggleSidebar = () => {
        sidebarOpen.value = !sidebarOpen.value
    }

    const addNotification = (notification: Omit<Notification, 'id' | 'timestamp' | 'read'>) => {
        const newNotification: Notification = {
            id: Date.now(),
            timestamp: new Date(),
            read: false,
            ...notification
        }
        notifications.value.unshift(newNotification)
    }

    const markAsRead = (id: number) => {
        const notification = notifications.value.find(n => n.id === id)
        if (notification) {
            notification.read = true
        }
    }

    return {
        isLoading,
        sidebarOpen,
        notifications,
        unreadNotificationsCount,
        setLoading,
        toggleSidebar,
        addNotification,
        markAsRead
    }
})

interface Notification {
    id: number
    type: 'success' | 'error' | 'warning' | 'info'
    title: string
    message: string
    timestamp: Date
    read: boolean
}