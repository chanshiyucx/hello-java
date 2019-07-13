import request from '@/utils/request'

export function getList() {
  return request({
    url: '/buyer/product/category',
    method: 'get'
  })
}
