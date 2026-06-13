-- 首次启动时的种子数据（已存在则跳过）
MERGE INTO children (id, name, gender, birth_date, avatar_url, created_at) KEY(id) VALUES
(1, '小宜蓁', '女宝', '2026-01-22', NULL, NOW());
