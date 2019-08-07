import request from '@/utils/request'

export function getOrderList(params) {
  return request({
    url: '/seller/order/list',
    method: 'GET',
    params
  })
}

export function cancelOrder(data) {
  return request({
    url: '/seller/order/cancel',
    method: 'PUT',
    data
  })
}
