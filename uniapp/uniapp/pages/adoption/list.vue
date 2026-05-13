<template>
	<view class="page-wrap">
		<view class="adoption-card card" v-for="item in adoptions" :key="item.id" @click="goToDetail(item.id)">
			<view class="adoption-header">
				<image v-if="item.petImage" :src="getImageUrl(item.petImage)" class="adoption-img" mode="aspectFill" />
				<view class="adoption-info">
					<text class="adoption-pet-name">{{ item.petName || '宠物' }}</text>
					<text class="adoption-breed">{{ item.petBreed || '' }}</text>
				</view>
				<text class="tag" :class="statusClass(item.status)">{{ statusLabel(item.status) }}</text>
			</view>
			<view class="adoption-body">
				<text class="reason-label">领养理由：</text>
				<text class="reason-text">{{ item.reason }}</text>
			</view>
			<view class="adoption-footer">
				<text class="apply-time">申请时间：{{ item.applyTime }}</text>
			</view>
		</view>
		<EmptyData v-if="adoptions.length === 0" message="暂无领养记录" />
	</view>
</template>

<script setup>
import { ref } from 'vue'
import { onShow, onPullDownRefresh } from '@dcloudio/uni-app'
import { getImageUrl } from '../../utils/index.js'
import EmptyData from '../../components/EmptyData.vue'
import { adoptionApi } from '../../api/adoption.js'

const adoptions = ref([])
const refreshing = ref(false)

function statusClass(status) {
	return { pending: 'tag-warning', approved: 'tag-success', rejected: 'tag-danger' }[status] || 'tag-info'
}
function statusLabel(status) {
	return { pending: '审核中', approved: '已通过', rejected: '已拒绝' }[status] || status
}

function goToDetail(id) { uni.navigateTo({ url: '/pages/adoption/detail?id=' + id }) }

async function fetchAdoptions() {
	try { const res = await adoptionApi.myAdoptions(); adoptions.value = res.data || [] } catch (e) { console.error(e) }
}

onShow(() => { fetchAdoptions() })

onPullDownRefresh(async () => {
	refreshing.value = true
	await fetchAdoptions()
	refreshing.value = false
	uni.stopPullDownRefresh()
})
</script>

<style scoped>
.adoption-card { padding: 24rpx; }
.adoption-header { display: flex; align-items: center; gap: 20rpx; margin-bottom: 16rpx; }
.adoption-img { width: 100rpx; height: 80rpx; border-radius: 12rpx; }
.adoption-info { flex: 1; }
.adoption-pet-name { font-size: 30rpx; font-weight: 600; color: #303133; display: block; }
.adoption-breed { font-size: 24rpx; color: #909399; }
.adoption-body { background: #F5F7FA; border-radius: 12rpx; padding: 16rpx; margin-bottom: 16rpx; }
.reason-label { font-size: 24rpx; color: #909399; }
.reason-text { font-size: 28rpx; color: #606266; }
.adoption-footer { font-size: 24rpx; color: #C0C4CC; }
.apply-time { font-size: 24rpx; }
</style>
