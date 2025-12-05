<template>
  <div class="admin-layout">
    <!-- 侧边栏 -->
    <div class="sidebar" :class="{ collapsed: sidebarCollapsed }">
      <div class="sidebar-header">
        <div class="logo">
          <div class="logo-icon">
            <i class="fas fa-baby"></i>
          </div>
          <div class="logo-text" v-if="!sidebarCollapsed">孕妇健康管理系统</div>
        </div>
        <button class="toggle-btn" @click="toggleSidebar">
          <i class="fas fa-bars"></i>
        </button>
      </div>
      
      <div class="sidebar-menu">
        <div 
          class="menu-item" 
          :class="{ active: $route.name === 'Dashboard' }"
          @click="$router.push('/admin/dashboard')"
        >
          <i class="fas fa-home"></i>
          <span v-if="!sidebarCollapsed">仪表盘</span>
        </div>
        
        <div 
          class="menu-item admin-only" 
          :class="{ active: $route.name === 'Users' }"
          @click="$router.push('/admin/users')"
          v-if="userInfo.role === 'ADMIN'"
        >
          <i class="fas fa-users"></i>
          <span v-if="!sidebarCollapsed">用户管理</span>
        </div>
        
        <div 
          class="menu-item" 
          :class="{ active: $route.name === 'Doctors' }"
          @click="$router.push('/admin/doctors')"
        >
          <i class="fas fa-user-md"></i>
          <span v-if="!sidebarCollapsed">医生管理</span>
        </div>
        
        <div 
          class="menu-item" 
          :class="{ active: $route.name === 'Appointments' }"
          @click="$router.push('/admin/appointments')"
        >
          <i class="fas fa-calendar-check"></i>
          <span v-if="!sidebarCollapsed">预约管理</span>
        </div>
        
        <div 
          class="menu-item" 
          :class="{ active: $route.name === 'Knowledge' }"
          @click="$router.push('/admin/knowledge')"
        >
          <i class="fas fa-book-medical"></i>
          <span v-if="!sidebarCollapsed">知识管理</span>
        </div>
        
        <div 
          class="menu-item" 
          :class="{ active: $route.name === 'Consultations' }"
          @click="$router.push('/admin/consultations')"
        >
          <i class="fas fa-comments"></i>
          <span v-if="!sidebarCollapsed">咨询管理</span>
        </div>
      </div>
    </div>
    
    <!-- 主内容区 -->
    <div class="main-content">
      <!-- 顶部导航栏 -->
      <div class="header">
        <div class="header-left">
          <h1>{{ pageTitle }}</h1>
        </div>
        <div class="header-right">
          <div class="user-profile">
            <div class="user-avatar">{{ userAvatar }}</div>
            <div class="user-name">{{ userInfo.realName || userInfo.username }}</div>
            <i class="fas fa-chevron-down"></i>
          </div>
          <div class="logout-btn" @click="handleLogout">
            <i class="fas fa-sign-out-alt"></i> 退出
          </div>
        </div>
      </div>
      
      <!-- 内容区域 -->
      <div class="content">
        <router-view />
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'

export default {
  name: 'AdminLayout',
  setup() {
    const router = useRouter()
    const sidebarCollapsed = ref(false)
    const userInfo = ref({})
    
    // 计算属性
    const pageTitle = computed(() => {
      const titles = {
        'Dashboard': '仪表盘',
        'Users': '用户管理',
        'Doctors': '医生管理',
        'Appointments': '预约管理',
        'Knowledge': '知识管理',
        'Consultations': '咨询管理'
      }
      return titles[router.currentRoute.value.name] || '未知页面'
    })
    
    const userAvatar = computed(() => {
      if (userInfo.value.role === 'ADMIN') {
        return '管'
      } else if (userInfo.value.role === 'DOCTOR') {
        return '医'
      }
      return '用'
    })
    
    // 方法
    const toggleSidebar = () => {
      sidebarCollapsed.value = !sidebarCollapsed.value
    }
    
    const handleLogout = async () => {
      try {
        // 调用后端退出接口
        await axios.post('/api/users/logout')
      } catch (error) {
        console.error('退出失败:', error)
      } finally {
        // 清除用户信息
        localStorage.removeItem('userInfo')
        // 跳转到登录页面
        router.push('/login')
      }
    }
    
    // 生命周期
    onMounted(() => {
      // 获取用户信息
      const storedUserInfo = localStorage.getItem('userInfo')
      if (storedUserInfo) {
        userInfo.value = JSON.parse(storedUserInfo)
      } else {
        // 如果没有用户信息，跳转到登录页面
        router.push('/login')
      }
    })
    
    return {
      sidebarCollapsed,
      userInfo,
      pageTitle,
      userAvatar,
      toggleSidebar,
      handleLogout
    }
  }
}
</script>

<style scoped>
.admin-layout {
  display: flex;
  height: 100vh;
  width: 100vw; /* 添加这一行确保布局占满整个视口宽度 */
}

.sidebar {
  width: 250px;
  background: #2c3e50;
  color: white;
  transition: width 0.3s ease;
  display: flex;
  flex-direction: column;
  flex-shrink: 0; /* 防止侧边栏被压缩 */
}

.sidebar.collapsed {
  width: 60px;
}

.sidebar-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.logo {
  display: flex;
  align-items: center;
  gap: 10px;
}

.logo-icon {
  font-size: 1.5rem;
}

.logo-text {
  font-size: 1.1rem;
  font-weight: 600;
}

.toggle-btn {
  background: transparent;
  border: none;
  color: white;
  cursor: pointer;
  font-size: 1.2rem;
}

.sidebar-menu {
  flex: 1;
  padding: 20px 0;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 15px 20px;
  cursor: pointer;
  transition: background 0.3s;
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.1);
}

.menu-item.active {
  background: #3498db;
}

.menu-item i {
  font-size: 1.2rem;
  width: 20px;
  text-align: center;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  min-width: 0; /* 防止内容溢出 */
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  background: white;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-bottom: 1px solid #eee;
  flex-shrink: 0; /* 防止头部被压缩 */
}

.header-left h1 {
  margin: 0;
  font-size: 1.5rem;
  color: #333;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.user-profile {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.user-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: #3498db;
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 600;
}

.user-name {
  font-weight: 500;
  color: #333;
}

.logout-btn {
  display: flex;
  align-items: center;
  gap: 5px;
  padding: 8px 15px;
  background: #e74c3c;
  color: white;
  border-radius: 5px;
  cursor: pointer;
  transition: background 0.3s;
}

.logout-btn:hover {
  background: #c0392b;
}

.content {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background: #f5f5f5;
}
</style>