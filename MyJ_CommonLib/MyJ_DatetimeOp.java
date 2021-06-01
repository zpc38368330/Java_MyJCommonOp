package MyJ_CommonLib;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

//import javax.xml.crypto.Data;

/**
 * 
 * @author Administrator
 * mark:java.sql.Date only write as 'yyyy-mm-dd' ,not 'yyyy-mm-dd hh:mm:ss',so don't use it;
 */
public class MyJ_DatetimeOp {
	public static String DatetimeToStr(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	public static String getCurrentDateStr()
	{
		java.util.Date day=new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return df.format(day);   
	}
	public static String getCurrentDay()
	{
		java.util.Date day=new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		return df.format(day);   
	}
	public static Date getZhengDianTime(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将分钟、秒、毫秒域清零
//        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
//        Date todayReset = cal1.getTime();
//        cal1.setTime(DateUtils.addDay(today,1));
       	return cal1.getTime();
	}
	public static Date getDateForDay(Date date) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date);
        // 将分钟、秒、毫秒域清零
//        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
//        Date todayReset = cal1.getTime();
//        cal1.setTime(DateUtils.addDay(today,1));
       	return cal1.getTime();
	}
    /** 
     * 将时间转换为时间戳
     * @throws Exception 
     */    
    public static long dateToStamp(String s) throws Exception{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
			date = (Date) simpleDateFormat.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new Exception("Explain date string error:"+s+"\r\n"+e.getStackTrace().toString());
		}
        long ts = ((Date) date).getTime();
        return ts;
    }
    public static String stampToDateString(long stamp)
    {
    	SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式
    	String timeText=format.format(stamp);   
    	return timeText;
    }
    public static Date stampToDate(long stamp)
    {
    	return new Date(stamp);
    }
	public static long getInterval_Minute(Date newTime,Date oldTime)
	{
		long result=(newTime.getTime()-oldTime.getTime())/60000;
		return result;
	}
	public static long getInterval_Second(Date newTime,Date oldTime)
	{
		long result=(newTime.getTime()-oldTime.getTime())/1000;
		return result;
	}
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	public static Date ConvertStrToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	public static String getSqlStrFromDate(java.util.Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		return df.format(date);   
	}
	public static Long dateToStamp(Date date) {
		// TODO Auto-generated method stub
		return date.getTime(); 
	}
	public static Date addDateForMinute(Date date,int interval) {
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, interval);
		return c.getTime();
	}
	public static Date getData_yyyy_MM_dd_HH(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
		String s = sdf.format(date);
		return sdf.parse(s);
	}
}
