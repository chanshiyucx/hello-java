<template>
  <el-upload
    v-loading="loading"
    class="uploader"
    name="image"
    :show-file-list="false"
    :action="apiUrl"
    :headers="headers"
    :accept="accept"
    :before-upload="beforeUpload"
    :on-success="handleSuccess"
    :on-error="handleError"
  >
    <img v-if="preview" :src="preview" class="img" >
    <i v-else class="el-icon-plus uploader-icon" />
    <div class="mask">点击上传</div>
  </el-upload>
</template>

<script>
const auth = {
  ClientID: '4433d0ee1f85168',
  ClientSecret: '17408a8b7389c562acfdf80c2c24f903cbcc02c5'
}

export default {
  name: 'Upload',
  props: {
    loading: {
      type: Boolean,
      default: false
    },
    accept: {
      type: String,
      default: 'image/*'
    },
    preview: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      apiUrl: 'https://api.imgur.com/3/image',
      headers: {
        Authorization: 'Client-ID ' + auth.ClientID
      }
    }
  },
  methods: {
    beforeUpload(file) {
      this.$emit('beforeUpload', file)
    },
    handleSuccess(response, file, fileList) {
      this.$emit('handleSuccess', response, file, fileList)
    },
    handleError(err, file, fileList) {
      this.$message.error('图片上传失败:' + err.message)
    }
  }
}
</script>
<style lang="scss" scoped>
.uploader {
  width: 162px;
  height: 162px;

  /deep/.el-upload {
    cursor: pointer;
    position: relative;
    overflow: hidden;
    border-radius: 6px;
    border: 1px dashed #d9d9d9;
    background-color: #eee;
    &:hover {
      .mask {
        opacity: 1;
      }
    }
  }
}

.uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 160px;
  height: 160px;
  line-height: 160px;
  text-align: center;
}
.mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 160px;
  height: 160px;
  line-height: 160px;
  opacity: 0;
  color: #fff;
  text-align: center;
  transition: opacity 0.3s ease;
  border-radius: 6px;
  background-color: rgba(0, 0, 0, 0.4);
  z-index: 100;
}
.img {
  width: 160px;
  height: 160px;
  display: block;
}
</style>
