/**
 * 设备预约表单页
 * 选择时间、填写用途、选择审批人 → 提交预约申请
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'
Page({

  data: {
    deviceList: {},
    startDate: '',
    startTime: '',
    endDate: '',
    endTime: '',
    content: '',
    userInfo: {},
    adminList: [],
    selectedAdmin: {}
  },

  onLoad() {
    this.initTime();
    const deviceList = wx.getStorageSync('deviceList');
    const user = wx.getStorageSync('userInfo');
    this.setData({ deviceList, userInfo: user });
    this.getAdminList();
  },

  /**
   * 获取该设备所在学科下的管理员列表（排除当前用户自己）
   */
  getAdminList() {
    const prefix = this.data.deviceList.id.substring(0, 2);
    const userId = this.data.userInfo.id;

    request({
      url: config.baseUrl + '/user/adminByPrefix',
      method: 'GET',
      data: { prefix },

      success: (res) => {
        const list = (res.data || []).filter(item => item.id !== userId);
        this.setData({ adminList: list });
      }
    });
  },


  /** 选择审批人 */
  onAdminChange(e) {
    const index = e.detail.value;
    this.setData({
      selectedAdmin: this.data.adminList[index]
    });
  },

  /** 用途输入绑定 */
  onInput(e) {
    this.setData({ content: e.detail.value });
  },

  /** 开始日期选择 */
  onStartDateChange(e) {
    this.setData({ startDate: e.detail.value });
  },

  /** 开始时间选择 */
  onStartTimeChange(e) {
    this.setData({ startTime: e.detail.value });
  },

  /** 结束日期选择 */
  onEndDateChange(e) {
    this.setData({ endDate: e.detail.value });
  },

  /** 结束时间选择 */
  onEndTimeChange(e) {
    this.setData({ endTime: e.detail.value });
  },

  /** 提交预约：校验必填项 → 生成ID → 调用API */
  onsubmitTap() {

    const {
      startDate,
      startTime,
      endDate,
      endTime,
      content,
      selectedAdmin,
      deviceList,
      userInfo
    } = this.data;

    // 必选审批人
    if (!selectedAdmin.id) {
      return this.showError('请选择审批人');
    }

    // 必填用途
    if (!content || content.trim() === '') {
      return this.showError('请输入预约用途');
    }

    // 拼接完整的日期时间字符串
    const startStr = `${startDate}T${startTime}:00`;
    const endStr = `${endDate}T${endTime}:00`;

    const start = new Date(startDate + ' ' + startTime).getTime();
    const end = new Date(endDate + ' ' + endTime).getTime();

    // 时间校验：开始时间必须早于结束时间
    if (start >= end) {
      return this.showError('结束时间必须晚于开始时间');
    }

    // 生成8位ID
    const id = this.generateId();

    // 提交
    this.submit({
      id,
      deviceList,
      userInfo,
      selectedAdmin,
      startStr,
      endStr,
      content
    });
  },

  /**
   * 调用后端 API 提交预约
   * @param data 预约数据对象
   */
  submit(data) {

    wx.showLoading({
      title: '提交中...',
      mask: true
    });

    setTimeout(() => {
      request({
        url: config.baseUrl + '/reservation/add',
        method: 'POST',
        data: {
          id: data.id,
          apply_id: data.userInfo.id,
          admin_id: data.selectedAdmin.id,
          device_id: data.deviceList.id,
          start_time: data.startStr,
          end_time: data.endStr,
          purpose: data.content,
          status: 0
        },

        success: () => {
          wx.hideLoading();

          wx.showToast({
            title: '预约成功',
            icon: 'success'
          });

          setTimeout(() => {
            wx.switchTab({
              url: '/pages/index/index'
            });
          }, 800);
        },

        fail: () => {
          wx.hideLoading();
          this.showError('提交失败');
        }
      });

    }, 500);
  },

  /** 生成8位随机预约ID */
  generateId() {
    return Math.floor(10000000 + Math.random() * 90000000).toString();
  },

  /** 初始化默认开始/结束时间为当前时间 */
  initTime() {

    const now = new Date();

    const y = now.getFullYear();
    const m = String(now.getMonth() + 1).padStart(2, '0');
    const d = String(now.getDate()).padStart(2, '0');

    const date = `${y}-${m}-${d}`;
    const time = `${String(now.getHours()).padStart(2, '0')}:${String(now.getMinutes()).padStart(2, '0')}`;

    this.setData({
      startDate: date,
      startTime: time,
      endDate: date,
      endTime: time
    });
  },


  /** 错误提示 Toast */
  showError(msg) {
    wx.showToast({
      title: msg,
      icon: 'none'
    });
  }

});