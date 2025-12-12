-- 创建数据库
CREATE DATABASE IF NOT EXISTS pregnant_health DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pregnant_health;

-- 用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '电子邮件',
    phone VARCHAR(20) COMMENT '手机号',
    real_name VARCHAR(50) COMMENT '真实姓名',
    gender TINYINT DEFAULT 0 COMMENT '性别 0未知 1男 2女',
    age INT COMMENT '年龄',
    role VARCHAR(20) NOT NULL DEFAULT 'USER' COMMENT '角色 USER普通用户 DOCTOR医生 ADMIN管理员',
    status TINYINT DEFAULT 1 COMMENT '状态 0禁用 1启用',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '用户表';

-- 医生表
CREATE TABLE doctors (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '医生ID',
    user_id BIGINT NOT NULL COMMENT '关联用户ID',
    hospital VARCHAR(100) COMMENT '医院',
    department VARCHAR(100) COMMENT '科室',
    title VARCHAR(50) COMMENT '职称',
    specialty VARCHAR(200) COMMENT '专长',
    introduction TEXT COMMENT '简介',
    score DECIMAL(3,1) DEFAULT 0 COMMENT '评分',  -- 新增字段
    consultation_count INT DEFAULT 0 COMMENT '接诊量',  -- 新增字段
    positive_rate DECIMAL(5,2) DEFAULT 0 COMMENT '好评率',  -- 新增字段
    avatar VARCHAR(255) DEFAULT '' COMMENT '头像',  -- 新增字段
    name VARCHAR(50) DEFAULT '' COMMENT '医生姓名',  -- 新增字段
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id)
) COMMENT '医生表';

-- 孕期知识表
CREATE TABLE knowledge (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '知识ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    category VARCHAR(50) COMMENT '分类',
    status TINYINT DEFAULT 1 COMMENT '状态 0草稿 1发布',
    created_by BIGINT COMMENT '创建人',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) COMMENT '孕期知识表';

-- 预约表
CREATE TABLE appointments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '预约ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    doctor_id BIGINT NOT NULL COMMENT '医生ID',
    appointment_time DATETIME NOT NULL COMMENT '预约时间',
    status TINYINT DEFAULT 0 COMMENT '状态 0待确认 1已确认 2已完成 3已取消',
    remark TEXT COMMENT '备注',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
) COMMENT '预约表';

-- 咨询表
CREATE TABLE consultations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '咨询ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    doctor_id BIGINT NOT NULL COMMENT '医生ID',
    title VARCHAR(200) NOT NULL COMMENT '咨询标题',
    content TEXT NOT NULL COMMENT '咨询内容',
    reply TEXT COMMENT '回复内容',
    status TINYINT DEFAULT 0 COMMENT '状态 0未回复 1已回复',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    replied_time DATETIME COMMENT '回复时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
) COMMENT '咨询表';

-- 病人医生关系表
CREATE TABLE patient_doctor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关系ID',
    user_id BIGINT NOT NULL COMMENT '用户ID(病人)',
    doctor_id BIGINT NOT NULL COMMENT '医生ID',
    status TINYINT DEFAULT 1 COMMENT '状态 0解除关系 1建立关系',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id),
    UNIQUE KEY uk_user_doctor (user_id, doctor_id)
) COMMENT '病人医生关系表';

-- 孕期档案表
CREATE TABLE pregnancy_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '档案ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    height DECIMAL(5,2) COMMENT '身高(cm)',
    weight DECIMAL(5,2) COMMENT '体重(kg)',
    blood_pressure_high INT COMMENT '血压高压',
    blood_pressure_low INT COMMENT '血压低压',
    fetal_movement_count INT COMMENT '胎动次数',
    record_date DATE NOT NULL COMMENT '记录日期',
    remark TEXT COMMENT '备注',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (user_id) REFERENCES users(id),
    UNIQUE KEY uk_user_date (user_id, record_date)
) COMMENT '孕期档案表';

-- 插入初始管理员用户
INSERT INTO users (username, password, email, role) VALUES ('admin', '123456', 'admin@example.com', 'ADMIN');

-- 插入更多用户数据
INSERT INTO users (username, password, email, real_name, gender, age, role, status) VALUES 
('doctor1', '123456', 'doctor1@example.com', '张医生', 2, 35, 'DOCTOR', 1),
('doctor2', '123456', 'doctor2@example.com', '李医生', 2, 32, 'DOCTOR', 1),
('doctor3', '123456', 'doctor3@example.com', '王医生', 2, 38, 'DOCTOR', 1),
('user1', '123456', 'user1@example.com', '李女士', 2, 28, 'USER', 1),
('user2', '123456', 'user2@example.com', '陈女士', 2, 30, 'USER', 1),
('user3', '123456', 'user3@example.com', '刘女士', 2, 25, 'USER', 1),
('user4', '123456', 'user4@example.com', '赵女士', 2, 32, 'USER', 1),
('user5', '123456', 'user5@example.com', '黄女士', 2, 29, 'USER', 1);

-- 插入更多医生数据（包含新增字段）
INSERT INTO doctors (user_id, hospital, department, title, specialty, introduction, score, consultation_count, positive_rate, avatar, name) VALUES 
(2, '北京协和医院', '妇产科', '主任医师', '高危妊娠管理', '从事妇产科临床工作20年，擅长高危妊娠管理', 4.8, 120, 98.50, 'https://youke2.picui.cn/s1/2025/12/13/693ca75d22e05.jpeg', '张伟'),
(3, '北京大学第一医院', '妇产科', '副主任医师', '产前诊断', '专注于产前诊断和遗传咨询', 4.6, 95, 96.20, 'https://youke2.picui.cn/s1/2025/12/13/693ca75e3ba7d.jpg', '李娜'),
(4, '北京妇产医院', '妇产科', '主治医师', '围产医学', '在围产医学领域有丰富经验', 4.7, 80, 97.80, 'https://youke2.picui.cn/s1/2025/12/13/693ca75d22e05.jpeg', '王强');

-- 插入更多孕期知识数据
INSERT INTO knowledge (title, content, category, status, created_by) VALUES 
('孕期营养指南', '孕期应该均衡饮食，多吃新鲜蔬菜水果，补充叶酸和铁质...', '营养', 1, 1),
('产检时间安排', '第一次产检应在怀孕6-8周进行，之后定期按医生建议进行各项检查...', '产检', 1, 1),
('孕期运动指导', '适当的运动有助于孕妇健康，推荐散步、孕妇瑜伽等低强度运动...', '运动', 1, 1),
('孕期心理调适', '保持良好的心态对母婴健康都很重要，可以通过听音乐、阅读等方式放松心情...', '心理', 1, 1),
('分娩准备事项', '提前了解分娩过程，准备好待产包，参加孕妇学校课程...', '分娩', 1, 1),
('产后恢复要点', '产后要注意休息，合理饮食，适当运动促进身体恢复...', '产后', 1, 1),
('新生儿护理基础', '学习如何给新生儿洗澡、换尿布、喂养等基本护理技能...', '育儿', 1, 1),
('母乳喂养技巧', '掌握正确的哺乳姿势，注意乳房护理，保证充足的营养摄入...', '喂养', 1, 1);

-- 插入更多病人医生关系数据
INSERT INTO patient_doctor (user_id, doctor_id, status) VALUES 
(3, 1, 1),
(5, 1, 1),
(6, 2, 1),
(7, 2, 1),
(8, 3, 1);

-- 插入更多预约数据
INSERT INTO appointments (user_id, doctor_id, appointment_time, status, remark, created_time, updated_time) VALUES
(5, 1, '2025-12-10 09:00:00', 1, '常规产检', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(6, 2, '2025-12-11 10:30:00', 0, '首次产检', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(7, 2, '2025-12-12 14:00:00', 1, 'B超检查', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(8, 3, '2025-12-13 15:30:00', 2, '产前咨询', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(3, 1, '2025-12-15 09:30:00', 0, '营养咨询', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(5, 1, '2025-12-20 11:00:00', 3, '因故取消', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(8, 3, '2025-12-13 15:30:00', 2, '产前咨询', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(3, 1, '2025-12-15 09:30:00', 0, '营养咨询', '2025-12-02 21:56:28', '2025-12-02 21:56:28'),
(5, 1, '2025-12-20 11:00:00', 3, '因故取消', '2025-12-03 21:56:28', '2025-12-02 21:56:28'),
(5, 1, '2025-12-10 09:00:00', 1, '常规产检', '2025-12-03 21:56:28', '2025-12-02 21:56:28'),
(5, 2, '2025-12-12 10:30:00', 0, '首次产检', '2025-12-03 21:56:28', '2025-12-02 21:56:28'),
(7, 2, '2025-12-12 14:00:00', 1, 'B超检查', '2025-12-03 21:56:28', '2025-12-02 21:56:28');


-- 插入更多咨询数据
INSERT INTO consultations (user_id, doctor_id, title, content, reply, status, replied_time) VALUES 
(5, 1, '关于孕期体重增长的问题', '我现在怀孕20周，体重增长了15斤，这正常吗？', '您的体重增长在正常范围内，请继续保持均衡饮食...', 1, '2025-12-01 10:30:00'),
(6, 2, '产检时间安排咨询', '我是初次怀孕，想了解一下产检的时间安排', '第一次产检应在怀孕6-8周进行，之后按孕周定期检查...', 1, '2025-12-01 14:20:00'),
(7, 2, '关于胎动的疑问', '我感觉最近胎动减少，这是否正常？', '胎动变化可能与胎儿睡眠周期有关，建议您密切观察...', 1, '2025-12-02 09:15:00'),
(8, 3, '分娩方式选择', '我想了解自然分娩和剖腹产的区别', '两种分娩方式各有优缺点，需要根据您的具体情况决定...', 1, '2025-12-02 11:45:00'),
(3, 1, '孕期营养补充', '除了叶酸还需要补充其他营养素吗？', '建议补充铁、钙、维生素D等，具体剂量请咨询医生...', 1, '2025-12-02 15:30:00'),
(5, 1, '关于孕期运动', '我可以进行哪些类型的运动？', '推荐散步、孕妇瑜伽等低强度运动，避免剧烈运动...', 0, NULL),
(6, 2, '产前焦虑问题', '临近预产期感到很紧张，怎么办？', NULL, 0, NULL);

-- 插入更多孕期档案数据
INSERT INTO pregnancy_records (user_id, height, weight, blood_pressure_high, blood_pressure_low, fetal_movement_count, record_date, remark) VALUES 
(5, 165.5, 68.2, 120, 80, 4, '2025-12-01', '常规记录'),
(5, 165.5, 69.0, 118, 78, 5, '2025-12-02', '体重略有增加'),
(6, 160.0, 62.5, 115, 75, 3, '2025-12-01', '初次建档'),
(7, 168.0, 70.0, 122, 82, 6, '2025-12-01', '胎动活跃'),
(7, 168.0, 70.5, 120, 80, 4, '2025-12-02', '正常记录'),
(8, 162.5, 65.0, 118, 77, 5, '2025-12-01', '各项指标正常');