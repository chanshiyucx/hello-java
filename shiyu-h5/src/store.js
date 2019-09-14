import Vue from 'vue'
import Vuex from 'vuex'
import config from '@/config'
import IM from '@/IM'
import { localRead, localSave } from '@/utils'
import request from '@/utils/request'
import avatar from '@/assets/images/avatar.png'

Vue.use(Vuex)

const localUserInfo = localRead('userInfo') ? JSON.parse(localRead('userInfo')) : {}
const localRoomCache = localRead('roomCache') ? JSON.parse(localRead('roomCache')) : []

let lastSaveTime = +new Date()

export default new Vuex.Store({
  state: {
    userInfo: localUserInfo,
    IMSocket: null,
    IMStatus: false,
    roomList: {},
    roomCache: localRoomCache,
    curRoomId: ''
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
    setCurRoomId(state, roomId) {
      state.curRoomId = roomId
    },
    setRoom(state, room) {
      state.roomCache.unshift({
        roomInfo: room,
        lastMsg: '',
        unReadCount: 0
      })
    },
    resetChatMsg(state, roomId) {
      state.roomList[roomId] = []
      const item = state.roomCache.find(o => o.roomInfo.id === roomId)
      if (item) {
        item.unReadCount = 0
      }
    },
    setChatMsg(state, data) {
      const { roomId, msg, msgIndex } = data
      const roomList = state.roomList
      // 初始化房间信息
      if (!roomList[roomId]) {
        roomList[roomId] = []
      }
      // 设置聊天记录
      const roomInfo = roomList[roomId]
      if (msgIndex >= 0) {
        roomInfo[msgIndex] = msg
      } else {
        roomInfo.push(msg)
      }
      state.roomList = { ...roomList }

      // 保存本地消息
      const item = state.roomCache.find(o => o.roomInfo.id === roomId)
      if (item) {
        item.lastMsg = data
        if (roomId !== state.curRoomId) {
          item.unReadCount += 1
        }
        const now = +new Date()
        if (now - lastSaveTime > 1000) {
          lastSaveTime = now
          localSave('roomCache', JSON.stringify(state.roomCache))
        }
      }
    },
    logout(state) {
      state.userInfo = {}
      localSave('userInfo', JSON.stringify({}))
    }
  },
  actions: {
    initIM({ dispatch, commit, state }) {
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
      IMSocket.on('ACCEPT_MESSAGE', async data => {
        try {
          data.msg = JSON.parse(data.msg)
          await dispatch('getRoomInfo', data.roomId)
          commit('setChatMsg', { roomId: data.roomId, msg: data })
        } catch (error) {
          console.log(error)
        }
      })
      commit('setIMSocket', IMSocket)
    },
    async getRoomInfo({ state, commit }, roomId) {
      const inx = state.roomCache.findIndex(o => o.roomInfo.id === roomId)
      if (inx >= 0) return
      // 如果该房间未被缓存
      try {
        const res = await request({
          url: '/room/detail',
          method: 'GET',
          params: { roomId }
        })
        commit('setRoom', res.data)
      } catch (error) {
        console.log(error)
      }
    }
  },
  getters: {
    IMSocket: state => state.IMSocket,
    IMStatus: state => state.IMStatus,
    userInfo: state => state.userInfo,
    userId: state => state.userInfo.id,
    roomList: state => state.roomList,
    roomCache: state => state.roomCache
  }
})
