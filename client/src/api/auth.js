import request from './request'

export const authApi = {
  login: (data) => request.post('/api/auth/login', data),
  register: (data) => request.post('/api/auth/register', data),
  getInfo: () => request.get('/api/auth/info')
}
