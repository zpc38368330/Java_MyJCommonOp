package myJ.myEF_DbBase;

public class struct_ListParams {
	public String fieldName;
	public Object value;
	public enum_FieldType type;
	public boolean allowUpdate=true;
	public struct_ListParams(String Name,Object ob,enum_FieldType en)
	{
		fieldName=Name;
		value=ob;
		type=en;
	}
	public struct_ListParams(String Name,Object ob,enum_FieldType en,boolean allowUpdt)
	{
		fieldName=Name;
		value=ob;
		type=en;
		allowUpdate=allowUpdt;
	}
}
