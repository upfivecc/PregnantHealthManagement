// Vue实例
new Vue({
    el: '#app',
    data: {
        activeTab: 'login',
        loginLoading: false,
        registerLoading: false,
        
        // 登录表单数据
        loginForm: {
            username: '',
            password: ''
        },
        loginRules: {
            username: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '请输入密码', trigger: 'blur' }
            ]
        },
        
        // 注册表单数据
        registerForm: {
            username: '',
            password: '',
            confirmPassword: '',
            email: ''
        },
        registerRules: {
            username: [
                { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
                { required: true, message: '请输入密码', trigger: 'blur' },
                { min: 6, message: '密码长度至少6位', trigger: 'blur' }
            ],
            confirmPassword: [
                { required: true, message: '请确认密码', trigger: 'blur' },
                { validator: validateConfirmPassword, trigger: 'blur' }
            ],
            email: [
                { required: true, message: '请输入邮箱', trigger: 'blur' },
                { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
            ]
        }
    },
    
    methods: {
        // 标签页切换处理
        handleTabClick(tab) {
            this.activeTab = tab.name;
        },
        
        // 登录处理
        handleLogin() {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {
                    this.loginLoading = true;
                    
                    // 模拟API调用
                    setTimeout(() => {
                        this.loginLoading = false;
                        
                        // 模拟登录成功
                        if (this.loginForm.username === 'admin' && this.loginForm.password === 'admin123') {
                            this.$message.success('登录成功');
                            // 跳转到新的管理后台
                            window.location.href = './admin.html';
                        } else if (this.loginForm.username === 'user1' && this.loginForm.password === 'user123') {
                            this.$message.success('登录成功');
                            // 跳转到用户主页
                            window.location.href = './user.html';
                        } else {
                            this.$message.error('用户名或密码错误');
                        }
                    }, 1000);
                }
            });
        },
        
        // 注册处理
        handleRegister() {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    this.registerLoading = true;
                    
                    // 模拟API调用
                    setTimeout(() => {
                        this.registerLoading = false;
                        this.$message.success('注册成功，请登录');
                        this.activeTab = 'login';
                        // 清空注册表单
                        this.registerForm = {
                            username: '',
                            password: '',
                            confirmPassword: '',
                            email: ''
                        };
                    }, 1000);
                }
            });
        }
    }
});

// 自定义验证规则：确认密码
function validateConfirmPassword(rule, value, callback) {
    if (value === '') {
        callback(new Error('请再次输入密码'));
    } else if (value !== vm.registerForm.password) {
        callback(new Error('两次输入密码不一致'));
    } else {
        callback();
    }
}

// Element UI全局配置
Vue.use(ElementUI);

// 设置axios默认配置
axios.defaults.baseURL = '/api';
axios.defaults.timeout = 10000;

// 获取Vue实例的引用
const vm = new Vue();