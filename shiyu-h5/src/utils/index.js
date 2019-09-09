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
