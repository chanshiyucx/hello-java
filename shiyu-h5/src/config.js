const isDev = /^(192\.168|localhost)/.test(window.location.host)
if (!isDev) {
  console.log = () => {}
  console.info = () => {}
  console.warn = () => {}
}

export default {
  baseURL: 'http://192.168.52.242:8090',
  imWSUrl: 'ws://192.168.52.242:8088/ws'
}
