package MyJ_CommonLib;

public class MyResult {
	public boolean bSuccess;
	public Integer errcode;
	public String Msg;	
	public Integer iParam;
//	static final String SUCCESS="isok";//this value set 'isok',because most js framwork use 'success' to explain 
	static final String SUCCESS="success";//this value set 'isok',because most js framwork use 'success' to explain 
	public static String getSimpleResult(boolean Success,String msg)
	{
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("{\""+SUCCESS+"\":");
		//if(Success==true)
			sBuffer.append("true,");
		//else
		//	sBuffer.append("false,");
		
		sBuffer.append("\"NoError\":");
		if(Success==true)
			sBuffer.append("true,");
		else
			sBuffer.append("false,");
			
		//put follow code at function last
		sBuffer.append("\"msg\":");
		if(msg!=null)
		{			
			if(msg!=null)
			{
				msg=MyJ_JsonOp.convertStandardJsonString(msg);//msg.replace("\"", "'").replace("\r","").replace("\n", "");
			}
			sBuffer.append("\""+msg+"\"");
		}
		else
			sBuffer.append("\"\"");
		sBuffer.append("}");
		return sBuffer.toString();
	}
	public static String getSimpleResult(boolean Success,Integer errcode,String msg)
	{
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("{\""+SUCCESS+"\":");
		if(Success==true)
			sBuffer.append("true,");
		else
			sBuffer.append("false,");
		
		sBuffer.append("\"errcode\":"+errcode.toString()+",");
		
		//put follow code at function last
		sBuffer.append("\"msg\":");
		if(msg!=null)
			sBuffer.append("\""+msg+"\"");
		else
			sBuffer.append("\"\"");
		sBuffer.append("}");
		return sBuffer.toString();
	}
	public String getJson()
	{		
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append("{\""+SUCCESS+"\":");
		if(bSuccess==true)
			sBuffer.append("true,");
		else
			sBuffer.append("false,");
		sBuffer.append("\"errcode\":");
		if(errcode!=null)
			sBuffer.append(errcode.toString()+",");
		else
			sBuffer.append("0,");
		
		//put follow code at function last
		sBuffer.append("\"msg\":");
		if(Msg!=null)
			sBuffer.append("\""+Msg+"\"");
		else
			sBuffer.append("\"\"");
		sBuffer.append("}");
		return sBuffer.toString();
	}
}
