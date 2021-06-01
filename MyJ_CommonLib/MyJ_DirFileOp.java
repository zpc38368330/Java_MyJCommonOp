package MyJ_CommonLib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.lang.StringUtils;

public class MyJ_DirFileOp {
	public static void tryCreateDir(String direct) {
		if (StringUtils.isNotEmpty(direct)) {
			File file = new File(direct);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
	}
	public static void CreateDirs(String directs)
	{
		File file = new File(directs);
		if (!file.exists()) {
			file.mkdirs();
		}
	}
	public static String getCurrentPath() {
		return System.getProperty("user.dir");
	}

	public static void getCurrentServletpath_Example() {
		// ServletContext s1=this.getServletContext();
		// String temp=s1.getRealPath("/"); (�ؼ�)
	}
	//codetype =["GBK"|"UTF8"]
    public static String getStringFromFile(String filePath,String codetype) {    	
    	StringBuilder sBuilder=new StringBuilder("");
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), codetype);
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    sBuilder.append(lineTxt);
                    sBuilder.append("\r\n");
                }
                br.close();
                isr.close();
            } else {
                sBuilder.append("文件不存在!"+filePath);
            }
        } catch (Exception e) {
        	sBuilder.append("文件读取错误!"+filePath);
        }
        return sBuilder.toString();
    }
	public static void writeToFile_AppendOrCreate_Try(String pathfiel, String text) {
		File file = new File(pathfiel);
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			if (!file.exists()) {
				boolean hasFile = file.createNewFile();
				if (hasFile) {
					//System.out.println("file not exists, create new file");
				}
				fos = new FileOutputStream(file);
			} else {
				//System.out.println("file exists");
				fos = new FileOutputStream(file, true);
			}
			osw = new OutputStreamWriter(fos, "utf-8");
			osw.write(text);
			// д������ osw.write("\r\n"); //����
		} catch (Exception e) {
			e.printStackTrace();
		} finally { // �ر���
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
