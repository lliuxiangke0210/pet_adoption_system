<template>
  <div class="stat-card" :style="{ background: gradient }">
    <div class="stat-content">
      <div class="stat-info">
        <p class="stat-label">{{ title }}</p>
        <p class="stat-value">{{ animatedValue }}</p>
      </div>
      <div class="stat-icon" :style="{ backgroundColor: iconBg }">
        <el-icon :size="28" :color="iconColor">
          <component :is="icon" />
        </el-icon>
      </div>
    </div>
    <div class="stat-footer" v-if="showTrend">
      <span :style="{ color: trendColor }">
        <el-icon :size="14"><component :is="trendUp ? 'CaretTop' : 'CaretBottom'" /></el-icon>
        {{ trendText }}
      </span>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  title: { type: String, required: true },
  value: { type: Number, default: 0 },
  icon: { type: String, default: 'DataAnalysis' },
  gradient: { type: String, default: 'linear-gradient(135deg, #FF8C42, #FFB380)' },
  iconBg: { type: String, default: 'rgba(255,255,255,0.25)' },
  iconColor: { type: String, default: '#fff' },
  showTrend: { type: Boolean, default: false },
  trendUp: { type: Boolean, default: true },
  trendText: { type: String, default: '' },
  trendColor: { type: String, default: '#fff' }
})

const animatedValue = ref(0)

watch(() => props.value, (newVal) => {
  animatedValue.value = newVal
}, { immediate: true })
</script>

<style scoped lang="scss">
.stat-card {
  border-radius: 12px;
  padding: 22px 24px;
  color: #fff;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease, box-shadow 0.3s ease;

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
  }

  .stat-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .stat-label {
    font-size: 14px;
    opacity: 0.9;
    margin-bottom: 8px;
  }

  .stat-value {
    font-size: 32px;
    font-weight: 700;
    letter-spacing: 0.5px;
  }

  .stat-icon {
    width: 56px;
    height: 56px;
    border-radius: 12px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .stat-footer {
    margin-top: 16px;
    padding-top: 14px;
    border-top: 1px solid rgba(255, 255, 255, 0.2);
    font-size: 13px;
    opacity: 0.9;
  }
}
</style>
