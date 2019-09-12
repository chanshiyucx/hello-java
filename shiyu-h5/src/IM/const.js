/**
 * 消息命令字
 */
export const CMD = {
  HEARTBEAT_REQUEST: 1, // 心跳请求
  HEARTBEAT_RESPONSE: 2, // 心跳响应
  LOGIN_REQUEST: 3, // 登录请求
  LOGIN_RESPONSE: 4, // 登录响应
  MESSAGE_REQUEST: 5, // 发送消息
  MESSAGE_RESPONSE: 6 // 响应消息
}

/**
 * 消息类型
 */
export const TYPES = {
  HINT: 1, // 系统提示
  TEXT: 2, // 文本消息
  PICTURE: 3, // 图片消息
  VOICE: 4, // 语音消息
  VEDIO: 5 // 视频消息
}
