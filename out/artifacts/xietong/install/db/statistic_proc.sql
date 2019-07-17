CREATE PROCEDURE `statistic_proc`(IN v_year INTEGER, IN v_month INTEGER)
    NOT DETERMINISTIC
    SQL SECURITY DEFINER
    COMMENT '月度打卡数据统计'
BEGIN

 declare done int default 0;
 declare v_workTime1,v_workTime2,v_workTime3,v_workTime4 int;
 declare v_userId int;

 declare v_workTime,v_overTime,v_businessTime,v_sickTime,v_vacationTime int;
 declare v_lateCount, v_earlyCount int;

 declare cur_1 cursor for select distinct users_id from t_dakalog;
 declare continue handler for not found set done=1;

 delete from t_statistic where year=v_year and month=v_month; /*先删除原有的*/

 select time_to_sec(time) into v_workTime1 from t_worktime where id = 1;
 select time_to_sec(time) into v_workTime2 from t_worktime where id = 2;
 select time_to_sec(time) into v_workTime3 from t_worktime where id = 3;
 select time_to_sec(time) into v_workTime4 from t_worktime where id = 4;

 open cur_1;
 repeat
   fetch cur_1 into v_userId;
   if not done then

   
       /*工作时间*/
       select
       (select ifnull(sum(if (time_to_sec(time (date2)) >v_workTime3,
            time_to_sec(date2) - time_to_sec(date1) -(v_workTime3 -v_workTime2),
            time_to_sec(date2) - time_to_sec(date1))
         ),0) as total
       from t_dakalog where users_id = v_userId and tip1_id not in (8, 9, 10, 11)
       and tip2_id not in (8, 9, 10, 11) and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) +
       (select ifnull(sum(if (time_to_sec(time (date3)) > v_workTime2,
       (time_to_sec(date4) - time_to_sec(date3))
       , (time_to_sec(date4) - time_to_sec(date3)) -(v_workTime3 -v_workTime2))
       ),0) as total from t_dakalog
       where users_id = v_userId and tip3_id not in (8, 9, 10, 11)
       and tip4_id not in (8, 9, 10, 11) and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) as aaa  into v_workTime from dual;

       /*加班时间*/
       select
       (select ifnull(sum(if (time_to_sec(time (date2)) >v_workTime3,
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1) -(v_workTime3 -v_workTime2),
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1))
         ),0) as total
       from t_dakalog where users_id = v_userId and tip1_id=11
       and tip2_id=11 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) +
       (select ifnull(sum(if (time_to_sec(time (date3)) > v_workTime2,
       (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3))
       , (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3)) -(v_workTime3 -v_workTime2))
       ),0) as total from t_dakalog
       where users_id = v_userId and tip3_id=11
       and tip4_id=11 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) as aaa  into v_overTime from dual;

       /*事假时间*/
       select
       (select ifnull(sum(if (time_to_sec(time (date2)) >v_workTime3,
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1) -(v_workTime3 -v_workTime2),
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1))
       ),0) as total
       from t_dakalog where users_id = v_userId and tip1_id=8
       and tip2_id=8 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) +
       (select ifnull(sum(if (time_to_sec(time (date3)) > v_workTime2,
       (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3))
       , (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3)) -(v_workTime3 -v_workTime2))
       ),0) as total from t_dakalog
       where users_id = v_userId and tip3_id=8
       and tip4_id=8 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) as aaa  into v_businessTime from dual;

       /*病假时间*/
       select
       (select ifnull(sum(if (time_to_sec(time (date2)) >v_workTime3,
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1) -(v_workTime3 -v_workTime2),
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1))
       ),0) as total
       from t_dakalog where users_id = v_userId and tip1_id=9
       and tip2_id=9 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) +
       (select ifnull(sum(if (time_to_sec(time (date3)) > v_workTime2,
       (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3))
       , (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3)) -(v_workTime3 -v_workTime2))
       ),0) as total from t_dakalog
       where users_id = v_userId and tip3_id=9
       and tip4_id=9 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) as aaa  into v_sickTime from dual;

       /*休假时间*/
       select
       (select ifnull(sum(if (time_to_sec(time (date2)) >v_workTime3,
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1) -(v_workTime3 -v_workTime2),
            time_to_sec(date2) - if(time_to_sec(date1)>v_workTime1,time_to_sec(date1),v_workTime1))
       ),0) as total
       from t_dakalog where users_id = v_userId and tip1_id=10
       and tip2_id=10 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) +
       (select ifnull(sum(if (time_to_sec(time (date3)) > v_workTime2,
       (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3))
       , (if(time_to_sec(date4)<v_workTime4,time_to_sec(date4),v_workTime4) - time_to_sec(date3)) -(v_workTime3 -v_workTime2))
       ),0) as total from t_dakalog
       where users_id = v_userId and tip3_id=10
       and tip4_id=10 and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month
       ) as aaa  into v_vacationTime from dual;

       /*迟到次数*/
       select count(id) into v_lateCount from t_dakalog
       where ( tip1_id in (2,3,4,7) or tip2_id in (2,3,4,7) or tip3_id in (2,3,4,7) or tip4_id in (2,3,4,7) )
       and users_id=v_userId and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month;

       /*早退次数*/
       select count(id) into v_earlyCount from t_dakalog
       where ( tip1_id=5 or tip2_id=5 or tip3_id=5 or tip4_id=5 )
       and users_id=v_userId and year(ifnull(date1,date3))=v_year and month(ifnull(date1,date3))=v_month;

       /* 插入统计数据 */
       insert into t_statistic(users_id, workTime, overTime, businessTime, sickTime, vacationTime, countOfLate, countOfEO, year, month)
       values(ifnull(v_userId,0), ifnull(v_workTime,0), ifnull(v_overTime,0), ifnull(v_businessTime,0), ifnull(v_sickTime,0), ifnull(v_vacationTime,0), ifnull(v_lateCount,0), ifnull(v_earlyCount,0), v_year, v_month );

   end if;
   until done=1
 end repeat;

 close cur_1;

END;
