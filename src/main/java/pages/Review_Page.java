package pages;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.Review_ObjRepo;
import utils.Common;


public class Review_Page extends Review_ObjRepo{
	public Review_Page(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	
	
	public void adminLogin() {
		Common.waitForElement(2);
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	public void userLoginWithZlaataIndia() {
	    LoginPage log = new LoginPage(driver);
	    log.userLogin();
	    Common.waitForElement(1);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Fixed XPath: Removed leading '/' and extra trailing ']'
	    WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW'])[1]")
	    ));

	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);

	    System.out.println("Clicked ZLAATA INDIA Home Page Banner");
	}

	public void launchHomepage(String page) {
	    HomePage home = new HomePage(driver);
	    home.homeLaunch();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Updated to use the correct SHOP NOW banner XPath
	    WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW'])[1]")
	    ));

	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);

	    System.out.println("Clicked " + page + " Home Page Banner");
	}

	public void launchHomepageWithBrand(String page) {
	    HomePage home = new HomePage(driver);
	    home.homeLaunch();
	    String formattedBrand = page.toUpperCase();
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    // Updated to use the correct SHOP NOW banner XPath
	    WebElement banner = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW'])[1]")
	    ));

	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", banner);

	    System.out.println("Clicked " + formattedBrand + " Home Page Banner");
	}
	
	String productlistingName;

	public String takeRandomProductFromAll() {
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);
	    Common.waitForElement(1);

	    // Hover on Shop
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@class='header_nav_item has_dropdown shop']")
	    ));
	    actions.moveToElement(shopMenu).perform();

	    // Click All
	    WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//a[contains(@class,'dropdown_category_link')])[1]")
	    ));
	    allButton.click();

	    System.out.println("✅ Clicked on 'All' under Shop menu");
	    Common.waitForElement(2);

	    Random rand = new Random();
	    int maxAttempts = 5;
	    boolean productFound = false;

	    for (int attempt = 1; attempt <= maxAttempts; attempt++) {
	        try {
	            // Re-find products list fresh each attempt (avoid stale element)
	            List<WebElement> products = wait.until(ExpectedConditions
	                    .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'prod_listing_card')]")));

	            if (products.isEmpty()) {
	                System.out.println("⚠️ No products found on listing page!");
	                return null;
	            }

	            int randomIndex = rand.nextInt(products.size()) + 1;
	            System.out.println("🎯 Checking random product index: " + randomIndex);

	            // Find fresh product card
	            WebElement productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("(//div[contains(@class,'prod_listing_card')])[" + randomIndex + "]")
	            ));

	            // Get product name with retry for stale element
	            String name = "";
	            for (int nameRetry = 0; nameRetry < 3; nameRetry++) {
	                try {
	                    name = productCard.findElement(
	                            By.xpath(".//a[contains(@class,'product_list_name')]"))
	                            .getText().trim();
	                    break; // success
	                } catch (StaleElementReferenceException e) {
	                    if (nameRetry == 2) throw e;
	                    Common.waitForElement(1);
	                    // Re-find product card
	                    productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                            By.xpath("(//div[contains(@class,'prod_listing_card')])[" + randomIndex + "]")
	                    ));
	                }
	            }

	            // Found in-stock product
	            String productName = name;

	            // Click product with retry for stale element
	            for (int clickRetry = 0; clickRetry < 3; clickRetry++) {
	                try {
	                    WebElement productNameElement = productCard.findElement(
	                            By.xpath(".//a[contains(@class,'product_list_name')]"));
	                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productNameElement);
	                    break; // success
	                } catch (StaleElementReferenceException e) {
	                    if (clickRetry == 2) throw e;
	                    Common.waitForElement(1);
	                    // Re-find product card
	                    productCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                            By.xpath("(//div[contains(@class,'prod_listing_card')])[" + randomIndex + "]")
	                    ));
	                }
	            }

	            productFound = true;
	            System.out.println("✅ Selected random in-stock product: " + productName);
	            break;

	        } catch (StaleElementReferenceException e) {
	            System.out.println("⚠️ Stale element on attempt " + attempt + ", retrying...");
	            Common.waitForElement(2);
	        }
	    }

	    if (!productFound) {
	        System.out.println("⚠️ No product found after " + maxAttempts + " attempts");
	        return null;
	    }

	    // Get product name from PDP
	    productlistingName = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//h3[@class='prod_name']")
	    )).getText().trim();
	    
	    System.out.println("Product Name: " + productlistingName);
	    return productlistingName;
	}
	
	public void completeReviewFlow() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	 // ============================================
	    // STEP 1: Scroll 20-25% from top & open review popup
	    // ============================================
	    
	    By reviewBtnLocator = By.xpath("(//button[normalize-space()='Write a Review'])[1]");
	    // Targets the active popup modal wrapper OR any input field inside it
	    By reviewFormLocator = By.xpath("//div[contains(@class,'write_review_popup') and contains(@class,'active')] | (//textarea[@placeholder='Write a review'])[1]");

	    boolean isFormOpened = false;

	    // Check if the popup is already open before attempting any scrolling/clicking
	    if (!driver.findElements(reviewFormLocator).isEmpty() && driver.findElement(reviewFormLocator).isDisplayed()) {
	        System.out.println("🛒 Review popup is already open.");
	        isFormOpened = true;
	    } else {
	        for (int attempt = 1; attempt <= 3; attempt++) {
	            try {
	                // 1. Initial page scroll to load review section
	                js.executeScript("window.scrollTo(0, document.body.scrollHeight * 0.25);");
	                Common.waitForElement(1);

	                // 2. Locate button and scroll it 20-25% down from top
	                WebElement writeReviewBtn = wait.until(ExpectedConditions.presenceOfElementLocated(reviewBtnLocator));
	                js.executeScript(
	                    "var elementRect = arguments[0].getBoundingClientRect();" +
	                    "var absoluteElementTop = elementRect.top + window.pageYOffset;" +
	                    "var targetPosition = absoluteElementTop - (window.innerHeight * 0.22);" +
	                    "window.scrollTo({top: targetPosition, behavior: 'instant'});", 
	                    writeReviewBtn
	                );
	                Common.waitForElement(1);

	                // 3. Re-query fresh element reference
	                writeReviewBtn = driver.findElement(reviewBtnLocator);

	                // 4. Force JavaScript click to avoid layout overlap issues
	                js.executeScript("arguments[0].click();", writeReviewBtn);

	                // 5. Wait for active modal popup container to become visible
	                WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(8));
	                popupWait.until(ExpectedConditions.visibilityOfElementLocated(reviewFormLocator));
	                
	                isFormOpened = true;
	                System.out.println("🛒 'Write a Review' button clicked successfully for: " + productlistingName);
	                break;

	            } catch (Exception e) {
	                // If the popup popped open despite catching an exception, break out cleanly
	                if (!driver.findElements(reviewFormLocator).isEmpty() && driver.findElement(reviewFormLocator).isDisplayed()) {
	                    isFormOpened = true;
	                    System.out.println("🛒 'Write a Review' popup detected open after attempt " + attempt);
	                    break;
	                }
	                System.out.println("⚠️ Attempt " + attempt + " to open review popup failed. Retrying...");
	                Common.waitForElement(1);
	            }
	        }
	    }

	    if (!isFormOpened) {
	        Assert.fail("❌ Failed to open 'Write a Review' form after 3 attempts.");
	    }

	    if (!isFormOpened) {
	        Assert.fail("❌ Failed to open 'Write a Review' form after 3 attempts.");
	    }
	    // ============================================
	    // STEP 2: Select 5-star rating
	    // ============================================
	    
	    WebElement star5 = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//*[name()='svg'][@class='order_review_star Cls_order_review_star '])[5]")
	    ));
	    star5.click();
	    System.out.println("⭐ Selected 5-star rating");

	    // ============================================
	    // STEP 3: Select Tags
	    // ============================================
	    
	    WebElement tagsBest = wait.until(ExpectedConditions.elementToBeClickable(
	    		
	        By.xpath("(//label[@for='reviewInputTag0'])[1]")
	    ));
	    tagsBest.click();
	    System.out.println("🏷️ Selected tag: Best");

	    
	    // ============================================
	    // STEP 4: Enter review description
	    // ============================================
	    
	    WebElement reviewBox = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//textarea[@placeholder='Write a review'])[1]")
	    ));
	    reviewBox.clear();
	    reviewBox.sendKeys("Best product! Highly recommended.");
	    System.out.println("✍️ Entered review text");

	    // ============================================
	    // STEP 5: Upload image
	    // ============================================
	    
	    WebElement uploadInput = driver.findElement(By.xpath("(//input[@id='reviewImageInput'])[1]"));
	    String imagePath = System.getProperty("user.dir") + "/src/test/resources/images/Review.jpg";
	    uploadInput.sendKeys(imagePath);
	    System.out.println("📷 Image uploaded");

	    // ============================================
	    // STEP 6: Select "True to size"
	    // ============================================
	    
	    WebElement trueToSize = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//input[@id='overallFit_true'])[1]")
	    ));
	    js.executeScript("arguments[0].click();", trueToSize);
	    System.out.println("📏 Selected 'True to size'");

	    // ============================================
	    // STEP 7: Enter Name
	    // ============================================
	    
	    WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//input[@id='reviewUserName'])[1]")
	    ));
	    nameField.clear();
	    nameField.sendKeys("Test User");
	    System.out.println("👤 Entered name");

	    // ============================================
	    // STEP 8: Enter Email
	    // ============================================
	    
	    WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//input[@id='reviewUserEmail'])[1]")
	    ));
	    emailField.clear();
	    emailField.sendKeys("testuser@example.com");
	    System.out.println("📧 Entered email");

	    // ============================================
	    // STEP 9: Submit review
	    // ============================================
	    
	    WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//button[normalize-space()='Submit'])[1]")
	    ));
	    
	    // Scroll submit button into view with offset
	    js.executeScript(
	        "var headerOffset = 100;" +
	        "var elementPosition = arguments[0].getBoundingClientRect().top;" +
	        "var offsetPosition = elementPosition + window.pageYOffset - headerOffset;" +
	        "window.scrollTo({top: offsetPosition, behavior: 'smooth'});", 
	        submitBtn
	    );
	    Common.waitForElement(1);
	    
	    submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
	        By.xpath("(//button[normalize-space()='Submit'])[1]")
	    ));
	    
	    try {
	        submitBtn.click();
	    } catch (ElementClickInterceptedException e) {
	        js.executeScript("arguments[0].click();", submitBtn);
	    }
	    
	    System.out.println("📤 Review submitted");

	    // ============================================
	    // STEP 10: Verify success message
	    // ============================================
	    
	    WebElement successMsg = wait.until(
	        ExpectedConditions.visibilityOf(mailValidationMessage)
	    );
	    Assert.assertEquals("❌ Success message mismatch",
	        "Review updated successfully!",
	        successMsg.getText().trim());
	    System.out.println("✅ Review submitted successfully");
	}
	
	
	public String normalize(String text) {
	    return text.toLowerCase().replaceAll("\\s+", "");
	}
	public void approveReviewIfProductMatches() {
		adminLogin();
	     Common.waitForElement(2);
	        click(searchMenu);
	        type(searchMenu, "Product Reviews");
	        click(clickProductReview);
	        System.out.println("✅ Selected Product Reviews");
	        Common.waitForElement(3);
	    
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        JavascriptExecutor js = (JavascriptExecutor) driver;


	    // Normalize expected name
	    String expected = normalize(productlistingName);

	    // Get FIRST product name from admin table
	    WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//table//tr[1]/td[3]//span)[1]")
	    ));

	    String actualProductName = firstProduct.getText();
	    String actual = normalize(actualProductName);

	    System.out.println("Expected: " + expected);
	    System.out.println("Actual: " + actual);

	    // Compare
	    if (expected.equals(actual)) {

	        System.out.println("✅ Product matched. Approving review...");

	        // Click Approve button (✓)
	        WebElement approveBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//table//tr[1]//button[@data-status='approve'])[1]")
	        ));
	        approveBtn.click();
	        Common.waitForElement(3);
	        // Verify Approved badge
	        WebElement approvedBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//table//tr[1]//span[contains(@class,'badge-success') and contains(text(),'Approved')])[1]")
	        ));

	        if (approvedBadge.isDisplayed()) {
	            System.out.println("✅ Review successfully approved");
	        } else {
	            System.out.println("❌ Approval failed");
	        }
	        
	     // Clear cache
		    Common.waitForElement(3);
		    click(clearCatchButton);
		    System.out.println("✅ Cleared cache");
		     Common.waitForElement(2);

	    } else {
	        System.out.println("❌ Product NOT matched — skipping approval");
	    }
	    
	}
	
	public void verifyApproveReview() {

	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // ---------------- STEP 1: SEARCH PRODUCT ----------------
	    System.out.println(YELLOW + "🔍 Searching for product: " + productlistingName + RESET);

	    Common.waitForElement(2);

	    wait.until(ExpectedConditions.elementToBeClickable(searchIcon)).click();

	    wait.until(ExpectedConditions.visibilityOf(userSearchBox));
	    userSearchBox.clear();
	    userSearchBox.sendKeys(productlistingName);
	    userSearchBox.sendKeys(Keys.ENTER);

	    Common.waitForElement(3);

	    // ---------------- STEP 2: CLICK PRODUCT ----------------
	    WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//a[@class='product_list_name' and normalize-space()='" + productlistingName + "'])[1]")
	    ));

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", productLink);
	    js.executeScript("arguments[0].click();", productLink);

	    System.out.println("✅ Clicked product");
	    Common.waitForElement(2);
	    WebElement writeReview = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//button[contains(text(),'Review')])[1]")));
	    Common.waitForElement(2);
	    // scroll it into center
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block: 'center'});", writeReview);

	    // click via JS (bypasses click interception)
	    // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", writeReview);
	    System.out.println("🛒 Review clicked on PDP for: " + productlistingName);
	}
	
	public void verifyFirstReviewDetails() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String RESET  = "\u001B[0m";
	    Common.waitForElement(2);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {
	        // ---------------- FIRST REVIEW CARD ----------------
	        WebElement firstReview = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//div[contains(@class,'customer_review_card')])[1]")
	        ));

	        // ---------------- NAME ----------------
	        String actualName = firstReview.findElement(
	                By.xpath(".//div[@class='review_customer_name']")  // ← FIXED: .// instead of //
	        ).getText().trim().toLowerCase().replaceAll("\\s+", "");

	        if (actualName.contains("testuser")) {
	            System.out.println(GREEN + "✅ Name verified: " + actualName + RESET);
	        } else {
	            throw new AssertionError("❌ Name mismatch: " + actualName);
	        }

	        // ---------------- REVIEW TEXT ----------------
	        String reviewText = firstReview.findElements(
	                By.xpath(".//p[@class='review_card_content']")  // ← FIXED: .// instead of //
	        ).size() > 0 ?
	                firstReview.findElement(By.xpath(".//p[@class='review_card_content']")).getText().trim()  // ← FIXED
	                : "";

	        if (reviewText.equalsIgnoreCase("Best product! Highly recommended.")) {
	            System.out.println(GREEN + "✅ Review text verified: " + reviewText + RESET);
	        } else {
	            throw new AssertionError("❌ Review text mismatch: " + reviewText);
	        }

	        // ---------------- IMAGE ----------------
	        boolean imagePresent = firstReview.findElements(
	                By.xpath(".//img[@alt='Zlaata Product Image']")  // ← FIXED: .// instead of //
	        ).size() > 0;

	        if (imagePresent) {
	            System.out.println(GREEN + "✅ Image is displayed" + RESET);
	        } else {
	            throw new AssertionError("❌ Image not found");
	        }
	        Common.waitForElement(2);
	    } catch (Exception e) {
	        System.out.println(RED + "❌ Review verification FAILED: " + e.getMessage() + RESET);
	        throw e;
	    }
	}
	
	
	String productName;
	public void verifyMyOrderReviewFlow() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;
		 String CYAN = "\u001B[36m";
		    String YELLOW = "\u001B[33m";
		    String GREEN = "\u001B[32m";
		    String RED = "\u001B[31m";
		    String RESET = "\u001B[0m";
		    String line = "──────────────────────────────────────────────────────────────";

		    System.out.println(CYAN + line + RESET);
		    System.out.println(GREEN + "🚀 Starting My Order Review Flow..." + RESET);
		    System.out.println(CYAN + line + RESET);
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(myProfileIcon));
		    waitFor(myProfileIcon);
			click(myProfileIcon);
			Common.waitForElement(1);
		    wait.until(ExpectedConditions.elementToBeClickable(myOrdersBtn));
		    waitFor(myOrdersBtn);
			click(myOrdersBtn);
			Common.waitForElement(2);
			wait.until(ExpectedConditions.elementToBeClickable(pendingReview));
		    waitFor(pendingReview);
			click(pendingReview);
		    Common.waitForElement(3);
			// Build dynamic XPath
			String xpath = "(//a[contains(@class,'order_placed_redirect_btn')])[1]";
			WebElement btn = driver.findElement(By.xpath(xpath));

			// 1️⃣ Scroll to the element
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
			
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		    
		    
		    Common.waitForElement(2);
		    String xpath1 = "(//button[contains(text(),'Write a Review')])[1]";
		    WebElement btn1 = driver.findElement(By.xpath(xpath1));

		    // 1️⃣ Scroll to the element
		    ((JavascriptExecutor) driver).executeScript(
		        "arguments[0].scrollIntoView({block:'center'});", btn1
		    );

		    // 2️⃣ Click using JS
		    ((JavascriptExecutor) driver).executeScript(
		        "arguments[0].click();", btn1
		    );
		    Common.waitForElement(2);
		    
		    
		     WebElement star5 = wait.until(ExpectedConditions.visibilityOfElementLocated(
		             By.xpath("(//div[contains(@class,'order_review_cont')]//*[name()='svg' and @data-star])[last()]")
		     ));
		    
		     star5.click();
		     System.out.println("⭐ Selected 5-star rating");
		     productName = driver.findElement(
			            By.xpath("(//h4[@class='placed_prod_name'])[last()]")
			    ).getText().trim();
			    System.out.println("Product Name: " + productName);
			    System.out.println("🛒 Review clicked on PDP for: " + productName);
		     Common.waitForElement(1);
		     // ✍️ Enter review text
		     WebElement reviewBox1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
		             By.xpath("//textarea[@placeholder='Write a review']")
		     ));
		     reviewBox1.clear();
		     reviewBox1.sendKeys("Best");
		     System.out.println("✍️ Entered review text");
		     Common.waitForElement(2);

		     // 📷 Upload image
		     WebElement uploadInput = driver.findElement(
		             By.xpath("(//input[@type='file'])[1]")
		     );

		     // 👉 Give full system path
		     String imagePath = System.getProperty("user.dir") + "/src/test/resources/images/Review.jpg";
		     uploadInput.sendKeys(imagePath);

		     System.out.println("📷 Image uploaded");
		     Common.waitForElement(1);

		     // 🔘 Select "True to size"
		     WebElement trueToSize = wait.until(ExpectedConditions.elementToBeClickable(
		             By.xpath("//input[@id='overallFit_true']")
		     ));
		     js.executeScript("arguments[0].click();", trueToSize);

		     System.out.println("📏 Selected 'True to size'");
		     Common.waitForElement(1);

		     // ✅ Click Submit
		     WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
		             By.xpath("//button[contains(@class,'write_review_submit_btn')]")
		     ));
		     js.executeScript("arguments[0].click();", submitBtn);
		     Common.waitForElement(2);
		     WebElement successMsg = wait.until(
		                ExpectedConditions.visibilityOf(mailValidationMessage)
		        );

		        Assert.assertEquals("❌ successfully success message mismatch",
		                "Review updated successfully!",
		                successMsg.getText().trim());
		     System.out.println("✅ Review submitted successfully");
	
	
	}
	
	String brandName;
	public void approveMyOrderReviewIfProductMatches() {
		adminLogin();
	     Common.waitForElement(2);
	        click(searchMenu);
	        type(searchMenu, "Product Reviews");
	        click(clickProductReview);
	        System.out.println("✅ Selected Product Reviews");
	        Common.waitForElement(3);
	    
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        JavascriptExecutor js = (JavascriptExecutor) driver;


	    // Normalize expected name
	    String expected = normalize(productName);

	    // Get FIRST product name from admin table
	    WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        By.xpath("(//table//tr[1]/td[3]//span)[1]")
	    ));

	    String actualProductName = firstProduct.getText();
	    String actual = normalize(actualProductName);

	    System.out.println("Expected: " + expected);
	    System.out.println("Actual: " + actual);

	    // Compare
	    if (expected.equals(actual)) {

	        System.out.println("✅ Product matched. Approving review...");
	        
	        WebElement brandEle = wait.until(ExpectedConditions.visibilityOfElementLocated(
	        	    By.xpath("(//table//tr[1]/td[4]//span)[1]")
	        	));

	        	 brandName = brandEle.getText().trim();
	        	System.out.println("Brand: " + brandName);

	        // Click Approve button (✓)
	        WebElement approveBtn = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//table//tr[1]//button[@data-status='approve'])[1]")
	        ));
	        approveBtn.click();
	        Common.waitForElement(3);
	        // Verify Approved badge
	        WebElement approvedBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("(//table//tr[1]//span[contains(@class,'badge-success') and contains(text(),'Approved')])[1]")
	        ));

	        if (approvedBadge.isDisplayed()) {
	            System.out.println("✅ Review successfully approved");
	        } else {
	            System.out.println("❌ Approval failed");
	        }
	        
	     // Clear cache
		    Common.waitForElement(3);
		    click(clearCatchButton);
		    System.out.println("✅ Cleared cache");
		     Common.waitForElement(2);

	    } else {
	        System.out.println("❌ Product NOT matched — skipping approval");
	    }
	    
	}
	public void verifyApproveMyOrderReview() {

	    String YELLOW = "\u001B[33m";
	    String RESET  = "\u001B[0m";

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // ---------------- STEP 1: SEARCH PRODUCT ----------------
	    System.out.println(YELLOW + "🔍 Searching for product: " + productName + RESET);

	    Common.waitForElement(2);

	    wait.until(ExpectedConditions.elementToBeClickable(searchIcon)).click();

	    wait.until(ExpectedConditions.visibilityOf(userSearchBox));
	    userSearchBox.clear();
	    userSearchBox.sendKeys(productName);
	    Common.waitForElement(2);
	    userSearchBox.sendKeys(Keys.ENTER);

	    Common.waitForElement(3);

	    // ---------------- STEP 2: CLICK PRODUCT ----------------
	    WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//a[contains(@class,'product_list_name') and contains(text(),'" + productName + "')]")
	    ));

	    js.executeScript("arguments[0].scrollIntoView({block:'center'});", productLink);
	    js.executeScript("arguments[0].click();", productLink);

	    System.out.println("✅ Clicked product");
	    Common.waitForElement(2);
	    WebElement writeReview = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("(//button[contains(text(),'Review')])[1]")));
	    Common.waitForElement(2);
	 // scroll it into center
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block: 'center'});", writeReview);

	    // click via JS (bypasses click interception)
	//    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", writeReview);
	     System.out.println("🛒 Review clicked on PDP for: " + productName);
	}
	
	
	
	public void verifyMyOrderReviewDetails() {

	    String GREEN  = "\u001B[32m";
	    String RED    = "\u001B[31m";
	    String RESET  = "\u001B[0m";
	    Common.waitForElement(2);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    try {
	        // ---------------- FIRST REVIEW CARD ----------------
	        WebElement firstReview = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//div[contains(@class,'customer_review_card')])[1]")
	        ));

	        // ---------------- NAME ----------------
	        String actualName = firstReview.findElement(
	                By.xpath(".//div[@class='review_customer_name']")  // ← FIXED: .// instead of //
	        ).getText().trim();

	        if (!actualName.isEmpty()) {
	            System.out.println(GREEN + "✅ Name verified: " + actualName + RESET);
	        } else {
	            throw new AssertionError("❌ Name is empty");
	        }

	        // ---------------- REVIEW TEXT ----------------
	        String reviewText = firstReview.findElements(
	                By.xpath(".//p[@class='review_card_content']")  // ← FIXED: .// instead of //
	        ).size() > 0 ?
	                firstReview.findElement(By.xpath(".//p[@class='review_card_content']")).getText().trim()  // ← FIXED
	                : "";

	        if (reviewText.equalsIgnoreCase("Best")) {
	            System.out.println(GREEN + "✅ Review text verified: " + reviewText + RESET);
	        } else {
	            throw new AssertionError("❌ Review text mismatch: " + reviewText);
	        }

	        // ---------------- IMAGE ----------------
	        boolean imagePresent = firstReview.findElements(
	                By.xpath(".//img[@alt='Zlaata Product Image']")  // ← FIXED: .// instead of //
	        ).size() > 0;

	        if (imagePresent) {
	            System.out.println(GREEN + "✅ Image is displayed" + RESET);
	        } else {
	            throw new AssertionError("❌ Image not found");
	        }
	        Common.waitForElement(2);
	    } catch (Exception e) {
	        System.out.println(RED + "❌ Review verification FAILED: " + e.getMessage() + RESET);
	        throw e;
	    }
	}
	
	
	public void validateUserReview() {
		
		userLoginWithZlaataIndia();
		
		takeRandomProductFromAll();
		
		completeReviewFlow();
		
		approveReviewIfProductMatches();
		
		launchHomepage("ZLAATA INDIA");
		
		verifyApproveReview();
		
		verifyFirstReviewDetails();
		
	}
	
	
	public void validateMyOrderReview() {
		
		userLoginWithZlaataIndia();

		verifyMyOrderReviewFlow();
		
		approveMyOrderReviewIfProductMatches();
		
		launchHomepageWithBrand(brandName);
		
		verifyApproveMyOrderReview();
		
		verifyMyOrderReviewDetails(); 


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

