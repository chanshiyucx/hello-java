export { CMD, TYPES } from './const'

/**
 * =====================================================
 *             IM 只做消息收发，不处理业务逻辑
 * =====================================================
 */
export default class IM {
  constructor(options) {
    // 初始化配置
    this.options = options

    // 内部变量
    this.socket = null // websocket 实例
    this.pingTimeout = 8000 // 发送心跳包间隔
    this.pingTimer = null // 心跳包定时器
    this.pongTimeout = 20000 // 最长未接收消息的间隔
    this.pongTimer = null // 接收消息定时器
    this.reconnectTimeout = 5000 // 每次重连间隔
    this.reconnectLimit = 10 // 最大重连次数
    this.reconnectCount = 0 // 当前的重连次数
    this.reconnectTimer = null // 重连定时器
    this.forbidReconnect = false // 禁止重连
    this.lockReconnect = false // 锁定重连
    this.lockReconnectTask = false // 锁定重连任务队列
    this.handers = {} // 事件广播

    // Let's go
    this._init()
  }

  /**
   * ===================================================
   *             Websocket Event 【内部模块】
   * ===================================================
   */

  // 创建 socket 实例
  _init() {
    this.socket = new WebSocket(this.options.url)
    this.socket.onclose = () => this._onclose()
    this.socket.onerror = () => this._onerror()
    this.socket.onopen = () => this._onopen()
    this.socket.onmessage = event => this._onmessage(event)
  }

  // 连接关闭
  _onclose() {
    console.log('====== 连接关闭')
    this._readyReconnect()
    this.options.ondisconnect()
  }

  // 连接错误
  _onerror() {
    console.log('====== 连接错误')
    this._readyReconnect()
    this.options.onerror()
  }

  // 连接成功
  _onopen() {
    console.log('====== 连接成功')
    this._resetStatus()
    this.options.onconnect()
  }

  // 接收消息
  _onmessage(event) {
    // console.log('====== 接收消息', event.data)
    this.handleResponseEvent(JSON.parse(event.data))
    // 超时定时器
    clearTimeout(this.pongTimer)
    this.pongTimer = setTimeout(() => {
      this._readyReconnect()
    }, this.pongTimeout)
  }

  /**
   * ================================================
   *               心跳与重连【内部模块】
   * ================================================
   */

  // 清空所有定时器
  _clearAllTimer() {
    clearTimeout(this.pingTimer)
    clearTimeout(this.pongTimer)
    clearTimeout(this.reconnectTimer)
    this.pingTimer = this.pongTimer = this.reconnectTimer = null
  }

  // 准备重连
  _readyReconnect() {
    // 避免循环重连，当一个重连任务进行时，不进行重连
    if (this.lockReconnectTask) return
    this.lockReconnectTask = true
    this._clearAllTimer()
    this._reconnect()
  }

  // 重连
  _reconnect() {
    if (this.forbidReconnect) return
    if (this.lockReconnect) return
    if (this.reconnectCount > this.reconnectLimit) return

    console.log('====== 重连', this.reconnectCount)
    this.options.onreconnect()
    this.lockReconnect = true
    this.reconnectCount += 1
    this._init()
    this.reconnectTimer = setTimeout(() => {
      this.lockReconnect = false
      this._reconnect()
    }, this.reconnectTimeout)
  }

  // 连接成功
  _resetStatus() {
    this._clearAllTimer()
    this._heartBeat(true)
    this.reconnectCount = 0
    this.lockReconnect = false
    this.lockReconnectTask = false
  }

  // 发送心跳包
  _heartBeat(immediate) {
    this.pingTimer = setTimeout(
      () => {
        this._sendMessage(this.options.heartBeatMsg)
        this._heartBeat()
      },
      immediate ? 1000 : this.pingTimeout
    )
  }

  // 发送消息
  _sendMessage(msg) {
    // 魔法常量
    const WSENUM = {
      VERSION: 1, // 协议版本
      SERIALIZE: 1 // 序列化 json = 1
    }
    if (this.socket.readyState === WebSocket.OPEN) {
      msg.version = WSENUM.VERSION
      msg.algorithm = WSENUM.SERIALIZE
      this.socket.send(JSON.stringify(msg))
    }
  }

  /**
   * ================================================
   *                事件广播【外部模块】
   * ================================================
   */
  on(eventName, fn) {
    if (!eventName) {
      throw new Error('事件名无效')
    }
    if (!(typeof fn === 'function')) {
      throw new Error('必须提供事件函数')
    }
    this.handers[eventName] = fn
  }

  emit(eventName, data) {
    if (!eventName) {
      throw new Error('事件名无效')
    }
    const handle = this.handers[eventName]
    handle && handle(data)
  }

  /**
   * ============================================================
   *    统一处理内外部消息通信【外部模块】
   *    IM 只暴露给外部三个方法可使用
   *    handleRequestEvent   : 数据请求
   *    handleResponseEvent  : 数据接收
   *    closeSocket          : 关闭连接
   * ============================================================
   */

  // 处理外部请求命令
  handleRequestEvent(msg) {
    console.log('%c发送消息', 'color:#F2C047;', msg)
    this._sendMessage(msg)
  }

  // 处理消息回调给外部
  handleResponseEvent(msg) {
    // 过滤心跳消息
    if (msg.command === 2) return
    console.log('%c接收消息', 'color:#cd5da0;', msg)
    // 返回信息给工作台
    this.options.handleResponseEvent(msg.command, msg)
  }

  // 手动关闭连接
  closeSocket() {
    // 如果手动关闭连接，不再重连
    this._clearAllTimer()
    this.forbidReconnect = true
    this.socket && this.socket.close()
    this.socket = null
  }
}
