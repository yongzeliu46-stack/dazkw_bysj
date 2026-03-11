<template>
  <div class="sys-user-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增用户</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="头像" width="80">
        <template #default="scope">
          <el-avatar :src="scope.row.avatarUrl" />
        </template>
      </el-table-column>
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="role" label="角色" width="120">
        <template #default="scope">
          <el-tag :type="scope.row.role === 2 ? 'danger' : ''">
            {{ scope.row.role === 2 ? '管理员' : '学生' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="注册时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="500px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="用户名">
          <el-input v-model="form.username" :disabled="!!form.id" />
        </el-form-item>
        <el-form-item label="密码" v-if="!form.id">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.role">
            <el-option label="学生" :value="1" />
            <el-option label="管理员" :value="2" />
          </el-select>
        </el-form-item>
        <el-form-item label="头像URL">
          <el-input v-model="form.avatarUrl">
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
import { getSysUserList, addSysUser, updateSysUser, deleteSysUser } from '../api/sysUser'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
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

const form = ref({
  id: null, username: '', password: '', nickname: '', role: 1, avatarUrl: ''
})

const fetchList = async () => {
  const res = await getSysUserList()
  tableData.value = res.data
}

onMounted(() => fetchList())

const handleAdd = () => {
  dialogTitle.value = '新增用户'
  form.value = { id: null, username: '', password: '', nickname: '', role: 1, avatarUrl: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑用户'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateSysUser(form.value)
    ElMessage.success('更新成功')
  } else {
    await addSysUser(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteSysUser(row.id)
    ElMessage.success('删除成功')
    fetchList()
  })
}
</script>
