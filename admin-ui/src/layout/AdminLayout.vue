<template>
  <el-container class="layout-container">
    <el-aside width="200px">
      <el-menu
        :default-active="$route.path"
        class="el-menu-vertical"
        background-color="#304156"
        text-color="#fff"
        router
      >
        <el-menu-item index="/">
          <el-icon><HomeFilled /></el-icon>
          <span>返回首页</span>
        </el-menu-item>
        <el-divider />
        <el-menu-item index="/admin/subject">
          <el-icon><Reading /></el-icon>
          <span>学科管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/teacher">
          <el-icon><User /></el-icon>
          <span>教师管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/course-video">
          <el-icon><VideoCamera /></el-icon>
          <span>视频课程</span>
        </el-menu-item>
        <el-menu-item index="/admin/study-material">
          <el-icon><Document /></el-icon>
          <span>学习资料</span>
        </el-menu-item>
        <el-menu-item index="/admin/news-info">
          <el-icon><Bell /></el-icon>
          <span>新闻资讯</span>
        </el-menu-item>
        <el-menu-item index="/admin/sys-user">
          <el-icon><Avatar /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header>
        <div class="header-content">
          大庆市中考网 - 管理后台
        </div>
      </el-header>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout-container {
  height: 100vh;
}
.el-header {
  background-color: #fff;
  border-bottom: 1px solid #dcdfe6;
  line-height: 60px;
  padding: 0 20px;
  font-weight: bold;
  font-size: 18px;
}
.el-menu-vertical {
  height: 100%;
}
.el-divider {
  margin: 0;
  opacity: 0.2;
}
</style>
