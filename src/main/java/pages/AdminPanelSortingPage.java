package pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import manager.FileReaderManager;
import objectRepo.AdminPanelObjRepo;
import objectRepo.AdminPanelSortingObjRepo;
import stepDef.ExtentManager;
import utils.Common;
import utils.ExcelXLSReader;

public class AdminPanelSortingPage extends AdminPanelSortingObjRepo {
	
	public AdminPanelSortingPage(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	WebElement firstProductName;
	 String expectedFirstProductName;
	 WebElement collectionFirstProductName;
	 String expectedcollectionFirstProductName;
	 WebElement stylesFirstProductName;
	 String expectedStylesFirstProductName;
	 WebElement MicroPageFirstProductName;
	 String expectedAllProductFirstProductName;
	 WebElement AllProductFirstProductName;
	 String expectedMicroPageFirstProductName;
	
	
	public void adminLoginApp() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	
	//Sort catagory
	public void sortTheCategoriesInAdminPanel() throws IOException {
	    Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Redirected to Admin product Categories page");
	    
	    String categoryName = Common.getValueFromTestDataMap("Shop Name");
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	        // Refresh page before each search
	        driver.navigate().refresh();
	        Common.waitForElement(3);

	        // Open category
	        wait.until(ExpectedConditions.elementToBeClickable(categoriesNameButton)).click();
	        System.out.println("✅ Clicked Categories button");

	        wait.until(ExpectedConditions.elementToBeClickable(searchTextBox));
	        searchTextBox.clear();
	        searchTextBox.sendKeys(categoryName);
	        searchTextBox.sendKeys(Keys.ENTER);
	        System.out.println("✅ Searched for Category: " + categoryName);

	        // Verify category visible
	        By categoryLocator = By.xpath("//span[@title='" + categoryName + "']");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(categoryLocator));
	        System.out.println("✅ Category is visible: " + categoryName);

	        // Open Product Sort
	        Common.waitForElement(2);
	        click(searchProductSortMenu);
	        type(searchProductSortMenu, "Product Sorts");
	        click(clickProductSort);
	        System.out.println("✅ Selected Product Sorts");

	        click(addProductSort);
	        System.out.println("✅ Clicked add product Sort");
	        Common.waitForElement(2);

	        click(categoryType);
	        type(categorySearchTextBox, "Category");
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        click(categoryId);
	        Common.waitForElement(2);
	        type(categorySearchTextBox, categoryName);
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        System.out.println("✅ Category selected for Product Sort");

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
	     action.clickAndHold(thirdProduct)
	       .pause(Duration.ofMillis(500))
	       .moveToElement(firstProduct, 0, -30) // Drop 30px above the first card
	       .pause(Duration.ofMillis(500))
	       .release()
	       .build()
	       .perform();
	     

	     Common.waitForElement(3);
	     System.out.println("✅ Dragged 3rd product to 1st position");

	        // Capture first product after sort
	         firstProductName = driver.findElement(
	            By.xpath("(//div[contains(@class,'sortable-card')])[1]//span[contains(@class,'product-name')]")
	        );
	        
	         expectedFirstProductName = firstProductName.getText().trim();
	        System.out.println("📌 First product after sorting (Admin): " + expectedFirstProductName + "");

	        Common.setValueInTestDataMap("ExpectedFirstProduct", expectedFirstProductName);

	        // Save sort
	        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	        System.out.println("✅ Sorting saved for category: " + categoryName);
	    

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
	 
	
	public void verifySortingCategoriesInUserApp() throws IOException, InterruptedException {
	    switchToWindow(1);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);

	 
	    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Categories in User App");
	    ExtentManager.setTest(test);
	    
	    //for moving catagory
	    String category = Common.getValueFromTestDataMap("Shop Name");
		   
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // Hover Shop menu
	    WebElement shopMenu = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
	    actions.moveToElement(shopMenu).perform();

	    // Get dropdown links
	    List<WebElement> dropdownLinks = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

	    boolean foundCategory = false;
	    for (WebElement link : dropdownLinks) {
	        if (link.getText().trim().equalsIgnoreCase(category.trim())) {
	            link.click();
	            System.out.println("✅ Navigated to Category: " + category);
	            foundCategory = true;
	            break;
	        }
	    }

	    if (!foundCategory) {
	        throw new RuntimeException("❌ Category not found in dropdown: " + category);
	    }

	    Common.waitForElement(2); // small wait for page load
	    

	    ExtentManager.getExtentReports().flush();
	}
	

	
	// Method to verify that the first product in category is correct
	public void verifyFirstProductInUserApp() throws InterruptedException {
	    // Get the expected first product from test data
	    String expectedFirstProduct = Common.getValueFromTestData("ExpectedFirstProduct"); 

	    // Scroll a bit to make products visible
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,340);");
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

	            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(expectedFirstProduct)) {
	                return elements.get(0);
	            }
	            return null;
	    });

	    if (card != null && card.isDisplayed()) {
	        System.out.println("✅ Product '" + expectedFirstProduct + "' is visible in User App for category in First Position ");
	    } else {
	        throw new RuntimeException("❌ Product '" + expectedFirstProduct + "' not found in User App for category: " );
	    }
	}

	
	//Sort Collection
	
	public void sortTheCollectionInAdminPanel() throws IOException {
	    Common.waitForElement(2);
	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    System.out.println("✅ Redirected to Admin product Categories page");
	    
	    String collectionName = Common.getValueFromTestDataMap("Shop Name");
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  
	        // Open Product Sort
	        Common.waitForElement(2);
	        click(searchProductSortMenu);
	        type(searchProductSortMenu, "Product Sorts");
	        click(clickProductSort);
	        System.out.println("✅ Selected Product Sorts");

	        click(addProductSort);
	        System.out.println("✅ Clicked add product Sort");
	        Common.waitForElement(2);

	        click(categoryType);
	        type(categorySearchTextBox, "Collection");
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        click(categoryId);
	        Common.waitForElement(2);
	        type(categorySearchTextBox, collectionName);
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        System.out.println("✅ Collection selected for Product Sort");

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
	     action.clickAndHold(thirdProduct)
	       .pause(Duration.ofMillis(500))
	       .moveToElement(firstProduct, 0, -30) // Drop 30px above the first card
	       .pause(Duration.ofMillis(500))
	       .release()
	       .build()
	       .perform();
	     

	     Common.waitForElement(3);
	     System.out.println("✅ Dragged 3rd product to 1st position");

	        // Capture first product after sort
	         collectionFirstProductName = driver.findElement(
	            By.xpath("(//div[contains(@class,'sortable-card')])[1]//span[contains(@class,'product-name')]")
	        );
	        
	         expectedcollectionFirstProductName = collectionFirstProductName.getText().trim();
	        System.out.println("📌 First product after sorting (Admin): " + expectedcollectionFirstProductName + "");

	        Common.setValueInTestDataMap("ExpectedCollectionFirstProduct", expectedcollectionFirstProductName);

	        // Save sort
	        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	        System.out.println("✅ Sorting saved for category: " + collectionName);
	    

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
	
	public void verifySortingCollectionInUserApp() throws IOException, InterruptedException {
	    switchToWindow(1);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);

	 
	    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Collections in User App");
	    ExtentManager.setTest(test);
	    
	    //for moving catagory
	    String collection = Common.getValueFromTestDataMap("Shop Name");
		   
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // Hover Shop menu
	    WebElement shopMenu = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
	    actions.moveToElement(shopMenu).perform();

	    // Get dropdown links
	    List<WebElement> dropdownLinks = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

	    boolean foundCollection = false;
	    for (WebElement link : dropdownLinks) {
	        if (link.getText().trim().equalsIgnoreCase(collection.trim())) {
	            link.click();
	            System.out.println("✅ Navigated to Collection: " + collection);
	            foundCollection = true;
	            break;
	        }
	    }

	    if (!foundCollection) {
	        throw new RuntimeException("❌ Collection not found in dropdown: " + collection);
	    }

	    Common.waitForElement(2); // small wait for page load
	    

	    ExtentManager.getExtentReports().flush();
	}
	

	
	// Method to verify that the first product in category is correct
	public void verifyFirstProductInUserAppCollection() throws InterruptedException {
	    // Get the expected first product from test data
	    String expectedFirstProduct = Common.getValueFromTestData("ExpectedCollectionFirstProduct"); 

	    // Scroll a bit to make products visible
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,340);");
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

	            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(expectedFirstProduct)) {
	                return elements.get(0);
	            }
	            return null;
	    });

	    if (card != null && card.isDisplayed()) {
	        System.out.println("✅ Product '" + expectedFirstProduct + "' is visible in User App for Collection in First Position ");
	    } else {
	        throw new RuntimeException("❌ Product '" + expectedFirstProduct + "' not found in User App for Collection: " );
	    }
	}

	
	//Sort Styles
	public void sortTheStylesInAdminPanel() throws IOException {
	    Common.waitForElement(2);  
	    String StylesName = Common.getValueFromTestDataMap("Shop Name");
	    
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  
	        // Open Product Sort
	        Common.waitForElement(2);
	        click(searchProductSortMenu);
	        type(searchProductSortMenu, "Product Sorts");
	        click(clickProductSort);
	        System.out.println("✅ Selected Product Sorts");

	        click(addProductSort);
	        System.out.println("✅ Clicked add product Sort");
	        Common.waitForElement(2);

	        click(categoryType);
	        type(categorySearchTextBox, "Product Style");
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        click(categoryId);
	        Common.waitForElement(2);
	        type(categorySearchTextBox, StylesName);
	        categorySearchTextBox.sendKeys(Keys.ENTER);
	        System.out.println("✅ Styles selected for Product Sort");

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
	     action.clickAndHold(thirdProduct)
	       .pause(Duration.ofMillis(500))
	       .moveToElement(firstProduct, 0, -30) // Drop 30px above the first card
	       .pause(Duration.ofMillis(500))
	       .release()
	       .build()
	       .perform();
	     

	     Common.waitForElement(3);
	     System.out.println("✅ Dragged 3rd product to 1st position");

	        // Capture first product after sort
	     stylesFirstProductName = driver.findElement(
	            By.xpath("(//div[contains(@class,'sortable-card')])[1]//span[contains(@class,'product-name')]")
	        );
	        
	         expectedStylesFirstProductName = stylesFirstProductName.getText().trim();
	        System.out.println("📌 First product after sorting (Admin): " + expectedStylesFirstProductName + "");

	        Common.setValueInTestDataMap("ExpectedStylesFirstProduct", expectedStylesFirstProductName);

	        // Save sort
	        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
	        System.out.println("✅ Sorting saved for category: " + StylesName);
	    

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
	
	public void verifySortingStylesInUserApp() throws IOException, InterruptedException {
	    switchToWindow(1);
	    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	    Common.waitForElement(3);

	 
	    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Styles in User App");
	    ExtentManager.setTest(test);
	    
	    //for moving catagory
	    String styles = Common.getValueFromTestDataMap("Shop Name");
		   
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	    Actions actions = new Actions(driver);

	    // Hover Shop menu
	    WebElement shopMenu = wait.until(ExpectedConditions
	            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
	    actions.moveToElement(shopMenu).perform();

	    // Get dropdown links
	    List<WebElement> dropdownLinks = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

	    boolean foundCollection = false;
	    for (WebElement link : dropdownLinks) {
	        if (link.getText().trim().equalsIgnoreCase(styles.trim())) {
	            link.click();
	            System.out.println("✅ Navigated to Styles: " + styles);
	            foundCollection = true;
	            break;
	        }
	    }

	    if (!foundCollection) {
	        throw new RuntimeException("❌ Styles not found in dropdown: " + styles);
	    }

	    Common.waitForElement(2); // small wait for page load
	    

	    ExtentManager.getExtentReports().flush();
	}
	

	
	// Method to verify that the first product in category is correct
	public void verifyFirstProductInUserAppStyles() throws InterruptedException {
	    // Get the expected first product from test data
	    String expectedFirstProduct = Common.getValueFromTestData("ExpectedStylesFirstProduct"); 

	    // Scroll a bit to make products visible
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,340);");
	    Common.waitForElement(2);

	    // Wait for the product to appear in product cards
	    FluentWait<WebDriver> wait = new FluentWait<>(driver)
	            .withTimeout(Duration.ofMinutes(10))
	            .pollingEvery(Duration.ofSeconds(3))
	            .ignoring(NoSuchElementException.class)
	            .ignoring(StaleElementReferenceException.class);

	    WebElement card = wait.until(d -> {
	        driver.navigate().refresh();
	        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

	        List<WebElement> elements = d.findElements(By.xpath(
	                "//div[@id='cls_newproduct_sec_dev']/div[contains(@class,'product_list_cards_list')][1]//h2[@class='product_list_cards_heading']"
	            ));

	            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(expectedFirstProduct)) {
	                return elements.get(0);
	            }
	            return null;
	    });

	    if (card != null && card.isDisplayed()) {
	        System.out.println("✅ Product '" + expectedFirstProduct + "' is visible in User App for Styles in First Position ");
	    } else {
	    	System.out.println("Product not sorted  within time period ");
	        throw new RuntimeException("❌ Product '" + expectedFirstProduct + "' not found in User App for Styles: " );
	    }
	}

	
	
//Sort Micro Page
	
	
		public void sortTheMicroPageInAdminPanel() throws IOException {
		    Common.waitForElement(2);  
		    String MicroPageName = Common.getValueFromTestDataMap("Shop Name");
		    
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	  
		        // Open Product Sort
		        Common.waitForElement(2);
		        click(searchProductSortMenu);
		        type(searchProductSortMenu, "Product Sorts");
		        click(clickProductSort);
		        System.out.println("✅ Selected Product Sorts");

		        click(addProductSort);
		        System.out.println("✅ Clicked add product Sort");
		        Common.waitForElement(2);

		        click(categoryType);
		        type(categorySearchTextBox, "Micro page");
		        categorySearchTextBox.sendKeys(Keys.ENTER);
		        click(categoryId);
		        Common.waitForElement(2);
		        type(categorySearchTextBox, MicroPageName);
		        categorySearchTextBox.sendKeys(Keys.ENTER);
		        System.out.println("✅ MicroPage selected for Product Sort");

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
		     action.clickAndHold(thirdProduct)
		       .pause(Duration.ofMillis(500))
		       .moveToElement(firstProduct, 0, -30) // Drop 30px above the first card
		       .pause(Duration.ofMillis(500))
		       .release()
		       .build()
		       .perform();
		     

		     Common.waitForElement(3);
		     System.out.println("✅ Dragged 3rd product to 1st position");

		        // Capture first product after sort
		     MicroPageFirstProductName = driver.findElement(
		            By.xpath("(//div[contains(@class,'sortable-card')])[1]//span[contains(@class,'product-name')]")
		        );
		        
		         expectedMicroPageFirstProductName = MicroPageFirstProductName.getText().trim();
		        System.out.println("📌 First product after sorting (Admin): " + expectedMicroPageFirstProductName + "");

		        Common.setValueInTestDataMap("ExpectedMicroPageFirstProduct", expectedMicroPageFirstProductName);

		        // Save sort
		        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		        System.out.println("✅ Sorting saved for category: " + MicroPageName);
		    

		    // Clear cache
		    Common.waitForElement(2);
		    click(clearCatchButton);
		    System.out.println("✅ Cleared cache");
		    Common.waitForElement(6);
		    click(clearCatchButton);
		    System.out.println("✅ Cleared cache");
		    Common.waitForElement(3);

		    System.out.println("🎉 MicroPage sorting completed successfully!");
		}
		
		public void verifySortingMicroPageInUserApp() throws IOException, InterruptedException {
		    switchToWindow(1);
		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		    Common.waitForElement(3);

		    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify MicroPage in User App");
		    ExtentManager.setTest(test);

		    // ✅ Get shop name from Excel / Test Data
		    String microPage = Common.getValueFromTestDataMap("Shop Name");
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

		    // ✅ Step 1: Search Shop
		    Common.waitForElement(2);
		    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		    searchBox.clear();
		    searchBox.sendKeys(microPage);
		    searchBox.sendKeys(Keys.ENTER);
		    System.out.println("✅ Searched for Shop: " + microPage);

		    Common.waitForElement(3); // wait for results

		    // ✅ Step 2: Reopen Search Box
		   wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		    searchBox.click();
		    System.out.println("✅ Clicked on Search box again to open search history");

		    // ✅ Step 3: Select from Search History
		    WebElement historyItem = wait.until(ExpectedConditions.elementToBeClickable(
		            By.xpath("//div[@class='search_history_item_name' and normalize-space(text())='" + microPage + "']")));
		    historyItem.click();
		    System.out.println("✅ Clicked on search history item: " + microPage);

		    Common.waitForElement(2);

		    ExtentManager.getExtentReports().flush();
		}
		

		
		// Method to verify that the first product in category is correct
		public void verifyFirstProductInUserAppMicroPage() throws InterruptedException {
		    // Get the expected first product from test data
		    String expectedFirstProduct = Common.getValueFromTestData("ExpectedMicroPageFirstProduct"); 

		    // Scroll a bit to make products visible
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,340);");
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

		            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(expectedFirstProduct)) {
		                return elements.get(0);
		            }
		            return null;
		    });

		    if (card != null && card.isDisplayed()) {
		        System.out.println("✅ Product '" + expectedFirstProduct + "' is visible in User App for MicroPage in First Position ");
		    } else {
		        throw new RuntimeException("❌ Product '" + expectedFirstProduct + "' not found in User App for MicroPage: " );
		    }
		}


	
	
	
//Sort All Product
		public void sortTheAllProductInAdminPanel() throws IOException {
		    Common.waitForElement(2);  
		    String AllProductName = Common.getValueFromTestDataMap("Shop Name");
		    
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	  
		        // Open Product Sort
		        Common.waitForElement(2);
		        click(searchProductSortMenu);
		        type(searchProductSortMenu, "Product Sorts");
		        click(clickProductSort);
		        System.out.println("✅ Selected Product Sorts");

		        click(addProductSort);
		        System.out.println("✅ Clicked add product Sort");
		        Common.waitForElement(2);

		        click(categoryType);
		        type(categorySearchTextBox, "All Product");
		        categorySearchTextBox.sendKeys(Keys.ENTER);
		        System.out.println("✅ AllProduct selected for Product Sort");

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
		     action.clickAndHold(thirdProduct)
		       .pause(Duration.ofMillis(500))
		       .moveToElement(firstProduct, 0, -30) // Drop 30px above the first card
		       .pause(Duration.ofMillis(500))
		       .release()
		       .build()
		       .perform();
		     

		     Common.waitForElement(3);
		     System.out.println("✅ Dragged 3rd product to 1st position");

		        // Capture first product after sort
		     AllProductFirstProductName = driver.findElement(
		            By.xpath("(//div[contains(@class,'sortable-card')])[1]//span[contains(@class,'product-name')]")
		        );
		        
		         expectedAllProductFirstProductName = AllProductFirstProductName.getText().trim();
		        System.out.println("📌 First product after sorting (Admin): " + expectedAllProductFirstProductName + "");

		        Common.setValueInTestDataMap("ExpectedAllProductFirstProduct", expectedAllProductFirstProductName);

		        // Save sort
		        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		        Common.waitForElement(2);
		        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
		        System.out.println("✅ Sorting saved for AllProduct: " + AllProductName);
		    

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
		
		public void verifySortingAllProductInUserApp() throws IOException, InterruptedException {
		    switchToWindow(1);
		    driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
		    Common.waitForElement(3);

		 
		    ExtentTest test = ExtentManager.getExtentReports().createTest("Verify Styles in User App");
		    ExtentManager.setTest(test);
		    
		    //for moving catagory
		    String AllProduct = Common.getValueFromTestDataMap("Shop Name");
			   
		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		    Actions actions = new Actions(driver);
		    
		    // Hover Shop menu
		    WebElement shopMenu = wait.until(ExpectedConditions
		            .visibilityOfElementLocated(By.xpath("//span[@class='navigation_menu_txt'][normalize-space()='Shop']")));
		    actions.moveToElement(shopMenu).perform();

		    // Get dropdown links
		    List<WebElement> dropdownLinks = wait.until(ExpectedConditions
		            .visibilityOfAllElementsLocatedBy(By.xpath("//div[@class='nav_drop_down_box_category active']//ul/li/a")));

		    boolean foundCollection = false;
		    for (WebElement link : dropdownLinks) {
		        if (link.getText().trim().equalsIgnoreCase(AllProduct.trim())) {
		            link.click();
		            System.out.println("✅ Navigated to AllProduct: " + AllProduct);
		            foundCollection = true;
		            break;
		        }
		    }

		    if (!foundCollection) {
		        throw new RuntimeException("❌ AllProduct not found in dropdown: " + AllProduct);
		    }

		    Common.waitForElement(2); // small wait for page load
		    

		    ExtentManager.getExtentReports().flush();
		}
		

		
		// Method to verify that the first product in category is correct
		public void verifyFirstProductInUserAppAllProduct() throws InterruptedException {
		    // Get the expected first product from test data
		    String expectedFirstProduct = Common.getValueFromTestData("ExpectedAllProductFirstProduct"); 

		    // Scroll a bit to make products visible
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,340);");
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

		            if (!elements.isEmpty() && elements.get(0).getText().trim().equals(expectedFirstProduct)) {
		                return elements.get(0);
		            }
		            return null;
		    });

		    if (card != null && card.isDisplayed()) {
		        System.out.println("✅ Product '" + expectedFirstProduct + "' is visible in User App for AllProduct in First Position ");
		    } else {
		        throw new RuntimeException("❌ Product '" + expectedFirstProduct + "' not found in User App for AllProduct: " );
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
