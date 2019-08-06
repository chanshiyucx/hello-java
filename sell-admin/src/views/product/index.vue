<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-plus" @click="handleDialog()">新增商品</el-button>
    </div>

    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column prop="productName" label="名称" align="center" min-width="150px" />
      <el-table-column prop="productDescription" label="描述" align="center" min-width="150px" />
      <el-table-column prop="productIcon" label="小图" align="center" min-width="150px" />
      <el-table-column prop="productPrice" label="单价" align="center" min-width="100px" />
      <el-table-column prop="productStock" label="库存" align="center" min-width="100px" />
      <el-table-column label="类目" align="center" min-width="100px">
        <template slot-scope="scope">
          <span>{{ getCategoryName(scope.row.categoryId) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="productStock" label="状态" align="center" min-width="100px">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.productStatus === 0 ? 'success' : 'danger'"
          >{{ scope.row.productStatus === 0 ? '上架' : '下架' }}</el-tag>
        </template>
      </el-table-column>
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
        <el-form-item label="类目" prop="categoryId">
          <el-select v-model="dataForm.categoryId" placeholder="请选择类目">
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
  categoryId: ''
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
        productPrice: [
          { required: true, message: '请输入单价', trigger: 'change' },
          {
            validator(rule, value, callback) {
              if (isNaN(Number(value))) {
                callback('商品单价必须为数值类型')
              } else {
                callback()
              }
            }
          }
        ],
        productStock: [
          { required: true, message: '请输入库存', trigger: 'change' },
          {
            validator(rule, value, callback) {
              const num = Number(value)
              if (isNaN(num) || num !== ~~num) {
                callback('商品库存必须为整数')
              } else {
                callback()
              }
            }
          }
        ],
        productStatus: [{ required: true, message: '请输入状态', trigger: 'change' }],
        productDescription: [{ required: true, message: '请输入描述', trigger: 'change' }],
        productIcon: [{ required: true, message: '请输入小图', trigger: 'change' }],
        categoryId: [{ required: true, message: '请输入类目', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getCategoryList()
    this.getData()
  },
  methods: {
    async getCategoryList() {
      try {
        this.loading.table = true
        const res = await getCategoryList()
        this.loading.table = false
        this.categoryList = res.data
      } catch (error) {
        this.loading.table = false
      }
    },
    async getData() {
      try {
        this.loading.table = true
        const res = await getProductList()
        this.loading.table = false
        this.list = res.data
      } catch (error) {
        this.loading.table = false
      }
    },
    getCategoryName(categoryId) {
      if (!this.categoryList) return categoryId
      const category = this.categoryList.find(o => o.categoryId === categoryId)
      return category ? category.categoryName : categoryId
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
        try {
          this.loading.formDialog = true
          const req = { ...this.dataForm }
          req.productStatus = req.productStatus ? 0 : 1
          if (this.status === 'edit') {
            await updateProduct(req)
          } else {
            await createProduct(req)
          }
          this.loading.formDialog = false
          this.visible.formDialog = false
          this.getData()
        } catch (error) {
          this.loading.formDialog = false
        }
      })
    },
    handleDelete(row) {
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          try {
            this.loading.table = true
            await deleteProduct({ productId: row.productId })
            this.loading.table = false
            this.getData()
          } catch (error) {
            this.loading.table = false
          }
        })
        .catch(() => {
          console.log('取消操作')
        })
    }
  }
}
</script>

