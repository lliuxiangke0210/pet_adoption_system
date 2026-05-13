import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
	const token = ref(uni.getStorageSync('token') || '')
	const userInfo = ref(JSON.parse(uni.getStorageSync('userInfo') || 'null'))

	/** 保存登录状态 */
	function setLogin(data) {
		token.value = data.token
		userInfo.value = {
			id: data.userId,
			username: data.username,
			role: data.role
		}
		uni.setStorageSync('token', data.token)
		uni.setStorageSync('userInfo', JSON.stringify(userInfo.value))
	}

	/** 登出 */
	function logout() {
		token.value = ''
		userInfo.value = null
		uni.removeStorageSync('token')
		uni.removeStorageSync('userInfo')
		uni.reLaunch({ url: '/pages/index/index' })
	}

	/** 是否已登录 */
	function isLoggedIn() {
		return !!token.value
	}

	return { token, userInfo, setLogin, logout, isLoggedIn }
})
