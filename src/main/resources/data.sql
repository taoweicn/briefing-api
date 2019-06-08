USE `briefing`;

# 初始化管理员账户
INSERT INTO user(student_id, username, department, type, password)
VALUES ('admin', 'admin', 'admin', 2, MD5('ADMIN_PASSWORD'))
