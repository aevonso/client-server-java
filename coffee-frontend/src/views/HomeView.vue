<template>
  <div class="home">

    <section class="hero">
      <div class="container">
        <div class="hero__content">
          <div class="hero__text">
            <h1 class="hero__title">
              Добро пожаловать в
              <span class="hero__title-accent">SuperCoffee</span>
            </h1>
            <p class="hero__subtitle">
              Современная система управления кофейней.
              Контролируйте заказы, клиентов и сотрудников в одном месте.
            </p>
            <div class="hero__actions">
              <BaseButton variant="primary" @click="$router.push('/dashboard')">
                <template #icon>
                  <BarChart3Icon />
                </template>
                Перейти к дашборду
              </BaseButton>
              <BaseButton variant="outline" @click="$router.push('/orders')">
                <template #icon>
                  <CoffeeIcon />
                </template>
                Смотреть заказы
              </BaseButton>
            </div>
          </div>
          <div class="hero__image">
            <div class="coffee-cup">
              <div class="coffee-cup__body"></div>
              <div class="coffee-cup__handle"></div>
              <div class="coffee-cup__steam">
                <span></span>
                <span></span>
                <span></span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <section class="features">
      <div class="container">
        <h2 class="features__title">Возможности системы</h2>
        <div class="features__grid">
          <div
              v-for="feature in features"
              :key="feature.title"
              class="feature-card"
          >
            <div class="feature-card__icon">
              <component :is="feature.icon" />
            </div>
            <h3 class="feature-card__title">{{ feature.title }}</h3>
            <p class="feature-card__description">{{ feature.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <section class="stats">
      <div class="container">
        <div class="stats__grid">
          <div class="stat-item">
            <div class="stat-item__value">150+</div>
            <div class="stat-item__label">Заказов в день</div>
          </div>
          <div class="stat-item">
            <div class="stat-item__value">500+</div>
            <div class="stat-item__label">Постоянных клиентов</div>
          </div>
          <div class="stat-item">
            <div class="stat-item__value">99.8%</div>
            <div class="stat-item__label">Удовлетворенность</div>
          </div>
          <div class="stat-item">
            <div class="stat-item__value">24/7</div>
            <div class="stat-item__label">Поддержка</div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import BaseButton from '@/components/ui/BaseButton.vue'
import {
  CoffeeIcon,
  BarChart3Icon,
  UsersIcon,
  UserCheckIcon,
  TrendingUpIcon,
  ShieldCheckIcon,
  ClockIcon
} from 'lucide-vue-next'

const router = useRouter()

const features = ref([
  {
    icon: CoffeeIcon,
    title: 'Управление заказами',
    description: 'Принимайте и отслеживайте заказы кофе и десертов в реальном времени'
  },
  {
    icon: UsersIcon,
    title: 'База клиентов',
    description: 'Ведите учет клиентов, их предпочтений и скидочных программ'
  },
  {
    icon: UserCheckIcon,
    title: 'Сотрудники',
    description: 'Управляйте расписанием и эффективностью работы сотрудников'
  },
  {
    icon: TrendingUpIcon,
    title: 'Аналитика',
    description: 'Подробная статистика и отчеты для принятия бизнес-решений'
  },
  {
    icon: ShieldCheckIcon,
    title: 'Безопасность',
    description: 'Надежная защита данных и резервное копирование'
  },
  {
    icon: ClockIcon,
    title: 'Экономия времени',
    description: 'Автоматизация рутинных операций и процессов'
  }
])
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.home {
  min-height: 100vh;
}

.hero {
  background: linear-gradient(135deg, $primary-color 0%, $primary-dark 100%);
  color: white;
  padding: $spacing-2xl 0;

  &__content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: $spacing-2xl;
    align-items: center;
    min-height: 70vh;

    @media (max-width: $breakpoint-lg) {
      grid-template-columns: 1fr;
      text-align: center;
    }
  }

  &__title {
    font-size: 3.5rem;
    font-weight: 800;
    line-height: 1.1;
    margin-bottom: $spacing-lg;

    @media (max-width: $breakpoint-md) {
      font-size: 2.5rem;
    }
  }

  &__title-accent {
    background: linear-gradient(45deg, $accent-color, #FFED4E);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }

  &__subtitle {
    font-size: $font-size-xl;
    margin-bottom: $spacing-2xl;
    opacity: 0.9;
    line-height: 1.6;
  }

  &__actions {
    display: flex;
    gap: $spacing-md;
    flex-wrap: wrap;

    @media (max-width: $breakpoint-md) {
      justify-content: center;
    }
  }

  &__image {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}

.features {
  padding: $spacing-2xl 0;
  background: $surface-color;

  &__title {
    text-align: center;
    font-size: $font-size-3xl;
    margin-bottom: $spacing-2xl;
    color: $text-primary;
  }

  &__grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    gap: $spacing-xl;
  }
}

.feature-card {
  background: white;
  padding: $spacing-xl;
  border-radius: $border-radius-xl;
  text-align: center;
  box-shadow: $shadow-md;
  transition: transform 0.3s ease, box-shadow 0.3s ease;

  &:hover {
    transform: translateY(-4px);
    box-shadow: $shadow-lg;
  }

  &__icon {
    width: 64px;
    height: 64px;
    margin: 0 auto $spacing-lg;
    background: linear-gradient(135deg, $primary-color, $secondary-color);
    border-radius: $border-radius-lg;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
  }

  &__title {
    font-size: $font-size-lg;
    font-weight: 600;
    margin-bottom: $spacing-sm;
    color: $text-primary;
  }

  &__description {
    color: $text-secondary;
    line-height: 1.6;
  }
}

.stats {
  padding: $spacing-2xl 0;
  background: linear-gradient(135deg, $secondary-color, $primary-light);

  &__grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: $spacing-xl;
    text-align: center;
  }
}

.stat-item {
  color: white;

  &__value {
    font-size: 3rem;
    font-weight: 800;
    margin-bottom: $spacing-sm;
  }

  &__label {
    font-size: $font-size-lg;
    opacity: 0.9;
  }
}

// Coffee cup animation
.coffee-cup {
  position: relative;
  width: 150px;
  height: 150px;

  &__body {
    width: 100px;
    height: 80px;
    background: #6F4E37;
    border-radius: 0 0 50px 50px;
    position: relative;

    &::before {
      content: '';
      position: absolute;
      top: -20px;
      left: -10px;
      width: 120px;
      height: 40px;
      border: 10px solid #6F4E37;
      border-radius: 50%;
    }
  }

  &__handle {
    position: absolute;
    right: -10px;
    top: 10px;
    width: 40px;
    height: 60px;
    border: 10px solid #6F4E37;
    border-left: none;
    border-radius: 0 50px 50px 0;
  }

  &__steam {
    position: absolute;
    top: -60px;
    left: 50%;
    transform: translateX(-50%);

    span {
      display: inline-block;
      width: 8px;
      height: 40px;
      background: white;
      border-radius: 4px;
      margin: 0 2px;
      opacity: 0.6;
      animation: steam 3s infinite ease-in-out;

      &:nth-child(2) { animation-delay: 0.2s; }
      &:nth-child(3) { animation-delay: 0.4s; }
    }
  }
}

@keyframes steam {
  0%, 100% {
    transform: translateY(0) scale(1);
    opacity: 0.6;
  }
  50% {
    transform: translateY(-20px) scale(1.1);
    opacity: 0.3;
  }
}
</style>