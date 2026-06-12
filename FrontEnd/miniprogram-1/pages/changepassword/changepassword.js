/**
 * 修改密码页
 * 验证旧密码 → 设置新密码
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'
Page({
  data: {
    oldPwd: '',
    newPwd: ''
  },

  /** 旧密码输入绑定 */
  oldPwdInput(e) {
    this.setData({ oldPwd: e.detail.value })
  },

  /** 新密码输入绑定 */
  newPwdInput(e) {
    this.setData({ newPwd: e.detail.value })
  },

  /** 提交修改：验证旧密码 → 更新为新密码 */
  submit() {
    const user = wx.getStorageSync('userInfo');
    request({
      url: config.baseUrl + '/user/updatePassword',
      method: 'POST',
      data: {
        id: user.id,
        oldPassword: this.data.oldPwd,
        newPassword: this.data.newPwd
      },
      success: (res) => {
        if (res.data) {
          wx.showToast({ title: '修改成功' });
          wx.navigateBack();
        } else {
          wx.showToast({ title: '旧密码错误', icon: 'none' });
        }
      }
    });
  }
})