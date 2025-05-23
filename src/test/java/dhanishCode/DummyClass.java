package dhanishCode;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DummyClass {
	public static void main(String[] args) throws InterruptedException {

		String userName = "ann";
		String userPassword = "qaz4321";
		boolean WorkedOnDays = false;

		// Initialize the Chrome Driver
		// WebDriverManager.chromedriver().setup();
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
		driver.findElement(By.xpath("(//*[@type='button'])[41]")).click();

		try {
			WebElement clickReplacementleaveModule = driver
					.findElement(By.id("ctl00_ContentPlaceHolder1_divReplacementLeave"));
			clickReplacementleaveModule.click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		WebElement applyNewBtn = driver.findElement(By.id("ctl00_ContentPlaceHolder1_BtnApply"));
		applyNewBtn.click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

		if (WorkedOnDays) {
			driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_rbtWorkOnDays_0']")).isSelected();
			System.out.println("Default Select is Non Working day");
		} else {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Public Holiday']")));

			driver.findElement(By.xpath("//label[text()='Public Holiday']")).click();
			// driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_rbtWorkOnDays_1']")).isSelected();
			System.out.println("Public Holiday radio button it's selected");

		}
	}
}
