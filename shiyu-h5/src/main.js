import Vue from 'vue'
import Vant from 'vant'
import 'vant/lib/index.css'

import App from './App.vue'
import router from './router'
import store from './store'
import Avatar from './components/Avatar'

import '@/assets/style/reset.less'
import '@/assets/icons'

// 表单生成器
import { formBuilder } from '@/utils'
Vue.prototype.$formBuilder = formBuilder

Vue.use(Vant)
Vue.component('Avatar', Avatar)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
