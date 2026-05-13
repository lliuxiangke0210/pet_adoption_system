import request from './request'

export const petApi = {
  list: (params) => request.get('/api/pet/list', { params }),
  getById: (id) => request.get(`/api/pet/${id}`),
  add: (data) => request.post('/api/admin/pet', data),
  update: (data) => request.put('/api/admin/pet', data),
  delete: (id) => request.delete(`/api/admin/pet/${id}`)
}
