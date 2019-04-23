<template>
  <div>
   <el-table height="360" style="width: 100%;" highlight-current-row
      :stripe="true"
      size="mini"
      :data="tableData"
      header-row-class-name="tableHeader"
      @row-dblclick="rowDblClick">
      <el-table-column prop="userName" label="姓名" sortable width="180"/>
      <el-table-column prop="password" label="密码" sortable width="250"/>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button @click="rowEdit(scope.row)" size="mini">编辑</el-button>
          <el-button @click="rowDel(scope.row)" size="mini" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="pagiSizeChange"
      @current-change="pagiCurrentChange"
      :current-page="1"
      :page-sizes="[20, 100, 500]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
</template>
<script>
import {allUsers, deleteUser} from '@/service/user'
import { Loading, MessageBox } from 'element-ui'
export default {
  data () {
    return {
      page: 0,
      pageSize: 20,
      total: 0,
      tableData: null
    }
  },
  methods: {
    loadData () {
      var params = {
        page: this.page,
        size: this.pageSize
      }
      let dataLoading = Loading.service({fullscreen: false})
      allUsers(params).then(res => {
        var data = res.data
        if (data.code === 200) {
          var content = data.data
          this.total = content.totalElements
          this.tableData = content.content
        } else {
          MessageBox.alert(data.errMsg)
        }
      }).catch(res => {
        MessageBox.alert(res.response.status + ' ' + res.response.statusText)
      }).finally(() => {
        dataLoading.close()
      })
    },
    /* 行处理 */
    rowDblClick (row, event) {
      console.log(row)
      console.log(event)
    },
    rowEdit (row) {
      console.log(row)
    },
    rowDel (row) {
      MessageBox.confirm('此操作将删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        let delLoading = Loading.service({fullscreen: false})
        deleteUser(row.id).then(res => {
          var data = res.data
          if (data.code === 200) {
            this.loadData()
          } else {
            MessageBox.alert(data.errMsg)
          }
        }).catch(res => {
          MessageBox.alert(res.response.status + ' ' + res.response.statusText)
        }).finally(() => {
          delLoading.close()
        })
      }).catch(() => {})
    },
    /* 分页处理 */
    pagiSizeChange (size) {
      this.pageSize = size
      this.loadData()
    },
    pagiCurrentChange (currentPage) {
      this.page = currentPage
      this.loadData()
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style>
.tableHeader{
  height: 50px !important;
}
</style>
