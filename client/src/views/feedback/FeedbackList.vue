<template>
  <div class="page-container">
    <div class="page-header"><h2>用户反馈管理</h2></div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column prop="username" label="用户" width="120" />
        <el-table-column prop="content" label="反馈内容" min-width="250" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="90">
          <template #default="{ row }">
            <el-tag :type="row.status === 'replied' ? 'success' : 'warning'" size="small">
              {{ row.status === 'replied' ? '已回复' : '待回复' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="160">
          <template #default="{ row }">
            <el-button size="small" class="action-btn btn-view" @click="openReplyDialog(row)">
              {{ row.status === 'replied' ? '查看回复' : '回复' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <Pagination v-model:page-num="pageNum" v-model:page-size="pageSize" :total="total" @change="fetchData" />
    </el-card>

    <!-- 回复对话框 -->
    <el-dialog v-model="dialogVisible" title="反馈详情" width="500px">
      <div class="feedback-detail">
        <p class="detail-label">用户反馈：</p>
        <p class="detail-content">{{ currentFeedback?.content }}</p>
        <p class="detail-label" style="margin-top: 20px;">
          {{ currentFeedback?.status === 'replied' ? '回复内容：' : '输入回复：' }}
        </p>
        <div v-if="currentFeedback?.status === 'replied'">
          <p class="detail-content reply-text">{{ currentFeedback?.reply }}</p>
        </div>
        <el-input v-else v-model="replyText" type="textarea" :rows="3" placeholder="请输入回复内容..." />
      </div>
      <template #footer>
        <el-button @click="dialogVisible = false">关闭</el-button>
        <el-button v-if="currentFeedback?.status !== 'replied'" type="primary" @click="handleReply" :loading="submitting">
          提交回复
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { feedbackApi } from '../../api/feedback'
import Pagination from '../../components/Pagination.vue'

const loading = ref(false)
const tableData = ref([])
const total = ref(0)
const pageNum = ref(1)
const pageSize = ref(10)

const dialogVisible = ref(false)
const currentFeedback = ref(null)
const replyText = ref('')
const submitting = ref(false)

async function fetchData() {
  loading.value = true
  try {
    const res = await feedbackApi.list({ pageNum: pageNum.value, pageSize: pageSize.value })
    tableData.value = res.data.records
    total.value = res.data.total
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

function openReplyDialog(row) {
  currentFeedback.value = row
  replyText.value = row.reply || ''
  dialogVisible.value = true
}

async function handleReply() {
  if (!replyText.value.trim()) {
    ElMessage.warning('请输入回复内容')
    return
  }
  submitting.value = true
  try {
    await feedbackApi.reply(currentFeedback.value.id, replyText.value)
    ElMessage.success('回复成功')
    dialogVisible.value = false
    fetchData()
  } catch (e) { console.error(e) }
  finally { submitting.value = false }
}

onMounted(() => { fetchData() })
</script>

<style scoped lang="scss">
.feedback-detail {
  .detail-label {
    font-size: 14px;
    font-weight: 600;
    color: #303133;
    margin-bottom: 8px;
  }
  .detail-content {
    font-size: 14px;
    color: #606266;
    line-height: 1.8;
    background: #f5f7fa;
    padding: 12px;
    border-radius: 8px;
  }
  .reply-text {
    color: #67C23A;
  }
}
</style>
