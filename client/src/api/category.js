import request from './request'

export const categoryApi = {
  list: () => request.get('/api/category/list'),
  add: (data) => request.post('/api/admin/category', data),
  update: (data) => request.put('/api/admin/category', data),
  delete: (id) => request.delete(`/api/admin/category/${id}`)
}
