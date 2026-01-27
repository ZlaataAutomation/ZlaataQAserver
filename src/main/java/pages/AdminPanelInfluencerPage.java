package pages;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Assume;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.AdminPanelInfluencerObjRepo;
import utils.Common;

public class AdminPanelInfluencerPage extends AdminPanelInfluencerObjRepo {
	
	public AdminPanelInfluencerPage(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}
	public void adminLoginApp() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	
	public String takeRandomProductName() {

	    HomePage home = new HomePage(driver);
	    home.homeLaunch();
	    Common.waitForElement(3);

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // Hover on Shop → Dresses
	    WebElement shopMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
	    actions.moveToElement(shopMenu).perform();

	    WebElement dressesButton = wait.until(ExpectedConditions.elementToBeClickable(
	            By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')='DRESSES']")));
	    dressesButton.click();

	    System.out.println("✅ Clicked on 'Dresses' menu");

	    // Get all products
	    List<WebElement> products = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(By.xpath("//div[contains(@class,'product_list_cards_list')]")));

	    if (products.isEmpty()) {
	        System.out.println("⚠️ No products found!");
	        return null;
	    }

	    Random rand = new Random();
	    String productName = null;

	    // Try multiple random products until in-stock found
	    for (int attempt = 1; attempt <= 5; attempt++) {

	        int randomIndex = rand.nextInt(products.size()) + 1;
	        System.out.println("🎯 Checking product index: " + randomIndex);

	        WebElement productCard = driver.findElement(
	                By.xpath("(//div[contains(@class,'product_list_cards_list')])[" + randomIndex + "]"));

	        String name = productCard.findElement(
	                By.xpath(".//h2[@class='product_list_cards_heading']"))
	                .getText().trim();

	        boolean isOutOfStock = !productCard.findElements(
	                By.xpath(".//h2[contains(@class,'product_list_cards_out_of_stock_heading') and normalize-space()='OUT OF STOCK']"))
	                .isEmpty();

	        if (isOutOfStock) {
	            System.out.println("❌ OUT OF STOCK → " + name);
	            continue;
	        }

	        productName = name;   
	        System.out.println("✅ Selected in-stock product: " + productName);
	        break;
	    }

	    if (productName == null) {
	        System.out.println("⚠️ No in-stock product found after retries");
	        return null;
	    }

	    System.out.println("📌 Final Product Name: " + productName);
	    return productName;
	}
	String copiedProductName;
	String copiedSku;
	public void setTheProductImageAndStatusInInfluencer(String productListingName, String imagePath) {
		Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Successful redirect to Adimn Product page");
		 // Open product listing
	    waitFor(productListingMenu);
	    click(productListingMenu);  
	    System.out.println("✅ Successful click product listing menu");
	    waitFor(productSearchBox);
	    click(productSearchBox);
	 // Search or enter the product
	    type(productSearchBox, productListingName + Keys.ENTER);
	    Common.waitForElement(2);
	    System.out.println("✅ Entered product listing name in search box & pressed ENTER");
	    
	 // now click edit, etc…
	    Common.waitForElement(3);
	    waitFor(editProductButton);
	    click(editProductButton);
	    System.out.println("✅ Clicked product edit option");
	    
	 // Copy text from Product Name textbox
	    copiedProductName = productNameTextbox.getAttribute("value").trim();
	    System.out.println("📋 Copied product name: " + copiedProductName);
	    
	    // now click item, etc…
	    Common.waitForElement(3);
	    waitFor(itemProductButton);
	    click(itemProductButton);
	    System.out.println("✅  Clicked product item option");
	    
	 // ✅ Get product listing input
	    WebElement listingInput = wait.until(ExpectedConditions
	            .presenceOfElementLocated(By.xpath("(//div[label[text()='Product Listing Name']]//input)[1]")));
	    String listingValue = listingInput.getAttribute("value").trim();
	    System.out.println("✅ Matched product listing: " + listingValue);
	    copiedSku = copySkuTextbox.getAttribute("value").trim();
	    System.out.println("📋 Copied sku : " + copiedSku);
	    
	 // ✅ Check Influencer Yes
	    if (influencerYes.isSelected()) {
	        System.out.println("ℹ️ Influencer already ON for: " + listingValue);
	    } else {
	        influencerYes.click();
	        System.out.println("✅ Turned ON Influencer for: " + listingValue);
	    }
	            // Upload image inside this row
	         // now click edit, etc…
	    	    Common.waitForElement(3);
	    	    waitFor(uploadImage);
	    	    click(uploadImage);
	    	    System.out.println("✅ Clicked uploadImage Button");
	    	    Common.waitForElement(3);
	    	    waitFor(InfluImageUpload);
	    	    click(InfluImageUpload);
	    	    System.out.println("✅ Clicked InfluImageUpload Button");
	    	    Common.waitForElement(2);
	    	    // Upload new banner image
	    	    WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(
	    	    	    By.cssSelector(".modal-body.modal-scroll-btn")));
	    	    WebElement fileInput = modal.findElement(By.id("listingImagesDesktopInput"));
	    	    ((JavascriptExecutor) driver).executeScript("arguments[0].classList.remove('d-none');", fileInput);
	    	    fileInput.sendKeys(imagePath);
	    	    ((JavascriptExecutor) driver).executeScript("arguments[0].classList.add('d-none');", fileInput);
	            System.out.println("✅ successful image Upload");
	            
	    	    Common.waitForElement(2);
	    	    waitFor(confirmUpload);
	    	    click(confirmUpload);
	    	    System.out.println("✅ Clicked confirmUpload Button");
	    	    
	    	    
	           
	            // Click Save inside this row
	    	    Common.waitForElement(5);
	            wait.until(ExpectedConditions
	    	            .elementToBeClickable(saveButton));
	            click(saveButton);
	            System.out.println("✅ Saved product successfully: " + listingValue);

	}
	
	public void addTheProductInProductCollection() {
		click(searchProductCollectionMenu);
		Common.waitForElement(2);
	    waitFor(searchProductCollectionMenu); 
	    type(searchProductCollectionMenu, "Product Collections");
	    System.out.println("✅ Typed 'Product Collections' ");
	    Common.waitForElement(2);
	    waitFor(clickProductCollection);
	    click(clickProductCollection);
	    System.out.println("✅ Selected Product Collection");
	    Common.waitForElement(2);
	    waitFor(clickStatus);
	    click(clickStatus);
	    waitFor(statusActiveOption);
	    click(statusActiveOption);
	    System.out.println("✅ Selected Active status");
	    Common.waitForElement(2);
	    waitFor(collectionButton);
	    click(collectionButton);
	    System.out.println("✅ Clicked Collection button");
	    Common.waitForElement(2);
	    waitFor(searchTextBox);
	    type(searchTextBox, "influencers");
	    searchTextBox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Searched for influencers");
	    Common.waitForElement(2);
	    waitFor(editCollectionButton);
	    editCollectionButton.click();
	    System.out.println("✅ Entered Edit mode for collection");
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,400);");
	    waitFor(addProductTextbox);
	    type(addProductTextbox, copiedProductName);
	    Common.waitForElement(2);
	    addProductTextbox.sendKeys(Keys.ENTER);
	    System.out.println("✅ Added product name to collection");
	    //Save
	    waitFor(saveButton);
	    click(saveButton);
	    System.out.println("✅ Saved collection changes");	
	}
		
	public void sortTheProductForIncluencer() {
		 // Open Product Sort
		driver.navigate().refresh();
        Common.waitForElement(2);
        click(searchProductSortMenu);
        type(searchProductSortMenu, "Product Sorts");
        click(clickProductSort);
        System.out.println("✅ Selected Product Sorts");
        click(addProductSort);
        System.out.println("✅ Clicked add product Sort");
        Common.waitForElement(2);
        
        click(categoryType);
        type(categorySearchTextBox, "Style By");
        categorySearchTextBox.sendKeys(Keys.ENTER);
        System.out.println("✅ Collection selected for Product Sort");

        // All product cards
        List<WebElement> allProducts = driver.findElements(By.xpath("//div[contains(@class,'sortable-card')]"));

        if (allProducts.size() < 2) {
            System.out.println("❌ Not enough products available to perform reorder.");
            return;
        }

        WebElement productToMove = null;

        // ✅ Find product card by matching copied SKU
        for (WebElement card : allProducts) {
            try {
                WebElement skuElement = card.findElement(By.xpath(".//span[contains(@class,'font-weight-bold')]"));
                String skuValue = skuElement.getText().trim();

                System.out.println("🔍 Found SKU on card: " + skuValue);
                
                if (skuValue.equalsIgnoreCase(copiedSku)) {
                    productToMove = card;
                    System.out.println("✅ Found product card with matching SKU: " + skuValue);
                    break;
                }
            } catch (Exception e) {
                // Ignore cards without SKU field
            }
        }

        if (productToMove == null) {
            System.out.println("❌ No product card found with copied SKU: " + copiedSku);
            return;
        }

        // ✅ First product card (drop target)
        WebElement firstProduct = allProducts.get(0);

        // Perform drag & drop
        Actions action = new Actions(driver);
        action.clickAndHold(productToMove)
              .pause(Duration.ofMillis(500))
              .moveToElement(firstProduct, 0, -30) // move above first card
              .pause(Duration.ofMillis(500))
              .release()
              .build()
              .perform();

        Common.waitForElement(3);
        System.out.println("✅ Dragged product with SKU " + copiedSku + " to 1st position");

        // Save sort
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        System.out.println("✅ Sorting saved for category: " );
    

    // Clear cache
    Common.waitForElement(2);
    click(clearCatchButton);
    System.out.println("✅ Cleared cache");
    Common.waitForElement(6);
    click(clearCatchButton);
    System.out.println("✅ Cleared cache");
    Common.waitForElement(3);

    System.out.println("🎉 Category sorting completed successfully!");
}
		
	
	public void verifyFirstProductInUserAppInfluencer(String productListingName) {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
	    Common.waitForElement(3);
	    waitFor(clickInfluencer);
	    click(clickInfluencer);
	    System.out.println("✅ Clicked  Influencer ");
	    
  // Scroll a bit to make products visible
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,200);");
	    Common.waitForElement(2);

	    // Wait for the product to appear in product cards
	    FluentWait<WebDriver> wait = new FluentWait<>(driver)
	            .withTimeout(Duration.ofMinutes(15))
	            .pollingEvery(Duration.ofSeconds(3))
	            .ignoring(NoSuchElementException.class)
	            .ignoring(StaleElementReferenceException.class);

	    WebElement card = wait.until(d -> {
	        driver.navigate().refresh();
	        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

	        List<WebElement> elements = d.findElements(By.xpath(
	                "//div[@id='cls_newproduct_sec_dev']/div[contains(@class,'product_list_cards_list')][1]//h2[@class='product_list_cards_heading']"
	            ));

	            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(productListingName)) {
	                return elements.get(0);
	            }
	            return null;
	    });

	    if (card != null && card.isDisplayed()) {
	        System.out.println("✅ Product '" + productListingName + "' is visible in User App for Influencer in First Position ");
	    } else {
	        throw new RuntimeException("❌ Product '" + productListingName + "' not found in User App for Influencer: " );
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
