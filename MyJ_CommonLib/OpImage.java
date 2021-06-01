package MyJ_CommonLib;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


/**
 * * @author WQ * @date 2011-01-14 * @versions 1.0 ͼƬѹ�������� �ṩ�ķ����п����趨���ɵ�
 * ����ͼƬ�Ĵ�С�ߴ��
 */
public class OpImage
{
	private static int DaTuWidth=400;
	private static int DaTuHeight=400;
	private static int ZhongTuWidth=216;//��editgoods.css�ж�Ӧ��CTRL-F���Բ�
	private static int ZhongTuHeight=216;
	/** * ͼƬ�ļ���ȡ * * @param srcImgPath * @return */
	private static BufferedImage InputImage(String srcImgPath)
	{
		BufferedImage srcImage = null;
		try
		{
			FileInputStream in = new FileInputStream(srcImgPath);
			srcImage = javax.imageio.ImageIO.read(in);
		} catch (IOException e)
		{
			System.out.println("��ȡͼƬ�ļ�����" + e.getMessage());
			e.printStackTrace();
		}
		return srcImage;
	}
	/**
	 * ��img_xt.jpg ,�滻Ϊimg_dt.jpg
	 * @param fname
	 * @return
	 */
	public static String getDatuNameFromXiaoTu(String fname)
	{
		return fname.replace("_xt", "_dt");
	}
	/**
	 * ��ͼ��ʽΪ��img_xt.jpg�滻Ϊimg_zt.jpg
	 * @param fname
	 * @return
	 */
	public static String getZhongTuNameFromXiaoTu(String fname)
	{
		return fname.replace("_xt", "_zt");
	}
	public static String getXiaoTuName(String fname)
	{
		int index=fname.lastIndexOf(".");
		String ext=fname.substring(index);
		String name=fname.substring(0,index);
		return name+"_xt"+ext;
	}
	/**
	 * * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ�� * * @param srcImgPath :ԴͼƬ·�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param new_w * :ѹ�����ͼƬ�� * @param new_h * :ѹ�����ͼƬ��
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int new_w, int new_h)
	{
		BufferedImage src = InputImage(srcImgPath);
		disposeImage(src, outImgPath, new_w, new_h);
	}
	/**
	 * * ��ͼƬ����ָ����ͼƬ�ߴ�ѹ�� * * @param BufferedImage :ԴͼƬ�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param new_w * :ѹ�����ͼƬ�� * @param new_h * :ѹ�����ͼƬ��
	 */
	public static void compressImage(BufferedImage src, String outImgPath,
			int new_w, int new_h)
	{
		disposeImage(src, outImgPath, new_w, new_h);
	}
	/**
	 * * ָ�������߿�����ֵ��ѹ��ͼƬ * * @param srcImgPath * :ԴͼƬ·�� * @param outImgPath *
	 * :�����ѹ��ͼƬ��·�� * @param maxLength * :�����߿�����ֵ
	 */
	public static void compressImage(String srcImgPath, String outImgPath,
			int maxLength)
	{
		// �õ�ͼƬ
		BufferedImage src = InputImage(srcImgPath);
		if (null != src)
		{
			int old_w = src.getWidth();
			// �õ�Դͼ��
			int old_h = src.getHeight();
			// �õ�Դͼ��
			int new_w = 0;
			// ��ͼ�Ŀ�
			int new_h = 0;
			// ��ͼ�ĳ�
			// ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
			if (old_w > old_h)
			{
				// ͼƬҪ���ŵı���
				new_w = maxLength;
				new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
			} else
			{
				new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
				new_h = maxLength;
			}
			disposeImage(src, outImgPath, new_w, new_h);
		}
	}

	/** * ����ͼƬ * * @param src * @param outImgPath * @param new_w * @param new_h */
	private synchronized static void disposeImage(BufferedImage src,
			String outImgPath, int new_w, int new_h)
	{		
		// �õ�ͼƬ
		int old_w = src.getWidth();
		// �õ�Դͼ��
		int old_h = src.getHeight();
		// �õ�Դͼ��
		BufferedImage newImg = null;
		// �ж�����ͼƬ������
		switch (src.getType())
		{
		case 13:
			// png,gifnewImg = new BufferedImage(new_w, new_h,
			// BufferedImage.TYPE_4BYTE_ABGR);
			break;
		default:
			newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			break;
		}
		Graphics2D g = newImg.createGraphics();
		// ��ԭͼ��ȡ��ɫ������ͼ
		g.drawImage(src, 0, 0, old_w, old_h, null);
		g.dispose();
		// ����ͼƬ�ߴ�ѹ���ȵõ���ͼ�ĳߴ�
		newImg.getGraphics().drawImage(
				src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
				null);
		// ���÷������ͼƬ�ļ�
		OutImage(outImgPath, newImg);
	}

	/**
	 * * ��ͼƬ�ļ������ָ����·���������趨ѹ������ * * @param outImgPath * @param newImg * @param
	 * per
	 */
	private static void OutImage(String outImgPath, BufferedImage newImg)
	{
		// �ж�������ļ���·���Ƿ���ڣ��������򴴽�
		File file = new File(outImgPath);
		if (!file.getParentFile().exists())
		{
			file.getParentFile().mkdirs();
		}// ������ļ���
		try
		{
			ImageIO.write(newImg,
					outImgPath.substring(outImgPath.lastIndexOf(".") + 1),
					new File(outImgPath));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static Map<Integer, String> readfile(String filepath,
			Map<Integer, String> pathMap) throws Exception
	{
		if (pathMap == null)
		{
			pathMap = new HashMap<Integer, String>();
		}

		File file = new File(filepath);
		// �ļ�
		if (!file.isDirectory())
		{
			pathMap.put(pathMap.size(), file.getPath());

		} else if (file.isDirectory())
		{ // �����Ŀ¼�� ����������Ŀ¼ȡ�������ļ���
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++)
			{
				File readfile = new File(filepath + "/" + filelist[i]);
				if (!readfile.isDirectory())
				{
					pathMap.put(pathMap.size(), readfile.getPath());

				} else if (readfile.isDirectory())
				{ // ��Ŀ¼��Ŀ¼
					readfile(filepath + "/" + filelist[i], pathMap);
				}
			}
		}
		return pathMap;
	}
	public static void compressToDaTu(String srcImgPath,String outImgPath)
			throws Exception
	{
		 File picture = new File(srcImgPath);  
	    BufferedImage sourceImg =ImageIO.read(new FileInputStream(picture));
	    if(sourceImg!=null) compressToDaTu(sourceImg, outImgPath);
	    else throw new Exception("ͼƬ�ļ�����");
	}
	public static Point compressToDaTu(BufferedImage sourceImg,String outImgPath)
	{		 
		    float img_w=sourceImg.getWidth();
		    float img_h=sourceImg.getHeight();
		    float rate;
		    if(img_w>img_h)
		     {
		    	rate=DaTuWidth/img_w;
		     } 
		    else {
				rate=DaTuHeight/img_h;
			}
		   // MyMsg.DbgMsg("OpImage.compressToDaTu:\r\n"+String.format("r=%f,w=%f,h=%f",rate,img_w*rate,img_h*rate));
		    Point p=new Point();//�����p��ʵ��ͼƬ�Ŀ���ߣ���ʱ��������ṹ�����Լ�������
		    p.x=(int)( img_w*rate);
		    p.y=(int)(img_h*rate);
		    disposeImage(sourceImg, outImgPath,p.x,p.y);
		    return p;
	}
	public static Point compressToZhongTu(BufferedImage sourceImg,String outImgPath)
	{		 
		    float img_w=sourceImg.getWidth();
		    float img_h=sourceImg.getHeight();
		    float rate;
		    if(img_w>img_h)
		     {
		    	rate=ZhongTuWidth/img_w;
		     } 
		    else {
				rate=ZhongTuHeight/img_h;
			}
		    Point p=new Point();//�����p��ʵ��ͼƬ�Ŀ���ߣ���ʱ��������ṹ�����Լ�������
		    p.x=(int)( img_w*rate);
		    p.y=(int)(img_h*rate);
		   // MyMsg.DbgMsg("OpImage.compressToZhongTu:\r\n"+String.format("r=%f,w=%f,h=%f",rate,img_w*rate,img_h*rate));
		    disposeImage(sourceImg, outImgPath,p.x,p.y);
		    return p;
	}
	/**��һ��ʹ����������ȡ����ͼ��html�е����ųߴ�
	 * @param sourceImg
	 * @param outImgPath
	 * @return
	 */
	public static Point getDatuHtmlStyle_WH(BufferedImage sourceImg)
	{		 
		    float img_w=sourceImg.getWidth();
		    float img_h=sourceImg.getHeight();
		    float rate;
		    if(img_w>img_h)
		     {
		    	rate=DaTuWidth/img_w;
		     } 
		    else {
				rate=DaTuHeight/img_h;
			}
		    Point p=new Point();//�����p��ʵ��ͼƬ�Ŀ���ߣ���ʱ��������ṹ�����Լ�������
		    p.x=(int)( img_w*rate);
		    p.y=(int)(img_h*rate);
		    return p;
	}
}
