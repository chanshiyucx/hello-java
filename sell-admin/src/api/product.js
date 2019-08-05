import request from '@/utils/request'

export function getProductList() {
  return request({
    url: '/seller/product/list',
    method: 'GET'
  })
}

export function createProduct(data) {
  return request({
    url: '/seller/product/create',
    method: 'POST',
    data
  })
}

export function updateProduct(data) {
  return request({
    url: '/seller/product/update',
    method: 'PUT',
    data
  })
}

export function deleteProduct(params) {
  return request({
    url: '/seller/product/delete',
    method: 'DELETE',
    params
  })
}
