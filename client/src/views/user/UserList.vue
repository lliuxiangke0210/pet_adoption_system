<template>
  <div class="page-container">
    <div class="page-header">
      <h2>用户管理</h2>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input v-model="keyword" placeholder="搜索用户名/姓名/手机号" clearable style="width: 260px"
        @clear="fetchData" @keyup.enter="fetchData" />
      <el-button type="primary" @click="fetchData">
        <el-icon><Search /></el-icon> 搜索
      </el-button>
      <el-button @click="openAddDialog">
        <el-icon><Plus /></el-icon> 添加用户
      </el-button>
    </div>

    <!-- 用户表格 -->
    <el-card shadow="never">
      <el-table :data="tableData" stripe v-loading="loading" style="width: 100%">
        <el-table-column prop="id" label="ID" width="70" align="center" />
        <el-table-column prop="username" label="用户名" width="120" show-overflow-tooltip>
          <template #default="{ row }"><span class="cell-text">{{ row.username || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="realName" label="真实姓名" width="120" show-overflow-tooltip>
          <template #default="{ row }"><span>{{ row.realName || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="phone" label="手机号" width="140" align="center">
          <template #default="{ row }"><span>{{ row.phone || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="email" label="邮箱" width="180" show-overflow-tooltip>
          <template #default="{ row }"><span>{{ row.email || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="role" label="角色" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="row.role === 'admin' ? 'danger' : ''" size="small">
              {{ row.role === 'admin' ? '管理员' : '普通用户' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'" size="small">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" align="center">
          <template #default="{ row }"><span class="cell-time">{{ formatTime(row.createTime) }}</span></template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150" align="center">
          <template #default="{ row }">
            <div style="display:flex;align-items:center;justify-content:center;gap:6px;white-space:nowrap;">
              <el-button size="small" class="action-btn btn-edit" @click="openEditDialog(row)">编辑</el-button>
              <el-button size="small" class="action-btn btn-delete" :disabled="row.role === 'admin'" @click="confirmDelete(row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <Pagination v-model:page-num="pageNum" v-model:page-size="pageSize" :total="total" @change="fetchData" />
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑用户' : '添加用户'" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="isEdit" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" :prop="isEdit ? '' : 'password'">
          <el-input v-model="form.password" placeholder="请输入密码" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" style="width: 100%">
            <el-option label="普通用户" value="user" />
            <el-option label="管理员" value="admin" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" active-text="启用" inactive-text="禁用" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { userApi } from '../../api/user'
import Pagination from '../../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const keyword = ref('')

const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({
  id: null, username: '', password: '', realName: '', phone: '', email: '', role: 'user', status: 1
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

async function fetchData() {
  loading.value = true
  try {
    const res = await userApi.list({ keyword: keyword.value, pageNum: pageNum.value, pageSize: pageSize.value })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) {
    console.error(e)
  } finally {
    loading.value = false
  }
}

function formatTime(str) {
  if (!str) return '-'
  return str.replace('T', ' ').substring(0, 19)
}
function openAddDialog() {
  isEdit.value = false
  Object.assign(form, { id: null, username: '', password: '', realName: '', phone: '', email: '', role: 'user', status: 1 })
  dialogVisible.value = true
}

function openEditDialog(row) {
  isEdit.value = true
  Object.assign(form, { ...row, password: '' })
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value) {
      await userApi.update(form)
      ElMessage.success('更新成功')
    } else {
      await userApi.add(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } catch (e) {
    console.error(e)
  } finally {
    submitting.value = false
  }
}

function confirmDelete(row) {
  ElMessageBox.confirm(`确定删除用户"${row.username}"吗？`, '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await userApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) {
    console.error(e)
  }
}

onMounted(() => { fetchData() })
</script>
