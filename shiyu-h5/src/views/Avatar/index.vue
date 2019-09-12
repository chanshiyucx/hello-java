<template>
  <div id="avatar">
    <van-nav-bar
      title="个人头像"
      class="header"
      left-arrow
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <van-icon name="ellipsis" slot="right" />
    </van-nav-bar>
    <img :src="userInfo.avatarBig" alt="头像" />
    <van-popup v-model="visible.popup" position="bottom" class="popup">
      <div id="pick-avatar" class="popup-item" @click="visible.popup = false">从手机相册选择</div>
      <div class="popup-item" @click="saveAvatar">保存到手机</div>
    </van-popup>
    <avatar-cropper
      @uploading="handleUploading"
      @uploaded="handleUploaded"
      @completed="handleCompleted"
      @error="handlerError"
      :upload-url="uploadUrl"
      trigger="#pick-avatar"
    />
  </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex'
import AvatarCropper from 'vue-avatar-cropper'
import config from '@/config'
import { downloadImg } from '@/utils'
import request from '@/utils/request'

export default {
  name: 'avatar',
  components: { AvatarCropper },
  data() {
    return {
      uploadUrl: `${config.baseURL}/tool/upload`,
      visible: {
        popup: true
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo'])
  },
  mounted() {
    // popup 是懒加载组件，需要初始挂载一次
    this.$nextTick(() => {
      this.visible.popup = false
    })
  },
  methods: {
    ...mapMutations({
      setUserInfo: 'setUserInfo'
    }),
    onClickLeft() {
      this.$router.go(-1)
    },
    onClickRight() {
      this.visible.popup = true
    },
    saveAvatar() {
      downloadImg(this.avatar, 'avatar.png')
      this.visible.popup = false
    },
    handleUploading() {
      this.$toast.loading({
        mask: true,
        duration: 0,
        message: '上传中...'
      })
    },
    async handleUploaded(response) {
      if (response.status !== 200) {
        return this.$toast.fail(response.msg)
      }
      try {
        const url = response.data
        const imgArr = url.split('.')
        imgArr[imgArr.length - 2] = imgArr[imgArr.length - 2] + '_150x150'
        const avatar = imgArr.join('.')
        const req = {
          id: this.userInfo.id,
          avatarBig: url,
          avatar
        }
        const res = await request({
          url: '/user/update',
          method: 'POST',
          data: req
        })
        if (res.status !== 200) {
          return this.$toast.fail(res.msg)
        }
        this.setUserInfo(res.data)
      } catch (error) {
        console.log(error)
      }
    },
    handleCompleted() {
      this.$toast.clear()
    },
    handlerError(message) {
      this.$toast.fail(message || '上传失败')
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
