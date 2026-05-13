import request from './request'

export const adoptionApi = {
	apply: (data) => request.post('/api/adoption', data),
	myAdoptions: () => request.get('/api/adoption/my')
}
