/**
 * 历史预约页
 * 管理员：查看未通过/使用中/已完成
 * 普通用户：查看已取消/已完成
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'
Page({
  data: {
    historyReservations: [],
    statusMap: {}
  },

  onLoad() {
    const user = wx.getStorageSync('userInfo');

    this.setData({
      userInfo: user,
      statusMap: this.getStatusMap(user.is_admin)
    });

    this.getHistoryReservations();
  },

  /** 根据身份返回可见的状态映射 */
  getStatusMap(isAdmin) {
    if (isAdmin == 1) {
      return {
        1: '未通过',
        3: '使用中',
        4: '已完成'
      };
    } else {
      return {
        2: '已取消',
        4: '已完成'
      };
    }
  },

  /** 获取历史预约并附加状态文本/样式类 */
  getHistoryReservations() {
    const user = this.data.userInfo;
    request({
      url: config.baseUrl + '/reservation/history',
      method: 'GET',
      data: {
        applyId: user.id,
        isAdmin: user.is_admin
      },

      success: (res) => {
        const list = res.data.map(item => {
          let statusClass = '';
          switch (item.status) {
            case 1: statusClass = 'reject'; break;
            case 2: statusClass = 'cancel'; break;
            case 3: statusClass = 'using';  break;
            case 4: statusClass = 'finish'; break;
          }
          return {
            ...item,
            statusText: this.data.statusMap[item.status],
            statusClass: statusClass
          };
        });
        this.setData({ historyReservations: list });
      }
    });
  }

});