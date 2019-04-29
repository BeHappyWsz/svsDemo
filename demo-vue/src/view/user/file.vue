<template>
  <div>
    <input type="file" ref="myfile">
    <el-button @click="importData" type="success" size="mini" icon="el-icon-upload2">上传文件</el-button>
  </div>
</template>
<script>
import { Loading, MessageBox } from 'element-ui'
import {uploadFile} from '@/service/file'
export default{
  name: 'file',
  methods: {
    importData () {
      let myfile = this.$refs.myfile
      if (myfile.files[0] === null || myfile.files[0] === undefined) {
        MessageBox.alert('请先选择文件')
        return
      }
      let loading = Loading.service({fullscreen: false})
      uploadFile(myfile).then(res => {
        var data = res.data
        if (data.code !== 200) {
          MessageBox.alert(data.errMsg)
        } else {
          this.$message('成功')
        }
      }).catch(res => {
        MessageBox.alert(res.response.status + ' ' + res.response.statusText)
      }).finally(() => {
        loading.close()
      })
    }
  }
}
</script>
<style scoped>

</style>
