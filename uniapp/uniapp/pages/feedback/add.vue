<template>
	<view class="page-wrap">
		<view class="feedback-card">
			<text class="form-label">反馈内容</text>
			<textarea v-model="content" class="feedback-input"
				placeholder="请描述您在使用过程中遇到的问题或建议..." :maxlength="500" />
			<text class="char-count">{{ content.length }}/500</text>
			<button class="btn-primary" :disabled="loading" @click="handleSubmit">
				{{ loading ? '提交中...' : '提交反馈' }}
			</button>
		</view>
	</view>
</template>

<script setup>
import { ref } from 'vue'
import { feedbackApi } from '../../api/feedback.js'

const content = ref('')
const loading = ref(false)

async function handleSubmit() {
	if (!content.value.trim()) {
		uni.showToast({ title: '请输入反馈内容', icon: 'none' })
		return
	}
	loading.value = true
	try {
		await feedbackApi.submit({ content: content.value })
		uni.showToast({ title: '反馈已提交', icon: 'success' })
		setTimeout(() => { uni.navigateBack() }, 1000)
	} catch (e) { console.error(e) }
	finally { loading.value = false }
}
</script>

<style scoped>
.feedback-card { background: #fff; border-radius: 24rpx; padding: 40rpx 32rpx; box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.04); }
.form-label { font-size: 28rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 16rpx; }
.feedback-input { width: 100%; height: 320rpx; background: #F5F7FA; border-radius: 16rpx; padding: 20rpx; font-size: 28rpx; box-sizing: border-box; }
.char-count { text-align: right; font-size: 24rpx; color: #C0C4CC; margin: 10rpx 0 30rpx; display: block; }
</style>
