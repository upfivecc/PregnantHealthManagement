// register.js
Page({
  data: {
    username: '',
    password: '',
    email: '',
    phone: '',
    realName: '',
    gender: 2, // 默认女性
    age: '',
    confirmPassword: '',
    isAgree: false
  },

  // 输入手机号
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

  // 确认密码
  onConfirmPasswordInput(e) {
    this.setData({
      confirmPassword: e.detail.value
    })
  },



  // 输入用户名
  onUsernameInput(e) {
    this.setData({
      username: e.detail.value
    })
  },

  // 输入邮箱
  onEmailInput(e) {
    this.setData({
      email: e.detail.value
    })
  },

  // 输入真实姓名
  onRealNameInput(e) {
    this.setData({
      realName: e.detail.value
    })
  },

  // 选择性别
  onGenderChange(e) {
    this.setData({
      gender: parseInt(e.detail.value)
    })
  },

  // 输入年龄
  onAgeInput(e) {
    this.setData({
      age: parseInt(e.detail.value)
    })
  },

  // 切换协议同意状态
  toggleAgree() {
    this.setData({
      isAgree: !this.data.isAgree
    })
  },



  // 注册按钮点击
  onRegister() {
    const { username, password, confirmPassword, email, phone, realName, gender, age, isAgree } = this.data

    // 表单验证
    if (!username) {
      wx.showToast({ title: '请输入用户名', icon: 'none' })
      return
    }

    if (!email) {
      wx.showToast({ title: '请输入邮箱', icon: 'none' })
      return
    }

    if (!/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
      wx.showToast({ title: '请输入正确的邮箱格式', icon: 'none' })
      return
    }

    if (!phone) {
      wx.showToast({ title: '请输入手机号', icon: 'none' })
      return
    }

    if (!/^1[3-9]\d{9}$/.test(phone)) {
      wx.showToast({ title: '请输入正确的手机号', icon: 'none' })
      return
    }

    if (!realName) {
      wx.showToast({ title: '请输入真实姓名', icon: 'none' })
      return
    }

    if (!age || age <= 0 || age > 120) {
      wx.showToast({ title: '请输入有效的年龄', icon: 'none' })
      return
    }

    if (!password) {
      wx.showToast({ title: '请输入密码', icon: 'none' })
      return
    }

    if (password.length < 6) {
      wx.showToast({ title: '密码长度不能少于6位', icon: 'none' })
      return
    }

    if (password !== confirmPassword) {
      wx.showToast({ title: '两次输入的密码不一致', icon: 'none' })
      return
    }

    if (!isAgree) {
      wx.showToast({ title: '请阅读并同意用户协议', icon: 'none' })
      return
    }

    // 调用注册接口
    wx.showLoading({ title: '注册中...' })

    wx.request({
      url: 'http://localhost:8080/api/users',
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      data: {
        username,
        password,
        email,
        phone,
        realName,
        gender,
        age,
        role: 'USER',
        status: 1
      },
      success: (res) => {
        wx.hideLoading()
        if (res.statusCode === 200 || res.statusCode === 201) {
          wx.showToast({
            title: '注册成功',
            icon: 'success'
          })
          
          // 跳转到登录页面
          setTimeout(() => {
            wx.navigateBack()
          }, 1500)
        } else {
          wx.showToast({
            title: res.data.message || '注册失败',
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

  // 跳转到登录页面
  goToLogin() {
    wx.navigateBack()
  }
})
