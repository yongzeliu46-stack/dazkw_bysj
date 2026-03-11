<template>
  <header class="bg-white border-b border-gray-100 sticky top-0 z-50">
    <div class="max-w-[1280px] mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between h-20 gap-8">
        <div class="flex items-center gap-3 min-w-fit cursor-pointer" @click="goTo('/')">
          <div class="size-10 bg-blue-600/10 rounded-lg flex items-center justify-center text-blue-600">
            <span class="material-symbols-outlined text-3xl">school</span>
          </div>
          <h1 class="text-xl font-bold tracking-tight text-gray-900 hidden md:block">大庆市中考学习网</h1>
        </div>

        <nav class="hidden lg:flex items-center gap-6 xl:gap-8">
          <router-link to="/" class="nav-link" active-class="active">首页</router-link>
          <router-link to="/expert" class="nav-link" active-class="active">名师点睛</router-link>
          <router-link to="/news" class="nav-link" active-class="active">资讯中心</router-link>
          <router-link to="/resource" class="nav-link" active-class="active">资料中心</router-link>
        </nav>

        <div class="flex items-center gap-3">
          <el-button type="primary" @click="goTo('/admin')">进入后台</el-button>
          <button class="lg:hidden p-2 rounded-md text-gray-600 hover:bg-gray-100" @click="mobileMenuVisible = true">
            <span class="material-symbols-outlined">menu</span>
          </button>
        </div>
      </div>
    </div>
  </header>

  <el-drawer
    v-model="mobileMenuVisible"
    title="导航菜单"
    direction="rtl"
    size="72%"
  >
    <div class="flex flex-col gap-3">
      <el-button text @click="goTo('/')">首页</el-button>
      <el-button text @click="goTo('/expert')">名师点睛</el-button>
      <el-button text @click="goTo('/news')">资讯中心</el-button>
      <el-button text @click="goTo('/resource')">资料中心</el-button>
      <el-divider />
      <el-button type="primary" @click="goTo('/admin')">进入后台</el-button>
    </div>
  </el-drawer>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const mobileMenuVisible = ref(false)

const goTo = (path) => {
  mobileMenuVisible.value = false
  router.push(path)
}
</script>

<style scoped>
.nav-link {
  @apply text-gray-600 hover:text-blue-600 font-medium text-sm transition-colors;
}
.nav-link.active {
  @apply text-blue-600 font-bold;
}
</style>
