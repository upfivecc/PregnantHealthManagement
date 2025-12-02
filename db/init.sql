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

-- 插入示例数据
INSERT INTO users (username, password, email, real_name, role) VALUES 
('doctor1', '123456', 'doctor1@example.com', '张医生', 'DOCTOR'),
('user1', '123456', 'user1@example.com', '李女士', 'USER');

INSERT INTO doctors (user_id, hospital, department, title, specialty) VALUES 
(2, '北京协和医院', '妇产科', '主任医师', '高危妊娠管理');

INSERT INTO knowledge (title, content, category, status, created_by) VALUES 
('孕期营养指南', '孕期应该均衡饮食，多吃新鲜蔬菜水果...', '营养', 1, 1),
('产检时间安排', '第一次产检应在怀孕6-8周进行...', '产检', 1, 1);

INSERT INTO patient_doctor (user_id, doctor_id, status) VALUES 
(3, 1, 1);
