// knowledge.js
Page({
  data: {
    categories: [
      { id: 'all', name: '全部' },
      { id: 'early', name: '孕早期' },
      { id: 'middle', name: '孕中期' },
      { id: 'late', name: '孕晚期' },
      { id: 'nutrition', name: '孕期营养' },
      { id: 'exercise', name: '孕期运动' },
      { id: 'health', name: '孕期保健' }
    ],
    currentCategory: 'all',
    articles: [],
    allArticles: [], // 存储所有文章，用于本地分类
    loading: true,
    // 模态框相关数据
    showModal: false,
    currentArticle: null
  },

  onLoad() {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    this.fetchArticles().catch(err => {
      console.error('获取文章失败:', err)
    })
  },

  // 获取文章列表
  fetchArticles() {
    this.setData({ loading: true })

    // 返回Promise，解决异步操作问题
    return new Promise((resolve, reject) => {
      // 构建请求参数
      const requestData = {
        page: 1,
        size: 100 // 一次获取所有文章
      }
      
      wx.request({
        url: 'http://localhost:8080/api/knowledge',
        method: 'GET',
        data: requestData,
        success: (res) => {
          console.log('API响应数据:', res.data)
          
          // 假设API返回的数据格式为：{ articles: [...], total: 100, page: 1, size: 10 }
          // 这里根据实际API返回格式进行调整
          if (res.statusCode === 200 && res.data) {
            // 处理返回的文章数据，确保每个文章都有必要的字段
            const processedArticles = res.data.data.records
              .filter(article => article.status === 1) // 只展示status=1的文章
              .map(article => ({
                id: article.id || '',
                title: article.title || '无标题',
                category: article.category || '',
                categoryName: this.getCategoryName(article.category) || '',
                // 如果没有封面图片，使用基于文章ID的随机图片
                cover: article.cover || `https://picsum.photos/seed/pregnancy${article.id}/600/400`,
                content: article.content || '',
                readCount: article.readCount || 0,
                publishDate: article.publishDate || '',
                createdTime: article.createdTime || '' // 保存创建时间
              }))
            
            this.setData({
              allArticles: processedArticles, // 保存所有文章
              articles: processedArticles, // 初始显示所有文章
              loading: false
            })
            resolve()
          } else {
            wx.showToast({
              title: '获取文章列表失败',
              icon: 'none'
            })
            this.setData({ loading: false })
            reject(new Error('获取文章列表失败'))
          }
        },
        fail: (err) => {
          console.error('网络请求失败:', err)
          wx.showToast({
            title: '网络错误',
            icon: 'none'
          })
          this.setData({ loading: false })
          reject(err)
        }
      })
    })
  },
  
  // 根据分类值获取分类名称
  getCategoryName(categoryValue) {
    const { categories } = this.data
    
    // 直接返回categoryValue作为分类名称，因为API返回的已经是中文分类名
    return categoryValue || ''
  },
  
  // 切换分类
  switchCategory(e) {
    const { categoryId } = e.currentTarget.dataset

    if (categoryId !== this.data.currentCategory) {
      this.setData({
        currentCategory: categoryId,
        loading: true
      })
      
      // 根据分类ID过滤文章
      const { allArticles } = this.data
      let filteredArticles = allArticles
      
      if (categoryId !== 'all') {
        // 分类ID与API返回的中文category值的映射关系
        const categoryMap = {
          'early': '孕早期',
          'middle': '孕中期',
          'late': '孕晚期',
          'nutrition': '孕期营养',
          'exercise': '孕期运动',
          'health': '孕期保健'
        }
        
        // 获取当前分类ID对应的中文category值
        const targetCategory = categoryMap[categoryId]
        
        // 过滤出与目标category匹配的文章
        filteredArticles = allArticles.filter(article => article.category === targetCategory)
      }
      
      // 更新显示的文章列表
      this.setData({
        articles: filteredArticles,
        loading: false
      })
    }
  },

  // 查看文章详情
  viewArticleDetail(e) {
    const app = getApp()
    if (!app.validateLogin()) {
      return
    }
    
    const { articleId } = e.currentTarget.dataset
    const article = this.data.articles.find(a => a.id === articleId)
    
    if (article) {
      // 显示模态框，展示文章详情
      this.setData({
        showModal: true,
        currentArticle: article
      })
    }
  },
  
  // 关闭模态框
  closeModal() {
    this.setData({
      showModal: false,
      currentArticle: null
    })
  },

  // 下拉刷新
  onPullDownRefresh() {
    this.fetchArticles().then(() => {
      wx.stopPullDownRefresh()
    }).catch(err => {
      console.error('刷新失败:', err)
      wx.stopPullDownRefresh()
    })
  }
})
