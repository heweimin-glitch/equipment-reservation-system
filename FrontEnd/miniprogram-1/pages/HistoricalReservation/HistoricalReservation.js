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

  getHistoryReservations() {
    const user = this.data.userInfo;
    wx.request({
      url: 'http://10.69.174.110:8080/reservation/history',
      method: 'GET',
      data: {
        applyId: user.id,
        isAdmin: user.is_admin
      },

      success: (res) => {

        const list = res.data.map(item => {
          let statusClass = '';
          switch (item.status) {
            case 1:
              statusClass = 'reject';
              break;
            case 2:
              statusClass = 'cancel';
              break;
            case 3:
              statusClass = 'using';
              break;
            case 4:
              statusClass = 'finish';
              break;
          }
          return {
            ...item,  
            statusText: this.data.statusMap[item.status],
            statusClass:statusClass
          }
        });

        this.setData({
          historyReservations: list
        });
      }
    });
  }

});