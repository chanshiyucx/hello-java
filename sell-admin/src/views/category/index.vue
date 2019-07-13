<template>
  <div class="app-container">分类列表</div>
</template>

<script>
import { getList } from '@/api/product'

export default {
  data() {
    return {
      loading: {
        table: false
      },
      visible: {},
      list: null
    }
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      this.loading.table = true
      const res = await getList()
      this.loading.table = false
      if (res.status !== 200) {
        return this.$message.error(res.msg)
      }
      console.log('res-->', res)
    }
  }
}
</script>

