<script>
	export default {
		globalData: {
			petCategoryId: null
		},
		onLaunch: function() {
			console.log('宠物领养小程序启动')
			const token = uni.getStorageSync('token')
			if (token) {
				console.log('已登录用户')
			}
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	page {
		background-color: #F5F7FA;
		font-family: -apple-system, BlinkMacSystemFont, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
		color: #303133;
		font-size: 28rpx;
	}
	.page-wrap {
		min-height: 100vh;
		padding: 20rpx;
		box-sizing: border-box;
	}
	.btn-primary {
		background: linear-gradient(135deg, #FF8C42, #E67A2E);
		color: #fff;
		border-radius: 44rpx;
		font-size: 32rpx;
		padding: 24rpx 0;
		text-align: center;
		border: none;
		width: 100%;
	}
	.card {
		background: #fff;
		border-radius: 16rpx;
		padding: 24rpx;
		margin-bottom: 20rpx;
		box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.04);
	}
	.tag {
		display: inline-block;
		padding: 4rpx 16rpx;
		border-radius: 8rpx;
		font-size: 22rpx;
	}
	.tag-success { background: #E8F5E9; color: #67C23A; }
	.tag-warning { background: #FFF5EC; color: #FF8C42; }
	.tag-danger { background: #FEF0F0; color: #F56C6C; }
	.tag-info { background: #F0F2F5; color: #909399; }
</style>
