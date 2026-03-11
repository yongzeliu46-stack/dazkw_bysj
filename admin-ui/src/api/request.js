import axios from 'axios'
import { ElMessage } from 'element-plus'

const service = axios.create({
  baseURL: 'http://localhost:8080', // 直接请求后端，或通过vite代理
  timeout: 5000
})

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.msg || 'Error')
      return Promise.reject(new Error(res.msg || 'Error'))
    } else {
      return res
    }
  },
  error => {
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

export default service
