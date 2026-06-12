/**
 * 个人中心页
 * 展示用户信息，提供退出、修改密码、预约记录查询、管理员功能入口
 */
Page({
  onLoad() {
    const user = wx.getStorageSync('userInfo');
    this.setData({
      userInfo: user,
      name: user.name,
      is_admin: user.is_admin
    });
  },

  /** 退出登录：清除本地存储 → 回到登录页 */
  onLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.removeStorageSync('userInfo');
          wx.showToast({ title: '已退出登录', icon: 'success' });
          setTimeout(() => {
            wx.reLaunch({ url: '/pages/login/login' });
          }, 800);
        }
      }
    });
  },

  /** 跳转修改密码页 */
  goChangePassword() {
    wx.navigateTo({ url: '/pages/changepassword/changepassword' });
  },

  /** 跳转当前预约页 */
  CurrentReservation() {
    wx.navigateTo({ url: '/pages/CurrentReservation/CurrentReservation' });
  },

  /** 跳转历史预约页 */
  HistoricalReservation() {
    wx.navigateTo({ url: '/pages/HistoricalReservation/HistoricalReservation' });
  },

  /** 管理员——设备管理入口（普通用户被拦） */
  goDeviceManage() {
    if (this.data.is_admin != 1) {
      wx.showToast({ title: '权限不足', icon: 'none' });
      return;
    }
    wx.navigateTo({ url: '/pages/DeviceManage/DeviceManage' });
  },

  /** 管理员——用户管理入口（普通用户被拦） */
  goUserManage() {
    if (this.data.is_admin != 1) {
      wx.showToast({ title: '权限不足', icon: 'none' });
      return;
    }
    wx.navigateTo({ url: '/pages/UserManage/UserManage' });
  }
})