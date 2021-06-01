package MyJ_CommonLib;

import java.io.PrintWriter;
import java.sql.Connection;
import java.text.BreakIterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lyshw.Control.Init.DispatchInit;
import lyshw.model.Db.DbLyUser;
import lyshw.model.Template._SessionUserInfo;



public class OpCookie extends HttpServlet
{
	public static final String NAME_USERNAME="un";
	public static final String NAME_USERCHKCODE="chkcode";
	public static int COOKIEMAXAGE=60*60*24*30;
	public static final String SETCOOKIEURL="/view/logon/setcookie.jsp";
	public static final String JS_COOKIEFUN=
								"<script type=\"text/javascript\">"
								+ "function writeCookie(name,value,day)"
								+ "{expire=\"\";"
								+ "expire=new Date();"
								+ "expire.setTime(expire.getTime()+day*24*3600*1000);"
								+ "expire=expire.toGMTString();"
								+ "document.cookie=name+\"=\"+escape(value)+\";expires=\"+expire;}";
	public String Js_strNewCookie="";
	public void Js_NewCookie(String name,String Value,int time)
	{
		Js_strNewCookie+="writeCookie('"+name+"','"+Value+"',"
								+String.valueOf(time)+");";		
	}
	public String Js_getCookieCode()
	{
		return JS_COOKIEFUN+Js_strNewCookie+"</script>";
	}
		/**<b>因网页include后不能写cookie，所以现在废弃此函数
	 * 当网页被include时（如Dispatch.class--mvc结构)，被转发的response是不能设置
	 * http头内的cookie的，所以此函数完成一个转发（forward（）），在转发的页面中设置
	 * cookie然后再跳转到forwardUrl的网址
	 * 设置客户端的cookie方便用户下次登录,加密数据存储在数据库
	 * @see UpdataUserCookieChkCode
	 * @param resp <b>可以为NULL<b> ，为NULL将不设置cookie，如zpcroot账户就不需设置cookie
	 * @param sUserName
	 * @return
	 */
/*	public static boolean setCookieUserLogonParam_Dispatch_delete(
			HttpServletRequest req,
			HttpServletResponse resp,String forwardUrl)
	{
		boolean bRst=false;
//		MyMsg.DbgMsg("in setCookie");
		if(resp==null) return false;
		if(forwardUrl==null||forwardUrl=="")
			forwardUrl="/index.html";
		OpSession.setCookieAfterForwardUrl(req, forwardUrl);
		try
		{
				DispatchInit.st_context.getRequestDispatcher(SETCOOKIEURL)
									.forward(req, resp);
				//MyMsg.DbgMsg("sendRe");
				//resp.sendRedirect(SETCOOKIEURL);
				//MyMsg.DbgMsg("sendRedir ok");
				bRst=true;
		} catch (Exception e)
		{
			// TODO: handle exception
			MyMsg.RlsMsg("error:--------------------------\r\n"
					+"\r\nlyshw.model.Common.OpCookie.setCookieUserLogonParam_Dispatch(): exception\r\n"
					+e.toString());
			
		}
		return bRst;
	}*/

	/**通过js设置cookie
	 * @param req
	 * @param resp
	 * @return 返回cookie的js代码
	 */
	public static boolean setCookieUserLogonParam(HttpServletRequest req,
			HttpServletResponse resp)
	{
		boolean bRsulte=false;
		String sUserName=OpSession.getSessionUserInfo(req, resp).sUserName;
		if(sUserName==null || sUserName=="")
		{
			MyMsg.RlsMsg("error:lyshw.model.Common.setCookieUserLogonParam() sUserName is null or ''");
			return bRsulte;
		}
		DbLyUser dlu=new DbLyUser();
		String sCod=String.format("%d",(long)(Math.random()*1000000));
		DbResultEnum dre=dlu.UpdataUserCookieChkCode(sUserName,sCod);
		
		if(dre!=DbResultEnum.RECODE_UPDATASECCUES)
		{
			MyMsg.RlsMsg("error:lyshw.model.Common.setCookieUserLogonParam() database control failed!");
			return bRsulte;
		}
		else {
			PrintWriter out;
			try{
				out=resp.getWriter();
			}catch(Exception e)
			{
				MyMsg.RlsMsg("lyshw.model.Common.setCookieUserLogonParam() "
						+ "resp.getWriter() exception\r\n"+e.toString());
				return false;
			}
			OpCookie oc=new OpCookie();
			oc.Js_NewCookie(NAME_USERNAME,sUserName, 30);
			oc.Js_NewCookie(NAME_USERCHKCODE, sCod, 30);
			out.println(oc.Js_getCookieCode());
			bRsulte=true;
		}
		return bRsulte;
	}
	/**如果是Dispatch分配的或者是在include包含的页面，执行此函数无效
	 * 第一次使用在view/logon/setcookie.jsp
	 * @see setCookieUserLogonParam_Dispatch
	 * @param req
	 * @param resp
	 * @return
	 */
/*	oldCode public static boolean setCookieUserLogonParam(HttpServletRequest req,
			HttpServletResponse resp)
	{
		boolean bRsulte=false;
		String sUserName=OpSession.getSessionUserInfo(req, resp).sUserName;
		if(sUserName==null || sUserName=="")
		{
			MyMsg.RlsMsg("error:lyshw.model.Common.setCookieUserLogonParam() sUserName is null or ''");
			return bRsulte;
		}
		DbLyUser dlu=new DbLyUser();
		String sCod=String.format("%d",(long)(Math.random()*1000000));
		DbResultEnum dre=dlu.UpdataUserCookieChkCode(sUserName,sCod);
		
		if(dre!=DbResultEnum.RECODE_UPDATASECCUES)
		{
			MyMsg.RlsMsg("error:lyshw.model.Common.setCookieUserLogonParam() database control failed!");
			return bRsulte;
		}
		else {
			Cookie cnm=new Cookie(KEY_USERNAME, sUserName);
			cnm.setMaxAge(COOKIEMAXAGE);
			resp.addCookie(cnm);
			Cookie ccode=new Cookie(KEY_USERCHKCODE, sCod);
			ccode.setMaxAge(COOKIEMAXAGE);
				resp.addCookie(ccode);
			bRsulte=true;
		}
		return bRsulte;
	}*/
	public static void ReleaseCookie(HttpServletRequest req,HttpServletResponse resp)
	{
/*		Cookie [] cookies=req.getCookies();
		for(Cookie cookie: cookies){
			cookie.setMaxAge(0);
			cookie.setPath("/");
			resp.addCookie(cookie);   
		}*/
		PrintWriter out;
		try{
			out=resp.getWriter();
		}
		catch(Exception e){
			MyMsg.RlsMsg("error:lyshw.model.Common.OpCookie.class.ReleaseCookie() resp.getWriter() error");
			return;
		}
		out.println(
		"<script>"
		+"function clearCookie(){"
		+"var keys=document.cookie.match(/[^ =;]+(?=\\=)/g);"
		+"if (keys) {"
		+"for (var i = keys.length; i--;)"
	   +"document.cookie=keys[i]+'=0;expires=' + new Date( 0).toUTCString()"
		+"}}"
		+ "clearCookie();"
		+ "</script>" 
		);
	}
	/**
	 * @param req
	 * @param resp
	 * @param sui
	 * @return
	 */
	public static boolean ChkCookieForUserLogon(
			HttpServletRequest req,HttpServletResponse resp,_SessionUserInfo sui)
	{
		boolean bRsulte=false;
		Cookie [] cookies=req.getCookies();
		if(cookies==null)
			return false;
		String sUserName="";
		String sCookieCod="";
		for(int i=0;i<cookies.length;i++)
		{	
			if(cookies[i].getName().equals(NAME_USERNAME))
			{
				sUserName=cookies[i].getValue();
			}
			if(cookies[i].getName().equals(NAME_USERCHKCODE))
			{
				sCookieCod=cookies[i].getValue();
			}
	//		MyMsg.DbgMsg("["+i+"]name="+cookies[i].getName()+" sc="+cookies[i].getValue());
		}
		if(sUserName==null||sCookieCod==null
				||sUserName==""||sCookieCod=="")
			return false;
		DbLyUser dlu=new DbLyUser();		
		String sCod=dlu.getCookieChkCode(sUserName,sui);
		if(sCod==null||sCod=="")
		{
			return false;
		}
		if(sCod.equals(sCookieCod))
			if(setCookieUserLogonParam(req,resp))
				bRsulte=true;
		return bRsulte;		
	}
}
