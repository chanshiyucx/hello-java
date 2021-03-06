<template>
  <div id="chat">
    <van-nav-bar :title="`聊天${IMStatus ? '' : '-连接中'}`" class="header" left-arrow @click-left="onClickLeft" />
    <div ref="chat" :class="['main', visible.emoji && 'toggle']">
      <PullRefresh
        :disabled="historyLoading === 2"
        :value="historyLoading === 0"
        pulling-text="下拉加载历史纪录"
        loosing-text="释放加载历史纪录"
        @refresh="onRefresh"
      >
        <!-- 历史消息获取 -->
        <div class="history-msg">
          <div class="divider" v-if="historyLoading === 2">暂无历史消息</div>
          <div class="divider" v-else>下拉加载历史消息</div>
        </div>

        <ul ref="chatMsgList" class="chat-list">
          <!-- 历史消息 -->
          <li v-for="(item, i) in historyList" :key="i" class="chat-msg">
            <div class="times">{{ formatTime(historyList[i > 0 ? i - 1 : 0].date, item.date) }}</div>
            <!-- 自己 -->
            <div v-if="item.sendUserId === userInfo.id" class="msg me">
              <Avatar class="avatar" :url="userInfo.avatar" alt="头像" />
              <div class="me-msg">
                <div v-if="item.msg.contentType === TYPES.TEXT" v-html="toHtml(item.msg.text)" />
                <div v-else-if="item.msg.contentType === TYPES.PICTURE">
                  <LazyImg
                    :src="item.msg.smallImgUrl"
                    @click.native="previewImg(item.msg.smallImgUrl)"
                    @load="setScrollHeight('load')"
                  />
                </div>
              </div>
            </div>
            <!-- 好友 -->
            <div v-else class="msg user">
              <Avatar class="avatar" :url="item.sendUserAvatar" alt="头像" />
              <p class="nickname">{{ item.sendUserNickname }}</p>
              <div class="user-msg">
                <div v-if="item.msg.contentType === TYPES.TEXT" v-html="toHtml(item.msg.text)" />
                <div v-else-if="item.msg.contentType === TYPES.PICTURE">
                  <LazyImg
                    :src="item.msg.smallImgUrl"
                    @click.native="previewImg(item.msg.smallImgUrl)"
                    @load="setScrollHeight('load')"
                  />
                </div>
              </div>
            </div>
          </li>
          <!-- 聊天消息 -->
          <li v-for="(item, i) in chatMsgList" :key="`chat-${i}`" class="chat-msg">
            <div class="times">{{ formatTime(chatMsgList[i > 0 ? i - 1 : 0].date, item.date) }}</div>
            <!-- 自己 -->
            <div v-if="item.sendUserId === userInfo.id" class="msg me">
              <Avatar class="avatar" :url="userInfo.avatar" alt="头像" />
              <div :class="['me-msg', item.state === 0 && 'loading-msg']">
                <div v-if="item.msg.contentType === TYPES.TEXT" v-html="toHtml(item.msg.text)" />
                <div v-else-if="item.msg.contentType === TYPES.PICTURE">
                  <LazyImg
                    :src="item.msg.smallImgUrl"
                    @click.native="previewImg(item.msg.smallImgUrl)"
                    @load="setScrollHeight('load')"
                  />
                </div>
                <Loading
                  class="loading"
                  type="spinner"
                  :color="item.state === 0 ? '#c9c9c9' : 'transparent'"
                  :style="{
                    background: item.state === 0 ? 'rgba(0, 0, 0, 0.4)' : 'transparent'
                  }"
                />
              </div>
            </div>
            <!-- 好友 -->
            <div v-else class="msg user">
              <Avatar class="avatar" :url="item.sendUserAvatar" alt="头像" />
              <p class="nickname">{{ item.sendUserNickname }}</p>
              <div class="user-msg">
                <div v-if="item.msg.contentType === TYPES.TEXT" v-html="toHtml(item.msg.text)" />
                <div v-else-if="item.msg.contentType === TYPES.PICTURE">
                  <LazyImg
                    :src="item.msg.smallImgUrl"
                    @click.native="previewImg(item.msg.smallImgUrl)"
                    @load="setScrollHeight('load')"
                  />
                </div>
              </div>
            </div>
          </li>
        </ul>
      </PullRefresh>
    </div>

    <!-- 底部输入框 -->
    <div class="footer">
      <div :class="['box', visible.emoji && 'active']">
        <div class="input-wraper">
          <Icon name="smile-o" size="30" color="#888" class="icon" @click="toggleEmoji" />
          <Uploader accept="image/gif, image/png, image/jpeg" :after-read="afterRead">
            <Icon name="photo-o" size="30" color="#888" class="icon" />
          </Uploader>
          <Field ref="fieldInput" v-model="inputMsg" placeholder="说点什么吧~" @keyup.enter="sendMsg">
            <span slot="button" @click="sendMsg" :class="['send', isDisabledSend && 'disabled']">发送</span>
          </Field>
        </div>
        <div class="emoji-list">
          <span v-for="item in emojiList" :key="item.name" @click="handleEmoji(item)">
            <img :src="require(`@/assets/emoji/${item.name}.png`)" />
          </span>
          <span v-for="i in 20" :key="i" class="empty"></span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import { PullRefresh, Field, Uploader, Loading, ImagePreview, Icon } from 'vant'
import LazyImg from '@/components/LazyImg'
import emoji from '@/assets/emoji.json'
import { CMD, TYPES } from '@/IM'
import { getFormatTime } from '@/utils/date'
import { getFileExt } from '@/utils'
import request from '@/utils/request'

export default {
  name: 'chat',
  components: { PullRefresh, Field, Uploader, Loading, Icon, LazyImg },
  data() {
    return {
      ImSocket: null,
      CMD,
      TYPES,
      visible: {
        emoji: false
      },
      emoji,
      emojiList: [],
      roomId: this.$route.query.id,
      historyLoading: 0, // 0: 正在拉取 1: 拉取历史消息 2: 暂无历史消息
      historyList: [], // 历史消息
      imgPreviewList: [],
      msgIndex: 0,
      inputMsg: ''
    }
  },
  computed: {
    ...mapGetters(['userInfo', 'IMSocket', 'IMStatus', 'roomList']),
    isDisabledSend() {
      const inputMsg = this.inputMsg.trim()
      return !inputMsg || !this.IMStatus
    },
    chatMsgList() {
      return this.roomList[this.roomId] || []
    }
  },
  watch: {
    historyList: {
      deep: true,
      handler() {
        this.setScrollHeight()
      }
    },
    chatMsgList: {
      deep: true,
      handler() {
        this.setScrollHeight()
      }
    },
    'visible.emoji': {
      immediate: true,
      handler() {
        this.setScrollHeight()
      }
    }
  },
  async created() {
    // 处理表情包数据
    Object.keys(emoji).forEach(o => {
      this.emojiList.push({ name: emoji[o], val: o })
    })
    if (!this.roomId) {
      this.$toast.fail('获取房间信息失败！')
      return
    }
    this.getHistory(false)
    this.resetChatMsg(this.roomId)
    this.setCurRoomId(this.roomId)
  },
  beforeDestroy() {
    this.setCurRoomId('')
  },
  methods: {
    ...mapMutations({
      setChatMsg: 'setChatMsg',
      resetChatMsg: 'resetChatMsg',
      setCurRoomId: 'setCurRoomId'
    }),
    onClickLeft() {
      this.$router.go(-1)
    },
    // 调整滚动位置
    setScrollHeight(type) {
      setTimeout(() => {
        this.$refs.chat.scrollTop = this.scrollTopState ? 0 : this.$refs.chatMsgList.scrollHeight + 1000
        if (type === 'load') return
        this.imgPreviewList = []
        ;[...this.historyList, ...this.chatMsgList].forEach(({ msg }) => {
          if (msg && msg['contentType'] === this.TYPES.PICTURE) {
            this.imgPreviewList.push(msg.smallImgUrl)
          }
        })
      }, 200)
    },
    // 图片预览
    previewImg(source) {
      const index = this.imgPreviewList.findIndex(src => src === source)
      ImagePreview({
        images: this.imgPreviewList,
        startPosition: index
      })
    },
    // 下拉刷新
    onRefresh() {
      this.historyLoading = 0
      setTimeout(() => {
        this.getHistory(true)
      }, 1000)
    },
    // 关闭/展开表情
    toggleEmoji() {
      this.visible.emoji = !this.visible.emoji
    },
    // 发送表情信息
    handleEmoji(emoji) {
      this.inputMsg += emoji.val
    },
    // 字符串转 HTML
    toHtml(text) {
      const str = text
        .replace(/(\[.*?\])/g, $1 => {
          if (this.emoji[$1]) {
            let url = require(`@/assets/emoji/${this.emoji[$1]}.png`)
            return `<img src="${url}" class="emoji" title="emoji" name="${$1}" style="user-select:none" oncontextmenu="return false"/>`
          } else {
            return $1
          }
        })
        .replace(/\n/g, '</br>')
      return str
    },
    // 比较时间差
    formatTime(time1, time2) {
      const now = +new Date()
      return getFormatTime(time1 || now, time2 || now)
    },
    // 获取历史纪录
    async getHistory(topState) {
      this.scrollTopState = topState
      this.historyLoading = 0
      const lastMsg = this.historyList[0] || { id: 0 }
      const data = {
        roomId: this.roomId,
        lastMsgId: lastMsg.id
      }

      const res = await request({
        url: '/chat/list',
        method: 'GET',
        params: data
      })

      this.getHistoryOk(res)
    },
    // 成功获取历史纪录
    getHistoryOk(res) {
      const { data = [], attributes = {} } = res
      try {
        data
          .filter(o => o.msg)
          .forEach(o => {
            if (typeof o.msg === 'string') {
              o.msg = JSON.parse(o.msg)
            }
          })
      } catch (error) {
        console.log('消息转换失败')
      }
      this.historyList = data.reverse().concat(this.historyList)
      const { pageNum, pageSize, total } = attributes
      this.historyLoading = pageNum * pageSize < total ? 1 : 2 // 1: 拉取历史消息 2: 暂无历史消息
    },
    // 文件读取成功
    async afterRead({ file }) {
      if (!file) return
      // 判断文件类型
      const ext = getFileExt(file.name)
      const acceptType = ['PNG', 'GIF', 'JPG', 'JPEG']
      if (!acceptType.includes(ext.toUpperCase())) {
        return this.$toast.fail('暂不支持该文件类型')
      }
      const msgIndex = ++this.msgIndex
      const obj = {
        contentType: this.TYPES.PICTURE,
        smallImgUrl: '',
        bigImgUrl: ''
      }
      const msg = {
        msg: obj,
        sendUserId: this.userInfo.id,
        state: 0,
        msgIndex
      }
      this.setChatMsg({ roomId: this.roomId, msg })

      const req = this.$formBuilder({ file })
      const res = await request({
        url: '/tool/upload',
        method: 'POST',
        data: req
      })
      if (res.status !== 200) {
        return this.$toast.fail(res.msg || '添加图片失败')
      }

      const img = new Image()
      img.src = res.data
      img.onload = () => {
        obj.smallImgUrl = res.data
        obj.bigImgUrl = res.data
        obj.width = img.width
        obj.height = img.height
        const data = {
          roomId: this.roomId,
          msgIndex,
          msg: JSON.stringify(obj)
        }
        this.IMSocket.handleRequestEvent('SEND_MESSAGE', data, res => {
          this.messageSuccess(res)
        })
      }
    },
    // 发送消息
    sendMsg() {
      const inputMsg = this.inputMsg.trim()
      if (!inputMsg) return
      const msgIndex = ++this.msgIndex
      const obj = {
        contentType: this.TYPES.TEXT,
        text: inputMsg
      }
      const msg = {
        msg: obj,
        sendUserId: this.userInfo.id,
        state: 0,
        msgIndex
      }

      this.setChatMsg({ roomId: this.roomId, msg })

      const data = {
        roomId: this.roomId,
        msgIndex,
        msg: JSON.stringify(obj)
      }
      this.IMSocket.handleRequestEvent('SEND_MESSAGE', data, res => {
        this.messageSuccess(res)
      })
      this.visible.emoji = false
    },
    // 消息发送成功
    messageSuccess(data) {
      this.scrollTopState = false
      this.inputMsg = ''
      const msgIndex = this.chatMsgList.findIndex(x => x.msgIndex === data.msgIndex)
      const msg = this.chatMsgList[msgIndex]
      msg.state = 1
      msg.date = data.date || new Date().getTime()
      this.setChatMsg({ roomId: this.roomId, msg, msgIndex })
    }
  }
}
</script>

<style lang="less" scoped>
@import url('./index.less');
</style>
