Page({
  data:{
    oldPwd:'',
    newPwd:''
  },

  oldPwdInput(e){
    this.setData({
      oldPwd:e.detail.value
    })
  },

  newPwdInput(e){
    this.setData({
      newPwd:e.detail.value
    })
  },

  submit(){
    const user = wx.getStorageSync('userInfo')
    wx.request({
      url: config.baseUrl + '/user/updatePassword',
      method:'POST',
      data:{
        id:user.id,
        oldPassword:this.data.oldPwd,
        newPassword:this.data.newPwd
      },
      success:(res)=>{
        if(res.data){
          wx.showToast({
            title:'修改成功'
          })
          wx.navigateBack()
        }else{
          wx.showToast({
            title:'旧密码错误',
            icon:'none'
          })
        }
      }
    })
  }
})