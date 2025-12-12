-- 修改医生表结构，添加评分、接诊量、好评率和头像字段
ALTER TABLE doctors ADD COLUMN score DECIMAL(3,1) DEFAULT 0 COMMENT '评分';
ALTER TABLE doctors ADD COLUMN consultation_count INT DEFAULT 0 COMMENT '接诊量';
ALTER TABLE doctors ADD COLUMN positive_rate DECIMAL(5,2) DEFAULT 0 COMMENT '好评率';
ALTER TABLE doctors ADD COLUMN avatar VARCHAR(255) DEFAULT '' COMMENT '头像';
-- 添加医生姓名字段
ALTER TABLE doctors ADD COLUMN name VARCHAR(50) DEFAULT '' COMMENT '医生姓名';