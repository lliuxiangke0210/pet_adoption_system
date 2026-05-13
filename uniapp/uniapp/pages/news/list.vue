<template>
	<view class="page-wrap">
		<view class="news-card card" v-for="item in newsList" :key="item.id" @click="goToDetail(item.id)">
			<image v-if="item.coverImage" :src="getImageUrl(item.coverImage)" class="news-cover" mode="aspectFill" />
			<view class="news-content">
				<text class="news-title">{{ item.title }}</text>
				<text class="news-desc">{{ stripHtml(item.content).substring(0, 80) }}...</text>
				<view class="news-meta">
					<text>{{ item.author || '管理员' }}</text>
					<text>{{ item.viewCount || 0 }} 阅读</text>
					<text>{{ item.createTime }}</text>
				</view>
			</view>
		</view>
		<EmptyData v-if="newsList.length === 0" message="暂无资讯" />
		<view class="no-more"><text>— 已经到底了 —</text></view>
	</view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getImageUrl } from '../../utils/index.js'
import EmptyData from '../../components/EmptyData.vue'
import { newsApi } from '../../api/news.js'

const newsList = ref([])

function stripHtml(html) {
	if (!html) return ''
	return html.replace(/<[^>]+>/g, '')
}

function goToDetail(id) { uni.navigateTo({ url: '/pages/news/detail?id=' + id }) }

onMounted(async () => {
	try { const res = await newsApi.list({ pageNum: 1, pageSize: 20 }); newsList.value = res.data?.records || [] } catch (e) { console.error(e) }
})
</script>

<style scoped>
.news-card { padding: 0; overflow: hidden; }
.news-cover { width: 100%; height: 320rpx; }
.news-content { padding: 24rpx; }
.news-title { font-size: 32rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 12rpx; }
.news-desc { font-size: 26rpx; color: #909399; display: block; margin-bottom: 16rpx; line-height: 1.6; }
.news-meta { display: flex; gap: 24rpx; font-size: 22rpx; color: #C0C4CC; }
.no-more { text-align: center; padding: 30rpx; color: #C0C4CC; font-size: 24rpx; }
</style>
