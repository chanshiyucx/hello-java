<template>
  <div id="contact">
    <van-nav-bar title="通信录" class="header" />
    <div v-if="requestList.length" class="request">
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

    <van-index-bar :index-list="friendList">
      <div v-for="(item, idx) in friendList" :key="idx">
        <van-index-anchor :index="item">{{ item }}</van-index-anchor>
        <div v-for="(item, i) in friendObj[item]" :key="i" class="user">
          <div>
            <Avatar :url="item.avatar" />
            <div class="info">
              <span>{{ item.nickname }}</span>
            </div>
          </div>
        </div>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import request from '@/utils/request'
import pinyin from '@/utils/pinyin'

export default {
  name: 'contact',
  data() {
    return {
      requestList: [],
      friendList: [],
      friendObj: {}
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created() {
    this.init()
    setTimeout(() => {
      this.init()
    }, 5000)
  },
  methods: {
    init() {
      this.getFriendRequest()
      this.getFriendList()
    },
    async getFriendList() {
      try {
        const res = await request({
          url: '/user/friendList',
          method: 'GET',
          params: {
            userId: this.userInfo.id
          }
        })

        const friendObj = {}
        const friendList = []
        res.data.forEach(o => {
          const py = pinyin.getFirstCamelChars(o.nickname).toUpperCase()
          if (!friendObj[py]) {
            friendObj[py] = []
            friendList.push(py)
          }
          friendObj[py].push(o)
        })
        friendList.sort()
        this.friendList = friendList
        this.friendObj = friendObj
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
        this.getFriendList()
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
