import Vue from 'vue'
import Vuex from 'vuex'
import config from '@/config'
import IM from '@/IM'
import { localRead, localSave } from '@/utils'
import avatar from '@/assets/images/avatar.png'

Vue.use(Vuex)

const localUserInfo = localRead('userInfo') ? JSON.parse(localRead('userInfo')) : {}

export default new Vuex.Store({
  state: {
    userInfo: localUserInfo,
    IMSocket: null,
    IMStatus: false,
    roomList: {}
  },
  mutations: {
    setUserInfo(state, userInfo) {
      if (!userInfo.avatar) {
        userInfo.avatar = avatar
        userInfo.avatarBig = avatar
      }
      state.userInfo = userInfo
      localSave('userInfo', JSON.stringify(userInfo))
    },
    setIMSocket(state, IMSocket) {
      state.IMSocket = IMSocket
    },
    setIMStatus(state, IMStatus) {
      state.IMStatus = IMStatus
    },
    setChatMsg(state, data) {
      const { roomId, msg, msgIndex } = data
      const roomList = state.roomList
      // 初始化房间信息
      if (!roomList[roomId]) {
        roomList[roomId] = { chatMsgList: [] }
      }
      // 设置聊天记录
      const roomInfo = roomList[roomId]
      if (msgIndex >= 0) {
        roomInfo.chatMsgList[msgIndex] = msg
      } else {
        roomInfo.chatMsgList.push(msg)
      }
      state.roomList = { ...roomList }
    },
    logout(state) {
      state.userInfo = {}
      localSave('userInfo', JSON.stringify({}))
    }
  },
  actions: {
    initIM({ commit, state }) {
      const { id, username, nickname } = state.userInfo
      const IMSocket = new IM({
        url: config.imWSUrl,
        onconnect: () => {
          const data = { userId: id, username, nickname }
          IMSocket.handleRequestEvent('LOGIN', data)
        }
      })
      IMSocket.on('LOGIN', () => {
        commit('setIMStatus', true)
      })
      IMSocket.on('ACCEPT_MESSAGE', data => {
        console.log('接收消息-->', data)
        try {
          data.msg = JSON.parse(data.msg)
          commit('setChatMsg', { roomId: data.roomId, msg: data })
        } catch (error) {
          console.log('消息转换失败')
        }
      })
      commit('setIMSocket', IMSocket)
    }
  },
  getters: {
    IMSocket: state => state.IMSocket,
    IMStatus: state => state.IMStatus,
    userInfo: state => state.userInfo,
    userId: state => state.userInfo.id,
    roomList: state => state.roomList
  }
})
