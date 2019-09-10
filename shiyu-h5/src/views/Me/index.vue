<template>
  <div id="me">
    <van-nav-bar title="我的" class="header" />
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
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'

export default {
  name: 'me',
  computed: {
    ...mapGetters(['userInfo'])
  },
  methods: {
    ...mapMutations({
      logout: 'logout'
    }),
    handleLogout() {
      this.logout()
      this.$router.push('/login')
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
