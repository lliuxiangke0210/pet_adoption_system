<template>
  <div class="page-container">
    <div class="page-header">
      <h2>宠物分类管理</h2>
    </div>

    <div class="search-bar">
      <el-button type="primary" @click="openAddDialog">
        <el-icon><Plus /></el-icon> 添加分类
      </el-button>
    </div>

    <el-card shadow="never">
      <el-table :data="tableData" v-loading="loading" stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" width="160" />
        <el-table-column prop="description" label="分类描述" min-width="300" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
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

    <!-- 添加/编辑对话框 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑分类' : '添加分类'" width="460px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入分类描述" />
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
import { categoryApi } from '../../api/category'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const submitting = ref(false)
const formRef = ref(null)
const form = reactive({ id: null, name: '', description: '' })
const rules = { name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }] }

async function fetchData() {
  loading.value = true
  try {
    const res = await categoryApi.list()
    tableData.value = res.data
  } catch (e) { console.error(e) }
  finally { loading.value = false }
}

function openAddDialog() {
  isEdit.value = false
  Object.assign(form, { id: null, name: '', description: '' })
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
      await categoryApi.update(form)
      ElMessage.success('更新成功')
    } else {
      await categoryApi.add(form)
      ElMessage.success('添加成功')
    }
    dialogVisible.value = false
    fetchData()
  } catch (e) { console.error(e) }
  finally { submitting.value = false }
}

function confirmDelete(row) {
  ElMessageBox.confirm(`确定删除分类"${row.name}"吗？`, '删除确认', { type: 'warning' })
    .then(() => handleDelete(row.id))
    .catch(() => {})
}
async function handleDelete(id) {
  try {
    await categoryApi.delete(id)
    ElMessage.success('删除成功')
    fetchData()
  } catch (e) { console.error(e) }
}

onMounted(() => { fetchData() })
</script>
