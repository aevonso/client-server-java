<template>
  <div class="orders">
    <div class="container">
      <div class="orders__header">
        <h1>Управление заказами</h1>
        <BaseButton variant="primary" @click="showCreateModal = true">
          <template #icon>
            <PlusIcon />
          </template>
          Новый заказ
        </BaseButton>
      </div>

      <!-- Заказы кофе -->
      <div class="card">
        <h2>Заказы кофе</h2>
        <div v-if="ordersStore.loading" class="loading">Загрузка...</div>
        <div v-else-if="ordersStore.error" class="error">{{ ordersStore.error }}</div>
        <div v-else-if="ordersStore.coffeeOrders.length === 0" class="empty-state">
          <p>Нет заказов кофе</p>
        </div>
        <table v-else class="table">
          <thead>
          <tr>
            <th>ID</th>
            <th>Клиент</th>
            <th>Тип кофе</th>
            <th>Количество</th>
            <th>Сумма</th>
            <th>Дата</th>
            <th>Статус</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="order in ordersStore.coffeeOrders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.customerName || 'Гость' }}</td>
            <td>{{ order.coffeeType?.name }}</td>
            <td>{{ order.quantity }}</td>
            <td>{{ order.totalPrice }}₽</td>
            <td>{{ formatDate(order.orderDate) }}</td>
            <td>
                <span :class="['status-badge', `status-${order.status.toLowerCase()}`]">
                  {{ getStatusText(order.status) }}
                </span>
            </td>
            <td>
              <button
                  class="btn-danger"
                  @click="deleteCoffeeOrder(order.id)"
                  :disabled="ordersStore.loading"
              >
                Удалить
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>

      <!-- Заказы десертов -->
      <div class="card">
        <h2>Заказы десертов</h2>
        <div v-if="ordersStore.dessertOrders.length === 0" class="empty-state">
          <p>Нет заказов десертов</p>
        </div>
        <table v-else class="table">
          <thead>
          <tr>
            <th>ID</th>
            <th>Клиент</th>
            <th>Десерт</th>
            <th>Количество</th>
            <th>Сумма</th>
            <th>Дата</th>
            <th>Статус</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="order in ordersStore.dessertOrders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.customerName || 'Гость' }}</td>
            <td>{{ order.dessert?.name }}</td>
            <td>{{ order.quantity }}</td>
            <td>{{ order.totalPrice }}₽</td>
            <td>{{ formatDate(order.orderDate) }}</td>
            <td>
                <span :class="['status-badge', `status-${order.status.toLowerCase()}`]">
                  {{ getStatusText(order.status) }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Модальное окно создания заказа -->
    <div v-if="showCreateModal" class="modal-overlay" @click="showCreateModal = false">
      <div class="modal-content" @click.stop>
        <h3>Создать новый заказ кофе</h3>
        <form @submit.prevent="createNewOrder">
          <div class="form-group">
            <label>Тип кофе:</label>
            <select v-model="newOrder.coffeeTypeId" required>
              <option value="">Выберите тип кофе</option>
              <option v-for="coffee in coffeeTypes" :key="coffee.id" :value="coffee.id">
                {{ coffee.name }} - {{ coffee.price }}₽
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>Количество:</label>
            <input v-model.number="newOrder.quantity" type="number" min="1" required>
          </div>
          <div class="form-group">
            <label>Имя клиента:</label>
            <input v-model="newOrder.customerName" type="text">
          </div>
          <div class="form-group">
            <label>Официант:</label>
            <input v-model="newOrder.waiterName" type="text">
          </div>
          <div class="form-actions">
            <BaseButton type="button" variant="outline" @click="showCreateModal = false">
              Отмена
            </BaseButton>
            <BaseButton type="submit" variant="primary" :loading="ordersStore.loading">
              Создать заказ
            </BaseButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useOrdersStore } from '@/stores/orders'
import BaseButton from '@/components/ui/BaseButton.vue'
import { PlusIcon } from 'lucide-vue-next'

const ordersStore = useOrdersStore()
const showCreateModal = ref(false)
const coffeeTypes = ref([
  { id: 1, name: 'Эспрессо', price: 120 },
  { id: 2, name: 'Капучино', price: 180 },
  { id: 3, name: 'Латте', price: 200 }
])

const newOrder = ref({
  coffeeTypeId: 1,
  quantity: 1,
  customerName: '',
  waiterName: ''
})

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('ru-RU')
}

const getStatusText = (status: string) => {
  const statusMap: { [key: string]: string } = {
    'PENDING': 'В ожидании',
    'IN_PROGRESS': 'В процессе',
    'COMPLETED': 'Завершен',
    'CANCELLED': 'Отменен'
  }
  return statusMap[status] || status
}

const createNewOrder = async () => {
  try {
    await ordersStore.createCoffeeOrder(newOrder.value)
    showCreateModal.value = false
    // Сбросить форму
    newOrder.value = {
      coffeeTypeId: 1,
      quantity: 1,
      customerName: '',
      waiterName: ''
    }
  } catch (error) {
    console.error('Failed to create order:', error)
  }
}

const deleteCoffeeOrder = async (id: number) => {
  if (confirm('Вы уверены, что хотите удалить этот заказ?')) {
    try {
      await ordersStore.deleteCoffeeOrder(id)
    } catch (error) {
      console.error('Failed to delete order:', error)
    }
  }
}

onMounted(() => {
  ordersStore.fetchCoffeeOrders()
  ordersStore.fetchDessertOrders()
})
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.orders {
  padding: 2rem 0;

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
  }
}

.table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 1rem;

  th, td {
    padding: 1rem;
    text-align: left;
    border-bottom: 1px solid #e2e8f0;
  }

  th {
    background: #f8fafc;
    font-weight: 600;
    color: $text-primary;
  }
}

.status-badge {
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;

  &.status-pending {
    background: #fef3c7;
    color: #92400e;
  }

  &.status-in_progress {
    background: #dbeafe;
    color: #1e40af;
  }

  &.status-completed {
    background: #d1fae5;
    color: #065f46;
  }

  &.status-cancelled {
    background: #fee2e2;
    color: #991b1b;
  }
}

.btn-danger {
  background: #ef4444;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 6px;
  cursor: pointer;
  font-size: 0.8rem;

  &:hover:not(:disabled) {
    background: #dc2626;
  }

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
  }
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal-content {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  width: 90%;
  max-width: 500px;

  h3 {
    margin-bottom: 1.5rem;
    color: $text-primary;
  }
}

.form-group {
  margin-bottom: 1rem;

  label {
    display: block;
    margin-bottom: 0.5rem;
    font-weight: 500;
    color: $text-primary;
  }

  input, select {
    width: 100%;
    padding: 0.75rem;
    border: 1px solid #d1d5db;
    border-radius: 6px;
    font-size: 1rem;

    &:focus {
      outline: none;
      border-color: $primary-color;
    }
  }
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 1.5rem;
}

.loading, .error, .empty-state {
  text-align: center;
  padding: 2rem;
  color: $text-muted;
}

.error {
  color: #ef4444;
}
</style>