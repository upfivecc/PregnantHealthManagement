# 孕妇健康管理系统 - 管理后台 (Vue 3 + Vite)

这是一个基于Vue 3和Vite构建的前后端分离的管理后台系统。

## 技术栈

- Vue 3 (Composition API)
- Vue Router
- Vite
- Axios
- Chart.js

## 项目结构

```
admin/
├── src/
│   ├── assets/          # 静态资源
│   ├── components/      # 公共组件
│   ├── views/           # 页面组件
│   ├── router/          # 路由配置
│   ├── store/           # 状态管理
│   ├── utils/           # 工具函数
│   ├── App.vue          # 根组件
│   └── main.js          # 入口文件
├── index.html           # HTML模板
├── vite.config.js       # Vite配置
└── package.json         # 项目依赖
```

## 开发环境

确保已安装以下软件：
- Node.js (推荐使用最新稳定版)
- pnpm (推荐使用pnpm管理依赖)

## 安装依赖

```bash
pnpm install
```

## 启动开发服务器

```bash
pnpm run dev
```

开发服务器将在 `http://localhost:3000` 上运行。

## 构建生产版本

```bash
pnpm run build
```

## 预览生产构建

```bash
pnpm run preview
```

## 功能模块

1. **登录模块**
   - 支持管理员和医生角色登录
   - 用户名密码验证

2. **仪表盘**
   - 统计数据展示
   - 图表可视化

3. **用户管理** (仅管理员)
   - 用户列表查看
   - 用户增删改查

4. **医生管理**
   - 医生列表查看
   - 医生增删改查

5. **预约管理**
   - 预约列表查看
   - 预约状态管理

6. **知识管理**
   - 健康知识列表
   - 知识增删改查

7. **咨询管理**
   - 用户咨询列表
   - 咨询回复功能

## 注意事项

1. 后端API代理已配置，默认代理到 `http://localhost:8080`
2. 确保后端服务已启动并运行在指定端口
3. 登录成功后用户信息会存储在localStorage中