/**
 * HTTP 请求封装
 * 自动注入 JWT Authorization Header，
 * Token 过期（401）时自动跳转登录页
 */
function request(options) {
  const token = wx.getStorageSync('token') || '';

  const merged = {
    ...options,
    header: {
      ...options.header,
      'Authorization': token ? 'Bearer ' + token : ''
    }
  };

  const originalSuccess = options.success;
  merged.success = function (res) {
    // 401 未授权 → 清除登录态，跳转登录页
    if (res.statusCode === 401) {
      wx.removeStorageSync('token');
      wx.removeStorageSync('userInfo');
      wx.showToast({ title: '登录已过期，请重新登录', icon: 'none' });
      setTimeout(() => {
        wx.reLaunch({ url: '/pages/login/login' });
      }, 1000);
      return;
    }
    if (originalSuccess) {
      originalSuccess(res);
    }
  };

  return wx.request(merged);
}

export default request;
