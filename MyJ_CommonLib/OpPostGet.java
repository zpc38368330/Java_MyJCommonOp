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
	/**存储在String DIR_IMGTMP="/tmpimg/" 目录中的临时文件名，扩展名要自己设置 
	 * 2014.8.10 22:29 更改名称，原为FILE_GDS_IMGTMP="tmp",不再为仅存在/tmpimg/中的临时名称,此为商品名的前缀名*/	
	public static final String FILE_GDS_IMGNAME="gdsimg";
	public static final String FILE_GDS_PAGES="gdspages.json";
	/**上传变量名*/
	/**前缀UP_对应UserUpload.java*/
	public static final String UP_NAME="upfile";
	/**此变量后跟随字符串,即：http://myurl?<%=UP_NAME_URL%>="info/index.html"
	*在UserUpload.java内第一次使用，用来将jsp输出为静态网页
	*此为jsp位置*/
	public static final String UP_NAME_JSPFILE="up_jsp";
	/**同上 ，此为存储的htm文件位置*/
	public static final String UP_NAME_SAVEHTM="up_htm";
	/**此url参数当为UP_YES时,会将普通用户浏览时所能看到的页面存储到静态htm文件。
	 * 第一使用是在lyshw.Control.UserUpload.java 中存储editpage.jsp为静态htm文件时使用
	 * @see view.editpage.editpage.jsp*/
	public static final String UP_NAME_CREATEFILE="up_create";
	/**此变量主要是考虑到转换jsp为静态htm时，或许jsp会需要额外的url参数，所以设置此变量以备以后使用
	 * 在UserUpload.java文件中第一此定义
	 */
	public static final String UP_NAME_URLPARAM="up_param";
	
	/**下面三个变量第一次用在：tag_editgoods.jsp中，参考图上传后返回的大中小三图的jsong格式的变量名
	 * 和修改的第几个参考图
	 * 提示：在tag_editgoods.jsp中搜索ajax，找上传成功后服务器返回的代码即可*/
	public static final String UP_NAME_DATUIMGURI="up_dt";
	public static final String UP_NAME_ZHONGTUIMGURI="up_zt";
	public static final String UP_NAME_XIAOTUIMGURI="up_xt";
	public static final String UP_NAME_WHICHREFIMG="up_wi";
	/**此变量后跟随字符串，即：url?upinfostr=“。。。。”*/
	public static final String upinfostr="upinfostr";
	/**变量值*/
	public static final String UP_upGoodsImg="upgdsimg";
	public static final String UP_headerinfo="herderinfo";
	public static final String UP_headerimg="herderimg";
	public static final String UP_savehtm="savehtm";
	/**上传时是否选中使用超大图的复选框,第一次使用配合UP_YES在tag_editgoods.jsp与UserUpload.java中*/
	public static final String UP_cboxDaTu="cboxdatu";
	/**第一次使用是配合UP_NAME_CREATEFILE在editpage.jsp中使用
	 * @see UP_NAME_CREATEFILE:注释如下</br>
	 *  此url参数当为UP_YES时,会将普通用户浏览时所能看到的页面存储到静态htm文件。
	 * 第一使用是在lyshw.Control.UserUpload.java 中存储editpage.jsp为静态htm文件时使用
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
