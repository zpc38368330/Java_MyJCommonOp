package MyJ_CommonLib_V1.MyJ_DbOp;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class MyJ_DbOp {
	public static String [] getColumns(ResultSet rs) throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		int count=rsmd.getColumnCount();
		String[] name=new String[count];
		for(int i=0;i<count;i++)
			name[i]=rsmd.getColumnName(i+1); 
		return name;
	}
}
