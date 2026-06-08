Page({

  data: {
    iusername: '',
    ipassword: '',
    checked: false,
    canSubmit: false,
    eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E7%9D%81%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
  },

  onSubmit() {
    const { iusername, ipassword, checked } = this.data;
    if (!checked) {
      wx.showToast({
        title: '请先同意用户协议',
        icon: 'none'
      });
      return;
    }
    wx.request({
      url: config.baseUrl + '/user/login',
      method: 'GET',
      data: {
        id: iusername,
        password: ipassword
      },
      success: (res) => {
        console.log("登录结果：", res.data);
        if (res.data) {
          wx.setStorageSync('userInfo',res.data);
         wx.showToast({
  title: '正在登陆',
  icon: 'loading',
  duration: 1500
});
setTimeout(() => {
  wx.switchTab({
    url: '/pages/index/index'
  });
}, 1500);
        } else {
          wx.showToast({
            title: '账号或密码错误',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.log("请求失败：", err);
      }
    });
  },

  // 输入
  onUsernameInput(e) {
    // console.log("当前输入名字为：", e.detail.value);
    this.setData({
      iusername: e.detail.value
    });
  },
  
  onPasswordInput(e) {
    // console.log("当前输入密码为:", e.detail.value);
    this.setData({
      ipassword: e.detail.value
    });
  },

  onPasswordFocus: function () {
    // 密码框获得焦点时，更换为遮眼图片
    this.setData({
      eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E9%81%AE%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
    });
  },

  onPasswordBlur: function () {
    // 密码框失去焦点时，更换为睁眼图片
    this.setData({
      eyeImage: 'https://bishe-1415871826.cos.ap-shanghai.myqcloud.com/%E7%BB%93%E6%9E%84%E5%9B%BE%E7%89%87/loginimage/%E7%9D%81%E7%9C%BC%E5%9B%BE%E7%89%87%EF%BC%88%E8%93%9D%EF%BC%89.jpg'
    });
  },

  showError: function (message) {
    wx.showToast({
      title: message,
      icon: 'none',
      duration: 1000
    });
  },

  onCheck(e) {
    this.setData({
      checked:!this.data.checked
    })

    }
    
});