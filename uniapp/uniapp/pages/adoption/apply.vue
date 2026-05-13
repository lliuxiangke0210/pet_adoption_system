<template>
	<view class="page-wrap">
		<view class="apply-card">
			<text class="card-title">领养申请</text>
			<view class="pet-summary" v-if="pet.name">
				<image :src="getImageUrl(pet.image)" mode="aspectFill" class="pet-thumb" />
				<view>
					<text class="pet-name">{{ pet.name }}</text>
					<text class="pet-info">{{ pet.breed }} · {{ pet.gender }} · {{ pet.age }}个月</text>
				</view>
			</view>

			<text class="form-label">领养理由</text>
			<textarea v-model="reason" class="reason-input" placeholder="请详细说明您的领养理由及家庭环境，这将有助于我们审核您的申请..."
				:maxlength="500" />
			<text class="char-count">{{ reason.length }}/500</text>

			<button class="btn-primary" :disabled="loading" @click="handleApply">
				{{ loading ? '提交中...' : '提交申请' }}
			</button>
		</view>
	</view>
</template>

<script setup>
import { ref } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getImageUrl } from '../../utils/index.js'
import { petApi } from '../../api/pet.js'
import { adoptionApi } from '../../api/adoption.js'

const pet = ref({})
const reason = ref('')
const loading = ref(false)

async function fetchPet(id) {
	try { const res = await petApi.getById(id); pet.value = res.data } catch (e) { console.error(e) }
}

async function handleApply() {
	if (!reason.value.trim()) {
		uni.showToast({ title: '请输入领养理由', icon: 'none' })
		return
	}
	loading.value = true
	try {
		await adoptionApi.apply({ petId: pet.value.id, reason: reason.value })
		uni.showToast({ title: '申请已提交，请等待审核', icon: 'success' })
		// 跳转到"我的领养"页面，而不是返回宠物详情
		setTimeout(() => { uni.redirectTo({ url: '/pages/adoption/list' }) }, 1200)
	} catch (e) { console.error(e) }
	finally { loading.value = false }
}

onLoad((options) => {
	if (options?.petId) fetchPet(parseInt(options.petId))
})
</script>

<style scoped>
.apply-card { background: #fff; border-radius: 24rpx; padding: 40rpx 32rpx; box-shadow: 0 4rpx 16rpx rgba(0,0,0,0.04); }
.card-title { font-size: 36rpx; font-weight: 700; color: #303133; display: block; margin-bottom: 30rpx; }
.pet-summary { display: flex; align-items: center; gap: 20rpx; background: #FFF5EC; border-radius: 16rpx; padding: 20rpx; margin-bottom: 30rpx; }
.pet-thumb { width: 120rpx; height: 90rpx; border-radius: 12rpx; }
.pet-name { font-size: 30rpx; font-weight: 600; color: #303133; display: block; }
.pet-info { font-size: 24rpx; color: #909399; }
.form-label { font-size: 28rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 16rpx; }
.reason-input { width: 100%; height: 280rpx; background: #F5F7FA; border-radius: 16rpx; padding: 20rpx; font-size: 28rpx; box-sizing: border-box; }
.char-count { text-align: right; font-size: 24rpx; color: #C0C4CC; margin: 10rpx 0 30rpx; display: block; }
</style>
