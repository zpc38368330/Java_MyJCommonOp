package MyJ_CommonLib;



import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;

import MyJ_CommonLib.PackagingMethod.JsonDateValueProcessor;
import antlr.collections.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

/*
 * �ο����ĵ��ײ�
 */
public class MyJ_JsonOp {
	public static JsonConfig getJsonConfig_NumToBlankStr()
	{
		JsonConfig jConfig=new JsonConfig();
		jConfig.registerDefaultValueProcessor(Float.class, new DefaultValueProcessor() {			
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub				
				return "";
			}
		});
		jConfig.registerDefaultValueProcessor(Double.class, new DefaultValueProcessor() {
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub
				return "";
			}
		});
		jConfig.registerDefaultValueProcessor(java.util.Date.class, new DefaultValueProcessor() {
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub
				return "";
			}
		});
		jConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub
				return "";
			}
		});
		jConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub
				return "";
			}
		});
		jConfig.registerDefaultValueProcessor(Boolean.class, new DefaultValueProcessor() {
			@SuppressWarnings("rawtypes") 
			public Object getDefaultValue(Class arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		jConfig.registerJsonValueProcessor(Date.class,  
                new JsonDateValueProcessor());  
		return jConfig;
	}
	//��������쳣,������ʱ���ֶε�����
	//net.sf.json.JSONException: java.lang.reflect.InvocationTargetException
	public static String getJsonArrayFromClass(Object obClass)throws Exception
	{		
		JsonConfig jConfig=getJsonConfig_NumToBlankStr();
		JSONArray jArray=JSONArray.fromObject(obClass,jConfig);
		return convertStandardJsonString(jArray.toString());
	}
	public static String getJsonObject_FromClass(Object obClass)throws Exception
	{		
		JsonConfig jConfig=getJsonConfig_NumToBlankStr();
		JSONObject jArray=JSONObject.fromObject(obClass,jConfig);
		return convertStandardJsonString(jArray.toString());
	}
	public static <T> java.util.List<T> getBeanArrFromJsonStr(String json, Class<T> cls){		 
		JSONArray arr = JSONArray.fromObject(json);
		@SuppressWarnings({ "deprecation", "unchecked" })
		java.util.List<T> list = JSONArray.toList(arr,cls);
		return list;
	}
	public static String convertStandardJsonString(String jsonValue)
	{
		return jsonValue.replace("\r", "").replace("\n", "");
	}
	public static void demo_foreach(String str)
	{
		JSONObject hostObject = JSONObject.fromObject(str);
		Iterator<String> sIterator = hostObject.keys();
		while(sIterator.hasNext()){
			// 获得key
			String key = sIterator.next();
			// 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可
			String value = hostObject.getString(key);
			System.out.println("key: "+key+",value"+value);
		}
	}
	public static boolean isJsonObject(String content) {
        try {
            JSONObject.fromObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
/*
 * 		List<struct_Menus> sMenus=getMenusFromPid(pid);
		JsonConfig jConfig=MyJ_JsonOp.getJsonConfig_NumToBlankStr();
		JSONArray jArray=JSONArray.fromObject(sMenus,jConfig);
		return jArray.toString();
 */
