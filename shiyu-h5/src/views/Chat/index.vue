<template>
  <div id="chat">
    <van-nav-bar title="聊天" class="header" left-arrow @click-left="onClickLeft" />
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

        <ul ref="chatList" class="chat-list">
          <!-- 历史消息 -->
          <li v-for="(item, i) in historyList" :key="i" class="chat-msg">
            <!-- <div class="times">{{ formatTime(historyList[i > 0 ? i - 1 : 0].date, item.date) }}</div> -->
            <!-- 自己 -->
            <!-- <div v-if="imInfo.imUserId && item.sendUserId === imInfo.imUserId" class="msg me">
              <img class="avatar" src="@/assets/images/service/client.jpg" alt />
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
            </div>-->
            <!-- 好友 -->
            <!-- <div v-else class="msg user">
              <img class="avatar" :src="item.avatar || serviceInfo.sysAvatar || defaultAvatar" alt />
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
            </div>-->
          </li>
          <!-- 分界线 -->
          <li class="divider middle-divider" v-if="historyList.length">以下为最新消息</li>
          <!-- 聊天消息 -->
          <li v-for="(item, i) in chatList" :key="`chat-${i}`" class="chat-msg">
            <!-- <div class="times">{{ formatTime(chatList[i > 0 ? i - 1 : 0].date, item.date) }}</div> -->
            <!-- 自己 -->
            <!-- <div v-if="imInfo.imUserId && item.sendUserId === imInfo.imUserId" class="msg me">
              <img class="avatar" src="@/assets/images/service/client.jpg" alt />
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
            </div>-->
            <!-- 好友 -->
            <!-- <div v-else class="msg user">
              <img class="avatar" :src="item.sendAvatar || serviceInfo.sysAvatar || defaultAvatar" alt />
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
            </div>-->
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
import { mapGetters } from 'vuex'
import { PullRefresh, Field, Uploader, Loading, ImagePreview, Icon } from 'vant'
import LazyImg from '@/components/LazyImg'
import emoji from '@/assets/emoji.json'
import IM, { CMD, TYPES } from '@/IM'
import config from '@/config'
import { localRead, localSave, getFileExt } from '@/utils'
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
      historyLoading: 0, // 0: 正在拉取 1: 拉取历史消息 2: 暂无历史消息
      historyList: [], // 历史消息
      chatList: [], // 对话消息
      imgPreviewList: [],
      msgIndex: 0,
      inputMsg: ''
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    isDisabledSend() {
      const inputMsg = this.inputMsg.trim()
      return !inputMsg
    }
  },
  watch: {},
  async created() {
    // 处理表情包数据
    Object.keys(emoji).forEach(o => {
      this.emojiList.push({ name: emoji[o], val: o })
    })
    this.init()
  },
  methods: {
    // 初始化
    init() {
      try {
        this.$toast.loading({ duration: 0, message: '客服连接中' })
        this.instaceIm()
      } catch (error) {
        this.$toast.fail('客服初始化失败')
      }
    },
    // 创建 IM 连接
    instaceIm() {
      this.ImSocket = new IM({
        url: config.imWSUrl,
        onconnect: this.onconnect, // 服务器连接成功
        ondisconnect: this.ondisconnect, // 服务器已断开连接
        onerror: this.onerror, // 服务器连接发生错误
        onreconnect: this.onreconnect, // 服务器开始重连
        onmsgerror: this.onmsgerror, // 服务器接收消息内容错误
        handleResponseEvent: this.handleResponseEvent, // 消息业务处理
        heartBeatMsg: { command: this.CMD.HEARTBEAT_REQUEST, data: {} } // 心跳包消息
      })
    },
    onconnect() {
      this.$toast.loading({ duration: 0, message: '连接成功，正在联系客服' })
      // this.linkStart()
    },
    ondisconnect() {
      this.$toast.fail('服务器已断开连接')
    },
    onerror() {
      this.$toast.fail('客服连接错误')
    },
    onreconnect() {
      this.$toast.loading({ duration: 0, message: '重连客服中' })
      // this.initData()
    },
    onmsgerror(error) {
      this.$message.error(error)
    },
    onClickLeft() {
      this.$router.go(-1)
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
    // 文件读取成功
    async afterRead({ file }) {
      if (!file) return
      // 判断文件类型
      const ext = getFileExt(file.name)
      const acceptType = ['PNG', 'GIF', 'JPG', 'JPEG']
      if (!acceptType.includes(ext.toUpperCase())) {
        return this.$toast.fail('暂不支持该文件类型')
      }
      // const msgIndex = ++this.msgIndex
      // const obj = {
      //   contentType: this.TYPES.PICTURE,
      //   smallImgUrl: '',
      //   bigImgUrl: ''
      // }
      // const msg = {
      //   msg: obj,
      //   sendUserId: this.imInfo.imUserId,
      //   state: 0,
      //   msgIndex
      // }
      // this.chatMsgList.push(msg)
      const req = this.$formBuilder({ file })
      const res = await request({
        url: '/tool/upload',
        method: 'POST',
        data: req
      })
      if (res.status !== 200) {
        return this.$toast.fail(res.msg || '添加图片失败')
      }
      // const img = new Image()
      // img.src = res.data
      // img.onload = () => {
      //   obj.smallImgUrl = res.data
      //   obj.bigImgUrl = res.data
      //   obj.width = img.width
      //   obj.height = img.height
      //   const data = {
      //     msgIndex,
      //     msg: JSON.stringify(obj)
      //   }
      //   this.handleRequestEvent('sendMessage', data)
      // }
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
      // this.chatMsgList.push(msg)

      const data = {
        msgIndex,
        msg: JSON.stringify(obj)
      }
      console.log('data-->', data)
      this.handleRequestEvent('sendMessage', data)
      this.visible.emoji = false
    },
    /**
     * 统一处理数据请求逻辑 【工作台 ===》IM】
     * 将不同消息类型转换成对应的消息关键字
     * @param { String } type 消息类型
     * @param { Object } data 消息内容
     */
    handleRequestEvent(type, data) {
      let command, newData
      switch (type) {
        case 'getHistoryMsg': // 请求历史消息
          command = CMD.MESSAGE_HISTORY_REQUEST
          newData = { ...data }
          break
        case 'sendMessage': // 发送消息
          command = CMD.MESSAGE_REQUEST
          newData = { ...data }
          break
        case 'signMsg': // 签收消息
          command = CMD.MESSAGE_SIGN_REQUEST
          newData = { ...data }
          break
        default:
          return
      }
      const msg = { command, data: newData }
      this.ImSocket.handleRequestEvent(msg)
    },
    /**
     * 统一处理消息响应逻辑 【IM ===》工作台】
     * 将接收消息关键字触发不同的回调事件
     * @param { Enumerator } command 消息关键字
     * @param { Object } data 消息内容
     */
    handleResponseEvent(command, data) {
      switch (command) {
        case CMD.MESSAGE_RESPONSE: // 接收消息
          this.receiveMessage(data)
          break
        case CMD.MESSAGE_SUCCESS: // 发送消息成功的响应
          this.messageSuccess(data)
          break
        case CMD.MESSAGE_SIGN_RESPONSE: // 签收消息响应
          this.signSuccess(data)
          break
        case CMD.MESSAGE_HISTORY_RESPONSE: // 返回历史消息
          this.getHistoryOk(data)
          break
        default:
          break
      }
    }
  }
}
</script>

<style lang="less" scoped>
@import url('./index.less');
</style>
