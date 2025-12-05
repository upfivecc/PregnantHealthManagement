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
    
    <!-- 医生编辑模态框 -->
    <div class="modal" :class="{ 'modal-show': showDoctorModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>{{ modalTitle }}</h3>
          <button class="btn btn-outline" @click="closeDoctorModal">&times;</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveDoctor">
            <input type="hidden" v-model="doctorForm.id">
            <div class="form-group">
              <label>用户ID</label>
              <input type="number" class="form-control" v-model="doctorForm.userId" required>
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
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeDoctorModal">取消</button>
          <button class="btn btn-primary" @click="saveDoctor">保存</button>
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
    const modalTitle = ref('添加医生')
    
    const searchForm = reactive({
      realName: ''
    })
    
    const doctorForm = reactive({
      id: '',
      userId: '',
      hospital: '',
      department: '',
      title: '',
      specialty: '',
      introduction: ''
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
        doctorForm[key] = ''
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
          alert(`医生详情:
ID: ${doctor.id}
用户ID: ${doctor.userId}
医院: ${doctor.hospital}
科室: ${doctor.department}
职称: ${doctor.title}
专长: ${doctor.specialty}
简介: ${doctor.introduction || ''}`)
        }
      } catch (error) {
        console.error('获取医生详情失败:', error)
      }
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
        if (doctorForm.id) {
          // 更新医生
          response = await axios.put(`/api/doctors/${doctorForm.id}`, doctorForm)
        } else {
          // 添加医生
          response = await axios.post('/api/doctors', doctorForm)
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
      modalTitle,
      searchForm,
      doctorForm,
      pagination,
      searchDoctors,
      resetSearch,
      prevPage,
      nextPage,
      showAddDoctorModal,
      closeDoctorModal,
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
</style>