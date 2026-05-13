import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE || '',
  timeout: 15000
})

// 请求拦截器 - 添加 Token
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器 - 统一处理错误
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 200) {
      return res
    }
    // 未授权
    if (res.code === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      router.push('/login')
      return Promise.reject(new Error(res.message || '认证失败'))
    }
    ElMessage.error(res.message || '请求失败')
    return Promise.reject(new Error(res.message || '请求失败'))
  },
  error => {
    if (error.response) {
      const status = error.response.status
      if (status === 401) {
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        router.push('/login')
        ElMessage.error('登录已过期，请重新登录')
      } else if (status === 403) {
        ElMessage.error('没有访问权限')
      } else {
        ElMessage.error('服务器异常')
      }
    } else {
      ElMessage.error('网络连接失败')
    }
    return Promise.reject(error)
  }
)

export default request
