<template>
  <div class="dashboard-page">
    <div class="page-header">
      <h2>仪表盘</h2>
      <p class="subtitle">宠物领养系统数据概览</p>
    </div>

    <!-- 统计卡片 -->
    <div class="stat-cards" v-loading="loading">
      <StatCard title="总用户数" :value="stats.totalUsers" icon="User"
        gradient="linear-gradient(135deg, #667eea 0%, #764ba2 100%)" />
      <StatCard title="总宠物数" :value="stats.totalPets" icon="House"
        gradient="linear-gradient(135deg, #FF8C42 0%, #FFB380 100%)" />
      <StatCard title="待审批领养" :value="stats.pendingAdoptions" icon="Clock"
        gradient="linear-gradient(135deg, #E6A23C 0%, #F7D06E 100%)" />
      <StatCard title="已完成领养" :value="stats.totalAdoptions" icon="Checked"
        gradient="linear-gradient(135deg, #67C23A 0%, #B3E19D 100%)" />
    </div>

    <!-- 图表区域 -->
    <div class="charts-row">
      <el-card class="chart-card" shadow="never">
        <template #header>
          <span class="chart-title">宠物分类分布</span>
        </template>
        <div class="chart-container">
          <v-chart :option="categoryOption" autoresize />
        </div>
      </el-card>

      <el-card class="chart-card" shadow="never">
        <template #header>
          <span class="chart-title">月度用户注册趋势</span>
        </template>
        <div class="chart-container">
          <v-chart :option="userLineOption" autoresize />
        </div>
      </el-card>
    </div>

    <div class="charts-row">
      <el-card class="chart-card chart-card-full" shadow="never">
        <template #header>
          <span class="chart-title">月度领养统计</span>
        </template>
        <div class="chart-container">
          <v-chart :option="adoptionBarOption" autoresize />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { PieChart, LineChart, BarChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, LegendComponent, GridComponent } from 'echarts/components'
import StatCard from '../components/StatCard.vue'
import { dashboardApi } from '../api/dashboard'

use([CanvasRenderer, PieChart, LineChart, BarChart, TitleComponent, TooltipComponent, LegendComponent, GridComponent])

const loading = ref(false)
const stats = ref({
  totalUsers: 0,
  totalPets: 0,
  pendingAdoptions: 0,
  totalAdoptions: 0,
  petsByCategory: [],
  userRegistrationsByMonth: [],
  adoptionsByMonth: []
})

async function fetchStats() {
  loading.value = true
  try {
    const res = await dashboardApi.getStats()
    stats.value = res.data
  } catch (e) {
    console.error('获取统计数据失败', e)
  } finally {
    loading.value = false
  }
}

// 宠物分类饼图
const categoryOption = computed(() => ({
  tooltip: { trigger: 'item' },
  legend: { bottom: '5%' },
  series: [{
    type: 'pie',
    radius: ['45%', '70%'],
    avoidLabelOverlap: false,
    itemStyle: { borderRadius: 8, borderColor: '#fff', borderWidth: 2 },
    label: { show: false },
    emphasis: { label: { show: true, fontWeight: 'bold' } },
    data: (stats.value.petsByCategory || []).map(item => ({
      name: item.category_name,
      value: item.count
    }))
  }],
  color: ['#FF8C42', '#667eea', '#67C23A', '#E6A23C', '#F56C6C', '#409EFF']
}))

// 用户注册折线图
const userLineOption = computed(() => {
  const months = Array.from({ length: 12 }, (_, i) => `${i + 1}月`)
  const data = Array(12).fill(0)
  ;(stats.value.userRegistrationsByMonth || []).forEach(item => {
    data[(item.month || 1) - 1] = item.count
  })
  return {
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: months, boundaryGap: false },
    yAxis: { type: 'value', minInterval: 1 },
    series: [{
      type: 'line',
      data,
      smooth: true,
      lineStyle: { width: 3, color: '#667eea' },
      itemStyle: { color: '#667eea' },
      areaStyle: {
        color: {
          type: 'linear', x: 0, y: 0, x2: 0, y2: 1,
          colorStops: [
            { offset: 0, color: 'rgba(102,126,234,0.3)' },
            { offset: 1, color: 'rgba(102,126,234,0.02)' }
          ]
        }
      }
    }]
  }
})

// 领养柱状图
const adoptionBarOption = computed(() => {
  const months = Array.from({ length: 12 }, (_, i) => `${i + 1}月`)
  const data = Array(12).fill(0)
  ;(stats.value.adoptionsByMonth || []).forEach(item => {
    data[(item.month || 1) - 1] = item.count
  })
  return {
    tooltip: { trigger: 'axis' },
    grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
    xAxis: { type: 'category', data: months },
    yAxis: { type: 'value', minInterval: 1 },
    series: [{
      type: 'bar',
      data,
      itemStyle: {
        borderRadius: [8, 8, 0, 0],
        color: {
          type: 'linear', x: 0, y: 0, x2: 0, y2: 1,
          colorStops: [
            { offset: 0, color: '#FF8C42' },
            { offset: 1, color: '#FFB380' }
          ]
        }
      },
      barWidth: '40%'
    }]
  }
})

onMounted(() => {
  fetchStats()
})
</script>

<style scoped lang="scss">
.dashboard-page {
  .page-header {
    margin-bottom: 24px;

    h2 { font-size: 22px; color: #303133; margin-bottom: 4px; }
    .subtitle { color: #909399; font-size: 14px; }
  }

  .stat-cards {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    gap: 16px;
    margin-bottom: 16px;
  }

  .charts-row {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 16px;
    margin-bottom: 16px;
  }

  .chart-card {
    border-radius: 12px;

    :deep(.el-card__header) {
      padding: 16px 20px;
      border-bottom: 1px solid #EBEEF5;
    }
  }

  .chart-card-full {
    grid-column: 1 / -1;
  }

  .chart-title {
    font-size: 15px;
    font-weight: 600;
    color: #303133;
  }

  .chart-container {
    height: 300px;
  }
}
</style>
