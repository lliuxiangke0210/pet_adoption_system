<template>
	<view class="detail-page">
		<image v-if="article.coverImage" :src="getImageUrl(article.coverImage)" class="cover-img" mode="aspectFill" />

		<view class="article-content">
			<text class="article-title">{{ article.title }}</text>
			<view class="article-meta">
				<text>{{ article.author || '管理员' }}</text>
				<text>{{ article.viewCount || 0 }} 阅读</text>
				<text>{{ article.createTime }}</text>
			</view>

			<view class="article-body">
				<rich-text :nodes="article.content || ''" />
			</view>

			<!-- 评论区 -->
			<view class="comment-section">
				<text class="comment-title">评论 ({{ comments.length }})</text>

				<view class="comment-item card" v-for="c in comments" :key="c.id">
					<view class="comment-header">
						<text class="comment-user">{{ c.username }}</text>
						<text class="comment-time">{{ c.createTime }}</text>
					</view>
					<text class="comment-text">{{ c.content }}</text>
				</view>
				<EmptyData v-if="comments.length === 0" message="暂无评论，来说两句吧" />

				<!-- 评论输入 -->
				<view class="comment-input">
					<input v-model="commentText" placeholder="写下你的评论..." />
					<text class="send-btn" @click="handleComment">发送</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getImageUrl } from '../../utils/index.js'
import EmptyData from '../../components/EmptyData.vue'
import { useUserStore } from '../../store/user.js'
import { newsApi } from '../../api/news.js'

const userStore = useUserStore()
const article = ref({})
const comments = ref([])
const commentText = ref('')

async function fetchArticle(id) {
	try { const res = await newsApi.getById(id); article.value = res.data } catch (e) { console.error(e) }
}
async function fetchComments(id) {
	try { const res = await newsApi.getComments(id); comments.value = res.data || [] } catch (e) { console.error(e) }
}

async function handleComment() {
	if (!commentText.value.trim()) {
		uni.showToast({ title: '请输入评论内容', icon: 'none' })
		return
	}
	if (!userStore.isLoggedIn()) {
		uni.showModal({
			title: '提示', content: '请先登录后再评论',
			success: (res) => { if (res.confirm) uni.navigateTo({ url: '/pages/login/login' }) }
		})
		return
	}
	try {
		await newsApi.addComment({ newsId: article.value.id, content: commentText.value })
		uni.showToast({ title: '评论成功', icon: 'success' })
		commentText.value = ''
		fetchComments(article.value.id)
	} catch (e) { console.error(e) }
}

onLoad((options) => {
	if (options?.id) {
		fetchArticle(parseInt(options.id))
		fetchComments(parseInt(options.id))
	}
})
</script>

<style scoped>
.detail-page { background: #F5F7FA; min-height: 100vh; }
.cover-img { width: 100%; height: 400rpx; }
.article-content { padding: 30rpx; }
.article-title { font-size: 38rpx; font-weight: 700; color: #303133; display: block; margin-bottom: 16rpx; }
.article-meta { display: flex; gap: 24rpx; font-size: 24rpx; color: #C0C4CC; margin-bottom: 30rpx; }
.article-body { font-size: 30rpx; color: #303133; line-height: 1.8; margin-bottom: 40rpx; }
.comment-section { padding-top: 30rpx; border-top: 1px solid #EBEEF5; }
.comment-title { font-size: 30rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 20rpx; }
.comment-item { padding: 20rpx 24rpx; }
.comment-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12rpx; }
.comment-user { font-size: 28rpx; font-weight: 600; color: #FF8C42; }
.comment-time { font-size: 22rpx; color: #C0C4CC; }
.comment-text { font-size: 28rpx; color: #606266; line-height: 1.6; }
.comment-input { display: flex; align-items: center; gap: 16rpx; background: #fff; border-radius: 44rpx; padding: 16rpx 24rpx; margin-top: 24rpx; box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.06); }
.comment-input input { flex: 1; font-size: 28rpx; }
.send-btn { color: #FF8C42; font-size: 28rpx; font-weight: 600; padding: 8rpx 16rpx; }
</style>
