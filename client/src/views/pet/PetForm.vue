<template>
  <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="名称" prop="name">
      <el-input v-model="form.name" placeholder="请输入宠物名称" />
    </el-form-item>
    <el-form-item label="分类" prop="categoryId">
      <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
        <el-option v-for="c in categories" :key="c.id" :label="c.name" :value="c.id" />
      </el-select>
    </el-form-item>
    <el-form-item label="品种" prop="breed">
      <el-input v-model="form.breed" placeholder="请输入品种" />
    </el-form-item>
    <el-row :gutter="16">
      <el-col :span="12">
        <el-form-item label="年龄(月)" prop="age">
          <el-input-number v-model="form.age" :min="0" :max="240" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="公">公</el-radio>
            <el-radio label="母">母</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row :gutter="16">
      <el-col :span="12">
        <el-form-item label="毛色" prop="color">
          <el-input v-model="form.color" placeholder="毛色" />
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item label="体型" prop="size">
          <el-select v-model="form.size" style="width: 100%">
            <el-option label="小型" value="小型" />
            <el-option label="中型" value="中型" />
            <el-option label="大型" value="大型" />
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-form-item label="状态" prop="status">
      <el-radio-group v-model="form.status">
        <el-radio label="available">可领养</el-radio>
        <el-radio label="pending">待审核</el-radio>
        <el-radio label="adopted">已领养</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="描述" prop="description">
      <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入宠物描述" />
    </el-form-item>
    <el-form-item label="图片">
      <ImageUpload v-model="form.image" />
    </el-form-item>
  </el-form>
</template>

<script setup>
import { ref, reactive, onMounted, defineExpose } from 'vue'
import { ElMessage } from 'element-plus'
import { petApi } from '../../api/pet'
import { categoryApi } from '../../api/category'
import ImageUpload from '../../components/ImageUpload.vue'

const props = defineProps({ petData: { type: Object, default: null } })
const emit = defineEmits(['success'])

const formRef = ref(null)
const categories = ref([])
const form = reactive({
  id: null, name: '', categoryId: null, breed: '', age: 6,
  gender: '公', color: '', size: '中型', description: '', image: '', status: 'available'
})

const rules = {
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  categoryId: [{ required: true, message: '请选择分类', trigger: 'change' }]
}

async function fetchCategories() {
  try {
    const res = await categoryApi.list()
    categories.value = res.data
  } catch (e) { console.error(e) }
}

// 暴露 submit 方法给父组件调用
async function submit() {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return
  try {
    if (form.id) {
      await petApi.update(form)
    } else {
      await petApi.add(form)
    }
    emit('success')
  } catch (e) {
    ElMessage.error('操作失败')
    console.error(e)
  }
}

defineExpose({ submit })

onMounted(() => {
  fetchCategories()
  if (props.petData) {
    Object.assign(form, props.petData)
  }
})
</script>
