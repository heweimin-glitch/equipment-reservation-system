Page({

  data:{
    tab:'list',
    deviceList:[],
    form:{},
    numMap:{}
  },

  onLoad(){
    this.getDeviceList()
  },

  // 切换tab
  switchTab(e){
    this.setData({
      tab:e.currentTarget.dataset.tab
    })
  },

  // 获取设备
  getDeviceList(){
    const user = wx.getStorageSync('userInfo')
    wx.request({
      url:config.baseUrl + '/device/xxlist',
      method:'GET',
      data:{
        adminId:user.id
      },
      success:(res)=>{
        this.setData({
          deviceList:res.data
        })
      }
    })
  },

  // 数量输入
  onNumInput(e){
    const id = e.currentTarget.dataset.id
    this.data.numMap[id] = e.detail.value
  },

  // 入库
  addNumber(e){

    const id = e.currentTarget.dataset.id
    const name = e.currentTarget.dataset.name
    const num = this.data.numMap[id] || 0

    wx.showModal({
      title:'确认',
      content:`添加${num}个【${name}】？`,
      success:(res)=>{
        if(res.confirm){

          wx.request({
            url:config.baseUrl + '/device/addNumber',
            method:'POST',
            data:{id, number:num},
            success:()=>{
              wx.showToast({title:'成功'})
              this.getDeviceList()
            }
          })

        }
      }
    })
  },

  // 出库
  reduceNumber(e){
    const id = e.currentTarget.dataset.id
    const name = e.currentTarget.dataset.name
    const current = e.currentTarget.dataset.current
    const num = this.data.numMap[id] || 0
    if(num > current){
      wx.showToast({title:'库存不足',icon:'none'})
      return
    }

    wx.showModal({
      title:'确认',
      content:`删除${num}个【${name}】？`,
      success:(res)=>{
        if(res.confirm){

          wx.request({
            url:config.baseUrl + '/device/reduceNumber',
            method:'POST',
            data:{id, number:num},
            success:(res)=>{
              if(res.data){
                wx.showToast({title:'成功'})
                this.getDeviceList()
              }else{
                wx.showToast({title:'库存不足',icon:'none'})
              }
            }
          })

        }
      }
    })
  },

  // 表单输入
  setField(e){
    const field = e.currentTarget.dataset.field
    this.data.form[field] = e.detail.value
  },

  // 新增设备
  addDevice(){
    const d = this.data.form
    const user = wx.getStorageSync('userInfo')
  
    // 自动生成ID
    d.id = user.id.substring(0,2) + Math.floor(Math.random()*900+100)
  
    wx.showLoading({
      title: '添加中...',
      mask: true
    })
  
    // 模拟服务器处理延迟（更真实）
    setTimeout(() => {
      console.log(d)
      wx.request({
        url:config.baseUrl + '/device/add',
        method:'POST',
        data:d,
  
        success:(res)=>{
  
          wx.hideLoading()
  
          if(res.data){
  
            wx.showToast({
              title:'添加成功',
              icon:'success'
            })
  
            setTimeout(()=>{
              this.setData({tab:'list'})
              this.getDeviceList()
            }, 500)
  
          }else{
  
            wx.showToast({
              title:'ID重复',
              icon:'none'
            })
  
          }
        },
  
        fail:()=>{
          wx.hideLoading()
          wx.showToast({
            title:'网络错误',
            icon:'none'
          })
        }
  
      })
  
    }, 600)
  }

})