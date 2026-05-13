<template>
	<view class="page-wrap">
		<scroll-view scroll-x class="tab-scroll">
			<view class="tab-list">
				<view class="tab-item" :class="{ active: !activeCategory }" @click="changeCategory(null)">全部</view>
				<view class="tab-item" v-for="c in categories" :key="c.id"
					:class="{ active: activeCategory === c.id }" @click="changeCategory(c.id)">
					{{ c.name }}
				</view>
			</view>
		</scroll-view>

		<view v-if="pets.length > 0">
			<PetCard v-for="pet in pets" :key="pet.id" :pet="pet" @click="goToDetail(pet.id)" />
		</view>
		<EmptyData v-else message="暂无宠物数据" />

		<view v-if="hasMore" class="load-more" @click="loadMore">
			<text>{{ loading ? '加载中...' : '加载更多' }}</text>
		</view>
		<view v-else class="no-more"><text>-- 已经到底了 --</text></view>
	</view>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { onShow } from '@dcloudio/uni-app'
import PetCard from '../../components/PetCard.vue'
import EmptyData from '../../components/EmptyData.vue'
import { petApi } from '../../api/pet.js'
import { categoryApi } from '../../api/category.js'

const categories = ref([])
const activeCategory = ref(null)
const pets = ref([])
const pageNum = ref(1)
const total = ref(0)
const loading = ref(false)

const hasMore = computed(() => pets.value.length < total.value)

function changeCategory(id) {
	activeCategory.value = id
	pageNum.value = 1
	pets.value = []
	fetchPets()
}

async function fetchPets() {
	loading.value = true
	try {
		const res = await petApi.list({ pageNum: pageNum.value, pageSize: 10, categoryId: activeCategory.value, status: 'available' })
		if (pageNum.value === 1) {
			pets.value = res.data.records || []
		} else {
			pets.value = pets.value.concat(res.data.records || [])
		}
		total.value = res.data.total || 0
	} catch (e) { console.error(e) }
	finally { loading.value = false }
}

function loadMore() { pageNum.value++; fetchPets() }

function goToDetail(id) { uni.navigateTo({ url: '/pages/pet/detail?id=' + id }) }

onMounted(async () => {
	try { const res = await categoryApi.list(); categories.value = res.data || [] } catch (e) { console.error(e) }
	fetchPets()
})

// 每次切换到此tab时检查是否有首页传入的分类筛选
onShow(() => {
	const app = getApp()
	if (app.globalData.petCategoryId) {
		const catId = app.globalData.petCategoryId
		app.globalData.petCategoryId = null
		activeCategory.value = catId
		pageNum.value = 1
		pets.value = []
		fetchPets()
	}
})
</script>

<style scoped>
.page-wrap { padding: 20rpx; min-height: 100vh; }
.tab-scroll { white-space: nowrap; margin-bottom: 20rpx; }
.tab-list { display: inline-flex; gap: 16rpx; padding: 8rpx 0; }
.tab-item { padding: 16rpx 32rpx; border-radius: 28rpx; font-size: 26rpx; color: #606266; background: #fff; transition: all 0.3s; }
.tab-item.active { background: linear-gradient(135deg, #FF8C42, #E67A2E); color: #fff; font-weight: 600; }
.load-more { text-align: center; padding: 30rpx; color: #FF8C42; font-size: 28rpx; }
.no-more { text-align: center; padding: 30rpx; color: #C0C4CC; font-size: 24rpx; }
</style>
