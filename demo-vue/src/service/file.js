/* 文件管理 */
import axios from 'axios'
import Global from '../../utils/global'

/* 文件上传 */
export const uploadFile = (params) => {
  let files = params.files
  let file = files[0]
  var formData = new FormData()
  formData.append('file', file)
  return axios.post(Global.getContextPath() + '/file', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}
