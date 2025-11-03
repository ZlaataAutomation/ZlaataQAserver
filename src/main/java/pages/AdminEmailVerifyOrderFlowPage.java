package pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
	 public void deleteAllProductsFromCart() {

		    // Open cart
		    driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']")).click();
		    Common.waitForElement(1);

		    // ✅ STEP 1: Check if cart is already empty
		    try {
		        if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
		            System.out.println("🛍️ Cart already empty. No delete action needed.");
		            return; // Stop method immediately
		        }
		    } catch (NoSuchElementException ignored) {
		        // Cart is NOT empty, proceed to delete
		    }

		    // ✅ STEP 2: Delete products one by one
		    while (true) {
		        try {
		            WebElement deleteBtn = driver.findElement(By.xpath("//div[@title='Delete']"));
		            deleteBtn.click();
		            System.out.println("🗑️ Product deleted");
		            Common.waitForElement(1); 
		        } catch (NoSuchElementException e) {
		            System.out.println("✅ No more products to delete.");
		            break;
		        } catch (Exception e) {
		            System.out.println("⚠️ Error while deleting: " + e.getMessage());
		            break;
		        }
		    }

		    // ✅ STEP 3: Final confirmation
		    try {
		        if (driver.findElement(By.xpath("//h5[contains(text(),'Your bag is empty')]")).isDisplayed()) {
		            System.out.println("🛍️ Cart is empty, Continue Shopping displayed.");
		        }
		    } catch (NoSuchElementException e) {
		        System.out.println("ℹ️ Bag is not empty message not found.");
		    }
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
		    
		   deleteAllProductsFromCart();

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
		    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
		    click(placeOrderBtn);
		    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);

		    Thread.sleep(5000);    
		 // ✅ 1. Switch to Razorpay iframe (you already have this)
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
		            By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
		    ));
		    System.out.println("✅ Switched to Razorpay iframe");

		    // ✅ 2. Click Continue button
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[contains(.,'Continue')]")
		    )).click();
		    System.out.println("✅ Continue clicked");
//
//		    // ✅ 3. Click Skip OTP
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[contains(text(),'Skip OTP')]")
//		    )).click();
//		    System.out.println("✅ Skipped OTP");

		    // ✅ 4. Enter Pincode
		    wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.id("zipcode")
		    )).sendKeys("560001");

		    // ✅ 5. Enter City auto-filled → skip  
		    // ✅ 6. Enter Name
		    driver.findElement(By.id("name")).sendKeys("Saroj Test");

		    // ✅ 7. Enter House / Building
		    driver.findElement(By.id("line1")).sendKeys("Bangalore");

		    // ✅ 8. Enter Area / Street
		    driver.findElement(By.id("line2")).sendKeys("bjvhcgfchvbjkn");

		    
		    // ✅ 9. Click Continue (Address Submit)
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[contains(.,'Continue') and @name='new_shipping_address_cta']")
		    )).click();

		    System.out.println("✅ Address submitted successfully");
		    
		    
		    

		    // ✅ 3. Select Netbanking option
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//span[@data-testid='Netbanking']")
		    )).click();

		    // ✅ 4. Select HDFC Bank
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("(//div[@role='button' and .//span[contains(text(),'HDFC Bank')]])[1]")
		    )).click();

		    // ⬅️ Optional: Switch back to main page after selecting
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
	
	
		
		String gmailId="zlaata.qa.test@gmail.com";
		String gmailPassword="user@123";
			
	// ✅ Price comparison using normalized values
private String normalizePrice(String price) {
    return price.replaceAll("[^0-9]", ""); // Keep only digits
}

public void verifyOrderConfirmationMail(String expectedmsg)
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
		// Check if already logged in by looking for inbox element
		List<WebElement> inboxCheck = driver.findElements(By.xpath("//table//tr//span[@class='bog']/span"));

		if (inboxCheck.size() > 0) {
		    System.out.println(YELLOW + "⚠️ Gmail session already active... Skipping login." + RESET);
		} else {
		    System.out.println(CYAN + "🔐 Logging into Gmail..." + RESET);

		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys(gmailId);
		    driver.findElement(By.id("identifierNext")).click();

		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys(gmailPassword);
		    driver.findElement(By.id("passwordNext")).click();

		    System.out.println(GREEN + "✅ Logged into Gmail successfully." + RESET);
		}
		
		// ✅ Wait for inbox to load
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		System.out.println(GREEN + "📥 Gmail inbox loaded." + RESET);
		
		// ---- WAIT FOR ORDER CONFIRMATION MAIL ----
		boolean mailFound = false;
		int retries = 36; // 3 min max wait

		for (int i = 0; i < retries; i++) {

		    try {
		        WebElement latestMail = driver.findElement(By.xpath("(//table//tr//span[@class='bog']/span)[1]"));

		        if (latestMail.getText().contains(expectedmsg)) {
		            latestMail.click();
		            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
		            mailFound = true;
		            break;
		        }
		    } catch (Exception ignored) {}

		    System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
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

		    
		    System.out.println(GREEN + "🔍 Comparing mail details with order summary..." + RESET);

		    Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
		            mailOrderId.contains(orderId));

		    Assert.assertTrue("❌ Product name mismatch! Expected: " + productName + " | Found: " + mailProductName,
		            mailProductName.contains(productName));

		   
		    Assert.assertEquals("❌ Total MRP mismatch!", normalizePrice(totalMRF), normalizePrice(mailTotalMRP));
		    Assert.assertEquals("❌ Discounted MRP mismatch!", normalizePrice(discountedMRP), normalizePrice(mailDiscountedMRP));
		    Assert.assertEquals("❌ Total Amount mismatch!", normalizePrice(totalAmount), normalizePrice(mailTotalAmount));

		    System.out.println(GREEN + "✅ All order details verified successfully in the mail!" + RESET);
		    System.out.println(CYAN + line + RESET);
		    
		}

//Order Status change Place to Shipped
public void updateOrderStatusToShipped() throws InterruptedException {

    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String line = "──────────────────────────────────────────────────────────────";

    System.out.println(line);
    System.out.println(GREEN + "🚚 Updating Order Status for Order ID: " + orderId + RESET);
    System.out.println(line);

    adminLoginApp();
    
	
    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	System.out.println("Redirect to Placed Order Page");
	Common.waitForElement(1);
	
    // ✅ Go to order search box and search order ID
	Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn));
    waitFor(orderIdbtn);
	click(orderIdbtn);
	 Common.waitForElement(1);
	wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
    Common.waitForElement(1);
	waitFor(orderSearchBox);
    orderSearchBox.clear();
    orderSearchBox.sendKeys(orderId);
    Common.waitForElement(1);
    orderSearchBox.sendKeys(Keys.ENTER);
    Common.waitForElement(2);

    // ✅ Verify order is displayed
    try {
        WebElement orderRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
        System.out.println(GREEN + "✅ Order found in table!" + RESET);
    } catch (TimeoutException e) {
        System.out.println(RED + "❌ Order not found! Stopping execution." + RESET);
        return;
    }

    // ✅ Click Edit button
    wait.until(ExpectedConditions.elementToBeClickable(editBtn));
    Common.waitForElement(2);
	waitFor(editBtn);
	click(editBtn);
    System.out.println(GREEN + "✅ Clicked Edit" + RESET);

    // ✅ Shipment Status → Order Accept
    wait.until(ExpectedConditions.elementToBeClickable(shipmentStatus));
    Common.waitForElement(2);
	waitFor(shipmentStatus);
	click(shipmentStatus);
	Common.waitForElement(2);
	Select select = new Select(shipmentStatus);
	select.selectByVisibleText("Order Accept");
	System.out.println(GREEN + "✅ Shipment Status set to 'Order Accept'" + RESET);

    // ✅ Courier Provider → Manual
    wait.until(ExpectedConditions.elementToBeClickable(courierProvider));
    Common.waitForElement(2);
	waitFor(courierProvider);
	click(courierProvider);
	Common.waitForElement(2);
	Select select1 = new Select(courierProvider);
	select1.selectByVisibleText("Manual");
	System.out.println(GREEN + "✅ Courier Provider set to Manual" + RESET);

    // ✅ Save & Back
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
    waitFor(saveButton);
    click(saveButton);
    System.out.println("✅ Saved  changes");

    // ✅ Again click Edit for second update
    Common.waitForElement(5);
    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    System.out.println(GREEN + "✅ Re-opened Edit screen" + RESET);

    // ✅ Order Status → Order Shipped
    wait.until(ExpectedConditions.elementToBeClickable(orderStatus));
    Common.waitForElement(2);
	waitFor(orderStatus);
	click(orderStatus);
	Common.waitForElement(2);
	Select select2 = new Select(orderStatus);
	select2.selectByVisibleText("Order Shipped");
	System.out.println(GREEN + "✅ Order Status set to 'Order Shipped'" + RESET);

 // ✅ Save & Back
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
    waitFor(saveButton);
    click(saveButton);
    System.out.println("✅ Saved  changes");

    System.out.println(GREEN + "🎉 Order status updated successfully to SHIPPED!" + RESET);
    System.out.println(line);
}



public void orderStatusShippedToDelivered() {

    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String line = "──────────────────────────────────────────────────────────────";

    System.out.println(line);
    System.out.println(GREEN + "🚚 Updating Order Status for Order ID: " + orderId + RESET);
    System.out.println(line);

    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

//    // ✅ Search Order ID
//    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
//    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
//    orderSearchBox.clear();
//    orderSearchBox.sendKeys(orderId);
//    orderSearchBox.sendKeys(Keys.ENTER);
    Common.waitForElement(3);

    // ✅ Validate Order Exists
    try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
        System.out.println(GREEN + "✅ Order found in table!" + RESET);
    } catch (TimeoutException e) {
        System.out.println(RED + "❌ Order not found! Stopping execution." + RESET);
        return;
    }

    // ✅ Open Edit
    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    System.out.println(GREEN + "✅ Opened Edit page" + RESET);

    // ✅ Step 1: Set to Out For Delivery
    wait.until(ExpectedConditions.elementToBeClickable(orderStatus));
	waitFor(orderStatus);
	click(orderStatus);
	Common.waitForElement(2);
    Select step1 = new Select(orderStatus);
    step1.selectByVisibleText("Out For Delivery");
    System.out.println(GREEN + "✅ Status changed → Out For Delivery" + RESET);
    Common.waitForElement(3);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    System.out.println(GREEN + "💾 Saved changes (Out For Delivery)" + RESET);

    // ✅ Re-open Edit
    Common.waitForElement(5);
    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    System.out.println(GREEN + "✅ Re-opened Edit page" + RESET);

    // ✅ Step 2: Set to Order Delivered
    wait.until(ExpectedConditions.elementToBeClickable(orderStatus));
    waitFor(orderStatus);
	click(orderStatus);
	Common.waitForElement(2);
    Select step2 = new Select(orderStatus);
    step2.selectByVisibleText("Order Delivered");
    System.out.println(GREEN + "✅ Status changed → Order Delivered" + RESET);

    Common.waitForElement(3);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    System.out.println(GREEN + "💾 Saved changes (Order Delivered)" + RESET);

    System.out.println(line);
    System.out.println(GREEN + "🎉 Order successfully updated from Shipped → Delivered!" + RESET);
    System.out.println(line);
}

	
//String totalMRF="₹1999", discountedMRP="₹999", youSaved="₹1000", totalAmount="₹999", orderId="ZLTQA/25-26/18079";
//TC01 Verify Order Placed Confirm
		public void verifyOrderPlacedEmail() throws InterruptedException {
			
			addProductToCartAndPlacedTheOrder();
			
			verifyOrderConfirmationMail("Order Confirmation");
			
			//Order Shipped
			updateOrderStatusToShipped();
			
			verifyOrderConfirmationMail("Order Shipped");
			
			//Order Delivered
			orderStatusShippedToDelivered();
			
			verifyOrderConfirmationMail("Order Delivered Confirmation");
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
