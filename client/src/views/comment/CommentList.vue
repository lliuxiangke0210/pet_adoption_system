<template>
  <div class="page-container">
    <div class="page-header"><h2>评论管理</h2></div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="username" label="用户" width="120" />
        <el-table-column prop="content" label="评论内容" min-width="300" show-overflow-tooltip />
        <el-table-column prop="petId" label="宠物ID" width="80" />
        <el-table-column prop="newsId" label="资讯ID" width="80" />
        <el-table-column prop="createTime" label="评论时间" width="170" />
        <el-table-column label="操作" width="100">
          <template #default="{ row }">
            <el-button size="small" class="action-btn btn-delete" @click="confirmDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-model:page-num="pageNum" v-model:page-size="pageSize" :total="total" @change="fetchData" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { commentApi } from '../../api/comment'
import Pagination from '../../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

async function fetchData() {
  loading.value = true
  try {
    const res = await commentApi.list({ pageNum: pageNum.value, pageSize: pageSize.value })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

function confirmDelete(row) {
  ElMessageBox.confirm('确定删除此评论吗？', '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await commentApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) { console.error(e) }
}

onMounted(() => { fetchData() })
</script>
