package MyJ_CommonLib;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import com.netflix.discovery.shared.resolver.ResolverUtils;

import net.sf.json.JSONObject;

public class MyJ_SocketOp implements Runnable{
	
	public Socket m_socket=null;
	public String m_ip="";
	public int m_port=0;
	public String m_sendMsg="";
	public void run()
	{
		//暂时只是一个简单的发送代码,过后可以加switch进行切换所需执行的操作
		try {
			SendAndReceivMsg_TCP(m_ip, m_port, m_sendMsg,false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Deprecated
	public static String SendAndReceivMsg_TCP(String ip,int port,String msg,boolean isReceiv) throws IOException, InterruptedException
	{
        Socket socket=null;
        InputStream is=null;
        OutputStream os=null;
        String result="Socket网络连接出错IP:="+ip+":"+String.valueOf(port);
        try {
           //对服务端发起连接请求
           socket=new Socket();
           socket.connect(new InetSocketAddress(ip, port));
           socket.setSoTimeout(20000);
           //给服务端发送响应信息
           os=socket.getOutputStream();
           os.write(msg.getBytes());
           
           //接受服务端消息并打印
           is=socket.getInputStream();
//         int len=is.available();//这里因为不会阻塞,socket刚获取输入流的时间len:=0,网上很多教程代码有坑.
           byte []b;
           int len=0;
           if(!isReceiv)
           {
        	   Thread.sleep(10000);//等待一下,过早的关闭socket会因为数据未处理完毕,而提前断开
        	   return "";
           }
           for(int i=0;i<20;i++)
           {
        	   len =is.available();
        	   if(len>0)
        	   {
        		   b=new byte[len];
        		   is.read(b,0,len);
        		   result=new String(b,StandardCharsets.UTF_8);
        		   System.out.println(result);
        		   return new String(result);
        	   }
        	   Thread.sleep(1000);
           }           
//           is.read(b);
//           if(len>0)
//           {
//        	   is.read(b,0,len);
//        	   result=new String(b);
//           }
           System.out.println(result);
           return "发送命令出错,未接收到任何数据" ;

       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } 
       finally {
    	   if(socket!=null)
    		   socket.close();
    	   if(is!=null)
    		   is.close();
    	   if(os!=null)
    		   os.close();
       }  
       return result;
	}
	public static MyResult SendAndReceivResult_TCP(String ip,int port,String msg,boolean isReceiv) throws IOException, InterruptedException
	{
        Socket socket=null;
        InputStream is=null;
        OutputStream os=null;
        MyResult result=new MyResult();
        result.errcode=100;
        result.Msg="Socket网络连接出错IP:="+ip+":"+String.valueOf(port);
        result.bSuccess=false;
        try {
           //对服务端发起连接请求
           socket=new Socket();
           socket.connect(new InetSocketAddress(ip, port));
           socket.setSoTimeout(30000);
           //给服务端发送响应信息
           os=socket.getOutputStream();
           os.write(msg.getBytes());
           
           //接受服务端消息并打印
           is=socket.getInputStream();
//         int len=is.available();//这里因为不会阻塞,socket刚获取输入流的时间len:=0,网上很多教程代码有坑.
           byte []b;
           int len=0;
           if(!isReceiv)
           {
        	   Thread.sleep(10000);//等待一下,过早的关闭socket会因为数据未处理完毕,而提前断开
        	   result.bSuccess=true;
        	   result.Msg="数据已发送.";
        	   result.errcode=0;
        	   return result;
           }
           for(int i=0;i<30;i++)
           {
        	   len =is.available();
        	   if(len>0)
        	   {
        		   b=new byte[len];
        		   is.read(b,0,len);
        		   result.Msg=new String(b,StandardCharsets.UTF_8);
        		   result.bSuccess=true;
        		   result.errcode=0;
        		   if(MyJ_JsonOp.isJsonObject(result.Msg))
        		   {
        			   JSONObject jo=JSONObject.fromObject(result.Msg);
        			   if(jo.has("success"))
        			   {
        				   result.bSuccess=jo.getBoolean("success");        				   
        				   result.Msg=jo.getString("msg");
        				   result.errcode=jo.getInt("code");
        			   }
        		   }        		   
        		   //System.out.println(result);
        		   return result;
        	   }
        	   Thread.sleep(1000);
           }           
//           is.read(b);
//           if(len>0)
//           {
//        	   is.read(b,0,len);
//        	   result=new String(b);
//           }
           System.out.println(result.Msg);
           result.bSuccess=false;
           result.Msg="发送命令出错,未接收到任何数据" ;
           return result;

       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } 
       finally {
    	   if(socket!=null)
    		   socket.close();
    	   if(is!=null)
    		   is.close();
    	   if(os!=null)
    		   os.close();
       }  
       return result;
	}
	public static String SendAndReceivMsg_TCP(String ip,int port,String msg) throws IOException, InterruptedException
	{
		return SendAndReceivMsg_TCP(ip, port, msg,true);
	}
	public static void SendMsg_OnlySend_TCP(String ip,int port,String msg) throws IOException, InterruptedException
	{
		MyJ_SocketOp socketOp=new MyJ_SocketOp();
		socketOp.m_ip=ip;
		socketOp.m_port=port;
		socketOp.m_sendMsg=msg;
		Thread thread=new Thread(socketOp);
		thread.start();
	}
}
