import Vue from 'vue'
import Vuex from 'vuex'
import { localRead, localSave } from '@/utils'
import avatar from '@/assets/images/avatar.png'

Vue.use(Vuex)

const localUserInfo = localRead('userInfo') ? JSON.parse(localRead('userInfo')) : {}
const localToken = localRead('token')

export default new Vuex.Store({
  state: {
    userInfo: localUserInfo,
    token: localToken
  },
  mutations: {
    setUserInfo(state, userInfo) {
      if (!userInfo.avatar) {
        userInfo.avatar = avatar
        userInfo.avatarBig = avatar
      }
      state.userInfo = userInfo
      state.token = userInfo.id
      localSave('userInfo', JSON.stringify(userInfo))
      localSave('token', userInfo.id)
    },
    logout(state) {
      state.userInfo = {}
      state.token = ''
      localSave('userInfo', JSON.stringify({}))
      localSave('token', '')
    }
  },
  getters: {
    userInfo: state => state.userInfo,
    token: state => state.token
  }
})
