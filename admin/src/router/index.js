import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import AdminLayout from '../views/AdminLayout.vue'
import Dashboard from '../views/Dashboard.vue'
import Users from '../views/Users.vue'
import Doctors from '../views/Doctors.vue'
import Appointments from '../views/Appointments.vue'
import Knowledge from '../views/Knowledge.vue'
import Consultations from '../views/Consultations.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: AdminLayout,
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: Dashboard
      },
      {
        path: 'users',
        name: 'Users',
        component: Users
      },
      {
        path: 'doctors',
        name: 'Doctors',
        component: Doctors
      },
      {
        path: 'appointments',
        name: 'Appointments',
        component: Appointments
      },
      {
        path: 'knowledge',
        name: 'Knowledge',
        component: Knowledge
      },
      {
        path: 'consultations',
        name: 'Consultations',
        component: Consultations
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router