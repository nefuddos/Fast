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
	static String outputFile = "F:/results.xls";
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
		ExcelInfo1 excelHandler = xml.createExcel(outputFile);
		xml.fillDataArea();
		//xml.displayDataArea();
		try {
			xml.writeToExcel(excelHandler);
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeToExcel(ExcelInfo1 handler) throws RowsExceededException, WriteException, IOException {
		// TODO Auto-generated method stub
		handler.writeData(dataArea);
	}
	private void displayDataArea() {
		for(ArrayList<String> rowIter : dataArea) {
			for(String str : rowIter) {
				System.out.format("%s ", str);
			}
			System.out.println("");
		}
	}
	private ExcelInfo1 createExcel(String fileStr) {
		String sheetName = "article-text";
		String[] title = {"articleTitle",
							"sectionId",
							"paragraphId",
							"firstClassTitle",
							"secondClassTitle",
							"pContent"};
		ExcelInfo1 writer = new ExcelInfo1();
		if(writer.fileExist(fileStr) == false) {
			try {
				writer.createExcel(fileStr, sheetName, title);
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
	private void fillDataArea() {
		int pNum = 0;
		for(int i=0;i<this.articles.size();i++)
		{
			//abstract
			String tempTitle = this.articles.get(i).front.articleMeta.articleTitle;//1
			List<String> pContent = this.articles.get(i).front.articleMeta.abs.paragraph;
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
			List<String> kwdsStr = this.articles.get(i).front.articleMeta.kwdObj.kwds;
			record.add(tempTitle); //1
			record.add("2");//2
			pNum++;
			record.add(new Integer(pNum).toString()); //3
			record.add("keywords");//4
			record.add(" ");//5
			record.addAll(kwdsStr);//6
			dataArea.add(record);
			//introduction,methods,result,discuss
			List<lover.Sec> secList = this.articles.get(i).body.secs;
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
		}
		
	}
}
