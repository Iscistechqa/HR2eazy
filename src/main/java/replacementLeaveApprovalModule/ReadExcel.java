package replacementLeaveApprovalModule;

import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public static String[][] getExcelData() {
		
		String fileLocation = "./Testdata/ApplyReplacementLeave1.xlsx";
		
		XSSFWorkbook workBook = null;
		try {
			workBook = new XSSFWorkbook(fileLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		XSSFSheet sheet = workBook.getSheetAt(0);
		
		int lastRowNum = sheet.getLastRowNum();
		
		short lastCellNum = sheet.getRow(0).getLastCellNum();
		
		String [][] data = new String[lastRowNum][lastCellNum];
		
		for(int i=1;i<=lastRowNum; i++) {
			XSSFRow row = sheet.getRow(i);
			for(int j=0; j<lastCellNum; j++) {
				XSSFCell cell = row.getCell(j);
				DataFormatter dataChange = new DataFormatter();
				String formatCellValue = dataChange.formatCellValue(cell);
				data[i-1][j] = formatCellValue;
			}
		}
		
		try {
			workBook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
		
		
		
	}

}
