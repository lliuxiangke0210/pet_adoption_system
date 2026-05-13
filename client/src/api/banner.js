import request from './request'

export const bannerApi = {
  list: () => request.get('/api/admin/banner'),
  add: (data) => request.post('/api/admin/banner', data),
  update: (data) => request.put('/api/admin/banner', data),
  delete: (id) => request.delete(`/api/admin/banner/${id}`)
}
