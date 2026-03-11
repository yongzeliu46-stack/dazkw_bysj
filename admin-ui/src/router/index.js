import { createRouter, createWebHashHistory } from 'vue-router'
import UserLayout from '../layout/UserLayout.vue'
import AdminLayout from '../layout/AdminLayout.vue'

const routes = [
  {
    path: '/',
    component: UserLayout,
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('../views/HomeView.vue')
      },
      {
        path: 'resource',
        name: 'ResourceCenter',
        component: () => import('../views/ResourceCenterView.vue')
      },
      {
        path: 'expert',
        name: 'ExpertTalk',
        component: () => import('../views/ExpertTalkView.vue')
      }
    ]
  },
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/subject',
    children: [
      {
        path: 'subject',
        component: () => import('../views/SubjectList.vue')
      },
      {
        path: 'teacher',
        component: () => import('../views/TeacherList.vue')
      },
      {
        path: 'course-video',
        component: () => import('../views/CourseVideoList.vue')
      },
      {
        path: 'study-material',
        component: () => import('../views/StudyMaterialList.vue')
      },
      {
        path: 'news-info',
        component: () => import('../views/NewsInfoList.vue')
      },
      {
        path: 'sys-user',
        component: () => import('../views/SysUserList.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
