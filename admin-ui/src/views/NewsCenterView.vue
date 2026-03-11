<template>
  <div class="space-y-6 pb-10">
    <section class="bg-white rounded-xl border border-gray-100 shadow-sm p-6">
      <div class="flex flex-col gap-4">
        <div>
          <h2 class="text-2xl font-bold text-gray-900">中考资讯中心</h2>
          <p class="text-gray-500 text-sm mt-1">支持关键词与标签筛选，点击按钮查看详情。</p>
        </div>
        <div class="flex flex-col sm:flex-row gap-3">
          <el-input
            v-model="queryParams.keyword"
            placeholder="输入关键词检索新闻标题或正文"
            clearable
            @keyup.enter="handleQuery"
          />
          <el-button type="primary" @click="handleQuery">搜索</el-button>
        </div>
        <div class="flex flex-wrap gap-2">
          <el-check-tag :checked="!queryParams.tag" @change="selectTag(undefined)">全部</el-check-tag>
          <el-check-tag
            v-for="tag in tagOptions"
            :key="tag"
            :checked="queryParams.tag === tag"
            @change="selectTag(tag)"
          >
            {{ tag }}
          </el-check-tag>
        </div>
      </div>
    </section>

    <section class="grid grid-cols-1 md:grid-cols-2 gap-5">
      <article
        v-for="item in pagedList"
        :key="item.id"
        class="bg-white border border-gray-100 rounded-xl p-5 shadow-sm hover:shadow-md transition-shadow"
      >
        <h3 class="text-lg font-bold text-gray-900 line-clamp-2">{{ item.title }}</h3>
        <p class="text-xs text-gray-400 mt-2">
          {{ item.source || '官方发布' }} · {{ formatDate(item.publishTime) }}
        </p>
        <p class="text-sm text-gray-600 mt-3 line-clamp-3">{{ plainText(item.content) || '暂无内容摘要' }}</p>
        <div class="mt-4 flex items-center justify-between">
          <el-tag v-if="item.tags" size="small">{{ item.tags }}</el-tag>
          <el-button type="primary" plain @click="goDetail(item.id)">查看详情</el-button>
        </div>
      </article>
    </section>

    <el-empty v-if="newsList.length === 0" description="暂无匹配资讯" />

    <div v-if="newsList.length > pageSize" class="flex justify-center pt-2">
      <el-pagination
        v-model:current-page="currentPage"
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="newsList.length"
      />
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getNewsInfoList } from '../api/newsInfo'

const router = useRouter()
const newsList = ref([])
const tagOptions = ref([])
const currentPage = ref(1)
const pageSize = 6

const queryParams = reactive({
  keyword: '',
  tag: undefined
})

const plainText = (content) => {
  if (!content) return ''
  return content.replace(/<[^>]+>/g, '').replace(/\s+/g, ' ').trim()
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split(' ')[0]
}

const pagedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return newsList.value.slice(start, start + pageSize)
})

const extractTags = (list) => {
  const tags = new Set()
  list.forEach((item) => {
    if (!item.tags) return
    item.tags
      .split(/[,\uFF0C\s]+/)
      .map((tag) => tag.trim())
      .filter(Boolean)
      .forEach((tag) => tags.add(tag))
  })
  tagOptions.value = Array.from(tags)
}

const handleQuery = async () => {
  const res = await getNewsInfoList({
    keyword: queryParams.keyword || undefined,
    tag: queryParams.tag
  })
  if (res.code === 200) {
    newsList.value = res.data
    extractTags(res.data)
    currentPage.value = 1
  }
}

const selectTag = (tag) => {
  queryParams.tag = queryParams.tag === tag ? undefined : tag
  handleQuery()
}

const goDetail = (id) => {
  router.push(`/news/${id}`)
}

onMounted(() => {
  handleQuery()
})
</script>
