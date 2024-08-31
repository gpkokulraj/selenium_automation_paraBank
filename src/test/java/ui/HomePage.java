package ui;

import java.time.Duration;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;


public class HomePage {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://parabank.parasoft.com/parabank/index.htm");
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("username");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("password");
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='accountTable']/tbody/tr/td/a")));
		String accountNumberString = driver.findElement(By.xpath("//table[@id='accountTable']/tbody/tr/td/a[1]")).getText();
		int accountNumber = Integer.parseInt(accountNumberString);
		Assert.assertTrue(accountNumber == 13899);
		String accountBalanceString = driver.findElement(By.xpath("//table[@id='accountTable']/tbody/tr/td/a")).getText();
		int accountBalance = Integer.parseInt(accountBalanceString);
		if(accountBalance>0) {
			if(accountBalance > 100) {
				driver.findElement(By.xpath("//div[@id='leftPanel']/ul/li[3]/a")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showForm")));
				driver.findElement(By.id("amount")).sendKeys("100");
				Select fromAccountSelect = new Select(driver.findElement(By.id("fromAccountId")));
				fromAccountSelect.selectByVisibleText(accountNumberString);
				Select toAccountSelect = new Select(driver.findElement(By.id("fromAccountId")));
				toAccountSelect.selectByVisibleText("15120");
				driver.findElement(By.xpath("//input[@value='Transfer']")).click();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("showResult")));
				driver.findElement(By.xpath("//div[@id='showResult']/h1[contains(text(),'Transfer Complete!')]")).isDisplayed();
				System.out.println("Funder Transfer Completed !");
			}
		}
		else {
			System.out.println("Your Account Banalce is 0");
		}

	}

}
