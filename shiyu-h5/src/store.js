import Vue from 'vue'
import Vuex from 'vuex'
import { localRead } from '@/utils'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: localRead('userInfo'),
    token: localRead('token')
  },
  mutations: {},
  actions: {},
  getters: {
    userInfo: state => state.userInfo,
    token: state => state.token
  }
})
