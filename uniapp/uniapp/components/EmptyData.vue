<template>
	<view class="empty-data">
		<text class="empty-icon">📭</text>
		<text class="empty-text">{{ message }}</text>
	</view>
</template>

<script setup>
defineProps({
	message: { type: String, default: '暂无数据' }
})
</script>

<style scoped>
.empty-data {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 100rpx 0;
}

.empty-icon {
	font-size: 80rpx;
	margin-bottom: 20rpx;
}

.empty-text {
	font-size: 28rpx;
	color: #909399;
}
</style>
