package MyJ_CommonLib;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

public class ImageCompress {
	protected ImageWriter imgWrier;
	protected ImageWriteParam imgWriteParams;
	 
	private int width=125;
	private int height=125;
	private float quality=0.8f;////鏉╂瑤閲滈崐鑹板瘱閸ョ繝璐�0-1.0,閸婅壈绉虹亸蹇ョ礉閸樺缂夐惃鍕Ш鐏忓骏绱濊ぐ鎾跺姧閸ュ墽澧栨径杈╂埂鐡掑﹥妲戦弰锟�
	private static ImageCompress compress;
	
	
	private  ImageCompress() {
		
	}
	
	public static ImageCompress getImageCompress(){
		if(null==compress){
			compress=new ImageCompress();
		}
		return compress;
	}
	
	public  void compress(File src,File des) {
		try {
			// 閸樺缂夐崜宥囨畱JPEG閺傚洣娆�
			
			// 閸樺缂夐崜宥呮倵閻ㄥ嚙PEG閺傚洣娆�
			if(!des.exists()){
				des.createNewFile();
			}
		
			// 閸樺缂夐惂鎯у瀻濮ｏ拷
			if(des.exists()){
				des.delete();
			}				
			Image image = javax.imageio.ImageIO.read(src); 
		        BufferedImage tag=new BufferedImage(this.width,this.height ,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(image, 0, 0, this.width, this.width, null); // 缂佹ê鍩楃紓鈺佺毈閸氬海娈戦崶锟�
			Iterator<ImageWriter> it = ImageIO.getImageWritersBySuffix("jpg");
			if (it.hasNext()) {
				FileImageOutputStream fileImageOutputStream = new FileImageOutputStream(
						des);
				ImageWriter iw = (ImageWriter) it.next();
				ImageWriteParam iwp = iw.getDefaultWriteParam();
				iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				iwp.setCompressionQuality(quality);
				iw.setOutput(fileImageOutputStream);
				iw.write(null, new IIOImage(tag, null, null), iwp);
				iw.dispose();
				fileImageOutputStream.flush();
				fileImageOutputStream.close();
			}		
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			
		}
	}
}