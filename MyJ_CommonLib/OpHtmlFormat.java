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
	/**����ע��ΪDispathHome.java������ע��</br>
	 * <b>ע��</b>�޸Ĵ˴����룬Ҳ���޸�DispathHome������
	 * @author zpc
	 <b>ע��</b>�����doPostΪ��ʡȥservlet�����һ����ת���磺http:/.. ?home=/../../....)
	 * ����lyshw.mode.Common.<b>OpHtmlFormat.OutTemplateHtml</b>�ĺ����������һ��if�жϣ�Ϊ��
	 * ���û�����ʱ��һ��if�ж��ؽ�<b>OpHtmlFormat.OutTemplateHtml</b>���븴�Ƶ���doPost��������
	 * </br><b>�����޸Ĵ�doPost�����ס�޸�<b>OpHtmlFormat.OutTemplateHtml</b>����
	 */	
	public static boolean OutTemplateHtml(
			HttpServletRequest req,HttpServletResponse resp,			
			PrintWriter out,String sDispatchUrl,String sTemplateKey)
	{
		boolean bRsulte=true;
		//��ȡURL
		String urlString=req.getServletPath();
		
		int iSub=0;
		iSub = urlString.indexOf(sDispatchUrl);
		if (iSub < 0)
		{
			MyMsg.RlsMsg("!!error!!: " +sDispatchUrl+ " not indexOf url string");
			forwardNotFoundPage(req, resp,"����ĵ�ַ��"+
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
			forwardExceptionPage(req, resp,"����Ĺ�ϵ��:δ�ҵ�"+sDispatchUrl);
			return false;
		}
		//��ӡ��Ӧ��URL��Ӧ��title
		urlString.substring(0, iSub);
		_Uri uri=hMap.get(urlString);
		if(uri==null)
		{
			forwardExceptionPage(req, resp,"��ϵ����δ�ҵ�:"+urlString);
			MyMsg.RlsMsg("!!error!!:"+urlString+" uri == null");
			return false;
		}
		out.print(uri.getHtmlHead());
/*		out.print(uri.getSTittle());

		out.print("</title><style type=\"text/css\">");
		// ��ӡcss�����ַ���
		out.print(uri.getSCss());
		out.print("</style></head><body>");*/

		//��ӡ(����)body�ַ�������
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
