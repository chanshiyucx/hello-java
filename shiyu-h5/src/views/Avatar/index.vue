<template>
  <div id="avatar">
    <van-nav-bar title="个人头像" class="header" left-arrow @click-left="onClickLeft" @click-right="onClickRight">
      <van-icon name="ellipsis" slot="right" />
    </van-nav-bar>
    <img :src="avatar" alt="头像" />
    <van-popup v-model="visible.popup" position="bottom" class="popup">
      <div id="pick-avatar" class="popup-item" @click="visible.popup = false">从手机相册选择</div>
      <div class="popup-item" @click="saveAvatar">保存到手机</div>
    </van-popup>
    <avatar-cropper
      @uploading="handleUploading"
      @uploaded="handleUploaded"
      @completed="handleCompleted"
      @error="handlerError"
      trigger="#pick-avatar"
      upload-url="https://demo.overtrue.me/upload.php"
    />
  </div>
</template>

<script>
import AvatarCropper from 'vue-avatar-cropper'
import { mapGetters } from 'vuex'
import { downloadImg } from '@/utils'

export default {
  name: 'avatar',
  components: { AvatarCropper },
  data() {
    return {
      defaultAvatar: require('@/assets/images/avatar.png'),
      visible: {
        popup: true
      }
    }
  },
  computed: {
    ...mapGetters(['userInfo']),
    avatar() {
      return this.userInfo.avatar || this.defaultAvatar
    }
  },
  mounted() {
    // popup 是懒加载组件，需要初始挂载一次
    this.$nextTick(() => {
      this.visible.popup = false
    })
  },
  methods: {
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
    handleUploading(form, xhr) {
      this.message = 'uploading...'
    },
    handleUploaded(response) {
      if (response.status == 'success') {
        this.user.avatar = response.url
        this.message = 'user avatar updated.'
      }
    },
    handleCompleted(response, form, xhr) {
      this.message = 'upload completed.'
    },
    handlerError(message, type, xhr) {
      this.message = 'Oops! Something went wrong...'
    }
  }
}
</script>

<style lang="less" scope>
@import url('./index.less');
</style>
