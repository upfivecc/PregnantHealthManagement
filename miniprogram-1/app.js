// app.js
App({
  onLaunch() {
    // 展示本地存储能力
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 初始化登录状态
    this.checkLoginStatus()

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
  },
  
  // 检查登录状态
  checkLoginStatus() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.isLoggedIn) {
      this.globalData.userInfo = userInfo
      this.globalData.isLoggedIn = true
    } else {
      this.globalData.userInfo = null
      this.globalData.isLoggedIn = false
    }
  },
  
  // 验证登录状态，如果未登录则提示并跳转登录页
  validateLogin(navigateToLogin = true) {
    this.checkLoginStatus()
    if (!this.globalData.isLoggedIn) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      })
      if (navigateToLogin) {
        setTimeout(() => {
          wx.switchTab({
            url: '/pages/login/login'
          })
        }, 1500)
      }
      return false
    }
    return true
  },
  
  globalData: {
    userInfo: null,
    isLoggedIn: false,
    baseUrl:"http://127.0.0.1:8080"
  }
})
