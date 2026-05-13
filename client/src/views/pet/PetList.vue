<template>
  <div class="page-container">
    <div class="page-header"><h2>宠物管理</h2></div>

    <div class="search-bar">
      <el-input v-model="keyword" placeholder="搜索宠物名称/品种" clearable style="width: 220px"
        @clear="fetchData" @keyup.enter="fetchData" />
      <el-select v-model="categoryFilter" placeholder="选择分类" clearable style="width: 150px" @change="fetchData">
        <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
      </el-select>
      <el-select v-model="statusFilter" placeholder="选择状态" clearable style="width: 150px" @change="fetchData">
        <el-option label="可领养" value="available" />
        <el-option label="已领养" value="adopted" />
        <el-option label="待审核" value="pending" />
      </el-select>
      <el-button type="primary" @click="fetchData"><el-icon><Search /></el-icon> 搜索</el-button>
      <el-button @click="openAdd"><el-icon><Plus /></el-icon> 添加宠物</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="图片" width="100" align="center">
          <template #default="{ row }">
            <el-image v-if="row.image" :src="row.image" style="width: 70px; height: 55px; border-radius: 8px"
              fit="cover" preview-teleported :preview-src-list="[row.image]" />
            <div v-else class="no-image">🐾</div>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="名称" width="100" show-overflow-tooltip>
          <template #default="{ row }"><span class="cell-text">{{ row.name || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="categoryName" label="分类" width="90" align="center">
          <template #default="{ row }"><span>{{ row.categoryName || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="breed" label="品种" width="110" show-overflow-tooltip>
          <template #default="{ row }"><span>{{ row.breed || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="age" label="年龄" width="80" align="center">
          <template #default="{ row }"><span>{{ row.age ? row.age + '个月' : '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="60" align="center">
          <template #default="{ row }"><span>{{ row.gender || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="color" label="毛色" width="80" align="center">
          <template #default="{ row }"><span>{{ row.color || '-' }}</span></template>
        </el-table-column>
        <el-table-column prop="size" label="体型" width="70" align="center">
          <template #default="{ row }">
            <el-tag size="small" :type="row.size === '大型' ? 'danger' : row.size === '中型' ? 'warning' : 'success'">{{ row.size || '-' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="85" align="center">
          <template #default="{ row }">
            <el-tag :type="statusTagType(row.status)" size="small">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" align="center">
          <template #default="{ row }"><span class="cell-time">{{ formatTime(row.createTime) }}</span></template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150" align="center">
          <template #default="{ row }">
            <div style="display:flex;align-items:center;justify-content:center;gap:6px;white-space:nowrap;">
              <el-button size="small" class="action-btn btn-edit" @click="openEdit(row)">编辑</el-button>
              <el-button size="small" class="action-btn btn-delete" @click="confirmDelete(row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-model:page-num="pageNum" v-model:page-size="pageSize" :total="total" @change="fetchData" />
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑宠物' : '添加宠物'" width="600px" destroy-on-close>
      <PetForm ref="petFormRef" :pet-data="editPet" @success="onFormSuccess" />
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitting">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { petApi } from '../../api/pet'
import { categoryApi } from '../../api/category'
import Pagination from '../../components/Pagination.vue'
import PetForm from './PetForm.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const keyword = ref('')
const categoryFilter = ref(null)
const statusFilter = ref(null)
const categories = ref([])

const dialogVisible = ref(false)
const petFormRef = ref(null)
const editPet = ref(null)
const isEdit = ref(false)
const submitting = ref(false)

function openAdd() {
  isEdit.value = false
  editPet.value = null
  dialogVisible.value = true
}
function openEdit(row) {
  isEdit.value = true
  editPet.value = { ...row }
  dialogVisible.value = true
}
function statusTagType(status) {
  return { available: 'success', adopted: 'info', pending: 'warning' }[status] || 'info'
}
function statusLabel(status) {
  return { available: '可领养', adopted: '已领养', pending: '待审核' }[status] || status
}

async function fetchData() {
  loading.value = true
  try {
    const res = await petApi.list({
      keyword: keyword.value, categoryId: categoryFilter.value,
      status: statusFilter.value, pageNum: pageNum.value, pageSize: pageSize.value
    })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

async function fetchCategories() {
  try {
    const res = await categoryApi.list()
    categories.value = res.data
  } catch (e) { console.error(e) }
}

function formatTime(str) {
  if (!str) return '-'
  return str.replace('T', ' ').substring(0, 19)
}
function submitForm() {
  petFormRef.value?.submit()
}

function onFormSuccess() {
  dialogVisible.value = false
  ElMessage.success('更新成功')
  fetchData()
}

function confirmDelete(row) {
  ElMessageBox.confirm(`确定删除宠物"${row.name}"吗？`, '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await petApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) { console.error(e) }
}

onMounted(() => {
  fetchData()
  fetchCategories()
})
</script>

<style scoped lang="scss">
.no-image {
  width: 70px; height: 55px;
  background: #f5f7fa;
  border-radius: 8px;
  display: flex; align-items: center; justify-content: center;
  font-size: 24px; color: #c0c4cc;
}
.cell-text {
  font-weight: 500;
  color: #303133;
}
.cell-time {
  font-size: 12px;
  color: #909399;
}
</style>
