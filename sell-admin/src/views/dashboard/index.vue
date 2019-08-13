<template>
  <div class="dashboard">
    <img :src="emptyGif" class="emptyGif" >
  </div>
</template>

<script>
import config from '@/config'

export default {
  name: 'Dashboard',
  data() {
    return {
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3',
      wsUrl: '',
      socket: null
    }
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.socket = new WebSocket(config.wsURL)
      this.socket.onopen = () => this.onopen()
      this.socket.onclose = () => this.onclose()
      this.socket.onerror = () => this.onerror()
      this.socket.onmessage = event => this.onmessage(event)
    },
    onopen() {
      console.log('连接成功')
    },
    onclose() {
      console.log('连接关闭')
    },
    onerror() {
      console.log('连接错误')
    },
    onmessage(event) {
      console.log('接收消息', event)
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  img {
    display: block;
    margin: 30px auto;
  }
}
</style>
