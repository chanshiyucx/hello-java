import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/seller/user/login',
    method: 'POST',
    data
  })
}
