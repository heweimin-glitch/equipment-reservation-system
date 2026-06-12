/**
 * 首页——设备列表
 * 展示所有设备，支持按学科分类筛选，点击设备跳转详情页
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'

Page({
  onLoad() {
    const user = wx.getStorageSync('userInfo');
    this.setData({
      userInfo: user,
      name: user.name,
      is_admin: user.is_admin
    });
  },

  data: {
    equipmentList: [],      // 当前显示的设备列表
    allEquipmentList: [],   // 全部设备（用于筛选恢复）
    count: 0,
    currentType: ''         // 当前选中的学科分类
  },

  onShow() {
    this.getEquipment();
  },

  // 学科分类映射表（ID前2位 → 学科名称）
  CATEGORY_MAP: {
    '01': '化学工程', '02': '医学实验',
    '03': '机械工程', '04': '材料科学',
    '05': '物理实验', '06': '环境科学',
    '07': '生物科学', '08': '电子信息'
  },

  /** 获取全部设备列表，并附加学科分类字段 */
  getEquipment() {
    request({
      url: config.baseUrl + '/device/list',
      method: 'GET',
      success: (res) => {
        console.log("设备数据：", res.data);
        const list = res.data.map(item => ({
          ...item,
          category: this.CATEGORY_MAP[item.id.substring(0, 2)] || '未知'
        }));
        this.setData({
          equipmentList: list,
          allEquipmentList: list,
          count: list.length
        });
      }
    });
  },

  /** 按学科分类筛选设备 */
  filterCategory(e) {
    const type = e.currentTarget.dataset.type;
    const newList = this.data.allEquipmentList.filter(
      item => item.id.toString().startsWith(type)
    );
    this.setData({
      equipmentList: newList,
      count: newList.length,
      currentType: type
    });
  },

  /** 显示全部设备（清除筛选） */
  showAll() {
    this.setData({
      equipmentList: this.data.allEquipmentList,
      count: this.data.allEquipmentList.length,
      currentType: ''
    });
  },

  /** 点击设备 → 跳转设备详情页（管理员不能预约，库存不足不能预约） */
  DeviceLink(e) {
    const item = e.currentTarget.dataset.item;
    if (item.number > 0 && this.data.is_admin != 1) {
      wx.navigateTo({ url: '/pages/device/device?id=' + item.id });
    } else if (item.number <= 0) {
      wx.showToast({ title: '设备不足', icon: 'none' });
    } else if (this.data.is_admin == 1) {
      wx.showToast({ title: '管理员不能预约', icon: 'none' });
    }
  },

  /** 跳转 AI 助手页面（管理员暂不可用） */
  goAI(e) {
    if (this.data.is_admin == 1) {
      wx.showToast({ title: '未对管理员开放', icon: 'wrong' });
    } else {
      wx.navigateTo({ url: '/pages/Ai/Ai' });
    }
  }

})