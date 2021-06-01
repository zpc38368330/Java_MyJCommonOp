package MyJ_CommonLib;

import java.util.Date;

public class MyDebug {
	public static boolean bShowing=false;
	public static void show (String msg) {
		if(bShowing)
		{
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        StackTraceElement e = stackTrace[2];
	        StringBuffer info=new StringBuffer();
	        info.append(e.getClassName());
	        info.append("."+e.getMethodName()+"()");
			info.append("  "+e.getLineNumber());//dont use this code on release web;
			System.out.println("--------"+MyJ_DatetimeOp.DatetimeToStr(new Date())+"--------");			
			System.out.println(info);
			System.out.println(msg); 
			System.out.println("-----------------------------------");
		}
	}
	public static String getExecutingMethodName() {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        StackTraceElement e = stackTrace[2];
	        return e.getMethodName();
	}
}
