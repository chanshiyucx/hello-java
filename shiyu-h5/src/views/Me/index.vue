<template>
  <div id="me">
    <van-nav-bar title="我的" class="header" />
    <van-cell-group>
      <van-cell is-link title="头像" class="avatar-cell" to="/avatar">
        <img class="avatar" slot="default" :src="avatar" alt="头像" />
      </van-cell>
      <van-cell is-link title="昵称" :value="userInfo.nickname" />
      <van-cell title="账号" :value="userInfo.username" />
      <van-cell is-link title="二维码名片">
        <van-icon slot="default" name="qr" class="icon" size="20" />
      </van-cell>
    </van-cell-group>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'me',
  data() {
    return {
      defaultAvatar: require('@/assets/images/avatar.png')
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    avatar() {
      return this.userInfo.avatar || this.defaultAvatar
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
