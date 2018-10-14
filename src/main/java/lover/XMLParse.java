package lover;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
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

import excel.ExcelInfo1;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
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
	static String outputFile = "F:/results.xlsx";
	static String outputFile1 = "F:/results1.xlsx";
	static String filePathRoot1 = "res/result";
	int paragraphNum;
	private ArrayList<ArrayList<String>> dataArea = new ArrayList<ArrayList<String>>();
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
        int xmlFileNum = 0, folderNum = 0, htmlMrkFile = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            
			for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                    folderNum++;
                } else if(file2.getName().endsWith("VSM.xml")){
                	continue;
                } else if(file2.getName().endsWith(".xml")){
                    System.out.println("xml文件:" + file2.getAbsolutePath());
                    readXmlFile(file2.getAbsolutePath());
                    xmlFileNum++;
                } else if(file2.getName().endsWith(".html.mrk")) {
                	System.out.println("html.mrk文件:" + file2.getAbsolutePath());
                	readHtmlMrkFile(file2.getAbsolutePath(),htmlMrkFile);
                	htmlMrkFile++;
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
                    } else if(file2.getName().endsWith("VSM.xml")){
                    	continue;
                    } else if(file2.getName().endsWith(".xml")){
                        System.out.println("xml文件:" + file2.getAbsolutePath());
                        readXmlFile(file2.getAbsolutePath());
                        xmlFileNum++;
                    } else if(file2.getName().endsWith(".html.mrk")) {
                    	System.out.println("html.mrk文件:" + file2.getAbsolutePath());
                    	readHtmlMrkFile(file2.getAbsolutePath(),htmlMrkFile);
                    	htmlMrkFile++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在！");
        }
        System.out.println("文件夹数目: " + folderNum + ",xml文件数目: " + xmlFileNum + " html.mrk文件数目: "+htmlMrkFile);

    }
	private void readHtmlMrkFile(String absolutePath, int id) throws DocumentException {
		String encoding = "UTF-8";
		File instance = new File(absolutePath);
		byte[] content = new byte[(int) instance.length()];
		try {
			FileInputStream in = new FileInputStream(instance);
			in.read(content);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String contentStr = new String(content, encoding);
			contentStr = contentStr.replaceAll("\r\n", "");
			String[] arr = contentStr.split("<section id=\\d+>");
			filldataArea(arr,id);
			//System.out.println(contentStr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void filldataArea(String[] arr, int id) {
		if(arr.length == 0) {
			System.out.println("this file is empty");
			return;
		}
		if(arr.length >=2) {
			String[] pStr = arr[1].split("<paragraph id=\\d+>");
			if(pStr.length >=2)
			{
				String tempTitle = pStr[1];
				for(int j=2;j<arr.length;j++) {
					String[] str = arr[j].split("<paragraph id=\\d+>");
					String title = str[1];
					for(int i=2;i<str.length;i++)
					{
						ArrayList<String> record = new ArrayList<String>();
						record.add(tempTitle);
						record.add(new Integer(i-1).toString());
						record.add(new Integer(paragraphNum).toString());
						record.add(title);
						record.add("");
						record.add(str[i]);
						dataArea.add(record);
						paragraphNum++;
					}
				}
			}
		}
	}
	public void readXmlFile(String path) throws DocumentException {
		Article ar = new Article();
		SAXReader reader = getSAXReader();
		Document document = reader.read(new File(path));
		Element root = document.getRootElement();
		ar.front = new Front().parse(root.element("front"));
		ar.body = new Body().parse(root.element("body"));
		ar.back = new Back().parse(root.element("back"));
		this.articles.add(ar);
		fillDataArea(ar);
	}
	public static void main(String[] args) throws IOException {
		XMLParse xml = new XMLParse();
		xml.paragraphNum = 1;
		try {
			xml.traverseFolder(filePathRoot);
			xml.traverseFolder(filePathRoot1);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		//ExcelInfo1 excelHandler = xml.createExcel(outputFile);
		//ExcelInfo1 excelHandler = xml.createExcel(outputFile1);
		//xml.writeToExcel(excelHandler);
		xml.writeToText();
		xml.displayDataArea();
		System.out.println("Write data success");
	}

	private void writeToText() throws FileNotFoundException {
		// TODO Auto-generated method stub
		System.setOut(new PrintStream(new BufferedOutputStream(
				new FileOutputStream("f:/2.txt")),true));
		for(ArrayList<String> rowIter : dataArea) {
			for(String str : rowIter) {
				System.out.format("%s\t", str);
			}
			System.out.println("");
		}
	}
	private void writeToExcel(ExcelInfo1 handler) throws RowsExceededException, WriteException, IOException {
		// TODO Auto-generated method stub
		handler.writeData(dataArea, 0);
	}
	private void displayDataArea() {
		for(ArrayList<String> rowIter : dataArea) {
			for(String str : rowIter) {
				System.out.format("%s\t", str);
			}
			System.out.println("");
		}
	}
	private ExcelInfo1 createExcel(String fileStr) throws IOException {
		String sheetName = "article-text";
		String[] title = {"articleTitle",
							"sectionId",
							"paragraphId",
							"firstClassTitle",
							"secondClassTitle",
							"pContent"};
		ExcelInfo1 writer = new ExcelInfo1();
		if(writer.fileExist(fileStr,sheetName) == false) {
			try {
				writer.createExcel(fileStr, sheetName);
				System.out.println("create file success");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("file exists");
		}
		return writer;
	}
	private void fillDataArea(Article ar) {
		int pNum = 0;
//		for(int i=0;i<this.articles.size();i++)
//		{
			//abstract
			String tempTitle = ar.front.articleMeta.articleTitle;//1
			List<String> pContent = ar.front.articleMeta.abs.paragraph;
			for(String pStr : pContent) {
				ArrayList<String> record = new ArrayList<String>();
				record.add(tempTitle); //1
				record.add("1");//2
				pNum++;
				record.add(new Integer(pNum).toString()); //3
				record.add("Abstract");//4
				record.add(" ");//5
				record.add(pStr);//6
				dataArea.add(record);
			}
			//key-words
			ArrayList<String> record = new ArrayList<String>();
			String kwdsStr = ar.front.articleMeta.kwdObj.toString();
			record.add(tempTitle); //1
			record.add("2");//2
			pNum++;
			record.add(new Integer(pNum).toString()); //3
			record.add("keywords");//4
			record.add(" ");//5
			record.add(kwdsStr);//6
			dataArea.add(record);
			//introduction,methods,result,discuss
			List<lover.Sec> secList = ar.body.secs;
			int secNum = 3;
			for(lover.Sec secIter : secList) {
				for(String pStr: secIter.paragrapth) {
					ArrayList<String> record1 = new ArrayList<String>();
					record1.add(tempTitle); //1
					record1.add(new Integer(secNum).toString());//2
					pNum++;
					record1.add(new Integer(pNum).toString());
					record1.add(secIter.title);
					record1.add(" ");
					record1.add(pStr);
					dataArea.add(record1);
				}
				for(lover.Sec childSecIter : secIter.childSecList) {
					ArrayList<String> record1 = new ArrayList<String>();
					record1.add(tempTitle); //1
					record1.add(new Integer(secNum).toString());//2
					pNum++;
					record1.add(new Integer(pNum).toString());
					record1.add(secIter.title);
					record1.add(childSecIter.title);
					record1.add(childSecIter.getAllParagraph());
					dataArea.add(record1);
				}
				secNum++;
			}
//		}
		
	}
}
