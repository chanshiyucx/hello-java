<template>
  <div id="nickname">
    <van-nav-bar title="设置昵称" class="header" left-arrow @click-left="onClickLeft" />
    <van-field v-model="userInfo.nickname" center clearable>
      <van-button :disabled="isDisabled" slot="button" size="mini" type="primary" @click="handleSubmit">保存</van-button>
    </van-field>
    <p class="tips">好名字可以让你的朋友更容易记住你。</p>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'nickname',
  computed: {
    ...mapGetters(['userInfo']),
    isDisabled() {
      return this.userInfo.nickname.trim() === ''
    }
  },
  methods: {
    ...mapMutations({
      setUserInfo: 'setUserInfo'
    }),
    onClickLeft() {
      this.$router.go(-1)
    },
    async handleSubmit() {
      const validate = /^[a-zA-Z0-9\u4e00-\u9fa5]{4,12}$/
      if (!validate.test(this.userInfo.nickname)) {
        return this.$toast.fail('用户名必须为4-12位中文字母或数字')
      }
      try {
        const req = {
          id: this.userInfo.id,
          nickname: this.userInfo.nickname
        }
        const res = await request({
          url: '/user/update',
          method: 'POST',
          data: req
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.$toast.success(res.msg)
        this.setUserInfo(res.data)
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
