package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
import java.util.function.Function;

import objectRepo.AdminEmailVerifyOrderFlowObjRepo;
import objectRepo.Calculation_MyOrder_ObjRepo;
import utils.Common;

public class Calculation_MyOrder_Page extends Calculation_MyOrder_ObjRepo {
	
	public Calculation_MyOrder_Page(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	
	public void userLoginApp() {
//		HomePage home = new HomePage(driver);
//		home.homeLaunch();
    	LoginPage login = new LoginPage(driver);
		login.userLogin();
	}
public void deleteAllProductFromCart() {
	
//	AdminEmailVerifyOrderFlowPage mail= new AdminEmailVerifyOrderFlowPage(driver);
//	mail.deleteAllProductsFromCart();
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
            System.out.println("🗑️ Product delete initiated");

            // Check if confirmation pop-up appears for gift card
            try {
                WebElement confirmDeleteBtn = driver.findElement(By.xpath("//button[contains(@class, 'Cls_gc_remove_btn')]"));
                if (confirmDeleteBtn.isDisplayed()) {
                    System.out.println("⚠️ Gift card detected. Confirming deletion...");
                    confirmDeleteBtn.click(); // Confirm gift card deletion
                    Common.waitForElement(1); // Wait for deletion to complete
                }
            } catch (NoSuchElementException ignored) {
                // No confirmation for normal product, proceed with deletion
                Common.waitForElement(1); // Wait for deletion to complete
            }
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
	        productlistingName = name;

	        WebElement productNameElement = productCard.findElement(
	                By.xpath(".//h2[@class='product_list_cards_heading']"));

	     // Fix: JS click to avoid interception
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productNameElement);

	        productFound = true;
	        System.out.println("✅ Selected random in-stock product: " + productlistingName);
	        break;
	    }

	    if (!productFound) {
	        System.out.println("⚠️ No in-stock product found after trying " + maxAttempts);
	        return null;
	    }
	    // Click ADD TO CART button on PDP
	    

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
	
	
	public void addGiftCardInCart() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String BLUE   = "\u001B[34m";
	    String RESET  = "\u001B[0m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "🎁 Starting Gift Card Add-to-Cart Process..." + RESET);
	    System.out.println(LINE);

	    // Home
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(homeBtn)).click();
	    System.out.println(GREEN + "🏠 Successfully navigated to Home page" + RESET);

	    // Scroll
	    Common.waitForElement(2);
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("window.scrollBy(0, 3700);");
	    System.out.println(CYAN + "📜 Scrolled to Gift Card banner" + RESET);

	    // Open Gift Card
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(giftCardBanner));
	    js.executeScript("arguments[0].click();", giftCardBanner);
	    System.out.println(GREEN + "🖱️ Gift Card banner clicked" + RESET);

	    // Select 500
	    Common.waitForElement(2);
	 // Scroll into view
	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block: 'center'});", choose500
	    );

	    // JS Click → 100% no interception
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", choose500);
	    System.out.println(GREEN + "💵 Selected ₹500 Gift Card" + RESET);

	    // Next Button
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(nextBtn)).click();
	    System.out.println(GREEN + "➡️ Clicked Next button" + RESET);

	    // Enter recipient email
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("gift_recipient_email")))
	            .sendKeys("test@gmail.com");

	    // Suggestion select
	    Common.waitForElement(2);
	    WebElement suggestionBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@id='suggestions-box'])[1]")));
	    suggestionBtn.click();
	    System.out.println(GREEN + "📧 Recipient email selected" + RESET);

	    // Name
	    driver.findElement(By.id("gift_name")).sendKeys("Saroj Test");

	    // Date Picker — using JS
	    Common.waitForElement(2);
	    WebElement dateInput = driver.findElement(By.id("gift__dob"));

	    LocalDate today = LocalDate.now();
	    String formatted = today.toString(); // yyyy-MM-dd

	    js.executeScript("arguments[0].value = arguments[1];", dateInput, formatted);
	    System.out.println(GREEN + "📅 Selected Date: " + formatted + RESET);

	    // Phone
	    Common.waitForElement(1);
	    driver.findElement(By.id("gitPhonenumber")).sendKeys("9348714087");
	    
	    Common.waitForElement(1);
	    // Message
	    driver.findElement(By.id("gift_message")).sendKeys("Happy Birthday");
	    Common.waitForElement(1);
	    
	    // Sender Name
	    driver.findElement(By.id("gift_sendName")).sendKeys("Saroj Test");
	    
	    // Preview
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(@class,'gift-card-preview')]")
	    )).click();

	    System.out.println(GREEN + "📄 Successfully clicked Preview" + RESET);

	    // Add to Cart
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
	    System.out.println(GREEN + "🛒 Successfully clicked Add to Cart" + RESET);

	    // ----------------------
	    // VERIFY GIFT CARD ADDED
	    // ----------------------
	    System.out.println(LINE);
	    System.out.println(CYAN + "🔍 Verifying Gift Card is added to cart..." + RESET);
	    System.out.println(LINE);
	}
	
	
	public void applyCouponAndGiftWrap() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String BLUE   = "\u001B[34m";
	    String RESET  = "\u001B[0m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "🛒 Starting Apply Coupon & Gift Wrap Process..." + RESET);
	    System.out.println(LINE);

	    // Open cart
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(clickCartBtn));
	    click(clickCartBtn);
	    System.out.println(CYAN + "🛒 Opened Cart" + RESET);

	    // Enter coupon
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys("TEST");
	    System.out.println(YELLOW + "✍️ Entered coupon code: TEST" + RESET);

	    // Click Apply
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
	    click(applyBtn);
	    System.out.println(CYAN + "🔄 Applying coupon..." + RESET);

	    System.out.println(LINE);
	    System.out.println(CYAN + "🔍 Checking Coupon Status..." + RESET);
	    System.out.println(LINE);

	    // CHECK 1: Coupon Applied
	    try {
	        WebElement appliedMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_status']")));

	        System.out.println(GREEN + "✅ Coupon applied successfully!" + RESET);

	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Coupon NOT applied!" + RESET);
	        Assert.fail("Coupon was not applied!");
	    }

	    // CHECK 2: Discount Amount
	    try {
	        WebElement discountMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[@class='acc_details_status']")));

	        String discountText = discountMsg.getText(); 
	        String discountValue = discountText.replaceAll("[^0-9]", "");

	        System.out.println(GREEN + "💰 Discount Applied: ₹" + discountValue + RESET);

	    } catch (TimeoutException e) {
	        System.out.println(RED + "❌ Discount amount not found!" + RESET);
	        Assert.fail("Discount amount not detected!");
	    }

	    System.out.println(LINE);
	    System.out.println(CYAN + "🎁 Applying Gift Wrap..." + RESET);
	    System.out.println(LINE);

	    // Gift Wrap
	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(clickGiftWrap));
	    click(clickGiftWrap);
	    System.out.println(YELLOW + "🎁 Clicked Gift Wrap" + RESET);

	    Common.waitForElement(2);
	    driver.findElement(By.id("recipient-name")).sendKeys("Saroj Test");
	    System.out.println(GREEN + "✍️ Entered Recipient Name: Saroj Test" + RESET);

	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//button[@type='submit'][normalize-space()='Submit'])[1]"))).click();

	    System.out.println(GREEN + "✅ Successfully Clicked Submit" + RESET);

	    System.out.println(LINE);
	    System.out.println(GREEN + "🎉 Coupon & Gift Wrap Completed Successfully!" + RESET);
	    System.out.println(LINE);
	}
	
	public void applyGiftCardAmount() {

	    // Console Colors
	    String GREEN = "\u001B[32m";
	    String RED = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String BLUE = "\u001B[34m";
	    String RESET = "\u001B[0m";
	    String LINE = "──────────────────────────────────────────────────────────────";

	    System.out.println(BLUE + LINE + RESET);
	    System.out.println(BLUE + "🎁 APPLYING GIFT CARD" + RESET);
	    System.out.println(BLUE + LINE + RESET);

	    try {
	        // Expand Gift Card section
	        Common.waitForElement(2);
	        WebElement plusBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//div[@class='checkout_details_header gift_card_box_heading']")
	        ));

	        plusBtn.click();
	        System.out.println(GREEN + "➕ Clicked Gift Card expand button" + RESET);

	        Common.waitForElement(2);

	        // Enter Gift Card Number
	        WebElement giftCardInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//input[contains(@class,'Cls_gift_card_number')]")
	        ));

	        giftCardInput.clear();
	        giftCardInput.sendKeys("7624 9826 0572 1212");
	        System.out.println(GREEN + "✔ Entered Gift Card number successfully" + RESET);

	        Common.waitForElement(2);

	        // Click Apply
	        WebElement applyBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//button[contains(@class,'ClsGCapplyButton') and not(@disabled)]")
	        ));

	     // Scroll into view
	        ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block: 'center'});", applyBtn
	        );

	        // Safe JS click to avoid interception
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
	        System.out.println(GREEN + "✔ Clicked Apply button" + RESET);

	        // Wait for Balance
	        WebElement balanceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//p[contains(@class,'ClsGCAvailableBalance')]")
	        ));

	        String balanceText = balanceElement.getText();
	        System.out.println(YELLOW + "💰 Balance Text: " + balanceText + RESET);

	        int availableBalance = Integer.parseInt(balanceText.replaceAll("[^0-9]", ""));
	        System.out.println(YELLOW + "💳 Available Balance: ₹" + availableBalance + RESET);

	        // Enter amount if balance > 100
	        if (availableBalance > 100) {

	            WebElement amountInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//input[contains(@class,'Cls_gc_value')]")
	            ));

	            amountInput.clear();
	            amountInput.sendKeys("50");

	            System.out.println(GREEN + "✔ Entered ₹50 as Gift Card amount" + RESET);

	        } else {
	            System.out.println(RED + "❌ Not enough balance (Less than ₹100). Skipping amount entry." + RESET);
	        }

	        System.out.println(BLUE + LINE + RESET);

	    } catch (Exception e) {

	        System.out.println(RED + "❌ Gift Card NOT Applied!" + RESET);
	        System.out.println(RED + "Reason: " + e.getMessage() + RESET);
	        System.out.println(BLUE + LINE + RESET);
	    }
	}
	
	
	public void selectExpressDelivery() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";

	    String LINE = CYAN + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "🚚 Checking Express Delivery availability..." + RESET);
	    System.out.println(LINE);
	    Common.waitForElement(2);
	    try {
	        // Locate Express Delivery parent div
	        WebElement expressDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//div[contains(@class,'delivery_type_card')][label/input[@id='delivery_type_2']]")
	        ));

	        // Check if class contains 'disabled'
	        String classValue = expressDiv.getAttribute("class");

	        if (classValue.contains("disabled")) {
	            System.out.println(RED + "❌ Express Delivery NOT enabled!" + RESET);
	            return;  // Do nothing
	        }

	        // If enabled → click radio button
	        WebElement expressRadio = expressDiv.findElement(By.xpath("//input[@id='delivery_type_2']"));
	      
	        wait.until(ExpectedConditions.elementToBeClickable(expressRadio)).click();

	        System.out.println(GREEN + "✅ Express Delivery Selected Successfully!" + RESET);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Unable to check/select Express Delivery!" + RESET);
	        System.out.println(YELLOW + "⚠ Reason: " + e.getMessage() + RESET);
	    }

	    System.out.println(LINE);
	}
	
	
	
	public void applyThreadValue() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String LINE   = CYAN + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "🧵 Checking available Threads..." + RESET);
	    System.out.println(LINE);

	    try {
	    	Common.waitForElement(2);
	        // Get available thread count
	        WebElement availableThreadElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[@class='price_details_key_span']")
	        ));

	        String threadText = availableThreadElement.getText(); // Example: "35"
	        int availableThreads = Integer.parseInt(threadText);

	        System.out.println(GREEN + "✔ Available Threads: " + availableThreads + RESET);

	        if (availableThreads >= 10) {

	            // Locate input field
	            WebElement threadInput = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//input[contains(@class,'Cls_thread_value')]")
	            ));

	            threadInput.clear();
	            threadInput.sendKeys("10");

	            System.out.println(GREEN + "🧵 Entered 10 Threads successfully!" + RESET);

	        } else {
	            System.out.println(RED + "❌ Not enough threads available (" + availableThreads + ") — Need at least 10" + RESET);
	        }

	    } catch (Exception e) {
	        System.out.println(RED + "❌ Error while applying thread value!" + RESET);
	        System.out.println(YELLOW + "⚠ Reason: " + e.getMessage() + RESET);
	    }

	    System.out.println(LINE);
	}
	
	
	int giftCardMRP;
	int totalMRP;
	int discountedMRP;
	int giftWrapFee;
	int expressShipping;
	int customFee;
	int threadValue;
	int giftCardAmount;
	int couponDiscount;
	public void verifyPriceDetailsCalculation() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String RESET  = "\u001B[0m";
	    String LINE   = CYAN + "──────────────────────────────────────────────────────────────" + RESET;

	    System.out.println(LINE);
	    System.out.println(CYAN + "🔎 Starting Price Details Calculation..." + RESET);

	    try {

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

	        // -----------------------------
	        // Fetch ALL values safely
	        // -----------------------------

	        giftCardMRP    = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_gift_card_mrp')]");
	        totalMRP    = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_mrp')]");
	        discountedMRP  = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_discounted_mrp')]");
	        giftWrapFee    = safeGet.apply("//div[@data-gift_wrapper_fee]");
	        expressShipping = safeGet.apply("(//span[contains(@class,'Cls_convency_fee')])[1]");
	        customFee       = safeGet.apply("(//div[contains(@class,'Cls_customized_extra')])[2]");

	        // Thread Value input
	        threadValue = 0;
	        try {
	            WebElement threadInput = driver.findElement(By.xpath("//input[contains(@class,'Cls_thread_value')]"));
	            if (!threadInput.getAttribute("value").isEmpty()) {
	                threadValue = Integer.parseInt(threadInput.getAttribute("value"));
	            }
	        } catch (Exception e) { threadValue = 0; }

	        // Gift card amount applied
	        giftCardAmount = 0;
	        try {
	            WebElement gcInput = driver.findElement(By.xpath("//input[contains(@class,'Cls_gc_value')]"));
	            if (!gcInput.getAttribute("value").isEmpty()) {
	                giftCardAmount = Integer.parseInt(gcInput.getAttribute("value"));
	            }
	        } catch (Exception e) { giftCardAmount = 0; }

	        // Coupon discount
	        couponDiscount = safeGet.apply("//div[@data-coupon_discount]");

	        // UI shown values
	        int uiSavedAmount = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_saved_amount')]");
	        int uiTotalAmount = safeGet.apply("//div[contains(@class, 'price_details_pair') and contains(@class, 'Cls_cart_total_amount')]");

	        // -----------------------------
	        // PRINT fetched values
	        //------------------------------

	       
	        System.out.println(LINE);
	        System.out.println(CYAN + "📌 Fetched Values From UI" + RESET);

	        System.out.println(YELLOW + "Gift Card MRP: " + giftCardMRP + RESET);
	        System.out.println(YELLOW + "Total MRP: " + totalMRP + RESET);
	        System.out.println(YELLOW + "Discounted MRP: " + discountedMRP + RESET);
	        System.out.println(YELLOW + "Gift Wrap Fee: " + giftWrapFee + RESET);
	        System.out.println(YELLOW + "Express Shipping: " + expressShipping + RESET);
	        System.out.println(YELLOW + "Customisation Fee: " + customFee + RESET);
	        System.out.println(YELLOW + "Thread Value: " + threadValue + RESET);
	        System.out.println(YELLOW + "Gift Card Amount Used: " + giftCardAmount + RESET);
	        System.out.println(YELLOW + "Coupon Discount: " + couponDiscount + RESET);
	        System.out.println(LINE);
	     // UI shown values
	        System.out.println(LINE);
	        System.out.println(CYAN + "📌 This Value Displayng in application checkout page" + RESET);
	        System.out.println(YELLOW + "You Saved UI: " + uiSavedAmount + RESET);
	        System.out.println(YELLOW + "Total Amount UI : " + uiTotalAmount + RESET);
	        System.out.println(LINE);
	        // -----------------------------
	        // Perform calculations
	        // -----------------------------
	        System.out.println(
	        	    "calcTotalAmount = ("
	        	        + "GiftCardMRP : " + giftCardMRP + " + "
	        	        + "DiscountedMRP : " + discountedMRP + " + "
	        	        + "GiftWrapFee : " + giftWrapFee + " + "
	        	        + "ExpressShipping : " + expressShipping + " + "
	        	        + "CustomFee : " + customFee
	        	        + ") - ("
	        	        + "ThreadValue : " + threadValue + " + "
	        	        + "GiftCardAmount : " + giftCardAmount + " + "
	        	        + "CouponDiscount : " + couponDiscount
	        	        + ") " 
	        	     
	        	);

	        int calcTotalAmount =
	            (giftCardMRP + discountedMRP + giftWrapFee + expressShipping + customFee)
	                    - (threadValue + giftCardAmount + couponDiscount);
	        System.out.println(LINE);
	        System.out.println(
	        	    "calcSaved = ("
	        	        + "TotalMRP : " + totalMRP + " - "
	        	        + "DiscountedMRP : " + discountedMRP
	        	        + ") + "
	        	        + "ThreadValue : " + threadValue + " + "
	        	        + "CouponDiscount : " + couponDiscount
	        	        + "  "
	        	        
	        	);
	        // Calculate Saved: (TotalMRP - DiscountedMRP) + coupon + thread 
	        int calcSaved = (totalMRP - discountedMRP)
	                + threadValue + couponDiscount;

	        System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
	        System.out.println(GREEN + "Calculated Saved Amount: " + calcSaved + RESET);
	        System.out.println(GREEN + "Calculated Total Amount: " + calcTotalAmount + RESET);
	        System.out.println(LINE);

	        // -----------------------------
	        // VALIDATION
	        // -----------------------------
	     // Validation of "You Saved" amount
	        System.out.println(CYAN + "📌 Expected vs Actual Saved Amount:" + RESET);
	        System.out.println(YELLOW + "Expected Saved Amount (UI): " + uiSavedAmount + RESET);
	        System.out.println(YELLOW + "Calculated Saved Amount: " + calcSaved + RESET);

	        if (calcSaved == uiSavedAmount) {
	            System.out.println(GREEN + "✅ Saved Amount MATCHES UI" + RESET);
	        } else {
	            System.out.println(RED + "❌ Saved Amount MISMATCH — UI: " + uiSavedAmount +
	                    " | Calc: " + calcSaved + RESET);

	            Assert.fail("❌ Saved Amount MISMATCH — UI: " + uiSavedAmount +
	                    " | Calc: " + calcSaved);
	        }
	     // Validation of "Total Amount"
	        System.out.println(CYAN + "📌 Expected vs Actual Total Amount:" + RESET);
	        System.out.println(YELLOW + "Expected Total Amount (UI): " + uiTotalAmount + RESET);
	        System.out.println(YELLOW + "Calculated Total Amount: " + calcTotalAmount + RESET);

	        if (calcTotalAmount == uiTotalAmount) {
	            System.out.println(GREEN + "✅ Total Amount MATCHES UI" + RESET);
	        } else {
	            System.out.println(RED + "❌ Total Amount MISMATCH — UI: " + uiTotalAmount +
	                    " | Calc: " + calcTotalAmount + RESET);

	            Assert.fail("❌ Total Amount MISMATCH — UI: " + uiTotalAmount +
	                    " | Calc: " + calcTotalAmount);
	        }

	        System.out.println(LINE);

	    } catch (Exception e) {
	        System.out.println(RED + "❌ ERROR: " + e.getMessage() + RESET);
	    }
	}
	
	
	
	
	
	public void validateRazorpaySummaryCalculation() {

	    String CYAN = "\u001B[36m";
	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String LINE = "────────────────────────────────────────────";
	    
	    
	 // Scroll so the button is fully visible
	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block: 'center'});", placeOrderBtn
	    );

	    // JS Click (cannot be intercepted)
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrderBtn);
	    System.out.println(GREEN + "✅ Clicked Place Order" + RESET);
	    Common.waitForElement(4);
		 // ✅ 1. Switch to Razorpay iframe (you already have this)
		    wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
		            By.xpath("//iframe[contains(@name,'razorpay') or contains(@id,'razorpay') or contains(@src,'razorpay')]")
		    ));
		    System.out.println("✅ Switched to Razorpay iframe");

		    // ✅ 2. Click Continue button
		    wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//button[@data-testid='order-summary-widget-multiple']")
		    )).click();
		    System.out.println("✅ Summary clicked");

	    // ---------------------------
	    // 🔹 READ VALUES FROM UI
	    // ---------------------------
		    Common.waitForElement(2);
	    // Subtotal
	    String subTotalTxt = driver.findElement(By.xpath("//p[span[contains(text(),'Subtotal')]]/span[last()]")).getText();
	    int uiSubtotal = Integer.parseInt(subTotalTxt.replaceAll("[^0-9]", ""));

	    // Discount on price
	    String discountTxt = driver.findElement(By.xpath("(//p[span[contains(text(),'Discount on price')]]/span)[2]")).getText();
	    int uiDiscount = Integer.parseInt(discountTxt.replaceAll("[^0-9]", ""));

	    // Grand Total
	    String grandTotalTxt = driver.findElement(By.xpath("(//p[span[contains(text(),'Grand Total')]]/span)[2]")).getText();
	    int uiGrandTotal = Integer.parseInt(grandTotalTxt.replaceAll("[^0-9]", ""));

	    // ---------------------------------------
	    // 🔹 PRINT UI VALUES
	    // ---------------------------------------
	    System.out.println(LINE);
	    System.out.println(CYAN + "📌 VALUES DISPLAYED IN RAZORPAY SUMMARY" + RESET);
	    System.out.println(YELLOW + "Subtotal UI: " + uiSubtotal + RESET);
	    System.out.println(YELLOW + "Discount on Price UI: " + uiDiscount + RESET);
	    System.out.println(YELLOW + "Grand Total UI: " + uiGrandTotal + RESET);
	    System.out.println(LINE);

	    // ------------------------------------------------------
	    // 🔹 You MUST already have these values from your system:
	    // ------------------------------------------------------
	    System.out.println(YELLOW + "Gift Card MRP: " + giftCardMRP + RESET);
        System.out.println(YELLOW + "Total MRP: " + totalMRP + RESET);
        System.out.println(YELLOW + "Discounted MRP: " + discountedMRP + RESET);
        System.out.println(YELLOW + "Gift Wrap Fee: " + giftWrapFee + RESET);
        System.out.println(YELLOW + "Express Shipping: " + expressShipping + RESET);
        System.out.println(YELLOW + "Customisation Fee: " + customFee + RESET);
        System.out.println(YELLOW + "Thread Value: " + threadValue + RESET);
        System.out.println(YELLOW + "Gift Card Amount Used: " + giftCardAmount + RESET);
        System.out.println(YELLOW + "Coupon Discount: " + couponDiscount + RESET);
        System.out.println(LINE);


	    // ------------------------------------------------------
	    // 🔹 PERFORM CALCULATIONS (As per your formula)
	    // ------------------------------------------------------
        System.out.println(
        	    "calcSubtotal = ("
        	        + "TotalMRP : " + totalMRP + " + "
        	        + "GiftCardMRP : " + giftCardMRP + " + "
        	        + "GiftWrapFee : " + giftWrapFee + " + "
        	        + "CustomFee : " + customFee + " + "
        	        + "ExpressShipping : " + expressShipping
        	        + ")  "
        	        
        	);
	    int calcSubtotal =
	            (totalMRP + giftCardMRP + giftWrapFee + customFee + expressShipping);

	    System.out.println(
	    	    "calcDiscount = ("
	    	        + "TotalMRP : " + totalMRP + " - "
	    	        + "DiscountedMRP : " + discountedMRP
	    	        + ") + "
	    	        + "GiftCardAmount : " + giftCardAmount + " + "
	    	        + "ThreadValue : " + threadValue + " + "
	    	        + "CouponDiscount : " + couponDiscount
	    	        + " "
	    	        
	    	);
	    int calcDiscount =
	            (totalMRP - discountedMRP) + giftCardAmount + threadValue + couponDiscount;
	    System.out.println(
	    	    "calcGrandTotal = "
	    	        + "Subtotal : " + calcSubtotal + " - "
	    	        + "Discount : " + calcDiscount
	    	        + "  "
	    	        
	    	);
	    int calcGrandTotal =
	            calcSubtotal - calcDiscount;
	    System.out.println(LINE);
	    // ------------------------------------------------------
	    // 🔹 PRINT CALCULATION DETAILS CLEARLY
	    // ------------------------------------------------------
	    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

	    System.out.println(YELLOW + "Subtotal Formula: " + RESET);
	    System.out.println("   (" + totalMRP + " + " + giftCardMRP + " + " + giftWrapFee + " + " +
	            customFee + " + " + expressShipping + ")");
	    System.out.println(GREEN + "   = " + calcSubtotal + RESET);

	    System.out.println();

	    System.out.println(YELLOW + "Discount Formula: " + RESET);
	    System.out.println("   (" + totalMRP + " - " + discountedMRP + ") + " +
	            giftCardAmount + " + " + threadValue + " + " + couponDiscount);
	    System.out.println(GREEN + "   = " + calcDiscount + RESET);

	    System.out.println();

	    System.out.println(YELLOW + "Grand Total Formula: " + RESET);
	    System.out.println("   " + calcSubtotal + " - " + calcDiscount);
	    System.out.println(GREEN + "   = " + calcGrandTotal + RESET);
	    System.out.println(LINE);

	 // ------------------------------------------------------
	 // 🔹 VALIDATIONS (with detailed print)
	 // ------------------------------------------------------

	 System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

	 // Subtotal
	 System.out.println(YELLOW + "Subtotal Validation:" + RESET);
	 System.out.println("   Calculated Subtotal = " + calcSubtotal);
	 System.out.println("   UI Subtotal         = " + uiSubtotal);

	 if (calcSubtotal == uiSubtotal) {
		    System.out.println(GREEN + "   ✔ MATCHED" + RESET);
		} else {
		    System.out.println(RED + "   ✘ MISMATCH — UI: " + uiSubtotal +
		            " | Calc: " + calcSubtotal + RESET);

		    Assert.fail("❌ Subtotal MISMATCH — UI: " + uiSubtotal +
		            " | Calc: " + calcSubtotal);
		}

	 System.out.println();

	 // Discount
	 System.out.println(YELLOW + "Discount Validation:" + RESET);
	 System.out.println("   Calculated Discount = " + calcDiscount);
	 System.out.println("   UI Discount         = " + uiDiscount);

	 if (calcDiscount == uiDiscount) {
		    System.out.println(GREEN + "   ✔ MATCHED" + RESET);
		} else {
		    System.out.println(RED + "   ✘ MISMATCH — UI: " + uiDiscount +
		            " | Calc: " + calcDiscount + RESET);

		    Assert.fail("❌ Discount MISMATCH — UI: " + uiDiscount +
		                " | Calc: " + calcDiscount);
		}

	 System.out.println();

	 // Grand Total
	 System.out.println(YELLOW + "Grand Total Validation:" + RESET);
	 System.out.println("   Calculated Grand Total = " + calcGrandTotal);
	 System.out.println("   UI Grand Total         = " + uiGrandTotal);

	 if (calcGrandTotal == uiGrandTotal) {
		    System.out.println(GREEN + "   ✔ MATCHED" + RESET);
		} else {
		    System.out.println(RED + "   ✘ MISMATCH — UI: " + uiGrandTotal +
		            " | Calc: " + calcGrandTotal + RESET);

		    Assert.fail("❌ Grand Total MISMATCH — UI: " + uiGrandTotal +
		                " | Calc: " + calcGrandTotal);

	 System.out.println(LINE);
	}
	
		}
	
	
	int calcTotalAmount;
	int totalMRP_P1;
	int discountedMRP_P1;
	int customFee_P1;
	int giftCardAmount_P1;
	int couponDiscount_P1;
	int threadValue_P1;
	public void validatePriceBreakupDetails() throws InterruptedException {
	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String LINE = "──────────────────────────────────────────────────────────────";

	    Common.waitForElement(2);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(@class,'flex items-center') and contains(@class,'-mr-2')]")
	    )).click();
	    System.out.println("✅ Close clicked");

	    // Click Continue
	    Common.waitForElement(1);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(.,'Continue')]")
	    )).click();
	    System.out.println("✅ Continue clicked");

	    // Enter Pincode
	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.id("zipcode")
	    )).sendKeys("560001");

	    // Enter Name
	    driver.findElement(By.id("name")).sendKeys("Saroj Test");

	    // Enter House / Building
	    driver.findElement(By.id("line1")).sendKeys("Bangalore");

	    // Enter Area / Street
	    driver.findElement(By.id("line2")).sendKeys("bjvhcgfchvbjkn");

	    // Address Submit
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[contains(.,'Continue') and @name='new_shipping_address_cta']")
	    )).click();

	    System.out.println("✅ Address submitted successfully");

	    // Select Netbanking
	    Common.waitForElement(3);
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//span[@data-testid='Netbanking']")
	    )).click();

	    // Select HDFC Bank
	    wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//div[@role='button' and .//span[contains(text(),'HDFC Bank')]])[1]")
	    )).click();

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

	    // Click Success button
	    WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//button[@data-val='S' and normalize-space(text())='Success']")
	    ));
	    successBtn.click();
	    System.out.println(GREEN + "💳 Payment Success clicked" + RESET);

	    Thread.sleep(5000);
	    driver.switchTo().window(mainWindow);
	    System.out.println(GREEN + "🔙 Switched back to main window" + RESET);

	    // Confirm order
	    Thread.sleep(7000);

	    try {
	        WebElement confirmMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Order Confirmed']")
	        ));

	        if (confirmMsg.isDisplayed()) {
	            System.out.println(GREEN + "🎉 Order Confirmed Successfully!" + RESET);
	            WebElement element = driver.findElement(By.cssSelector(".placed_prod_view_details_row"));
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);


	            Common.waitForElement(2);
	            wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails));
	            click(viewOrderDetails);
	            System.out.println(GREEN + "🧾 Clicked View Order Details" + RESET);
	            Common.waitForElement(2);
	           
	    	    driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();
	    	    Common.waitForElement(2);

	    	    // Helper: returns value or 0 if row missing
	    	    Function<String, Integer> getValue = (label) -> {
	    	        try {
	    	            WebElement ele = driver.findElement(By.xpath(
	    	                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
	    	                "/following-sibling::div[@class='price_details_pair']"
	    	            ));
	    	            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
	    	        } catch (Exception e) { return 0; }
	    	    };

	    	    Common.waitForElement(1);

	    	    // -------------------------------
	    	    // 🔹 FETCH UI VALUES 
	    	    // -------------------------------
	    	     totalMRP_P1         = getValue.apply("Total MRP");
	    	      discountedMRP_P1    = getValue.apply("Discounted MRP");
	    	      customFee_P1        = getValue.apply("Customisation fee");
	    	      giftCardAmount_P1   = getValue.apply("Gift Card Applied");
	    	     couponDiscount_P1   = getValue.apply("Coupon Discount");
	    	      threadValue_P1      = getValue.apply("Applied Threads");

	    	    int uiYouSaved       = getValue.apply("You Saved");
	    	    int uiTotalAmount    = getValue.apply("Total Amount");

	    	    // -------------------------------
	    	    // 🔹 PRINT UI VALUES
	    	    // -------------------------------
	    	    System.out.println(LINE);
	    	    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

	    	    System.out.println(YELLOW + "Total MRP:            " + totalMRP_P1 + RESET);
	    	    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP_P1 + RESET);
	    	    System.out.println(YELLOW + "Customisation Fee:    " + customFee_P1 + RESET);
	    	    System.out.println(YELLOW + "Gift Card Applied:    " + giftCardAmount_P1 + RESET);
	    	    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount_P1 + RESET);
	    	    System.out.println(YELLOW + "Applied Threads:      " + threadValue_P1 + RESET);
	    	    System.out.println(YELLOW + "You Saved (UI):       " + uiYouSaved + RESET);
	    	    System.out.println(YELLOW + "Total Amount (UI):    " + uiTotalAmount + RESET);
	    	    System.out.println(LINE);

	    	    // -------------------------------
	    	    // 🔹 CALCULATIONS
	    	    // -------------------------------
	    	    
	    	    System.out.println(
	    	    	    "calcTotalAmount = ("
	    	    	        + "DiscountedMRP_P1 : " + discountedMRP_P1 + " + "
	    	    	        + "CustomFee_P1 : " + customFee_P1
	    	    	        + ") - ("
	    	    	        + "ThreadValue_P1 : " + threadValue_P1 + " + "
	    	    	        + "GiftCardAmount_P1 : " + giftCardAmount_P1 + " + "
	    	    	        + "CouponDiscount_P1 : " + couponDiscount_P1
	    	    	        + ") "
	    	    	        
	    	    	);
	    	     calcTotalAmount =
	    	            (discountedMRP_P1 + customFee_P1)
	    	            - (threadValue_P1 + giftCardAmount_P1 + couponDiscount_P1);
	    	     
	    	     
	    	    System.out.println(
	    	    	    "calcYouSaved = "
	    	    	        + "TotalMRP_P1 : " + totalMRP_P1 + " - "
	    	    	        + "Total Amount : " + calcTotalAmount
	    	    	        + ""
	    	    	        
	    	    	);
	    	    int calcYouSaved =
	    	            totalMRP_P1 - calcTotalAmount;
	    

	    	    // -------------------------------
	    	    // 🔹 PRINT CALCULATIONS
	    	    // -------------------------------
	    	    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

	    	    // YOU SAVED
	    	    System.out.println(YELLOW + "You Saved Formula:" + RESET);
	    	    System.out.println("   " + totalMRP_P1 + " - " + calcTotalAmount +"");
	    	    System.out.println(GREEN + "   = " + calcYouSaved + RESET);

	    	    System.out.println();

	    	    // TOTAL AMOUNT
	    	    System.out.println(YELLOW + "Total Amount Formula:" + RESET);
	    	    System.out.println("   (" + discountedMRP_P1 + " + " + customFee_P1 + ")" +
	    	            " - (" + threadValue_P1 + " + " + giftCardAmount_P1 + " + " + couponDiscount_P1 + ")");
	    	    System.out.println(GREEN + "   = " + calcTotalAmount + RESET);

	    	    System.out.println(LINE);

	    	    // -------------------------------
	    	    // 🔹 VALIDATIONS
	    	    // -------------------------------
	    	    System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

	    	    // YOU SAVED
	    	    System.out.println(YELLOW + "You Saved Validation:" + RESET);
	    	    System.out.println("   Calculated = " + calcYouSaved);
	    	    System.out.println("   UI Value   = " + uiYouSaved);

	    	    if (calcYouSaved == uiYouSaved) {
	    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
	    	    } else {
	    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiYouSaved +
	    	                " | Calc: " + calcYouSaved + RESET);
	    	        Assert.fail("❌ You Saved MISMATCH!");
	    	    }

	    	    System.out.println();

	    	    // TOTAL AMOUNT
	    	    System.out.println(YELLOW + "Total Amount Validation:" + RESET);
	    	    System.out.println("   Calculated = " + calcTotalAmount);
	    	    System.out.println("   UI Value   = " + uiTotalAmount);

	    	    if (calcTotalAmount == uiTotalAmount) {
	    	        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
	    	    } else {
	    	        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiTotalAmount +
	    	                " | Calc: " + calcTotalAmount + RESET);
	    	        Assert.fail("❌ Total Amount MISMATCH!");
	    	    }

	    	    System.out.println(LINE);          
	            
	         
	} // END OF METHOD
	    } catch (Exception e) {
	        System.out.println(RED + "❌ ERROR DURING ORDER CONFIRMATION: " + e.getMessage() + RESET);
	    }


	    	    
	    	}   
	   
	
	
	
	
	public void validateOrderSummary() {

	    String CYAN = "\u001B[36m";
	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String LINE = "──────────────────────────────────────────────";
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    
        // =============================
        // STEP 1: UI Values
        // =============================
	    
	    driver.findElement(By.xpath("(//div[contains(@class,'popup_containers_cls_btn')])[5]")).click();
	    Common.waitForElement(1);
       js.executeScript("window.scrollBy(0, 500);");
	 // Payable Amount - only first text node
	    Common.waitForElement(2);
	    WebElement amountDiv = driver.findElement(By.cssSelector(".prod_order_amount_value"));
	    String fullText = amountDiv.getText().trim();

	    // Remove the "You have Saved …" part completely
	    String cleaned = fullText.replaceAll("You have Saved.*", "").trim();

	    // Now cleaned = "₹300"

	    int uiPayableAmount = Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
	    System.out.println("Order Value: " + uiPayableAmount);
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
	    	

	    	// Saved Amount
	    	String savedText = driver.findElement(
	    	        By.cssSelector(".prod_order_amount_value span")
	    	).getText().trim();

	    	int uiSavedAmount = parseMoney(savedText);
        int uiGiftWrapFee = parseMoney(driver.findElement(By.cssSelector(".prod_order_gift_wrap_fee_value")).getText());
        int uiShippingCharges = safeGet.apply("//div[normalize-space(text())='Shipping Charges']/following::div[1]");
    //   int uiShippingCharges = parseMoney(driver.findElement(By.xpath("//div[text()=' Shipping Charges ']/following::div[1]")).getText());
        int uiTotalOrderValue = parseMoney(driver.findElement(By.xpath("//div[text()=' Total Order Value ']/following::div[1]")).getText());

        // =============================
        // STEP 2: Print Backend Values
        // =============================
        System.out.println(CYAN + "📌 BACKEND / VARIABLES YOU STORED EARLIER(Price Break Up)" + RESET);
        System.out.println(YELLOW + "Total MRP:            " + totalMRP_P1 + RESET);
	    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP_P1 + RESET);
	    System.out.println(YELLOW + "Customisation Fee:    " + customFee_P1 + RESET);
	    System.out.println(YELLOW + "Gift Card Applied:    " + giftCardAmount_P1 + RESET);
	    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount_P1 + RESET);
	    System.out.println(YELLOW + "Applied Threads:      " + threadValue_P1 + RESET);
        System.out.println(LINE);

        System.out.println(CYAN + "📌 UI Values from Order Summary Page" + RESET);
        System.out.println(YELLOW + "Payable Amount (UI): " + uiPayableAmount + RESET);
        System.out.println(YELLOW + "You Saved (UI): " + uiSavedAmount + RESET);
        System.out.println(YELLOW + "Gift Wrap Fee (UI): " + uiGiftWrapFee + RESET);
          System.out.println(YELLOW + "Shipping Charges (UI): " + uiShippingCharges + RESET);
        System.out.println(YELLOW + "Total Order Value (UI): " + uiTotalOrderValue + RESET);
        System.out.println(LINE);

        // =============================
        // STEP 3: Calculations
        // =============================
        System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
        System.out.println(GREEN + "Payable Amount Formula: Total Amount(Price Break Up):" + calcTotalAmount + "+ Gift Card(Price Break Up):"+ giftCardAmount_P1 +" " + RESET);
        int calcPayableAmount =
        		calcTotalAmount + giftCardAmount_P1;
        System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount + RESET);
        System.out.println(LINE);
        System.out.println(GREEN + "Total Order Value Formula: Payable Amount:" + calcPayableAmount + "+ Gift Wrap Fee:"+ uiGiftWrapFee +"+ Shipping Charges Fee:"+ uiShippingCharges +" " + RESET);
        int calcTotalOrderValue =
             (calcPayableAmount + uiGiftWrapFee + uiShippingCharges);
        System.out.println(YELLOW + "Calculated Total Order Value: " + calcTotalOrderValue + RESET);
        System.out.println(LINE);
        System.out.println(GREEN + "You Saved  Formula: Total Amount(Price Break Up):" + calcTotalAmount + "+ Customized Fee(Price Break Up):"+ customFee_P1 +" " + RESET);
        int calcYouSaved =
                (totalMRP_P1 + customFee_P1)
                - calcPayableAmount;
        System.out.println(YELLOW + "You saved  Amount: " + calcYouSaved + RESET);
//        int calcTotalOrderValue =
//                (discountedMRP + giftWrapFee + expressShipping + customFee)
//                        - (threadValue + couponDiscount);

//        System.out.println(GREEN + "Formula: (DiscountedMRP + Wrap + Express + Custom) - (Thread + Coupon)" + RESET);
 //       System.out.println(GREEN + "Formula: (calcTotalAmount + giftWrapFee + expressShipping)" + RESET);
//        System.out.println(YELLOW + "Calculated Total Order Value: " + calcTotalOrderValue + RESET);
//        System.out.println(YELLOW + "You saved  Amount: " + calcYouSaved + RESET);
//        int calcPayableAmount =
//                calcTotalOrderValue - (giftWrapFee + expressShipping);
//
 //       System.out.println(GREEN + "Formula: TotalOrderValue - (Wrap + Express)" + RESET);
        
 //       System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount + RESET);

        System.out.println(LINE);

        // =============================
        // STEP 4: VALIDATION
        // =============================
        if (calcTotalOrderValue == uiTotalOrderValue) {
            System.out.println(GREEN + "✅ TOTAL ORDER VALUE MATCHED UI" + RESET);
        } else {
            System.out.println(RED + "❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                    uiTotalOrderValue + " | Calc: " + calcTotalOrderValue + RESET);

            Assert.fail("❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                    uiTotalOrderValue + " | Calc: " + calcTotalOrderValue);
        }
        
     // ---- YOUSAVED AMOUNT ----
        if (calcYouSaved == uiSavedAmount) {
            System.out.println(GREEN + "✅ YOUSAVED AMOUNT MATCHED UI" + RESET);
        } else {
            System.out.println(RED + "❌ YOUSAVED AMOUNT MISMATCH — UI: " +
            		uiSavedAmount + " | Calc: " + calcYouSaved + RESET);

            Assert.fail("❌ YOUSAVED AMOUNT MISMATCH — UI: " +
            		uiSavedAmount + " | Calc: " + calcYouSaved);
        }

        // ---- PAYABLE AMOUNT ----
        if (calcPayableAmount == uiPayableAmount) {
            System.out.println(GREEN + "✅ PAYABLE AMOUNT MATCHED UI" + RESET);
        } else {
            System.out.println(RED + "❌ PAYABLE AMOUNT MISMATCH — UI: " +
                    uiPayableAmount + " | Calc: " + calcTotalAmount + RESET);

            Assert.fail("❌ PAYABLE AMOUNT MISMATCH — UI: " +
                    uiPayableAmount + " | Calc: " + calcTotalAmount);
        }

        System.out.println(LINE);
    }  
	
	
	// Helper to extract ₹ values → int
		private int parseMoney(String text) {
		    return Integer.parseInt(text.replaceAll("[^0-9]", ""));
		}
	
	
	
		
public void placeOrderAndCheckOrderConfirmation() throws InterruptedException {

	String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String CYAN = "\u001B[36m";
    String RESET = "\u001B[0m";
    String LINE = "──────────────────────────────────────────────────────────────";

    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(@class,'flex items-center') and contains(@class,'-mr-2')]")
    )).click();
    System.out.println("✅ Close clicked");

    // Click Continue
    Common.waitForElement(1);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(.,'Continue')]")
    )).click();
    System.out.println("✅ Continue clicked");

    // Enter Pincode
    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.id("zipcode")
    )).sendKeys("560001");

    // Enter Name
    driver.findElement(By.id("name")).sendKeys("Saroj Test");

    // Enter House / Building
    driver.findElement(By.id("line1")).sendKeys("Bangalore");

    // Enter Area / Street
    driver.findElement(By.id("line2")).sendKeys("bjvhcgfchvbjkn");

    // Address Submit
    Common.waitForElement(3);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(.,'Continue') and @name='new_shipping_address_cta']")
    )).click();

    System.out.println("✅ Address submitted successfully");

    // Select Netbanking
    Common.waitForElement(3);
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[@data-testid='Netbanking']")
    )).click();

    // Select HDFC Bank
    wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//div[@role='button' and .//span[contains(text(),'HDFC Bank')]])[1]")
    )).click();

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

    // Click Success button
    WebElement successBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[@data-val='S' and normalize-space(text())='Success']")
    ));
    successBtn.click();
    System.out.println(GREEN + "💳 Payment Success clicked" + RESET);

    Thread.sleep(5000);
    driver.switchTo().window(mainWindow);
    System.out.println(GREEN + "🔙 Switched back to main window" + RESET);

    // Confirm order
    Thread.sleep(7000);

		    // Wait for confirmed message
		    WebElement confirmMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
		            By.xpath("//h5[@class='checkout_success_heading' and normalize-space()='Order Confirmed']")));

		    if (confirmMsg.isDisplayed()) {

		        // Scroll to "Placed Order Details Section"
		        WebElement element = driver.findElement(By.cssSelector(".placed_prod_view_details_row"));
		        ((JavascriptExecutor) driver).executeScript(
		                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);

		        Common.waitForElement(1);

		        // Click View Order Details
		        wait.until(ExpectedConditions.elementToBeClickable(viewOrderDetails)).click();

		        Common.waitForElement(1);

		       
		    }
		}	

		
int calcTotalAmount1;
int totalMRP1;
int discountedMRP1;
int customFee1;
int giftCardAmount1;
int couponDiscount1;
int threadValue1;
int calcPayableAmount1;	
public void validatePriceBreakupDetails_P1() {
	 String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String BLUE   = "\u001B[34m";
	    String RESET  = "\u001B[0m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;
	    Common.waitForElement(2);
	    
	    // Open Price Breakup
        driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	 // Helper: returns value or 0 if row missing
    Function<String, Integer> getValue = (label) -> {
        try {
            WebElement ele = driver.findElement(By.xpath(
                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
                "/following-sibling::div[@class='price_details_pair']"
            ));
            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
        } catch (Exception e) { return 0; }
    };

    Common.waitForElement(1);

    // -------------------------------
    // 🔹 FETCH UI VALUES 
    // -------------------------------
      totalMRP1         = getValue.apply("Total MRP");
      discountedMRP1    = getValue.apply("Discounted MRP");
      customFee1        = getValue.apply("Customisation fee");
      giftCardAmount1   = getValue.apply("Gift Card Applied");
      couponDiscount1   = getValue.apply("Coupon Discount");
      threadValue1      = getValue.apply("Applied Threads");

    int uiYouSaved       = getValue.apply("You Saved");
    int uiTotalAmount    = getValue.apply("Total Amount");

    // -------------------------------
    // 🔹 PRINT UI VALUES
    // -------------------------------
    System.out.println(LINE);
    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

    System.out.println(YELLOW + "Total MRP:            " + totalMRP1 + RESET);
    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP1 + RESET);
    System.out.println(YELLOW + "Customisation Fee:    " + customFee1 + RESET);
    System.out.println(YELLOW + "Gift Card Applied:    " + giftCardAmount1 + RESET);
    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount1 + RESET);
    System.out.println(YELLOW + "Applied Threads:      " + threadValue1 + RESET);
    System.out.println(YELLOW + "You Saved (UI):       " + uiYouSaved + RESET);
    System.out.println(YELLOW + "Total Amount (UI):    " + uiTotalAmount + RESET);
    System.out.println(LINE);

    // -------------------------------
    // 🔹 CALCULATIONS
    // -------------------------------
    calcTotalAmount1 =
            (discountedMRP1 + customFee1)
            - (threadValue1 + giftCardAmount1 + couponDiscount1);
    
    int calcYouSaved =
            totalMRP1 - calcTotalAmount1;

    calcPayableAmount1 =
  		  calcTotalAmount1 + giftCardAmount1;

    // -------------------------------
    // 🔹 PRINT CALCULATIONS
    // -------------------------------
    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

    // YOU SAVED
    System.out.println(YELLOW + "You Saved Formula:" + RESET);
    System.out.println("   (" + totalMRP1 + " - " + calcTotalAmount1);
    System.out.println(GREEN + "   = " + calcYouSaved + RESET);

    System.out.println();

    // TOTAL AMOUNT
    System.out.println(YELLOW + "Total Amount Formula:" + RESET);
    System.out.println("   (" + discountedMRP1 + " + " + customFee1 + ")" +
            " - (" + threadValue1 + " + " + giftCardAmount1 + " + " + couponDiscount1 + ")");
    System.out.println(GREEN + "   = " + calcTotalAmount1 + RESET);

    System.out.println(LINE);

    // -------------------------------
    // 🔹 VALIDATIONS
    // -------------------------------
    System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

    // YOU SAVED
    System.out.println(YELLOW + "You Saved Validation:" + RESET);
    System.out.println("   Calculated = " + calcYouSaved);
    System.out.println("   UI Value   = " + uiYouSaved);

    if (calcYouSaved == uiYouSaved) {
        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    } else {
        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiYouSaved +
                " | Calc: " + calcYouSaved + RESET);
        Assert.fail("❌ You Saved MISMATCH!");
    }

    System.out.println();

    // TOTAL AMOUNT
    System.out.println(YELLOW + "Total Amount Validation:" + RESET);
    System.out.println("   Calculated = " + calcTotalAmount1);
    System.out.println("   UI Value   = " + uiTotalAmount);

    if (calcTotalAmount1 == uiTotalAmount) {
        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    } else {
        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiTotalAmount +
                " | Calc: " + calcTotalAmount1 + RESET);
        Assert.fail("❌ Total Amount MISMATCH!");
    }

    System.out.println(LINE);
    
    
}	
		


int calcTotalAmount2;
int totalMRP2;
int discountedMRP2;
int customFee2;
int giftCardAmount2;
int couponDiscount2;
int threadValue2;
public void validatePriceBreakupDetails_P2() {
	 String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String YELLOW = "\u001B[33m";
	    String CYAN   = "\u001B[36m";
	    String BLUE   = "\u001B[34m";
	    String RESET  = "\u001B[0m";

	    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;
	    Common.waitForElement(2);
	    
	    // Open Price Breakup
        driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();

	 // Helper: returns value or 0 if row missing
    Function<String, Integer> getValue = (label) -> {
        try {
            WebElement ele = driver.findElement(By.xpath(
                "//div[@class='price_details_key' and normalize-space(text())='" + label + "']" +
                "/following-sibling::div[@class='price_details_pair']"
            ));
            return Integer.parseInt(ele.getText().replaceAll("[^0-9]", ""));
        } catch (Exception e) { return 0; }
    };

    Common.waitForElement(1);

    // -------------------------------
    // 🔹 FETCH UI VALUES 
    // -------------------------------
      totalMRP2         = getValue.apply("Total MRP");
      discountedMRP2    = getValue.apply("Discounted MRP");
      customFee2        = getValue.apply("Customisation fee");
      giftCardAmount2   = getValue.apply("Gift Card Applied");
     couponDiscount2   = getValue.apply("Coupon Discount");
      threadValue2      = getValue.apply("Applied Threads");

    int uiYouSaved       = getValue.apply("You Saved");
    int uiTotalAmount    = getValue.apply("Total Amount");

    // -------------------------------
    // 🔹 PRINT UI VALUES
    // -------------------------------
    System.out.println(LINE);
    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

    System.out.println(YELLOW + "Total MRP:            " + totalMRP2 + RESET);
    System.out.println(YELLOW + "Discounted MRP:       " + discountedMRP2 + RESET);
    System.out.println(YELLOW + "Customisation Fee:    " + customFee2 + RESET);
    System.out.println(YELLOW + "Gift Card Applied:    " + giftCardAmount2 + RESET);
    System.out.println(YELLOW + "Coupon Discount:      " + couponDiscount2 + RESET);
    System.out.println(YELLOW + "Applied Threads:      " + threadValue2 + RESET);
    System.out.println(YELLOW + "You Saved (UI):       " + uiYouSaved + RESET);
    System.out.println(YELLOW + "Total Amount (UI):    " + uiTotalAmount + RESET);
    System.out.println(LINE);

    // -------------------------------
    // 🔹 CALCULATIONS
    // -------------------------------
    calcTotalAmount2 =
            (discountedMRP2 + customFee2)
            - (threadValue2 + giftCardAmount2 + couponDiscount2);
    
    int calcYouSaved =
            totalMRP2 - calcTotalAmount2;

     

    // -------------------------------
    // 🔹 PRINT CALCULATIONS
    // -------------------------------
    System.out.println(CYAN + "🧮 DETAILED CALCULATIONS" + RESET);

    // YOU SAVED
    System.out.println(YELLOW + "You Saved Formula:" + RESET);
    System.out.println("   (" + totalMRP2 + " - " + calcTotalAmount2 );
    System.out.println(GREEN + "   = " + calcYouSaved + RESET);

    System.out.println();

    // TOTAL AMOUNT
    System.out.println(YELLOW + "Total Amount Formula:" + RESET);
    System.out.println("   (" + discountedMRP2 + " + " + customFee2 + ")" +
            " - (" + threadValue2 + " + " + giftCardAmount2 + " + " + couponDiscount2 + ")");
    System.out.println(GREEN + "   = " + calcTotalAmount2 + RESET);

    System.out.println(LINE);

    // -------------------------------
    // 🔹 VALIDATIONS
    // -------------------------------
    System.out.println(CYAN + "🔍 FINAL VALIDATION RESULTS" + RESET);

    // YOU SAVED
    System.out.println(YELLOW + "You Saved Validation:" + RESET);
    System.out.println("   Calculated = " + calcYouSaved);
    System.out.println("   UI Value   = " + uiYouSaved);

    if (calcYouSaved == uiYouSaved) {
        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    } else {
        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiYouSaved +
                " | Calc: " + calcYouSaved + RESET);
        Assert.fail("❌ You Saved MISMATCH!");
    }

    System.out.println();

    // TOTAL AMOUNT
    System.out.println(YELLOW + "Total Amount Validation:" + RESET);
    System.out.println("   Calculated = " + calcTotalAmount2);
    System.out.println("   UI Value   = " + uiTotalAmount);

    if (calcTotalAmount2 == uiTotalAmount) {
        System.out.println(GREEN + "   ✔ MATCHED" + RESET);
    } else {
        System.out.println(RED + "   ✘ MISMATCH — UI: " + uiTotalAmount +
                " | Calc: " + calcTotalAmount2 + RESET);
        Assert.fail("❌ Total Amount MISMATCH!");
    }

    System.out.println(LINE);
    
    
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

public void validateOrderSummaryForTwoProduct_P1() {

    String CYAN = "\u001B[36m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String LINE = "──────────────────────────────────────────────";
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    // =============================
    // STEP 1: UI Values
    // =============================
    
   js.executeScript("window.scrollBy(0, 500);");
 // Payable Amount - only first text node
    Common.waitForElement(2);
    WebElement amountDiv = driver.findElement(By.cssSelector(".prod_order_amount_value"));
    String fullText = amountDiv.getText().trim();

    // Remove the "You have Saved …" part completely
    String cleaned = fullText.replaceAll("You have Saved.*", "").trim();

    // Now cleaned = "₹300"

    int uiPayableAmount = Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
//    System.out.println("Order Value: " + uiPayableAmount);
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
    	

    	// Saved Amount
    	String savedText = driver.findElement(
    	        By.cssSelector(".prod_order_amount_value span")
    	).getText().trim();

    	int uiSavedAmount = parseMoney(savedText);
    int uiGiftWrapFee = parseMoney(driver.findElement(By.cssSelector(".prod_order_gift_wrap_fee_value")).getText());
    int uiShippingCharges = safeGet.apply("//div[normalize-space(text())='Shipping Charges']/following::div[1]");
  // int uiShippingCharges = parseMoney(driver.findElement(By.xpath("//div[text()=' Shipping Charges ']/following::div[1]")).getText());
    int uiTotalOrderValue = parseMoney(driver.findElement(By.xpath("//div[text()=' Total Order Value ']/following::div[1]")).getText());

    // =============================
    // STEP 2: Print Backend Values
    // =============================
    System.out.println(CYAN + "📌 BACKEND / VARIABLES YOU STORED EARLIER" + RESET);
    System.out.println(LINE);
    System.out.println(CYAN + "📌 PRICE DETAILS DISPLAYED IN UI FROM PRICE BREAK UP" + RESET);

    System.out.println(YELLOW + "Total MRP1:            " + totalMRP1 + RESET);
    System.out.println(YELLOW + "Discounted MRP1:       " + discountedMRP1 + RESET);
    System.out.println(YELLOW + "Customisation Fee1:    " + customFee1 + RESET);
    System.out.println(YELLOW + "Gift Card Applied1:    " + giftCardAmount1 + RESET);
    System.out.println(YELLOW + "Coupon Discount1:      " + couponDiscount1 + RESET);
    System.out.println(YELLOW + "Applied Threads1:      " + threadValue1 + RESET);
    System.out.println(LINE);

    System.out.println(CYAN + "📌 UI Values from Order Summary Page" + RESET);
    System.out.println(YELLOW + "Payable Amount (UI): " + uiPayableAmount + RESET);
    System.out.println(YELLOW + "You Saved (UI): " + uiSavedAmount + RESET);
    System.out.println(YELLOW + "Gift Wrap Fee (UI): " + uiGiftWrapFee + RESET);
      System.out.println(YELLOW + "Shipping Charges (UI): " + uiShippingCharges + RESET);
    System.out.println(YELLOW + "Total Order Value (UI): " + uiTotalOrderValue + RESET);
    System.out.println(LINE);

    // =============================
    // STEP 3: Calculations
    // =============================
    System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
//   calcPayableAmount1 =
//		  calcTotalAmount1 + giftCardAmount1;
  
    int calcTotalOrderValue =
         (calcPayableAmount1 + calcPayableAmount2 + uiGiftWrapFee + uiShippingCharges);
    
    int calcYouSaved1 =
            (totalMRP1 + customFee1)
            - calcPayableAmount1;

//    int calcTotalOrderValue =
//            (discountedMRP + giftWrapFee + expressShipping + customFee)
//                    - (threadValue + couponDiscount);

//    System.out.println(GREEN + "Formula: (DiscountedMRP + Wrap + Express + Custom) - (Thread + Coupon)" + RESET);
 //   System.out.println(GREEN + "Formula: (calcTotalAmount + giftWrapFee + expressShipping)" + RESET);
    System.out.println(YELLOW + "Calculated Total Order Value: " + calcTotalOrderValue + RESET);
    System.out.println(YELLOW + "Calculated YouSaved Amount: " + calcYouSaved1 + RESET);
    System.out.println(YELLOW + "Calculated Payable  Amount: " + calcPayableAmount1 + RESET);
//    int calcPayableAmount =
//            calcTotalOrderValue - (giftWrapFee + expressShipping);
//
//    System.out.println(GREEN + "Formula: TotalOrderValue - (Wrap + Express)" + RESET);
    
//    System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount + RESET);

    System.out.println(LINE);

    // =============================
    // STEP 4: VALIDATION
    // =============================
    if (calcTotalOrderValue == uiTotalOrderValue) {
        System.out.println(GREEN + "✅ TOTAL ORDER VALUE MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                uiTotalOrderValue + " | Calc: " + calcTotalOrderValue + RESET);

        Assert.fail("❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                uiTotalOrderValue + " | Calc: " + calcTotalOrderValue);
    }
 // ---- YOUSAVED AMOUNT ----
    if (calcYouSaved1 == uiSavedAmount) {
        System.out.println(GREEN + "✅ YOUSAVED AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved1 + RESET);

        Assert.fail("❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved1);
    }

    // ---- PAYABLE AMOUNT ----
    if (calcPayableAmount1 == uiPayableAmount) {
        System.out.println(GREEN + "✅ PAYABLE AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ PAYABLE AMOUNT MISMATCH — UI: " +
                uiPayableAmount + " | Calc: " + calcPayableAmount1 + RESET);

        Assert.fail("❌ PAYABLE AMOUNT MISMATCH — UI: " +
                uiPayableAmount + " | Calc: " + calcPayableAmount1);
    }

    System.out.println(LINE);
}  
int calcPayableAmount2;
public void validateOrderSummaryForTwoProduct_P2() {

    String CYAN = "\u001B[36m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String LINE = "──────────────────────────────────────────────";
    JavascriptExecutor js = (JavascriptExecutor) driver;
    
    // =============================
    // STEP 1: UI Values
    // =============================
    
   js.executeScript("window.scrollBy(0, 500);");
 // Payable Amount - only first text node
    Common.waitForElement(2);
    WebElement amountDiv = driver.findElement(By.cssSelector(".prod_order_amount_value"));
    String fullText = amountDiv.getText().trim();

    // Remove the "You have Saved …" part completely
    String cleaned = fullText.replaceAll("You have Saved.*", "").trim();

    // Now cleaned = "₹300"

    int uiPayableAmount = Integer.parseInt(cleaned.replaceAll("[^0-9]", ""));
   // System.out.println("Order Value: " + uiPayableAmount);

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

    	// Saved Amount
    	String savedText = driver.findElement(
    	        By.cssSelector(".prod_order_amount_value span")
    	).getText().trim();

    	int uiSavedAmount = parseMoney(savedText);
    int uiGiftWrapFee = parseMoney(driver.findElement(By.cssSelector(".prod_order_gift_wrap_fee_value")).getText());
    int uiShippingCharges = safeGet.apply("//div[normalize-space(text())='Shipping Charges']/following::div[1]");
    int uiTotalOrderValue = parseMoney(driver.findElement(By.xpath("//div[text()=' Total Order Value ']/following::div[1]")).getText());

    // =============================
    // STEP 2: Print Backend Values
    // =============================
    System.out.println(CYAN + "📌 BACKEND / VARIABLES YOU STORED EARLIER" + RESET);

    System.out.println(YELLOW + "Total MRP2:            " + totalMRP2 + RESET);
    System.out.println(YELLOW + "Discounted MRP2:       " + discountedMRP2 + RESET);
    System.out.println(YELLOW + "Customisation Fee2:    " + customFee2 + RESET);
    System.out.println(YELLOW + "Gift Card Applied2:    " + giftCardAmount2 + RESET);
    System.out.println(YELLOW + "Coupon Discount2:      " + couponDiscount2 + RESET);
    System.out.println(YELLOW + "Applied Threads2:      " + threadValue2 + RESET);
    System.out.println(LINE);

    System.out.println(CYAN + "📌 UI Values from Order Summary Page" + RESET);
    System.out.println(YELLOW + "Payable Amount (UI): " + uiPayableAmount + RESET);
    System.out.println(YELLOW + "You Saved (UI): " + uiSavedAmount + RESET);
    System.out.println(YELLOW + "Gift Wrap Fee (UI): " + uiGiftWrapFee + RESET);
      System.out.println(YELLOW + "Shipping Charges (UI): " + uiShippingCharges + RESET);
    System.out.println(YELLOW + "Total Order Value (UI): " + uiTotalOrderValue + RESET);
    System.out.println(LINE);

    // =============================
    // STEP 3: Calculations
    // =============================
    System.out.println(CYAN + "🧮 Performing Calculations..." + RESET);
     calcPayableAmount2 =
  		  calcTotalAmount2 + giftCardAmount2;
    
      int calcTotalOrderValue =
           (calcPayableAmount2 + calcPayableAmount1 + uiGiftWrapFee + uiShippingCharges);
      
      int calcYouSaved2 =
              (totalMRP2 + customFee2)
              - calcPayableAmount2;
      
 

//    int calcTotalOrderValue =
//            (discountedMRP + giftWrapFee + expressShipping + customFee)
//                    - (threadValue + couponDiscount);

//    System.out.println(GREEN + "Formula: (DiscountedMRP + Wrap + Express + Custom) - (Thread + Coupon)" + RESET);
    System.out.println(GREEN + "Calculated Payable Amount From First Product: "+ calcPayableAmount1 + RESET);
    System.out.println(YELLOW + "Calculated Total Order Value: " + calcTotalOrderValue + RESET);
    System.out.println(YELLOW + "Calculated YouSaved Amount: " + calcYouSaved2 + RESET);
    System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount2 + RESET);
//    int calcPayableAmount =
//            calcTotalOrderValue - (giftWrapFee + expressShipping);
//
//    System.out.println(GREEN + "Formula: TotalOrderValue - (Wrap + Express)" + RESET);
    
//    System.out.println(YELLOW + "Calculated Payable Amount: " + calcPayableAmount + RESET);

    System.out.println(LINE);

    // =============================
    // STEP 4: VALIDATION
    // =============================
    if (calcTotalOrderValue == uiTotalOrderValue) {
        System.out.println(GREEN + "✅ TOTAL ORDER VALUE MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                uiTotalOrderValue + " | Calc: " + calcTotalOrderValue + RESET);

        Assert.fail("❌ TOTAL ORDER VALUE MISMATCH — UI: " +
                uiTotalOrderValue + " | Calc: " + calcTotalOrderValue);
    }
    
 // ---- YOUSAVED AMOUNT ----
    if (calcYouSaved2 == uiSavedAmount) {
        System.out.println(GREEN + "✅ YOUSAVED AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved2 + RESET);

        Assert.fail("❌ YOUSAVED AMOUNT MISMATCH — UI: " +
        		uiSavedAmount + " | Calc: " + calcYouSaved2);
    }

    // ---- PAYABLE AMOUNT ----
    if (calcPayableAmount2 == uiPayableAmount) {
        System.out.println(GREEN + "✅ PAYABLE AMOUNT MATCHED UI" + RESET);
    } else {
        System.out.println(RED + "❌ PAYABLE AMOUNT MISMATCH — UI: " +
                uiPayableAmount + " | Calc: " + calcPayableAmount2 + RESET);

        Assert.fail("❌ PAYABLE AMOUNT MISMATCH — UI: " +
                uiPayableAmount + " | Calc: " + calcPayableAmount2);
    }

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
	    calcCouponRaw = ((double) discountedMRP1 / (double) discountedMRP) * couponDiscount;
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
	if (discountedMRP1 > 0) {
	    calcCouponRaw = ((double) discountedMRP2 / (double) discountedMRP) * couponDiscount;
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


public void verifyThreadSplit_P1() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 THREAD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If threadValue2 is ZERO
    // ============================================
    if (threadValue == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING THREAD SPLIT VALIDATION — UI Thread Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any thread validation
    }

    // ============================================
    // THREAD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Thread Split Calculation..." + RESET);
    System.out.println(GREEN +
            "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalThreadAmount"
            + RESET);

    double calcThreadRaw = 0.0;

    if (discountedMRP > 0) {
        calcThreadRaw = ((double) discountedMRP1 / (double) discountedMRP) * threadValue;
    }

    int calcThreadFloor = (int) Math.floor(calcThreadRaw);
    int calcThreadCeil  = (int) Math.ceil(calcThreadRaw);

    System.out.println(YELLOW + "Calculated Thread Raw:      " + calcThreadRaw + RESET);
    System.out.println(YELLOW + "Calculated Thread Floor:    " + calcThreadFloor + RESET);
    System.out.println(YELLOW + "Calculated Thread Ceil:     " + calcThreadCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Thread Value:            " + threadValue1 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (threadValue1 == calcThreadFloor || threadValue1 == calcThreadCeil) {

        System.out.println(GREEN +
                "✅ THREAD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
                RESET);

    } else {

        System.out.println(RED +
                "❌ THREAD DISTRIBUTION MISMATCH — UI: " + threadValue1 +
                " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil +
                RESET);

        Assert.fail("❌ THREAD DISTRIBUTION MISMATCH — UI: " +
        		threadValue1 + " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil);
    }

    System.out.println(LINE);
}
		



public void verifyThreadSplit_P2() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 THREAD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If threadValue2 is ZERO
    // ============================================
    if (threadValue == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING THREAD SPLIT VALIDATION — UI Thread Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any thread validation
    }

    // ============================================
    // THREAD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Thread Split Calculation..." + RESET);
    System.out.println(GREEN +
            "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalThreadAmount"
            + RESET);

    double calcThreadRaw = 0.0;

    if (discountedMRP > 0) {
        calcThreadRaw = ((double) discountedMRP2 / (double) discountedMRP) * threadValue;
    }

    int calcThreadFloor = (int) Math.floor(calcThreadRaw);
    int calcThreadCeil  = (int) Math.ceil(calcThreadRaw);

    System.out.println(YELLOW + "Calculated Thread Raw:      " + calcThreadRaw + RESET);
    System.out.println(YELLOW + "Calculated Thread Floor:    " + calcThreadFloor + RESET);
    System.out.println(YELLOW + "Calculated Thread Ceil:     " + calcThreadCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Thread Value:            " + threadValue2 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (threadValue2 == calcThreadFloor || threadValue2 == calcThreadCeil) {

        System.out.println(GREEN +
                "✅ THREAD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
                RESET);

    } else {

        System.out.println(RED +
                "❌ THREAD DISTRIBUTION MISMATCH — UI: " + threadValue2 +
                " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil +
                RESET);

        Assert.fail("❌ THREAD DISTRIBUTION MISMATCH — UI: " +
                threadValue2 + " | CalcFloor: " + calcThreadFloor +
                " | CalcCeil: " + calcThreadCeil);
    }

    System.out.println(LINE);
}


public void verifyGiftCardSplit_P2() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 GIFT CARD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If giftCardAmount2 is ZERO
    // ============================================
    if (giftCardAmount == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING GIFT CARD SPLIT VALIDATION — UI Gift Card Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any validation
    }

    // ============================================
    // GIFT CARD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Gift Card Split Calculation..." + RESET);

    System.out.println(GREEN +
        "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalGiftCardAmount"
        + RESET);

    double calcGiftCardRaw = 0.0;

    if (discountedMRP > 0) {
        calcGiftCardRaw = ((double) discountedMRP2 / (double) discountedMRP) * giftCardAmount;
    }

    int calcGiftCardFloor = (int) Math.floor(calcGiftCardRaw);
    int calcGiftCardCeil  = (int) Math.ceil(calcGiftCardRaw);

    System.out.println(YELLOW + "Calculated Gift Card Raw:      " + calcGiftCardRaw + RESET);
    System.out.println(YELLOW + "Calculated Gift Card Floor:    " + calcGiftCardFloor + RESET);
    System.out.println(YELLOW + "Calculated Gift Card Ceil:     " + calcGiftCardCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Gift Card Value:            " + giftCardAmount2 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (giftCardAmount2 == calcGiftCardFloor || giftCardAmount2 == calcGiftCardCeil) {

        System.out.println(GREEN +
            "✅ GIFT CARD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
        RESET);

    } else {

        System.out.println(RED +
            "❌ GIFT CARD DISTRIBUTION MISMATCH — UI: " + giftCardAmount2 +
            " | CalcFloor: " + calcGiftCardFloor +
            " | CalcCeil: " + calcGiftCardCeil +
            RESET);

        Assert.fail("❌ GIFT CARD DISTRIBUTION MISMATCH — UI: " +
            giftCardAmount2 + " | CalcFloor: " + calcGiftCardFloor +
            " | CalcCeil: " + calcGiftCardCeil);
    }

    System.out.println(LINE);
}
public void verifyGiftCardSplit_P1() {

    String GREEN  = "\u001B[32m";
    String RED    = "\u001B[31m";
    String YELLOW = "\u001B[33m";
    String CYAN   = "\u001B[36m";
    String RESET  = "\u001B[0m";
    String BLUE   = "\u001B[34m";

    String LINE = BLUE + "──────────────────────────────────────────────────────────────" + RESET;

    System.out.println(LINE);
    System.out.println(CYAN + "📘 GIFT CARD DISTRIBUTION CALCULATION" + RESET);

    // ============================================
    // 🛑 SKIP LOGIC — If giftCardAmount2 is ZERO
    // ============================================
    if (giftCardAmount == 0) {

        System.out.println(YELLOW +
            "⚠ SKIPPING GIFT CARD SPLIT VALIDATION — UI Gift Card Value is 0" +
        RESET);

        System.out.println(LINE);
        return;  // EXIT — Do NOT perform any validation
    }

    // ============================================
    // GIFT CARD SPLIT CALCULATION
    // ============================================
    System.out.println(CYAN + "🧮 Performing Gift Card Split Calculation..." + RESET);

    System.out.println(GREEN +
        "Formula: (ProductDiscountedMRP / TotalDiscountedMRP) * TotalGiftCardAmount"
        + RESET);

    double calcGiftCardRaw = 0.0;

    if (discountedMRP > 0) {
        calcGiftCardRaw = ((double) discountedMRP1 / (double) discountedMRP) * giftCardAmount;
    }

    int calcGiftCardFloor = (int) Math.floor(calcGiftCardRaw);
    int calcGiftCardCeil  = (int) Math.ceil(calcGiftCardRaw);

    System.out.println(YELLOW + "Calculated Gift Card Raw:      " + calcGiftCardRaw + RESET);
    System.out.println(YELLOW + "Calculated Gift Card Floor:    " + calcGiftCardFloor + RESET);
    System.out.println(YELLOW + "Calculated Gift Card Ceil:     " + calcGiftCardCeil + RESET);
    System.out.println(LINE);

    System.out.println(YELLOW + "UI Gift Card Value:            " + giftCardAmount1 + RESET);
    System.out.println(LINE);

    // ============================================
    // VALIDATION WITH TOLERANCE
    // ============================================
    if (giftCardAmount1 == calcGiftCardFloor || giftCardAmount1 == calcGiftCardCeil) {

        System.out.println(GREEN +
            "✅ GIFT CARD DISTRIBUTION MATCHED UI (Accepted Floor/Ceil Tolerance)" +
        RESET);

    } else {

        System.out.println(RED +
            "❌ GIFT CARD DISTRIBUTION MISMATCH — UI: " + giftCardAmount1 +
            " | CalcFloor: " + calcGiftCardFloor +
            " | CalcCeil: " + calcGiftCardCeil +
            RESET);

        Assert.fail("❌ GIFT CARD DISTRIBUTION MISMATCH — UI: " +
        		giftCardAmount1 + " | CalcFloor: " + calcGiftCardFloor +
            " | CalcCeil: " + calcGiftCardCeil);
    }

    System.out.println(LINE);
}


String accessoriesProduct;

public String takeRandomAccessoriesProductFromAll() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    Actions actions = new Actions(driver);

    // Hover on Shop → All
    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
    actions.moveToElement(shopMenu).perform();

    WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[normalize-space()='Accessories']")));
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
        productlistingName = name;

        WebElement productNameElement = productCard.findElement(
                By.xpath(".//h2[@class='product_list_cards_heading']"));

        productNameElement.click(); // Open PDP

        productFound = true;
        System.out.println("✅ Selected random in-stock product: " + productlistingName);
        break;
    }

    if (!productFound) {
        System.out.println("⚠️ No in-stock product found after trying " + maxAttempts);
        return null;
    }
    // Click ADD TO CART button on PDP
    

   
    WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[contains(text(),'Add to')])[1]")));
    Common.waitForElement(2);
    wait.until(ExpectedConditions.elementToBeClickable(addToCart));

    addToCart.click();
    

    System.out.println("🛒 Add to Cart clicked on PDP for: " + productlistingName);

    return productlistingName;
}

public void takeCustomizeProduct() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String RED = "\u001B[31m";
    String RESET = "\u001B[0m";
    String CYAN = "\u001B[36m";
    String line = "──────────────────────────────────────────────────────────────";
    String productName = Common.getValueFromTestDataMap("ProductListingName");

    try {
        System.out.println(CYAN + line + RESET);
        System.out.println(GREEN + "🛒 Selecting Product & Applying Custom Size..." + RESET);
        System.out.println(CYAN + line + RESET);
        
        System.out.println(YELLOW + "🔍 Searching for product: " + productName + RESET);
	    wait.until(ExpectedConditions.elementToBeClickable(userSearchBox));
	    userSearchBox.clear();
	    userSearchBox.sendKeys(productName);
	    userSearchBox.sendKeys(Keys.ENTER);
	    Common.waitForElement(2);

        // ▌1️⃣ Click product from listing
        System.out.println(YELLOW + "👉 Clicking product: " + productName + RESET);
        WebElement productElement = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath(".//h2[@class='product_list_cards_heading']"))
        );
        Common.waitForElement(1);
        productElement.click();
        Common.waitForElement(1);
        // ▌2️⃣ Click CUSTOM SIZE button
        System.out.println(YELLOW + "📏 Clicking Custom Size option..." + RESET);
        Common.waitForElement(2);
        WebElement customSizeBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class, 'prod_size_name') and contains(@class, 'Cls_custom_btn')]"))
        );
        customSizeBtn.click();
        System.out.println(YELLOW + "✏️ Entering size: ");
        driver.findElement(By.xpath("//input[@id='bustInput']")).sendKeys("45");
        driver.findElement(By.xpath("//input[@id='pantWaistInput']")).sendKeys("55");
        driver.findElement(By.xpath("//input[@id='bottomLengthInput']")).sendKeys("55");
        driver.findElement(By.xpath("//input[@id='hipInput']")).sendKeys("55");

        Common.waitForElement(1);
        // ▌4️⃣ Submit custom size
        System.out.println(YELLOW + "📨 Submitting custom size..." + RESET);
        WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Submit')]"))
        );
        submitBtn.click();
        Common.waitForElement(1);
        // ▌5️⃣ Click ADD TO CART
        System.out.println(YELLOW + "🛍️ Adding product to cart..." + RESET);
        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'Add to')])[1]"))
        );
        addToCartBtn.click();

        System.out.println(GREEN + "✅ Product added to cart successfully!" + RESET);
        System.out.println(CYAN + line + RESET);

    } catch (Exception e) {
        System.out.println(RED + "❌ Failed during Custom Size Add-to-Cart flow: " + e.getMessage() + RESET);
    }
}




//TC-01 For one Product	
	public void verify_P1_With_GW_C_GC_GA_T() throws InterruptedException {
		
		userLoginApp();
		
		deleteAllProductFromCart();
		
		takeRandomProductFromAll();
		
		addGiftCardInCart();
			
		applyCouponAndGiftWrap();
		
		selectExpressDelivery();
		
		applyGiftCardAmount();
			
		applyThreadValue();
		
		verifyPriceDetailsCalculation();
		
		validateRazorpaySummaryCalculation();
		
		validatePriceBreakupDetails();
		
		validateOrderSummary();
		
		
	}
	
//TC-02 For Two Product		
	public void verify_P1_P2_With_GC_C_GW_GCA_T_E() throws InterruptedException{
		
        userLoginApp();
		
		deleteAllProductFromCart();
		
		takeRandomProductFromAll();
		
		takeRandomProductFromAll();
		
		addGiftCardInCart();
			
		applyCouponAndGiftWrap();
		
		selectExpressDelivery();
		
		applyGiftCardAmount();
			
		applyThreadValue();
		
		verifyPriceDetailsCalculation();
		
		validateRazorpaySummaryCalculation();
		
		placeOrderAndCheckOrderConfirmation();
//First Product		
		moveToProduct(1);
	
		validatePriceBreakupDetails_P1();
		
		verifyCouponSplit_P1();
		
		verifyThreadSplit_P1();
		
		verifyGiftCardSplit_P1();
	    
		closeBtn.click();
	    
		driver.navigate().back();
		moveToProduct(2);
		
		validatePriceBreakupDetails_P2();
		
		verifyCouponSplit_P2();
		
		verifyThreadSplit_P2();
		
		verifyGiftCardSplit_P1();
		
		closeBtn.click();
		
		validateOrderSummaryForTwoProduct_P2();
		driver.navigate().back();
		moveToProduct(1);
		
		validateOrderSummaryForTwoProduct_P1();	
		
	}
	
	//TC-03 For one  Product, one Customize Product and Accessories		
		public void verify_P1_CP_AP_With_GC_C_GW_GCA_T_E() throws InterruptedException{
			
	        userLoginApp();
			
			deleteAllProductFromCart();
			
			takeRandomProductFromAll();
			
			takeRandomAccessoriesProductFromAll();
			
			takeCustomizeProduct();
			
			addGiftCardInCart();
				
			applyCouponAndGiftWrap();
			
			selectExpressDelivery();
			
			applyGiftCardAmount();
				
			applyThreadValue();
			
			verifyPriceDetailsCalculation();
			
			validateRazorpaySummaryCalculation();
			
			placeOrderAndCheckOrderConfirmation();
//First Product		
			moveToProduct(1);
		
			validatePriceBreakupDetails_P1();
			
			verifyCouponSplit_P1();
			
			verifyThreadSplit_P1();
			
			verifyGiftCardSplit_P1();
		    
			closeBtn.click();
		    
			driver.navigate().back();
			moveToProduct(2);
			
			validatePriceBreakupDetails_P2();
			
			verifyCouponSplit_P2();
			
			verifyThreadSplit_P2();
			
			verifyGiftCardSplit_P1();
			
			closeBtn.click();
			
			validateOrderSummaryForTwoProduct_P2();
			driver.navigate().back();
			moveToProduct(1);
			
			validateOrderSummaryForTwoProduct_P1();	
			
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
