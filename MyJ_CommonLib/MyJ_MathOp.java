package MyJ_CommonLib;

import java.text.DecimalFormat;

public class MyJ_MathOp{
	/*
	 * count 小数点后精确到几位
	 */
	public static String round(double val,int count) {
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(val);
	}
}