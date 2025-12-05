<template>
  <div class="dashboard">
    <div class="card">
      <div class="card-header">
        <div class="card-title">仪表盘</div>
      </div>
      <div class="card-body">
        <!-- 统计数据 -->
        <div class="stats-grid">
          <div class="stat-card">
            <div class="stat-icon bg-blue">
              <i class="fas fa-users"></i>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.userCount || 0 }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon bg-green">
              <i class="fas fa-user-md"></i>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.doctorCount || 0 }}</div>
              <div class="stat-label">医生总数</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon bg-orange">
              <i class="fas fa-calendar-check"></i>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.todayAppointmentCount || 0 }}</div>
              <div class="stat-label">今日预约</div>
            </div>
          </div>
          <div class="stat-card">
            <div class="stat-icon bg-red">
              <i class="fas fa-comments"></i>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ stats.unrepliedCount || 0 }}</div>
              <div class="stat-label">未回复咨询</div>
            </div>
          </div>
        </div>
        
        <!-- 图表区域 -->
        <div class="chart-container">
          <div class="chart-card">
            <div class="chart-header">
              <h3>预约趋势</h3>
            </div>
            <div class="chart-body">
              <canvas ref="appointmentChart"></canvas>
            </div>
          </div>
          <div class="chart-card">
            <div class="chart-header">
              <h3>病人统计</h3>
            </div>
            <div class="chart-body">
              <canvas ref="patientChart"></canvas>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
import Chart from 'chart.js/auto'

export default {
  name: 'Dashboard',
  setup() {
    const appointmentChart = ref(null)
    const patientChart = ref(null)
    const stats = reactive({
      userCount: 0,
      doctorCount: 0,
      todayAppointmentCount: 0,
      unrepliedCount: 0
    })
    
    let appointmentChartInstance = null
    let patientChartInstance = null
    
    // 加载统计数据
    const loadStats = async () => {
      try {
        const response = await axios.get('/api/dashboard/statistics')
        if (response.data.code === 200) {
          Object.assign(stats, response.data.data)
        }
      } catch (error) {
        console.error('加载统计数据失败:', error)
      }
    }
    
    // 创建预约趋势图表
    const createAppointmentChart = async () => {
      if (appointmentChartInstance) {
        appointmentChartInstance.destroy()
      }
      
      try {
        const response = await axios.get('/api/dashboard/appointment-trend')
        if (response.data.code === 200) {
          const ctx = appointmentChart.value.getContext('2d')
          
          const labels = response.data.data.map(item => item.date)
          const values = response.data.data.map(item => item.count)
          
          appointmentChartInstance = new Chart(ctx, {
            type: 'line',
            data: {
              labels: labels,
              datasets: [{
                label: '预约数量',
                data: values,
                borderColor: '#ff6b8b',
                backgroundColor: 'rgba(255, 107, 139, 0.1)',
                borderWidth: 2,
                fill: true,
                tension: 0.4
              }]
            },
            options: {
              responsive: true,
              maintainAspectRatio: false,
              plugins: {
                legend: {
                  display: false
                }
              },
              scales: {
                y: {
                  beginAtZero: true,
                  ticks: {
                    stepSize: 1
                  }
                }
              }
            }
          })
        }
      } catch (error) {
        console.error('加载预约趋势数据失败:', error)
      }
    }
    
    // 创建病人数量图表
    const createPatientChart = async () => {
      if (patientChartInstance) {
        patientChartInstance.destroy()
      }
      
      try {
        const response = await axios.get('/api/dashboard/patient-stats')
        if (response.data.code === 200) {
          const ctx = patientChart.value.getContext('2d')
          
          const labels = response.data.data.map(item => item.date)
          const values = response.data.data.map(item => item.count)
          
          patientChartInstance = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: labels,
              datasets: [{
                label: '病人数量',
                data: values,
                backgroundColor: '#ff8fab',
                borderColor: '#ff6b8b',
                borderWidth: 1
              }]
            },
            options: {
              responsive: true,
              maintainAspectRatio: false,
              plugins: {
                legend: {
                  display: false
                }
              },
              scales: {
                y: {
                  beginAtZero: true,
                  ticks: {
                    stepSize: 10
                  }
                }
              }
            }
          })
        }
      } catch (error) {
        console.error('加载病人数量数据失败:', error)
      }
    }
    
    // 生命周期
    onMounted(() => {
      loadStats()
      createAppointmentChart()
      createPatientChart()
    })
    
    return {
      appointmentChart,
      patientChart,
      stats
    }
  }
}
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.card-header {
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

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
}

.stat-icon {
  width: 50px;
  height: 50px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.5rem;
  color: white;
}

.bg-blue {
  background: linear-gradient(135deg, #ff6b8b, #ff8fab);
}

.bg-green {
  background: linear-gradient(135deg, #ff8fab, #ff6b8b);
}

.bg-orange {
  background: linear-gradient(135deg, #ffc0cb, #ffb6c1);
}

.bg-red {
  background: linear-gradient(135deg, #ff5271, #ff6b8b);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 1.5rem;
  font-weight: 600;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 0.9rem;
  color: #909399;
}

.chart-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.chart-card {
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.chart-header {
  margin-bottom: 15px;
}

.chart-header h3 {
  margin: 0;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.chart-body {
  height: 300px;
  position: relative;
}

@media (max-width: 768px) {
  .chart-container {
    grid-template-columns: 1fr;
  }
  
  .chart-body {
    height: 250px;
  }
}
</style>