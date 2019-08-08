import request from '@/utils/request'

export function getOrderList(params) {
  return request({
    url: '/seller/order/list',
    method: 'GET',
    params
  })
}

export function finishOrder(data) {
  return request({
    url: '/seller/order/finish',
    method: 'PUT',
    data
  })
}

export function cancelOrder(data) {
  return request({
    url: '/seller/order/cancel',
    method: 'PUT',
    data
  })
}

export function getOrderDetail(params) {
  return request({
    url: '/seller/order/detail',
    method: 'GET',
    params
  })
}
