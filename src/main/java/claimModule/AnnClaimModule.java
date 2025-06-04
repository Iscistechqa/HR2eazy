package claimModule;

import java.io.File;
import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import replacementLeaveApprovalModule.DataUtilis;

public class AnnClaimModule {

	public static int generateRandomNumber(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	public static String generateAccountNumber(int length) {
		Random rand = new Random();
		StringBuilder accountNumber = new StringBuilder();

		// Ensure first digit is non-zero
		accountNumber.append(rand.nextInt(9) + 1);

		for (int i = 1; i < length; i++) {
			accountNumber.append(rand.nextInt(10));
		}

		return accountNumber.toString();
	}

	// random account number generator

	@Test(dataProvider = "getData", dataProviderClass = DataUtilis.class)
	public void annClaimModule(String[] data) {

		// Read data from excel
		String userName = data[0];
		String password = data[1];
		String typeData = data[2];
		boolean type = Boolean.parseBoolean(typeData);
		String selectClient = data[3];
		String selectProject = data[4];
		String date = data[5];
		String clientType = data[6];
		String personType = data[7];
		String bankName = data[8];
		String receiptDate = data[9];
		String Description = data[10];
		String loopCountType = data[11];
		boolean loopCount = Boolean.parseBoolean(loopCountType);
		String noOfCount = data[12];
		int noOfCountLoop = Integer.parseInt(noOfCount);

		String receiptNo = generateAccountNumber(10);
		System.out.println("Generated Account Number: " + receiptNo);

		String accNo = generateAccountNumber(12);
		System.out.println("Generated Account Number: " + accNo);

		int randomNumber = generateRandomNumber(1, 10000);
		System.out.println("Random Number: " + randomNumber);
		String claimAmount = String.valueOf(randomNumber);

		// initialize the chrome driver
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://qaen.hr2eazy.com/Default.aspx");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		// Login Module
		// enter username
		driver.findElement(By.xpath("//input[@id='txtLanId']")).sendKeys(userName);
		// enter password
		driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(password);
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

		if (type) {
			// objective option in multiple field
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
					try {
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Children']")));
						WebElement selectDropDown = driver.findElement(By.xpath(
								"//select[@id='ctl00_ContentPlaceHolder1_ddlSingleChildren'] | //select[@id='ctl00_ContentPlaceHolder1_ddlMultipleChildren']"));
						Select selectOption = new Select(selectDropDown);
						selectOption.selectByIndex(1);
					} catch (Exception e) {
						System.out.println("Children option it's not there");
					}

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

			// gl-code
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='GL Code']")));
				WebElement glCode = driver
						.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlSingleGLCode']"));
				Select selectGlCode = new Select(glCode);
				selectGlCode.selectByIndex(1);
			} catch (Exception e) {
				System.out.println("GL-Code it's not available..");
			}

			// issuer type
			try {
				driver.findElement(By.xpath(
						"//input[@id='ctl00_ContentPlaceHolder1_txtIssuer'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleIssuer']"))
						.sendKeys("Automation Testing");
			} catch (Exception e) {
				System.out.println("Issuer type it's not there...");
			}

			// Detail field
			try {
				String textDetails = driver.findElement(By.xpath(
						"//span[@id='ctl00_ContentPlaceHolder1_lblSingleDetails'] | //span[@id='ctl00_ContentPlaceHolder1_lblMultipleDetails']"))
						.getText();
				if (textDetails.equals("Type")) {
					System.out.println("We are going to select dropdown options");
					WebElement elementType = driver.findElement(By.xpath(
							"//select[@id='ctl00_ContentPlaceHolder1_drpTypeDetails'] | //select[@id='ctl00_ContentPlaceHolder1_drpMultipleTypeDetails']"));
					Select select = new Select(elementType);
					select.selectByIndex(1);
				} else {
					System.out.println("We are going to enter text");
					driver.findElement(By.xpath(
							"//input[@id='ctl00_ContentPlaceHolder1_txtSingleDetails'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleDetails']"))
							.sendKeys("Automation Test");
				}
			} catch (Exception e) {
				System.out.println("Detail type it's not there...");
			}

			// Additional field
			try {
				driver.findElement(By.xpath(
						"//input[@id='ctl00_ContentPlaceHolder1_txtSingleAdditional'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleAdditional']"))
						.sendKeys("Automation Testing");
			} catch (Exception e) {
				System.out.println("Additional Field it's not there..");
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

			// add button
//			try {
//				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")));
//				driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")).click();
//			} catch (Exception e) {
//				System.out.println("Add button it's not there");
//			}

			// terms and conditions
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm']")));
				driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm']"))
						.click();
				System.out.println("Terms and condition option clicked..");
			} catch (Exception e) {
				System.out.println("Terms and Condition it's not available");
			}

		} else {
			if (loopCount && !type) {
				System.out.println("We are going to loop same client type based on loop number");

				for (int i = 0; i < noOfCountLoop; i++) {

					receiptNo = generateAccountNumber(10);
					System.out.println("Generated Account Number: " + receiptNo);

					randomNumber = generateRandomNumber(1, 10000);
					System.out.println("Random Number: " + randomNumber);
					claimAmount = String.valueOf(randomNumber);

					accNo = generateAccountNumber(12);
					System.out.println("Generated Account Number: " + accNo);

					// select client type

					// objective option in multiple field
					try {
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Objective']")));
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
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//span[text()='Children']")));
								WebElement selectDropDown = driver.findElement(By.xpath(
										"//select[@id='ctl00_ContentPlaceHolder1_ddlSingleChildren'] | //select[@id='ctl00_ContentPlaceHolder1_ddlMultipleChildren']"));
								Select selectOption = new Select(selectDropDown);
								selectOption.selectByIndex(1);
							} catch (Exception e) {
								System.out.println("Children option it's not there");
							}

						}

					} catch (Exception e) {
						System.out.println("Person field it's no there");
					}

					// enter receipt no
					try {
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Receipt No']")));
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
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Account Number']")));
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
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Receipt Date']")));
						WebElement workDate = driver.findElement(By.xpath("(//input[@format='dd-mmm-yyyy'])[2]"));
						JavascriptExecutor js = (JavascriptExecutor) driver;
						// Change the value attribute using JavaScript
						js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, receiptDate);

					} catch (Exception e) {
						System.out.println("Receipt no option it's not there");
					}

					// gl-code
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='GL Code']")));
						WebElement glCode = driver
								.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlSingleGLCode']"));
						Select selectGlCode = new Select(glCode);
						selectGlCode.selectByIndex(1);
					} catch (Exception e) {
						System.out.println("GL-Code it's not available..");
					}

					// issuer type
					try {
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtIssuer'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleIssuer']"))
								.sendKeys("Automation Testing");
					} catch (Exception e) {
						System.out.println("Issuer type it's not there...");
					}

					// Detail field
					try {
						String textDetails = driver.findElement(By.xpath(
								"//span[@id='ctl00_ContentPlaceHolder1_lblSingleDetails'] | //span[@id='ctl00_ContentPlaceHolder1_lblMultipleDetails']"))
								.getText();
						if (textDetails.equals("Type")) {
							System.out.println("We are going to select dropdown options");
							WebElement elementType = driver.findElement(By.xpath(
									"//select[@id='ctl00_ContentPlaceHolder1_drpTypeDetails'] | //select[@id='ctl00_ContentPlaceHolder1_drpMultipleTypeDetails']"));
							Select select = new Select(elementType);
							select.selectByIndex(1);
						} else {
							System.out.println("We are going to enter text");
							driver.findElement(By.xpath(
									"//input[@id='ctl00_ContentPlaceHolder1_txtSingleDetails'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleDetails']"))
									.sendKeys("Automation Test");
						}
					} catch (Exception e) {
						System.out.println("Detail type it's not there...");
					}

					// Additional field
					try {
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtSingleAdditional'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleAdditional']"))
								.sendKeys("Automation Testing");
					} catch (Exception e) {
						System.out.println("Additional Field it's not there..");
					}

					// claim amount
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Claim Amount']")));
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtClaimAmount'] | //input[@id='ctl00_ContentPlaceHolder1_txtClaimAmountMultiple']"))
								.sendKeys(claimAmount);
					} catch (Exception e) {
						System.out.println("Claim amout option it's not there");
					}

					// description
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Description']")));
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

					// add button
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")));
						driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")).click();
					} catch (Exception e) {
						System.out.println("Add button it's not there");
					}
				}
				// terms and conditions
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm'] | //input[@id='ctl00_ContentPlaceHolder1_chkMultipleRequiredConfirm']")));
					driver.findElement(By.xpath(
							"//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm'] | //input[@id='ctl00_ContentPlaceHolder1_chkMultipleRequiredConfirm']"))
							.click();
					System.out.println("Terms and condition option clicked..");
				} catch (Exception e) {
					System.out.println("Terms and Condition it's not available");
				}
			} else {
				for (int i = 1; i <= noOfCountLoop; i++) {

					receiptNo = generateAccountNumber(10);
					System.out.println("Generated Account Number: " + receiptNo);

					randomNumber = generateRandomNumber(1, 10000);
					System.out.println("Random Number: " + randomNumber);
					claimAmount = String.valueOf(randomNumber);

					accNo = generateAccountNumber(12);
					System.out.println("Generated Account Number: " + accNo);

					// objective option in multiple field
					System.out.println("We are going to loop different client type based on loop number");
					try {
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Objective']")));
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
						selectOption.selectByIndex(i);
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
							try {
								wait.until(ExpectedConditions
										.visibilityOfElementLocated(By.xpath("//span[text()='Children']")));
								WebElement selectDropDown = driver.findElement(By.xpath(
										"//select[@id='ctl00_ContentPlaceHolder1_ddlSingleChildren'] | //select[@id='ctl00_ContentPlaceHolder1_ddlMultipleChildren']"));
								Select selectOption = new Select(selectDropDown);
								selectOption.selectByIndex(1);
							} catch (Exception e) {
								System.out.println("Children option it's not there");
							}

						}

					} catch (Exception e) {
						System.out.println("Person field it's no there");
					}

					// enter receipt no
					try {
						wait.until(
								ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Receipt No']")));
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
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Account Number']")));
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
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Receipt Date']")));
						WebElement workDate = driver.findElement(By.xpath("(//input[@format='dd-mmm-yyyy'])[2]"));
						JavascriptExecutor js = (JavascriptExecutor) driver;
						// Change the value attribute using JavaScript
						js.executeScript("arguments[0].setAttribute('value', arguments[1])", workDate, receiptDate);

					} catch (Exception e) {
						System.out.println("Receipt no option it's not there");
					}

					// gl-code
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='GL Code']")));
						WebElement glCode = driver
								.findElement(By.xpath("//select[@id='ctl00_ContentPlaceHolder1_ddlSingleGLCode']"));
						Select selectGlCode = new Select(glCode);
						selectGlCode.selectByIndex(1);
					} catch (Exception e) {
						System.out.println("GL-Code it's not available..");
					}

					// issuer type
					try {
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtIssuer'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleIssuer']"))
								.sendKeys("Automation Testing");
					} catch (Exception e) {
						System.out.println("Issuer type it's not there...");
					}

					// Detail field
					try {
						String textDetails = driver.findElement(By.xpath(
								"//span[@id='ctl00_ContentPlaceHolder1_lblSingleDetails'] | //span[@id='ctl00_ContentPlaceHolder1_lblMultipleDetails']"))
								.getText();
						if (textDetails.equals("Type")) {
							System.out.println("We are going to select dropdown options");
							WebElement elementType = driver.findElement(By.xpath(
									"//select[@id='ctl00_ContentPlaceHolder1_drpTypeDetails'] | //select[@id='ctl00_ContentPlaceHolder1_drpMultipleTypeDetails']"));
							Select select = new Select(elementType);
							select.selectByIndex(1);
						} else {
							System.out.println("We are going to enter text");
							driver.findElement(By.xpath(
									"//input[@id='ctl00_ContentPlaceHolder1_txtSingleDetails'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleDetails']"))
									.sendKeys("Automation Test");
						}
					} catch (Exception e) {
						System.out.println("Detail type it's not there...");
					}

					// Additional field
					try {
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtSingleAdditional'] | //input[@id='ctl00_ContentPlaceHolder1_txtMultipleAdditional']"))
								.sendKeys("Automation Testing");
					} catch (Exception e) {
						System.out.println("Additional Field it's not there..");
					}

					// claim amount
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Claim Amount']")));
						driver.findElement(By.xpath(
								"//input[@id='ctl00_ContentPlaceHolder1_txtClaimAmount'] | //input[@id='ctl00_ContentPlaceHolder1_txtClaimAmountMultiple']"))
								.sendKeys(claimAmount);
					} catch (Exception e) {
						System.out.println("Claim amout option it's not there");
					}

					// description
					try {
						wait.until(ExpectedConditions
								.visibilityOfElementLocated(By.xpath("//span[text()='Description']")));
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

					// add button
					try {
						wait.until(ExpectedConditions.visibilityOfElementLocated(
								By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")));
						driver.findElement(By.xpath("//input[@id='ctl00_ContentPlaceHolder1_BtnAdd']")).click();
					} catch (Exception e) {
						System.out.println("Add button it's not there");
					}
				}
				// terms and conditions
				try {
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
							"//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm'] | //input[@id='ctl00_ContentPlaceHolder1_chkMultipleRequiredConfirm']")));
					driver.findElement(By.xpath(
							"//input[@id='ctl00_ContentPlaceHolder1_chkSingleRequiredConfirm'] | //input[@id='ctl00_ContentPlaceHolder1_chkMultipleRequiredConfirm']"))
							.click();
					System.out.println("Terms and condition option clicked..");
				} catch (Exception e) {
					System.out.println("Terms and Condition it's not available");
				}
			}
		}

		// submit button
		try {
			System.out.println("We are going to click submit button");
			driver.findElement(By.xpath(
					"//input[@id='ctl00_ContentPlaceHolder1_btnSaveDown'] | // input[@id='ctl00_ContentPlaceHolder1_btnSaveDownMultiple']"))
					.click();
			System.out.println("Submit button clicked successfully");

		} catch (Exception e) {
			System.out.println("Submit button it's not available");
		}
		
		// String chord = Keys.chord(Keys.CONTROL, Keys.ENTER);
		// it.hasNext() --> It will search next index it's present or not.
		// window.scrollBy(x,y) 
		// driver.switchTo().newWindow(WindowType.TAB);

	}

}
