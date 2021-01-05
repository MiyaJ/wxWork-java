/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : wxwork

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 24/07/2020 11:21:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_approval_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_apply`;
CREATE TABLE `t_approval_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sp_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批编号',
  `system_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '业务标识',
  `template_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批模板id',
  `sp_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批申请类型(审批模板名称)',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '审批状态 1-审批中；2-已通过；3-已驳回；4-已撤销；6-通过后撤销；7-已删除；10-已支付',
  `use_template_approver` int(11) NOT NULL COMMENT '审批人模式 0指定模式;1默认模式',
  `apply_time` int(11) NULL DEFAULT NULL COMMENT '申请提交时间 审批申请提交时间,Unix时间戳',
  `emp_id` bigint(20) NOT NULL COMMENT '申请人员工id 申请人员工id',
  `wx_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人微信用户id 申请人userid',
  `wx_party_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '申请人wx部门id',
  `apply_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '审批数据',
  `error_reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '申请失败原因',
  `ack` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用方回调结果',
  `callback_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用方回调url',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = ' 审批申请记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_apply_data
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_apply_data`;
CREATE TABLE `t_approval_apply_data`  (
  `id` bigint(12) NOT NULL,
  `sp_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批编号',
  `control` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件类型',
  `control_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件id',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件名称',
  `value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '控件值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批申请数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_sp_notifyer
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_sp_notifyer`;
CREATE TABLE `t_approval_sp_notifyer`  (
  `id` bigint(12) NOT NULL,
  `sp_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批单号',
  `user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业微信用户id',
  `emp_id` bigint(12) NULL DEFAULT NULL COMMENT '员工id',
  `emp_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '员工姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批抄送人信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_sp_record
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_sp_record`;
CREATE TABLE `t_approval_sp_record`  (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `sp_no` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '审批单号',
  `sp_status` int(4) NOT NULL COMMENT '审批节点状态：1-审批中；2-已同意；3-已驳回；4-已转审',
  `approver_attr` int(4) NOT NULL COMMENT '节点审批方式：1-或签；2-会签',
  `step` int(4) NOT NULL COMMENT '当前审批节点：0-第一个审批节点；1-第二个审批节点…以此类推',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批流程信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_sp_record_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_sp_record_detail`;
CREATE TABLE `t_approval_sp_record_detail`  (
  `id` bigint(12) NOT NULL,
  `sp_record_id` bigint(12) NOT NULL COMMENT '审批流程id',
  `approver_user_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分支审批人userid',
  `approver_emp_id` bigint(12) NOT NULL COMMENT '分支审批人员工id',
  `speech` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
  `sp_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '分支审批人审批状态：1-审批中；2-已同意；3-已驳回；4-已转审',
  `sp_time` bigint(20) NULL DEFAULT 0 COMMENT '节点分支审批人审批操作时间，0为尚未操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批节点详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_template
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_template`;
CREATE TABLE `t_approval_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `template_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  `template_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名称',
  `is_deleted` int(2) NOT NULL DEFAULT 0 COMMENT '删除标识 0未删除;1已删除',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板内容',
  `pattern_image` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板样式示例图url',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'json结构, 模板入参, 调用方需要传入的参数及其说明',
  `response_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'json结构',
  `desc` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板描述',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否启用: 1 true 启用; 0 false 未启用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批模板 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_template_control
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_template_control`;
CREATE TABLE `t_approval_template_control`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  `control` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '控件类型：Text-文本；Textarea-多行文本；Number-数字；Money-金额；Date-日期/日期+时间；Selector-单选/多选；Contact-成员/部门；Tips-说明文字；File-附件；Table-明细；Attendance-假勤控件；Vacation-请假控件',
  `control_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件名称',
  `placeholder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '控件说明',
  `require` int(1) NULL DEFAULT NULL COMMENT '是否必填：1-必填；0-非必填',
  `un_print` int(1) NULL DEFAULT NULL COMMENT '是否参与打印：1-不参与打印；0-参与打印',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板控件配置，包含了部分控件类型的附加类型、属性，详见附录说明',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批模板控件信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_approval_template_system
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_template_system`;
CREATE TABLE `t_approval_template_system`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `system_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务标识, 调用方唯一标识',
  `template_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板id',
  `callback_url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用方回调url',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '审批模板与调用系统关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_message_group_chat
-- ----------------------------
DROP TABLE IF EXISTS `t_message_group_chat`;
CREATE TABLE `t_message_group_chat`  (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `chatid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊id',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊名称',
  `owner` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群主',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_by` bigint(12) NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL,
  `update_by` bigint(12) NULL DEFAULT NULL,
  `update_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_message_group_chat_user
-- ----------------------------
DROP TABLE IF EXISTS `t_message_group_chat_user`;
CREATE TABLE `t_message_group_chat_user`  (
  `id` bigint(12) NOT NULL AUTO_INCREMENT,
  `chatid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '群聊id',
  `userid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '成员id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '群聊成员' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_message_log
-- ----------------------------
DROP TABLE IF EXISTS `t_message_log`;
CREATE TABLE `t_message_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `system_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `from_emp_id` bigint(20) NOT NULL COMMENT '发送人员工id',
  `from_wx_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发送人微信id',
  `to_emp_id` bigint(20) NULL DEFAULT NULL COMMENT '接收人员工id',
  `to_wx_user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人企业微信id',
  `to_chat_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收企业微信群id',
  `content` varchar(3072) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息发送记录 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_message_template
-- ----------------------------
DROP TABLE IF EXISTS `t_message_template`;
CREATE TABLE `t_message_template`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板名称',
  `content` varchar(3072) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模板内容',
  `create_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int(4) NULL DEFAULT NULL COMMENT '状态: 启用/未启用',
  `is_deleted` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识: 0未删除; 1 已删除',
  `request_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `response` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '消息模板 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for t_messasge_template_system
-- ----------------------------
DROP TABLE IF EXISTS `t_messasge_template_system`;
CREATE TABLE `t_messasge_template_system`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_code` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用方系统标识',
  `message_template_id` bigint(12) NULL DEFAULT NULL COMMENT '消息模板id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '企微消息模板与调用方系统关系表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
