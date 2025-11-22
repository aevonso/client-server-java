export interface DailyStatistics {
    date: string;
    totalOrders: number;
    totalRevenue: number;
    averageOrderValue: number;
}

export interface Customer {
    id: number;
    name: string;
    email: string;
}

export interface Notification {
    id: number;
    type: 'success' | 'error' | 'warning' | 'info';
    title: string;
    message: string;
    timestamp: Date;
    read: boolean;
}