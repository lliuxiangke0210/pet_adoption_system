import request from './request'

export const newsApi = {
	list: (params) => request.get('/api/news/list', params),
	getById: (id) => request.get('/api/news/' + id),
	getComments: (newsId) => request.get(`/api/news/${newsId}/comment`),
	addComment: (data) => request.post('/api/comment', data)
}
