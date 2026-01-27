package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mongodb.assertions.Assertions;

import manager.FileReaderManager;
import objectRepo.AdminPanelCouponObjRepo;
import utils.Common;

public class AdminPanelCouponPage extends AdminPanelCouponObjRepo{
	
	public AdminPanelCouponPage(WebDriver driver) 
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
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	

	String couponName;
	    public void createCouponInAdminPanel() throws InterruptedException {

	    	Common.waitForElement(4);
		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
		    System.out.println("✅ Successfull redirect to Adimn Coupon page");
		    Common.waitForElement(2);
		    //  Click on "Add Coupon" button
		    wait.until(ExpectedConditions.elementToBeClickable(addCouponButton));
		    click(addCouponButton);
	        // ✅  Generate a unique random coupon name
	         int randomNum = (int) (Math.random() * 10000); // creates 0–9999
	        couponName = "Automation100Flat" + randomNum;

	        // ✅  Enter Title
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(titleBox));
	        titleBox.clear();
	        titleBox.sendKeys(couponName);
	        System.out.println("Title: " + couponName);
	        // ✅Enter Coupon Code
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(codeBox));
	        codeBox.clear();
	        codeBox.sendKeys(couponName);
	        System.out.println("Coupon Code: " + couponName);
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(shortDesriptionBox));
	        shortDesriptionBox.clear();
	        shortDesriptionBox.sendKeys("Applicable over the purchase of ₹499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxDiscountBox));
	        maxDiscountBox.clear();
	        maxDiscountBox.sendKeys("100");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxUsageLimitBox));
	        maxUsageLimitBox.clear();
	        maxUsageLimitBox.sendKeys("1");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(percentageBox));
	        percentageBox.clear();
	        percentageBox.sendKeys("10");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(minimumPurchaseBox));
	        minimumPurchaseBox.clear();
	        minimumPurchaseBox.sendKeys("499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(isAlwaysBtn));
	        isAlwaysBtn.click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(statusBtn));
	        statusBtn.click();
	        // Select  Normal  Coupon
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBtn));
		    click(couponTypeBtn); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBox));
	        couponTypeBox.clear();
	        type(couponTypeBox, "Normal" + Keys.ENTER);
		    System.out.println("✅ Successfull Typed Normal Coupon");
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(saveAndBackButton));
		    click(saveAndBackButton); 
	        System.out.println("Created coupon: " + couponName);
	      
	    
	}
	
	    public void verifyCouponInAdmin() { 
	    Common.waitForElement(4);
	    driver.navigate().refresh();
        // Type Coupon
	    Common.waitForElement(2);
        wait.until(ExpectedConditions.elementToBeClickable(clickTitle));
        click(clickTitle); 
        wait.until(ExpectedConditions.elementToBeClickable(adminTitleBox));
        adminTitleBox.clear();
        adminTitleBox.sendKeys(couponName);
        adminTitleBox.sendKeys(Keys.ENTER);
        System.out.println("✅ Searched for Coupon: " + couponName);

        // Wait for Coupon to appear in table
        Common.waitForElement(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='" + couponName + "']")));
        System.out.println("✅ Coupon is visible in Admin panel: " + couponName);
        Common.waitForElement(2);
 		    waitFor(clearCatchButton);
 		    click(clearCatchButton);
 		    System.out.println("✅ Successfull click Clear Catch Button");
 		    Common.waitForElement(2);
	
	    }
	    public void verifyProductsInUserAppMyCouponSection() {
	    	HomePage home = new HomePage(driver);
			home.homeLaunch();
	    	LoginPage login = new LoginPage(driver);
			login.userLogin();
		   
			
			Common.waitForElement(3);
	        wait.until(ExpectedConditions.elementToBeClickable(clickProfile));
		    click(clickProfile);
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(myCoupon));
		    click(myCoupon);
		    Common.waitForElement(3);
		 // Convert to uppercase to match the app display
		    couponName = couponName.toUpperCase();
		    
		    // Locate all coupon elements in the list
		    List<WebElement> coupons = driver.findElements(By.xpath("//div[@class='coupon__code__title']/span"));
		    
		    boolean couponFound = false;
		    
		    // Check if the coupon is present
		    for (WebElement coupon : coupons) {
		        String expectedCoupon = coupon.getText().trim().toUpperCase();
		        if (expectedCoupon.equalsIgnoreCase(couponName)) {
		            System.out.println("✅ Coupon found successfully: " + expectedCoupon);
		            couponFound = true;
		            break;
		        }
		    }
		    
		    // If not found, refresh and check again
		    if (!couponFound) {
		        System.out.println("⚠️ Coupon not found, refreshing app...");
		        driver.navigate().refresh();
		        
		        Common.waitForElement(4);
		        // Check again after refresh
		        List<WebElement> refreshedCoupons = driver.findElements(By.xpath("//div[@class='coupon__code__title']/span"));
		        for (WebElement coupon : refreshedCoupons) {
		            String expectedCoupon = coupon.getText().trim().toUpperCase();
		            if (expectedCoupon.equalsIgnoreCase(couponName)) {
		                System.out.println("✅ Coupon found after refresh: " + expectedCoupon);
		                couponFound = true;
		                break;
		            }
		        }
		    }
		    
		    // Final validation
		    if (!couponFound) {
		        System.out.println("❌ Coupon not found even after refresh.");
		        Assert.fail("Coupon not available in the app.");
		    }
		  
	    }
		    
	    
	public void verifyIncheckoutCouponPage() {
		
		
		switchToWindow(1);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);
		
		Actions actions = new Actions(driver);
		// ✅ Hover on "Shop" menu
	    WebElement shopMenu = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
	    actions.moveToElement(shopMenu).perform();

	    // ✅ Click "All" from dropdown
	    WebElement allButton = wait.until(ExpectedConditions
	            .elementToBeClickable(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'DRESSES']")));
	    allButton.click();

	    System.out.println("✅ Clicked on 'All' under Shop menu");
	    Common.waitForElement(3);

	    // --------------------------
	    // Step 1: Get all products > 499
	    // --------------------------
	    List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
	    List<WebElement> validProducts = new ArrayList<>();

	    for (WebElement product : allProducts) {
	        String priceText = product.findElement(By.xpath(".//span[@class='prod_current_price']")).getText().replaceAll("[^0-9]", "");
	        int price = Integer.parseInt(priceText);
	        if (price > 499) {
	            validProducts.add(product);
	        }
	    }

	    if (validProducts.isEmpty()) {
	        Assert.fail("❌ No product found with price above 500!");
	        return;
	    }

	    // --------------------------
	    // Step 2: Select a random product
	    // --------------------------
	    Random rand = new Random();
	    WebElement selectedProduct = validProducts.get(rand.nextInt(validProducts.size()));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
	    Common.waitForElement(2);
	    selectedProduct.click();
	    System.out.println("🛒 Selected random product lessthan 999.");

	    // --------------------------
	    // Step 3: Add to Cart
	    // --------------------------
//	    Common.waitForElement(2);
//	    js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
//	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product_list_cards_btn_group product_list_add_to_cart Cls_CartListes ClsSingleCart']"))).click();
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Buy')]"))).click();
	    System.out.println("✅ Product added to cart successfully.");

	    // --------------------------
	    // Step 4: Go to Coupon section
	    // --------------------------
	    Common.waitForElement(3);
        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);
//	    Common.waitForElement(3);
//        wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
//	    click(viewCoupon);
	 // Convert to uppercase to match the app display
	    couponName = couponName.toUpperCase();
	    
	 // Enter coupon
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys(couponName);
	    System.out.println( "✍️ Entered coupon code:"+ couponName);

	    // Click Apply
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
	    click(applyBtn);
	    System.out.println("🔄 Applying coupon...");

	  
	    System.out.println( "🔍 Checking Coupon Status..." );

	    Common.waitForElement(2);
	    // CHECK 1: Coupon Applied
	    try {
	        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_status']")));

	        System.out.println( "✅ Coupon applied successfully!" );

	    } catch (TimeoutException e) {
	        System.out.println( "❌ Coupon NOT applied!" );
	        Assert.fail("Coupon was not applied!");
	    }

	    // CHECK 2: Discount Amount
	    try {
	        WebElement discountMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_details_status']")));

	        String discountText = discountMsg.getText(); 
	        String discountValue = discountText.replaceAll("[^0-9]", "");

	        System.out.println( "💰 Discount Applied: ₹" + discountValue );

	    } catch (TimeoutException e) {
	        System.out.println( "❌ Discount amount not found!" );
	        Assert.fail("Discount amount not detected!");
	    }
	    
	    
	    
//	 // Check for coupon heading text anywhere in coupon list
//	    List<WebElement> couponElements = driver.findElements(
//	        By.xpath("//div[@class='coupon_list_wrap eligible_coupons']//div[contains(@class,'coupon_heading') and normalize-space(text())='" + couponName + "']")
//	    );
//
//	    if (!couponElements.isEmpty()) {
//	        System.out.println("✅ Coupon '" + couponName + "' is visible on available for you section.");
//	    } else {
//	        System.out.println("❌ Coupon '" + couponName + "' is NOT visible on the page.");
//	        Assert.fail("Coupon not available in the app.");
//	    }
	    
	
	 }
	
	    
//Special Coupon
	    
	    
	    String specialCouponName;
	    public void createSpecialCouponInAdminPanel() throws InterruptedException {

	    	Common.waitForElement(4);
		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
		    System.out.println("✅ Successfull redirect to Adimn Coupon page");
		    Common.waitForElement(2);
		  // ✅ Step 1: Click on "Add Coupon" button
		    wait.until(ExpectedConditions.elementToBeClickable(addCouponButton));
		    click(addCouponButton);
	        // ✅ Step 2: Generate a unique random coupon name
	         int randomNum = (int) (Math.random() * 10000); // creates 0–9999
	         specialCouponName = "Special100Flat" + randomNum;

	        // ✅ Step 3: Enter Title
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(titleBox));
	        titleBox.clear();
	        titleBox.sendKeys(specialCouponName);
	        System.out.println("Title: " + specialCouponName);
	        // ✅ Step 4: Enter Coupon Code
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(codeBox));
	        codeBox.clear();
	        codeBox.sendKeys(specialCouponName);
	        System.out.println("Coupon Code: " + specialCouponName);
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(shortDesriptionBox));
	        shortDesriptionBox.clear();
	        shortDesriptionBox.sendKeys("Applicable over the purchase of ₹499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxDiscountBox));
	        maxDiscountBox.clear();
	        maxDiscountBox.sendKeys("100");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxUsageLimitBox));
	        maxUsageLimitBox.clear();
	        maxUsageLimitBox.sendKeys("1");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(percentageBox));
	        percentageBox.clear();
	        percentageBox.sendKeys("10");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(minimumPurchaseBox));
	        minimumPurchaseBox.clear();
	        minimumPurchaseBox.sendKeys("499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(isAlwaysBtn));
	        isAlwaysBtn.click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(statusBtn));
	        statusBtn.click();
	        // Select  Normal  Coupon
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBtn));
		    click(couponTypeBtn); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBox));
	        couponTypeBox.clear();
	        type(couponTypeBox, "Special" + Keys.ENTER);
		    System.out.println("✅ Successfull Typed Special Coupon");
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(saveAndBackButton));
		    click(saveAndBackButton); 
	        System.out.println("Created coupon: " + specialCouponName);
	      
	    
	}
	
	    public void verifySpecialCouponInAdmin() { 
	    Common.waitForElement(4);
	    driver.navigate().refresh();
        // Type Coupon
	    Common.waitForElement(2);
        wait.until(ExpectedConditions.elementToBeClickable(clickTitle));
        click(clickTitle); 
        wait.until(ExpectedConditions.elementToBeClickable(adminTitleBox));
        adminTitleBox.clear();
        adminTitleBox.sendKeys(specialCouponName);
        adminTitleBox.sendKeys(Keys.ENTER);
        System.out.println("✅ Searched for Coupon: " + specialCouponName);

        // Wait for Coupon to appear in table
        Common.waitForElement(3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[normalize-space()='" + specialCouponName + "']")));
        System.out.println("✅ Coupon is visible in Admin panel: " + specialCouponName);
        Common.waitForElement(2);
 		    waitFor(clearCatchButton);
 		    click(clearCatchButton);
 		    System.out.println("✅ Successfull click Clear Catch Button");
 		    Common.waitForElement(2);
	
	 }
	    
	    public void verifySpecialCouponInUserApp() throws InterruptedException {
 	
	    	HomePage home = new HomePage(driver);
			home.homeLaunch();
			LoginPage login = new LoginPage(driver);
			login.userLogin();
			
			Actions actions = new Actions(driver);
			// ✅ Hover on "Shop" menu
		    WebElement shopMenu = wait.until(ExpectedConditions
		            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
		    actions.moveToElement(shopMenu).perform();

		    // ✅ Click "All" from dropdown
		    WebElement allButton = wait.until(ExpectedConditions
		            .elementToBeClickable(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'DRESSES']")));
		    allButton.click();

		    System.out.println("✅ Clicked on 'All' under Shop menu");
		    Common.waitForElement(3);

		    // --------------------------
		    // Step 1: Get all products > 500
		    // --------------------------
		    List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		    List<WebElement> validProducts = new ArrayList<>();

		    for (WebElement product : allProducts) {
		        String priceText = product.findElement(By.xpath(".//span[@class='prod_current_price']")).getText().replaceAll("[^0-9]", "");
		        int price = Integer.parseInt(priceText);
		        if (price > 499) {
		            validProducts.add(product);
		        }
		    }

		    if (validProducts.isEmpty()) {
		        Assert.fail("❌ No product found with price above 500!");
		        return;
		    }

		    // --------------------------
		    // Step 2: Select a random product
		    // --------------------------
		    Random rand = new Random();
		    WebElement selectedProduct = validProducts.get(rand.nextInt(validProducts.size()));
		    JavascriptExecutor js = (JavascriptExecutor) driver;
		    js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
		    Common.waitForElement(2);
		    selectedProduct.click();
		    System.out.println("🛒 Selected random product above 500.");

		    // --------------------------
		    // Step 3: Add to Cart
		    // --------------------------
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Buy')]"))).click();
		    System.out.println("✅ Product added to cart successfully.");

		    // --------------------------
		    // Step 4: Go to Coupon section
		    // --------------------------
		    Common.waitForElement(3);
	        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
		    click(clickCartBtn);
		 // Convert to uppercase to match the app display
		    specialCouponName = specialCouponName.toUpperCase();
		    
		
		    
			 // Enter coupon
			    Common.waitForElement(2);
			    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
			    click(searchBox);
			    searchBox.sendKeys(specialCouponName);
			    System.out.println( "✍️ Entered coupon code:"+ specialCouponName);

			    // Click Apply
			    Common.waitForElement(2);
			    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
			    click(applyBtn);
			    System.out.println("🔄 Applying coupon...");
		    Thread.sleep(3000);

		 // Apply coupon
		    boolean applied = false;

		    try {
		        // Wait for "Applied" message on main screen
		        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//p[@class='acc_status']")));
		        System.out.println("✅ Coupon applied successfully and popup closed.");
		        applied = true;

		        // --- Verify Discount Limit (₹100 max) ---
		        WebElement discountMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		                By.xpath("//p[@class='acc_details_status']")));
		        String discountText = discountMsg.getText(); // e.g., "You saved ₹100 extra"

		        // Extract numeric value from message
		        String discountValueStr = discountText.replaceAll("[^0-9.]", "");
		        double discountValue = Double.parseDouble(discountValueStr);

		        if (discountValue <= 100) {
		            System.out.println("✅ Discount applied correctly: ₹" + discountValue);
		        } else {
		            System.out.println("❌ Discount exceeded maximum limit! Applied: ₹" + discountValue);
		            Assert.fail("Discount exceeded maximum limit of ₹100!");
		        }

		    } catch (TimeoutException e) {
		        System.out.println("❌ Popup not closed, coupon not applied!");
		        applied = false;
		    }
		}
	
	
//TC-03
//Specific Product Item
	    public void verifySpecificProductItemCoupon() throws InterruptedException {

	    	Common.waitForElement(4);
		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
		    System.out.println("✅ Successfull redirect to Adimn Coupon page");
		    Common.waitForElement(2);
		  // ✅ Step 1: Click on "Add Coupon" button
		    wait.until(ExpectedConditions.elementToBeClickable(addCouponButton));
		    click(addCouponButton);
	        // ✅ Step 2: Generate a unique random coupon name
	         int randomNum = (int) (Math.random() * 10000); // creates 0–9999
	        couponName = "Specific100Flat" + randomNum;

	        // ✅ Step 3: Enter Title
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(titleBox));
	        titleBox.clear();
	        titleBox.sendKeys(couponName);
	        System.out.println("Title: " + couponName);
	        // ✅ Step 4: Enter Coupon Code
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(codeBox));
	        codeBox.clear();
	        codeBox.sendKeys(couponName);
	        System.out.println("Coupon Code: " + couponName);
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(shortDesriptionBox));
	        shortDesriptionBox.clear();
	        shortDesriptionBox.sendKeys("Applicable only Specific Product above ₹499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxDiscountBox));
	        maxDiscountBox.clear();
	        maxDiscountBox.sendKeys("100");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(maxUsageLimitBox));
	        maxUsageLimitBox.clear();
	        maxUsageLimitBox.sendKeys("1");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(percentageBox));
	        percentageBox.clear();
	        percentageBox.sendKeys("10");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(minimumPurchaseBox));
	        minimumPurchaseBox.clear();
	        minimumPurchaseBox.sendKeys("499");
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(isAlwaysBtn));
	        isAlwaysBtn.click();
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(statusBtn));
	        statusBtn.click();
	        // Select  Specific Product Item  Coupon
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBtn));
		    click(couponTypeBtn); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBox));
	        couponTypeBox.clear();
	        type(couponTypeBox, "Special Specific" + Keys.ENTER);
		    System.out.println("✅ Successfull Typed Specific Product Item Coupon");
		 // Select  Specific Product Item  Coupon
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(menuBtn));
		    click(menuBtn); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(menuBtn));
	        menuBtn.clear();
	        type(menuBtn, "Product Item" + Keys.ENTER);
		    System.out.println("✅ Successfull Typed Specific Product Item Coupon");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(productBox));
		    click(productBox); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(productBox));
	        waitFor(productBox);
		    type(productBox, "Test by Auto1");
		    Common.waitForElement(2);
		    productBox.sendKeys(Keys.ENTER);
		    wait.until(ExpectedConditions.elementToBeClickable(productBox));
		    click(productBox); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(productBox));
	        waitFor(productBox);
		    type(productBox, "Test by Auto2");
		    Common.waitForElement(2);
		    productBox.sendKeys(Keys.ENTER);
		    System.out.println("✅ Successfull Typed Specific Product Item Coupon");
		    Common.waitForElement(3);
		     wait.until(ExpectedConditions.elementToBeClickable(saveAndBackButton));
		    click(saveAndBackButton); 
	        System.out.println("Created coupon: " + couponName);
	        Common.waitForElement(2);
		    waitFor(clearCatchButton);
		    click(clearCatchButton);
		    System.out.println("✅ Successfull click Clear Catch Button");
		    Common.waitForElement(2);
	      
	    
	}
	
	    public void searchAndAddThatProductsToCart(String firstProduct, String secondProduct) throws InterruptedException {
	    	HomePage home = new HomePage(driver);
			home.homeLaunch();
	    	LoginPage login = new LoginPage(driver);
			login.userLogin();
	        Common.waitForElement(3);

	        // ✅ Product list to search and add
	        String[] products = { firstProduct, secondProduct };

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	        for (String productName : products) {
	            System.out.println("\n🔍 Searching for product: " + productName);

	            // ✅ Click on search icon / box
	            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//input[contains(@class, 'navigation_search_input_field') and @type='text']")
	            ));
	            
	         // Search in user app
	            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	            searchBox.click();
	            searchBox.clear();
	            searchBox.sendKeys(productName);
	            System.out.println("✅ Searched for listing: " + productName);

	            // Wait for product to appear
	            By productLocator = By.xpath("//h6[normalize-space()='" + productName + "']");
	            wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));

	            WebElement productElement = driver.findElement(productLocator);
	            Assert.assertTrue("❌ Listing name not found in User App: " + productName, productElement.isDisplayed());

	            // Click product
	            productElement.click();
	            System.out.println("✅ Product opened in User App: "+ productName);
	            
//	         // ✅ Wait for & scroll into the Top Selling section
//	            WebElement targetProduct = wait.until(
//	                    ExpectedConditions.presenceOfElementLocated(
//	                            By.cssSelector(".prod_add_cart_btn"))
//	            );
//	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetProduct);
//	            Common.waitForElement(3);
	            // ✅ Wait for Add to Cart button
	            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[contains(text(),'Add to')]")
	            ));
	            addToCartBtn.click();
	            System.out.println("🛒 Added product to cart: " + productName);
	        }

	        System.out.println("\n🎉 Both products searched and added to cart successfully!");
	        
	        checkCouponInCheckOutPage();
	    }
	    
	public void checkCouponInCheckOutPage() {
		
	 // Go to Coupon section
	    // --------------------------
	    Common.waitForElement(3);
        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);
	    Common.waitForElement(3);
	    couponName = couponName.toUpperCase();
	    
		 // Enter coupon
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		    click(searchBox);
		    searchBox.sendKeys(couponName);
		    System.out.println( "✍️ Entered coupon code:"+ couponName);

		    // Click Apply
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
		    click(applyBtn);
		    System.out.println("🔄 Applying coupon...");

		  
		    System.out.println( "🔍 Checking Coupon Status..." );
	    Common.waitForElement(3);
	 // Apply coupon
	    boolean applied = false;

	    try {
	        // Wait for "Applied" message on main screen
	        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_status']")));
	        System.out.println("✅ Coupon applied successfully and popup closed.");
	        applied = true;

	        // --- Verify Discount Limit (₹100 max) ---
	        WebElement discountMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_details_status']")));
	        String discountText = discountMsg.getText(); // e.g., "You saved ₹100 extra"

	        // Extract numeric value from message
	        String discountValueStr = discountText.replaceAll("[^0-9.]", "");
	        double discountValue = Double.parseDouble(discountValueStr);

	        if (discountValue <= 100) {
	            System.out.println("✅ Discount applied correctly: ₹" + discountValue);
	        } else {
	            System.out.println("❌ Discount exceeded maximum limit! Applied: ₹" + discountValue);
	            Assert.fail("Discount exceeded maximum limit of ₹100!");
	        }

	    } catch (TimeoutException e) {
	        System.out.println("❌ Popup not closed, coupon not applied!");
	        applied = false;
	    }
	
	
	}
	    
	    
	
	//TC-04
	//Specific  Item
		    public void createSpecificProductItemCoupon1() throws InterruptedException {

		    	Common.waitForElement(4);
			    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
			    System.out.println("✅ Successfull redirect to Adimn Coupon page");
			    Common.waitForElement(2);
			  // ✅ Step 1: Click on "Add Coupon" button
			    wait.until(ExpectedConditions.elementToBeClickable(addCouponButton));
			    click(addCouponButton);
		        // ✅ Step 2: Generate a unique random coupon name
		         int randomNum = (int) (Math.random() * 10000); // creates 0–9999
		        couponName = "SPECIFIC100FLAT" + randomNum;

		        // ✅ Step 3: Enter Title
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(titleBox));
		        titleBox.clear();
		        titleBox.sendKeys(couponName);
		        System.out.println("Title: " + couponName);
		        // ✅ Step 4: Enter Coupon Code
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(codeBox));
		        codeBox.clear();
		        codeBox.sendKeys(couponName);
		        System.out.println("Coupon Code: " + couponName);
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(shortDesriptionBox));
		        shortDesriptionBox.clear();
		        shortDesriptionBox.sendKeys("Applicable only Specific Product above ₹499");
		        Common.waitForElement(2);
		        driver.findElement(By.xpath("//label[normalize-space()='Fixed']/preceding-sibling::input")).click();
		        Common.waitForElement(1);
		        wait.until(ExpectedConditions.elementToBeClickable(maxDiscountBox));
		        maxDiscountBox.clear();
		        maxDiscountBox.sendKeys("100");
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(maxUsageLimitBox));
		        maxUsageLimitBox.clear();
		        maxUsageLimitBox.sendKeys("10");
		        Common.waitForElement(2);
//		        wait.until(ExpectedConditions.elementToBeClickable(percentageBox));
//		        percentageBox.clear();
//		        percentageBox.sendKeys("10");
//		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(minimumPurchaseBox));
		        minimumPurchaseBox.clear();
		        minimumPurchaseBox.sendKeys("399");
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(isAlwaysBtn));
		        isAlwaysBtn.click();
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(statusBtn));
		        statusBtn.click();
		        // Select  Specific Product Item  Coupon
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBtn));
			    click(couponTypeBtn); 
			    Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(couponTypeBox));
		        couponTypeBox.clear();
		        type(couponTypeBox, "Specific" + Keys.ENTER);
			    System.out.println("✅ Successfull Typed Specific Product Item Coupon");
			 // Select  Specific Product Item  Coupon
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(menuBtn));
			    click(menuBtn); 
			    Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(menuBtn));
		        menuBtn.clear();
		        type(menuBtn, "Product Item" + Keys.ENTER);
			    System.out.println("✅ Successfull added one Specific Product Item ");
			    Common.waitForElement(3);
			    wait.until(ExpectedConditions.elementToBeClickable(productBox));
			    click(productBox); 
			    Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(productBox));
		        waitFor(productBox);
			    type(productBox, "Test by Auto1");
			    Common.waitForElement(2);
			    productBox.sendKeys(Keys.ENTER);
			    wait.until(ExpectedConditions.elementToBeClickable(productBox));
			    click(productBox); 
			    Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(productBox));
		        waitFor(productBox);
			    type(productBox, "Test by Auto2");
			    Common.waitForElement(2);
			    productBox.sendKeys(Keys.ENTER);
			    System.out.println("✅ Successfull added two Specific Product Item ");
			    Common.waitForElement(3);
			     wait.until(ExpectedConditions.elementToBeClickable(saveAndBackButton));
			    click(saveAndBackButton); 
		        System.out.println("Created coupon: " + couponName);
		        Common.waitForElement(2);
			    waitFor(resetBtn);
			    click(resetBtn);
			    wait.until(ExpectedConditions.elementToBeClickable(resetAllBtn));
			    Common.waitForElement(1);
			    click(resetAllBtn);
			    System.out.println("✅ Successfull click reset all Button");
			    Common.waitForElement(2);
		      
		    
		}
		    
		    public void deleteAllProductsFromCart() {
	        	AdminEmailVerifyOrderFlowPage delete = new AdminEmailVerifyOrderFlowPage(driver);
	        	delete.deleteAllProductsFromCart();
	        	}
		    
		    
		    String productlistingName;

			public String takeRandomProductFromAll() {
			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			    Actions actions = new Actions(driver);

			    // Hover on Shop → All
			    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
			            By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
			    actions.moveToElement(shopMenu).perform();

			    WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
			            By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'DRESSES']")));
			    allButton.click();

			    System.out.println("✅ Clicked on 'All' under Shop menu");

			    // Collect all product cards
			    List<WebElement> products = wait.until(ExpectedConditions
			            .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'product_list_cards_list ')]")));

			    if (products.isEmpty()) {
			        System.out.println("⚠️ No products found on listing page!");
			        return null;
			    }

			    Random rand = new Random();
			    int maxAttempts = Math.min(5, products.size());
			    boolean productFound = false;

			    for (int attempt = 1; attempt <= maxAttempts; attempt++) {

			        int randomIndex = rand.nextInt(products.size()) + 1;
			        System.out.println("🎯 Checking random product index: " + randomIndex);

			        WebElement productCard = driver.findElement(
			                By.xpath("(//div[contains(@class,'product_list_cards_list')])[" + randomIndex + "]"));

			        String name = productCard.findElement(
			                By.xpath(".//h2[@class='product_list_cards_heading']"))
			                .getText().trim();

			        List<WebElement> stockLabels = productCard.findElements(
			                By.xpath(".//h2[contains(@class,'product_list_cards_out_of_stock_heading') and normalize-space()='OUT OF STOCK']"));

			        boolean isOutOfStock = !stockLabels.isEmpty() && stockLabels.get(0).isDisplayed();

			        if (isOutOfStock) {
			            System.out.println("❌ '" + name + "' is OUT OF STOCK. Retrying...");
			            continue;
			        }

			        // Found in-stock product
			        String  productName = name;

			        WebElement productNameElement = productCard.findElement(
			                By.xpath(".//h2[@class='product_list_cards_heading']"));

			     // Fix: JS click to avoid interception
			        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productNameElement);

			        productFound = true;
			        System.out.println("✅ Selected random in-stock product: " + productName);
			        break;
			    }

			    if (!productFound) {
			        System.out.println("⚠️ No in-stock product found after trying " + maxAttempts);
			        return null;
			    }
			    // Click ADD TO CART button on PDP
			    
			     productlistingName = driver.findElement(
			            By.xpath("//h4[@class='prod_name']")
			    ).getText().trim();
			    System.out.println("Product Name: " + productlistingName);
			    
			    Common.waitForElement(2);
			    WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
			            By.xpath("(//button[contains(text(),'Add to')])[1]")));
			    Common.waitForElement(2);
			 // scroll it into center
			    ((JavascriptExecutor) driver).executeScript(
			            "arguments[0].scrollIntoView({block: 'center'});", addToCart);

			    // click via JS (bypasses click interception)
			    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);
			    

			    System.out.println("🛒 Add to Cart clicked on PDP for: " + productlistingName);

			    return productlistingName;
			}
	    
	public void loginAndDelete() {
		
		LoginPage login = new LoginPage(driver);
		login.userLogin();
        Common.waitForElement(2);
        
       // deleteAllProductsFromCart();
		
 
	}
	
	
	public static final String RESET = "\u001B[0m";

	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String CYAN = "\u001B[36m";
	public static final String WHITE = "\u001B[37m";
	    
	
	 
	public void validateCheckoutCouponSection() {

	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);

	    couponName = couponName.toUpperCase();

	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys(couponName);
	    System.out.println(CYAN + "✍️ Entered coupon code: " + couponName + RESET);

	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
	    click(applyBtn);
	    System.out.println(BLUE + "🔄 Applying coupon..." + RESET);

	    System.out.println(YELLOW + "🔍 Checking Coupon Status..." + RESET);

	 // ❌ Coupon should NOT be applied
	    try {
	        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_status' and normalize-space()='applied']")));

	        // If found → FAIL
	        System.out.println(RED + "❌ ERROR: Coupon got applied, but it should NOT!" + RESET);
	        Assert.fail("Coupon applied unexpectedly!");

	    } catch (TimeoutException e) {

	        // If not found → PASS
	        System.out.println(GREEN + "✅ Correct: Coupon NOT applied." + RESET);
	    }

	    // ✔️ Check Apply button still visible
	    try {
	        wait.until(ExpectedConditions.visibilityOf(applyBtn));
	        System.out.println(GREEN + "✅ Apply button is still visible." + RESET);
	    } catch (TimeoutException ex) {
	        System.out.println(RED + "❌ Apply button not visible!" + RESET);
	        Assert.fail("Apply button disappeared!");
	    }
	}

	public void validateCouponSectionForUnlock() {

	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
	    click(viewCoupon);

	    couponName = couponName.toUpperCase();
	    System.out.println(CYAN + "🔍 Validating coupon: " + couponName + RESET);

	    // XPaths
	    String availableCouponXpath = "//div[@class='coupon_list_wrap eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" + couponName + "']";
//	    String unlockMoreXpath = "//div[@class='coupon_list_wrap non_eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" + couponName + "']";

	    // ❌ STEP 1: Coupon MUST NOT appear in AVAILABLE COUPONS
	    try {
	        driver.findElement(By.xpath(availableCouponXpath));
	        System.out.println(RED + "❌ ERROR: Coupon FOUND in Available Coupons section!" + RESET);
	        Assert.fail("Coupon incorrectly shown in Available Coupons!");
	    } catch (NoSuchElementException e) {
	        System.out.println(GREEN + "✅ Correct: Coupon NOT in Available Coupons." + RESET);
	    }

	    // ❌ STEP 2: Coupon MUST NOT show SELECT button
	    String selectBtnXpath = "//div[@class='coupon_list_wrap eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" 
	                            + couponName + "']/following::button[contains(text(),'Select')][1]";

	    try {
	        driver.findElement(By.xpath(selectBtnXpath));
	        System.out.println(RED + "❌ ERROR: Select button visible for this coupon!" + RESET);
	        Assert.fail("Coupon incorrectly shows Select button!");
	    } catch (NoSuchElementException ex) {
	        System.out.println(GREEN + "✅ Correct: No Select button for this coupon." + RESET);
	    }

	 // ✔️ STEP 3: Coupon MUST appear under UNLOCK MORE COUPONS
	 // STEP 3: Coupon MUST appear under Unlock More Coupons
	    try {
	        String unlockMoreXpath =
	                "//div[@class='coupon_list_wrap non_eligible_coupons']" +
	                "//div[contains(@class,'coupon_heading')][contains(normalize-space(),'" + couponName + "')]";

	        WebElement couponUnderUnlock = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath(unlockMoreXpath))
	        );

	        System.out.println(GREEN + "✅ Coupon found under Unlock More Coupons." + RESET);

	        // Correct Add Product locator
	        String addProductBtnXpath =
	                ".//ancestor::div[contains(@class,'coupon_card')]//a[contains(@class,'coupon_apply_btn')]";

	        WebElement addProductBtn = couponUnderUnlock.findElement(By.xpath(addProductBtnXpath));

	        // Scroll
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({block: 'center'});", addProductBtn);

	        Common.waitForElement(1);

	        if (!addProductBtn.isDisplayed()) {
	            System.out.println(RED + "❌ Add Product button NOT visible!" + RESET);
	            Assert.fail("Add Product button missing!");
	        }

	        System.out.println(GREEN + "✅ Add Product button is visible. Clicking..." + RESET);
	        addProductBtn.click();
	        
	          // 👈 CLICK HERE
	        waitUntilHeadingAllVisible();
	        waitUntilProductsAppear();
	        System.out.println("✅ FINAL RESULT: Heading and both products successfully verified!");
	        
	        wait.until(ExpectedConditions.elementToBeClickable(addToBag));
	        click(addToBag);
	        System.out.println(GREEN + "✅ Clicked 'Add To Bag'" + RESET);

	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn));
	        click(addToCartBtn);
	        System.out.println(GREEN + "✅ Added product to cart" + RESET);
	    }
	    catch (Exception e) {
	        System.out.println(RED + "❌ ERROR: Coupon NOT found under Unlock More Coupons!" + RESET);
	        Assert.fail("Coupon missing in Unlock More Coupons!");
	    }

	}
	
	
	    
	public void waitUntilHeadingAllVisible() {
		Common.waitForElement(3);
	    String headingName = "All";
	    long endTime = System.currentTimeMillis() + Duration.ofMinutes(10).toMillis();

	    while (System.currentTimeMillis() < endTime) {

	        driver.navigate().refresh();
	        try { Thread.sleep(2000); } catch (Exception ignored) {}

	        List<WebElement> heading = driver.findElements(By.xpath(
	                "//h3[@class='prod_list_topic']/span[normalize-space()='" + headingName + "']"
	        ));

	        if (!heading.isEmpty() && heading.get(0).isDisplayed()) {
	            System.out.println("✅ Heading 'All' is now visible");
	            return;
	        }

	        System.out.println("⏳ Waiting for heading 'All'...");
	    }

	    throw new RuntimeException("❌ Heading 'All' did NOT appear within 10 min");
	}
	String expected1;
	public void waitUntilProductsAppear() {
		Common.waitForElement(3);
	     expected1 = "Test By Auto1";
	    String expected2 = "Test By Auto2";

	    long endTime = System.currentTimeMillis() + Duration.ofMinutes(10).toMillis();

	    while (System.currentTimeMillis() < endTime) {

	        driver.navigate().refresh();
	        try { Thread.sleep(2000); } catch (Exception ignored) {}

	        List<WebElement> products = driver.findElements(By.xpath(
	                "//h2[@class='product_list_cards_heading']"
	        ));

	        if (products.size() >= 2) {

	            String p1 = products.get(0).getText().trim();
	            String p2 = products.get(1).getText().trim();

	            System.out.println("➡ Checking products: " + p1 + " | " + p2);

	            if (p1.equals(expected1) && p2.equals(expected2)) {
	                System.out.println("🎉 Both products appeared in correct order!");
	                return;
	            }
	        }

	        System.out.println("⏳ Waiting for products to appear...");
	    }

	    throw new RuntimeException("❌ Required products did NOT appear within 10 minutes.");
	}
	    
	    
	    
	public void validateCouponSectionForAvailable() {
		
		
		Common.waitForElement(3);
        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
	    click(viewCoupon);

	    couponName = couponName.toUpperCase();
	    System.out.println(CYAN + "🔍 Validating AVAILABLE coupon: " + couponName + RESET);

	    // XPaths
	    String availableCouponXpath = 
	            "//div[@class='coupon_list_wrap eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" 
	            + couponName + "']";

	    String selectBtnXpath = 
	            "//div[@class='coupon_list_wrap eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" 
	            + couponName + "']/following::button[contains(@class,'coupon_apply_btn')][1]";

	    String unlockMoreXpath = 
	            "//div[@class='coupon_list_wrap non_eligible_coupons']//div[@class='coupon_heading' and normalize-space()='" 
	            + couponName + "']";


	    // ✔ STEP 1: Coupon MUST appear in AVAILABLE section
	    WebElement availableCoupon;
	    try {
	        availableCoupon = driver.findElement(By.xpath(availableCouponXpath));
	        System.out.println(GREEN + "✅ Coupon FOUND under Available Coupons." + RESET);
	    } catch (NoSuchElementException e) {
	        System.out.println(RED + "❌ ERROR: Coupon NOT found under Available Coupons!" + RESET);
	        Assert.fail("Coupon must be in Available section!");
	        return;
	    }


	    // ❌ STEP 2: Coupon MUST NOT appear in Unlock More section
	    if (!driver.findElements(By.xpath(unlockMoreXpath)).isEmpty()) {
	        System.out.println(RED + "❌ ERROR: Coupon appears in Unlock More section also!" + RESET);
	        Assert.fail("Coupon MUST NOT be in Unlock More!");
	    } else {
	        System.out.println(GREEN + "✅ Correct: Coupon NOT present in Unlock More." + RESET);
	    }


	    // ✔ STEP 3: Select button MUST be visible
	    WebElement selectButton;
	    try {
	        selectButton = driver.findElement(By.xpath(selectBtnXpath));
	        System.out.println(GREEN + "✅ Select button is visible." + RESET);
	    } catch (NoSuchElementException ex) {
	        System.out.println(RED + "❌ ERROR: Select button NOT found for this coupon!" + RESET);
	        Assert.fail("Select button missing!");
	        return;
	    }


	    // ✔ STEP 4: Click SELECT button
	    try {
	        wait.until(ExpectedConditions.elementToBeClickable(selectButton));
	        selectButton.click();
	        System.out.println(GREEN + "👉 Clicked SELECT button successfully." + RESET);
	        Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(applyBtn2));
		    click(applyBtn2);
		    System.out.println("🟢 Clicked on Apply button.");
	        
	    } catch (Exception e) {
	        System.out.println(RED + "❌ ERROR: Failed to click SELECT button!" + RESET);
	        Assert.fail("Unable to click Select button!");
	    }

	    
	} 
	    
		    
	public int expectedCouponAmount=100;    
	public void verifyAppliedCoupon() {
		
		couponName = couponName.toUpperCase();
	    System.out.println(CYAN + "🔍 Validating AVAILABLE coupon: " + couponName + RESET);
	   

	    // Wait for applied coupon card
	    WebElement appliedCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[contains(@class,'applied_coupon_card')]")
	    ));

	    Assert.assertTrue("❌ Applied coupon card not visible!", appliedCard.isDisplayed());
	    System.out.println(GREEN + "✅ Applied coupon card is visible" + RESET);


	    // 1️⃣ Coupon Name Validation
	    WebElement appliedCouponName = appliedCard.findElement(
	            By.xpath(".//h5[@class='acc_details']")
	    );

	    String uiCouponCode = appliedCouponName.getText().trim().toUpperCase();
	    System.out.println(YELLOW + "📌 UI Coupon: " + uiCouponCode + RESET);

	    Assert.assertEquals("❌ Coupon code mismatch!", couponName, uiCouponCode);
	    System.out.println(GREEN + "✅ Coupon name is correct" + RESET);


	    // 2️⃣ Status must be "applied"
	    WebElement status = appliedCard.findElement(
	            By.xpath(".//p[@class='acc_status']")
	    );

	    String uiStatus = status.getText().trim().toLowerCase();
	    Assert.assertEquals("❌ Coupon status is not 'applied'!", "applied", uiStatus);
	    System.out.println(GREEN + "✅ Coupon status is applied" + RESET);


	    // 3️⃣ Saved amount validation
	    WebElement savedText = appliedCard.findElement(
	            By.xpath(".//p[@class='acc_details_status']")
	    );

	    String uiSavedMsg = savedText.getText().trim();
	    System.out.println(YELLOW + "💰 Saved message UI: " + uiSavedMsg + RESET);

	    // Extract number from something like "You saved ₹300 extra"
	    int uiSavedAmount = Integer.parseInt(uiSavedMsg.replaceAll("[^0-9]", ""));

	    Assert.assertEquals("❌ Saved amount mismatch!", expectedCouponAmount, uiSavedAmount);
	    System.out.println(GREEN + "💰 Saved amount is correct: ₹" + expectedCouponAmount + RESET);


	    System.out.println(GREEN + "🎉 SUCCESS: Coupon validated → " + couponName 
	            + " | Saved: ₹" + expectedCouponAmount + RESET);
	}
		    
		int discountedMRP;    
	public void placeOrder() throws InterruptedException {

	    //Common.waitForElement(2);
	      Common.waitForElement(2);

	        // Helper to parse int safely
	        Function<WebElement, Integer> parseMoney = el ->
	                Integer.parseInt(el.getText().replaceAll("[^0-9]", ""));

	        // Helper to safely get integer value (returns 0 if not found)
	        Function<String, Integer> safeGet = (xpath) -> {
	            try {
	                WebElement el = driver.findElement(By.xpath(xpath));
	                return parseMoney.apply(el);
	            } catch (Exception e) {
	                return 0;  // element not available
	            }
	        };
	    discountedMRP  = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_discounted_mrp')]");
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
	    click(placeOrderBtn);
	    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);

	    Thread.sleep(5000);

	    // ===============================
	    // 1️⃣ Switch to Razorpay iframe
	    // ===============================
	    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
	        By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
	    ));
	    System.out.println("✅ Switched to Razorpay iframe");

	    // Continue
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[contains(.,'Continue')]")
	    )).click();
	    System.out.println("✅ Continue clicked");

	    // ===============================
	    // 2️⃣ Fill Razorpay address
	    // ===============================
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("zipcode"))).sendKeys("560001");
	    driver.findElement(By.id("name")).sendKeys("Saroj Test");
	    driver.findElement(By.id("line1")).sendKeys("Bangalore");
	    driver.findElement(By.id("line2")).sendKeys("bjvhcgfchvbjkn");

	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[contains(.,'Continue') and @name='new_shipping_address_cta']"))
	    ).click();

	    System.out.println("✅ Address submitted successfully");

	    // ===============================
	    // 3️⃣ Select Netbanking → HDFC
	    // ===============================
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//span[@data-testid='Netbanking']"))).click();

	    wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//div[@role='button' and .//span[contains(text(),'HDFC Bank')]])[1]"))).click();

	    driver.switchTo().defaultContent();

	    // ===============================
	    // 4️⃣ Switch Razorpay success window
	    // ===============================
	    String mainWindow = driver.getWindowHandle();
	    Thread.sleep(3000);

	    for (String window : driver.getWindowHandles()) {
	        if (!window.equals(mainWindow)) {
	            driver.switchTo().window(window);
	            System.out.println(GREEN + "✅ Switched to Razorpay window" + RESET);
	            break;
	        }
	    }

	    WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("//button[@data-val='S' and normalize-space()='Success']")));
	    successBtn.click();
	    System.out.println(GREEN + "💳 Payment Success clicked" + RESET);

	    Thread.sleep(5000);
	    driver.switchTo().window(mainWindow);
	    System.out.println(GREEN + "🔙 Switched back to main window" + RESET);

	    // ===============================
	    // 5️⃣ Validate Order Confirmation
	    // ===============================
	    Thread.sleep(7000);

	    try {
	        WebElement confirmMsg = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Order Confirmed']")
	            )
	        );

	        if (confirmMsg.isDisplayed()) {

	            System.out.println(GREEN + "🎉 Order Confirmed Successfully!" + RESET);

	            // Scroll to product summary
	            WebElement element = driver.findElement(By.cssSelector(".placed_prod_view_details_row"));
	            ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
	                element
	            );

	            Common.waitForElement(2);

	            // 🔥 CLICK VIEW ORDER DETAILS
	            wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
	            click(viewOrderDetails);
	            System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);


	        } else {
	            System.out.println(RED + "❌ Order Confirmed message NOT visible." + RESET);
	        }

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Order not confirmed. Skipping View Order Details." + RESET);
	    }

	}
		    
		    
	public void switchToOrder(String specificProduct) {


	    System.out.println(GREEN + "🚀 Starting Order Return Flow..." + RESET);
	  

	    // Open My Orders
//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);

	    wait.until(ExpectedConditions.elementToBeClickable(myProfileIcon)).click();
	    Common.waitForElement(1);

	    wait.until(ExpectedConditions.elementToBeClickable(myOrdersBtn)).click();
	    Common.waitForElement(2);

	    // Search Order
	    wait.until(ExpectedConditions.visibilityOf(myOrderSearchBox));
	    myOrderSearchBox.clear();
	    myOrderSearchBox.sendKeys(specificProduct);
	    myOrderSearchBox.sendKeys(Keys.ENTER);

	    Common.waitForElement(3);

	    // dynamic xpath – always first visible redirect button
	    String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[1]";

	    try {
	        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));

	        // Scroll to the element
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
	                btn
	        );
	        Common.waitForElement(1);

	        // Click using JS (avoids intercepted issues)
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

	        System.out.println(GREEN + "✅ Opened Order Successfully" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Order button not found or not clickable!" + RESET);
	        e.printStackTrace();
	    }
	}
	
		String orderId;    
	public void validateCouponCode() {

	    System.out.println(CYAN + "🔍 Validating applied coupon discount..." + RESET);
	    
Common.waitForElement(2);
WebElement orderIdElement = driver.findElement(By.xpath("//div[@class='prod_order_id_value']"));
orderId = orderIdElement.getText().trim();
System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
Common.waitForElement(2);
	    // Open Price Breakup
        driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	    // Coupon Discount row (from your HTML)
	    String couponXpath =
	            "//div[@class='price_details_row coupon_discount']//div[@class='price_details_pair']";

	    // 1️⃣ Wait for coupon row to appear
	    WebElement couponElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.xpath(couponXpath))
	    );

	    // 2️⃣ Assert Coupon Discount section is visible
	    Assert.assertTrue(
	            "❌ Coupon Discount row is NOT displayed!",
	            couponElement.isDisplayed()
	    );

	    // 3️⃣ Extract only numbers (removes ₹, -, space)
	    String uiText = couponElement.getText().replaceAll("[^0-9]", "");
	    int uiCouponAmount = Integer.parseInt(uiText);

	    System.out.println(GREEN + "🟢 UI Coupon Amount: " + uiCouponAmount + RESET);
	    System.out.println(GREEN + "🟢 Expected Coupon Amount: " + expectedCouponAmount + RESET);

	    // 4️⃣ Assert expected amount matches UI amount
	    Assert.assertEquals(
	            "❌ Coupon Discount MISMATCH!",
	            expectedCouponAmount,
	            uiCouponAmount
	    );

	    System.out.println(GREEN + "✅ Coupon discount validated successfully!" + RESET);
	    Common.waitForElement(2);
	    closeBtn.click();
	}
		    
	public void validateCouponNotDisplayed() {

	    System.out.println(CYAN + "🔍 Validating coupon discount is NOT displayed..." + RESET);
	 // Open Price Breakup
        driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	    try {
	        Common.waitForElement(1);

	        String couponXpath =
	                "//div[@class='price_details_row coupon_discount']//div[@class='price_details_pair']";

	        // Try to find the element
	        WebElement couponElement = driver.findElement(By.xpath(couponXpath));

	        // If found & displayed → FAIL test
	        if (couponElement.isDisplayed()) {
	            assert false : "❌ Coupon Discount is VISIBLE but it should NOT be!";
	        }

	    } catch (NoSuchElementException e) {
	        // Perfect → element is not present
	        System.out.println(GREEN + "✅ Coupon Discount is NOT displayed (as expected)" + RESET);
	        return;
	    } catch (Exception e) {
	        // Any other unexpected error → fail
	        assert false : "❌ Unexpected error while checking coupon visibility: " + e.getMessage();
	    }

	    System.out.println(GREEN + "✅ Coupon Discount is NOT displayed!" + RESET);
	}	    
		    
		    
	public void validateCouponAdminPanel() {

//	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    driver.get(Common.getValueFromTestDataMap("Link"));
	    System.out.println(GREEN + "✅ Navigated to Orders page" + RESET);

	    couponName = couponName.toUpperCase();
	    System.out.println(CYAN + "🔍 Validating coupon: " + couponName + RESET);

	    // Search Order ID
	    wait.until(ExpectedConditions.elementToBeClickable(orderIdbtn)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(orderSearchBox));
	    orderSearchBox.clear();
	    orderSearchBox.sendKeys(orderId);
	    orderSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(3);

	    // Validate Order exists
	    try {
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//td/span[normalize-space(text())='" + orderId + "']")));
	        System.out.println(GREEN + "✅ Order found in table!" + RESET);
	    } catch (TimeoutException e) {
	        assert false : "❌ Order not found in Admin Panel!";
	        return;
	    }

	    // Open Edit Page
	    wait.until(ExpectedConditions.elementToBeClickable(editBtn)).click();
	    System.out.println(GREEN + "✅ Opened Edit page" + RESET);

	    // ============================
	    // 🔍 Check Coupon Code Visible
	    // ============================
	    String couponXpath = "//input[@name='coupon_code']";

	    try {
	        WebElement couponInput = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath(couponXpath))
	        );

	        String uiCoupon = couponInput.getAttribute("value").trim();

	        assert uiCoupon.equals(couponName) :
	                "❌ Coupon Code mismatch! Expected: " + couponName + ", Found: " + uiCoupon;
	        Common.waitForElement(3);
	        System.out.println(GREEN + "✅ Coupon Code visible & matched: " + uiCoupon + RESET);

	    } catch (Exception e) {
	        assert false : "❌ Coupon Code NOT visible in Admin Panel!";
	    }
	}
		    
	
	public void copySpecificCouponCode() {

	    Common.waitForElement(3);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successfully redirected to Admin Coupon page");
	    Common.waitForElement(2);

	    try {
	        // XPath for the first coupon code
	        String couponXpath = "(//td/span)[3]";

	        WebElement couponElement = driver.findElement(By.xpath(couponXpath));
	         couponName = couponElement.getText().trim();

	        System.out.println("📌 Extracted Coupon Code: " + couponName);

	        // Store to global variable (if needed)
	   //     this.couponName = couponCode;

	    } catch (NoSuchElementException e) {
	        System.out.println("❌ ERROR: Unable to locate first coupon code!");
	        e.printStackTrace();
	    }
	}   
		    
		    
		    
	public void searchAndAddSpecificProductsToCart(String firstProduct, String secondProduct) throws InterruptedException {
    	
        Common.waitForElement(2);

        // ✅ Product list to search and add
        String[] products = { firstProduct, secondProduct };

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        for (String productName : products) {
            System.out.println("\n🔍 Searching for product: " + productName);

            // ✅ Click on search icon / box
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[contains(@class, 'navigation_search_input_field') and @type='text']")
            ));
            
         // Search in user app
            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
            searchBox.click();
            searchBox.clear();
            searchBox.sendKeys(productName);
            System.out.println("✅ Searched for listing: " + productName);
            Common.waitForElement(1);
            // Wait for product to appear
            By productLocator = By.xpath("//h6[normalize-space()='" + productName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));

            WebElement productElement = driver.findElement(productLocator);
            Assert.assertTrue("❌ Listing name not found in User App: " + productName, productElement.isDisplayed());
            Common.waitForElement(1);
            // Click product
            productElement.click();
            System.out.println("✅ Product opened in User App: "+ productName);
            
//         // ✅ Wait for & scroll into the Top Selling section
//            WebElement targetProduct = wait.until(
//                    ExpectedConditions.presenceOfElementLocated(
//                            By.cssSelector(".prod_add_cart_btn"))
//            );
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetProduct);
//            Common.waitForElement(3);
            // ✅ Wait for Add to Cart button
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(),'Add to')]")
            ));
            addToCartBtn.click();
            Common.waitForElement(3);
            System.out.println("🛒 Added product to cart: " + productName);
        }

        System.out.println("\n🎉 Both products searched and added to cart successfully!");
        
        
    }    
		    
		    
	public void moveToProduct(int productLevel) {
		Common.waitForElement(2);
		// Build dynamic XPath
		String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[" + productLevel + "]";
		WebElement btn = driver.findElement(By.xpath(xpath));

		// 1️⃣ Scroll to the element
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
		
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}    
		    
	int discountedMRP1;
	int couponDiscount1;
	public void validatePriceBreakupDetails_P1() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String LINE = CYAN + "──────────────────────────────────────────────" + RESET;

	    System.out.println(CYAN + "🔍 Validating applied coupon discount..." + RESET);
	    
	    Common.waitForElement(2);
	    WebElement orderIdElement = driver.findElement(By.xpath("//div[@class='prod_order_id_value']"));
	    orderId = orderIdElement.getText().trim();
	    System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
	    Common.waitForElement(2);
	    	    // Open Price Breakup
	            driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	    // Helper: returns integer value OR fails test if missing
	    Function<String, Integer> getMandatoryValue = (label) -> {
	        try {
	            WebElement ele = driver.findElement(By.xpath(
	                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
	                "/following-sibling::div[@class='price_details_pair']"
	            ));
	            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
	        } catch (Exception e) {
	            Assert.fail("❌ Missing required field in Price Breakup: " + label);
	            return 0; // unreachable but required by compiler
	        }
	    };

	    Common.waitForElement(1);

	    // -------------------------------
	    // 🔹 FETCH ONLY REQUIRED UI VALUES
	    // -------------------------------
	    discountedMRP1  = getMandatoryValue.apply("Discounted MRP");
	    couponDiscount1 = getMandatoryValue.apply("Coupon Discount");

	    // -------------------------------
	    // 🔹 PRINT VALUES
	    // -------------------------------
	    System.out.println(LINE);
	    System.out.println(CYAN + "📌 PRICE DETAILS COPIED FROM PRICE BREAKUP" + RESET);

	    System.out.println(GREEN + "Discounted MRP:   " + discountedMRP1 + RESET);
	    System.out.println(GREEN + "Coupon Discount UI:  " + couponDiscount1 + RESET);

	    System.out.println(LINE);
	}	    
		
	int discountedMRP2;
	int couponDiscount2;
	public void validatePriceBreakupDetails_P2() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String LINE = CYAN + "──────────────────────────────────────────────" + RESET;

	    System.out.println(CYAN + "🔍 Validating applied coupon discount..." + RESET);
	    
//	    Common.waitForElement(2);
//	    WebElement orderIdElement = driver.findElement(By.xpath("//div[@class='prod_order_id_value']"));
//	    orderId = orderIdElement.getText().trim();
	    System.out.println(YELLOW + "🆔 Order ID: " + orderId + RESET);
	    Common.waitForElement(2);
	    	    // Open Price Breakup
	            driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	    // Helper: returns integer value OR fails test if missing
	    Function<String, Integer> getMandatoryValue = (label) -> {
	        try {
	            WebElement ele = driver.findElement(By.xpath(
	                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
	                "/following-sibling::div[@class='price_details_pair']"
	            ));
	            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
	        } catch (Exception e) {
	            Assert.fail("❌ Missing required field in Price Breakup: " + label);
	            return 0; // unreachable but required by compiler
	        }
	    };

	    Common.waitForElement(1);

	    // -------------------------------
	    // 🔹 FETCH ONLY REQUIRED UI VALUES
	    // -------------------------------
	    discountedMRP2  = getMandatoryValue.apply("Discounted MRP");
	    couponDiscount2 = getMandatoryValue.apply("Coupon Discount");

	    // -------------------------------
	    // 🔹 PRINT VALUES
	    // -------------------------------
	    System.out.println(LINE);
	    System.out.println(CYAN + "📌 PRICE DETAILS COPIED FROM PRICE BREAKUP" + RESET);

	    System.out.println(GREEN + "Discounted MRP:   " + discountedMRP2 + RESET);
	    System.out.println(GREEN + "Coupon Discount UI:  " + couponDiscount2 + RESET);

	    System.out.println(LINE);
	}
		    
		    
	public void verifyCouponSplit_P1() {
		String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String BLUE   = "\u001B[34m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "📘 COUPON DISTRIBUTION CALCULATION" + RESET);
		// =============================
		// COUPON CALCULATION
		// =============================
		System.out.println(CYAN + "🧮 Performing Coupon Calculation..." + RESET);

		// Formula: (Product Discounted Amount / Total Discounted MRP) * Coupon Discount Amount
		System.out.println(GREEN + "Formula: (ProductDiscountedAmount / TotalDiscountedMRP) * TotalCouponDiscount" + RESET);

		// Avoid divide-by-zero
		double calcCouponRaw = 0.0;
		if (discountedMRP1 > 0) {
		    calcCouponRaw = ((double) discountedMRP1 / (double) discountedMRP) * expectedCouponAmount;
		}

		// ROUNDING OPTIONS
		int calcCouponFloor = (int) Math.floor(calcCouponRaw);
		int calcCouponCeil  = (int) Math.ceil(calcCouponRaw);

		System.out.println(YELLOW + "Calculated Coupon Raw:      " + calcCouponRaw + RESET);
		System.out.println(YELLOW + "Calculated Coupon Floor:    " + calcCouponFloor + RESET);
		System.out.println(YELLOW + "Calculated Coupon Ceil:     " + calcCouponCeil + RESET);
		System.out.println(LINE);
		System.out.println(YELLOW + "UI Coupon Discount:         " + couponDiscount1 + RESET);

		System.out.println(LINE);

		// =============================
		// VALIDATION WITH TOLERANCE
		// =============================
		if (couponDiscount1 == calcCouponFloor || couponDiscount1 == calcCouponCeil) {

		    System.out.println(GREEN +
		        "✅ COUPON DISCOUNT MATCHED UI (Accepted Floor/Ceil Tolerance)" +
		    RESET);

		} else {

		    System.out.println(RED +
		        "❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount1 +
		        " | CalcFloor: " + calcCouponFloor +
		        " | CalcCeil: " + calcCouponCeil +
		        RESET);

		    Assert.fail("❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount1 +
		        " | CalcFloor: " + calcCouponFloor +
		        " | CalcCeil: " + calcCouponCeil);
		}

		System.out.println(LINE);
		
		
	} 
	public void verifyCouponSplit_P2() {
		String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String BLUE   = "\u001B[34m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "📘 COUPON DISTRIBUTION CALCULATION" + RESET);
		// =============================
		// COUPON CALCULATION
		// =============================
		System.out.println(CYAN + "🧮 Performing Coupon Calculation..." + RESET);

		// Formula: (Product Discounted Amount / Total Discounted MRP) * Coupon Discount Amount
		System.out.println(GREEN + "Formula: (ProductDiscountedAmount / TotalDiscountedMRP) * TotalCouponDiscount" + RESET);

		// Avoid divide-by-zero
		double calcCouponRaw = 0.0;
		if (discountedMRP2 > 0) {
		    calcCouponRaw = ((double) discountedMRP2 / (double) discountedMRP) * expectedCouponAmount;
		}

		// ROUNDING OPTIONS
		int calcCouponFloor = (int) Math.floor(calcCouponRaw);
		int calcCouponCeil  = (int) Math.ceil(calcCouponRaw);

		System.out.println(YELLOW + "Calculated Coupon Raw:      " + calcCouponRaw + RESET);
		System.out.println(YELLOW + "Calculated Coupon Floor:    " + calcCouponFloor + RESET);
		System.out.println(YELLOW + "Calculated Coupon Ceil:     " + calcCouponCeil + RESET);
		System.out.println(LINE);
		System.out.println(YELLOW + "UI Coupon Discount:         " + couponDiscount2 + RESET);

		System.out.println(LINE);

		// =============================
		// VALIDATION WITH TOLERANCE
		// =============================
		if (couponDiscount2 == calcCouponFloor || couponDiscount2 == calcCouponCeil) {

		    System.out.println(GREEN +
		        "✅ COUPON DISCOUNT MATCHED UI (Accepted Floor/Ceil Tolerance)" +
		    RESET);

		} else {

		    System.out.println(RED +
		        "❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount2 +
		        " | CalcFloor: " + calcCouponFloor +
		        " | CalcCeil: " + calcCouponCeil +
		        RESET);

		    Assert.fail("❌ COUPON DISCOUNT MISMATCH — UI: " + couponDiscount2 +
		        " | CalcFloor: " + calcCouponFloor +
		        " | CalcCeil: " + calcCouponCeil);
		}

		System.out.println(LINE);
		
		
	} 
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
		    
//TC-04	    
		    
		public void validateOneSpecificProductWithNormalProduct() throws InterruptedException {
			
			createSpecificProductItemCoupon1();
			
			loginAndDelete();
			
			takeRandomProductFromAll();
			
			validateCheckoutCouponSection();
			 
			validateCouponSectionForUnlock();
			
			validateCouponSectionForAvailable();
			
			verifyAppliedCoupon();
			
			placeOrder();
			
			switchToOrder(expected1);
			
			validateCouponCode();
			
			switchToOrder(productlistingName);
			
			validateCouponNotDisplayed();
			
			validateCouponAdminPanel();
		}
	
		//TC-05
	    
				public void validateTwoSpecificProductCoupon() throws InterruptedException {
					
					copySpecificCouponCode();
					
					loginAndDelete();
					
					searchAndAddSpecificProductsToCart("Test By Auto1", "Test By Auto2");
					
					validateCouponSectionForAvailable();
					
					verifyAppliedCoupon();
					
					placeOrder();
					
					moveToProduct(1);
					
					validatePriceBreakupDetails_P1();
					
					verifyCouponSplit_P1();
					
					closeBtn.click();
				    
					driver.navigate().back();
					moveToProduct(2);
					
					validatePriceBreakupDetails_P2();
					
					verifyCouponSplit_P2();
					
					validateCouponAdminPanel();
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
