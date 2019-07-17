package com.huigao.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.huigao.dao.DakaDao;
import com.huigao.dao.SystemTipsDao;
import com.huigao.dao.UsersDao;
import com.huigao.dao.WorkTimeDao;
import com.huigao.pojo.DakaLog;
import com.huigao.pojo.Users;
import com.huigao.pojo.WorkTime;
import com.huigao.service.DakaService;
import com.huigao.util.DateUtil;

public class DakaServiceImpl implements DakaService {
	
	private DakaDao dakaDao;
	private SystemTipsDao systemTipsDao;
	private WorkTimeDao workTimeDao;
	private UsersDao usersDao;

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public void deleteDakaLog(DakaLog dakaLog) throws Exception {
		dakaDao.deleteDakaLog(dakaLog);
	}
	
	public void saveDakaLog(DakaLog dakaLog) throws Exception {
		dakaDao.saveDakaLog(dakaLog); 
	}

	public Integer getCountByDateTime(Integer userId, Date beginDate, Date endDate) {
		return dakaDao.getCountByDateTime(userId, beginDate, endDate); 
	}

	public DakaLog getDakaLogById(Integer id) throws Exception {
		return dakaDao.getDakaLogById(id); 
	}
	
	public DakaLog getDakaLogByUserAndDay(Integer userId, Date day)
			throws Exception {
		return dakaDao.getDakaLogByUserAndDay(userId, day);
	}

	public Collection<DakaLog> getListByJsonArray(String array) {
		Collection<DakaLog> collect = new HashSet<DakaLog>();
		
		JSONArray a = JSONArray.fromObject(array);
		Object[] os = a.toArray();
 		for (int i = 0; i < os.length; i++) {
			JSONObject o = (JSONObject)os[i];
			Integer id = null;
			DakaLog dakaLog = null;
			try {
				id = o.getInt("id");
				dakaLog = dakaDao.getDakaLogById(id);
			} catch (Exception e1) {
				dakaLog = new DakaLog();
			}
			try{
				dakaLog.setUsers(usersDao.getById(o.getInt("userid"))); 
			} catch (Exception e) {
				e.printStackTrace();
			}
			String date = o.getString("dakaDate");
			String date1 = o.getString("date1");
			String date2 = o.getString("date2");
			String date3 = o.getString("date3");
			String date4 = o.getString("date4");
			try { dakaLog.setDate1(DateUtil.getDate(date + " " + date1, "yyyy-MM-dd HH:mm:ss"));  }catch(Exception e){ }
			try { dakaLog.setDate2(DateUtil.getDate(date + " " + date2, "yyyy-MM-dd HH:mm:ss"));  }catch(Exception e){ }
			try { dakaLog.setDate3(DateUtil.getDate(date + " " + date3, "yyyy-MM-dd HH:mm:ss"));  }catch(Exception e){ }
			try { dakaLog.setDate4(DateUtil.getDate(date + " " + date4, "yyyy-MM-dd HH:mm:ss"));  }catch(Exception e){ }

			try{ dakaLog.setTip1(systemTipsDao.getById(o.getInt("tip1")));  } catch (Exception e) { }
			try{ dakaLog.setTip2(systemTipsDao.getById(o.getInt("tip2")));  } catch (Exception e) { }
			try{ dakaLog.setTip3(systemTipsDao.getById(o.getInt("tip3")));  } catch (Exception e) { }
			try{ dakaLog.setTip4(systemTipsDao.getById(o.getInt("tip4")));  } catch (Exception e) { }
			collect.add(dakaLog);
		}
		return collect;
	}
	
	public List<DakaLog> listByDateTime(Integer userId, Date beginDate,
			Date endDate, Integer start, Integer limit) {
		Map<Integer,DakaLog> m = new TreeMap<Integer,DakaLog>(); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= days; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			DakaLog d = new DakaLog();
			d.setFlag(false);
			d.setDakaDate(cal.getTime());
			m.put(i, d);
		}
		List<DakaLog> list = dakaDao.listByDateTime(userId, beginDate, endDate, start, limit);
		for (DakaLog dakaLog : list) {
			cal.setTime(dakaLog.getDate1() != null ? dakaLog.getDate1() : dakaLog.getDate3());
			if(m.containsKey(cal.get(Calendar.DAY_OF_MONTH))) { //有这一天的记录
				m.put(cal.get(Calendar.DAY_OF_MONTH), dakaLog);
			}
		}
		Set<Map.Entry<Integer,DakaLog>> s = m.entrySet();
		Iterator<Map.Entry<Integer,DakaLog>> it = s.iterator();
		list = new ArrayList<DakaLog>();
		while(it.hasNext()) {
			list.add(it.next().getValue());
		}
		return list;
	}
	
	public List<DakaLog> listByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate,Integer start,Integer limit){
		long t1 = System.currentTimeMillis();
		Map<Integer,Map<Integer,DakaLog>> m = new TreeMap<Integer,Map<Integer,DakaLog>>(); 
		List<Users> ulist = usersDao.listByDepartment(departmentId, 0, 10000);
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= days; i++) {
			cal.set(Calendar.DAY_OF_MONTH, i);
			Map<Integer,DakaLog> temp = new TreeMap<Integer,DakaLog>();
			for (int j = 0; j < ulist.size(); j++) {
				DakaLog d = new DakaLog();
				d.setFlag(false);
				d.setDakaDate(cal.getTime());
				d.setUsers(ulist.get(j)); 
				temp.put(ulist.get(j).getId(), d);
			}
			m.put(i, temp);
		}
		List<DakaLog> list = dakaDao.listByDateTimeAndDepartment(departmentId, beginDate, endDate, start, limit);
		for (DakaLog dakaLog : list) {
			cal.setTime(dakaLog.getDate1() != null ? dakaLog.getDate1() : dakaLog.getDate3());
			if(m.containsKey(cal.get(Calendar.DAY_OF_MONTH))) { //有这一天的记录
				if(m.get(cal.get(Calendar.DAY_OF_MONTH)).containsKey(dakaLog.getUsers().getId())) { //有这个人的记录
					m.get(cal.get(Calendar.DAY_OF_MONTH)).put(dakaLog.getUsers().getId(), dakaLog);
				}
			}
		}
		Set<Map.Entry<Integer,Map<Integer,DakaLog>>> s = m.entrySet();
		Iterator<Map.Entry<Integer,Map<Integer,DakaLog>>> it = s.iterator();
		list.clear();
		while(it.hasNext()) {
			Map<Integer, DakaLog> t_m = it.next().getValue();
			Set<Map.Entry<Integer,DakaLog>> t_s = t_m.entrySet();
			Iterator<Map.Entry<Integer,DakaLog>> t_it = t_s.iterator();
			while(t_it.hasNext()) {
				list.add(t_it.next().getValue());
			}
		} 
		System.out.println("耗时：" + (System.currentTimeMillis() - t1) + "毫秒");
		return list;
	}
	public Integer getCountByDateTimeAndDepartment(Integer departmentId,Date beginDate,Date endDate){
		return dakaDao.getCountByDateTimeAndDepartment(departmentId, beginDate, endDate);
	}
	
	public WorkTime getWorkTimeById(Integer id) {
		return workTimeDao.getById(id);
	}
	
	
	public String saveDakaLog(Users users) throws Exception {
		Date date = new Date();
		
		/*if(overTimeDao.isOverTime(users,date)) {
			return "加班时间不需要打卡。";
		}*/
		
		DakaLog dakaLog = dakaDao.getDakaLogByUserAndDay(users.getId(), date);
		String info = "打卡成功。 ";
		if(dakaLog == null) { //今天尚未打卡，添加新纪录
			dakaLog = new DakaLog();
			dakaLog.setUsers(users);
			Date standardStartTime = DateUtil.getDateFromTime(workTimeDao.getById(1).getTime());
			int minute = DateUtil.getMinites(date, standardStartTime);
			if(minute > 0) {
				if(DateUtil.getMinites(date, DateUtil.getDateFromTime(workTimeDao.getById(3).getTime())) > 0) { //下午才第一次打卡
					if(DateUtil.getMinites(date, DateUtil.getDateFromTime(workTimeDao.getById(4).getTime())) > 0){//超过下午下班  算下午下班 
						dakaLog.setDate4(date);
						info = info + "您正常下班。";
						dakaLog.setTip4(systemTipsDao.getById(6));
						/*if(dakaLog.getDate3() == null) { //将下午上班补上
							dakaLog.setDate3(DateUtil.getDateFromTime(workTimeDao.getById(3).getTime()));
							dakaLog.setTip3(systemTipsDao.getById(1));
						}*/
					} else {
						dakaLog.setDate3(date);
						info = info + "您迟到" + minute + "分钟，属于三级迟到。";
						dakaLog.setTip3(systemTipsDao.getById(4));
					}
				} else { //上午第一次打卡
					dakaLog.setDate1(date);
					if(minute > 5 && minute <= 15) { //一级迟到
						info = info + "您迟到" + minute + "分钟，属于一级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(2));
					} else if(minute > 15 && minute <= 60) { //二级迟到
						info = info + "您迟到" + minute + "分钟，属于二级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(3));
					} else if(minute > 60) { //三级迟到
						info = info + "您迟到" + minute + "分钟，属于三级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(4));
					} else {
						info = info + "您正常上班。";
						dakaLog.setTip1(systemTipsDao.getById(1));
					}
				}
				
				/*boolean isHoliday = holidayDao.isHoliday(standardStartTime, date); //检查是否已经请过假
				if(isHoliday){
					info = info + "您迟到" + minute + "分钟，但已经请过假。";
					dakaLog.setTip1(systemTipsDao.getById(1));
				} else {
					if(minute > 5 && minute <= 15) { //一级迟到
						info = info + "您迟到" + minute + "分钟，属于一级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(2));
					} else if(minute > 15 && minute <= 60) { //二级迟到
						info = info + "您迟到" + minute + "分钟，属于二级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(3));
					} else if(minute > 60) { //三级迟到
						info = info + "您迟到" + minute + "分钟，属于三级迟到。";
						dakaLog.setTip1(systemTipsDao.getById(4));
					}
				}*/
			} else{
				info = info + "您正常上班。";
				dakaLog.setDate1(date);
				dakaLog.setTip1(systemTipsDao.getById(1));
			}
			dakaDao.saveDakaLog(dakaLog); 
			return info;
		} else { //已经打过卡，只需修改打卡信息
			Date standardStartTime = DateUtil.getDateFromTime(workTimeDao.getById(2).getTime());
			int minute = DateUtil.getMinites(date, standardStartTime);
			if(minute > 0 ) { //下午下班
				dakaLog.setDate4(date);
				if(dakaLog.getDate3() == null) {
					dakaLog.setDate3(DateUtil.getDateFromTime(workTimeDao.getById(3).getTime()));
					dakaLog.setTip3(systemTipsDao.getById(1));
				}
				int minute2 = DateUtil.getMinites(DateUtil.getDateFromTime(workTimeDao.getById(4).getTime()), date);
				if(minute2 < 0) {//下午正常上班
					dakaLog.setTip4(systemTipsDao.getById(6));
					info = info + "您现在正常下班。";
				} else {
					info = info + "您早退" + minute2 + "分钟。";
					dakaLog.setTip4(systemTipsDao.getById(5)); //下午早退
				}
				if(dakaLog.getDate2() == null) {
					dakaLog.setDate2(DateUtil.getDateFromTime(workTimeDao.getById(2).getTime()));
					dakaLog.setTip2(systemTipsDao.getById(6));
				}
				if(dakaLog.getDate3() == null) {
					dakaLog.setDate3(DateUtil.getDateFromTime(workTimeDao.getById(3).getTime()));
					dakaLog.setTip3(systemTipsDao.getById(1));
				}
			} else { //上午下班
				dakaLog.setDate2(date);
				int minute2 = DateUtil.getMinites(DateUtil.getDateFromTime(workTimeDao.getById(4).getTime()), date);
				info = info + "您早退" + minute2 + "分钟。";
				dakaLog.setTip2(systemTipsDao.getById(5)); //上午早退
			}
			
			dakaDao.updateDakaLog(dakaLog); 
			return info;
			
			
			/*dakaLog.setEndTime(date);
			Date standardEndTime = DateUtil.getDateFromTime(workTimeDao.getById(4).getTime());
			int minute = DateUtil.getMinites(standardEndTime,date);
			if(minute > 0)  {
				if(holidayDao.isHoliday(date, standardEndTime)){
					info = info + "您早退" + minute + "分钟，但是已经请过假。";
					dakaLog.setEndTips(systemTipsDao.getById(6));
				}
				info = info + "您早退" + minute + "分钟。";
				dakaLog.setEndTips(systemTipsDao.getById(5));
			} else {
				info = info + "您现在正常下班。";
				dakaLog.setEndTips(systemTipsDao.getById(6));
			}*/

		}/* else {
			return "您今天已经完成上下班，重复打卡无效。";
		}*/
	}
	
	
	// --- Inject ---
	public void updateDakaLog(DakaLog dakaLog) throws Exception {
		dakaDao.updateDakaLog(dakaLog);
	}
	public void updateDakaLog(Collection<DakaLog> collect) throws Exception {
		for (DakaLog dakaLog : collect) {
			dakaDao.saveOrUpdate(dakaLog); 
		}
	}
	public void setSystemTipsDao(SystemTipsDao systemTipsDao) {
		this.systemTipsDao = systemTipsDao;
	}
	
	public void setWorkTimeDao(WorkTimeDao workTimeDao) {
		this.workTimeDao = workTimeDao;
	}

	public void setDakaDao(DakaDao dakaDao) {
		this.dakaDao = dakaDao;
	}
	
}
