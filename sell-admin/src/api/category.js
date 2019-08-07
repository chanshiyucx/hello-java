import request from '@/utils/request'

export function getCategoryList(params) {
  return request({
    url: '/seller/category/list',
    method: 'GET',
    params
  })
}

export function createCategory(data) {
  return request({
    url: '/seller/category/create',
    method: 'POST',
    data
  })
}

export function updateCategory(data) {
  return request({
    url: '/seller/category/update',
    method: 'PUT',
    data
  })
}

export function deleteCategory(params) {
  return request({
    url: '/seller/category/delete',
    method: 'DELETE',
    params
  })
}
