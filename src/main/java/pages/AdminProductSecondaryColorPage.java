package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.AdminProductSecondaryColorObjRepo;
import utils.Common;

public class AdminProductSecondaryColorPage extends AdminProductSecondaryColorObjRepo {

    public AdminProductSecondaryColorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(this.driver, this);
    }

    // ===============================
    // 🔐 Admin Login
    // ===============================
    public void adminLoginApp() {
        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
        type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
        type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
        click(adminLogin);
        System.out.println("✅ Admin Login Successful");
    }

    String productlistingName;
    String productColor;
    String productName;
    String secondaryColor;

    // ===============================
    // 🛍️ Select In-stock Product from App
    // ===============================
    public String takeRandomProductName() {
        HomePage home = new HomePage(driver);
        home.homeLaunch();
        Common.waitForElement(3);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        // Hover on Shop → All
        WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
        actions.moveToElement(shopMenu).perform();

        WebElement allButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[normalize-space()='All']")));
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
        int maxAttempts = Math.min(5, products.size()); // check max 5 random ones
        boolean productFound = false;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            int randomIndex = rand.nextInt(products.size()) + 1; // 1-based index for XPath
            System.out.println("🎯 Checking random product index: " + randomIndex);

            WebElement productCard = driver.findElement(
                    By.xpath("(//div[contains(@class,'product_list_cards_list')])[" + randomIndex + "]"));

            String name = productCard.findElement(By.xpath(".//h2[@class='product_list_cards_heading']")).getText().trim();

            List<WebElement> stockLabels = productCard.findElements(
                    By.xpath(".//h2[contains(@class,'product_list_cards_out_of_stock_heading') and normalize-space()='OUT OF STOCK']"));

            boolean isOutOfStock = !stockLabels.isEmpty() && stockLabels.get(0).isDisplayed();

            if (isOutOfStock) {
                System.out.println("❌ Random product '" + name + "' is OUT OF STOCK. Retrying another...");
                continue; // try another random one
            }

            // ✅ Product is in stock
            productlistingName = name;
            WebElement productNameElement = productCard.findElement(By.xpath(".//h2[@class='product_list_cards_heading']"));
            productNameElement.click();
            productFound = true;

            System.out.println("✅ Selected random in-stock product: " + productlistingName);
            break;
        }

        if (!productFound) {
            System.out.println("⚠️ Could not find any in-stock product after " + maxAttempts + " random tries.");
            return null;
        }

        // ✅ Get active color
        WebElement activeColorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("(//div[contains(@class,'prod_color_name Cls_prod_color_name')])[1]")));

        productColor = activeColorElement.getText().trim();
        System.out.println("🎨  product color: " + productColor);

        return productlistingName + " | Color: " + productColor;
    }

    
    
    boolean isAdminLoggedIn = false; // ✅ Global flag to track login state

 // ===============================
 // 🧩 Verify & Add Secondary Color in Admin
 // ===============================
 public boolean verifyAndAddSecondaryColorInAdmin() throws InterruptedException {
     JavascriptExecutor js = (JavascriptExecutor) driver;
     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));

     // ✅ Login only once
     if (!isAdminLoggedIn) {
         adminLoginApp();
         isAdminLoggedIn = true;
         System.out.println("🌐 Logged into Admin Panel for the first time...");
     } else {
         System.out.println("➡️ Reusing active Admin session...");
     }

     Common.waitForElement(3);

     // ✅ Go directly to admin product page URL (read from config or Excel)
     driver.get(Common.getValueFromTestDataMap("ExcelPath"));
     System.out.println("✅ Redirected to Admin Product page");

     click(productListingMenu);
     System.out.println("✅ Clicked Product Listing Menu");

     waitFor(productSearchBox);
     type(productSearchBox, productlistingName + Keys.ENTER);
     Common.waitForElement(3);
     System.out.println("✅ Searched for product: " + productlistingName);

     waitFor(editProductButton);
     click(editProductButton);
     System.out.println("🖊️ Clicked Edit for: " + productlistingName);

     waitFor(itemProductButton);
     click(itemProductButton);
     System.out.println("✅ Opened product item tab");

     // ✅ Verify product name
     WebElement productNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("//label[normalize-space()='Product Listing Name']/following-sibling::input[@name='filters[0][listing_name]']")));
     productName = productNameField.getAttribute("value").trim();
     System.out.println("✅ Product name verified in Admin: " + productName);

     // ✅ Primary color
     WebElement primaryColorField = wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("(//label[normalize-space()='Colour']/following::span[contains(@id,'color')][1])[1]")));
     String adminPrimaryColor = primaryColorField.getText().trim();
     System.out.println("🎨 Primary Color in Admin: " + adminPrimaryColor);
     
     Common.waitForElement(2);
  // ✅ Secondary color
     WebElement secondaryColorField = wait.until(ExpectedConditions.visibilityOfElementLocated(
             By.xpath("(//label[normalize-space()='Secondary Colour']/following::span[contains(@id,'secondary_color')][1])[1]")));
     secondaryColor = secondaryColorField.getText().trim();

     // 🟡 Check if secondary color is already selected
     if (!secondaryColor.isEmpty() && 
         !secondaryColor.equalsIgnoreCase("Select an List") && 
         !secondaryColor.equalsIgnoreCase("Select a List") && 
         !secondaryColor.equalsIgnoreCase("-")) {
         System.out.println("⚠️ Secondary color already exists: " + secondaryColor + " — skipping this product.");
         return false;
     }

     // ✅ Field empty or default — select a color now
     waitFor(secondaryColorField);
     click(secondaryColorField);
     waitFor(searchTextBox);
     click(searchTextBox);
     // example color to add
     String colorToAdd = "Space";
     type(searchTextBox, colorToAdd);
     searchTextBox.sendKeys(Keys.ENTER);

     // Wait a moment for UI to update
     Common.waitForElement(2);

     if (productColor.equalsIgnoreCase(adminPrimaryColor)) {
         System.out.println("❌ Skipping because selected color matches primary color: " + productColor);
         return false;
     }

     secondaryColor = colorToAdd; // assign added color
     System.out.println("🎨 Added Secondary Color: " + secondaryColor);

     // ✅ Save changes
     waitFor(saveButton);
     js.executeScript("arguments[0].click();", saveButton);
     System.out.println("💾 Product saved successfully with new secondary color!");

     // ✅ Clear cache
     waitFor(clearCatchButton);
     click(clearCatchButton);
     System.out.println("✅ Cache cleared successfully");

     return true;
 }


 // ===============================
 // 🔁 Retry Flow (Pick new products until success)
 // ===============================
 public void verifySecondaryColorFlow() throws InterruptedException {
     boolean success = false;
     int attempt = 0;

     while (!success && attempt < 5) {
         attempt++;
         System.out.println("\n🔁 Attempt " + attempt + ": Selecting random in-stock product...");

         String productData = takeRandomProductName();
         if (productData == null) {
             System.out.println("❌ No product found in stock on attempt " + attempt);
             continue;
         }

         System.out.println("🛍️ Selected Product: " + productlistingName + " | Color: " + productColor);

         // ✅ Try to add secondary color
         success = verifyAndAddSecondaryColorInAdmin();

         if (!success && attempt < 5) {
             System.out.println("🔁 Product already has secondary color — trying next product...");
         }
     }

     if (success) {
         System.out.println("✅ Secondary color successfully added for: " + productName);
     } else {
         System.out.println("❌ Could not find any product without secondary color after 5 attempts.");
     }
 }
	



 public void verifyProductColorInUserApp() throws InterruptedException {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    // ANSI color codes
	    final String RESET = "\u001B[0m";
	    final String GREEN = "\u001B[32m";
	    final String CYAN = "\u001B[36m";
	    final String YELLOW = "\u001B[33m";
	    final String BLUE = "\u001B[34m";
	    final String RED = "\u001B[31m";

	    System.out.println(GREEN + "✅ Final Product Details:" + RESET);
	    System.out.println("   🛍️ Product Name    : " + CYAN + productName + RESET);
	    System.out.println("   🎨 Secondary Color : " + YELLOW + secondaryColor + RESET);
	    System.out.println(BLUE + "🔍 Verifying product color in User App..." + RESET);

	    // ✅ Launch home page
	    HomePage home = new HomePage(driver);
		home.homeLaunch();
    	LoginPage login = new LoginPage(driver);
		login.userLogin();
	    Common.waitForElement(3);

	    // ✅ Search for product
	    wait.until(ExpectedConditions.elementToBeClickable(userSearchBox));
	    userSearchBox.clear();
	    userSearchBox.sendKeys(productName);
	    userSearchBox.sendKeys(Keys.ENTER);
	    System.out.println("🛍️ Searched for listing: " + productName);
	    
	 // 🧩 Section divider
	    String line = "──────────────────────────────────────────────────────────────";

	    // 🛍️ Start color checking section
	    System.out.println("\n" + CYAN + line + RESET);
	    System.out.println("🛍️ " + YELLOW + "Starting Product Color Check: Add to Cart Popup" + RESET);
	    System.out.println(CYAN + line + RESET);
	    
	    // ✅ Wait for search results and click product
	    By productLocator = By.xpath("//h2[normalize-space()='" + productName + "']");
	    WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
	    System.out.println(GREEN + "✅ Found product: " + productName + RESET);

	 
	    Common.waitForElement(3);

	    // ✅ Check if Add to Cart is clickable
	    try {
	        // ✅ Check if "Add to Bag" button exists and is clickable
	        WebElement addToBagBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//div[contains(@class,'product_list_add_to_cart')]")));

	        if (addToBagBtn.isDisplayed() && addToBagBtn.isEnabled()) {
	            System.out.println(GREEN + "🛍️ 'Add to Bag' button is clickable — adding product..." + RESET);
	            Common.waitForElement(3);
	            addToBagBtn.click();
	            Common.waitForElement(3);

	            // ✅ Verify color in popup after adding
	            WebElement popupColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("(//div[contains(@class,'prod_color_name Cls_prod_color_name')])[1]")));
	            String popupColorText = popupColor.getText().trim();

	            if (popupColorText.equalsIgnoreCase(secondaryColor)) {
	                System.out.println(GREEN + "🎨 Color matched in 'Add to Bag' popup: " + popupColorText + RESET);
	            } else {
	                System.out.println(RED + "❌ Color mismatch in 'Add to Bag' popup! Expected: " 
	                        + secondaryColor + " | Found: " + popupColorText + RESET);
	             // ❌ Fail test or throw exception
	                Assert.fail("Color mismatch in Add to Bag popup! Expected: " 
	                            + secondaryColor + " | Found: " + popupColorText);
	            }
	         // ✅ Clear cache
	            waitFor(addToCartBtn);
	            click(addToCartBtn);
	            System.out.println("✅  successfully Add to Cart");
	            

	        }

	    } catch (Exception e) {
	        System.out.println(YELLOW + "⚠️ 'Add to Bag' button not clickable — trying Wishlist flow..." + RESET);
	    }

	    // ✅ If Add to Cart not clickable → Try Wishlist route
	    // 🛍️ Start color checking section
	    System.out.println("\n" + CYAN + line + RESET);
	    System.out.println("🛍️ " + YELLOW + "Starting Product Color Check: Product Details Page" + RESET);
	    System.out.println(CYAN + line + RESET);
	    
	        WebElement wishListBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//div[contains(@class,'product_list_wishlist_icon') and contains(@class,'prod_wishlist_icon')]")));
	        wishListBtn.click();
	        System.out.println(GREEN + "❤️ Added product to Wishlist" + RESET);
	        Common.waitForElement(3);
	        
	     // 🔍 Click product name to open product details page
	            WebElement productNameLink = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//h2[normalize-space()='" + productName + "']")));
	            productNameLink.click();
	            System.out.println(CYAN + "🔗 Opened product details page for: " + productName + RESET);

	            // ✅ Wait for color section on details page
	            WebElement detailsColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("(//div[contains(@class,'prod_color_name Cls_prod_color_name')])[1]")));

	            String detailsColorText = detailsColor.getText().trim();

	            if (detailsColorText.equalsIgnoreCase(secondaryColor)) {
	                System.out.println(GREEN + "✅ Color matched in Product Details page: " + detailsColorText + RESET);
	            } else {
	                System.out.println(RED + "❌ Color mismatch in Product Details page! Expected: "
	                        + secondaryColor + " | Found: " + detailsColorText + RESET);
	                Assert.fail("Color mismatch in Product Details page! Expected: "
	                        + secondaryColor + " | Found: " + detailsColorText);
	            }
            
	            // 🛍️ Start color checking section
	    	    System.out.println("\n" + CYAN + line + RESET);
	    	    System.out.println("🛍️ " + YELLOW + "Starting Product Color Check: Product Review Page" + RESET);
	    	    System.out.println(CYAN + line + RESET);
	         // ✅ Step 2: Scroll to "Write a Review"
	            JavascriptExecutor js = (JavascriptExecutor) driver;
	            WebElement writeReviewBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//button[contains(.,'Write a Review')]")));
	            js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", writeReviewBtn);
	            Common.waitForElement(2);

	            System.out.println(CYAN + "📝 Scrolled to 'Write a Review' section" + RESET);

	            // ✅ Step 3: Click the button
	            wait.until(ExpectedConditions.elementToBeClickable(writeReviewBtn)).click();
	            System.out.println(GREEN + "✅ Clicked on 'Write a Review' button" + RESET);

	            // ✅ Step 4: Verify color in the review popup
	            WebElement reviewPopupColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("(//span[@class='placed_prod_color_value'])[1]")));
	            String reviewPopupColorText = reviewPopupColor.getText().trim();

	            if (reviewPopupColorText.equalsIgnoreCase(secondaryColor)) {
	                System.out.println(GREEN + "🎨 Color matched in Review Popup: " + reviewPopupColorText + RESET);
	            } else {
	                System.out.println(RED + "❌ Color mismatch in Review Popup! Expected: "
	                        + secondaryColor + " | Found: " + reviewPopupColorText + RESET);
	                Assert.fail("Color mismatch in Review Popup! Expected: "
	                        + secondaryColor + " | Found: " + reviewPopupColorText);
	            }

	            
	            // ✅ Step 5: Close review popup
	            try {
	                WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                        By.xpath("//div[@class='popup_containers_cls_btn cls_review_popup_close']//*[name()='svg']")));
	                closeBtn.click();
	                System.out.println(CYAN + "❎ Closed the review popup successfully" + RESET);
	            } catch (Exception e) {
	                System.out.println(RED + "⚠️ Failed to close review popup: " + e.getMessage() + RESET);
	            }
	               
	        
	          // Go to wishlist page
	            Common.waitForElement(3);
	        WebElement wishListIcon = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//a[@title='Wishlist Icon'])[1]")));
	        wishListIcon.click();
	        Common.waitForElement(3);

	        // 🛍️ Start color checking section
		    System.out.println("\n" + CYAN + line + RESET);
		    System.out.println("🛍️ " + YELLOW + "Starting Product Color Check: Wish list  Page" + RESET);
		    System.out.println(CYAN + line + RESET);
	  
	        
	        try {
	            // ✅ Step 1: Locate the wishlist product block by product name
	            WebElement wishlistProductCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//div[contains(@class,'product_list_cards_list')][.//h2[normalize-space()='" + productName + "']]")));
	            System.out.println(GREEN + "💖 Found wishlist item: " + productName + RESET);

	         // ✅ Step 2: Locate the Move to Cart button inside the card
	            WebElement moveToCartBtn = wishlistProductCard.findElement(
	                By.xpath(".//div[contains(@class,'product_list_add_to_cart')]")
	            );

	            // ✅ Step 3: Scroll into view and attempt to click safely
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", moveToCartBtn);
	            Thread.sleep(2000); // wait for smooth scroll


	            // ✅ Step 3: Check if clickable
	            if (moveToCartBtn.isDisplayed() && moveToCartBtn.isEnabled()) {
	                System.out.println(CYAN + "🛒 'Move to Cart' button is clickable for: " + productName + RESET);
	                Common.waitForElement(3);
	                moveToCartBtn.click();
	                Common.waitForElement(4);

	                // ✅ Step 4: Wait for popup and check color
	                WebElement popupColor = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("(//div[contains(@class,'prod_color_name Cls_prod_color_name')])[1]")));
	                String popupColorText = popupColor.getText().trim();

	                if (popupColorText.equalsIgnoreCase(secondaryColor)) {
	                    System.out.println(GREEN + "🎨 Color matched in 'Move to Cart' popup: " + popupColorText + RESET);
	                } else {
	                    System.out.println(RED + "❌ Color mismatch in 'Move to Cart' popup! Expected: "
	                            + secondaryColor + " | Found: " + popupColorText + RESET);
	                    Assert.fail("Color mismatch in Move to Cart popup! Expected: "
	                            + secondaryColor + " | Found: " + popupColorText);
	                }

	                // ✅ Step 5: Close popup
	                try {
	                    WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(
	                            By.xpath("//div[contains(@class,'add_bag_cls_btn')]")));
	                    closeBtn.click();
	                    System.out.println(CYAN + "❎ Closed 'Move to Cart' popup successfully" + RESET);
	                } catch (Exception e) {
	                    System.out.println(RED + "⚠️ Failed to close 'Move to Cart' popup: " + e.getMessage() + RESET);
	                }

	            } else {
	                System.out.println(RED + "⚠️ 'Move to Cart' button not clickable for: " + productName + RESET);
	            }

	        } catch (NoSuchElementException e) {
	            System.out.println(RED + "⚠️ Could not find 'Move to Cart' button for: " + productName + RESET);
	        }
	        
	        
	        // Go to Check Out  page
            Common.waitForElement(3);
        WebElement bagIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@title='Cart Icon']")));
        bagIcon.click();
        Common.waitForElement(3);
	        
        // 🛍️ Start color checking section
	    System.out.println("\n" + CYAN + line + RESET);
	    System.out.println("🛍️ " + YELLOW + "Starting Product Color Check:  Check Out  Page" + RESET);
	    System.out.println(CYAN + line + RESET);
	    
	     // ✅ Find the Check out card for this product
	    WebElement checkoutCard = driver.findElement(By.xpath(
	    	    "//div[contains(@class,'card_prod_combo_wrpr') and .//a[@class='cp_name' and normalize-space(text())='" + productName + "']]"
	    	));

	        System.out.println(GREEN + "🛒 Found checkout product card for: " + productName + RESET);

	     // ✅ Step 2: Find color text inside that card
	     WebElement checkoutColorElement = checkoutCard.findElement(
	         By.xpath(".//p[contains(@class,'cp_selected_color')]")
	     );
	     String checkoutColorText = checkoutColorElement.getText().trim();

	     System.out.println("🎨 Color shown in Checkout Page: " + checkoutColorText);

	     // ✅ Step 3: Compare color
	     if (checkoutColorText.equalsIgnoreCase(secondaryColor)) {
	         System.out.println(GREEN + "✅ Color matched in Checkout page: " + checkoutColorText + RESET);
	     } else {
	         System.out.println(RED + "❌ Color mismatch in Checkout page! Expected: "
	                 + secondaryColor + " | Found: " + checkoutColorText + RESET);
	         Assert.fail("Color mismatch in Checkout page! Expected: "
	                 + secondaryColor + " | Found: " + checkoutColorText);
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
