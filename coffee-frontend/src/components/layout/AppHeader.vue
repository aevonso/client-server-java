<template>
  <header class="app-header">
    <div class="container">
      <div class="app-header__content">
        <div class="app-header__brand">
          <CoffeeIcon class="app-header__logo" />
          <h1 class="app-header__title">SuperCoffee</h1>
        </div>

        <nav class="app-header__nav">
          <router-link
              v-for="item in navItems"
              :key="item.to"
              :to="item.to"
              class="app-header__nav-item"
              :class="{ 'app-header__nav-item--active': $route.path === item.to }"
          >
            <component :is="item.icon" class="app-header__nav-icon" />
            <span class="app-header__nav-text">{{ item.text }}</span>
          </router-link>
        </nav>

        <div class="app-header__actions">
          <button class="app-header__action-btn" @click="toggleTheme">
            <SunIcon v-if="isDark" />
            <MoonIcon v-else />
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  CoffeeIcon,
  HomeIcon,
  BarChart3Icon,
  UsersIcon,
  UserCheckIcon,
  TrendingUpIcon,
  SunIcon,
  MoonIcon
} from 'lucide-vue-next'

const route = useRoute()
const isDark = ref(false)

const navItems = computed(() => [
  { to: '/', text: 'Главная', icon: HomeIcon },
  { to: '/dashboard', text: 'Дашборд', icon: BarChart3Icon },
  { to: '/orders', text: 'Заказы', icon: CoffeeIcon },
  { to: '/customers', text: 'Клиенты', icon: UsersIcon },
  { to: '/employees', text: 'Сотрудники', icon: UserCheckIcon },
  { to: '/statistics', text: 'Статистика', icon: TrendingUpIcon }
])

const toggleTheme = () => {
  isDark.value = !isDark.value
  document.documentElement.classList.toggle('dark', isDark.value)
}
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.app-header {
  background: linear-gradient(135deg, $primary-color, $primary-dark);
  color: white;
  box-shadow: $shadow-lg;
  position: sticky;
  top: 0;
  z-index: 100;

  &__content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: $spacing-md 0;
  }

  &__brand {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
  }

  &__logo {
    width: 32px;
    height: 32px;
  }

  &__title {
    font-size: $font-size-xl;
    font-weight: 700;
    margin: 0;
  }

  &__nav {
    display: flex;
    gap: $spacing-lg;
  }

  &__nav-item {
    display: flex;
    align-items: center;
    gap: $spacing-sm;
    padding: $spacing-sm $spacing-md;
    border-radius: $border-radius-md;
    text-decoration: none;
    color: rgba(255, 255, 255, 0.8);
    transition: all 0.2s ease;
    font-weight: 500;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
      color: white;
    }

    &--active {
      background: rgba(255, 255, 255, 0.15);
      color: white;
    }
  }

  &__nav-icon {
    width: 18px;
    height: 18px;
  }

  &__nav-text {
    @media (max-width: $breakpoint-md) {
      display: none;
    }
  }

  &__actions {
    display: flex;
    gap: $spacing-sm;
  }

  &__action-btn {
    background: none;
    border: none;
    color: white;
    padding: $spacing-sm;
    border-radius: $border-radius-md;
    cursor: pointer;
    transition: background 0.2s ease;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
    }
  }
}
</style>