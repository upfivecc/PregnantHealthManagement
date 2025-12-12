// index.js
Page({
  data: {
  },

  // 页面加载时检查用户信息
  onLoad() {
  },

  // 导航到预约医生页面
  navigateToDoctor() {
    const app = getApp()
    if (app.validateLogin()) {
      wx.switchTab({
        url: '../doctor/doctor'
      })
    }
  },

  // 导航到孕期档案页面
  navigateToRecord() {
    const app = getApp()
    if (app.validateLogin()) {
      wx.switchTab({
        url: '../record/record'
      })
    }
  },

  // 导航到孕期知识页面
  navigateToKnowledge() {
    const app = getApp()
    if (app.validateLogin()) {
      wx.switchTab({
        url: '../knowledge/knowledge'
      })
    }
  },

  // 导航到在线咨询页面
  navigateToConsult() {
    const app = getApp()
    if (app.validateLogin()) {
      wx.navigateTo({
        url: '../consult/consult'
      })
    }
  },

})

