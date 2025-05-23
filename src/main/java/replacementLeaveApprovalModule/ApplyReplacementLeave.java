package replacementLeaveApprovalModule;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApplyReplacementLeave {
	
	
	@Test(dataProvider = "getData", dataProviderClass = DataUtilis.class)
	public void applyReplacementLeave(String []data) throws InterruptedException {
		String employeeUserName = data[0];
		String employeePassword = data[1];
		String workDay = data[2];
		boolean workDays = Boolean.parseBoolean(workDay);
		String selectDaysValue = data[3];
		String duration = data[4];
		boolean workDuaration = Boolean.parseBoolean(duration);
		String date = data[5];
		String halfDayLeave = data[6];
		boolean halfDay = Boolean.parseBoolean(halfDayLeave);
		String leaveSession = data[7];
		String toDate = data[8];
		String fromTime = data[9];
		String toTime = data[10];
	
		
		
		// initialize the chrome driver
				ChromeDriver driver = new ChromeDriver();
				driver.manage().window().maximize();
				driver.get("https://qaen.hr2eazy.com/Default.aspx");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

				// Login Module
				// enter username
				driver.findElement(By.xpath("//input[@id='txtLanId']")).sendKeys(employeeUserName);
				// enter password
				driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(employeePassword);
				// click login
				driver.findElement(By.xpath("//input[@id='btnSubmit']")).click();

				//
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));

				// String title = driver.getTitle();
				// System.out.println(title);

				// click replacement leave button
				driver.findElement(By.xpath("//a[@id='ctl00_ContentPlaceHolder1_lnkReplacementLeave']")).click();
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@value='Apply New']")));
				
				// click apply new button
				driver.findElement(By.xpath("//input[@class='buttonnew buttonnew1']")).click();
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Worked On Days']")));

				// String applyReplacementLeaveTitle = driver.getTitle();
				// System.out.println(applyReplacementLeaveTitle);

				// Worked on Days Flow
				if(workDays) {
				// If it is true - Default Select is Non Working Days
				boolean selectedNonWorkingDays = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_rbtWorkOnDays_0']")).isSelected();
				Assert.assertEquals(true, selectedNonWorkingDays);
				System.out.println("Default Select is Non Working Days");

				}else {
				// If it is false - we are going to select public holiday
				driver.findElement(By.xpath("//label[text()='Public Holiday']")).click();
				boolean selectedPublicHoliday = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_rbtWorkOnDays_1']")).isSelected();
				Assert.assertEquals(true, selectedPublicHoliday);
				System.out.println("Public Holiday radio button it's selected");
				}
				
				// select drop-down in working on days
				try {
					WebElement selectDropDown = driver.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlNonWorkingDays'] | //select[@id='ctl00_ContentPlaceHolder1_ddlPublicHoliDay']"));
					Select selectOption = new Select(selectDropDown);
					selectOption.selectByVisibleText(selectDaysValue);
				} catch (Exception e) {
					WebElement selectDropDown1 = driver.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlNonWorkingDays'] | //select[@id='ctl00_ContentPlaceHolder1_ddlPublicHoliDay']"));
					Select selectOption1 = new Select(selectDropDown1);
					selectOption1.selectByIndex(1);
				}
				
				// work duration
				if(workDuaration) {
					// default selection - Days options
					System.out.println("Default Selection - Days Options");
				}else {
					// we need to change hours duration
					driver.findElement(By.xpath("//label[text()='Hours']")).click();
					System.out.println("Selected - Hours Options");
					Thread.sleep(5000);
				}
				
				// work date
				if(workDays) {
					WebElement workDate = driver.findElement(By.xpath("//input[@format='dd-mmm-yyyy']"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					// Change the value attribute using JavaScript
					js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, date);
				}else {
					System.out.println("We can't able to edit in public holiday");
				}
				

				// hours 
				if(workDuaration) {
					System.out.println("Check the Half day condition");
					// half day conditions
					if(halfDay) {
						// click the half day checkbox
						driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_chkHalfday']")).click();
						// click the leave session
						WebElement clickLeaveSession = driver.findElement(By.xpath("//label[text()='" + leaveSession + "']"));
						clickLeaveSession.click();
					}else {
						System.out.println("Ignore this step");
					}
					
				}else {
					System.out.println("We are selected hours options so we are going to select some time");
					JavascriptExecutor js = (JavascriptExecutor) driver;
					if(workDays) {
						// select - from field
						WebElement fromDateForHour = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtFrom']"));
						js.executeScript("arguments[0].setAttribute('value', arguments[1])", fromDateForHour, date);
						
						
						//  select - To date
						WebElement workDate = driver.findElement(By.xpath("(//input[@format='dd-mmm-yyyy'])[2]"));
						
						// Change the value attribute using JavaScript
						js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, toDate);
					}else {
						@Nullable
						String attribute = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtFrom']")).getAttribute("value");
						System.out.println(attribute);
					}
					
					
					// select - Hours - From Time
					WebElement fromTimes = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursFrom']"));
					js.executeScript("arguments[0].setAttribute('value', arguments[1])", fromTimes, fromTime);
					
					// select - hours - To Time
					WebElement toTimes = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtHoursTo']"));
					js.executeScript("arguments[0].setAttribute('value', arguments[1])", toTimes, toTime);
					
				}
				
				// enter reason
				driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_txtReason']")).sendKeys("Automation Test");
				
				// upload attachment
				
				try {
					WebElement uploadFile = driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_fileupload']"));
					File file = new File("Documents/transaction_report (3).pdf");
					 String absolutePath = file.getAbsolutePath();

					 // Upload the file
					 uploadFile.sendKeys(absolutePath);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 
				 // submit button
				  driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnSubmit']")).click();
				  
				  // wait for successful message
				  try {
					  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Record saved successfully.']")));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				  
				 // navigate back to check record has been stored in grid
				  driver.findElement(By.xpath("//a[text()='Replacement Leave']")).click();
				  wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
				  
				  // Grid - we need to check all replacement leave successfully applied or not.
				 if(workDuaration) {
					 System.out.println("User applied replacement leave based on the Days");
					 if(workDays) {
						 // date checking
						 List<WebElement> elements = driver.findElements(By.xpath("//tr/td[2]"));
						 int countOfDate = elements.size();
						 // Replace all hyphens with spaces
					       String formattedDate = date.replace("-", " ");
						 for(int i=0; i<=countOfDate; i++) {
							 String grabActualDate = elements.get(i).getText();
							 if(grabActualDate.equals(formattedDate)) {
								 System.out.println(grabActualDate + " successfully registered");
								 break;
							 }else {
								 System.out.println(grabActualDate + " not registered successfully");
							 }
						 }
					 }else {
						 // festival checking
						 List<WebElement> elements = driver.findElements(By.xpath("//tr/td[5]"));
						 int countOfLeaveDes = elements.size();
						 for(int i=0; i<=countOfLeaveDes; i++) {
							 String grabLeaveDes = elements.get(i).getText();
							 if(grabLeaveDes.equalsIgnoreCase(selectDaysValue)) {
								 System.out.println(grabLeaveDes + " successfully registered");
								 break;
							 }else {
								 System.out.println(grabLeaveDes + " not registered successfully");
							 }
						 }
					 }
				 }else {
					 System.out.println("User applied replacement leave based on the Hours");
					 if(workDays) {
						 List<WebElement> elements = driver.findElements(By.xpath("//tr/td[2]"));
						 int countOfDate = elements.size();
						 // Replace all hyphens with spaces
					       String formattedDate = date.replace("-", " ");
						 for(int i=0; i<=countOfDate; i++) {
							 String grabActualDate = elements.get(i).getText();
							 if(grabActualDate.equals(formattedDate)) {
								 System.out.println(grabActualDate + " successfully registered");
								 break;
							 }else {
								 System.out.println(grabActualDate + " not registered successfully");
							 }
						 }
					 }else {
						 List<WebElement> elements = driver.findElements(By.xpath("//tr/td[6]"));
						 int countOfLeaveDes = elements.size();
						 for(int i=0; i<=countOfLeaveDes; i++) {
							 String grabLeaveDes = elements.get(i).getText();
							 if(grabLeaveDes.equalsIgnoreCase(selectDaysValue)) {
								 System.out.println(grabLeaveDes + " successfully registered");
								 break;
							 }else {
								 System.out.println(grabLeaveDes + " not registered successfully");
							 }
						 }
					 }
				 }
				  
				 driver.quit();
				 
			}

// Test Case 2 - approver leave
		@Test(dataProvider = "getData", dataProviderClass = DataUtilis.class, dependsOnMethods = {"applyReplacementLeave"})
		public void approverLeaveModule(String[] data) {
			System.out.println("It's started");
			String approverUserName = data[11];
			String approverPassword = data[12];
			String duration = data[4];
			boolean workDuaration = Boolean.parseBoolean(duration);
			String workDay = data[2];
			boolean workDays = Boolean.parseBoolean(workDay);
			String selectDaysValue = data[3];
			String date = data[5];
			// change the date format for assertions
			String formattedDate = date.replace("-", " ");
			String approveReject = data[13];
			boolean statusApproveReject = Boolean.parseBoolean(approveReject);
			
			// initialize the chrome driver
			ChromeDriver driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.get("https://qaen.hr2eazy.com/Default.aspx");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));

			// Login Module
			// enter username
			driver.findElement(By.xpath("//input[@id='txtLanId']")).sendKeys(approverUserName);
			// enter password
			driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(approverPassword);
			// click login
			driver.findElement(By.xpath("//input[@id='btnSubmit']")).click();
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
			
			// check pop-up it's displayed
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='×'])[4]")));	
				driver.findElement(By.xpath("(//button[text()='×'])[4]")).click();
				wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Pop-up it's not displayed " + e.getMessage());
			}
			
			// click approval
			try {
				driver.findElement(By.xpath("//a[text()='Approval']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Approval']")));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// click apply replacement leave 
			try {
				driver.findElement(By.xpath("//a[@id='ctl00_ContentPlaceHolder1_tvApprovalDetailt12']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Year']")));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
			// wait for some element to be present
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ctl00_ContentPlaceHolder1_LblYear']")));
			
			// first check days or hours
			if(workDuaration) {
				System.out.println("Default select option in Days");
				// check the workdays - if it's true it will search date and if it's false it will search public holiday
				if(workDays) {
					System.out.println("Based on date we are going to approve/reject");
					List<WebElement> elements = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeave']/tbody/tr/td[3]"));
					for(int i=0;i<=elements.size();i++) {
						String dateVerification = elements.get(i).getText();
						if(dateVerification.equals(formattedDate)) {
							System.out.println(dateVerification + " it's matches");
							List<WebElement> elements2 = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeave']/tbody/tr/td[1]"));
							elements2.get(i).click();
							System.out.println("Check-box clicked successfully");
							break;
						}else {
							System.out.println("Check-box doesn't clicked successfully");
						}
					}
					
				}else {
					System.out.println("Based on public holiday we are going to approve/reject");
					List<WebElement> elements = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeave']/tbody/tr/td[8]"));
					for(int i=0;i<=elements.size();i++) {
						String publicHolidayVerification = elements.get(i).getText();
						if(publicHolidayVerification.equals(selectDaysValue)) {
							System.out.println(publicHolidayVerification + " it's matches");
							List<WebElement> elements2 = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeave']/tbody/tr/td[1]"));
							elements2.get(i).click();
							System.out.println("Check-box clicked successfully");
							break;
						}else {
							System.out.println("Check-box doesn't clicked successfully");
						}
					}
				}
				
			}else {
				System.out.println("We are going to select Hours options in Type option");
				driver.findElement(By.xpath("//label[text()='Hours']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='ctl00_ContentPlaceHolder1_lblReplacementLeave']")));
				// check the workdays - if it's true it will search date and if it's false it will search public holiday
				if(workDays) {
					System.out.println("Based on date we are going to approve/reject");
					List<WebElement> elements = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeaveHours']/tbody/tr/td[3]"));
					for(int i=0;i<=elements.size();i++) {
						String dateVerification = elements.get(i).getText();
						if(dateVerification.equals(formattedDate)) {
							System.out.println(dateVerification + " it's matches");
							List<WebElement> elements2 = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeaveHours']/tbody/tr/td[1]"));
							elements2.get(i).click();
							System.out.println("Check-box clicked successfully");
							break;
						}else {
							System.out.println("Check-box doesn't clicked successfully");
						}
					}
					
				}else {
					System.out.println("Based on public holiday we are going to approve/reject");
					List<WebElement> elements = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeaveHours']/tbody/tr/td[9]"));
					
					for(int i=0;i<=elements.size();i++) {
						String publicHolidayVerification = elements.get(i).getText();
						if(publicHolidayVerification.equals(selectDaysValue)) {
							System.out.println(publicHolidayVerification + " it's matches");
							List<WebElement> elements2 = driver.findElements(By.xpath("//table[@id='ctl00_ContentPlaceHolder1_GvReplacementLeaveHours']/tbody/tr/td[1]"));
							elements2.get(i).click();
							System.out.println("Check-box clicked successfully");
							break;
						}else {
							System.out.println("Check-box doesn't clicked successfully");
						}
					}
				}
				
			}
			
			// approve/reject flow
//			if(statusApproveReject) {
//				System.out.println("We are going to approve");
//				driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnApproveDown']")).click();
//				System.out.println("Approved Successfully");
//			}else {
//				System.out.println("We are going to reject");
//				driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_btnRejectDown']")).click();
//				System.out.println("Reject Successfully");
//			}
			
		// 	driver.quit();
		}
	}


