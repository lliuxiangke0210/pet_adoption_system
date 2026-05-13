<template>
  <div class="main-layout">
    <AdminSidebar />
    <div class="layout-right">
      <AdminHeader />
      <div class="layout-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import AdminSidebar from '../components/AdminSidebar.vue'
import AdminHeader from '../components/AdminHeader.vue'
import { useAppStore } from '../stores/app'

const appStore = useAppStore()
</script>

<style scoped lang="scss">
.main-layout {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

.layout-right {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-width: 0;
}

.layout-content {
  flex: 1;
  overflow-y: auto;
  padding: 16px 20px;
  background-color: #F5F7FA;
}
</style>
