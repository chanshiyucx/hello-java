<template>
  <div id="login">
    <div class="main">
      <h3>時語</h3>
      <van-cell-group>
        <van-field ref="username" v-model="username" left-icon="contact" placeholder="用户名" clearable />
        <van-field
          ref="password"
          v-model="password"
          left-icon="closed-eye"
          type="password"
          placeholder="密码"
          clearable
        />
      </van-cell-group>
      <van-button class="submit" type="primary" @click="handleSubmit">登录/注册</van-button>
    </div>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'login',
  data() {
    return {
      username: '',
      password: ''
    }
  },
  methods: {
    ...mapMutations({
      setUserInfo: 'setUserInfo'
    }),
    async handleSubmit() {
      const validate = /^[a-zA-Z0-9]{4,12}$/
      if (!validate.test(this.username)) {
        this.$refs.username.focus()
        return this.$toast.fail('用户名必须为4-12位数字或字母')
      }
      if (!validate.test(this.password)) {
        this.$refs.password.focus()
        return this.$toast.fail('密码必须为4-12位数字或字母')
      }
      try {
        const res = await request({
          url: '/user/registerOrlogin',
          method: 'POST',
          data: {
            username: this.username,
            password: this.password
          }
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.setUserInfo(res.data)
        this.$router.push('/')
      } catch (error) {
        console.log(error)
      }
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
