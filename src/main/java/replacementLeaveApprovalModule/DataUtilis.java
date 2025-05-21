package replacementLeaveApprovalModule;

import org.testng.annotations.DataProvider;

public class DataUtilis {
	
	@DataProvider
	public String[][] getData(){
		
		String[][] excelData = ReadExcel.getExcelData();
		
		return excelData;
		
	}

}
