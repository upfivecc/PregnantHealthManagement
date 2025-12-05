<template>
  <div class="login-container">
    <!-- 背景装饰元素 -->
    <div class="background-elements">
      <div class="decor-circle circle-1"></div>
      <div class="decor-circle circle-2"></div>
      <div class="decor-circle circle-3"></div>
      <div class="decor-hearts">
        <svg viewBox="0 0 24 24" class="heart-icon">
          <path fill="currentColor" d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z" />
        </svg>
        <svg viewBox="0 0 24 24" class="heart-icon">
          <path fill="currentColor" d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z" />
        </svg>
        <svg viewBox="0 0 24 24" class="heart-icon">
          <path fill="currentColor" d="M12,21.35L10.55,20.03C5.4,15.36 2,12.27 2,8.5C2,5.41 4.42,3 7.5,3C9.24,3 10.91,3.81 12,5.08C13.09,3.81 14.76,3 16.5,3C19.58,3 22,5.41 22,8.5C22,12.27 18.6,15.36 13.45,20.03L12,21.35Z" />
        </svg>
      </div>
    </div>

    <!-- 主要登录容器 -->
    <div class="login-wrapper">
      <!-- 左侧信息区域 -->
      <div class="login-info">
        <div class="info-content">
          <div class="logo">
            <svg viewBox="0 0 24 24" class="logo-icon">
              <path fill="currentColor" d="M12,2A10,10 0 0,1 22,12A10,10 0 0,1 12,22C10.5,22 9,21.75 7.7,21.2C7.9,20.8 8,20.4 8,20A2,2 0 0,0 6,18C5.7,18 5.45,18 5.2,18.05C3.25,18.36 1.75,17.13 1.42,15.2C1.34,14.9 1.3,14.6 1.3,14.3C1.3,13.6 1.5,12.9 1.85,12.3L3.8,14.3C3.6,14.9 3.5,15.5 3.5,16.2C3.5,17.45 4.45,18.5 5.75,18.5A2,2 0 0,0 7.75,16.5C7.75,16.3 7.7,16.1 7.65,15.9L6.25,14.5C6.45,14.35 6.6,14.15 6.7,13.9C6.7,13.9 6.7,13.9 6.7,13.9C6.95,13.85 7.2,13.8 7.45,13.8C9.55,13.8 11.2,15.45 11.2,17.55V18H14V17.55C14,15.45 15.65,13.8 17.75,13.8C18,13.8 18.25,13.85 18.5,13.9C18.5,13.9 18.5,13.9 18.5,13.9C18.6,14.15 18.75,14.35 18.95,14.5L17.55,15.9C17.5,16.1 17.45,16.3 17.45,16.5C17.45,16.8 17.5,17.05 17.6,17.3L17.3,17.9C16.55,18.45 15.7,18.75 14.8,18.75C13.95,18.75 13.15,18.5 12.4,18L12,18.2V16.2L13.35,15.45C13.15,14.8 13,14.1 13,13.4C13,12.3 13.2,11.2 13.6,10.15L12,8.55V11.8C12,12.45 11.45,13 10.8,13C10.15,13 9.6,12.45 9.6,11.8V8.55L8,10.15C8.4,11.2 8.6,12.3 8.6,13.4C8.6,14.1 8.45,14.8 8.25,15.45L9.6,16.2V18.2L9.2,18C8.45,18.5 7.65,18.75 6.8,18.75C5.9,18.75 5.05,18.45 4.3,17.9L4,17.3C4.1,17.05 4.15,16.8 4.15,16.5C4.15,15.8 3.9,15.1 3.45,14.5L2.9,13.95C3.2,13.35 3.35,12.7 3.35,12C3.35,10.25 4.5,8.75 6.25,8.75C6.5,8.75 6.75,8.8 7,8.9L8.6,7.3C8.4,6.55 8.3,5.75 8.3,5C8.3,3.8 8.95,2.8 10,2.25L12,4L14,2.25C15.05,2.8 15.7,3.8 15.7,5C15.7,5.75 15.6,6.55 15.4,7.3L17,8.9C17.25,8.8 17.5,8.75 17.75,8.75C19.5,8.75 20.65,10.25 20.65,12C20.65,12.7 20.5,13.35 20.2,13.95L19.65,14.5C19.2,15.1 18.95,15.8 18.95,16.5C18.95,17.55 19.4,18.5 20.25,19.2L20.75,18.4C21.1,17.7 21.3,16.9 21.3,16.1C21.3,14.85 20.8,13.65 19.9,12.8C19.7,13 19.5,13.15 19.25,13.25C18.95,12.7 18.6,12.2 18.2,11.8C18.2,11.8 18.2,11.8 18.2,11.8C18,11.6 17.85,11.4 17.65,11.2L18.85,10C19.2,9.65 19.4,9.25 19.4,8.85C19.4,7.75 18.55,6.9 17.45,6.9C17.15,6.9 16.85,6.95 16.55,7.05L15.4,5.9C15.6,5.45 15.7,5.05 15.7,4.65C15.7,3.65 14.9,2.9 13.9,2.9C13.5,2.9 13.1,3 12.7,3.2L12,2Z" />
            </svg>
          </div>
          <h1>孕妇健康管理系统</h1>
          <p class="subtitle">专业的孕期健康管理平台，为孕妈妈提供全方位的健康服务</p>
          
          <div class="features">
            <div class="feature">
              <svg class="feature-icon" viewBox="0 0 24 24">
                <path fill="currentColor" d="M12,1L3,5V11C3,16.55 6.84,21.74 12,23C17.16,21.74 21,16.55 21,11V5L12,1Z" />
                <path fill="currentColor" d="M12,5C13.1,5 14,5.9 14,7C14,8.1 13.1,9 12,9C10.9,9 10,8.1 10,7C10,5.9 10.9,5 12,5Z" />
                <path fill="currentColor" d="M10,13H14V15H10V13Z" />
              </svg>
              <span>专业的医生团队</span>
            </div>
            <div class="feature">
              <svg class="feature-icon" viewBox="0 0 24 24">
                <path fill="currentColor" d="M12,2A10,10 0 0,0 2,12A10,10 0 0,0 12,22A10,10 0 0,0 22,12A10,10 0 0,0 12,2M12,17A2,2 0 0,1 10,15A2,2 0 0,1 12,13A2,2 0 0,1 14,15A2,2 0 0,1 12,17M12,9A4,4 0 0,0 8,13H16A4,4 0 0,0 12,9Z" />
              </svg>
              <span>个性化的健康管理</span>
            </div>
            <div class="feature">
              <svg class="feature-icon" viewBox="0 0 24 24">
                <path fill="currentColor" d="M20,2H4A2,2 0 0,0 2,4V22L6,18H20A2,2 0 0,0 22,16V4C22,2.89 21.1,2 20,2M20,16H5.17L4,17.17V4H20V16Z" />
              </svg>
              <span>便捷的在线咨询</span>
            </div>
            <div class="feature">
              <svg class="feature-icon" viewBox="0 0 24 24">
                <path fill="currentColor" d="M14,17H7V15H14V17M17,13H7V11H17V13M17,9H7V7H17V9M19,3H5C3.89,3 3,3.9 3,5V19A2,2 0 0,0 5,21H19A2,2 0 0,0 21,19V5C21,3.9 20.1,3 19,3Z" />
              </svg>
              <span>丰富的孕期知识</span>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 右侧登录表单区域 -->
      <div class="login-form-container">
        <div class="login-card">
          <div class="form-header">
            <h2>欢迎登录</h2>
            <p>请使用您的账户登录系统</p>
          </div>
          
          <form @submit.prevent="handleLogin" class="auth-form">
            <div class="form-group">
              <label for="username">用户名</label>
              <div class="input-wrapper">
                <svg class="input-icon" viewBox="0 0 24 24">
                  <path fill="currentColor" d="M12,4A4,4 0 0,1 16,8A4,4 0 0,1 12,12A4,4 0 0,1 8,8A4,4 0 0,1 12,4M12,14C16.42,14 20,15.79 20,18V20H4V18C4,15.79 7.58,14 12,14Z" />
                </svg>
                <input 
                  type="text" 
                  id="username" 
                  v-model="loginForm.username" 
                  placeholder="请输入用户名"
                  required
                  class="form-control"
                >
              </div>
            </div>
            
            <div class="form-group">
              <label for="password">密码</label>
              <div class="input-wrapper">
                <svg class="input-icon" viewBox="0 0 24 24">
                  <path fill="currentColor" d="M12,17A2,2 0 0,1 10,15A2,2 0 0,1 12,13A2,2 0 0,1 14,15A2,2 0 0,1 12,17M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6A2,2 0 0,1 4,20V10C4,8.89 4.9,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M16,10V8H8V10H16Z" />
                </svg>
                <input 
                  type="password" 
                  id="password" 
                  v-model="loginForm.password" 
                  placeholder="请输入密码"
                  required
                  class="form-control"
                >
              </div>
            </div>
            
            <div class="form-group">
              <label for="role">角色</label>
              <div class="custom-select-wrapper">
                <div class="input-wrapper select-wrapper" @click="toggleDropdown">
                  <svg class="input-icon" viewBox="0 0 24 24">
                    <path fill="currentColor" d="M12,16A2,2 0 0,1 14,14C14,12.89 13.1,12 12,12C10.89,12 10,12.89 10,14A2,2 0 0,1 12,16M18,8A2,2 0 0,1 20,10V20A2,2 0 0,1 18,22H6A2,2 0 0,1 4,20V10C4,8.89 4.9,8 6,8H7V6A5,5 0 0,1 12,1A5,5 0 0,1 17,6V8H18M17,8H7V6A3,3 0 0,1 10,3A3,3 0 0,1 13,6V8Z" />
                  </svg>
                  <div class="selected-value">{{ selectedRoleText }}</div>
                  <svg class="select-icon" viewBox="0 0 24 24">
                    <path fill="currentColor" d="M7,10L12,15L17,10H7Z" />
                  </svg>
                </div>
                <div v-if="showDropdown" class="custom-dropdown">
                  <div 
                    v-for="role in roles" 
                    :key="role.value"
                    class="dropdown-option"
                    :class="{ 'active': loginForm.role === role.value }"
                    @click="selectRole(role.value)"
                  >
                    {{ role.text }}
                  </div>
                </div>
              </div>
            </div>
            
            <button type="submit" class="btn btn-primary" :class="{ 'btn-loading': loading }" :disabled="loading" id="loginBtn">
              {{ loading ? '登录中...' : '登录' }}
            </button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'Login',
  mounted() {
    // 添加登录页面标识class
    document.body.classList.add('login-page');
    // 添加点击外部关闭下拉框的事件监听
    this.addClickOutsideListener();
  },
  beforeUnmount() {
    // 移除登录页面标识class
    document.body.classList.remove('login-page');
    // 移除点击外部关闭下拉框的事件监听
    this.removeClickOutsideListener();
  },
  setup() {
    const router = useRouter()
    const loading = ref(false)
    const showDropdown = ref(false) // 控制下拉框显示/隐藏
    
    // 角色选项数据
    const roles = ref([
      { value: 'ADMIN', text: '管理员' },
      { value: 'DOCTOR', text: '医生' }
    ])
    
    const loginForm = ref({
      username: '',
      password: '',
      role: 'ADMIN'
    })
    
    // 计算当前选中的角色文本
    const selectedRoleText = computed(() => {
      const role = roles.value.find(r => r.value === loginForm.value.role)
      return role ? role.text : ''
    })
    
    // 切换下拉框显示状态
    const toggleDropdown = () => {
      showDropdown.value = !showDropdown.value
    }
    
    // 选择角色
    const selectRole = (value) => {
      loginForm.value.role = value
      showDropdown.value = false
    }
    
    // 点击外部关闭下拉框
    const handleClickOutside = (event) => {
      const customSelect = document.querySelector('.custom-select-wrapper')
      if (customSelect && !customSelect.contains(event.target)) {
        showDropdown.value = false
      }
    }
    
    const handleLogin = async () => {
      loading.value = true
      
      try {
        // 调用实际的登录API
        const response = await axios.post('/api/users/admin-login', {
          username: loginForm.value.username,
          password: loginForm.value.password,
          role: loginForm.value.role
        })
        
        if (response.data.code === 200) {
          // 登录成功，将token存储到localStorage
          localStorage.setItem('token', response.data.data.token)
          localStorage.setItem('userInfo', JSON.stringify(response.data.data.userInfo))
          
          // 根据角色跳转到不同页面
          if (loginForm.value.role === 'ADMIN') {
            router.push('/dashboard')
          } else if (loginForm.value.role === 'DOCTOR') {
            router.push('/doctor-dashboard')
          }
        } else {
          // 登录失败，显示错误消息
          alert(response.data.message || '登录失败，请检查用户名和密码')
        }
      } catch (error) {
        console.error('登录失败:', error)
        alert('登录失败，请稍后重试')
      } finally {
        loading.value = false
      }
    }
    
    // 在组件挂载时添加点击外部事件监听
    const addClickOutsideListener = () => {
      document.addEventListener('click', handleClickOutside)
    }
    
    // 在组件卸载时移除点击外部事件监听
    const removeClickOutsideListener = () => {
      document.removeEventListener('click', handleClickOutside)
    }
    
    return {
      router,
      loading,
      loginForm,
      showDropdown,
      roles,
      selectedRoleText,
      toggleDropdown,
      selectRole,
      handleLogin,
      addClickOutsideListener,
      removeClickOutsideListener
    }
  }
}
</script>

<style scoped>
/* 现代化登录页面样式 */
:root {
  --primary-color: #ff6b8b;
  --secondary-color: #ff8fab;
  --accent-color: #ffe0b2;
  --light-color: #fff9f9;
  --dark-color: #2d3436;
  --gray-color: #636e72;
  --border-color: #f0e6e6;
  --shadow: 0 10px 40px rgba(255, 107, 139, 0.15);
  --transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 登录容器 */
.login-container {
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #fff5f7 0%, #ffebee 100%);
  position: relative;
  overflow: hidden;
}

/* 背景装饰元素 */
.background-elements {
  position: absolute;
  width: 100%;
  height: 100%;
  overflow: hidden;
  z-index: 1;
}

.decor-circle {
  position: absolute;
  border-radius: 50%;
  background: rgba(255, 107, 139, 0.08);
  animation: float 6s ease-in-out infinite;
}

.circle-1 {
  width: 400px;
  height: 400px;
  top: -100px;
  left: -100px;
  animation-delay: 0s;
}

.circle-2 {
  width: 300px;
  height: 300px;
  bottom: -150px;
  right: -100px;
  animation-delay: 3s;
}

.circle-3 {
  width: 200px;
  height: 200px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  animation-delay: 1.5s;
}

.decor-hearts {
  position: absolute;
  width: 100%;
  height: 100%;
  pointer-events: none;
}

.heart-icon {
  position: absolute;
  width: 24px;
  height: 24px;
  color: rgba(255, 107, 139, 0.1);
  animation: float 8s ease-in-out infinite;
}

.heart-icon:nth-child(1) {
  top: 20%;
  right: 15%;
  animation-delay: 0s;
}

.heart-icon:nth-child(2) {
  top: 60%;
  left: 10%;
  animation-delay: 2s;
}

.heart-icon:nth-child(3) {
  bottom: 30%;
  right: 25%;
  animation-delay: 4s;
}

/* 主要登录包装器 */
.login-wrapper {
  display: flex;
  width: 1000px;
  height: 600px;
  background: white;
  border-radius: 30px;
  box-shadow: var(--shadow);
  overflow: hidden;
  position: relative;
  z-index: 10;
  animation: slideUp 0.8s ease-out;
}

/* 左侧信息区域 */
.login-info {
  flex: 1.2;
  background: linear-gradient(135deg, #ff6b8b, #ff8fab); /* 直接使用颜色值，确保渐变背景正确显示 */
  color: white;
  padding: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  overflow: hidden;
  z-index: 2;
}

.login-info::before {
  content: "";
  position: absolute;
  width: 400px;
  height: 400px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  top: -150px;
  right: -150px;
  z-index: -1;
}

.login-info::after {
  content: "";
  position: absolute;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  bottom: -80px;
  left: -80px;
  z-index: -1;
}

/* 确保左侧所有文字元素显示为白色 */
.login-info h1,
.login-info .subtitle,
.login-info .feature span {
  color: white !important;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.1);
}

.info-content {
  z-index: 10;
}

.logo {
  display: flex;
  justify-content: center;
  margin-bottom: 30px;
}

.logo-icon {
  width: 80px;
  height: 80px;
  color: white;
  animation: pulse 2s infinite;
}

.login-info h1 {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 20px;
  text-align: center;
  position: relative;
  z-index: 2;
}

.subtitle {
  font-size: 18px;
  line-height: 1.6;
  opacity: 0.95;
  text-align: center;
  margin-bottom: 50px;
  position: relative;
  z-index: 2;
}

.features {
  position: relative;
  z-index: 2;
}

.feature {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 15px;
  background: rgba(255, 255, 255, 0.1);
  border-radius: 12px;
  transition: var(--transition);
}

.feature:hover {
  transform: translateX(10px);
  background: rgba(255, 255, 255, 0.15);
}

.feature-icon {
  width: 24px;
  height: 24px;
  margin-right: 15px;
  color: white;
  flex-shrink: 0;
}

.feature span {
  font-size: 16px;
  font-weight: 400;
}

/* 右侧登录表单区域 */
.login-form-container {
  flex: 1;
  padding: 60px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  position: relative;
  z-index: 3;
}

.login-card {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
}

.form-header {
  text-align: center;
  margin-bottom: 40px;
}

.form-header h2 {
  font-size: 32px;
  font-weight: 700;
  color: var(--dark-color);
  margin-bottom: 10px;
}

.form-header p {
  color: var(--gray-color);
  font-size: 16px;
}

/* 表单样式 */
.form-group {
  margin-bottom: 25px;
}

.form-group label {
  display: block;
  margin-bottom: 10px;
  font-weight: 600;
  color: var(--dark-color);
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.input-wrapper {
  position: relative;
  display: flex;
  align-items: center;
}

.input-icon {
  position: absolute;
  left: 15px;
  width: 20px;
  height: 20px;
  color: var(--gray-color);
  z-index: 2;
}

.form-control {
  width: 100%;
  padding: 15px 20px 15px 50px;
  border: 2px solid var(--border-color);
  border-radius: 12px;
  font-size: 16px;
  transition: var(--transition);
  background: var(--light-color);
}

.form-control:focus {
  outline: none;
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(255, 107, 139, 0.1);
}

.select-wrapper {
  display: flex;
  align-items: center;
  background: var(--light-color);
  border-radius: 12px;
  border: 2px solid var(--border-color);
  transition: var(--transition);
}

.select-wrapper:focus-within {
  border-color: var(--primary-color);
  background: white;
  box-shadow: 0 0 0 4px rgba(255, 107, 139, 0.1);
}

.select-wrapper select {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  padding: 15px 50px 15px 50px;
  border: none;
  background: transparent;
  font-size: 16px;
  color: var(--dark-color);
  cursor: pointer;
}

.select-wrapper select:focus {
  outline: none;
}

.select-icon {
  position: absolute;
  right: 15px;
  width: 20px;
  height: 20px;
  color: var(--gray-color);
  pointer-events: none;
}

/* 下拉框选项样式 */
.select-wrapper select option {
  background: #ff8fab !important;
  color: white !important;
  padding: 10px !important;
}

/* 自定义下拉框样式 */
.custom-select-wrapper {
  position: relative;
  width: 100%;
}

.selected-value {
  width: 100%;
  padding: 12px 15px;
  color: var(--dark-color);
  background: transparent;
}

.custom-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  width: 100%;
  background: white;
  border-radius: 8px;
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
  z-index: 10;
  margin-top: 4px;
  overflow: hidden;
}

.dropdown-option {
  padding: 12px 15px;
  cursor: pointer;
  transition: all 0.2s ease;
  background: linear-gradient(135deg, #ff6b8b, #ff8fab);
  color: white;
}

.dropdown-option:hover {
  background: linear-gradient(135deg, #ff8fab, #ff6b8b);
  color: white;
}

.dropdown-option.active {
  background: linear-gradient(135deg, #ff5277, #ff7390);
  color: white;
}

/* 针对Firefox的下拉框选项样式 */
.select-wrapper select:-moz-focusring {
  color: transparent;
  text-shadow: 0 0 0 #000;
}

/* 增强下拉框的整体样式一致性 */
.select-wrapper {
  position: relative;
}

/* 按钮样式 */
.btn {
  width: 100%;
  padding: 16px;
  border: none;
  border-radius: 12px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: var(--transition);
  position: relative;
  z-index: 3;
  margin-top: 15px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  box-shadow: 0 4px 15px rgba(255, 107, 139, 0.3);
}

.btn-primary {
  background: linear-gradient(135deg, #ff6b8b, #ff8fab); /* 与左侧背景颜色保持一致 */
  color: white;
}

.btn-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(255, 107, 139, 0.4);
}

.btn-loading {
  position: relative;
  pointer-events: none;
}

.btn-loading::after {
  content: "";
  position: absolute;
  width: 20px;
  height: 20px;
  border: 3px solid transparent;
  border-top: 3px solid white;
  border-radius: 50%;
  top: 50%;
  right: 25px;
  transform: translateY(-50%);
  animation: spin 1s linear infinite;
}

/* 动画效果 */
@keyframes spin {
  0% { transform: translateY(-50%) rotate(0deg); }
  100% { transform: translateY(-50%) rotate(360deg); }
}

@keyframes float {
  0% { transform: translateY(0px); }
  50% { transform: translateY(-20px); }
  100% { transform: translateY(0px); }
}

@keyframes pulse {
  0% { transform: scale(1); }
  50% { transform: scale(1.05); }
  100% { transform: scale(1); }
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(40px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .login-wrapper {
    width: 90%;
    height: auto;
    flex-direction: column;
    margin: 20px;
  }
  
  .login-info {
    padding: 40px;
  }
  
  .login-form-container {
    padding: 40px;
  }
}

@media (max-width: 768px) {
  .login-container {
    padding: 20px;
  }
  
  .login-wrapper {
    width: 100%;
    border-radius: 20px;
  }
  
  .login-info {
    flex: none;
    padding: 40px 30px;
  }
  
  .login-info h1 {
    font-size: 28px;
  }
  
  .login-form-container {
    padding: 40px 30px;
  }
  
  .form-header h2 {
    font-size: 28px;
  }
}

@media (max-width: 480px) {
  .login-info {
    padding: 30px 20px;
  }
  
  .login-info h1 {
    font-size: 24px;
  }
  
  .login-form-container {
    padding: 30px 20px;
  }
  
  .form-header h2 {
    font-size: 24px;
  }
  
  .form-control {
    padding: 12px 15px 12px 45px;
    font-size: 14px;
  }
  
  .select-wrapper select {
    padding: 12px 45px 12px 45px;
    font-size: 14px;
  }
  
  .btn {
    padding: 14px;
    font-size: 14px;
  }
}
</style>