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
    IMSocket: null
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
      commit('setIMSocket', IMSocket)
    }
  },
  getters: {
    IMSocket: state => state.IMSocket,
    userInfo: state => state.userInfo,
    userId: state => state.userInfo.id
  }
})
