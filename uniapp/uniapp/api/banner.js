import request from './request'

export const bannerApi = {
	listActive: () => request.get('/api/banner/list')
}
