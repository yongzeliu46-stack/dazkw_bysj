<template>
  <div class="pb-10">
    <div class="mb-5">
      <el-button text type="primary" @click="goBack">返回资讯列表</el-button>
    </div>

    <div class="grid grid-cols-1 lg:grid-cols-12 gap-6">
      <article class="lg:col-span-8 bg-white border border-gray-100 rounded-xl p-6 shadow-sm">
        <template v-if="news">
          <h1 class="text-2xl font-bold text-gray-900">{{ news.title }}</h1>
          <div class="mt-2 text-sm text-gray-400">
            <span>{{ news.source || '官方发布' }}</span>
            <span class="mx-2">·</span>
            <span>{{ formatDate(news.publishTime) }}</span>
          </div>
          <img
            v-if="news.coverUrl"
            :src="news.coverUrl"
            alt="cover"
            class="w-full rounded-lg mt-5 max-h-[420px] object-cover border border-gray-100"
          />
          <div class="prose max-w-none mt-6 text-gray-700 leading-7" v-html="news.content || '暂无正文内容'" />
        </template>
        <el-empty v-else description="资讯不存在或已删除" />
      </article>

      <aside class="lg:col-span-4 bg-white border border-gray-100 rounded-xl p-5 shadow-sm h-fit">
        <h3 class="text-lg font-bold text-gray-900 mb-4">最新资讯</h3>
        <ul class="space-y-3">
          <li
            v-for="item in latestNews"
            :key="item.id"
            class="border border-gray-100 rounded-lg p-3 hover:border-blue-200 transition-colors"
          >
            <button class="text-left w-full" @click="goDetail(item.id)">
              <p class="text-sm font-medium text-gray-800 line-clamp-2 hover:text-blue-600">{{ item.title }}</p>
              <p class="text-xs text-gray-400 mt-1">{{ formatDate(item.publishTime) }}</p>
            </button>
          </li>
        </ul>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getNewsInfoById, getNewsInfoList } from '../api/newsInfo'

const route = useRoute()
const router = useRouter()

const news = ref(null)
const latestNews = ref([])

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.replace('T', ' ').split(' ')[0]
}

const loadNewsDetail = async (id) => {
  if (!id) return
  const res = await getNewsInfoById(id)
  if (res.code === 200) {
    news.value = res.data
  } else {
    news.value = null
  }
}

const loadLatest = async () => {
  const res = await getNewsInfoList()
  if (res.code === 200) {
    latestNews.value = res.data.slice(0, 8)
  }
}

const goBack = () => {
  router.push('/news')
}

const goDetail = (id) => {
  router.push(`/news/${id}`)
}

watch(
  () => route.params.id,
  (id) => {
    loadNewsDetail(id)
  }
)

onMounted(async () => {
  await Promise.all([loadNewsDetail(route.params.id), loadLatest()])
})
</script>
