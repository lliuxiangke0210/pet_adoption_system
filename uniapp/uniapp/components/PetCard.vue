<template>
	<view class="pet-card" @click="$emit('click')">
		<image class="pet-img" :src="imgSrc" mode="aspectFill" @error="onImgError" />
		<view class="pet-info">
			<view class="pet-name-row">
				<text class="pet-name">{{ pet.name }}</text>
				<text class="tag" :class="statusClass">{{ statusLabel }}</text>
			</view>
			<text class="pet-breed">{{ pet.breed }} · {{ pet.gender }}</text>
			<text class="pet-meta">{{ pet.age }}个月 · {{ pet.size }}</text>
		</view>
	</view>
</template>

<script setup>
import { ref, computed } from 'vue'
import { getImageUrl } from '../utils/index.js'

const props = defineProps({
	pet: { type: Object, required: true }
})

const imgSrc = ref(getImageUrl(props.pet.image))
function onImgError() { imgSrc.value = '/static/logo.png' }

const statusClass = computed(() => {
	const map = { available: 'tag-success', pending: 'tag-warning', adopted: 'tag-info' }
	return map[props.pet.status] || 'tag-info'
})

const statusLabel = computed(() => {
	const map = { available: '可领养', pending: '审核中', adopted: '已领养' }
	return map[props.pet.status] || props.pet.status
})
</script>

<style scoped>
.pet-card {
	background: #fff;
	border-radius: 16rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.06);
	margin-bottom: 20rpx;
}
.pet-img {
	width: 100%;
	height: 300rpx;
	background: linear-gradient(135deg, #FFF5EC, #FFE4D0);
}
.pet-info {
	padding: 20rpx;
}
.pet-name-row {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 10rpx;
}
.pet-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #303133;
}
.pet-breed {
	font-size: 26rpx;
	color: #606266;
	display: block;
	margin-bottom: 6rpx;
}
.pet-meta {
	font-size: 24rpx;
	color: #909399;
}
</style>
