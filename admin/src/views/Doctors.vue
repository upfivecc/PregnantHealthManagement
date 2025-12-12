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
        <!-- 添加查询表单 -->
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
                <th>用户ID</th>
                <th>医生姓名</th>
                <th>医院</th>
                <th>科室</th>
                <th>职称</th>
                <th>专长</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="doctor in doctorList" :key="doctor.id">
                <td>{{ doctor.id }}</td>
                <td>{{ doctor.userId }}</td>
                <td>{{ doctor.name }}</td>
                <td>{{ doctor.hospital }}</td>
                <td>{{ doctor.department }}</td>
                <td>{{ doctor.title }}</td>
                <td>{{ doctor.specialty }}</td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-doctor" @click="viewDoctor(doctor.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline edit-doctor" @click="editDoctor(doctor.id)">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger delete-doctor" @click="deleteDoctor(doctor.id)">
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
    
    <!-- 医生查看详情模态框 -->
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
          <div class="detail-item">
            <label>ID:</label>
            <span>{{ detailData.id }}</span>
          </div>
          <div class="detail-item">
            <label>用户ID:</label>
            <span>{{ detailData.userId }}</span>
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
            <label>简介:</label>
            <span>{{ detailData.introduction || '无' }}</span>
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
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" @click="closeDetailModal">关闭</button>
        </div>
      </div>
    </div>

    <!-- 医生编辑模态框 -->
    <div class="modal" :class="{ 'modal-show': showDoctorModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>{{ modalTitle }}</h3>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveDoctor">
            <input type="hidden" v-model="doctorForm.id">
            <div class="form-group">
              <label>用户ID</label>
              <input type="number" class="form-control" v-model="doctorForm.userId" required>
            </div>
            <div class="form-group">
              <label>医生姓名</label>
              <input type="text" class="form-control" v-model="doctorForm.name" required>
            </div>
            <div class="form-group">
              <label>医院</label>
              <input type="text" class="form-control" v-model="doctorForm.hospital" required>
            </div>
            <div class="form-group">
              <label>科室</label>
              <input type="text" class="form-control" v-model="doctorForm.department" required>
            </div>
            <div class="form-group">
              <label>职称</label>
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
            <div class="form-group">
              <label>评分 (1-10)</label>
              <div class="d-flex align-items-center">
                <input type="range" class="form-control-range mr-3" v-model.number="doctorForm.score" step="0.1" min="1" max="10" style="flex: 1;">
                <input type="number" class="form-control w-25" v-model.number="doctorForm.score" step="0.1" min="1" max="10" readonly>
              </div>
            </div>
            <div class="form-group">
              <label>接诊量</label>
              <input type="number" class="form-control" v-model="doctorForm.consultationCount" min="0">
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
            <div class="form-group">
              <label>医生照片</label>
              <div class="avatar-upload">
                <div class="avatar-preview" v-if="doctorForm.avatar">
                  <img :src="doctorForm.avatar" alt="医生照片" />
                </div>
                <div class="avatar-preview placeholder" v-else>
                  <i class="fas fa-user-circle"></i>
                  <span>预览</span>
                </div>
                <input type="file" id="avatar-input" class="avatar-input" ref="avatarInput" @change="handleAvatarUpload" accept="image/*">
                <label for="avatar-input" class="btn btn-outline avatar-btn">
                  <i class="fas fa-upload"></i> 选择照片
                </label>
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
    
    // 头像上传相关
    const avatarInput = ref(null)
    const avatarFile = ref(null)
    
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
      avatarFile.value = null
      showDoctorModal.value = true
    }
    
    // 关闭医生模态框
    const closeDoctorModal = () => {
      showDoctorModal.value = false
      // 重置文件上传状态
      if (avatarInput.value) {
        avatarInput.value.value = ''
      }
      avatarFile.value = null
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
          // 重置文件上传状态
          if (avatarInput.value) {
            avatarInput.value.value = ''
          }
          avatarFile.value = null
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
      console.log('------------------------------------------')
      console.log('saveDoctor方法开始执行')
      console.log('当前时间:', new Date().toISOString())
      console.log('avatarFile.value:', avatarFile.value)
      console.log('doctorForm:', doctorForm)
      console.log('formData准备构建...')
      
      let response
      const formData = new FormData()
      
      // 将表单数据添加到FormData，排除avatar字段（因为它是Base64字符串）
      Object.keys(doctorForm).forEach(key => {
        if (key !== 'avatar') {
          formData.append(key, doctorForm[key])
        }
      })
      
      // 如果有头像文件，添加到FormData
      if (avatarFile.value) {
        formData.append('avatar', avatarFile.value)
        // 调试：打印添加的文件信息
        console.log('添加的头像文件:', avatarFile.value)
      }
      // 调试：打印FormData中的所有字段
      console.log('FormData内容:')
      for (let pair of formData.entries()) {
        console.log(pair[0] + ': ' + pair[1])
      }
      
      const config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      
      console.log('准备发送请求...')
      console.log('请求URL:', doctorForm.id ? `/api/doctors/${doctorForm.id}` : '/api/doctors')
      console.log('请求方法:', doctorForm.id ? 'PUT' : 'POST')
      
      // 使用axios的拦截器来查看请求和响应的详细信息
      console.log('配置axios拦截器...')
      
      // 请求拦截器
      axios.interceptors.request.use(
        config => {
          console.log('请求配置:', config)
          return config
        },
        error => {
          console.error('请求错误:', error)
          return Promise.reject(error)
        }
      )
      
      // 响应拦截器
      axios.interceptors.response.use(
        response => {
          console.log('响应数据:', response)
          return response
        },
        error => {
          console.error('响应错误:', error)
          return Promise.reject(error)
        }
      )
      
      try {
        if (doctorForm.id) {
          // 更新医生
          console.log('发送PUT请求...')
          response = await axios.put(`/api/doctors/${doctorForm.id}`, formData, config)
        } else {
          // 添加医生
          console.log('发送POST请求...')
          response = await axios.post('/api/doctors', formData, config)
        }
        
        console.log('请求成功，响应数据:', response.data)
        
        if (response.data.code === 200) {
          alert('保存成功')
          closeDoctorModal()
          loadDoctorList(pagination.currentPage)
        } else {
          alert('保存失败: ' + response.data.message)
        }
      } catch (error) {
        console.error('保存医生失败:', error)
        console.error('错误详情:', error.response ? error.response : error)
        alert('保存失败')
      } finally {
        // 移除拦截器，避免重复添加
        axios.interceptors.request.clear()
        axios.interceptors.response.clear()
      }
    }
    
    // 处理头像上传
    const handleAvatarUpload = (event) => {
      console.log('文件选择事件触发')
      const file = event.target.files[0]
      if (file) {
        console.log('选择的文件:', file)
        avatarFile.value = file
        const reader = new FileReader()
        reader.onload = (e) => {
          console.log('文件读取完成:', e.target.result.substring(0, 100) + '...')
          doctorForm.avatar = e.target.result
        }
        reader.onerror = (error) => {
          console.error('文件读取错误:', error)
        }
        reader.readAsDataURL(file)
      } else {
        console.log('没有选择文件')
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
</style>