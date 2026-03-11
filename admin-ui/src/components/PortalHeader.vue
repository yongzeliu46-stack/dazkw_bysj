<template>
  <header class="bg-white border-b border-gray-100 sticky top-0 z-50">
    <div class="max-w-[1280px] mx-auto px-4 sm:px-6 lg:px-8">
      <div class="flex items-center justify-between h-20 gap-8">
        <div class="flex items-center gap-3 min-w-fit cursor-pointer" @click="$router.push('/')">
          <div class="size-10 bg-blue-600/10 rounded-lg flex items-center justify-center text-blue-600">
            <span class="material-symbols-outlined text-3xl">school</span>
          </div>
          <h1 class="text-xl font-bold tracking-tight text-gray-900 hidden md:block">大庆市中考学习网</h1>
        </div>

        <nav class="hidden lg:flex items-center gap-6 xl:gap-8">
          <router-link to="/" class="nav-link" active-class="active">首页</router-link>
          <router-link to="/expert" class="nav-link" active-class="active">名师点睛</router-link>
          <router-link to="/resource" class="nav-link" active-class="active">资料中心</router-link>
        </nav>

        <div class="flex items-center gap-3">
          <el-button type="primary" @click="$router.push('/admin')">进入后台</el-button>
          <button class="lg:hidden p-2 rounded-md text-gray-600 hover:bg-gray-100">
            <span class="material-symbols-outlined">menu</span>
          </button>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.nav-link {
  @apply text-gray-600 hover:text-blue-600 font-medium text-sm transition-colors;
}
.nav-link.active {
  @apply text-blue-600 font-bold;
}
</style>
