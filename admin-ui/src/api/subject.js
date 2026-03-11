import request from './request'

export function getSubjectList() {
  return request({
    url: '/subject/list',
    method: 'get'
  })
}

export function addSubject(data) {
  return request({
    url: '/subject/add',
    method: 'post',
    data
  })
}

export function updateSubject(data) {
  return request({
    url: '/subject/update',
    method: 'put',
    data
  })
}

export function deleteSubject(id) {
  return request({
    url: `/subject/delete/${id}`,
    method: 'delete'
  })
}
