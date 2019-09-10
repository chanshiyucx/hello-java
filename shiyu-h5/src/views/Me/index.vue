<template>
  <div id="me">
    <van-nav-bar title="我的" class="header" @click-right="onClickRight">
      <van-icon name="scan" slot="right" />
    </van-nav-bar>
    <van-cell-group>
      <van-cell is-link title="头像" class="avatar-cell" to="/avatar">
        <img class="avatar" slot="default" :src="userInfo.avatar" alt="头像" />
      </van-cell>
      <van-cell is-link title="昵称" :value="userInfo.nickname" to="/nickname" />
      <van-cell title="账号" :value="userInfo.username" />
      <van-cell is-link title="二维码名片" to="/qrcode">
        <van-icon slot="default" name="qr" class="icon" size="20" />
      </van-cell>
    </van-cell-group>
    <div class="logout" @click="handleLogout">退出登录</div>
    <!-- <QrcodeStream @decode="onDecode" @init="onInit" /> -->
    <qrcode-stream @decode="onDecode" @init="onInit" />
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import { QrcodeStream } from 'vue-qrcode-reader'

export default {
  name: 'me',
  components: { QrcodeStream },
  data() {
    return {
      visible: {
        qrcode: false
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    ...mapMutations({
      logout: 'logout'
    }),
    handleLogout() {
      this.$dialog
        .confirm({
          title: '退出登录',
          message: '确定退出该账号？'
        })
        .then(() => {
          this.logout()
          this.$router.push('/login')
        })
        .catch(() => {
          console.log('取消')
        })
    },
    async onInit(promise) {
      try {
        await promise
      } catch (error) {
        this.$toast.fail('扫码失败')
      }
    },
    onClickRight() {
      this.visible.qrcode = true
    },
    onDecode(result) {
      console.log('result-->', result)
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
