package dhanishCode;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class SSLCertificateCode {
	
	public static void main(String[] args) {
		
		ChromeOptions option = new ChromeOptions();
		option.setAcceptInsecureCerts(true);
		
		WebDriver driver = new ChromeDriver(option);
		driver.get("https://mail.iscistech.in/");
		
		System.out.println("Title of the page is " + driver.getTitle());
	}

}
