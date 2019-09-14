<template>
  <div id="discover">
    <van-nav-bar title="发现" class="header" />
    <van-cell-group>
      <van-cell title="新の朋友" is-link to="/search" />
      <van-cell title="新の群聊" is-link to="/group" />
      <van-cell title="扫一扫" is-link @click="handleQrcode" />
    </van-cell-group>
  </div>
</template>

<script>
export default {
  name: 'discover',
  data() {
    return {}
  },
  methods: {
    handleQrcode() {
      this.$toast.fail('敬请期待')
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
