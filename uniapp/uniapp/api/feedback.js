import request from './request'

export const feedbackApi = {
	submit: (data) => request.post('/api/feedback', data),
	myFeedbacks: () => request.get('/api/feedback/my')
}
