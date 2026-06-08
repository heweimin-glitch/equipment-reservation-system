// index.js
Page({
  
// 静态版数据源
  // data: {
  //   test:1,
  //   currentIndex: 0, // 默认选中第一个
  //   DeviceNumber: 0,
  //   num: 1,
  //   ListNumber: [],
  //   Device:{
  //     DeviceImage:'/DeviceImage/数字存储示波器.jpg',
  //     DeviceName:'数字存储示波器',
  //     DeviceState:'可用',
  //     DeviceSite:'实验楼b座201',
  //     DeviceClasses:'默认设备分类',
  //     DeviceIntro:'设备简介区（---------测试字段---------）'
  //   },
  //   userid: {
  //     name: 'xiaoli',
  //     age: 111,
  //   },
  //   list: [
  //     [1],
  //     [2, 3],
  //     [4, 5, 6],
  //     [7, 8, 9, 10],
  //   ],
  //   DeviceKind: ['全部', '电子仪器', '光学仪器', '化学仪器', '生物仪器'],
  // },
onLoad(){
  const user = wx.getStorageSync('userInfo')
    this.setData({
      userInfo: user,
      name:user.name,
      is_admin: user.is_admin
    })
},

data:{
  equipmentList:[],
  allEquipmentList:[],
  count:0,
  currentType:'' ,
},

onShow(){
  this.getEquipment()
},



  getEquipment() {
    wx.request({
      url: 'http://10.69.174.110:8080/device/list',
      method: 'GET',
      success: (res) => {
        console.log("设备数据：", res.data)

        const list = res.data.map(item => {

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
      
          return {
            ...item,
            category  
          }
        })
        this.setData({
          equipmentList: list,
          allEquipmentList: list,
          count:list.length
        })
      }
    })
    console.log("处理后的数据：", list)
  },

  filterCategory(e) {
    const type = e.currentTarget.dataset.type
    const newList = this.data.allEquipmentList.filter(item => {
      return item.id.toString().startsWith(type)
    })
    this.setData({
      equipmentList: newList,
      count:newList.length,
      currentType:type  
    })
  },

  showAll() {
    this.setData({
      equipmentList: this.data.allEquipmentList,
      count:this.data.allEquipmentList.length,
      currentType:''   
    })
  },

  UpdateList(event) {
    const index = event.currentTarget.dataset.index;
    const selectedindex = event.currentTarget.dataset.index
    let backindex = index - 1;
    let sum =0;
    let displayList = [];

    if (index == 0) {
      backindex = index;
      for (let i = 0; i < this.data.list.length; i++) {
        sum += this.data.list[i].length;
      }
      displayList = this.mergeAllLists();//合并列表
    } else{
       sum = this.data.list[backindex].length;
       displayList = this.data.list[backindex];
    }
    this.setData({
      ListNumber: displayList,
      DeviceNumber: sum,
      currentIndex: selectedindex
    })
  },

  DeviceLink(e){
    const item = e.currentTarget.dataset.item
    if(item.number> 0 && this.data.is_admin != 1){
      wx.navigateTo({
        url: '/pages/device/device?id=' + item.id // 目标页面路径
      })
    }else if(item.number <= 0){
      wx.showToast({
        title:'设备不足',
        icon:'none'
      })
      return
    }else if(this.data.is_admin == 1){
      wx.showToast({
        title:'管理员不能预约',
        icon:'none'
      })
      return
    }

    
  },



})