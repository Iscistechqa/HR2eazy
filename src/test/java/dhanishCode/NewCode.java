package dhanishCode;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewCode {
	
	public static void main(String[] args) throws InterruptedException {



		 String userName = "ann";

		 String userPassword = "qaz4321";

		 boolean WorkedOnDays = false;

		 String selectDaysValue = "Celebration";

		 boolean WorkDuration = false;

		 boolean HalfDay = true;

		 boolean LeaveSession = false;

		 String fromDate = "20-Mar-2025";

		 String toDate = "20-Mar-2025";

		 String fromTime = "06:00";

		 String toTime = "09:00";

		 String leaveReason = "Testing entry";

		 // Initialize the Chrome Driver

		 // WebDriverManager.chromedriver().setup(); --4.0 version we dont need to

		 // initialize webdriver setup this this update

		 ChromeDriver driver = new ChromeDriver();

		 driver.manage().window().maximize();

		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		 driver.get("https://qaen.hr2eazy.com/");



		 // Login Module

		 // Enter Username

		 driver.findElement(By.xpath("//input[@id='txtLanId']")).sendKeys(userName);

		 // Enter Password

		 driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(userPassword);

		 // Click Login button

		 driver.findElement(By.xpath("//*[@id='btnSubmit']")).click();

		 // click Approval Pending Popup



		 try {

		 driver.findElement(By.xpath("(//*[@type='button'])[41]")).click();

		 } catch (Exception e) {

		 e.printStackTrace();

		 System.out.println("Expection " + e);

		 }



		 // driver.findElement(By.xpath("(//*[@type='button'])[41]")).click();



		 WebElement clickReplacementleaveModule = driver

		 .findElement(By.id("ctl00_ContentPlaceHolder1_divReplacementLeave"));

		 clickReplacementleaveModule.click();



		 WebElement applyNewBtn = driver.findElement(By.id("ctl00_ContentPlaceHolder1_BtnApply"));

		 applyNewBtn.click();

		 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));



		 wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));



		 if (WorkedOnDays) {

		 driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_rbtWorkOnDays_0']")).isSelected();

		 System.out.println("Default Select is Non Working day");



		 WebElement selectDropDown = driver.findElement(By.id("ctl00_ContentPlaceHolder1_ddlNonWorkingDays"));

		 Select s = new Select(selectDropDown);

		 s.selectByVisibleText(selectDaysValue);



		 if (WorkDuration) {



		 System.out.println("default selection is Days");

		 if (HalfDay) {

		 driver.findElement(By.id("ctl00_ContentPlaceHolder1_chkHalfday")).click();

		 if (LeaveSession) {

		 System.out.println("Default Leave Session is Morning");

		 driver.findElement(By.xpath("//*[text()='Morning']")).click();

		 WebElement workDate = driver.findElement(By.xpath("//*[@format='dd-mmm-yyyy']"));

		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, fromDate);

		 } else {

		 driver.findElement(By.xpath("//*[text()='Evening']")).click();

		 WebElement workDate = driver.findElement(By.xpath("//*[@format='dd-mmm-yyyy']"));

		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, fromDate);

		 }



		 } else {

		 System.out.println("Half Day is Not Enabled");

		 WebElement workDate = driver.findElement(By.xpath("//*[@format='dd-mmm-yyyy']"));

		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, fromDate);

		 }



		 } else {

		 System.out.println("Hours option is Enabled");

		 driver.findElement(By.xpath("//*[text()='Hours']")).click();

		 WebElement workDate = driver.findElement(By.xpath("(//*[@format='dd-mmm-yyyy'])[1]"));

		 JavascriptExecutor js = (JavascriptExecutor) driver;

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, fromDate);

		 WebElement workDate1 = driver.findElement(By.xpath("(//*[@format='dd-mmm-yyyy'])[2]"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate1, toDate);

		 WebElement fromTimes = driver

		 .findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursFrom']"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", fromTimes, fromTime);

		 WebElement toTimes = driver

		 .findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursTo']"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", toTimes, toTime);

		 }

		 } else {



		 wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Public Holiday']")));



		 driver.findElement(By.xpath("//label[text()='Public Holiday']")).click();



		 System.out.println("Public Holiday radio button it's selected");

		 WebElement selectDropDown = driver.findElement(By.id("ctl00_ContentPlaceHolder1_ddlPublicHoliDay"));

		 Select s = new Select(selectDropDown);

		 s.selectByVisibleText(selectDaysValue);

		 if (WorkDuration) {



		 System.out.println("default selection is Days");

		 if (HalfDay) {

		 driver.findElement(By.id("ctl00_ContentPlaceHolder1_chkHalfday")).click();

		 if (LeaveSession) {

		 System.out.println("Default Leave Session is Morning");

		 driver.findElement(By.xpath("//*[text()='Morning']")).click();



		 } else {

		 driver.findElement(By.xpath("//*[text()='Evening']")).click();



		 }



		 } else {

		 System.out.println("Half Day is Not Enabled");



		 }



		 } else {

		 System.out.println("Hours option is Enabled");

		 driver.findElement(By.xpath("//*[text()='Hours']")).click();



		 JavascriptExecutor js = (JavascriptExecutor) driver;



		 WebElement workDate = driver.findElement(By.xpath("//*[@format='dd-mmm-yyyy']"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, toDate);

		 WebElement fromTimes = driver

		 .findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursFrom']"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", fromTimes, fromTime);

		 WebElement toTimes = driver

		 .findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursTo']"));

		 js.executeScript("arguments[0].setAttribute('value', arguments[1])", toTimes, toTime);

		 }



		 }



		 WebElement reason = driver.findElement(By.id("ctl00_ContentPlaceHolder1_txtReason"));

		 reason.sendKeys(leaveReason);

		 driver.findElement(By.id("ctl00_ContentPlaceHolder1_btnSubmit")).click();

		 System.out.println(" Leave applied is successfully");

		 }



		
	
}
