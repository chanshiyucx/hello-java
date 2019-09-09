<template>
  <div id="app">
    <van-nav-bar v-show="!hideNavbar" title="时语" class="header" />
    <router-view />
    <van-tabbar v-show="!hideTabbar" v-model="active" active-color="#FB618D" inactive-color="#555">
      <van-tabbar-item icon="home-o" to="/home">时语</van-tabbar-item>
      <van-tabbar-item icon="friends-o" to="/contact">通讯录</van-tabbar-item>
      <van-tabbar-item icon="search" to="/discover">发现</van-tabbar-item>
      <van-tabbar-item icon="contact" to="/me">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      active: 'home',
      hideNavbar: false,
      hideTabbar: false
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  watch: {
    $route(val) {
      console.log('val-->', val)
      const { hideNavbar = false, hideTabbar = false } = val.meta || {}
      this.hideNavbar = hideNavbar
      this.hideTabbar = hideTabbar
    }
  }
}
</script>

<style lang="less" scope>
.header {
  background: linear-gradient(268deg, #ed5a4a, #ff9452), linear-gradient(#ff07fc, #ff07fc);
  > div {
    color: #fff;
    letter-spacing: 2px;
  }
}
#nprogress .bar {
  background: #fb618d !important;
}
</style>
