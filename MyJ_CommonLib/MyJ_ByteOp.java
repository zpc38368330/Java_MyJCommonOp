package MyJ_CommonLib;

public class MyJ_ByteOp{
	/*
	 * 转换成真是的byte[]，即低位数在高位地址，高位数在地位的内存格式
	 */
	public static byte[] convertIntToRealMemoryByteArr(int num) {
		byte[] targets = new byte[4];
        targets[0] = (byte) (num & 0xFF);
        targets[1] = (byte) (num >> 8 & 0xFF);
        targets[2] = (byte) (num >> 16 & 0xFF);
        targets[3] = (byte) (num >> 24 & 0xFF);
        return targets;
	}
}