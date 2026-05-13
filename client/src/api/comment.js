import request from './request'

export const commentApi = {
  list: (params) => request.get('/api/admin/comment/list', { params }),
  delete: (id) => request.delete(`/api/admin/comment/${id}`)
}
