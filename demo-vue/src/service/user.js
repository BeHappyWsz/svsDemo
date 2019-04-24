/* 车辆绑定+车辆状态页面api */
import axios from 'axios'
import Global from '../../utils/global'

/* 所有用户 */
export const allUsers = (params) => {
  return axios.post(Global.getContextPath() + '/user/page', params)
}

/* 所有用户 */
export const deleteUser = (ids) => {
  return axios.delete(Global.getContextPath() + '/user?ids=' + ids)
}
