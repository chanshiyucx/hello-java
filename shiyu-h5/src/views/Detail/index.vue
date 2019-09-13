<template>
  <div id="detail">
    <van-nav-bar title="好友详情" class="header" left-arrow @click-left="onClickLeft" />
    <div class="main">
      <div class="header">
        <Avatar :url="user.avatar" alt="头像" />
        <div>
          <p>{{ user.nickname }}</p>
          <p class="username">{{ user.username }}</p>
        </div>
      </div>
      <div class="footer">
        <van-button size="small" type="primary" @click="handleChat">发送消息</van-button>
        <van-button size="small" type="danger" @click="handleDelete">删除好友</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'detail',
  data() {
    return {
      user: ''
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created() {
    this.getDetail()
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1)
    },
    async getDetail() {
      try {
        const { id } = this.$route.query
        const res = await request({
          url: '/user/detail',
          method: 'GET',
          params: {
            userId: id
          }
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.user = res.data
      } catch (error) {
        console.log(error)
      }
    },
    handleDelete() {
      this.$dialog
        .confirm({
          title: '删除好友',
          message: '确定删除该好友？'
        })
        .then(async () => {
          const res = await request({
            url: '/user/deleteFriend',
            method: 'POST',
            data: {
              userId: this.userInfo.id,
              friendUserId: this.user.id
            }
          })
          if (res.status !== 200) {
            return this.$toast.fail(res.msg)
          }
          this.$toast.success('删除成功')
          setTimeout(() => {
            this.onClickLeft()
          }, 2000)
        })
        .catch(() => {
          console.log('取消')
        })
    },
    async handleChat() {
      try {
        const users = [this.userInfo.id, this.user.id].join(',')
        const res = await request({
          url: '/room/create',
          method: 'POST',
          data: {
            createUser: this.userInfo.id,
            users
          }
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.$router.push({
          path: '/chat',
          query: {
            roomId: res.data.id
          }
        })
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
