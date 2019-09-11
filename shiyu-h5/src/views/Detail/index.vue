<template>
  <div class="detail">
    <van-nav-bar title="好友详情" class="header" left-arrow @click-left="onClickLeft" />
    <div class="main">
      <div class="footer"></div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import request from '@/utils/request'

export default {
  name: 'detail',
  data() {
    return {
      user: ''
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  created() {
    this.getDetail()
  },
  methods: {
    async getDetail() {
      try {
        const { id } = this.$route.query
        const res = await request({
          url: '/user/detail',
          method: 'GET',
          params: {
            userId: id
          }
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.user = res.data
      } catch (error) {
        console.log(error)
      }
    },
    onClickLeft() {
      this.$router.go(-1)
    }
  }
}
</script>
