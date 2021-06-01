package MyJ_CommonLib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bouncycastle.asn1.dvcs.Data;

import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

//<!--        poi实现excel导入导出-->
//<dependency>
//    <groupId>org.apache.poi</groupId>
//    <artifactId>poi</artifactId>
//    <version>3.15</version>
//</dependency>
//<dependency>
//    <groupId>org.apache.poi</groupId>
//    <artifactId>poi-ooxml-schemas</artifactId>
//    <version>3.15</version>
//</dependency>
//<dependency>
//    <groupId>org.apache.poi</groupId>
//    <artifactId>poi-ooxml</artifactId>
//    <version>3.15</version>
//</dependency>


public class MyJ_ExportExcelOp{
  static XSSFWorkbook getWorkBook(JSONObject columns,JSONArray data) {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("Goods");//创建一张表
        Row titleRow = sheet.createRow(0);//创建第一行，起始为0
        int collen=columns.size();
        List<String> colName=new ArrayList<String>();
        List<String> fieldName=new ArrayList<String>();
        
        Iterator iter = columns.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,String> entry = (Map.Entry<String,String>) iter.next();
            fieldName.add(entry.getKey().toString());
            colName.add(entry.getValue().toString());
        }
        //创建表头
        for(int i=0;i<collen;i++) {
        	titleRow.createCell(i).setCellValue(colName.get(i));
        }
//        titleRow.createCell(0).setCellValue("序号");//第一列
//        titleRow.createCell(1).setCellValue("名称");
//        titleRow.createCell(2).setCellValue("数量");
//        titleRow.createCell(3).setCellValue("库存");
//        int cell = 1;
        int rowlen=data.size();
        for(int i=1;i<=rowlen;i++) {
        	Row row = sheet.createRow(i);
        	JSONObject jo=data.getJSONObject(i-1);
        	for(int ci=0;ci<collen;ci++) {
        		row.createCell(ci).setCellValue(jo.getString(fieldName.get(ci)));
        	}        	
        }
//        for (  : list) {
//            Row row = sheet.createRow(cell);//从第二行开始保存数据
//            row.createCell(0).setCellValue(cell);
//            row.createCell(1).setCellValue(goods.getGname());//将数据库的数据遍历出来
//            row.createCell(2).setCellValue(goods.getGprice());
//            row.createCell(3).setCellValue(goods.getTid());
//            cell++;
//        }
        return wb;
    }
  /*
   * columns格式  {dbfieldName：中文名，.....}
   * data: 数据库list的转换 JSONArray.fromObject(List<..>)
   */
  	static String generate_download_Excel(JSONObject columns,JSONArray data,String path){
      XSSFWorkbook wb = getWorkBook(columns, data);
      OutputStream outputStream =null;
      try {

//          response.reset();//tomcat 部署项目可以不用加这行代码
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          wb.write(baos);
//          response.setContentType("application/x-download;charset=utf-8");
//          String excelName = new String(( fileName + "（" + date + "）").getBytes("GB2312"), "ISO8859-1") + ".xlsx";//防止中文文件名乱码
//          response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//          outputStream = response.getOutputStream();
          ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
          byte[] b = new byte[1024];
          File tempFile = new File(path);
          if (!tempFile.exists()) {
              tempFile.mkdirs();
          }
          String fileName=String.valueOf(new Date().getTime())+".xlsx";
          fileName=tempFile.getPath() + File.separator + fileName;
          outputStream = new FileOutputStream(fileName);
          while ((bais.read(b)) > 0) {
              outputStream.write(b);
          }
          bais.close();
          outputStream.flush();
          outputStream.close();
          return fileName;
          
      } catch (UnsupportedEncodingException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      }
      return "";
  	}
  	/*
  	 * 常用的封装，前端提交 filename colmuns两个字段
  	 */
  	public static String common_1_create_temp_excel_getDeFileName(HttpServletRequest request
  			,List<?> ls,String path) throws Exception{
//  		String fileName=request.getParameter("filename");
//  		if(StringUtils.isBlank(fileName))
//  			fileName=MyJ_DatetimeOp.getCurrentDateStr().replace("-", "").replace(" ", "_").replace(":", "");
  		String strColumns=request.getParameter("columns");
  		strColumns=MyJ_StringOp.decodingUrlParam_try(strColumns);
  		JSONObject jo=JSONObject.fromObject(strColumns);
  		JsonConfig jConfig=MyJ_JsonOp.getJsonConfig_NumToBlankStr();
  		String tempfile=generate_download_Excel(jo, 
  				//JSONArray.fromObject(ls)
  				JSONArray.fromObject(ls,jConfig)
  				, path);
  		String enFileName=tempfile+"`"+String.valueOf(new Date().getTime());
  		enFileName=MyJ_CiphertextOp.getRcEncode(enFileName);
  		return enFileName;
  	}
}