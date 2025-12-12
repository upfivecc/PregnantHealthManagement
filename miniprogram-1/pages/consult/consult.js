// consult.js
Page({
  data: {
    doctorInfo: {},
    messages: [],
    inputValue: '',
    scrollTop: 0,
    loading: true,
    connecting: false
  },

  onLoad(options) {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { doctorId } = options
    this.doctorId = doctorId
    this.loadDoctorInfo()
    this.loadChatHistory()
  },

  // 加载医生信息
  loadDoctorInfo() {
    this.setData({ loading: true })

    // 模拟医生数据
    const mockDoctorInfo = {
      id: this.doctorId,
      name: '张医生',
      title: '主任医师',
      department: '妇产科',
      hospital: '妇幼保健院',
      avatar: 'https://randomuser.me/api/portraits/women/32.jpg',
      online: true
    }

    setTimeout(() => {
      this.setData({
        doctorInfo: mockDoctorInfo,
        loading: false
      })
      
      // 设置页面标题
      wx.setNavigationBarTitle({
        title: `${mockDoctorInfo.name}医生`
      })
    }, 500)

    // 实际接口调用示例（注释掉）
    /*
    wx.request({
      url: `https://your-api.com/doctors/${this.doctorId}`,
      method: 'GET',
      success: (res) => {
        if (res.data.success) {
          this.setData({
            doctorInfo: res.data.doctor,
            loading: false
          })
          wx.setNavigationBarTitle({
            title: `${res.data.doctor.name}医生`
          })
        } else {
          wx.showToast({
            title: '获取医生信息失败',
            icon: 'none'
          })
          this.setData({ loading: false })
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
        this.setData({ loading: false })
      }
    })
    */
  },

  // 加载聊天历史
  loadChatHistory() {
    this.setData({ connecting: true })

    // 模拟聊天记录
    const mockMessages = [
      {
        id: 1,
        content: '您好，我是张医生，请问有什么可以帮助您的？',
        type: 'received',
        time: '10:00',
        date: '今天'
      },
      {
        id: 2,
        content: '医生您好，我现在怀孕3个月了，最近总是感觉恶心，正常吗？',
        type: 'sent',
        time: '10:02',
        date: '今天'
      },
      {
        id: 3,
        content: '您好，孕早期恶心是很常见的妊娠反应，通常在怀孕6周左右开始，12周左右逐渐减轻。如果恶心不严重，建议您：1. 少食多餐，避免空腹；2. 避免油腻、辛辣、刺激性食物；3. 可以尝试吃一些苏打饼干、姜片等缓解症状；4. 保持充足的休息。如果恶心严重影响到正常饮食和生活，建议及时就医。',
        type: 'received',
        time: '10:05',
        date: '今天'
      }
    ]

    setTimeout(() => {
      this.setData({
        messages: mockMessages,
        connecting: false
      })
      this.scrollToBottom()
    }, 1000)

    // 实际接口调用示例（注释掉）
    /*
    wx.request({
      url: 'https://your-api.com/chat/history',
      method: 'GET',
      data: {
        doctorId: this.doctorId
      },
      success: (res) => {
        if (res.data.success) {
          this.setData({
            messages: res.data.messages,
            connecting: false
          })
          this.scrollToBottom()
        } else {
          wx.showToast({
            title: '获取聊天记录失败',
            icon: 'none'
          })
          this.setData({ connecting: false })
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
        this.setData({ connecting: false })
      }
    })
    */
  },

  // 输入内容变化
  onInput(e) {
    this.setData({
      inputValue: e.detail.value
    })
  },

  // 发送消息
  sendMessage() {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { inputValue, messages } = this.data
    
    if (!inputValue.trim()) {
      return
    }

    // 添加新消息
    const now = new Date()
    const time = `${now.getHours().toString().padStart(2, '0')}:${now.getMinutes().toString().padStart(2, '0')}`
    const newMessage = {
      id: Date.now(),
      content: inputValue.trim(),
      type: 'sent',
      time: time,
      date: '今天'
    }

    const updatedMessages = [...messages, newMessage]
    
    this.setData({
      messages: updatedMessages,
      inputValue: ''
    })

    this.scrollToBottom()

    // 模拟医生回复
    setTimeout(() => {
      const replyMessages = [
        '好的，我了解了您的情况。',
        '请问您还有其他症状吗？',
        '根据您的描述，建议您注意休息，保持良好的心态。',
        '如果症状加重，建议您及时到医院就诊。',
        '祝您孕期愉快，宝宝健康成长！'
      ]
      
      const randomReply = replyMessages[Math.floor(Math.random() * replyMessages.length)]
      const replyNow = new Date()
      const replyTime = `${replyNow.getHours().toString().padStart(2, '0')}:${replyNow.getMinutes().toString().padStart(2, '0')}`
      
      const doctorReply = {
        id: Date.now() + 1,
        content: randomReply,
        type: 'received',
        time: replyTime,
        date: '今天'
      }

      this.setData({
        messages: [...updatedMessages, doctorReply]
      })
      
      this.scrollToBottom()
    }, 2000)

    // 实际接口调用示例（注释掉）
    /*
    wx.request({
      url: 'https://your-api.com/chat/send',
      method: 'POST',
      data: {
        doctorId: this.doctorId,
        content: inputValue.trim()
      },
      success: (res) => {
        if (!res.data.success) {
          wx.showToast({
            title: '发送消息失败，请重试',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误，请重试',
          icon: 'none'
        })
      }
    })
    */
  },

  // 滚动到底部
  scrollToBottom() {
    setTimeout(() => {
      this.setData({
        scrollTop: 99999
      })
    }, 100)
  },

  // 滚动事件
  onScroll(e) {
    this.scrollTop = e.detail.scrollTop
  },

  // 点击发送按钮
  handleSend() {
    this.sendMessage()
  },

  // 键盘发送
  handleConfirm() {
    this.sendMessage()
  }
})
