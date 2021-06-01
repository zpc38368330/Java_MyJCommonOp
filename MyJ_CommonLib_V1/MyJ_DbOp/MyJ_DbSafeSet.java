package MyJ_CommonLib_V1.MyJ_DbOp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyJ_DbSafeSet {

	public Connection conn=null;
	public Statement statement =null;
	public java.sql.PreparedStatement pst=null;
	public ResultSet rst=null;


	public void close()
	{
		finalize();
	}
	protected  void finalize()
	{
		try {
			if(rst!=null)rst.close();rst=null;
		}catch (Exception e) {}
		try {
			if(pst!=null)pst.close();pst=null;
		}catch (Exception e) {}
		try {
			if(statement!=null)statement.close();statement=null;
		}catch (Exception e) {}

		try {
			if(conn!=null) conn.close();conn=null;
		}catch (Exception e) {}
	}
}
