<template>
	<view class="custom-navbar" :style="{ paddingTop: statusBarHeight + 'px' }">
		<view class="navbar-content" :style="{ height: navHeight + 'px' }">
			<view class="navbar-left" @click="goBack" v-if="showBack">
				<text class="back-icon">&#8592;</text>
			</view>
			<view class="navbar-title">{{ title }}</view>
			<view class="navbar-right"></view>
		</view>
	</view>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
	title: { type: String, default: '宠物领养' },
	showBack: { type: Boolean, default: false }
})

const statusBarHeight = ref(20)
const navHeight = ref(44)

onMounted(() => {
	const systemInfo = uni.getSystemInfoSync()
	statusBarHeight.value = systemInfo.statusBarHeight || 20
	navHeight.value = 44
})

function goBack() {
	uni.navigateBack()
}
</script>

<style scoped>
.custom-navbar {
	background: linear-gradient(135deg, #FF8C42, #E67A2E);
}

.navbar-content {
	display: flex;
	align-items: center;
	justify-content: center;
	padding: 0 20rpx;
	position: relative;
}

.navbar-title {
	color: #fff;
	font-size: 34rpx;
	font-weight: 600;
}

.navbar-left {
	position: absolute;
	left: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	width: 60rpx;
	height: 60rpx;
}

.back-icon {
	color: #fff;
	font-size: 40rpx;
}

.navbar-right {
	position: absolute;
	right: 20rpx;
}
</style>
