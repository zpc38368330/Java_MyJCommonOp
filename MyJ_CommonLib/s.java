package MyJ_CommonLib;
import org.apache.commons.lang.StringUtils;

//String temp="E:\\MyJava\\YanFaBu\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MARVEL\\";
//s.setShowType(s.DEBUG_SHOW|s.ERROR_SHOW|s.INFO_SHOW|s.WARN_SHOW|s.CONSOLE_SHOW);
//s.setLogPath(temp+"WEB-INF\\logs");
//s.debug("hello my world!","123");
//s.error("hello my world!","123");
public class s {
	static String s_pathName="";
	static int s_showType=0;
	public static final int DEBUG_SHOW=1;
	public static final int INFO_SHOW=2;
	public static final int WARN_SHOW=4;
	public static final int ERROR_SHOW=8;
	public static final int CONSOLE_SHOW=16;
	public static void setLogPath(String path)
	{
		s_pathName=path;
		MyJ_DirFileOp.CreateDirs(s_pathName);
	}
	public static void setShowType(int type)//
	{
		s_showType=type;
	}
	public static void info(String msg,String fn)
	{
		if((s_showType&INFO_SHOW)==0)
			return;
		_writeToFile("INFO", msg, fn);
	}	
	public static void debug(String msg,String fn)
	{
		if((s_showType&DEBUG_SHOW)==0)
			return;
		_writeToFile("DBUG", msg, fn);
	}
	public static void warn(String msg,String fn)
	{
		if((s_showType&WARN_SHOW)==0)
			return;
		_writeToFile("WARN", msg, fn);
	}
	public static void error(String msg,String fn)
	{
		if((s_showType&ERROR_SHOW)==0)
			return;
		_writeToFile("ERRO", msg, fn);
	}
	static void _writeToFile(String type,String msg,String fn)
	{
		if(StringUtils.isBlank(s_pathName))
			return;		
		StringBuilder sBuilder=new 	StringBuilder();
		sBuilder.append("["+type+"]");
		sBuilder.append("["+MyJ_DatetimeOp.getCurrentDateStr()+"]");
		sBuilder.append("["+fn+"] ");
		sBuilder.append(msg);
		if((s_showType&CONSOLE_SHOW)!=0)
			System.out.println(sBuilder.toString());
		sBuilder.append("\r\n");
		MyJ_DirFileOp.writeToFile_AppendOrCreate_Try(s_pathName+"\\"+MyJ_DatetimeOp.getCurrentDay()+".log", sBuilder.toString());
		if((s_showType&CONSOLE_SHOW)!=0)
			System.out.println(msg);
	}
}
