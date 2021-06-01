package myJ.myEF_DbBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import MyJ_CommonLib.MyJ_DatetimeOp;

public class SqlStatementConvertor {
	public static struct_PrepareStatement getInsertSqlStatement(List<struct_ListParams> list,String tableName,enum_DbType dbType)
		throws MySqlException
	{
		String field="";
		String value="";
		struct_PrepareStatement pStatement=new struct_PrepareStatement();
		pStatement.params=new ArrayList<String>();		
		for (struct_ListParams p: list) {
			if(p.allowUpdate==false)
				continue;
			if(p.value==null)
				continue;
			field+=p.fieldName+",";
			if(p.type==enum_FieldType.isString)
			{
				value+="?,";
				pStatement.params.add(p.value.toString());
			}
			else if(p.type==enum_FieldType.isDatetime)
			{
				value+="'"+MyJ_DatetimeOp.DatetimeToStr((Date)p.value)+"',";
			}
			else
			{
				value+=convertToSqlTextValue(p, dbType)+",";
			}
		}
		if(value.length()>1)
			value=value.substring(0, value.length()-1);
		else
			throw new MySqlException("---MySqlException---:insert record has empty content");
		if(field.length()>1)
			field=field.substring(0, field.length()-1);
		else
			throw new MySqlException("---MySqlException---:insert record has empty content");
		pStatement.sql="insert into "+tableName+" ("+field+") values("+value+")";
		return pStatement;
	}
	public static struct_PrepareStatement getUpdateSqlStatement(String tableName,String where,List<struct_ListParams> list,String[] whereFields,enum_DbType dbType)
			throws MySqlException
	{
		String statement="";
		
		struct_PrepareStatement pStatement=new struct_PrepareStatement();
		pStatement.params=new ArrayList<String>();
		for (struct_ListParams p: list) {
			if(p.allowUpdate==false)
				continue;
			if(p.value==null)
				continue;
			statement+=p.fieldName+"=";
			if(p.type==enum_FieldType.isString)
			{
				statement+="?,";
				pStatement.params.add(p.value.toString());
			}
			else if(p.type==enum_FieldType.isDatetime)
			{
				statement+="'"+MyJ_DatetimeOp.DatetimeToStr((Date)p.value)+"',";
			}
			else
			{
				statement+=convertToSqlTextValue(p, dbType)+",";
			}
		}
		if(whereFields!=null)
		{
			for(String string :whereFields)
			{
				pStatement.params.add(string);
			}
		}
		if(statement.length()>3)
			statement=statement.substring(0, statement.length()-1);
		else
			throw new MySqlException("---MySqlException---:insert record has empty content");
		pStatement.sql="update "+tableName+" set "+statement+" where "+where;
		return pStatement;
	}
	public static struct_PrepareStatement getDeleteSqlStatement(String tableName,String where,String[] whereFields,enum_DbType dbType)
			throws MySqlException
	{		
		struct_PrepareStatement pStatement=new struct_PrepareStatement();
		if(whereFields!=null)
		{
			pStatement.params=new ArrayList<String>();		
			for(String string :whereFields)
			{
				pStatement.params.add(string);
			}
		}
		pStatement.sql="delete from  "+tableName+" where "+where;
		return pStatement;
	}
	static String convertToSqlTextValue(struct_ListParams p,enum_DbType dbType)
	{
		if(p.type==enum_FieldType.isDatetime)
		{
			if(dbType==enum_DbType.SqlServer)
				return "'"+MyJ_DatetimeOp.DatetimeToStr((Date)p.value)+"'";
		}		
		else if(p.type==enum_FieldType.isBoolean)
		{
			if(dbType==enum_DbType.SqlServer)
			{
				if(p.value==null||!(Boolean)p.value)
					return "0";
				else
					return "1";
			}
		}
		return p.value.toString();		
	}
}
