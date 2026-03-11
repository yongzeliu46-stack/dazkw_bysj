import request from './request'

export function getNewsInfoList(params) {
  return request({ url: '/news-info/list', method: 'get', params })
}
export function addNewsInfo(data) {
  return request({ url: '/news-info/add', method: 'post', data })
}
export function updateNewsInfo(data) {
  return request({ url: '/news-info/update', method: 'put', data })
}
export function deleteNewsInfo(id) {
  return request({ url: `/news-info/delete/${id}`, method: 'delete' })
}
