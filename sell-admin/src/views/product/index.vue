<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-plus" @click="handleDialog()">新增分类</el-button>
    </div>

    <el-table v-loading="loading.table" :data="list" border fit highlight-current-row stripe>
      <el-table-column prop="id" label="ID" align="center" min-width="150px" />
      <el-table-column prop="name" label="名称" align="center" min-width="150px" />
      <el-table-column prop="type" label="类型" align="center" min-width="150px" />
      <el-table-column label="操作" align="center" min-width="150px">
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleDialog(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :title="`${status === 'edit' ? '编辑' : '新增'}分类`"
      :visible.sync="visible.editDialog"
      :close-on-click-modal="false"
      width="500px"
    >
      <el-form
        ref="editForm"
        :rules="rules"
        :model="editForm"
        label-position="left"
        label-width="100px"
        style="width: 400px;"
      >
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="editForm.name" />
        </el-form-item>
        <el-form-item label="分类类型" prop="type">
          <el-input v-model="editForm.type" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="visible.editDialog = false">取消</el-button>
        <el-button type="primary" :loading="loading.editDialog" @click="handleSure">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getProductList } from '@/api/product'

const initEditForm = {
  name: '',
  type: ''
}

export default {
  data() {
    return {
      loading: {
        table: false,
        editDialog: false
      },
      visible: {
        editDialog: false
      },
      status: 'edit',
      list: null,
      editForm: { ...initEditForm },
      rules: {
        name: [{ required: true, message: '请输入分类名称', trigger: 'change' }],
        type: [{ required: true, message: '请输入分类类型', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getData()
  },
  methods: {
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
      this.visible.editDialog = true
      if (row) {
        this.status = 'edit'
        this.editForm = { ...row }
      } else {
        this.status = 'create'
        this.editForm = { ...initEditForm }
      }
      this.$nextTick(() => {
        this.$refs['editForm'].clearValidate()
      })
    },
    async handleSure() {
      // this.loading.table = true
      // const res = await createCategory(this.editForm)
      // this.loading.table = false
      // if (res.status !== 200) {
      //   return this.$message.error(res.msg)
      // }
      // this.list = res.data
    },
    handleDelete() {}
  }
}
</script>

