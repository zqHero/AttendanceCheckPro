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
(114,21,'2019-08-03 09:00:00','2019-08-03 11:50:00','2019-08-03 13:30:00','2019-08-03 18:42:42',1,6,1,6),
(115,21,'2019-08-03 09:00:00','2019-08-03 11:50:00','2019-08-03 13:30:00','2019-08-03 19:00:00',1,6,1,6),
(116,21,'2019-08-03 09:00:00','2019-08-03 11:50:00','2019-08-03 13:30:00','2019-08-03 19:36:07',1,6,1,6),
(117,21,'2019-08-04 08:50:01','2019-08-04 11:50:00','2019-08-04 13:30:00','2019-08-04 19:15:00',1,6,1,6),
(118,21,'2019-08-04 08:50:16','2019-08-04 11:50:00','2019-08-04 13:30:00','2019-08-04 19:14:11',1,6,1,6);

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
(3,'项目A','项目A');

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
(5,'管理员','ROLE_15638954912855'),
(6,'研发部员工','ROLE_15638956398228'),
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
(5,3),
(5,4),
(5,5),
(5,6),
(5,9),
(5,12),
(5,14),
(5,15),
(5,16),

(7,6),
(9,3),
(9,4),
(9,5),
(9,9),
(10,15),
(11,15),
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
INSERT INTO `t_task` (`id`, `priority`, `taskdescription`, `taskname`, `taskstate`, `tasktype`, `project_id`, `users_id`) VALUES
(25,'高','xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx','xxx','已完成','修改任务',3,21);

#COMMIT;

#
# Data for the `t_tasklog` table  (LIMIT 0,500)
INSERT INTO `t_tasklog` (`id`, `action`, `date`, `executor_id`, `operator_id`, `task_id`, `result`) VALUES
(5,'提交任务','2009-08-11 17:20:35',NULL,18,25,NULL);

#COMMIT;

#
# Data for the `t_user_role` table  (LIMIT 0,500)
#
INSERT INTO `t_user_role` (`user_id`, `role_id`) VALUES
(21,5),
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