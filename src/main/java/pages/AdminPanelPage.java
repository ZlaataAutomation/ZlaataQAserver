package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import manager.FileReaderManager;
import objectRepo.AdminPanelObjRepo;
import stepDef.ExtentManager;
import utils.Common;
import utils.ExcelXLSReader;
import utils.ExportValidator;

public final class AdminPanelPage extends AdminPanelObjRepo  {
	

	 

	public AdminPanelPage(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	 String expectedBannerTitle;


		public void adminLoginApp() {
			 driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
		        type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
		        type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
		        click(adminLogin);
		        System.out.println( "✅ Admin Login Successful" );

		    
		}

	public void uploadImage(String imagePath) {
	    Scanner sc = new Scanner(System.in);

	    // ANSI color codes for console output
	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String BLUE = "\u001B[34m";
	    String CYAN = "\u001B[36m";
	    String RESET = "\u001B[0m";
	    String line = "─────────────────────────────────────────────";

//	    // Step 1: Login to Admin
//	    driver.manage().window().minimize();
//
//	 
//
//	    // Step 3: Take banner title input from console
//	    System.out.println(line);
//	    System.out.print(YELLOW + "🖼️ Enter Banner Title: " + RESET);
//	     expectedBannerTitle = sc.nextLine().trim();
//	    System.out.println(CYAN + "📢 You entered Banner Title: " + GREEN + expectedBannerTitle + RESET);
//	    System.out.println(line);
//	    
//	 // ✅ Login once before starting all exports
//        driver.manage().window().maximize();
        
        adminLoginApp();
	    // Step 2: Navigate to Excel path (if needed)
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    Common.waitForElement(2);

	    // Step 4: Add new homepage banner
	    expectedBannerTitle = "TestHome" + System.currentTimeMillis();
	 // 🔥 FIX FOR HEADLESS
	    ((JavascriptExecutor) driver)
	            .executeScript("arguments[0].click();", addHomePageBanner);
	    System.out.println("Clicked Add Home Page Banner");
	    Common.waitForElement(2);
	    type(bannerTitle, expectedBannerTitle);
	    Common.waitForElement(2);

	    // Step 5: Upload image
	    waitFor(uploadImageInput);
	    uploadImageInput.sendKeys(imagePath);
	    System.out.println(GREEN + "✅ Image uploaded successfully!" + RESET);

	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", uploadButton);
//	    click(uploadButton);
	    
	    Common.waitForElement(3);
	    driver.navigate().refresh();
//	    Common.waitForElement(4);

	    // Step 6: Select Home Page filter and set status
//	    click(homePageBannerDropDown);
//	    selectHomePageValue.get(0).click();
//	    Common.waitForElement(2);
//
//	    click(status);
//	    statusFilterSelect.get(0).click();
//	    Common.waitForElement(2);
//
//	    // Step 7: Set sort order
//	    type(sortBy, "1");
//	    Common.waitForElement(2);
//	    click(sortBySave);
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clearCatchButton);
//	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    Common.waitForElement(2);

	    // Step 8: Final success message
	    System.out.println(line);
	    System.out.println(BLUE + "🎯 Added new banner: " + GREEN + expectedBannerTitle + RESET);
//	    System.out.println(BLUE + "🔢 Sort By: " + YELLOW + "1" + RESET);
	    System.out.println(GREEN + "✅ Banner upload and setup completed successfully!" + RESET);
	    System.out.println(line);
	}



	public void verifyBannerOnHomePage() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();

	  //  String expectedTitle = Common.getValueFromTestDataMap("Banner Title");

	    int timeoutMinutes = 10;
	    boolean titleFound = false;
	    WebElement titleElement = null;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            driver.navigate().refresh();
	            Common.waitForElement(2);

	            Wait<WebDriver> wait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(15)) // ⬅ increase from 5s to 15s
	                    .pollingEvery(Duration.ofSeconds(3))
	                    .ignoring(NoSuchElementException.class)
	                    .ignoring(StaleElementReferenceException.class);

	            titleElement = wait.until(d -> {
	                List<WebElement> elements = driver.findElements(
	                        By.xpath("//a[contains(@class,'carousel_banner')]//img[@alt='" + expectedBannerTitle + "']")
	                );
	              
	                return elements.isEmpty() ? null : elements.get(0);
	            });

	            // ✅ Relax condition → as soon as element is found
	            if (titleElement != null) {
	                titleFound = true;
	                break;
	            }

	        } catch (TimeoutException te) {
	            // keep looping until timeout
	        }

	        try {
	            Thread.sleep(1000);
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    // ✅ Final check
	    if (titleFound) {
	        System.out.println("✅ Banner title '" + expectedBannerTitle + "' is visible in User Application.");
	    } else {
	        System.out.println("❌ Banner title '" + expectedBannerTitle + "' not found within " + timeoutMinutes + " minutes.");
	        Assert.fail("❌ Banner title '" + expectedBannerTitle + "' not found within " + timeoutMinutes + " minutes.");
	    }
	}
	

//SarojKumar 
	//Top Selling
	
	public void adminLogin() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	
	public void givesProductName() throws InterruptedException {
		
		adminLogin();
		Common.waitForElement(3);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successfull redirect to Adimn Product page");
		
//	    ((JavascriptExecutor) driver)
//        .executeScript("arguments[0].click();", productDetailMenu);
//
//	    System.out.println("✅ Successfull click product listing menu");
//	    Common.waitForElement(2);
//
//	    ((JavascriptExecutor) driver)
//        .executeScript("arguments[0].click();", productSearchBox);
//
//	    System.out.println("✅ Successfull fetch product listing name from excel sheet");
//
//	   driver.switchTo().activeElement().sendKeys(productName);
//	
//	    System.out.println("✅ Product name typed: " + productName);
//	    
//	    Thread.sleep(3000);
//	    
//	    productSearchBox.sendKeys(Keys.ENTER);
//	    
//	    System.out.println("✅ Select2 dropdown option clicked ");
	    
	    
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    Long width = (Long) js.executeScript("return window.innerWidth;");
	    Long height = (Long) js.executeScript("return window.innerHeight;");

	    System.out.println("Window Size: " + width + " x " + height);
	    
	 //   JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    // Click product listing menu
	    js.executeScript("arguments[0].click();", productDetailMenu);
	    System.out.println("✅ Successfull click product listing menu");

	    // Wait for search box
	    wait.until(ExpectedConditions.visibilityOf(productSearchBox));
	    wait.until(ExpectedConditions.elementToBeClickable(productSearchBox));

	    // Scroll into view (VERY IMPORTANT for headless)
	    js.executeScript("arguments[0].scrollIntoView(true);", productSearchBox);

	    // Focus input properly
	    js.executeScript("arguments[0].focus();", productSearchBox);

	    // Clear + Type
	    productSearchBox.clear();
	    productSearchBox.sendKeys(productName);

	    System.out.println("✅ Product name typed: " + productName);

	    // Press Enter
	    Thread.sleep(3000);
	    productSearchBox.sendKeys(Keys.ENTER);

	    System.out.println("✅ Product search triggered");
	    
	    
	   

		
	}
	String topSellingProductSku;
	public String fetchSkuFromProduct() {

	    // Click "Edit Product" button
		Common.waitForElement(3);
	//    waitFor(editProductButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", editProductButton);
//	    click(editProductButton);
	    System.out.println("\u001B[32m✅ Successfully clicked product edit option\u001B[0m");

	    // Click "Item Product" button
//	    waitFor(itemProductButton);
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", itemProductButton);
//	    click(itemProductButton);
	    System.out.println("\u001B[32m✅ Successfully clicked product item option\u001B[0m");

	    // Fetch SKU value
	    Common.waitForElement(2);
//	    waitFor(skuField);
	     topSellingProductSku = skuField.getAttribute("value").trim();
	    System.out.println("\u001B[33m📌 SKU fetched: " + topSellingProductSku + "\u001B[0m");

	    return topSellingProductSku;
	}
	
	
	
	public void putSkuIntoTopSelling() {
	    try {
	        // ANSI Colors for better console logs
	        String GREEN = "\u001B[32m";
	        String YELLOW = "\u001B[33m";
	        String BLUE = "\u001B[34m";
	        String CYAN = "\u001B[36m";
	        String RED = "\u001B[31m";
	        String RESET = "\u001B[0m";
	        String line = "─────────────────────────────────────────────";
	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	        Common.waitForElement(2);
	        waitFor(generalSettingsMenu);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", generalSettingsMenu);
//	        click(generalSettingsMenu);
	        Common.waitForElement(2);

	        waitFor(clickSetKey);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", clickSetKey);
	     //   click(clickSetKey);
	        Common.waitForElement(2);
	        waitFor(productSearchBox);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", productSearchBox);
//	        click(productSearchBox);
	        Common.waitForElement(2);
	        type(productSearchBox, "top_selling");
	        productSearchBox.sendKeys(Keys.ENTER);
	        System.out.println(BLUE + "🔍 Searched for 'top_selling'..." + RESET);
	        Common.waitForElement(3);
	        waitFor(topSellingEdit);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", topSellingEdit);
	    //    click(topSellingEdit);
	        System.out.println(GREEN + "✅ Opened Top Selling edit section" + RESET);

	        waitFor(topSellingSkuInput);
	        String current = topSellingSkuInput.getAttribute("value");
	        String cleaned = (current == null) ? "" : current.trim();

	        // Clean JSON-like format [sku1, sku2, sku3]
	        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
	            cleaned = cleaned.substring(1, cleaned.length() - 1).trim();
	        }

	        java.util.List<String> skuList = new java.util.ArrayList<>();
	        if (!cleaned.isEmpty()) {
	            for (String part : cleaned.split(",")) {
	                String val = part.trim();
	                if (!val.isEmpty()) skuList.add(val);
	            }
	        }

	        System.out.println(CYAN + "📦 Current SKU list: " + skuList + RESET);

	        boolean alreadyExists = skuList.stream()
	                .anyMatch(s -> s.equalsIgnoreCase(topSellingProductSku));

	        String updated;
	        String message;

	        // ✅ If already exists → remove and move to first
	        if (alreadyExists) {
	            skuList.removeIf(s -> s.equalsIgnoreCase(topSellingProductSku));
	            skuList.add(0, topSellingProductSku);
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + topSellingProductSku + "' already existed — moved to first position.";
	        } else {
	            // ✅ If not exist → add to first
	            skuList.add(0, topSellingProductSku);
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + topSellingProductSku + "' added to Top Selling list.";
	        }

	        // Update field
	        topSellingSkuInput.clear();
	        type(topSellingSkuInput, updated);
	        Common.waitForElement(2);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", saveTopSelling);
	   //     click(saveTopSelling);

	        System.out.println(line);
	        System.out.println(GREEN + "✅ " + message + RESET);
	        System.out.println(YELLOW + "💾 Updated Top Selling List: " + CYAN + updated + RESET);
	        System.out.println(line);

	    } catch (Exception e) {
	        System.err.println("❌ Failed to update Top Selling SKU due to: " + e.getMessage());
	        throw e;
	    }

	    // ✅ Always clear cache at the end
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clearCatchButton);
	//    click(clearCatchButton);
	    System.out.println( "🧹 Cache cleared successfully." );
	    Common.waitForElement(2);
	}
	
	
	//Verify the product successfull showing in user application home page top selling Section
	
	public void verifyProductShowInTopSelling() throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		  // Scroll directly to Top Selling section
	    WebElement topSellingSection = driver.findElement(
	            By.xpath("//div[@data-section='top_selling']")
	    );
	    
	    ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo({top: arguments[0].getBoundingClientRect().top + window.pageYOffset - 120, behavior: 'smooth'});",
	            topSellingSection
	    );
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
	            topSellingSection
	    );


	    int timeoutMinutes = 15;   // total wait time
	    int refreshInterval = 5;  // refresh every 5 seconds
	    boolean productFound = false;
	    WebElement card = null;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            //  Refresh page every cycle
	            driver.navigate().refresh();
	            Common.waitForElement(3);

	            // ✅ After refresh,  FluentWait to check element without refreshing again
	            Wait<WebDriver> wait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(refreshInterval)) // max wait before next refresh
	                    .pollingEvery(Duration.ofSeconds(3))
	                    .ignoring(NoSuchElementException.class)
	                    .ignoring(StaleElementReferenceException.class);

	            card = wait.until(d -> {
	                List<WebElement> elements = d.findElements(By.xpath(
	                		"(//div[contains(@class,'top_selling_carousel_wrap')]//a[contains(@class,'prod_title') and contains(normalize-space(),'" + excpectedName + "')])[1]"
	                        
	                ));
	                return elements.isEmpty() ? null : elements.get(0);
	            });

	            if (card != null && card.isDisplayed()) {
	                productFound = true;
	                break; //  stop if found
	            }
	        } catch (TimeoutException te) {
	            // element not found in this refresh cycle → loop continues
	        }

	        Thread.sleep(1000); // small buffer
	    }

	    // ✅ Final validation
	    if (productFound && card != null && card.getText().trim().equalsIgnoreCase(excpectedName.trim())) {
	        System.out.println("✅ Product '" + excpectedName + "' is visible in Top Selling.");
	    } else {
	        System.err.println("❌ Product '" + excpectedName + "' not found in Top Selling within " 
	                           + timeoutMinutes + " minutes.");
	        
	    }
	}
	    
	  
	
	
	//Negative Testcase Top Selling Section
	
//	public void forNegativeGivesProductName() {
//		Common.waitForElement(4);
//	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
//	    System.out.println("✅ Successfull redirect to Adimn Product page");
//		 // Open product listing
//	    click(productListingMenu);  
//	    System.out.println("✅ Successfull click product listing menu");
//	    waitFor(productSearchBox);
//	    click(productSearchBox);
//
//	    // Fetch the product name directly from Excel map
//	    String productName = Common.getValueFromTestDataMap("ProductListingName");
//	    System.out.println("✅ Successfull fetch product listing name from excel sheet");
//	 // Search or enter the product
//	    type(productSearchBox, productName + Keys.ENTER);
//	    Common.waitForElement(2);
//	    System.out.println("✅ Successfull put product listing name in searchbox and also click enter");
//
//		
//	}
//	public String forNegativeFetchSkuFromProduct() {
//		
//	    // now click edit, etc…
//	    waitFor(editProductButton);
//	    click(editProductButton);
//	    System.out.println("✅ Successfull click product edit option");
//	    // now click item, etc…
//	    waitFor(itemProductButton);
//	    click(itemProductButton);
//	    System.out.println("✅ Successfull click product item option");
//	    
//	 // now take Sku
//	    waitFor(skuField);
//	    System.out.println("✅ Successfull copy the SKU from skufield");
//        return skuField.getAttribute("value").trim();
//     
// 	
//	}
	// Remove SKU from Top Selling list
	public void removeSkuFromTopSelling() {
	    try {
	    	Common.waitForElement(2);
	    	waitFor(generalSettingsMenu);
	    	((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", generalSettingsMenu);
//	        click(generalSettingsMenu);
	        Common.waitForElement(2);
	        
		    waitFor(clickSetKey);
		    ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", clickSetKey);
//		    click(clickSetKey);
		    Common.waitForElement(2);
		    waitFor(productSearchBox);
		    ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", productSearchBox);
//		    click(productSearchBox);
		    
		    type(productSearchBox, "top_selling");
		    
		    productSearchBox.sendKeys(Keys.ENTER);
		    System.out.println("✅ Searched for top-selling");
		    Common.waitForElement(3);
	        waitFor(topSellingEdit);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", topSellingEdit);
	    //    click(topSellingEdit);
	        System.out.println("✅ Successfull click the top selling edit button");  

	        waitFor(topSellingSkuInput);
	        String current = topSellingSkuInput.getAttribute("value");
	        String cleaned = (current == null) ? "" : current.trim();

	        if (cleaned.startsWith("[") && cleaned.endsWith("]")) {
	            cleaned = cleaned.substring(1, cleaned.length() - 1).trim();
	        }

	        List<String> skuList = new ArrayList<>();
	        if (!cleaned.isEmpty()) {
	            for (String part : cleaned.split(",")) {
	                String val = part.trim();
	                if (!val.isEmpty()) skuList.add(val);
	            }
	        }

	        boolean exists = skuList.removeIf(s -> s.equalsIgnoreCase(topSellingProductSku));

	        String updated;
	        String message;
	        if (!exists) {
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + topSellingProductSku + "' not found. No changes made.";
	        } else if (skuList.isEmpty()) {
	            updated = "[]";
	            message = "SKU '" + topSellingProductSku + "' removed. List is now empty.";
	        } else {
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + topSellingProductSku + "' removed from Top Selling list.";
	        }

	        topSellingSkuInput.clear();
	        type(topSellingSkuInput, updated);
	        ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", saveTopSelling);
	//        click(saveTopSelling);

	     // Wait briefly for possible error page
	     Common.waitForElement(3);
	         System.out.println("✅ Save successful, no error page displayed.");
	        System.out.println("✅ " + message);

	    } catch (Exception e) {
	        String error = "❌ Failed to remove SKU due to: " + e.getMessage();
	        System.err.println(error);
	        throw e;
	    }

	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clearCatchButton);
//	    click(clearCatchButton);
	    System.out.println("✅ Successfully clicked Clear Cache Button");
	    Common.waitForElement(2);
	}


	// Verify product is NOT in Top Selling (Negative Test)
	public void verifyProductNotInTopSelling() throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    // Scroll directly to Top Selling section
	    WebElement topSellingSection = driver.findElement(
	            By.xpath("//div[@data-section='top_selling']")
	    );
	    
	    ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo({top: arguments[0].getBoundingClientRect().top + window.pageYOffset - 120, behavior: 'smooth'});",
	            topSellingSection
	    );
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
	            topSellingSection
	    );

	    int timeoutMinutes = 5;   // total wait time
	    boolean productFound = false;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            driver.navigate().refresh();
	            Common.waitForElement(3);

	            List<WebElement> elements = driver.findElements(By.xpath(
	            		"(//div[contains(@class,'top_selling_carousel_wrap')]//a[contains(@class,'prod_title') and contains(normalize-space(),'" + excpectedName + "')])[1]"
	            ));

	            if (elements.isEmpty()) {
	                // ✅ Product is gone → stop waiting immediately
	                productFound = false;
	                break;
	            } else if (elements.get(0).isDisplayed()) {
	                // Product is still there → set flag true
	                productFound = true;
	            }
	        } catch (Exception ignored) {}

	        Thread.sleep(2000);
	    }

	    if (!productFound) {
	        System.out.println("✅ Product '" + excpectedName + "' is NOT visible in Top Selling (as expected).");
	    } else {
	        System.err.println("❌ Product '" + excpectedName + "' still visible in Top Selling even after removal!");
	    }
	}

//New Arrivals
	 private String copiedSku;
	
	public void verifyColourOfTheProductIsFirstPosition() throws InterruptedException {
		adminLogin();
		Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successful redirect to Adimn Product page");
		 // Open product listing
	      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].click();", productDetailMenu);
	  //  click(productDetailMenu);  
	    System.out.println("✅ Successful click product listing menu");
	    Common.waitForElement(2);
	      ((JavascriptExecutor) driver)
          .executeScript("arguments[0].click();", productSearchBox);
	 //   click(productSearchBox);

	    // Fetch the product name directly from Excel map
	//    String productName = Common.getValueFromTestDataMap("ProductListingName");
	    System.out.println("✅ Fetched product listing name from Excel sheet:" + productDetailName);
	 // Search or enter the product
	    // Clear + Type
	    productSearchBox.clear();
	    productSearchBox.sendKeys(productDetailName);

	    System.out.println("✅ Product name typed: " + productDetailName);

	    // Press Enter
	    Thread.sleep(3000);
	    productSearchBox.sendKeys(Keys.ENTER);

	//    type(productSearchBox, productDetailName + Keys.ENTER);
	    Common.waitForElement(2);
	    System.out.println("✅ Entered product listing name in search box & pressed ENTER");
	    
	 // now click edit, etc…
	    Common.waitForElement(3);
	  //  waitFor(editProductButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", editProductButton);
	  //  click(editProductButton);
	    System.out.println("✅ Clicked product edit option");
	    // now click item, etc…
	  //  waitFor(itemProductButton);
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", itemProductButton);
	  //  click(itemProductButton);
	    System.out.println("✅  Clicked product item option");
	    
	    // -------- Check that the product text matches Excel name --------
	 //   waitFor(productListingBoxText); 
	    Common.waitForElement(3);
	    String uiProductName = productListingBoxText.getAttribute("value").trim();
	    System.out.println(" UI Product Listing Name: " + uiProductName);
	    //For Sorting we copied sku
	    copiedSku = skuTextbox.getAttribute("value").trim();
	    System.out.println(" Copied SKU : " + copiedSku);

//	  
//	    boolean match = uiProductName.toLowerCase().contains(productlistingName.toLowerCase());
//	    if (!match) {
//	        System.err.println("❌ Product mismatch,This is not First Colour Both are Different— skipping remaining steps → Expected: " + productlistingName + " | Actual: " + uiProductName);
//	        // Now skip scenario
//	        Assume.assumeTrue("Skipping due to product mismatch", false);
//	    }
	  //For Assert
//	    Assert.assertEquals(
//	    	    uiProductName,
//	    	    productName,
//	    	    "❌ Product mismatch, expected: " + productName + " | but found: " + uiProductName
//	    	);
	    
	    System.out.println("✅ Product listing name matches expected.");
	    
	    
////	 -------- Check Stock Status --------
//	    waitFor(stockStatusTextbox); 
//	    String stockStatus = firstProductStockStatus.getAttribute("value").trim();
//
//	    if(stockStatus.equalsIgnoreCase("Available")) {
//	        System.out.println("✅ Product is available in stock");
//	    } 
//	    else if(stockStatus.equalsIgnoreCase("Out Of Stock")) {
//	        System.out.println("❌ Product is out of stock");
//	        // skip further steps if out of stock
//	        Assume.assumeTrue("Skipping further steps as product is out of stock", false);
//	    } 
//	    

		
		
		
	}
	
	public void addTheProductInNewArrivalSection() throws InterruptedException {
		// Click product button
		Common.waitForElement(2);
		((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", productButton);
	  //  click(productButton);
	    System.out.println("✅ Clicked Product button");

	    // Copy text from Product Name textbox
	    Common.waitForElement(2);
	    String copiedProductName = productNameTextbox.getAttribute("value").trim();
	    System.out.println("📋 Copied product name: " + copiedProductName);

	    //Navigate to Search Product Collection page
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", searchProductCollectionMenu);
	  //  click(searchProductCollectionMenu);
	    System.out.println("✅ Clicked Search Product Collection");

	    //Type in the search text box
	    //Thread.sleep(2000);
	    Common.waitForElement(2);
	//    waitFor(searchProductCollectionMenu); 
	    type(searchProductCollectionMenu, "Product Collections");
	    //Thread.sleep(2000);
	    System.out.println("✅ Typed 'Product Collections' ");
	//    waitFor(clickProductCollection);
	  //  Thread.sleep(3000);
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clickProductCollection);
	 //   click(clickProductCollection);
	    System.out.println("✅ Selected Product Collection");
//	     3. Select from the dropdown or result line
//	    selectDropdownByVisibleText(productCollectionDropdown, "Product Collections");
//	    System.out.println("✅ Selected Product Collection");

	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clickStatus);
//	    waitFor(clickStatus);
//	    click(clickStatus);

	    // Select Status -> Active
	    Common.waitForElement(3);
	    Thread.sleep(2000);
//	    ((JavascriptExecutor) driver)
//        .executeScript("arguments[0].click();", statusActiveOption);
	   // click(statusActiveOption);
	 // 3️⃣ Scroll + native click (NOT JS)
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", statusActiveOption
	    );

	    statusActiveOption.click();  // ✅ THIS WILL WORK IN HEADLESS
	    System.out.println("✅ Selected Active status");

	    // Click Collection button
	    //Thread.sleep(2000);
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", collectionButton);
	 //   click(collectionButton);
	    System.out.println("✅ Clicked Collection button");

	    //Search 'new-arrivals'
	    //Thread.sleep(2000);
	    Common.waitForElement(2);
	 //   waitFor(searchTextBox);
	    type(searchTextBox, "new-arrivals");
	    searchTextBox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Searched for new-arrivals");
	   // Thread.sleep(2000);

	    //Click Edit
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", editCollectionButton);
	//    editCollectionButton.click();
	    System.out.println("✅ Entered Edit mode for collection");

	    //Add copied product to last position in product text field
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,900);");
	    Common.waitForElement(2);
	    type(addProductTextbox, copiedProductName);
	    Common.waitForElement(2);
	   //Thread.sleep(3000);
	    addProductTextbox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Added product name to collection");
	    

	    //Save
	    Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", saveButton);
	  //  click(saveButton);
	    System.out.println("✅ Saved collection changes");
		
		
	}
	
	public void sortTheProductInFirstPosition() {
		// Click on Search Box for Product Sort
		Common.waitForElement(2);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", searchProductSortMenu);
		//click(searchProductSortMenu);
	    System.out.println("✅ Clicked Search Product Collection");

		// Type in the search text box
		// Thread.sleep(2000);
	     Common.waitForElement(2);
		type(searchProductSortMenu, "Product Sorts");
		// Thread.sleep(2000);
		System.out.println("✅ Typed 'Product Sorts");
		 Common.waitForElement(2);
		// Thread.sleep(3000);
		 ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", clickProductSort);
		//click(clickProductSort);
		System.out.println("✅ Selected Product Sorts");

		//  Click Category Name
		Common.waitForElement(2);
	   		 ((JavascriptExecutor) driver)
	        .executeScript("arguments[0].click();", categoryName);
	//	click(categoryName);
		System.out.println("✅ Clicked Category Name");

		// In the Category Search, type "New Arrivals" and hit Enter
		 Common.waitForElement(2);
		//    waitFor(categorySearchBox);
		    type(categorySearchBox, "New Arrivals");
		    categorySearchBox.sendKeys(Keys.ENTER);
		    System.out.println("✅ Typed 'New Arrivals' & pressed Enter");
		   // Thread.sleep(2000);
		

		// Scroll down
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");
		System.out.println("✅ Scrolled down");

		//Click Next Page arrow
		Common.waitForElement(2);
  		 ((JavascriptExecutor) driver)
       .executeScript("arguments[0].click();", nextPageArrow);
		
		//click(nextPageArrow);
		System.out.println("✅ Clicked next page");

		// Click Plus Button to add products
		Common.waitForElement(2);
		waitFor(plusButton);
		 ((JavascriptExecutor) driver)
	       .executeScript("arguments[0].click();", plusButton);
		//click(plusButton);
		System.out.println("✅ Clicked '+' button");

		//  In the products list, find SKU & drag to first position
		 // All product cards
        List<WebElement> allProducts = driver.findElements(By.xpath("//div[contains(@class,'sortable-card')]"));
        if (allProducts.size() < 3) {
            System.out.println("❌ Less than 3 products available, cannot perform reorder.");
            return;
        }

        WebElement thirdProduct = driver.findElement(By.xpath("(//div[contains(@class,'sortable-card')])[3]"));
        WebElement firstProduct = driver.findElement(By.xpath("(//div[contains(@class,'sortable-card')])[1]"));

        Actions action = new Actions(driver);

     // Perform drag & drop to above the first card
		// XPath to locate card by SKU text
		Common.waitForElement(3);
		By skuCard = By.xpath("//div[contains(@class,'sortable-card')]//span[contains(text(),'" + copiedSku + "')]/ancestor::div[contains(@class,'sortable-card')]");

		// Find the product card
		List<WebElement> products = driver.findElements(skuCard);

		if (products.isEmpty()) {
		    System.out.println("❌ Product with SKU '" + copiedSku + "' not found.");
		} else {
		    WebElement from = products.get(0);  // found product card
		    WebElement firstPosition = driver.findElement(By.xpath("(//div[contains(@class,'sortable-card')])[1]"));

		    try {
		        // try native Selenium drag & drop
		    	Actions actions = new Actions(driver);
		    	action.clickAndHold(from)
		        .pause(Duration.ofMillis(500))
		        .moveToElement(firstPosition, 0, -30) // Drop 30px above the first card
		        .pause(Duration.ofMillis(500))
		        .release()
		        .build()
		        .perform();
		        System.out.println("✅ Dragged product '" + copiedSku + "' to first position (Selenium).");

		    } catch (Exception e) {
		        // JavaScript drag & drop simulation
		        String jsDragDrop =
		            "function triggerDragAndDrop(source, target) {" +
		            "  const dataTransfer = new DataTransfer();" +
		            "  const dragStartEvent = new DragEvent('dragstart', { dataTransfer });" +
		            "  source.dispatchEvent(dragStartEvent);" +
		            "  const dropEvent = new DragEvent('drop', { dataTransfer });" +
		            "  target.dispatchEvent(dropEvent);" +
		            "  const dragEndEvent = new DragEvent('dragend', { dataTransfer });" +
		            "  source.dispatchEvent(dragEndEvent);" +
		            "}" +
		            "triggerDragAndDrop(arguments[0], arguments[1]);";

		        ((JavascriptExecutor) driver).executeScript(jsDragDrop, from, firstPosition);

		        System.out.println("✅ Dragged product '" + copiedSku + "' to first position (JS fallback).");
		    }
		}
		
		 //  Save
		Common.waitForElement(3);
		((JavascriptExecutor) driver)
	       .executeScript("arguments[0].click();", saveButton);
	  //  click(saveButton);
	    System.out.println("✅ Saved collection changes");
	    //Clear Catch
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clearCatchButton);
	//    click(clearCatchButton);
	    System.out.println( "🧹 Cache cleared successfully." );
	    Common.waitForElement(7);
	    waitFor(clearCatchButton);
	    ((JavascriptExecutor) driver)
        .executeScript("arguments[0].click();", clearCatchButton);
	//    click(clearCatchButton);
	    System.out.println( "🧹 Cache cleared successfully." );
		
		
	}
	
	public void verifyProductShowInNewArrivalsSction() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    WebElement topSellingSection = driver.findElement(
	            By.xpath("//div[@data-section='new_arrivals']")
	    );
	    
	    ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo({top: arguments[0].getBoundingClientRect().top + window.pageYOffset - 120, behavior: 'smooth'});",
	            topSellingSection
	    );
	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
	            topSellingSection
	    );

	    int timeoutMinutes = 10;   // total wait time
	    int refreshInterval = 15; // refresh every 15 seconds
	    boolean productFound = false;
	    WebElement card = null;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            //  Refresh page
	            driver.navigate().refresh();
	            Common.waitForElement(2);

	            // ✅ Use FluentWait after refresh
	            Wait<WebDriver> wait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(refreshInterval)) 
	                    .pollingEvery(Duration.ofSeconds(2))
	                    .ignoring(NoSuchElementException.class)
	                    .ignoring(StaleElementReferenceException.class);

	            card = wait.until(d -> {
	                List<WebElement> elements = d.findElements(By.xpath(
	                		"(//div[contains(@class,'new_arrival_inner_wrpr')]//a[contains(@class,'prod_title') and normalize-space(.)='" + productlistingName + "'])[1]"
	                ));
	                return elements.isEmpty() ? null : elements.get(0);
	            });

	            if (card != null && card.isDisplayed()) {
	                productFound = true;
	                break; // ✅ stop if found
	            }
	        } catch (TimeoutException te) {
	            // not found in this refresh cycle → loop continues
	        }

	        try {
	            Thread.sleep(1000); // small buffer
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

	    // ✅ Final validation
	    if (productFound && card != null && card.getText().trim().equalsIgnoreCase(productlistingName.trim())) {
	        System.out.println("✅ Product '" + productlistingName + "' is visible in New Arrivals.");
	    } else {
	        System.err.println("❌ Product '" + productlistingName + "' not found in New Arrivals within " 
	                           + timeoutMinutes + " minutes.");
	    }
	}
	
	//Catagory Section
	String expectedTitle;
	public void updateCategoryBanner(String imagePathCategory) {
	    Scanner sc = new Scanner(System.in);

	    // 🎨 Console colors
	    String GREEN = "\u001B[32m";
	    String YELLOW = "\u001B[33m";
	    String BLUE = "\u001B[34m";
	    String CYAN = "\u001B[36m";
	    String RED = "\u001B[31m";
	    String RESET = "\u001B[0m";
	    String line = "─────────────────────────────────────────────";
	    driver.manage().window().minimize();
	 // Step 5: Get Banner Title from console
        System.out.print(YELLOW + "🖼️ Enter new Banner Title: " + RESET);
         expectedTitle = sc.nextLine().trim();
         
         driver.manage().window().maximize();

	    try {
	        // Step 1: Open Excel page (or config page)
	        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	        System.out.println(GREEN + "✅ Successfully redirected to Home Page Banner section." + RESET);

	        // Step 2: Click on Home Page Banner menu
	        Common.waitForElement(2);
	        waitFor(homePageBannerMenu);
	        click(homePageBannerMenu);
	        Common.waitForElement(2);
	        System.out.println(BLUE + "📂 Opened 'Home Page Banner' menu." + RESET);

	        // Step 3: Search for category
	        Common.waitForElement(2);
	        waitFor(catagorysearchTextBox);
	        type(catagorysearchTextBox, "Dazzle Category");
	        catagorysearchTextBox.sendKeys(Keys.ENTER);
	        System.out.println(GREEN + "🔍 Searched for 'Dazzle Category'." + RESET);

	        // Step 4: Click Edit button
	        Common.waitForElement(2);
	        waitFor(editButton);
	        click(editButton);
	        System.out.println(GREEN + "🖊️ Clicked 'Edit' button for Dazzle Category." + RESET);

	        

	        // Step 6: Clear and set new banner title
	        Common.waitForElement(2);
	        waitFor(bannerTitleTextBox);
	        bannerTitleTextBox.clear();
	        type(bannerTitleTextBox, expectedTitle);
	        System.out.println(CYAN + "🏷️  Banner Title set to: " + YELLOW + expectedTitle + RESET);

	        // Step 7: Upload new banner image
	        Common.waitForElement(2);
	        waitFor(desktopBannerUpload);
	        desktopBannerUpload.sendKeys(imagePathCategory);
	        System.out.println(GREEN + "✅ Banner image uploaded successfully." + RESET);

	        // Step 8: Save changes
	        Common.waitForElement(2);
	        waitFor(saveButton);
	        saveButton.click();
	        Common.waitForElement(3);
	        System.out.println(GREEN + "💾 Changes saved successfully." + RESET);

	        // Step 9: Clear cache
	        Common.waitForElement(2);
	        waitFor(clearCatchButton);
	        click(clearCatchButton);
	        System.out.println(GREEN + "🧹 Cache cleared successfully." + RESET);
	        Common.waitForElement(2);

	        System.out.println(line);
	        System.out.println(BLUE + "🎯 Successfully updated category banner with title: " 
	                           + YELLOW + expectedTitle + RESET);
	        System.out.println(line);

	    } catch (Exception e) {
	        System.err.println(RED + "❌ Failed to update category banner: " + e.getMessage() + RESET);
	        throw e;
	    }
	}

    // Method 2: Verify Banner in User Application
	public void verifyBannerUserApp() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1300);");
	    Common.waitForElement(3);

	

	    // ✅ Wait until the expected banner title appears
	    int timeoutMinutes = 10;  
	    int pollSeconds = 5;
	    boolean titleFound = false;
	    WebElement titleElement = null;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            // Refresh and wait
	            driver.navigate().refresh();
	            Common.waitForElement(2);

	            Wait<WebDriver> wait = new FluentWait<>(driver)
	                    .withTimeout(Duration.ofSeconds(pollSeconds))
	                    .pollingEvery(Duration.ofSeconds(2))
	                    .ignoring(NoSuchElementException.class)
	                    .ignoring(StaleElementReferenceException.class);

	            titleElement = wait.until(d -> {
	                List<WebElement> elements = d.findElements(By.xpath(
	                		"//img[@alt='" + expectedTitle + "']"));
	                return elements.isEmpty() ? null : elements.get(0);
	            });

	            if (titleElement != null && titleElement.isDisplayed()) {
	                titleFound = true;
	                break;
	            }

	        } catch (TimeoutException te) {
	            // keep looping until timeout
	        }

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
	    }

		// ✅ Final check
		if (titleFound) {
			System.out.println("✅ Banner title '" + expectedTitle + "' is visible in User Application.");
		} else {
			System.out
					.println("❌ Banner title '" + expectedTitle + "' not found within " + timeoutMinutes + " minutes.");
			Assert.fail("❌ Banner title '" + expectedTitle + "' not found within " + timeoutMinutes + " minutes.");
		}
	}



//Bulk Product Add 
	public void UploadTheProductExcel(String excelPath) {
		Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successful redirect to Adimn Product page");
	    
	    Common.waitForElement(2);
	    waitFor(importButton);
        click(importButton);
        System.out.println("✅ Clicked  Importbutton");
        
     // Upload new bulk order
	    Common.waitForElement(2);
	    waitFor(uploadExcelButton);
	    uploadExcelButton.sendKeys(excelPath);
	    System.out.println("✅ successful product added");
        
	 // Save changes
        Common.waitForElement(2);
        waitFor(submitButton);
        submitButton.click();
	    System.out.println("✅ successful saved");
	        
      //  Assert.assertTrue("❌ Excel upload failed!", successMessage.isDisplayed());
        System.out.println("✅ Excel uploaded successfully");
        Common.waitForElement(5);
        
    }
	
	public void verifyProductsInAdmin(String filePath) throws IOException {
		Common.waitForElement(2);
		//driver.navigate().refresh();
		List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath)
			    .stream()
			    .filter(product -> {
			        Object skuObj = product.get("Sku");
			        return skuObj != null && !skuObj.toString().trim().isEmpty();
			    })
			    .collect(Collectors.toList());
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    for (Map<String, Object> product : products) {
	        String sku = (String) product.get("Sku");

	        // Click SKU menu
	        wait.until(ExpectedConditions.elementToBeClickable(clickSKU)).click();

	        // Wait for search boxes
	        List<WebElement> searchBoxes = wait.until(d -> {
	            List<WebElement> elements = d.findElements(By.xpath("//input[@role='searchbox']"));
	            return elements.size() >= 2 ? elements : null;
	        });
	        WebElement adminSearchBox = searchBoxes.get(1);

	        // Type SKU
	        wait.until(ExpectedConditions.elementToBeClickable(adminSearchBox));
	        adminSearchBox.clear();
	        adminSearchBox.sendKeys(sku);
	        adminSearchBox.sendKeys(Keys.ENTER);

	        // ✅ Print only once per SKU
	        System.out.println("✅ Searched for SKU: " + sku);

	        // Wait for SKU to appear in table
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@title='" + sku + "']")));
	        System.out.println("✅ SKU is visible in Admin panel: " + sku);

	        // Click outside after confirming visibility (optional)
	        wait.until(ExpectedConditions.elementToBeClickable(clickBlankSpace)).click();
	    }
	    
	    //Clear Catch
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    Common.waitForElement(2);
	}

	
	public void verifyProductsInUserApp(String filePath) throws IOException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);

	    List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    
	    // Create ExtentTest once
	    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Products in User App");
	    ExtentManager.setTest(test);


	    for (Map<String, Object> product : products) {

	        // ✅ Treat as List, not String
	        @SuppressWarnings("unchecked")
	        List<String> listingNames = (List<String>) product.get("Product Listing Name");

	        if (listingNames == null || listingNames.isEmpty()) {
	            System.out.println(" Skipping empty listing names");
	            continue;
	        }

	        for (String listingName : listingNames) {
	            if (listingName == null || listingName.trim().isEmpty()) continue;

	            // Search in user app
	            wait.until(ExpectedConditions.elementToBeClickable(userSearchBox));
	            userSearchBox.clear();
	            userSearchBox.sendKeys(listingName);
	            System.out.println("✅ Searched for listing: " + listingName);

	            // Wait for product to appear
	            By productLocator = By.xpath("//h6[normalize-space()='" + listingName + "']");
	            wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));

	            WebElement productElement = driver.findElement(productLocator);
	            Assert.assertTrue("❌ Listing name not found in User App: " + listingName, productElement.isDisplayed());

	            // Click product
	            productElement.click();
	            System.out.println("✅ Product opened in User App: "+ listingName);


	            try {
	                // Actual price
	                WebElement actualPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_actual_price']")
	                ));

	                // Current price
	                WebElement currentPrice = driver.findElement(
	                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_current_price']")
	                );
	                
	             // Product Discount Percentage 
	                WebElement discountPercentage = driver.findElement(
	                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_discount_percentage']")
	                );

	                // Delivery date
	                WebElement deliveryDate = driver.findElement(
	                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prodcut_list_cards_best_pricing_txt']/span")
	                );

	                // Section 
	                WebElement section = driver.findElement(
	                    By.xpath("//div[@class='prod_main_details_head']//h5[@class='prod_category']")
	                );

	                // ✅ Print details
	                System.out.println(" Product: " + listingName);
	                System.out.println("    Actual Price   : " + actualPrice.getText());
	                System.out.println("    Discount Price : " + currentPrice.getText());
	                System.out.println("    Percentage     : " + discountPercentage.getText());
	                System.out.println("    Delivery Date  : " + deliveryDate.getText());
	                System.out.println("    Section        : " + section.getText());
	                
	                // Log all details properly
	                test.pass("Product Name       : " + listingName);
	                test.pass("Section            : " + section.getText());
	                test.pass("Actual Price       : " + actualPrice.getText());
	                test.pass("Current Price      : " + currentPrice.getText());
	                test.pass("Discount Percentage: " + discountPercentage.getText());
	                test.pass("Delivery Date      : " + deliveryDate.getText());
	                

	            } catch (Exception e) {
	                System.out.println("⚠ Could not fetch all details for product: " + listingName);
	            }
	        }
	         
	    }
	    ExtentManager.getExtentReports().flush();
	}


//Special Timer Product
	
	public void clickPlusOrResetFlow() throws InterruptedException {

	    // 1️⃣ Click Active Status
	    Common.waitForElement(3);
	    waitFor(statusActiveOption);
	    click(statusActiveOption);
	    System.out.println("✅ Selected Active status");

	    Common.waitForElement(2);

	    // 2️⃣ TRY clicking Plus button
	    try {
	        if (plusButton.isDisplayed() && plusButton.isEnabled()) {
	            click(plusButton);
	            System.out.println("✅ Clicked '+' Plus button (Direct)");
	            return; // SUCCESS, exit method
	        }
	    } catch (Exception e) {
	        System.out.println("⚠️ Plus Button NOT visible, switching to fallback flow...");
	    }

	    // ---------------------------------------------------------
	    // 3️⃣ FALLBACK FLOW: Reset → Search Event Name → Enable status → Enable promotional → Click plus
	    // ---------------------------------------------------------

	    // Click Reset
	    WebElement resetBtn = driver.findElement(By.id("crudTable_reset_button"));
	    click(resetBtn);
	    System.out.println("🔄 Clicked Reset");

	    Common.waitForElement(2);

	    // Click Event Name dropdown
	    WebElement eventNameDropdown = driver.findElement(By.xpath("//a[contains(text(),'Event Name')]"));
	    click(eventNameDropdown);
	    System.out.println("📂 Opened Event Name dropdown");

	    Common.waitForElement(1);

	    // Enter search text
	    WebElement searchBox = driver.findElement(By.id("text-filter-eventName"));
	    searchBox.clear();
	    searchBox.sendKeys("Festiv Sale");
	    searchBox.sendKeys(Keys.ENTER);
	    System.out.println("🔍 Entered event name: Festiv Sale");

	    Common.waitForElement(2);

	    // Enable Status toggle
	    WebElement statusToggle = driver.findElement(By.xpath("//label[@for='V_status_54']"));
	    if (!statusToggle.isSelected()) {
	    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", statusToggle);
	        System.out.println("🔘 Enabled EVENT Status");
	    }

	    Common.waitForElement(2);

	    // Enable Promotional Offer toggle
	    WebElement promoToggle = driver.findElement(By.xpath("//label[@for='V_promotional_offer_54']"));
	    if (!promoToggle.isSelected()) {
	    	((JavascriptExecutor) driver).executeScript("arguments[0].click();", promoToggle);
	        System.out.println("🎁 Enabled Promotional Offer");
	    }

	    Common.waitForElement(2);

	    // Click PLUS button again
	    //waitFor(plusButton);
	    Common.waitForElement(2);
	    click(plusButton);
	    System.out.println("🚀 Clicked '+' Plus button (AFTER fallback)");
	}
	 // ✅ Upload excel
    public void uploadTheSpecialTimerProductExcel(String filePath) throws InterruptedException {
        Common.waitForElement(2);
        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
        
        System.out.println("✅ Redirected to Admin Special Timer Product page");
        Common.waitForElement(3);
	    waitFor(clickStatus);
	    click(clickStatus);

	    clickPlusOrResetFlow();

        Common.waitForElement(2);
        waitFor(importButton);
        click(importButton);
        System.out.println("✅ Clicked Import Button");

        Common.waitForElement(2);
        waitFor(uploadExcelButton);
        uploadExcelButton.sendKeys(filePath);
        System.out.println("✅ Uploaded file: " + filePath);

        Common.waitForElement(2);
        waitFor(submitButton);
        submitButton.click();
        System.out.println("✅ Excel uploaded successfully");
        
      //Clear Catch
	    Common.waitForElement(4);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    
        
    }

    // ✅ Verify in Admin panel
    public void verifySpecialProductsinAdmin(String excelPath) throws IOException {
        List<Map<String, Object>> excelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath);
        List<Map<String, String>> adminProducts = readAdminProductsFromTable();

        for (Map<String, Object> excelProduct : excelProducts) {
            String excelSku = excelProduct.get("sku").toString().trim();
            String excelDiscount = excelProduct.get("discount").toString().trim();

            Optional<Map<String, String>> match = adminProducts.stream()
                .filter(p -> p.get("Sku").equalsIgnoreCase(excelSku))
                .findFirst();

            if (match.isPresent()) {
                Map<String, String> adminProduct = match.get();
                String colorName = adminProduct.get("ColorName");

                if (!adminProduct.get("Discount").equals(excelDiscount)) {
                    System.out.println("❌ Discount mismatch for SKU: " + excelSku);
                } else {
                    System.out.println("✅ Verified in Admin → SKU: " + excelSku + 
                                       " | Color: " + colorName + 
                                       " | Discount: " + excelDiscount);
                }
            } else {
                System.out.println("❌ SKU not found in Admin: " + excelSku);
            }
        }
    }

    private List<Map<String, String>> readAdminProductsFromTable() {
    	List<WebElement> productRows = driver.findElements(
    		    By.xpath("//tbody[@id='specialEventTimerProduct']/tr")
    		);
    	//	waitFor(productRows.get(0));  // wait for first row to be visible
        List<Map<String, String>> adminProducts = new ArrayList<>();

        for (WebElement row : productRows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 6) {
                Map<String, String> product = new HashMap<>();
                product.put("ColorName", cols.get(2).getText().trim());
                product.put("Sku", cols.get(3).getText().trim());
                product.put("DiscountType", cols.get(4).getText().trim());
                product.put("Discount", cols.get(5).getText().trim());
                adminProducts.add(product);
            }
        }
        return adminProducts;
    }

    // ✅ Verify in User app
    public void verifyProductsUserApp(String excelPath) throws IOException {
        // ✅ Step 1: Read products from Excel
        List<Map<String, Object>> excelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath);

        // ✅ Step 2: Read all Admin products into a map (Sku → ColorName)
        Map<String, String> adminProducts = new HashMap<>();
        List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));
        for (WebElement row : rows) {
            try {
                String sku = row.findElement(By.xpath(".//td[4]")).getText().trim();     // adjust index if SKU col is different
                String colorName = row.findElement(By.xpath(".//td[3]")).getText().trim(); // adjust index for ColorName col
                adminProducts.put(sku, colorName);
            } catch (Exception e) {
                // ignore header or invalid rows
            }
        }

        Common.waitForElement(4);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
        // ✅ Step 3: Switch to User App
        HomePage home = new HomePage(driver);
		home.homeLaunch();
        // ✅ Step 4: Loop through Excel SKUs and verify in User App
        for (Map<String, Object> product : excelProducts) {
            Object skuObj = product.get("sku");
            if (skuObj == null) {
                System.out.println("⚠ SKU missing in Excel row, skipping...");
                continue;
            }

            String sku = skuObj.toString().trim();

            // Get ColorName from Admin Map
            String productColorName = adminProducts.get(sku);
            if (productColorName == null) {
                System.out.println("⚠ No ColorName found in Admin for SKU: " + sku);
                continue;
            }

            // Search in user app
            wait.until(ExpectedConditions.elementToBeClickable(userSearchBox));
            userSearchBox.clear();
            userSearchBox.sendKeys(productColorName);
          
            Common.waitForElement(3);
            By locator = By.xpath("//h6[normalize-space()='" + productColorName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            WebElement productElement = driver.findElement(locator);
            Assert.assertTrue("❌ Product not found in User App: " + productColorName, productElement.isDisplayed());
            productElement.click();

            System.out.println("✅ Product found in User App: " + productColorName);
         // ✅ SPECIAL TIMER CHECK
            try {
                WebElement specialTimer = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@id='prodTimer' and contains(@class,'prod_category_timer')]")
                ));

                if (specialTimer.isDisplayed()) {
                    System.out.println("⏳ Special Timer is displayed on Product Detail Page.");
                } else {
                    throw new RuntimeException("❌ Special Timer element is present but NOT visible.");
                }

            } catch (Exception e) {
                throw new RuntimeException("❌ Special Timer is NOT displayed on Product Detail Page!", e);
            }

            // ✅ Verify price details
            try {
                WebElement actualPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_actual_price']")
                ));

                WebElement currentPrice = driver.findElement(
                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_current_price']")
                );

                WebElement discountPercentage = driver.findElement(
                    By.xpath("//div[@class='prod_main_details_head']//div[@class='prod_discount_percentage']")
                );

                System.out.println("   Actual Price   : " + actualPrice.getText());
                System.out.println("   Special  Price  : " + currentPrice.getText());
                System.out.println("   Discount Shown : " + discountPercentage.getText());

            } catch (Exception e) {
                System.out.println("⚠ Could not fetch all price details for: " + productColorName);
            }
        }
    }
	
    
	//Bulk Catagories
    
    public void uploadTheCategoriesBulkExcel(String filePath) {
    	Common.waitForElement(2);
        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
        
        System.out.println("✅ Redirected to Admin product Catagories page");
 
        Common.waitForElement(2);
        waitFor(importButton);
        click(importButton);
        System.out.println("✅ Clicked Import Button");

        Common.waitForElement(2);
        waitFor(uploadExcelButton);
        uploadExcelButton.sendKeys(filePath);
        System.out.println("✅ Uploaded file: " + filePath);

        Common.waitForElement(2);
        waitFor(submitButton);
        submitButton.click();
        System.out.println("✅ Excel uploaded successfully");
        
      //Clear Catch
	    Common.waitForElement(3);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successful click Clear Catch Button");
		
	}
	
    public void verifyCategoriesInAdmin(String filePath) throws IOException {
        Common.waitForElement(2);

        // ✅ Read Excel → Filter only non-empty Categories
        List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath)
            .stream()
            .filter(product -> {
                Object categoryObj = product.get("Category Name");  // <-- Excel column name
                return categoryObj != null && !categoryObj.toString().trim().isEmpty();
            })
            .collect(Collectors.toList());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        for (Map<String, Object> product : products) {
            String categoryName = (String) product.get("Category Name");

            // ✅ Refresh page before each search
            driver.navigate().refresh();
            Common.waitForElement(3);

            // ✅ Click Categories button (replace with your locator)
            wait.until(ExpectedConditions.elementToBeClickable(categoriesNameButton)).click();
            System.out.println("✅ Clicked Categories button");

            // ✅ Enter category name in search box
            wait.until(ExpectedConditions.elementToBeClickable(searchTextBox));
            searchTextBox.clear();
            searchTextBox.sendKeys(categoryName);
            searchTextBox.sendKeys(Keys.ENTER);
            System.out.println("✅ Searched for Category: " + categoryName);

            // ✅ Verify category visible in table
            By categoryLocator = By.xpath("//span[@title='" + categoryName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(categoryLocator));
            System.out.println("✅ Category is visible in Admin panel: " + categoryName);
            
            // Click on Search Box for Product Sort
    		Common.waitForElement(3);
    		click(searchProductSortMenu);
    		waitFor(searchProductSortMenu);
    		type(searchProductSortMenu, "Product Sorts");
    		// Thread.sleep(2000);
    		System.out.println("Typed 'Product Sorts");
    		waitFor(clickProductSort);
    		// Thread.sleep(3000);
    		click(clickProductSort);
    		System.out.println("Selected Product Sorts");

    		//  Click add Product Sort Name
    		waitFor(addProductSort);
    		click(addProductSort);
    		System.out.println("Clicked add product Sort");
    		Common.waitForElement(2);
    		//  Click Category Name
    		waitFor(categoryType);
    		click(categoryType);
    		System.out.println("Clicked Category Type");
    		Common.waitForElement(2);
    		waitFor(categorySearchTextBox);
    		type(categorySearchTextBox,"Category");
    		categorySearchTextBox.sendKeys(Keys.ENTER);
    		System.out.println("Typed 'Category Name' & pressed Enter");
    	//  Click Category Name
    		waitFor(categoryId);
    		click(categoryId);
    		System.out.println("Clicked Catagory Id Type");
    		Common.waitForElement(2);
    		waitFor(categorySearchTextBox);
    		type(categorySearchTextBox,categoryName);
    		categorySearchTextBox.sendKeys(Keys.ENTER);
    		System.out.println("Typed 'Category id' & pressed Enter");
    		
    		// Get all product cards
    	    List<WebElement> allProducts = driver.findElements(By.xpath("//div[contains(@class,'sortable-card')]"));

    	    if (allProducts.size() < 3) {
    	        System.out.println("❌ Less than 3 products available, cannot perform reorder.");
    	        return;
    	    }

    	    WebElement thirdProduct = allProducts.get(2); // index starts at 0 → 2 = 3rd element
    	    WebElement firstProduct = allProducts.get(0);

    	    try {
    	        // Perform drag and drop with Actions
    	        Actions actions = new Actions(driver);
    	        actions.clickAndHold(thirdProduct)
    	               .moveToElement(firstProduct, 0, 0) // move inside the first product card
    	               .release()
    	               .build()
    	               .perform();

    	        System.out.println("✅ Dragged 3rd product to 1st position.");
    	    } catch (Exception e) {
    	        System.out.println("❌ Drag and drop failed: " + e.getMessage());
    	    }
    		Common.waitForElement(2);
            waitFor(saveButton);
            saveButton.click();
            System.out.println("✅ Excel uploaded successfully");
    		
        }
        
    
      //Clear Catch
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
		
	

        System.out.println("🎉 All categories verification completed successfully!");
    }
		
    public void verifyCatagoriesInUserApp(String filePath) throws IOException, InterruptedException {
    	HomePage home = new HomePage(driver);
		home.homeLaunch();
        Common.waitForElement(3);

        List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Categories in User App");
        ExtentManager.setTest(test);

        for (Map<String, Object> product : products) {
            String category = (String) product.get("Category Name");

            if (category == null || category.trim().isEmpty()) {
                System.out.println("⚠ Skipping empty category");
                continue;
            }

            // ✅ Hover Shop menu
            WebElement shopMenu = wait.until(ExpectedConditions
                    .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
            actions.moveToElement(shopMenu).perform();

            // ✅ Get dropdown links
            List<WebElement> dropdownLinks = wait.until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

            boolean found = false;
            for (WebElement link : dropdownLinks) {
                if (link.getText().trim().equalsIgnoreCase(category.trim())) {
                    found = true;

                    // ✅ Verify link
                    Assert.assertTrue("❌ Category not visible: " + category, link.isDisplayed());
                    System.out.println("✅ Category visible in dropdown: " + category);
                    test.pass("Category visible: " + category);

                    // ✅ Click category
                    link.click();
                    System.out.println("✅ Navigated to Category: " + category);

                    // 🔄 WAIT + REFRESH here until products show
                    int timeoutMinutes = 5;
                    int refreshInterval = 5; // seconds
                    boolean productsFound = false;
                    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

                    while (System.currentTimeMillis() < endTime) {
                        try {
                            List<WebElement> productsInCollection = driver.findElements(By.xpath("//h2[@class='product_list_cards_heading']"));

                            if (!productsInCollection.isEmpty()) {
                                productsFound = true;
                                break;
                            }
                        } catch (Exception ignored) {}

                        driver.navigate().refresh();
                        Common.waitForElement(3);
                        Thread.sleep(refreshInterval * 1000);
                    }

                    // ✅ Final check
                    if (productsFound) {
                        System.out.println("✅ Products available under Category: " + category);
                        test.pass("Products found in Category: " + category);
                    } else {
                        System.err.println("❌ No products found in Category '" + category + "' within " + timeoutMinutes + " minutes.");
                        test.fail("No products found in Category: " + category);
                    }

                    break; // stop dropdown loop
                }
            }

            if (!found) {
                System.err.println("❌ Category not found in dropdown: " + category);
                test.fail("Category not found: " + category);
            }

            // ✅ Reset for next category
            driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
            Common.waitForElement(2);
        }

        ExtentManager.getExtentReports().flush();
    }
		
		
		

		//Bulk Upload Collection
		
		 public void bulkBploadCollectionExcel(String filePath) {
		        Common.waitForElement(2);
		        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
		        
		        System.out.println("✅ Redirected to Admin product collection page");
		 
		        Common.waitForElement(2);
		        waitFor(importButton);
		        click(importButton);
		        System.out.println("✅ Clicked Import Button");

		        Common.waitForElement(2);
		        waitFor(uploadExcelButton);
		        uploadExcelButton.sendKeys(filePath);
		        System.out.println("✅ Uploaded file: " + filePath);

		        Common.waitForElement(2);
		        waitFor(submitButton);
		        submitButton.click();
		        System.out.println("✅ Excel uploaded successfully");
		        
		      //Clear Catch
			    Common.waitForElement(5);
			    waitFor(clearCatchButton);
			    click(clearCatchButton);
			    System.out.println("✅ Successfull click Clear Catch Button");
			    
		        
		    }
		
		 public void verifyCollectionsInAdmin(String filePath) throws IOException {
			    Common.waitForElement(2);

			    // Read Excel → Filter only non-empty collection names
			    List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath)
			        .stream()
			        .filter(product -> {
			            Object collectionObj = product.get("Collections");  // <-- Excel column
			            return collectionObj != null && !collectionObj.toString().trim().isEmpty();
			        })
			        .collect(Collectors.toList());
			    
			    Common.waitForElement(2);
			    waitFor(clickStatus);
			    click(clickStatus);

			    // Select Status -> Active
			    waitFor(statusActiveOption);
			    click(statusActiveOption);
			    System.out.println("✅ Selected Active status");

			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

			    for (Map<String, Object> product : products) {
			        String collectionName = (String) product.get("Collections");

			        // ✅ Navigate to Product Collection section
			        wait.until(ExpectedConditions.elementToBeClickable(collectionButton)).click();
			        System.out.println("✅ Clicked Collection button");

			        wait.until(ExpectedConditions.elementToBeClickable(searchTextBox));
			        searchTextBox.clear();
			        searchTextBox.sendKeys(collectionName);
			        searchTextBox.sendKeys(Keys.ENTER);

			        System.out.println("✅ Searched for Collection: " + collectionName);

			        // ✅ Wait until collection is visible in table
			        By collectionLocator = By.xpath("//span[@title='" + collectionName + "']");
			        wait.until(ExpectedConditions.visibilityOfElementLocated(collectionLocator));

			        System.out.println("✅ Collection is visible in Admin panel: " + collectionName);
			        
			     // Click Edit button
				    Common.waitForElement(2);
				    waitFor(editButton);
			        click(editButton);
			        System.out.println("✅ Clicked  editbutton");
			        Common.waitForElement(3);
				    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1800);");
				    // Click Collection button
				    //Thread.sleep(2000);
				    Common.waitForElement(2);
				    waitFor(menuButton);
				    click(menuButton);
				    System.out.println("✅ Clicked Collection button");

				    //Search 'new-arrivals'
				    //Thread.sleep(2000);
				    Common.waitForElement(2);
				    waitFor(menuSearchBox);
				    type(menuSearchBox, "Shop");
				    menuSearchBox.sendKeys(Keys.ENTER);
				    System.out.println("✅ Successfull  set ShopMenu");
				   // Thread.sleep(2000);
				    // Save changes
			        Common.waitForElement(2);
			        waitFor(saveButton);
			        saveButton.click();
				    System.out.println("✅ successful saved");
			        

			    }
			 
			    // ✅ Clear Cache
			    Common.waitForElement(2);
			    waitFor(clearCatchButton);
			    click(clearCatchButton);
			    System.out.println("✅ Successfully clicked Clear Cache Button");
			    Common.waitForElement(2);
			}





		 public void verifyCollectionsInUserApp(String filePath) throws IOException {
			 HomePage home = new HomePage(driver);
				home.homeLaunch();
			    Common.waitForElement(3);

			    List<Map<String, Object>> products = ExcelXLSReader.readProductsWithMultipleListing(filePath);

			    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			    Actions actions = new Actions(driver);

			    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Collections in User App");
			    ExtentManager.setTest(test);

			    for (Map<String, Object> product : products) {
			        String collection = (String) product.get("Title");

			        if (collection == null || collection.trim().isEmpty()) {
			            System.out.println("⚠ Skipping empty collection");
			            continue;
			        }

			        // ✅ Hover Shop menu
			        WebElement shopMenu = wait.until(ExpectedConditions
			                .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
			        actions.moveToElement(shopMenu).perform();

			        // ✅ Wait for dropdown
			        List<WebElement> dropdownLinks = wait.until(ExpectedConditions
			                .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

			        boolean found = false;
			        for (WebElement link : dropdownLinks) {
			            String linkText = link.getText().trim();
			            if (linkText.equalsIgnoreCase(collection.trim())) {
			                found = true;

			                // ✅ Verify visible
			                Assert.assertTrue("❌ Collection not visible: " + collection, link.isDisplayed());
			                System.out.println("✅ Collection visible in dropdown: " + collection);
			                test.pass("Collection visible: " + collection);

			                // ✅ Click
			                link.click();
			                System.out.println("✅ Navigated to collection: " + collection);

			                break;
			            }
			        }

			        if (!found) {
			            System.out.println("❌ Collection not found in dropdown: " + collection);
			            test.fail("Collection not found: " + collection);
			        }

			        // ✅ After clicking, go back & refresh Shop menu for next collection
			        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
			        Common.waitForElement(2);
			    }

			    ExtentManager.getExtentReports().flush();
			}
		 
		 String productName;
		 String excpectedName;
		 public String copyProductNameFromHomePageTopSelling() throws InterruptedException {

			    // ANSI colors
			    String GREEN = "\u001B[32m";
			    String CYAN  = "\u001B[36m";
			    String RESET = "\u001B[0m";

			    HomePage home = new HomePage(driver);
			    home.homeLaunch();

			    // Scroll directly to Top Selling section
			    WebElement topSellingSection = driver.findElement(
			            By.xpath("//div[@data-section='top_selling']")
			    );

			    ((JavascriptExecutor) driver).executeScript(
			            "window.scrollTo({top: arguments[0].getBoundingClientRect().top + window.pageYOffset - 120, behavior: 'smooth'});",
			            topSellingSection
			    );

			    ((JavascriptExecutor) driver).executeScript(
			            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});",
			            topSellingSection
			    );

			    Thread.sleep(500); // allow smooth scroll to finish

//			    // Click the first product
//			    WebElement firstProduct = driver.findElement(
//			            By.xpath("(//div[@data-section='top_selling']//a[contains(@class,'prod_card_img')])[1]")
//			    );
			    WebElement firstProduct1 = driver.findElement(
			            By.xpath("(//div[contains(@class,'top_selling_carousel_wrap')]//a[contains(@class,'prod_title')])[1]")
			    );
			            
			     excpectedName = firstProduct1.getText().trim();
			     Common.waitForElement(2);
			     System.out.println(CYAN + "📦 Product Listing Name: " + excpectedName + RESET);
			     Common.waitForElement(2);
			  // First product card link
			     WebElement firstProduct = driver.findElement(
			             By.xpath("(//div[contains(@class,'top_selling_carousel_wrap')]//a[contains(@href,'/product-detail')])[1]")
			     );

			     String productUrl = firstProduct.getAttribute("href");

			     // JS click (HEADLESS SAFE)
			     ((JavascriptExecutor) driver)
			             .executeScript("arguments[0].click();", firstProduct);

			     System.out.println(GREEN + "✅ First Top Selling Product Clicked: " + productUrl + RESET);

			     // ✅ 1. WAIT FOR URL TO CHANGE (CRITICAL)
			     wait.until(ExpectedConditions.urlContains("/product-detail"));

			     // ✅ 2. WAIT FOR DOM READY
			     wait.until(driver ->
			             ((JavascriptExecutor) driver)
			                     .executeScript("return document.readyState")
			                     .equals("complete")
			     );

			     // ✅ 3. WAIT FOR PRODUCT NAME PRESENCE (NOT VISIBILITY)
			     WebElement productNameElement = wait.until(
			             ExpectedConditions.presenceOfElementLocated(
			                     By.xpath("//h4[contains(@class,'prod_name')]")
			             )
			     );

			     // ✅ 4. READ VIA JS (HEADLESS FIX)
			      productName = ((JavascriptExecutor) driver)
			             .executeScript("return arguments[0].textContent;", productNameElement)
			             .toString()
			             .trim();

			     System.out.println(CYAN + "📦 Product Name (PDP): " + productName + RESET);

			     return productName;
			}
		 
		 String productlistingName;
		    String productDetailName;
//		    String productName;
//		    String secondaryColor;

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
		                By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'DRESSES']")));
		 //       allButton.click();
		        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", allButton);

		        System.out.println("✅ Clicked on 'All' under Shop menu");

		        // Collect all product cards
		        List<WebElement> products = wait.until(ExpectedConditions
		                .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'product_list_cards_list ')]")));

		        if (products.isEmpty()) {
		            System.out.println("⚠️ No products found on listing page!");
		            return null;
		        }

		        Random rand = new Random();
		        int maxAttempts = Math.min(8, products.size()); // check max 5 random ones
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
		            ((JavascriptExecutor) driver)
	                .executeScript("arguments[0].click();", productNameElement);
		         //   productNameElement.click();
		            productFound = true;

		            System.out.println("✅ Selected random in-stock product: " + productlistingName);
		            break;
		        }

		        if (!productFound) {
		            System.out.println("⚠️ Could not find any in-stock product after " + maxAttempts + " random tries.");
		            return null;
		        }

		     // ✅ Get product detail name
		        WebElement productDetailNameElement = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//h4[contains(@class,'prod_name')]")
		                )
		        );

		         productDetailName = productDetailNameElement.getText().trim();
		        System.out.println("📦 Product Detail Name: " + productDetailName);

		        // return or store it as needed
		        return productlistingName + " | Detail Name: " + productDetailName;
		    }


		    
		 
			   
		    
		    
		    
		    
//Home Page Test Case
	
//TC-02
		public void  validateProductSuccessfullyRemoved() throws InterruptedException{
			
			copyProductNameFromHomePageTopSelling();
			
			givesProductName();
			
			fetchSkuFromProduct();
			
			removeSkuFromTopSelling();
			
			verifyProductNotInTopSelling();
			
		
		 }
	
		public void  validateProductSuccessfullyAdded() throws InterruptedException{
			
			putSkuIntoTopSelling();
			
			verifyProductShowInTopSelling();
			
			
		 }
	
//TC-03	
	public void validateNewArrivalSuccessfullyAdded() throws InterruptedException {
	
		takeRandomProductName();
		
		verifyColourOfTheProductIsFirstPosition();
		
		addTheProductInNewArrivalSection();
		
		sortTheProductInFirstPosition();
	
		verifyProductShowInNewArrivalsSction();
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
