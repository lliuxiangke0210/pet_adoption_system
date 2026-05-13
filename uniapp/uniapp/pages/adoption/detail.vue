<template>
	<view class="detail-page">
		<view v-if="loading" class="loading-wrap"><text>加载中...</text></view>

		<template v-else>
			<!-- 宠物信息 -->
			<view class="card pet-card">
				<text class="card-title">领养宠物</text>
				<view class="pet-row">
					<image v-if="adopt.petImage" :src="getImageUrl(adopt.petImage)" class="pet-img" mode="aspectFill" />
					<view class="pet-info">
						<text class="pet-name">{{ adopt.petName || '未知' }}</text>
						<text class="pet-meta">{{ adopt.petBreed || '' }}</text>
					</view>
				</view>
			</view>

			<!-- 状态 -->
			<view class="card status-card">
				<text class="card-title">申请状态</text>
				<view class="status-row">
					<view class="status-badge" :class="statusClass">
						<text class="status-icon">{{ statusIcon }}</text>
						<text class="status-text">{{ statusLabel }}</text>
					</view>
				</view>
			</view>

			<!-- 详细信息 -->
			<view class="card">
				<text class="card-title">申请详情</text>
				<view class="info-row">
					<text class="info-label">领养理由</text>
					<text class="info-value reason-text">{{ adopt.reason || '无' }}</text>
				</view>
				<view class="info-row">
					<text class="info-label">申请时间</text>
					<text class="info-value">{{ formatTime(adopt.applyTime) }}</text>
				</view>
				<view class="info-row" v-if="adopt.approveTime">
					<text class="info-label">审批时间</text>
					<text class="info-value">{{ formatTime(adopt.approveTime) }}</text>
				</view>
			</view>

			<!-- 审批结果 -->
			<view class="card" v-if="adopt.status === 'approved'">
				<text class="card-title" style="color:#67C23A">审批结果：已通过</text>
				<text class="desc-text">恭喜！您的领养申请已通过审核。</text>
				<view class="contact-box">
					<text class="contact-title">联系管理员办理手续</text>
					<view class="contact-item" @click="makePhoneCall">
						<text class="contact-icon">📞</text>
						<view class="contact-info">
							<text class="contact-label">电话</text>
							<text class="contact-value">{{ contact.phone || '13800138000' }}</text>
						</view>
						<text class="contact-action">拨打</text>
					</view>
					<view class="contact-item" v-if="contact.email" @click="copyEmail">
						<text class="contact-icon">📧</text>
						<view class="contact-info">
							<text class="contact-label">邮箱</text>
							<text class="contact-value">{{ contact.email }}</text>
						</view>
						<text class="contact-action">复制</text>
					</view>
					<view class="contact-item">
						<text class="contact-icon">👤</text>
						<view class="contact-info">
							<text class="contact-label">联系人</text>
							<text class="contact-value">{{ contact.realName || '管理员' }}</text>
						</view>
					</view>
				</view>
			</view>
			<view class="card" v-if="adopt.status === 'rejected'">
				<text class="card-title" style="color:#F56C6C">审批结果：已拒绝</text>
				<text class="desc-text">很遗憾，您的领养申请未通过审核。如有疑问可联系管理员。</text>
			</view>
		</template>
	</view>
</template>

<script setup>
import { ref, computed } from 'vue'
import { onLoad } from '@dcloudio/uni-app'
import { getImageUrl } from '../../utils/index.js'
import { adoptionApi } from '../../api/adoption.js'
import request from '../../api/request.js'

const adopt = ref({})
const loading = ref(true)
const contact = ref({ phone: '', email: '', realName: '' })

const statusClass = computed(() => {
	const map = { pending: 'status-pending', approved: 'status-approved', rejected: 'status-rejected' }
	return map[adopt.value.status] || 'status-pending'
})
const statusLabel = computed(() => {
	const map = { pending: '审核中', approved: '已通过', rejected: '已拒绝' }
	return map[adopt.value.status] || '未知'
})
const statusIcon = computed(() => {
	const map = { pending: '⏳', approved: '✅', rejected: '❌' }
	return map[adopt.value.status] || '❓'
})

function formatTime(str) {
	if (!str) return '-'
	return str.replace('T', ' ').substring(0, 19)
}

function makePhoneCall() {
	const phone = contact.value.phone || '13800138000'
	uni.makePhoneCall({ phoneNumber: phone })
}

function copyEmail() {
	uni.setClipboardData({ data: contact.value.email, success: () => {
		uni.showToast({ title: '邮箱已复制', icon: 'success' })
	}})
}

async function fetchContact() {
	try {
		const res = await request.get('/api/auth/admin-contact')
		if (res && res.data) contact.value = res.data
	} catch (e) { console.error(e) }
}

onLoad((options) => {
	if (options?.id) {
		loadAdoption(parseInt(options.id))
		fetchContact()
	} else {
		uni.showToast({ title: '参数错误', icon: 'none' })
	}
})

async function loadAdoption(id) {
	loading.value = true
	try {
		const res = await adoptionApi.myAdoptions()
		const list = res.data || []
		adopt.value = list.find(item => item.id === id) || {}
	} catch (e) {
		console.error(e)
		uni.showToast({ title: '加载失败', icon: 'none' })
	} finally {
		loading.value = false
	}
}
</script>

<style scoped>
.detail-page { min-height: 100vh; background: #F5F7FA; padding: 20rpx; }
.loading-wrap { display: flex; align-items: center; justify-content: center; height: 60vh; color: #909399; }
.card { background: #fff; border-radius: 16rpx; padding: 30rpx; margin-bottom: 20rpx; box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.04); }
.card-title { font-size: 30rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 20rpx; }
.pet-row { display: flex; align-items: center; gap: 20rpx; }
.pet-img { width: 140rpx; height: 110rpx; border-radius: 12rpx; }
.pet-name { font-size: 34rpx; font-weight: 600; color: #303133; display: block; }
.pet-meta { font-size: 26rpx; color: #909399; }
.status-row { display: flex; align-items: center; }
.status-badge { display: flex; align-items: center; gap: 12rpx; padding: 16rpx 28rpx; border-radius: 40rpx; font-size: 28rpx; }
.status-icon { font-size: 36rpx; }
.status-text { font-weight: 600; }
.status-pending { background: #FFF5EC; color: #E6A23C; }
.status-approved { background: #E8F5E9; color: #67C23A; }
.status-rejected { background: #FEF0F0; color: #F56C6C; }
.info-row { padding: 20rpx 0; border-bottom: 1px solid #F5F7FA; }
.info-row:last-child { border-bottom: none; }
.info-label { font-size: 26rpx; color: #909399; display: block; margin-bottom: 8rpx; }
.info-value { font-size: 28rpx; color: #303133; }
.reason-text { line-height: 1.8; }
.desc-text { font-size: 28rpx; color: #606266; line-height: 1.8; margin-bottom: 24rpx; }
.contact-box { background: #F9FAFB; border-radius: 12rpx; padding: 20rpx; }
.contact-title { font-size: 26rpx; font-weight: 600; color: #303133; display: block; margin-bottom: 16rpx; }
.contact-item { display: flex; align-items: center; gap: 16rpx; padding: 16rpx 0; border-bottom: 1px solid #EBEEF5; }
.contact-item:last-child { border-bottom: none; }
.contact-icon { font-size: 36rpx; }
.contact-info { flex: 1; }
.contact-label { font-size: 22rpx; color: #909399; display: block; }
.contact-value { font-size: 28rpx; color: #303133; font-weight: 500; }
.contact-action { font-size: 26rpx; color: #FF8C42; font-weight: 500; }
</style>
