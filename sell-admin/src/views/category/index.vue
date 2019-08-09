<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-plus" @click="handleDialog()">新增分类</el-button>
    </div>

    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column prop="categoryId" label="ID" align="center" min-width="150px" />
      <el-table-column prop="categoryName" label="类目名称" align="center" min-width="150px" />
      <el-table-column prop="createTime" label="创建时间" align="center" min-width="150px" />
      <el-table-column prop="updateTime" label="更新时间" align="center" min-width="150px" />
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
        <el-form-item label="类目名称" prop="categoryName">
          <el-input v-model="dataForm.categoryName" />
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
import { getCategoryList, createCategory, updateCategory, deleteCategory } from '@/api/category'

const initdataForm = {
  categoryName: ''
}

export default {
  name: 'Category',
  data() {
    return {
      loading: {
        table: false,
        formDialog: false
      },
      visible: {
        formDialog: false
      },
      list: null,
      status: 'edit',
      dataForm: { ...initdataForm },
      rules: {
        categoryName: [{ required: true, message: '请输入类目名称', trigger: 'change' }]
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
        const res = await getCategoryList()
        this.loading.table = false
        this.list = res.data
      } catch (error) {
        this.loading.table = false
      }
    },
    handleDialog(row) {
      if (row) {
        this.status = 'edit'
        this.dataForm = { ...row }
      } else {
        this.status = 'create'
        this.dataForm = { ...initdataForm }
      }
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
          if (this.status === 'edit') {
            await updateCategory(req)
          } else {
            await createCategory(req)
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
      this.$confirm('此操作将永久删除该类目, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          try {
            this.loading.table = true
            await deleteCategory({ categoryId: row.categoryId })
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

