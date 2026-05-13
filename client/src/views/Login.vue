<template>
  <div class="login-page">
    <div class="login-bg-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
    </div>

    <div class="login-card">
      <div class="card-left">
        <div class="brand-area">
          <div class="brand-icon">
            <el-icon :size="48" color="#fff"><House /></el-icon>
          </div>
          <h1>宠物领养管理系统</h1>
          <p>给流浪动物一个温暖的家</p>
          <div class="feature-list">
            <div class="feature-item">
              <el-icon><Checked /></el-icon>
              <span>领养代替购买</span>
            </div>
            <div class="feature-item">
              <el-icon><Checked /></el-icon>
              <span>用爱终止流浪</span>
            </div>
            <div class="feature-item">
              <el-icon><Checked /></el-icon>
              <span>给它们一个家</span>
            </div>
          </div>
        </div>
      </div>

      <div class="card-right">
        <div class="form-wrapper">
          <h2>管理员登录</h2>
          <p class="subtitle">欢迎回来，请登录您的账号</p>

          <el-form ref="formRef" :model="form" :rules="rules" size="large" class="login-form">
            <el-form-item prop="username">
              <el-input
                v-model="form.username"
                placeholder="请输入用户名"
                :prefix-icon="User"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="form.password"
                type="password"
                placeholder="请输入密码"
                :prefix-icon="Lock"
                show-password
                @keyup.enter="handleLogin"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" class="login-btn" :loading="loading" @click="handleLogin">
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <p class="login-tip">默认管理员账号: admin / 123456</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref(null)
const loading = ref(false)

const form = reactive({
  username: 'admin',
  password: '123456'
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function handleLogin() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    await userStore.login(form.username, form.password)
    ElMessage.success('登录成功')
    router.push('/dashboard')
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #FFF5EC 0%, #FFE4D0 50%, #FFD4B8 100%);
  position: relative;
  overflow: hidden;

  .login-bg-shapes {
    position: absolute;
    inset: 0;
    .shape {
      position: absolute;
      border-radius: 50%;
      opacity: 0.1;
    }
    .shape-1 {
      width: 400px; height: 400px;
      background: #FF8C42;
      top: -100px; right: -100px;
    }
    .shape-2 {
      width: 300px; height: 300px;
      background: #FFB380;
      bottom: -50px; left: -50px;
    }
    .shape-3 {
      width: 200px; height: 200px;
      background: #E67A2E;
      top: 50%; left: 60%;
    }
  }

  .login-card {
    position: relative;
    display: flex;
    width: 880px;
    height: 520px;
    background: #fff;
    border-radius: 20px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.1);
    overflow: hidden;
  }

  .card-left {
    width: 400px;
    background: linear-gradient(135deg, #FF8C42 0%, #E67A2E 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 40px;

    .brand-area {
      text-align: center;
      color: #fff;

      .brand-icon {
        width: 80px; height: 80px;
        background: rgba(255,255,255,0.2);
        border-radius: 20px;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 24px;
      }

      h1 {
        font-size: 24px;
        margin-bottom: 8px;
      }

      p {
        opacity: 0.85;
        margin-bottom: 36px;
        font-size: 14px;
      }

      .feature-item {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-bottom: 14px;
        justify-content: center;
        font-size: 14px;
        opacity: 0.9;
      }
    }
  }

  .card-right {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;

    .form-wrapper {
      width: 340px;

      h2 {
        font-size: 24px;
        color: #303133;
        margin-bottom: 6px;
      }

      .subtitle {
        color: #909399;
        font-size: 14px;
        margin-bottom: 32px;
      }

      .login-btn {
        width: 100%;
        height: 44px;
        font-size: 16px;
        border-radius: 22px;
      }

      .login-tip {
        text-align: center;
        color: #C0C4CC;
        font-size: 12px;
        margin-top: 16px;
      }
    }
  }
}
</style>
