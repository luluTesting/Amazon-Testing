
	import java.io.IOException;

	import java.util.concurrent.TimeUnit;

	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.ITestResult;
	import org.testng.annotations.AfterMethod;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.Test;
	import org.testng.asserts.SoftAssert;

	import com.relevantcodes.extentreports.ExtentReports;
	import com.relevantcodes.extentreports.ExtentTest;
	import com.relevantcodes.extentreports.LogStatus;

	//import extent.ExtentManager;
	public class AmazonTest1{

			public WebDriver driver;
			SoftAssert softAssert = new SoftAssert();
			//ExtentReports extent = ExtentManager.getInstance("amazonTest.html");
			ExtentTest logger;
			String WEBURL = "https://www.amazon.ca/";
			String EMAIL = "";
			String PASSWORD = "";

			@BeforeTest
			public void startTest() throws IOException{
				System.setProperty("webdriver.chrome.driver", "C:\\Users\\xguo\\Selenium Webdriver\\chromedriver.exe");
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				//Navigate to amazon
				driver.get(WEBURL);
			}

			@Test(priority = 1)
			public void signInButton() throws InterruptedException, IOException {
				WebDriverWait wait = new WebDriverWait(driver, 40);
				
				// Sign-in page
				String helloText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-yourAccount"))).getText();
				String signInText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-flyout-ya-signin > a[href^=\"/gp/navigation/redirector.html/ref=sign-in-redirect\"].nav-action-button > .nav-action-inner"))).getText();

				
				System.out.println(helloText);
				try {
					Assert.assertEquals(signInText.getText(), "Sign in");
				} catch (Throwable d) {
					logger.log(LogStatus.FAIL, "Does not display: Hello. Sign in");
				}
			}
			@Test(priority = 2)
			public void SignIn() {
				WebDriverWait wait = new WebDriverWait(driver, 40);
				WebElement signIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-link-yourAccount")));
				signIn.click();
				// Email
				WebElement emailBox = driver.findElement(By.id("ap_email"));
				WebElement nextBox = driver.findElement(By.cssSelector("input[id=\"continue\"][type=\"submit\"]"));
				emailBox.sendKeys(EMAIL);
				nextBox.click();		
				// Password
				WebElement pwBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ap_password")));
				pwBox.sendKeys(PASSWORD);
				WebElement submitSignIn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInSubmit")));
				submitSignIn.click();

				
			}
			
			@Test (priority = 3)
			public void MemorySearch() throws IOException {
				//logger = extent.startTest("", "This is a log in test to standalone EC");
				WebDriverWait wait = new WebDriverWait(driver, 40);
				//Searching for Memory Card
				WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
				searchBar.sendKeys("Memory Card");
				searchBar.submit();
				checkPageIsReady();
				//observe results				
			}
			
			@Test (priority = 4)
			public void NullSerach() {
				WebDriverWait wait = new WebDriverWait(driver, 40);
				//Searching for "no results"
				WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
				String errorSearch = "[alpja]";
				searchBar.sendKeys(errorSearch);
				WebElement noResults = driver.get(By.id("noResultsTitle"));
				Assert.assertEquals(noResults.getText(), "You search " + errorSearch.getText() + "did not match any products");
				
			}
			
			@Test (priority =5)
			public void PurchaseOrder() {
				//WebElement MemoryCard = driver.get(By.linkText("/Sandisk-Ultra-Class-Memory-SDSDUNC-032G-GN6IN/dp/B0143RT8OY/ref=sr_1_4"));
				WebDriverWait wait = new WebDriverWait(driver, 40);
				//Click on Memory Card
				WebElement MemoryCard = driver.get(By.cssSelector("a[href*=\"/Sandisk-Ultra-Class-Memory-SDSDUNC-032G-GN6IN/dp/B0143RT8OY/ref=sr_1_4\"] > h2.a-size-medium.s-inline.s-access-title.a-text-normal"));
				MemoryCard.click();
				//Add to cart
				WebElement addToCart = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-to-cart-button")));
				addToCart.click();
				//proceed with Order
				WebElement proceedOrder = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlb-ptc-btn-native")));
				proceedOrder.click();
				//Check Shipping Page
				WebElement shippingPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("clearfix > h1")));
				Assert.assertEquals(shippingPage.getText(),"Select a shipping address");
			}
			
			
			
			@AfterTest
			public void endReport() {
				//extent.flush();
				//extent.close(); // closes underlying stream, all operations
				driver.quit();
			}
			public void checkPageIsReady() {

				JavascriptExecutor js = (JavascriptExecutor) driver;

				// Initial condition will check ready state of page.
				if (js.executeScript("return document.readyState").toString().equals("complete")) {
					System.out.println("Page Is loaded.");
					return;
				}

				// This loop will rotate for 100 times to check If page Is ready after every 250
				// milliseconds.
				
				for (int i = 0; i < 100; i++) {
					try {
						Thread.sleep(250);
						System.out.println("loading...");
					} catch (InterruptedException e) {
					}
					// To check page ready state.
					if (js.executeScript("return document.readyState").toString().equals("complete")) {
						System.out.println("We're out");
						break;
					}

				}
				return;
			}
	}
	

	

