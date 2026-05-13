import { BASE_URL } from '../utils/config.js'

/**
 * uni.request 封装，自动拼接 BASE_URL、注入 Token、过滤 null 参数
 */
function request(options) {
	return new Promise((resolve, reject) => {
		const token = uni.getStorageSync('token')
		const header = {
			'Content-Type': 'application/json',
			...(options.header || {})
		}

		if (token) {
			header['Authorization'] = 'Bearer ' + token
		}

		let url = options.url
		if (!url.startsWith('http')) {
			url = BASE_URL + url
		}

		// 过滤掉 null/undefined 参数
		let data = options.data
		if (data && typeof data === 'object') {
			data = {}
			for (const key in options.data) {
				if (options.data[key] !== null && options.data[key] !== undefined) {
					data[key] = options.data[key]
				}
			}
		}

		uni.request({
			url,
			method: options.method || 'GET',
			data,
			header,
			success: (res) => {
				if (res.statusCode === 200 && res.data.code === 200) {
					resolve(res.data)
				} else if (res.statusCode === 401) {
					uni.removeStorageSync('token')
					uni.removeStorageSync('userInfo')
					uni.reLaunch({ url: '/pages/login/login' })
					reject(new Error(res.data?.message || '认证失败'))
				} else {
					uni.showToast({ title: res.data?.message || '请求失败', icon: 'none' })
					reject(new Error(res.data?.message || '请求失败'))
				}
			},
			fail: (err) => {
				uni.showToast({ title: '网络连接失败', icon: 'none' })
				reject(err)
			}
		})
	})
}

export default {
	get: (url, data) => request({ url, method: 'GET', data }),
	post: (url, data) => request({ url, method: 'POST', data }),
	put: (url, data) => request({ url, method: 'PUT', data }),
	delete: (url, data) => request({ url, method: 'DELETE', data })
}
