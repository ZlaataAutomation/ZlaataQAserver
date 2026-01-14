package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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

	
	public void captureApplicationTryAlong() {

	    HomePage home = new HomePage(driver);
	    home.homeLaunch();

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // ================= NAVIGATE TO PLP =================
	    Actions actions = new Actions(driver);
	    actions.moveToElement(shopMenu).perform();
	    actions.moveToElement(category).click().perform();

	    Common.waitForElement(2);

	    // ================= FETCH PRODUCTS =================
	    List<WebElement> products = wait.until(
	            ExpectedConditions.visibilityOfAllElementsLocatedBy(
	                    By.xpath("//div[contains(@class,'product_list_cards_list')]")
	            )
	    );

	    if (products.isEmpty()) {
	        Assert.fail("❌ No products found on listing page");
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
	            System.out.println("⏭ Skipping multi-color product → " + productName);
	            continue;
	        }

	        System.out.println("✅ Selected product → " + productName);

	        WebElement productNameElement = productCard.findElement(
	                By.xpath(".//h2[@class='product_list_cards_heading']")
	        );

	        js.executeScript("arguments[0].click();", productNameElement);
	        productFound = true;
	        break;
	    }

	    if (!productFound) {
	        Assert.fail("❌ No single-color product found after retries");
	    }

	    // ================= SCROLL TO TRY ALONG =================
	    js.executeScript("window.scrollBy(0,1200);");
	    Common.waitForElement(2);

	    // ================= MAIN PRODUCT =================
	    appMainProduct = mainProductName.getText().trim();
	    System.out.println("\nAPPLICATION MAIN PRODUCT → " + appMainProduct);

	    // ================= TRY ALONG BEFORE REFRESH =================
	    appTryAlongBeforeRefresh.clear();
	    System.out.println("\nTRY ALONG (BEFORE REFRESH):");
	    readTryAlong(appTryAlongBeforeRefresh);

	    // ================= REFRESH PAGE =================
	    System.out.println("\n🔄 Refreshing page...");
	    driver.navigate().refresh();
	    Common.waitForElement(5);

	    js.executeScript("window.scrollBy(0,1200);");
	    Common.waitForElement(2);

	    // ================= TRY ALONG AFTER REFRESH =================
	    appTryAlongAfterRefresh.clear();
	    System.out.println("\nTRY ALONG (AFTER REFRESH):");
	    readTryAlong(appTryAlongAfterRefresh);

	    // ================= ASSERT NOT SHUFFLED =================
	    assertTryAlongNotShuffled();
	}


	private void readTryAlong(List<String> targetList) {

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    for (WebElement viewBtn : tryAlongProductsViewButton) {
	        js.executeScript("arguments[0].scrollIntoView(true);", viewBtn);
	        js.executeScript("arguments[0].click();", viewBtn);
	        Common.waitForElement(2);

	        String name = tryAlongProduct.getText().trim();
	        targetList.add(name);
	        System.out.println(" - " + name);

	        js.executeScript("arguments[0].click();", closeTheQuickViewPopup);
	        Common.waitForElement(1);
	    }
	}
	
	private void assertTryAlongNotShuffled() {

	    System.out.println("\n========== VERIFY TRY ALONG AFTER REFRESH ==========");

	    if (appTryAlongBeforeRefresh.size() != appTryAlongAfterRefresh.size()) {
	        Assert.fail(
	            "❌ Try Along COUNT changed after refresh\n" +
	            "Before: " + appTryAlongBeforeRefresh.size() +
	            " | After: " + appTryAlongAfterRefresh.size()
	        );
	    }

	    for (int i = 0; i < appTryAlongBeforeRefresh.size(); i++) {

	        String before = appTryAlongBeforeRefresh.get(i);
	        String after  = appTryAlongAfterRefresh.get(i);

	        System.out.println(
	                "Position " + (i + 1) +
	                "\n BEFORE → " + before +
	                "\n AFTER  → " + after
	        );

	        if (!before.equalsIgnoreCase(after)) {
	            Assert.fail(
	                "❌ Try Along product changed after refresh at position " + (i + 1) +
	                "\nBefore: " + before +
	                "\nAfter : " + after
	            );
	        }
	    }

	    System.out.println("✅ PASSED: Try Along products SAME before & after refresh");
	}

	
	  public void adminLogin() {

	        driver.get(FileReaderManager.getInstance()
	                .getConfigReader()
	                .getApplicationAdminUrl());

	        type(adminEmail,
	                FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	        type(adminPassword,
	                FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));

	        click(adminLogin);
	        System.out.println(" Admin Login Successful");
	    }
	
	
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
	            System.out.println("✅ ADMIN MAIN PRODUCT MATCHED → " + value);
	            break;
	        }
	    }

	    if (!matched) {
	        Assert.fail("❌ MAIN PRODUCT NOT FOUND IN ADMIN");
	    }

	    // ADMIN TRY ALONG
	    adminTryAlongProducts.clear();

	    List<WebElement> adminTryAlong =
	            driver.findElements(By.xpath("//div[contains(@class,'sort-try-along-wrapper')]//small"));

	    for (WebElement e : adminTryAlong) {
	        adminTryAlongProducts.add(removeSku(e.getText().trim()));
	    }

	    System.out.println("\nADMIN TRY ALONG:");
	    adminTryAlongProducts.forEach(p -> System.out.println(" - " + p));
	}
	public void finalComparison() {

	    System.out.println("\n========== FINAL COMPARISON ==========");

	    for (int i = 0; i < 3; i++) {

	        String before = appTryAlongBeforeRefresh.get(i);
	        String after  = appTryAlongAfterRefresh.get(i);
	        String admin  = adminTryAlongProducts.get(i);

	        System.out.println(
	            "\nPosition " + (i + 1) +
	            "\n APP BEFORE → " + before +
	            "\n APP AFTER  → " + after +
	            "\n ADMIN      → " + admin
	        );

	        if (!before.equalsIgnoreCase(admin)) {
	            Assert.fail("❌ Mismatch BEFORE refresh at position " + (i + 1));
	        }

	        if (!after.equalsIgnoreCase(admin)) {
	            Assert.fail("❌ Mismatch AFTER refresh at position " + (i + 1));
	        }
	    }

	    System.out.println("\n✅ PASSED: Admin & Application Try Along SAME before & after refresh");
	}
	private String removeSku(String name) {
	    // Removes anything inside brackets like (ZL123-BK)
	    return name.replaceAll("\\s*\\(.*?\\)", "").trim();
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
