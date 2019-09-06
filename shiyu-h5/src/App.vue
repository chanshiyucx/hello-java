<template>
  <div id="app">
    <van-nav-bar title="时语" class="header" />
    <router-view />
    <van-tabbar v-model="active" active-color="#FB618D" inactive-color="#555">
      <van-tabbar-item icon="home-o" to="/home">时语</van-tabbar-item>
      <van-tabbar-item icon="friends-o" to="/contact">通讯录</van-tabbar-item>
      <van-tabbar-item icon="search" to="/discover">发现</van-tabbar-item>
      <van-tabbar-item icon="contact" to="/me">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 'home'
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
</style>
