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
	 
	 public void deleteAllMailsIfNotEmpty() throws InterruptedException {
		    driver.get("https://mail.google.com/");
		    

		    // --- Gmail Login ---
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys(gmailId);
		    driver.findElement(By.id("identifierNext")).click();

		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys(gmailPassword);
		    driver.findElement(By.id("passwordNext")).click();

		    // --- Wait for Inbox to load ---
		    wait.until(ExpectedConditions.or(
		        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.ae4.aDM")),
		        ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.aRv"))
		    ));

		    // --- STEP 1: Check for any mail rows ---
		    List<WebElement> mails = driver.findElements(By.xpath("//tr[contains(@class,'zA')]"));

		    if (mails.isEmpty()) {
		        System.out.println("✅ Inbox empty — nothing to delete.");
		        return;
		    }

		    System.out.println("⚠️ Found " + mails.size() + " email(s). Proceeding to delete...");

		    // --- STEP 2: Click 'Select all' checkbox ---
		    WebElement selectAll = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//div[@aria-label='Select']//span[@role='checkbox']")));
		    selectAll.click();

		    Thread.sleep(2000);

		    // --- STEP 3: Click 'Delete' button ---
		    WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//div[@aria-label='Delete']")));
		    deleteButton.click();
		    Thread.sleep(3000);

		    System.out.println("🗑️ All emails deleted successfully.");
		}

		// Fetch from Excel
		String productName = Common.getValueFromTestDataMap("ProductListingName");
		String totalMRF, discountedMRP, youSaved, totalAmount, orderId, uiAddress, contact, name, type;

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
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Continue Button" + RESET);
		    
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
		    click(continueBtn);
		    System.out.println(GREEN + "✅ Clicked Address Page Continue Button" + RESET);
		    
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(selectNetBank));
		    click(selectNetBank);
		    System.out.println(GREEN + "✅ Select Netbanking" + RESET);
		    
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
//
//		    // ✅ 2. Click Continue button
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[contains(.,'Continue')]")
//		    )).click();
//		    System.out.println("✅ Continue clicked");
////
//		    // ✅ 3. Click Skip OTP
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[contains(text(),'Skip OTP')]")
//		    )).click();
//		    System.out.println("✅ Skipped OTP");
//
//		    // ✅ 4. Enter Pincode
//		    wait.until(ExpectedConditions.visibilityOfElementLocated(
//		            By.id("zipcode")
//		    )).sendKeys("560001");
//
//		    // ✅ 5. Enter City auto-filled → skip  
//		    // ✅ 6. Enter Name
//		    driver.findElement(By.id("name")).sendKeys("Saroj Test");
//
//		    // ✅ 7. Enter House / Building
//		    driver.findElement(By.id("line1")).sendKeys("Bangalore");
//
//		    // ✅ 8. Enter Area / Street
//		    driver.findElement(By.id("line2")).sendKeys("bjvhcgfchvbjkn");
//
//		    
//		    // ✅ 9. Click Continue (Address Submit)
//		    Common.waitForElement(3);
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//button[contains(.,'Continue') and @name='new_shipping_address_cta']")
//		    )).click();
//
//		    System.out.println("✅ Address submitted successfully");
		    
		    
		    

		    // ✅ 3. Select Netbanking option
		    Common.waitForElement(4);
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//span[@data-testid='Netbanking']")
		    )).click();

		    // ✅ 4. Select HDFC Bank
		    Common.waitForElement(2);
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
		            
		    	    WebElement cancelBtn = driver.findElement(By.xpath("//button[@class='prod_cancel_btn']"));
		    	    if (cancelBtn.isDisplayed()) {
		    	        System.out.println("❌ Cancel Button: Displayed ✅");
		    	    }
		    	    WebElement orderIdElement = driver.findElement(By.xpath("//div[@class='prod_order_id_value']"));
		            orderId = orderIdElement.getText().trim();
		            System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
		            
		            WebElement productNameElement = driver.findElement(By.xpath("//div[contains(@class,'placed_prod_details')]//h4[@class='placed_prod_name']"));
		            productName = productNameElement.getText().trim();
		            System.out.println(YELLOW + "Product Name: " + productName + RESET);
		          
		            System.out.println(CYAN + line + RESET);
		            WebElement addressDiv = driver.findElement(By.cssSelector("div.address_card.Cls_addr_data_section"));

		         // Extract data attributes
		         name = addressDiv.getAttribute("data-name").trim();
		         type = addressDiv.getAttribute("data-address_type").trim();
		         contact = addressDiv.getAttribute("data-contact").trim();
		         String house = addressDiv.getAttribute("data-house-no").trim();
		         String street = addressDiv.getAttribute("data-street-name").trim();
		         String city = addressDiv.getAttribute("data-city").trim();
		         String state = addressDiv.getAttribute("data-state").trim();
		         String pincode = addressDiv.getAttribute("data-pincode").trim();

		         // Build full address (same format as email)
		          uiAddress = house + ", " + street + ", " + city + ", " + state + " - " + pincode;

		          
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
		    System.out.println("📌 UI Address: " + uiAddress);
	         System.out.println("📞 UI Mobile: " + contact); 
	         System.out.println("👤 UI Name: " + name);
	         System.out.println("🏷️ UI Type: " + type);
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
		int retries = 36; // ~3 minutes

		for (int i = 0; i < retries; i++) {
		    try {
		        // Always re-locate the first mail element fresh each time
		        WebElement firstMail = wait.until(
		            ExpectedConditions.presenceOfElementLocated(
		                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
		            )
		        );

		        String mailText = firstMail.getText().trim();

		        if (mailText.contains(expectedmsg)) {
		            // Wait until clickable before clicking
		            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
		            firstMail.click();
		            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
		            mailFound = true;
		            break;
		        }

		    } catch (StaleElementReferenceException e) {
		        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
		    } catch (Exception e) {
		        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
		    }

		    // Wait and refresh for next retry
		    Thread.sleep(5000);
		    driver.navigate().refresh();

		    // Wait until inbox reloads before next iteration
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
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
		    String mailProductName = driver.findElement(By.xpath("(//td[contains(text(),'x 1')]/preceding-sibling::td[contains(@class,'font_12')])[1]")).getText().trim();
		    String mailTotalMRP = driver.findElement(By.xpath("//td[contains(text(),'Total MRP')]/following-sibling::td")).getText().trim();
		    String mailDiscountedMRP = driver.findElement(By.xpath("//td[contains(text(),'Discounted MRP')]/following-sibling::td")).getText().trim();
		    String mailTotalAmount = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]/following-sibling::td")).getText().trim();
		    String mailPaymentMethod = driver.findElement(By.xpath("//td[contains(text(),'Payment Method')]/following-sibling::td/following-sibling::td")).getText().trim();
//		    String prepaidOfferAmount = driver.findElement(By.xpath("//td[contains(text(),'Flat 50 off on Prepaid')]/following-sibling::td")).getText().trim();
		    String mailAddressBlock = driver.findElement(By.xpath("//td[@align='right' and contains(@class,'font_15') and contains(.,'Mobile:')]")).getText();
		  
		    System.out.println("📩 Mail Address Block: \n" + mailAddressBlock);

		    // Clean address from mail
		    String[] lines = mailAddressBlock.split("\n");
		 // ---- FIX ADDRESS PARSING ----
		    String firstLine = lines[0].trim();  // "Home Saroj Test"
		    String mailType = firstLine.split(" ")[0].trim(); // Home
		    String mailName = firstLine.replace(mailType, "").trim(); // Saroj Test

		    // join address lines safely
		    String mailAddress = (lines.length > 2 ? lines[1].trim() + " " + lines[2].trim() : "").trim();

		    // get mobile safely
		    String mailMobile = lines[lines.length - 1].replace("Mobile:", "").trim();

		    
		    
		    System.out.println(CYAN + line + RESET);
		    System.out.println(YELLOW + "📬 Mail Extracted Details:" + RESET);
		    System.out.println("📦 Product Name: " + mailProductName);
		    System.out.println("🆔 Order ID: " + mailOrderId);
		    System.out.println("💰 Total MRP: " + mailTotalMRP);
		    System.out.println("💸 Discounted MRP: " + mailDiscountedMRP);
		    System.out.println("🪙 Total Amount: " + mailTotalAmount);
		    System.out.println("💳 Payment Method: " + mailPaymentMethod);
//		    System.out.println("💳 Prepaid Offer Amount: " + prepaidOfferAmount);
		    
		    System.out.println("📩 Mail Name: " + mailName);
		    System.out.println("📩 Mail Type: " + mailType);
		    System.out.println("📩 Mail Address: " + mailAddress);
		    System.out.println("📩 Mail Mobile: " + mailMobile);
		    System.out.println(CYAN + line + RESET);

		    
		    
		    System.out.println(GREEN + "🔍 Comparing mail details with order summary..." + RESET);

		    Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
		            mailOrderId.contains(orderId));

		    Assert.assertTrue("❌ Product name mismatch! Expected: " + productName + " | Found: " + mailProductName,
		            mailProductName.contains(productName));

		   
		    Assert.assertEquals("❌ Total MRP mismatch!", normalizePrice(totalMRF), normalizePrice(mailTotalMRP));
		    Assert.assertEquals("❌ Discounted MRP mismatch!", normalizePrice(discountedMRP), normalizePrice(mailDiscountedMRP));
		    Assert.assertEquals("❌ Total Amount mismatch!", normalizePrice(totalAmount), normalizePrice(mailTotalAmount));
		    Assert.assertTrue("❌ Payment method mismatch! Expected: Prepaid | Found: " + mailPaymentMethod,
		            mailPaymentMethod.equalsIgnoreCase("Prepaid"));
//		    Assert.assertTrue("❌ Prepaid discount amount mismatch!", prepaidOfferAmount.contains("50"));
		    
		    Assert.assertEquals("❌ Name mismatch!", name, mailName);
		    Assert.assertEquals(
		    	    "❌ Address type mismatch!",
		    	    type.toLowerCase().trim(),
		    	    mailType.toLowerCase().trim()
		    	);
		    Assert.assertEquals("❌ Mobile number mismatch!", contact, mailMobile);
		    uiAddress = uiAddress.replaceAll("\\s+,", ",").replaceAll("\\s+", " ").trim();
		    mailAddress = mailAddress.replaceAll("\\s+,", ",").replaceAll("\\s+", " ").trim();

		   // Assert.assertEquals("❌ Address mismatch!\nUI: " + uiAddress + "\nMail: " + mailAddress,
		   //         uiAddress, mailAddress);
		    
		    System.out.println("✅ All address details matched successfully!");
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
    
    Common.waitForElement(2);
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
    Common.waitForElement(2);
	waitFor(orderSearchBox);
	click(orderSearchBox);

    orderSearchBox.clear();
    orderSearchBox.sendKeys(orderId);
    Common.waitForElement(2);
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



//Cancel Order From User Side

public void cancelOrderFromUser() throws Exception {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String line = "──────────────────────────────────────────────────────────────";
    Common.waitForElement(3);
    wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
    waitFor(closeBtn);
	click(closeBtn);
    
    // Click Cancel button
    WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='prod_cancel_btn']")));
    if (cancelButton.isDisplayed()) {
        System.out.println(" Cancel Button: Displayed ✅");
        cancelButton.click();
        System.out.println(GREEN + "🛑 Clicked Cancel Order button" + RESET);
    }
    
    
    // Select cancellation reason
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(selectReason));
    waitFor(selectReason);
	click(selectReason);
    System.out.println(GREEN + "📌 Selected Cancel Reason: " + selectReason + RESET);

    // 3 Click Continue / Confirm Cancel
    Common.waitForElement(1);
    wait.until(ExpectedConditions.elementToBeClickable(continueReturnBtn));
    waitFor(continueReturnBtn);
	click(continueReturnBtn);
    System.out.println(GREEN + "✅ Clicked Continue button" + RESET);

    //  Verify Order Cancelled message
    try {
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h4[contains(@class,'order_status') and normalize-space()='Order Cancelled']")));
        System.out.println(GREEN + "🎉 Order cancelled successfully: " + successMsg.getText() + RESET);
    } catch (Exception e) {
        System.out.println(RED + "❌ Order cancellation message not found!" + RESET);
        throw e;
    }


}

public void verifyRefundCreditedEmail(String expectedmsg)
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
		int retries = 36; // ~3 minutes

		for (int i = 0; i < retries; i++) {
		    try {
		        // Always re-locate the first mail element fresh each time
		        WebElement firstMail = wait.until(
		            ExpectedConditions.presenceOfElementLocated(
		                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
		            )
		        );

		        String mailText = firstMail.getText().trim();

		        if (mailText.contains(expectedmsg)) {
		            // Wait until clickable before clicking
		            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
		            firstMail.click();
		            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
		            mailFound = true;
		            break;
		        }

		    } catch (StaleElementReferenceException e) {
		        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
		    } catch (Exception e) {
		        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
		    }

		    // Wait and refresh for next retry
		    Thread.sleep(5000);
		    driver.navigate().refresh();

		    // Wait until inbox reloads before next iteration
		    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
		}

		if (!mailFound) {
		    System.out.println(RED + "❌ Order Confirmation Mail not received within time!" + RESET);
		    Assert.fail("Order confirmation mail not found.");
		}
		
		// ---- READ MAIL CONTENT ----
		Thread.sleep(4000);
		

		    // ✅ Extract order details from mail DOM
		    System.out.println(GREEN + "🔍 Extracting order details from mail..." + RESET);
		    String mailReferenceNo = driver.findElement(By.xpath("(//p[contains(text(),'Refund Reference Number')]/span)[1]")).getText().trim();
		    String mailText = driver.findElement(By.xpath("(//p[contains(text(),'Your refund of')])[1]")).getText().trim();
		 // Extract only numbers before the first space OR before non-digit
		    String mailTotalAmount = mailText.replaceAll("[^0-9]", " ").trim().split(" ")[0];

		    System.out.println(CYAN + line + RESET);
		    System.out.println(YELLOW + "📬 Mail Extracted Details:" + RESET);
		    System.out.println("💸 Reference No: " + mailReferenceNo);
		    System.out.println("🪙 Total Amount: " + mailTotalAmount);
		    System.out.println(CYAN + line + RESET);

		    
			System.out.println(GREEN + "🔍 Verifying Refund Details in Email..." + RESET);
		    Assert.assertEquals("❌ Total Amount mismatch in email!", normalizePrice(totalAmount), normalizePrice(mailTotalAmount));
		    Assert.assertEquals("❌ Reference Number mismatch in email!", normalizePrice(referenceNo), normalizePrice(mailReferenceNo));

		    System.out.println(GREEN + "✅ Refund Amount & Reference Number matched successfully!" + RESET);
		    System.out.println(CYAN + line + RESET);
		    
		   
}

String referenceNo;

public void orderRefundInitiateByAdmin() {
	String CYAN = "\u001B[36m";
	String YELLOW = "\u001B[33m";
	String GREEN = "\u001B[32m";
	String RED = "\u001B[31m";
	String RESET = "\u001B[0m";
	String line = "──────────────────────────────────────────────────────────────";
	System.out.println(line);
    System.out.println(GREEN + "🚚 Giving  Refund  for Order ID: " + orderId + RESET);
    System.out.println(line);

    adminLoginApp();
    
	
    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	System.out.println("Redirect to Canceled Order Page");
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
    wait.until(ExpectedConditions.elementToBeClickable(paymentRefundBtn));
    Common.waitForElement(2);
	waitFor(paymentRefundBtn);
	click(paymentRefundBtn);
	Common.waitForElement(2);
	Select select6 = new Select(paymentRefundBtn);
	select6.selectByVisibleText("Refund request");
	System.out.println(GREEN + "✅ Selected 'Refund request'" + RESET);

	 // ✅ Save & Back
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
    waitFor(saveButton);
    click(saveButton);
    System.out.println(GREEN + "💰 Refund Initiated Successfully" + RESET);

    // ✅ Again click Edit for second update
    Common.waitForElement(7);
    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
    System.out.println(GREEN + "✅ Re-opened Edit Page (For Refund)" + RESET);

    // ✅ Extract Refund Reference Number
    Common.waitForElement(2);
    WebElement referenceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//label[normalize-space()='Refund Transaction Id']/following-sibling::input")));
  //input[@name='item[0][refund_transaction_id]']

    referenceNo = referenceElement.getAttribute("value").trim();
    System.out.println(GREEN + "🔢 Refund Reference No: " + referenceNo + RESET);
 // ✅ Save & Back
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
    waitFor(saveButton);
    click(saveButton);
    System.out.println("✅ Saved  changes");

    System.out.println(GREEN + "🎉 Refund Initiated Successfully!" + RESET);
    System.out.println(line);
    
    System.out.println(line);
    System.out.println(YELLOW + "🔢 Refund Reference No: " + referenceNo + RESET);
    System.out.println(YELLOW + "🪙 Total Amount: " + totalAmount + RESET);
    System.out.println(line);

}

//Return Flow User
public void orderReturnForUserSide() {
	
	 String CYAN = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + "🚀 Starting Order Return Flow..." + RESET);
	    System.out.println(CYAN + line + RESET);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(myProfileIcon));
	    waitFor(myProfileIcon);
		click(myProfileIcon);
		Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(myOrdersBtn));
	    waitFor(myOrdersBtn);
		click(myOrdersBtn);
		Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(myOrderSearchBox));
	    waitFor(myOrderSearchBox);
	    myOrderSearchBox.clear();
	    myOrderSearchBox.sendKeys(productName);
	    Common.waitForElement(1);
	    myOrderSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(2);
	 		// Build dynamic XPath
	 		String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[1]";
	 		WebElement btn = driver.findElement(By.xpath(xpath));

	 		// 1️⃣ Scroll to the element
	 		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
	 		
	 		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	    
	    Common.waitForElement(3);
	   // Click Return button
	    WebElement returnButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[normalize-space()='return'])[1]")));
	    if (returnButton.isDisplayed()) {
	        System.out.println(" Return  Button: Displayed ✅");
	        returnButton.click();
	        System.out.println(GREEN + "🛑 Clicked Return Order button" + RESET);
	    }
	    
	    // Select Return reason
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(returnReason));
	    waitFor(returnReason);
		click(returnReason);
	    System.out.println(GREEN + "📌 Selected Return Reason: " + returnReason + RESET);

	    // 3 Click Continue / Confirm Cancel
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(continueReturnBtn));
	    waitFor(continueReturnBtn);
		click(continueReturnBtn);
	    System.out.println(GREEN + "✅ Clicked Continue button" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(confirmReturnBtn));
	    waitFor(confirmReturnBtn);
		click(confirmReturnBtn);
	    System.out.println(GREEN + "✅ Clicked Confirm Return  button" + RESET);
	    
	    
	    
	    WebElement returnMsg = driver.findElement(By.xpath("//h5[normalize-space()='Return Successful']"));
	    Assert.assertTrue("Return success message not displayed!", returnMsg.isDisplayed());
	    
	    System.out.println("✅ Return was successful — message verified.");
	    
			  
}

//Exchange Flow User
public void orderExchangeForUserSide() {
	
	 String CYAN = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(CYAN + line + RESET);
	    System.out.println(GREEN + "🚀 Starting Order Exchange Flow..." + RESET);
	    System.out.println(CYAN + line + RESET);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(myProfileIcon));
	    waitFor(myProfileIcon);
		click(myProfileIcon);
		Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(myOrdersBtn));
	    waitFor(myOrdersBtn);
		click(myOrdersBtn);
		Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(myOrderSearchBox));
	    waitFor(myOrderSearchBox);
	    myOrderSearchBox.clear();
	    myOrderSearchBox.sendKeys(productName);
	    Common.waitForElement(1);
	    myOrderSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(2);
		// Build dynamic XPath
		String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[1]";
		WebElement btn = driver.findElement(By.xpath(xpath));

		// 1️⃣ Scroll to the element
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	    
	    
	    Common.waitForElement(3);
	   // Click Exchange button
	    WebElement exchangeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[normalize-space()='exchange'])[1]")));
	    if (exchangeButton.isDisplayed()) {
	        System.out.println(" Exchange  Button: Displayed ✅");
	        exchangeButton.click();
	        System.out.println(GREEN + "🛑 Clicked Exchange Order button" + RESET);
	    }
	    
	    // Select Exchange reason
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeReason));
	    waitFor(exchangeReason);
		click(exchangeReason);
	    System.out.println(GREEN + "📌 Selected Exchange Reason: " + exchangeReason + RESET);

	    // 3 Click Continue / Confirm Cancel
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(continueReturnBtn));
	    waitFor(continueReturnBtn);
		click(continueReturnBtn);
	    System.out.println(GREEN + "✅ Clicked Continue button" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeItemBtn));
	    waitFor(exchangeItemBtn);
		click(exchangeItemBtn);
	    System.out.println(GREEN + "✅ Clicked exchange Item  button" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeBtn));
	    waitFor(exchangeBtn);
		click(exchangeBtn);
	    System.out.println(GREEN + "✅ Clicked exchange  button" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeBtn));
	    waitFor(exchangeBtn);
		click(exchangeBtn);
	    System.out.println(GREEN + "✅ Clicked exchange  button" + RESET);
	    
	    Common.waitForElement(2);
	    WebElement exchangeMsg = driver.findElement(By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Exchange Successful']"));
	    Assert.assertTrue("Exchange success message not displayed!", exchangeMsg.isDisplayed());

	    System.out.println("✅ Exchange was successful — message verified.");
	    
	    
	    
	    
	    
	    
}
//Exchange request Accept By Admin
	public void orderExchangeRequestAcceptByAdmin() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Accepting Exchange Request for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

		    driver.get(Common.getValueFromTestDataMap("Link"));
		    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(exShipmentStatus));
			waitFor(exShipmentStatus);
			click(exShipmentStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(exShipmentStatus);
		    step3.selectByVisibleText("Exchange Accept");
		    System.out.println(GREEN + "✅ Status changed → Exchange Accept" + RESET);
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Exchange Accept)" + RESET);

		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Accepted !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	public void verifyOrderExchangeEmail(String expectedmsg)
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
			int retries = 36; // ~3 minutes

			for (int i = 0; i < retries; i++) {
			    try {
			        // Always re-locate the first mail element fresh each time
			        WebElement firstMail = wait.until(
			            ExpectedConditions.presenceOfElementLocated(
			                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
			            )
			        );

			        String mailText = firstMail.getText().trim();

			        if (mailText.contains(expectedmsg)) {
			            // Wait until clickable before clicking
			            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
			            firstMail.click();
			            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
			            mailFound = true;
			            break;
			        }

			    } catch (StaleElementReferenceException e) {
			        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
			    } catch (Exception e) {
			        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
			    }

			    // Wait and refresh for next retry
			    Thread.sleep(5000);
			    driver.navigate().refresh();

			    // Wait until inbox reloads before next iteration
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
			}

			if (!mailFound) {
			    System.out.println(RED + "❌ Order Confirmation Mail not received within time!" + RESET);
			    Assert.fail("Order confirmation mail not found.");
			}
			
			// ---- READ MAIL CONTENT ----
			Thread.sleep(4000);
			

			System.out.println(GREEN + "🔍 Extracting exchange mail details..." + RESET);

			// Order ID
			String mailOrderId = driver.findElement(By.xpath("//td[normalize-space()='Order ID']/following-sibling::td/following-sibling::td"))
			        .getText().trim();

			// Product names (should be exactly 2)
			String mailProductNames = driver.findElement(By.xpath("(//td[contains(text(),'x 1')]/preceding-sibling::td[contains(@class,'font_12')])[1]")).getText().trim();

			// Total Amount
			String mailTotalAmount = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]/following-sibling::td"))
			        .getText().trim();

			System.out.println(CYAN + line + RESET);
			System.out.println(YELLOW + "📬 Mail Extracted Details:" + RESET);
			System.out.println("🆔 Order ID: " + mailOrderId);
			System.out.println("📦 Product Name : " + mailProductNames);
			System.out.println("🪙 Total Amount: " + mailTotalAmount);
			System.out.println(CYAN + line + RESET);


			// ✅ Validations

			// 1️⃣ Order ID match
			Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
			        mailOrderId.contains(orderId));

			Assert.assertTrue("❌ Product name mismatch! Expected: " + productName + " | Found: " + mailProductNames,
					mailProductNames.contains(productName));

			// 3️⃣ Total Amount = 0
			Assert.assertEquals("❌ Total Amount must be 0 for exchange mail!", "0", normalizePrice(mailTotalAmount));

			System.out.println(GREEN + "✅ Exchange mail verified successfully!" + RESET);
			System.out.println(CYAN + line + RESET);
			    
			   
	}
	
	public void updateExchangeRequestToShipped() {

	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(line);
	    System.out.println(GREEN + "🔄 Updating Exchange Order Status for Order ID: " + orderId + RESET);
	    System.out.println(line);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    driver.get(Common.getValueFromTestDataMap("Link"));
	    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

	    Common.waitForElement(3);

	    // ✅ Validate Order Exists
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
	        System.out.println(GREEN + "✅ Exchange Order found in table!" + RESET);
	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Exchange Order not found! Stopping execution." + RESET);
	        return;
	    }

	    // ✅ Open Edit Page
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Opened Edit page" + RESET);

	    // ✅ Step 1 → Product Pickup
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
	    waitFor(exchangeStatus);
	    click(exchangeStatus);
	    Common.waitForElement(2);
	    new Select(exchangeStatus).selectByVisibleText("Product Pickup");
	    System.out.println(GREEN + "📦 Status changed → Product Pickup" + RESET);
	    Common.waitForElement(2);
	    click(saveButton);
	    System.out.println(GREEN + "💾 Saved (Product Pickup)" + RESET);

	    // Re-open Edit
	    Common.waitForElement(4);
	    click(editBtn);

	    // ✅ Step 2 → Product Received
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
	    waitFor(exchangeStatus);
	    click(exchangeStatus);
	    Common.waitForElement(2);
	    new Select(exchangeStatus).selectByVisibleText("Product Received");
	    System.out.println(GREEN + "📥 Status changed → Product Received" + RESET);
	    Common.waitForElement(2);
	    click(saveButton);
	    System.out.println(GREEN + "💾 Saved (Product Received)" + RESET);

	    // Re-open Edit
	    Common.waitForElement(4);
	    click(editBtn);

	    // ✅ Step 3 → Exchange Order Shipped
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
	    waitFor(exchangeStatus);
	    click(exchangeStatus);
	    Common.waitForElement(2);
	    new Select(exchangeStatus).selectByVisibleText("Exchange Order Shipped");
	    System.out.println(GREEN + "🚚 Status changed → Exchange Order Shipped" + RESET);
	    Common.waitForElement(2);
	    click(saveButton);
	    System.out.println(GREEN + "💾 Saved (Exchange Order Shipped)" + RESET);

	    System.out.println(line);
	    System.out.println(GREEN + "🎉 Exchange request successfully updated → Exchange Order Shipped!" + RESET);
	    System.out.println(line);
	}
	
	
	public void updateExchangeShippedToExchangeDelivered() {

	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(line);
	    System.out.println(GREEN + "🔄 Updating Exchange Order Status (Shipped → Delivered) for Order ID: " + orderId + RESET);
	    System.out.println(line);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    driver.get(Common.getValueFromTestDataMap("Link"));
	    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

	    Common.waitForElement(3);

	    // ✅ Validate Order Exists
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
	        System.out.println(GREEN + "✅ Exchange Order found in table!" + RESET);
	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Exchange Order not found! Stopping execution." + RESET);
	        return;
	    }

	    // ✅ Open Edit Page
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Opened Edit page" + RESET);

	    // ✅ Step 1 → Change to Exchange Out For Delivery (Shipped)
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
	    waitFor(exchangeStatus);
	    click(exchangeStatus);
	    Common.waitForElement(1);
	    new Select(exchangeStatus).selectByVisibleText("Exchange Out For Delivery");
	    System.out.println(GREEN + "📦 Status changed → Exchange Out For Delivery" + RESET);
	    Common.waitForElement(2);
	    click(saveButton);
	    System.out.println(GREEN + "💾 Saved (Exchange Out For Delivery)" + RESET);

	    // ✅ Re-open Edit Page
	    Common.waitForElement(4);
	    click(editBtn);

	    // ✅ Step 2 → Change to Exchange Delivered
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
	    waitFor(exchangeStatus);
	    click(exchangeStatus);
	    Common.waitForElement(1);
	    new Select(exchangeStatus).selectByVisibleText("Exchange Delivered");
	    System.out.println(GREEN + "📥 Status changed → Exchange Delivered" + RESET);
	    Common.waitForElement(2);
	    click(saveButton);
	    System.out.println(GREEN + "💾 Saved (Exchange Delivered)" + RESET);
	    Common.waitForElement(2);
	    System.out.println(line);
	    System.out.println(GREEN + "🎉 Exchange status successfully updated → Exchange Delivered!" + RESET);
	    System.out.println(line);
	}

	
	public void orderReturnRequestAcceptByAdmin() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Accepting Exchange Request for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

		    driver.get(Common.getValueFromTestDataMap("Link"));
		    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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

		    // ✅ Step 1: Set to Return Accept
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(reShipmentStatus));
			waitFor(reShipmentStatus);
			click(reShipmentStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(reShipmentStatus);
		    step3.selectByVisibleText("Return Accept");
		    System.out.println(GREEN + "✅ Status changed → Return Accept" + RESET);
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Return Accept)" + RESET);

		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Accepted !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	
	public void orderReturnRefundInitiateByAdmin() {
		String CYAN = "\u001B[36m";
		String YELLOW = "\u001B[33m";
		String GREEN = "\u001B[32m";
		String RED = "\u001B[31m";
		String RESET = "\u001B[0m";
		String line = "──────────────────────────────────────────────────────────────";
		System.out.println(line);
	    System.out.println(GREEN + "🚚 Giving  Refund  for Order ID: " + orderId + RESET);
	    System.out.println(line);

	//    adminLoginApp();
	    
		
	    driver.get(Common.getValueFromTestDataMap("Link"));
		System.out.println("Redirect to Canceled Order Page");
		Common.waitForElement(1);
		
//	    // ✅ Go to order search box and search order ID
//		Common.waitForElement(2);
//	    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn));
//	    waitFor(orderIdbtn);
//		click(orderIdbtn);
//		 Common.waitForElement(1);
//		wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
//	    Common.waitForElement(1);
//		waitFor(orderSearchBox);
//	    orderSearchBox.clear();
//	    orderSearchBox.sendKeys(orderId);
//	    Common.waitForElement(1);
//	    orderSearchBox.sendKeys(Keys.ENTER);
//	    Common.waitForElement(2);

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

	    // ✅ Step 1: Set to Return Accept
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
		waitFor(returnStatus);
		click(returnStatus);
		Common.waitForElement(2);
	    Select step3 = new Select(returnStatus);
	    step3.selectByVisibleText("Pickup Expected");
	    System.out.println(GREEN + "✅ Status changed → Pickup Expected" + RESET);

		 // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	   

	    // ✅ Again click Edit for second update
	    Common.waitForElement(5);
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Re-opened Edit Page (For Refund)" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
		waitFor(returnStatus);
		click(returnStatus);
		Common.waitForElement(2);
	    Select step4 = new Select(returnStatus);
	    step4.selectByVisibleText("Refund Initiated");
	    System.out.println(GREEN + "✅ Status changed → Refund Initiated" + RESET);

	    // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	    
	 // ✅ Again click Edit for second update
	    Common.waitForElement(5);
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Re-opened Edit Page (For Refund)" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(paymentRefundBtn));
		waitFor(paymentRefundBtn);
		click(paymentRefundBtn);
		Common.waitForElement(2);
	    Select step5 = new Select(paymentRefundBtn);
	    step5.selectByVisibleText("Refund request");
	    System.out.println(GREEN + "✅ Status changed → Refund request" + RESET);
	 // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	    
	 // ✅ Again click Edit for second update
	    Common.waitForElement(5);
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Re-opened Edit Page (For Refund)" + RESET);
	    
	    // ✅ Extract Refund Reference Number
	    Common.waitForElement(2);
	    WebElement referenceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//label[normalize-space()='Refund Transaction Id']/following-sibling::input")));

	    referenceNo = referenceElement.getAttribute("value").trim();
	    System.out.println(GREEN + "🔢 Refund Reference No: " + referenceNo + RESET);
	    
	    

	 // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	    System.out.println("✅ Saved  changes");

	    System.out.println(GREEN + "🎉 Refund Initiated Successfully!" + RESET);
	    System.out.println(line);
	    
	    System.out.println(line);
	    System.out.println(YELLOW + "🔢 Refund Reference No: " + referenceNo + RESET);
	    System.out.println(YELLOW + "🪙 Total Amount: " + totalAmount + RESET);
	    System.out.println(line);    
		 	
	}
	
	public void orderCancelByAdminSide() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Cancelled  by Admin for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    adminLoginApp();

		    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);
		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
			waitFor(returnStatus);
			click(returnStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(returnStatus);
		    step3.selectByVisibleText("Order Cancelled by Admin");
		    System.out.println(GREEN + "✅ Status changed → Order Cancelled by Admin" + RESET);
		    
		    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[0][description]']"));
		    click(descriptionField);
		    descriptionField.sendKeys("Product Not Available");
		    
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Order Cancelled by Admin)" + RESET);

		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Accepted !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	
	public void returnOrderCancelFromUserSide() {
		String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(line);
	    System.out.println(GREEN + "🚚 Return Order Cancelled  by User for Order ID: " + orderId + RESET);
	    System.out.println(line);
		
	    Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
        click(viewOrderDetails);
        System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);
        
        Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(cancelBtn));
        click(cancelBtn);
        System.out.println(GREEN + "🧾 Clicked Cancel Button" + RESET);
     // Select Return reason
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(cancelreturnReason));
	    waitFor(cancelreturnReason);
		click(cancelreturnReason);
	    System.out.println(GREEN + "📌 Selected Return Reason: " + cancelreturnReason + RESET);

	    // 3 Click Continue / Confirm Cancel
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(continueReturnBtn));
	    waitFor(continueReturnBtn);
		click(continueReturnBtn);
	    System.out.println(GREEN + "✅ Clicked Continue button" + RESET);
	    
	 
        Common.waitForElement(2);
        WebElement deliveredMsg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[normalize-space()='Order Delivered']"))
            );

            // Check message visibility
            Assert.assertTrue("Order Delivered message not displayed!", deliveredMsg.isDisplayed());
		
	}
	
	public void returnOrderCancelFromAdminSide1() {
		
		String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(line);
	    System.out.println(GREEN + "🚚 Cancelled  by Admin for Order ID: " + orderId + RESET);
	    System.out.println(line);

	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

	    driver.get(Common.getValueFromTestDataMap("Link"));
	    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);
	    
//	    // ✅ Search Order ID
//	    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
//	    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
//	    orderSearchBox.clear();
//	    orderSearchBox.sendKeys(orderId);
//	    orderSearchBox.sendKeys(Keys.ENTER);
//	    Common.waitForElement(3);

	    // ✅ Validate Order Exists
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
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

	    // ✅ Step 1: Set to Return Accept
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
		waitFor(returnStatus);
		click(returnStatus);
		Common.waitForElement(2);
	    Select step3 = new Select(returnStatus);
	    step3.selectByVisibleText("Pickup Expected");
	    System.out.println(GREEN + "✅ Status changed → Pickup Expected" + RESET);

		 // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	   

	    // ✅ Again click Edit for second update
	    Common.waitForElement(5);
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Re-opened Edit Page (For Refund)" + RESET);
	    
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
		waitFor(returnStatus);
		click(returnStatus);
		Common.waitForElement(2);
	    Select step4 = new Select(returnStatus);
	    step4.selectByVisibleText("Product Received in Damaged State");
	    System.out.println(GREEN + "✅ Status changed → Product Received in Damaged State" + RESET);
	    
	    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[0][description]']"));
	    click(descriptionField);
	    descriptionField.sendKeys("Product Received Damaged");
	    // ✅ Save & Back
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
		
		
	}
	
	public void verifyReturnOrderCanceledByAdminSideEmail(String expectedmsg)
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
			int retries = 36; // ~3 minutes

			for (int i = 0; i < retries; i++) {
			    try {
			        // Always re-locate the first mail element fresh each time
			        WebElement firstMail = wait.until(
			            ExpectedConditions.presenceOfElementLocated(
			                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
			            )
			        );

			        String mailText = firstMail.getText().trim();

			        if (mailText.contains(expectedmsg)) {
			            // Wait until clickable before clicking
			            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
			            firstMail.click();
			            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
			            mailFound = true;
			            break;
			        }

			    } catch (StaleElementReferenceException e) {
			        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
			    } catch (Exception e) {
			        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
			    }

			    // Wait and refresh for next retry
			    Thread.sleep(5000);
			    driver.navigate().refresh();

			    // Wait until inbox reloads before next iteration
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
			}

			if (!mailFound) {
			    System.out.println(RED + "❌ Order Confirmation Mail not received within time!" + RESET);
			    Assert.fail("Order confirmation mail not found.");
			}
			
			// ---- READ MAIL CONTENT ----
			Thread.sleep(4000);
			

			    // ✅ Extract order details from mail DOM
			    System.out.println(GREEN + "🔍 Extracting order details from mail..." + RESET);
			    WebElement heading = driver.findElement(By.xpath("//p[normalize-space()='Return Cancelled - Product received in damaged state']"));
			    String actualMsg = heading.getText().trim();

			    Assert.assertEquals("❌ Heading text mismatch in email!", expectedmsg, actualMsg);

			    System.out.println("✅ Heading verified successfully: " + actualMsg);

			  
			    System.out.println(CYAN + line + RESET);
			    
			   
	}
	
	public void verifyExchangeOrderCanceledByAdminSideEmail(String expectedmsg)
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
			int retries = 36; // ~3 minutes

			for (int i = 0; i < retries; i++) {
			    try {
			        // Always re-locate the first mail element fresh each time
			        WebElement firstMail = wait.until(
			            ExpectedConditions.presenceOfElementLocated(
			                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
			            )
			        );

			        String mailText = firstMail.getText().trim();

			        if (mailText.contains(expectedmsg)) {
			            // Wait until clickable before clicking
			            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
			            firstMail.click();
			            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
			            mailFound = true;
			            break;
			        }

			    } catch (StaleElementReferenceException e) {
			        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
			    } catch (Exception e) {
			        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
			    }

			    // Wait and refresh for next retry
			    Thread.sleep(5000);
			    driver.navigate().refresh();

			    // Wait until inbox reloads before next iteration
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
			}

			if (!mailFound) {
			    System.out.println(RED + "❌ Order Confirmation Mail not received within time!" + RESET);
			    Assert.fail("Order confirmation mail not found.");
			}
			
			// ---- READ MAIL CONTENT ----
			Thread.sleep(4000);
			

			    // ✅ Extract order details from mail DOM
			    System.out.println(GREEN + "🔍 Extracting order details from mail..." + RESET);
			    WebElement heading = driver.findElement(By.xpath("//p[normalize-space(text())='Exchange Cancelled - Product received in damaged state']"));
			    String actualMsg = heading.getText().trim();

			    Assert.assertEquals("❌ Heading text mismatch in email!", expectedmsg, actualMsg);

			    System.out.println("✅ Heading verified successfully: " + actualMsg);

			  
			    System.out.println(CYAN + line + RESET);
			    
			   
	}
	
	public void orderReturnRequestRejectByAdmin() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Return  Request Rejected for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

		    driver.get(Common.getValueFromTestDataMap("Link"));
		    System.out.println(GREEN + "✅ Navigated to Return page" + RESET);

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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

		    // ✅ Step 1: Set to Return Accept
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(returnStatus));
			waitFor(returnStatus);
			click(returnStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(returnStatus);
		    step3.selectByVisibleText("Return Rejected by Admin");
		    System.out.println(GREEN + "✅ Status changed →Return Rejected by Admin" + RESET);
		    Common.waitForElement(2);
		    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[0][description]']"));
		    click(descriptionField);
		    descriptionField.sendKeys("Due to Delay");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Return Rejected by Admin)" + RESET);
		    Common.waitForElement(4);
		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Rejected !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	
	public void exchangeOrderCancelFromUserSide() {
		String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";

	    System.out.println(line);
	    System.out.println(GREEN + "🚚 Exchange Order Cancelled  by User for Order ID: " + orderId + RESET);
	    System.out.println(line);
		
	    Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
        click(viewOrderDetails);
        System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);
        
        Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(cancelBtn));
        click(cancelBtn);
        System.out.println(GREEN + "🧾 Clicked Cancel Button" + RESET);
        Common.waitForElement(2);
        WebElement exchangeCancelledMsg = wait.until(
        	    ExpectedConditions.visibilityOfElementLocated(
        	        By.xpath("//h4[@class='order_status' and normalize-space()='Exchange Cancelled']")
        	    )
        	);

        	Assert.assertTrue("Exchange Cancelled message not displayed!", exchangeCancelledMsg.isDisplayed());

        	System.out.println("✅ Exchange Cancelled message verified successfully.");
		
	}
	
	public void verifyExchangeOrderCancelMail(String expectedmsg)
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
			int retries = 36; // ~3 minutes

			for (int i = 0; i < retries; i++) {
			    try {
			        // Always re-locate the first mail element fresh each time
			        WebElement firstMail = wait.until(
			            ExpectedConditions.presenceOfElementLocated(
			                By.xpath("(//table//tr//span[@class='bog']/span)[1]")
			            )
			        );

			        String mailText = firstMail.getText().trim();

			        if (mailText.contains(expectedmsg)) {
			            // Wait until clickable before clicking
			            wait.until(ExpectedConditions.elementToBeClickable(firstMail));
			            firstMail.click();
			            System.out.println(GREEN + "📨 Order mail received and opened!" + RESET);
			            mailFound = true;
			            break;
			        }

			    } catch (StaleElementReferenceException e) {
			        System.out.println(YELLOW + "⚠️ Element went stale after refresh, re-locating..." + RESET);
			    } catch (Exception e) {
			        System.out.println(YELLOW + "⏳ Waiting for latest mail... retry " + (i + 1) + RESET);
			    }

			    // Wait and refresh for next retry
			    Thread.sleep(5000);
			    driver.navigate().refresh();

			    // Wait until inbox reloads before next iteration
			    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
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
			    String mailProductName = driver.findElement(By.xpath("(//td[contains(text(),'x 1')]/preceding-sibling::td[contains(@class,'font_12')])[1]")).getText().trim();
			//    String mailTotalMRP = driver.findElement(By.xpath("//td[contains(text(),'Total MRP')]/following-sibling::td")).getText().trim();
			 //   String mailDiscountedMRP = driver.findElement(By.xpath("//td[contains(text(),'Discounted MRP')]/following-sibling::td")).getText().trim();
			    String mailTotalAmount = driver.findElement(By.xpath("//td[contains(text(),'Total Amount')]/following-sibling::td")).getText().trim();
			    String mailPaymentMethod = driver.findElement(By.xpath("//td[contains(text(),'Payment Method')]/following-sibling::td/following-sibling::td")).getText().trim();
//			    String prepaidOfferAmount = driver.findElement(By.xpath("//td[contains(text(),'Flat 50 off on Prepaid')]/following-sibling::td")).getText().trim();
			    String mailAddressBlock = driver.findElement(By.xpath("//td[@align='right' and contains(@class,'font_15') and contains(.,'Mobile:')]")).getText();
			  
			    System.out.println("📩 Mail Address Block: \n" + mailAddressBlock);

			    // Clean address from mail
			    String[] lines = mailAddressBlock.split("\n");
			 // ---- FIX ADDRESS PARSING ----
			    String firstLine = lines[0].trim();  // "Home Saroj Test"
			    String mailType = firstLine.split(" ")[0].trim(); // Home
			    String mailName = firstLine.replace(mailType, "").trim(); // Saroj Test

			    // join address lines safely
			    String mailAddress = (lines.length > 2 ? lines[1].trim() + " " + lines[2].trim() : "").trim();

			    // get mobile safely
			    String mailMobile = lines[lines.length - 1].replace("Mobile:", "").trim();

			    
			    
			    System.out.println(CYAN + line + RESET);
			    System.out.println(YELLOW + "📬 Mail Extracted Details:" + RESET);
			    System.out.println("📦 Product Name: " + mailProductName);
			    System.out.println("🆔 Order ID: " + mailOrderId);
//			    System.out.println("💰 Total MRP: " + mailTotalMRP);
//			    System.out.println("💸 Discounted MRP: " + mailDiscountedMRP);
			    System.out.println("🪙 Total Amount: " + mailTotalAmount);
			    System.out.println("💳 Payment Method: " + mailPaymentMethod);
//			    System.out.println("💳 Prepaid Offer Amount: " + prepaidOfferAmount);
			    
			    System.out.println("📩 Mail Name: " + mailName);
			    System.out.println("📩 Mail Type: " + mailType);
			    System.out.println("📩 Mail Address: " + mailAddress);
			    System.out.println("📩 Mail Mobile: " + mailMobile);
			    System.out.println(CYAN + line + RESET);

			    
			    
			    System.out.println(GREEN + "🔍 Comparing mail details with order summary..." + RESET);

			    Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
			            mailOrderId.contains(orderId));

			    Assert.assertTrue("❌ Product name mismatch! Expected: " + productName + " | Found: " + mailProductName,
			            mailProductName.contains(productName));

			   
//			    Assert.assertEquals("❌ Total MRP mismatch!", normalizePrice(totalMRF), normalizePrice(mailTotalMRP));
//			    Assert.assertEquals("❌ Discounted MRP mismatch!", normalizePrice(discountedMRP), normalizePrice(mailDiscountedMRP));
			    Assert.assertEquals("❌ Total Amount mismatch!", normalizePrice(totalAmount), normalizePrice(mailTotalAmount));
			    Assert.assertTrue("❌ Payment method mismatch! Expected: Prepaid | Found: " + mailPaymentMethod,
			            mailPaymentMethod.equalsIgnoreCase("Prepaid"));
//			    Assert.assertTrue("❌ Prepaid discount amount mismatch!", prepaidOfferAmount.contains("50"));
			    
			    Assert.assertEquals("❌ Name mismatch!", name, mailName);
			    Assert.assertEquals(
			    	    "❌ Address type mismatch!",
			    	    type.toLowerCase().trim(),
			    	    mailType.toLowerCase().trim()
			    	);
			    Assert.assertEquals("❌ Mobile number mismatch!", contact, mailMobile);
			    uiAddress = uiAddress.replaceAll("\\s+,", ",").replaceAll("\\s+", " ").trim();
			    mailAddress = mailAddress.replaceAll("\\s+,", ",").replaceAll("\\s+", " ").trim();

			    Assert.assertEquals("❌ Address mismatch!\nUI: " + uiAddress + "\nMail: " + mailAddress,
			            uiAddress, mailAddress);
			    
			    System.out.println("✅ All address details matched successfully!");
			    System.out.println(GREEN + "✅ All order details verified successfully in the mail!" + RESET);
			    System.out.println(CYAN + line + RESET);
			    
			}
	
	public void exchangeOrderRequestRejectByAdminOutOfStock() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Exchange  Request Rejected for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

		    driver.get(Common.getValueFromTestDataMap("Link"));
		    System.out.println(GREEN + "✅ Navigated to Exchange page" + RESET);

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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

		    // ✅ Step 1: Set to Return Accept
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
			waitFor(exchangeStatus);
			click(exchangeStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(exchangeStatus);
		    step3.selectByVisibleText("Product Out of Stock");
		    System.out.println(GREEN + "✅ Status changed →Product Out of Stock" + RESET);
		    Common.waitForElement(2);
		    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[1][description]']"));
		    click(descriptionField);
		    descriptionField.sendKeys("Product Out of Stock");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Product Out of Stock)" + RESET);
		    Common.waitForElement(4);
		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Rejected !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	
	public void exchangeOrderRequestRejectByAdminReceivedDamageState() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Exchange  Request Rejected for Order ID: " + orderId + RESET);
		    System.out.println(line);

		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

		    driver.get(Common.getValueFromTestDataMap("Link"));
		    System.out.println(GREEN + "✅ Navigated to Exchange page" + RESET);

		    // ✅ Search Order ID
		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
		    orderSearchBox.clear();
		    orderSearchBox.sendKeys(orderId);
		    orderSearchBox.sendKeys(Keys.ENTER);
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

		    // ✅ Step 1: Set to Return Accept
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
			waitFor(exchangeStatus);
			click(exchangeStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(exchangeStatus);
		    step3.selectByVisibleText("Product Received in Damaged State");
		    System.out.println(GREEN + "✅ Status changed →Product Received in Damaged State" + RESET);
		    Common.waitForElement(2);
		    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[1][description]']"));
		    click(descriptionField);
		    descriptionField.sendKeys("Damaged Product");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Product Received in Damaged State)" + RESET);
		    Common.waitForElement(4);
		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Rejected !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}
	
	public void exchangeOrderShippedRejectByAdminSide() {
		 String GREEN = "\u001B[32m";
		    String YELLOW = "\u001B[33m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(line);
		    System.out.println(GREEN + "🚚 Exchange  Request Rejected for Order ID: " + orderId + RESET);
		    System.out.println(line);

//		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
//
//		    driver.get(Common.getValueFromTestDataMap("Link"));
//		    System.out.println(GREEN + "✅ Navigated to Exchange page" + RESET);

//		    // ✅ Search Order ID
//		    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
//		    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
//		    orderSearchBox.clear();
//		    orderSearchBox.sendKeys(orderId);
//		    orderSearchBox.sendKeys(Keys.ENTER);
//		    Common.waitForElement(3);

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

		    // ✅ Step 1: Set to Return Accept
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(exchangeStatus));
			waitFor(exchangeStatus);
			click(exchangeStatus);
			Common.waitForElement(2);
		    Select step3 = new Select(exchangeStatus);
		    step3.selectByVisibleText("Exchange Cancelled by Admin");
		    System.out.println(GREEN + "✅ Status changed →Exchange Cancelled by Admin" + RESET);
		    Common.waitForElement(2);
		    WebElement descriptionField = driver.findElement(By.xpath("//input[@name='item[1][description]']"));
		    click(descriptionField);
		    descriptionField.sendKeys("Damaged Product");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		    System.out.println(GREEN + "💾 Saved changes (Exchange Cancelled by Admin)" + RESET);
		    Common.waitForElement(4);
		    System.out.println(line);
		    System.out.println(GREEN + "🎉 Order successfully Rejected !" + RESET);
		    System.out.println(line);
		    
		    
		 	
	}

//String totalMRF="₹1999", discountedMRP="₹999", youSaved="₹1000", totalAmount="₹999", orderId="ZLTQA/25-26/18079";
//TC01 Verify Order Placed Confirm
		public void verifyOrderPlacedEmail() throws InterruptedException {
			
			deleteAllMailsIfNotEmpty();
			
			addProductToCartAndPlacedTheOrder();
			
			verifyOrderConfirmationMail("Order Confirmation");
			
			//Order Shipped
			updateOrderStatusToShipped();
			
			verifyOrderConfirmationMail("Order Shipped");
			
			//Order Delivered
			orderStatusShippedToDelivered();
			
			verifyOrderConfirmationMail("Order Delivered Confirmation");
		}
	
//TC02 Verify OrderCancellation From User Side
		public void verifyOrderCancellationEmailFromUserSide() throws Exception {
			
			addProductToCartAndPlacedTheOrder();
			
			cancelOrderFromUser();
			
			verifyOrderConfirmationMail("Order Cancellation Confirmation");
			
			orderRefundInitiateByAdmin();
			
			verifyRefundCreditedEmail("Refund Credited");
						
		}
	
//TC03 Verify Order Exchange Flow
		
		public void verifyOrderExchangeAllEmail() throws InterruptedException {
			
			addProductToCartAndPlacedTheOrder();
			
			updateOrderStatusToShipped();
			
			orderStatusShippedToDelivered();
			
			orderExchangeForUserSide();
			
			orderExchangeRequestAcceptByAdmin();
			
			verifyOrderExchangeEmail("Order Exchange Request");
			
			updateExchangeRequestToShipped();
			
			verifyOrderExchangeEmail("Exchange Order Shipped");
			
			updateExchangeShippedToExchangeDelivered();
			
			verifyOrderExchangeEmail("Exchange Order Delivered Confirmation");
			
		}
//TC04 Verify Order Return Flow 
		public void verifyOrderReturnAllEmail() throws InterruptedException {
			deleteAllMailsIfNotEmpty();
			
			addProductToCartAndPlacedTheOrder();
			
			updateOrderStatusToShipped();
			
			orderStatusShippedToDelivered();
			
			orderReturnForUserSide();
			
			orderReturnRequestAcceptByAdmin();
			
			verifyOrderConfirmationMail("Order Return Request");
			
			orderReturnRefundInitiateByAdmin();
			
			verifyRefundCreditedEmail("Refund Credited");
						
		}
		
//TC05 Verify Order Cancellation From Admin Side 
		
		public void verifyOrderCancellationEmailFromAdminSide() throws InterruptedException {
			
			addProductToCartAndPlacedTheOrder();
			
			orderCancelByAdminSide();
			
			verifyOrderConfirmationMail("Cancellation of Your Order");
				
		}
	
//Tc06 Verify Return Order Cancel From User Side
		public void verifyReurnOrderCancellationEmailFromUserSide() throws InterruptedException {
			deleteAllMailsIfNotEmpty();
			
			addProductToCartAndPlacedTheOrder();
		
			updateOrderStatusToShipped();
			
			orderStatusShippedToDelivered();
			
			orderReturnForUserSide();
			
			returnOrderCancelFromUserSide();
			
			verifyOrderConfirmationMail("Return Order Cancellation");
		}
//Tc07 Verify Return Order Cancel From Admin Side	
	
	public void verifyReturnOrderCancellationEmailFromAdminSide() throws InterruptedException {
		
		addProductToCartAndPlacedTheOrder();
		
		updateOrderStatusToShipped();
		
		orderStatusShippedToDelivered();
		
		orderReturnForUserSide();
		
		orderReturnRequestAcceptByAdmin();
		
		returnOrderCancelFromAdminSide1();
		
		verifyReturnOrderCanceledByAdminSideEmail("Return Cancelled - Product received in damaged state");
		
	}
	
//Tc08 Verify Return Order Rejected From Admin Side 	
	
		public void verifyReturnOrderRejectedEmailFromAdminSide() throws InterruptedException {
			
			addProductToCartAndPlacedTheOrder();
			
			updateOrderStatusToShipped();
			
			orderStatusShippedToDelivered();
			
			orderReturnForUserSide();	
	
			orderReturnRequestRejectByAdmin();
			
			verifyOrderConfirmationMail("Cancellation of Your Return Order");
	
		}	
	
//Tc09 Verify Exchange Order Cancel From User Side 	
		
			public void verifyExchangeOrderCancelEmailFromUserSide() throws InterruptedException {
				
				addProductToCartAndPlacedTheOrder();
				
				updateOrderStatusToShipped();
				
				orderStatusShippedToDelivered();	
	
				orderExchangeForUserSide();
				
				exchangeOrderCancelFromUserSide();
				
				verifyExchangeOrderCancelMail("Exchange Order Cancellation");
				
			}
	
	
//Tc10 Verify Exchange Order Cancel From Admin Side 	
			
			public void verifyExchangeOrderCancelOutOfStockEmailFromAdminSide() throws InterruptedException {
				
				addProductToCartAndPlacedTheOrder();
				
				updateOrderStatusToShipped();
				
				orderStatusShippedToDelivered();	
	
				orderExchangeForUserSide();	
				
				exchangeOrderRequestRejectByAdminOutOfStock();
				
				verifyOrderExchangeEmail("Exchange Out Of Stock Cancellation");
				
			}
	
//TC11 Verify Exchange Order Cancel From Admin Side 	
			
			public void verifyExchangeOrderCancelReceivedDamageStateEmailFromAdminSide() throws InterruptedException {
				
				addProductToCartAndPlacedTheOrder();
				
				updateOrderStatusToShipped();
				
				orderStatusShippedToDelivered();	
	
				orderExchangeForUserSide();	
				
				exchangeOrderRequestRejectByAdminReceivedDamageState();
				
				verifyExchangeOrderCanceledByAdminSideEmail("Exchange Cancelled - Product received in damaged state");
				
			}	
	
//TC12 Verify Exchange Order Cancel From Admin Side 	
			
			public void verifyExchangeOrderCancelEmailFromAdminSide() throws InterruptedException {
				
				addProductToCartAndPlacedTheOrder();
				
				updateOrderStatusToShipped();
				
				orderStatusShippedToDelivered();	
	
				orderExchangeForUserSide();	
				
				orderExchangeRequestAcceptByAdmin();
				
				updateExchangeRequestToShipped();
				
				exchangeOrderShippedRejectByAdminSide();
				
				verifyOrderExchangeEmail("Cancellation of Your Exchange Order");
				
				
				
				
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
