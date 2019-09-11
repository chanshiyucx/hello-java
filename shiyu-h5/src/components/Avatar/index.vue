<template>
  <img :src="imgUrl" alt="avatar" />
</template>

<script>
import avatar from '@/assets/images/avatar.png'

export default {
  name: 'avatar',
  props: {
    url: {
      type: String,
      default: ''
    }
  },
  computed: {
    imgUrl() {
      return this.url || avatar
    }
  }
}
</script>

<style lang="less" scope>
img {
  width: 36px;
  height: 36px;
  border-radius: 5px;
}
</style>
