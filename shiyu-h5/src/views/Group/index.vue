<template>
  <div id="group">
    <van-nav-bar title="添加群聊" class="header" left-arrow @click-left="onClickLeft" />
    <div class="box">
      <div class="tags">
        <van-tag class="tag" v-for="item in formData" :key="item.id" plain>{{ item.nickname }}</van-tag>
      </div>
      <van-field v-model="chatName" placeholder="新の群聊" center clearable>
        <van-button :disabled="!formData.length" slot="button" size="mini" type="primary" @click="handleChat"
          >新建群聊</van-button
        >
      </van-field>
    </div>
    <van-index-bar class="friends" :index-list="friendList">
      <div v-for="(item, idx) in friendList" :key="idx">
        <van-index-anchor :index="item">{{ item }}</van-index-anchor>
        <div v-for="(item, i) in friendObj[item]" :key="i" class="user">
          <div>
            <Avatar :url="item.avatar" />
            <div class="info">
              <span>{{ item.nickname }}</span>
            </div>
          </div>
          <van-button size="mini" :type="isAdd(item) ? 'danger' : 'primary'" @click="handleOper(item)">{{
            isAdd(item) ? '移除' : '添加'
          }}</van-button>
        </div>
      </div>
    </van-index-bar>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import request from '@/utils/request'
import pinyin from '@/utils/pinyin'

export default {
  name: 'group',
  data() {
    return {
      chatName: '',
      requestList: [],
      friendList: [],
      friendObj: {},
      formData: []
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'IMSocket'])
  },
  created() {
    this.getFriendList()
  },
  methods: {
    ...mapActions({
      getRoomInfo: 'getRoomInfo'
    }),
    onClickLeft() {
      this.$router.go(-1)
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
    isAdd(item) {
      return this.formData.find(o => o.id === item.id)
    },
    handleOper(item) {
      const inx = this.formData.findIndex(o => o.id === item.id)
      if (inx >= 0) {
        this.formData.splice(inx, 1)
      } else {
        if (this.formData.length >= 10) {
          return this.$toast.fail('群聊最多添加10人')
        }
        this.formData.push(item)
      }
    },
    async handleChat() {
      if (!this.chatName.trim()) {
        return this.$toast.fail('请输入新的群聊名称')
      }
      const addUsers = this.formData.map(o => o.id)
      const users = [this.userInfo.id, addUsers].sort().join(',')
      const data = {
        name: this.chatName,
        createUserId: this.userInfo.id,
        users
      }
      this.IMSocket.handleRequestEvent('CREATE_ROOM', data, msg => {
        if (msg.isExist) {
          this.$toast.fail('该群聊已存在！')
        }
        this.getRoomInfo(msg.id)
        this.$router.push({
          path: '/chat',
          query: {
            id: msg.id
          }
        })
      })
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
