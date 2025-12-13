// record.js
Page({
  data: {
    // 表单数据
    formData: {
      height: '',
      weight: '',
      systolicPressure: '',
      diastolicPressure: '',
      fetalMovements: '',
      recordDate: ''
    },
    // 历史记录
    records: [],
    // 图表数据
    chartData: {
      weights: [],
      dates: []
    },
    // 加载状态
    loading: false
  },

  onLoad() {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    // 设置默认日期为今天
    const today = new Date()
    const recordDate = today.getFullYear() + '-' + 
      String(today.getMonth() + 1).padStart(2, '0') + '-' + 
      String(today.getDate()).padStart(2, '0')
    
    this.setData({
      'formData.recordDate': recordDate
    })
    
    // 加载历史记录
    this.loadRecords()
  },

  // 输入身高
  onHeightInput(e) {
    this.setData({
      'formData.height': e.detail.value
    })
  },

  // 输入体重
  onWeightInput(e) {
    this.setData({
      'formData.weight': e.detail.value
    })
  },

  // 输入收缩压
  onSystolicPressureInput(e) {
    this.setData({
      'formData.systolicPressure': e.detail.value
    })
  },

  // 输入舒张压
  onDiastolicPressureInput(e) {
    this.setData({
      'formData.diastolicPressure': e.detail.value
    })
  },

  // 输入胎动次数
  onFetalMovementsInput(e) {
    this.setData({
      'formData.fetalMovements': e.detail.value
    })
  },

  // 选择日期
  onDateChange(e) {
    this.setData({
      'formData.recordDate': e.detail.value
    })
  },

  // 提交记录
  submitRecord() {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { formData } = this.data
    
    // 表单验证
    if (!formData.recordDate) {
      wx.showToast({ title: '请选择记录日期', icon: 'none' })
      return
    }
    
    // 可以选择记录任意一项或多项
    if (!formData.height && !formData.weight && 
        !formData.systolicPressure && !formData.diastolicPressure && 
        !formData.fetalMovements) {
      wx.showToast({ title: '请至少记录一项数据', icon: 'none' })
      return
    }
    
    // 血压数据验证
    if ((formData.systolicPressure && !formData.diastolicPressure) || 
        (!formData.systolicPressure && formData.diastolicPressure)) {
      wx.showToast({ title: '请完整输入血压数据', icon: 'none' })
      return
    }
    
    this.setData({ loading: true })
    
    // 构造请求数据
    const requestData = {
      userId: app.globalData.userInfo.id,
      height: formData.height ? parseFloat(formData.height) : null,
      weight: formData.weight ? parseFloat(formData.weight) : null,
      bloodPressureHigh: formData.systolicPressure ? parseInt(formData.systolicPressure) : null,
      bloodPressureLow: formData.diastolicPressure ? parseInt(formData.diastolicPressure) : null,
      fetalMovementCount: formData.fetalMovements ? parseInt(formData.fetalMovements) : null,
      recordDate: formData.recordDate,
      remark: ""
    };
    
    console.log('提交的数据:', requestData);
    
    // 调用后端接口保存记录
    wx.request({
      url: app.globalData.baseUrl + '/api/mini-program/pregnancy-records',
      method: 'POST',
      data: requestData,
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        this.setData({ loading: false })
        console.log('提交记录响应:', res);
        // 修正判断条件，使用code===200而不是success属性
        if (res.data.code === 200) {
          wx.showToast({ title: '记录成功', icon: 'success' })
          // 重置表单并重新加载记录
          this.resetForm()
          this.loadRecords()
        } else {
          wx.showToast({ title: res.data.message || '记录失败', icon: 'none' })
        }
      },
      fail: (err) => {
        this.setData({ loading: false })
        console.error('提交记录失败:', err)
        wx.showToast({ title: '网络错误', icon: 'none' })
      }
    })
  },

  // 加载历史记录
  loadRecords() {
    const app = getApp();
    if (!app.validateLogin()) {
      return;
    }
    
    console.log('开始加载历史记录，用户ID:', app.globalData.userInfo.id);
    
    // 调用后端接口获取记录
    wx.request({
      url: app.globalData.baseUrl + '/api/mini-program/pregnancy-records/my?userId=' + app.globalData.userInfo.id,
      method: 'GET',
      header: {
        'content-type': 'application/json'
      },
      success: (res) => {
        console.log('获取孕期档案返回数据:', res); // 调试信息
        // 修正判断条件，使用code===200而不是success属性
        if (res.data.code === 200) {
          // 检查返回的数据是否为数组
          if (!Array.isArray(res.data.data)) {
            console.error('返回的数据不是数组格式:', res.data.data);
            this.loadFromLocalStorage();
            return;
          }
          
          console.log('从后端获取的原始记录:', res.data.data);
          
          // 转换数据格式以匹配前端显示
          const records = res.data.data.map(record => {
            // 处理日期格式，确保是字符串
            let recordDate = '';
            if (record.recordDate) {
              if (typeof record.recordDate === 'object' && record.recordDate.year !== undefined) {
                // 处理Java LocalDate对象格式 {year: 2025, month: 12, day: 1}
                recordDate = `${record.recordDate.year}-${String(record.recordDate.month).padStart(2, '0')}-${String(record.recordDate.day).padStart(2, '0')}`;
              } else if (record.recordDate instanceof Date) {
                // 处理JavaScript Date对象
                const date = new Date(record.recordDate);
                recordDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
              } else {
                // 直接使用字符串格式
                recordDate = record.recordDate;
              }
            }
            
            return {
              id: record.id,
              height: record.height !== null && record.height !== undefined ? record.height.toString() : '',
              weight: record.weight !== null && record.weight !== undefined ? record.weight.toString() : '',
              systolicPressure: record.bloodPressureHigh !== null && record.bloodPressureHigh !== undefined ? record.bloodPressureHigh.toString() : '',
              diastolicPressure: record.bloodPressureLow !== null && record.bloodPressureLow !== undefined ? record.bloodPressureLow.toString() : '',
              fetalMovements: record.fetalMovementCount !== null && record.fetalMovementCount !== undefined ? record.fetalMovementCount.toString() : '',
              recordDate: recordDate
            };
          });
          
          console.log('转换后的记录:', records); // 调试信息
          console.log('记录数量:', records.length); // 调试信息
          this.setData({ records: records })
          this.updateChartData(records)
        } else {
          // 如果获取失败，尝试从本地存储加载
          console.log('获取记录失败，尝试从本地存储加载'); // 调试信息
          this.loadFromLocalStorage();
        }
      },
      fail: (err) => {
        // 网络错误时，尝试从本地存储加载
        console.log('网络错误，尝试从本地存储加载'); // 调试信息
        console.error('网络请求失败:', err);
        this.loadFromLocalStorage();
      }
    })
  },
  
  // 从本地存储加载记录（备用方案）
  loadFromLocalStorage() {
    console.log('从本地存储加载记录');
    // 从本地存储加载记录
    const records = wx.getStorageSync('pregnancyRecords') || []
    console.log('本地存储中的记录:', records);
    console.log('本地存储记录数量:', records.length);
    
    // 如果没有记录，使用模拟数据
    if (records.length === 0) {
      const mockRecords = [
        {
          id: 1,
          height: '165',
          weight: '62.5',
          systolicPressure: '110',
          diastolicPressure: '70',
          fetalMovements: '8',
          recordDate: '2025-12-01'
        },
        {
          id: 2,
          height: '',
          weight: '63.0',
          systolicPressure: '115',
          diastolicPressure: '72',
          fetalMovements: '10',
          recordDate: '2025-12-02'
        }
      ]
      wx.setStorageSync('pregnancyRecords', mockRecords)
      this.setData({ records: mockRecords })
      this.updateChartData(mockRecords)
    } else {
      this.setData({ records })
      this.updateChartData(records)
    }
  },

  // 更新图表数据
  updateChartData(records) {
    console.log('更新图表数据，记录数量:', records.length); // 调试信息
    // 提取体重和日期数据用于图表
    const weights = []
    const dates = []
    
    // 按日期排序
    const sortedRecords = [...records].sort((a, b) => new Date(a.recordDate) - new Date(b.recordDate))
    
    // 只取最近10条有体重记录的数据
    sortedRecords
      .filter(record => record.weight)
      .slice(-10)
      .forEach(record => {
        weights.push(parseFloat(record.weight))
        dates.push(record.recordDate)
      })
    
    // 预计算图表所需的数据
    const chartPoints = []
    if (weights.length > 0) {
      const minWeight = Math.min(...weights)
      const maxWeight = Math.max(...weights)
      const weightRange = maxWeight - minWeight || 1 // 避免除以零
      const step = weights.length > 1 ? 100 / (weights.length - 1) : 0
      
      weights.forEach((weight, index) => {
        // 计算位置（百分比）
        const left = index * step
        const bottom = ((weight - minWeight) / weightRange * 80) + 10 // 10% 的底部边距
        
        chartPoints.push({
          weight: weight,
          date: dates[index],
          left: left,
          bottom: bottom
        })
      })
    }
    
    this.setData({
      'chartData.weights': weights,
      'chartData.dates': dates,
      'chartData.points': chartPoints
    })
  },

  // 查看记录详情
  viewRecordDetail(e) {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { recordId } = e.currentTarget.dataset
    const record = this.data.records.find(r => r.id === recordId)
    
    if (record) {
      // 显示记录详情
      let detail = `记录日期：${record.recordDate}\n`
      if (record.height) detail += `身高：${record.height}cm\n`
      if (record.weight) detail += `体重：${record.weight}kg\n`
      if (record.systolicPressure && record.diastolicPressure) {
        detail += `血压：${record.systolicPressure}/${record.diastolicPressure}mmHg\n`
      }
      if (record.fetalMovements) detail += `胎动次数：${record.fetalMovements}次`
      
      wx.showModal({
        title: '记录详情',
        content: detail,
        showCancel: false
      })
    }
  },

  // 删除记录
  deleteRecord(e) {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { recordId } = e.currentTarget.dataset
    
    wx.showModal({
      title: '确认删除',
      content: '确定要删除这条记录吗？',
      success: (res) => {
        if (res.confirm) {
          // 调用后端接口删除记录
          wx.request({
            url: app.globalData.baseUrl + '/api/pregnancy-records/' + recordId,
            method: 'DELETE',
            header: {
              'content-type': 'application/json'
            },
            success: (res) => {
              console.log('删除记录响应:', res);
              // 修正判断条件，使用code===200而不是success属性
              if (res.data.code === 200) {
                // 从前端数组中删除记录
                const updatedRecords = this.data.records.filter(r => r.id !== recordId)
                
                // 更新本地存储
                wx.setStorageSync('pregnancyRecords', updatedRecords)
                
                this.setData({ records: updatedRecords })
                
                // 更新图表数据
                this.updateChartData(updatedRecords)
                
                wx.showToast({ title: '删除成功', icon: 'success' })
              } else {
                wx.showToast({ title: res.data.message || '删除失败', icon: 'none' })
              }
            },
            fail: (err) => {
              console.error('删除记录失败:', err)
              wx.showToast({ title: '网络错误', icon: 'none' })
            }
          });
        }
      }
    })
  },
  
  // 重置表单
  resetForm() {
    const today = new Date()
    const recordDate = today.getFullYear() + '-' + 
      String(today.getMonth() + 1).padStart(2, '0') + '-' + 
      String(today.getDate()).padStart(2, '0')
      
    this.setData({
      'formData.height': '',
      'formData.weight': '',
      'formData.systolicPressure': '',
      'formData.diastolicPressure': '',
      'formData.fetalMovements': '',
      'formData.recordDate': recordDate
    })
  }
})