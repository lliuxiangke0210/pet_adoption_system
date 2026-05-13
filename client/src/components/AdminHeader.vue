<template>
  <div class="admin-header">
    <div class="header-left">
      <el-icon
        class="collapse-btn"
        :size="22"
        @click="appStore.toggleSidebar()"
      >
        <Fold v-if="!appStore.sidebarCollapsed" />
        <Expand v-else />
      </el-icon>
      <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: '/dashboard' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item v-if="route.meta.title">{{ route.meta.title }}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="header-right">
      <el-dropdown trigger="click">
        <div class="user-info">
          <el-avatar :size="34" icon="UserFilled" :style="{ backgroundColor: '#FF8C42' }" />
          <span class="username">{{ userStore.userInfo?.realName || userStore.userInfo?.username || '管理员' }}</span>
          <el-icon><ArrowDown /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item disabled>
              <el-icon><User /></el-icon>
              {{ userStore.userInfo?.username }}
            </el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { useAppStore } from '../stores/app'
import { ElMessageBox } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()
const appStore = useAppStore()

function handleLogout() {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped lang="scss">
.admin-header {
  height: 50px;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  position: sticky;
  top: 0;
  z-index: 100;

  .header-left {
    display: flex;
    align-items: center;
    gap: 16px;

    .collapse-btn {
      cursor: pointer;
      color: #606266;
      transition: color 0.3s;

      &:hover { color: #FF8C42; }
    }
  }

  .header-right {
    .user-info {
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      padding: 4px 8px;
      border-radius: 8px;
      transition: background 0.3s;

      &:hover { background: #f5f7fa; }

      .username {
        font-size: 14px;
        color: #303133;
        font-weight: 500;
      }

      .el-icon { color: #909399; }
    }
  }
}
</style>
