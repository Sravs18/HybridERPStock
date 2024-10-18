package driverFactory;

import org.openqa.selenium.WebDriver;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript {
WebDriver driver;
String inputpath="./FileInput/Controller.xlsx";
String outputpath="./FileOutput/HybridResults.xlsx";
String TCSheet = "MasterTestCases";
public void startTest() throws Throwable
{
	String Module_status="";
	String Module_New="";
	//create reference object for accessing excel methods
	ExcelFileUtil xl = new ExcelFileUtil(inputpath);
	//iterate all rows in TCSheet
	for(int i=1;i<=xl.rowCount(TCSheet);i++)
	{
		if(xl.getCellData(TCSheet, i, 2).equalsIgnoreCase("Y"))
		{
			//read testcases from TCSheet
			String TCModule = xl.getCellData(TCSheet, i, 1);
			//iterate all rows in TCModule sheet
			for(int j=1;j<=xl.rowCount(TCModule);j++)
			{
				//read all cells from TCModule sheet
				String Description = xl.getCellData(TCModule, j, 0);
				String ObjectType = xl.getCellData(TCModule, j, 1);
				String Ltype = xl.getCellData(TCModule, j, 2);
				String Lvalue = xl.getCellData(TCModule, j, 3);
				String TestData = xl.getCellData(TCModule, j, 4);
				try {
						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver = FunctionLibrary.startBrowser();
						}
						if(ObjectType.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionLibrary.waitForElement(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle(TestData);
						}
						if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
						}
						if(ObjectType.equalsIgnoreCase("captureStock"))
						{
							FunctionLibrary.captureStock(Ltype, Lvalue);
						}
						if(ObjectType.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Ltype, Lvalue, TestData);
						}
						if(ObjectType.equalsIgnoreCase("stockTable"))
						{
							FunctionLibrary.stockTable();
						}
						//write as pass into status cell in TCmodule sheet
						xl.setCellData(TCModule, j, 5, "pass", outputpath);
						Module_status="True";
						
				} catch (Exception e) {
					System.out.println(e.getMessage());
					//write as fail into status cell in TCModule sheet
					xl.setCellData(TCModule, j, 5, "fail", outputpath);
					Module_New = "False";
				}
					if(Module_status.equalsIgnoreCase("True"))
					{
						//write as pass into TCSheet in status cell
						xl.setCellData(TCSheet, i, 3, "pass", outputpath);
					}
			}
			if(Module_New.equalsIgnoreCase("False"))
			{
				//write as fail into TCSheet in status cell
				xl.setCellData(TCSheet, i, 3, "fail", outputpath);
			}
			
		}
		else
		{
			//write as blocked for testcases which are flag to N
			xl.setCellData(TCSheet, i, 3, "Blocked", outputpath);
		}
	}
	
}
}
