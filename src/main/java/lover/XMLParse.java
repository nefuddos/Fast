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
*@date  2018��8��11��---����8:57:13
*@problem ����xml�ļ�������ض��ֶε�excel��
*@answer ����DOM4J�ķ�ʽ���д���
*@action ��ʱ�����Ƿ�װ��ֱ�Ӷ�ȡ�ʹ�����
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
	    //�ڶ�ȡ�ļ�ʱ��ȥ��dtd����֤��������������ʱ��  
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
                    System.out.println("�ļ���:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else {
                    System.out.println("�ļ�:" + file2.getAbsolutePath());
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
                        System.out.println("�ļ���:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        System.out.println("�ļ�:" + file2.getAbsolutePath());
                        readFile(file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("�ļ�������!");
        }
        System.out.println("�ļ��й���:" + folderNum + ",�ļ�����:" + fileNum);

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
