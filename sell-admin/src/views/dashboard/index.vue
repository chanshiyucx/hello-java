<template>
  <div class="dashboard">
    <img :src="emptyGif" class="emptyGif" >
  </div>
</template>

<script>
export default {
  name: 'Dashboard',
  data() {
    return {
      emptyGif: 'https://wpimg.wallstcn.com/0e03b7da-db9e-4819-ba10-9016ddfdaed3'
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  img {
    display: block;
    margin: 30px auto;
  }
}
</style>
