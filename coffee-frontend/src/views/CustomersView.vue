<template>
  <div class="customers">
    <div class="container">
      <div class="customers__header">
        <h1>Управление клиентами</h1>
        <BaseButton variant="primary" @click="showCreateModal = true">
          <template #icon>
            <PlusIcon />
          </template>
          Добавить клиента
        </BaseButton>
      </div>

      <!-- Статистика по скидкам -->
      <div class="stats-grid">
        <div class="stat-card">
          <div class="stat-card__value">{{ statistics.minDiscount || 0 }}%</div>
          <div class="stat-card__label">Минимальная скидка</div>
        </div>
        <div class="stat-card">
          <div class="stat-card__value">{{ statistics.maxDiscount || 0 }}%</div>
          <div class="stat-card__label">Максимальная скидка</div>
        </div>
        <div class="stat-card">
          <div class="stat-card__value">{{ statistics.averageDiscount?.toFixed(1) || 0 }}%</div>
          <div class="stat-card__label">Средняя скидка</div>
        </div>
      </div>

      <!-- Список клиентов -->
      <div class="card">
        <h2>Список клиентов</h2>
        <div v-if="customersStore.loading" class="loading">Загрузка...</div>
        <div v-else-if="customersStore.error" class="error">{{ customersStore.error }}</div>
        <div v-else-if="customersStore.customers.length === 0" class="empty-state">
          <p>Нет клиентов</p>
        </div>
        <table v-else class="table">
          <thead>
          <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Телефон</th>
            <th>Дата рождения</th>
            <th>Скидка</th>
            <th>Действия</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="customer in customersStore.customers" :key="customer.id">
            <td>{{ customer.firstName }}</td>
            <td>{{ customer.lastName }}</td>
            <td>{{ customer.email || '-' }}</td>
            <td>{{ customer.phone || '-' }}</td>
            <td>{{ customer.birthDate ? formatDate(customer.birthDate) : '-' }}</td>
            <td>
              <span class="discount-badge">{{ customer.discount }}%</span>
            </td>
            <td>
              <button
                  class="btn-danger"
                  @click="deleteCustomer(customer.id)"
                  :disabled="customersStore.loading"
              >
                Удалить
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Модальное окно создания клиента -->
    <div v-if="showCreateModal" class="modal-overlay" @click="showCreateModal = false">
      <div class="modal-content" @click.stop>
        <h3>Добавить нового клиента</h3>
        <form @submit.prevent="createNewCustomer">
          <div class="form-row">
            <div class="form-group">
              <label>Имя *</label>
              <input v-model="newCustomer.firstName" type="text" required>
            </div>
            <div class="form-group">
              <label>Фамилия *</label>
              <input v-model="newCustomer.lastName" type="text" required>
            </div>
          </div>
          <div class="form-group">
            <label>Email</label>
            <input v-model="newCustomer.email" type="email">
          </div>
          <div class="form-group">
            <label>Телефон</label>
            <input v-model="newCustomer.phone" type="tel">
          </div>
          <div class="form-group">
            <label>Дата рождения</label>
            <input v-model="newCustomer.birthDate" type="date">
          </div>
          <div class="form-group">
            <label>Скидка (%)</label>
            <input v-model.number="newCustomer.discount" type="number" min="0" max="100" step="0.1">
          </div>
          <div class="form-actions">
            <BaseButton type="button" variant="outline" @click="showCreateModal = false">
              Отмена
            </BaseButton>
            <BaseButton type="submit" variant="primary" :loading="customersStore.loading">
              Добавить клиента
            </BaseButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useCustomersStore } from '@/stores/customers'
import BaseButton from '@/components/ui/BaseButton.vue'
import { PlusIcon } from 'lucide-vue-next'

const customersStore = useCustomersStore()
const showCreateModal = ref(false)

const newCustomer = ref({
  firstName: '',
  lastName: '',
  email: '',
  phone: '',
  birthDate: '',
  discount: 0
})

const statistics = computed(() => customersStore.statistics || {})

const formatDate = (dateString: string) => {
  return new Date(dateString).toLocaleDateString('ru-RU')
}

const createNewCustomer = async () => {
  try {
    await customersStore.createCustomer(newCustomer.value)
    showCreateModal.value = false
    // Сбросить форму
    newCustomer.value = {
      firstName: '',
      lastName: '',
      email: '',
      phone: '',
      birthDate: '',
      discount: 0
    }
  } catch (error) {
    console.error('Failed to create customer:', error)
  }
}

const deleteCustomer = async (id: number) => {
  if (confirm('Вы уверены, что хотите удалить этого клиента?')) {
    try {
      await customersStore.deleteCustomer(id)
    } catch (error) {
      console.error('Failed to delete customer:', error)
    }
  }
}

onMounted(() => {
  customersStore.fetchCustomers()
  customersStore.fetchStatistics()
})
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.customers {
  padding: 2rem 0;

  &__header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 2rem;
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
  margin-bottom: 2rem;
}

.stat-card {
  background: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: $shadow-md;
  text-align: center;

  &__value {
    font-size: 2rem;
    font-weight: 800;
    color: $primary-color;
    margin-bottom: 0.5rem;
  }

  &__label {
    color: $text-secondary;
    font-size: 0.9rem;
  }
}

.discount-badge {
  background: $success-color;
  color: white;
  padding: 0.25rem 0.75rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 600;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}
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