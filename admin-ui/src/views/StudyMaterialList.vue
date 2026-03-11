<template>
  <div class="study-material-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增资料</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column label="所属学科" width="120">
        <template #default="scope">
          {{ getSubjectName(scope.row.subjectId) }}
        </template>
      </el-table-column>
      <el-table-column prop="year" label="年份" width="100" />
      <el-table-column prop="type" label="类型" width="120">
        <template #default="scope">
          <el-tag>{{ typeMap[scope.row.type] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="downloadCount" label="下载量" width="100" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="资料标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="文件URL">
          <el-input v-model="form.fileUrl">
            <template #append>
              <el-upload
                action="http://localhost:8080/file/upload"
                :show-file-list="false"
                :on-success="handleUploadSuccess"
              >
                <el-button type="primary">上传</el-button>
              </el-upload>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="文件大小">
          <el-input v-model="form.fileSize" placeholder="如 2.5MB" />
        </el-form-item>
        <el-form-item label="所属学科">
          <el-select v-model="form.subjectId" placeholder="请选择学科" style="width: 100%">
            <el-option
              v-for="item in subjectList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="年份">
          <el-input-number v-model="form.year" :min="2000" />
        </el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option label="真题" :value="1" />
            <el-option label="模拟" :value="2" />
            <el-option label="笔记" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="下载量">
          <el-input-number v-model="form.downloadCount" :min="0" />
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
import { getStudyMaterialList, addStudyMaterial, updateStudyMaterial, deleteStudyMaterial } from '../api/studyMaterial'
import { getSubjectList } from '../api/subject'
import { ElMessage, ElMessageBox } from 'element-plus'

const typeMap = { 1: '真题', 2: '模拟', 3: '笔记' }
const tableData = ref([])
const subjectList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    form.value.fileUrl = res.data
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(res.msg)
  }
}

const getSubjectName = (id) => {
  const subject = subjectList.value.find(s => s.id === id)
  return subject ? subject.name : '未知学科'
}

const form = ref({
  id: null, title: '', fileUrl: '', fileSize: '', subjectId: null, year: 2026, type: 1, downloadCount: 0
})

const fetchList = async () => {
  const [materialRes, subjectRes] = await Promise.all([
    getStudyMaterialList(),
    getSubjectList()
  ])
  tableData.value = materialRes.data
  subjectList.value = subjectRes.data
}

onMounted(() => fetchList())

const handleAdd = () => {
  dialogTitle.value = '新增资料'
  form.value = { id: null, title: '', fileUrl: '', fileSize: '', subjectId: null, year: 2026, type: 1, downloadCount: 0 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑资料'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateStudyMaterial(form.value)
    ElMessage.success('更新成功')
  } else {
    await addStudyMaterial(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteStudyMaterial(row.id)
    ElMessage.success('删除成功')
    fetchList()
  })
}
</script>
