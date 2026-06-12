/**
 * 登录页
 * 用户输入账号密码 → 后端验证 → 返回 JWT Token + 用户信息
 */
import config from '../../utils/config.js'

Page({
  data: {
    iusername: '',
    ipassword: '',
    checked: false,
    canSubmit: false,
    eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E7%9D%81%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
  },

  /** 提交登录表单 */
  onSubmit() {
    const { iusername, ipassword, checked } = this.data;
    if (!checked) {
      wx.showToast({ title: '请先同意用户协议', icon: 'none' });
      return;
    }
    wx.request({
      url: config.baseUrl + '/user/login',
      method: 'GET',
      data: { id: iusername, password: ipassword },
      success: (res) => {
        console.log("登录结果：", res.data);
        if (res.data && res.data.token) {
          // 存储 JWT Token
          wx.setStorageSync('token', res.data.token);
          // 存储用户信息
          wx.setStorageSync('userInfo', res.data.user);
          wx.showToast({ title: '正在登录', icon: 'loading', duration: 1500 });
          setTimeout(() => {
            wx.switchTab({ url: '/pages/index/index' });
          }, 1500);
        } else {
          wx.showToast({ title: '账号或密码错误', icon: 'none' });
        }
      },
      fail: (err) => {
        console.log("请求失败：", err);
      }
    });
  },

  /** 用户名输入绑定 */
  onUsernameInput(e) {
    this.setData({ iusername: e.detail.value });
  },

  /** 密码输入绑定 */
  onPasswordInput(e) {
    this.setData({ ipassword: e.detail.value });
  },

  /** 密码框聚焦 → 切换为闭眼图 */
  onPasswordFocus() {
    this.setData({
      eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E9%81%AE%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
    });
  },

  /** 密码框失焦 → 切换回睁眼图 */
  onPasswordBlur() {
    this.setData({
      eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E7%9D%81%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
    });
  },

  /** 错误提示 Toast */
  showError(message) {
    wx.showToast({ title: message, icon: 'none', duration: 1000 });
  },

  /** 用户协议勾选框 */
  onCheck() {
    this.setData({ checked: !this.data.checked });
  }

});
