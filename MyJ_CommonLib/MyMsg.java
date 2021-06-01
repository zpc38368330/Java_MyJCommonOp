package MyJ_CommonLib;

public class MyMsg {
	public static void DbgMsg (String msg)
	{
		System.out.println("-----------------DBGMSG---------------");
		System.out.println(msg);
/*		StackTraceElement stack[] = Thread.currentThread().getStackTrace();  		
		for (StackTraceElement ste : stack) {
					System.out.println(ste.getClassName()+":"+ste.getMethodName());
				}*/
		System.out.println("-----------------DBGMSG---------------");
	}
	public static void RlsMsg (String msg)
	{
		System.out.println("-----------------RLSMSG---------------");
		System.out.println(msg);			
		System.out.println("------------------------------------");
	}
	public static void DbgMsg(Class<?> o,String msg)
	{
		System.out.println("-----------------DBGMSG---------------");
		System.out.println(o.toString()+"\r\n"+msg);
		System.out.println("-----------------DBGMSG---------------");		
	}
	public static void DbgMsg(String tittle,String msg)
	{
		System.out.println("-----------------DBGMSG---------------");
		System.out.println(tittle+"\r\n"+msg);
		System.out.println("-----------------DBGMSG---------------");		
	}
}
