import request from '@/utils/request'

// ============ 分类相关 ===========
export function getCategoryList() {
  return request({
    url: '/product/category',
    method: 'get'
  })
}

export function createCategory(data) {
  return request({
    url: '/product/category',
    method: 'post',
    data
  })
}

export function updateCategory(data) {
  return request({
    url: '/product/category',
    method: 'put',
    data
  })
}

// ============== 商品相关 ============
export function getProductList() {
  return request({
    url: '/product/list',
    method: 'get'
  })
}
