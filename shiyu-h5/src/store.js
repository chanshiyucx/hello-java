import Vue from 'vue'
import Vuex from 'vuex'
import { localRead, localSave } from '@/utils'

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
      state.userInfo = userInfo
      state.token = userInfo.id
      localSave('userInfo', JSON.stringify(userInfo))
      localSave('token', JSON.stringify(userInfo.id))
    }
  },
  getters: {
    userInfo: state => state.userInfo,
    token: state => state.token
  }
})
