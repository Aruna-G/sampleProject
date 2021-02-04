package sampleProject.sampleProject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * Hello world!
 *
 */
public class App 
{
	public static Logger log = Logger.getLogger(App.class.getName());
	static com.relevantcodes.extentreports.ExtentTest test;
	static com.relevantcodes.extentreports.ExtentReports report;
	String filePath = System.getProperty("user.dir")+"\\TestData\\Datasheet.xlsx";

	static WebDriver driver;


	@BeforeClass
	public static void startTest()
	{
		report = new com.relevantcodes.extentreports.ExtentReports(System.getProperty("user.dir")+".\\ExtentReportResults.html");

		test = report.startTest("ExtentDemo");


	}

	public static String capture(WebDriver driver) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Dest = new File("src/../ErrImages/" + System.currentTimeMillis()
		+ ".png");
		String errflpath = Dest.getAbsolutePath();
		FileUtils.copyFile(scrFile, Dest);
		return errflpath;}

	@Test
	public void extentReportsDemo() throws IOException, InterruptedException
	{

		String uiupdatedvalue = null;
		WebDriverManager.chromedriver().setup(); 
		driver = new ChromeDriver();
		driver.get("http://playactio.com:8087/");
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		WebDriverWait w = new WebDriverWait(driver,20);
		w.until(ExpectedConditions.titleIs(driver.getTitle()));

		if(driver.getTitle().equalsIgnoreCase("Welcome | ACTIO")) {
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Login Title");
		}else {test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Login Title");}
		
		/*
		 * Assert.assertEquals(driver.getTitle(), "Welcome | ACTIO");
		 * test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+
		 * "Login Title");
		 */

		driver.findElement(By.name("userName")).sendKeys(getExcelData(filePath,"Sheet1","UserName"));
		test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "UserName is entered successfully");

		driver.findElement(By.name("password")).sendKeys(getExcelData(filePath,"Sheet1","Password"));
		test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Password is entered successfully");

		driver.findElement(By.xpath("//button[text()=' Login ']")).click();
		test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Login button clicked successfully");

		/*
		 * Assert.assertEquals(driver.getTitle(), "Welcome | ACTIO");
		 * test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+
		 * "HomePage Title");
		 */
		if(driver.getTitle().equalsIgnoreCase("Welcome | ACTIO")) {
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "HomePage Title");
		}else {test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "HomePage Title");}
		


		//Clicking MAster
		w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[text()='Master ']"))));
		driver.findElement(By.xpath("//span[text()='Master ']")).click();
		test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Master is Clicked");

		//Clicking Venue
		driver.findElement(By.xpath("//a[text()=' Venue ']")).click();
		test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Venue is Clicked");

		w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h5[text()='Venue List']"))));
		if(driver.findElement(By.xpath("//h5[text()='Venue List']")).isDisplayed()) {
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Venue List tab is opened");

			w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//button[text()=' Create Venue ']"))));
			driver.findElement(By.xpath("//button[text()=' Create Venue ']")).click();
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Create Venue button is Clicked");

			if(driver.findElement(By.xpath("//h5[text()='Create Venue']")).isDisplayed()) {
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Create Venue Section is displayed...");
				String uuid = UUID.randomUUID().toString();

				//Now this uuid enter to your text box

				driver.findElement(By.name("venue_name")).sendKeys(uuid);
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Entered Venue name...");


				//Selected Venue Type
				WebElement element = driver.findElements(By.name("ventype")).get(0);
				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();

				WebElement element1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","Venue Type")+"')]")).get(0);
				Actions actions1 = new Actions(driver);
				actions1.moveToElement(element1).click().build().perform();
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected Venue Type...");

				//Entered address
				driver.findElement(By.name("address")).sendKeys(getExcelData(filePath,"Sheet1","Address 1"));
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Entered Address...");

				//country 
				WebElement countryelement = driver.findElements(By.name("country")).get(0);
				Actions conactions = new Actions(driver);
				conactions.moveToElement(countryelement).click().build().perform();

				WebElement countryelement1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","Country")+"')]")).get(0);
				Actions conactions1 = new Actions(driver);
				conactions1.moveToElement(countryelement1).click().build().perform();
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected Country...");

				//state
				WebElement stateelement = driver.findElements(By.name("state")).get(0);
				Actions stateactions = new Actions(driver);
				stateactions.moveToElement(stateelement).click().build().perform();

				WebElement stateelement1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","State")+"')]")).get(0);
				Actions stateactions1 = new Actions(driver);
				stateactions1.moveToElement(stateelement1).click().build().perform();
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected State...");

				//city
				WebElement cityelement = driver.findElements(By.name("city")).get(0);
				Actions cityactions = new Actions(driver);
				cityactions.moveToElement(cityelement).click().build().perform();

				WebElement cityelement1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","City")+"')]")).get(0);
				Actions cityactions1 = new Actions(driver);
				cityactions1.moveToElement(cityelement1).click().build().perform();
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected City...");

				driver.findElement(By.name("zipcode")).sendKeys(getExcelData(filePath,"Sheet1","Zipcode"));
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Entered Zipcode...");

				//Switching to frame
				Thread.sleep(2000);
				driver.switchTo().frame(0);
				driver.findElement(By.xpath("//body[@id='tinymce']")).sendKeys(getExcelData(filePath,"Sheet1","Description"));
				driver.switchTo().defaultContent();


				driver.findElement(By.xpath("//button[text()=' Add Play Area ']")).click();
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked Add Play Area button...");

				if(driver.findElement(By.xpath("//h5[text()='Add Play Area']")).isDisplayed()) {
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Add play Area section is displayed...");

					driver.findElement(By.name("fieldname")).sendKeys(getExcelData(filePath,"Sheet1","Play Area"));
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Entered Play Area..");

					//Surface Type
					WebElement surfaceTypeelement = driver.findElements(By.name("surfacetype")).get(0);
					Actions surfaceactions = new Actions(driver);
					surfaceactions.moveToElement(surfaceTypeelement).click().build().perform();

					WebElement surfaceTypeelement1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","Surface Type")+"')]")).get(0);
					Actions surfaceactions1 = new Actions(driver);
					surfaceactions1.moveToElement(surfaceTypeelement1).click().build().perform();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected Surface Type...");

					//Play sport

					WebElement playSporteelement = driver.findElements(By.name("playsports")).get(0);
					Actions playeactions = new Actions(driver);
					playeactions.moveToElement(playSporteelement).click().build().perform();

					WebElement playSporteelement1 = driver.findElements(By.xpath("//span[contains(.,'"+getExcelData(filePath,"Sheet1","Sport Name")+"')]")).get(0);
					Actions playactions1 = new Actions(driver);
					playactions1.moveToElement(playSporteelement1).click().build().perform();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Selected Sports Name...");

					driver.findElement(By.xpath("//button[@class='btn btn-info justify-content-center']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked Save button...");

					driver.findElement(By.xpath("//button[@class='btn btn-info text-center']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked final Save button...");

					String successmsg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

					Assert.assertEquals(successmsg, "Venue Added Successfully");
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Created Venue Success msg vlaidated");

					driver.findElement(By.xpath("//button[text()='OK']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked OK button...");

					driver.findElement(By.xpath("(//button[contains(.,' Edit ')])[1]")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked Edit button...");

					if(driver.findElement(By.xpath("//h5[text()='Edit Venue']")).isDisplayed())
					{
						test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Edit Venue section is displayed...");
						driver.findElement(By.name("venue_name")).click();
						driver.findElement(By.name("venue_name")).clear();
						driver.findElement(By.name("venue_name")).sendKeys(uuid);
						Thread.sleep(2000);
						uiupdatedvalue = uuid;
					}

					driver.findElement(By.xpath("//button[@class='btn btn-info text-center']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked save button...");

					w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@id='swal2-content']"))));
					String Editsuccessmsg = driver.findElement(By.xpath("//div[@id='swal2-content']")).getText();

					Assert.assertEquals(Editsuccessmsg, "Venue Updated Successfully");
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Updated Venue Success msg vlaidated");
					driver.findElement(By.xpath("//button[text()='OK']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Clicked OK button...");
					String updatedvalue = driver.findElement(By.xpath("(//div[@class='wrap-text'])[1]")).getText();
					Assert.assertEquals(uiupdatedvalue, updatedvalue);
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "Updated Value is displayed successfully...");

					w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(//a[@class='dropdown-toggle ng-tns-c84-0']/i)[2]"))));
					driver.findElement(By.xpath("(//a[@class='dropdown-toggle ng-tns-c84-0']/i)[2]")).click();
					w.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//i[@class='feather icon-log-out ng-tns-c84-0']"))));
					driver.findElement(By.xpath("//i[@class='feather icon-log-out ng-tns-c84-0']")).click();
					test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+ "LogOut is successfull....");
				}else {test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Add Play area Section is NOT displayed...");}

			}else {test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Create Venue Section is NOT displayed...");}

		}else {test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+ "Venue List tab is NOT opened");}


	}
	@AfterClass
	public static void endTest()
	{
		driver.quit();
		report.endTest(test);
		report.flush();
	}

	public static String getExcelData(String fileName, String sheetName, String cellname) throws IOException {
		String Cellvalue = null;
		FileInputStream fis = new FileInputStream(fileName);
		XSSFWorkbook wb= new XSSFWorkbook(fis);
		XSSFSheet sheet = wb.getSheet(sheetName);
		XSSFCell cell;


		for(int i=0; i<=sheet.getPhysicalNumberOfRows(); i++) {
			if(sheet.getRow(i).getCell(0).getStringCellValue().equalsIgnoreCase(cellname)) {
				cell = sheet.getRow(i).getCell(1);
				Cellvalue = cell.getStringCellValue();
				break;
			}
		}

		wb.close();
		fis.close();
		return Cellvalue.trim();
	}

}
