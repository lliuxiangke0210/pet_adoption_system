<template>
  <div class="page-container">
    <div class="page-header"><h2>轮播图管理</h2></div>

    <div class="search-bar">
      <el-button type="primary" @click="openAddDialog">
        <el-icon><Plus /></el-icon> 添加轮播图
      </el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="70" />
        <el-table-column label="图片" width="200">
          <template #default="{ row }">
            <el-image :src="row.image" style="width: 160px; height: 70px; border-radius: 8px" fit="cover" preview-teleported />
          </template>
        </el-table-column>
        <el-table-column prop="title" label="标题" min-width="200" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
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
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑轮播图' : '添加轮播图'" width="500px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="图片" prop="image">
          <ImageUpload v-model="form.image" />
        </el-form-item>
        <el-form-item label="链接" prop="link">
          <el-input v-model="form.link" placeholder="可选的跳转链接" />
        </el-form-item>
        <el-form-item label="排序" prop="sortOrder">
          <el-input-number v-model="form.sortOrder" :min="0" :max="999" />
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
import { bannerApi } from '../../api/banner'
import ImageUpload from '../../components/ImageUpload.vue'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({ id: null, title: '', image: '', link: '', sortOrder: 0, status: 1 })
const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  image: [{ required: true, message: '请上传图片', trigger: 'change' }]
}

async function fetchData() {
  loading.value = true
  try {
    const res = await bannerApi.list()
    tableData.value = res.data
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

function openAddDialog() {
  isEdit.value = false
  Object.assign(form, { id: null, title: '', image: '', link: '', sortOrder: 0, status: 1 })
  dialogVisible.value = true
}

function openEditDialog(row) {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
}

async function handleSubmit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  submitting.value = true
  try {
    if (isEdit.value) {
      await bannerApi.update(form)
      ElMessage.success('更新成功')
    } else {
      await bannerApi.add(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } catch (e) { console.error(e) }
  finally { submitting.value = false }
}

function confirmDelete(row) {
  ElMessageBox.confirm(`确定删除轮播图"${row.title}"吗？`, '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await bannerApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) { console.error(e) }
}

onMounted(() => { fetchData() })
</script>
