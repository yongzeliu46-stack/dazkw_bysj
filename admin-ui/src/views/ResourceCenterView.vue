<template>
  <div class="flex flex-col gap-6 pb-10">
    <div class="flex flex-col gap-4">
      <nav class="flex items-center text-sm text-gray-400">
        <span class="hover:text-blue-600 cursor-pointer">首页</span>
        <span class="mx-2">/</span>
        <span class="text-gray-800 font-medium">资料中心</span>
      </nav>
      <div class="flex flex-col md:flex-row md:items-end justify-between gap-4">
        <div>
          <h2 class="text-3xl font-bold text-gray-900 tracking-tight">中考资料下载中心</h2>
          <p class="mt-2 text-gray-500 max-w-2xl">可按年份、科目、类型快速筛选并下载资料。</p>
        </div>
        <div class="relative w-full md:w-80">
          <el-input
            v-model="queryParams.keyword"
            placeholder="请输入资料标题..."
            @keyup.enter="handleQuery"
          >
            <template #prefix>
              <el-icon><Search /></el-icon>
            </template>
          </el-input>
        </div>
      </div>
    </div>

    <div class="flex flex-col lg:flex-row gap-8 items-start">
      <aside class="w-full lg:w-64 flex-shrink-0 space-y-6">
        <div class="bg-white rounded-xl p-5 border border-gray-100 shadow-sm">
          <h3 class="font-bold text-gray-900 flex items-center gap-2 mb-4">
            <span class="material-symbols-outlined text-blue-600 text-[20px]">calendar_month</span>
            年份
          </h3>
          <el-radio-group v-model="queryParams.year" class="filter-radio-group flex flex-col gap-2 w-full" @change="handleQuery">
            <el-radio :label="undefined">全部年份</el-radio>
            <el-radio :label="2026">2026</el-radio>
            <el-radio :label="2025">2025</el-radio>
            <el-radio :label="2024">2024</el-radio>
          </el-radio-group>
        </div>

        <div class="bg-white rounded-xl p-5 border border-gray-100 shadow-sm">
          <h3 class="font-bold text-gray-900 flex items-center gap-2 mb-4">
            <span class="material-symbols-outlined text-blue-600 text-[20px]">menu_book</span>
            科目
          </h3>
          <div class="flex flex-wrap gap-2">
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

        <div class="bg-white rounded-xl p-5 border border-gray-100 shadow-sm">
          <h3 class="font-bold text-gray-900 flex items-center gap-2 mb-4">
            <span class="material-symbols-outlined text-blue-600 text-[20px]">category</span>
            资料类型
          </h3>
          <el-radio-group v-model="queryParams.type" class="filter-radio-group flex flex-col gap-3 w-full" @change="handleQuery">
            <el-radio :label="undefined">全部</el-radio>
            <el-radio :label="1">真题</el-radio>
            <el-radio :label="2">模拟</el-radio>
            <el-radio :label="3">笔记</el-radio>
          </el-radio-group>
        </div>
      </aside>

      <div class="flex-1 flex flex-col gap-4 w-full">
        <div class="flex flex-wrap items-center justify-between gap-4 pb-2 border-b border-gray-50">
          <div class="text-sm text-gray-500">
            共找到 <span class="font-bold text-blue-600">{{ materialList.length }}</span> 份资料
          </div>
        </div>

        <div class="flex flex-col gap-4">
          <div
            v-for="item in materialList"
            :key="item.id"
            class="group bg-white rounded-xl border border-gray-100 p-5 hover:shadow-md hover:border-blue-200 transition-all duration-200 flex flex-col sm:flex-row gap-4 sm:items-center"
          >
            <div class="flex-shrink-0 w-12 h-12 bg-red-50 rounded-lg flex items-center justify-center text-red-500">
              <span class="material-symbols-outlined text-[32px]">picture_as_pdf</span>
            </div>
            <div class="flex-grow min-w-0">
              <div class="flex items-start justify-between gap-2">
                <h3 class="text-lg font-bold text-gray-900 truncate group-hover:text-blue-600 transition-colors">
                  {{ item.title }}
                </h3>
                <el-tag size="small" :type="getTypeTag(item.type)">{{ getTypeName(item.type) }}</el-tag>
              </div>
              <div class="flex flex-wrap items-center gap-x-4 gap-y-2 mt-2 text-xs text-gray-400 font-medium">
                <div class="flex items-center gap-1">
                  <el-icon><Calendar /></el-icon>
                  {{ formatDate(item.createdAt) }}
                </div>
                <div class="flex items-center gap-1">
                  <el-icon><Download /></el-icon>
                  {{ item.downloadCount }} 次下载
                </div>
                <div class="flex items-center gap-1">
                  <el-icon><Files /></el-icon>
                  {{ item.fileSize }}
                </div>
              </div>
            </div>
            <div class="flex-shrink-0">
              <el-button type="primary" @click="download(item.id)">
                <el-icon class="mr-1"><Download /></el-icon> 下载
              </el-button>
            </div>
          </div>
        </div>

        <div v-if="materialList.length === 0" class="py-20 text-center text-gray-400">
          <el-empty description="暂无匹配资料" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { getStudyMaterialList, getStudyMaterialDownloadLink } from '../api/studyMaterial'
import { getSubjectList } from '../api/subject'

const materialList = ref([])
const subjects = ref([])
const queryParams = reactive({
  year: undefined,
  subjectId: undefined,
  type: undefined,
  keyword: ''
})

const getTypeName = (type) => {
  const names = { 1: '真题', 2: '模拟', 3: '笔记' }
  return names[type] || '其他'
}

const getTypeTag = (type) => {
  const tags = { 1: 'primary', 2: 'success', 3: 'warning' }
  return tags[type] || 'info'
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return dateStr.split(' ')[0]
}

const download = async (id) => {
  if (!id) return
  const res = await getStudyMaterialDownloadLink(id)
  if (res.code === 200 && res.data) {
    window.open(res.data)
    handleQuery()
  }
}

const handleQuery = async () => {
  const res = await getStudyMaterialList(queryParams)
  if (res.code === 200) {
    materialList.value = res.data
  }
}

onMounted(async () => {
  const subRes = await getSubjectList()
  if (subRes.code === 200) {
    subjects.value = subRes.data
  }
  handleQuery()
})
</script>

<style scoped>
:deep(.filter-radio-group .el-radio) {
  margin-right: 0;
  width: 100%;
  justify-content: flex-start;
}

:deep(.filter-radio-group .el-radio__label) {
  padding-left: 8px;
}
</style>
