/**
 * 设备详情页
 * 展示单个设备的完整信息，提供「预约」入口
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'
Page({

  data: {
    device: {},
    category: '',
  },

  onLoad(options) {
    console.log("接收到ID：", options.id)
    this.getDeviceDetail(options.id)
  },

  /** 请求设备详情并附加学科分类 */
  getDeviceDetail(id) {
    request({
      url: config.baseUrl + '/device/detail?id=' + id,
      success: (res) => {
        console.log("设备详情：", res.data)
        const item = res.data

        // ID 前2位 → 学科分类
        const prefix = item.id.substring(0, 2)
        let category = ''
        if (prefix == '01') category = '化学工程'
        else if (prefix == '02') category = '医学实验'
        else if (prefix == '03') category = '机械工程'
        else if (prefix == '04') category = '材料科学'
        else if (prefix == '05') category = '物理实验'
        else if (prefix == '06') category = '环境科学'
        else if (prefix == '07') category = '生物科学'
        else if (prefix == '08') category = '电子信息'
        else category = '未知'

        this.setData({
          device: { ...item, category }
        })
      }
    })
  },

  /** 显示错误提示 */
  showError: function (message) {
    wx.showToast({ title: message, icon: 'none', duration: 1000 });
  },

  /** 显示加载提示 */
  showLoading: function (message) {
    wx.showToast({ title: message, icon: 'loading', duration: 1000 });
  },

  /** 点击「预约」→ 缓存设备信息并跳转预约表单页 */
  onReserveTap() {
    wx.setStorageSync('deviceList', this.data.device)
    wx.navigateTo({ url: '/pages/deviceappointment/deviceappointment' })
  }

})