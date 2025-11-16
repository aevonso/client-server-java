<template>
  <div class="dashboard">
    <div class="container">
      <div class="dashboard__header">
        <h1>Дашборд кофейни</h1>
        <p>Обзор ключевых показателей и статистики</p>
      </div>

      <!-- Статистика в реальном времени -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-card__icon">
            <UsersIcon />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ customersCount }}</div>
            <div class="stat-card__label">Всего клиентов</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-card__icon">
            <CoffeeIcon />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ coffeeOrdersCount }}</div>
            <div class="stat-card__label">Заказов кофе</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-card__icon">
            <CakeIcon />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ dessertOrdersCount }}</div>
            <div class="stat-card__label">Заказов десертов</div>
          </div>
        </div>

        <div class="stat-card">
          <div class="stat-card__icon">
            <TrendingUpIcon />
          </div>
          <div class="stat-card__content">
            <div class="stat-card__value">{{ averageDiscount }}%</div>
            <div class="stat-card__label">Средняя скидка</div>
          </div>
        </div>
      </div>

      <!-- Клиенты с максимальной скидкой -->
      <div class="card">
        <h2>Клиенты с максимальной скидкой</h2>
        <div v-if="customersStore.statistics?.customersWithMaxDiscount?.length" class="customers-list">
          <div
              v-for="customer in customersStore.statistics.customersWithMaxDiscount"
              :key="customer.id"
              class="customer-item"
          >
            <div class="customer-info">
              <strong>{{ customer.firstName }} {{ customer.lastName }}</strong>
              <span class="discount-badge">{{ customer.discount }}%</span>
            </div>
            <div class="customer-contacts">
              <span v-if="customer.email">{{ customer.email }}</span>
              <span v-if="customer.phone">{{ customer.phone }}</span>
            </div>
          </div>
        </div>
        <div v-else class="empty-state">
          <p>Нет данных о клиентах</p>
        </div>
      </div>

      <!-- Сегодняшние заказы с бариста -->
      <div class="card">
        <h2>Сегодняшние заказы</h2>
        <div v-if="statisticsStore.todayCustomers.length" class="orders-list">
          <div
              v-for="item in statisticsStore.todayCustomers"
              :key="item.order.id"
              class="order-item"
          >
            <div class="order-info">
              <strong>{{ item.customer.firstName }} {{ item.customer.lastName }}</strong>
              <span>Заказ: {{ item.order.totalPrice }}₽</span>
            </div>
            <div class="barista-info">
              Бариста: {{ item.barista.firstName }} {{ item.barista.lastName }}
            </div>
          </div>
        </div>
        <div v-else class="empty-state">
          <p>Сегодня еще не было заказов</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, computed } from 'vue'
import { useCustomersStore } from '@/stores/customers'
import { useOrdersStore } from '@/stores/orders'
import { useStatisticsStore } from '@/stores/statistics'
import { UsersIcon, CoffeeIcon, CakeIcon, TrendingUpIcon } from 'lucide-vue-next'

const customersStore = useCustomersStore()
const ordersStore = useOrdersStore()
const statisticsStore = useStatisticsStore()

const customersCount = computed(() => customersStore.customers.length)
const coffeeOrdersCount = computed(() => ordersStore.coffeeOrders.length)
const dessertOrdersCount = computed(() => ordersStore.dessertOrders.length)
const averageDiscount = computed(() =>
    customersStore.statistics?.averageDiscount?.toFixed(1) || '0'
)

onMounted(async () => {
  await Promise.all([
    customersStore.fetchCustomers(),
    customersStore.fetchStatistics(),
    ordersStore.fetchCoffeeOrders(),
    ordersStore.fetchDessertOrders(),
    statisticsStore.fetchTodayCustomersWithBarista()
  ])
})
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.dashboard {
  padding: 2rem 0;

  &__header {
    margin-bottom: 2rem;
    text-align: center;

    h1 {
      font-size: 2.5rem;
      margin-bottom: 0.5rem;
      color: $text-primary;
    }

    p {
      font-size: 1.2rem;
      color: $text-secondary;
    }
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1.5rem;
  margin-bottom: 2rem;
}

.stat-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: $shadow-md;
  display: flex;
  align-items: center;
  gap: 1rem;
  transition: transform 0.2s ease;

  &:hover {
    transform: translateY(-2px);
    box-shadow: $shadow-lg;
  }

  &__icon {
    width: 60px;
    height: 60px;
    background: linear-gradient(135deg, $primary-color, $secondary-color);
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
  }

  &__value {
    font-size: 2rem;
    font-weight: 800;
    color: $text-primary;
    line-height: 1;
  }

  &__label {
    color: $text-secondary;
    font-size: 0.9rem;
    margin-top: 0.25rem;
  }
}

.customers-list,
.orders-list {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.customer-item,
.order-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background: #f8f9fa;
  border-radius: 8px;
  border-left: 4px solid $primary-color;
}

.customer-info,
.order-info {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.discount-badge {
  background: $success-color;
  color: white;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

.customer-contacts {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
  font-size: 0.9rem;
  color: $text-secondary;
}

.barista-info {
  font-size: 0.9rem;
  color: $text-secondary;
  font-style: italic;
}

.empty-state {
  text-align: center;
  padding: 2rem;
  color: $text-muted;

  p {
    margin: 0;
  }
}
</style>