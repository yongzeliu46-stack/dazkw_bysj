<template>
  <div class="news-info-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增资讯</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="source" label="来源" width="150" />
      <el-table-column prop="publishTime" label="发布时间" width="180" />
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="700px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="来源">
          <el-input v-model="form.source" />
        </el-form-item>
        <el-form-item label="标签">
          <el-input v-model="form.tags" placeholder="逗号分隔" />
        </el-form-item>
        <el-form-item label="图片新闻">
          <el-radio-group v-model="form.isImageNews">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="封面URL" v-if="form.isImageNews">
          <el-input v-model="form.coverUrl">
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
        <el-form-item label="发布时间">
          <el-date-picker v-model="form.publishTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input type="textarea" :rows="10" v-model="form.content" />
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
import { getNewsInfoList, addNewsInfo, updateNewsInfo, deleteNewsInfo } from '../api/newsInfo'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')

const handleUploadSuccess = (res) => {
  if (res.code === 200) {
    form.value.coverUrl = res.data
    ElMessage.success('上传成功')
  } else {
    ElMessage.error(res.msg)
  }
}

const form = ref({
  id: null, title: '', content: '', coverUrl: '', source: '', tags: '', isImageNews: 0, publishTime: ''
})

const fetchList = async () => {
  const res = await getNewsInfoList()
  tableData.value = res.data
}

onMounted(() => fetchList())

const handleAdd = () => {
  dialogTitle.value = '新增资讯'
  form.value = { id: null, title: '', content: '', coverUrl: '', source: '', tags: '', isImageNews: 0, publishTime: '' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑资讯'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateNewsInfo(form.value)
    ElMessage.success('更新成功')
  } else {
    await addNewsInfo(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteNewsInfo(row.id)
    ElMessage.success('删除成功')
    fetchList()
  })
}
</script>
