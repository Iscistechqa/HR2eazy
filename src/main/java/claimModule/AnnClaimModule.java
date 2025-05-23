package claimModule;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AnnClaimModule {

	@Test
	public void annClaimModule() {

		boolean type = true;
		String selectClient = "Daikin Customer";
		String selectProject = "Daikin project";
		String date = "22-May-2025";
		String clientType = "Ann Claim Type";
		String personType = "Self"; // ["Self", "Spouse", "Children"]
		String receiptNo = "42423113";
		String bankName = "Test Automation Bank";
		String accNo = "3442444446";
		String receiptDate = "23-May-2025";
		String claimAmount = "270";
		String Description = "fbvfvfbsbvfbsdfvsdfvdscxvsgrbgtenhyjngdvsbfgndf";

		// initialize the chrome driver
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://qaen.hr2eazy.com/Default.aspx");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// Login Module
		// enter username
		driver.findElement(By.xpath("//input[@id='txtLanId']")).sendKeys("lax");
		// enter password
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys("qaz4321");
		// click login
		driver.findElement(By.xpath("//input[@id='btnSubmit']")).click();

		// wait for page load
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

		// check pop-up it's displayed
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='×'])[4]")));
			driver.findElement(By.xpath("(//button[text()='×'])[4]")).click();
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
		} catch (Exception e) {
			System.out.println("Pop-up it's not displayed " + e.getMessage());
		}

		// click claim button
		driver.findElement(By.xpath("//a[@id='ctl00_ContentPlaceHolder1_lnkClaims']")).click();
		wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Claims Entry']")));

		if (type) {
			// default select - single options
			System.out.println("Default select - single options");

		} else {
			// we are going to select - Multiple options
			driver.findElement(By.xpath("//label[text()='Multiple']")).click();
			System.out.println("Multiple option clicked successfully");
		}

		// client drowndown
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Client']")));
			System.out.println("Client option it's there");
			WebElement selectDropDown = driver.findElement(By.xpath(
					"//select[@id='ctl00_ContentPlaceHolder1_ddlClient'] | //select[@id='ctl00_ContentPlaceHolder1_ddlClientMultiple']"));
			Select selectOption = new Select(selectDropDown);
			selectOption.selectByVisibleText(selectClient);
		} catch (Exception e) {
			System.out.println("Client option it's not there");
		}

		// project dropdown
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Project']")));
			WebElement selectDropDown = driver.findElement(By.xpath(
					"//select[@id='ctl00_ContentPlaceHolder1_ddlProject'] | //select[@id='ctl00_ContentPlaceHolder1_ddlMultipleProject']"));
			Select selectOption = new Select(selectDropDown);
			selectOption.selectByVisibleText(selectProject);
		} catch (Exception e) {
			System.out.println("Project option it's not there");
		}

		// ogjective option in multiple field
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Objective']")));
			driver.findElement(By.xpath("//textarea[@id='ctl00_ContentPlaceHolder1_txtMultipleObjective']"))
					.sendKeys("Automation Testing...");
		} catch (Exception e) {
			System.out.println("Objective option it's not there");
		}

		// select date

		try {
			WebElement workDate = driver.findElement(By.xpath("//input[@format='dd-mmm-yyyy']"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			// Change the value attribute using JavaScript
			js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, date);

		} catch (Exception e) {
			System.out.println("Date options it's not available");

		}

		// select client type

		try {

			WebElement selectDropDown = driver.findElement(By.xpath(
					"//select[@id='ctl00_ContentPlaceHolder1_ddlClaimtype'] | //select[@id='ctl00_ContentPlaceHolder1_ddlClaimtypeMultiple']"));
			Select selectOption = new Select(selectDropDown);
			selectOption.selectByVisibleText(clientType);
		} catch (Exception e) {
			System.out.println("Client type options it's not available/ Element mismatched...");
		}

		// person - field
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Person']")));
			if (personType.equalsIgnoreCase("Self")) {
				System.out.println("We are going to click Self option");
				driver.findElement(By.xpath("//label[text()='Self']")).click();
			} else if (personType.equalsIgnoreCase("Spouse")) {
				System.out.println("We are going to click Spouse option");
				driver.findElement(By.xpath("//label[text()='Spouse']")).click();
			} else {
				System.out.println("We are going to click Children option");
				driver.findElement(By.xpath("//label[text()='Children']")).click();
				
				// children type
//				try {
//					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Children']")));
//					WebElement selectDropDown = driver.findElement(By.xpath(
//							"//select[@id='ctl00_ContentPlaceHolder1_ddlSingleChildren'] | //select[@id='ctl00_ContentPlaceHolder1_ddlMultipleChildren']"));
//					Select selectOption = new Select(selectDropDown);
//					selectOption.selectByVisibleText(clientType);
//				} catch (Exception e) {
//					System.out.println("Children option it's not there");
//				}

			}

		} catch (Exception e) {
			System.out.println("Person field it's no there");
		}

		// enter receipt no
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Receipt No']")));
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtReceiptNoMultiple'] | //input[@id='ctl00_ContentPlaceHolder1_txtReceiptNo']"))
					.sendKeys(receiptNo);

		} catch (Exception e) {
			System.out.println("Receipt no it's not there");
		}

		// bank name
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//span[@id='ctl00_ContentPlaceHolder1_lbl_BankName'] | //span[@id='ctl00_ContentPlaceHolder1_lblBank_Name']")));
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtBankNameMultiple'] | //input[@id='ctl00_ContentPlaceHolder1_txtBankName']"))
					.clear();
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtBankNameMultiple'] | //input[@id='ctl00_ContentPlaceHolder1_txtBankName']"))
					.sendKeys(bankName);
		} catch (Exception e) {
			System.out.println("Bank name option it's not there");
		}

		// account no
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Account Number']")));
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtAccountNumber'] | //input[@id='ctl00_ContentPlaceHolder1_txtAccountNumberMultiple']"))
					.clear();
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtAccountNumber'] | //input[@id='ctl00_ContentPlaceHolder1_txtAccountNumberMultiple']"))
					.sendKeys(accNo);
		} catch (Exception e) {
			System.out.println("Account no option it's not there");
		}

		// receipt date
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Receipt Date']")));
			WebElement workDate = driver.findElement(By.xpath("(//input[@format='dd-mmm-yyyy'])[2]"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			// Change the value attribute using JavaScript
			js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, receiptDate);

		} catch (Exception e) {
			System.out.println("Receipt no option it's not there");
		}

		// claim amount
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Claim Amount']")));
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_txtClaimAmount'] | //input[@id='ctl00_ContentPlaceHolder1_txtClaimAmountMultiple']"))
					.sendKeys(claimAmount);
		} catch (Exception e) {
			System.out.println("Claim amout option it's not there");
		}

		// description
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Description']")));
			driver.findElement(By.xpath(
					"//textarea[@id='ctl00_ContentPlaceHolder1_txtDescriptionMultiple'] | //textarea[@id='ctl00_ContentPlaceHolder1_txtDescription']"))
					.sendKeys(Description);
		} catch (Exception e) {
			System.out.println("Description option it's not there");
		}

		// attachment
		try {
			WebElement uploadFile = driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_fileupload'] | //input[@id='ctl00_ContentPlaceHolder1_fileuploadMultiple']"));
			File file = new File("Documents/transaction_report (3).pdf");
			String absolutePath = file.getAbsolutePath();

			// Upload the file
			uploadFile.sendKeys(absolutePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		// terms and conditions 
//		try {
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm']")));
//			driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm']")).click();
//			System.out.println("Terms and condition option clicked..");
//		} catch (Exception e) {
//			System.out.println("Terms and Condition it's not available");
//		}
		
		
	}

}
