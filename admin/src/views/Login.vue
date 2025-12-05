<template>
  <div class="login-container">
    <div class="login-left">
      <h1>孕妇健康管理系统</h1>
      <p>专业的孕期健康管理平台，为孕妈妈提供全方位的健康服务</p>
      
      <div class="features">
        <div class="feature">
          <i class="fas fa-check-circle"></i>
          <span>专业的医生团队</span>
        </div>
        <div class="feature">
          <i class="fas fa-check-circle"></i>
          <span>个性化的健康管理</span>
        </div>
        <div class="feature">
          <i class="fas fa-check-circle"></i>
          <span>便捷的在线咨询</span>
        </div>
        <div class="feature">
          <i class="fas fa-check-circle"></i>
          <span>丰富的孕期知识</span>
        </div>
      </div>
    </div>
    
    <div class="login-right">
      <h2>欢迎登录</h2>
      <p>请使用您的账户登录系统</p>
      
      <form @submit.prevent="handleLogin" class="auth-form">
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            required
            class="form-control"
          >
        </div>
        
        <div class="form-group">
          <label for="password">密码</label>
          <input 
            type="password" 
            id="password" 
            v-model="loginForm.password" 
            placeholder="请输入密码"
            required
            class="form-control"
          >
        </div>
        
        <div class="form-group">
          <label for="role">角色</label>
          <select id="role" v-model="loginForm.role" class="form-control">
            <option value="ADMIN">管理员</option>
            <option value="DOCTOR">医生</option>
          </select>
        </div>
        
        <button type="submit" class="btn btn-primary" :class="{ 'btn-loading': loading }" :disabled="loading" id="loginBtn">
          {{ loading ? '登录中...' : '登录' }}
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'Login',
  mounted() {
    // 添加登录页面标识class
    document.body.classList.add('login-page');
  },
  beforeUnmount() {
    // 移除登录页面标识class
    document.body.classList.remove('login-page');
  },
  setup() {
    const router = useRouter()
    const loading = ref(false)
    
    const loginForm = ref({
      username: '',
      password: '',
      role: 'ADMIN'
    })
    
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
          // 将用户信息保存到localStorage
          localStorage.setItem('currentUser', JSON.stringify(response.data.data))
          
          // 跳转到管理后台
          window.location.href = './admin.html'
        } else {
          alert('登录失败：' + response.data.message)
        }
      } catch (error) {
        console.error('登录失败:', error)
        alert('登录失败，请检查用户名和密码')
      } finally {
        loading.value = false
      }
    }
    
    return {
      loginForm,
      loading,
      handleLogin
    }
  }
}
</script>

<style scoped>
/* 现代化登录页面样式 */
:root {
  --primary-color: #4361ee;
  --secondary-color: #3f37c9;
  --success-color: #4cc9f0;
  --light-color: #f8f9fa;
  --dark-color: #212529;
  --gray-color: #6c757d;
  --border-color: #dee2e6;
  --transition: all 0.3s ease;
}

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 确保Vue应用根元素占满整个屏幕 */
#app {
  height: 100vh;
  width: 100vw;
}

.login-container {
  display: flex;
  width: 900px;
  height: 500px;
  background: transparent;
  border-radius: 20px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  position: relative;
  z-index: 10;
}

/* 左侧装饰区域 */
.login-left {
  flex: 1;
  background: linear-gradient(135deg, var(--primary-color), var(--secondary-color));
  color: white;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  position: relative;
  overflow: hidden;
}

.login-left::before {
  content: "";
  position: absolute;
  width: 300px;
  height: 300px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  top: -100px;
  right: -100px;
}

.login-left::after {
  content: "";
  position: absolute;
  width: 200px;
  height: 200px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  bottom: -80px;
  left: -80px;
}



.login-left h1 {
  font-size: 32px;
  font-weight: 700;
  margin-bottom: 20px;
  position: relative;
  z-index: 2;
}

.login-left p {
  font-size: 16px;
  line-height: 1.6;
  opacity: 0.9;
  position: relative;
  z-index: 2;
}

.features {
  margin-top: 30px;
  position: relative;
  z-index: 2;
}

.feature {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}



.feature i {
  margin-right: 10px;
  font-size: 18px;
}

/* 右侧登录表单区域 */
.login-right {
  flex: 1;
  padding: 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  background: white;
  animation: fadeIn 0.5s ease forwards;
}

.login-right h2 {
  font-size: 28px;
  font-weight: 700;
  color: var(--dark-color);
  margin-bottom: 10px;
  text-align: center;
}

.login-right p {
  color: var(--gray-color);
  text-align: center;
  margin-bottom: 30px;
}

/* 表单样式 */
.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  font-weight: 500;
  color: var(--dark-color);
}

.form-control {
  width: 100%;
  padding: 12px 15px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
  font-size: 16px;
  transition: var(--transition);
}

.form-control:focus {
  outline: none;
  border-color: var(--primary-color);
  box-shadow: 0 0 0 3px rgba(67, 97, 238, 0.1);
}

/* 按钮样式 */
.btn {
  width: 100%;
  padding: 12px;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: var(--transition);
  position: relative;
  z-index: 1;
  margin-top: 10px;
  margin-bottom: 20px;
}

.btn-primary {
  background: var(--primary-color);
  color: white;
}

.btn-primary:hover {
  background: var(--secondary-color);
}

.btn-loading {
  position: relative;
  pointer-events: none;
}

.btn-loading::after {
  content: "";
  position: absolute;
  width: 16px;
  height: 16px;
  border: 2px solid transparent;
  border-top: 2px solid white;
  border-radius: 50%;
  top: 50%;
  right: 20px;
  transform: translateY(-50%);
  animation: spin 1s linear infinite;
}

@keyframes spin {
  0% { transform: translateY(-50%) rotate(0deg); }
  100% { transform: translateY(-50%) rotate(360deg); }
}





/* 响应式设计 */
@media (max-width: 992px) {
  .login-container {
    width: 90%;
    max-width: 500px;
    height: auto;
  }
  
  .login-left {
    display: none;
  }
}

@media (max-width: 576px) {
  .login-container {
    width: 95%;
    padding: 20px;
  }
  
  .login-right {
    padding: 20px;
  }
  
  .login-right h2 {
    font-size: 24px;
  }
}

/* 动画效果 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 确整表单位置和间距 */
.auth-form {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>