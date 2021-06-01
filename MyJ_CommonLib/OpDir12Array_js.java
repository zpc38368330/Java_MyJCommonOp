package MyJ_CommonLib;

import javax.servlet.http.HttpServlet;

import lyshw.model.Db.DbLyshwSeller_TbDirOp;
import lyshw.model.Db.DbLyshwSeller_TbDirOp._TbDir12Reset;

public class OpDir12Array_js extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public static String getDir12ArrayJsonStrFromDb(String dbname,String tbname)
	{
		DbLyshwSeller_TbDirOp dbls=new DbLyshwSeller_TbDirOp();
		_TbDir12Reset [] tbdr=null;
		if(dbname==null||tbname==null)
		{
			RlsMsg("dbname or tbname is null");
			return null;
		}
		if(dbname==""||tbname=="")
		{
			RlsMsg("dbname or tbname is ''");
			return null;
		}		
		tbdr=dbls.getResultSet(dbname, tbname);
		if(tbdr==null)
		{
			//RlsMsg("dbls is null");
			return null;
		}
		StringBuffer sbuf=new StringBuffer();
		//sbuf.append("window.dir12arry=new Array();\r\n");
		sbuf.append("[");
		if(tbdr.length>0)
			sbuf.append(String.format(
					"{'dir1name':'%s','dir2name':'%s',"
					+ "'dir12index':%d,'dir12id':%d,"
					+ "'dir12url':'%s','goodsnum':%d}",
					tbdr[0].dir1name,	tbdr[0].dir2name,
					tbdr[0].dir12index,tbdr[0].dir12id,
					tbdr[0].dir12url,tbdr[0].goodsnum		
					));
		for(int i=1;i<tbdr.length;i++) 
		{ 
			sbuf.append(String.format( 
					",{'dir1name':'%s','dir2name':'%s',"
					+ "'dir12index':%d,'dir12id':%d,"
					+ "'dir12url':'%s','goodsnum':%d}",
					tbdr[i].dir1name,	tbdr[i].dir2name,
					tbdr[i].dir12index,tbdr[i].dir12id,
					tbdr[i].dir12url,tbdr[i].goodsnum		
					));
/*			sbuf.append(String.format("window.dir12arry[%d]=new Array();\r\n",i));
			sbuf.append(String.format("window.dir12arry[%d]['dir1name']='%s';\r\n"
					,i,tbdr[i].dir1name));
			sbuf.append(String.format("window.dir12arry[%d]['dir2name']='%s';\r\n"
					,i,tbdr[i].dir2name));
			sbuf.append(String.format("window.dir12arry[%d]['dir12index']=%d;\r\n"
					,i,tbdr[i].dir12index));
			sbuf.append(String.format("window.dir12arry[%d]['dir12id']=%d;\r\n"
					,i,tbdr[i].dir12id));
			sbuf.append(String.format("window.dir12arry[%d]['dir12url']='%s';\r\n"
					,i,tbdr[i].dir12url));
			sbuf.append(String.format("window.dir12arry[%d]['goodsnum']=%d;\r\n"
					,i,tbdr[i].goodsnum));*/
		}
		sbuf.append("]"); 
		return sbuf.toString();
	}
	private static void RlsMsg(String msg)
	{
		MyMsg.RlsMsg("lyshw.model.Common.OpDir12Array_js.java error:"+msg);
	}
}
