// Vue实例
new Vue({
    el: '#app',
    data: {
        activeMenu: 'dashboard',
        currentPage: 'dashboard',
        
        // 统计数据
        statistics: {
            userCount: 0,
            doctorCount: 0,
            todayAppointmentCount: 0,
            unrepliedCount: 0
        },
        
        // 用户管理相关数据
        userList: [],
        userQuery: {
            username: '',
            role: ''
        },
        userPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        userDialogVisible: false,
        userForm: {
            id: null,
            username: '',
            password: '',
            email: '',
            realName: '',
            role: 'USER',
            status: 1
        },
        userFormTitle: '',
        userRules: {
            username: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '请输入密码', trigger: 'blur' }
            ],
            email: [
                { required: true, message: '请输入邮箱', trigger: 'blur' }
            ]
        },
        
        // 医生管理相关数据
        doctorList: [],
        doctorPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        doctorDialogVisible: false,
        doctorForm: {
            id: null,
            userId: null,
            hospital: '',
            department: '',
            title: '',
            specialty: '',
            introduction: ''
        },
        doctorFormTitle: '',
        doctorRules: {
            userId: [
                { required: true, message: '请输入用户ID', trigger: 'blur' }
            ],
            hospital: [
                { required: true, message: '请输入医院', trigger: 'blur' }
            ]
        },
        
        // 预约管理相关数据
        appointmentList: [],
        appointmentPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        
        // 知识管理相关数据
        knowledgeList: [],
        knowledgePage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        knowledgeDialogVisible: false,
        knowledgeForm: {
            id: null,
            title: '',
            content: '',
            category: '',
            status: 1
        },
        knowledgeFormTitle: '',
        knowledgeRules: {
            title: [
                { required: true, message: '请输入标题', trigger: 'blur' }
            ]
        },
        
        // 咨询管理相关数据
        consultationList: [],
        consultationPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        consultationDialogVisible: false,
        consultationForm: {
            id: null,
            content: '',
            reply: ''
        }
    },
    
    mounted() {
        this.loadDashboardData();
    },
    
    methods: {
        // 菜单选择处理
        handleMenuSelect(key) {
            this.currentPage = key;
            this.activeMenu = key;
            
            // 根据选择的菜单加载对应数据
            switch (key) {
                case 'users':
                    this.loadUsers();
                    break;
                case 'doctors':
                    this.loadDoctors();
                    break;
                case 'appointments':
                    this.loadAppointments();
                    break;
                case 'knowledge':
                    this.loadKnowledge();
                    break;
                case 'consultations':
                    this.loadConsultations();
                    break;
            }
        },
        
        // 用户操作处理
        handleUserCommand(command) {
            if (command === 'logout') {
                // 退出登录逻辑
                alert('退出登录');
            }
        },
        
        // 加载仪表盘数据
        loadDashboardData() {
            // 模拟API调用
            setTimeout(() => {
                this.statistics = {
                    userCount: 128,
                    doctorCount: 15,
                    todayAppointmentCount: 8,
                    unrepliedCount: 3
                };
            }, 500);
        },
        
        // 加载用户列表
        loadUsers() {
            // 模拟API调用
            setTimeout(() => {
                this.userList = [
                    { id: 1, username: 'user1', email: 'user1@example.com', realName: '张三', role: 'USER', status: 1 },
                    { id: 2, username: 'user2', email: 'user2@example.com', realName: '李四', role: 'USER', status: 1 },
                    { id: 3, username: 'doctor1', email: 'doctor1@example.com', realName: '王医生', role: 'DOCTOR', status: 1 }
                ];
                this.userPage.total = 3;
            }, 500);
        },
        
        // 搜索用户
        searchUsers() {
            this.userPage.currentPage = 1;
            this.loadUsers();
        },
        
        // 显示添加用户对话框
        showAddUserDialog() {
            this.userForm = {
                id: null,
                username: '',
                password: '',
                email: '',
                realName: '',
                role: 'USER',
                status: 1
            };
            this.userFormTitle = '添加用户';
            this.userDialogVisible = true;
        },
        
        // 编辑用户
        editUser(row) {
            this.userForm = Object.assign({}, row);
            this.userFormTitle = '编辑用户';
            this.userDialogVisible = true;
        },
        
        // 查看用户
        viewUser(row) {
            this.editUser(row);
        },
        
        // 保存用户
        saveUser() {
            this.$refs.userForm.validate((valid) => {
                if (valid) {
                    // 模拟API调用
                    setTimeout(() => {
                        this.userDialogVisible = false;
                        this.loadUsers();
                        this.$message.success('保存成功');
                    }, 500);
                }
            });
        },
        
        // 删除用户
        deleteUser(id) {
            this.$confirm('确定要删除该用户吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 模拟API调用
                setTimeout(() => {
                    this.loadUsers();
                    this.$message.success('删除成功');
                }, 500);
            });
        },
        
        // 用户分页处理
        handleUserSizeChange(val) {
            this.userPage.pageSize = val;
            this.loadUsers();
        },
        
        handleUserCurrentChange(val) {
            this.userPage.currentPage = val;
            this.loadUsers();
        },
        
        // 加载医生列表
        loadDoctors() {
            // 模拟API调用
            setTimeout(() => {
                this.doctorList = [
                    { id: 1, userId: 3, username: 'doctor1', realName: '王医生', hospital: '北京协和医院', department: '妇产科', title: '主任医师' }
                ];
                this.doctorPage.total = 1;
            }, 500);
        },
        
        // 显示添加医生对话框
        showAddDoctorDialog() {
            this.doctorForm = {
                id: null,
                userId: null,
                hospital: '',
                department: '',
                title: '',
                specialty: '',
                introduction: ''
            };
            this.doctorFormTitle = '添加医生';
            this.doctorDialogVisible = true;
        },
        
        // 编辑医生
        editDoctor(row) {
            // 模拟获取详细信息
            this.doctorForm = {
                id: row.id,
                userId: row.userId,
                hospital: row.hospital || '',
                department: row.department || '',
                title: row.title || '',
                specialty: '',
                introduction: ''
            };
            this.doctorFormTitle = '编辑医生';
            this.doctorDialogVisible = true;
        },
        
        // 查看医生
        viewDoctor(row) {
            this.editDoctor(row);
        },
        
        // 保存医生
        saveDoctor() {
            this.$refs.doctorForm.validate((valid) => {
                if (valid) {
                    // 模拟API调用
                    setTimeout(() => {
                        this.doctorDialogVisible = false;
                        this.loadDoctors();
                        this.$message.success('保存成功');
                    }, 500);
                }
            });
        },
        
        // 删除医生
        deleteDoctor(id) {
            this.$confirm('确定要删除该医生吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 模拟API调用
                setTimeout(() => {
                    this.loadDoctors();
                    this.$message.success('删除成功');
                }, 500);
            });
        },
        
        // 医生分页处理
        handleDoctorSizeChange(val) {
            this.doctorPage.pageSize = val;
            this.loadDoctors();
        },
        
        handleDoctorCurrentChange(val) {
            this.doctorPage.currentPage = val;
            this.loadDoctors();
        },
        
        // 加载预约列表
        loadAppointments() {
            // 模拟API调用
            setTimeout(() => {
                this.appointmentList = [
                    { id: 1, userName: '张三', hospital: '北京协和医院', department: '妇产科', title: '主任医师', appointmentTime: '2023-06-15 10:00', status: 1 }
                ];
                this.appointmentPage.total = 1;
            }, 500);
        },
        
        // 查看预约
        viewAppointment(row) {
            alert('查看预约详情：' + row.id);
        },
        
        // 删除预约
        deleteAppointment(id) {
            this.$confirm('确定要删除该预约吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 模拟API调用
                setTimeout(() => {
                    this.loadAppointments();
                    this.$message.success('删除成功');
                }, 500);
            });
        },
        
        // 预约分页处理
        handleAppointmentSizeChange(val) {
            this.appointmentPage.pageSize = val;
            this.loadAppointments();
        },
        
        handleAppointmentCurrentChange(val) {
            this.appointmentPage.currentPage = val;
            this.loadAppointments();
        },
        
        // 加载知识列表
        loadKnowledge() {
            // 模拟API调用
            setTimeout(() => {
                this.knowledgeList = [
                    { id: 1, title: '孕期营养指南', category: '营养', status: 1, createdTime: '2023-06-01' },
                    { id: 2, title: '产检时间安排', category: '产检', status: 1, createdTime: '2023-06-05' }
                ];
                this.knowledgePage.total = 2;
            }, 500);
        },
        
        // 显示添加知识对话框
        showAddKnowledgeDialog() {
            this.knowledgeForm = {
                id: null,
                title: '',
                content: '',
                category: '',
                status: 1
            };
            this.knowledgeFormTitle = '发布知识';
            this.knowledgeDialogVisible = true;
        },
        
        // 编辑知识
        editKnowledge(row) {
            // 模拟获取详细信息
            this.knowledgeForm = {
                id: row.id,
                title: row.title,
                content: '',
                category: row.category,
                status: row.status
            };
            this.knowledgeFormTitle = '编辑知识';
            this.knowledgeDialogVisible = true;
        },
        
        // 查看知识
        viewKnowledge(row) {
            this.editKnowledge(row);
        },
        
        // 保存知识
        saveKnowledge() {
            this.$refs.knowledgeForm.validate((valid) => {
                if (valid) {
                    // 模拟API调用
                    setTimeout(() => {
                        this.knowledgeDialogVisible = false;
                        this.loadKnowledge();
                        this.$message.success('保存成功');
                    }, 500);
                }
            });
        },
        
        // 删除知识
        deleteKnowledge(id) {
            this.$confirm('确定要删除该知识吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 模拟API调用
                setTimeout(() => {
                    this.loadKnowledge();
                    this.$message.success('删除成功');
                }, 500);
            });
        },
        
        // 知识分页处理
        handleKnowledgeSizeChange(val) {
            this.knowledgePage.pageSize = val;
            this.loadKnowledge();
        },
        
        handleKnowledgeCurrentChange(val) {
            this.knowledgePage.currentPage = val;
            this.loadKnowledge();
        },
        
        // 加载咨询列表
        loadConsultations() {
            // 模拟API调用
            setTimeout(() => {
                this.consultationList = [
                    { id: 1, userName: '张三', title: '关于孕期饮食的咨询', status: 0, createdTime: '2023-06-10' },
                    { id: 2, userName: '李四', title: '产检时间咨询', status: 1, createdTime: '2023-06-08' }
                ];
                this.consultationPage.total = 2;
            }, 500);
        },
        
        // 查看并回复咨询
        viewConsultation(row) {
            this.consultationForm = {
                id: row.id,
                content: '这是用户的咨询内容...',
                reply: row.reply || ''
            };
            this.consultationDialogVisible = true;
        },
        
        // 回复咨询
        replyConsultation() {
            // 模拟API调用
            setTimeout(() => {
                this.consultationDialogVisible = false;
                this.loadConsultations();
                this.$message.success('回复成功');
            }, 500);
        },
        
        // 删除咨询
        deleteConsultation(id) {
            this.$confirm('确定要删除该咨询吗？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                // 模拟API调用
                setTimeout(() => {
                    this.loadConsultations();
                    this.$message.success('删除成功');
                }, 500);
            });
        },
        
        // 咨询分页处理
        handleConsultationSizeChange(val) {
            this.consultationPage.pageSize = val;
            this.loadConsultations();
        },
        
        handleConsultationCurrentChange(val) {
            this.consultationPage.currentPage = val;
            this.loadConsultations();
        }
    }
});

// Element UI全局配置
Vue.use(ElementUI);

// 设置axios默认配置
axios.defaults.baseURL = '/api';
axios.defaults.timeout = 10000;