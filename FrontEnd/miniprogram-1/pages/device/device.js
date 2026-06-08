// pages/device/device.js
Page({

  data: {
    device: {},
    category:'',
  },

  onLoad(options) {
    console.log("接收到ID：", options.id)
    this.getDeviceDetail(options.id)
  },
  getDeviceDetail(id) {
    wx.request({
      url: config.baseUrl + '/device/detail?id=' + id,
      success: (res) => {
        console.log("设备详情：", res.data)
        const item = res.data

      // 处理分类
      const prefix = item.id.substring(0,2)

      let category = ''

      if(prefix=='01') category='化学工程'
      else if(prefix=='02') category='医学实验'
      else if(prefix=='03') category='机械工程'
      else if(prefix=='04') category='材料科学'
      else if(prefix=='05') category='物理实验'
      else if(prefix=='06') category='环境科学'
      else if(prefix=='07') category='生物科学'
      else if(prefix=='08') category='电子信息'
      else category='未知'
        this.setData({
          device:{
            ...item,
            category
          }
        })

      }

    })
  },

  showError: function(message) {
    wx.showToast({
      title: message,
      icon: 'none',
      duration: 1000
    });
  },

  showLoading: function(message) {
    wx.showToast({
      title: message,
      icon: 'loading',
      duration: 1000
    });
  },

  onReserveTap(){
    wx.setStorageSync('deviceList', this.data.device)
    wx.navigateTo({
      url:'/pages/deviceappointment/deviceappointment'
    })
  }

})