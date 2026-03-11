<template>
  <div class="course-video-list">
    <div class="header">
      <el-button type="primary" @click="handleAdd">新增视频课程</el-button>
    </div>
    <el-table :data="tableData" border style="width: 100%; margin-top: 20px">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column label="封面" width="120">
        <template #default="scope">
          <el-image :src="scope.row.coverUrl" style="width: 100px; height: 60px" fit="cover" />
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" />
      <el-table-column prop="duration" label="时长" width="100" />
      <el-table-column prop="viewCount" label="播放量" width="100" />
      <el-table-column prop="teacherId" label="教师ID" width="80" />
      <el-table-column label="所属学科" width="120">
        <template #default="scope">
          {{ getSubjectName(scope.row.subjectId) }}
        </template>
      </el-table-column>
      <el-table-column prop="publishTime" label="发布时间" width="180" />
      <el-table-column prop="isRecommend" label="推荐" width="80">
        <template #default="scope">
          <el-tag :type="scope.row.isRecommend ? 'success' : 'info'">
            {{ scope.row.isRecommend ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="180">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="dialogTitle" v-model="dialogVisible" width="600px">
      <el-form :model="form" label-width="100px">
        <el-form-item label="课程标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="封面URL">
          <el-input v-model="form.coverUrl">
            <template #append>
              <el-upload
                action="http://localhost:8080/file/upload"
                :show-file-list="false"
                :on-success="(res) => handleUploadSuccess(res, 'coverUrl')"
              >
                <el-button type="primary">上传</el-button>
              </el-upload>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="视频URL">
          <el-input v-model="form.videoUrl">
            <template #append>
              <el-upload
                action="http://localhost:8080/file/upload"
                :show-file-list="false"
                :on-success="(res) => handleUploadSuccess(res, 'videoUrl')"
              >
                <el-button type="primary">上传</el-button>
              </el-upload>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="时长">
          <el-input v-model="form.duration" placeholder="00:00" />
        </el-form-item>
        <el-form-item label="教师ID">
          <el-input-number v-model="form.teacherId" :min="1" />
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
        <el-form-item label="是否推荐">
          <el-radio-group v-model="form.isRecommend">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="发布时间">
          <el-date-picker v-model="form.publishTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss" />
        </el-form-item>
        <el-form-item label="播放量">
          <el-input-number v-model="form.viewCount" :min="0" />
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
import { getCourseVideoList, addCourseVideo, updateCourseVideo, deleteCourseVideo } from '../api/courseVideo'
import { getSubjectList } from '../api/subject'
import { ElMessage, ElMessageBox } from 'element-plus'

const tableData = ref([])
const subjectList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')

const handleUploadSuccess = (res, field) => {
  if (res.code === 200) {
    form.value[field] = res.data
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
  id: null, title: '', coverUrl: '', videoUrl: '', duration: '',
  teacherId: null, subjectId: null, isRecommend: 0, publishTime: '', viewCount: 0
})

const fetchList = async () => {
  const [videoRes, subjectRes] = await Promise.all([
    getCourseVideoList(),
    getSubjectList()
  ])
  tableData.value = videoRes.data
  subjectList.value = subjectRes.data
}

onMounted(() => fetchList())

const handleAdd = () => {
  dialogTitle.value = '新增视频课程'
  form.value = { id: null, title: '', coverUrl: '', videoUrl: '', duration: '', teacherId: null, subjectId: null, isRecommend: 0, publishTime: '', viewCount: 0 }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑视频课程'
  form.value = { ...row }
  dialogVisible.value = true
}

const submitForm = async () => {
  if (form.value.id) {
    await updateCourseVideo(form.value)
    ElMessage.success('更新成功')
  } else {
    await addCourseVideo(form.value)
    ElMessage.success('添加成功')
  }
  dialogVisible.value = false
  fetchList()
}

const handleDelete = (row) => {
  ElMessageBox.confirm('确定要删除吗？', '提示', { type: 'warning' }).then(async () => {
    await deleteCourseVideo(row.id)
    ElMessage.success('删除成功')
    fetchList()
  })
}
</script>
