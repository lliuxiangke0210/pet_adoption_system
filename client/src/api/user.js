import request from './request'

export const userApi = {
  list: (params) => request.get('/api/admin/user/list', { params }),
  getById: (id) => request.get(`/api/admin/user/${id}`),
  add: (data) => request.post('/api/admin/user', data),
  update: (data) => request.put('/api/admin/user', data),
  delete: (id) => request.delete(`/api/admin/user/${id}`)
}
