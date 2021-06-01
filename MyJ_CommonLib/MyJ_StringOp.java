package MyJ_CommonLib;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.web.servlet.RequestToViewNameTranslator;

public class MyJ_StringOp{
	
	/**����������Ա*/
	String [] m_StrArray;
	public MyJ_StringOp(String [] pams)
	{
		m_StrArray=pams;
	}
	public boolean ArrayContain(String hasStr)
	{
		return MyJ_StringOp.ChkStrArrayContains(m_StrArray, hasStr);
	}
	public static String encodingUrlParam_try(String param,StringBuffer outMsg)
	{
		String encodedParam="";
		outMsg=new StringBuffer();
		try {
			encodedParam = URLEncoder.encode(param, "UTF-8");			
			return encodedParam;			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			encodedParam=e.getStackTrace().toString();
			outMsg.append(encodedParam);
			return "";
		} //encode
	}
	public static String convertByteToString(byte[] b)
	{
		return new String(b,StandardCharsets.UTF_8);
	}
	public static String decodingUrlParam_try(String param,StringBuffer outMsg)
	{
		String decodedParam="";
		outMsg=new StringBuffer();
		try {
			decodedParam = URLDecoder.decode(param, "UTF-8");			
			return decodedParam;			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			decodedParam=e.getStackTrace().toString();
			outMsg.append(decodedParam);
			return "";
		} //encode
	}	
	public static String decodingUrlParam_try(String param)
	{
		String decodedParam="";
		try {
			decodedParam = URLDecoder.decode(param, "UTF-8");			
			return decodedParam;			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			decodedParam=e.getStackTrace().toString();
			return null;
		} //encode
	}
	@Deprecated	 //函数有bug,因为Dobule的声明类似为class Double{final double value;}所以只能使用一次,当再次赋值时,会改变指针位置.
	public static boolean tryParseDouble_1(String str,Double outDbl)
	{
		try {
			outDbl=Double.parseDouble(str);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	public static boolean tryParseDouble(String str)
	{
		try {
			Double.parseDouble(str);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}
	/**�����Ǿ�̬��Ա*/
	public static boolean ChkStrArrayContains(String []arr,String comp)
	{
		if(arr==null)
			return false;
		for (String string : arr) {
			if(string.equals(comp))
				return true;
		}
		return false;
	}
	public static int getIndexOfStrArray(String []arr,String comp)
	{
		if(arr==null)
			return -1;
		for (int i=0;i<arr.length;i++) {
			if(arr[i].equals(comp))
				return i;
		}
		return -1;
	}
	public static boolean tryParseFloat(String str)
	{
		try {
			Float.parseFloat(str);
		}catch (Exception e) {			
			return false;
		}
		return true;
	}
	public static boolean chkIsInjectionAttacksStr(String str) {
		if(str==null||str.trim()==""||str.contains("=")||str.contains(">")||str.contains("<")
				||str.contains("'")||str.contains("\""))
			return true;
		return  false;
	}
	public static boolean ChkNameValid(String username)
	{
		if (username==null)
			return false;
		String regEx="[^a-zA-Z0-9_]";
		Pattern pattern=Pattern.compile(regEx);
		Matcher matcher=pattern.matcher(username);
		return  !matcher.find();
	}
	public static boolean ChkJsonStrValid(String jsonstr)
	{
		if (jsonstr==null)
			return false;
		String regEx="[\\(\\)<>]"; 
		Pattern pattern=Pattern.compile(regEx);
		Matcher matcher=pattern.matcher(jsonstr);
		return  !matcher.find();
	}
	public static String  GetMD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       

        try {
            byte[] btInput = s.getBytes();
            // ���MD5ժҪ�㷨�� MessageDigest ����
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ʹ��ָ�����ֽڸ���ժҪ
            mdInst.update(btInput);
            // �������
            byte[] md = mdInst.digest();
            // ������ת����ʮ�����Ƶ��ַ�����ʽ
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	public static boolean tryParseLong(String str) {
		// TODO Auto-generated method stub
		try {
			Long.parseLong(str);
		}catch (Exception e) {			
			return false;
		}
		return true;
	}
}
