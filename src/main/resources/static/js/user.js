// Vue实例
new Vue({
    el: '#app',
    data: {
        activeMenu: 'dashboard',
        currentPage: 'dashboard',
        
        // 医生列表相关数据
        doctorList: [],
        doctorPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        
        // 咨询相关数据
        consultationList: [],
        consultationPage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        consultDialogVisible: false,
        consultForm: {
            title: '',
            content: '',
            doctorId: null
        },
        consultRules: {
            title: [
                { required: true, message: '请输入咨询标题', trigger: 'blur' }
            ],
            content: [
                { required: true, message: '请输入咨询内容', trigger: 'blur' }
            ]
        },
        
        // 孕期档案相关数据
        recordList: [],
        recordDialogVisible: false,
        recordForm: {
            id: null,
            recordDate: '',
            height: null,
            weight: null,
            bloodPressureHigh: null,
            bloodPressureLow: null,
            fetalMovementCount: null,
            remark: ''
        },
        recordFormTitle: '',
        recordRules: {
            recordDate: [
                { required: true, message: '请选择记录日期', trigger: 'change' }
            ]
        },
        
        // 孕期知识相关数据
        knowledgeList: [],
        knowledgePage: {
            currentPage: 1,
            pageSize: 10,
            total: 0
        },
        knowledgeDialogVisible: false,
        knowledgeDetail: {
            title: '',
            content: ''
        }
    },
    
    mounted() {
        this.loadDoctors();
    },
    
    methods: {
        // 菜单选择处理
        handleMenuSelect(key) {
            this.currentPage = key;
            this.activeMenu = key;
            
            // 根据选择的菜单加载对应数据
            switch (key) {
                case 'doctors':
                    this.loadDoctors();
                    break;
                case 'consultations':
                    this.loadConsultations();
                    break;
                case 'records':
                    this.loadRecords();
                    break;
                case 'knowledge':
                    this.loadKnowledge();
                    break;
            }
        },
        
        // 用户操作处理
        handleUserCommand(command) {
            if (command === 'logout') {
                // 退出登录逻辑
                window.location.href = './login.html';
            } else if (command === 'profile') {
                alert('个人资料');
            }
        },
        
        // 加载医生列表
        loadDoctors() {
            // 模拟API调用
            setTimeout(() => {
                this.doctorList = [
                    { id: 1, realName: '王医生', hospital: '北京协和医院', department: '妇产科', title: '主任医师', specialty: '高危妊娠管理' },
                    { id: 2, realName: '李医生', hospital: '北京大学第一医院', department: '妇产科', title: '副主任医师', specialty: '产前诊断' }
                ];
                this.doctorPage.total = 2;
            }, 500);
        },
        
        // 咨询医生
        consultDoctor(doctor) {
            this.consultForm.doctorId = doctor.id;
            this.consultDialogVisible = true;
        },
        
        // 显示咨询对话框
        showConsultDialog() {
            this.consultForm = {
                title: '',
                content: '',
                doctorId: null
            };
            this.consultDialogVisible = true;
        },
        
        // 提交咨询
        submitConsultation() {
            this.$refs.consultForm.validate((valid) => {
                if (valid) {
                    // 模拟API调用
                    setTimeout(() => {
                        this.consultDialogVisible = false;
                        this.loadConsultations();
                        this.$message.success('咨询提交成功');
                    }, 500);
                }
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
        
        // 加载咨询列表
        loadConsultations() {
            // 模拟API调用
            setTimeout(() => {
                this.consultationList = [
                    { id: 1, title: '关于孕期饮食的咨询', doctorName: '王医生', status: 1, createdTime: '2023-06-10' },
                    { id: 2, title: '产检时间咨询', doctorName: '李医生', status: 0, createdTime: '2023-06-08' }
                ];
                this.consultationPage.total = 2;
            }, 500);
        },
        
        // 查看咨询
        viewConsultation(row) {
            alert('查看咨询详情：' + row.title);
        },
        
        // 咨询分页处理
        handleConsultationSizeChange(val) {
            this.consultationPage.pageSize = val;
            this.loadConsultations();
        },
        
        handleConsultationCurrentChange(val) {
            this.consultationPage.currentPage = val;
            this.loadConsultations();
        },
        
        // 加载孕期档案
        loadRecords() {
            // 模拟API调用
            setTimeout(() => {
                this.recordList = [
                    { id: 1, recordDate: '2023-06-01', height: 165, weight: 60.5, bloodPressureHigh: 120, bloodPressureLow: 80, fetalMovementCount: 10, remark: '一切正常' },
                    { id: 2, recordDate: '2023-06-08', height: 165, weight: 62.0, bloodPressureHigh: 118, bloodPressureLow: 78, fetalMovementCount: 12, remark: '体重增长正常' }
                ];
            }, 500);
        },
        
        // 显示档案对话框
        showRecordDialog() {
            this.recordForm = {
                id: null,
                recordDate: '',
                height: null,
                weight: null,
                bloodPressureHigh: null,
                bloodPressureLow: null,
                fetalMovementCount: null,
                remark: ''
            };
            this.recordFormTitle = '添加孕期档案';
            this.recordDialogVisible = true;
        },
        
        // 编辑档案
        editRecord(row) {
            this.recordForm = Object.assign({}, row);
            this.recordFormTitle = '编辑孕期档案';
            this.recordDialogVisible = true;
        },
        
        // 查看档案
        viewRecord(row) {
            this.editRecord(row);
        },
        
        // 保存档案
        saveRecord() {
            this.$refs.recordForm.validate((valid) => {
                if (valid) {
                    // 模拟API调用
                    setTimeout(() => {
                        this.recordDialogVisible = false;
                        this.loadRecords();
                        this.$message.success('保存成功');
                    }, 500);
                }
            });
        },
        
        // 加载孕期知识
        loadKnowledge() {
            // 模拟API调用
            setTimeout(() => {
                this.knowledgeList = [
                    { id: 1, title: '孕期营养指南', category: '营养', createdTime: '2023-06-01' },
                    { id: 2, title: '产检时间安排', category: '产检', createdTime: '2023-06-05' }
                ];
                this.knowledgePage.total = 2;
            }, 500);
        },
        
        // 查看知识详情
        viewKnowledge(row) {
            this.knowledgeDetail = {
                title: row.title,
                content: '<p>这里是<strong>' + row.title + '</strong>的详细内容...</p><p>详细内容会在这里显示。</p>'
            };
            this.knowledgeDialogVisible = true;
        },
        
        // 知识分页处理
        handleKnowledgeSizeChange(val) {
            this.knowledgePage.pageSize = val;
            this.loadKnowledge();
        },
        
        handleKnowledgeCurrentChange(val) {
            this.knowledgePage.currentPage = val;
            this.loadKnowledge();
        }
    }
});

// Element UI全局配置
Vue.use(ElementUI);

// 设置axios默认配置
axios.defaults.baseURL = '/api';
axios.defaults.timeout = 10000;