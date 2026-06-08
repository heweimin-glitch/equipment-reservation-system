// Page({
//   data: {
//     currentReservations: [],
//     statusMap:{
//       0:'待审批',
//       1:'未通过',
//       2:'已取消',
//       3:'使用中',
//       4:'已完成'
//     }
//   },

//   onLoad(){
//     const user = wx.getStorageSync('userInfo')
//     this.setData({
//       userInfo:user
//     })
  
//     this.getCurrentReservations()
//   },

// //获取当前预约
//   getCurrentReservations(){
//     const user = this.data.userInfo
//     wx.request({
//       url:'http://192.168.31.91:8080/reservation/current',
//       method:'GET',
//       data:{
//         applyId:user.id,
//         isAdmin:user.is_admin
//       },
  
//       success:(res)=>{
//         const list = res.data.map(item=>{
//           return {
//             ...item,
//             statusText:this.data.statusMap[item.status]
//           }
//         })
//         this.setData({
//           currentReservations:list
//         })
//       }
//     })
//   },



//   // 处理操作按钮（取消/归还）
//   handleAction(e) {

//     const { id, status, index } = e.currentTarget.dataset;
//     const device = this.data.currentReservations[index];
  
//     this.setData({
//       currentAction: {
//         id,
//         index,
//         status,
//         deviceName: device.deviceName,
//         location: device.address
//       }
//     });
  
//     // ⭐ 取消预约（待审批 or 未通过）
//     if (status == 0 || status == 1) {
  
//       wx.showModal({
//         title: '确认取消',
//         content: `确定要取消【${device.deviceName}】的预约吗？`,
//         success: (res) => {
//           if (res.confirm) {
//             this.cancelReservation();
//           }
//         }
//       });
  
//     //  归还设备（使用中）
//     } else if (status == 3) {
  
//       wx.showModal({
//         title: '归还确认',
//         content: `确认已将设备【${device.deviceName}】归还至【${device.address}】吗？`,
//         success: (res) => {
//           if (res.confirm) {
//             this.returnDevice();
//           }
//         }
//       });
//     }
//   },

//   cancelReservation() {
//     const { id } = this.data.currentAction;
  
//     // ⭐ 1. 先显示加载动画
//     wx.showLoading({
//       title: '取消中...',
//       mask: true
//     });
  
//     // ⭐ 2. 加延迟（模拟服务器处理 + 过渡感）
//     setTimeout(() => {
  
//       wx.request({
//         url: 'http://192.168.31.91:8080/reservation/cancel',
//         method: 'POST',
//         data: { id },
  
//         success: (res) => {
  
//           wx.hideLoading();
  
//           wx.showToast({
//             title: '已取消预约',
//             icon: 'success',
//             duration: 1200
//           });
  
//           // ⭐ 3. 延迟刷新列表（更丝滑）
//           setTimeout(() => {
//             this.getCurrentReservations();
//           }, 300);
//         },
  
//         fail: (err) => {
//           wx.hideLoading();
  
//           wx.showToast({
//             title: '取消失败',
//             icon: 'none'
//           });
  
//           console.log(err);
//         }
//       });
  
//     }, 600); // ⭐ 关键：过渡延迟
//   },
 
//   returnDevice() {
//     const { id } = this.data.currentAction;
  
//     wx.showLoading({
//       title: '归还中...',
//       mask: true
//     });
  
//     setTimeout(() => {
//       wx.request({
//         url: 'http://192.168.31.91:8080/reservation/finish',
//         method: 'POST',
//         data: { id },
  
//         success: () => {
  
//           wx.hideLoading();
  
//           wx.showToast({
//             title: '归还成功',
//             icon: 'success',
//             duration: 1200
//           });
  
//           setTimeout(() => {
//             this.getCurrentReservations();
//           }, 300);
//         },
  
//         fail: (err) => {
//           wx.hideLoading();
  
//           wx.showToast({
//             title: '归还失败',
//             icon: 'none'
//           });
  
//           console.log(err);
//         }
//       });
  
//     }, 600);
//   }
 
// });
Page({

  data: {
    currentReservations: [],
    userInfo: {},
    currentAction: {},
    showRejectModal:false,
    rejectReason:'',
    rejectId:null,
    statusMap: {
      0: '待审批',
      1: '未通过',
      2: '已取消',
      3: '使用中',
      4: '已完成'
    }
  },

  // 页面加载
  onLoad(){
    const user = wx.getStorageSync('userInfo');
    this.setData({
      userInfo: user
    });
    this.getCurrentReservations();
  },

  // 获取当前预约
  getCurrentReservations() {
    const user = this.data.userInfo;
    wx.request({
      url: 'http://10.69.174.110:8080/reservation/current',
      method: 'GET',
      data: {
        applyId: user.id,
        isAdmin: user.is_admin
      },
      success: (res) => {
        const list = res.data.map(item => {
          let statusClass = '';
          switch (item.status) {
            case 0:
              statusClass = 'pending';
              break;
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
          currentReservations: list
        });
        console.log(res.data)
      }
    });
  },

  // 操作按钮
  handleAction(e) {
    const { id, status, action, index } = e.currentTarget.dataset;
    const device = this.data.currentReservations[index];
    // 管理员 —— 同意预约
    if (action == "approve") {
      wx.showModal({
        title: '确认审批',
        content: `确认同意【${device.deviceName}】预约吗？`,
        success: (res) => {
          if (res.confirm) {
            this.approveReservation(id);
          }
        }
      });
      return;
    }

    if (action == "reject") {
      this.setData({    
        showRejectModal:true,
        rejectId:id,
        rejectReason:''
      });
      return;
    }

    // 普通用户操作
    this.setData({
      currentAction: {
        id,
        index,
        status,
        deviceName: device.deviceName,
        location: device.address
      }
    });

    // 取消预约
    if (status == 0 || status == 1) {
      wx.showModal({
        title: '确认取消',
        content: `设备：【${device.deviceName}】
        拒绝理由：【${device.reject_reason}】`,
        success: (res) => {
          if (res.confirm) {
            this.cancelReservation();
          }
        }
      });
    }

    // 归还设备
    else if (status == 3) {

      wx.showModal({

        title: '归还确认',

        content: `确认已将设备【${device.deviceName}】归还至【${device.address}】吗？`,

        success: (res) => {

          if (res.confirm) {

            this.returnDevice();
          }
        }
      });
    }
  },

  //拒绝
  onRejectReasonInput(e){
    this.setData({
      rejectReason:e.detail.value
    });
  },

  closeRejectModal(){
    this.setData({
      showRejectModal:false
    });
  },

  // 普通用户 —— 取消预约
  cancelReservation() {
    const { id } = this.data.currentAction;
    wx.showLoading({
      title: '取消中...',
      mask: true
    });

    setTimeout(() => {
      wx.request({
        url: 'http://10.69.174.110:8080/reservation/cancel',
        method: 'POST',
        data: { id },
        success: () => {
          wx.hideLoading();
          wx.showToast({
            title: '已取消预约',
            icon: 'success',
            duration: 1200
          });

          setTimeout(() => {
            this.getCurrentReservations();
          }, 300);
        },

        fail: (err) => {

          wx.hideLoading();

          wx.showToast({
            title: '取消失败',
            icon: 'none'
          });

          console.log(err);
        }
      });

    }, 600);
  },

  // 普通用户 —— 归还设备
  returnDevice() {
    const { id } = this.data.currentAction;
    wx.showLoading({
      title: '归还中...',
      mask: true
    });

    setTimeout(() => {

      wx.request({
        url: 'http://10.69.174.110:8080/reservation/finish',
        method: 'POST',
        data: { id },
        success: () => {
          wx.hideLoading();
          wx.showToast({
            title: '归还成功',
            icon: 'success',
            duration: 1200
          });

          setTimeout(() => {

            this.getCurrentReservations();

          }, 300);
        },

        fail: (err) => {

          wx.hideLoading();

          wx.showToast({
            title: '归还失败',
            icon: 'none'
          });

          console.log(err);
        }
      });

    }, 600);
  },

  // 管理员 —— 同意预约
  // approveReservation(id) {
  //   wx.showLoading({
  //     title: '审批中...',
  //     mask: true
  //   });
  //   setTimeout(() => {
  //     wx.request({

  //       url: 'http://192.168.31.91:8080/reservation/approve',

  //       method: 'POST',

  //       data: { id },

  //       success: () => {

  //         wx.hideLoading();

  //         wx.showToast({
  //           title: '已同意',
  //           icon: 'success'
  //         });

  //         setTimeout(() => {

  //           this.getCurrentReservations();

  //         }, 300);
  //       },

  //       fail: (err) => {

  //         wx.hideLoading();

  //         wx.showToast({
  //           title: '操作失败',
  //           icon: 'none'
  //         });

  //         console.log(err);
  //       }
  //     });

  //   }, 600);
  // },
  // 管理员 —— 同意预约
approveReservation(id) {
  wx.showLoading({
    title: '审批中...',
    mask: true
  });
  setTimeout(() => {
    wx.request({
      url: 'http://10.69.174.110:8080/reservation/approve',
      method: 'POST',
      data: { id },
      success: (res) => {
        wx.hideLoading();
        console.log(res)
        // ⭐ 库存充足
        if(res.data){

          wx.showToast({
            title: '已同意',
            icon: 'success'
          });

          setTimeout(() => {

            this.getCurrentReservations();

          }, 300);

        }

        // ⭐ 库存不足
        else{

          wx.showToast({
            title: '设备库存不足',
            icon: 'none'
          });
        }
      },

      fail: (err) => {

        wx.hideLoading();

        wx.showToast({
          title: '操作失败',
          icon: 'none'
        });

        console.log(err);
      }
    });

  }, 600);
},

  // 管理员 —— 拒绝预约
  // rejectReservation(id) {

  //   wx.showLoading({
  //     title: '处理中...',
  //     mask: true
  //   });

  //   setTimeout(() => {

  //     wx.request({

  //       url: 'http://192.168.31.91:8080/reservation/reject',

  //       method: 'POST',

  //       data: { id },

  //       success: () => {

  //         wx.hideLoading();

  //         wx.showToast({
  //           title: '已拒绝',
  //           icon: 'success'
  //         });

  //         setTimeout(() => {

  //           this.getCurrentReservations();

  //         }, 300);
  //       },

  //       fail: (err) => {

  //         wx.hideLoading();

  //         wx.showToast({
  //           title: '操作失败',
  //           icon: 'none'
  //         });

  //         console.log(err);
  //       }
  //     });

  //   }, 600);
  // }
  rejectReservation(){

    const {
  
      rejectId,
  
      rejectReason
  
    } = this.data;
  
    if(!rejectReason){
  
      wx.showToast({
  
        title:'请输入拒绝理由',
  
        icon:'none'
      });
  
      return;
    }
  
    wx.showLoading({
  
      title:'处理中...',
      mask:true
    });
  
    setTimeout(()=>{
  
      wx.request({
  
        url:'http://10.69.174.110:8080/reservation/reject',
  
        method:'POST',
  
        data:{
  
          id:rejectId,
  
          rejectReason:rejectReason
        },
  
        success:()=>{
  
          wx.hideLoading();
  
          this.setData({
  
            showRejectModal:false
          });
  
          wx.showToast({
  
            title:'已拒绝',
            icon:'success'
          });
  
          setTimeout(()=>{
  
            this.getCurrentReservations();
  
          },300);
        },
  
        fail:(err)=>{
  
          wx.hideLoading();
  
          wx.showToast({
  
            title:'操作失败',
            icon:'none'
          });
  
          console.log(err);
        }
      })
  
    },600);
  },

});