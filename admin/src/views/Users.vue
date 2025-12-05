<template>
  <div class="users">
    <div class="card">
      <div class="card-header">
        <div class="card-title">用户管理</div>
        <button class="btn btn-primary" @click="showAddUserModal">
          <i class="fas fa-plus"></i> 添加用户
        </button>
      </div>
      <div class="card-body">
        <div class="form-row">
          <div class="form-group">
            <label>用户名</label>
            <input type="text" class="form-control" v-model="searchForm.username" placeholder="请输入用户名">
          </div>
          <div class="form-group">
            <label>角色</label>
            <select class="form-control" v-model="searchForm.role">
              <option value="">全部</option>
              <option value="USER">普通用户</option>
              <option value="DOCTOR">医生</option>
              <option value="ADMIN">管理员</option>
            </select>
          </div>
          <div class="form-group">
            <label>&nbsp;</label>
            <button class="btn btn-primary" @click="searchUsers">
              <i class="fas fa-search"></i> 查询
            </button>
          </div>
        </div>
        
        <div class="table-container">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>邮箱</th>
                <th>真实姓名</th>
                <th>角色</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="user in userList" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.email || '' }}</td>
                <td>{{ user.realName || '' }}</td>
                <td><span class="tag tag-primary">{{ getRoleName(user.role) }}</span></td>
                <td><span class="tag" :class="user.status === 1 ? 'tag-success' : 'tag-danger'">{{ user.status === 1 ? '启用' : '禁用' }}</span></td>
                <td>
                  <div class="table-actions">
                    <button class="btn btn-outline view-user" @click="viewUser(user.id)">
                      <i class="fas fa-eye"></i>
                    </button>
                    <button class="btn btn-outline edit-user" @click="editUser(user.id)">
                      <i class="fas fa-edit"></i>
                    </button>
                    <button class="btn btn-danger delete-user" @click="deleteUser(user.id)">
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
    
    <!-- 用户查看详情模态框 -->
    <div class="modal" :class="{ 'modal-show': showDetailModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>用户详情</h3>
          <button class="btn btn-outline" @click="closeDetailModal">&times;</button>
        </div>
        <div class="modal-body">
          <div class="detail-item">
            <label>ID:</label>
            <span>{{ detailData.id }}</span>
          </div>
          <div class="detail-item">
            <label>用户名:</label>
            <span>{{ detailData.username }}</span>
          </div>
          <div class="detail-item">
            <label>邮箱:</label>
            <span>{{ detailData.email || '无' }}</span>
          </div>
          <div class="detail-item">
            <label>真实姓名:</label>
            <span>{{ detailData.realName || '无' }}</span>
          </div>
          <div class="detail-item">
            <label>角色:</label>
            <span>{{ getRoleName(detailData.role) }}</span>
          </div>
          <div class="detail-item">
            <label>状态:</label>
            <span>{{ detailData.status === 1 ? '启用' : '禁用' }}</span>
          </div>
        </div>
        <div class="modal-footer">
          <button class="btn btn-primary" @click="closeDetailModal">关闭</button>
        </div>
      </div>
    </div>

    <!-- 用户编辑模态框 -->
    <div class="modal" :class="{ 'modal-show': showUserModal }">
      <div class="modal-dialog">
        <div class="modal-header">
          <h3>{{ modalTitle }}</h3>
          <button class="btn btn-outline" @click="closeUserModal">&times;</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="saveUser">
            <input type="hidden" v-model="userForm.id">
            <div class="form-group">
              <label>用户名</label>
              <input type="text" class="form-control" v-model="userForm.username" required>
            </div>
            <div class="form-group">
              <label>密码</label>
              <input type="password" class="form-control" v-model="userForm.password" :required="!userForm.id">
            </div>
            <div class="form-group">
              <label>邮箱</label>
              <input type="email" class="form-control" v-model="userForm.email">
            </div>
            <div class="form-group">
              <label>真实姓名</label>
              <input type="text" class="form-control" v-model="userForm.realName">
            </div>
            <div class="form-group">
              <label>角色</label>
              <select class="form-control" v-model="userForm.role">
                <option value="USER">普通用户</option>
                <option value="DOCTOR">医生</option>
                <option value="ADMIN">管理员</option>
              </select>
            </div>
            <div class="form-group">
              <label>状态</label>
              <select class="form-control" v-model="userForm.status">
                <option value="1">启用</option>
                <option value="0">禁用</option>
              </select>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button class="btn btn-outline" @click="closeUserModal">取消</button>
          <button class="btn btn-primary" @click="saveUser">保存</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'

export default {
  name: 'Users',
  setup() {
    // 数据
    const userList = ref([])
    const showUserModal = ref(false)
    const showDetailModal = ref(false)
    const detailData = reactive({
      id: '',
      username: '',
      email: '',
      realName: '',
      role: '',
      status: 1
    })
    const modalTitle = ref('添加用户')
    
    const searchForm = reactive({
      username: '',
      role: ''
    })
    
    const userForm = reactive({
      id: '',
      username: '',
      password: '',
      email: '',
      realName: '',
      role: 'USER',
      status: '1'
    })
    
    const pagination = reactive({
      currentPage: 1,
      pageSize: 10,
      total: 0,
      totalPages: 0,
      start: 0,
      end: 0
    })
    
    // 获取角色名称
    const getRoleName = (role) => {
      const roles = {
        'USER': '普通用户',
        'DOCTOR': '医生',
        'ADMIN': '管理员'
      }
      return roles[role] || role
    }
    
    // 加载用户列表
    const loadUserList = async (page = 1) => {
      try {
        const params = {
          page: page,
          size: pagination.pageSize
        }
        
        if (searchForm.username) {
          params.username = searchForm.username
        }
        
        if (searchForm.role) {
          params.role = searchForm.role
        }
        
        const response = await axios.get('/api/users', { params })
        if (response.data.code === 200) {
          userList.value = response.data.data.records
          updatePagination(response.data.data)
        }
      } catch (error) {
        console.error('加载用户列表失败:', error)
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
    
    // 搜索用户
    const searchUsers = () => {
      loadUserList(1)
    }
    
    // 上一页
    const prevPage = () => {
      if (pagination.currentPage > 1) {
        loadUserList(pagination.currentPage - 1)
      }
    }
    
    // 下一页
    const nextPage = () => {
      if (pagination.currentPage < pagination.totalPages) {
        loadUserList(pagination.currentPage + 1)
      }
    }
    
    // 显示添加用户模态框
    const showAddUserModal = () => {
      modalTitle.value = '添加用户'
      // 重置表单
      Object.keys(userForm).forEach(key => {
        if (key === 'role') {
          userForm[key] = 'USER'
        } else if (key === 'status') {
          userForm[key] = '1'
        } else {
          userForm[key] = ''
        }
      })
      showUserModal.value = true
    }
    
    // 关闭用户模态框
    const closeUserModal = () => {
      showUserModal.value = false
    }
    
    // 查看用户详情
    const viewUser = async (userId) => {
      try {
        const response = await axios.get(`/api/users/${userId}`)
        if (response.data.code === 200) {
          const user = response.data.data
          Object.keys(detailData).forEach(key => {
            detailData[key] = user[key] || ''
          })
          showDetailModal.value = true
        }
      } catch (error) {
        console.error('获取用户详情失败:', error)
      }
    }

    // 关闭详情模态框
    const closeDetailModal = () => {
      showDetailModal.value = false
    }
    
    // 编辑用户
    const editUser = async (userId) => {
      try {
        const response = await axios.get(`/api/users/${userId}`)
        if (response.data.code === 200) {
          const user = response.data.data
          // 填充表单数据
          Object.keys(userForm).forEach(key => {
            userForm[key] = user[key] || ''
          })
          modalTitle.value = '编辑用户'
          showUserModal.value = true
        }
      } catch (error) {
        console.error('获取用户信息失败:', error)
      }
    }
    
    // 删除用户
    const deleteUser = async (userId) => {
      if (confirm('确定要删除这个用户吗？')) {
        try {
          const response = await axios.delete(`/api/users/${userId}`)
          if (response.data.code === 200) {
            alert('删除成功')
            loadUserList(pagination.currentPage)
          } else {
            alert('删除失败: ' + response.data.message)
          }
        } catch (error) {
          console.error('删除用户失败:', error)
          alert('删除失败')
        }
      }
    }
    
    // 保存用户
    const saveUser = async () => {
      try {
        let response
        if (userForm.id) {
          // 更新用户
          response = await axios.put(`/api/users/${userForm.id}`, userForm)
        } else {
          // 添加用户
          response = await axios.post('/api/users', userForm)
        }
        
        if (response.data.code === 200) {
          alert('保存成功')
          closeUserModal()
          loadUserList(pagination.currentPage)
        } else {
          alert('保存失败: ' + response.data.message)
        }
      } catch (error) {
        console.error('保存用户失败:', error)
        alert('保存失败')
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadUserList(1)
    })
    
    return {
      userList,
      showUserModal,
      showDetailModal,
      detailData,
      modalTitle,
      searchForm,
      userForm,
      pagination,
      getRoleName,
      searchUsers,
      prevPage,
      nextPage,
      showAddUserModal,
      closeUserModal,
      closeDetailModal,
      viewUser,
      editUser,
      deleteUser,
      saveUser
    }
  }
}
</script>

<style scoped>
.users {
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

.tag {
  padding: 4px 8px;
  border-radius: 4px;
  font-size: 12px;
  font-weight: 500;
}

.tag-primary {
  background: #ff8fab;
  color: white;
}

.tag-success {
  background: #ffc0cb;
  color: #ff6b8b;
}

.tag-danger {
  background: #F56C6C;
  color: white;
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
</style>