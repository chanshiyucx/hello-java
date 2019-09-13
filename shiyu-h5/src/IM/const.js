/**
 * 消息命令字
 */
export const CMD = {
  HEART_BEAT: {
    // 心跳
    REQUEST: 1,
    RESPONSE: 2
  },
  LOGIN: {
    // 登录
    REQUEST: 3,
    RESPONSE: 4
  },
  CREATE_ROOM: {
    // 创建房间
    REQUEST: 5,
    RESPONSE: 6
  },
  SEND_MESSAGE: {
    // 发送消息
    REQUEST: 7,
    RESPONSE: 8
  },
  ACCEPT_MESSAGE: {
    // 接收消息
    RESPONSE: 9
  }
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
