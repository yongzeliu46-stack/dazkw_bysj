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
        </div>
        <div v-if="imageNews.length > 0" class="relative w-full aspect-video rounded-xl overflow-hidden group shadow-md">
          <div
            class="w-full h-full bg-cover bg-center transition-transform duration-700 hover:scale-105"
            :style="{ backgroundImage: `url(${imageNews[0].coverUrl || 'https://via.placeholder.com/800x450'})` }"
          ></div>
          <div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent flex flex-col justify-end p-6 md:p-8">
            <span class="inline-block px-3 py-1 bg-blue-600 text-white text-xs font-bold rounded-full w-fit mb-3">头条</span>
            <h4 class="text-white text-xl md:text-2xl font-bold leading-tight mb-2">{{ imageNews[0].title }}</h4>
            <p class="text-gray-200 text-sm md:text-base line-clamp-2">{{ imageNews[0].content || '查看详情了解更多中考资讯。' }}</p>
          </div>
        </div>
      </div>

      <div class="lg:col-span-5 xl:col-span-4 flex flex-col h-full">
        <div class="flex items-center justify-between mb-4">
          <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
            <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
            中考要闻
          </h3>
          <el-link type="primary" :underline="false">更多 <el-icon><ArrowRight /></el-icon></el-link>
        </div>
        <div class="bg-white rounded-xl shadow-sm border border-gray-100 flex-1 p-1">
          <ul class="flex flex-col divide-y divide-gray-50">
            <li v-for="news in latestNews" :key="news.id" class="p-4 hover:bg-gray-50 transition-colors rounded-lg group cursor-pointer">
              <div class="flex gap-3">
                <div class="flex-1">
                  <h5 class="text-gray-800 text-sm font-medium line-clamp-1 group-hover:text-blue-600 transition-colors">{{ news.title }}</h5>
                  <p class="text-xs text-gray-400 mt-1">{{ news.source }} · {{ formatDate(news.publishTime) }}</p>
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
        <div v-for="sub in subjects" :key="sub.id" class="flex flex-col items-center justify-center gap-3 p-6 bg-white rounded-xl shadow-sm border border-gray-100 hover:border-blue-200 hover:shadow-md transition-all group cursor-pointer">
          <div class="size-12 rounded-full bg-blue-50 text-blue-600 flex items-center justify-center group-hover:scale-110 transition-transform">
            <span class="material-symbols-outlined">{{ getIcon(sub.name) }}</span>
          </div>
          <span class="font-bold text-gray-700">{{ sub.name }}</span>
        </div>
      </div>
    </section>

    <section>
      <div class="flex items-center justify-between mb-6">
        <h3 class="text-xl font-bold text-gray-900 flex items-center gap-2">
          <span class="w-1 h-6 bg-blue-600 rounded-full"></span>
          热门资源推荐
        </h3>
        <el-link type="primary" :underline="false">查看全部 <el-icon><ArrowRight /></el-icon></el-link>
      </div>
      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        <div v-for="res in hotResources" :key="res.id" class="bg-white rounded-xl shadow-sm border border-gray-100 p-4 flex flex-col gap-4 hover:shadow-md transition-shadow">
          <div class="flex items-start justify-between">
            <div class="size-10 bg-red-50 text-red-500 rounded flex items-center justify-center">
              <span class="material-symbols-outlined">picture_as_pdf</span>
            </div>
            <span class="text-xs font-medium text-gray-400 bg-gray-50 px-2 py-1 rounded">{{ res.fileSize }}</span>
          </div>
          <div>
            <h4 class="font-bold text-gray-800 mb-1 line-clamp-2">{{ res.title }}</h4>
            <p class="text-xs text-gray-400">下载量 {{ res.downloadCount }}</p>
          </div>
          <el-button type="primary" plain class="w-full mt-auto" @click="download(res.id)">
            <el-icon class="mr-1"><Download /></el-icon> 立即下载
          </el-button>
        </div>
      </div>
    </section>

    <section class="py-12 border-t border-gray-100 mt-10">
      <div class="bg-gray-900 rounded-2xl p-8 md:p-12 text-center relative overflow-hidden">
        <div class="absolute top-0 left-0 w-full h-full opacity-10 bg-[url('https://www.transparenttextures.com/patterns/cubes.png')]"></div>
        <div class="relative z-10 max-w-2xl mx-auto">
          <h3 class="text-2xl md:text-3xl font-bold text-white mb-4">后台管理入口</h3>
          <p class="text-gray-400 mb-8">用于维护新闻、课程、资料和用户数据。</p>
          <el-button type="primary" size="large" @click="$router.push('/admin')" class="px-10 h-12 text-lg font-bold">
            进入后台管理系统
          </el-button>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, reactive } from 'vue'
import { getNewsInfoList } from '../api/newsInfo'
import { getSubjectList } from '../api/subject'
import { getStudyMaterialList, getStudyMaterialDownloadLink } from '../api/studyMaterial'

const imageNews = ref([])
const latestNews = ref([])
const subjects = ref([])
const hotResources = ref([])

const countdown = reactive({
  days: 0
})

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

const EXAM_TIME = new Date('2026-06-25T09:00:00+08:00').getTime()
let countdownTimer = null

const updateCountdown = () => {
  const diff = EXAM_TIME - Date.now()
  if (diff <= 0) {
    countdown.days = 0
    return
  }

  countdown.days = Math.ceil(diff / (24 * 3600 * 1000))
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}-${date.getDate()}`
}

const download = async (id) => {
  if (!id) return
  const res = await getStudyMaterialDownloadLink(id)
  if (res.code === 200 && res.data) {
    window.open(res.data)
    fetchData()
  }
}

const fetchData = async () => {
  try {
    const [newsRes, subRes, resRes] = await Promise.all([
      getNewsInfoList(),
      getSubjectList(),
      getStudyMaterialList({ orderBy: 'downloadCount' })
    ])

    if (newsRes.code === 200) {
      imageNews.value = newsRes.data.filter((n) => n.isImageNews === 1).slice(0, 5)
      latestNews.value = newsRes.data.filter((n) => n.isImageNews === 0).slice(0, 5)
    }

    if (subRes.code === 200) {
      subjects.value = subRes.data
    }

    if (resRes.code === 200) {
      hotResources.value = resRes.data.slice(0, 4)
    }
  } catch (error) {
    console.error('Failed to fetch data:', error)
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
