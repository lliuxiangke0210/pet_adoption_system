import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { title: '登录', noAuth: true }
  },
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '仪表盘', icon: 'DataAnalysis' }
      },
      {
        path: 'user',
        name: 'UserList',
        component: () => import('../views/user/UserList.vue'),
        meta: { title: '用户管理', icon: 'User' }
      },
      {
        path: 'category',
        name: 'CategoryList',
        component: () => import('../views/category/CategoryList.vue'),
        meta: { title: '宠物分类', icon: 'CollectionTag' }
      },
      {
        path: 'pet',
        name: 'PetList',
        component: () => import('../views/pet/PetList.vue'),
        meta: { title: '宠物管理', icon: 'House' }
      },
      {
        path: 'adoption',
        name: 'AdoptionList',
        component: () => import('../views/adoption/AdoptionList.vue'),
        meta: { title: '领养管理', icon: 'List' }
      },
      {
        path: 'banner',
        name: 'BannerList',
        component: () => import('../views/banner/BannerList.vue'),
        meta: { title: '轮播图管理', icon: 'PictureFilled' }
      },
      {
        path: 'news',
        name: 'NewsList',
        component: () => import('../views/news/NewsList.vue'),
        meta: { title: '资讯管理', icon: 'Document' }
      },
      {
        path: 'comment',
        name: 'CommentList',
        component: () => import('../views/comment/CommentList.vue'),
        meta: { title: '评论管理', icon: 'ChatDotRound' }
      },
      {
        path: 'feedback',
        name: 'FeedbackList',
        component: () => import('../views/feedback/FeedbackList.vue'),
        meta: { title: '反馈管理', icon: 'Message' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫 - 权限验证
router.beforeEach((to, from, next) => {
  document.title = to.meta.title ? `${to.meta.title} - 宠物领养管理系统` : '宠物领养管理系统'

  const token = localStorage.getItem('token')
  if (to.meta.noAuth) {
    // 已登录用户访问登录页，跳转到首页
    if (token && to.path === '/login') {
      next('/dashboard')
    } else {
      next()
    }
  } else {
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
