import request from '@/utils/request'

export function getOrderList(params) {
  return request({
    url: '/seller/order/list',
    method: 'GET',
    params
  })
}
