import { defineStore } from 'pinia'
import { ref } from 'vue'
import { authApi } from '../api/auth'

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  /** 登录 */
  async function login(username, password) {
    const res = await authApi.login({ username, password })
    token.value = res.data.token
    localStorage.setItem('token', res.data.token)

    const infoRes = await authApi.getInfo()
    userInfo.value = infoRes.data
    localStorage.setItem('userInfo', JSON.stringify(infoRes.data))

    return res
  }

  /** 登出 */
  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
    localStorage.removeItem('userInfo')
  }

  return { token, userInfo, login, logout }
})
