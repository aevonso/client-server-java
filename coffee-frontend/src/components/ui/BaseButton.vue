<template>
  <button
      :class="[
      'btn',
      `btn--${variant}`,
      { 'btn--disabled': disabled, 'btn--loading': loading }
    ]"
      :disabled="disabled || loading"
      @click="$emit('click', $event)"
  >
    <span v-if="loading" class="btn__spinner"></span>
    <slot v-else name="icon"></slot>
    <span class="btn__text">
      <slot></slot>
    </span>
  </button>
</template>

<script setup lang="ts">
defineProps<{
  variant?: 'primary' | 'secondary' | 'outline'
  disabled?: boolean
  loading?: boolean
}>()

defineEmits<{
  click: [event: MouseEvent]
}>()
</script>

<style scoped lang="scss">
@import '@/styles/variables';

.btn {
  &--loading {
    position: relative;
    color: transparent;

    .btn__spinner {
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
      width: 20px;
      height: 20px;
      border: 2px solid transparent;
      border-top: 2px solid currentColor;
      border-radius: 50%;
      animation: spin 1s linear infinite;
    }
  }
}

@keyframes spin {
  0% { transform: translate(-50%, -50%) rotate(0deg); }
  100% { transform: translate(-50%, -50%) rotate(360deg); }
}
</style>