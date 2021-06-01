
package myJ.myEF_DbBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import MyJ_CommonLib_V1.MyJ_DbOp.MyJ_DbSafeSet;
import MyJ_CommonLib_V1.MyJ_DbOp.MyJ_Transcation;

/*初始化数据库,下面的代码仅初始化了索引为0的数据库,及各表实例中的super(0);
        struct_DbParams dbParams=new struct_DbParams();
		String dburl = getServletContext().getInitParameter("dburl0");
		String username = getServletContext().getInitParameter("username0");  
		String password = getServletContext().getInitParameter("password0");
		String dbdrive = getServletContext().getInitParameter("driver0");
		dbParams.dbType=enum_DbType.valueOf(getServletContext().getInitParameter("dbtype0"));
        dbParams.DbUrl=dburl;
        dbParams.UserName=username;
        dbParams.Password=password;
        dbParams.dbdriver=dbdrive;
        DbEF_Params.list_dbParmas.add(dbParams);
 */
public class DbBaseClass {
	public static List<DbBaseClass> s_db=new ArrayList<DbBaseClass>();
	public DbBaseClass m_db;
	public static void addDbParmas(struct_DbParams dbParams)
			throws Exception
		{			
			s_db.add(new DbBaseClass(dbParams));
		}
	Connection  m_conn=null;	
	struct_DbParams m_DbParams;
	private DbBaseClass(struct_DbParams dbParams) throws ClassNotFoundException {
		Class.forName(dbParams.dbdriver);
		this.m_DbParams=dbParams;
		// TODO Auto-generated constructor stub
	}
	public DbBaseClass(int MultipleDbIndex) 
	{
		if(DbEF_Params.list_dbParmas.size()<=0)
		{
			String dburl = "jdbc:sqlserver://localhost:1433;DatabaseName=GIS_TEST";		
			String username = "sa";  
			String password = "sql51011";
			String dbdrive = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			struct_DbParams dbParams=new struct_DbParams();
			dbParams.DbUrl=dburl;
			dbParams.dbType=enum_DbType.SqlServer;
			dbParams.dbdriver=dbdrive;
			dbParams.Password=password;
			dbParams.UserName=username;
			
			try {
				Class.forName(dbParams.dbdriver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.m_DbParams=dbParams;
		}
		else
		{
			try {
				Class.forName(DbEF_Params.list_dbParmas.get(MultipleDbIndex).dbdriver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.m_DbParams=DbEF_Params.list_dbParmas.get(MultipleDbIndex);
		}
		//m_db=s_db.get(MultipleDbIndex);
	}
	public enum_DbType getDbType()
	{
		return m_DbParams.dbType;
	}
//	public MyJ_EnumDbType dbType;
//	public String DbUrl = "";  
//    public String UserName = "";  
//    public String Password = "";  
//    public String dbdriver="";
//	public static  MyJ_EnumDbType dbType=MyJ_EnumDbType.Mysql;
//	public static String DbUrl = "jdbc:mysql://localhost:3306/vipnet_db";  

	public MyJ_DbSafeSet execQuery(String sql,List<String> params) throws Exception
	{
		if(params==null)
		{
			return execQuery(sql);
		}
		return execPreparedQuery(sql,params);
	}
	public int execNonQuery(String sql,List<String>params,MyJ_Transcation transcation) throws Exception
	{
		if(params!=null)
			return execPreparedNonQuery(sql, params,transcation);
		return execNonQuery(sql,transcation);
	}
//	public int execCommInsert(String tableName,Object o,String [] NullField,String classname)throws Exception
//	{
//		int result=0;
//		if(o instanceof List)
//		{
//			for (Object one :(List<?>)o) {
//				result+=_execCommInsert(tableName,one, NullField,classname);
//			}
//		}
//		else
//			result+=_execCommInsert(tableName,o, NullField,classname);
//		return result;
//	}
//	private int _execCommInsert(String tableName,Object o,String [] NullField,String className) 
//			throws Exception
//	{
//		//className="_db_test$class_Results";
//        Class<?> clazz = Class.forName(className);
//        Field[] fields = clazz.getFields();
//        SqlStatementKeyVal kv;
//        String sqlfields="",sqlvalues="";
//        List<String> sqlprepares=new ArrayList<>();
//        
//        for( Field field : fields ){
//        	kv=getKeyVal(field, o);
//        	if(kv==null)
//        		continue;
//        	if(MyJ_StringOp.ChkStrArrayContains(NullField,kv.key))
//        		continue;
//        	sqlfields+=kv.key+",";
//        	if(kv.prepared!=null&&kv.prepared.equals("?"))
//        	{
//        		sqlvalues+="?,";
//        		sqlprepares.add(kv.val);
//        	}
//        	else
//        	{
//        		sqlvalues+=kv.val+",";        		
//        	}
//        }
//        if(sqlfields.length()<=1||sqlvalues.length()<=1)
//        	throw new Exception("error: _execCommInsert(): 0 param in insert pretement of sql.");
//        sqlfields=sqlfields.substring(0, sqlfields.length()-1);
//        sqlvalues=sqlvalues.substring(0, sqlvalues.length()-1);
//        String sql="insert into "+tableName+" ("+sqlfields+") values("+sqlvalues+")";
//
//		return execPreparedNonQuery(sql, sqlprepares);
//		
//	}
	private int  execPreparedNonQuery(String sql,List<String> values,MyJ_Transcation transcation)throws Exception
	{
		MyJ_DbSafeSet dss;
		if(transcation==null)
		{
			dss=new MyJ_DbSafeSet();
			dss.conn=getMySqlConnection();
		}
		else
		{
			dss=(MyJ_DbSafeSet)transcation;
		}
		dss.pst=dss.conn.prepareStatement(sql);
		for(int i=1;i<=values.size();i++)
		{
			dss.pst.setString(i,values.get(i-1));
		}
		int count= dss.pst.executeUpdate();
		if(transcation==null)
			dss.close();
		return count;
	}	
	public MyJ_Transcation getTranscation_NeedLocalTry() throws Exception
	{
		MyJ_Transcation transcation= new MyJ_Transcation(getSqlConnection());
		return transcation;
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
	public int execNonQuery(String sql,MyJ_Transcation transcation) throws Exception
	{
		MyJ_DbSafeSet dss;
		if(transcation==null)
		{
			dss=new MyJ_DbSafeSet();
			dss.conn=getMySqlConnection();
		}
		else
		{
			dss=transcation;		
		}
    	dss.statement= dss.conn.createStatement();  
    	int count= dss.statement.executeUpdate(sql);
    	if(transcation==null)
    		dss.close();
    	return count;
	}
	public MyJ_DbSafeSet execQuery(String sql) throws Exception
	{
		MyJ_DbSafeSet dss=new MyJ_DbSafeSet();
    	dss.conn=getMySqlConnection();
    	dss.statement = dss.conn.createStatement();  
    	dss.rst=dss.statement.executeQuery(sql);
    	return dss;
	}
	private Connection getMySqlConnection() throws Exception
	{
		return DriverManager.getConnection(m_DbParams.DbUrl, m_DbParams.UserName,m_DbParams.Password);
	}
	public Connection getSqlConnection() throws Exception
	{
		return DriverManager.getConnection(m_DbParams.DbUrl, m_DbParams.UserName,m_DbParams.Password);
	}
//	private SqlStatementKeyVal getKeyVal(Field f,Object obj) throws Exception
//	{
//		if(f==null||f.get(obj)==null)
//			return null;
//		SqlStatementKeyVal kv=new SqlStatementKeyVal();
//		kv.key=f.getName();
//		String type=f.getType().getSimpleName();
//		if(type.equalsIgnoreCase("string"))
//		{
//			//kv.val="'"+f.toString()+"'";
//			Object o=f.get(obj);
//			kv.val=o.toString();
//			kv.prepared="?";
//		}		
//		else
//		{						
//			Object o=f.get(obj);
//			kv.val=o.toString();
//		}
//		return kv;
//	}
//	private class SqlStatementKeyVal
//	{
//		String key;
//		String val;
//		String prepared;
//	}
}
