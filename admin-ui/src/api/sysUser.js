import request from './request'

export function getSysUserList() {
  return request({ url: '/sys-user/list', method: 'get' })
}
export function addSysUser(data) {
  return request({ url: '/sys-user/add', method: 'post', data })
}
export function updateSysUser(data) {
  return request({ url: '/sys-user/update', method: 'put', data })
}
export function deleteSysUser(id) {
  return request({ url: `/sys-user/delete/${id}`, method: 'delete' })
}
