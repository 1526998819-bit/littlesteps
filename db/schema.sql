-- LittleSteps Database Schema

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'PARENT' COMMENT 'PARENT/ADMIN'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS children (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(10) NOT NULL COMMENT '男宝/女宝',
    birth_date DATE NOT NULL,
    avatar_url VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='孩子信息';

CREATE TABLE IF NOT EXISTS growth_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    record_date DATE NOT NULL,
    height DOUBLE COMMENT '身高(cm)',
    weight DOUBLE COMMENT '体重(kg)',
    head_circumference DOUBLE COMMENT '头围(cm)',
    notes VARCHAR(500),
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成长记录';

CREATE TABLE IF NOT EXISTS journal_entries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    entry_date DATE NOT NULL,
    title VARCHAR(200) NOT NULL,
    content TEXT,
    mood VARCHAR(50) COMMENT '心情',
    category VARCHAR(50) COMMENT '分类: milestone/daily/health',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='日记记录';

CREATE TABLE IF NOT EXISTS vaccine_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    name VARCHAR(200) NOT NULL,
    dose VARCHAR(50) COMMENT '剂次',
    scheduled_date DATE,
    administered_date DATE,
    status VARCHAR(20) NOT NULL DEFAULT 'Pending' COMMENT 'Completed/Pending/Overdue',
    description TEXT,
    clinic_name VARCHAR(200),
    doctor_name VARCHAR(100),
    age_group VARCHAR(50) COMMENT '年龄段: 0-6/6-12/12-24',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='疫苗记录';

CREATE TABLE IF NOT EXISTS gallery_photos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    child_id BIGINT NOT NULL,
    url VARCHAR(500) NOT NULL,
    caption VARCHAR(200),
    type VARCHAR(20) NOT NULL DEFAULT 'photo' COMMENT 'photo/video',
    taken_at DATETIME,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (child_id) REFERENCES children(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='照片视频';
