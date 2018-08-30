package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jxl.Cell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
/**
 * @author created by Ren Jingui
 * @date 2018年8月22日---下午10:13:40
 * @problem
 * @answer
 * @action
 */
public class ExcelInfo1 {
	public double Xnew[];
	public double Ynew[];
	private List<WritableSheet> sheets;
//	private WritableWorkbook workbook;
//	private FileOutputStream fos;
	private int maxSheetRow;
	private int sheetNum;
	private String sheetName;
	private String fileName;
	public ExcelInfo1() {
		maxSheetRow = 10000;
		sheetNum = 0;
		sheets = new ArrayList<WritableSheet>();
	}
	/*
	 * if there are over maxSheetRow record, write it to next sheet
	 */
	public boolean writeData(ArrayList<ArrayList<String>> dataArea, int begin) throws IOException, RowsExceededException, WriteException {
		String titles[]= {"articleTitle","sectionId","paragraphId","firstClassTitle","secondClassTitle","pContent"};
		FileOutputStream fos = new FileOutputStream(fileName);
		WritableWorkbook workbook = Workbook.createWorkbook(fos);
		String curSheetName = this.sheetName + new Integer(this.sheetNum).toString();
		WritableSheet curSheet = workbook.createSheet(this.sheetName, sheetNum);
		for(int i=0;i<titles.length;i++) {
	        WritableFont wFont = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, true);   
	        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
	        Label labelCF = new Label(i, 0, titles[i], wCellFormatC);  
	        curSheet.addCell(labelCF);
		}
		for(int row = begin; row< dataArea.size();row++) {
			if(row > maxSheetRow + begin) {
				sheetNum++;
				writeDataNext(fos, workbook, dataArea, row);
				workbook.write();
				workbook.close();
				fos.flush();
				fos.close();
				break;
			}
			else {
				for(int col = 0;col<dataArea.get(row).size();col++) {
			        WritableFont wFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);   
			        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
			        Label labelCF = new Label(col, row+1-begin, dataArea.get(row).get(col), wCellFormatC);
			        curSheet.addCell(labelCF);
				}
				if(row == dataArea.size() - 1) {
					workbook.write();
					workbook.close();
					fos.flush();
					fos.close();
				}
			}
		}
		return true;
	}

	private void writeDataNext(FileOutputStream fos,WritableWorkbook workbook, ArrayList<ArrayList<String>> dataArea, int begin) throws RowsExceededException, WriteException, IOException {
		String titles[]= {"articleTitle","sectionId","paragraphId","firstClassTitle","secondClassTitle","pContent"};
		String curSheetName = this.sheetName + new Integer(this.sheetNum).toString();
		WritableSheet curSheet = workbook.createSheet(this.sheetName, sheetNum);
		for(int i=0;i<titles.length;i++) {
	        WritableFont wFont = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, true);   
	        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
	        Label labelCF = new Label(i, 0, titles[i], wCellFormatC);  
	        curSheet.addCell(labelCF);
		}
		for(int row = begin; row< dataArea.size();row++) {
			if(row > maxSheetRow + begin) {
				sheetNum++;
				writeDataNext(fos, workbook, dataArea, row);
				break;
			}
			else {
				for(int col = 0;col<dataArea.get(row).size();col++) {
			        WritableFont wFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);   
			        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
			        Label labelCF = new Label(col, row+1-begin, dataArea.get(row).get(col), wCellFormatC);
			        curSheet.addCell(labelCF);
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		// Workbook wb = Workbook.getWorkbook(new FileInputStream("F:/test.xls"));
		// Sheet[] sheets = wb.getSheets();
		// Sheet sheet = sheets[0];
		// int rows = sheet.getRows();
		// double X[] = new double[rows];
		// double Y[] = new double[rows];
		// List<Double> xList = new ArrayList<>();
		// List<Double> yList = new ArrayList<>();
		// for (int i = 0; i < rows; i++) {
		// Cell[] cells = sheet.getRow(i);
		// X[i] = ((NumberCell) cells[0]).getValue();
		// Y[i] = ((NumberCell) cells[1]).getValue();
		// }
		// for (int n = 1; n < rows - 2; n++) {
		// if ((Y[n] >= Y[n - 1]) && (Y[n] > Y[n + 1]) || (Y[n] <= Y[n - 1]) && (Y[n] <
		// Y[n + 1]) || (Y[n] > Y[n - 1]) && (Y[n] >= Y[n + 1])
		// || (Y[n] < Y[n - 1]) && (Y[n] <= Y[n + 1])) {
		// xList.add(X[n]);
		// yList.add(Y[n]);
		// }
		// }
		// Double[] Xnew = xList.toArray(new Double[xList.size()]);
		// Double[] Ynew = yList.toArray(new Double[yList.size()]);

//		String outFileName = "F:/abc.xls";
//		File outFile = new File(outFileName);
//		if (!outFile.exists()) {
//			outFile.createNewFile();
//		}
//		FileOutputStream fos = new FileOutputStream(outFile);
//		WritableWorkbook workbook = Workbook.createWorkbook(fos);
//		WritableSheet sheet1 = workbook.createSheet("sheet1", 0);
//		for (int i = 0; i < Xnew.length; i++) {
//			sheet1.addCell(new Number(0, i, Xnew[i]));
//		}
//		for (int i = 0; i < Ynew.length; i++) {
//			sheet1.addCell(new Number(1, i, Ynew[i]));
//		}
//		workbook.write();
//		workbook.close();
//		fos.flush();
//		fos.close();
	}

	public boolean fileExist(String fileStr, String sheetStr) throws IOException {
		File outfile = new File(fileStr);
		if(outfile.exists()) {
			this.fileName = fileStr;
			this.sheetName = sheetStr;
			return true;
		}
		else
			return false;
	}

	public void createExcel(String fileStr, String sheetStr) throws IOException {
		File outFile = new File(fileStr);
		outFile.createNewFile();
		this.fileName = fileStr;
		this.sheetName = sheetStr;
//		WritableSheet sheet = workbook.createSheet(sheetName, 0);
//		this.sheets.add(sheet);
	}
}
