<template>
  <div id="app">
    <router-view />
    <van-tabbar v-show="showTabbar" v-model="active" active-color="#FB618D" inactive-color="#555">
      <van-tabbar-item name="home" icon="home-o" to="/">时语</van-tabbar-item>
      <van-tabbar-item name="contact" icon="friends-o" to="/contact">通讯录</van-tabbar-item>
      <van-tabbar-item name="discover" icon="search" to="/discover">发现</van-tabbar-item>
      <van-tabbar-item name="me" icon="contact" to="/me">我の</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  data() {
    return {
      tabbar: ['home', 'contact', 'discover', 'me'],
      active: 'home',
      showTabbar: false
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'roomCache'])
  },
  watch: {
    $route: {
      immediate: true,
      handler(val) {
        if (this.tabbar.includes(val.name)) {
          this.active = val.name
          this.showTabbar = true
        } else {
          this.showTabbar = false
        }
      }
    }
  }
}
</script>

<style lang="less" scope>
#app {
  display: flex;
  flex-direction: column;
  width: 100vw;
  overflow: hidden;
}
#nprogress .bar {
  background: #fb618d !important;
}
.header {
  background: linear-gradient(268deg, #ed5a4a, #ff9452), linear-gradient(#ff07fc, #ff07fc);
  > div {
    color: #fff;
    letter-spacing: 1px;
  }
  .van-icon {
    color: #fff;
    font-size: 20px;
  }
}
</style>
