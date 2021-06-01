package MyJ_CommonLib;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import net.sf.json.JSONObject;

public class MyJ_ReqResult {

    public static final Integer OK = 0;
    //url授权等严重错误
    public static final Integer ERROR = 100;
    public static final Integer ERROR_ACTION_UNKOWN=101;
	public static final Integer ERROR_AUTH_CHECKEXCEPTION=102;
	public static final Integer ERROR_AUTH_REJECT=103;
	
	//用户错误
    public static final int USER_NOTLOGIN=201;    
    public static final int USERERROR_POSTPARAM_ERROR=301;
    
    //数据库错误
    public static final Integer DB_NOTFIND = 601;
	private static final Integer ERROR_DB_EXCEPTION = 602;
	

    private Integer code;
    private String message;
    private String url;
	private Object data;	
	private Boolean success=true;//留给Extjs的form返回判断的,没有这个等于true,总是走failure分支
	public byte[] tcpByteData; 
	private long longParam1=0;
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public static String getSucessMsg(Object o)
	{
		return getJsonMsg(0, "sucess", o);
	}
//	public byte[] getDataToByteArray() {
//		byte[] bytes = null;      
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();      
//		try {        
//		    ObjectOutputStream oos = new ObjectOutputStream(bos);         
//		    oos.writeObject(data);        
//		    oos.flush();         
//		    bytes = bos.toByteArray ();      
//		    oos.close();         
//		    bos.close();        
//		} catch (IOException ex) {        
//		    ex.printStackTrace();   
//		}      
//		return bytes;   
//	}
	public static MyJ_ReqResult getBeanFromJson(String json)
	{
		JSONObject jObject=JSONObject.fromObject(json);
		return (MyJ_ReqResult)JSONObject.toBean(jObject,MyJ_ReqResult.class);
	}
	public static  String getJsonMsg(Integer code,String msg,Object data)
	{
		MyJ_ReqResult rm=new MyJ_ReqResult(code,msg,data);
		String result= rm.toString();
		
		return result;
		
//		if(result.substring(0, 1).equals("["))
//			result=result.substring(1,result.length()-1);
//		return result.toString();
		
//		String result= rm.toString();
//		if(!result.substring(0, 1).equals("["))
//			result="["+result+"]";
//		return result;
	}
	public static String getJsonDatabasePageShow_For_Extjs(int code,long totalcount,Object o) 
	{
		String data="";
		String result="";
		String msg="";
		try {
			data = MyJ_JsonOp.getJsonArrayFromClass(o);
			//data="{\"totalCount\":"+String.valueOf(totalcount)+"\"data\":"+data+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg+=e.getStackTrace().toString();
			return MyJ_ReqResult.getJsonMsg(MyJ_ReqResult.ERROR_DB_EXCEPTION, msg, "");
		}		
		result="{\"code\":0,\"totalCount\":"+String.valueOf(totalcount)+",\"data\":"+data+"}";
		return result;
	}
	public static String getJsonHasOnline(int code,long totalcount,long onlineNum,Object o) 
	{
		String data="";
		String result="";
		String msg="";
		try {
			data = MyJ_JsonOp.getJsonArrayFromClass(o);
			//data="{\"totalCount\":"+String.valueOf(totalcount)+"\"data\":"+data+"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg+=e.getStackTrace().toString();
			return MyJ_ReqResult.getJsonMsg(MyJ_ReqResult.ERROR_DB_EXCEPTION, msg, "");
		}		
		result="{\"code\":0,\"totalCount\":"+String.valueOf(totalcount)+",\"onlineNum\":"+String.valueOf(onlineNum)+",\"data\":"+data+"}";
		return result;
	}
	@Override
	public String toString()
	{
		try {
			String result= MyJ_JsonOp.getJsonObject_FromClass(this);
			if(result.substring(0, 1).equals("["))
				result=result.substring(1,result.length()-1);
			return result.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block			
			return e.toString();
		}		
	}
	public MyJ_ReqResult()
	{
	}
	public MyJ_ReqResult(Integer code,String msg,Object data)
	{
		setCode(code);
		setMessage(msg);
		setData(data);
	}
	

	public long getLongParam1() {
		return longParam1;
	}
	public void setLongParam1(long longParam1) {
		this.longParam1 = longParam1;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}

}
