package commonFunctions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	//method for launching browser
	public static WebDriver startBrowser() throws Throwable
	{
		conpro = new Properties();
		conpro.load(new FileInputStream("PropertyFiles\\Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		else
		{
			Reporter.log("Browser value is not matching");
		}
		return driver;
	}
	//method for launching url
	public static void openUrl()
	{
		driver.get(conpro.getProperty("url"));
	}

	//method for wait to any element
		public static void waitForElement(String LocatorType,String LocatorValue,String TestData)
		{
			WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
			if(LocatorType.equalsIgnoreCase("xpath"))
			{
				//wait until element is visible
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
			}
			if(LocatorType.equalsIgnoreCase("name"))
			{
				//wait until element is visible
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
			}
			if(LocatorType.equalsIgnoreCase("id"))
			{
				//wait until element is visible
				mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
			}
		}
	//method for any Textbox
	public static void typeAction(String LocatorType,String Locatorvalue,String TestData)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).clear();
			driver.findElement(By.xpath(Locatorvalue)).sendKeys(TestData);
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).clear();
			driver.findElement(By.id(Locatorvalue)).sendKeys(TestData);
		}

		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).clear();
			driver.findElement(By.name(Locatorvalue)).sendKeys(TestData);
		}
	}
	//method for buttons,radiobutons,checkbox,images,links
	public static void clickAction(String LocatorType,String Locatorvalue)
	{
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Locatorvalue)).click();
		}

		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Locatorvalue)).click();
		}
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);
		}

	}
	//method for validate page title
	public static void validateTitle(String Expected_Title)
	{
		String Actual_title = driver.getTitle();
		try {
			Assert.assertEquals(Actual_title, Expected_Title,"Title is not matching");
		}catch(AssertionError a)
		{
			System.out.println(a.getMessage());
		}

	}

	//method for close browser
	public static void closeBrowser()
	{
		driver.quit();
	}

}
