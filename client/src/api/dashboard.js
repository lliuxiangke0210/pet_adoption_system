import request from './request'

export const dashboardApi = {
  getStats: () => request.get('/api/admin/dashboard')
}
