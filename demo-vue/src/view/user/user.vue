<template>
  <section>
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;height:45px;">
      <el-form :inline="true" style="height:45px;">
        <el-form-item>
          <el-input v-model="searchName" :clearable="true" placeholder="请输入内容"></el-input>
        </el-form-item>
        <!-- 搜索功能 -->
        <el-form-item>
          <el-button type="primary" @click="doSearchName">查询</el-button>
        </el-form-item>
        <!-- 新增功能 -->
        <el-form-item>
          <el-button type="primary" @click="openAddForm">新增</el-button>
        </el-form-item>
        <!-- 删除按钮 -->
        <el-form-item>
          <el-button type="danger" @click="deleteUsers" :disabled="delDisabled">删除</el-button>
        </el-form-item>
      </el-form>
    </el-col>
    <!-- table -->
    <el-table height="340" style="width: 100%;" highlight-current-row
      ref="userTable"
      border
      :tripe="true"
      :data="tableData"
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
      :page-sizes="[20, 50, 100]"
      :page-size="pageSize"
      style="height:50px;"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
    <!--新增界面-->
    <el-dialog title="新增" center v-show="addFormVisible" :close-on-click-modal="false">
      <el-form ref="editForm">
        <el-form-item label="姓名">
          <el-input auto-complete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="addFormVisible = false">取消</el-button>
        <el-button type="primary">提交</el-button>
      </div>
    </el-dialog>
  </section>
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
      checkData: null,
      // 弹出窗口
      addFormVisible: false
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
    },
    /* 新增窗口 */
    openAddForm () {
      this.addFormVisible = true
    }
  },
  mounted () {
    this.loadData()
  }
}
</script>
<style scope>

</style>
