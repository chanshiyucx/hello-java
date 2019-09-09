<template>
  <div id="login">
    <div class="main">
      <van-cell-group>
        <van-field
          v-model="username"
          required
          clearable
          label="用户名"
          right-icon="question-o"
          placeholder="请输入用户名"
          @click-right-icon="$toast('question')"
        />

        <van-field v-model="password" type="password" label="密码" placeholder="请输入密码" required />
      </van-cell-group>

      <van-button class="submit" type="primary">登录/注册</van-button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'login',
  data() {
    return {
      username: '',
      password: ''
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
