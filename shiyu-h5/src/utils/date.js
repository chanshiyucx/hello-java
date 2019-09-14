import { MathMul } from './formatMath'

// 日期转 string  2018-10-10
export function formatDateToString(date) {
  const y = date.getFullYear()
  const m = date.getMonth() + 1
  const d = date.getDate()
  return `${y}-${m < 10 ? '0' + m : m}-${d < 10 ? '0' + d : d}`
}
// 日期转string  2018-10-10 00:00:00
export function formatDateTimeToString(date) {
  if (typeof date === 'string') return date
  const y = date.getFullYear()
  const m = date.getMonth() + 1
  const d = date.getDate()
  const hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
  const minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  const second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
  return `${y}-${m < 10 ? '0' + m : m}-${d < 10 ? '0' + d : d} ${hour}:${minute}:${second}`
}

// 日期转string   00:00:00
export function formatDateMinuteToString(date) {
  const hour = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
  const minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
  const second = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()

  return `${hour}:${minute}:${second}`
}

// 补0操作
function getzf(num) {
  if (parseInt(num) < 10) {
    num = '0' + num
  }
  return num
}

export const formatTime = str => {
  var oDate = new Date(str),
    oYear = oDate.getFullYear(),
    oMonth = oDate.getMonth() + 1,
    oDay = oDate.getDate(),
    oHour = oDate.getHours(),
    oMin = oDate.getMinutes(),
    oSen = oDate.getSeconds(),
    oTime =
      oYear +
      '-' +
      getzf(oMonth) +
      '-' +
      getzf(oDay) +
      ' ' +
      getzf(oHour) +
      ':' +
      getzf(oMin) +
      ':' +
      getzf(oSen) //最后拼接时间
  return oTime
}

/**
 *
 * @param {*} time1
 * @param {*} time2
 * 首先判断两条消息间隔是否在五分钟以上，然后判断最后一条消息与当前时间的显示
 * t < 5分钟               return ''
 * t >= 5分钟              return '13:23';//(时:分)
 * t > 1天 && t < 2天      return '昨天 13:23';
 * t >= 2天 && t < 1周     return '2018/9/10 :13:23';
 */
export const getFormatTime = (time1, time2) => {
  //5分钟，1天和一周的毫秒值
  let fiveMin = MathMul(MathMul(1000, 60), 5)
  let day = MathMul(MathMul(MathMul(1000, 60), 60), 24)
  let week = MathMul(MathMul(MathMul(MathMul(1000, 60), 60), 24), 7)
  let now = Date.now()
  let weekArr = ['一', '二', '三', '四', '五', '六', '七']
  let r = formatTime(time2)
  //当前时间
  if (time1 == 0) {
    return r.substring(r.length - 8, r.length - 3)
  }
  //两个时间间隔小于5分钟返回空
  if (time2 - time1 < fiveMin) {
    return ''
  }
  //当天的判断
  if (now - time2 >= fiveMin && now - time2 < day) {
    return r.substring(r.length - 8, r.length - 3)
  }
  //昨天
  if (now - time2 >= day && now - time2 < MathMul(day, 2)) {
    return '昨天 ' + r.substring(r.length - 8, r.length - 3)
  }
  //一周内
  if (now - time2 >= MathMul(day, 2) && now - time2 < week) {
    return '周' + weekArr[new Date(time2).getDay()] + ' ' + r.substring(r.length - 8, r.length - 3)
  }
}
