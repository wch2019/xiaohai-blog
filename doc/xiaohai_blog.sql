/*
 Navicat Premium Data Transfer

 Source Server         : Blog
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 192.168.68.207:3308
 Source Schema         : xiaohai_blog_initial

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 03/07/2024 11:07:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_article
-- ----------------------------
DROP TABLE IF EXISTS `b_article`;
CREATE TABLE `b_article`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NULL DEFAULT NULL COMMENT '用户id',
  `category_id` int NULL DEFAULT NULL COMMENT '分类id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文章标题',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文章简介',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '封面',
  `text` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `is_push` int NULL DEFAULT 0 COMMENT '是否发布(0否，1是)',
  `is_top` int NULL DEFAULT 0 COMMENT '是否顶置(0否，1是)',
  `top_time` datetime NULL DEFAULT NULL COMMENT '顶置时间',
  `is_original` int NULL DEFAULT 0 COMMENT '是否原创 (0原创，1转载)',
  `original_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '转载地址',
  `page_view` int NULL DEFAULT 0 COMMENT '浏览量',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_article
-- ----------------------------

-- ----------------------------
-- Table structure for b_article_like
-- ----------------------------
DROP TABLE IF EXISTS `b_article_like`;
CREATE TABLE `b_article_like`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int NOT NULL COMMENT '文章id',
  `user_id` int NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户文章点赞表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_article_like
-- ----------------------------

-- ----------------------------
-- Table structure for b_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `b_article_tag`;
CREATE TABLE `b_article_tag`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` int NOT NULL COMMENT '文章id',
  `tag_id` int NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文章标签关联' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_article_tag
-- ----------------------------

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_category
-- ----------------------------

-- ----------------------------
-- Table structure for b_comment
-- ----------------------------
DROP TABLE IF EXISTS `b_comment`;
CREATE TABLE `b_comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '父id',
  `article_id` int NOT NULL COMMENT '文章id(0 代表留言)',
  `user_id` int NOT NULL COMMENT '评论人id',
  `reply_user_id` int NULL DEFAULT NULL COMMENT '回复人id',
  `content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '评论内容',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_comment
-- ----------------------------

-- ----------------------------
-- Table structure for b_friend_link
-- ----------------------------
DROP TABLE IF EXISTS `b_friend_link`;
CREATE TABLE `b_friend_link`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站地址',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '站长邮箱',
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '下架原因',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '审核状态（0-待审核,1-通过,2-未通过）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '友情链接表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_friend_link
-- ----------------------------

-- ----------------------------
-- Table structure for b_notifications
-- ----------------------------
DROP TABLE IF EXISTS `b_notifications`;
CREATE TABLE `b_notifications`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知类型',
  `article_id` int NULL DEFAULT NULL COMMENT '文章id',
  `comment_id` int NULL DEFAULT NULL COMMENT '评论id',
  `like_id` int NULL DEFAULT NULL COMMENT '喜欢id',
  `link_id` int NULL DEFAULT NULL COMMENT '友链id',
  `feedback_id` int NULL DEFAULT NULL COMMENT '反馈id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `is_read` int NULL DEFAULT 0 COMMENT '是否已读(0否，1是)',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统通知' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_notifications
-- ----------------------------

-- ----------------------------
-- Table structure for b_tags
-- ----------------------------
DROP TABLE IF EXISTS `b_tags`;
CREATE TABLE `b_tags`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签名称',
  `click` int NULL DEFAULT 0 COMMENT '点击次数',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of b_tags
-- ----------------------------

-- ----------------------------
-- Table structure for file_manager
-- ----------------------------
DROP TABLE IF EXISTS `file_manager`;
CREATE TABLE `file_manager`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '父id目录关联',
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名称',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件地址',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件类型',
  `file_size` int NULL DEFAULT 0 COMMENT '文件大小',
  `file_hash` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件内容哈希',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of file_manager
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站logo',
  `name` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站名称',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `keywords` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '关键字',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '网站描述',
  `record_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ICP备案号',
  `security_record_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '公安备案号',
  `email_message` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '1' COMMENT '消息邮箱通知（0开启，1关闭）',
  `email_host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱地址',
  `email_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱发件人',
  `email_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱授权码',
  `email_port` int NULL DEFAULT NULL COMMENT '邮箱发送端口',
  `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '本地文件地址',
  `disk_size` bigint NOT NULL DEFAULT 0 COMMENT '存储容量',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '关于介绍',
  `initial` int NOT NULL DEFAULT 0 COMMENT '初始化状态（0开启，1关闭）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES (1, '/system/favicon.ico', 'DoteCode', 'DotCode | 点码', 'DoteCode,点码,开源博客,Java技术分享,Spring教程', '一个专注于技术分享的博客平台，大家以共同学习，乐于分享，拥抱开源的价值观进行学习交流', '', NULL, '1', 'smtp.qq.com', '1372195290@qq.com', '', 587, 'C:/Users/wangchenghai/Pictures/files/', 104857600, '# 欢迎来到我的博客！\n这里是一个分享知识、记录生活、思考未来的平台。\n\n在这里，我将和大家分享我对于生活、职场、学习等各个方面的见解和心得体会，希望能够为大家提供有价值的帮助和启发。\n\n同时，我也会在博客中记录我的生活点滴、分享我的兴趣爱好、思考我的未来规划，希望能够通过这种方式与大家建立更加亲近的关系。\n\n感谢您的光临，希望我的博客能够给您带来不一样的体验和收获！\n\n', 0, 1, '2023-02-02 10:34:11', 1, '2024-02-03 16:33:08');

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
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典数据表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `sys_dict_data` VALUES (10, 0, 'sys_check_state', '待审核', '0', '0', 'warning', '', 1, '2023-07-03 16:01:33', 1, '2023-07-03 16:01:33');
INSERT INTO `sys_dict_data` VALUES (11, 1, 'sys_check_state', '已通过', '1', '0', 'success', '', 1, '2023-07-03 16:02:03', 1, '2023-08-03 12:46:36');
INSERT INTO `sys_dict_data` VALUES (12, 2, 'sys_check_state', '未通过', '2', '0', 'danger', '', 1, '2023-07-03 16:02:18', 1, '2023-07-03 16:02:18');
INSERT INTO `sys_dict_data` VALUES (13, 1, 'sys_notification_type', '点赞', '1', '0', 'default', '用户点赞', 1, '2024-03-09 21:26:10', 1, '2024-03-17 07:53:53');
INSERT INTO `sys_dict_data` VALUES (14, 2, 'sys_notification_type', '评论', '2', '0', 'default', '评论相关', 1, '2024-03-09 21:27:17', 1, '2024-03-17 07:54:10');
INSERT INTO `sys_dict_data` VALUES (15, 3, 'sys_notification_type', '系統', '3', '0', 'default', '系统相关、友情链接、用户反馈\n', 1, '2024-03-09 21:29:28', 1, '2024-03-17 07:54:40');

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
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典类型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', '用户性别', 1, '2023-02-25 21:52:09', 1, '2023-02-27 17:22:36');
INSERT INTO `sys_dict_type` VALUES (2, '系统状态', 'sys_normal_disable', '0', '系统状态\n', 1, '2023-02-26 09:07:02', 1, '2023-03-01 15:03:21');
INSERT INTO `sys_dict_type` VALUES (3, '请求方式', 'sys_request_method', '0', '请求方式', 1, '2023-03-30 17:52:00', 1, '2023-03-30 17:59:16');
INSERT INTO `sys_dict_type` VALUES (4, '审核状态', 'sys_check_state', '0', '审核状态', 1, '2023-07-03 16:00:34', 1, '2023-07-03 16:01:08');
INSERT INTO `sys_dict_type` VALUES (5, '通知类型', 'sys_notification_type', '0', '通知类型', 1, '2024-03-09 21:24:16', 1, '2024-03-09 21:24:45');

-- ----------------------------
-- Table structure for sys_feedback
-- ----------------------------
DROP TABLE IF EXISTS `sys_feedback`;
CREATE TABLE `sys_feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '用户id',
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '回复',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0-待审核,1-已通过,2-未通过）',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_feedback
-- ----------------------------

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
  `oper_os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `oper_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `json_result` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '返回参数',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误消息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '状态（0正常 1异常）',
  `created_by` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

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
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 102, 'el-icon-s-tools', 1, '系统管理', 'M', 'system', '', '', '0', 1, '2023-03-02 11:30:16', 1, '2024-06-20 23:22:09');
INSERT INTO `sys_menu` VALUES (2, 1, 'tree-table', 3, '菜单管理', 'C', 'menu', 'system/menu/index', '', '0', 1, '2023-03-02 13:17:28', 1, '2023-03-05 12:38:36');
INSERT INTO `sys_menu` VALUES (3, 1, 'user', 1, '用户管理', 'C', 'user', 'system/user/index', '', '0', 1, '2023-03-02 15:52:57', 1, '2024-06-21 11:35:43');
INSERT INTO `sys_menu` VALUES (4, 1, 'peoples', 2, '角色管理', 'C', 'role', 'system/role/index', '', '0', 1, '2023-03-02 16:23:39', 1, '2023-03-05 12:38:32');
INSERT INTO `sys_menu` VALUES (5, 1, 'el-icon-s-order', 4, '字典管理', 'C', 'dictType', 'system/dict/type/index', '', '0', 1, '2023-03-02 17:05:03', 1, '2023-03-05 12:38:39');
INSERT INTO `sys_menu` VALUES (6, 102, 'el-icon-cpu', 2, '系统监控', 'M', 'monitor', '', '', '0', 1, '2023-03-02 17:11:55', 1, '2024-06-17 22:56:14');
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
INSERT INTO `sys_menu` VALUES (32, 102, 'el-icon-files', 3, '文件管理', 'M', 'file', '', '', '0', 1, '2023-03-18 11:53:52', 1, '2024-06-17 22:56:07');
INSERT INTO `sys_menu` VALUES (33, 32, 'el-icon-folder-opened', 0, '文件详情', 'C', 'files', 'file/files/index', '', '0', 1, '2023-03-18 11:57:26', 1, '2023-03-18 12:09:05');
INSERT INTO `sys_menu` VALUES (34, 32, 'el-icon-delete', 2, '回收站', 'C', 'delete', 'file/delete/index', '', '0', 1, '2023-03-18 12:09:56', 1, '2023-08-31 16:26:56');
INSERT INTO `sys_menu` VALUES (35, 1, 'el-icon-info', 5, '日志管理', 'C', 'log', 'system/log/index', '', '0', 1, '2023-03-30 15:58:04', 1, '2023-03-30 15:58:49');
INSERT INTO `sys_menu` VALUES (36, 35, '', 0, '列表', 'F', '', '', 'system:log:list', '0', 1, '2023-03-30 16:15:16', 1, '2023-03-30 16:15:16');
INSERT INTO `sys_menu` VALUES (37, 35, '', 1, '删除', 'F', '', '', 'system:log:delete', '0', 1, '2023-03-30 16:15:42', 1, '2023-03-30 16:15:42');
INSERT INTO `sys_menu` VALUES (38, 35, '', 2, '清空', 'F', '', '', 'system:log:clean', '0', 1, '2023-03-30 16:16:51', 1, '2023-03-30 16:17:04');
INSERT INTO `sys_menu` VALUES (39, 31, '', 0, '查看', 'F', '', '', 'system:config:select', '0', 1, '2023-03-31 11:04:08', 1, '2023-03-31 11:04:08');
INSERT INTO `sys_menu` VALUES (40, 31, '', 1, '保存', 'F', '', '', 'system:config:save', '0', 1, '2023-03-31 11:04:58', 1, '2023-03-31 11:04:58');
INSERT INTO `sys_menu` VALUES (41, 0, 's-operation', 0, '基础功能', 'M', 'basic', '', '', '0', 1, '2023-03-31 17:55:49', 1, '2024-06-21 11:58:22');
INSERT INTO `sys_menu` VALUES (42, 41, 'el-icon-collection-tag', 4, '标签管理', 'C', 'tags', 'note/tags/index', '', '0', 1, '2023-03-31 17:59:09', 1, '2024-06-21 15:50:10');
INSERT INTO `sys_menu` VALUES (43, 42, '', 0, '列表', 'F', '', '', 'note:tags:list', '0', 1, '2023-04-03 13:25:39', 1, '2023-04-03 13:25:39');
INSERT INTO `sys_menu` VALUES (44, 42, '', 1, '新增', 'F', '', '', 'note:tags:add', '0', 1, '2023-04-03 13:25:53', 1, '2023-04-03 13:25:57');
INSERT INTO `sys_menu` VALUES (45, 42, '', 2, '更新', 'F', '', '', 'note:tags:update', '0', 1, '2023-04-03 13:26:15', 1, '2023-04-03 13:26:15');
INSERT INTO `sys_menu` VALUES (46, 42, '', 3, '删除', 'F', '', '', 'note:tags:delete', '0', 1, '2023-04-03 13:26:35', 1, '2023-04-03 13:26:35');
INSERT INTO `sys_menu` VALUES (47, 41, 'el-icon-price-tag', 3, '分类管理', 'C', 'category', 'note/category/index', '', '0', 1, '2023-04-03 13:28:22', 1, '2024-06-21 15:50:06');
INSERT INTO `sys_menu` VALUES (48, 47, '', 0, '列表', 'F', '', '', 'note:category:list', '0', 1, '2023-04-03 13:53:48', 1, '2023-04-03 13:53:48');
INSERT INTO `sys_menu` VALUES (49, 47, '', 1, '新增', 'F', '', '', 'note:category:add', '0', 1, '2023-04-03 13:54:09', 1, '2023-04-03 13:54:09');
INSERT INTO `sys_menu` VALUES (50, 47, '', 2, '更新', 'F', '', '', 'note:category:update', '0', 1, '2023-04-03 13:54:29', 1, '2023-04-03 13:54:29');
INSERT INTO `sys_menu` VALUES (51, 47, '', 3, '删除', 'F', '', '', 'note:category:delete', '0', 1, '2023-04-03 13:55:00', 1, '2023-04-03 13:55:00');
INSERT INTO `sys_menu` VALUES (52, 41, 'el-icon-document', 0, '文章中心', 'C', 'article', 'note/article/index', '', '0', 1, '2023-04-04 14:26:20', 1, '2024-06-21 14:12:12');
INSERT INTO `sys_menu` VALUES (53, 52, '', 0, '列表', 'F', '', '', 'note:article:list', '0', 1, '2023-04-04 14:33:19', 1, '2023-04-04 14:33:19');
INSERT INTO `sys_menu` VALUES (54, 52, '', 1, '新增', 'F', '', '', 'note:article:add', '0', 1, '2023-04-04 14:33:59', 1, '2023-04-04 14:33:59');
INSERT INTO `sys_menu` VALUES (55, 52, '', 2, '更新', 'F', '', '', 'note:article:update', '0', 1, '2023-04-04 14:34:15', 1, '2023-04-04 14:34:15');
INSERT INTO `sys_menu` VALUES (56, 52, '', 3, '删除', 'F', '', '', 'note:article:delete', '0', 1, '2023-04-04 14:34:45', 1, '2023-04-04 14:34:45');
INSERT INTO `sys_menu` VALUES (57, 41, 'el-icon-edit', 1, '写作中心', 'C', 'edit', 'note/edit/index', '', '0', 1, '2023-04-06 17:36:31', 1, '2024-06-21 15:49:55');
INSERT INTO `sys_menu` VALUES (58, 52, '', 4, '是否顶置', 'F', '', '', 'note:article:top', '0', 1, '2023-04-08 21:34:32', 1, '2023-04-08 21:34:32');
INSERT INTO `sys_menu` VALUES (59, 52, '', 5, '是否发布', 'F', '', '', 'note:article:push', '0', 1, '2023-04-08 21:34:51', 1, '2023-04-08 21:34:51');
INSERT INTO `sys_menu` VALUES (65, 8, '', 0, '列表', 'F', '', '', 'monitor:online:list', '0', 1, '2023-04-13 13:22:58', 1, '2023-04-13 13:22:58');
INSERT INTO `sys_menu` VALUES (66, 8, '', 1, '退出', 'F', '', '', 'monitor:online:delete', '0', 1, '2023-04-13 13:23:18', 1, '2023-04-13 13:23:18');
INSERT INTO `sys_menu` VALUES (67, 41, 'el-icon-message', 5, '留言管理', 'C', 'comment', 'note/comment/index', '', '0', 1, '2023-05-25 21:03:38', 1, '2024-06-21 15:50:15');
INSERT INTO `sys_menu` VALUES (68, 67, '', 0, '列表', 'F', '', '', 'note:comment:list', '0', 1, '2023-05-28 08:37:42', 1, '2023-05-28 08:38:16');
INSERT INTO `sys_menu` VALUES (69, 67, '', 1, '新增', 'F', '', '', 'note:comment:add', '0', 1, '2023-05-28 08:38:32', 1, '2023-07-03 15:52:03');
INSERT INTO `sys_menu` VALUES (70, 67, '', 2, '删除', 'F', '', '', 'note:comment:delete', '0', 1, '2023-05-28 08:39:01', 1, '2023-05-28 08:39:01');
INSERT INTO `sys_menu` VALUES (71, 41, 'like', 2, '喜欢文章', 'C', 'like', 'note/like/index', '', '0', 1, '2023-07-03 10:39:29', 1, '2024-06-21 15:50:00');
INSERT INTO `sys_menu` VALUES (72, 71, '', 0, '列表', 'F', '', '', 'note:like:list', '0', 1, '2023-07-03 10:40:49', 1, '2023-07-03 13:28:03');
INSERT INTO `sys_menu` VALUES (73, 71, '', 2, '删除', 'F', '', '', 'note:like:delete', '0', 1, '2023-07-03 10:41:10', 1, '2023-07-03 13:28:12');
INSERT INTO `sys_menu` VALUES (74, 71, '', 1, '新增', 'F', '', '', 'note:like:add', '0', 1, '2023-07-03 10:42:47', 1, '2023-07-03 13:28:08');
INSERT INTO `sys_menu` VALUES (75, 41, 'el-icon-link', 6, '友情链接', 'C', 'link', 'note/link/index', '', '0', 1, '2023-07-03 15:50:05', 1, '2024-06-21 15:50:20');
INSERT INTO `sys_menu` VALUES (76, 75, '', 0, '列表', 'F', '', '', 'note:link:list', '0', 1, '2023-07-03 15:51:46', 1, '2023-07-03 15:51:46');
INSERT INTO `sys_menu` VALUES (77, 75, '', 1, '新增', 'F', '', '', 'note:link:add', '0', 1, '2023-07-03 15:52:40', 1, '2023-07-03 15:52:40');
INSERT INTO `sys_menu` VALUES (78, 75, '', 2, '更新', 'F', '', '', 'note:link:update', '0', 1, '2023-07-03 15:53:16', 1, '2023-07-03 15:53:16');
INSERT INTO `sys_menu` VALUES (79, 75, '', 3, '删除', 'F', '', '', 'note:link:delete', '0', 1, '2023-07-03 15:53:45', 1, '2023-07-03 15:53:45');
INSERT INTO `sys_menu` VALUES (80, 102, 'el-icon-help', 4, '接口文档', 'M', 'api', '', '', '0', 1, '2023-07-06 11:47:52', 1, '2024-06-22 10:53:26');
INSERT INTO `sys_menu` VALUES (81, 80, 'el-icon-document', 0, 'Knife4j', 'C', 'knife4j', 'api/knife4j/index', '', '0', 1, '2023-07-06 11:49:12', 1, '2023-07-06 11:49:12');
INSERT INTO `sys_menu` VALUES (82, 80, 'el-icon-document', 1, 'Swagger', 'C', 'swagger', 'api/swagger/index', '', '0', 1, '2023-07-06 13:17:41', 1, '2023-07-06 13:17:41');
INSERT INTO `sys_menu` VALUES (83, 106, '', 1, '导入', 'F', '', '', 'note:article:import', '0', 1, '2023-07-08 13:47:22', 1, '2024-07-02 21:51:49');
INSERT INTO `sys_menu` VALUES (84, 1, 'bug', 6, '用户反馈', 'C', 'feedback', 'system/feedback/index', '', '0', 1, '2023-08-03 11:25:08', 1, '2023-08-03 16:58:08');
INSERT INTO `sys_menu` VALUES (85, 84, '', 0, '列表', 'F', '', '', 'system:feedback:list', '0', 1, '2023-08-03 13:36:54', 1, '2023-08-03 13:36:54');
INSERT INTO `sys_menu` VALUES (86, 84, '', 1, '新增', 'F', '', '', 'system:feedback:add', '0', 1, '2023-08-03 13:37:24', 1, '2023-08-03 13:37:28');
INSERT INTO `sys_menu` VALUES (87, 84, '', 2, '更新', 'F', '', '', 'system:feedback:update', '0', 1, '2023-08-03 13:39:30', 1, '2023-08-03 13:39:30');
INSERT INTO `sys_menu` VALUES (88, 84, '', 3, '删除', 'F', '', '', 'system:feedback:delete', '0', 1, '2023-08-03 13:39:52', 1, '2023-08-03 13:39:52');
INSERT INTO `sys_menu` VALUES (89, 32, 'el-icon-picture-outline', 1, '文章图片', 'C', 'image', 'file/image/index', '', '0', 1, '2023-08-31 16:19:24', 1, '2023-08-31 16:20:25');
INSERT INTO `sys_menu` VALUES (90, 52, '', 7, '导出', 'F', '', '', 'note:article:download', '0', 1, '2023-12-05 13:38:45', 1, '2023-12-05 13:38:53');
INSERT INTO `sys_menu` VALUES (91, 89, '', 0, '列表', 'F', '', '', 'file:image:list', '0', 1, '2024-01-23 11:35:51', 1, '2024-01-23 11:35:51');
INSERT INTO `sys_menu` VALUES (92, 89, '', 1, '上传图片', 'F', '', '', 'file:image:upload', '0', 1, '2024-01-23 11:38:08', 1, '2024-01-23 11:41:34');
INSERT INTO `sys_menu` VALUES (93, 89, '', 2, '重命名', 'F', '', '', 'file:image:update', '0', 1, '2024-01-23 11:38:31', 1, '2024-01-23 11:38:31');
INSERT INTO `sys_menu` VALUES (94, 89, '', 3, '删除', 'F', '', '', 'file:image:delete', '0', 1, '2024-01-23 11:38:48', 1, '2024-01-23 11:38:48');
INSERT INTO `sys_menu` VALUES (95, 33, '', 0, '列表', 'F', '', '', 'file:files:list', '0', 1, '2024-01-26 11:09:52', 1, '2024-01-26 11:09:52');
INSERT INTO `sys_menu` VALUES (96, 33, '', 1, '新建文件夹', 'F', '', '', 'file:files:folder', '0', 1, '2024-01-26 11:10:35', 1, '2024-01-26 11:12:04');
INSERT INTO `sys_menu` VALUES (97, 33, '', 2, '上传文件', 'F', '', '', 'file:files:file', '0', 1, '2024-01-26 11:11:15', 1, '2024-01-26 11:11:15');
INSERT INTO `sys_menu` VALUES (98, 33, '', 3, '重命名', 'F', '', '', 'file:files:update', '0', 1, '2024-01-26 11:11:38', 1, '2024-01-26 11:11:45');
INSERT INTO `sys_menu` VALUES (99, 33, '', 4, '删除', 'F', '', '', 'file:files:delete', '0', 1, '2024-01-26 11:12:53', 1, '2024-01-26 11:12:53');
INSERT INTO `sys_menu` VALUES (100, 52, '', 7, '抓取', 'F', '', '', 'note:article:reptile', '0', 1, '2024-03-01 10:11:47', 1, '2024-03-01 10:11:47');
INSERT INTO `sys_menu` VALUES (102, 0, 's-operation', 1, '更多功能', 'M', 'more', '', '', '0', 1, '2024-06-17 22:51:31', 1, '2024-06-17 23:06:14');
INSERT INTO `sys_menu` VALUES (106, 1, 'el-icon-coin', 7, '小工具', 'C', 'tool', 'system/tool/index', '', '0', 1, '2024-07-02 12:02:39', 1, '2024-07-02 13:37:20');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'admin', '管理员', '可以管理所有东西', '0', 1, '2023-01-29 16:51:22', 1, '2024-01-26 13:59:20');
INSERT INTO `sys_role` VALUES (2, 'user', '普通用户', '', '0', 2, '2023-02-03 17:39:35', 1, '2024-01-26 11:03:47');

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
) ENGINE = InnoDB AUTO_INCREMENT = 5107 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色权限关联表' ROW_FORMAT = DYNAMIC;

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
INSERT INTO `sys_role_menu` VALUES (3487, 5, 32, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3488, 5, 41, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3489, 5, 52, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3490, 5, 53, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3491, 5, 54, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3492, 5, 58, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3493, 5, 59, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3494, 5, 83, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3495, 5, 100, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3496, 5, 57, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3497, 5, 71, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3498, 5, 72, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3499, 5, 74, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3500, 5, 73, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3501, 5, 47, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3502, 5, 48, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3503, 5, 49, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3504, 5, 51, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3505, 5, 42, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3506, 5, 43, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3507, 5, 44, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3508, 5, 46, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3509, 5, 67, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3510, 5, 68, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3511, 5, 69, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3512, 5, 75, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3513, 5, 76, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3514, 5, 77, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3515, 5, 1, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3516, 5, 3, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3517, 5, 13, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3518, 5, 4, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3519, 5, 14, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3520, 5, 31, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3521, 5, 39, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3522, 5, 2, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3523, 5, 18, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3524, 5, 5, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3525, 5, 22, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3526, 5, 30, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3527, 5, 9, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3528, 5, 26, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3529, 5, 35, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3530, 5, 36, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3531, 5, 84, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3532, 5, 85, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3533, 5, 86, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3534, 5, 6, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3535, 5, 7, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3536, 5, 8, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3537, 5, 65, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3538, 5, 33, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3539, 5, 95, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3540, 5, 96, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3541, 5, 97, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3542, 5, 98, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3543, 5, 99, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3544, 5, 89, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3545, 5, 91, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3546, 5, 92, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3547, 5, 93, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3548, 5, 94, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3549, 5, 80, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3550, 5, 81, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (3551, 5, 82, 1, '2024-06-01 23:13:25', 1, '2024-06-01 23:13:25');
INSERT INTO `sys_role_menu` VALUES (4961, 1, 41, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4962, 1, 52, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4963, 1, 53, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4964, 1, 54, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4965, 1, 55, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4966, 1, 56, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4967, 1, 58, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4968, 1, 59, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4969, 1, 100, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4970, 1, 90, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4971, 1, 57, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4972, 1, 71, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4973, 1, 72, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4974, 1, 74, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4975, 1, 73, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4976, 1, 47, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4977, 1, 48, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4978, 1, 49, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4979, 1, 50, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4980, 1, 51, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4981, 1, 42, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4982, 1, 43, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4983, 1, 44, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4984, 1, 45, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4985, 1, 46, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4986, 1, 67, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4987, 1, 68, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4988, 1, 69, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4989, 1, 70, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4990, 1, 75, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4991, 1, 76, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4992, 1, 77, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4993, 1, 78, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4994, 1, 79, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4995, 1, 102, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4996, 1, 1, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4997, 1, 3, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4998, 1, 13, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (4999, 1, 10, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5000, 1, 11, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5001, 1, 12, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5002, 1, 4, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5003, 1, 14, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5004, 1, 15, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5005, 1, 16, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5006, 1, 17, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5007, 1, 31, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5008, 1, 39, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5009, 1, 40, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5010, 1, 2, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5011, 1, 18, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5012, 1, 19, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5013, 1, 20, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5014, 1, 21, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5015, 1, 5, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5016, 1, 22, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5017, 1, 23, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5018, 1, 25, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5019, 1, 24, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5020, 1, 30, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5021, 1, 35, 1, '2024-07-02 21:53:45', 1, '2024-07-02 21:53:45');
INSERT INTO `sys_role_menu` VALUES (5022, 1, 36, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5023, 1, 37, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5024, 1, 38, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5025, 1, 9, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5026, 1, 26, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5027, 1, 27, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5028, 1, 28, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5029, 1, 29, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5030, 1, 84, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5031, 1, 85, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5032, 1, 86, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5033, 1, 87, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5034, 1, 88, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5035, 1, 106, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5036, 1, 83, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5037, 1, 6, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5038, 1, 7, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5039, 1, 8, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5040, 1, 65, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5041, 1, 66, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5042, 1, 32, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5043, 1, 33, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5044, 1, 95, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5045, 1, 96, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5046, 1, 97, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5047, 1, 98, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5048, 1, 99, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5049, 1, 89, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5050, 1, 91, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5051, 1, 92, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5052, 1, 93, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5053, 1, 94, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5054, 1, 34, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5055, 1, 80, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5056, 1, 81, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5057, 1, 82, 1, '2024-07-02 21:53:46', 1, '2024-07-02 21:53:46');
INSERT INTO `sys_role_menu` VALUES (5058, 2, 102, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5059, 2, 1, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5060, 2, 41, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5061, 2, 52, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5062, 2, 53, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5063, 2, 54, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5064, 2, 55, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5065, 2, 56, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5066, 2, 58, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5067, 2, 59, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5068, 2, 100, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5069, 2, 90, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5070, 2, 57, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5071, 2, 71, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5072, 2, 72, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5073, 2, 74, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5074, 2, 73, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5075, 2, 47, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5076, 2, 48, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5077, 2, 49, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5078, 2, 42, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5079, 2, 43, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5080, 2, 44, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5081, 2, 67, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5082, 2, 68, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5083, 2, 69, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5084, 2, 70, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5085, 2, 75, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5086, 2, 76, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5087, 2, 77, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5088, 2, 78, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5089, 2, 79, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5090, 2, 84, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5091, 2, 85, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5092, 2, 86, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5093, 2, 106, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5094, 2, 83, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5095, 2, 32, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5096, 2, 33, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5097, 2, 95, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5098, 2, 96, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5099, 2, 97, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5100, 2, 98, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5101, 2, 99, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5102, 2, 89, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5103, 2, 91, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5104, 2, 92, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5105, 2, 93, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');
INSERT INTO `sys_role_menu` VALUES (5106, 2, 94, 1, '2024-07-02 21:54:19', 1, '2024-07-02 21:54:19');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `gender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '2' COMMENT '用户性别（ 0女 1男2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像地址',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `gitee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'gitee地址',
  `github` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'github地址',
  `qq_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'qq号',
  `we_chat` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信号',
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '自我介绍',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_count` int UNSIGNED NULL DEFAULT 0 COMMENT '登录次数',
  `login_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '最后登录ip',
  `login_source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'ip来源',
  `login_date` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `login_os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `login_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '浏览器',
  `disk_size` bigint NOT NULL DEFAULT 0 COMMENT '存储容量',
  `created_by` int NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '4XBeVW8wO9P1tgT0TDRU2w==', '小海', '1', '/files/1/avatar/da7626c4538341078e7f7db16779afc8.png', '1372195290@qq.com', '15677777777', 'https://gitee.com/wch2019', 'https://github.com/wch2019', '1372195290', '1', '用一点点代码，改变生活', '0', 536, '127.0.0.1', '内网IP', '2024-02-03 16:29:35', 'Windows 10', 'Chrome 12', 104857600, 1, '2023-02-02 17:30:22', 1, '2024-02-03 16:32:45');
INSERT INTO `sys_user` VALUES (2, 'user', '4XBeVW8wO9P1tgT0TDRU2w==', '普通用户', '2', '/files/2/avatar/a760e7d77d5a415ba2e876081115d9dd.png', '137@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, '0', 20, '127.0.0.1', '内网IP', '2024-01-26 11:03:59', 'Windows 10', 'Chrome 12', 104857600, 1, '2023-02-02 17:30:22', 1, '2024-02-03 16:32:52');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1, 1, '2024-02-03 16:32:45', 1, '2024-02-03 16:32:45');
INSERT INTO `sys_user_role` VALUES (2, 2, 2, 1, '2024-02-03 16:32:52', 1, '2024-02-03 16:32:52');

SET FOREIGN_KEY_CHECKS = 1;
