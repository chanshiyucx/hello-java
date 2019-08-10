<template>
  <div class="app-container">
    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column label="订单ID" align="center" min-width="150px">
        <template slot-scope="scope">
          <el-link type="primary" @click="handleDeatil(scope.row)">{{ scope.row.orderId }}</el-link>
        </template>
      </el-table-column>
      <el-table-column prop="buyerName" label="买家名称" align="center" min-width="100px" />
      <el-table-column prop="buyerPhone" label="买家电话" align="center" min-width="100px" />
      <el-table-column prop="buyerAddress" label="买家地址" align="center" min-width="100px" />
      <el-table-column prop="buyerOpenid" label="买家openid" align="center" min-width="100px" />
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
          <el-button
            size="mini"
            type="success"
            :disabled="scope.row.orderStatus !== 0"
            @click="handleFinish(scope.row)"
          >完结</el-button>
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

    <el-dialog title="订单详情" :visible.sync="visible.detailDialog" width="900px">
      <el-table v-loading="loading.detailDialog" :data="orderDetail.orderDetailList" border fit highlight-current-row>
        <el-table-column prop="productId" label="商品ID" align="center" min-width="150px" />
        <el-table-column label="小图" align="center" min-width="150px">
          <template slot-scope="scope">
            <img class="thumb" :src="scope.row.productIcon" alt="小图" >
          </template>
        </el-table-column>
        <el-table-column prop="productName" label="名称" align="center" min-width="100px" />
        <el-table-column prop="productPrice" label="单价" align="center" min-width="100px" />
        <el-table-column prop="productQuantity" label="数量" align="center" min-width="100px" />
        <el-table-column prop="createTime" label="创建时间" align="center" min-width="150px" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getOrderList, cancelOrder, finishOrder, getOrderDetail } from '@/api/order'

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
        detailDialog: false
      },
      visible: {
        detailDialog: false
      },
      total: 0,
      list: null,
      query: {
        pageNum: 1,
        pageSize: 10
      },
      orderDetail: {}
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
    handleFinish(row) {
      this.$confirm('此操作将完结该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          try {
            this.loading.table = true
            await finishOrder({ orderId: row.orderId })
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
    async handleDeatil(row) {
      this.visible.detailDialog = true
      try {
        this.loading.detailDialog = true
        const res = await getOrderDetail({ orderId: row.orderId })
        this.loading.detailDialog = false
        this.orderDetail = res.data
      } catch (error) {
        this.loading.detailDialog = false
      }
    }
  }
}
</script>

