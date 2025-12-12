// doctor.js
Page({
  data: {
    doctors: [],
    filteredDoctors: [], // 过滤后的医生列表
    loading: true,
    currentPage: 1,
    hasMore: true,
    searchKeyword: '', // 搜索关键词
    // 模态框相关数据
    showModal: false,
    currentDoctor: null,
    message: ''
  },

  onLoad() {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    this.fetchDoctors()
  },

  // 获取医生列表
  fetchDoctors() {
    const { currentPage } = this.data

    // 模拟接口调用获取医生列表
    this.setData({ loading: true })

    // 模拟数据
    const mockDoctors = [
      {
        id: 1,
        name: '张医生',
        title: '主任医师',
        department: '产科',
        hospital: '北京妇产医院',
        avatar: 'https://randomuser.me/api/portraits/women/1.jpg',
        rating: 4.8,
        patients: 1256,
        introduction: '从事产科临床工作20年，擅长高危妊娠管理、产前诊断等。'
      },
      {
        id: 2,
        name: '李医生',
        title: '副主任医师',
        department: '产科',
        hospital: '上海第一妇婴保健院',
        avatar: 'https://randomuser.me/api/portraits/women/2.jpg',
        rating: 4.6,
        patients: 987,
        introduction: '擅长产科急危重症抢救、妊娠期糖尿病管理等。'
      },
      {
        id: 3,
        name: '王医生',
        title: '主治医师',
        department: '产科',
        hospital: '广州妇幼保健院',
        avatar: 'https://randomuser.me/api/portraits/men/3.jpg',
        rating: 4.5,
        patients: 765,
        introduction: '擅长正常分娩、剖宫产手术等。'
      },
      {
        id: 4,
        name: '刘医生',
        title: '主任医师',
        department: '妇科',
        hospital: '北京协和医院',
        avatar: 'https://randomuser.me/api/portraits/women/4.jpg',
        rating: 4.9,
        patients: 2134,
        introduction: '从事妇科临床工作25年，擅长妇科肿瘤、子宫内膜异位症等疾病的诊治。'
      },
      {
        id: 5,
        name: '陈医生',
        title: '副主任医师',
        department: '妇科',
        hospital: '上海瑞金医院',
        avatar: 'https://randomuser.me/api/portraits/women/5.jpg',
        rating: 4.7,
        patients: 1567,
        introduction: '擅长妇科微创手术、月经不调等疾病的诊治。'
      }
    ]

    // setTimeout(() => {
    //   const doctors = currentPage === 1 ? mockDoctors : [...this.data.doctors, ...mockDoctors]
    //   this.setData({
    //     doctors: doctors,
    //     filteredDoctors: doctors,
    //     loading: false,
    //     hasMore: currentPage < 3 // 模拟只有3页数据
    //   })
    // }, 1000)
    const app = getApp()
    // 实际接口调用示例（注释掉）
    wx.request({
      url: app.globalData.baseUrl+"/api/doctors",
      method: 'GET',
      data: {
        page: currentPage,
        pageSize: 10
      },
      success: (res) => {
        if (res.data.code == 200) {
          const doctors = currentPage === 1 ? res.data.data.records : [...this.data.records, ...res.data.records]
          for(let i in doctors){
            doctors[i].patients = doctors[i].consultationCount
            doctors[i].rating = doctors[i].score
          }
          this.setData({
            doctors: doctors,
            filteredDoctors: doctors,
            loading: false,
            hasMore: res.data.hasMore
          })
        } else {
          wx.showToast({
            title: '获取医生列表失败',
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
  },

  // 搜索输入处理
  onSearchInput(e) {
    this.setData({
      searchKeyword: e.detail.value
    })
  },

  // 搜索按钮点击
  onSearch() {
    const { searchKeyword } = this.data
    
    // 搜索内容为空时，重新请求接口拉取数据
    if (!searchKeyword.trim()) {
      this.setData({
        currentPage: 1,
        hasMore: true
      })
      this.fetchDoctors()
      return
    }
    
    this.filterDoctors()
  },

  // 过滤医生列表（仅保留搜索功能）
  filterDoctors() {
    const { doctors, searchKeyword } = this.data
    let filtered = doctors

    // 搜索过滤
    if (searchKeyword) {
      const keyword = searchKeyword.toLowerCase()
      filtered = filtered.filter(doctor => 
        doctor.name.toLowerCase().includes(keyword) ||
        doctor.department.toLowerCase().includes(keyword) ||
        doctor.hospital.toLowerCase().includes(keyword)
      )
    }

    this.setData({
      filteredDoctors: filtered,
      currentPage: 1, // 重置页码
      hasMore: false // 搜索时不显示加载更多
    })
    
    // 搜索完成后滚动到页面顶部
    wx.pageScrollTo({
      scrollTop: 0,
      duration: 300
    })
  },

  // 预约医生
  makeAppointment(e) {
    const app = getApp()
    if (!app.validateLogin(false)) {
      return
    }
    
    const { doctorId } = e.currentTarget.dataset
    
    // 构建API请求参数
    const requestData = {
      userId: app.globalData.userId || 5, // 从全局数据获取userId，如果没有则使用默认值5
      doctorId: doctorId,
      appointmentTime: "2025-12-25T10:00:00", // 固定预约时间
      remark: "常规产检" // 固定备注
    }
    
    // 显示加载提示
    wx.showLoading({
      title: '预约中...'
    })
    
    // 实际API调用
    wx.request({
      url: app.globalData.baseUrl ? app.globalData.baseUrl + '/api/appointments' : 'http://localhost:8080/api/appointments',
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      data: requestData,
      success: (res) => {
        if (res.statusCode === 200 || res.statusCode === 201) {
          wx.showToast({
            title: '预约成功',
            icon: 'success'
          })
        } else {
          wx.showToast({
            title: res.data.message || '预约失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
      },
      complete: () => {
        // 隐藏加载提示
        wx.hideLoading()
      }
    })
  },

  // 查看医生详情
  viewDoctorDetail(e) {
    const app = getApp()
    if (app.validateLogin()) {
      const { doctorId } = e.currentTarget.dataset
      
      wx.navigateTo({
        url: `/pages/doctorDetail/doctorDetail?doctorId=${doctorId}`
      })
    }
  },

  // 咨询医生
  consultDoctor(e) {
    const app = getApp()
    if (app.validateLogin()) {
      const { doctorId, doctorName } = e.currentTarget.dataset
      
      // 显示咨询模态框
      this.setData({
        showModal: true,
        currentDoctor: {
          id: doctorId,
          name: doctorName
        },
        message: ''
      })
    }
  },
  
  // 关闭模态框
  closeModal() {
    this.setData({
      showModal: false
    })
  },
  
  // 输入留言内容
  inputMessage(e) {
    this.setData({
      message: e.detail.value
    })
  },
  
  // 提交咨询留言
  submitConsultation() {
    const app = getApp()
    if (!app.validateLogin(false)) {
      this.closeModal()
      return
    }
    
    const { currentDoctor, message } = this.data
    
    if (!message.trim()) {
      wx.showToast({
        title: '请输入留言内容',
        icon: 'none'
      })
      return
    }
    
    // 构建API请求参数
    const requestData = {
      userId: app.globalData.userId || 5, // 从全局数据获取userId，如果没有则使用默认值5
      doctorId: currentDoctor.id,
      title: `关于${currentDoctor.name}医生的咨询`, // 自动生成标题
      content: message,
      status: 0
    }
    
    // 显示加载提示
    wx.showLoading({
      title: '提交中...'
    })
    
    // 实际API调用
    wx.request({
      url: app.globalData.baseUrl ? app.globalData.baseUrl + '/api/consultations' : 'http://localhost:8080/api/consultations',
      method: 'POST',
      header: {
        'Content-Type': 'application/json'
      },
      data: requestData,
      success: (res) => {
        if (res.statusCode === 200 || res.statusCode === 201) {
          wx.showToast({
            title: '提交成功',
            icon: 'success'
          })
          this.closeModal()
        } else {
          wx.showToast({
            title: res.data.message || '提交失败',
            icon: 'none'
          })
        }
      },
      fail: (err) => {
        console.error('提交咨询失败:', err)
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
      },
      complete: () => {
        wx.hideLoading()
      }
    })
  },

  // 下拉刷新
  onPullDownRefresh() {
    this.setData({
      currentPage: 1,
      hasMore: true,
      searchKeyword: ''
    })
    this.fetchDoctors().then(() => {
      wx.stopPullDownRefresh()
    })
  },

  // 上拉加载更多
  onReachBottom() {
    const { loading, hasMore, currentPage, searchKeyword } = this.data
    
    if (!loading && hasMore && !searchKeyword) {
      this.setData({
        currentPage: currentPage + 1
      })
      this.fetchDoctors()
    }
  }
})
