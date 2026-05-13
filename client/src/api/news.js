import request from './request'

export const newsApi = {
  list: (params) => request.get('/api/news/list', { params }),
  getById: (id) => request.get(`/api/news/${id}`),
  add: (data) => request.post('/api/admin/news', data),
  update: (data) => request.put('/api/admin/news', data),
  delete: (id) => request.delete(`/api/admin/news/${id}`)
}
