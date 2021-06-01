package MyJ_CommonLib;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpPostGet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public static final String DIR_UPLOAD="/upload/";
	public static final String DIR_UPLOADTMP="/uploadtmp/";
	public static final String DIR_HEADERINFO="/headerinfo/";
	public static final String DIR_HEADERINFO_IMG="/headerinfo/img/";
	public static final String DIR_IMGTMP="/tmpimg/";
	public static final String DIR_GDS="/gds/";
	
	public static final String FILE_HEADER_SRC="headinfo.src";
	public static final String FILE_HEADER_HTM="headinfo.htm";
	/**�洢��String DIR_IMGTMP="/tmpimg/" Ŀ¼�е���ʱ�ļ�������չ��Ҫ�Լ����� 
	 * 2014.8.10 22:29 �������ƣ�ԭΪFILE_GDS_IMGTMP="tmp",����Ϊ������/tmpimg/�е���ʱ����,��Ϊ��Ʒ����ǰ׺��*/	
	public static final String FILE_GDS_IMGNAME="gdsimg";
	public static final String FILE_GDS_PAGES="gdspages.json";
	/**�ϴ�������*/
	/**ǰ׺UP_��ӦUserUpload.java*/
	public static final String UP_NAME="upfile";
	/**�˱���������ַ���,����http://myurl?<%=UP_NAME_URL%>="info/index.html"
	*��UserUpload.java�ڵ�һ��ʹ�ã�������jsp���Ϊ��̬��ҳ
	*��Ϊjspλ��*/
	public static final String UP_NAME_JSPFILE="up_jsp";
	/**ͬ�� ����Ϊ�洢��htm�ļ�λ��*/
	public static final String UP_NAME_SAVEHTM="up_htm";
	/**��url������ΪUP_YESʱ,�Ὣ��ͨ�û����ʱ���ܿ�����ҳ��洢����̬htm�ļ���
	 * ��һʹ������lyshw.Control.UserUpload.java �д洢editpage.jspΪ��̬htm�ļ�ʱʹ��
	 * @see view.editpage.editpage.jsp*/
	public static final String UP_NAME_CREATEFILE="up_create";
	/**�˱�����Ҫ�ǿ��ǵ�ת��jspΪ��̬htmʱ������jsp����Ҫ�����url�������������ô˱����Ա��Ժ�ʹ��
	 * ��UserUpload.java�ļ��е�һ�˶���
	 */
	public static final String UP_NAME_URLPARAM="up_param";
	
	/**��������������һ�����ڣ�tag_editgoods.jsp�У��ο�ͼ�ϴ��󷵻صĴ���С��ͼ��jsong��ʽ�ı�����
	 * ���޸ĵĵڼ����ο�ͼ
	 * ��ʾ����tag_editgoods.jsp������ajax�����ϴ��ɹ�����������صĴ��뼴��*/
	public static final String UP_NAME_DATUIMGURI="up_dt";
	public static final String UP_NAME_ZHONGTUIMGURI="up_zt";
	public static final String UP_NAME_XIAOTUIMGURI="up_xt";
	public static final String UP_NAME_WHICHREFIMG="up_wi";
	/**�˱���������ַ���������url?upinfostr=������������*/
	public static final String upinfostr="upinfostr";
	/**����ֵ*/
	public static final String UP_upGoodsImg="upgdsimg";
	public static final String UP_headerinfo="herderinfo";
	public static final String UP_headerimg="herderimg";
	public static final String UP_savehtm="savehtm";
	/**�ϴ�ʱ�Ƿ�ѡ��ʹ�ó���ͼ�ĸ�ѡ��,��һ��ʹ�����UP_YES��tag_editgoods.jsp��UserUpload.java��*/
	public static final String UP_cboxDaTu="cboxdatu";
	/**��һ��ʹ�������UP_NAME_CREATEFILE��editpage.jsp��ʹ��
	 * @see UP_NAME_CREATEFILE:ע������</br>
	 *  ��url������ΪUP_YESʱ,�Ὣ��ͨ�û����ʱ���ܿ�����ҳ��洢����̬htm�ļ���
	 * ��һʹ������lyshw.Control.UserUpload.java �д洢editpage.jspΪ��̬htm�ļ�ʱʹ��
	 * @see view.editpage.editpage.jsp*/
	public static final String UP_YES="yes";
	
	
	
	public static String getUrl_SellerHeaderHtm(HttpServletRequest request,
			HttpServletResponse response)
	{
		if(OpSession.getSessionUserInfo(request, response).si!=null)
			return OpSession.getSellerUrl(request, response)+DIR_HEADERINFO+FILE_HEADER_HTM;
		else 
			return null;
	}
	public static String getUrl_SellerHeaderSrc(HttpServletRequest request,
			HttpServletResponse response)
	{
		if(OpSession.getSessionUserInfo(request, response).si!=null)
			return OpSession.getSellerUrl(request, response)+DIR_HEADERINFO+FILE_HEADER_SRC;
		else 
			return null;
	}
}
