package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import objectRepo.Admin_OrderObjRepo;
import utils.Common;

public class Admin_Order_Page extends Admin_OrderObjRepo {
	
	public Admin_Order_Page(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}

	
	
	
	public void placeOrderByAdmin(String paymentMode) {

	
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    System.out.println(CYAN + "🚀 Starting Admin Order Placement Flow" + RESET);

	    AdminPanelPage admin = new AdminPanelPage(driver);
	    admin.adminLogin();
	    System.out.println(GREEN + "✅ Admin login successful" + RESET);

	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", searchProductCollectionMenu);
	    System.out.println(GREEN + "✅ Clicked Search Product Collection menu" + RESET);

	    Common.waitForElement(2);
	    type(searchProductCollectionMenu, "Admin Order");
	    System.out.println(GREEN + "⌨️ Typed 'Admin Order' in search box" + RESET);

	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", clickProductCollection);
	    System.out.println(GREEN + "✅ Selected 'Admin Order' collection" + RESET);

	    Common.waitForElement(2);
	    waitFor(createAdminOrder);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", createAdminOrder);
	    System.out.println(GREEN + "📝 Clicked Create Admin Order" + RESET);

	    Common.waitForElement(2);
	    waitFor(selectCustomer);
	    selectCustomer.click();
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].click();", selectCustomer);
	    System.out.println(GREEN + "👤 Clicked Select Customer" + RESET);

	    Common.waitForElement(2);
	    waitFor(customerBox);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", customerBox);

	    type(customerBox, "Saroj test");
	    customerBox.sendKeys(Keys.ENTER);
	    System.out.println(CYAN + "🔍 Searched customer: Saroj test" + RESET);
	    
	    Common.waitForElement(2);
	    waitFor(selectAddress);
	    selectAddress.click();
//	    ((JavascriptExecutor) driver)
//	            .executeScript("arguments[0].click();", selectAddress);
	    System.out.println(GREEN + "👤 Clicked Select Address" + RESET);
	    
	    By address = By.xpath("(//div[contains(@class,'address-item')])[1]");
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    try {
	        WebElement element = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(address)
	        );
	        element.click();
	        System.out.println(GREEN + "🏠 Address selected successfully" + RESET);
	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Address not displayed" + RESET);
	        Assert.fail("Address is NOT displayed on the page");
	    }

	    Common.waitForElement(2);
	    waitFor(confirmBtn);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", confirmBtn);
	    System.out.println(GREEN + "✅ Address confirmed" + RESET);
	    
	    try {
	        System.out.println(CYAN + "💳 Selecting payment mode: " + paymentMode + RESET);

	        waitFor(selectPaymentType);
	        Common.waitForElement(2);
	        ((JavascriptExecutor) driver)
	                .executeScript("arguments[0].scrollIntoView({block:'center'});", selectPaymentType);

	        selectPaymentType.click();
	        System.out.println(GREEN + "✅ Clicked Payment Type dropdown" + RESET);
	        Common.waitForElement(2);
	        Select select = new Select(selectPaymentType);
	        select.selectByVisibleText(paymentMode);
	        Thread.sleep(2000);

	        System.out.println(GREEN + "✅ Payment mode selected successfully: " + paymentMode + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Failed to select payment mode: " + paymentMode + RESET);
	        Assert.fail("Payment mode selection failed: " + e.getMessage());
	    }
	    Common.waitForElement(2);
	    waitFor(createOrderBtn);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", createOrderBtn);
	    
	    switchToWindow(1);
	    
//	    String parentWindow = driver.getWindowHandle();
//
//	    wait.until(driver -> driver.getWindowHandles().size() > 1);
//
//	    for (String window : driver.getWindowHandles()) {
//	        if (!window.equals(parentWindow)) {
//	            driver.switchTo().window(window);
//	            break;
//	        }
//	    }

	    System.out.println(GREEN + "🪟 Switched to User Application window" + RESET);
	    
	    System.out.println(CYAN + "🎉 Admin Order flow completed successfully" + RESET);
	}
	
	
	public void switchToWindow(int index) {
	    List<String> windows = new ArrayList<>(driver.getWindowHandles());

	    if (windows.size() <= index) {
	        Assert.fail("❌ Window index " + index + " not available. Total windows: " + windows.size());
	    }

	    driver.switchTo().window(windows.get(index));
	}
	
	String totalMRF, discountedMRP, totalAmount;
	public void placeOrderForAdmin() throws InterruptedException {
		String CYAN = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "──────────────────────────────────────────────────────────────";
	    HomePage home=new HomePage(driver);
	    home.handleAccessCodeIfPresentFast();
	    home.popup();
	    home.closeDebugBarIfPresent();
	    
		Calculation_MyOrder_Page order=new Calculation_MyOrder_Page(driver);
		order.deleteAllProductsFromCart();
		
		order.takeRandomProductFromAll();
		
		 Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(bagIcon));
		    click(bagIcon);
		    System.out.println(GREEN + "✅ Opened cart" + RESET);
		    Thread.sleep(2000);
		    // ✅ Capture pricing details
		    WebElement totalMRFElement = driver.findElement(By.xpath("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_mrp')]"));
		    totalMRF = totalMRFElement.getText().trim();

		    WebElement discountedMRPElement = driver.findElement(By.xpath("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_discounted_mrp')]"));
		    discountedMRP = discountedMRPElement.getText().trim();
		    
		    WebElement totalAmountElement = driver.findElement(By.xpath("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_amount')]"));
		    totalAmount = totalAmountElement.getText().trim();
		    Thread.sleep(2000);
		    System.out.println(CYAN + line + RESET);
		    System.out.println(GREEN + "💰 Price Summary:" + RESET);
		    System.out.println(YELLOW + "🆔 Total MRP: " + totalMRF + RESET);
		    System.out.println(YELLOW + "💸 Discounted MRP: " + discountedMRP + RESET);
		    System.out.println(YELLOW + "🪙 Total Amount: " + totalAmount + RESET);
		    System.out.println(CYAN + line + RESET);

		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
		    click(placeOrderBtn);
		    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);
		    
		
		
	}
	
	String orderId;
	public void validatePrepaidOrder() {

	    // ===== COLORS =====
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    System.out.println(CYAN + "🔍 Starting Prepaid Order Validation" + RESET);

	    try {
	            WebElement orderIdElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//tr[contains(@class,'odd')]//td[1]/span[@title])[1]")
	                )
	        );

	         orderId = orderIdElement.getText().trim();
	        System.out.println(GREEN + "📄 Order ID captured: " + orderId + RESET);

	        // ================================
	        // 2️⃣ Validate Order Status (Draft → FAIL)
	        // ================================
	        WebElement statusBadge = driver.findElement(
	                By.xpath("(//tr[contains(@class,'odd')]//span[contains(@class,'badge')])[1]")
	        );

	        String orderStatus = statusBadge.getText().trim();
	        System.out.println(YELLOW + "📌 Order Status: " + orderStatus + RESET);
	        
	     // ✅ PASS if Draft, ❌ FAIL otherwise
	        Assert.assertEquals(
	                "❌ Order status is NOT Draft. Test case FAILED.",
	                "Draft",
	                orderStatus
	        );
	        
//
//	        if (orderStatus.equalsIgnoreCase("Draft")) {
//	            Assert.fail("❌ Order status is Draft. Test case FAILED.");
//	        }

	        // ================================
	        // 3️⃣ Validate Payment Link Status = Not Created
	        // ================================
	        WebElement paymentLinkStatus = driver.findElement(
	                By.xpath("(//tr[contains(@class,'odd')]//td/span[@title='Not Created'])[1]")
	        );

	        
	        if (!paymentLinkStatus.isDisplayed()) {
	            Assert.fail("❌ Payment Link Status 'Not Created' is not displayed");
	        }

	        System.out.println(GREEN + "✅ Payment Link Status: Not Created" + RESET);

	        // ================================
	        // 4️⃣ Validate Send Payment Link button
	        // ================================
	        WebElement sendPaymentLinkBtn = driver.findElement(
	                By.xpath("(//a[contains(@onclick,'confirmSendPaymentLink')])[1]")
	        );

	        if (!sendPaymentLinkBtn.isDisplayed()) {
	            Assert.fail("❌ Send Payment Link button not displayed");
	        }

	        System.out.println(GREEN + "🔗 Send Payment Link button is visible" + RESET);

	        // ================================
	        // 5️⃣ Click Send Payment Link
	        // ================================
			 Common.waitForElement(3);

	        sendPaymentLinkBtn.click();
	        System.out.println(CYAN + "📤 Clicked Send Payment Link" + RESET);

	        // ================================
	        // 6️⃣ Confirm SweetAlert
	        // ================================
	        WebElement confirmBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//button[contains(text(),'Yes, send it')]")
	                )
	        );
	        confirmBtn.click();
	        Thread.sleep(5000);
	        WebElement okBtn = wait.until(
	                ExpectedConditions.elementToBeClickable(
	                        By.xpath("//button[contains(text(),'OK')]")
	                )
	        );
	        okBtn.click();
	      
	        System.out.println(GREEN + "✅ Confirmed Send Payment Link popup" + RESET);

	        // ================================
	        // 7️⃣ Validate Payment Link Status = Created
	        // ================================
	        driver.navigate().refresh();
	        Thread.sleep(5000);
	        WebElement createdStatus = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//tr[contains(@class,'odd')]//span[contains(text(),'created')])[1]")
	                )
	        );

	        if (!createdStatus.isDisplayed()) {
	            Assert.fail("❌ Payment Link Status 'Created' not displayed");
	        }
	        
	        System.out.println(GREEN + "✅ Payment Link Status updated to Created" + RESET);

	        // ================================
	        // 8️⃣ Validate Expiry Date Displayed
	        // ================================
	        WebElement expiryDate = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//tr[contains(@class,'odd')]//td[contains(@class,'expiry')] | //tr[contains(@class,'odd')]//td/span[contains(text(),'202')])[2]")
	                )
	        );

	        if (!expiryDate.isDisplayed()) {
	            Assert.fail("❌ Expiry Date not displayed");
	        }
	
	        System.out.println(GREEN + "⏰ Payment Link Expiry Date is displayed" + RESET);

	        System.out.println(CYAN + "🎉 Prepaid Order Validation Completed Successfully" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Validation failed: " + e.getMessage() + RESET);
	        Assert.fail("Prepaid order validation failed");
	    }
	}
	
	private String normalizePrice(String price) {
	    return price.replaceAll("[^0-9]", ""); // Keep only digits
	}
	String gmailId="zlaata.qa.test@gmail.com";
	String gmailPassword="user@123";
	public void verifyMail_PaymentLink_Prepaid(String expectedmsg)
	        throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    System.out.println(CYAN + "📧 Verifying Prepaid Order Confirmation Mail" + RESET);

	    // =========================
	    // Open Gmail
	    // =========================
	    driver.get("https://mail.google.com/");
	    
	    System.out.println(CYAN + "🔐 Logging into Gmail..." + RESET);

	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("identifierId"))).sendKeys(gmailId);
	    driver.findElement(By.id("identifierNext")).click();

	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Passwd"))).sendKeys(gmailPassword);
	    driver.findElement(By.id("passwordNext")).click();

	    System.out.println(GREEN + "✅ Logged into Gmail successfully." + RESET);
	    
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
	    System.out.println(GREEN + "✅ Gmail inbox loaded" + RESET);
	   

	    // =========================
	    // Open latest order mail
	    // =========================
	    
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
	 		
	    // =========================
	    // 1️⃣ Validate Payment Link text
	    // =========================
	    By paymentLinkText = By.xpath(
	            "//p[contains(normalize-space(),'Payment link')]"
	    );

	    if (driver.findElements(paymentLinkText).isEmpty()) {
	        Assert.fail("❌ 'Payment link' text is NOT displayed in mail");
	    }
	    System.out.println(GREEN + "✅ Payment link text is displayed" + RESET);

	    // =========================
	    // 2️⃣ Copy & Validate Order ID
	    // =========================
	    System.out.println(GREEN + "🔍 Comparing mail details with order summary..." + RESET);
	    String mailOrderId = driver.findElement(
	            By.xpath("//td[contains(text(),'Order ID')]/following-sibling::td/following-sibling::td")
	    ).getText().trim();
	    
	    
	    Assert.assertTrue("❌ Order ID mismatch! Expected: " + orderId + " | Found: " + mailOrderId,
	            mailOrderId.contains(orderId));
	    System.out.println(GREEN + "✅ Order ID matched: " + mailOrderId + RESET);
	    
	    
	    String mailTotalMRP = driver.findElement(
	            By.xpath("//td[contains(text(),'Total MRP')]/following-sibling::td")
	    ).getText().trim();

	    Assert.assertEquals("❌ Total MRP mismatch!", normalizePrice(totalMRF), normalizePrice(mailTotalMRP));

	    System.out.println(GREEN + "✅ Total MRP verified" + RESET);

	    // =========================
	    // 4️⃣ Validate Discounted MRP
	    // =========================
	    String mailDiscountedMRP = driver.findElement(
	            By.xpath("//td[contains(text(),'Discounted MRP')]/following-sibling::td")
	    ).getText().trim();

	    Assert.assertEquals("❌ Discounted MRP mismatch",
	            normalizePrice(discountedMRP), normalizePrice(mailDiscountedMRP));

	    System.out.println(GREEN + "✅ Discounted MRP verified" + RESET);

	    // =========================
	    // 5️⃣ Validate Shipping is FREE
	    // =========================
	    By shippingFree = By.xpath(
	            "//td[contains(@class,'font_12')]//span[contains(text(),'Free')]"
	    );

	    if (driver.findElements(shippingFree).isEmpty()) {
	        Assert.fail("❌ Shipping is NOT displayed as Free");
	    }

	    System.out.println(GREEN + "✅ Shipping charge displayed as FREE" + RESET);

	    // =========================
	    // 6️⃣ Validate Payment Mode = Prepaid
	    // =========================
	    String mailPaymentMethod = driver.findElement(
	            By.xpath("//td[contains(text(),'Payment Method')]/following-sibling::td/following-sibling::td")
	    ).getText().trim();

	    Assert.assertTrue("❌ Payment method is NOT Prepaid. Found: " + mailPaymentMethod,
	            mailPaymentMethod.equalsIgnoreCase("Prepaid"));

	    System.out.println(GREEN + "✅ Payment method verified as PREPAID" + RESET);

	    System.out.println(CYAN + "🎉 Prepaid Order Mail verification completed successfully" + RESET);
	}
	
	
	public void verifyAndProceedPaymentLinkByAdminOrder() throws InterruptedException {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

	    String GREEN = "\u001B[32m";
	    String RED   = "\u001B[31m";
	    String CYAN  = "\u001B[36m";
	    String RESET = "\u001B[0m";

	    System.out.println(CYAN + "🔗 Verifying Admin Order Payment Link..." + RESET);

	    // ---------- STEP 1: Verify Pay Now button ----------
	    By payNowBtn = By.xpath("//a[normalize-space()='Pay Now']");

	    try {
	        WebElement payNow = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(payNowBtn)
	        );

	        // 🔽 Scroll Pay Now into view
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({block:'center'});", payNow
	        );
	        Common.waitForElement(1);

	        // ✅ Verify displayed
	        Assert.assertTrue(
	                "❌ Pay Now button is NOT displayed",
	                payNow.isDisplayed()
	        );
	        System.out.println(GREEN + "✅ Pay Now button is displayed" + RESET);

	        // 🖱️ Click Pay Now (JS is safest here)
	        ((JavascriptExecutor) driver)
	                .executeScript("arguments[0].click();", payNow);

	        System.out.println(GREEN + "🖱️ Clicked Pay Now button" + RESET);

	    } catch (TimeoutException e) {
	        Assert.fail("❌ Pay Now button not found. Payment link not generated.");
	    }

	    // ---------- STEP 2: Switch to new Razorpay window ----------
	 
//	    String parentWindow = driver.getWindowHandle();
//
//	    wait.until(driver -> driver.getWindowHandles().size() > 1);
//
//	    for (String window : driver.getWindowHandles()) {
//	        if (!window.equals(parentWindow)) {
//	            driver.switchTo().window(window);
//	            break;
//	        }
//	    }
	    switchToWindow(2);
	    System.out.println(GREEN + "🪟 Switched to Razorpay payment window" + RESET);
	    Thread.sleep(3000);
		 // ✅ 1. Switch to Razorpay iframe (you already have this)
	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	            By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
	    ));
	    System.out.println("✅ Switched to Razorpay iframe");
	    // ---------- STEP 3: Verify Contact Details section ----------
	    By contactHeader = By.xpath("//h3[contains(text(),'Contact details')]");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(contactHeader));

	    System.out.println(GREEN + "📱 Contact details section loaded" + RESET);

	    // ---------- STEP 4: Enter Mobile Number ----------
	    By mobileInput = By.xpath("//input[@data-testid='contactNumber']");

	    WebElement mobile = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
	    mobile.clear();
	    mobile.sendKeys("8596047219");

	    System.out.println(GREEN + "📞 Entered mobile number" + RESET);
Thread.sleep(2000);
	    // ---------- STEP 5: Enter Email ----------
	    By emailInput = By.xpath("//input[@data-testid='email']");

	    WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
	    email.clear();
	    email.sendKeys("zlaata.qa.test@gmail.com");

	    System.out.println(GREEN + "📧 Entered email address" + RESET);

	    // ---------- STEP 6: Click Continue ----------
	    By continueBtn = By.xpath("//button[normalize-space()='Continue']");
	    Common.waitForElement(2);
	    WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
	    continueButton.click();
	    System.out.println(GREEN + "➡️ Clicked Continue button" + RESET);
	    Thread.sleep(2000);    
	    
	    // Wait for payment form
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.id("mobile-nav")
	    ));

	    // Click Netbanking
	    By netBankingOption = By.xpath(
	        "//div[@data-value='netbanking' and .//span[@data-testid='Netbanking']]"
	    );
	    Common.waitForElement(1);
	    WebElement netBanking = wait.until(
	            ExpectedConditions.presenceOfElementLocated(netBankingOption)
	    );

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", netBanking);
	    Thread.sleep(1000);

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", netBanking);

	    System.out.println(GREEN + "🏦 Netbanking option clicked" + RESET);

	    // Click HDFC Bank
	    By hdfcBank = By.xpath(
	        "(//div[@role='button' and .//span[contains(text(),'HDFC')]])[1]"
	    );

	    WebElement hdfc = wait.until(
	            ExpectedConditions.presenceOfElementLocated(hdfcBank)
	    );

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].scrollIntoView({block:'center'});", hdfc);
	    Thread.sleep(1000);

	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", hdfc);

	    System.out.println(GREEN + "🏦 HDFC Bank selected" + RESET);
	    
	    
		 // ✅ 1. Switch to Razorpay iframe (you already have this)
//		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
//		            By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
//		    ));
//		    System.out.println("✅ Switched to Razorpay iframe");
		    // ✅ 3. Select Netbanking option
		    Common.waitForElement(4);
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("//span[@data-testid='Netbanking']")
//		    )).click();
//
//		    // ✅ 4. Select HDFC Bank
//		    Common.waitForElement(2);
//		    wait.until(ExpectedConditions.elementToBeClickable(
//		            By.xpath("(//div[@role='button' and .//span[contains(text(),'HDFC')]])[1]")
//		    )).click();

		    // ⬅️ Optional: Switch back to main page after selecting
		    driver.switchTo().defaultContent();
		    
		 // Switch to Razorpay window
		    switchToWindow(3);
//		    String mainWindow = driver.getWindowHandle();
//		    Thread.sleep(3000);
//		    Set<String> allWindows = driver.getWindowHandles();
//		    for (String window : allWindows) {
//		        if (!window.equals(mainWindow)) {
//		            driver.switchTo().window(window);
//		            System.out.println(GREEN + "✅ Switched to Razorpay window" + RESET);
//		            break;
//		        }
//		    }

		    // ✅ Click Success button
		    WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(
		        By.xpath("//button[@data-val='S' and normalize-space(text())='Success']")
		    ));
		    successBtn.click();
		    System.out.println(GREEN + "💳 Payment Success clicked" + RESET);

		    Thread.sleep(8000);
		       

	    System.out.println(GREEN + "✅ Payment link flow initiated successfully" + RESET);
	    switchToWindow(0);
	    System.out.println(GREEN + "🔙 Switched back to main window" + RESET);
	    Thread.sleep(3000);
	}
	
	
	
	public void validatePrepaidOrderStatusInAdminPanel() throws InterruptedException{
		 String GREEN  = "\u001B[32m";
		    String RED    = "\u001B[31m";
		    String CYAN   = "\u001B[36m";
		    String YELLOW = "\u001B[33m";
		    String RESET  = "\u001B[0m";
		    
		 Common.waitForElement(2);
		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", searchProductCollectionMenu);
		    System.out.println(GREEN + "✅ Clicked Search Product Collection menu" + RESET);

		    Common.waitForElement(2);
		    type(searchProductCollectionMenu, "Admin Order");
		    System.out.println(GREEN + "⌨️ Typed 'Admin Order' in search box" + RESET);

		    Common.waitForElement(2);
		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", clickProductCollection);
		    System.out.println(GREEN + "✅ Selected 'Admin Order' collection" + RESET);
		   
			
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
		    Common.waitForElement(3);

		    // ✅ Verify order is displayed
		    try {
		        WebElement orderRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
		        System.out.println(GREEN + "✅ Order found in table!" + RESET);
		    } catch (TimeoutException e) {
		        System.out.println(RED + "❌ Order not found! Stopping execution." + RESET);
		        return;
		    }
		
		    WebElement createdStatus = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//tr[contains(@class,'odd')]//span[contains(text(),'paid')])[1]")
	                )
	        );

	        if (!createdStatus.isDisplayed()) {
	            Assert.fail("❌ Payment Link Status 'Paid' not displayed");
	        }
	        
	        System.out.println(GREEN + "✅ Payment Link Status updated to Paid" + RESET);
	        
	        // ✅ Click Edit button
	        wait.until(ExpectedConditions.elementToBeClickable(editBtn));
	        Common.waitForElement(2);
	    	waitFor(editBtn);
	    	click(editBtn);
	        System.out.println(GREEN + "✅ Clicked Edit" + RESET);

	        // ✅ Shipment Status → Order Accept
	        wait.until(ExpectedConditions.elementToBeClickable(paymentMode));
	        Common.waitForElement(2);
	    	waitFor(paymentMode);
	    	click(paymentMode);
	    	Common.waitForElement(2);
	    	Select select = new Select(paymentMode);
	    	select.selectByVisibleText("Success");
	    	System.out.println(GREEN + "✅ Payment Status set to 'Success '" + RESET);
	    	
	    	// ✅ Save & Back
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	        waitFor(saveButton);
	        click(saveButton);
	        System.out.println("✅ Saved  changes");

	        // ✅ Again click Edit for second update
	        Common.waitForElement(5);
	   
	        WebElement statusBadge = driver.findElement(
	                By.xpath("(//tr[contains(@class,'odd')]//span[contains(@class,'badge')])[1]")
	        );

	        String orderStatus = statusBadge.getText().trim();
	        System.out.println(YELLOW + "📌 Order Status: " + orderStatus + RESET);
	        
	     // ✅ PASS if Paid, ❌ FAIL otherwise
	        Assert.assertEquals(
	                "❌ Order status is NOT Paid. Test case FAILED.",
	                "Paid",
	                orderStatus
	        );
	        
	        Common.waitForElement(2);
		    ((JavascriptExecutor) driver)
		            .executeScript("arguments[0].click();", ordersection);
		    System.out.println(GREEN + "✅ Selected ' Order' collection" + RESET);
		   
			
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
		    Common.waitForElement(3);

		    // ✅ Verify order is displayed
		    try {
		        WebElement orderRow = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//td/span[normalize-space()='" + orderId + "']")
		                )
		        );

		        Assert.assertTrue(
		                "❌ Order ID is NOT displayed in Placed Orders section",
		                orderRow.isDisplayed()
		        );

		        System.out.println(GREEN + "✅ Order ID displayed in Placed Orders section: " + orderId + RESET);

		    } catch (TimeoutException e) {
		        Assert.fail("❌ Order ID '" + orderId + "' is NOT displayed in Placed Orders section");
		    }
	        
	        Thread.sleep(3000);
	        
	}
	
	
	
	String orderIdCOD;
	public void validateCODOrder() {

	    // ===== COLORS =====
	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    System.out.println(CYAN + "🔍 Starting COD Order Validation" + RESET);

	    try {
	            WebElement orderIdElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//tr[contains(@class,'odd')]//td[1]/span[@title])[1]")
	                )
	        );

	            orderIdCOD = orderIdElement.getText().trim();
	        System.out.println(GREEN + "📄 Order ID captured: " + orderIdCOD + RESET);

	        // ================================
	        // 2️⃣ Validate Order Status (Draft → FAIL)
	        // ================================
	        WebElement statusBadge = driver.findElement(
	                By.xpath("(//tr[contains(@class,'odd')]//span[contains(@class,'badge')])[1]")
	        );

	        String orderStatus = statusBadge.getText().trim();
	        System.out.println(YELLOW + "📌 Order Status: " + orderStatus + RESET);
	        
	     // ✅ PASS if Draft, ❌ FAIL otherwise
	        Assert.assertEquals(
	                "❌ Order status is NOT Draft. Test case FAILED.",
	                "Draft",
	                orderStatus
	        );
	        
//
//	        if (orderStatus.equalsIgnoreCase("Draft")) {
//	            Assert.fail("❌ Order status is Draft. Test case FAILED.");
//	        }

	        // ================================
	        // 3️⃣ Validate Payment Link Status = Not Created
	        // ================================
	        WebElement paymentLinkStatus = driver.findElement(
	                By.xpath("(//tr[contains(@class,'odd')]//td/span[@title='Not Created'])[1]")
	        );

	        
	        if (!paymentLinkStatus.isDisplayed()) {
	            Assert.fail("❌ Payment Link Status 'Not Created' is not displayed");
	        }

	        System.out.println(GREEN + "✅ Payment Link Status: Not Created" + RESET);

	       

	       

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Validation failed: " + e.getMessage() + RESET);
	        Assert.fail("Cod order validation failed");
	    }
	}
	
	
	public void validateCODOrders() throws InterruptedException {
		String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";
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
	    orderSearchBox.sendKeys(orderIdCOD);
	    Common.waitForElement(1);
	    orderSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(3);

	    // ✅ Verify order is displayed
	    try {
	        WebElement orderRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//td/span[normalize-space(text())='" + orderIdCOD + "']")));
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


	    // ✅ Save & Back
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(saveButton));
	    waitFor(saveButton);
	    click(saveButton);
	    System.out.println("✅ Saved  changes");
	    
	    Common.waitForElement(4);
	    WebElement statusBadge = driver.findElement(
                By.xpath("(//tr[contains(@class,'odd')]//span[contains(@class,'badge')])[1]")
        );

        String orderStatus = statusBadge.getText().trim();
        System.out.println(YELLOW + "📌 Order Status: " + orderStatus + RESET);
        
     // ✅ PASS if Draft, ❌ FAIL otherwise
        Assert.assertEquals(
                "❌ Order status is NOT Success. Test case FAILED.",
                "Success",
                orderStatus
        );
        
        Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", ordersection);
	    System.out.println(GREEN + "✅ Selected ' Order' collection" + RESET);
	   
		
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
	    orderSearchBox.sendKeys(orderIdCOD);
	    Common.waitForElement(1);
	    orderSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(3);

	    // ✅ Verify order is displayed
	    try {
	        WebElement orderRow = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//td/span[normalize-space()='" + orderIdCOD + "']")
	                )
	        );

	        Assert.assertTrue(
	                "❌ Order ID is NOT displayed in Placed Orders section",
	                orderRow.isDisplayed()
	        );

	        System.out.println(GREEN + "✅ Order ID displayed in Placed Orders section: " + orderIdCOD + RESET);

	    } catch (TimeoutException e) {
	        Assert.fail("❌ Order ID '" + orderIdCOD + "' is NOT displayed in Placed Orders section");
	    }
        
        Thread.sleep(3000);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//TC-01
	public void validateAdminPlacePrepaidOrder() throws InterruptedException {
		
		placeOrderByAdmin("Prepaid");
		
		placeOrderForAdmin();
		
		validatePrepaidOrder();
		
		verifyMail_PaymentLink_Prepaid("Your Payment Link is Ready");
		
		verifyAndProceedPaymentLinkByAdminOrder();
		
		validatePrepaidOrderStatusInAdminPanel();
	}
	
	
	//TC-02
		public void validateAdminPlaceCODOrder() throws InterruptedException {
			
			
			placeOrderByAdmin("COD");
			
			placeOrderForAdmin();
			
			validateCODOrder();
			
			validateCODOrders();
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
