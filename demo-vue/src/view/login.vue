<template>
  <div style="width:100%;height:100%;">
    <el-form class="loginForm" ref="loginForm" :model="loginForm" status-icon :rules="rules2">
      <el-form-item label="账号" prop="userName">
        <el-input ref="loginName" type="test" v-model="loginForm.userName" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input ref="loginPass" type="password" v-model="loginForm.password" auto-complete="off"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('loginForm')">提交</el-button>
        <el-button @click="resetForm('loginForm')">重置</el-button>
        <el-button @click="resetForm('loginForm')">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import {userLogin} from '@/service/login'
export default {
  name: 'login',
  data () {
    var validateUserName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('账户不能为空'))
      } else {
        callback()
      }
    }
    var validatePassword = (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入密码'))
      } else {
        callback()
      }
    }
    return {
      loginForm: {
        userName: '',
        password: ''
      },
      rules2: {
        userName: [
          { validator: validateUserName, trigger: 'blur' }
        ],
        password: [
          { validator: validatePassword, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    submitForm (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userLogin(this.$refs.loginName.value.trim(),
            this.$refs.loginPass.value.trim()).then(res => {
            var data = res.data
            if (data.code === 200) {
              this.$store.state.user = data.data
            } else {
              alert(data.errMsg)
            }
          }).catch(res => {

          }).finally(() => {

          })
        } else {
          return false
        }
      })
    },
    resetForm (formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>

<style scoped>
.loginForm{
  width: 20rem;
  margin: 5rem auto;
  padding: 1rem;
  background-color: #e7e7e7;
  border: 1px solid #e7e7e7;
}
</style>
