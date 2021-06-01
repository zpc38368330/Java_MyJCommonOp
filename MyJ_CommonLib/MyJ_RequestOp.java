package MyJ_CommonLib;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class MyJ_RequestOp{
	public static Date getDate(HttpServletRequest request,String name) {
		String strDate=request.getParameter(name);
		if(StringUtils.isBlank(strDate))
			return null;
		String  time=MyJ_StringOp.decodingUrlParam_try(strDate);
		return MyJ_DatetimeOp.strToDateLong(time);
	}
	public static Double getDouble(HttpServletRequest request,String name) {
		String value=request.getParameter(name);
		if(!MyJ_StringOp.tryParseDouble(value))
			return null;
		return Double.parseDouble(value);
	}
	public static Long getLong(HttpServletRequest request,String name) {
		String strdid=request.getParameter(name);
		if(StringUtils.isBlank(strdid)||!MyJ_StringOp.tryParseLong(strdid))
			return null;
		return Long.parseLong(strdid);
	}
}