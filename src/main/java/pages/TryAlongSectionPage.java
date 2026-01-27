package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.TryAlongSectionObjRepo;
import utils.Common;

public  final class  TryAlongSectionPage  extends TryAlongSectionObjRepo{

	public TryAlongSectionPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}

	
	
	
	String appMainProduct;

	List<String> appTryAlongBeforeRefresh = new ArrayList<>();
	List<String> appTryAlongAfterRefresh  = new ArrayList<>();
	List<String> adminTryAlongProducts    = new ArrayList<>();

	
	// ----------------- ANSI COLORS -----------------
	private static final String RESET = "\u001B[0m";
	private static final String RED = "\u001B[31m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String CYAN = "\u001B[36m";
	private static final String MAGENTA = "\u001B[35m";

	// =================== CAPTURE APPLICATION TRY ALONG ===================
	public void captureApplicationTryAlong() {

	    HomePage home = new HomePage(driver);
	    home.homeLaunch();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // ================= NAVIGATE TO PLP =================
	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    // ================= FETCH PRODUCTS =================
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[contains(@class,'product_list_cards_list')]")
	            )
	    );

	    if (products.isEmpty()) {
	        Assert.fail(RED + "❌ No products found on listing page" + RESET);
	    }

	    Random rand = new Random();
	    boolean productFound = false;
	    int maxAttempts = Math.min(5, products.size());

	    // ================= PICK SINGLE COLOR PRODUCT =================
	    for (int attempt = 1; attempt <= maxAttempts; attempt++) {

	        WebElement productCard = products.get(rand.nextInt(products.size()));
	        String productName = productCard.findElement(
	                By.xpath(".//h2[@class='product_list_cards_heading']")
	        ).getText().trim();

	        // Skip multi-color products
	        List<WebElement> colorSwatches = productCard.findElements(
	                By.xpath(".//div[contains(@class,'zl-prod-color-swatches')]//span")
	        );

	        if (!colorSwatches.isEmpty()) {
	            System.out.println(YELLOW + "⏭ Skipping multi-color product → " + productName + RESET);
	            continue;
	        }

	        System.out.println(GREEN + "✅ Selected product → " + productName + RESET + "\n");

	        WebElement productNameElement = productCard.findElement(
	                By.xpath(".//h2[@class='product_list_cards_heading']")
	        );

	        js.executeScript("arguments[0].click();", productNameElement);
	        productFound = true;
	        break;
	    }

	    if (!productFound) {
	        Assert.fail(RED + "❌ No single-color product found after retries" + RESET);
	    }

	    // ================= SCROLL TO TRY ALONG =================
	    js.executeScript("window.scrollBy(0,1200);");

	    // ================= MAIN PRODUCT =================
	    appMainProduct = mainProductName.getText().trim();
	    System.out.println("\n" + CYAN + "APPLICATION MAIN PRODUCT → " + GREEN + appMainProduct + RESET + "\n");

	    // ================= TRY ALONG BEFORE REFRESH =================
	    appTryAlongBeforeRefresh.clear();
	    System.out.println(CYAN + "\nTRY ALONG (BEFORE REFRESH):" + RESET);
	    readTryAlong(appTryAlongBeforeRefresh);

	    // ================= REFRESH PAGE =================
	    System.out.println(MAGENTA + "\n🔄 Refreshing page..." + RESET);
	    driver.navigate().refresh();

	    js.executeScript("window.scrollBy(0,1200);");

	    // ================= TRY ALONG AFTER REFRESH =================
	    appTryAlongAfterRefresh.clear();
	    System.out.println(CYAN + "\nTRY ALONG (AFTER REFRESH):" + RESET);
	    readTryAlong(appTryAlongAfterRefresh);

	    // ================= ASSERT NOT SHUFFLED =================
	    assertTryAlongNotShuffled();
	}

	// =================== READ TRY ALONG ===================
	private void readTryAlong(List<String> targetList) {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    for (WebElement viewBtn : tryAlongProductsViewButton) {
	        js.executeScript("arguments[0].scrollIntoView(true);", viewBtn);
	        js.executeScript("arguments[0].click();", viewBtn);
	        Common.waitForElement(2);

	        String name = tryAlongProduct.getText().trim();
	        targetList.add(name);
	        System.out.println(YELLOW + " - " + name + RESET);

	        js.executeScript("arguments[0].click();", closeTheQuickViewPopup);
	        Common.waitForElement(1);
	    }
	}

	// =================== ASSERT TRY ALONG ===================
	private void assertTryAlongNotShuffled() {

	    System.out.println(CYAN + "\n========== VERIFY TRY ALONG AFTER REFRESH ==========" + RESET);

	    if (appTryAlongBeforeRefresh.size() != appTryAlongAfterRefresh.size()) {
	        Assert.fail(RED +
	            "❌ Try Along COUNT changed after refresh\n" +
	            "Before: " + appTryAlongBeforeRefresh.size() +
	            " | After: " + appTryAlongAfterRefresh.size() +
	            RESET
	        );
	    }

	    for (int i = 0; i < appTryAlongBeforeRefresh.size(); i++) {

	        String before = appTryAlongBeforeRefresh.get(i);
	        String after  = appTryAlongAfterRefresh.get(i);

	        System.out.println(
	                MAGENTA + "Position " + (i + 1) + RESET +
	                "\n BEFORE → " + YELLOW + before + RESET +
	                "\n AFTER  → " + YELLOW + after + RESET + "\n"
	        );

	        if (!before.equalsIgnoreCase(after)) {
	            Assert.fail(RED +
	                "❌ Try Along product changed after refresh at position " + (i + 1) +
	                "\nBefore: " + before +
	                "\nAfter : " + after + RESET
	            );
	        }
	    }

	    System.out.println(GREEN + "✅ PASSED: Try Along products SAME before & after refresh\n" + RESET);
	}

	// =================== ADMIN LOGIN ===================
	public void adminLogin() {

	    driver.get(FileReaderManager.getInstance()
	            .getConfigReader()
	            .getApplicationAdminUrl());

	    type(adminEmail,
	            FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword,
	            FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));

	    click(adminLogin);
	    System.out.println(GREEN + "\n Admin Login Successful\n" + RESET);
	}

	// =================== CAPTURE ADMIN TRY ALONG ===================
	public void captureAdminTryAlong() {

	    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	    Common.waitForElement(5);

	    click(productDetailsButton);
	    Common.waitForElement(3);

	    productDetailsButtondropdown.sendKeys(appMainProduct);
	    Common.waitForElement(3);
	    productDetailsButtondropdown.sendKeys(Keys.ENTER);
	    Common.waitForElement(3);

	    click(editbuttonOnProductSection);
	    Common.waitForElement(3);
	    click(itemButton);
	    Common.waitForElement(3);

	    // MATCH MAIN PRODUCT
	    boolean matched = false;
	    List<WebElement> adminMainProducts =
	            driver.findElements(By.xpath("//input[contains(@name,'filters')][contains(@name,'[name]')]"));

	    for (WebElement input : adminMainProducts) {
	        String value = input.getAttribute("value").trim();
	        if (value.equalsIgnoreCase(appMainProduct)) {
	            matched = true;
	            System.out.println(GREEN + "\n✅ ADMIN MAIN PRODUCT MATCHED → " + value + RESET + "\n");
	            break;
	        }
	    }
	    if (!matched) {
	        Assert.fail(RED + "\n❌ MAIN PRODUCT NOT FOUND IN ADMIN\n" + RESET);
	    }

	    // ADMIN TRY ALONG
	    adminTryAlongProducts.clear();
	    List<WebElement> adminTryAlong =
	            driver.findElements(By.xpath("//div[contains(@class,'sort-try-along-wrapper')]//small"));

	    for (WebElement e : adminTryAlong) {
	        adminTryAlongProducts.add(removeSku(e.getText().trim()));
	    }

	    System.out.println(CYAN + "ADMIN TRY ALONG:" + RESET);
	    adminTryAlongProducts.forEach(p -> System.out.println(YELLOW + " - " + p + RESET));
	}

	// =================== FINAL COMPARISON ===================
	public void finalComparison() {

	    System.out.println(CYAN + "\n========== FINAL COMPARISON ==========" + RESET);

	    for (int i = 0; i < 3; i++) {

	        String before = appTryAlongBeforeRefresh.get(i);
	        String after  = appTryAlongAfterRefresh.get(i);
	        String admin  = adminTryAlongProducts.get(i);

	        System.out.println(
	            MAGENTA + "\nPosition " + (i + 1) + RESET +
	            "\n APP BEFORE → " + YELLOW + before + RESET +
	            "\n APP AFTER  → " + YELLOW + after + RESET +
	            "\n ADMIN      → " + GREEN + admin + RESET + "\n"
	        );

	        if (!before.equalsIgnoreCase(admin)) {
	            Assert.fail(RED + "❌ Mismatch BEFORE refresh at position " + (i + 1) + RESET);
	        }

	        if (!after.equalsIgnoreCase(admin)) {
	            Assert.fail(RED + "❌ Mismatch AFTER refresh at position " + (i + 1) + RESET);
	        }
	    }

	    System.out.println(GREEN + "\n✅ PASSED: Admin & Application Try Along SAME before & after refresh\n" + RESET);
	}

	// =================== REMOVE SKU FROM NAME ===================
	private String removeSku(String name) {
	    return name.replaceAll("\\s*\\(.*?\\)", "").trim();
	}

	
	

	//2
		
		public void addNewTryalongProduct() {

		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));

		    click(productDetailsButton);
		    productDetailsButtondropdown.sendKeys(appMainProduct);
		    Common.waitForElement(3);
		    productDetailsButtondropdown.sendKeys(Keys.ENTER);

		    click(editbuttonOnProductSection);
		    click(itemButton);

		    // ---------------- MATCH MAIN PRODUCT ----------------
		    boolean matched = false;
		    List<WebElement> adminMainProducts =
		            driver.findElements(By.xpath("//input[contains(@name,'filters')][contains(@name,'[name]')]"));

		    for (WebElement input : adminMainProducts) {
		        String value = input.getAttribute("value").trim();
		        if (value.equalsIgnoreCase(appMainProduct)) {
		            matched = true;
		            System.out.println("\n" + GREEN + "✅ ADMIN MAIN PRODUCT MATCHED → " + value + RESET + "\n");
		            break;
		        }
		    }
		    if (!matched) {
		        Assert.fail("\n" + RED + "❌ MAIN PRODUCT NOT FOUND IN ADMIN" + RESET + "\n");
		    }

		    // ---------------- ADMIN TRY ALONG ----------------
		    Set<String> adminTryAlongSet = new LinkedHashSet<>();
		    List<WebElement> adminTryAlong =
		            driver.findElements(By.xpath("//div[contains(@class,'sort-try-along-wrapper')]//small"));

		    for (WebElement e : adminTryAlong) {
		        String text = e.getText().trim();
		        if (!text.isEmpty()) adminTryAlongSet.add(text);
		    }
		    List<String> adminTryAlongProducts = new ArrayList<>(adminTryAlongSet);

		    System.out.println("\n" + CYAN + "================ ADMIN TRY ALONG PRODUCTS (unique) ================" + RESET + "\n");
		    adminTryAlongProducts.forEach(p -> System.out.println(YELLOW + " - " + p + RESET));
		    System.out.println("\n" + CYAN + "===============================================================\n" + RESET);

		    // ---------------- BACK & FILTER ACTIVE ----------------
		    click(BacktoAllProductButon);
		    click(resetButton);
		    click(statusButton);
		    statusDropDown.sendKeys("Active");
		    statusDropDown.sendKeys(Keys.ENTER);

		    // ---------------- RANDOM PRODUCT + SKU CHECK ----------------
		    List<WebElement> editButtons = driver.findElements(By.xpath("//i[@class='las la-edit']"));
		    Random rand = new Random();
		    int attempts = 0;
		    int maxAttempts = 10;
		    String finalSku = null;

		    while (attempts < maxAttempts && !editButtons.isEmpty()) {
		        int randomIndex = rand.nextInt(editButtons.size());
		        WebElement editBtn = editButtons.get(randomIndex);
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editBtn);
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editBtn);

		        click(itemButton);
		        String skuValue = skuCopy.getAttribute("value").trim();
		        System.out.println("\n" + CYAN + "Attempt " + (attempts + 1) + " | Copied SKU: " + skuValue + RESET + "\n");

		        if (adminTryAlongProducts.contains(skuValue)) {
		            System.out.println(RED + "❌ SKU already in Try Along → trying another product\n" + RESET);
		            click(BacktoAllProductButon);
		            click(resetButton);
		            click(statusButton);
		            statusDropDown.sendKeys("Active");
		            statusDropDown.sendKeys(Keys.ENTER);
		            editButtons.remove(randomIndex);
		            attempts++;
		        } else {
		            System.out.println(GREEN + "✅ VALID SKU FOUND → " + skuValue + "\n" + RESET);
		            finalSku = skuValue;
		            click(BacktoAllProductButon);
		            click(resetButton);
		            click(statusButton);
		            statusDropDown.sendKeys("Active");
		            statusDropDown.sendKeys(Keys.ENTER);
		            break;
		        }
		    }

		    // ---------------- OPEN MAIN PRODUCT ----------------
		    click(productDetailsButton);
		    productDetailsButtondropdown.clear();
		    productDetailsButtondropdown.sendKeys(appMainProduct);
		    Common.waitForElement(2);
		    productDetailsButtondropdown.sendKeys(Keys.ENTER);
		    click(editbuttonOnProductSection);
		    click(itemButton);

		    WebElement adminMainProductNameEle = driver.findElement(
		            By.xpath("//input[contains(@name,'filters')][contains(@name,'[name]')]")
		    );
		    String adminMainProductName = adminMainProductNameEle.getAttribute("value").trim();
		    System.out.println("\n" + GREEN + "ADMIN MAIN PRODUCT → " + adminMainProductName + RESET + "\n");

		    // ---------------- COLLECT EXISTING TRY ALONG SKUs ----------------
		    Set<String> oldSkusSet = new LinkedHashSet<>();
		    List<WebElement> tryAlongCards = driver.findElements(By.cssSelector("div.sortable-card"));
		    System.out.println(CYAN + "Total Try Along SKUs found in DOM: " + tryAlongCards.size() + RESET + "\n");

		    for (WebElement card : tryAlongCards) {
		        String fullText = card.getText().trim();
		        if (fullText.contains("Size")) fullText = fullText.split("Size")[0].trim();
		        if (!fullText.isEmpty()) oldSkusSet.add(fullText);
		    }

		    List<String> oldSkus = new ArrayList<>(oldSkusSet);
		    System.out.println(CYAN + "Stored old Try Along SKUs (unique):\n" + RESET);
		    oldSkus.forEach(s -> System.out.println(YELLOW + " - " + s + RESET));
		    System.out.println("\n---------------------------------------------------------------\n");

		    // ---------------- REMOVE ALL SKUs ----------------
		    List<WebElement> removeButtons = driver.findElements(By.xpath(
		            "//div[contains(@class,'sort-try-along-wrapper0')]//div[contains(@class,'sortable-card')]//i[contains(@class,'sort-clear-btn')]"
		    ));
		    for (WebElement btn : removeButtons) {
		        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
		        Common.waitForElement(1);
		        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
		        Common.waitForElement(1);
		    }

		    // ---------------- ADD NEW SKU (FIRST POSITION) ----------------
		    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -200);");
		    WebElement tryAlongDropdown = driver.findElement(
		            By.xpath("//div[@class='form-group col-12 required']//span[@class='select2-selection__arrow']")
		    );
		    tryAlongDropdown.click();

		    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		    WebElement select2Input = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//span[@class='select2-search select2-search--dropdown']//input[@role='searchbox']")
		            )
		    );

		    select2Input.sendKeys(finalSku);
		    Common.waitForElement(1);
		    select2Input.sendKeys(Keys.ENTER);
		    Common.waitForElement(2);

		    List<WebElement> tryAlongCardsAfter = driver.findElements(By.cssSelector("div.sortable-card"));
		    String newProductName = "";
		    for (WebElement card : tryAlongCardsAfter) {
		        String text = card.getText().trim();
		        if (text.contains(finalSku)) {
		            newProductName = text;
		            break;
		        }
		    }
		    System.out.println(GREEN + "✅ New PRODUCT added as FIRST → " + newProductName + RESET + "\n");

		    // ---------------- ADD TWO OLD SKUs ----------------
		    int count = 0;
		    Set<String> printedOld = new HashSet<>();
		    for (String sku : oldSkus) {
		        if (sku.contains(finalSku)) continue;
		        if (count == 2) break;

		        WebElement tryAlongDropdownAgain = driver.findElement(
		                By.xpath("//div[@class='form-group col-12 required']//span[@class='select2-selection__arrow']")
		        );
		        tryAlongDropdownAgain.click();

		        WebElement select2InputAgain = wait.until(
		                ExpectedConditions.visibilityOfElementLocated(
		                        By.xpath("//span[@class='select2-search select2-search--dropdown']//input[@role='searchbox']")
		                )
		        );

		        select2InputAgain.clear();
		        select2InputAgain.sendKeys(sku);
		        Common.waitForElement(1);
		        select2InputAgain.sendKeys(Keys.ENTER);
		        Common.waitForElement(2);

		        if (!printedOld.contains(sku)) {
		            System.out.println(YELLOW + "♻ Re-added old PRODUCT → " + sku + RESET + "\n");
		            printedOld.add(sku);
		            count++;
		        }
		    }

		    // ---------------- CLICK SAVE BUTTON ----------------
		    WebElement saveButton = driver.findElement(
		            By.xpath("//span[@data-value='save_and_back']")
		    );
		    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", saveButton);
		    Common.waitForElement(1);
		    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveButton);
		    Common.waitForElement(5);

		    System.out.println(GREEN + "💾 Product saved successfully with updated Try Along SKUs\n" + RESET);

		    // ---------------- SEARCH IN APPLICATION ----------------
		    
		    
		    
		    HomePage home = new HomePage(driver);
		    home.homeLaunch();
		    Common.waitForElement(5);

		    WebDriverWait appWait = new WebDriverWait(driver, Duration.ofSeconds(15));
		    WebElement searchBar = appWait.until(
		            ExpectedConditions.elementToBeClickable(
		                    By.xpath("//div[@class='navigation_search_input_box']")
		            )
		    );
		    searchBar.click();

		    WebElement searchInput = appWait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//div[@class='navigation_search_input_box']//input")
		            )
		    );
		    searchInput.sendKeys(appMainProduct);
		    searchInput.sendKeys(Keys.ENTER);
		    Common.waitForElement(5);

		    System.out.println(CYAN + "\n🔍 Application search → " + appMainProduct + RESET + "\n");

		    productImage.click();
		    wait.until(ExpectedConditions.urlContains("product-detail"));
		    System.out.println("Navigated to Product Detail Page\n");

		    WebElement productName = wait.until(
		            ExpectedConditions.visibilityOfElementLocated(
		                    By.xpath("//h4[@class='prod_name']")
		            )
		    );
		    String appMainProductName = productName.getText().trim();
		    System.out.println(GREEN + "APP MAIN PRODUCT → " + appMainProductName + RESET + "\n");

		    List<String> appTryAlongAfterUpdate = new ArrayList<>();
		    System.out.println(CYAN + "TRY ALONG (AFTER UPDATE):\n" + RESET);
		    readTryAlong(appTryAlongAfterUpdate);

		    System.out.println(GREEN + "\n🆕 NEWLY ADDED TRY ALONG PRODUCTS:" + RESET);
		    for (String product : appTryAlongAfterUpdate) {
		        if (!appTryAlongBeforeRefresh.contains(product)) {
		            System.out.println(YELLOW + " - " + product + RESET + "\n");
		        }
		    }

		    System.out.println(GREEN + "\n✅ Comparison completed.\n" + RESET);
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
