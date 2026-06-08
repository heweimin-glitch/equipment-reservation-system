// pages/addUser/addUser.js

Page({

  data: {
    roleList: ['普通用户', '管理员'],
    selectedRole: '普通用户',
    name: '',
    password: '',
    userIdInput: '',   // 新加 用户输入ID
    currentUser: {} ,// 当前登录用户（管理员拼接用）
    nameShow:'',
    passwordShow:''
  },

  onShow() {
    const user = wx.getStorageSync('userInfo')
    this.setData({
      currentUser: user
    })
  },


  // 权限选择
  onRoleChange(e) {
    const index = e.detail.value

    this.setData({
      selectedRole: this.data.roleList[index],
      userIdInput: '' // 切换角色清空输入
    })
  },


  // 用户ID输入 有长度控制
  onUserIdInput(e) {

    let value = e.detail.value.replace(/\D/g, '') // 只允许数字

    const { selectedRole } = this.data

    if (selectedRole === '普通用户') {

      // 最多11位
      if (value.length > 11) {
        value = value.slice(0, 11)
      }

    } else {

      // 管理员最多6位（后缀）
      if (value.length > 6) {
        value = value.slice(0, 6)
      }
    }

    this.setData({
      userIdInput: value
    })
  },

  // 姓名
  onNameInput(e) {
    this.setData({
      name: e.detail.value,
      nameShow:e.detail.value
    })
  },

  // 密码
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value,
      passwordShow:e.detail.value
    })
  },

  // 提交
  submitUser() {
    const {
      selectedRole,
      name,
      password,
      userIdInput,
      currentUser
    } = this.data

    // 校验必填
    if (!name || !password) {
      return wx.showToast({
        title: '请填写完整信息',
        icon: 'none'
      })
    }

    let userId = ''

    // 普通用户：必须11位
    if (selectedRole === '普通用户') {

      if (userIdInput.length !== 11) {
        return wx.showToast({
          title: '普通用户ID必须11位',
          icon: 'none'
        })
      }

      userId = userIdInput
    }

    // 管理员：5位前缀 + 6位输入
    else {

      if (userIdInput.length !== 6) {
        return wx.showToast({
          title: '管理员ID必须6位',
          icon: 'none'
        })
      }

      const prefix = (currentUser.id || '').substring(0, 5)

      userId = prefix + userIdInput
    }

    // 再次保证11位
    if (userId.length !== 11) {
      return wx.showToast({
        title: 'ID格式错误',
        icon: 'none'
      })
    }

    // ======================
    // 提交后端
    // ======================
    wx.request({
      url: config.baseUrl + '/user/add',
      method: 'POST',
      data: {
        id: userId,
        name,
        password,
        is_admin: selectedRole === '管理员' ? 1 : 0
      },
      success: (res) => {

        if (res.data === true) {
      
          wx.showToast({
            title: '创建成功',
            icon: 'success'
          })
      
          this.setData({
            name: '',
            password: '',
            userIdInput: '',
            nameShow:'',
            passwordShow:''
          })
         
      
        } else {
      
          wx.showToast({
            title: '用户已存在或插入失败',
            icon: 'none'
          })
        }
      },
      fail: () => {
        wx.showToast({
          title: '创建失败',
          icon: 'none'
        })
      }
    })
  }

})