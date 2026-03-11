<template>
  <div class="user-layout min-h-screen bg-gray-50 flex flex-col">
    <PortalHeader />
    <main class="flex-grow w-full max-w-[1280px] mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <router-view />
    </main>
    <footer class="bg-gray-900 text-gray-400 py-12">
      <div class="max-w-[1280px] mx-auto px-4 sm:px-6 lg:px-8 text-center text-sm">
        <p>© 2026 大庆市中考学习网 版权所有</p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import PortalHeader from '../components/PortalHeader.vue'
</script>

<style scoped>
.user-layout {
  font-family: 'Lexend', sans-serif;
}
</style>
