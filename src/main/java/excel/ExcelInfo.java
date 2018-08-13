package excel;
/**
*@author    created by Ren Jingui
*@date  2018��8��12��---����9:21:51
*@problem ר�Ŵ���excel��ص���Ϣ
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
     * �ж��ļ��Ƿ����. 
     * @param fileDir  �ļ�·�� 
     * @return 
     */  
    public static boolean fileExist(String fileDir){  
         boolean flag = false;  
         File file = new File(fileDir);  
         flag = file.exists();  
         return flag;  
    }  
    /** 
     * �ж��ļ���sheet�Ƿ����. 
     * @param fileDir   �ļ�·�� 
     * @param sheetName  ��������� 
     * @return 
     */  
    public static boolean sheetExist(String fileDir,String sheetName) throws Exception{  
         boolean flag = false;  
         File file = new File(fileDir);  
         if(file.exists()){    //�ļ�����  
            //����workbook  
             try {  
                workbook = new HSSFWorkbook(new FileInputStream(file));  
                //���Worksheet�������sheetʱ���ɵ�xls�ļ���ʱ�ᱨ��)  
                HSSFSheet sheet = workbook.getSheet(sheetName);    
                if(sheet!=null)  
                    flag = true;  
            } catch (Exception e) {  
                throw e;
            }   
              
         }else{    //�ļ�������  
             flag = false;  
         }  
         return flag;  
    }  
    /** 
     * ������excel. 
     * @param fileDir  excel��·�� 
     * @param sheetName Ҫ�����ı������ 
     * @param titleRow excel�ĵ�һ�м����ͷ 
     */  
    public static void createExcel(String fileDir,String sheetName,String titleRow[]) throws Exception{  
        //����workbook
    	if(fileExist(fileDir)) return;
        workbook = new HSSFWorkbook();
        //���Worksheet�������sheetʱ���ɵ�xls�ļ���ʱ�ᱨ��)  
        HSSFSheet sheet1 = workbook.createSheet(sheetName);    
        //�½��ļ�  
        FileOutputStream out = null;  
        try {  
            //��ӱ�ͷ  
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
     * ɾ���ļ�. 
     * @param fileDir  �ļ�·�� 
     */  
    public static boolean deleteExcel(String fileDir) {  
        boolean flag = false;  
        File file = new File(fileDir);  
        // �ж�Ŀ¼���ļ��Ƿ����    
        if (!file.exists()) {  // �����ڷ��� false    
            return flag;    
        } else {    
            // �ж��Ƿ�Ϊ�ļ�    
            if (file.isFile()) {  // Ϊ�ļ�ʱ����ɾ���ļ�����    
                file.delete();  
                flag = true;  
            }   
        }  
        return flag;  
    }  
    /** 
     * ��excel��д��(�Ѵ��ڵ������޷�д��). 
     * @param fileDir    �ļ�·�� 
     * @param sheetName  ������� 
     * @param object 
     * @throws Exception 
     */  
    public static void writeToExcel(String fileDir,String sheetName,List<Map> mapList) throws Exception{  
        //����workbook  
        File file = new File(fileDir);
        try {  
            workbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        //��  
        FileOutputStream out = null;  
        HSSFSheet sheet = workbook.getSheet(sheetName);
        int columnCount = sheet.getRow(0).getLastCellNum();
        try {  
            // ��ñ�ͷ�ж���  
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
//        /*�ж��ļ��Ƿ����  
        System.out.println(ExcelInfo.fileExist("F:/test2.xls"));  
        //�����ļ�  
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
        map.put("name", "����");
        map.put("password", "111��@#");
        
        Map<String,String> map2=new HashMap<String,String>();
        map2.put("id", "222");
        map2.put("name", "����");
        map2.put("password", "222��@#");
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
