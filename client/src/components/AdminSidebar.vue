<template>
  <div class="admin-sidebar" :class="{ collapsed: isCollapsed }">
    <div class="sidebar-logo">
      <el-icon :size="28" color="#FF8C42"><House /></el-icon>
      <span class="logo-text" v-show="!isCollapsed">宠物领养系统</span>
    </div>

    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapsed"
      :collapse-transition="false"
      router
      background-color="#1F2D3D"
      text-color="#BFCBD9"
      active-text-color="#FF8C42"
    >
      <el-menu-item index="/dashboard">
        <el-icon><DataAnalysis /></el-icon>
        <span>仪表盘</span>
      </el-menu-item>
      <el-sub-menu index="content">
        <template #title>
          <el-icon><Operation /></el-icon>
          <span>内容管理</span>
        </template>
        <el-menu-item index="/category">
          <el-icon><CollectionTag /></el-icon>
          <span>宠物分类</span>
        </el-menu-item>
        <el-menu-item index="/pet">
          <el-icon><House /></el-icon>
          <span>宠物管理</span>
        </el-menu-item>
        <el-menu-item index="/banner">
          <el-icon><PictureFilled /></el-icon>
          <span>轮播图管理</span>
        </el-menu-item>
        <el-menu-item index="/news">
          <el-icon><Document /></el-icon>
          <span>资讯管理</span>
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/adoption">
        <el-icon><List /></el-icon>
        <span>领养管理</span>
      </el-menu-item>
      <el-menu-item index="/user">
        <el-icon><User /></el-icon>
        <span>用户管理</span>
      </el-menu-item>
      <el-sub-menu index="interact">
        <template #title>
          <el-icon><ChatDotRound /></el-icon>
          <span>互动管理</span>
        </template>
        <el-menu-item index="/comment">
          <el-icon><ChatLineSquare /></el-icon>
          <span>评论管理</span>
        </el-menu-item>
        <el-menu-item index="/feedback">
          <el-icon><Message /></el-icon>
          <span>反馈管理</span>
        </el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { useAppStore } from '../stores/app'

const route = useRoute()
const appStore = useAppStore()
const isCollapsed = computed(() => appStore.sidebarCollapsed)
const activeMenu = computed(() => route.path)
</script>

<style scoped lang="scss">
.admin-sidebar {
  width: 200px;
  height: 100vh;
  background-color: #1F2D3D;
  overflow-y: auto;
  overflow-x: hidden;
  transition: width 0.3s ease;

  &.collapsed {
    width: 56px;
  }

  .sidebar-logo {
    height: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 10px;
    border-bottom: 1px solid rgba(255, 255, 255, 0.08);

    .logo-text {
      font-size: 17px;
      font-weight: 700;
      color: #fff;
      white-space: nowrap;
    }
  }

  .el-menu {
    border-right: none;
  }

  :deep(.el-menu-item) {
    border-left: 3px solid transparent;

    &.is-active {
      border-left-color: #FF8C42;
      background-color: rgba(255, 140, 66, 0.08) !important;
    }
  }
}
</style>
