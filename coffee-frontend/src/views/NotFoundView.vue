<template>
  <div class="not-found">
    <div class="container">
      <div class="not-found__content">
        <h1>404</h1>
        <p>Страница не найдена</p>
        <BaseButton variant="primary" @click="$router.push('/')">
          Вернуться на главную
        </BaseButton>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import BaseButton from '@/components/ui/BaseButton.vue'
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.not-found {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 60vh;
  text-align: center;

  &__content {
    h1 {
      font-size: 6rem;
      font-weight: 800;
      color: $primary-color;
      margin-bottom: $spacing-md;
    }

    p {
      font-size: $font-size-xl;
      color: $text-secondary;
      margin-bottom: $spacing-xl;
    }
  }
}
</style>