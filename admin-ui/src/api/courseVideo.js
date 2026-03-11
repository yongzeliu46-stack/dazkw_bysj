import request from './request'

export function getCourseVideoList(params) {
  return request({ url: '/course-video/list', method: 'get', params })
}
export function addCourseVideo(data) {
  return request({ url: '/course-video/add', method: 'post', data })
}
export function updateCourseVideo(data) {
  return request({ url: '/course-video/update', method: 'put', data })
}
export function deleteCourseVideo(id) {
  return request({ url: `/course-video/delete/${id}`, method: 'delete' })
}

export function getCourseVideoPlayLink(id) {
  return request({ url: `/course-video/play/${id}`, method: 'get' })
}
