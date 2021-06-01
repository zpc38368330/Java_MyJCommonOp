package MyJ_CommonLib;

import java.math.BigDecimal;

public class MyJ_DoubleFloatOp {
	/*
	 * 小数点精确到 scale 位
	 */
	public static double convertDouble(double val,int scale)
	{
		BigDecimal bigDecimal=new BigDecimal(val);
		return bigDecimal.setScale(scale).doubleValue();
	}
	/*
	 * 小数点精确到 scale 位
	 */
	public static double convertFloat(float val,int scale)
	{
		BigDecimal bigDecimal=new BigDecimal(val);
		return bigDecimal.setScale(scale).floatValue();
	}
}
