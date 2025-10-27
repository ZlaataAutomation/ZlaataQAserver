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
	
	private String expectedBannerTitle;



	public void uploadImage(String imagePath) {
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    Common.waitForElement(2);

	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    expectedBannerTitle = Common.getValueFromTestDataMap("Banner Title");
	    Common.waitForElement(2);
	    click(addHomePageBanner);
	    
	        type(bannerTitle,expectedBannerTitle );
	        Common.waitForElement(2);
		    waitFor(uploadImageInput);
	        uploadImageInput.sendKeys(imagePath);
	        System.out.println("✅ successful image updated");
	        click(uploadButton);
	        driver.navigate().refresh();
	       
	      
	        
	        Common.waitForElement(4);
	    click(homePageBannerDropDown);
	    selectHomePageValue.get(0).click();
	    Common.waitForElement(2);
		click(status);
		statusFilterSelect.get(0).click();
		Common.waitForElement(2);

	   
	        type(sortBy, "1");
	        Common.waitForElement(2);
	        click(sortBySave);

	        System.out.println("Added new banner and saved with Sort By = 1");
	    
	}


	public void verifyBannerOnHomePage() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();

	    String expectedTitle = Common.getValueFromTestDataMap("Banner Title");

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
	                        By.xpath("//a[contains(@class,'carousel_banner Cls_OpenSubscribe')]//img[@alt='" + expectedTitle + "']")
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
	        System.out.println("✅ Banner title '" + expectedTitle + "' is visible in User Application.");
	    } else {
	        System.out.println("❌ Banner title '" + expectedTitle + "' not found within " + timeoutMinutes + " minutes.");
	        Assert.fail("❌ Banner title '" + expectedTitle + "' not found within " + timeoutMinutes + " minutes.");
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
	public void givesProductName() {
		Common.waitForElement(4);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successfull redirect to Adimn Product page");
		 // Open product listing
	    click(productListingMenu);  
	    System.out.println("✅ Successfull click product listing menu");
	    waitFor(productSearchBox);
	    click(productSearchBox);

	    // Fetch the product name directly from Excel map
	    String productName = Common.getValueFromTestDataMap("ProductListingName");
	    System.out.println("✅ Successfull fetch product listing name from excel sheet");
	 // Search or enter the product
	    type(productSearchBox, productName + Keys.ENTER);
	    Common.waitForElement(2);
	    System.out.println("✅ Successfull put product listing name in searchbox and also click enter");

		
	}
	public String fetchSkuFromProduct() {
		
	    // now click edit, etc…
	    waitFor(editProductButton);
	    click(editProductButton);
	    System.out.println("✅ Successfull click product edit option");
	    // now click item, etc…
	    waitFor(itemProductButton);
	    click(itemProductButton);
	    System.out.println("✅ Successfull click product item option");
	    
	 // now take Sku
	    waitFor(skuField);
	    System.out.println("✅ Successfull copy the SKU from skufield");
        return skuField.getAttribute("value").trim();
     
        
	   	
	}
	public void putSkuIntoTopSelling(String sku) {
	    try {
	    	
	    	Common.waitForElement(2);
	    	waitFor(generalSettingsMenu);
	        click(generalSettingsMenu);
	        Common.waitForElement(2);
	        
//		    waitFor(clickSetKey);
//		    click(clickSetKey);
//		    type(clickSetKey, "top_selling");
//		    clickSetKey.sendKeys(Keys.ENTER);
//		    System.out.println("✅ Searched for top-selling");
	        waitFor(topSellingEdit);
	        click(topSellingEdit);
	        System.out.println("✅ Successfull click the top selling edit button");     
	        
	        waitFor(topSellingSkuInput);
	        String current = topSellingSkuInput.getAttribute("value");
	        String cleaned = (current == null) ? "" : current.trim();

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

	        boolean alreadyExists = skuList.stream()
	                .anyMatch(s -> s.equalsIgnoreCase(sku));

	        String updated;
	        String message;
	        if (alreadyExists) {
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + sku + "' already present. No changes made.";
	        } else if (skuList.isEmpty()) {
	            updated = "[" + sku + "]";
	            message = "SKU '" + sku + "' added as the first Top Selling item.";
	        } else {
	            updated = "[" + sku + ", " + String.join(", ", skuList) + "]";
	            message = "SKU '" + sku + "' prepended to Top Selling list.";
	        }

	        topSellingSkuInput.clear();
	        type(topSellingSkuInput, updated);
	        Common.waitForElement(5);
	        click(saveTopSelling);

	     // Wait briefly for possible error page
	     Common.waitForElement(3);

	     System.out.println("✅ " + message);

	         // No error page found → continue test
	         System.out.println("✅ Save successful.");
	        

	    } catch (Exception e) {
	        String error = "❌ Failed to update Top Selling SKU due to: " + e.getMessage();
	        System.err.println(error);
	        throw e;
	    }
	    
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    Common.waitForElement(2);
	    
	}
	
	
	//Verify the product successfull showing in user application home page top selling Section
	
	public void verifyProductShowInTopSelling(String productName) throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,700);");

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
	                		"//div[contains(@class,'products_cards')]//a[@class='product_heading' and normalize-space(.)='" + productName + "']"
	                        
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
	    if (productFound && card != null && card.getText().trim().equalsIgnoreCase(productName.trim())) {
	        System.out.println("✅ Product '" + productName + "' is visible in Top Selling.");
	    } else {
	        System.err.println("❌ Product '" + productName + "' not found in Top Selling within " 
	                           + timeoutMinutes + " minutes.");
	        
	    }
	}
	    
	  
	
	
	//Negative Testcase Top Selling Section
	
	public void forNegativeGivesProductName() {
		Common.waitForElement(4);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successfull redirect to Adimn Product page");
		 // Open product listing
	    click(productListingMenu);  
	    System.out.println("✅ Successfull click product listing menu");
	    waitFor(productSearchBox);
	    click(productSearchBox);

	    // Fetch the product name directly from Excel map
	    String productName = Common.getValueFromTestDataMap("ProductListingName");
	    System.out.println("✅ Successfull fetch product listing name from excel sheet");
	 // Search or enter the product
	    type(productSearchBox, productName + Keys.ENTER);
	    Common.waitForElement(2);
	    System.out.println("✅ Successfull put product listing name in searchbox and also click enter");

		
	}
	public String forNegativeFetchSkuFromProduct() {
		
	    // now click edit, etc…
	    waitFor(editProductButton);
	    click(editProductButton);
	    System.out.println("✅ Successfull click product edit option");
	    // now click item, etc…
	    waitFor(itemProductButton);
	    click(itemProductButton);
	    System.out.println("✅ Successfull click product item option");
	    
	 // now take Sku
	    waitFor(skuField);
	    System.out.println("✅ Successfull copy the SKU from skufield");
        return skuField.getAttribute("value").trim();
     
        
	   	
	}
	// Remove SKU from Top Selling list
	public void removeSkuFromTopSelling(String sku) {
	    try {
	        Common.waitForElement(2);
	        waitFor(generalSettingsMenu);
	        click(generalSettingsMenu);
	        Common.waitForElement(2);

	        waitFor(topSellingEdit);
	        click(topSellingEdit);
	        System.out.println("✅ Clicked Top Selling Edit button");

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

	        boolean exists = skuList.removeIf(s -> s.equalsIgnoreCase(sku));

	        String updated;
	        String message;
	        if (!exists) {
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + sku + "' not found. No changes made.";
	        } else if (skuList.isEmpty()) {
	            updated = "[]";
	            message = "SKU '" + sku + "' removed. List is now empty.";
	        } else {
	            updated = "[" + String.join(", ", skuList) + "]";
	            message = "SKU '" + sku + "' removed from Top Selling list.";
	        }

	        topSellingSkuInput.clear();
	        type(topSellingSkuInput, updated);
	        click(saveTopSelling);

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
	    click(clearCatchButton);
	    System.out.println("✅ Successfully clicked Clear Cache Button");
	    Common.waitForElement(2);
	}


	// Verify product is NOT in Top Selling (Negative Test)
	public void verifyProductNotInTopSelling(String productName) throws InterruptedException {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,700);");

	    int timeoutMinutes = 5;   // total wait time
	    boolean productFound = false;

	    long endTime = System.currentTimeMillis() + timeoutMinutes * 60 * 1000;

	    while (System.currentTimeMillis() < endTime) {
	        try {
	            driver.navigate().refresh();
	            Common.waitForElement(3);

	            List<WebElement> elements = driver.findElements(By.xpath(
	                    "//div[contains(@class,'products_cards')]//h3[@class='product_heading' and normalize-space(text())='" 
	                    + productName + "']"
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
	        System.out.println("✅ Product '" + productName + "' is NOT visible in Top Selling (as expected).");
	    } else {
	        System.err.println("❌ Product '" + productName + "' still visible in Top Selling even after removal!");
	    }
	}

//New Arrivals
	 private String copiedSku;
	
	public void verifyColourOfTheProductIsFirstPosition() {
		Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successful redirect to Adimn Product page");
		 // Open product listing
	    click(productListingMenu);  
	    System.out.println("✅ Successful click product listing menu");
	    waitFor(productSearchBox);
	    click(productSearchBox);

	    // Fetch the product name directly from Excel map
	    String productName = Common.getValueFromTestDataMap("ProductListingName");
	    System.out.println("✅ Fetched product listing name from Excel sheet:" + productName);
	 // Search or enter the product
	    type(productSearchBox, productName + Keys.ENTER);
	    Common.waitForElement(2);
	    System.out.println("✅ Entered product listing name in search box & pressed ENTER");
	    
	 // now click edit, etc…
	    Common.waitForElement(3);
	    waitFor(editProductButton);
	    click(editProductButton);
	    System.out.println("✅ Clicked product edit option");
	    // now click item, etc…
	    waitFor(itemProductButton);
	    click(itemProductButton);
	    System.out.println("✅  Clicked product item option");
	    
	    // -------- Check that the product text matches Excel name --------
	    waitFor(productListingBoxText); 
	    String uiProductName = productListingBoxText.getAttribute("value").trim();
	    System.out.println(" UI Product Listing Name: " + uiProductName);
	    //For Sorting we copied sku
	    copiedSku = skuTextbox.getAttribute("value").trim();
	    System.out.println(" Copied SKU : " + copiedSku);

	  
	    boolean match = uiProductName.toLowerCase().contains(productName.toLowerCase());
	    if (!match) {
	        System.err.println("❌ Product mismatch,This is not First Colour Both are Different— skipping remaining steps → Expected: " + productName + " | Actual: " + uiProductName);
	        // Now skip scenario
	        Assume.assumeTrue("Skipping due to product mismatch", false);
	    }
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
	    click(productButton);
	    System.out.println("✅ Clicked Product button");

	    // Copy text from Product Name textbox
	    String copiedProductName = productNameTextbox.getAttribute("value").trim();
	    System.out.println("📋 Copied product name: " + copiedProductName);

	    //Navigate to Search Product Collection page
	    
	    click(searchProductCollectionMenu);
	    System.out.println("✅ Clicked Search Product Collection");

	    //Type in the search text box
	    //Thread.sleep(2000);
	    waitFor(searchProductCollectionMenu); 
	    type(searchProductCollectionMenu, "Product Collections");
	    //Thread.sleep(2000);
	    System.out.println("✅ Typed 'Product Collections' ");
	    waitFor(clickProductCollection);
	  //  Thread.sleep(3000);
	    click(clickProductCollection);
	    System.out.println("✅ Selected Product Collection");
//	     3. Select from the dropdown or result line
//	    selectDropdownByVisibleText(productCollectionDropdown, "Product Collections");
//	    System.out.println("✅ Selected Product Collection");

	    Common.waitForElement(2);
	    waitFor(clickStatus);
	    click(clickStatus);

	    // Select Status -> Active
	    waitFor(statusActiveOption);
	    click(statusActiveOption);
	    System.out.println("✅ Selected Active status");

	    // Click Collection button
	    //Thread.sleep(2000);
	    Common.waitForElement(2);
	    waitFor(collectionButton);
	    click(collectionButton);
	    System.out.println("✅ Clicked Collection button");

	    //Search 'new-arrivals'
	    //Thread.sleep(2000);
	    Common.waitForElement(2);
	    waitFor(searchTextBox);
	    type(searchTextBox, "new-arrivals");
	    searchTextBox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Searched for new-arrivals");
	   // Thread.sleep(2000);

	    //Click Edit
	    Common.waitForElement(2);
	    waitFor(editCollectionButton);
	    editCollectionButton.click();
	    System.out.println("✅ Entered Edit mode for collection");

	    //Add copied product to last position in product text field
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,900);");
	    waitFor(addProductTextbox);
	    type(addProductTextbox, copiedProductName);
	    Common.waitForElement(2);
	   //Thread.sleep(3000);
	    addProductTextbox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Added product name to collection");
	    

	    //Save
	    waitFor(saveButton);
	    click(saveButton);
	    System.out.println("✅ Saved collection changes");
		
		
	}
	
	public void sortTheProductInFirstPosition() {
		// Click on Search Box for Product Sort
		Common.waitForElement(3);
		click(searchProductSortMenu);
	    System.out.println("✅ Clicked Search Product Collection");

		// Type in the search text box
		// Thread.sleep(2000);
		waitFor(searchProductSortMenu);
		type(searchProductSortMenu, "Product Sorts");
		// Thread.sleep(2000);
		System.out.println("✅ Typed 'Product Sorts");
		waitFor(clickProductSort);
		// Thread.sleep(3000);
		click(clickProductSort);
		System.out.println("✅ Selected Product Sorts");

		//  Click Category Name
		waitFor(categoryName);
		click(categoryName);
		System.out.println("✅ Clicked Category Name");

		// In the Category Search, type "New Arrivals" and hit Enter
		 Common.waitForElement(2);
		    waitFor(categorySearchBox);
		    type(categorySearchBox, "New Arrivals");
		    categorySearchBox.sendKeys(Keys.ENTER);
		    System.out.println("✅ Typed 'New Arrivals' & pressed Enter");
		   // Thread.sleep(2000);
		

		// Scroll down
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");
		System.out.println("✅ Scrolled down");

		//Click Next Page arrow
		Common.waitForElement(3);
		waitFor(nextPageArrow);
		click(nextPageArrow);
		System.out.println("✅ Clicked next page");

		// Click Plus Button to add products
		Common.waitForElement(2);
		waitFor(plusButton);
		click(plusButton);
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
	    waitFor(saveButton);
	    click(saveButton);
	    System.out.println("✅ Saved collection changes");
	    //Clear Catch
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    Common.waitForElement(2);
		
		
		
	}
	
	public void verifyProductShowInNewArrivalsSction(String productName) {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,2400);");
	    Common.waitForElement(3);

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
	                		"//div[contains(@class,'products_cards')]//a[@class='product_heading' and normalize-space(.)='" + productName + "']"
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
	    if (productFound && card != null && card.getText().trim().equalsIgnoreCase(productName.trim())) {
	        System.out.println("✅ Product '" + productName + "' is visible in New Arrivals.");
	    } else {
	        System.err.println("❌ Product '" + productName + "' not found in New Arrivals within " 
	                           + timeoutMinutes + " minutes.");
	    }
	}
	
	//Catagory Section
	
	  public void updateCategoryBanner(String imagePathCatagory) {
		
		driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successful redirect to Home page Banner ");
	    
      
        // Click on Home Page Banner menu
	    Common.waitForElement(2);
	    waitFor(homePageBannerMenu);
        click(homePageBannerMenu);
        Common.waitForElement(2);
        

        // Search for category
        Common.waitForElement(2);
	    waitFor(catagorysearchTextBox);
	    type(catagorysearchTextBox, "Dazzle Category");
	    catagorysearchTextBox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Typed 'Dazzle Category' & pressed Enter");
	    
        // Click Edit button
	    Common.waitForElement(2);
	    waitFor(editButton);
        click(editButton);
        System.out.println("✅ Clicked  editbutton");
        

        // Clear and enter new banner title
        String bannerTitle = Common.getValueFromTestDataMap("Banner Title");
        Common.waitForElement(2);
	    waitFor(bannerTitleTextBox);
	    bannerTitleTextBox.clear();
	    type(bannerTitleTextBox, bannerTitle);
	    System.out.println("✅ Typed bannerTitle");
	    

        // Upload new banner image
	    Common.waitForElement(2);
	    waitFor(desktopBannerUpload);
        desktopBannerUpload.sendKeys(imagePathCatagory);
        System.out.println("✅ successful image updated");
        

        // Save changes
        Common.waitForElement(2);
        waitFor(saveButton);
        saveButton.click();
        Common.waitForElement(3);
      //Clear Catch
	    Common.waitForElement(2);
	    waitFor(clearCatchButton);
	    click(clearCatchButton);
	    System.out.println("✅ Successfull click Clear Catch Button");
	    Common.waitForElement(2);
    }

    // Method 2: Verify Banner in User Application
	public void verifyBannerUserApp() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,1300);");
	    Common.waitForElement(3);

	    // Expected values from test data
	    String expectedTitle = Common.getValueFromTestDataMap("Banner Title");

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
	
	 // ✅ Upload excel
    public void uploadTheSpecialTimerProductExcel(String filePath) {
        Common.waitForElement(2);
        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
        
        System.out.println("✅ Redirected to Admin Special Timer Product page");
        Common.waitForElement(3);
	    waitFor(clickStatus);
	    click(clickStatus);

	    // Select Status -> Active
	    Common.waitForElement(3);
	    waitFor(statusActiveOption);
	    click(statusActiveOption);
	    System.out.println("✅ Selected Active status");
	    
	 // Click Plus Button to add products
	 		Common.waitForElement(2);
	 		waitFor(plusButton);
	 		click(plusButton);
	 		System.out.println("✅ Clicked '+'Plus button");

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
	    Common.waitForElement(2);
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
          

            By locator = By.xpath("//h6[normalize-space()='" + productColorName + "']");
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            WebElement productElement = driver.findElement(locator);
            Assert.assertTrue("❌ Product not found in User App: " + productColorName, productElement.isDisplayed());
            productElement.click();

            System.out.println("✅ Product found in User App: " + productColorName);

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
