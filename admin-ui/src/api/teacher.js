import request from './request'

export function getTeacherList(params) {
  return request({
    url: '/teacher/list',
    method: 'get',
    params
  })
}

export function addTeacher(data) {
  return request({
    url: '/teacher/add',
    method: 'post',
    data
  })
}

export function updateTeacher(data) {
  return request({
    url: '/teacher/update',
    method: 'put',
    data
  })
}

export function deleteTeacher(id) {
  return request({
    url: `/teacher/delete/${id}`,
    method: 'delete'
  })
}

export function getTeacherById(id) {
  return request({
    url: `/teacher/${id}`,
    method: 'get'
  })
}
