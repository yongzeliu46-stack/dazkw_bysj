<template>
  <div class="subject-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增学科</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="名称" />
      <el-table-column prop="sortOrder" label="排序" width="100" />
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 弹窗 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="400px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="学科名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sortOrder" :min="0" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getSubjectList, addSubject, updateSubject, deleteSubject } from '../api/subject'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const form = ref({
  id: null,
  name: '',
  sortOrder: 0
})

const fetchList = async () => {
  const res = await getSubjectList()
  tableData.value = res.data
}

onMounted(() => {
  fetchList()
})

const handleAdd = () => {
  dialogTitle.value = '新增学科'
  form.value = { id: null, name: '', sortOrder: 0 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑学科'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateSubject(form.value)
    ElMessage.success('更新成功')
  } else {
    await addSubject(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该学科吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteSubject(row.id)
    ElMessage.success('删除成功')
    fetchList()
  })
}
</script>

<style scoped>
.header {
  margin-bottom: 20px;
}
</style>
