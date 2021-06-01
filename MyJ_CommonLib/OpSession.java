package MyJ_CommonLib; 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.AddressingFeature.Responses;


/**
 * @author zpc
 *�����ǻỰ��һЩ���ò�������������_SessionUserInfo�ṹ��һ�����ʹ�ã���Ч����
 *_SessionUserInfo�ṹ�ķʶ�
 */
public class OpSession
{
	//////////////////////////////////////////////////////////////////
	public static final String sSession_ChkCode="se_cc";//��CheckCode
	//��lyshw.Control.Root.forwardRootPage(..)��ת�������ڱ�ת����jsp�ļ��ڻ�ô˻Ự��Ϣ
	public static final String sSession_ForwardMsg="se_fm";
	public static final String sReq_CreateSellerInfoMsg="se_csim";
	/**����Χ�Ự���û���Ϣ����ֵ*/
	public static final String SESSION_USERINFO="se_userinfo";
	/** ����Χ�Ựdir12arry������ֵ*/
	/**ע������������̼ҵ�dir12table���뽫SESSION_DIR12ARRY����Ϊnull
	 * ���亯��Ϊ:OpSession.DelDir12Arry(req)
	 * ���ô˱�����Ϊ����߷��ʵ��ٶȣ���ֹÿ���û��л�ҳ�涼��������ݿ�*/
	/**2014.7.21*///public static final String SESSION_DIR12ARRY="se_dir";
	/**����cookie���ٴ���ת��url���ο�setCookieAfterFrowardUrl()*/
	public static final String SESSION_CookieAfterBeSetForwardUrl="se_ck";
	/**���÷��������ÿͻ�cookie��ִ����ת��url���ο�OpCookie.java*/
	public static void setCookieAfterForwardUrl(HttpServletRequest req,
			String url)
	{
		req.setAttribute(SESSION_CookieAfterBeSetForwardUrl, url);
	}
	/**��ȡ���������ÿͻ�cookie��ִ����ת��url���ο�OpCookie.java*/
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
	 * �������_SessionUserInfo.sTmpMsg
	 * @see  lyshw.model.Template._SessionUserInfo
	 */
	public static void AddsTmpMsg(_SessionUserInfo sui,String str)
	{
		sui.sTmpMsg+=str+"<br/>";
	}
	/**��_SessionUserInfo ��������Χ�ڣ��Ա��ѯ//���󲹳䣬Ӧ���Ǵ����̼�ʱ����ʱ�Ự��Ϣ��
	 * @param req
	 * @param sui
	 *@see getReqCreateSellerMsg
	 */
	public static void PutReqCreateSellerMsg(HttpServletRequest req,_SessionUserInfo sui)
	{
		req.setAttribute(sReq_CreateSellerInfoMsg,sui);
	}
	/**������Χ�ڻ�ȡ _SessionUserInfo ����Ϣ//���󲹳䣬Ӧ���Ǵ����̼�ʱ����ʱ�Ự��Ϣ��
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
							(HttpServletRequest req,HttpServletResponse resp)�����غ��� 
	 * ������жϿͻ�cookie�洢�ĵ�¼��¼���Զ���¼
	 * @param req
	 * @param forwardUrl ����ǵ�һ������ҳ�������û���ͨ��cookie��¼��ҳ������ͨ��cookie��¼��
	 * ��Ҫ����ת���ĵ�ַ����Ϊͨ��dispatch��includeת������ҳ�������޸�response������ͷ��setCookie
	 * ���������ã�������������cookie�����ӵ��±�����
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
	/**��������Χ�ڻỰ���û���Ϣ�������̼�
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
	/**��ȡdir12�������Ϣ��������js�������ʽ�����ַ���������js����
	 *<b>ע��:</b>��ʱ�޶���СΪ4kb����ֹ�����⹥��
	 * @param req
	 * @return error:null 
	 * 	ע������������̼ҵ�dir12table���뽫SESSION_DIR12ARRY����Ϊnull
	* ���ô˱�����Ϊ����߷��ʵ��ٶȣ���ֹÿ���û��л�ҳ�涼��������ݿ�
	 */
	public static String getDir12ArryForJs(HttpServletRequest req)
	{/**2014.7.21�������req.getSession().setAttribute(SESSION_DIR12ARRY, sResult);
	  *ע�͵��ˣ���Ϊ�ͻ�����ͨ����������json�ļ���ȡ�ģ����Բ���Ự�мӴ�ֵ
	  */
		/**ע������������̼ҵ�dir12table���뽫SESSION_DIR12ARRY����Ϊnull
		 * ���亯��Ϊ:OpSession.DelDir12Arry(req)
		 * ���ô˱�����Ϊ����߷��ʵ��ٶȣ���ֹÿ���û��л�ҳ�涼��������ݿ�*/
		String sResult;
		/**2014.7.21*///String sResult=(String)req.getSession().getAttribute(SESSION_DIR12ARRY);
		/**2014.7.21*///if(sResult==null)
		/**2014.7.21*///{//�����ݿ��м���
			_SessionUserInfo sui=(_SessionUserInfo)req.getSession().
					getAttribute(SESSION_USERINFO);
			if(sui!=null&&sui.si!=null)
				sResult=OpDir12Array_js.getDir12ArrayJsonStrFromDb
					(sui.si.sDb_Name, sui.si.sTb_Dir12Name);
			else return null;			
			/**2014.7.21*///}
		if(sResult==null) 
			return null;
		/**��ʱ�޶���СΪ4kb����ֹ�����⹥��*/
		if(sResult.length()>4096)
			sResult.substring(0,4095);
		/**2014.7.21*///req.getSession().setAttribute(SESSION_DIR12ARRY, sResult);
		return sResult;		
	}
	/**
	 * ��ȡ�̼ҵ���url
	 */
	public static String getSellerUrl(HttpServletRequest req,HttpServletResponse resp)
	{
		String strurl="";
		String strtmp="/seller/";
		_SessionUserInfo sui=getSessionUserInfo(req, resp);
		if(sui.si==null)
			strurl="����:�������̼�";
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
				resp.getWriter().println("�����㲻���̼ң�");
			}catch(Exception e)
			{
				MyMsg.RlsMsg("OpSession.java exception:\r\n"+e.toString());
				return null;
			}
		}
		return sui.si.sSellerDir;		
	}
}
