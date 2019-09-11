<template>
  <div id="search">
    <van-nav-bar title="新の朋友" class="header" left-arrow @click-left="onClickLeft" />
    <van-field v-model="username" placeholder="搜索用户" left-icon="search" center clearable>
      <van-button :disabled="isDisabled" slot="button" size="mini" type="primary" @click="handleSearch"
        >搜索</van-button
      >
    </van-field>

    <div v-if="user" class="search-user user">
      <div>
        <Avatar :url="user.avatar" />
        <span>{{ user.nickname }}</span>
      </div>
      <van-button slot="button" size="mini" type="primary" @click="handleRequest(user)">添加</van-button>
    </div>

    <div class="group">
      <p>
        <van-icon name="location-o" />
        <span>好友广场</span>
      </p>
      <div v-for="item in list" :key="item.id" class="user">
        <div>
          <Avatar :url="item.avatar" />
          <span>{{ item.nickname }}</span>
        </div>
        <van-button size="mini" type="primary" @click="handleRequest(item)">添加</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'search',
  data() {
    return {
      username: '',
      user: '',
      list: []
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    isDisabled() {
      return this.username.trim() === ''
    }
  },
  created() {
    this.getRecommend()
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1)
    },
    async getRecommend() {
      try {
        const res = await request({
          url: '/user/recommend',
          method: 'GET'
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.list = res.data.filter(o => o.id !== this.userInfo.id)
      } catch (error) {
        console.log(error)
      }
    },
    async handleSearch() {
      const validate = /^[a-zA-Z0-9\u4e00-\u9fa5]{4,12}$/
      if (!validate.test(this.username)) {
        return this.$toast.fail('用户名必须为4-12位中文字母或数字')
      }
      try {
        const req = {
          myUserId: this.userInfo.id,
          friendUserName: this.username
        }
        const res = await request({
          url: '/user/search',
          method: 'POST',
          data: req
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.user = res.data
      } catch (error) {
        console.log(error)
      }
    },
    async handleRequest(user) {
      try {
        const req = {
          myUserId: this.userInfo.id,
          friendUserName: user.username
        }
        const res = await request({
          url: '/user/sendFriendRequest',
          method: 'POST',
          data: req
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.$toast.success('好友验证已发送')
      } catch (error) {
        console.log(error)
      }
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
