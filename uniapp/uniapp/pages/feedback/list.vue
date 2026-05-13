<template>
	<view class="page-wrap">
		<view class="feedback-card card" v-for="item in feedbacks" :key="item.id">
			<view class="fb-header">
				<text class="fb-time">{{ item.createTime }}</text>
				<text class="tag" :class="item.status === 'replied' ? 'tag-success' : 'tag-warning'">
					{{ item.status === 'replied' ? '已回复' : '待回复' }}
				</text>
			</view>
			<view class="fb-content">
				<text class="fb-label">我的反馈：</text>
				<text class="fb-text">{{ item.content }}</text>
			</view>
			<view class="fb-reply" v-if="item.reply">
				<text class="fb-label" style="color: #67C23A">管理员回复：</text>
				<text class="fb-text" style="color: #67C23A">{{ item.reply }}</text>
			</view>
		</view>
		<EmptyData v-if="feedbacks.length === 0" message="暂无反馈" />
	</view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import EmptyData from '../../components/EmptyData.vue'
import { feedbackApi } from '../../api/feedback.js'

const feedbacks = ref([])

onMounted(async () => {
	try { const res = await feedbackApi.myFeedbacks(); feedbacks.value = res.data || [] } catch (e) { console.error(e) }
})
</script>

<style scoped>
.feedback-card { padding: 24rpx; }
.fb-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 16rpx; }
.fb-time { font-size: 24rpx; color: #C0C4CC; }
.fb-content { margin-bottom: 16rpx; }
.fb-label { font-size: 24rpx; color: #909399; display: block; margin-bottom: 8rpx; }
.fb-text { font-size: 28rpx; color: #606266; line-height: 1.6; }
.fb-reply { background: #F0F9EB; border-radius: 12rpx; padding: 16rpx; }
</style>
