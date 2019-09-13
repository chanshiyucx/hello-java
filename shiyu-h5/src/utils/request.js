import axios from 'axios'
import config from '@/config'

// create an axios instance
const service = axios.create({
  baseURL: config.baseURL,
  timeout: 15000
})

// request interceptor
service.interceptors.request.use(
  config => {
    return config
  },
  error => {
    console.log('❎ error:' + error)
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => response.data,
  error => {
    console.log('❎ error:' + error.response)
    return Promise.reject(error)
  }
)

export default service
