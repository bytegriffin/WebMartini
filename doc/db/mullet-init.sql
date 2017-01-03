ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (1, 0, '0001', '控制台',  'dashboard', 1, 1,'fa-dashboard','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (2, 0, '0002', '系统管理',  '', 2,1,'fa-desktop','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (3, 2, '00020001', '用户管理',  'user/list', 1,2,'fa-user','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (4, 2, '00020002', '组织管理',  'group/list', 2,2,'fa-sitemap','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (5, 2, '00020003', '菜单管理',  'menu/list', 3,2,'fa-list-alt','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (6, 2, '00020004', '角色管理',  'role/list', 4,2,'fa-users','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (7, 2, '00020005', '日志管理',  'userlog/list', 5,2,'fa-file-text-o','1');
/**  如果非菜单的系统功能(主要是依赖权限的功能)多了的话，是否考虑为一张Resources表，分为[菜单]和[功能]  两个选项  重构Menu表  **/
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (8, 2, '00020006', '系统公告',  'message/list', 6,2,'fa-envelope-square','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (9, 0, '0003', '报表管理',  '', 3,1,'fa-bar-chart-o','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (10, 9, '00030001', 'ECharts',  'chart/echarts', 1,2,'fa-line-chart','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (11, 9, '00030002', 'Chartjs',  'chart/chartjs', 1,2,'fa-bar-chart-o','1');
INSERT INTO menu (id,parent_id,code,name,url,priority,levels,icon,status) VALUES (12, 9, '00030003', 'Sparkline',  'chart/sparkline', 1,2,'fa-area-chart','1');



INSERT INTO users (id,login_name,email,password,phone,status) VALUES (1, 'admin', 'admin@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138001','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (2, 'admin1', 'admin1@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13900139002','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (3, 'admin2', 'admin2@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138003','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (4, 'zhangsan', 'zhangsan@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138004','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (5, 'lisi', 'lisi@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138005','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (6, 'wangwu', 'wangwu@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138006','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (7, 'maliu', 'maliu@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138007','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (8, 'liudehua', '8888@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138008','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (9, 'guofucheng', '999@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138009','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (10, 'zhangxueyou', '101010@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138010','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (11, 'wanganshi', '111111@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138011','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (12, 'wuming1', '121212@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138012','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (13, 'wuming2', '131313@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138013','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (14, 'lijiaxin', '141414@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138014','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (15, 'pangzi', '151515@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138015','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (16, 'libai', '161616@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138016','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (17, 'chenglong',  '171717@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138017','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (18, 'yuwentaishi', '181818@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138018','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (19, 'huangfeihong',  '191919@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138019','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (20, 'yewen', '202020@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138020','2');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (21, 'shitailong', '212121@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138021','1');
INSERT INTO users (id,login_name,email,password,phone,status) VALUES (22, 'sudaji', '222222@mullet.com', 'E10ADC3949BA59ABBE56E057F20F883E', '13800138022','2');



INSERT INTO roles (id,parent_id,code,name) VALUES (1, 0, '0001', '系统管理员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (2, 0, '0002', '董事长' );
INSERT INTO roles (id,parent_id,code,name,description) VALUES (3, 2, '00020001', '总经理一' , '主管产品/项目/研发/运维');
INSERT INTO roles (id,parent_id,code,name,description) VALUES (4, 2, '00020002', '总经理二' , '主管营销/行政');
INSERT INTO roles (id,parent_id,code,name,description) VALUES (5, 3, '000200010001', '副总经理一', '主要负责产品/项目的研发与运维' );
INSERT INTO roles (id,parent_id,code,name,description) VALUES (6, 3, '000200010002', '副总经理二', '主要负责财务' );
INSERT INTO roles (id,parent_id,code,name) VALUES (7, 5, '0002000100010001', '产品总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (8, 7, '00020001000100010001', '产品经理' );
INSERT INTO roles (id,parent_id,code,name) VALUES (9, 8, '000200010001000100010001', '产品运营专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (10, 5, '0002000100010002', '研发总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (11, 10, '00020001000100020001', '架构师' );
INSERT INTO roles (id,parent_id,code,name) VALUES (12, 10, '00020001000100020002', '产品技术经理' );
INSERT INTO roles (id,parent_id,code,name) VALUES (13, 12, '000200010001000200020001', '产品软件工程师' );
INSERT INTO roles (id,parent_id,code,name) VALUES (14, 12, '000200010001000200020002', '产品测试工程师' );
INSERT INTO roles (id,parent_id,code,name) VALUES (15, 10, '00020001000100020003', '项目经理' );
INSERT INTO roles (id,parent_id,code,name) VALUES (16, 15, '000200010001000200030001', '项目软件工程师' );
INSERT INTO roles (id,parent_id,code,name) VALUES (17, 15, '000200010001000200030002', '项目测试工程师' );
INSERT INTO roles (id,parent_id,code,name) VALUES (18, 5, '0002000100010003', '运维总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (19, 18, '00020001000100030001', '运维经理' );
INSERT INTO roles (id,parent_id,code,name) VALUES (20, 19, '000200010001000300010001', '运维专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (21, 6, '0002000100020001', '财务总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (22, 21, '00020001000200010001', '财务专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (23, 4, '000200020001', '营销总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (24, 23, '0002000200010001', '市场专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (25, 23, '0002000200010002', '售后服务专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (26, 4, '000200020002', '行政总监' );
INSERT INTO roles (id,parent_id,code,name) VALUES (27, 26, '0002000200020001', '行政专员' );
INSERT INTO roles (id,parent_id,code,name) VALUES (28, 26, '0002000200020002', 'HR' );


INSERT INTO groups (id,parent_id,code,name) VALUES (1, 0, '0001', '系统管理组' );
INSERT INTO groups (id,parent_id,code,name) VALUES (2, 0, '0002', '集团' );
INSERT INTO groups (id,parent_id,code,name) VALUES (3, 2, '00020001', '北京分公司' );
INSERT INTO groups (id,parent_id,code,name) VALUES (4, 3, '000200010001', '产品部' );
INSERT INTO groups (id,parent_id,code,name) VALUES (5, 3, '000200010002', '研发部' );
INSERT INTO groups (id,parent_id,code,name) VALUES (6, 3, '000200010003', '项目组' );
INSERT INTO groups (id,parent_id,code,name) VALUES (7, 3, '000200010004', '运营部' );
INSERT INTO groups (id,parent_id,code,name) VALUES (8, 2, '00020002', '上海分公司' );
INSERT INTO groups (id,parent_id,code,name) VALUES (9, 8, '000200020001', '营销部' );
INSERT INTO groups (id,parent_id,code,name) VALUES (10, 8, '000200020002', '行政办公室' );


INSERT INTO user_role (id,user_id,role_id) VALUES ('1', '1', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('1', '1', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('2', '2', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('3', '3', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('4', '4', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('5', '5', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('6', '6', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('7', '7', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('8', '8', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('9', '9', '1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('10','10','1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('11','11','1' );
INSERT INTO menu_role (id,menu_id,role_id) VALUES ('12','12','1' );







