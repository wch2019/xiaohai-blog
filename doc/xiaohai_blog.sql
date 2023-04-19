/*
 Navicat Premium Data Transfer

 Source Server         : Code01
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : xiaohai_blog

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 16/04/2023 09:28:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_article
-- ----------------------------
DROP TABLE IF EXISTS `b_article`;
CREATE TABLE `b_article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `category_id` int NULL DEFAULT NULL COMMENT '分类id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章标题',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `is_push` int NULL DEFAULT 0 COMMENT '是否发布(0否，1是)',
  `is_top` int NULL DEFAULT 0 COMMENT '是否顶置(0否，1是)',
  `top_time` datetime NULL DEFAULT NULL COMMENT '顶置时间',
  `is_original` int NULL DEFAULT 1 COMMENT '是否原创 (0原创，1转载)',
  `original_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '转载地址',
  `page_view` int NULL DEFAULT 0 COMMENT '浏览量',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_article
-- ----------------------------
INSERT INTO `b_article` VALUES (1, 1, 1, '测试', '/image/1/20230401.jpg', 'StpUtil.getLoginId()', 1, 1, '2023-04-11 09:36:05', 1, '', 0, '2023-04-09 00:29:28', '2023-04-09 00:29:28');
INSERT INTO `b_article` VALUES (2, 1, 2, '测试222', '/image/1/20230402.jpg', '是大V发SV地方\n# 是是是 ', 0, 0, NULL, 0, '', 0, '2023-04-07 17:42:01', '2023-04-08 14:50:00');

-- ----------------------------
-- Table structure for b_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `b_article_tag`;
CREATE TABLE `b_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_article_tag
-- ----------------------------
INSERT INTO `b_article_tag` VALUES (13, 2, 1);
INSERT INTO `b_article_tag` VALUES (14, 2, 2);
INSERT INTO `b_article_tag` VALUES (17, 1, 1);

-- ----------------------------
-- Table structure for b_category
-- ----------------------------
DROP TABLE IF EXISTS `b_category`;
CREATE TABLE `b_category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `click` int NULL DEFAULT 0 COMMENT '点击次数',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_category
-- ----------------------------
INSERT INTO `b_category` VALUES (1, '技术类', 0, 0, '0', 1, '2023-04-03 17:02:19', 1, '2023-04-03 17:04:10');
INSERT INTO `b_category` VALUES (2, '资源类', 0, 2, '0', 1, '2023-04-03 17:02:54', 1, '2023-04-03 17:05:33');

-- ----------------------------
-- Table structure for b_tags
-- ----------------------------
DROP TABLE IF EXISTS `b_tags`;
CREATE TABLE `b_tags`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `click` int NULL DEFAULT 0 COMMENT '点击次数',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_tags
-- ----------------------------
INSERT INTO `b_tags` VALUES (1, 'Java', 0, 0, '0', 1, '2023-04-04 09:44:14', 1, '2023-04-04 09:44:14');
INSERT INTO `b_tags` VALUES (2, 'Linux', 0, 1, '0', 1, '2023-04-04 09:44:22', 1, '2023-04-04 09:44:22');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `email_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `email_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱发件人',
  `email_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱授权码',
  `email_port` int NULL DEFAULT NULL COMMENT '邮箱发送端口',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '本地文件地址',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '系统通知',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, 'smtp.qq.com', '1372195290@qq.com', '', 587, 'C:/Users/wangchenghai/Pictures/files/', ' #  Linux  挂载ntfs硬盘\n\n如果直接在Centos 7上插u盘或移动硬盘，有可能该设备会无法挂载，原因是Centos默认不支持ntfs文件格式，一般来说安装 ntfs-3g即可如下所示：\n```\n#yum install ntfs-3g -y\n```\n\n或者\n\n```\n#yum install ntfs* -y\n```\n\n但是有时会出现 nothing to do 或者 no package ntfs-3g available 提示，此时yum安装失败\n\n这是因为这些软件包没有正式获得任何的CentOS或Red Hat的支持，使用yum搜索某些rpm包，找不到包是因为CentOS是RedHat企业版编译过来的，去掉了所有关于版权问题的东西。安装EPEL后可以很好的解决这个问题。EPEL(Extra Packages for Enterprise Linux )即企业版Linux的扩展包，提供了很多可共Centos使用的组件，安装完这个以后基本常用的rpm都可以找到。\n\n```\n#yum install epel-release -y\n```\n\nEPEL安装完成之后再执行nifs-3g的安装\n\n```\n#yum install ntfs-3g -y\n```\n\n现在就可以进行挂载硬盘了，插入硬盘，查看硬盘信息\n\n```\nfdisk -l\n```\n\n\n\n根据详细信息个，挂着自己想要的硬盘\n\n```\nmount -t ntfs-3g /dev/sdb1 /mnt/data\n```\n\n开机自动挂载NTFS（当然 如果不希望自动挂载的，可以不做这一步。）\n\n更改 /etc/fstab,更改前备份下 cp /etc/fstab /etc/fstab.bak\n\n```\nvim /etc/fstab\n\n/dev/sdb1 /mnt/data ntfs-3g defaults 0 0\n```\n\n\n\n取消挂载\n\n```\numount /mnt/data\n```\n\n![jichou.png](http://localhost:8089/api/document/upload/image/1/1680071812217.png)', 1, '2023-02-02 10:34:11', 1, '2023-03-29 14:58:49');

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_sort` int NULL DEFAULT NULL COMMENT '字典排序',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典键值',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `style` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, 'sys_user_sex', '女', '0', '0', 'success', NULL, 1, '2023-02-26 13:12:00', 1, '2023-03-19 22:26:36');
INSERT INTO `sys_dict_data` VALUES (2, 2, 'sys_user_sex', '男', '1', '0', 'success', NULL, 1, '2023-02-26 21:18:43', 1, '2023-03-19 22:26:47');
INSERT INTO `sys_dict_data` VALUES (3, 3, 'sys_user_sex', '未知', '2', '0', 'warning', NULL, 1, '2023-02-26 21:41:13', 1, '2023-03-01 11:09:57');
INSERT INTO `sys_dict_data` VALUES (4, 1, 'sys_normal_disable', '正常', '0', '0', 'success', NULL, 1, '2023-02-27 10:37:38', 1, '2023-03-01 15:03:21');
INSERT INTO `sys_dict_data` VALUES (5, 2, 'sys_normal_disable', '停用', '1', '0', 'warning', NULL, 1, '2023-02-27 10:37:49', 1, '2023-03-01 15:03:21');
INSERT INTO `sys_dict_data` VALUES (6, 0, 'sys_request_method', 'POST', 'POST', '0', '', '', 1, '2023-03-30 17:52:52', 1, '2023-03-30 18:13:00');
INSERT INTO `sys_dict_data` VALUES (7, 0, 'sys_request_method', 'DELETE', 'DELETE', '0', 'danger', '', 1, '2023-03-30 17:56:58', 1, '2023-03-30 17:59:16');
INSERT INTO `sys_dict_data` VALUES (8, 0, 'sys_request_method', 'GET', 'GET', '0', 'success', '', 1, '2023-03-30 17:57:13', 1, '2023-03-30 17:59:16');
INSERT INTO `sys_dict_data` VALUES (9, 0, 'sys_request_method', 'PUT', 'PUT', '0', 'warning', '', 1, '2023-03-30 17:57:26', 1, '2023-03-30 17:59:16');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', '用户性别', 1, '2023-02-25 21:52:09', 1, '2023-02-27 17:22:36');
INSERT INTO `sys_dict_type` VALUES (2, '系统状态', 'sys_normal_disable', '0', '系统状态\n', 1, '2023-02-26 09:07:02', 1, '2023-03-01 15:03:21');
INSERT INTO `sys_dict_type` VALUES (3, '请求方式', 'sys_request_method', '0', '请求方式', 1, '2023-03-30 17:52:00', 1, '2023-03-30 17:59:16');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模块名称',
  `method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方式',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求url',
  `oper_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主机地址',
  `oper_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `oper_browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '返回参数',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1异常）',
  `created_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 281 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":31,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"查看\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"system:config:select\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 11:04:08');
INSERT INTO `sys_log` VALUES (2, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":31,\"icon\":\"\",\"menuSort\":1,\"menuName\":\"保存\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"system:config:save\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 11:04:58');
INSERT INTO `sys_log` VALUES (3, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"ffdd2a3c-c1b1-4f80-9084-7773e86d153d\"}', NULL, '0', 'admin', '2023-03-31 11:11:32');
INSERT INTO `sys_log` VALUES (4, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"8e60410c-fbd5-45e9-aa26-3994a179ec46\"}', NULL, '0', 'admin', '2023-03-31 15:42:23');
INSERT INTO `sys_log` VALUES (5, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"dc8038d1-1555-4707-abc0-06c3a2f04f73\"}', NULL, '0', 'admin', '2023-03-31 17:17:42');
INSERT INTO `sys_log` VALUES (6, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":0,\"icon\":\"edit\",\"menuSort\":0,\"menuName\":\"文章管理\",\"menuType\":\"M\",\"path\":\"note\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:55:49');
INSERT INTO `sys_log` VALUES (7, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"parentId\":0,\"icon\":\"el-icon-s-tools\",\"menuSort\":1,\"menuName\":\"系统管理\",\"menuType\":\"M\",\"path\":\"system\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:55:56');
INSERT INTO `sys_log` VALUES (8, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"parentId\":0,\"icon\":\"el-icon-s-tools\",\"menuSort\":2,\"menuName\":\"系统管理\",\"menuType\":\"M\",\"path\":\"system\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:56:02');
INSERT INTO `sys_log` VALUES (9, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":32,\"parentId\":0,\"icon\":\"el-icon-files\",\"menuSort\":3,\"menuName\":\"文件管理\",\"menuType\":\"M\",\"path\":\"file\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:56:06');
INSERT INTO `sys_log` VALUES (10, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":41,\"parentId\":0,\"icon\":\"edit\",\"menuSort\":0,\"menuName\":\"文章\",\"menuType\":\"M\",\"path\":\"note\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:56:43');
INSERT INTO `sys_log` VALUES (11, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":0,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"note\",\"component\":\"note/tages\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:59:09');
INSERT INTO `sys_log` VALUES (12, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":42,\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":0,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"note\",\"component\":\"note/tages/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:59:25');
INSERT INTO `sys_log` VALUES (13, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":41,\"parentId\":0,\"icon\":\"edit\",\"menuSort\":0,\"menuName\":\"文章管理\",\"menuType\":\"M\",\"path\":\"note\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 17:59:32');
INSERT INTO `sys_log` VALUES (14, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":42,\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":0,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"tags\",\"component\":\"note/tags/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-03-31 18:02:04');
INSERT INTO `sys_log` VALUES (15, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"04bcc58e-c4dc-4940-9b53-a2d57a7df7b5\"}', NULL, '0', 'admin', '2023-04-03 09:22:02');
INSERT INTO `sys_log` VALUES (16, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"64350007-584d-44fb-ab42-f10745a03788\"}', NULL, '0', 'admin', '2023-04-03 13:17:43');
INSERT INTO `sys_log` VALUES (17, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":42,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"列表\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:tags:list\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:25:39');
INSERT INTO `sys_log` VALUES (18, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":42,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"新增\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:tags:add\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:25:53');
INSERT INTO `sys_log` VALUES (19, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":44,\"parentId\":42,\"icon\":\"\",\"menuSort\":1,\"menuName\":\"新增\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:tags:add\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:25:57');
INSERT INTO `sys_log` VALUES (20, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":42,\"icon\":\"\",\"menuSort\":2,\"menuName\":\"更新\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:tags:update\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:26:15');
INSERT INTO `sys_log` VALUES (21, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":42,\"icon\":\"\",\"menuSort\":3,\"menuName\":\"删除\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:tags:delete\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:26:35');
INSERT INTO `sys_log` VALUES (22, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":41,\"icon\":\"el-icon-collection-tag\",\"menuSort\":1,\"menuName\":\"分类管理\",\"menuType\":\"C\",\"path\":\"category\",\"component\":\"note/category/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:28:22');
INSERT INTO `sys_log` VALUES (23, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":47,\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":1,\"menuName\":\"分类管理\",\"menuType\":\"C\",\"path\":\"category\",\"component\":\"note/category/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:28:54');
INSERT INTO `sys_log` VALUES (24, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":42,\"parentId\":41,\"icon\":\"el-icon-collection-tag\",\"menuSort\":0,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"tags\",\"component\":\"note/tags/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:29:05');
INSERT INTO `sys_log` VALUES (25, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":47,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"列表\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:category:list\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:53:48');
INSERT INTO `sys_log` VALUES (26, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":47,\"icon\":\"\",\"menuSort\":1,\"menuName\":\"新增\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:category:add\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:54:09');
INSERT INTO `sys_log` VALUES (27, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":47,\"icon\":\"\",\"menuSort\":2,\"menuName\":\"更新\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:category:update\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:54:29');
INSERT INTO `sys_log` VALUES (28, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":47,\"icon\":\"\",\"menuSort\":3,\"menuName\":\"删除\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:category:delete\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:55:00');
INSERT INTO `sys_log` VALUES (29, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,42,43,44,45,46,47,48,49,50,51,6,7,8,1,3,13,10,11,12,31,39,40,4,14,15,16,17,2,18,19,20,21,5,22,23,25,24,30,35,36,37,38,9,26,27,28,29,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 13:55:38');
INSERT INTO `sys_log` VALUES (30, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"21a92721-7929-49c4-8cdb-fc124ccc78e9\"}', NULL, '0', 'admin', '2023-04-03 15:36:54');
INSERT INTO `sys_log` VALUES (31, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"dfefc757-130e-4780-98a2-0f922fbdb553\"}', NULL, '0', 'admin', '2023-04-03 16:37:18');
INSERT INTO `sys_log` VALUES (32, '新增分类', 'com.xiaohai.note.controller.CategoryController.add', 'POST', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"name\":\"技术类\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:02:19');
INSERT INTO `sys_log` VALUES (33, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"name\":\"技术类\",\"sort\":0,\"status\":\"1\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:02:30');
INSERT INTO `sys_log` VALUES (34, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"name\":\"技术类\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:02:33');
INSERT INTO `sys_log` VALUES (35, '新增分类', 'com.xiaohai.note.controller.CategoryController.add', 'POST', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"name\":\"资源类\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:02:54');
INSERT INTO `sys_log` VALUES (36, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"name\":\"技术类\",\"sort\":1,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:02:58');
INSERT INTO `sys_log` VALUES (37, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"name\":\"技术类\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:04:10');
INSERT INTO `sys_log` VALUES (38, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"name\":\"资源类\",\"sort\":1,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:04:13');
INSERT INTO `sys_log` VALUES (39, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"name\":\"资源类\",\"sort\":1,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:04:19');
INSERT INTO `sys_log` VALUES (40, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"name\":\"资源类\",\"sort\":2,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:05:25');
INSERT INTO `sys_log` VALUES (41, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"name\":\"资源类\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:05:30');
INSERT INTO `sys_log` VALUES (42, '更新分类', 'com.xiaohai.note.controller.CategoryController.update', 'PUT', 'http://localhost:8089/api/note/category', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"name\":\"资源类\",\"sort\":2,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新分类成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-03 17:05:33');
INSERT INTO `sys_log` VALUES (43, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"1e854de9-6209-41ee-b10e-9927823c33d7\"}', NULL, '0', 'admin', '2023-04-04 09:35:14');
INSERT INTO `sys_log` VALUES (44, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"acd2500a-ff10-4435-8f97-53f3ac14b24f\"}', NULL, '0', 'admin', '2023-04-04 09:35:20');
INSERT INTO `sys_log` VALUES (45, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"21e4a13f-b81c-4678-b6ff-67d1d8ba75ce\"}', NULL, '0', 'admin', '2023-04-04 09:35:27');
INSERT INTO `sys_log` VALUES (46, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"parentId\":0,\"icon\":\"el-icon-s-tools\",\"menuSort\":1,\"menuName\":\"系统管理\",\"menuType\":\"M\",\"path\":\"system\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 09:36:38');
INSERT INTO `sys_log` VALUES (47, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":6,\"parentId\":0,\"icon\":\"el-icon-cpu\",\"menuSort\":2,\"menuName\":\"系统监控\",\"menuType\":\"M\",\"path\":\"monitor\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 09:36:48');
INSERT INTO `sys_log` VALUES (48, '新增标签', 'com.xiaohai.note.controller.TagsController.add', 'POST', 'http://localhost:8089/api/note/tags', '127.0.0.1', NULL, NULL, '[{\"name\":\"Java\",\"sort\":0,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增标签成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 09:44:14');
INSERT INTO `sys_log` VALUES (49, '新增标签', 'com.xiaohai.note.controller.TagsController.add', 'POST', 'http://localhost:8089/api/note/tags', '127.0.0.1', NULL, NULL, '[{\"name\":\"Linux\",\"sort\":1,\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增标签成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 09:44:22');
INSERT INTO `sys_log` VALUES (50, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"1b6d19e6-829a-4955-8951-7716ba95913a\"}', NULL, '0', 'admin', '2023-04-04 10:41:07');
INSERT INTO `sys_log` VALUES (51, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"b525d894-e904-4e6e-a766-16576ba80e52\"}', NULL, '0', 'admin', '2023-04-04 14:24:05');
INSERT INTO `sys_log` VALUES (52, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":41,\"icon\":\"el-icon-document\",\"menuSort\":0,\"menuName\":\"文章管理\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:26:20');
INSERT INTO `sys_log` VALUES (53, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":52,\"parentId\":41,\"icon\":\"el-icon-document\",\"menuSort\":0,\"menuName\":\"文章管理\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"note/article/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:26:52');
INSERT INTO `sys_log` VALUES (54, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":42,\"parentId\":41,\"icon\":\"el-icon-collection-tag\",\"menuSort\":1,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"tags\",\"component\":\"note/tags/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:27:01');
INSERT INTO `sys_log` VALUES (55, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":47,\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":2,\"menuName\":\"分类管理\",\"menuType\":\"C\",\"path\":\"category\",\"component\":\"note/category/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:27:05');
INSERT INTO `sys_log` VALUES (56, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:27:53');
INSERT INTO `sys_log` VALUES (57, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"列表\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:list\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:33:19');
INSERT INTO `sys_log` VALUES (58, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":1,\"menuName\":\"新增\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:add\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:33:59');
INSERT INTO `sys_log` VALUES (59, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":2,\"menuName\":\"更新\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:update\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:34:15');
INSERT INTO `sys_log` VALUES (60, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":3,\"menuName\":\"删除\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:delete\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-04 14:34:45');
INSERT INTO `sys_log` VALUES (61, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"ef6e6980-6bf6-40b9-a8bf-ba28362f92b5\"}', NULL, '0', 'admin', '2023-04-04 17:09:52');
INSERT INTO `sys_log` VALUES (62, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"889d376e-6c58-4ab5-b406-68719b220789\"}', NULL, '0', 'admin', '2023-04-06 10:32:32');
INSERT INTO `sys_log` VALUES (63, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"34907dc0-04b0-49d9-a23f-d49f8ad5fe5d\"}', NULL, '0', 'admin', '2023-04-06 11:51:18');
INSERT INTO `sys_log` VALUES (64, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"df96bfe8-5ef4-486d-8989-dfa727472ee2\"}', NULL, '0', 'admin', '2023-04-06 13:03:42');
INSERT INTO `sys_log` VALUES (65, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"158a801e-3b88-41d0-8512-f695986aa6f4\"}', NULL, '0', 'admin', '2023-04-06 13:11:07');
INSERT INTO `sys_log` VALUES (66, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 13:21:51');
INSERT INTO `sys_log` VALUES (67, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"433a1a70-79e2-4008-a4b8-40e2d02d1ff3\"}', NULL, '0', 'admin', '2023-04-06 15:35:08');
INSERT INTO `sys_log` VALUES (68, '更新用户', 'com.xiaohai.system.controller.UserController.update', 'PUT', 'http://localhost:8089/api/system/user', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"username\":\"user\",\"nickName\":\"普通用户\",\"gender\":\"2\",\"avatar\":\"\",\"status\":\"0\",\"roleIds\":[2,1]}]', '{\"code\":200,\"msg\":\"更新用户表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 16:00:30');
INSERT INTO `sys_log` VALUES (69, '更新用户', 'com.xiaohai.system.controller.UserController.update', 'PUT', 'http://localhost:8089/api/system/user', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"username\":\"user\",\"nickName\":\"普通用户\",\"gender\":\"2\",\"avatar\":\"\",\"status\":\"0\",\"roleIds\":[2]}]', '{\"code\":200,\"msg\":\"更新用户表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 16:00:42');
INSERT INTO `sys_log` VALUES (70, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":52,\"parentId\":0,\"icon\":\"el-icon-document\",\"menuSort\":0,\"menuName\":\"文章中心\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"note/article/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 16:04:01');
INSERT INTO `sys_log` VALUES (71, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":52,\"parentId\":41,\"icon\":\"el-icon-document\",\"menuSort\":0,\"menuName\":\"文章中心\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"note/article/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 16:04:26');
INSERT INTO `sys_log` VALUES (72, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"847be88a-b28c-43db-808c-af8e218ca2ab\"}', NULL, '0', 'admin', '2023-04-06 16:37:24');
INSERT INTO `sys_log` VALUES (73, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片必应成功！\",\"data\":\"/image/1/20230330.jpg\"}', NULL, '0', 'admin', '2023-04-06 16:44:11');
INSERT INTO `sys_log` VALUES (74, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片必应成功！\",\"data\":\"/image/1/20230405.jpg\"}', NULL, '0', 'admin', '2023-04-06 16:57:44');
INSERT INTO `sys_log` VALUES (75, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":0,\"menuName\":\"写作\",\"menuType\":\"C\",\"path\":\"a\",\"component\":\"\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 17:36:31');
INSERT INTO `sys_log` VALUES (76, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":57,\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":0,\"menuName\":\"写作\",\"menuType\":\"C\",\"path\":\"edit:/\",\"component\":\"note/edit/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 17:37:19');
INSERT INTO `sys_log` VALUES (77, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"6c64a175-2606-42b8-9cd7-eb2afdf32cc1\"}', NULL, '0', 'admin', '2023-04-06 17:37:28');
INSERT INTO `sys_log` VALUES (78, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":57,\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":0,\"menuName\":\"写作\",\"menuType\":\"C\",\"path\":\"edit\",\"component\":\"note/edit/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 17:37:38');
INSERT INTO `sys_log` VALUES (79, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,57,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,31,39,40,4,14,15,16,17,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-06 17:39:10');
INSERT INTO `sys_log` VALUES (80, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片必应成功！\",\"data\":\"/image/1/20230401.jpg\"}', NULL, '0', 'admin', '2023-04-06 17:42:23');
INSERT INTO `sys_log` VALUES (81, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"78e89227-d08b-4641-8c92-77c88b41c427\"}', NULL, '0', 'admin', '2023-04-07 10:59:43');
INSERT INTO `sys_log` VALUES (82, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":57,\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":0,\"menuName\":\"写作中心\",\"menuType\":\"C\",\"path\":\"edit\",\"component\":\"note/edit/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 11:00:15');
INSERT INTO `sys_log` VALUES (83, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":57,\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":0,\"menuName\":\"写作中心\",\"menuType\":\"C\",\"path\":\"edit\",\"component\":\"note/edit/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 11:00:20');
INSERT INTO `sys_log` VALUES (84, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":52,\"parentId\":41,\"icon\":\"el-icon-document\",\"menuSort\":1,\"menuName\":\"文章中心\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"note/article/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 11:00:24');
INSERT INTO `sys_log` VALUES (85, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":42,\"parentId\":41,\"icon\":\"el-icon-collection-tag\",\"menuSort\":2,\"menuName\":\"标签管理\",\"menuType\":\"C\",\"path\":\"tags\",\"component\":\"note/tags/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 11:00:29');
INSERT INTO `sys_log` VALUES (86, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":47,\"parentId\":41,\"icon\":\"el-icon-price-tag\",\"menuSort\":3,\"menuName\":\"分类管理\",\"menuType\":\"C\",\"path\":\"category\",\"component\":\"note/category/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 11:00:36');
INSERT INTO `sys_log` VALUES (87, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"b22c4408-df6a-4480-93aa-5660c4cbf90e\"}', NULL, '0', 'admin', '2023-04-07 11:26:41');
INSERT INTO `sys_log` VALUES (88, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"378b6ff5-dd59-4b1d-874b-c6070deb3961\"}', NULL, '0', 'admin', '2023-04-07 12:31:43');
INSERT INTO `sys_log` VALUES (89, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230404.jpg\"}', NULL, '0', 'admin', '2023-04-07 12:32:48');
INSERT INTO `sys_log` VALUES (90, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230406.jpg\"}', NULL, '0', 'admin', '2023-04-07 12:39:13');
INSERT INTO `sys_log` VALUES (91, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"db3f06df-9ab8-4877-8aab-c4257e0eb5d4\"}', NULL, '0', 'admin', '2023-04-07 13:43:48');
INSERT INTO `sys_log` VALUES (92, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230403.jpg\"}', NULL, '0', 'admin', '2023-04-07 14:09:46');
INSERT INTO `sys_log` VALUES (93, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"fff\",\"categoryId\":1,\"tags\":[1],\"cover\":\"\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 14:17:08');
INSERT INTO `sys_log` VALUES (94, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"g\",\"categoryId\":1,\"tags\":[1],\"cover\":\"\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 14:18:14');
INSERT INTO `sys_log` VALUES (95, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"t\",\"categoryId\":1,\"tags\":[1],\"cover\":\"\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 14:18:30');
INSERT INTO `sys_log` VALUES (96, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"u\",\"categoryId\":1,\"tags\":[1],\"cover\":\"\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 14:19:55');
INSERT INTO `sys_log` VALUES (97, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"c47d6d0b-1ac1-402d-82fd-f093a883deef\"}', NULL, '0', 'admin', '2023-04-07 14:44:34');
INSERT INTO `sys_log` VALUES (98, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230406.jpg\"}', NULL, '0', 'admin', '2023-04-07 15:25:46');
INSERT INTO `sys_log` VALUES (99, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"测试\",\"categoryId\":1,\"tags\":[1,2],\"cover\":\"/image/1/20230406.jpg\",\"isTop\":0,\"isPush\":1,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦啦啦啦啦啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n啦啦啦啦\\n\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 15:26:12');
INSERT INTO `sys_log` VALUES (100, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230330.jpg\"}', NULL, '0', 'admin', '2023-04-07 15:27:52');
INSERT INTO `sys_log` VALUES (101, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 15:28:07');
INSERT INTO `sys_log` VALUES (102, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', NULL, 'class java.lang.String cannot be cast to class java.lang.Integer (java.lang.String and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', 'admin', '2023-04-07 15:30:32');
INSERT INTO `sys_log` VALUES (103, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"新增文章表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 15:32:06');
INSERT INTO `sys_log` VALUES (104, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"84b32f62-8bdb-4238-a267-32bb422d1a31\"}', NULL, '0', 'admin', '2023-04-07 15:45:26');
INSERT INTO `sys_log` VALUES (105, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', NULL, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\r\n### The error may exist in com/xiaohai/note/dao/ArticleTagMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: DELETE FROM b_article_tag     WHERE (articleId = ?)\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'', '1', 'admin', '2023-04-07 16:13:04');
INSERT INTO `sys_log` VALUES (106, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', NULL, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\r\n### The error may exist in com/xiaohai/note/dao/ArticleTagMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: DELETE FROM b_article_tag     WHERE (articleId = ?)\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'', '1', 'admin', '2023-04-07 16:15:02');
INSERT INTO `sys_log` VALUES (107, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', NULL, '\r\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\r\n### The error may exist in com/xiaohai/note/dao/ArticleTagMapper.java (best guess)\r\n### The error may involve defaultParameterMap\r\n### The error occurred while setting parameters\r\n### SQL: DELETE FROM b_article_tag     WHERE (articleId = ?)\r\n### Cause: java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: Unknown column \'articleId\' in \'where clause\'', '1', 'admin', '2023-04-07 16:18:31');
INSERT INTO `sys_log` VALUES (108, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 16:19:18');
INSERT INTO `sys_log` VALUES (109, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":57,\"parentId\":41,\"icon\":\"el-icon-edit\",\"menuSort\":1,\"menuName\":\"写作中心\",\"menuType\":\"C\",\"path\":\"edit\",\"component\":\"note/edit/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 16:37:23');
INSERT INTO `sys_log` VALUES (110, '更新菜单权限', 'com.xiaohai.system.controller.MenuController.update', 'PUT', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"id\":52,\"parentId\":41,\"icon\":\"el-icon-document\",\"menuSort\":0,\"menuName\":\"文章中心\",\"menuType\":\"C\",\"path\":\"article\",\"component\":\"note/article/index\",\"perms\":\"\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"更新菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 16:37:27');
INSERT INTO `sys_log` VALUES (111, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"0f51e649-b28c-4137-b468-4cf7209dfb1a\"}', NULL, '0', 'admin', '2023-04-07 16:56:48');
INSERT INTO `sys_log` VALUES (112, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230403.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:58:51');
INSERT INTO `sys_log` VALUES (113, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230404.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:17');
INSERT INTO `sys_log` VALUES (114, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230402.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:19');
INSERT INTO `sys_log` VALUES (115, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230405.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:20');
INSERT INTO `sys_log` VALUES (116, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230404.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:21');
INSERT INTO `sys_log` VALUES (117, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230331.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:22');
INSERT INTO `sys_log` VALUES (118, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230402.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:23');
INSERT INTO `sys_log` VALUES (119, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230402.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:24');
INSERT INTO `sys_log` VALUES (120, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230402.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:25');
INSERT INTO `sys_log` VALUES (121, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230404.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:26');
INSERT INTO `sys_log` VALUES (122, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230401.jpg\"}', NULL, '0', 'admin', '2023-04-07 16:59:27');
INSERT INTO `sys_log` VALUES (123, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230405.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:05:03');
INSERT INTO `sys_log` VALUES (124, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230330.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:05:13');
INSERT INTO `sys_log` VALUES (125, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230401.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:08:51');
INSERT INTO `sys_log` VALUES (126, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230330.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:08:53');
INSERT INTO `sys_log` VALUES (127, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230405.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:08:54');
INSERT INTO `sys_log` VALUES (128, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230401.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:08:57');
INSERT INTO `sys_log` VALUES (129, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":1,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 17:26:14');
INSERT INTO `sys_log` VALUES (130, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 17:26:42');
INSERT INTO `sys_log` VALUES (131, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230330.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 17:28:27');
INSERT INTO `sys_log` VALUES (132, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230403.jpg\"}', NULL, '0', 'admin', '2023-04-07 17:41:22');
INSERT INTO `sys_log` VALUES (133, '新增文章', 'com.xiaohai.note.controller.ArticleController.add', 'POST', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"title\":\"测试222\",\"categoryId\":1,\"tags\":[1,2],\"cover\":\"/image/1/20230403.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":0,\"originalUrl\":\"\",\"text\":\"是大V发SV地方\\n# 是是是 \"}]', '{\"code\":200,\"msg\":\"新增文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-07 17:42:01');
INSERT INTO `sys_log` VALUES (134, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"791f8b09-3f5e-4bc6-ae88-f2a49550e4b2\"}', NULL, '0', 'admin', '2023-04-07 20:11:56');
INSERT INTO `sys_log` VALUES (135, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"637e6305-bd18-4df6-be0d-5bdb8b7be1eb\"}', NULL, '0', 'admin', '2023-04-08 00:03:33');
INSERT INTO `sys_log` VALUES (136, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230406.jpg\"}', NULL, '0', 'admin', '2023-04-08 00:03:41');
INSERT INTO `sys_log` VALUES (137, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"4aa6aa03-2b58-4de0-9da9-ac3301ebc4d3\"}', NULL, '0', 'admin', '2023-04-08 13:53:02');
INSERT INTO `sys_log` VALUES (138, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230401.jpg\"}', NULL, '0', 'admin', '2023-04-08 13:54:04');
INSERT INTO `sys_log` VALUES (139, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230401.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 13:54:05');
INSERT INTO `sys_log` VALUES (140, '获取随机图片(必应)', 'com.xiaohai.note.controller.ArticleController.wallpaper', 'GET', 'http://localhost:8089/api/note/article/bing-wallpaper', '127.0.0.1', NULL, NULL, '[]', '{\"code\":200,\"msg\":\"获取随机图片成功！\",\"data\":\"/image/1/20230402.jpg\"}', NULL, '0', 'admin', '2023-04-08 13:54:16');
INSERT INTO `sys_log` VALUES (141, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"title\":\"测试222\",\"categoryId\":1,\"tags\":[1,2],\"cover\":\"/image/1/20230402.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":0,\"originalUrl\":\"\",\"text\":\"是大V发SV地方\\n# 是是是 \"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 13:54:17');
INSERT INTO `sys_log` VALUES (142, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"title\":\"测试222\",\"categoryId\":2,\"tags\":[1,2],\"cover\":\"/image/1/20230402.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":0,\"originalUrl\":\"\",\"text\":\"是大V发SV地方\\n# 是是是 \"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 14:20:22');
INSERT INTO `sys_log` VALUES (143, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":2,\"title\":\"测试222\",\"categoryId\":2,\"tags\":[1,2],\"cover\":\"/image/1/20230402.jpg\",\"isTop\":1,\"isPush\":0,\"isOriginal\":0,\"originalUrl\":\"\",\"text\":\"是大V发SV地方\\n# 是是是 \"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 14:50:00');
INSERT INTO `sys_log` VALUES (144, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"301e0e4b-c43d-449e-a0dd-38b1d3e65fe2\"}', NULL, '0', 'admin', '2023-04-08 15:06:32');
INSERT INTO `sys_log` VALUES (145, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"5ff1cc88-ea20-4bb7-8d17-263e3832963c\"}', NULL, '0', 'admin', '2023-04-08 20:09:35');
INSERT INTO `sys_log` VALUES (146, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"c5c12b9b-9e6e-4470-8d82-c6fd71fd19bb\"}', NULL, '0', 'admin', '2023-04-08 21:30:44');
INSERT INTO `sys_log` VALUES (147, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":4,\"menuName\":\"是否顶置\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:top\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:34:32');
INSERT INTO `sys_log` VALUES (148, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":52,\"icon\":\"\",\"menuSort\":5,\"menuName\":\"是否发布\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:push\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:34:51');
INSERT INTO `sys_log` VALUES (149, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":57,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"保存\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:save\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:44:59');
INSERT INTO `sys_log` VALUES (150, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,60,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:47:19');
INSERT INTO `sys_log` VALUES (151, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:48:44');
INSERT INTO `sys_log` VALUES (152, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":57,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"查看\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"note:article:select\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:49:53');
INSERT INTO `sys_log` VALUES (153, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,57,52,53,54,55,56,58,59,61,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:51:04');
INSERT INTO `sys_log` VALUES (154, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230401.jpg\",\"isTop\":0,\"isPush\":0,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:51:24');
INSERT INTO `sys_log` VALUES (155, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/61', '127.0.0.1', NULL, NULL, '[61]', '{\"code\":200,\"msg\":\"删除菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 21:59:12');
INSERT INTO `sys_log` VALUES (156, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/60', '127.0.0.1', NULL, NULL, '[60]', '{\"code\":200,\"msg\":\"删除菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:00:47');
INSERT INTO `sys_log` VALUES (157, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":57,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"查看\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"aa\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:06:47');
INSERT INTO `sys_log` VALUES (158, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,62,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:07:57');
INSERT INTO `sys_log` VALUES (159, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/62', '127.0.0.1', NULL, NULL, '[62]', '{\"code\":200,\"msg\":\"删除菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:08:07');
INSERT INTO `sys_log` VALUES (160, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":57,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"的\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"1\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:09:00');
INSERT INTO `sys_log` VALUES (161, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,63,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:09:12');
INSERT INTO `sys_log` VALUES (162, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/63', '127.0.0.1', NULL, NULL, '[63]', '{\"code\":200,\"msg\":\"删除菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:10:53');
INSERT INTO `sys_log` VALUES (163, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', NULL, NULL, '[{\"parentId\":57,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"3\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"33\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:11:47');
INSERT INTO `sys_log` VALUES (164, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:11:59');
INSERT INTO `sys_log` VALUES (165, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:13:31');
INSERT INTO `sys_log` VALUES (166, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:16:38');
INSERT INTO `sys_log` VALUES (167, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:17:50');
INSERT INTO `sys_log` VALUES (168, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:28:16');
INSERT INTO `sys_log` VALUES (169, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"05d01109-82b2-4898-83a9-fcc3efc06d06\"}', NULL, '0', 'admin', '2023-04-08 22:31:17');
INSERT INTO `sys_log` VALUES (170, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:33:53');
INSERT INTO `sys_log` VALUES (171, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,56,58,59,57,64,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:34:22');
INSERT INTO `sys_log` VALUES (172, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,64,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 22:37:55');
INSERT INTO `sys_log` VALUES (173, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:38:45');
INSERT INTO `sys_log` VALUES (174, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, '当前菜单存在绑定，无法删除', '1', 'admin', '2023-04-08 22:48:02');
INSERT INTO `sys_log` VALUES (175, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, NULL, '0', 'admin', '2023-04-08 22:48:34');
INSERT INTO `sys_log` VALUES (176, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', NULL, NULL, '0', 'admin', '2023-04-08 22:55:18');
INSERT INTO `sys_log` VALUES (177, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', '{\"code\":400,\"msg\":\"当前菜单存在绑定，无法删除\"}', NULL, '0', 'admin', '2023-04-08 23:22:58');
INSERT INTO `sys_log` VALUES (178, '删除角色', 'com.xiaohai.system.controller.RoleController.delete', 'DELETE', 'http://localhost:8089/api/system/role/3', '127.0.0.1', NULL, NULL, '[[3]]', '{\"code\":400,\"msg\":\"当前角色存在用户，无法删除\"}', NULL, '0', 'admin', '2023-04-08 23:24:00');
INSERT INTO `sys_log` VALUES (179, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:25:41');
INSERT INTO `sys_log` VALUES (180, '删除菜单权限', 'com.xiaohai.system.controller.MenuController.delete', 'DELETE', 'http://localhost:8089/api/system/menu/64', '127.0.0.1', NULL, NULL, '[64]', '{\"code\":200,\"msg\":\"删除菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:26:25');
INSERT INTO `sys_log` VALUES (181, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:26:37');
INSERT INTO `sys_log` VALUES (182, '是否发布', 'com.xiaohai.note.controller.ArticleController.push', 'PUT', 'http://localhost:8089/api/note/article/push/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"调整成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:26:56');
INSERT INTO `sys_log` VALUES (183, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:27:03');
INSERT INTO `sys_log` VALUES (184, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:27:13');
INSERT INTO `sys_log` VALUES (185, '是否发布', 'com.xiaohai.note.controller.ArticleController.push', 'PUT', 'http://localhost:8089/api/note/article/push/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"调整成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-08 23:27:22');
INSERT INTO `sys_log` VALUES (186, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"2029864d-a1c2-4d7c-9188-0ca325b7f405\"}', NULL, '0', 'admin', '2023-04-08 23:38:54');
INSERT INTO `sys_log` VALUES (187, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:16:50');
INSERT INTO `sys_log` VALUES (188, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:16:51');
INSERT INTO `sys_log` VALUES (189, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230401.jpg\",\"isTop\":0,\"isPush\":1,\"isOriginal\":0,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:24:53');
INSERT INTO `sys_log` VALUES (190, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:29:21');
INSERT INTO `sys_log` VALUES (191, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:29:22');
INSERT INTO `sys_log` VALUES (192, '更新文章', 'com.xiaohai.note.controller.ArticleController.update', 'PUT', 'http://localhost:8089/api/note/article', '127.0.0.1', NULL, NULL, '[{\"id\":1,\"title\":\"测试\",\"categoryId\":1,\"tags\":[1],\"cover\":\"/image/1/20230401.jpg\",\"isTop\":0,\"isPush\":1,\"isOriginal\":1,\"originalUrl\":\"\",\"text\":\"StpUtil.getLoginId()\"}]', '{\"code\":200,\"msg\":\"更新文章成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 00:29:28');
INSERT INTO `sys_log` VALUES (193, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"a21fed71-63ff-4121-8e8d-1e3fa2aaecc1\"}', NULL, '0', 'admin', '2023-04-09 09:46:39');
INSERT INTO `sys_log` VALUES (194, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:47:15');
INSERT INTO `sys_log` VALUES (195, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:47:16');
INSERT INTO `sys_log` VALUES (196, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:53:43');
INSERT INTO `sys_log` VALUES (197, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:53:46');
INSERT INTO `sys_log` VALUES (198, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:48');
INSERT INTO `sys_log` VALUES (199, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:51');
INSERT INTO `sys_log` VALUES (200, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:53');
INSERT INTO `sys_log` VALUES (201, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:55');
INSERT INTO `sys_log` VALUES (202, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:56');
INSERT INTO `sys_log` VALUES (203, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:54:59');
INSERT INTO `sys_log` VALUES (204, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:55:00');
INSERT INTO `sys_log` VALUES (205, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 09:55:01');
INSERT INTO `sys_log` VALUES (206, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"65cd0e2e-4705-40e3-98ac-5639fea8b648\"}', NULL, '0', 'admin', '2023-04-09 14:02:15');
INSERT INTO `sys_log` VALUES (207, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"f4ca4882-cf5f-4051-93d2-305c3cdbcefd\"}', NULL, '0', 'admin', '2023-04-09 19:44:16');
INSERT INTO `sys_log` VALUES (208, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":400,\"msg\":\"已存在顶置\"}', NULL, '0', 'admin', '2023-04-09 19:44:19');
INSERT INTO `sys_log` VALUES (209, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 19:44:22');
INSERT INTO `sys_log` VALUES (210, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-09 19:44:25');
INSERT INTO `sys_log` VALUES (211, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":400,\"msg\":\"已存在顶置\"}', NULL, '0', 'admin', '2023-04-09 19:44:26');
INSERT INTO `sys_log` VALUES (212, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"db3e4c46-01c2-4475-91da-f195ba0dda18\"}', NULL, '0', 'admin', '2023-04-11 09:35:48');
INSERT INTO `sys_log` VALUES (213, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:35:51');
INSERT INTO `sys_log` VALUES (214, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:35:52');
INSERT INTO `sys_log` VALUES (215, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":400,\"msg\":\"已存在顶置\"}', NULL, '0', 'admin', '2023-04-11 09:35:54');
INSERT INTO `sys_log` VALUES (216, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":400,\"msg\":\"已存在顶置\"}', NULL, '0', 'admin', '2023-04-11 09:36:00');
INSERT INTO `sys_log` VALUES (217, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:36:01');
INSERT INTO `sys_log` VALUES (218, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:36:02');
INSERT INTO `sys_log` VALUES (219, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/2', '127.0.0.1', NULL, NULL, '[2]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:36:04');
INSERT INTO `sys_log` VALUES (220, '是否顶置', 'com.xiaohai.note.controller.ArticleController.top', 'PUT', 'http://localhost:8089/api/note/article/top/1', '127.0.0.1', NULL, NULL, '[1]', '{\"code\":200,\"msg\":\"修改成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-11 09:36:05');
INSERT INTO `sys_log` VALUES (221, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"0059fd58-ce90-4c31-9cd1-4b3324c395a0\"}', NULL, '0', 'admin', '2023-04-11 11:44:29');
INSERT INTO `sys_log` VALUES (222, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"834426d0-efe4-45e8-922f-05330bd04f51\"}', NULL, '0', 'admin', '2023-04-11 13:36:35');
INSERT INTO `sys_log` VALUES (223, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"3abedf13-2f2b-4d1f-8106-4e45fab1f8a0\"}', NULL, '0', 'admin', '2023-04-11 13:38:00');
INSERT INTO `sys_log` VALUES (224, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'class java.lang.Long cannot be cast to class java.lang.Integer (java.lang.Long and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', NULL, '2023-04-11 14:02:21');
INSERT INTO `sys_log` VALUES (225, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'class java.lang.Long cannot be cast to class java.lang.Integer (java.lang.Long and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', NULL, '2023-04-11 14:03:29');
INSERT INTO `sys_log` VALUES (226, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'class java.lang.Long cannot be cast to class java.lang.Integer (java.lang.Long and java.lang.Integer are in module java.base of loader \'bootstrap\')', '1', NULL, '2023-04-11 14:04:36');
INSERT INTO `sys_log` VALUES (227, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'class java.lang.Long cannot be cast to class java.lang.String (java.lang.Long and java.lang.String are in module java.base of loader \'bootstrap\')', '1', NULL, '2023-04-11 14:06:17');
INSERT INTO `sys_log` VALUES (228, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'class java.lang.Long cannot be cast to class java.lang.String (java.lang.Long and java.lang.String are in module java.base of loader \'bootstrap\')', '1', NULL, '2023-04-11 14:10:01');
INSERT INTO `sys_log` VALUES (229, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"7b051f27-bde8-4654-969a-e9c476d8c278\"}', NULL, '0', 'admin', '2023-04-11 14:10:23');
INSERT INTO `sys_log` VALUES (230, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"9159cedf-8ddd-4b32-9fc3-4a736f3f8973\"}', NULL, '0', 'admin', '2023-04-11 16:31:32');
INSERT INTO `sys_log` VALUES (231, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"93314eff-3588-4f7b-82af-af0a16a30803\"}', NULL, '0', 'admin', '2023-04-11 17:16:54');
INSERT INTO `sys_log` VALUES (232, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"4813827c-ed13-4ced-aa1f-5f2b6051d772\"}', NULL, '0', 'admin', '2023-04-11 17:17:14');
INSERT INTO `sys_log` VALUES (233, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"ef950442-3b3b-4f06-8e5d-ecf9cd7cfc55\"}', NULL, '0', 'admin', '2023-04-11 17:20:53');
INSERT INTO `sys_log` VALUES (234, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"6ed477bf-7570-44a8-81c1-d698239e1b22\"}', NULL, '0', 'admin', '2023-04-11 17:43:57');
INSERT INTO `sys_log` VALUES (235, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"dc442bbc-d2af-4045-9259-15df7e174bce\"}', NULL, '0', 'admin', '2023-04-11 17:44:14');
INSERT INTO `sys_log` VALUES (236, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"199ad13c-6805-4b9e-b31b-6ce3b039bd43\"}', NULL, '0', 'admin', '2023-04-11 17:44:55');
INSERT INTO `sys_log` VALUES (237, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', NULL, NULL, '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"55db8c8e-d9f1-4d0f-93ed-f6f85b592b34\"}', NULL, '0', 'admin', '2023-04-11 17:47:50');
INSERT INTO `sys_log` VALUES (238, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"f272c9be-6100-4fdc-9bdd-8b8d753c0819\"}', NULL, '0', 'admin', '2023-04-11 18:00:54');
INSERT INTO `sys_log` VALUES (239, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"53c54ff9-91d1-4f3b-99fd-572790b0146d\"}', NULL, '0', 'admin', '2023-04-12 15:42:16');
INSERT INTO `sys_log` VALUES (240, '发送邮箱验证码', 'com.xiaohai.system.controller.LoginController.sendEmailCode', 'GET', 'http://localhost:8089/api/sendEmailCode', '127.0.0.1', 'Windows 10', 'Chrome 11', '[\"1372195290@qq.cpm\"]', '{\"code\":200,\"msg\":\"验证码已发送，请前往邮箱查看!\"}', NULL, '0', NULL, '2023-04-12 15:49:09');
INSERT INTO `sys_log` VALUES (241, '发送邮箱验证码', 'com.xiaohai.system.controller.LoginController.sendEmailCode', 'GET', 'http://localhost:8089/api/sendEmailCode', '127.0.0.1', 'Windows 10', 'Chrome 11', '[\"1372195290@qq.com\"]', '{\"code\":200,\"msg\":\"验证码已发送，请前往邮箱查看!\"}', NULL, '0', NULL, '2023-04-12 15:50:11');
INSERT INTO `sys_log` VALUES (242, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: ERR invalid expire time in setex', '1', NULL, '2023-04-12 16:23:54');
INSERT INTO `sys_log` VALUES (243, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: ERR invalid expire time in setex', '1', NULL, '2023-04-12 16:30:39');
INSERT INTO `sys_log` VALUES (244, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: ERR invalid expire time in setex', '1', NULL, '2023-04-12 16:35:29');
INSERT INTO `sys_log` VALUES (245, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', NULL, 'Error in execution; nested exception is io.lettuce.core.RedisCommandExecutionException: ERR invalid expire time in setex', '1', NULL, '2023-04-12 16:44:17');
INSERT INTO `sys_log` VALUES (246, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"7ce97537-2624-416c-b04a-a4259acdefc5\"}', NULL, '0', 'admin', '2023-04-12 16:45:29');
INSERT INTO `sys_log` VALUES (247, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"fb345188-7a95-4db5-bf84-273046816881\"}', NULL, '0', 'admin', '2023-04-12 17:46:48');
INSERT INTO `sys_log` VALUES (248, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"test\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"c1fb5db6-7887-4cc9-86f2-f1dd1f90a00a\"}', NULL, '0', 'test', '2023-04-12 18:00:06');
INSERT INTO `sys_log` VALUES (249, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"user\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"42d7fe87-115c-4877-b69e-c870bef395dd\"}', NULL, '0', 'user', '2023-04-12 18:00:13');
INSERT INTO `sys_log` VALUES (250, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"236c2d05-0fdb-4848-beaf-99b732a7be29\"}', NULL, '0', 'admin', '2023-04-13 11:25:26');
INSERT INTO `sys_log` VALUES (251, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"user\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"1c1d0573-d5a9-4cc9-8d11-a3f0447e050a\"}', NULL, '0', 'user', '2023-04-13 11:53:41');
INSERT INTO `sys_log` VALUES (252, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"c9861c03-2661-4b68-8b16-5c4c86a6df6f\"}', NULL, '0', 'admin', '2023-04-13 11:53:47');
INSERT INTO `sys_log` VALUES (253, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"test\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"baaa8dc2-2dec-4632-a45f-9f3567caa032\"}', NULL, '0', 'test', '2023-04-13 11:53:53');
INSERT INTO `sys_log` VALUES (254, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://172.16.100.46:8089/api/login', '172.16.100.46', 'Windows 10', 'Chrome 11', '[{\"username\":\"test\",\"password\":\"123456\",\"rememberMe\":true}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"2f1db1cd-ec37-4260-ae25-4a62e629fc54\"}', NULL, '0', 'test', '2023-04-13 11:54:31');
INSERT INTO `sys_log` VALUES (255, '退出在线用户', 'com.xiaohai.admin.monitor.ServerController.kickOut', 'DELETE', 'http://localhost:8089/api/monitor/server/on-line-user/2', '127.0.0.1', 'Windows 10', 'Chrome 11', '[\"2\"]', '{\"code\":200,\"msg\":\"退出在线用户成功！\",\"data\":\"2\"}', NULL, '0', 'admin', '2023-04-13 11:55:57');
INSERT INTO `sys_log` VALUES (256, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"eb4e12dd-793f-44a6-b371-8bf0392e3465\"}', NULL, '0', 'admin', '2023-04-13 13:06:19');
INSERT INTO `sys_log` VALUES (257, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"parentId\":8,\"icon\":\"\",\"menuSort\":0,\"menuName\":\"列表\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"monitor:online:list\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-13 13:22:58');
INSERT INTO `sys_log` VALUES (258, '新增菜单权限', 'com.xiaohai.system.controller.MenuController.add', 'POST', 'http://localhost:8089/api/system/menu', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"parentId\":8,\"icon\":\"\",\"menuSort\":1,\"menuName\":\"退出\",\"menuType\":\"F\",\"path\":\"\",\"component\":\"\",\"perms\":\"monitor:online:delete\",\"status\":\"0\"}]', '{\"code\":200,\"msg\":\"新增菜单权限表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-13 13:23:18');
INSERT INTO `sys_log` VALUES (259, '更新角色', 'com.xiaohai.system.controller.RoleController.update', 'PUT', 'http://localhost:8089/api/system/role', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"id\":1,\"code\":\"admin\",\"name\":\"管理员\",\"remarks\":\"可以管理所有东西\",\"status\":\"0\",\"menuIds\":[41,52,53,54,55,56,58,59,57,42,43,44,45,46,47,48,49,50,51,1,3,13,10,11,12,4,14,15,16,17,31,39,40,2,18,19,20,21,5,22,23,25,24,30,9,26,27,28,29,35,36,37,38,6,7,8,65,66,32,33,34]}]', '{\"code\":200,\"msg\":\"更新角色表成功！\",\"data\":1}', NULL, '0', 'admin', '2023-04-13 13:39:33');
INSERT INTO `sys_log` VALUES (260, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"120767b6-de66-4d45-843d-e611beaff081\"}', NULL, '0', 'admin', '2023-04-13 17:27:56');
INSERT INTO `sys_log` VALUES (261, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"291e51d2-df39-4f58-94eb-21c7afc0e17e\"}', NULL, '0', 'admin', '2023-04-14 10:26:36');
INSERT INTO `sys_log` VALUES (262, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"6f6e2dbe-4b41-4068-b477-21672e6ebca8\"}', NULL, '0', 'admin', '2023-04-14 15:28:31');
INSERT INTO `sys_log` VALUES (263, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"7f84de80-5a66-4825-a066-572dc77ae101\"}', NULL, '0', 'admin', '2023-04-14 15:29:40');
INSERT INTO `sys_log` VALUES (264, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"c821d147-986e-44f5-852d-fc7b9e17c2ca\"}', NULL, '0', 'admin', '2023-04-14 15:34:12');
INSERT INTO `sys_log` VALUES (265, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"ff975ad4-7818-4325-8a2b-e5d37d228020\"}', NULL, '0', 'admin', '2023-04-14 16:49:11');
INSERT INTO `sys_log` VALUES (266, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"17e29d9b-c979-4458-a735-8991c581bac8\"}', NULL, '0', 'admin', '2023-04-14 17:55:41');
INSERT INTO `sys_log` VALUES (267, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"dac6f2fb-cc72-4728-a06b-e309879bd52c\"}', NULL, '0', 'admin', '2023-04-14 19:57:03');
INSERT INTO `sys_log` VALUES (268, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"b9bbfce5-f578-4a4b-aebf-2fbb3c8c0705\"}', NULL, '0', 'admin', '2023-04-14 20:57:44');
INSERT INTO `sys_log` VALUES (269, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"6440c89b-8ffc-4318-b5eb-4d146f5f34bf\"}', NULL, '0', 'admin', '2023-04-14 20:57:49');
INSERT INTO `sys_log` VALUES (270, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"76a26bb7-9667-4348-8110-f14e21960271\"}', NULL, '0', 'admin', '2023-04-14 22:12:05');
INSERT INTO `sys_log` VALUES (271, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"8d5f2a31-47a9-4daf-96a3-8f978c2ce92a\"}', NULL, '0', 'admin', '2023-04-14 23:17:51');
INSERT INTO `sys_log` VALUES (272, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"fb5f3173-62b2-45df-8b8b-c1af8b3779db\"}', NULL, '0', 'admin', '2023-04-15 00:18:23');
INSERT INTO `sys_log` VALUES (273, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"e355859e-95d1-4996-972e-52d2a27117a7\"}', NULL, '0', 'admin', '2023-04-15 09:10:47');
INSERT INTO `sys_log` VALUES (274, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"77ccdbf8-9b4f-4bb6-8ea9-ad80acb89b82\"}', NULL, '0', 'admin', '2023-04-15 10:11:28');
INSERT INTO `sys_log` VALUES (275, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"3bffc703-cdef-4507-a68e-db427373284f\"}', NULL, '0', 'admin', '2023-04-15 11:14:28');
INSERT INTO `sys_log` VALUES (276, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"41852cac-9643-4bb8-9a55-659cd39321f1\"}', NULL, '0', 'admin', '2023-04-15 14:15:27');
INSERT INTO `sys_log` VALUES (277, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"31e2576b-1589-43e5-a492-8fa3cc989a6a\"}', NULL, '0', 'admin', '2023-04-15 21:33:48');
INSERT INTO `sys_log` VALUES (278, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"ac9ce0b6-9276-434c-9a71-fc377ac3f917\"}', NULL, '0', 'admin', '2023-04-15 22:34:32');
INSERT INTO `sys_log` VALUES (279, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"930e864d-c4af-4658-9e6f-bdc55aecf0df\"}', NULL, '0', 'admin', '2023-04-15 23:34:57');
INSERT INTO `sys_log` VALUES (280, '登录', 'com.xiaohai.system.controller.LoginController.login', 'POST', 'http://localhost:8089/api/login', '127.0.0.1', 'Windows 10', 'Chrome 11', '[{\"username\":\"admin\",\"password\":\"123456\",\"rememberMe\":false}]', '{\"code\":200,\"msg\":\"登录成功！\",\"data\":\"a77d1d0a-8754-4ac3-b800-0bc4b58b789e\"}', NULL, '0', 'admin', '2023-04-16 08:28:50');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int NULL DEFAULT 0 COMMENT '父菜单ID',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `menu_sort` int NULL DEFAULT NULL COMMENT '显示顺序',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名称',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '路径',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 'el-icon-s-tools', 1, '系统管理', 'M', 'system', '', '', '0', 1, '2023-03-02 11:30:16', 1, '2023-04-04 09:36:38');
INSERT INTO `sys_menu` VALUES (2, 1, 'tree-table', 3, '菜单管理', 'C', 'menu', 'system/menu/index', '', '0', 1, '2023-03-02 13:17:28', 1, '2023-03-05 12:38:36');
INSERT INTO `sys_menu` VALUES (3, 1, 'user', 1, '用户管理', 'C', 'user', 'system/user/index', '', '0', 1, '2023-03-02 15:52:57', 1, '2023-03-06 11:54:17');
INSERT INTO `sys_menu` VALUES (4, 1, 'peoples', 2, '角色管理', 'C', 'role', 'system/role/index', '', '0', 1, '2023-03-02 16:23:39', 1, '2023-03-05 12:38:32');
INSERT INTO `sys_menu` VALUES (5, 1, 'el-icon-s-order', 4, '字典管理', 'C', 'dictType', 'system/dict/type/index', '', '0', 1, '2023-03-02 17:05:03', 1, '2023-03-05 12:38:39');
INSERT INTO `sys_menu` VALUES (6, 0, 'el-icon-cpu', 2, '系统监控', 'M', 'monitor', '', '', '0', 1, '2023-03-02 17:11:55', 1, '2023-04-04 09:36:48');
INSERT INTO `sys_menu` VALUES (7, 6, 'el-icon-stopwatch', 1, '系统服务', 'C', 'server', 'monitor/server/index', '', '0', 1, '2023-03-02 17:15:15', 1, '2023-03-05 12:38:45');
INSERT INTO `sys_menu` VALUES (8, 6, 'el-icon-user', 2, '在线用户', 'C', 'online', 'monitor/online/index', '', '0', 1, '2023-03-02 17:18:26', 1, '2023-03-05 12:42:04');
INSERT INTO `sys_menu` VALUES (9, 1, 'el-icon-s-claim', 5, '字典数据', 'C', 'dictData/:id', 'system/dict/data/index', '', '0', 1, '2023-03-06 10:54:05', 1, '2023-03-06 10:54:05');
INSERT INTO `sys_menu` VALUES (10, 3, '', 1, '新增', 'F', '', '', 'system:user:add', '0', 1, '2023-03-06 11:17:09', 1, '2023-03-06 11:17:09');
INSERT INTO `sys_menu` VALUES (11, 3, '', 2, '更新', 'F', '', '', 'system:user:update', '0', 1, '2023-03-06 11:26:29', 1, '2023-03-06 11:26:29');
INSERT INTO `sys_menu` VALUES (12, 3, '', 3, '删除', 'F', '', '', 'system:user:delete', '0', 1, '2023-03-06 11:31:19', 1, '2023-03-06 11:31:19');
INSERT INTO `sys_menu` VALUES (13, 3, '', 0, '列表', 'F', '', '', 'system:user:list', '0', 1, '2023-03-06 11:54:33', 1, '2023-03-06 11:54:33');
INSERT INTO `sys_menu` VALUES (14, 4, '', 0, '列表', 'F', '', '', 'system:role:list', '0', 1, '2023-03-06 11:56:48', 1, '2023-03-06 11:56:48');
INSERT INTO `sys_menu` VALUES (15, 4, '', 1, '新增', 'F', '', '', 'system:role:add', '0', 1, '2023-03-06 11:58:19', 1, '2023-03-06 11:58:19');
INSERT INTO `sys_menu` VALUES (16, 4, '', 2, '更新', 'F', '', '', 'system:role:update', '0', 1, '2023-03-06 11:58:54', 1, '2023-03-06 11:58:54');
INSERT INTO `sys_menu` VALUES (17, 4, '', 3, '删除', 'F', '', '', 'system:role:delete', '0', 1, '2023-03-06 11:59:49', 1, '2023-03-06 11:59:49');
INSERT INTO `sys_menu` VALUES (18, 2, '', 0, '列表', 'F', '', '', 'system:menu:list', '0', 1, '2023-03-06 13:18:09', 1, '2023-03-06 13:18:28');
INSERT INTO `sys_menu` VALUES (19, 2, '', 1, '新增', 'F', '', '', 'system:menu:add', '0', 1, '2023-03-06 13:18:40', 1, '2023-03-06 13:18:40');
INSERT INTO `sys_menu` VALUES (20, 2, '', 2, '更新', 'F', '', '', 'system:menu:update', '0', 1, '2023-03-06 13:18:58', 1, '2023-03-06 13:18:58');
INSERT INTO `sys_menu` VALUES (21, 2, '', 3, '删除', 'F', '', '', 'system:menu:delete', '0', 1, '2023-03-06 13:19:12', 1, '2023-03-06 13:19:12');
INSERT INTO `sys_menu` VALUES (22, 5, '', 0, '列表', 'F', '', '', 'system:dict:list', '0', 1, '2023-03-06 13:21:30', 1, '2023-03-06 13:21:30');
INSERT INTO `sys_menu` VALUES (23, 5, '', 1, '新增', 'F', '', '', 'system:dict:add', '0', 1, '2023-03-06 13:21:45', 1, '2023-03-06 13:21:45');
INSERT INTO `sys_menu` VALUES (24, 5, '', 3, '删除', 'F', '', '', 'system:dict:delete', '0', 1, '2023-03-06 13:22:00', 1, '2023-03-06 13:22:00');
INSERT INTO `sys_menu` VALUES (25, 5, '', 2, '更新', 'F', '', '', 'system:dict:update', '0', 1, '2023-03-06 13:22:16', 1, '2023-03-06 13:22:16');
INSERT INTO `sys_menu` VALUES (26, 9, '', 0, '列表', 'F', '', '', 'dict:data:list', '0', 1, '2023-03-06 13:22:48', 1, '2023-03-06 13:22:48');
INSERT INTO `sys_menu` VALUES (27, 9, '', 1, '新增', 'F', '', '', 'dict:data:add', '0', 1, '2023-03-06 13:23:02', 1, '2023-03-06 13:23:02');
INSERT INTO `sys_menu` VALUES (28, 9, '', 2, '更新', 'F', '', '', 'dict:data:update', '0', 1, '2023-03-06 13:23:18', 1, '2023-03-06 13:23:18');
INSERT INTO `sys_menu` VALUES (29, 9, '', 3, '删除', 'F', '', '', 'dict:data:delete', '0', 1, '2023-03-06 13:23:39', 1, '2023-03-06 13:23:39');
INSERT INTO `sys_menu` VALUES (30, 5, '', 4, '刷新缓存', 'F', '', '', 'system:dict:clean', '0', 1, '2023-03-06 13:42:13', 1, '2023-03-06 13:42:32');
INSERT INTO `sys_menu` VALUES (31, 1, 'el-icon-s-tools', 2, '系统配置', 'C', 'config', 'system/config/index', '', '0', 1, '2023-03-08 14:15:33', 1, '2023-03-08 14:15:53');
INSERT INTO `sys_menu` VALUES (32, 0, 'el-icon-files', 3, '文件管理', 'M', 'file', '', '', '0', 1, '2023-03-18 11:53:52', 1, '2023-03-31 17:56:06');
INSERT INTO `sys_menu` VALUES (33, 32, 'el-icon-folder-opened', 0, '文件详情', 'C', 'files', 'file/files/index', '', '0', 1, '2023-03-18 11:57:26', 1, '2023-03-18 12:09:05');
INSERT INTO `sys_menu` VALUES (34, 32, 'el-icon-delete', 1, '回收站', 'C', 'delete', 'file/delete/index', '', '0', 1, '2023-03-18 12:09:56', 1, '2023-03-18 12:09:56');
INSERT INTO `sys_menu` VALUES (35, 1, 'el-icon-info', 5, '日志管理', 'C', 'log', 'system/log/index', '', '0', 1, '2023-03-30 15:58:04', 1, '2023-03-30 15:58:49');
INSERT INTO `sys_menu` VALUES (36, 35, '', 0, '列表', 'F', '', '', 'system:log:list', '0', 1, '2023-03-30 16:15:16', 1, '2023-03-30 16:15:16');
INSERT INTO `sys_menu` VALUES (37, 35, '', 1, '删除', 'F', '', '', 'system:log:delete', '0', 1, '2023-03-30 16:15:42', 1, '2023-03-30 16:15:42');
INSERT INTO `sys_menu` VALUES (38, 35, '', 2, '清空', 'F', '', '', 'system:log:clean', '0', 1, '2023-03-30 16:16:51', 1, '2023-03-30 16:17:04');
INSERT INTO `sys_menu` VALUES (39, 31, '', 0, '查看', 'F', '', '', 'system:config:select', '0', 1, '2023-03-31 11:04:08', 1, '2023-03-31 11:04:08');
INSERT INTO `sys_menu` VALUES (40, 31, '', 1, '保存', 'F', '', '', 'system:config:save', '0', 1, '2023-03-31 11:04:58', 1, '2023-03-31 11:04:58');
INSERT INTO `sys_menu` VALUES (41, 0, 'edit', 0, '文章管理', 'M', 'note', '', '', '0', 1, '2023-03-31 17:55:49', 1, '2023-03-31 17:59:32');
INSERT INTO `sys_menu` VALUES (42, 41, 'el-icon-collection-tag', 2, '标签管理', 'C', 'tags', 'note/tags/index', '', '0', 1, '2023-03-31 17:59:09', 1, '2023-04-07 11:00:29');
INSERT INTO `sys_menu` VALUES (43, 42, '', 0, '列表', 'F', '', '', 'note:tags:list', '0', 1, '2023-04-03 13:25:39', 1, '2023-04-03 13:25:39');
INSERT INTO `sys_menu` VALUES (44, 42, '', 1, '新增', 'F', '', '', 'note:tags:add', '0', 1, '2023-04-03 13:25:53', 1, '2023-04-03 13:25:57');
INSERT INTO `sys_menu` VALUES (45, 42, '', 2, '更新', 'F', '', '', 'note:tags:update', '0', 1, '2023-04-03 13:26:15', 1, '2023-04-03 13:26:15');
INSERT INTO `sys_menu` VALUES (46, 42, '', 3, '删除', 'F', '', '', 'note:tags:delete', '0', 1, '2023-04-03 13:26:35', 1, '2023-04-03 13:26:35');
INSERT INTO `sys_menu` VALUES (47, 41, 'el-icon-price-tag', 3, '分类管理', 'C', 'category', 'note/category/index', '', '0', 1, '2023-04-03 13:28:22', 1, '2023-04-07 11:00:36');
INSERT INTO `sys_menu` VALUES (48, 47, '', 0, '列表', 'F', '', '', 'note:category:list', '0', 1, '2023-04-03 13:53:48', 1, '2023-04-03 13:53:48');
INSERT INTO `sys_menu` VALUES (49, 47, '', 1, '新增', 'F', '', '', 'note:category:add', '0', 1, '2023-04-03 13:54:09', 1, '2023-04-03 13:54:09');
INSERT INTO `sys_menu` VALUES (50, 47, '', 2, '更新', 'F', '', '', 'note:category:update', '0', 1, '2023-04-03 13:54:29', 1, '2023-04-03 13:54:29');
INSERT INTO `sys_menu` VALUES (51, 47, '', 3, '删除', 'F', '', '', 'note:category:delete', '0', 1, '2023-04-03 13:55:00', 1, '2023-04-03 13:55:00');
INSERT INTO `sys_menu` VALUES (52, 41, 'el-icon-document', 0, '文章中心', 'C', 'article', 'note/article/index', '', '0', 1, '2023-04-04 14:26:20', 1, '2023-04-07 16:37:27');
INSERT INTO `sys_menu` VALUES (53, 52, '', 0, '列表', 'F', '', '', 'note:article:list', '0', 1, '2023-04-04 14:33:19', 1, '2023-04-04 14:33:19');
INSERT INTO `sys_menu` VALUES (54, 52, '', 1, '新增', 'F', '', '', 'note:article:add', '0', 1, '2023-04-04 14:33:59', 1, '2023-04-04 14:33:59');
INSERT INTO `sys_menu` VALUES (55, 52, '', 2, '更新', 'F', '', '', 'note:article:update', '0', 1, '2023-04-04 14:34:15', 1, '2023-04-04 14:34:15');
INSERT INTO `sys_menu` VALUES (56, 52, '', 3, '删除', 'F', '', '', 'note:article:delete', '0', 1, '2023-04-04 14:34:45', 1, '2023-04-04 14:34:45');
INSERT INTO `sys_menu` VALUES (57, 41, 'el-icon-edit', 1, '写作中心', 'C', 'edit', 'note/edit/index', '', '0', 1, '2023-04-06 17:36:31', 1, '2023-04-07 16:37:23');
INSERT INTO `sys_menu` VALUES (58, 52, '', 4, '是否顶置', 'F', '', '', 'note:article:top', '0', 1, '2023-04-08 21:34:32', 1, '2023-04-08 21:34:32');
INSERT INTO `sys_menu` VALUES (59, 52, '', 5, '是否发布', 'F', '', '', 'note:article:push', '0', 1, '2023-04-08 21:34:51', 1, '2023-04-08 21:34:51');
INSERT INTO `sys_menu` VALUES (65, 8, '', 0, '列表', 'F', '', '', 'monitor:online:list', '0', 1, '2023-04-13 13:22:58', 1, '2023-04-13 13:22:58');
INSERT INTO `sys_menu` VALUES (66, 8, '', 1, '退出', 'F', '', '', 'monitor:online:delete', '0', 1, '2023-04-13 13:23:18', 1, '2023-04-13 13:23:18');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色描述',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '管理员', '可以管理所有东西', '0', 1, '2023-01-29 16:51:22', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role` VALUES (2, 'user', '普通用户', '', '0', 2, '2023-02-03 17:39:35', 1, '2023-03-06 13:53:43');
INSERT INTO `sys_role` VALUES (3, 'test', '测试', '测试', '0', 1, '2023-03-01 15:22:14', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role` VALUES (4, 'test1', 'tset1', '测试1', '0', 1, '2023-03-03 15:16:56', 1, '2023-03-03 16:25:03');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int NOT NULL COMMENT '角色id',
  `menu_id` int NOT NULL COMMENT '菜单权限id',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1291 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (18, 3, 1, 1, '2023-03-03 15:03:48', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role_menu` VALUES (19, 3, 3, 1, '2023-03-03 15:03:48', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role_menu` VALUES (20, 3, 6, 1, '2023-03-03 15:03:48', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role_menu` VALUES (21, 3, 7, 1, '2023-03-03 15:03:48', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role_menu` VALUES (22, 3, 8, 1, '2023-03-03 15:03:48', 1, '2023-03-03 15:03:48');
INSERT INTO `sys_role_menu` VALUES (41, 4, 6, 1, '2023-03-03 16:25:03', 1, '2023-03-03 16:25:03');
INSERT INTO `sys_role_menu` VALUES (42, 4, 7, 1, '2023-03-03 16:25:03', 1, '2023-03-03 16:25:03');
INSERT INTO `sys_role_menu` VALUES (43, 4, 8, 1, '2023-03-03 16:25:03', 1, '2023-03-03 16:25:03');
INSERT INTO `sys_role_menu` VALUES (196, 2, 1, 1, '2023-03-06 13:53:43', 1, '2023-03-06 13:53:43');
INSERT INTO `sys_role_menu` VALUES (197, 2, 3, 1, '2023-03-06 13:53:43', 1, '2023-03-06 13:53:43');
INSERT INTO `sys_role_menu` VALUES (198, 2, 13, 1, '2023-03-06 13:53:43', 1, '2023-03-06 13:53:43');
INSERT INTO `sys_role_menu` VALUES (1230, 1, 41, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1231, 1, 52, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1232, 1, 53, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1233, 1, 54, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1234, 1, 55, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1235, 1, 56, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1236, 1, 58, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1237, 1, 59, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1238, 1, 57, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1239, 1, 42, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1240, 1, 43, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1241, 1, 44, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1242, 1, 45, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1243, 1, 46, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1244, 1, 47, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1245, 1, 48, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1246, 1, 49, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1247, 1, 50, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1248, 1, 51, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1249, 1, 1, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1250, 1, 3, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1251, 1, 13, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1252, 1, 10, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1253, 1, 11, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1254, 1, 12, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1255, 1, 4, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1256, 1, 14, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1257, 1, 15, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1258, 1, 16, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1259, 1, 17, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1260, 1, 31, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1261, 1, 39, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1262, 1, 40, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1263, 1, 2, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1264, 1, 18, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1265, 1, 19, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1266, 1, 20, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1267, 1, 21, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1268, 1, 5, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1269, 1, 22, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1270, 1, 23, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1271, 1, 25, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1272, 1, 24, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1273, 1, 30, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1274, 1, 9, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1275, 1, 26, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1276, 1, 27, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1277, 1, 28, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1278, 1, 29, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1279, 1, 35, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1280, 1, 36, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1281, 1, 37, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1282, 1, 38, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1283, 1, 6, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1284, 1, 7, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1285, 1, 8, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1286, 1, 65, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1287, 1, 66, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1288, 1, 32, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1289, 1, 33, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');
INSERT INTO `sys_role_menu` VALUES (1290, 1, 34, 1, '2023-04-13 13:39:33', 1, '2023-04-13 13:39:33');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '用户性别（ 0女 1男2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `qq_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'qq号',
  `we_chat` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信号',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我介绍',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_count` int UNSIGNED NULL DEFAULT 0 COMMENT '登录次数',
  `login_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录ip',
  `login_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_os` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `login_browser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '4XBeVW8wO9P1tgT0TDRU2w==', '管理员', '1', '/avatar/1/1679651173896.png', '1372195290@qq.com', '15677777777', '1', '1', '1', '0', 33, '127.0.0.1', '内网IP', '2023-04-16 08:28:50', 'Windows 10', 'Chrome 11', 1, '2023-02-02 17:30:22', NULL, '2023-04-16 08:28:50');
INSERT INTO `sys_user` VALUES (2, 'user', '4XBeVW8wO9P1tgT0TDRU2w==', '普通用户', '2', '', '137@qq.com', NULL, NULL, NULL, NULL, '0', 2, '172.16.100.46', '内网IP', '2023-04-13 11:53:41', 'Windows 10', 'Chrome 11', 1, '2023-02-02 17:30:22', NULL, '2023-04-13 11:53:41');
INSERT INTO `sys_user` VALUES (4, 'test', '4XBeVW8wO9P1tgT0TDRU2w==', 'test', '0', NULL, '123@qq.com', NULL, NULL, NULL, NULL, '0', 3, '172.16.100.46', '内网IP', '2023-04-13 11:54:31', 'Windows 10', 'Chrome 11', 3, '2023-03-01 17:23:48', NULL, '2023-04-13 11:54:31');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `role_id` int NOT NULL COMMENT '角色id',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (62, 1, 1, 1, '2023-03-28 10:14:17', 1, '2023-03-28 10:14:17');
INSERT INTO `sys_user_role` VALUES (65, 4, 3, 1, '2023-03-30 15:48:28', 1, '2023-03-30 15:48:28');
INSERT INTO `sys_user_role` VALUES (68, 2, 2, 1, '2023-04-06 16:00:42', 1, '2023-04-06 16:00:42');

SET FOREIGN_KEY_CHECKS = 1;
