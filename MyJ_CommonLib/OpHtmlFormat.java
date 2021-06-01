package MyJ_CommonLib;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lyshw.Control.Init.DispatchInit;
import lyshw.model.Template._Uri;

public class OpHtmlFormat extends HttpServlet
{ 
	private static final long serialVersionUID = 1L;
	/**下面注释为DispathHome.java处复制注释</br>
	 * <b>注意</b>修改此处代码，也需修改DispathHome处代码
	 * @author zpc
	 <b>注意</b>本类的doPost为了省去servlet代码的一次跳转（如：http:/.. ?home=/../../....)
	 * 如用lyshw.mode.Common.<b>OpHtmlFormat.OutTemplateHtml</b>的函数，则需加一次if判断，为了
	 * 大用户访问时的一次if判断特将<b>OpHtmlFormat.OutTemplateHtml</b>代码复制到此doPost单独处理，
	 * </br><b>所以修改此doPost代码记住修改<b>OpHtmlFormat.OutTemplateHtml</b>代码
	 */	
	public static boolean OutTemplateHtml(
			HttpServletRequest req,HttpServletResponse resp,			
			PrintWriter out,String sDispatchUrl,String sTemplateKey)
	{
		boolean bRsulte=true;
		//获取URL
		String urlString=req.getServletPath();
		
		int iSub=0;
		iSub = urlString.indexOf(sDispatchUrl);
		if (iSub < 0)
		{
			MyMsg.RlsMsg("!!error!!: " +sDispatchUrl+ " not indexOf url string");
			forwardNotFoundPage(req, resp,"错误的地址："+
					urlString+"indextOf("+sDispatchUrl+")");
			return false;
		}
		//System.out.println("-----------zpc:Dispatch init okey-------------");
		
//		System.out.println("-----------"+loadTemplate.toString()+" "+lyshw.model.Template.AddUri.sPageUrlMap);
		
		@SuppressWarnings("unchecked")
		HashMap<String, _Uri> hMap=(HashMap<String, _Uri> )
			DispatchInit.st_context.getAttribute(sTemplateKey);
		if(hMap==null)
		{
			MyMsg.RlsMsg("!!error!!:"+sDispatchUrl+" hMap == null");
			forwardExceptionPage(req, resp,"错误的哈系表:未找到"+sDispatchUrl);
			return false;
		}
		//打印相应的URL对应的title
		urlString.substring(0, iSub);
		_Uri uri=hMap.get(urlString);
		if(uri==null)
		{
			forwardExceptionPage(req, resp,"哈系表中未找到:"+urlString);
			MyMsg.RlsMsg("!!error!!:"+urlString+" uri == null");
			return false;
		}
		out.print(uri.getHtmlHead());
/*		out.print(uri.getSTittle());

		out.print("</title><style type=\"text/css\">");
		// 打印css导入字符串
		out.print(uri.getSCss());
		out.print("</style></head><body>");*/

		//打印(包含)body字符串数组
		ArrayList<String> alBody=uri.getAlBody();
		int len=alBody.size();
		for (int i=0;i<len;i++)
		{
			try
			{
				DispatchInit.st_context.getRequestDispatcher(alBody.get(i)).include(req,resp);
			} 
			catch (ServletException se)
			{
				MyMsg.RlsMsg("error:lyshw.model.Common.OpHtmlFormat()"+alBody.get(i)+"ServletException!\r\n"+se.toString());
			}
			catch(Exception e)
			{
				// TODO: handle exception
				MyMsg.RlsMsg("error:lyshw.model.Common.OpHtmlFormat()"+alBody.get(i)+" exception!\r\n"+e.toString());		
			}			
		}
		out.print("</body></html>");
		return bRsulte;
	}
	public static void forwardNotFoundPage(HttpServletRequest req,
			HttpServletResponse resp,String msg)
	{ 
		try
		{
			String spageurl=(String)DispatchInit.st_context.getAttribute("pageNotFound");
			DispatchInit.st_context.getRequestDispatcher(spageurl).forward(req, resp);
		} catch (Exception e)
		{
			// TODO: handle exception
			MyMsg.RlsMsg("error:--------------------------\r\n"
					+msg+"\r\nlyshw.model.Common.OpHtmlFormat.forwardNotFoundPage(): exception\r\n"
					+e.toString());
		}
		
	}
	public static void forwardExceptionPage(HttpServletRequest req,
			HttpServletResponse resp,String msg)
	{
		try
		{
			String spageurl=(String)DispatchInit.st_context.getAttribute("pageException");
			DispatchInit.st_context.getRequestDispatcher(spageurl).forward(req, resp);
		} catch (Exception e)
		{
			// TODO: handle exception
			MyMsg.RlsMsg("error:--------------------------\r\n"
					+msg+"\r\nlyshw.model.Common.OpHtmlFormat.forwardNotFoundPage(): exception\r\n"
					+e.toString());
		}
		
	}
}
