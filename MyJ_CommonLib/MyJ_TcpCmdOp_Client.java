package MyJ_CommonLib;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import net.sf.json.JSONObject;

/*
 * 此类在招远项目中第一次使用，使用的交互协议与c# 和python一样
 */
public class MyJ_TcpCmdOp_Client{
	
	Socket m_sock=null;
	public MyJ_ReqResult connect(String ip,int port)
	{
        //对服务端发起连接请求
		try {
		m_sock=new Socket();
		m_sock.connect(new InetSocketAddress(ip, port));
        m_sock.setSoTimeout(20000);
        return new MyJ_ReqResult(0, "IP:"+ip+" PORT:"+port+"连接成功", "");
		}catch (Exception e) {
			System.out.println(e.getMessage()+"\r\n"+e.getStackTrace());		
		}
		return new MyJ_ReqResult(100, "IP:"+ip+" PORT:"+port+"连接错误", "");
	}
	
	/*
	 * json格式：{"op":"alarm","type":"alarm","itemname":"","val":"' + alarmMsg.replace("'",'"') + '"}
	 */
	public  MyJ_ReqResult cli_getBigData(String jsonMsg) {
		//给服务端发送响应信息
		MyJ_ReqResult result=new MyJ_ReqResult(500,"服务器接收错误","");
		OutputStream os=null;
		InputStream is=null;
		try {
			
	        os=m_sock.getOutputStream();
	        os.write(jsonMsg.getBytes());
	        //接收数据大小等信息
	        byte[] rec=cli_receive(is);
	        
	        if(rec==null)
	        	return result;
	        String strBuf=new String(rec,StandardCharsets.UTF_8);
	        JSONObject jo=JSONObject.fromObject(strBuf);
	        int iTmp=jo.getInt("success");
	        if(iTmp!=0)
	        {
	        	result.setMessage(jo.getString("msg"));
	        	return result;
	        }
	        int rcvCount=jo.getInt("packageCount");
	        int rcvSize=jo.getInt("allSize");
	        byte [] sendbuf=new byte[5];
	        sendbuf[0]=0;
	        int index=0;
	        System.arraycopy(MyJ_ByteOp.convertIntToRealMemoryByteArr(index), 0, sendbuf, 1, 4);
	        //发送5个字节，第一字节为0，表示成功，后四个字节是序号
	        os.write(sendbuf);
	        byte[] allbuf=new byte[rcvSize];
	        int curlen=0;
	        for(int i=0;i<rcvCount;i++) {
	        	byte[] b=cli_receive(is);
	        	if(b==null)
	        	{
	        		result.setData(b);
	        		result.setCode(0);
	        		result.setMessage("");
	        		return result;
	        	}
	        	if(b[0]!=0)
	        	{
	        		result.setMessage("大数据接收到一半出现错误");
	        		System.out.println("ERROR:大数据接收到一半出现错误");
	        		return result;
	        	}
	        	System.arraycopy(b, 5, allbuf,curlen , b.length-5);
	        	//String tmp=MyJ_StringOp.convertByteToString(allbuf);
	        	curlen+=b.length-5;
	        	System.arraycopy(MyJ_ByteOp.convertIntToRealMemoryByteArr(i+1), 0, sendbuf, 1, 4);
	        	os.write(sendbuf);
	        }
	        result=new MyJ_ReqResult(0,"接收完毕.","");
	        result.tcpByteData=allbuf;
	        return result;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage()+"\r\n"+e.getStackTrace());
		}
		finally {
			try {
				if(os!=null)
					os.close();
				if(is!=null)
					is.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}			
		}
		return result;
	}
	//json格式：{"op":"alarm","type":"alarm","itemname":"","val":"' + alarmMsg.replace("'",'"') + '"}
	public  MyJ_ReqResult cli_SendCmdAndWaitE5(String jsonMsg) {
		//给服务端发送响应信息
		OutputStream os=null;
		InputStream is=null;
		try {
			
	        os=m_sock.getOutputStream();
	        os.write(jsonMsg.getBytes());
	        //接收数据大小等信息
	        byte[] rec=cli_receive(is);
	        
	        if(rec==null)
	        	return new MyJ_ReqResult(500,"服务器接收错误","");
	      //java没有byte转无符号，。。。0xE5=-27
	        int keng=rec[0]&0xff;
	        if(keng==0xe5)
	        	return new MyJ_ReqResult(0,"服务器返回成功","");	    
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage()+"\r\n"+e.getStackTrace());
		}
		finally {
			try {
				if(os!=null)
					os.close();
				if(is!=null)
					is.close();
			}
			catch (Exception e) {
				// TODO: handle exception
			}			
		}
		return new MyJ_ReqResult(500,"服务器接收错误。Has exception","");
	}
	public byte[] cli_receive(InputStream is) {
		
		try {
			byte []b;
	        int len=0;
	        is=m_sock.getInputStream();	    
	        for(int i=0;i<120;i++)
	        {
	     	   len =is.available();
	     	   if(len>0)
	     	   {
	     		   b=new byte[len];
	     		   is.read(b,0,len);
	     		   return b;
	     		   //String result=new String(b,StandardCharsets.UTF_8);
	     	   }
	     	   Thread.sleep(1000);
	        } 
		}catch (Exception e) {
			System.out.println(e.getMessage()+"\r\n"+e.getStackTrace());
		}
		finally {
//			try {
//				if(is!=null)
//					is.close();//is close 会关闭socket链接
//			}catch (Exception e) {
//				// TODO: handle exception
//			}
		}
		
		return null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		if(m_sock!=null)
		{
			m_sock.close();
			m_sock=null;
		}
	}
	
}