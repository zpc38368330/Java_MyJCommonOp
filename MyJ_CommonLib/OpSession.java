package MyJ_CommonLib; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.AddressingFeature.Responses;


/**
 * @author zpc
 *此类是会话的一些常用操作，经常会与_SessionUserInfo结构体一起搭配使用，有效减少
 *_SessionUserInfo结构的肥度
 */
public class OpSession
{
	//////////////////////////////////////////////////////////////////
	public static final String sSession_ChkCode="se_cc";//即CheckCode
	//在lyshw.Control.Root.forwardRootPage(..)内转发，并在被转发的jsp文件内获得此会话信息
	public static final String sSession_ForwardMsg="se_fm";
	public static final String sReq_CreateSellerInfoMsg="se_csim";
	/**请求范围会话的用户信息属性值*/
	public static final String SESSION_USERINFO="se_userinfo";
	/** 请求范围会话dir12arry的属性值*/
	/**注意如果更新了商家的dir12table必须将SESSION_DIR12ARRY设置为null
	 * 搭配函数为:OpSession.DelDir12Arry(req)
	 * 设置此变量是为了提高访问的速度，防止每次用户切换页面都会访问数据库*/
	/**2014.7.21*///public static final String SESSION_DIR12ARRY="se_dir";
	/**设置cookie后再此跳转的url，参考setCookieAfterFrowardUrl()*/
	public static final String SESSION_CookieAfterBeSetForwardUrl="se_ck";
	/**设置服务器设置客户cookie后执行跳转的url，参考OpCookie.java*/
	public static void setCookieAfterForwardUrl(HttpServletRequest req,
			String url)
	{
		req.setAttribute(SESSION_CookieAfterBeSetForwardUrl, url);
	}
	/**读取服务器设置客户cookie后执行跳转的url，参考OpCookie.java*/
	public static String getCookieAfterForwardUrl(HttpServletRequest req)
	{
		String result;
		result=(String)req.getAttribute(SESSION_CookieAfterBeSetForwardUrl);
		if(result==null)
			return "";
		return result;
	}
	public static String  setCreateRandChkCode(HttpSession session)
	{
		String str;
		str=String.format("%d",(long)(Math.random()*100000));
		session.setAttribute(sSession_ChkCode,str);
		return str;
	}
	public static String getCreateRandChkCode(HttpSession session)
	{
		String str;
		str=(String)session.getAttribute(sSession_ChkCode);
		if(str==null)
			return "";
		return str;
	}
	/**
	 * @param sui _SessionUserInfo
	 * @param str
	 * 用来添加_SessionUserInfo.sTmpMsg
	 * @see  lyshw.model.Template._SessionUserInfo
	 */
	public static void AddsTmpMsg(_SessionUserInfo sui,String str)
	{
		sui.sTmpMsg+=str+"<br/>";
	}
	/**将_SessionUserInfo 加入请求范围内，以便查询//过后补充，应该是创建商家时的临时会话信息吧
	 * @param req
	 * @param sui
	 *@see getReqCreateSellerMsg
	 */
	public static void PutReqCreateSellerMsg(HttpServletRequest req,_SessionUserInfo sui)
	{
		req.setAttribute(sReq_CreateSellerInfoMsg,sui);
	}
	/**从请求范围内获取 _SessionUserInfo 的信息//过后补充，应该是创建商家时的临时会话信息吧
	 * @param req
	 * @return _SessionUserInfo
	 * @see 
	 * lyshw.model.Common.PutReqCreateSellerMsg
	 */
	public static _SessionUserInfo getReqCreateSellerMsg(HttpServletRequest req)
	{
		return (_SessionUserInfo)req.getAttribute(sReq_CreateSellerInfoMsg);
	}
	/**_SessionUserInfo getSessionUserInfo
							(HttpServletRequest req,HttpServletResponse resp)的重载函数 
	 * 加入可判断客户cookie存储的登录记录，自动登录
	 * @param req
	 * @param forwardUrl 如果是第一访问网页，可能用户会通过cookie登录网页，所以通过cookie登录后
	 * 需要给出转发的地址，因为通过dispatch或include转发的网页，不能修改response的请求头即setCookie
	 * 不会起作用，这里是在设置cookie后增加的新变量，
	 * @see _SessionUserInfo getSessionUserInfo
							(HttpServletRequest req,HttpServletResponse resp)
	 * @return
	 */
	public static _SessionUserInfo getSessionUserInfo
							(HttpServletRequest req,HttpServletResponse resp)
	{
		_SessionUserInfo sui=(_SessionUserInfo)req.getSession().getAttribute(SESSION_USERINFO);
		if(sui==null)
		{
			sui=new _SessionUserInfo();
			setSessionUserInfo(sui, req);
		}
		return sui;		
	}
	/**设置请求范围内会话的用户信息，包括商家
	 * @param sui
	 * @param req
	 */
	public static void setSessionUserInfo(_SessionUserInfo sui,
							HttpServletRequest req)
	{
		req.getSession().setAttribute(SESSION_USERINFO,sui);
	}
	/**2014.7.21 public static void DelDir12Arry(HttpServletRequest req)
	{
		req.getSession().removeAttribute(SESSION_DIR12ARRY);
	}*/
	/**获取dir12的相关信息，并返回js的数组格式化的字符串，方便js调用
	 *<b>注意:</b>暂时限定大小为4kb，防止被恶意攻击
	 * @param req
	 * @return error:null 
	 * 	注意如果更新了商家的dir12table必须将SESSION_DIR12ARRY设置为null
	* 设置此变量是为了提高访问的速度，防止每次用户切换页面都会访问数据库
	 */
	public static String getDir12ArryForJs(HttpServletRequest req)
	{/**2014.7.21：这里的req.getSession().setAttribute(SESSION_DIR12ARRY, sResult);
	  *注释掉了，因为客户端是通过服务器的json文件获取的，所以不需会话中加此值
	  */
		/**注意如果更新了商家的dir12table必须将SESSION_DIR12ARRY设置为null
		 * 搭配函数为:OpSession.DelDir12Arry(req)
		 * 设置此变量是为了提高访问的速度，防止每次用户切换页面都会访问数据库*/
		String sResult;
		/**2014.7.21*///String sResult=(String)req.getSession().getAttribute(SESSION_DIR12ARRY);
		/**2014.7.21*///if(sResult==null)
		/**2014.7.21*///{//从数据库中加载
			_SessionUserInfo sui=(_SessionUserInfo)req.getSession().
					getAttribute(SESSION_USERINFO);
			if(sui!=null&&sui.si!=null)
				sResult=OpDir12Array_js.getDir12ArrayJsonStrFromDb
					(sui.si.sDb_Name, sui.si.sTb_Dir12Name);
			else return null;			
			/**2014.7.21*///}
		if(sResult==null) 
			return null;
		/**暂时限定大小为4kb，防止被恶意攻击*/
		if(sResult.length()>4096)
			sResult.substring(0,4095);
		/**2014.7.21*///req.getSession().setAttribute(SESSION_DIR12ARRY, sResult);
		return sResult;		
	}
	/**
	 * 获取商家的主url
	 */
	public static String getSellerUrl(HttpServletRequest req,HttpServletResponse resp)
	{
		String strurl="";
		String strtmp="/seller/";
		_SessionUserInfo sui=getSessionUserInfo(req, resp);
		if(sui.si==null)
			strurl="错误:您不是商家";
		strurl=sui.si.sSellerDir;
		int index=strurl.indexOf(strtmp);
		if(index>0)
			strurl=strurl.substring(index);
		return strurl;
	}
	public static String getSellerAbsoluteDir(HttpServletRequest req,HttpServletResponse resp)
	{
		_SessionUserInfo sui=getSessionUserInfo(req, resp);
		if(sui.si==null)
		{
			try{
				resp.getWriter().println("错误，你不是商家！");
			}catch(Exception e)
			{
				MyMsg.RlsMsg("OpSession.java exception:\r\n"+e.toString());
				return null;
			}
		}
		return sui.si.sSellerDir;		
	}
}
