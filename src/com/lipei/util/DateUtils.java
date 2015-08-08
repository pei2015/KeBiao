package com.lipei.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * 日期工具类
 * @author 赵李沛
 *
 */
public class DateUtils {

	public DateUtils() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * 获取当天日期
	 * @return
	 */
	public static String getNowDayTime(){
		String NowDateTime = "";
		Date date = new Date(System.currentTimeMillis());
		DateFormat sdf = new SimpleDateFormat("MM-dd");
		NowDateTime = sdf.format(date);
		return NowDateTime;	
	}
	/**
	 *获取指定日期所在一周的所有日期
	 * @param time
	 * @return
	 */   
	 public static List<String> convertWeekByDate(Date time) {  
		 if (time!=null) {

		List<String> list = new ArrayList<String>();
		DateFormat sdf=new SimpleDateFormat("MM-dd"); //设置时间格式  
		Calendar cal = Calendar.getInstance();  
		cal.setTime(time);  
		  //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
		 int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		 if(1 == dayWeek) {  
		         cal.add(Calendar.DAY_OF_MONTH, -1);  
		    }   
		    cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
		    int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
		    cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值  
		    Date monday =cal.getTime(); 
		    Calendar c=Calendar.getInstance();			   
			for (int i = 0; i <7; i++) {
			         c.setTime(monday);
			         c.add(Calendar.DATE, i);
			         Date date=c.getTime();
			         String datetime = sdf.format(date); 
			         list.add(datetime);
			}
		        return list;
		 }
		 return null;
		 
      }  
	 /**
	  * 获得考试所占用的节数
	  * @param time1
	  * @param time2
	  * @return
	  */
	  public static int getClassLen(String time1,String time2){
		    int _diff = 0; 
	        long h_diff = 0;
	        long m_diff = 0;
	        DateFormat df = new SimpleDateFormat("HH:mm");
			try {
				Date d1 = df.parse(time1);
			    Date d2 = df.parse(time2);
			  long dif = d1.getTime() - d2.getTime();
			  h_diff = dif/(60*60*1000);
			  m_diff =(dif - h_diff*(1000*60*60))/(1000*60);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_diff = (int) h_diff;
			if ((int)m_diff>=30) {
				_diff++;
			}
	      return _diff;
}
	  /**
	   * 根据日期取得星期几 
	   * @param date
	   * @return
	   */	  
	    public static int getWeek(Date date){  
	        int[] weeks = {7,1,2,3,4,5,6};  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date);  
	        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;  
	        if(week_index<0){  
	            week_index = 0;  
	        }   
	        return weeks[week_index];  
	    } 
/**
 * date转String
 * @param date
 * @param dateformatString
 * @return
 */
	    public static String ConverToString(Date date,String dateformatString)  
	    {  
	        DateFormat df = new SimpleDateFormat(dateformatString);  
	          
	        return df.format(date);  
	    } 
	    /**
	     *  String转Date方法
	     * @param dateString
	     * @return
	     */
	   
	    public static Date getDateFromString(String dateString){
	    	if (dateString!=null) {

	    	    DateFormat sdf = new SimpleDateFormat("MM-dd");  
			    try {
					return sdf.parse(dateString);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
       
				return null;	
	    }
	    
	    public static int getFromClassNum(String string){
            if (string.equals("08:00")) {
				return 1;
			}
            if (string.equals("09:00")) {
				return 2;
			}
            if (string.equals("10:20")) {
				return 3;
			}
            if (string.equals("11:20")) {
				return 4;
			}
            if (string.equals("12:40")) {
				return 5;
			}
            if (string.equals("13:40")) {
				return 6;
			}
            if (string.equals("15:00")) {
				return 7;
			}
            if (string.equals("16:00")) {
				return 8;
			}
            if (string.equals("17:00")) {
				return 9;
			}
            if (string.equals("18:00")) {
				return 10;
			}
            if (string.equals("19:30")) {
				return 11;
			}
            if (string.equals("20:30")) {
				return 12;
			}
			return 0;	
	    }
	    /**
	     * 判断两天是否在同一周
	     * @param date1
	     * @param date2
	     * @return
	     */	    
	    public static boolean isSameDate(String date1,String date2)
	    {
	    if (date1!=null&&date2!=null) {
	     SimpleDateFormat format = new SimpleDateFormat("MM-dd");
	     Date d1 = null;
	     Date d2 = null;
	     try
	     {
	      d1 = format.parse(date1);
	      d2 = format.parse(date2);
	     }
	     catch(Exception e)
	     {
	      e.printStackTrace();
	     }
	     Calendar cal1 = Calendar.getInstance();
	     Calendar cal2 = Calendar.getInstance();
	     cal1.setTime(d1);
	     cal2.setTime(d2);
	     int subYear = cal1.get(Calendar.YEAR)-cal2.get(Calendar.YEAR);
	     //subYear==0,说明是同一年
	     if(subYear == 0)
	     {
	      if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	       return true;
	     }
	     //例子:cal1是"2005-1-1"，cal2是"2004-12-25"
	     //java对"2004-12-25"处理成第52周
	     // "2004-12-26"它处理成了第1周，和"2005-1-1"相同了
	     //大家可以查一下自己的日历
	     //处理的比较好
	     //说明:java的一月用"0"标识，那么12月用"11"
	     else if(subYear==1 && cal2.get(Calendar.MONTH)==11)
	     {
	      if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	       return true;
	     }
	     //例子:cal1是"2004-12-31"，cal2是"2005-1-1"
	     else if(subYear==-1 && cal1.get(Calendar.MONTH)==11)
	     {
	      if(cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
	       return true;
	      
	     }		
		}
	     return false;
	    }
	    /**
	     * 获取下一天日期
	     * @param date
	     * @return
	     */
    public static Date getNextDay(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DAY_OF_MONTH, 1);
	date = calendar.getTime();
	return date;
	}
    /**
     * 获取上一天日期
     * @param date
     * @return
     */
    public static Date getLastDay(Date date) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.add(Calendar.DAY_OF_MONTH, -1);
	date = calendar.getTime();
	return date;
	}
}
