<template>
  <div class="login-container">
    <div class="login-form">
      <div class="login-header">
        <div class="logo">
          <i class="fas fa-baby"></i>
          <h2>孕妇健康管理系统</h2>
        </div>
        <p>登录到管理后台</p>
      </div>
      
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="username">用户名</label>
          <input 
            type="text" 
            id="username" 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            required
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
          >
        </div>
        
        <div class="form-group">
          <label for="role">角色</label>
          <select id="role" v-model="loginForm.role">
            <option value="ADMIN">管理员</option>
            <option value="DOCTOR">医生</option>
          </select>
        </div>
        
        <button type="submit" class="login-btn" :disabled="loading">
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
        const response = await axios.post('/api/users/login', {
          username: loginForm.value.username,
          password: loginForm.value.password,
          role: loginForm.value.role
        })
        
        if (response.data.code === 200) {
          // 保存用户信息到localStorage
          const userInfo = {
            ...response.data.data,
            role: loginForm.value.role
          }
          localStorage.setItem('userInfo', JSON.stringify(userInfo))
          
          // 跳转到管理后台
          router.push('/admin/dashboard')
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
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form {
  width: 100%;
  max-width: 400px;
  padding: 40px;
  background: white;
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-header .logo {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  margin-bottom: 15px;
}

.login-header .logo i {
  font-size: 2rem;
  color: #667eea;
}

.login-header h2 {
  margin: 0;
  color: #333;
}

.login-header p {
  color: #666;
  margin: 5px 0 0;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
  color: #333;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px;
  border: 1px solid #ddd;
  border-radius: 5px;
  font-size: 14px;
  transition: border-color 0.3s;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #667eea;
}

.login-btn {
  width: 100%;
  padding: 12px;
  background: #667eea;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
  transition: background 0.3s;
}

.login-btn:hover:not(:disabled) {
  background: #5a6fd8;
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
}
</style>