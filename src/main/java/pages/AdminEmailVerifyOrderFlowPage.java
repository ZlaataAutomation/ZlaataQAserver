package pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.AdminEmailVerifyOrderFlowObjRepo;
import utils.Common;

public class AdminEmailVerifyOrderFlowPage extends AdminEmailVerifyOrderFlowObjRepo {
	
	public AdminEmailVerifyOrderFlowPage(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	
	 public void adminLoginApp() {
	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	        type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	        type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	        click(adminLogin);
	        System.out.println("✅ Admin Login Successful");
	    }
	 
	 public void userLoginApp() {
		    HomePage home = new HomePage(driver);
		    home.homeLaunch();

		    Common.waitForElement(2);
		    click(profile);
		    type(loginNumber, "8596047219");
		    Common.waitForElement(1);
		    click(sendotp);
		    Common.waitForElement(2);
		    type(enterotp, "12345");
		    click(verifyotp);
		    Common.waitForElement(3);

		    System.out.println("\u001B[32m✅ Login successful\u001B[0m");
		}

		// Fetch from Excel
		String productName = Common.getValueFromTestDataMap("ProductListingName");
		String totalMRF, discountedMRP, youSaved, totalAmount, orderId;

		public void addProductToCartAndPlacedTheOrder() throws InterruptedException {
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    JavascriptExecutor js = (JavascriptExecutor) driver;

		    String CYAN = "\u001B[36m";
		    String YELLOW = "\u001B[33m";
		    String GREEN = "\u001B[32m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(CYAN + line + RESET);
		    System.out.println(GREEN + "🚀 Starting Order Placement Flow..." + RESET);
		    System.out.println(CYAN + line + RESET);

		    userLoginApp();
		    OrdersPage order = new OrdersPage(driver);
		    order.deleteAllProductsFromCart();

		    // ✅ Search product
		    System.out.println(YELLOW + "🔍 Searching for product: " + productName + RESET);
		    wait.until(ExpectedConditions.elementToBeClickable(userSearchBox));
		    userSearchBox.clear();
		    userSearchBox.sendKeys(productName);
		    userSearchBox.sendKeys(Keys.ENTER);
		    Common.waitForElement(2);

		    wait.until(ExpectedConditions.elementToBeClickable(addToBag));
		    click(addToBag);
		    System.out.println(GREEN + "✅ Clicked 'Add To Bag'" + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
		    click(addToCartBtn);
		    System.out.println(GREEN + "✅ Added product to cart" + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(bagIcon));
		    click(bagIcon);
		    System.out.println(GREEN + "✅ Opened cart" + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Continue (Cart Page)" + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Continue (Address Page)" + RESET);

		  	    
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(selectNetBank));
		    click(selectNetBank);
		    System.out.println(GREEN + "✅ Selected NetBanking option" + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
		    click(placeOrderBtn);
		    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);

		    Thread.sleep(5000);
		    List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		    System.out.println("🧩 Total iframes found: " + frames.size());
		    for (WebElement f : frames) {
		        System.out.println(" → iframe name/id: " + f.getAttribute("name") + " | " + f.getAttribute("id"));
		    }
		 // Wait for Razorpay iframe to appear dynamically
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
		        By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
		    ));

		    System.out.println("✅ Switched to Razorpay iframe successfully");

		    // Click HDFC Bank inside the iframe
		    WebElement hdfcBank = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//div[@role='button' and .//span[contains(text(),'HDFC Bank')]]")
		    ));
		    hdfcBank.click();
		    System.out.println("💳 Clicked HDFC Bank option");

		    // (Optional) Click the success/Pay button
		    // WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(.,'Success')]")));
		    // successBtn.click();

		    driver.switchTo().defaultContent();

		 

		    // Switch to Razorpay window
		    String mainWindow = driver.getWindowHandle();
		    Thread.sleep(3000);
		    Set<String> allWindows = driver.getWindowHandles();
		    for (String window : allWindows) {
		        if (!window.equals(mainWindow)) {
		            driver.switchTo().window(window);
		            System.out.println(GREEN + "✅ Switched to Razorpay window" + RESET);
		            break;
		        }
		    }

		    // ✅ Click Success button
		    WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//button[@data-val='S' and normalize-space(text())='Success']")
		    ));
		    successBtn.click();
		    System.out.println(GREEN + "💳 Payment Success clicked" + RESET);

		    Thread.sleep(5000);
		    driver.switchTo().window(mainWindow);
		    System.out.println(GREEN + "🔙 Switched back to main window" + RESET);

		    // ✅ Confirm order
		    Thread.sleep(9000);
		    try {
		        WebElement confirmMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Order Confirmed']")
		        ));

		        if (confirmMsg.isDisplayed()) {
		            System.out.println(GREEN + "🎉 Order Confirmed Successfully!" + RESET);

		            wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
		            click(viewOrderDetails);
		            System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);
		            
		    	    WebElement cancelBtn = driver.findElement(By.xpath("//button[@class='prod_cancel_btn cls_cancel_button']"));
		    	    if (cancelBtn.isDisplayed()) {
		    	        System.out.println("❌ Cancel Button: Displayed ✅");
		    	    }
		    	    WebElement orderIdElement = driver.findElement(By.xpath("//div[@class='prod_order_id_value']"));
		            orderId = orderIdElement.getText().trim();
		            System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
		            
		            WebElement productNameElement = driver.findElement(By.xpath("//div[contains(@class,'placed_prod_details')]//h4[@class='placed_prod_name']"));
		            productName = productNameElement.getText().trim();
		            System.out.println(YELLOW + "Product Name: " + productName + RESET);
		          

		    	    // Step 13: Price Breakup
		    	    driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();
		    	    Common.waitForElement(1);
		    	    
				    // ✅ Capture pricing details
				    WebElement totalMRFElement = driver.findElement(By.xpath("//div[@class='price_details_row actual_mrp']//div[@class='price_details_pair']"));
				    totalMRF = totalMRFElement.getText().trim();

				    WebElement discountedMRPElement = driver.findElement(By.xpath("//div[@class='price_details_row discount_mrp']//div[@class='price_details_pair']"));
				    discountedMRP = discountedMRPElement.getText().trim();

				    WebElement youSavedElement = driver.findElement(By.xpath("//div[@class='price_details_row saved_amount']//div[@class='price_details_pair']"));
				    youSaved = youSavedElement.getText().trim();

				    WebElement totalAmountElement = driver.findElement(By.xpath("//div[@class='price_details_row total_amount']//div[@class='price_details_pair']"));
				    totalAmount = totalAmountElement.getText().trim();

				    System.out.println(CYAN + line + RESET);
				    System.out.println(GREEN + "💰 Price Summary:" + RESET);
				    System.out.println(YELLOW + "🆔 Total MRP: " + totalMRF + RESET);
				    System.out.println(YELLOW + "💸 Discounted MRP: " + discountedMRP + RESET);
				    System.out.println(YELLOW + "💰 You Saved: " + youSaved + RESET);
				    System.out.println(YELLOW + "🪙 Total Amount: " + totalAmount + RESET);
				    System.out.println(CYAN + line + RESET);
		    	             

		       
		        } else {
		            System.out.println(RED + "❌ Order confirmation message not visible" + RESET);
		            Assert.fail("⏰ Order confirmation message not found within timeout");
		        }

		    } catch (TimeoutException e) {
		        System.out.println(RED + "⏰ Order confirmation message not found within timeout" + RESET);
		        Assert.fail("⏰ Order confirmation message not found within timeout");
		    }

		    // ✅ Final Summary
		    System.out.println(CYAN + line + RESET);
		    System.out.println(GREEN + "🛍️ ORDER SUMMARY" + RESET);
		    System.out.println(YELLOW + "📦 Product: " + productName + RESET);
		    System.out.println(YELLOW + "💰 Total MRP: " + totalMRF + RESET);
		    System.out.println(YELLOW + "💸 Discounted MRP: " + discountedMRP + RESET);
		    System.out.println(YELLOW + "💰 You Saved: " + youSaved + RESET);
		    System.out.println(YELLOW + "🪙 Total Amount: " + totalAmount + RESET);
		    System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
		    System.out.println(YELLOW + "Product Name: " + productName + RESET);
		    System.out.println(CYAN + line + RESET);
		   
		}
	
	
		
		
		
	
public void verifyOrderConfirmationMail(String gmailId, String gmailPassword, 
	                String expectedOrderId, 
	                String expectedProduct, 
	                String expectedAmount, String expectedmsg)
	throws InterruptedException {
	
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	String CYAN = "\u001B[36m";
	String YELLOW = "\u001B[33m";
	String GREEN = "\u001B[32m";
	String RED = "\u001B[31m";
	String RESET = "\u001B[0m";
	String line = "──────────────────────────────────────────────────────────────";
	
	System.out.println(CYAN + line + RESET);
	System.out.println(GREEN + "📧 Starting Gmail Order Confirmation Verification..." + RESET);
	System.out.println(CYAN + line + RESET);
	
	// ✅ Open Gmail login page
	driver.get("https://mail.google.com/");
	System.out.println("🌐 Opening Gmail login page...");
	
	// ---- LOGIN FLOW ----
	try {
	// Enter email
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys(gmailId);
	driver.findElement(By.id("identifierNext")).click();
	
	// Enter password
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys(gmailPassword);
	driver.findElement(By.id("passwordNext")).click();
	System.out.println(GREEN + "🔐 Logged into Gmail successfully." + RESET);
	
	} catch (Exception e) {
	System.out.println(YELLOW + "⚠️ Already logged in or session cached. Skipping login..." + RESET);
	}
	
	// ✅ Wait for inbox to load
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	System.out.println(GREEN + "📥 Gmail inbox loaded." + RESET);
	
	// ---- WAIT FOR ORDER CONFIRMATION MAIL ----
	boolean mailFound = false;
	int retries = 20; // 20 retries (5s each) = ~100 seconds max wait
	for (int i = 0; i < retries; i++) {
	List<WebElement> mails = driver.findElements(By.xpath("//table//tr//span[@class='bog']/span"));
	for (WebElement mail : mails) {
	if (mail.getText().contains(expectedmsg)) {
	mailFound = true;
	mail.click();
	System.out.println(GREEN + "📨 Found 'Order Confirmed' mail and opened it!" + RESET);
	break;
	}
	}
	if (mailFound) break;
	System.out.println(YELLOW + "⏳ Waiting for Order Confirmation Mail... retry " + (i + 1) + RESET);
	Thread.sleep(5000);
	driver.navigate().refresh();
	}
	
	if (!mailFound) {
	System.out.println(RED + "❌ Order Confirmation Mail not received within time!" + RESET);
	Assert.fail("Order confirmation mail not found.");
	}
	
	// ---- READ MAIL CONTENT ----
	Thread.sleep(4000);
	

	    // ✅ Extract order details from mail DOM
	    System.out.println(GREEN + "🔍 Extracting order details from mail..." + RESET);

	    String mailOrderId = driver.findElement(By.xpath("//td[contains(text(),'Order ID')]/following-sibling::td/following-sibling::td")).getText().trim();
	    String mailProductName = driver.findElement(By.xpath("//td[contains(text(),'Product')]/ancestor::table//td[contains(@class,'font_12') and contains(text(),'Flare')]")).getText().trim();
	    String mailTotalMRP = driver.findElement(By.xpath("//td[contains(text(),'Total MRP')]/following-sibling::td")).getText().trim();
	    String mailDiscountedMRP = driver.findElement(By.xpath("//td[contains(text(),'Discounted MRP')]/following-sibling::td")).getText().trim();
	    String mailTotalAmount = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]/following-sibling::td")).getText().trim();

	    System.out.println(CYAN + line + RESET);
	    System.out.println(YELLOW + "📬 Mail Extracted Details:" + RESET);
	    System.out.println("📦 Product Name: " + mailProductName);
	    System.out.println("🆔 Order ID: " + mailOrderId);
	    System.out.println("💰 Total MRP: " + mailTotalMRP);
	    System.out.println("💸 Discounted MRP: " + mailDiscountedMRP);
	    System.out.println("🪙 Total Amount: " + mailTotalAmount);
	    System.out.println(CYAN + line + RESET);

	    
	 // ✅ Compare with UI captured values
	    System.out.println(GREEN + "🔍 Comparing mail details with order summary..." + RESET);

	    Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
	            mailOrderId.contains(orderId));

	    Assert.assertTrue("❌ Product name mismatch! Expected: " + productName + " | Found: " + mailProductName,
	            mailProductName.contains(productName));

	    Assert.assertTrue("❌ Total MRP mismatch! Expected: " + totalMRF + " | Found: " + mailTotalMRP,
	            mailTotalMRP.contains(totalMRF));

	    Assert.assertTrue("❌ Discounted MRP mismatch! Expected: " + discountedMRP + " | Found: " + mailDiscountedMRP,
	            mailDiscountedMRP.contains(discountedMRP));

	    Assert.assertTrue("❌ Total Amount mismatch! Expected: " + totalAmount + " | Found: " + mailTotalAmount,
	            mailTotalAmount.contains(totalAmount));

	    System.out.println(GREEN + "✅ All order details verified successfully in the mail!" + RESET);
	    System.out.println(CYAN + line + RESET);
	    
	   
	
	
	
	
	}		
	
	
	
	//TC01 Verify Order Placed Confirm
		public void verifyOrderPlacedEmail() throws InterruptedException {
			addProductToCartAndPlacedTheOrder();
			
			verifyOrderConfirmationMail("zlaata.qa.test@gmail.com", "user@123", orderId, productName, totalAmount,"Order Confirmation" );
			
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WebDriver gmail(String browserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAt() {
		// TODO Auto-generated method stub
		return false;
	}

}
