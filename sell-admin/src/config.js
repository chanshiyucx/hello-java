const isDev = /^(192\.168|localhost)/.test(window.location.host)
if (!isDev) {
  console.log = () => {}
  console.info = () => {}
  console.warn = () => {}
}

export default {
  baseURL: 'http://127.0.0.1/',
  wsURL: 'ws://127.0.0.1:8080/webSocket'
}
