import request from './request'

export function getStudyMaterialList(params) {
  return request({ url: '/study-material/list', method: 'get', params })
}
export function addStudyMaterial(data) {
  return request({ url: '/study-material/add', method: 'post', data })
}
export function updateStudyMaterial(data) {
  return request({ url: '/study-material/update', method: 'put', data })
}
export function deleteStudyMaterial(id) {
  return request({ url: `/study-material/delete/${id}`, method: 'delete' })
}

export function getStudyMaterialDownloadLink(id) {
  return request({ url: `/study-material/download/${id}`, method: 'get' })
}
