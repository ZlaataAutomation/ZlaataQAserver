package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
		  // ✅ Step 1: Click on "Add Coupon" button
		    wait.until(ExpectedConditions.elementToBeClickable(addCouponButton));
		    click(addCouponButton);
	        // ✅ Step 2: Generate a unique random coupon name
	         int randomNum = (int) (Math.random() * 10000); // creates 0–9999
	        couponName = "Automation100Flat" + randomNum;

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
	            .elementToBeClickable(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[normalize-space()='All']")));
	    allButton.click();

	    System.out.println("✅ Clicked on 'All' under Shop menu");
	    Common.waitForElement(3);

	    // --------------------------
	    // Step 1: Get all products > 499
	    // --------------------------
	    List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_wrpr']"));
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
	    Common.waitForElement(2);
	    js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product_list_cards_btn_group product_list_add_to_cart Cls_CartListes ClsSingleCart']"))).click();
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='add_bag_prod_buy_now_btn btn___2 Cls_CartList ClsProductListSizes'])[1]"))).click();
	    System.out.println("✅ Product added to cart successfully.");

	    // --------------------------
	    // Step 4: Go to Coupon section
	    // --------------------------
	    Common.waitForElement(3);
        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);
	    Common.waitForElement(3);
        wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
	    click(viewCoupon);
	 // Convert to uppercase to match the app display
	    couponName = couponName.toUpperCase();
	    

	 // Check for coupon heading text anywhere in coupon list
	    List<WebElement> couponElements = driver.findElements(
	        By.xpath("//div[@class='coupon_list_wrap eligible_coupons']//div[contains(@class,'coupon_heading') and normalize-space(text())='" + couponName + "']")
	    );

	    if (!couponElements.isEmpty()) {
	        System.out.println("✅ Coupon '" + couponName + "' is visible on available for you section.");
	    } else {
	        System.out.println("❌ Coupon '" + couponName + "' is NOT visible on the page.");
	        Assert.fail("Coupon not available in the app.");
	    }
	    
	
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
		            .elementToBeClickable(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[normalize-space()='All']")));
		    allButton.click();

		    System.out.println("✅ Clicked on 'All' under Shop menu");
		    Common.waitForElement(3);

		    // --------------------------
		    // Step 1: Get all products > 500
		    // --------------------------
		    List<WebElement> allProducts = driver.findElements(By.xpath("//div[@class='product_list_cards_wrpr']"));
		    List<WebElement> validProducts = new ArrayList<>();

		    for (WebElement product : allProducts) {
		        String priceText = product.findElement(By.xpath(".//span[@class='prod_current_price']")).getText().replaceAll("[^0-9]", "");
		        int price = Integer.parseInt(priceText);
		        if (price > 500) {
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
		    js.executeScript("arguments[0].scrollIntoView(true);", selectedProduct);
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product_list_cards_btn_group product_list_add_to_cart Cls_CartListes ClsSingleCart']"))).click();
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[@class='add_bag_prod_buy_now_btn btn___2 Cls_CartList ClsProductListSizes'])[1]"))).click();
		    System.out.println("✅ Product added to cart successfully.");

		    // --------------------------
		    // Step 4: Go to Coupon section
		    // --------------------------
		    Common.waitForElement(3);
	        wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
		    click(clickCartBtn);
		    Common.waitForElement(3);
	        wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
		    click(viewCoupon);
		 // Convert to uppercase to match the app display
		    specialCouponName = specialCouponName.toUpperCase();
		    
		 // Wait for popup to appear
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		    click(searchBox);
		    searchBox.sendKeys(specialCouponName);
		    System.out.println("✍️ Entered coupon code: " + specialCouponName);

		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
		    click(applyBtn);
		    System.out.println("🟢 Clicked on Apply button.");
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
	        type(couponTypeBox, "Specific Product Item" + Keys.ENTER);
		    System.out.println("✅ Successfull Typed Specific Product Item Coupon");
		    Common.waitForElement(3);
		    wait.until(ExpectedConditions.elementToBeClickable(productBox));
		    click(productBox); 
		    Common.waitForElement(2);
	        wait.until(ExpectedConditions.elementToBeClickable(productBox));
	        waitFor(productBox);
		    type(productBox, "Test by Auto");
		    Common.waitForElement(4);
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
	                By.xpath("//input[@id='search__product']")
	            ));
	            
	         // Search in user app
	            wait.until(ExpectedConditions.elementToBeClickable(searchBox));
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
//	            // ✅ Wait for Add to Cart button
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
        wait.until(ExpectedConditions.elementToBeClickable(viewCoupon));
	    click(viewCoupon);
	 // Convert to uppercase to match the app display
	    couponName = couponName.toUpperCase();
	    
	 // Wait for popup to appear
	    Common.waitForElement(2);
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys(couponName);
	    System.out.println("✍️ Entered coupon code: " + couponName);

	    Common.waitForElement(2);
        wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
	    click(applyBtn);
	    System.out.println("🟢 Clicked on Apply button.");
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
