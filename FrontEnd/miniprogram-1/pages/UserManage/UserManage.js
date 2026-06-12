/**
 * 用户管理页（管理员专用）
 * 创建新用户：普通用户（11位ID）或同类别管理员（5位前缀 + 6位后缀）
 */
import config from '../../utils/config.js'
import request from '../../utils/request.js'
Page({
  data: {
    roleList: ['普通用户', '管理员'],
    selectedRole: '普通用户',
    name: '',
    password: '',
    userIdInput: '',    // 用户输入的ID部分
    currentUser: {},    // 当前登录用户（管理员拼接用）
    nameShow: '',
    passwordShow: ''
  },

  /** 页面显示时读取当前登录管理员信息 */
  onShow() {
    const user = wx.getStorageSync('userInfo');
    this.setData({ currentUser: user });
  },

  /** 选择角色：普通用户 / 管理员 */
  onRoleChange(e) {
    const index = e.detail.value;
    this.setData({
      selectedRole: this.data.roleList[index],
      userIdInput: ''   // 切换角色时清空输入
    });
  },

  /** 用户ID输入：普通用户限11位纯数字，管理员限6位后缀（自动补5位前缀） */
  onUserIdInput(e) {
    let value = e.detail.value.replace(/\D/g, '');  // 只允许数字
    const { selectedRole } = this.data;

    if (selectedRole === '普通用户') {
      if (value.length > 11) value = value.slice(0, 11);
    } else {
      if (value.length > 6) value = value.slice(0, 6);
    }
    this.setData({ userIdInput: value });
  },

  /** 姓名输入绑定 */
  onNameInput(e) {
    this.setData({
      name: e.detail.value,
      nameShow: e.detail.value
    });
  },

  /** 密码输入绑定 */
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value,
      passwordShow: e.detail.value
    });
  },

  /** 提交创建用户：校验必填 → 拼装ID → 调用API */
  submitUser() {
    const { selectedRole, name, password, userIdInput, currentUser } = this.data;

    // 校验必填
    if (!name || !password) {
      return wx.showToast({ title: '请填写完整信息', icon: 'none' });
    }

    let userId = '';

    // 普通用户：必须11位
    if (selectedRole === '普通用户') {
      if (userIdInput.length !== 11) {
        return wx.showToast({ title: '普通用户ID必须11位', icon: 'none' });
      }
      userId = userIdInput;
    }
    // 管理员：5位前缀 + 6位输入 = 11位
    else {
      if (userIdInput.length !== 6) {
        return wx.showToast({ title: '管理员ID必须6位', icon: 'none' });
      }
      const prefix = (currentUser.id || '').substring(0, 5);
      userId = prefix + userIdInput;
    }

    // 最终保证11位
    if (userId.length !== 11) {
      return wx.showToast({ title: 'ID格式错误', icon: 'none' });
    }

    // 提交后端
    request({
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
          wx.showToast({ title: '创建成功', icon: 'success' });
          this.setData({
            name: '', password: '',
            userIdInput: '', nameShow: '', passwordShow: ''
          });
        } else {
          wx.showToast({ title: '用户已存在或插入失败', icon: 'none' });
        }
      },
      fail: () => {
        wx.showToast({ title: '创建失败', icon: 'none' });
      }
    });
  }

})