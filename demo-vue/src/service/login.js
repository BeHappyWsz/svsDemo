/* 车辆绑定+车辆状态页面api */
import axios from 'axios'
import Global from '../../utils/global'

/* 用户登录 */
export const userLogin = (username, password) => {
  return axios.get(Global.getContextPath() + '/user/login?username=' + username + '&password=' + password)
}
