package MyJ_CommonLib_V1.MyJ_DbOp;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import MyJ_CommonLib.MyJ_StringOp;

/**
 * need  jdbc package whitch must corresponding SqlServer or Oracle or Mysql. 
 *
 */

public class MyJ_BaseDbOp { 
	Connection  m_conn=null;	
//	public static  MyJ_EnumDbType dbType=MyJ_EnumDbType.Mysql;
//	public static String DbUrl = "jdbc:mysql://localhost:3306/vipnet_db";  
	public static  MyJ_EnumDbType dbType;
	public static String DbUrl = "";  
    public static String UserName = "";  
    public static String Password = "";  
    public static String dbdriver="";
	public MyJ_BaseDbOp()throws Exception
	{
		Class.forName(dbdriver); 
	}
	public MyJ_BaseDbOp(MyJ_EnumDbType em,String dburl,String username,String password,String DbDrive)
		throws Exception
	{
		dbType=em;DbUrl=dburl;UserName=username;Password=password;dbdriver=DbDrive;
		Class.forName(dbdriver); 
	}
	public MyJ_DbSafeSet execSelect(String sql,List<String> params) throws Exception
	{
		if(params==null)
		{
			return execQuery(sql);
		}
		return execPreparedQuery(sql,params);
	}
	public int execCommInsert(String tableName,Object o,String [] NullField,String classname,MyJ_Transcation transcation)throws Exception
	{
		int result=0;
		if(o instanceof List)
		{
			for (Object one :(List<?>)o) {
				result+=_execCommInsert(tableName,one, NullField,classname,transcation);
			}
		}
		else
			result+=_execCommInsert(tableName,o, NullField,classname,transcation);
		return result;
	}
	private int _execCommInsert(String tableName,Object o,String [] NullField,String className,MyJ_Transcation transcation) 
			throws Exception
	{
		//className="_db_test$class_Results";
        Class<?> clazz = Class.forName(className);
        Field[] fields = clazz.getFields();
        SqlStatementKeyVal kv;
        String sqlfields="",sqlvalues="";
        List<String> sqlprepares=new ArrayList<String>();
        
        for( Field field : fields ){
        	kv=getKeyVal(field, o);
        	if(kv==null)
        		continue;
        	if(MyJ_StringOp.ChkStrArrayContains(NullField,kv.key))
        		continue;
        	sqlfields+=kv.key+",";
        	if(kv.prepared!=null&&kv.prepared.equals("?"))
        	{
        		sqlvalues+="?,";
        		sqlprepares.add(kv.val);
        	}
        	else
        	{
        		sqlvalues+=kv.val+",";        		
        	}
        }
        if(sqlfields.length()<=1||sqlvalues.length()<=1)
        	throw new Exception("error: _execCommInsert(): 0 param in insert pretement of sql.");
        sqlfields=sqlfields.substring(0, sqlfields.length()-1);
        sqlvalues=sqlvalues.substring(0, sqlvalues.length()-1);
        String sql="insert into "+tableName+" ("+sqlfields+") values("+sqlvalues+")";

		return execPreparedNonQuery(sql, sqlprepares,transcation);
		
	}
	private int  execPreparedNonQuery(String sql,List<String> values,MyJ_Transcation transcation)throws Exception
	{
		if(transcation==null)
		{
			MyJ_DbSafeSet dss=new MyJ_DbSafeSet();
	        dss.conn=getMySqlConnection();
			dss.pst=dss.conn.prepareStatement(sql);
			for(int i=1;i<=values.size();i++)
			{
				dss.pst.setString(i,values.get(i-1));
			}
			int count= dss.pst.executeUpdate();
			dss.close();
			return count;
		}
		transcation.pst=transcation.conn.prepareStatement(sql);
		for(int i=1;i<=values.size();i++)
		{
			transcation.pst.setString(i,values.get(i-1));
		}
		int count= transcation.pst.executeUpdate();
		return count;
		
	}	
	private MyJ_DbSafeSet execPreparedQuery(String sql,List<String> values)throws Exception
	{
		MyJ_DbSafeSet dss=new MyJ_DbSafeSet();
        dss.conn=getMySqlConnection();
		dss.pst=dss.conn.prepareStatement(sql);
		for(int i=1;i<=values.size();i++)
		{
			dss.pst.setString(i,values.get(i-1));
		}
		dss.rst=dss.pst.executeQuery();
		return dss;
	}
	protected int execNonQuery(String sql,MyJ_Transcation transcation) throws Exception
	{
		if(transcation==null)
		{
			MyJ_DbSafeSet dss=new MyJ_DbSafeSet();
	    	dss.conn=getMySqlConnection();
	    	dss.statement= dss.conn.createStatement();  
	    	int count= dss.statement.executeUpdate(sql);
	    	dss.close();    	
	    	return count;
		}
		transcation.statement= transcation.conn.createStatement();  
    	int count= transcation.statement.executeUpdate(sql);
    	return count;
	}
	protected MyJ_DbSafeSet execQuery(String sql) throws Exception
	{
		MyJ_DbSafeSet dss=new MyJ_DbSafeSet();
    	dss.conn=getMySqlConnection();
    	dss.statement = dss.conn.createStatement();  
    	dss.rst=dss.statement.executeQuery(sql);
    	return dss;
	}
	private Connection getMySqlConnection() throws Exception
	{
		return DriverManager.getConnection(DbUrl, UserName,Password);
	}
	private SqlStatementKeyVal getKeyVal(Field f,Object obj) throws Exception
	{
		if(f==null||f.get(obj)==null)
			return null;
		SqlStatementKeyVal kv=new SqlStatementKeyVal();
		kv.key=f.getName();
		String type=f.getType().getSimpleName();
		if(type.equalsIgnoreCase("string"))
		{
			//kv.val="'"+f.toString()+"'";
			Object o=f.get(obj);
			kv.val=o.toString();
			kv.prepared="?";
		}		
		else
		{						
			Object o=f.get(obj);
			kv.val=o.toString();
		}
		return kv;
	}
	private class SqlStatementKeyVal
	{
		String key;
		String val;
		String prepared;
	}
}

