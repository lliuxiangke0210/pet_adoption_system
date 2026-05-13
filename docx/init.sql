-- ============================================
-- 宠物领养系统 - 数据库初始化脚本
-- 数据库: MySQL 8.0
-- 端口: 3306
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS pet_adoption_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE pet_adoption_db;

-- ============================================
-- 1. 用户表
-- ============================================
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS feedback;
DROP TABLE IF EXISTS adoption;
DROP TABLE IF EXISTS pet;
DROP TABLE IF EXISTS pet_category;
DROP TABLE IF EXISTS news;
DROP TABLE IF EXISTS banner;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码(明文)',
    real_name VARCHAR(50) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    avatar VARCHAR(255) COMMENT '头像URL',
    role VARCHAR(20) NOT NULL DEFAULT 'user' COMMENT '角色: admin/user',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户表';

-- ============================================
-- 2. 宠物分类表
-- ============================================
CREATE TABLE pet_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    description VARCHAR(255) COMMENT '分类描述',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '宠物分类表';

-- ============================================
-- 3. 宠物表
-- ============================================
CREATE TABLE pet (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '宠物ID',
    name VARCHAR(50) NOT NULL COMMENT '宠物名称',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    breed VARCHAR(50) COMMENT '品种',
    age INT COMMENT '年龄(月)',
    gender VARCHAR(10) COMMENT '性别: 公/母',
    color VARCHAR(30) COMMENT '毛色',
    size VARCHAR(20) COMMENT '体型: 小型/中型/大型',
    description TEXT COMMENT '描述',
    image VARCHAR(255) COMMENT '图片URL',
    status VARCHAR(20) NOT NULL DEFAULT 'available' COMMENT '状态: available/adopted/pending',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '宠物表';

-- ============================================
-- 4. 领养记录表
-- ============================================
CREATE TABLE adoption (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '领养ID',
    user_id BIGINT NOT NULL COMMENT '申请人ID',
    pet_id BIGINT NOT NULL COMMENT '宠物ID',
    reason TEXT COMMENT '领养理由',
    status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态: pending/approved/rejected',
    apply_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
    approve_time DATETIME COMMENT '审批时间'
) COMMENT '领养记录表';

-- ============================================
-- 5. 轮播图表
-- ============================================
CREATE TABLE banner (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '轮播图ID',
    title VARCHAR(100) NOT NULL COMMENT '标题',
    image VARCHAR(255) NOT NULL COMMENT '图片URL',
    link VARCHAR(255) COMMENT '跳转链接',
    sort_order INT DEFAULT 0 COMMENT '排序',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0禁用 1启用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '轮播图表';

-- ============================================
-- 6. 宠物资讯表
-- ============================================
CREATE TABLE news (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '资讯ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content TEXT COMMENT '内容',
    cover_image VARCHAR(255) COMMENT '封面图',
    author VARCHAR(50) COMMENT '作者',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '宠物资讯表';

-- ============================================
-- 7. 评论表
-- ============================================
CREATE TABLE comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评论ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    pet_id BIGINT COMMENT '宠物ID(可为空)',
    news_id BIGINT COMMENT '资讯ID(可为空)',
    content TEXT NOT NULL COMMENT '评论内容',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '评论表';

-- ============================================
-- 8. 用户反馈表
-- ============================================
CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '反馈ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    content TEXT NOT NULL COMMENT '反馈内容',
    reply TEXT COMMENT '回复内容',
    status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态: pending/replied',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) COMMENT '用户反馈表';

-- ============================================
-- 测试数据
-- ============================================

-- 默认管理员用户 (密码: 123456)
INSERT INTO user (username, password, real_name, phone, email, role, status) VALUES
('admin', '123456', '系统管理员', '13800138000', 'admin@pet.com', 'admin', 1),
('zhangsan', '123456', '张三', '13800138001', 'zhangsan@qq.com', 'user', 1),
('lisi', '123456', '李四', '13800138002', 'lisi@qq.com', 'user', 1),
('wangwu', '123456', '王五', '13800138003', 'wangwu@qq.com', 'user', 1);

-- 宠物分类
INSERT INTO pet_category (name, description) VALUES
('猫咪', '各种可爱的猫咪，包括英短、美短、布偶等品种'),
('狗狗', '忠诚友善的狗狗，包括金毛、柯基、泰迪等品种'),
('其他小宠', '兔子、仓鼠、龙猫等小型宠物');

-- 宠物数据
INSERT INTO pet (name, category_id, breed, age, gender, color, size, description, image, status) VALUES
('小橘', 1, '中华田园猫', 6, '公', '橘色', '中型', '一只活泼可爱的橘猫，性格温顺，喜欢与人互动，已做绝育手术，身体健康。', '/uploads/pet-1.jpg', 'available'),
('雪球', 1, '英短', 4, '母', '白色', '中型', '纯种英短，毛色纯白如雪，性格安静温顺，适合家庭饲养。', '/uploads/pet-2.jpg', 'available'),
('旺财', 2, '金毛寻回犬', 12, '公', '金色', '大型', '温顺友好的金毛犬，性格开朗活泼，对人友善，是理想的家庭伴侣犬。', '/uploads/pet-3.jpg', 'available'),
('豆豆', 2, '泰迪', 8, '母', '棕色', '小型', '可爱的泰迪犬，聪明伶俐，不掉毛，适合公寓饲养。', '/uploads/pet-4.jpg', 'available'),
('花花', 1, '布偶猫', 5, '母', '三花', '中型', '高颜值布偶猫，蓝色大眼睛，性格粘人，喜欢被抱抱。', '/uploads/pet-5.jpg', 'available'),
('小灰灰', 3, '荷兰垂耳兔', 3, '公', '灰色', '小型', '萌萌的垂耳兔，性格温顺，容易饲养，是入门级宠物的好选择。', '/uploads/pet-6.jpg', 'available');

-- 轮播图数据
INSERT INTO banner (title, image, link, sort_order, status) VALUES
('给流浪动物一个温暖的家', '/uploads/banner-1.jpg', '', 1, 1),
('领养代替购买，用爱终止流浪', '/uploads/banner-2.jpg', '', 2, 1),
('宠物领养月活动进行中', '/uploads/banner-3.jpg', '', 3, 1);

-- 宠物资讯
INSERT INTO news (title, content, cover_image, author, view_count) VALUES
('新手养猫必备指南', '<p>养猫是一件非常有趣的事情，但对于新手来说，需要做好充分的准备。本文将为您详细介绍养猫前需要准备的物品和注意事项。</p><h3>一、基础用品准备</h3><p>1. 猫砂盆：选择合适大小的猫砂盆，放置在安静通风处。</p><p>2. 猫粮：选择适合猫咪年龄段的优质猫粮。</p><p>3. 猫抓板：满足猫咪磨爪子的天性。</p><p>4. 猫窝：给猫咪一个舒适的休息空间。</p><h3>二、健康护理</h3><p>定期带猫咪去宠物医院做体检，按时接种疫苗，做好驱虫工作。</p>', '/uploads/news-1.jpg', '宠物医生小王', 256),
('狗狗日常训练小技巧', '<p>训练狗狗不仅能让它们更加听话，还能增进主人与狗狗之间的感情。以下是几个实用的训练技巧。</p><h3>坐下训练</h3><p>手拿零食放在狗狗鼻子上方，慢慢向后移动，狗狗自然会坐下。</p><h3>握手训练</h3><p>先让狗狗坐下，然后伸出手说"握手"，轻轻抬起它的前爪，给予奖励。</p><h3>定点排便</h3><p>在固定时间带狗狗到指定地点，排便后立即给予奖励和表扬。</p>', '/uploads/news-2.jpg', '训犬师老张', 189),
('宠物领养流程及注意事项', '<p>领养宠物是一件严肃的事情，需要认真考虑。本文将为您介绍宠物领养的完整流程。</p><h3>领养流程</h3><p>1. 浏览待领养宠物信息</p><p>2. 提交领养申请</p><p>3. 等待审核</p><p>4. 审核通过后，签订领养协议</p><p>5. 带宠物回家</p><h3>注意事项</h3><p>领养前请确保自己有足够的时间、经济能力和空间来照顾宠物。领养后请善待它们，永不遗弃。</p>', '/uploads/news-3.jpg', '领养中心', 312),
('宠物春季护理要点', '<p>春天是万物复苏的季节，也是宠物需要特别关注的季节。以下是宠物春季护理的几个要点。</p><h3>驱虫防蚤</h3><p>春季是寄生虫活跃的季节，请按时给宠物做体内外驱虫。</p><h3>换毛期护理</h3><p>春季是宠物换毛期，需要经常梳理毛发，减少毛球问题。</p><h3>适当运动</h3><p>天气转暖，可以适当增加户外活动时间，但要注意防晒。</p>', '/uploads/news-4.jpg', '宠物医生小王', 145);

-- 领养记录测试数据
INSERT INTO adoption (user_id, pet_id, reason, status, apply_time, approve_time) VALUES
(2, 1, '我家里已经有养猫经验，希望能给小橘一个温暖的家。', 'approved', '2025-12-01 10:30:00', '2025-12-03 14:20:00'),
(3, 2, '非常喜欢英短猫，家里环境适合养猫。', 'pending', '2025-12-05 15:45:00', NULL),
(4, 4, '泰迪犬很适合公寓饲养，我有充足的时间陪伴它。', 'rejected', '2025-12-02 09:15:00', '2025-12-04 11:00:00');

-- 评论数据
INSERT INTO comment (user_id, pet_id, news_id, content) VALUES
(2, 1, NULL, '小橘真的好可爱，希望它早日找到新家！'),
(3, NULL, 1, '这篇文章对新手很有帮助，谢谢分享！'),
(4, NULL, 3, '已经提交了领养申请，希望能通过。');

-- 反馈数据
INSERT INTO feedback (user_id, content, reply, status) VALUES
(2, '希望能增加更多猫咪品种的展示。', '感谢您的建议，我们会尽快添加更多猫咪信息。', 'replied'),
(3, '领养流程很清晰，体验很好！', NULL, 'pending');
