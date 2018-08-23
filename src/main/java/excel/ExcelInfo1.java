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
	private WritableSheet sheet1;
	private WritableWorkbook workbook;
	private FileOutputStream fos;
	public boolean writeData(ArrayList<ArrayList<String>> dataArea) throws IOException, RowsExceededException, WriteException {
		String titles[]= {"articleTitle","sectionId","paragraphId","firstClassTitle","secondClassTitle","pContent"};
		for(int i=0;i<titles.length;i++) {
	        WritableFont wFont = new WritableFont(WritableFont.TIMES, 14, WritableFont.BOLD, true);   
	        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
	        Label labelCF = new Label(i, 0, titles[i], wCellFormatC);  
			this.sheet1.addCell(labelCF);
		}
		for(int row = 0; row< dataArea.size();row++) {
			for(int col = 0;col<dataArea.get(row).size();col++) {
		        WritableFont wFont = new WritableFont(WritableFont.TIMES, 12, WritableFont.BOLD, true);   
		        WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);   
		        Label labelCF = new Label(col, row+1, dataArea.get(row).get(col), wCellFormatC);
				this.sheet1.addCell(labelCF);
			}
		}
//		for (int i = 0; i < Xnew.length; i++) {
//			sheet1.addCell(new Number(0, i, Xnew[i]));
//		}
//		for (int i = 0; i < Ynew.length; i++) {
//			sheet1.addCell(new Number(1, i, Ynew[i]));
//		}
		this.workbook.write();
		this.workbook.close();
		this.fos.flush();
		this.fos.close();
		return true;
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

	public boolean fileExist(String fileStr) {
		File outfile = new File(fileStr);
		if(outfile.exists())
			return true;
		else
			return false;
	}

	public void createExcel(String fileStr, String sheetName, String[] title) throws IOException {
		File outFile = new File(fileStr);
		outFile.createNewFile();
		this.fos = new FileOutputStream(outFile);
		this.workbook = Workbook.createWorkbook(fos);
		this.sheet1 = workbook.createSheet(sheetName, 0);
	}
}
