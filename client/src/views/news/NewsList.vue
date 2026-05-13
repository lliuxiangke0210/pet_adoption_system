<template>
  <div class="page-container">
    <div class="page-header"><h2>资讯管理</h2></div>

    <div class="search-bar">
      <el-input v-model="keyword" placeholder="搜索标题/作者" clearable style="width: 240px"
        @clear="fetchData" @keyup.enter="fetchData" />
      <el-button type="primary" @click="fetchData"><el-icon><Search /></el-icon> 搜索</el-button>
      <el-button @click="openAddDialog"><el-icon><Plus /></el-icon> 添加资讯</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column label="封面" width="120">
          <template #default="{ row }">
            <el-image v-if="row.coverImage" :src="row.coverImage" style="width: 90px; height: 60px; border-radius: 8px" fit="cover" />
            <div v-else class="no-cover">暂无</div>
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" min-width="200" show-overflow-tooltip />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="viewCount" label="浏览数" width="90" />
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="180">
          <template #default="{ row }">
            <div style="display:flex;align-items:center;gap:6px;white-space:nowrap;">
              <el-button size="small" class="action-btn btn-edit" @click="openEditDialog(row)">编辑</el-button>
              <el-button size="small" class="action-btn btn-delete" @click="confirmDelete(row)">删除</el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-model:page-num="pageNum" v-model:page-size="pageSize" :total="total" @change="fetchData" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑资讯' : '添加资讯'" width="700px" destroy-on-close>
      <NewsForm ref="newsFormRef" :news-data="editNews" @success="onFormSuccess" />
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
import { newsApi } from '../../api/news'
import Pagination from '../../components/Pagination.vue'
import NewsForm from './NewsForm.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const keyword = ref('')

const dialogVisible = ref(false)
const newsFormRef = ref(null)
const isEdit = ref(false)
const editNews = ref(null)
const submitting = ref(false)

async function fetchData() {
  loading.value = true
  try {
    const res = await newsApi.list({ keyword: keyword.value, pageNum: pageNum.value, pageSize: pageSize.value })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

function openAddDialog() {
  isEdit.value = false
  editNews.value = null
  dialogVisible.value = true
}

function openEditDialog(row) {
  isEdit.value = true
  editNews.value = { ...row }
  dialogVisible.value = true
}

function submitForm() {
  newsFormRef.value?.submit()
}

function onFormSuccess() {
  dialogVisible.value = false
  ElMessage.success(isEdit.value ? '更新成功' : '添加成功')
  fetchData()
}

function confirmDelete(row) {
  ElMessageBox.confirm(`确定删除资讯"${row.title}"吗？`, '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await newsApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) { console.error(e) }
}

onMounted(() => { fetchData() })
</script>

<style scoped lang="scss">
.no-cover {
  width: 90px; height: 60px;
  background: #f5f7fa; border-radius: 8px;
  display: flex; align-items: center; justify-content: center;
  font-size: 12px; color: #c0c4cc;
}
</style>
