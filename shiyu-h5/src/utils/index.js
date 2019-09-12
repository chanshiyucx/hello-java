/**
 * @param {string} key
 * @param {string} value
 */
export const localSave = (key, value) => {
  localStorage.setItem(key, value)
}

/**
 * @param {string} key
 * @param {string} defaultValue
 */
export const localRead = (key, defaultValue = '') => {
  return localStorage.getItem(key) || defaultValue
}

/**
 * 下载图片
 * @param {*} url
 */
export const downloadImg = (url, name) => {
  const a = document.createElement('a')
  a.href = url
  a.download = name
  a.click()
}

/**
 * 获取文件扩展名
 * @param {*} filename
 */
export const getFileExt = filename => {
  return filename.slice(((filename.lastIndexOf('.') - 1) >>> 0) + 2)
}

/**
 * 表单生成器
 * @param {*} obj 待转换的数据对象
 */
export const formBuilder = obj => {
  const formData = new FormData()
  Object.keys(obj).forEach(k => {
    formData.append(k, obj[k])
  })
  return formData
}
