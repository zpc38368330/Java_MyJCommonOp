package MyJ_CommonLib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.naming.Context;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import lyshw.Control.Init.DispatchInit;
import lyshw.model.Db.DbSellUser._Param;

import org.apache.commons.fileupload.FileItem;

public class OpFile extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	 
	/**
	 * @param  context ServletContext
	 * @param  p _Param，DbSellUser 内的结构体
	 * @return ResultMsg 成功：创建的目录在ResultMsg.getMsg()返回的str内,失败ResultMsg.getMsg=失败信息
	 * @see ResultMsg
	 */
	public static ResultMsg MkSellerdir(ServletContext context,_Param p)
	{			
			String tempFilePath=String.format("/seller/%03d/%03d/%03d",
				  p.iCityId,p.iStreetId,p.iSellerId);		 
		  tempFilePath=context.getRealPath(tempFilePath);
		  ResultMsg rMsg=new ResultMsg(true,tempFilePath);
		  File file = new File(tempFilePath);
		  if(!file.exists())
		  {
			 	if(!file.mkdirs())
			 	{
			 		rMsg.setErr("创建目录失败:"+tempFilePath);
			 		return rMsg;
			 	}
		  }
		  return rMsg;
	}
	public static ResultMsg Mkdir(ServletContext context,String webdir)
	{			
			 
		String RealDir=context.getRealPath(webdir);
	  ResultMsg rMsg=new ResultMsg(true,RealDir);
	  File file = new File(RealDir);
	  if(!file.exists())
	  {
		 	if(!file.mkdirs())
		 	{
		 		rMsg.setErr("创建目录失败:"+RealDir);
		 	}
	  }
	  return rMsg;
	}
	/**注意：这里ResultMsg仅在catch里设置了错误信息，第一次使用在SellerJsonToDb中使用，如有修改需参照SellerJsonToDb
	 * @param AbsolutDir
	 * @param filename
	 * @param cmd "i"如果不存在则创建，“a”追加，“c”create new file；
	 * <br/>暂时只存在ia和ic的选项，如果以后有需要再加
	 * @return
	 */
	public static ResultMsg WriteFile_Powerful(String AbsolutDir,String filename,String data,String cmd)
	{
		ResultMsg msg=new ResultMsg(true,"WriteFile ok");		
		File path=new File(AbsolutDir);
		if(!path.exists()){
			if(!path.mkdirs()){
		 		msg.setErr("创建目录失败:"+AbsolutDir);
		 		return msg;
		 	}
		}
		AbsolutDir.replace('\\', '/');
		if(AbsolutDir.indexOf('/')!=(AbsolutDir.length()-1))
			AbsolutDir+="/";
		File file=new File(AbsolutDir+filename);
		try{
			if(!file.exists())
				file.createNewFile();
			FileWriter fw;
			switch (cmd)
			{
				case "ia":
					fw=new FileWriter(file,true);
					break;
				case "ic":
					fw=new FileWriter(file);
					break;
				default:
					fw=new FileWriter(file,true);
				break;
			}
			fw.write(data);
			fw.flush();
			fw.close();
		}catch(Exception e)
		{
			RlsMsg("WriteFile() error:"+e.toString());
			msg.setErr("WriteFile() error:"+e.toString());
		}
		return msg;
	}
	public static ResultMsg DelDir(String sDir)
	  {			
		 	ResultMsg rMsg=new ResultMsg();
			boolean brst=(new File(sDir)).delete(); 
			if(brst)
				rMsg.setOk("删除目录成功:"+sDir);
			else {
				rMsg.setErr("删除目录失败:"+sDir+" //可能不为空目录,或无此目录");
			}
		   return rMsg;
	  }
	/**
	 * @param str 写入的字符串
	 * @param descfilename 目标文件名
	 * @return boolean
	 */
	public static boolean writefile(String str,String descfilename)
	{
		try {
				File file = new File(descfilename);

			   // if file doesnt exists, then create it
			   if (!file.exists()) {
			    file.createNewFile();
			   }

			   FileWriter fw = new FileWriter(file.getAbsoluteFile());
			   BufferedWriter bw = new BufferedWriter(fw);
			   bw.write(str);
			   bw.close();
			  } catch (IOException e) {
				  RlsMsg("writefile() error:"+descfilename+"\r\n"+str);
				  return false;
			  }	
		return true;
	}
	/**
	 * @param item
	 * @param filepath
	 * @param filename 如不指定为 "" 空，则输出文件名为指定的字符串，跟bRandomFileName=true有冲突
	 * @param bRandomFileName 如为true则最后输出文件名为随机文件名
	 * @return
	 */
	public static ResultMsg writeFileFromFileItem(FileItem item,String filepath,String sFilename,boolean bRandomFileName)
	{
		ResultMsg rMsg=new ResultMsg(true,"写入文件成功");
		if((sFilename==null||sFilename=="")&&bRandomFileName==false){
			rMsg.setErr("文件名和设置是否取随机名布尔变量不能同时为空和假");
			return rMsg;
		}		
		String filename="";
		if(sFilename==null||"".equals(sFilename))
			filename=item.getName();
		else 
			filename=sFilename;
		int index=filename.lastIndexOf("//");
		filename=filename.substring(index+1,filename.length());
		long fileSize=item.getSize();
		
		if(filepath.equals("") && fileSize==0){
			rMsg.setErr("文件长度为0，或文件名错误");
			return rMsg;
		}		
		if(bRandomFileName)
		{
			for(int i=0;i<1000;i++){			
				filename=String.valueOf((long)(Math.random()*100000000))+filename.substring(filename.lastIndexOf("."));
				File file=new File(filepath+filename);
				if(!file.exists())
					break;
			}
		}
		rMsg.setOk(filename);
		File uploadedFile = new File(filepath+filename);
		try{
			item.write(uploadedFile);
		}catch (Exception e)
		{
			rMsg.setErr("OpFile.java:\r\n"+e.toString());
			return rMsg;
		}
		return rMsg;
	}
	public static void RlsMsg(String msg)
	{
		MyMsg.RlsMsg("OpFile.java:\r\n"+msg);
	}

	public static void makeUrlToHtmFile(
			HttpServletRequest request, HttpServletResponse response
			,String url,String fileAbsoluteName)
			throws ServletException, IOException
	{

		ServletContext sc = DispatchInit.st_context;//getServletContext();

		//String file_name = request.getParameter("file_name");// 你要访问的jsp文件,如index.jsp
		// 则你访问这个servlet时加参数.如http://localhost/toHtml?file_name=index

		//url = "/" + file_name + ".jsp";// 这是你要生成HTML的jsp文件,如
										// http://localhost/index.jsp的执行结果.

		//fileAbsoluteName = "/home/resin/resin-2.1.6/doc/" + file_name + ".htm";// 这是生成的html文件名,如index.htm.

		RequestDispatcher rd = sc.getRequestDispatcher(url);
		final ByteArrayOutputStream os = new ByteArrayOutputStream();

		final ServletOutputStream stream = new ServletOutputStream()
		{
			public void write(byte[] data, int offset, int length)
			{
				os.write(data, offset, length);
			}

			public void write(int b) throws IOException
			{
				os.write(b);
			}
		};

		final PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));

		HttpServletResponse rep = new HttpServletResponseWrapper(response)
		{
			public ServletOutputStream getOutputStream()
			{
				return stream;
			}

			public PrintWriter getWriter()
			{
				return pw;
			}
		};
		rd.include(request, rep);
		pw.flush();
		FileOutputStream fos = new FileOutputStream(fileAbsoluteName); // 把jsp输出的内容写到xxx.htm
		os.writeTo(fos);
		fos.close();
//		PrintWriter out = response.getWriter();
//		out.print("<p align=center><font size=3 color=red>首页已经成功生成！Andrew</font></p>");
	}
}
