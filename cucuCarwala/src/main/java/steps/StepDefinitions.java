package steps;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefinitions {
	
	public WebDriver driver;
	public Actions action;
	public WebDriverWait wait;
	
	@Given("Launch the Browser") 
	public void launchBrowser(){
		System.setProperty("webdriver.chrome.driver","./drivers/chromedriver83.exe");
		System.setProperty("webdriver.chrome.silentOutput","true");
    
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("--start-maximized");
		
		driver = new ChromeDriver(options);
	}

	@Given("Set the Timeouts") 
	public void timeOut() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Given("Load the URL") 
	public void loadURL() {
		driver.get("https://www.carwale.com/");
	}
	
	@Given("Click used car")
	public void clickUsed() {
		driver.findElement(By.xpath("//li[contains(text(),'Used')]")).click();
	}

	@Given("Select the City as Chennai") 
	public void selectCity() {
		action = new Actions(driver);
		WebElement city = driver.findElement(By.id("usedCarsList"));
		action.moveToElement(city).click().sendKeys("Chennai").build().perform();
		driver.findElement(By.xpath("//a[@cityname='chennai,tamilnadu']")).click();
	}

	@Given("Select budget min 8L and max 12L and Click") 
	public void budgetMinMax() throws InterruptedException{
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@id='minPriceList']//li[contains(text(),'8 Lakh')]")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//ul[@id='maxPriceList']//li[contains(text(),'12 Lakh')]")).click();
		driver.findElement(By.id("btnFindCar")).click();
	}

	@Given("Select Cars with Photos under Only Show Cars With") 
	public void carWithPic() {
		driver.findElement(By.name("CarsWithPhotos")).click();
	}

	@Given("Select Manufacturer as Hyundai and Creta") 
	public void selectCar(){
		JavascriptExecutor click =(JavascriptExecutor) driver;
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//li[@data-manufacture-en=\"Hyundai\"]")));
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//span[text()='Creta'] ")));
	}

	@Given("Select Fuel Type as Petrol") 
	public void fuelType() {
		JavascriptExecutor click =(JavascriptExecutor) driver;
		click.executeScript("arguments[0].click();", driver.findElement(By.xpath("//h3[contains(text(),'Fuel Type')]")));
		click.executeScript("arguments[0].click();", driver.findElement(By.name("Petrol")));		
		driver.findElement(By.xpath("//div[contains(@class,\"coachmark filters-coachmark hide\")]/p[4]/a")).click();
	}

	@When("Select Best Match as KM: Low to High") 
	public void bestMatch() {
		WebElement bestmatch = driver.findElement(By.id("sort"));
		Select sortcars = new Select(bestmatch);
		sortcars.selectByVisibleText("KM: Low to High");
	}

	@Then("Print the first resulting car") 
	public void printCarName(){
	    String carName = driver.findElement(By.xpath("(//span[@data-carname-id=\"carname\"])[1]")).getText();
	    System.out.println(carName);
	    driver.close();
	}
	
}
