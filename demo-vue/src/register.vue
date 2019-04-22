<template>
  <div style="width:100%;height:100%;">
    <el-form class="regiForm" ref="regiForm" :model="regiForm" status-icon :rules="rules2">
      <el-form-item label="账号" prop="userName">
        <el-input ref="regiName" type="test" v-model="regiForm.userName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="regiPass">
        <el-input ref="regiPass" type="password" v-model="regiForm.regiPass" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input ref="checkPass" type="password" v-model="regiForm.checkPass" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('regiForm')">注册</el-button>
        <el-button @click="toLogin()">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {userRegister} from '@/service/login'
import { Loading } from 'element-ui'
export default {
  name: 'register',
  data () {
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账户不能为空'))
      } else {
        callback()
      }
    }
    var validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.regiForm.checkPass !== '') {
          this.$refs.regiForm.validateField('checkPass')
        }
        callback()
      }
    }
    var validateCheckPass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.regiForm.regiPass) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      regiForm: {
        userName: '',
        regiPass: '',
        checkPass: ''
      },
      rules2: {
        userName: [
          { validator: validateUserName, trigger: 'blur' }
        ],
        regiPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validateCheckPass, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          let loadingInstance = Loading.service({ fullscreen: false })
          var params = {
            userName: this.$refs.regiName.value.trim(),
            password: this.$refs.regiPass.value.trim()
          }
          userRegister(params).then(res => {
            var data = res.data
            if (data.code === 200) {
              this.$store.state.user = data.data
              this.$router.push({path: '/index'})
            } else {
              this.$message(data.errMsg)
            }
          }).catch(res => {

          }).finally(() => {
            loadingInstance.close()
          })
        } else {
          return false
        }
      })
    },
    toLogin () {
      this.$router.push({path: '/login'})
    }
  }
}
</script>

<style scoped>
.regiForm{
  width: 20rem;
  margin: 5rem auto;
  padding: 1rem;
  background-color: #e7e7e7;
  border: 1px solid #e7e7e7;
}
</style>
