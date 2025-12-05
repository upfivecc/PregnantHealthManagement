<template>
  <div class="knowledge">
    <div class="card">
      <div class="card-header">
        <div class="card-title">知识管理</div>
        <button class="btn btn-primary" @click="showAddKnowledgeModal">
          <i class="fas fa-plus"></i> 添加知识
        </button>
      </div>
      <div class="card-body">
        <!-- 搜索表单 -->
        <div class="form-row">
          <div class="form-group">
            <label>标题</label>
            <input type="text" class="form-control" v-model="searchForm.title" placeholder="请输入标题">
          </div>
          <div class="form-group">
            <label>分类</label>
            <input type="text" class="form-control" v-model="searchForm.category" placeholder="请输入分类">
          </div>
          <div class="form-group search-buttons">
            <label>&nbsp;</label>
            <div class="button-group">
              <button class="btn btn-primary" @click="searchKnowledge">
                <i class="fas fa-search"></i> 查询
              </button>
              <button class="btn btn-outline" @click="resetSearch">
                <i class="fas fa-refresh"></i> 重置
              </button>
            </div>
          </div>
        </div>
        
        <div class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>标题</th>
                <th>分类</th>
                <th>作者</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="knowledge in knowledgeList" :key="knowledge.id">
                <td>{{ knowledge.id }}</td>
                <td>{{ knowledge.title }}</td>
                <td>{{ knowledge.category }}</td>
                <td>{{ knowledge.author }}</td>
                <td><span class="tag" :class="knowledge.status === 1 ? 'tag-success' : 'tag-warning'">{{ knowledge.status === 1 ? '已发布' : '草稿' }}</span></td>
                <td>{{ formatDate(knowledge.createdTime) }}</td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-knowledge" @click="viewKnowledge(knowledge.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline edit-knowledge" @click="editKnowledge(knowledge.id)">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger delete-knowledge" @click="deleteKnowledge(knowledge.id)">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div class="pagination">
          <div class="page-info">显示 {{ pagination.start }} 到 {{ pagination.end }} 条记录，共 {{ pagination.total }} 条</div>
          <div class="page-controls">
            <button class="btn btn-outline" @click="prevPage" :disabled="pagination.currentPage <= 1">
              <i class="fas fa-chevron-left"></i>
            </button>
            <button class="btn btn-primary">{{ pagination.currentPage }}</button>
            <button class="btn btn-outline" @click="nextPage" :disabled="pagination.currentPage >= pagination.totalPages">
              <i class="fas fa-chevron-right"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 知识查看详情模态框 -->
    <div class="modal" :style="{ display: showDetailModal ? 'flex' : 'none' }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>知识详情</h3>
          <button class="btn btn-outline" @click="closeDetailModal">&times;</button>
        </div>
        <div class="modal-body">
          <div class="detail-item">
            <span class="detail-label">ID</span>
            <span class="detail-value">{{ detailData.id }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">标题</span>
            <span class="detail-value">{{ detailData.title }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">分类</span>
            <span class="detail-value">{{ detailData.category }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">作者</span>
            <span class="detail-value">{{ detailData.author }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">状态</span>
            <span class="detail-value">{{ detailData.status === 1 ? '已发布' : '草稿' }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">创建时间</span>
            <span class="detail-value">{{ formatDate(detailData.createdTime) }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">内容</span>
            <span class="detail-value">{{ detailData.content }}</span>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" @click="closeDetailModal">关闭</button>
        </div>
      </div>
    </div>
    
    <!-- 知识编辑模态框 -->
    <div class="modal" :style="{ display: showKnowledgeModal ? 'flex' : 'none' }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>{{ modalTitle }}</h3>
          <button class="btn btn-outline" @click="closeKnowledgeModal">&times;</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveKnowledge">
            <input type="hidden" v-model="knowledgeForm.id">
            <div class="form-group">
              <label>标题</label>
              <input type="text" class="form-control" v-model="knowledgeForm.title" required>
            </div>
            <div class="form-group">
              <label>分类</label>
              <input type="text" class="form-control" v-model="knowledgeForm.category" required>
            </div>
            <div class="form-group">
              <label>作者</label>
              <input type="text" class="form-control" v-model="knowledgeForm.author" required>
            </div>
            <div class="form-group">
              <label>状态</label>
              <select class="form-control" v-model="knowledgeForm.status">
                <option value="1">已发布</option>
                <option value="0">草稿</option>
              </select>
            </div>
            <div class="form-group">
              <label>内容</label>
              <textarea class="form-control" v-model="knowledgeForm.content" rows="5" required></textarea>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeKnowledgeModal">取消</button>
          <button class="btn btn-primary" @click="saveKnowledge">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'Knowledge',
  setup() {
    // 数据
    const knowledgeList = ref([])
    const showKnowledgeModal = ref(false)
    const showDetailModal = ref(false)
    const modalTitle = ref('添加知识')
    
    const detailData = ref({})
    
    
    const searchForm = reactive({
      title: '',
      category: ''
    })
    
    const knowledgeForm = reactive({
      id: '',
      title: '',
      category: '',
      author: '',
      status: '1',
      content: ''
    })
    
    const pagination = reactive({
      currentPage: 1,
      pageSize: 10,
      total: 0,
      totalPages: 0,
      start: 0,
      end: 0
    })
    
    // 格式化日期
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }
    
    // 加载知识列表
    const loadKnowledgeList = async (page = 1) => {
      try {
        const params = {
          page: page,
          size: pagination.pageSize
        }
        
        if (searchForm.title) {
          params.title = searchForm.title
        }
        
        if (searchForm.category) {
          params.category = searchForm.category
        }
        
        const response = await axios.get('/api/knowledge', { params })
        if (response.data.code === 200) {
          knowledgeList.value = response.data.data.records
          updatePagination(response.data.data)
        }
      } catch (error) {
        console.error('加载知识列表失败:', error)
      }
    }
    
    // 更新分页信息
    const updatePagination = (pageData) => {
      pagination.currentPage = pageData.currentPage
      pagination.total = pageData.total
      pagination.totalPages = Math.ceil(pageData.total / pageData.pageSize)
      pagination.start = (pageData.currentPage - 1) * pageData.pageSize + 1
      pagination.end = Math.min(pageData.currentPage * pageData.pageSize, pageData.total)
    }
    
    // 搜索知识
    const searchKnowledge = () => {
      loadKnowledgeList(1)
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.title = ''
      searchForm.category = ''
      loadKnowledgeList(1)
    }
    
    // 上一页
    const prevPage = () => {
      if (pagination.currentPage > 1) {
        loadKnowledgeList(pagination.currentPage - 1)
      }
    }
    
    // 下一页
    const nextPage = () => {
      if (pagination.currentPage < pagination.totalPages) {
        loadKnowledgeList(pagination.currentPage + 1)
      }
    }
    
    // 显示添加知识模态框
    const showAddKnowledgeModal = () => {
      modalTitle.value = '添加知识'
      // 重置表单
      Object.keys(knowledgeForm).forEach(key => {
        if (key === 'status') {
          knowledgeForm[key] = '1'
        } else {
          knowledgeForm[key] = ''
        }
      })
      showKnowledgeModal.value = true
    }
    
    // 关闭知识模态框
    const closeKnowledgeModal = () => {
      showKnowledgeModal.value = false
    }
    
    // 关闭详情模态框
    const closeDetailModal = () => {
      showDetailModal.value = false
    }
    
    // 查看知识详情
    const viewKnowledge = async (knowledgeId) => {
      try {
        const response = await axios.get(`/api/knowledge/${knowledgeId}`)
        if (response.data.code === 200) {
          detailData.value = response.data.data
          showDetailModal.value = true
        }
      } catch (error) {
        console.error('获取知识详情失败:', error)
      }
    }
    
    // 编辑知识
    const editKnowledge = async (knowledgeId) => {
      try {
        const response = await axios.get(`/api/knowledge/${knowledgeId}`)
        if (response.data.code === 200) {
          const knowledge = response.data.data
          // 填充表单数据
          Object.keys(knowledgeForm).forEach(key => {
            knowledgeForm[key] = knowledge[key] || ''
          })
          modalTitle.value = '编辑知识'
          showKnowledgeModal.value = true
        }
      } catch (error) {
        console.error('获取知识信息失败:', error)
      }
    }
    
    // 删除知识
    const deleteKnowledge = async (knowledgeId) => {
      if (confirm('确定要删除这个知识吗？')) {
        try {
          const response = await axios.delete(`/api/knowledge/${knowledgeId}`)
          if (response.data.code === 200) {
            alert('删除成功')
            loadKnowledgeList(pagination.currentPage)
          } else {
            alert('删除失败: ' + response.data.message)
          }
        } catch (error) {
          console.error('删除知识失败:', error)
          alert('删除失败')
        }
      }
    }
    
    // 保存知识
    const saveKnowledge = async () => {
      try {
        let response
        if (knowledgeForm.id) {
          // 更新知识
          response = await axios.put(`/api/knowledge/${knowledgeForm.id}`, knowledgeForm)
        } else {
          // 添加知识
          response = await axios.post('/api/knowledge', knowledgeForm)
        }
        
        if (response.data.code === 200) {
          alert('保存成功')
          closeKnowledgeModal()
          loadKnowledgeList(pagination.currentPage)
        } else {
          alert('保存失败: ' + response.data.message)
        }
      } catch (error) {
        console.error('保存知识失败:', error)
        alert('保存失败')
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadKnowledgeList(1)
    })
    
    return {
      knowledgeList,
      searchForm,
      pagination,
      knowledgeForm,
      detailData,
      showKnowledgeModal,
      showDetailModal,
      modalTitle,
      searchKnowledge,
      resetSearch,
      prevPage,
      nextPage,
      showAddKnowledgeModal,
      viewKnowledge,
      editKnowledge,
      saveKnowledge,
      closeKnowledgeModal,
      closeDetailModal,
      deleteKnowledge,
      formatDate
    }
  }
}
</script>

<style scoped>
.knowledge {
  padding: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.card-title {
  margin: 0;
  font-size: 1.2rem;
  font-weight: 600;
  color: #303133;
}

.card-body {
  padding: 20px;
}

.form-row {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
  flex-wrap: wrap;
}

.search-buttons {
  display: flex;
  flex-direction: column;
}

.button-group {
  display: flex;
  gap: 10px;
  white-space: nowrap;
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-group label {
  font-weight: 500;
  color: #333;
}

.form-group.search-buttons label {
  visibility: hidden;
}

.form-control {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

textarea.form-control {
  resize: vertical;
}

.btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.btn-primary {
  background: linear-gradient(135deg, #ff6b8b, #ff8fab);
  color: white;
  box-shadow: 0 2px 10px rgba(255, 107, 139, 0.2);
}

.btn-primary:hover {
  background: linear-gradient(135deg, #ff5271, #ff7591);
  transform: translateY(-1px);
  box-shadow: 0 4px 15px rgba(255, 107, 139, 0.3);
}

.btn-outline {
  background: transparent;
  border: 1px solid #ddd;
  color: #666;
}

.btn-outline:hover {
  background: #f5f5f5;
}

.btn-danger {
  background: linear-gradient(135deg, #ff5271, #ff7591);
  color: white;
  box-shadow: 0 2px 10px rgba(255, 82, 113, 0.2);
}

.btn-danger:hover {
  background: linear-gradient(135deg, #ff3860, #ff5c79);
  transform: translateY(-1px);
  box-shadow: 0 4px 15px rgba(255, 82, 113, 0.3);
}

.tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.tag-success {
  background: #ff8fab;
  color: white;
}

.tag-warning {
  background: #ffc0cb;
  color: #ff6b8b;
}

.table-container {
  overflow-x: auto;
  margin-bottom: 20px;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.table th {
  background: #f5f7fa;
  font-weight: 600;
  color: #909399;
}

.table-actions {
  display: flex;
  gap: 5px;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-info {
  color: #666;
}

.page-controls {
  display: flex;
  gap: 5px;
  align-items: center;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-dialog {
  background: white;
  border-radius: 8px;
  width: 100%;
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #eee;
}

.modal-header h3 {
  margin: 0;
  font-size: 1.2rem;
  color: #333;
}

.modal-body {
  padding: 20px;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
  border-top: 1px solid #eee;
}

.detail-item {
  display: flex;
  margin-bottom: 15px;
}

.detail-label {
  width: 100px;
  font-weight: 600;
  color: #333;
}

.detail-value {
  flex: 1;
  color: #666;
}
</style>