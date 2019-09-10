<template>
  <div id="qrcode">
    <van-nav-bar title="我の二维码" class="header" left-arrow @click-left="onClickLeft" />
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
import VueQrcode from '@chenfengyuan/vue-qrcode'

export default {
  name: 'qrcode',
  components: { VueQrcode },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1)
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
