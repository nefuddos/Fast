package excel;
/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午9:21:51
*@problem 专门处理excel相关的信息
*@answer
*@action
*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelInfo {
    private static HSSFWorkbook workbook = null;  
    
    /** 
     * 判断文件是否存在
     * @param fileDir  文件路径
     * @return 
     */  
    public static boolean fileExist(String fileDir){  
         boolean flag = false;  
         File file = new File(fileDir);  
         flag = file.exists();  
         return flag;  
    }  
    /** 
     * 判断文件的sheet是否存在.
     * @param fileDir   文件路径
     * @param sheetName  表格索引名
     * @return 
     */  
    public static boolean sheetExist(String fileDir,String sheetName) throws Exception{  
         boolean flag = false;  
         File file = new File(fileDir);  
         if(file.exists()){    //文件存在
        	//创建workbook
             try {  
                workbook = new HSSFWorkbook(new FileInputStream(file));  
              //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {  
                throw e;
            }   
              
         }else{
             flag = false;  
         }  
         return flag;  
    }  
    /** 
     * 创建新excel.
     * @param fileDir  excel的路径
     * @param sheetName 要创建的表格索引
     * @param titleRow excel的第一行即表格头 
     */  
    public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{
    	if(fileExist(fileDir)) return;
        workbook = new HSSFWorkbook();  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        FileOutputStream out = null;  
        try {  
            HSSFRow row = workbook.getSheet(sheetName).createRow(0);    //������һ��    
            for(short i = 0;i < titleRow.length;i++){  
                HSSFCell cell = row.createCell(i);  
                cell.setCellValue(titleRow[i]);  
            }  
            out = new FileOutputStream(new File(fileDir));
            workbook.write(out);  
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {
            	if(Objects.nonNull(out))
            		out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
    }  
    /** 
     * 删除文件.
     * @param fileDir  文件路径
     */  
    public static boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);      
        if (!file.exists()) {
            return flag;    
        } else {     
            if (file.isFile()) {
                file.delete();  
                flag = true;  
            }   
        }  
        return flag;  
    }  
    /** 
     * 往excel中写入(已存在的数据无法写入). 
     * @param fileDir    文件路径 
     * @param sheetName  表格索引 
     * @param object 
     * @throws Exception 
     */ 
    public static void writeToExcel(String fileDir,String sheetName,List<Map> mapList) throws Exception{  
        File file = new File(fileDir);
        try {  
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        FileOutputStream out = null;  
        HSSFSheet sheet = workbook.getSheet(sheetName);
        int columnCount = sheet.getRow(0).getLastCellNum();
        try {  
            HSSFRow titleRow = sheet.getRow(0);  
            if(titleRow!=null){ 
                for(int rowId=0;rowId<mapList.size();rowId++){
                    Map map = mapList.get(rowId);
                    HSSFRow newRow=sheet.createRow(rowId+1);
                    for (short columnIndex = 0; columnIndex < columnCount; columnIndex++) {  //������ͷ  
                        @SuppressWarnings("deprecation")
						String mapKey = titleRow.getCell(columnIndex).toString().trim();
                        @SuppressWarnings("deprecation")
						HSSFCell cell = newRow.createCell(columnIndex);  
                        cell.setCellValue(map.get(mapKey)==null ? null : map.get(mapKey).toString());  
                    } 
                }
            }  
            out = new FileOutputStream(fileDir);  
            workbook.write(out);  
        } catch (Exception e) {  
            throw e;
        } finally {    
            try {
            	if(Objects.nonNull(out))
            		out.close();    
            } catch (IOException e) {    
                e.printStackTrace();  
            }    
        }    
    }  
      
    public static void main(String[] args) {  
        System.out.println(ExcelInfo.fileExist("F:/test2.xls"));  
        String title[] = {"id","name","password"};  
        try {
			ExcelInfo.createExcel("F:/test2.xls","sheet1",title);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        List<Map> list=new ArrayList<Map>();
        Map<String,String> map=new HashMap<String,String>();
        map.put("id", "111");
        map.put("name", "张三");
        map.put("password", "111！@#");
        
        Map<String,String> map2=new HashMap<String,String>();
        map2.put("id", "222");
        map2.put("name", "李四");
        map2.put("password", "222！@#");
        list.add(map);
        list.add(map2);
        try {
			ExcelInfo.writeToExcel("F:/test2.xls","sheet1",list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
}
