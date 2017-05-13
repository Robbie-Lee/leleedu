package com.lele.manager.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
	
    public static final int YEAR_RETURN = 0;  
    
    public static final int MONTH_RETURN = 1;  
      
    public static final int DAY_RETURN = 2;  
      
    public static final int HOUR_RETURN= 3;  
      
    public static final int MINUTE_RETURN = 4;  
      
    public static final int SECOND_RETURN = 5;  
      
    public static final String YYYY = "yyyy";  
      
    public static final String YYYYMM = "yyyy-MM";  
      
    public static final String YYYYMMDD = "yyyy-MM-dd";  
      
    public static final String YYYYMMDDHH= "yyyy-MM-dd HH";  
      
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";  
      
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";  
  
    public static long getBetween(Date beginDate, Date endDate, int returnPattern) { 
    	
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);  
//        Date beginDate = simpleDateFormat.parse(beginTime);  
//        Date endDate = simpleDateFormat.parse(endTime);  
          
        Calendar beginCalendar = Calendar.getInstance();  
        Calendar endCalendar = Calendar.getInstance();  
        beginCalendar.setTime(beginDate);  
        endCalendar.setTime(endDate);  
        
        switch (returnPattern) {  
        case YEAR_RETURN:  
            return TimeUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR);  
        case MONTH_RETURN:  
            return TimeUtils.getByField(beginCalendar, endCalendar, Calendar.YEAR)*12 + 
            		TimeUtils.getByField(beginCalendar, endCalendar, Calendar.MONTH);  
        case DAY_RETURN:  
            return TimeUtils.getTime(beginDate, endDate)/(24*60*60*1000);  
        case HOUR_RETURN:  
            return TimeUtils.getTime(beginDate, endDate)/(60*60*1000);  
        case MINUTE_RETURN:  
            return TimeUtils.getTime(beginDate, endDate)/(60*1000);  
        case SECOND_RETURN:  
            return TimeUtils.getTime(beginDate, endDate)/1000;  
        default:  
            return 0;  
        }  
    }  
  
    private static long getByField(Calendar beginCalendar, Calendar endCalendar, int calendarField){  
        return endCalendar.get(calendarField) - beginCalendar.get(calendarField);  
    }  
      
    private static long getTime(Date beginDate, Date endDate){  
        return endDate.getTime() - beginDate.getTime();  
    }  
}
