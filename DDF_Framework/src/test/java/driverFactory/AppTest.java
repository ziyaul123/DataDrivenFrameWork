package driverFactory;

import org.testng.Reporter;
import org.testng.annotations.Test;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class AppTest extends AppUtil
{
	String inputpath = "./FileInput/LoginData.xlsx";
	String outputpath = "./FileOutput/DataDrivenResults.xlsx";
	@Test
	public void startTest() throws Throwable
	{
		ExcelFileUtil xl = new ExcelFileUtil(inputpath);
		int rc = xl.rowCount("Sheet1");
		Reporter.log("no of row are::"+rc,true);
		for(int i=1;i<rc;i++) 
		{
			String user = xl.getCellData("Sheet1", i, 0);
			String pass = xl.getCellData("Sheet1", i, 1);
			boolean res = FunctionLibrary.adminLogin(user, pass);
			if(res) 
			{
				xl.setCellData("Sheet1", i, 2, "Login Succses", outputpath);
				xl.setCellData("Sheet1", i, 3, "Pass", outputpath);
			}
			else 
			{
				xl.setCellData("Sheet1", i, 2, "Login Fail", outputpath);
				xl.setCellData("Sheet1", i, 3, "Fail", outputpath);
			}
		}
		
		
	}
	

}
