<template>
  <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="标题" prop="title">
      <el-input v-model="form.title" placeholder="请输入资讯标题" />
    </el-form-item>
    <el-form-item label="作者" prop="author">
      <el-input v-model="form.author" placeholder="请输入作者" />
    </el-form-item>
    <el-form-item label="封面图">
      <ImageUpload v-model="form.coverImage" tip="建议尺寸 900x500" />
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input v-model="form.content" type="textarea" :rows="10" placeholder="请输入资讯内容（支持HTML标签）" />
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, reactive, onMounted, defineExpose } from 'vue'
import { ElMessage } from 'element-plus'
import { newsApi } from '../../api/news'
import ImageUpload from '../../components/ImageUpload.vue'

const props = defineProps({ newsData: { type: Object, default: null } })
const emit = defineEmits(['success'])

const formRef = ref(null)
const form = reactive({
  id: null, title: '', author: '', coverImage: '', content: ''
})
const rules = {
  title: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

async function submit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  try {
    if (form.id) {
      await newsApi.update(form)
    } else {
      await newsApi.add(form)
    }
    emit('success')
  } catch (e) {
    ElMessage.error('操作失败')
    console.error(e)
  }
}

defineExpose({ submit })

onMounted(() => {
  if (props.newsData) {
    Object.assign(form, props.newsData)
  }
})
</script>
