<template>
  <div class="page-container">
    <div class="page-header"><h2>领养管理</h2></div>

    <div class="search-bar">
      <el-input v-model="keyword" placeholder="搜索申请人/宠物名" clearable style="width: 220px"
        @clear="fetchData" @keyup.enter="fetchData" />
      <el-select v-model="statusFilter" placeholder="选择状态" clearable style="width: 140px" @change="fetchData">
        <el-option label="待审批" value="pending" />
        <el-option label="已通过" value="approved" />
        <el-option label="已拒绝" value="rejected" />
      </el-select>
      <el-button type="primary" @click="fetchData"><el-icon><Search /></el-icon> 搜索</el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="username" label="申请人" width="100" />
        <el-table-column prop="realName" label="姓名" width="100" />
        <el-table-column label="宠物" width="160">
          <template #default="{ row }">
            <div class="pet-info">
              <el-image v-if="row.petImage" :src="row.petImage" style="width: 40px; height: 32px; border-radius: 6px" fit="cover" />
              <span>{{ row.petName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="petBreed" label="品种" width="100" />
        <el-table-column prop="reason" label="领养理由" min-width="200" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="statusTag(row.status)" size="small">{{ statusLabel(row.status) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="申请时间" width="160" align="center">
          <template #default="{ row }"><span class="cell-time">{{ formatTime(row.applyTime) }}</span></template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="180">
          <template #default="{ row }">
            <div style="display:flex;align-items:center;gap:6px;white-space:nowrap;">
              <template v-if="row.status === 'pending'">
                <el-button size="small" class="action-btn btn-view" @click="handleApprove(row.id, 'approved')">通过</el-button>
                <el-button size="small" class="action-btn btn-delete" @click="handleApprove(row.id, 'rejected')">拒绝</el-button>
              </template>
              <span v-else class="done-text">已处理</span>
            </div>
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
import { adoptionApi } from '../../api/adoption'
import Pagination from '../../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)
const keyword = ref('')
const statusFilter = ref(null)

function statusTag(status) {
  return { pending: 'warning', approved: 'success', rejected: 'danger' }[status] || 'info'
}
function statusLabel(status) {
  return { pending: '待审批', approved: '已通过', rejected: '已拒绝' }[status] || status
}

function formatTime(str) { if (!str) return '-'; return str.replace('T', ' ').substring(0, 19) }
async function fetchData() {
  loading.value = true
  try {
    const res = await adoptionApi.list({
      keyword: keyword.value, status: statusFilter.value,
      pageNum: pageNum.value, pageSize: pageSize.value
    })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

async function handleApprove(id, status) {
  const label = status === 'approved' ? '通过' : '拒绝'
  try {
    await ElMessageBox.confirm(`确定${label}此领养申请吗？`, '审批确认', { type: 'warning' })
    await adoptionApi.approve(id, status)
    ElMessage.success('审批完成')
    fetchData()
  } catch (e) {
    if (e !== 'cancel') console.error(e)
  }
}

onMounted(() => { fetchData() })
</script>

<style scoped lang="scss">
.pet-info { display: flex; align-items: center; gap: 8px; }
.done-text { color: #909399; font-size: 13px; }
</style>
