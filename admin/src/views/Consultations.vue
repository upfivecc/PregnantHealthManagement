<template>
  <div class="consultations">
    <div class="card">
      <div class="card-header">
        <div class="card-title">咨询管理</div>
      </div>
      <div class="card-body">
        <!-- 添加查询表单 -->
        <div class="form-row">
          <div class="form-group">
            <label>标题</label>
            <input type="text" class="form-control" v-model="searchForm.title" placeholder="请输入标题">
          </div>
          <div class="form-group">
            <label>状态</label>
            <select class="form-control" v-model="searchForm.status">
              <option value="">全部</option>
              <option value="0">未回复</option>
              <option value="1">已回复</option>
              <option value="2">已关闭</option>
            </select>
          </div>
          <div class="form-group">
            <label>&nbsp;</label>
            <button class="btn btn-primary" @click="searchConsultations">
              <i class="fas fa-search"></i> 查询
            </button>
            <button class="btn btn-outline" @click="resetSearch">
              <i class="fas fa-refresh"></i> 重置
            </button>
          </div>
        </div>
        
        <div class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>用户</th>
                <th>医生</th>
                <th>标题</th>
                <th>状态</th>
                <th>创建时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="consultation in consultationList" :key="consultation.id">
                <td>{{ consultation.id }}</td>
                <td>{{ consultation.userName }}</td>
                <td>{{ consultation.doctorName }}</td>
                <td>{{ consultation.title }}</td>
                <td>
                  <span class="tag" :class="getStatusClass(consultation.status)">
                    {{ getStatusText(consultation.status) }}
                  </span>
                </td>
                <td>{{ formatDate(consultation.createTime) }}</td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-consultation" @click="viewConsultation(consultation.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline reply-consultation" @click="replyConsultation(consultation.id)" v-if="consultation.status !== 1">
                      <i class="fas fa-reply"></i>
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
    
    <!-- 咨询详情模态框 -->
    <div class="modal" :style="{ display: showDetailModal ? 'block' : 'none' }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>咨询详情</h3>
          <button class="btn btn-outline" @click="closeDetailModal">&times;</button>
        </div>
        <div class="modal-body">
          <div class="detail-item">
            <label>ID:</label>
            <span>{{ detailData.id }}</span>
          </div>
          <div class="detail-item">
            <label>用户:</label>
            <span>{{ detailData.userName }}</span>
          </div>
          <div class="detail-item">
            <label>医生:</label>
            <span>{{ detailData.doctorName }}</span>
          </div>
          <div class="detail-item">
            <label>标题:</label>
            <span>{{ detailData.title }}</span>
          </div>
          <div class="detail-item">
            <label>内容:</label>
            <span>{{ detailData.content }}</span>
          </div>
          <div class="detail-item">
            <label>回复:</label>
            <span>{{ detailData.reply || '暂无回复' }}</span>
          </div>
          <div class="detail-item">
            <label>状态:</label>
            <span>{{ getStatusText(detailData.status) }}</span>
          </div>
          <div class="detail-item">
            <label>创建时间:</label>
            <span>{{ formatDate(detailData.createTime) }}</span>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeDetailModal">关闭</button>
        </div>
      </div>
    </div>
    
    <!-- 回复咨询模态框 -->
    <div class="modal" :style="{ display: showReplyModal ? 'block' : 'none' }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>回复咨询</h3>
          <button class="btn btn-outline" @click="closeReplyModal">&times;</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveReply">
            <input type="hidden" v-model="replyForm.id">
            <div class="form-group">
              <label>标题</label>
              <input type="text" class="form-control" :value="replyForm.title" disabled>
            </div>
            <div class="form-group">
              <label>内容</label>
              <textarea class="form-control" :value="replyForm.content" rows="3" disabled></textarea>
            </div>
            <div class="form-group">
              <label>回复内容</label>
              <textarea class="form-control" v-model="replyForm.reply" rows="5" required></textarea>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeReplyModal">取消</button>
          <button class="btn btn-primary" @click="saveReply">保存回复</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'Consultations',
  setup() {
    // 数据
    const consultationList = ref([])
    const showDetailModal = ref(false)
    const showReplyModal = ref(false)
    const detailData = ref({})
    
    const searchForm = reactive({
      title: '',
      status: ''
    })
    
    const replyForm = reactive({
      id: '',
      title: '',
      content: '',
      reply: ''
    })
    
    const pagination = reactive({
      currentPage: 1,
      pageSize: 10,
      total: 0,
      totalPages: 0,
      start: 0,
      end: 0
    })
    
    // 获取状态文本
    const getStatusText = (status) => {
      const statusMap = {
        0: '未回复',
        1: '已回复',
        2: '已关闭'
      }
      return statusMap[status] || '未知'
    }
    
    // 获取状态样式类
    const getStatusClass = (status) => {
      const classMap = {
        0: 'tag-warning',
        1: 'tag-success',
        2: 'tag-danger'
      }
      return classMap[status] || ''
    }
    
    // 格式化日期
    const formatDate = (dateString) => {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    }
    
    // 加载咨询列表
    const loadConsultationList = async (page = 1) => {
      try {
        const params = {
          page: page,
          size: pagination.pageSize
        }
        
        if (searchForm.title) {
          params.title = searchForm.title
        }
        
        if (searchForm.status !== '') {
          params.status = searchForm.status
        }
        
        const response = await axios.get('/api/consultations', { params })
        if (response.data.code === 200) {
          consultationList.value = response.data.data.records
          updatePagination(response.data.data)
        }
      } catch (error) {
        console.error('加载咨询列表失败:', error)
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
    
    // 搜索咨询
    const searchConsultations = () => {
      loadConsultationList(1)
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.title = ''
      searchForm.status = ''
      loadConsultationList(1)
    }
    
    // 上一页
    const prevPage = () => {
      if (pagination.currentPage > 1) {
        loadConsultationList(pagination.currentPage - 1)
      }
    }
    
    // 下一页
    const nextPage = () => {
      if (pagination.currentPage < pagination.totalPages) {
        loadConsultationList(pagination.currentPage + 1)
      }
    }
    
    // 查看咨询详情
    const viewConsultation = async (consultationId) => {
      try {
        const response = await axios.get(`/api/consultations/${consultationId}`)
        if (response.data.code === 200) {
          detailData.value = response.data.data
          showDetailModal.value = true
        }
      } catch (error) {
        console.error('获取咨询详情失败:', error)
      }
    }
    
    // 关闭详情模态框
    const closeDetailModal = () => {
      showDetailModal.value = false
    }
    
    // 回复咨询
    const replyConsultation = async (consultationId) => {
      try {
        const response = await axios.get(`/api/consultations/${consultationId}`)
        if (response.data.code === 200) {
          const consultation = response.data.data
          // 填充回复表单数据
          replyForm.id = consultation.id
          replyForm.title = consultation.title
          replyForm.content = consultation.content
          replyForm.reply = consultation.reply || ''
          showReplyModal.value = true
        }
      } catch (error) {
        console.error('获取咨询信息失败:', error)
      }
    }
    
    // 关闭回复模态框
    const closeReplyModal = () => {
      showReplyModal.value = false
    }
    
    // 保存回复
    const saveReply = async () => {
      try {
        const response = await axios.put(`/api/consultations/${replyForm.id}/reply`, {
          reply: replyForm.reply
        })
        
        if (response.data.code === 200) {
          alert('回复成功')
          closeReplyModal()
          loadConsultationList(pagination.currentPage)
        } else {
          alert('回复失败: ' + response.data.message)
        }
      } catch (error) {
        console.error('回复咨询失败:', error)
        alert('回复失败')
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadConsultationList(1)
    })
    
    return {
      consultationList,
      showDetailModal,
      showReplyModal,
      detailData,
      searchForm,
      replyForm,
      pagination,
      getStatusText,
      getStatusClass,
      formatDate,
      searchConsultations,
      resetSearch,
      prevPage,
      nextPage,
      viewConsultation,
      closeDetailModal,
      replyConsultation,
      closeReplyModal,
      saveReply
    }
  }
}
</script>

<style scoped>
.consultations {
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

.form-group {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.form-group label {
  font-weight: 500;
  color: #333;
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
  background: #409EFF;
  color: white;
}

.btn-primary:hover {
  background: #337ecc;
}

.btn-outline {
  background: transparent;
  border: 1px solid #ddd;
  color: #666;
}

.btn-outline:hover {
  background: #f5f5f5;
}

.tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.tag-success {
  background: #67C23A;
  color: white;
}

.tag-warning {
  background: #E6A23C;
  color: white;
}

.tag-danger {
  background: #F56C6C;
  color: white;
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

.detail-item {
  display: flex;
  margin-bottom: 15px;
}

.detail-item label {
  font-weight: 600;
  width: 100px;
  color: #333;
}

.detail-item span {
  flex: 1;
  color: #666;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  padding: 20px;
  border-top: 1px solid #eee;
}
</style>