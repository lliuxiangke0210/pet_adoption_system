<template>
	<view class="page-wrap">
		<view class="register-card">
			<text class="card-title">用户注册</text>

			<view class="input-group">
				<input v-model="form.username" placeholder="请输入用户名" class="form-input" />
				<input v-model="form.password" type="password" placeholder="请输入密码" class="form-input" />
				<input v-model="confirmPwd" type="password" placeholder="请确认密码" class="form-input" />
				<input v-model="form.realName" placeholder="请输入真实姓名" class="form-input" />
				<input v-model="form.phone" placeholder="请输入手机号" class="form-input" />
				<input v-model="form.email" placeholder="请输入邮箱" class="form-input" />
			</view>

			<button class="btn-primary" :disabled="loading" @click="handleRegister">
				{{ loading ? '注册中...' : '注册' }}
			</button>

			<view class="footer-link">
				<text>已有账号？</text>
				<text class="link" @click="goBack">返回登录</text>
			</view>
		</view>
	</view>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { authApi } from '../../api/auth.js'

const form = reactive({ username: '', password: '', realName: '', phone: '', email: '' })
const confirmPwd = ref('')
const loading = ref(false)

async function handleRegister() {
	if (!form.username || !form.password) {
		uni.showToast({ title: '用户名和密码不能为空', icon: 'none' })
		return
	}
	if (form.password !== confirmPwd.value) {
		uni.showToast({ title: '两次密码不一致', icon: 'none' })
		return
	}
	loading.value = true
	try {
		await authApi.register(form)
		uni.showToast({ title: '注册成功，请登录', icon: 'success' })
		setTimeout(() => { uni.navigateBack() }, 1000)
	} catch (e) { console.error(e) }
	finally { loading.value = false }
}

function goBack() { uni.navigateBack() }
</script>

<style scoped>
.register-card { background: #fff; border-radius: 24rpx; padding: 48rpx 32rpx; box-shadow: 0 8rpx 32rpx rgba(0,0,0,0.06); }
.card-title { font-size: 40rpx; font-weight: 700; color: #303133; display: block; text-align: center; margin-bottom: 40rpx; }
.input-group { margin-bottom: 36rpx; }
.form-input { background: #F5F7FA; border-radius: 16rpx; padding: 28rpx 24rpx; font-size: 28rpx; margin-bottom: 20rpx; }
.footer-link { text-align: center; margin-top: 36rpx; font-size: 26rpx; color: #909399; }
.link { color: #FF8C42; margin-left: 8rpx; font-size: 26rpx; }
</style>
