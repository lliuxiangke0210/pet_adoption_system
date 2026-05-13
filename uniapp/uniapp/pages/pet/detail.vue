<template>
	<view class="detail-page">
		<!-- 加载中 -->
		<view v-if="loading" class="loading-wrap">
			<text class="loading-text">加载中...</text>
		</view>

		<template v-else>
			<image v-if="pet.image" class="detail-img" :src="getImageUrl(pet.image)" mode="aspectFill" @error="onImgError" />
			<view v-else class="detail-img-empty">🐾</view>

			<view class="detail-content">
				<view class="detail-header">
					<text class="detail-name">{{ pet.name || '(无名)' }}</text>
					<text class="tag" :class="statusClass">{{ statusLabel }}</text>
				</view>

				<view class="info-grid">
					<view class="info-item">
						<text class="info-label">品种</text>
						<text class="info-value">{{ pet.breed || '-' }}</text>
					</view>
					<view class="info-item">
						<text class="info-label">年龄</text>
						<text class="info-value">{{ pet.age ? pet.age + '个月' : '-' }}</text>
					</view>
					<view class="info-item">
						<text class="info-label">性别</text>
						<text class="info-value">{{ pet.gender || '-' }}</text>
					</view>
					<view class="info-item">
						<text class="info-label">毛色</text>
						<text class="info-value">{{ pet.color || '-' }}</text>
					</view>
					<view class="info-item">
						<text class="info-label">体型</text>
						<text class="info-value">{{ pet.size || '-' }}</text>
					</view>
					<view class="info-item">
						<text class="info-label">分类</text>
						<text class="info-value">{{ pet.categoryName || '-' }}</text>
					</view>
				</view>

				<view class="desc-section">
					<text class="desc-title">详细描述</text>
					<text class="desc-text">{{ pet.description || '暂无描述信息' }}</text>
				</view>

				<button v-if="pet.status === 'available'" class="btn-primary adopt-btn" @click="goApply">
					申请领养
				</button>
				<button v-else class="btn-disabled adopt-btn" disabled>
					{{ pet.status === 'pending' ? '正在审核中' : '已被领养' }}
				</button>
			</view>
		</template>
	</view>
</template>

<script setup>
import { ref, computed } from 'vue'
import { onLoad, onShow } from '@dcloudio/uni-app'
import { getImageUrl } from '../../utils/index.js'
import { petApi } from '../../api/pet.js'
import { useUserStore } from '../../store/user.js'

const userStore = useUserStore()
const pet = ref({})
const loading = ref(true)
const imgError = ref(false)
let currentPetId = null

const statusClass = computed(() => {
	const map = { available: 'tag-success', pending: 'tag-warning', adopted: 'tag-info' }
	return map[pet.value.status] || 'tag-info'
})

const statusLabel = computed(() => {
	const map = { available: '可领养', pending: '审核中', adopted: '已领养' }
	return map[pet.value.status] || pet.value.status
})

function onImgError() { imgError.value = true }

async function fetchPet(id) {
	loading.value = true
	try {
		const res = await petApi.getById(id)
		if (res && res.data) {
			pet.value = res.data
		}
	} catch (e) {
		console.error('获取宠物详情失败', e)
		uni.showToast({ title: '加载失败', icon: 'none' })
	} finally {
		loading.value = false
	}
}

function goApply() {
	if (!userStore.isLoggedIn()) {
		uni.showModal({
			title: '提示',
			content: '请先登录后再申请领养',
			success: (res) => {
				if (res.confirm) uni.navigateTo({ url: '/pages/login/login' })
			}
		})
		return
	}
	uni.navigateTo({ url: '/pages/adoption/apply?petId=' + pet.value.id })
}

onLoad((options) => {
	if (options?.id) {
		currentPetId = parseInt(options.id)
		fetchPet(currentPetId)
	} else {
		loading.value = false
		uni.showToast({ title: '参数错误', icon: 'none' })
	}
})

// 每次页面显示时刷新宠物数据（防止从其他页面返回后状态过期）
onShow(() => {
	if (currentPetId) {
		fetchPet(currentPetId)
	}
})
</script>

<style scoped>
.detail-page { background: #F5F7FA; min-height: 100vh; padding-bottom: 40rpx; }
.detail-img { width: 100%; height: 480rpx; }
.detail-img-empty {
	width: 100%; height: 480rpx;
	background: linear-gradient(135deg, #FFF5EC, #FFE4D0);
	display: flex; align-items: center; justify-content: center;
	font-size: 80rpx;
}
.loading-wrap { display: flex; align-items: center; justify-content: center; height: 60vh; }
.loading-text { color: #909399; font-size: 28rpx; }
.detail-content { padding: 30rpx; }
.detail-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30rpx; }
.detail-name { font-size: 40rpx; font-weight: 700; color: #303133; }
.info-grid { display: flex; flex-wrap: wrap; background: #fff; border-radius: 16rpx; padding: 24rpx; margin-bottom: 24rpx; }
.info-item { width: 33.33%; padding: 16rpx 0; }
.info-label { display: block; font-size: 24rpx; color: #909399; margin-bottom: 6rpx; }
.info-value { font-size: 28rpx; color: #303133; font-weight: 500; }
.desc-section { background: #fff; border-radius: 16rpx; padding: 24rpx; margin-bottom: 40rpx; }
.desc-title { font-size: 30rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 16rpx; }
.desc-text { font-size: 28rpx; color: #606266; line-height: 1.8; }
.adopt-btn { width: 100%; margin-top: 20rpx; }
.btn-disabled { background: #C0C4CC; color: #fff; border-radius: 44rpx; font-size: 32rpx; padding: 24rpx 0; text-align: center; }
</style>
