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
