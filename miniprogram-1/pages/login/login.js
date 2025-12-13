// login.js
Page({
  data: {
    phone: '',
    password: '',
    isAgree: false,
    isLoggedIn: false,
    userInfo: {}
  },

  // 页面加载时检查登录状态
  onLoad() {
    this.checkLoginStatus()
  },

  // 页面显示时检查登录状态
  onShow() {
    this.checkLoginStatus()
  },

  // 检查登录状态
  checkLoginStatus() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.isLoggedIn) {
      this.setData({
        isLoggedIn: true,
        userInfo: userInfo
      })
    } else {
      this.setData({
        isLoggedIn: false,
        userInfo: {}
      })
    }
  },

  // 输入用户名或手机号
  onPhoneInput(e) {
    this.setData({
      phone: e.detail.value
    })
  },

  // 输入密码
  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    })
  },

  // 切换协议同意状态
  toggleAgree() {
    this.setData({
      isAgree: !this.data.isAgree
    })
  },

  // 登录按钮点击
  onLogin() {
    const { phone, password, isAgree } = this.data

    // 表单验证
    if (!phone) {
      wx.showToast({
        title: '请输入用户名或手机号',
        icon: 'none'
      })
      return
    }

    if (!password) {
      wx.showToast({
        title: '请输入密码',
        icon: 'none'
      })
      return
    }

    if (!isAgree) {
      wx.showToast({
        title: '请阅读并同意用户协议',
        icon: 'none'
      })
      return
    }

    // 显示登录中提示
    wx.showLoading({ title: '登录中...' })

    // 实际接口调用
    const app = getApp()
    wx.request({
      url: app.globalData.baseUrl ? app.globalData.baseUrl + '/api/users/login' : 'http://localhost:8080/api/users/login',
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        username: phone, // 使用用户名或手机号登录
        password: password
      },
      success: (res) => {
        wx.hideLoading()
        if (res.data.code === 200) {
          // 登录成功，保存用户信息
          const userInfo = {
            ...res.data.data,
            phone: phone, // 保留手机号
            isLoggedIn: true
          }
          wx.setStorageSync('userInfo', userInfo)
          
          // 更新全局登录状态和用户信息
          app.globalData.userInfo = userInfo
          app.globalData.userId = userInfo.id
          app.checkLoginStatus()
          
          // 显示成功提示
          wx.showToast({
            title: '登录成功',
            icon: 'success'
          })
          
          // 更新页面状态
          this.setData({
            isLoggedIn: true,
            userInfo: userInfo
          })
          
          // 跳转到首页
          wx.switchTab({
            url: '../index/index'
          })
        } else {
          // 登录失败
          wx.showToast({
            title: res.data.message || '登录失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        wx.hideLoading()
        wx.showToast({
          title: '网络错误，请稍后重试',
          icon: 'none'
        })
      }
    })
  },

  // 跳转到注册页面
  goToRegister() {
    wx.navigateTo({
      url: '../register/register'
    })
  },

  // 忘记密码
  onForgotPassword() {
    wx.showToast({
      title: '忘记密码功能暂未开放',
      icon: 'none'
    })
  },

  // 退出登录
  onLogout() {
    wx.showModal({
      title: '确认退出',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          // 清除用户信息
          wx.removeStorageSync('userInfo')
          // 更新页面状态
          this.setData({
            isLoggedIn: false,
            userInfo: {},
            phone: '',
            password: '',
            isAgree: false
          })
          wx.showToast({
            title: '已退出登录',
            icon: 'success'
          })
        }
      }
    })
  }
})