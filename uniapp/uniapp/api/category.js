import request from './request'

export const categoryApi = {
	list: () => request.get('/api/category/list')
}
