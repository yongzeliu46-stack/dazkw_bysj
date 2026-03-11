<template>
  <div class="flex flex-col gap-8 pb-10">
    <section class="bg-white rounded-xl shadow-sm border border-gray-100 p-4">
      <div class="flex flex-col gap-4">
        <div class="flex items-center gap-4 overflow-x-auto hide-scrollbar pb-2 sm:pb-0">
          <span class="text-sm font-bold text-gray-500 whitespace-nowrap">学科分类：</span>
          <div class="flex items-center gap-2">
            <el-check-tag
              :checked="!queryParams.subjectId"
              @change="() => { queryParams.subjectId = undefined; handleQuery() }"
            >全部</el-check-tag>
            <el-check-tag
              v-for="sub in subjects"
              :key="sub.id"
              :checked="queryParams.subjectId === sub.id"
              @change="() => { queryParams.subjectId = sub.id; handleQuery() }"
            >{{ sub.name }}</el-check-tag>
          </div>
        </div>

        <div class="h-px bg-gray-50 w-full"></div>

        <div class="flex items-center justify-between">
          <div class="flex items-center gap-6 text-sm">
            <span
              class="flex items-center gap-1 cursor-pointer transition-colors"
              :class="!queryParams.orderBy ? 'text-blue-600 font-bold' : 'text-gray-500 hover:text-blue-600'"
              @click="handleSort(undefined)"
            >综合排序 <el-icon><Sort /></el-icon></span>
            <span
              class="cursor-pointer transition-colors"
              :class="queryParams.orderBy === 'latest' ? 'text-blue-600 font-bold' : 'text-gray-500 hover:text-blue-600'"
              @click="handleSort('latest')"
            >最新发布</span>
            <span
              class="cursor-pointer transition-colors"
              :class="queryParams.orderBy === 'viewCount' ? 'text-blue-600 font-bold' : 'text-gray-500 hover:text-blue-600'"
              @click="handleSort('viewCount')"
            >最多播放</span>
          </div>
          <div class="text-xs text-gray-400">
            共找到 <span class="text-blue-600 font-bold">{{ videoList.length }}</span> 个视频课程
          </div>
        </div>
      </div>
    </section>

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-8">
      <div class="lg:col-span-8 xl:col-span-9">
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
          <div
            v-for="video in videoList"
            :key="video.id"
            class="group flex flex-col gap-3 cursor-pointer"
            @click="playVideo(video)"
          >
            <div class="relative w-full aspect-video rounded-xl overflow-hidden bg-gray-100">
              <div
                class="w-full h-full bg-cover bg-center group-hover:scale-105 transition-transform duration-500"
                :style="{ backgroundImage: `url(${video.coverUrl || 'https://via.placeholder.com/320x180'})` }"
              ></div>
              <div class="absolute bottom-2 right-2 px-1.5 py-0.5 bg-black/60 rounded text-xs text-white font-mono">{{ video.duration }}</div>
              <div class="absolute inset-0 bg-black/10 group-hover:bg-transparent transition-colors"></div>
            </div>
            <div>
              <h3 class="text-base font-bold text-gray-800 line-clamp-2 leading-snug group-hover:text-blue-600 transition-colors mb-2">
                {{ video.title }}
              </h3>
              <div class="flex items-center justify-between text-xs text-gray-500">
                <div class="flex items-center gap-2">
                  <el-avatar :size="24" :src="getTeacherAvatar(video.teacherId)" />
                  <span class="hover:text-blue-600">{{ getTeacherName(video.teacherId) }}</span>
                  <span class="text-gray-200">|</span>
                  <span>{{ getTeacherSchool(video.teacherId) }}</span>
                </div>
              </div>
              <div class="flex items-center gap-4 mt-2 text-xs text-gray-400">
                <span class="flex items-center gap-1"><el-icon><VideoPlay /></el-icon> {{ video.viewCount }}</span>
                <span class="flex items-center gap-1"><el-icon><Calendar /></el-icon> {{ formatDate(video.publishTime) }}</span>
              </div>
            </div>
          </div>
        </div>

        <el-empty v-if="videoList.length === 0" description="暂无视频课程" />
      </div>

      <div class="lg:col-span-4 xl:col-span-3 space-y-8">
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
          <div class="p-4 border-b border-gray-50 flex justify-between items-center">
            <h4 class="font-bold text-gray-900 flex items-center gap-2">
              <el-icon class="text-yellow-500"><Trophy /></el-icon> 名师人气榜
            </h4>
          </div>
          <div class="p-2">
            <ul class="flex flex-col">
              <li
                v-for="(teacher, index) in topTeachers"
                :key="teacher.id"
                class="flex items-center gap-3 p-3 hover:bg-gray-50 rounded-lg transition-colors"
              >
                <span
                  class="flex items-center justify-center size-5 rounded text-xs font-bold shrink-0"
                  :class="index < 3 ? 'bg-orange-100 text-orange-600' : 'text-gray-400'"
                >{{ index + 1 }}</span>
                <el-avatar :size="40" :src="teacher.avatarUrl" class="shrink-0" />
                <div class="flex-1 min-w-0">
                  <h5 class="text-sm font-bold text-gray-800 truncate">{{ teacher.name }}</h5>
                  <p class="text-xs text-gray-400 truncate">{{ teacher.school }} | {{ getSubjectName(teacher.subjectId) }}</p>
                </div>
                <div class="text-xs font-medium text-blue-600">{{ teacher.popularity }}</div>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>

    <el-dialog v-model="playerVisible" title="课程播放" width="min(900px, 95vw)">
      <div v-if="selectedVideo?.videoUrl" class="space-y-3">
        <h3 class="font-bold text-gray-800">{{ selectedVideo.title }}</h3>
        <video
          :src="selectedVideo.videoUrl"
          controls
          autoplay
          class="w-full rounded-lg bg-black"
          style="max-height: 62vh"
        ></video>
      </div>
      <el-empty v-else description="暂无可播放视频" />
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getCourseVideoList, getCourseVideoPlayLink } from '../api/courseVideo'
import { getTeacherList } from '../api/teacher'
import { getSubjectList } from '../api/subject'

const videoList = ref([])
const topTeachers = ref([])
const subjects = ref([])
const teacherMap = ref({})
const playerVisible = ref(false)
const selectedVideo = ref(null)

const queryParams = reactive({
  subjectId: undefined,
  orderBy: undefined
})

const handleSort = (order) => {
  queryParams.orderBy = order
  handleQuery()
}

const getTeacherName = (id) => teacherMap.value[id]?.name || '未知教师'
const getTeacherAvatar = (id) => teacherMap.value[id]?.avatarUrl || ''
const getTeacherSchool = (id) => teacherMap.value[id]?.school || ''
const getSubjectName = (id) => subjects.value.find((s) => s.id === id)?.name || ''

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split(' ')[0]
}

const handleQuery = async () => {
  const res = await getCourseVideoList(queryParams)
  if (res.code === 200) {
    videoList.value = res.data
  }
}

const playVideo = async (video) => {
  const res = await getCourseVideoPlayLink(video.id)
  if (res.code === 200 && res.data) {
    selectedVideo.value = { ...video, videoUrl: res.data }
    playerVisible.value = true
    handleQuery()
  }
}

onMounted(async () => {
  const [subRes, teacherRes] = await Promise.all([
    getSubjectList(),
    getTeacherList({ orderBy: 'popularity' })
  ])

  if (subRes.code === 200) subjects.value = subRes.data

  if (teacherRes.code === 200) {
    topTeachers.value = teacherRes.data.slice(0, 5)
    teacherRes.data.forEach((t) => {
      teacherMap.value[t.id] = t
    })
  }

  handleQuery()
})
</script>
