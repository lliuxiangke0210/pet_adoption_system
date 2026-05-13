<template>
  <div class="custom-pagination" v-if="total > 0">
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="currentPageSize"
      :page-sizes="[5, 10, 20, 50]"
      :total="total"
      layout="total, sizes, prev, pager, next, jumper"
      background
      @size-change="handleSizeChange"
      @current-change="handlePageChange"
    />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  total: { type: Number, default: 0 },
  pageNum: { type: Number, default: 1 },
  pageSize: { type: Number, default: 10 }
})

const emit = defineEmits(['update:pageNum', 'update:pageSize', 'change'])

const currentPage = ref(props.pageNum)
const currentPageSize = ref(props.pageSize)

watch(() => props.pageNum, (val) => { currentPage.value = val })
watch(() => props.pageSize, (val) => { currentPageSize.value = val })

function handlePageChange(page) {
  emit('update:pageNum', page)
  emit('change')
}

function handleSizeChange(size) {
  emit('update:pageSize', size)
  currentPage.value = 1
  emit('update:pageNum', 1)
  emit('change')
}
</script>

<style scoped lang="scss">
.custom-pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}
</style>
