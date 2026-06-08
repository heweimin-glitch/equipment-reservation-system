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
      url: 'http://10.69.174.110:8080/user/login',
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

  // onCheck(e) {
  //   this.setData({
  //     checked: e.detail.value
  //   });
  // },
  onCheck(e) {
    this.setData({
      checked:!this.data.checked
    })

    }
    

  



  
  // 状态控制
  // updateState() {
  //   const { username, password, confirmPassword, checked, mode } = this.data;

  //   let valid = username && password && checked;

  //   if (mode === 'register') {
  //     valid = valid && confirmPassword && password === confirmPassword;
  //   }

  //   this.setData({
  //     canSubmit: valid
  //   });
  // },

  // 提交
  // onSubmit() {
  //   // 获取data中的checked值
  //   console.log('协议同意状态:', this.data.checked);
  //   // 获取完整的表单数据
  //   const {
  //     username,
  //     password,
  //     checked,
  //     ipassword,
  //     iusername
  //   } = this.data;
  //   // 检查表单是否完整
  //   if (!username || !password) {
  //     wx.showToast({
  //       title: '请填写用户名和密码',
  //       icon: 'none'
  //     });
  //     return;
  //   }

  //   // 检查协议是否同意
  //   if (!checked) {
  //     wx.showToast({
  //       title: '请先同意用户协议',
  //       icon: 'none'
  //     });
  //     return;
  //   }


  //   // if (ipassword != password || iusername != username) {
  //   //   wx.showToast({
  //   //     title: '用户名或密码错误',
  //   //     icon: 'none'
  //   //   });
  //   //   return;
  //   // }
  //   // 表单验证通过，开始登录


  //   wx.showToast({
  //     title: '登录中...',
  //     icon: 'loading'
  //   });

  //   // 延迟跳转，让Toast显示完整
  //   setTimeout(() => {
  //     wx.switchTab({
  //       url: '/pages/index/index',
  //     });
  //   }, 800);
  // }

});