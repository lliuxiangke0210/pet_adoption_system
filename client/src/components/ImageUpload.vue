<template>
  <div class="image-upload">
    <el-upload
      class="upload-cover"
      :action="uploadUrl"
      :headers="uploadHeaders"
      :show-file-list="false"
      :on-success="handleSuccess"
      :on-error="handleError"
      :before-upload="beforeUpload"
      accept="image/*"
    >
      <img v-if="modelValue" :src="modelValue" class="preview-image" />
      <div v-else class="upload-placeholder">
        <el-icon :size="28"><Plus /></el-icon>
        <span>上传图片</span>
      </div>
    </el-upload>
    <p class="upload-tip" v-if="tip">{{ tip }}</p>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { ElMessage } from 'element-plus'

const props = defineProps({
  modelValue: { type: String, default: '' },
  tip: { type: String, default: '支持 JPG/PNG 格式，建议尺寸 800x600' }
})

const emit = defineEmits(['update:modelValue'])

const uploadUrl = '/api/file/upload'
const uploadHeaders = computed(() => ({
  Authorization: `Bearer ${localStorage.getItem('token')}`
}))

function beforeUpload(file) {
  const isImage = file.type.startsWith('image/')
  if (!isImage) {
    ElMessage.error('只能上传图片文件')
    return false
  }
  const isLt5M = file.size / 1024 / 1024 < 5
  if (!isLt5M) {
    ElMessage.error('图片大小不能超过 5MB')
    return false
  }
  return true
}

function handleSuccess(response) {
  if (response.code === 200) {
    emit('update:modelValue', response.data)
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(response.message || '上传失败')
  }
}

function handleError() {
  ElMessage.error('上传失败，请重试')
}
</script>

<style scoped lang="scss">
.image-upload {
  .upload-cover {
    :deep(.el-upload) {
      border: 2px dashed #dcdfe6;
      border-radius: 8px;
      cursor: pointer;
      overflow: hidden;
      transition: border-color 0.3s;

      &:hover {
        border-color: #FF8C42;
      }
    }
  }

  .preview-image {
    width: 180px;
    height: 135px;
    object-fit: cover;
    display: block;
  }

  .upload-placeholder {
    width: 180px;
    height: 135px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 8px;
    color: #c0c4cc;
    font-size: 13px;
  }

  .upload-tip {
    font-size: 12px;
    color: #909399;
    margin-top: 6px;
  }
}
</style>
