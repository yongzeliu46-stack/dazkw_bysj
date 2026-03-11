<template>
  <div class="space-y-10 pb-10">
    <section class="bg-white rounded-xl shadow-sm border border-gray-100 p-6 md:p-8 flex flex-col md:flex-row items-center justify-between gap-6 relative overflow-hidden">
      <div class="absolute top-0 right-0 w-64 h-64 bg-blue-50 rounded-full blur-3xl -translate-y-1/2 translate-x-1/2"></div>
      <div class="z-10">
        <h2 class="text-2xl md:text-3xl font-bold text-gray-900 mb-2">2026 年中考倒计时</h2>
        <p class="text-gray-500 text-sm md:text-base">距离大庆市中考还有：</p>
      </div>
      <div class="z-10">
        <div class="flex flex-col items-center gap-2">
          <div class="w-16 h-16 md:w-20 md:h-20 bg-blue-50 rounded-lg flex items-center justify-center border border-blue-100">
            <span class="text-2xl md:text-4xl font-black text-blue-600">{{ countdown.days }}</span>
          </div>
          <span class="text-xs font-medium text-gray-500 uppercase">天</span>
        </div>
      </div>
    </section>

    <section class="grid grid-cols-1 lg:grid-cols-12 gap-8">
      <div class="lg:col-span-7 xl:col-span-8 flex flex-col gap-4">
        <div class="flex items-center justify-between">
          <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
            <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
            图片新闻
          </h3>
          <el-button text type="primary" @click="goNewsCenter">更多资讯 <el-icon><ArrowRight /></el-icon></el-button>
        </div>
        <div
          v-if="imageNews.length > 0"
          class="relative w-full aspect-video rounded-xl overflow-hidden group shadow-md cursor-pointer"
          @click="goNewsDetail(imageNews[0].id)"
        >
          <div
            class="w-full h-full bg-cover bg-center transition-transform duration-700 hover:scale-105"
            :style="{ backgroundImage: `url(${imageNews[0].coverUrl || 'https://via.placeholder.com/800x450'})` }"
          ></div>
          <div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent flex flex-col justify-end p-6 md:p-8">
            <span class="inline-block px-3 py-1 bg-blue-600 text-white text-xs font-bold rounded-full w-fit mb-3">头条</span>
            <h4 class="text-white text-xl md:text-2xl font-bold leading-tight mb-2">{{ imageNews[0].title }}</h4>
            <p class="text-gray-200 text-sm md:text-base line-clamp-2">{{ plainText(imageNews[0].content) || '点击查看新闻详情' }}</p>
          </div>
        </div>
      </div>

      <div class="lg:col-span-5 xl:col-span-4 flex flex-col h-full">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
            <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
            中考要闻
          </h3>
          <el-link type="primary" :underline="false" @click="goNewsCenter">更多 <el-icon><ArrowRight /></el-icon></el-link>
        </div>
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 flex-1 p-1">
          <ul class="flex flex-col divide-y divide-gray-50">
            <li
              v-for="news in latestNews"
              :key="news.id"
              class="p-4 hover:bg-gray-50 transition-colors rounded-lg group cursor-pointer"
              @click="goNewsDetail(news.id)"
            >
              <div class="flex gap-3">
                <div class="flex-1">
                  <h5 class="text-gray-800 text-sm font-medium line-clamp-1 group-hover:text-blue-600 transition-colors">{{ news.title }}</h5>
                  <p class="text-xs text-gray-400 mt-1">{{ news.source || '官方发布' }} · {{ formatDate(news.publishTime) }}</p>
                </div>
                <span v-if="news.tags" class="shrink-0 text-xs font-bold text-red-500 bg-red-50 px-2 py-1 rounded h-fit">{{ news.tags }}</span>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </section>

    <section>
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
          <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
          科目分类导航
        </h3>
      </div>
      <div class="grid grid-cols-2 sm:grid-cols-4 lg:grid-cols-8 gap-4">
        <div
          v-for="sub in subjects"
          :key="sub.id"
          class="flex flex-col items-center justify-center gap-3 p-6 bg-white rounded-xl shadow-sm border border-gray-100 hover:border-blue-200 hover:shadow-md transition-all"
        >
          <div class="size-12 rounded-full bg-blue-50 text-blue-600 flex items-center justify-center">
            <span class="material-symbols-outlined">{{ getIcon(sub.name) }}</span>
          </div>
          <span class="font-bold text-gray-700">{{ sub.name }}</span>
          <div class="flex gap-2">
            <el-button size="small" text type="primary" @click="goResourceBySubject(sub.id)">资料</el-button>
            <el-button size="small" text type="primary" @click="goExpertBySubject(sub.id)">视频</el-button>
          </div>
        </div>
      </div>
    </section>

    <section>
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
          <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
          热门资源推荐
        </h3>
        <el-link type="primary" :underline="false" @click="router.push('/resource')">查看全部 <el-icon><ArrowRight /></el-icon></el-link>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div v-for="res in hotResources" :key="res.id" class="bg-white rounded-xl shadow-sm border border-gray-100 p-4 flex flex-col gap-4 hover:shadow-md transition-shadow">
          <div class="flex items-start justify-between">
            <div class="size-10 bg-red-50 text-red-500 rounded flex items-center justify-center">
              <span class="material-symbols-outlined">picture_as_pdf</span>
            </div>
            <span class="text-xs font-medium text-gray-400 bg-gray-50 px-2 py-1 rounded">{{ res.fileSize || '未知' }}</span>
          </div>
          <div>
            <h4 class="font-bold text-gray-800 mb-1 line-clamp-2">{{ res.title }}</h4>
            <p class="text-xs text-gray-400">下载量：{{ res.downloadCount || 0 }}</p>
          </div>
          <el-button type="primary" plain class="w-full mt-auto" @click="download(res.id)">
            <el-icon class="mr-1"><Download /></el-icon> 立即下载
          </el-button>
        </div>
      </div>
    </section>

    <section>
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
          <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
          名师推荐
        </h3>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div v-for="teacher in hotTeachers" :key="teacher.id" class="bg-white rounded-xl shadow-sm border border-gray-100 p-4 flex flex-col gap-3">
          <div class="flex items-center gap-3">
            <el-avatar :src="teacher.avatarUrl" :size="52" />
            <div>
              <h4 class="font-bold text-gray-800">{{ teacher.name }}</h4>
              <p class="text-xs text-gray-400">{{ teacher.school || '未知学校' }}</p>
            </div>
          </div>
          <p class="text-sm text-gray-500 line-clamp-2">{{ teacher.description || '暂无教师简介' }}</p>
          <el-button type="primary" text @click="showTeacherDetail(teacher.id)">查看详情</el-button>
        </div>
      </div>
    </section>

    <section class="py-12 border-t border-gray-100 mt-10">
      <div class="bg-gray-900 rounded-2xl p-8 md:p-12 text-center relative overflow-hidden">
        <div class="absolute top-0 left-0 w-full h-full opacity-10 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')]"></div>
        <div class="relative z-10 max-w-2xl mx-auto">
          <h3 class="text-2xl md:text-3xl font-bold text-white mb-4">后台管理入口</h3>
          <p class="text-gray-400 mb-8">用于维护新闻、课程、资料和用户数据。</p>
          <el-button type="primary" size="large" @click="router.push('/admin')" class="px-10 h-12 text-lg font-bold">
            进入后台管理系统
          </el-button>
        </div>
      </div>
    </section>

    <el-dialog v-model="teacherDialogVisible" title="教师详情" width="560px">
      <template v-if="currentTeacher">
        <div class="space-y-3">
          <div class="flex items-center gap-3">
            <el-avatar :src="currentTeacher.avatarUrl" :size="58" />
            <div>
              <p class="font-bold text-lg">{{ currentTeacher.name }}</p>
              <p class="text-sm text-gray-500">{{ currentTeacher.school || '未知学校' }}</p>
            </div>
          </div>
          <p class="text-sm text-gray-600">主授学科：{{ getSubjectName(currentTeacher.subjectId) }}</p>
          <p class="text-sm text-gray-600">人气：{{ currentTeacher.popularity || 0 }}</p>
          <p class="text-sm text-gray-600 leading-7">{{ currentTeacher.description || '暂无简介' }}</p>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getNewsInfoList } from '../api/newsInfo'
import { getSubjectList } from '../api/subject'
import { getStudyMaterialList, getStudyMaterialDownloadLink } from '../api/studyMaterial'
import { getTeacherById, getTeacherList } from '../api/teacher'

const router = useRouter()

const imageNews = ref([])
const latestNews = ref([])
const subjects = ref([])
const hotResources = ref([])
const hotTeachers = ref([])

const teacherDialogVisible = ref(false)
const currentTeacher = ref(null)

const countdown = reactive({
  days: 0
})

const EXAM_TIME = new Date('2026-06-25T09:00:00+08:00').getTime()
let countdownTimer = null

const getIcon = (name) => {
  const icons = {
    语文: 'book_2',
    数学: 'calculate',
    英语: 'translate',
    物理: 'rocket_launch',
    化学: 'science',
    政治: 'gavel',
    历史: 'history_edu',
    生物: 'biotech'
  }
  return icons[name] || 'school'
}

const getSubjectName = (id) => {
  return subjects.value.find((s) => s.id === id)?.name || '未知学科'
}

const updateCountdown = () => {
  const diff = EXAM_TIME - Date.now()
  countdown.days = diff <= 0 ? 0 : Math.ceil(diff / (24 * 3600 * 1000))
}

const plainText = (content) => {
  if (!content) return ''
  return content.replace(/<[^>]+>/g, '').replace(/\s+/g, ' ').trim()
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').split(' ')[0]
}

const goNewsCenter = () => router.push('/news')
const goNewsDetail = (id) => id && router.push(`/news/${id}`)
const goResourceBySubject = (subjectId) => router.push({ path: '/resource', query: { subjectId } })
const goExpertBySubject = (subjectId) => router.push({ path: '/expert', query: { subjectId } })

const download = async (id) => {
  if (!id) return
  const res = await getStudyMaterialDownloadLink(id)
  if (res.code === 200 && res.data) {
    window.open(res.data)
    fetchData()
  }
}

const showTeacherDetail = async (id) => {
  if (!id) return
  const res = await getTeacherById(id)
  if (res.code === 200 && res.data) {
    currentTeacher.value = res.data
    teacherDialogVisible.value = true
  }
}

const fetchData = async () => {
  try {
    const [newsRes, subRes, resRes, teacherRes] = await Promise.all([
      getNewsInfoList(),
      getSubjectList(),
      getStudyMaterialList({ orderBy: 'downloadCount' }),
      getTeacherList({ orderBy: 'popularity' })
    ])

    if (newsRes.code === 200) {
      imageNews.value = newsRes.data.filter((n) => n.isImageNews === 1).slice(0, 5)
      latestNews.value = newsRes.data.filter((n) => n.isImageNews === 0).slice(0, 6)
    }

    if (subRes.code === 200) {
      subjects.value = subRes.data
    }

    if (resRes.code === 200) {
      hotResources.value = resRes.data.slice(0, 4)
    }

    if (teacherRes.code === 200) {
      hotTeachers.value = teacherRes.data.slice(0, 4)
    }
  } catch (error) {
    ElMessage.error('首页数据加载失败，请稍后重试')
  }
}

onMounted(() => {
  fetchData()
  updateCountdown()
  countdownTimer = setInterval(updateCountdown, 60 * 1000)
})

onUnmounted(() => {
  if (countdownTimer) clearInterval(countdownTimer)
})
</script>
