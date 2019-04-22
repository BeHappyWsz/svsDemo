/* 车辆绑定+车辆状态页面api */
import axios from 'axios'
import Global from '../../utils/global'

/* 用户登录 */
export const userLogin = (loginParams) => {
  return axios.get(Global.getContextPath() + '/user/login', {params: loginParams})
}

/* 用户注册 */
export const userRegister = (params) => {
  let param = new URLSearchParams()
  param.append('userName', params.userName)
  param.append('password', params.password)
  return axios.post(Global.getContextPath() + '/user', param)
}
