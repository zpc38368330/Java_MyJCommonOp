package MyJ_CommonLib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class MyJ_HttpOp {
	public final static int POST_DATATYPE_JSON=1;
	public final static int POST_DATATYPE_NONE=2;
	public static  void ResponseWrite(String strWrite) throws IOException
	{
		ResponseWrite(strWrite,true);
	}
	public static void ResponseWrite(String strWrite,boolean isJson) throws IOException
	{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		if(isJson)
			response.setContentType("application/json");
		PrintWriter printWriter=response.getWriter();
		printWriter.write(strWrite);
		printWriter.flush();
		printWriter.close();
	}
    public static String getDataFrom(String urlString) throws Exception {
        String res = "";
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            URLConnection conn = (URLConnection)url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            //System.out.println("����url!");
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println("�ֱ��ȡÿ�����ݣ���"+line);
                res += line;
            }
            reader.close();
        } catch (Exception e) {
            return e.getMessage();
        }
        //System.out.println(res);
        return res;
    }
	/*
	 * ����������ο�,������Ҫʹ��,�˷���Դ
	 */
	public static String getRequest(String key)
	{
		HttpServletRequest request=ServletActionContext.getRequest();
		return request.getParameter(key);
	}
	public static String getAjaxPostString() throws Exception 
	{
		HttpServletRequest request=ServletActionContext.getRequest();
        StringBuffer str=new StringBuffer();
        String lineString=null;
        try {
            BufferedReader reader=request.getReader();
            while ((lineString=reader.readLine())!=null) {
            	str.append(lineString);
            }
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
        return str.toString();
    }
	/*
	 * �˺���д���û�в���,ע����.
	 * PostType�ڱ������г���ȡֵ,�ֽ���json=POSTTYPE_JSON
	 */
    @SuppressWarnings("finally")
	public static String post(String url,String postContent,Map<String, String> querys,int PostType) throws Exception {
        String line         = "";
        String message        = "";
        BufferedReader bufferedReader = null;
        
        try {
            URL urlObject = new URL(url);
            HttpURLConnection urlConn = (HttpURLConnection) urlObject.openConnection();
            urlConn.setDoOutput(true);
urlConn.addRequestProperty("Authorization", "APPCODE a1d2d310f4464513aa41c17245ea29a1");
            /*�趨���û���*/
            //urlConn.setRequestProperty("Pragma:", "no-cache");
            urlConn.setRequestProperty("Cache-Control", "no-cache");
            /*ά�ֳ�����*/
            urlConn.setRequestProperty("Connection", "Keep-Alive");  
            /*�����ַ���*/
            urlConn.setRequestProperty("Charset", "UTF-8");
            /*�趨�����ʽΪjson*/
            if(POST_DATATYPE_JSON==PostType)
            	urlConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            else if(POST_DATATYPE_NONE==PostType)
            {
            	urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            }
            
            
            for (String  k:querys.keySet()) {
				urlConn.setRequestProperty(k, querys.get(k));
			}
            /*����ʹ��POST�ķ�ʽ����*/
            urlConn.setRequestMethod("POST");             
            /*���ò�ʹ�û���*/
            urlConn.setUseCaches(false);
            /*�����������*/
            urlConn.setDoOutput(true);  
            /*������������*/
            urlConn.setDoInput(true);              
            urlConn.connect();
            
            OutputStreamWriter outStreamWriter = new OutputStreamWriter(urlConn.getOutputStream(),"UTF-8"); 
            if(!StringUtils.isBlank(postContent))
            	outStreamWriter.write(postContent);
            outStreamWriter.flush();
            outStreamWriter.close();
            
            /*��postʧ��*/
            if((urlConn.getResponseCode() != 200)){
                message = "����POSTʧ�ܣ�"+ "code="+urlConn.getResponseCode() + "," + "ʧ����Ϣ��"+ urlConn.getResponseMessage();
                // ����BufferedReader����������ȡURL����Ӧ
                InputStream errorStream = urlConn.getErrorStream(); 
                
                if(errorStream != null)
                {
                    InputStreamReader inputStreamReader = new InputStreamReader(errorStream,"utf-8");
                    bufferedReader = new BufferedReader(inputStreamReader);  
              
                    while ((line = bufferedReader.readLine()) != null) {  
                        message += line;    
                    }         
                    inputStreamReader.close();
                errorStream.close();
                }
                return "Post error��Msg��"+message;                  
            }else{
                /*���ͳɹ����ط��ͳɹ�״̬*/
                
                // ����BufferedReader����������ȡURL����Ӧ
                InputStream inputStream = urlConn.getInputStream();  
                
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf-8");
                bufferedReader = new BufferedReader(inputStreamReader);  
          
                while ((line = bufferedReader.readLine()) != null) {  
                    message += line;  
                }        
                inputStream.close();
                inputStreamReader.close();
                
                return message;                        
            }
        } catch (Exception e) {
        	//e.printStackTrace();
        	throw e;
        }finally{
            try {   
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }                  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }          
        }
    }
    
 

}
