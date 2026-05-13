import request from './request'

export const feedbackApi = {
  list: (params) => request.get('/api/admin/feedback/list', { params }),
  reply: (id, reply) => request.put(`/api/admin/feedback/${id}`, null, { params: { reply } })
}
