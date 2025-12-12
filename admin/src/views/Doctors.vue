<template>
  <div class="doctors">
    <div class="card">
      <div class="card-header">
        <div class="card-title">医生管理</div>
        <button class="btn btn-primary" @click="showAddDoctorModal">
          <i class="fas fa-plus"></i> 添加医生
        </button>
      </div>
      <div class="card-body">
        <!-- 搜索表单 -->
        <div class="form-row">
          <div class="form-group">
            <label>医生姓名</label>
            <input type="text" class="form-control" v-model="searchForm.realName" placeholder="请输入医生姓名">
          </div>
          <div class="form-group search-buttons">
            <label>&nbsp;</label>
            <div class="button-group">
              <button class="btn btn-primary" @click="searchDoctors">
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
                <th>医生姓名</th>
                <th>医院</th>
                <th>科室</th>
                <th>职称</th>
                <th>评分</th>
                <th>接诊量</th>
                <th>好评率</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="doctor in doctorList" :key="doctor.id">
                <td>{{ doctor.id }}</td>
                <td>{{ doctor.name }}</td>
                <td>{{ doctor.hospital }}</td>
                <td>{{ doctor.department }}</td>
                <td>{{ doctor.title }}</td>
                <td>{{ doctor.score }}</td>
                <td>{{ doctor.consultationCount }}</td>
                <td>{{ doctor.positiveRate }}%</td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-doctor" @click="viewDoctor(doctor.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline edit-doctor" @click="editDoctor(doctor.id)">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-outline delete-doctor" @click="deleteDoctor(doctor.id)">
                      <i class="fas fa-trash"></i>
                    </button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          
          <!-- 分页 -->
          <div class="pagination" v-if="pagination.totalPages > 1">
            <button class="btn btn-outline" :disabled="pagination.currentPage === 1" @click="prevPage">上一页</button>
            <span class="page-info">{{ pagination.start }}-{{ pagination.end }} 共 {{ pagination.total }} 条</span>
            <button class="btn btn-outline" :disabled="pagination.currentPage === pagination.totalPages" @click="nextPage">下一页</button>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 医生详情模态框 -->
    <div class="modal" :class="{ 'modal-show': showDetailModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>医生详情</h3>
          <button class="btn btn-outline" @click="closeDetailModal">&times;</button>
        </div>
        <div class="modal-body">
          <div class="detail-avatar" v-if="detailData.avatar">
            <img :src="detailData.avatar" alt="医生照片" />
          </div>
          <div class="detail-avatar placeholder" v-else>
            <i class="fas fa-user-circle" style="font-size: 100px;"></i>
          </div>
          <div class="detail-item">
            <label>ID:</label>
            <span>{{ detailData.id }}</span>
          </div>
          <div class="detail-item">
            <label>医生姓名:</label>
            <span>{{ detailData.name }}</span>
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
            <label>职称:</label>
            <span>{{ detailData.title }}</span>
          </div>
          <div class="detail-item">
            <label>专长:</label>
            <span>{{ detailData.specialty }}</span>
          </div>
          <div class="detail-item">
            <label>评分:</label>
            <span>{{ detailData.score }}</span>
          </div>
          <div class="detail-item">
            <label>接诊量:</label>
            <span>{{ detailData.consultationCount }}</span>
          </div>
          <div class="detail-item">
            <label>好评率:</label>
            <span>{{ detailData.positiveRate }}%</span>
          </div>
          <div class="detail-item">
            <label>简介:</label>
            <span>{{ detailData.introduction }}</span>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" @click="closeDetailModal">确定</button>
        </div>
      </div>
    </div>
    
    <!-- 添加/编辑医生模态框 -->
    <div class="modal" :class="{ 'modal-show': showDoctorModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>{{ modalTitle }}</h3>
          <button class="btn btn-outline" @click="closeDoctorModal">&times;</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveDoctor">
            <div class="form-group">
              <label>关联用户ID *</label>
              <input type="number" class="form-control" v-model="doctorForm.userId" required>
            </div>
            <div class="form-group">
              <label>医生姓名 *</label>
              <input type="text" class="form-control" v-model="doctorForm.name" required>
            </div>
            <div class="form-group">
              <label>医院 *</label>
              <input type="text" class="form-control" v-model="doctorForm.hospital" required>
            </div>
            <div class="form-group">
              <label>科室 *</label>
              <input type="text" class="form-control" v-model="doctorForm.department" required>
            </div>
            <div class="form-group">
              <label>职称 *</label>
              <input type="text" class="form-control" v-model="doctorForm.title" required>
            </div>
            <div class="form-group">
              <label>专长</label>
              <input type="text" class="form-control" v-model="doctorForm.specialty">
            </div>
            <div class="form-group">
              <label>简介</label>
              <textarea class="form-control" v-model="doctorForm.introduction" rows="3"></textarea>
            </div>
            <div class="form-row">
              <div class="form-group">
                <label>评分</label>
                <input type="number" class="form-control" v-model.number="doctorForm.score" step="0.1" min="0" max="10">
              </div>
              <div class="form-group">
                <label>接诊量</label>
                <input type="number" class="form-control" v-model.number="doctorForm.consultationCount" min="0">
              </div>
              <div class="form-group">
                <label>好评率</label>
                <div class="d-flex align-items-center">
                  <input type="range" class="form-control-range mr-3" v-model.number="doctorForm.positiveRate" step="0.1" min="0" max="100" style="flex: 1;">
                  <div class="input-group w-25">
                    <input type="number" class="form-control" v-model.number="doctorForm.positiveRate" step="0.1" min="0" max="100" readonly>
                    <div class="input-group-append">
                      <span class="input-group-text">%</span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="form-group">
              <label>医生照片URL</label>
              <input type="text" class="form-control" v-model="doctorForm.avatar" placeholder="请输入图片URL">
              <div class="avatar-preview mt-2" v-if="doctorForm.avatar">
                <img :src="doctorForm.avatar" alt="医生照片预览" style="max-width: 100px; max-height: 100px;" />
              </div>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeDoctorModal">取消</button>
          <button class="btn btn-primary" @click="$event.preventDefault(); saveDoctor()">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'Doctors',
  setup() {
    // 数据
    const doctorList = ref([])
    const showDoctorModal = ref(false)
    const showDetailModal = ref(false)
    const detailData = reactive({
      id: '',
      userId: '',
      name: '',
      hospital: '',
      department: '',
      title: '',
      specialty: '',
      introduction: '',
      score: 1,
      consultationCount: 0,
      positiveRate: 0,
      avatar: ''
    })
    const modalTitle = ref('添加医生')
    
    const searchForm = reactive({
      realName: ''
    })
    
    const doctorForm = reactive({
      id: '',
      userId: '',
      name: '',
      hospital: '',
      department: '',
      title: '',
      specialty: '',
      introduction: '',
      score: 1,
      consultationCount: 0,
      positiveRate: 0,
      avatar: ''
    })
    
    const pagination = reactive({
      currentPage: 1,
      pageSize: 10,
      total: 0,
      totalPages: 0,
      start: 0,
      end: 0
    })
    
    // 加载医生列表
    const loadDoctorList = async (page = 1) => {
      try {
        const params = {
          page: page,
          size: pagination.pageSize
        }
        
        if (searchForm.realName) {
          params.realName = searchForm.realName
        }
        
        const response = await axios.get('/api/doctors', { params })
        if (response.data.code === 200) {
          doctorList.value = response.data.data.records
          updatePagination(response.data.data)
        }
      } catch (error) {
        console.error('加载医生列表失败:', error)
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
    
    // 搜索医生
    const searchDoctors = () => {
      loadDoctorList(1)
    }
    
    // 重置搜索
    const resetSearch = () => {
      searchForm.realName = ''
      loadDoctorList(1)
    }
    
    // 上一页
    const prevPage = () => {
      if (pagination.currentPage > 1) {
        loadDoctorList(pagination.currentPage - 1)
      }
    }
    
    // 下一页
    const nextPage = () => {
      if (pagination.currentPage < pagination.totalPages) {
        loadDoctorList(pagination.currentPage + 1)
      }
    }
    
    // 显示添加医生模态框
    const showAddDoctorModal = () => {
      modalTitle.value = '添加医生'
      // 重置表单
      Object.keys(doctorForm).forEach(key => {
        // 数字字段重置为0，其他字段重置为空字符串
        if (key === 'score' || key === 'consultationCount' || key === 'positiveRate') {
          doctorForm[key] = 0
        } else {
          doctorForm[key] = ''
        }
      })
      showDoctorModal.value = true
    }
    
    // 关闭医生模态框
    const closeDoctorModal = () => {
      showDoctorModal.value = false
    }
    
    // 查看医生详情
    const viewDoctor = async (doctorId) => {
      try {
        const response = await axios.get(`/api/doctors/${doctorId}`)
        if (response.data.code === 200) {
          const doctor = response.data.data
          Object.keys(detailData).forEach(key => {
            detailData[key] = doctor[key] || ''
          })
          showDetailModal.value = true
        }
      } catch (error) {
        console.error('获取医生详情失败:', error)
      }
    }

    // 关闭详情模态框
    const closeDetailModal = () => {
      showDetailModal.value = false
    }
    
    // 编辑医生
    const editDoctor = async (doctorId) => {
      try {
        const response = await axios.get(`/api/doctors/${doctorId}`)
        if (response.data.code === 200) {
          const doctor = response.data.data
          // 填充表单数据
          Object.keys(doctorForm).forEach(key => {
            doctorForm[key] = doctor[key] || ''
          })
          modalTitle.value = '编辑医生'
          showDoctorModal.value = true
        }
      } catch (error) {
        console.error('获取医生信息失败:', error)
      }
    }
    
    // 删除医生
    const deleteDoctor = async (doctorId) => {
      if (confirm('确定要删除这个医生吗？')) {
        try {
          const response = await axios.delete(`/api/doctors/${doctorId}`)
          if (response.data.code === 200) {
            alert('删除成功')
            loadDoctorList(pagination.currentPage)
          } else {
            alert('删除失败: ' + response.data.message)
          }
        } catch (error) {
          console.error('删除医生失败:', error)
          alert('删除失败')
        }
      }
    }
    
    // 保存医生
    const saveDoctor = async () => {
      try {
        let response
        // 创建请求数据对象
        const requestData = {}
        Object.keys(doctorForm).forEach(key => {
          requestData[key] = doctorForm[key]
        })
        
        if (doctorForm.id) {
          // 更新医生
          response = await axios.put(`/api/doctors/${doctorForm.id}`, requestData)
        } else {
          // 添加医生
          response = await axios.post('/api/doctors', requestData)
        }
        
        if (response.data.code === 200) {
          alert('保存成功')
          closeDoctorModal()
          loadDoctorList(pagination.currentPage)
        } else {
          alert('保存失败: ' + response.data.message)
        }
      } catch (error) {
        console.error('保存医生失败:', error)
        alert('保存失败')
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadDoctorList(1)
    })
    
    return {
      doctorList,
      showDoctorModal,
      showDetailModal,
      modalTitle,
      searchForm,
      doctorForm,
      detailData,
      pagination,
      searchDoctors,
      resetSearch,
      prevPage,
      nextPage,
      showAddDoctorModal,
      closeDoctorModal,
      closeDetailModal,
      viewDoctor,
      editDoctor,
      deleteDoctor,
      saveDoctor
    }
  }
}
</script>

<style scoped>
.doctors {
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
  margin-bottom: 15px;
}

.form-group label {
  font-weight: 500;
  color: #333;
}

/* 添加 form-control 样式定义 */
.form-control {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.3s;
  width: 100%;
  box-sizing: border-box;
}

.form-control:focus {
  outline: none;
  border-color: #ff6b8b;
  box-shadow: 0 0 0 2px rgba(255, 107, 139, 0.2);
}

/* 添加数字输入框样式 */
.form-control[type="number"] {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
  width: 100%;
  box-sizing: border-box;
}

/* 添加范围选择器样式 */
.form-control-range {
  padding: 0;
  border: none;
}

.form-control-range:focus {
  outline: none;
  box-shadow: 0 0 0 2px rgba(255, 107, 139, 0.2);
}

.table-container {
  overflow-x: auto;
}

.table {
  width: 100%;
  border-collapse: collapse;
}

.table th,
.table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.table th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.table-actions {
  display: flex;
  gap: 5px;
}

.btn {
  padding: 8px 16px;
  border-radius: 4px;
  border: 1px solid transparent;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
  display: inline-flex;
  align-items: center;
  gap: 5px;
}

.btn-primary {
  background-color: #ff6b8b;
  color: white;
  border-color: #ff6b8b;
}

.btn-primary:hover {
  background-color: #ff5271;
  border-color: #ff5271;
}

.btn-outline {
  background-color: transparent;
  color: #666;
  border-color: #ddd;
}

.btn-outline:hover {
  background-color: #f5f7fa;
  border-color: #c0c4cc;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
  margin-top: 20px;
}

.page-info {
  color: #666;
  font-size: 14px;
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
  visibility: hidden;
  opacity: 0;
  transition: visibility 0s, opacity 0.3s ease;
}

.modal.modal-show {
  visibility: visible;
  opacity: 1;
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
  justify-content: flex-start;
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

.detail-item label {
  font-weight: 600;
  width: 100px;
  color: #333;
}

.detail-item span {
  flex: 1;
  color: #666;
  white-space: nowrap;
}

.detail-avatar {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.detail-avatar img {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #eee;
}

/* 头像上传样式 */
.avatar-upload {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: center;
}

.avatar-preview {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  overflow: hidden;
  border: 2px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: center;
}

.avatar-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.avatar-preview.placeholder {
  background: #f5f7fa;
  color: #909399;
  font-size: 24px;
}

.avatar-preview.placeholder span {
  font-size: 12px;
  margin-top: 5px;
  display: block;
  text-align: center;
}

.avatar-input {
  display: none;
}

.avatar-btn {
  cursor: pointer;
  padding: 8px 16px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 4px;
  color: #666;
  transition: all 0.3s;
}

.avatar-btn:hover {
  background-color: #f5f7fa;
  border-color: #c0c4cc;
}

/* Flexbox 工具类 */
.d-flex {
  display: flex;
}

.align-items-center {
  align-items: center;
}

.mr-3 {
  margin-right: 1rem;
}

/* 输入组样式 */
.input-group {
  display: flex;
  align-items: stretch;
}

.input-group .form-control {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
}

.input-group-append {
  display: flex;
}

.input-group-text {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-left: 0;
  border-radius: 0 4px 4px 0;
  background-color: #f8f9fa;
  white-space: nowrap;
}

.w-25 {
  width: 25%;
}
</style>