// pages/my/my.js
Page({

  /**
   * 页面的初始数据
   */
  onLoad(){
    const user = wx.getStorageSync('userInfo')
    this.setData({
      userInfo: user,
      name: user.name,
      is_admin: user.is_admin
    })
  },
  onLogout(){
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success:(res)=>{
        if(res.confirm){
          // 清除登录状态
          wx.removeStorageSync('userInfo')
          wx.showToast({
            title: '已退出登录',
            icon: 'success'
          })
          // 跳转到登录页
          setTimeout(()=>{
            wx.reLaunch({
              url: '/pages/login/login'
            })
          },800)
        }
      }
    })
  },

  goChangePassword(){
    wx.navigateTo({
      url:'/pages/changepassword/changepassword'
    })
  },

  CurrentReservation(){
    wx.navigateTo({
      url: '/pages/CurrentReservation/CurrentReservation'  
    })
  },

  HistoricalReservation(){
    wx.navigateTo({
      url:'/pages/HistoricalReservation/HistoricalReservation'
    })
  },


  // 设备管理
goDeviceManage(){
  // 普通用户
  if(this.data.is_admin != 1){
    wx.showToast({
      title:'权限不足',
      icon:'none'
    });
    return;
  }
  // 管理员
  wx.navigateTo({
    url:'/pages/DeviceManage/DeviceManage'
  });
},
goUserManage(){
  // 普通用户
  if(this.data.is_admin != 1){
    wx.showToast({
      title:'权限不足',
      icon:'none'
    });
    return;
  }
  // 管理员
  wx.navigateTo({
    url:'/pages/UserManage/UserManage'
  });
}
})