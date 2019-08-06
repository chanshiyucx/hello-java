<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-plus" @click="handleDialog()">新增商品</el-button>
    </div>

    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column prop="productName" label="名称" align="center" min-width="150px" />
      <el-table-column prop="productDescription" label="描述" align="center" min-width="150px" />
      <el-table-column prop="productIcon" label="小图" align="center" min-width="150px" />
      <el-table-column prop="productPrice" label="单价" align="center" min-width="150px" />
      <el-table-column prop="productStock" label="库存" align="center" min-width="150px" />
      <el-table-column prop="productStock" label="状态" align="center" min-width="150px">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.productStatus === 0 ? 'success' : 'danger'"
          >{{ scope.row.productStatus === 0 ? '上架' : '下架' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="categoryType" label="类目" align="center" min-width="150px" />
      <el-table-column label="操作" align="center" min-width="150px">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleDialog(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="`${status === 'edit' ? '编辑' : '新增'}类目`"
      :visible.sync="visible.formDialog"
      :close-on-click-modal="false"
      width="500px"
    >
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        label-position="left"
        label-width="100px"
        style="width: 400px;"
      >
        <el-form-item label="名称" prop="productName">
          <el-input v-model="dataForm.productName" />
        </el-form-item>
        <el-form-item label="单价" prop="productPrice">
          <el-input v-model="dataForm.productPrice" />
        </el-form-item>
        <el-form-item label="库存" prop="productStock">
          <el-input v-model="dataForm.productStock" />
        </el-form-item>
        <el-form-item label="描述" prop="productDescription">
          <el-input v-model="dataForm.productDescription" />
        </el-form-item>
        <el-form-item label="小图" prop="productIcon">
          <el-input v-model="dataForm.productIcon" />
        </el-form-item>
        <el-form-item label="类目" prop="categoryType">
          <el-select v-model="dataForm.categoryType" placeholder="请选择类目">
            <el-option
              v-for="item in categoryList"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="productStatus">
          <el-switch v-model="dataForm.productStatus" active-color="#13ce66" inactive-color="#ff4949" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible.formDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading.formDialog" @click="handleSure">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCategoryList } from '@/api/category'
import { getProductList, createProduct, updateProduct, deleteProduct } from '@/api/product'

const initdataForm = {
  productName: '',
  productPrice: '',
  productStock: '',
  productStatus: 0,
  productDescription: '',
  productIcon: '',
  categoryType: ''
}

export default {
  name: 'Product',
  data() {
    return {
      loading: {
        table: false,
        formDialog: false
      },
      visible: {
        formDialog: false
      },
      status: 'edit',
      list: null,
      categoryList: null,
      dataForm: { ...initdataForm },
      rules: {
        productName: [{ required: true, message: '请输入名称', trigger: 'change' }],
        productPrice: [{ required: true, message: '请输入单价', trigger: 'change' }],
        productStock: [{ required: true, message: '请输入库存', trigger: 'change' }],
        productStatus: [{ required: true, message: '请输入状态', trigger: 'change' }],
        productDescription: [{ required: true, message: '请输入描述', trigger: 'change' }],
        productIcon: [{ required: true, message: '请输入小图', trigger: 'change' }],
        categoryType: [{ required: true, message: '请输入类目', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getCategoryList()
    this.getData()
  },
  methods: {
    async getCategoryList() {
      this.loading.table = true
      const res = await getCategoryList()
      this.loading.table = false
      if (res.status !== 200) {
        return this.$message.error(res.msg)
      }
      this.categoryList = res.data
    },
    async getData() {
      this.loading.table = true
      const res = await getProductList()
      this.loading.table = false
      if (res.status !== 200) {
        return this.$message.error(res.msg)
      }
      this.list = res.data
    },
    handleDialog(row) {
      if (row) {
        this.status = 'edit'
        this.dataForm = { ...row }
      } else {
        this.status = 'create'
        this.dataForm = { ...initdataForm }
      }
      this.dataForm.productStatus = this.dataForm.productStatus === 0
      this.loading.formDialog = false
      this.visible.formDialog = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    async handleSure() {
      this.$refs['dataForm'].validate(async valid => {
        if (!valid) return this.$message.error('请检查类目信息')
        this.loading.formDialog = true
        const req = { ...this.dataForm }
        req.productStatus = req.productStatus ? 0 : 1
        let res
        if (this.status === 'edit') {
          res = await updateProduct(req)
        } else {
          res = await createProduct(req)
        }
        this.loading.formDialog = false
        if (res.status !== 200) {
          return this.$message.error(res.msg)
        }
        this.visible.formDialog = false
        this.getData()
      })
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          this.loading.table = true
          const res = await deleteProduct({ productId: row.productId })
          this.loading.table = false
          if (res.status !== 200) {
            return this.$message.error(res.msg)
          }
          this.getData()
        })
        .catch(() => {
          console.log('取消操作')
        })
    }
  }
}
</script>

