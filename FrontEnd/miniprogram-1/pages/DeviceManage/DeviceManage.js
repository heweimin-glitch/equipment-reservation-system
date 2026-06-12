/**
 * 设备管理页（管理员专用）
 * 支持：设备列表查看、入库/出库、新增设备
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'

Page({
  data: {
    tab: 'list',       // 当前Tab：list=设备列表, add=新增设备
    deviceList: [],
    form: {},          // 新增设备表单
    numMap: {}         // 各设备的出入库数量输入缓存 { deviceId: Number }
  },

  onLoad() {
    this.getDeviceList();
  },

  /** 切换 Tab：设备列表 / 新增设备 */
  switchTab(e) {
    this.setData({ tab: e.currentTarget.dataset.tab });
  },

  /** 获取当前管理员管辖的设备列表，同时清空 numMap 缓存 */
  getDeviceList() {
    const user = wx.getStorageSync('userInfo');
    request({
      url: config.baseUrl + '/device/xxlist',
      method: 'GET',
      data: { adminId: user.id },
      success: (res) => {
        this.setData({
          deviceList: res.data,
          numMap: {}   // 刷新列表时清空数量缓存（修复1）
        });
      }
    });
  },

  /**
   * 出入库数量输入缓存
   * 修复2：立即转为数字类型存储
   */
  onNumInput(e) {
    const id = e.currentTarget.dataset.id;
    this.data.numMap[id] = parseInt(e.detail.value, 10) || 0;
  },

  /** 设备入库：增加库存 */
  addNumber(e) {
    const id = e.currentTarget.dataset.id;
    const name = e.currentTarget.dataset.name;
    const num = this.data.numMap[id] || 0;  // num 已是数字类型

    if (num <= 0) {
      wx.showToast({ title: '请输入有效数量', icon: 'none' });
      return;
    }

    wx.showModal({
      title: '确认',
      content: `入库 ${num} 个【${name}】？`,
      success: (res) => {
        if (res.confirm) {
          request({
            url: config.baseUrl + '/device/addNumber',
            method: 'POST',
            data: { id, number: num },
            success: () => {
              wx.showToast({ title: '入库成功' });
              this.getDeviceList();
            }
          });
        }
      }
    });
  },

  /** 设备出库：减少库存（校验库存是否充足） */
  reduceNumber(e) {
    const id = e.currentTarget.dataset.id;
    const name = e.currentTarget.dataset.name;
    const current = e.currentTarget.dataset.current;
    const num = this.data.numMap[id] || 0;

    if (num <= 0) {
      wx.showToast({ title: '请输入有效数量', icon: 'none' });
      return;
    }

    if (num > current) {
      wx.showToast({ title: '库存不足', icon: 'none' });
      return;
    }

    wx.showModal({
      title: '确认',
      content: `出库 ${num} 个【${name}】？`,   // 修复4："删除" → "出库"
      success: (res) => {
        if (res.confirm) {
          request({
            url: config.baseUrl + '/device/reduceNumber',
            method: 'POST',
            data: { id, number: num },
            success: (res) => {
              if (res.data) {
                wx.showToast({ title: '出库成功' });
                this.getDeviceList();
              } else {
                wx.showToast({ title: '库存不足', icon: 'none' });
              }
            }
          });
        }
      }
    });
  },

  /** 新增设备表单字段绑定 */
  setField(e) {
    const field = e.currentTarget.dataset.field;
    this.data.form[field] = e.detail.value;
  },

  /** 新增设备：校验 → 生成ID → 调用API → 清空表单 */
  addDevice() {
    // 修复3：拷贝表单数据避免引用污染
    const d = {
      name: this.data.form.name || '',
      address: this.data.form.address || '',
      number: this.data.form.number || '',
      spec: this.data.form.spec || '',
      ImageUrl: this.data.form.ImageUrl || '',
      introduction: this.data.form.introduction || ''
    };

    // 修复6：表单校验（6个字段全部检查）
    if (!d.name.trim()) {
      wx.showToast({ title: '请输入设备名称', icon: 'none' });
      return;
    }
    if (!d.address.trim()) {
      wx.showToast({ title: '请输入设备地址', icon: 'none' });
      return;
    }
    if (!d.number || parseInt(d.number, 10) <= 0) {
      wx.showToast({ title: '请输入有效数量', icon: 'none' });
      return;
    }
    if (!d.spec.trim()) {
      wx.showToast({ title: '请输入设备规格', icon: 'none' });
      return;
    }
    if (!d.ImageUrl.trim()) {
      wx.showToast({ title: '请输入图片链接', icon: 'none' });
      return;
    }
    if (!d.introduction.trim()) {
      wx.showToast({ title: '请输入设备简介', icon: 'none' });
      return;
    }
    d.number = parseInt(d.number, 10);

    const user = wx.getStorageSync('userInfo');
    d.id = user.id.substring(0, 2) + Math.floor(Math.random() * 900 + 100);

    wx.showLoading({ title: '添加中...', mask: true });

    // 修复10：去掉双层 setTimeout，只在回调里延迟切Tab
    request({
      url: config.baseUrl + '/device/add',
      method: 'POST',
      data: d,
      success: (res) => {
        wx.hideLoading();
        if (res.data) {
          wx.showToast({ title: '添加成功', icon: 'success' });
          // 修复3：清空表单
          this.setData({ form: {} });
          setTimeout(() => {
            this.setData({ tab: 'list' });
            this.getDeviceList();
          }, 500);
        } else {
          wx.showToast({ title: 'ID重复，请重试', icon: 'none' });
        }
      },
      fail: () => {
        wx.hideLoading();
        wx.showToast({ title: '网络错误', icon: 'none' });
      }
    });
  }

});
