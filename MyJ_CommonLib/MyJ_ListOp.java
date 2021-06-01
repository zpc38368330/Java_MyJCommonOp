package MyJ_CommonLib;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MyJ_ListOp {
	public static  <T> List<T> getLinq(List<T> obj,String whereParamName,String whereCompareSign,Object whereValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		if(obj==null)
			return null;
		if(obj.get(0)==null)
			return null;
		Field field;
		@SuppressWarnings("rawtypes") 
		Class class1;
		boolean bIsok;
		List<T> result=new ArrayList<T>();
		for (T t : obj) {
			bIsok=false;
			if(whereCompareSign=="==")
			{
				class1=t.getClass();				
				field=class1.getDeclaredField(whereParamName);
				field.setAccessible(true);
				if(field.get(t).equals(whereValue))
					bIsok=true;
			}
			
			if(bIsok)
			{
				result.add(t);
			}
			
			bIsok=false;
		}
		
		return result;
	}
}
