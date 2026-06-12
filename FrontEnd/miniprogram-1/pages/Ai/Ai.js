/**
 * AI 助手页
 * 用户发送自然语言 → 后端 DeepSeek 解析意图 → 返回格式化结果
 * 支持：查设备详情、查空闲设备、查预约记录、普通聊天
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'

Page({
  data: {
    inputText: '',
    messages: [],     // 聊天消息列表 [{role, content}]
    scrollTop: 0
  },

  onLoad() {
    const user = wx.getStorageSync('userInfo');
    const username = user?.name || '用户';
    const welcomeMsg = {
      role: 'assistant',
      content: `${username}，您好，我是AI助手”预约小序”，请问有什么可以帮助您？`
    };
    this.setData({
      userInfo: user,
      username: username,
      userid: user?.id,
      messages: [welcomeMsg],
      scrollTop: 1000
    });
  },

  /** 输入框双向绑定 */
  onInput(e) {
    this.setData({ inputText: e.detail.value })
  },

  /**
   * 发送消息流程：
   * 1. 将用户消息追加到列表
   * 2. 插入占位提示
   * 3. 调用 /ai/chat 获取回复
   * 4. 替换占位为实际回复
   */
  sendMessage() {
    const text = this.data.inputText.trim();
    if (!text) return;

    let list = this.data.messages;

    // 用户消息
    list.push({ role: 'user', content: text });

    // AI占位
    list.push({ role: 'assistant', content: 'AI助手正在思考...' });

    this.setData({
      messages: list,
      inputText: '',
      scrollTop: 99999
    });

    request({
      url: config.baseUrl + '/ai/chat',
      method: 'POST',
      data: {
        message: text,
        userid: this.data.userid
      },
      success: (res) => {
        console.log("返回数据", res);
        let reply = res.data;

        // 如果是对象则转字符串
        if (typeof reply === 'object') {
          reply = JSON.stringify(reply);
        }

        let list = this.data.messages;
        // 替换最后一条 AI 占位消息
        list[list.length - 1].content = reply;

        this.setData({ messages: list, scrollTop: 99999 });
      },
      fail: (err) => {
        let list = this.data.messages;
        list[list.length - 1].content = '网络异常，请稍后再试';
        this.setData({ messages: list });
      }
    });
  }

})