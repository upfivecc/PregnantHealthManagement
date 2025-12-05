<template>
  <div class="appointments">
    <div class="card">
      <div class="card-header">
        <div class="card-title">预约管理</div>
      </div>
      <div class="card-body">
        <!-- 添加查询表单 -->
        <div class="form-row">
          <div class="form-group">
            <label>用户姓名</label>
            <input type="text" class="form-control" v-model="searchForm.userRealName" placeholder="请输入用户姓名">
          </div>
          <div class="form-group search-buttons">
            <label>&nbsp;</label>
            <div class="button-group">
              <button class="btn btn-primary" @click="searchAppointments">
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
                <th>用户</th>
                <th>医院</th>
                <th>科室</th>
                <th>医生职称</th>
                <th>预约时间</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="appointment in appointmentList" :key="appointment.id">
                <td>{{ appointment.id }}</td>
                <td>{{ appointment.userName }}</td>
                <td>{{ appointment.hospital }}</td>
                <td>{{ appointment.department }}</td>
                <td>{{ appointment.title }}</td>
                <td>{{ formatDate(appointment.appointmentTime) }}</td>
                <td><span class="tag" :class="appointment.status === 1 ? 'tag-success' : 'tag-warning'">{{ appointment.status === 1 ? '已确认' : '待确认' }}</span></td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-appointment" @click="viewAppointment(appointment.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline edit-appointment" @click="editAppointment(appointment.id)">
                      <i class="fas fa-edit"></i>
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
    
    <!-- 预约详情模态框 -->
    <div class="modal" :style="{ display: showDetailModal ? 'block' : 'none' }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>预约详情</h3>
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
            <label>医院:</label>
            <span>{{ detailData.hospital }}</span>
          </div>
          <div class="detail-item">
            <label>科室:</label>
            <span>{{ detailData.department }}</span>
          </div>
          <div class="detail-item">
            <label>医生职称:</label>
            <span>{{ detailData.title }}</span>
          </div>
          <div class="detail-item">
            <label>预约时间:</label>
            <span>{{ formatDate(detailData.appointmentTime) }}</span>
          </div>
          <div class="detail-item">
            <label>状态:</label>
            <span>{{ detailData.status === 1 ? '已确认' : '待确认' }}</span>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeDetailModal">关闭</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'Appointments',
  setup() {
    // 数据
    const appointmentList = ref([])
    const showDetailModal = ref(false)
    const detailData = ref({})
    
    const searchForm = reactive({
      userRealName: ''
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
    
    // 加载预约列表
    const loadAppointmentList = async (page = 1) => {
      try {
        const params = {
          page: page,
          size: pagination.pageSize
        }
        
        if (searchForm.userRealName) {
          params.userRealName = searchForm.userRealName
        }
        
        const response = await axios.get('/api/appointments', { params })
        if (response.data.code === 200) {
          appointmentList.value = response.data.data.records
          updatePagination(response.data.data)
        }
      } catch (error) {
        console.error('加载预约列表失败:', error)
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
    
    // 搜索预约
    const searchAppointments = () => {
      loadAppointmentList(1)
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.userRealName = ''
      loadAppointmentList(1)
    }
    
    // 上一页
    const prevPage = () => {
      if (pagination.currentPage > 1) {
        loadAppointmentList(pagination.currentPage - 1)
      }
    }
    
    // 下一页
    const nextPage = () => {
      if (pagination.currentPage < pagination.totalPages) {
        loadAppointmentList(pagination.currentPage + 1)
      }
    }
    
    // 查看预约详情
    const viewAppointment = async (appointmentId) => {
      try {
        const response = await axios.get(`/api/appointments/${appointmentId}`)
        if (response.data.code === 200) {
          detailData.value = response.data.data
          showDetailModal.value = true
        }
      } catch (error) {
        console.error('获取预约详情失败:', error)
      }
    }
    
    // 关闭详情模态框
    const closeDetailModal = () => {
      showDetailModal.value = false
    }
    
    // 编辑预约
    const editAppointment = async (appointmentId) => {
      try {
        const response = await axios.get(`/api/appointments/${appointmentId}`)
        if (response.data.code === 200) {
          const appointment = response.data.data
          // 这里可以打开编辑模态框并填充数据
          alert(`预约信息:
ID: ${appointment.id}
用户: ${appointment.userName}
医院: ${appointment.hospital}
科室: ${appointment.department}
医生职称: ${appointment.title}
预约时间: ${formatDate(appointment.appointmentTime)}
状态: ${appointment.status === 1 ? '已确认' : '待确认'}

这里应该打开编辑模态框，但为了简化示例，我们只显示信息。`)
        }
      } catch (error) {
        console.error('获取预约信息失败:', error)
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadAppointmentList(1)
    })
    
    return {
      appointmentList,
      showDetailModal,
      detailData,
      searchForm,
      pagination,
      formatDate,
      searchAppointments,
      resetSearch,
      prevPage,
      nextPage,
      viewAppointment,
      closeDetailModal,
      editAppointment
    }
  }
}
</script>

<style scoped>
.appointments {
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
  max-width: 500px;
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