<template>
  <div id="home">
    <van-nav-bar title="時語" class="header" />
    <div v-if="roomCache.length > 0">
      <div v-for="item in roomCache" class="user" :key="item.roomId" @click="handleChat(item.roomInfo.id)">
        <div class="left">
          <Avatar :url="item.roomInfo.icon" />
          <div class="info">
            <span>{{ item.roomInfo.name }}</span>
          </div>
        </div>
        <div class="right">
          <div class="msg">{{ item.lastMsg | msgFilter }}</div>
          <van-tag v-show="item.unReadCount > 0" class="tag" type="warning">{{ item.unReadCount }}</van-tag>
        </div>
      </div>
    </div>
    <div class="empty" v-else>
      <van-button size="small" plain hairline type="primary" @click="handleDiscover"
        >空空如也，去加好友吧</van-button
      >
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { TYPES } from '@/IM'

export default {
  name: 'home',
  filters: {
    msgFilter(lastMsg) {
      if (!lastMsg) return
      const val = lastMsg.msg.msg
      if (val.contentType === TYPES.TEXT) {
        return val.text
      } else {
        return '[图片]'
      }
    }
  },
  computed: {
    ...mapGetters(['roomCache'])
  },
  methods: {
    handleChat(roomId) {
      this.$router.push({
        path: '/chat',
        query: {
          id: roomId
        }
      })
    },
    handleDiscover() {
      this.$router.push({
        path: '/discover'
      })
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
