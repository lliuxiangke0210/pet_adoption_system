<template>
	<view class="home-page">
		<CustomNavbar title="宠物领养" />
		<swiper v-if="banners.length > 0" class="banner-swiper" indicator-dots autoplay circular interval="4000">
			<swiper-item v-for="item in banners" :key="item.id">
				<image class="banner-img" :src="getImageUrl(item.image)" mode="aspectFill" />
				<view class="banner-title">{{ item.title }}</view>
			</swiper-item>
		</swiper>

		<view class="section">
			<view class="section-header">
				<text class="section-title">宠物分类</text>
			</view>
			<scroll-view scroll-x class="category-scroll">
				<view class="category-list">
					<view class="category-item" v-for="c in categories" :key="c.id" @click="navigateToPets(c.id)">
						<view class="category-icon"><text>{{ c.name === '猫咪' ? '🐱' : c.name === '狗狗' ? '🐶' : '🐹' }}</text></view>
						<text class="category-name">{{ c.name }}</text>
					</view>
				</view>
			</scroll-view>
		</view>

		<view class="section">
			<view class="section-header">
				<text class="section-title">推荐宠物</text>
				<text class="section-more" @click="navigateToPets()">查看更多 ›</text>
			</view>
			<PetCard v-for="pet in pets" :key="pet.id" :pet="pet" @click="goToDetail(pet.id)" />
			<EmptyData v-if="pets.length === 0" message="暂无宠物数据" />
		</view>
	</view>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import CustomNavbar from '../../components/CustomNavbar.vue'
import PetCard from '../../components/PetCard.vue'
import EmptyData from '../../components/EmptyData.vue'
import { getImageUrl } from '../../utils/index.js'
import { bannerApi } from '../../api/banner.js'
import { categoryApi } from '../../api/category.js'
import { petApi } from '../../api/pet.js'

const banners = ref([])
const categories = ref([])
const pets = ref([])

async function fetchBanners() {
	try { const res = await bannerApi.listActive(); banners.value = res.data || [] } catch (e) { console.error(e) }
}
async function fetchCategories() {
	try { const res = await categoryApi.list(); categories.value = res.data || [] } catch (e) { console.error(e) }
}
async function fetchPets() {
	try { const res = await petApi.list({ pageNum: 1, pageSize: 6, status: 'available' }); pets.value = res.data?.records || [] } catch (e) { console.error(e) }
}
function navigateToPets(categoryId) {
	// tabbar页面不能用navigateTo，改用switchTab + 全局数据传参
	if (categoryId) getApp().globalData.petCategoryId = categoryId
	else getApp().globalData.petCategoryId = null
	uni.switchTab({ url: '/pages/pet/list' })
}
function goToDetail(id) { uni.navigateTo({ url: '/pages/pet/detail?id=' + id }) }

onMounted(() => { fetchBanners(); fetchCategories(); fetchPets() })
</script>

<style scoped>
.home-page { min-height: 100vh; background: #F5F7FA; padding-bottom: 30rpx; }
.banner-swiper { height: 360rpx; }
.banner-img { width: 100%; height: 100%; }
.banner-title { position: absolute; bottom: 30rpx; left: 30rpx; color: #fff; font-size: 32rpx; font-weight: 600; text-shadow: 0 2rpx 8rpx rgba(0,0,0,0.3); }
.section { padding: 24rpx; }
.section-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20rpx; }
.section-title { font-size: 34rpx; font-weight: 700; color: #303133; padding-left: 20rpx; position: relative; }
.section-title::before { content: ''; position: absolute; left: 0; top: 50%; transform: translateY(-50%); width: 6rpx; height: 36rpx; background: #FF8C42; border-radius: 3rpx; }
.section-more { font-size: 26rpx; color: #909399; }
.category-scroll { white-space: nowrap; }
.category-list { display: inline-flex; gap: 24rpx; }
.category-item { display: flex; flex-direction: column; align-items: center; gap: 12rpx; }
.category-icon { width: 100rpx; height: 100rpx; background: #fff; border-radius: 20rpx; display: flex; align-items: center; justify-content: center; font-size: 44rpx; box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.06); }
.category-name { font-size: 24rpx; color: #606266; }
</style>
