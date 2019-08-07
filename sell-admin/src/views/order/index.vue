<template>
  <div class="app-container">
    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column prop="buyerName" label="买家名称" align="center" min-width="100px" />
      <el-table-column prop="buyerPhone" label="买家电话" align="center" min-width="100px" />
      <el-table-column prop="buyerAddress" label="买家地址" align="center" min-width="150px" />
      <el-table-column prop="buyerOpenid" label="买家openid" align="center" min-width="150px" />
      <el-table-column prop="orderAmount" label="订单金额" align="center" min-width="100px" />
      <el-table-column label="订单状态" align="center" min-width="100px">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.orderStatus | orderStatusFilter('type')"
          >{{ scope.row.orderStatus | orderStatusFilter('title') }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.payStatus | payStatusFilter('type')"
          >{{ scope.row.payStatus | payStatusFilter('title') }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" min-width="150px" />
      <el-table-column label="操作" align="center" min-width="150px">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleDeatil(scope.row)">详情</el-button>
          <el-button
            size="mini"
            type="danger"
            :disabled="scope.row.orderStatus !== 0"
            @click="handleCancel(scope.row)"
          >取消</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.pageNum"
      :limit.sync="query.pageSize"
      @pagination="getData()"
    />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getOrderList, cancelOrder } from '@/api/order'

const orderStatusEnum = [
  { title: '新订单', type: 'primary' },
  { title: '已完成订单', type: 'success' },
  { title: '已取消订单', type: 'info' }
]

const payStatusEnum = [{ title: '等待支付', type: 'primary' }, { title: '支付成功', type: 'success' }]

export default {
  name: 'Order',
  components: { Pagination },
  filters: {
    orderStatusFilter(val, type) {
      return orderStatusEnum[val][type]
    },
    payStatusFilter(val, type) {
      return payStatusEnum[val][type]
    }
  },
  data() {
    return {
      loading: {
        table: false,
        formDialog: false
      },
      visible: {
        formDialog: false
      },
      total: 0,
      list: null,
      query: {
        pageNum: 1,
        pageSize: 10
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
    async getData() {
      try {
        this.loading.table = true
        const res = await getOrderList({ ...this.query })
        this.loading.table = false
        this.list = res.data
        this.total = res.attributes.total
      } catch (error) {
        this.loading.table = false
      }
    },
    handleCancel(row) {
      this.$confirm('此操作将取消该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          try {
            this.loading.table = true
            await cancelOrder({ orderId: row.orderId })
            this.loading.table = false
            this.$message.success('操作成功')
            this.getData()
          } catch (error) {
            this.loading.table = false
          }
        })
        .catch(() => {
          console.log('取消操作')
        })
    },
    handleDeatil(row) {
      this.visible.detailDialog = true
    }
  }
}
</script>

