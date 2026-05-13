<template>
	<view class="mine-page">
		<!-- 用户信息头部 -->
		<view class="mine-header" :style="{ paddingTop: (statusBarHeight + 20) + 'px' }">
			<view class="user-area">
				<view class="avatar">
					<text class="avatar-icon">🐱</text>
				</view>
				<view class="user-text" v-if="userStore.isLoggedIn()">
					<text class="nickname">{{ userStore.userInfo?.username || '用户' }}</text>
					<text class="user-role">{{ userStore.userInfo?.role === 'admin' ? '管理员' : '普通用户' }}</text>
				</view>
				<view class="user-text" v-else>
					<text class="nickname">未登录</text>
					<text class="user-role" @click="goLogin">点击登录</text>
				</view>
			</view>
		</view>

		<!-- 菜单列表 -->
		<view class="menu-section">
			<view class="menu-group">
				<view class="menu-item" @click="navigateTo('/pages/adoption/list', 'myAdoptions')">
					<text class="menu-icon">📋</text>
					<text class="menu-text">我的领养</text>
					<text class="menu-arrow">›</text>
				</view>
				<view class="menu-item" @click="navigateTo('/pages/feedback/list', 'myFeedback')">
					<text class="menu-icon">💬</text>
					<text class="menu-text">我的反馈</text>
					<text class="menu-arrow">›</text>
				</view>
				<view class="menu-item" @click="navigateTo('/pages/feedback/add')">
					<text class="menu-icon">✏️</text>
					<text class="menu-text">意见反馈</text>
					<text class="menu-arrow">›</text>
				</view>
			</view>

			<view class="menu-group" v-if="userStore.isLoggedIn()">
				<view class="menu-item logout" @click="handleLogout">
					<text class="menu-icon">🚪</text>
					<text class="menu-text" style="color: #F56C6C">退出登录</text>
					<text class="menu-arrow">›</text>
				</view>
			</view>
		</view>

		<view class="version-text">宠物领养系统 v1.0.0</view>
	</view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from '../../store/user.js'

const userStore = useUserStore()
const statusBarHeight = ref(20)

function navigateTo(url) {
	if (!userStore.isLoggedIn()) {
		uni.showModal({
			title: '提示', content: '请先登录',
			success: (res) => { if (res.confirm) uni.navigateTo({ url: '/pages/login/login' }) }
		})
		return
	}
	uni.navigateTo({ url })
}

function goLogin() { uni.navigateTo({ url: '/pages/login/login' }) }

function handleLogout() {
	uni.showModal({
		title: '提示', content: '确定要退出登录吗？',
		success: (res) => {
			if (res.confirm) {
				userStore.logout()
				uni.showToast({ title: '已退出', icon: 'none' })
			}
		}
	})
}

onMounted(() => {
	const info = uni.getSystemInfoSync()
	statusBarHeight.value = info.statusBarHeight || 20
})
</script>

<style scoped>
.mine-page { min-height: 100vh; background: #F5F7FA; }
.mine-header {
	background: linear-gradient(160deg, #FF8C42 0%, #E67A2E 100%);
	padding: 40rpx 30rpx 50rpx;
}
.user-area { display: flex; align-items: center; gap: 24rpx; }
.avatar { width: 100rpx; height: 100rpx; background: rgba(255,255,255,0.3); border-radius: 50%; display: flex; align-items: center; justify-content: center; }
.avatar-icon { font-size: 50rpx; }
.nickname { font-size: 36rpx; font-weight: 600; color: #fff; display: block; }
.user-role { font-size: 26rpx; color: rgba(255,255,255,0.8); }
.menu-section { padding: 24rpx; }
.menu-group { background: #fff; border-radius: 16rpx; overflow: hidden; margin-bottom: 24rpx; }
.menu-item { display: flex; align-items: center; padding: 32rpx 24rpx; border-bottom: 1px solid #F5F7FA; }
.menu-item:last-child { border-bottom: none; }
.menu-icon { font-size: 36rpx; margin-right: 20rpx; }
.menu-text { flex: 1; font-size: 30rpx; color: #303133; }
.menu-arrow { color: #C0C4CC; font-size: 32rpx; font-weight: 300; }
.version-text { text-align: center; font-size: 24rpx; color: #C0C4CC; padding: 40rpx; }
</style>
