<template>
  <img :src="imgSrc" alt />
</template>

<script>
export default {
  props: {
    src: {
      type: String,
      default: ''
    },
    className: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      defaultImg: require('@/assets/images/default.png'),
      imgSrc: ''
    }
  },
  watch: {
    src: {
      immediate: true,
      handler() {
        this.loadImg()
      }
    }
  },
  methods: {
    loadImg() {
      this.imgSrc = this.defaultImg
      const img = new Image()
      const imgArr = this.src.split('.')
      imgArr[imgArr.length - 2] = imgArr[imgArr.length - 2] + '_150x150'
      const loadImg = imgArr.join('.')
      img.onload = () => {
        this.$nextTick(() => {
          this.imgSrc = loadImg
          this.$emit('load')
        })
      }
      img.src = loadImg
    }
  }
}
</script>
