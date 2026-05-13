<template>
	<view class="login-page">
		<view class="login-header">
			<text class="login-logo">🐾</text>
			<text class="login-title">宠物领养</text>
			<text class="login-subtitle">给流浪动物一个温暖的家</text>
		</view>

		<view class="login-form">
			<view class="input-group">
				<view class="input-item">
					<text class="input-icon">👤</text>
					<input v-model="username" placeholder="请输入用户名" />
				</view>
				<view class="input-item">
					<text class="input-icon">🔒</text>
					<input v-model="password" type="password" placeholder="请输入密码" />
				</view>
			</view>

			<button class="btn-primary" :disabled="loading" @click="handleLogin">
				{{ loading ? '登录中...' : '登录' }}
			</button>

			<view class="login-footer">
				<text>还没有账号？</text>
				<text class="link" @click="goRegister">立即注册</text>
			</view>
		</view>

		<text class="login-tip">测试账号: admin / 123456</text>
	</view>
</template>

<script setup>
import { ref } from 'vue'
import { useUserStore } from '../../store/user.js'
import { authApi } from '../../api/auth.js'

const userStore = useUserStore()
const username = ref('')
const password = ref('')
const loading = ref(false)

async function handleLogin() {
	if (!username.value || !password.value) {
		uni.showToast({ title: '请输入用户名和密码', icon: 'none' })
		return
	}
	loading.value = true
	try {
		const res = await authApi.login({ username: username.value, password: password.value })
		userStore.setLogin(res.data)
		uni.showToast({ title: '登录成功', icon: 'success' })
		setTimeout(() => { uni.switchTab({ url: '/pages/index/index' }) }, 800)
	} catch (e) {
		console.error(e)
	} finally { loading.value = false }
}

function goRegister() { uni.navigateTo({ url: '/pages/register/register' }) }
</script>

<style scoped>
.login-page { min-height: 100vh; background: linear-gradient(160deg, #FFF5EC 0%, #FFE4D0 50%, #FFD4B8 100%); display: flex; flex-direction: column; align-items: center; padding: 100rpx 40rpx 0; }
.login-header { text-align: center; margin-bottom: 80rpx; }
.login-logo { font-size: 80rpx; display: block; margin-bottom: 20rpx; }
.login-title { font-size: 44rpx; font-weight: 700; color: #303133; display: block; margin-bottom: 12rpx; }
.login-subtitle { font-size: 28rpx; color: #909399; }
.login-form { width: 100%; background: #fff; border-radius: 24rpx; padding: 48rpx 32rpx; box-shadow: 0 8rpx 32rpx rgba(0,0,0,0.06); }
.input-group { margin-bottom: 36rpx; }
.input-item { display: flex; align-items: center; padding: 28rpx 0; border-bottom: 1px solid #EBEEF5; }
.input-icon { font-size: 36rpx; margin-right: 20rpx; }
.input-item input { flex: 1; font-size: 30rpx; }
.login-footer { text-align: center; margin-top: 36rpx; font-size: 26rpx; color: #909399; }
.link { color: #FF8C42; margin-left: 8rpx; }
.login-tip { font-size: 24rpx; color: #C0C4CC; margin-top: 40rpx; }
</style>
