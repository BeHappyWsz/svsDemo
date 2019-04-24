<template>
  <div>
    <!-- 搜索功能 -->
    <el-input v-model="searchName" style="width:200px;" size="mini" :clearable="true" placeholder="请输入内容">
      <el-button slot="append" icon="el-icon-search" @click="doSearchName"></el-button>
    </el-input>
    <!-- 删除按钮 -->
    <el-button @click="deleteUsers" :disabled="delDisabled" type="danger">删除</el-button>
    <!-- table -->
    <el-table height="340" style="width: 100%;" highlight-current-row
      ref="userTable"
      border
      :tripe="true"
      :data="tableData"
      header-row-class-name="headerRow"
      @selection-change="checkUser"
      @row-dblclick="rowDblClick">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="userName" label="姓名" align="center" header-align="center" sortable width="180"/>
      <el-table-column prop="password" label="密码" align="center" header-align="center" sortable width="250"/>
      <el-table-column align="center" header-align="center" label="操作">
        <template slot-scope="scope">
          <el-button @click="rowEdit(scope.row)" size="mini">编辑</el-button>
          <el-button @click="rowDel(scope.row)" size="mini" type="danger">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
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
      tableData: null,
      searchName: '',
      delDisabled: true,
      checkData: null
    }
  },
  methods: {
    loadData () {
      var params = {
        page: this.page,
        size: this.pageSize,
        userName: this.searchName
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
    deleteData (ids) {
      MessageBox.confirm('此操作将删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        let delLoading = Loading.service({fullscreen: false})
        deleteUser(ids).then(res => {
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
    /* 行处理 */
    rowDblClick (row, event) {
      console.log(row)
      console.log(event)
    },
    rowEdit (row) {
      console.log(row)
    },
    rowDel (row) {
      this.deleteData(row.id)
    },
    /* 分页处理 */
    pagiSizeChange (size) {
      this.pageSize = size
      this.loadData()
    },
    pagiCurrentChange (currentPage) {
      this.page = currentPage
      this.loadData()
    },
    /* 搜索操作 */
    doSearchName () {
      this.loadData()
    },
    /* 勾选与删除 */
    checkUser (val) {
      if (val.length === 0) {
        this.delDisabled = true
      } else {
        this.delDisabled = false
      }
      this.checkData = val
    },
    deleteUsers () {
      var ids = []
      for (var user of this.checkData) {
        ids.push(user.id)
      }
      this.deleteData(ids)
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style>
.headerRow th{
  height: 50px !important;
}
</style>
