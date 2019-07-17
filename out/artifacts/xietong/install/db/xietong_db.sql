# SQL Manager 2005 for MySQL 3.7.5.1
# ---------------------------------------
# Host     : 192.168.1.106
# Port     : 3306
# Database : xietong_db


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES gbk */;


DROP DATABASE IF EXISTS `xietong_db`;

SET FOREIGN_KEY_CHECKS=0;

CREATE DATABASE `xietong_db`
    CHARACTER SET 'utf8'
    COLLATE 'utf8_general_ci';

USE `xietong_db`;

#
# Structure for the `t_systemtip` table : 
#

CREATE TABLE `t_systemtip` (
  `id` int(11) NOT NULL auto_increment,
  `tipName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

#
# Structure for the `t_department` table : 
#

CREATE TABLE `t_department` (
  `id` int(11) NOT NULL auto_increment,
  `departmentDesc` varchar(255) default NULL,
  `departmentName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

#
# Structure for the `t_user` table : 
#

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL auto_increment,
  `birthday` datetime default NULL,
  `gender` varchar(255) default NULL,
  `password` varchar(255) default NULL,
  `realName` varchar(255) default NULL,
  `school` varchar(255) default NULL,
  `specialty` varchar(255) default NULL,
  `userName` varchar(255) default NULL,
  `department_id` int(11) default NULL,
  `superiors_id` int(11) default NULL,
  `disabled` bit(1) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `userName` (`userName`),
  KEY `FKCB63CCB6CB141CBC` (`department_id`),
  KEY `FKCB63CCB64A0FD31E` (`superiors_id`),
  CONSTRAINT `FKCB63CCB64A0FD31E` FOREIGN KEY (`superiors_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FKCB63CCB6CB141CBC` FOREIGN KEY (`department_id`) REFERENCES `t_department` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

#
# Structure for the `t_dakalog` table : 
#

CREATE TABLE `t_dakalog` (
  `id` int(11) NOT NULL auto_increment,
  `users_id` int(11) default NULL,
  `date1` datetime default NULL,
  `date2` datetime default NULL,
  `date3` datetime default NULL,
  `date4` datetime default NULL,
  `tip1_id` int(11) default NULL,
  `tip2_id` int(11) default NULL,
  `tip3_id` int(11) default NULL,
  `tip4_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK1118BB06A12CEF38` (`users_id`),
  KEY `FK1118BB06374C6A0B` (`tip3_id`),
  KEY `FK1118BB06374BF5AC` (`tip2_id`),
  KEY `FK1118BB06374B814D` (`tip1_id`),
  KEY `FK1118BB06374CDE6A` (`tip4_id`),
  CONSTRAINT `FK1118BB06374B814D` FOREIGN KEY (`tip1_id`) REFERENCES `t_systemtip` (`id`),
  CONSTRAINT `FK1118BB06374BF5AC` FOREIGN KEY (`tip2_id`) REFERENCES `t_systemtip` (`id`),
  CONSTRAINT `FK1118BB06374C6A0B` FOREIGN KEY (`tip3_id`) REFERENCES `t_systemtip` (`id`),
  CONSTRAINT `FK1118BB06374CDE6A` FOREIGN KEY (`tip4_id`) REFERENCES `t_systemtip` (`id`),
  CONSTRAINT `FK1118BB06A12CEF38` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;

#
# Structure for the `t_holiday` table : 
#

CREATE TABLE `t_holiday` (
  `id` int(11) NOT NULL auto_increment,
  `holidayName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for the `t_holidaylog` table : 
#

CREATE TABLE `t_holidaylog` (
  `id` int(11) NOT NULL auto_increment,
  `endTime` datetime default NULL,
  `reason` varchar(255) default NULL,
  `startTime` datetime default NULL,
  `holiday_id` int(11) default NULL,
  `users_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK7A80C097A12CEF38` (`users_id`),
  KEY `FK7A80C0977C5665B8` (`holiday_id`),
  CONSTRAINT `FK7A80C0977C5665B8` FOREIGN KEY (`holiday_id`) REFERENCES `t_holiday` (`id`),
  CONSTRAINT `FK7A80C097A12CEF38` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

#
# Structure for the `t_overtime` table : 
#

CREATE TABLE `t_overtime` (
  `id` int(11) NOT NULL auto_increment,
  `endTime` datetime default NULL,
  `reason` varchar(255) default NULL,
  `startTime` datetime default NULL,
  `user_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKD644790CA83C1695` (`user_id`),
  CONSTRAINT `FKD644790CA83C1695` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

#
# Structure for the `t_project` table : 
#

CREATE TABLE `t_project` (
  `id` int(11) NOT NULL auto_increment,
  `projectdescription` varchar(255) default NULL,
  `projectname` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

#
# Structure for the `t_resource` table : 
#

CREATE TABLE `t_resource` (
  `id` int(11) NOT NULL auto_increment,
  `type` varchar(255) default NULL,
  `value` varchar(255) default NULL,
  `description` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

#
# Structure for the `t_role` table : 
#

CREATE TABLE `t_role` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

#
# Structure for the `t_role_resource` table : 
#

CREATE TABLE `t_role_resource` (
  `role_id` int(11) NOT NULL,
  `resource_id` int(11) NOT NULL,
  PRIMARY KEY  (`role_id`,`resource_id`),
  KEY `FK379F912C811033BC` (`role_id`),
  KEY `FK379F912C7AE1BBBC` (`resource_id`),
  CONSTRAINT `FK379F912C7AE1BBBC` FOREIGN KEY (`resource_id`) REFERENCES `t_resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK379F912C811033BC` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `t_statistic` table : 
#

CREATE TABLE `t_statistic` (
  `id` int(11) NOT NULL auto_increment,
  `businessTime` int(11) default NULL,
  `countOfEO` int(11) default NULL,
  `countOfLate` int(11) default NULL,
  `month` int(11) default NULL,
  `overTime` int(11) default NULL,
  `sickTime` int(11) default NULL,
  `vacationTime` int(11) default NULL,
  `workTime` int(11) default NULL,
  `year` int(11) default NULL,
  `users_id` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FKA2D47345A12CEF38` (`users_id`),
  CONSTRAINT `FKA2D47345A12CEF38` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

#
# Structure for the `t_task` table : 
#

CREATE TABLE `t_task` (
  `id` int(11) NOT NULL auto_increment,
  `priority` varchar(255) default NULL,
  `taskdescription` text,
  `taskname` varchar(255) default NULL,
  `taskstate` varchar(255) default NULL,
  `tasktype` varchar(255) default NULL,
  `project_id` int(11) NOT NULL,
  `users_id` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `FKCB631670A12CEF38` (`users_id`),
  KEY `FKCB631670F642E218` (`project_id`),
  CONSTRAINT `FKCB631670A12CEF38` FOREIGN KEY (`users_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKCB631670F642E218` FOREIGN KEY (`project_id`) REFERENCES `t_project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8;

#
# Structure for the `t_tasklog` table : 
#

CREATE TABLE `t_tasklog` (
  `id` int(11) NOT NULL auto_increment,
  `action` varchar(255) default NULL,
  `date` datetime default NULL,
  `executor_id` int(11) default NULL,
  `operator_id` int(11) default NULL,
  `task_id` int(11) default NULL,
  `result` varchar(255) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK5FF1B6D4D35EE55C` (`task_id`),
  KEY `FK5FF1B6D496398C8D` (`executor_id`),
  KEY `FK5FF1B6D4B911FF1C` (`operator_id`),
  CONSTRAINT `FK5FF1B6D496398C8D` FOREIGN KEY (`executor_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK5FF1B6D4B911FF1C` FOREIGN KEY (`operator_id`) REFERENCES `t_user` (`id`),
  CONSTRAINT `FK5FF1B6D4D35EE55C` FOREIGN KEY (`task_id`) REFERENCES `t_task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

#
# Structure for the `t_user_role` table : 
#

CREATE TABLE `t_user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY  (`user_id`,`role_id`),
  KEY `FK331DEE5FA83C1695` (`user_id`),
  KEY `FK331DEE5F811033BC` (`role_id`),
  CONSTRAINT `FK331DEE5F811033BC` FOREIGN KEY (`role_id`) REFERENCES `t_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK331DEE5FA83C1695` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Structure for the `t_worktime` table : 
#

CREATE TABLE `t_worktime` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(255) default NULL,
  `time` time default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;




# 数据 

INSERT INTO `t_systemtip` (`id`, `tipName`) VALUES 
  (1,'正常上班'),
  (2,'一级迟到'),
  (3,'二级迟到'),
  (4,'三级迟到'),
  (5,'早退'),
  (6,'正常下班'),
  (7,'特殊迟到'),
  (8,'事假'),
  (9,'病假'),
  (10,'休假'),
  (11,'加班');

#COMMIT;

#
# Data for the `t_department` table  (LIMIT 0,500)
#

INSERT INTO `t_department` (`id`, `departmentDesc`, `departmentName`) VALUES 
  (2,'销售工作&nbsp;','销售部'),
  (3,'<P>汇高网讯科技（北京）有限公司研发部</P>','研发部');

#COMMIT;

#
# Data for the `t_user` table  (LIMIT 0,500)
#

INSERT INTO `t_user` (`id`, `birthday`, `gender`, `password`, `realName`, `school`, `specialty`, `userName`, `department_id`, `superiors_id`, `disabled`) VALUES 
  (4,NULL,'男','67c762276bced09ee4df0ed537d164ea','dfg','c','c','c',2,NULL,False),
  (17,NULL,'男','e10adc3949ba59abbe56e057f20f883e','asd','','','asd',3,21,False),
  (18,NULL,'女','e10adc3949ba59abbe56e057f20f883e','zx','','','zx',3,21,False),
  (19,NULL,'女','e10adc3949ba59abbe56e057f20f883e','qwe','','','qwe',3,21,False),
  (20,NULL,'男','e10adc3949ba59abbe56e057f20f883e','safd','','','safd',3,21,False),
  (21,NULL,'男','21232f297a57a5a743894a0e4a801fc3','admin','','','admin',3,4,False),
  (22,'2009-06-30','女','827ccb0eea8a706c4c34a16891f84e7b','d','d','d','d',2,18,False);

#COMMIT;

#
# Data for the `t_dakalog` table  (LIMIT 0,500)
#

INSERT INTO `t_dakalog` (`id`, `users_id`, `date1`, `date2`, `date3`, `date4`, `tip1_id`, `tip2_id`, `tip3_id`, `tip4_id`) VALUES 
  (114,20,'2009-08-03 09:00:00','2009-08-03 11:50:00','2009-08-03 13:30:00','2009-08-03 18:42:42',1,6,1,6),
  (115,21,'2009-08-03 09:00:00','2009-08-03 11:50:00','2009-08-03 13:30:00','2009-08-03 19:00:00',1,6,1,6),
  (116,17,'2009-08-03 09:00:00','2009-08-03 11:50:00','2009-08-03 13:30:00','2009-08-03 19:36:07',1,6,1,6),
  (117,19,'2009-08-04 08:50:01','2009-08-04 11:50:00','2009-08-04 13:30:00','2009-08-04 19:15:00',1,6,1,6),
  (118,20,'2009-08-04 08:50:16','2009-08-04 11:50:00','2009-08-04 13:30:00','2009-08-04 19:14:11',1,6,1,6),
  (119,18,'2009-08-04 08:51:09','2009-08-04 11:50:00','2009-08-04 13:30:00','2009-08-04 19:16:02',1,6,1,6),
  (120,17,'2009-08-04 08:54:45','2009-08-04 11:50:00','2009-08-04 13:30:00','2009-08-04 19:03:00',1,6,1,6),
  (121,21,'2009-08-04 08:54:49','2009-08-04 11:50:00','2009-08-04 13:30:00','2009-08-04 18:59:27',1,6,1,6),
  (122,20,'2009-08-05 08:39:52','2009-08-05 11:50:00','2009-08-05 13:30:00','2009-08-05 19:06:15',1,6,1,6),
  (123,18,'2009-08-05 08:43:37','2009-08-05 11:50:00','2009-08-05 13:30:00','2009-08-05 19:08:15',1,6,1,6),
  (124,19,'2009-08-05 08:45:31','2009-08-05 11:50:00','2009-08-05 13:30:00','2009-08-05 19:10:31',1,6,1,6),
  (125,17,'2009-08-05 08:50:59','2009-08-05 11:50:00','2009-08-05 13:30:00','2009-08-05 19:06:22',1,6,1,6),
  (126,21,'2009-08-05 08:55:25','2009-08-05 11:50:00','2009-08-05 13:30:00','2009-08-05 18:51:11',1,6,1,6),
  (127,20,'2009-08-06 08:42:38','2009-08-06 11:50:00','2009-08-06 13:30:00','2009-08-06 19:21:02',1,6,1,6),
  (128,19,'2009-08-06 08:46:05','2009-08-06 11:50:00','2009-08-06 13:30:00','2009-08-06 19:16:13',1,6,1,6),
  (129,18,'2009-08-06 08:49:10','2009-08-06 11:50:00','2009-08-06 13:30:00','2009-08-06 19:08:03',1,6,1,6),
  (130,17,'2009-08-06 08:53:45','2009-08-06 11:50:00','2009-08-06 13:30:00','2009-08-06 19:23:38',1,6,1,6),
  (131,21,'2009-08-06 09:03:48','2009-08-06 11:50:00','2009-08-06 13:30:00','2009-08-06 18:48:37',1,6,1,6),
  (132,20,'2009-08-07 08:41:22','2009-08-07 11:50:00','2009-08-07 13:30:00','2009-08-07 19:10:34',1,6,1,6),
  (133,17,'2009-08-07 08:43:46','2009-08-07 11:50:00','2009-08-07 13:30:00','2009-08-07 19:06:38',1,6,1,6),
  (134,21,'2009-08-07 08:45:39','2009-08-07 11:50:00','2009-08-07 13:30:00','2009-08-07 19:49:24',1,6,1,6),
  (135,19,'2009-08-07 08:49:24','2009-08-07 11:50:00','2009-08-07 13:30:00','2009-08-07 19:04:20',1,6,1,6),
  (136,18,'2009-08-07 08:50:04','2009-08-07 11:50:00','2009-08-07 13:30:00','2009-08-07 18:43:12',1,6,1,6),
  (137,18,'2009-08-10 08:54:35','2009-08-10 11:50:00','2009-08-10 13:30:00','2009-08-10 18:35:51',1,6,1,6),
  (138,19,'2009-08-10 08:55:32','2009-08-10 11:50:00','2009-08-10 13:30:00','2009-08-10 18:38:57',1,6,1,6),
  (139,20,'2009-08-10 08:55:59','2009-08-10 11:50:00','2009-08-10 13:30:00','2009-08-10 19:00:55',1,6,1,6),
  (140,21,'2009-08-10 09:05:31','2009-08-10 11:50:00','2009-08-10 13:30:00','2009-08-10 19:00:52',1,6,1,6),
  (141,17,'2009-08-10 09:07:06','2009-08-10 11:50:00','2009-08-10 13:30:00','2009-08-10 18:19:19',2,6,1,6),
  (142,20,'2009-08-11 08:40:36','2009-08-11 11:50:00','2009-08-11 13:30:00','2009-08-11 19:18:29',1,6,1,6),
  (143,17,'2009-08-11 08:44:16','2009-08-11 11:50:00','2009-08-11 13:30:00','2009-08-11 18:10:00',1,6,1,6),
  (144,19,'2009-08-11 08:45:20','2009-08-11 11:50:00','2009-08-11 13:30:00','2009-08-11 18:11:12',1,6,1,6),
  (145,18,'2009-08-11 08:59:40','2009-08-11 11:50:00','2009-08-11 13:30:00','2009-08-11 18:55:32',1,6,1,6),
  (146,21,'2009-08-11 09:04:43','2009-08-11 11:50:00','2009-08-11 13:30:00','2009-08-11 18:56:41',1,6,1,6),
  (147,20,'2009-08-12 08:35:58','2009-08-12 11:50:00','2009-08-12 13:30:00','2009-08-12 19:10:20',1,6,1,6),
  (148,19,'2009-08-12 08:49:14','2009-08-12 11:50:00','2009-08-12 13:30:00','2009-08-12 18:34:13',1,6,1,6),
  (149,18,'2009-08-12 08:51:15','2009-08-12 11:50:00','2009-08-12 13:30:00','2009-08-12 18:32:52',1,6,1,6),
  (150,17,'2009-08-12 08:56:57','2009-08-12 11:50:00','2009-08-12 13:30:00','2009-08-12 18:10:00',1,6,1,6),
  (151,21,'2009-08-12 09:02:06','2009-08-12 12:00:00','2009-08-12 13:30:00','2009-08-12 18:15:00',1,6,1,6),
  (152,20,'2009-08-13 08:33:29','2009-08-13 11:50:00','2009-08-13 13:30:00','2009-08-13 19:14:33',1,6,1,6),
  (153,19,'2009-08-13 08:40:48','2009-08-13 11:50:00','2009-08-13 13:30:00','2009-08-13 19:12:27',1,6,1,6),
  (154,18,'2009-08-13 08:55:08','2009-08-13 11:50:00','2009-08-13 13:30:00','2009-08-13 19:17:37',1,6,1,6),
  (155,17,'2009-08-13 08:56:16','2009-08-13 11:50:00','2009-08-13 13:30:00','2009-08-13 19:10:52',1,6,1,6),
  (156,21,'2009-08-13 09:05:04','2009-08-13 11:50:00','2009-08-13 13:30:00','2009-08-13 19:17:07',1,6,1,6),
  (157,20,'2009-08-14 08:36:18','2009-08-14 11:50:00','2009-08-14 13:30:00','2009-08-14 19:01:03',1,6,1,6),
  (158,18,'2009-08-14 08:41:31','2009-08-14 11:50:00','2009-08-14 13:30:00','2009-08-14 19:03:38',1,6,1,6),
  (159,17,'2009-08-14 08:50:28','2009-08-14 11:50:00','2009-08-14 13:30:00','2009-08-14 19:01:03',1,6,1,6),
  (160,19,'2009-08-14 08:55:19','2009-08-14 11:50:00','2009-08-14 13:30:00','2009-08-14 19:03:18',1,6,1,6),
  (161,21,'2009-08-14 08:45:00','2009-08-14 11:50:00','2009-08-14 13:30:00','2009-08-14 18:56:49',1,6,1,6),
  (162,20,'2009-08-17 08:33:50','2009-08-17 11:50:00','2009-08-17 13:30:00','2009-08-17 18:59:08',1,6,1,6),
  (163,17,'2009-08-17 08:55:52','2009-08-17 11:50:00','2009-08-17 13:30:00','2009-08-17 18:14:58',1,6,1,6),
  (164,18,'2009-08-17 08:58:52','2009-08-17 11:50:00','2009-08-17 13:30:00','2009-08-17 19:02:21',1,6,1,6),
  (165,19,'2009-08-17 09:07:01','2009-08-17 11:50:00','2009-08-17 13:30:00','2009-08-17 19:02:42',2,6,1,6),
  (166,21,'2009-08-17 09:07:29','2009-08-17 11:50:00','2009-08-17 13:30:00','2009-08-17 19:37:18',2,6,1,6),
  (167,20,'2009-08-18 08:41:18','2009-08-18 11:50:00','2009-08-18 13:30:00','2009-08-18 19:13:32',1,6,6,6),
  (168,17,'2009-08-18 08:48:25','2009-08-18 11:50:00','2009-08-18 13:30:00','2009-08-18 18:39:41',1,6,1,6),
  (169,18,'2009-08-18 08:53:58','2009-08-18 11:50:00','2009-08-18 13:30:00','2009-08-18 18:43:34',1,6,1,6),
  (170,21,'2009-08-18 09:10:51','2009-08-18 11:50:00','2009-08-18 13:30:00','2009-08-18 18:52:22',2,6,1,6),
  (175,19,'2009-08-18 08:30:00','2009-08-18 11:50:00','2009-08-18 13:30:00','2009-08-18 18:44:34',1,6,1,6),
  (176,20,'2009-08-19 08:41:41','2009-08-19 11:50:00','2009-08-19 13:30:00','2009-08-19 19:03:51',1,6,1,6),
  (177,19,'2009-08-19 08:54:08','2009-08-19 11:50:00','2009-08-19 13:30:00','2009-08-19 18:46:55',1,6,1,6),
  (178,18,'2009-08-19 08:57:10','2009-08-19 11:50:00','2009-08-19 13:30:00','2009-08-19 18:44:09',1,6,1,6),
  (179,17,'2009-08-19 08:57:24','2009-08-19 11:50:00','2009-08-19 13:30:00','2009-08-19 19:04:17',1,6,1,6),
  (180,21,'2009-08-19 08:59:04','2009-08-19 11:50:00','2009-08-19 13:30:00','2009-08-19 18:52:04',1,6,1,6),
  (181,20,'2009-08-20 08:42:38','2009-08-20 11:50:00','2009-08-20 13:30:00','2009-08-20 19:07:58',1,6,1,6),
  (182,19,'2009-08-20 08:51:26','2009-08-20 11:50:00','2009-08-20 13:30:00','2009-08-20 19:06:25',1,6,1,6),
  (183,18,'2009-08-20 08:55:14','2009-08-20 11:50:00','2009-08-20 13:30:00','2009-08-20 19:06:38',1,6,1,6),
  (184,21,'2009-08-20 08:58:40','2009-08-20 11:50:00','2009-08-20 13:30:00','2009-08-20 18:56:22',1,6,1,6),
  (185,17,'2009-08-20 09:01:14','2009-08-20 11:50:00','2009-08-20 13:30:00','2009-08-20 19:07:45',1,6,1,6),
  (186,20,'2009-08-21 08:34:35','2009-08-21 11:50:00','2009-08-21 13:30:00','2009-08-21 18:30:00',1,6,1,6),
  (187,18,'2009-08-21 08:48:38','2009-08-21 11:50:00','2009-08-21 13:30:00','2009-08-21 18:48:08',1,6,1,6),
  (188,17,'2009-08-21 08:56:37','2009-08-21 11:50:00','2009-08-21 13:30:00','2009-08-21 18:35:26',1,6,1,6),
  (189,21,'2009-08-21 09:09:57','2009-08-21 11:50:00','2009-08-21 13:30:00','2009-08-21 19:25:47',2,6,1,6),
  (190,20,'2009-08-24 08:41:34','2009-08-24 11:50:00','2009-08-24 13:30:00','2009-08-24 19:02:17',1,6,1,6),
  (191,19,'2009-08-24 08:43:27','2009-08-24 11:50:00','2009-08-24 13:30:00','2009-08-24 18:49:15',1,6,1,6),
  (192,18,'2009-08-24 08:55:10','2009-08-24 11:50:00','2009-08-24 13:30:00','2009-08-24 18:49:47',1,6,1,6),
  (193,21,'2009-08-24 08:56:16','2009-08-24 11:50:00','2009-08-24 13:30:00','2009-08-24 19:03:19',1,6,1,6),
  (194,17,'2009-08-24 09:02:32','2009-08-24 11:50:00','2009-08-24 13:30:00','2009-08-24 19:00:17',1,6,1,6),
  (195,19,'2009-08-21 09:00:00','2009-08-21 11:50:00','2009-08-21 13:30:00','2009-08-21 18:10:00',8,8,8,8),
  (196,20,'2009-08-25 08:42:38','2009-08-25 11:50:00','2009-08-25 13:30:00','2009-08-25 18:42:12',1,6,1,6),
  (197,18,'2009-08-25 08:44:04','2009-08-25 11:50:00','2009-08-25 13:30:00','2009-08-25 18:33:07',1,6,1,6),
  (198,17,'2009-08-25 08:46:19','2009-08-25 11:50:00','2009-08-25 13:30:00','2009-08-25 18:42:32',1,6,1,6),
  (199,19,'2009-08-25 08:48:38','2009-08-25 11:50:00','2009-08-25 13:30:00','2009-08-25 18:45:24',1,6,1,6),
  (200,22,NULL,'2009-08-03 11:50:00',NULL,NULL,NULL,NULL,NULL,NULL),
  (201,19,'2009-08-03 09:00:00','2009-08-03 11:50:00','2009-08-03 13:30:00','2009-08-03 18:43:05',1,6,1,6),
  (202,18,'2009-08-03 09:00:00','2009-08-03 11:50:00','2009-08-03 13:30:00','2009-08-03 18:45:00',1,6,1,6),
  (203,4,NULL,'2009-08-04 11:50:00',NULL,NULL,NULL,NULL,NULL,NULL),
  (204,21,'2009-08-25 09:09:44','2009-08-25 11:45:00','2009-08-25 13:30:00','2009-08-25 18:45:00',1,6,1,6),
  (205,20,'2009-08-26 08:42:26','2009-08-26 11:50:00','2009-08-26 13:30:00','2009-08-26 18:53:40',1,6,1,6),
  (206,17,'2009-08-26 08:47:22','2009-08-26 11:50:00','2009-08-26 13:30:00','2009-08-26 18:55:52',1,6,1,6),
  (207,19,'2009-08-26 08:48:10','2009-08-26 11:50:00','2009-08-26 13:30:00','2009-08-26 19:41:36',1,6,1,6),
  (208,18,'2009-08-26 08:51:38','2009-08-26 11:50:00','2009-08-26 13:30:00','2009-08-26 19:12:06',1,6,1,6),
  (209,21,'2009-08-26 08:56:08','2009-08-26 11:50:00','2009-08-26 13:30:00','2009-08-26 18:55:02',1,6,1,6),
  (210,20,'2009-08-27 08:36:57','2009-08-27 15:00:00','2009-08-27 15:00:00','2009-08-27 18:15:00',1,6,8,8),
  (211,19,'2009-08-27 08:43:00','2009-08-27 11:50:00','2009-08-27 13:30:00','2009-08-27 18:30:21',1,6,1,6),
  (212,18,'2009-08-27 08:49:54','2009-08-27 11:50:00','2009-08-27 13:30:00','2009-08-27 18:31:29',1,6,1,6),
  (213,21,'2009-08-27 08:54:28','2009-08-27 12:00:00','2009-08-27 13:30:00','2009-08-27 18:36:40',1,6,1,6),
  (214,17,'2009-08-27 08:58:48','2009-08-27 11:50:00','2009-08-27 13:30:00','2009-08-27 18:23:04',1,6,1,6),
  (215,20,'2009-08-28 08:41:36','2009-08-28 11:50:00','2009-08-28 13:30:00','2009-08-28 18:32:17',1,6,1,6),
  (216,18,'2009-08-28 08:51:17','2009-08-28 11:50:00','2009-08-28 13:30:00','2009-08-28 18:35:14',1,6,1,6),
  (217,19,'2009-08-28 08:58:06','2009-08-28 11:50:00','2009-08-28 13:30:00','2009-08-28 18:36:12',1,6,1,6),
  (218,17,'2009-08-28 09:02:58','2009-08-28 11:50:00','2009-08-28 13:30:00','2009-08-28 18:35:47',1,6,1,6),
  (219,21,'2009-08-28 09:06:01','2009-08-28 11:50:00','2009-08-28 13:30:00','2009-08-28 18:23:47',2,6,1,6),
  (220,17,'2009-08-31 08:33:38','2009-08-31 11:50:00','2009-08-31 13:30:00','2009-08-31 18:30:19',1,6,1,6),
  (221,20,'2009-08-31 08:37:02','2009-08-31 11:50:00','2009-08-31 13:30:00','2009-08-31 18:52:24',1,6,1,6),
  (222,19,'2009-08-31 08:47:44','2009-08-31 11:50:00','2009-08-31 13:30:00','2009-08-31 18:21:56',1,6,1,6),
  (223,18,'2009-08-31 08:48:20','2009-08-31 11:50:00','2009-08-31 13:30:00','2009-08-31 18:39:07',1,6,1,6),
  (224,20,'2009-09-01 08:35:17','2009-09-01 11:50:00','2009-09-01 13:30:00','2009-09-01 18:46:58',1,6,1,6),
  (225,19,'2009-09-01 08:45:03','2009-09-01 11:50:00','2009-09-01 13:30:00','2009-09-01 18:34:57',1,6,1,6),
  (226,18,'2009-09-01 08:51:23','2009-09-01 11:50:00','2009-09-01 13:30:00','2009-09-01 18:52:01',1,6,1,6),
  (227,21,'2009-09-01 08:58:30','2009-09-01 11:50:00','2009-09-01 13:30:00','2009-09-01 19:05:37',1,6,1,6),
  (228,17,'2009-09-01 09:04:42','2009-09-01 11:50:00','2009-09-01 13:30:00','2009-09-01 18:50:58',1,6,1,6),
  (229,20,'2009-09-02 08:38:40','2009-09-02 11:50:00','2009-09-02 13:30:00','2009-09-02 18:17:58',1,6,1,6),
  (230,18,'2009-09-02 08:50:35','2009-09-02 11:50:00','2009-09-02 13:30:00','2009-09-02 18:18:53',1,6,1,6),
  (231,21,'2009-09-02 08:55:19','2009-09-02 11:50:00','2009-09-02 13:30:00','2009-09-02 18:18:28',1,6,1,6),
  (232,17,'2009-09-02 08:56:43','2009-09-02 11:50:00','2009-09-02 13:30:00','2009-09-02 18:21:59',1,6,1,6),
  (233,19,'2009-09-02 08:58:32','2009-09-02 11:50:00','2009-09-02 13:30:00','2009-09-02 18:18:47',1,6,1,6),
  (234,20,'2009-09-03 08:45:42','2009-09-03 11:50:00','2009-09-03 13:30:00','2009-09-03 18:52:52',1,6,1,6),
  (235,18,'2009-09-03 08:51:06','2009-09-03 11:50:00','2009-09-03 13:30:00','2009-09-03 18:53:47',1,6,1,6),
  (236,19,'2009-09-03 08:51:19','2009-09-03 11:50:00','2009-09-03 13:30:00','2009-09-03 18:54:29',1,6,1,6),
  (237,21,'2009-09-03 09:03:51','2009-09-03 11:50:00','2009-09-03 13:30:00','2009-09-03 19:01:26',1,6,1,6),
  (238,17,'2009-09-03 09:23:39','2009-09-03 11:50:00','2009-09-03 13:30:00','2009-09-03 18:48:19',3,6,1,6),
  (239,20,'2009-09-04 08:28:45','2009-09-04 11:50:00','2009-09-04 13:30:00','2009-09-04 18:27:02',1,6,1,6),
  (240,19,'2009-09-04 08:42:38','2009-09-04 11:50:00','2009-09-04 13:30:00','2009-09-04 18:31:00',1,6,1,6),
  (241,18,'2009-09-04 08:52:52','2009-09-04 11:50:00','2009-09-04 13:30:00','2009-09-04 18:27:54',1,6,1,6),
  (242,21,'2009-09-04 09:03:21','2009-09-04 11:50:00','2009-09-04 13:30:00','2009-09-04 19:26:24',1,6,1,6),
  (243,17,'2009-09-04 09:17:43','2009-09-04 11:50:00','2009-09-04 13:30:00','2009-09-04 18:30:11',3,6,1,6),
  (244,20,'2009-09-07 08:35:00',NULL,NULL,NULL,1,NULL,NULL,NULL),
  (245,19,'2009-09-07 08:43:38',NULL,NULL,NULL,1,NULL,NULL,NULL),
  (246,18,'2009-09-07 08:52:13',NULL,NULL,NULL,1,NULL,NULL,NULL),
  (247,21,'2009-09-07 08:59:03',NULL,NULL,NULL,1,NULL,NULL,NULL),
  (248,21,'2009-08-31 08:45:00','2009-08-31 12:00:00','2009-08-31 13:30:00','2009-08-31 18:30:00',1,6,1,6),
  (249,17,'2009-09-07 09:19:15',NULL,NULL,NULL,3,NULL,NULL,NULL);

#COMMIT;

#
# Data for the `t_holiday` table  (LIMIT 0,500)
#

INSERT INTO `t_holiday` (`id`, `holidayName`) VALUES 
  (1,'事假'),
  (2,'病假'),
  (3,'休假'),
  (4,'加班');

#COMMIT;

#
# Data for the `t_holidaylog` table  (LIMIT 0,500)
#

INSERT INTO `t_holidaylog` (`id`, `endTime`, `reason`, `startTime`, `holiday_id`, `users_id`) VALUES 
  (1,'2009-07-17 15:26:00','xxxxx','2009-07-13 09:26:00',1,4);

#COMMIT;

#
# Data for the `t_overtime` table  (LIMIT 0,500)
#

INSERT INTO `t_overtime` (`id`, `endTime`, `reason`, `startTime`, `user_id`) VALUES 
  (4,'2009-07-08 10:28:00','ddddd','2009-07-07 10:28:00',4);

#COMMIT;

#
# Data for the `t_project` table  (LIMIT 0,500)
#

INSERT INTO `t_project` (`id`, `projectdescription`, `projectname`) VALUES 
  (3,'项目A','项目A'),
  (4,'项目B','项目B'),
  (5,'项目C','项目C'),
  (6,'项目D','项目D'),
  (7,'项目E','项目E'), 
  (8,'项目F','项目F'),
  (9,'项目G','项目G'),
  (10,'项目H','项目H'),
  (11,'项目I','项目I'),
  (12,'项目J','项目J'),
  (13,'项目K','项目K');

#COMMIT;
#
# Data for the `t_resource` table  (LIMIT 0,500)
#

INSERT INTO `t_resource` (`id`, `type`, `value`, `description`) VALUES 
  (3,'URL','/module/users/*','员工管理'),
  (4,'URL','/module/resource/*','权限管理'),
  (5,'URL','/module/department/*','部门管理'),
  (6,'URL','/module/daka/*','打卡管理'),
  (9,'URL','/module/role/*','角色管理'),
  (12,'URL','/module/task/schedule*','调度任务'),
  (14,'URL','/module/task/audit*','审核任务'),
  (15,'URL','/module/project/*','项目管理'),
  (16,'URL','/module/task/taskmanage/*','任务管理（增删改）');

#COMMIT;

#
# Data for the `t_role` table  (LIMIT 0,500)
#

INSERT INTO `t_role` (`id`, `description`, `name`) VALUES 
  (7,'打卡管理员','ROLE_12482501801403'),
  (9,'后台管理员','ROLE_12493735764849'),
  (10,'项目管理','ROLE_12493742580156'),
  (11,'产生任务','ROLE_12493743231875'),
  (12,'审删调任务','ROLE_12493745330000');

#COMMIT;

#
# Data for the `t_role_resource` table  (LIMIT 0,500)
#

INSERT INTO `t_role_resource` (`role_id`, `resource_id`) VALUES 
  (7,6),
  (9,3),
  (9,4),
  (9,5),
  (9,9),
  (10,15),
  (12,12),
  (12,14),
  (12,16);

#COMMIT;

#
# Data for the `t_statistic` table  (LIMIT 0,500)
#

INSERT INTO `t_statistic` (`id`, `businessTime`, `countOfEO`, `countOfLate`, `month`, `overTime`, `sickTime`, `vacationTime`, `workTime`, `year`, `users_id`) VALUES 
  (99,0,0,0,8,0,0,0,0,2009,4),
  (100,0,0,1,8,0,0,0,623250,2009,17),
  (101,0,0,0,8,0,0,0,630078,2009,18),
  (102,27000,0,1,8,0,0,0,603942,2009,19),
  (103,11400,0,0,8,0,0,0,639880,2009,20),
  (104,0,0,4,8,0,0,0,627726,2009,21),
  (105,0,0,0,8,0,0,0,0,2009,22);

#COMMIT;

#
# Data for the `t_task` table  (LIMIT 0,500)
#

INSERT INTO `t_task` (`id`, `priority`, `taskdescription`, `taskname`, `taskstate`, `tasktype`, `project_id`, `users_id`) VALUES 
  (25,'高','xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','xxx','已完成','修改任务',3,18), 
  (26,'高','qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq','qqq','已完成','修改任务',3,18),
  (27,'高','wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww','www','已完成','修改任务',3,18),
  (28,'高','aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa','aaa','已完成','修改任务',3,18),
  (29,'高','ssssssssssssssssssssssssssssssss','sss','已完成','修改任务',3,17),
  (30,'高','dddddddddddddddddddddddddddddddd','ddd','已完成','修改任务',4,17),
  (31,'高','ffffffffffffffffffffffffffffffff','fff','已完成','修改任务',3,17),
  (32,'高','eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee','eee','已完成','修改任务',3,18),
  (33,'低','gggggggggggggggggggggggggggggggg','ggg','待完成','开发任务',5,20),
  (34,'高','hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh','hhh','待完成','开发任务',6,19),
  (35,'高','pppppppppppppppppppppppppppppppp','ppp','已完成','修改任务',3,18),
  (36,'高','oooooooooooooooooooooooooooooooo','ooo','已完成','修改任务',3,18),
  (37,'高','llllllllllllllllllllllllllllllll','lll','已完成','修改任务',3,20),
  (38,'高','kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk','kkk','已完成','开发任务',5,20),
  (40,'高','hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh','hhh','已完成','修改任务',3,18),
  (41,'高','jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj','jjj','已完成','修改任务',3,17),
  (42,'中','zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz','zzz','已完成','修改任务',5,18),
  (43,'中','xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','xxx','待完成','开发任务',7,18),
  (44,'高','cccccccccccccccccccccccccccccccc','ccc','已完成','开发任务',3,17),
  (45,'高','vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv','vvv','已完成','修改任务',9,17),
  (46,'高','hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh','hhh','待完成','修改任务',3,17),
  (47,'高','rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr','rrr','已完成','支持任务',9,17),
  (48,'高','tttttttttttttttttttttttttttttttt','ttt','已完成','修改任务',4,17),
  (49,'高','yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy','yyy','已完成','修改任务',8,20),
  (50,'低','mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm','mmm','待完成','开发任务',10,17),
  (51,'中','nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn','nnn','待完成','开发任务',11,19),
  (52,'高','11111111111111111111111111111111','111','待完成','支持任务',11,19),
  (53,'高','22222222222222222222222222222222','222','已完成','修改任务',3,18),
  (54,'高','33333333333333333333333333333333','333','已完成','修改任务',8,18),
  (55,'高','44444444444444444444444444444444','444','已完成','开发任务',11,20),
  (56,'高','55555555555555555555555555555555','555','待完成','美工任务',12,19),
  (57,'高','66666666666666666666666666666666','666','已完成','修改任务',6,17),
  (58,'高','77777777777777777777777777777777','777','已完成','修改任务',3,18),
  (59,'高','88888888888888888888888888888888','888','已完成','修改任务',3,18),
  (60,'高','99999999999999999999999999999999','999','待完成','修改任务',3,17),
  (61,'高','sdasdddddddddddddddssssssssssssf','asd','待完成','文档任务',7,18),
  (62,'高','wergdfhhhhhhhhhhhhhrtturrrrrrrrr','qwe','已完成','文档任务',11,17),
  (63,'高','uitytttttttttyuuuuurtrrgggrtyrty','sdf','已完成','文档任务',5,20),
  (64,'中','dfhytetujeryyyyyyyyyyyyrertyhrty','ser','已完成','修改任务',3,17),
  (65,'高','pppprtiohrntuehusdfkdjflkjiojifs','por','已完成','支持任务',4,17);

#COMMIT;

#
# Data for the `t_tasklog` table  (LIMIT 0,500)
#

INSERT INTO `t_tasklog` (`id`, `action`, `date`, `executor_id`, `operator_id`, `task_id`, `result`) VALUES 
  (5,'提交任务','2009-08-11 17:20:35',NULL,18,42,NULL),
  (6,'添加任务','2009-08-13 09:06:13',17,21,44,NULL),
  (7,'添加任务','2009-08-13 09:11:38',17,21,45,NULL),
  (8,'添加任务','2009-08-13 10:00:44',17,21,46,NULL),
  (9,'提交任务','2009-08-14 08:53:50',NULL,17,41,NULL),
  (10,'添加任务','2009-08-17 09:45:51',17,21,47,NULL),
  (11,'审核任务','2009-08-17 09:46:44',17,21,41,'审核通过'),
  (12,'添加任务','2009-08-17 18:26:03',17,21,48,NULL),
  (13,'提交任务','2009-08-18 08:49:42',NULL,17,47,NULL),
  (14,'提交任务','2009-08-18 08:50:00',NULL,17,45,NULL),
  (15,'提交任务','2009-08-18 08:50:09',NULL,17,44,NULL),
  (16,'审核任务','2009-08-18 09:12:38',17,21,44,'审核通过'),
  (17,'审核任务','2009-08-18 09:12:38',17,21,45,'审核通过'),
  (18,'审核任务','2009-08-18 09:12:38',17,21,47,'审核通过'),
  (19,'添加任务','2009-08-18 09:38:22',20,21,49,NULL),
  (20,'添加任务','2009-08-18 09:43:29',17,21,50,NULL),
  (21,'提交任务','2009-08-18 10:22:16',NULL,20,49,NULL),
  (22,'添加任务','2009-08-18 10:23:45',19,21,51,NULL),
  (23,'添加任务','2009-08-18 13:24:42',19,21,52,NULL),
  (24,'提交任务','2009-08-19 14:19:22',NULL,17,48,NULL),
  (25,'添加任务','2009-08-19 16:09:22',18,21,53,NULL),
  (26,'添加任务','2009-08-19 17:46:48',18,21,54,NULL),
  (27,'审核任务','2009-08-19 17:47:22',17,21,48,'审核通过'),
  (28,'审核任务','2009-08-19 17:48:25',20,21,49,'审核通过'),
  (29,'添加任务','2009-08-20 14:38:52',20,21,55,NULL),
  (30,'添加任务','2009-08-24 18:22:32',19,21,56,NULL),
  (31,'添加任务','2009-08-25 09:38:32',17,21,57,NULL),
  (32,'添加任务','2009-08-26 11:37:29',18,21,58,NULL),
  (33,'添加任务','2009-08-26 11:41:09',18,21,59,NULL),
  (34,'添加任务','2009-08-26 13:48:22',17,21,60,NULL),
  (35,'提交任务','2009-08-26 18:56:37',NULL,17,57,NULL),
  (36,'审核任务','2009-08-27 08:57:12',17,21,57,'审核通过'),
  (37,'添加任务','2009-08-27 09:06:30',18,21,61,NULL),
  (38,'添加任务','2009-08-27 09:07:57',17,21,62,NULL),
  (39,'添加任务','2009-08-27 09:09:58',20,21,63,NULL),
  (40,'提交任务','2009-08-27 09:16:03',NULL,18,58,NULL),
  (41,'提交任务','2009-08-27 09:16:34',NULL,18,54,NULL),
  (42,'提交任务','2009-08-27 09:16:42',NULL,18,53,NULL),
  (43,'提交任务','2009-08-27 10:30:38',NULL,20,63,NULL),
  (44,'提交任务','2009-08-27 10:30:50',NULL,20,55,NULL),
  (45,'添加任务','2009-08-27 17:15:57',17,21,64,NULL),
  (46,'审核任务','2009-08-27 17:17:10',18,21,53,'审核通过'),
  (47,'审核任务','2009-08-27 17:17:10',18,21,54,'审核通过'),
  (48,'审核任务','2009-08-27 17:17:10',18,21,58,'审核通过'),
  (49,'审核任务','2009-08-27 17:17:10',20,21,63,'审核通过'),
  (50,'审核任务','2009-08-27 17:17:10',20,21,55,'审核通过'),
  (51,'提交任务','2009-08-27 18:11:21',NULL,17,62,NULL),
  (52,'提交任务','2009-08-27 18:22:19',NULL,17,64,NULL),
  (53,'审核任务','2009-08-27 18:37:33',17,21,62,'审核通过'),
  (54,'审核任务','2009-08-27 18:37:33',17,21,64,'审核通过'),
  (63,'提交任务','2009-08-28 15:19:02',NULL,20,38,NULL),
  (67,'审核任务','2009-09-01 09:01:59',20,21,38,'审核通过'),
  (81,'提交任务','2009-09-03 18:11:19',NULL,18,59,NULL),
  (86,'审核任务','2009-09-04 09:25:18',18,21,59,'审核通过');

#COMMIT;

#
# Data for the `t_user_role` table  (LIMIT 0,500)
#

INSERT INTO `t_user_role` (`user_id`, `role_id`) VALUES 
  (17,11),
  (17,12),
  (18,11),
  (18,12),
  (19,11),
  (19,12),
  (20,7),
  (20,11),
  (20,12),
  (21,7),
  (21,9),
  (21,10),
  (21,11),
  (21,12);

#COMMIT;

#
# Data for the `t_worktime` table  (LIMIT 0,500)
#

INSERT INTO `t_worktime` (`id`, `description`, `time`) VALUES 
  (1,'上班','09:00:00'),
  (2,'上午下班','11:50:00'),
  (3,'下午上班','13:30:00'),
  (4,'下班','18:10:00');

#COMMIT;


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;