package MyJ_CommonLib_V1.MyJ_DbOp;

import java.sql.Connection;
import java.sql.SQLException;

public class MyJ_Transcation extends MyJ_DbSafeSet{
	protected boolean bTranscation;
	public boolean isbTranscation() {
		return bTranscation;
	}
	public void setbTranscation(boolean bTranscation) {
		this.bTranscation = bTranscation;
	}
	public MyJ_Transcation(Connection conn) throws SQLException
	{
		super();
		setbTranscation(true);
		super.conn=conn;
		super.conn.setAutoCommit(false);
	}

	public void commit() throws SQLException
	{
		setbTranscation(false);
		conn.commit();
		conn.setAutoCommit(true);
	}
	public void rollback() throws SQLException
	{
		setbTranscation(false);
		conn.rollback();
		conn.setAutoCommit(true);
	}
	@Override
	protected void finalize() {
		// TODO Auto-generated method stub
		try {
			if(bTranscation&&conn!=null)
			{
				conn.rollback();
				conn.setAutoCommit(true);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		super.finalize();
		
	}
}
