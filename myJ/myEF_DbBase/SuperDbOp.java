package myJ.myEF_DbBase;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//import org.apache.commons.lang3.StringUtils;

import MyJ_CommonLib.MyJ_JsonOp;
import MyJ_CommonLib_V1.MyJ_DbOp.MyJ_DbSafeSet;
import MyJ_CommonLib_V1.MyJ_DbOp.MyJ_Transcation;

public class SuperDbOp extends DbBaseClass{
	public SuperDbOp(int MultipleDbIndex)
	{
		super(MultipleDbIndex);
	}
	

	public int insert(List<struct_ListParams> list,String tableName,MyJ_Transcation transcation) throws Exception
	{
		struct_PrepareStatement ps= SqlStatementConvertor.getInsertSqlStatement(list, tableName,getDbType());
		return execNonQuery(ps.sql, ps.params,transcation);
		//m_DbOp.m_db.execCommInsert(_TableName, ps.params, null, _RST.class));	
	}
	public int update(String tableName,String where,List<struct_ListParams> list,String[] whereFields,MyJ_Transcation transcation) throws Exception
	{
		struct_PrepareStatement ps= SqlStatementConvertor.getUpdateSqlStatement(tableName,where,list, whereFields,getDbType()); 
		return execNonQuery(ps.sql, ps.params,transcation);
		//m_DbOp.m_db.execCommInsert(_TableName, ps.params, null, _RST.class));	
	}
	public int delete(String tableName,String where,String[] list,MyJ_Transcation transcation) throws Exception
	{
		struct_PrepareStatement ps= SqlStatementConvertor.getDeleteSqlStatement(tableName,where,list,getDbType()); 
		return execNonQuery(ps.sql, ps.params,transcation);
		//m_DbOp.m_db.execCommInsert(_TableName, ps.params, null, _RST.class));	
	}
	//�����������ʡ�Ե�,������Ҫ�����Ż�myefcode�еĴ���,��һ���汾���Ż���
	public MyJ_DbSafeSet select(String tableName,String where,List<String>listParams,String orderby,String[] fields)
			throws Exception
	{
		return select(tableName, where, listParams, orderby, fields,0,0);
//		String selectFields="*";
////		String geoField="Shape.STAsText() as __Shape__";
//		if(fields!=null)
//		{
//			selectFields=String.join(",", fields);			
//		}
//		String sql="select "+selectFields+" from "+tableName;
//		if(!StringUtils.isBlank(where))
//		{
//			sql+=" where "+where;
//		}
//		if(StringUtils.isNotBlank(orderby))
//		{
//			sql+=" order by "+orderby;
//		}
//		return super.execQuery(sql, listParams);//m_DbOp.m_db.execSelect(sql,listParams);	    

	}
	public MyJ_DbSafeSet select(String tableName,String where,List<String>listParams,String orderby,String[] fields,long pageBegin,long pageSize)
			throws Exception
	{
		String selectFields="*";
		String MSselectFields="*";
//		String geoField="Shape.STAsText() as __Shape__";
		if(fields!=null)
		{
			selectFields=String.join(",", fields);
			if(pageSize>0&&getDbType()==enum_DbType.SqlServer)
				MSselectFields=String.join(",", fields);
		}
		if(pageSize>0)
		{
			if(getDbType()==enum_DbType.SqlServer)
				selectFields+=",ROW_NUMBER() OVER(Order by "+orderby+" ) AS RowId";
		}
		String sql="select "+selectFields+" from "+tableName;
		if(!StringUtils.isBlank(where))
		{
			sql+=" where "+where;
		}
		if(StringUtils.isNotBlank(orderby))
		{
			if(pageSize>0&&getDbType()==enum_DbType.SqlServer)
			{}
			else
				sql+=" order by "+orderby;
		}
		if(pageSize>0)
		{			
			if(getDbType()==enum_DbType.MySql)
				sql+=" limit "+String.valueOf(pageBegin)+","+String.valueOf(pageSize);
			else if(getDbType()==enum_DbType.SqlServer)
				sql="select "+MSselectFields+" from ("+sql+") as b where RowId between "+String.valueOf(pageBegin)+" and "+String.valueOf(pageBegin+pageSize); 
		}
		System.out.println(sql);	
		return super.execQuery(sql, listParams);//m_DbOp.m_db.execSelect(sql,listParams);
	}
// ��ȡ�������Ͷ�Ӧֵ
//	-7 	BIT
//	-6 	TINYINT
//	-5 	BIGINT
//	-4 	LONGVARBINARY 
//	-3 	VARBINARY
//	-2 	BINARY
//	-1 	LONGVARCHAR
//	0 	NULL
//	1 	CHAR
//	2 	NUMERIC
//	3 	DECIMAL
//	4 	INTEGER
//	5 	SMALLINT
//	6 	FLOAT
//	7 	REAL
//	8 	DOUBLE
//	12 	VARCHAR
//	91 	DATE
//	92 	TIME
//	93 	TIMESTAMP
//	1111  	OTHER
//	public String  selectJson_MySql_Abandon(String tableName,String where,List<String>listParams,String orderby,String[] fields,long pageBegin,long pageSize)
//			throws Exception
//	{
//		MyJ_DbSafeSet safeSet= select(tableName, where, listParams, orderby, fields,pageBegin,pageSize);
//		if(safeSet==null||safeSet.rst==null)
//			return "{}";
//		StringBuilder result=new StringBuilder("");
//		Object oTmp;
//		int iTmp;
//		while(safeSet.rst.next())
//		{
//		    com.mysql.jdbc.ResultSetMetaData rsMeta=(com.mysql.jdbc.ResultSetMetaData )safeSet.rst.getMetaData();
//		    int columnCount=rsMeta.getColumnCount();
//		    result.append("{");		    
//		    for (int i=1; i<=columnCount; i++)
//		    {//�±��1��ʼ
//		        result.append("\""+rsMeta.getColumnLabel(i)+"\":");
//		        oTmp=safeSet.rst.getObject(i);
//		        int type=rsMeta.getColumnType(i);
//// 				-7 	BIT
////		    	0 	NULL
////		    	1 	CHAR
////		    	12 	VARCHAR
////		    	91 	DATE
////		    	92 	TIME
////		    	1111  	OTHER
//		        switch (type) {
//				case -7: 
//					if((Boolean)oTmp) 
//						result.append("true,");
//					else {
//						result.append("false,");
//					}
//					break;
//				case 0:
//					result.append("\"\",");
//					break;
//				case 1://char
//				case 12:
//					if(oTmp!=null) result.append("\""+oTmp.toString()+"\",");
//					else result.append("\"\",");
//					break;
//				case 91://date
//				case 92://time
//				case 93://TIMESTAMP
//					if(oTmp!=null)
//					{
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");					
//						result.append("\""+sdf.format(oTmp)+"\",");
//					}
//					else {
//						result.append("\"\",");
//					}
//					break;
//				default:
//					if(oTmp!=null) result.append(""+oTmp.toString()+",");
//					else result.append("\"\",");
//					break;
//				}	//end switch		        
//		    }//end for	
//		    iTmp=result.length();
//		    if(iTmp>1)
//				result.deleteCharAt(iTmp-1);
//		    result.append("},");
//		}//end while
//		if(result.length()>1)
//			result=result.deleteCharAt(result.length()-1);
//		return "["+result.toString()+"]";
//	}
	public String  jsonSelect(String tableName,String where,List<String>listParams,String orderby,String[] fields,long pageBegin,long pageSize)
			throws Exception
	{		
		MyJ_DbSafeSet safeSet= select(tableName, where, listParams, orderby, fields,pageBegin,pageSize);
		if(safeSet==null||safeSet.rst==null)
			return "{}";
		StringBuilder result=new StringBuilder("");
		Object oTmp;
		int iTmp;
		while(safeSet.rst.next())
		{
			java.sql.ResultSetMetaData rsMeta=safeSet.rst.getMetaData();
		    int columnCount=rsMeta.getColumnCount();
		    result.append("{");		    
		    for (int i=1; i<=columnCount; i++)
		    {//�±��1��ʼ
		        result.append("\""+rsMeta.getColumnLabel(i)+"\":");
		        oTmp=safeSet.rst.getObject(i);
		        int type=rsMeta.getColumnType(i);
// 				-7 	BIT
//		    	0 	NULL
//		    	1 	CHAR
//		    	12 	VARCHAR
//		    	91 	DATE
//		    	92 	TIME
//		    	1111  	OTHER
		        switch (type) {
				case -7:
					if(oTmp!=null&&(Boolean)oTmp) 
						result.append("true,");
					else {
						result.append("false,");
					}
					break;
				case 0:
					result.append("\"\",");
					break;
				case 1://char
				case 12:
				case -9://nvarchar(sqlserver)
					if(oTmp!=null) result.append("\""+MyJ_JsonOp.convertStandardJsonString(oTmp.toString())+"\",");
					else result.append("\"\",");
					break;
				case 91://date
				case 92://time
				case 93://TIMESTAMP
					if(oTmp!=null)
					{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");					
						result.append("\""+sdf.format(oTmp)+"\",");
					}
					else {
						result.append("\"\",");
					}
					break;
				default:
					if(oTmp!=null) result.append(""+oTmp.toString()+",");
					else result.append("\"\",");
					break;
				}	//end switch		        
		    }//end for	
		    iTmp=result.length();
		    if(iTmp>1)
				result.deleteCharAt(iTmp-1);
		    result.append("},");
		}//end while
		if(result.length()>1)
			result=result.deleteCharAt(result.length()-1);
		return "["+result.toString()+"]";
	}
}
