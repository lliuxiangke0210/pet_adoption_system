import request from './request'

export const adoptionApi = {
  list: (params) => request.get('/api/admin/adoption/list', { params }),
  approve: (id, status) => request.put(`/api/admin/adoption/${id}`, null, { params: { status } })
}
