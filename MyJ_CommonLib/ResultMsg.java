package MyJ_CommonLib;

/**
 * @author zpc<br/>
 * @灞炴��
 * private boolean bisok;杩斿洖true锛宖alse <br/>
*	private String strRst; 杩斿洖 鎴愬姛澶辫触淇℃伅
*
 */
public class ResultMsg  
{
	private boolean bisok;
	private String strRst; 
	public ResultMsg()
	{
		this.bisok=false;
		this.strRst="";
	}
	public ResultMsg(boolean isok,String msg)
	{
		this.bisok=isok;
		this.strRst=msg;
	}
	public  void setErr(String str)
	{
		this.bisok=false;
		this.strRst=str;
	}
	public void setOk(String str)
	{
		bisok=true;
		this.strRst=str;
	}
	public boolean IsOk()
	{
		return bisok;
	}
	/**
	 * @return
	 * 鎴愬姛涓庡け璐ュ叏杩斿洖鑷埗鐨勬湁鐢ㄤ俊鎭紝鍏蜂綋鍙傝�冭皟鐢ㄦ绫诲嚱鏁扮殑杩斿洖璇存槑
	 */
	public String getMsg()
	{
		return this.strRst;
	}
}
