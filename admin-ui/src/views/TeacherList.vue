<template>
  <div class="teacher-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增教师</el-button>
    </div>

    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="头像" width="100">
        <template #default="scope">
          <el-avatar :src="scope.row.avatarUrl" />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="school" label="学校" />
      <el-table-column label="所属学科" width="120">
        <template #default="scope">
          {{ getSubjectName(scope.row.subjectId) }}
        </template>
      </el-table-column>
      <el-table-column prop="popularity" label="人气" width="100" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 弹窗 -->
    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="头像URL">
          <el-input v-model="form.avatarUrl">
            <template #append>
              <el-upload
                action="http://localhost:8080/file/upload"
                :show-file-list="false"
                :on-success="handleUploadSuccess"
                name="file"
              >
                <el-button type="primary">上传</el-button>
              </el-upload>
            </template>
          </el-input>
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
        <el-form-item label="学校">
          <el-input v-model="form.school" />
        </el-form-item>
        <el-form-item label="人气值">
          <el-input-number v-model="form.popularity" :min="0" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input type="textarea" v-model="form.description" />
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
import { getTeacherList, addTeacher, updateTeacher, deleteTeacher } from '../api/teacher'
import { getSubjectList } from '../api/subject'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const subjectList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    form.value.avatarUrl = res.data
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
  id: null,
  name: '',
  avatarUrl: '',
  subjectId: null,
  school: '',
  popularity: 0,
  description: ''
})

const fetchList = async () => {
  const [teacherRes, subjectRes] = await Promise.all([
    getTeacherList(),
    getSubjectList()
  ])
  tableData.value = teacherRes.data
  subjectList.value = subjectRes.data
}

onMounted(() => {
  fetchList()
})

const handleAdd = () => {
  dialogTitle.value = '新增教师'
  form.value = { id: null, name: '', avatarUrl: '', subjectId: null, school: '', popularity: 0, description: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑教师'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateTeacher(form.value)
    ElMessage.success('更新成功')
  } else {
    await addTeacher(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除该教师吗？', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    await deleteTeacher(row.id)
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
