<template>
  <div id="contact">
    <van-nav-bar title="通信录" class="header" />
    <div class="request">
      <div v-for="item in requestList" :key="item.id" class="user">
        <div>
          <Avatar :url="item.avatar" />
          <div class="info">
            <span>{{ item.nickname }}</span>
            <span class="tips">请求添加你为好友</span>
          </div>
        </div>
        <div class="right">
          <van-button class="default-btn" size="mini" type="default" @click="handleOper(item, 0)"
            >忽略</van-button
          >
          <van-button size="mini" type="primary" @click="handleOper(item, 1)">通过</van-button>
        </div>
      </div>
    </div>
    <div class="request">
      <div v-for="item in friendList" :key="item.id" class="user">
        <div>
          <Avatar :url="item.avatar" />
          <div class="info">
            <span>{{ item.nickname }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'contact',
  data() {
    return {
      requestList: [],
      friendList: []
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created() {
    this.getFriendRequest()
    this.getFriendList()
  },
  methods: {
    async getFriendList() {
      try {
        const res = await request({
          url: '/user/friendList',
          method: 'GET',
          params: {
            userId: this.userInfo.id
          }
        })
        this.friendList = res.data
      } catch (error) {
        console.log(error)
      }
    },
    async getFriendRequest() {
      try {
        const res = await request({
          url: '/user/friendRequest',
          method: 'GET',
          params: {
            userId: this.userInfo.id
          }
        })
        this.requestList = res.data
      } catch (error) {
        console.log(error)
      }
    },
    async handleOper(user, operType) {
      try {
        const res = await request({
          url: '/user/operFriendRequest',
          method: 'POST',
          data: {
            sendUserId: user.id,
            acceptUserId: this.userInfo.id,
            operType
          }
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.$toast.success('操作成功')
        this.getFriendRequest()
        this.friendList()
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
