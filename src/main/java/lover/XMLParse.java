package lover;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		writeToExcel();
	}
	private static void writeToExcel() {
		
		for(Article art: articles) {
			//art.write();
		}
	}
}
