package lover;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import excel.ExcelInfo;
/**
*@author    created by Ren Jingui
*@date  2018年8月12日---下午8:57:13
*@problem 解析xml信息，网址为：http://www.mdpi.com/2073-4409/1/1/15/htm
*@answer 根据xml构建类的组成关系
*@action 根据组成关系写入excel文件
*/
public class XMLParse {
	static List<Article> articles = new ArrayList<Article>();
	static String filePathRoot = "res/cells";
	static class IgnoreDTDEntityResolver implements EntityResolver {    
	    @Override    
	    public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException {    
	         return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));     
	    }   
	}
	public static SAXReader getSAXReader() {
	    SAXReader saxReader = new SAXReader();  
	    try {  
	        saxReader.setEntityResolver(new XMLParse.IgnoreDTDEntityResolver());  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    }  
	    return saxReader;  
	}  
	public void traverseFolder(String path) throws DocumentException {
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    readFile(file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        readFile(file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在！");
        }
        System.out.println("文件夹数目: " + folderNum + ",文件数目: " + fileNum);

    }
	
	public void readFile(String path) throws DocumentException {
		Article ar = new Article();
		SAXReader reader = getSAXReader();
		Document document = reader.read(new File(path));
		Element root = document.getRootElement();
		ar.front = new Front().parse(root.element("front"));
		ar.body = new Body().parse(root.element("body"));
		ar.back = new Back().parse(root.element("back"));
		this.articles.add(ar);
	}
	public static void main(String[] args) {
		XMLParse xml = new XMLParse();
		try {
			xml.traverseFolder(filePathRoot);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		xml.writeToExcel();
	}
	private void writeToExcel() {
		String path = "F:/results.xls";
		String sheetName = "article-text";
		String[] title = {"articleTitle",//
							"Abstract",
							"Keywords",
							"1. Introduction",
							"2. Results and Discussion",
							"3. Experimental Section",
							"4. Conclusions",
							"Acknowledgments",
							"Conflict of Interest",
							"References"};
		ExcelInfo writer = new ExcelInfo();
		if(writer.fileExist(path) == false) {
			try {
				writer.createExcel(path, sheetName, title);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//writer.writeToExcel(path, sheetName, mapList);
//		int max = 0, temp,rowNum = 0;
//		for() {//article
//			for() {
//				temp = writer.writeToExcelByColumn(path, sheetName, rowNum, columnName, content);//return how many number
//				if(max < temp)
//			}
//			
//		}
		int[] rowNumArr = new int[this.articles.size()];
		int rowNum = 0;
		for(int i=0;i<this.articles.size();i++)
		{
			List<Integer> tempArr = new ArrayList<Integer>();
			tempArr.add(this.articles.get(i).front.articleMeta.abs.paragraph.size());
			tempArr.add(1);
			for(int j=0;j<this.articles.get(i).body.secs.size();j++)
			{
				tempArr.add(this.articles.get(i).body.secs.get(j).paragrapth.size());
			}
			tempArr.add(this.articles.get(i).back.ack.paragraphs.size());
			if(Objects.nonNull(this.articles.get(i).back.notes)) {
				tempArr.add(this.articles.get(i).back.notes.paragraph.size());
			}
			tempArr.add(this.articles.get(i).back.obj.refs.size());
			
			int max = Collections.max(tempArr);
			for(int titleIndex = 0;titleIndex < title.length;titleIndex++)
			{
				writer.writeToExcelByColumn(path, sheetName, rowNum, title[titleIndex], max);//pathName, sheetName, rownum, column, total_num
			}
			
		}
		
	}
}
