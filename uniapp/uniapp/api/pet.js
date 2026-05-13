import request from './request'

export const petApi = {
	list: (params) => request.get('/api/pet/list', params),
	getById: (id) => request.get('/api/pet/' + id)
}
