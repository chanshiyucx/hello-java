<template>
  <div id="qrcode">
    <van-nav-bar title="我の二维码" class="header" left-arrow @click-left="onClickLeft" />
    <!-- <qrcode-stream @decode="onDecode" @init="onInit" /> -->
    <div class="main">
      <div class="header">
        <img :src="userInfo.avatar" alt="头像" />
        <div>
          <p>{{ userInfo.nickname }}</p>
          <p class="username">{{ userInfo.username }}</p>
        </div>
      </div>
      <VueQrcode :value="userInfo.username" :options="{ width: 260, margin: 0 }" />
      <p class="footer">扫一扫上面的二维码图案，加我時語</p>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { QrcodeStream } from 'vue-qrcode-reader'
import VueQrcode from '@chenfengyuan/vue-qrcode'

export default {
  name: 'qrcode',
  components: { QrcodeStream, VueQrcode },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1)
    },
    async onInit(promise) {
      try {
        await promise
      } catch (error) {
        let errorMsg
        if (error.name === 'NotAllowedError') {
          errorMsg = 'ERROR: you need to grant camera access permisson'
        } else if (error.name === 'NotFoundError') {
          errorMsg = 'ERROR: no camera on this device'
        } else if (error.name === 'NotSupportedError') {
          errorMsg = 'ERROR: secure context required (HTTPS, localhost)'
        } else if (error.name === 'NotReadableError') {
          errorMsg = 'ERROR: is the camera already in use?'
        } else if (error.name === 'OverconstrainedError') {
          errorMsg = 'ERROR: installed cameras are not suitable'
        } else if (error.name === 'StreamApiNotSupportedError') {
          errorMsg = 'ERROR: Stream API is not supported in this browser'
        }
        this.$toast.fail(errorMsg)
      }
    },
    onDecode(result) {
      this.result = result
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
