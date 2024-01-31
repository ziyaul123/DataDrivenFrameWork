package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunctions.FunctionLibrary;
import config.AppUtil;
import utilities.ExcelFileUtil;

public class Take_Sc extends AppUtil 
{
	
		    String inputpath = "./FileInput/LoginData.xlsx";
			String outputpath = "./FileOutput/DataDrivenResults.xlsx";
			ExtentReports report;
			ExtentTest logger;
			@Test
			public void startTest() throws Throwable
			{
				report = new ExtentReports(".target/Reports/DataDriven.html");
				ExcelFileUtil xl = new ExcelFileUtil(inputpath);
				int rc = xl.rowCount("Sheet1");
				Reporter.log("no of row are::"+rc,true);
				for(int i=1;i<rc;i++) 
				{
					logger = report.startTest("Validate Login");
					String user = xl.getCellData("Sheet1", i, 0);
					String pass = xl.getCellData("Sheet1", i, 1);
					boolean res = FunctionLibrary.adminLogin(user, pass);
					if(res) 
					{
						File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./ScreenShot/Iteration/+i"+"Loginpage.png"));
						xl.setCellData("Sheet1", i, 2, "Login Succses", outputpath);
						xl.setCellData("Sheet1", i, 3, "Pass", outputpath);
						logger.log(LogStatus.PASS, "Valid Username and Password");
					}
					else 
					{
						File screen = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./ScreenShot/Iteration/+i"+"Loginpage.png"));
						xl.setCellData("Sheet1", i, 2, "Login Fail", outputpath);
						xl.setCellData("Sheet1", i, 3, "Fail", outputpath);
						logger.log(LogStatus.FAIL, "Invalid Username and Password");
					}
					report.endTest(logger);
					report.flush();
				}
				
				
			}
			

		}






