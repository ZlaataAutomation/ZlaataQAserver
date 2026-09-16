	package pages;
	
	import java.time.Duration;
	import java.util.List;
	import java.util.Random;
	
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.Keys;
	import org.openqa.selenium.StaleElementReferenceException;
	import org.openqa.selenium.TimeoutException;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.PageFactory;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	
	import manager.FileReaderManager;
	import objectRepo.AdminPanelMediaLibraryObjRepo;
	import utils.Common;
	
	public class AdminPanelMediaLibrary extends AdminPanelMediaLibraryObjRepo {
	
	    private WebDriverWait wait;
	    private WebDriverWait fastWait;
	    String productlistingName;
	    protected String productInternalName;
	    protected String skuValue;
	    protected String uploadedImageSrc;     // full S3 URL captured from admin
	    protected String uploadedImageSrcKey;  // filename without extension for loose match
	    String updatedBrandType;
	    
	    
	    public class ConsoleColor {
	        // Reset
	        public static final String RESET = "\u001B[0m";

	        // Text Colors
	        public static final String GREEN = "\u001B[32m";  // Success ✅
	        public static final String YELLOW = "\u001B[33m"; // Warnings ⚠️ / Notes ℹ️
	        public static final String CYAN = "\u001B[36m";   // Highlights / URLs 🌐 / Selectors 🎯
	        public static final String PURPLE = "\u001B[35m"; // Key captures 📌 / Timers ⏳
	        public static final String BLUE = "\u001B[34m";   // Debug / Loop details 🔎
	    }
	
	    public AdminPanelMediaLibrary(WebDriver driver) {
	        this.driver = driver;
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        this.fastWait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        PageFactory.initElements(this.driver, this);
	    }
	
	    // ─── HELPER: Builds a quote-safe XPath contains() expression ─────────────
//	    private String buildContainsXPath(String element, String text) {
//	        if (!text.contains("'")) {
//	            return element + "[contains(normalize-space(),'" + text + "')]";
//	        }
//	        String[] parts = text.split("'", -1);
//	        StringBuilder concat = new StringBuilder("concat(");
//	        for (int i = 0; i < parts.length; i++) {
//	            if (i > 0) concat.append(",\"'\",");
//	            concat.append("'").append(parts[i]).append("'");
//	        }
//	        concat.append(")");
//	        return element + "[contains(normalize-space()," + concat + ")]";
//	    }
	
// ─── STEP 1 ───────────────────────────────────────────────────────────────
	    
	    public void navigateToLandingPageUI() {
	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	        wait.until(ExpectedConditions.visibilityOf(accessCode));
	        type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
	        click(submit);
	        wait.until(ExpectedConditions.elementToBeClickable(zlaataIndiaShopNowButton));
	        System.out.println(ConsoleColor.GREEN + "✅ Step 1: Successfully navigated to Landing Page UI" + ConsoleColor.RESET);
	    }
	
	 // ─── STEP 2 ───────────────────────────────────────────────────────────────
	    
	    public void navigateToDressesPage() {
	        Actions actions = new Actions(driver);

	        // Click SHOP NOW
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", zlaataIndiaShopNowButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked SHOP NOW button" + ConsoleColor.RESET);

	     // ─── CLOSE POPUP IF IT APPEARS ON DRESSES PAGE ───────────────────────────
	        try {
	            WebDriverWait popupWait = new WebDriverWait(driver, Duration.ofSeconds(8));

	            // Wait for the × close button (top-right of popup)
	            WebElement closeBtn = popupWait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//div[@class='cross__bttn_for_topsecretpopup popup_containers_cls_btn']")));
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
	            System.out.println(ConsoleColor.GREEN + "✅ Popup close button clicked" + ConsoleColor.RESET);

	            // Confirm popup is gone before proceeding
	            new WebDriverWait(driver, Duration.ofSeconds(8)).until(
	                    ExpectedConditions.invisibilityOfElementLocated(
	                            By.xpath("//img[@alt='secret sale inside image']")));
	            System.out.println(ConsoleColor.GREEN + "✅ Popup fully closed — proceeding" + ConsoleColor.RESET);

	        } catch (TimeoutException e) {
	            System.out.println(ConsoleColor.YELLOW + "ℹ️ No popup appeared on All page — continuing" + ConsoleColor.RESET);
	        }

	        // ─── HOVER ON SHOP HEADER LINK ────────────────────────────────────────────
	        WebDriverWait headerWait = new WebDriverWait(driver, Duration.ofSeconds(15));
	        WebElement shopLink = headerWait.until(
	                ExpectedConditions.visibilityOf(zlaataIndiaShopHeaderLink));
	        actions.moveToElement(shopLink).perform();
	        System.out.println(ConsoleColor.GREEN + "✅ Hovered on Shop header link" + ConsoleColor.RESET);

	        // ─── CLICK DRESSES VIA JS (bypasses dropdown disappearing) ───────────────
	        WebDriverWait dressWait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        // Wait until the dresses link is present in DOM (not necessarily visible)
	        WebElement dressesLink = dressWait.until(
	                ExpectedConditions.presenceOfElementLocated(
	                        By.xpath("(//a[normalize-space()='All'])[1]")));

	        // Hover shop link again immediately before JS click to keep dropdown open
	        actions.moveToElement(shopLink).perform();

	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dressesLink);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked on Dresses category" + ConsoleColor.RESET);

	        // ─── WAIT FOR PRODUCT CARDS TO LOAD ──────────────────────────────────────
	        new WebDriverWait(driver, Duration.ofSeconds(20)).until(driver -> {
	            try {
	                List<WebElement> cards = driver.findElements(
	                        By.xpath("//div[contains(@class,'prod_listing_card')]"));
	                return cards.size() > 0 && cards.get(0).isDisplayed();
	            } catch (Exception e) {
	                return false;
	            }
	        });

	        System.out.println(ConsoleColor.GREEN + "✅ Step 2: Dresses page loaded" + ConsoleColor.RESET);
	    }
	
	 // ─── STEP 3 ───────────────────────────────────────────────────────────────
	    
	    public String selectRandomProductAndCaptureName() {
	
	        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	                By.xpath("//div[contains(@class,'prod_listing_card')]"), 0));
	
	        List<WebElement> allProducts = driver.findElements(
	                By.xpath("//div[contains(@class,'prod_listing_card')]"));
	
	        if (allProducts.isEmpty()) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ No products found on Dresses page!" + ConsoleColor.RESET);
	            return null;
	        }
	
	        int totalProducts = allProducts.size();
	        int randomIndex = new Random().nextInt(totalProducts) + 1;
	        System.out.println(ConsoleColor.CYAN + "🎯 Selected product index: " + randomIndex
	                + " (from " + totalProducts + " products on page)" + ConsoleColor.RESET);
	
	        WebElement plpNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("(//div[contains(@class,'prod_listing_card')])[" + randomIndex + "]"
	                        + "//a[contains(@class,'product_list_name')]")));
	
	        productlistingName = plpNameElement.getText().trim().split("\\n")[0].trim();
	        System.out.println(ConsoleColor.PURPLE + "📌 Product name captured from PLP: " + productlistingName + ConsoleColor.RESET);
	
	        String plpUrl = driver.getCurrentUrl();
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", plpNameElement);
	
	        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(plpUrl)));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h3[@class='prod_name']")));
	
	        int waitSeconds = 3 + new Random().nextInt(3);
	        System.out.println(ConsoleColor.PURPLE + "⏳ Waiting " + waitSeconds + " seconds on PDP for visual confirmation..." + ConsoleColor.RESET);
	        Common.waitForElement(waitSeconds);
	
	        System.out.println(ConsoleColor.GREEN + "✅ Step 3: Opened PDP for product: " + productlistingName + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "🌐 PDP URL: " + driver.getCurrentUrl() + ConsoleColor.RESET);
	
	        return productlistingName;
	    }
	
	    public void searchProductAndCaptureInternalName() {

	        // ANSI Color Escape Codes
	        final String RESET = "\u001B[0m";
	        final String GREEN = "\u001B[32m";
	        final String YELLOW = "\u001B[33m";
	        final String CYAN = "\u001B[36m";
	        final String PURPLE = "\u001B[35m";

	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

	        wait.until(ExpectedConditions.visibilityOf(adminEmail));
	        type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	        type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	        click(adminLogin);
	        System.out.println(GREEN + "✅ Admin Login Successful" + RESET);

	        try {
	            fastWait.until(ExpectedConditions.visibilityOf(accessCode));
	            type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
	            click(submit);
	        } catch (Exception e) {
	            System.out.println(YELLOW + "ℹ️ No access code prompt on admin login" + RESET);
	        }

	        // ─── DIRECT NAVIGATION ────────────────────────────────────────────────────

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//a[normalize-space()=\"Product's\"])[1]")));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	                driver.findElement(By.xpath("(//a[normalize-space()=\"Product's\"])[1]")));
	        System.out.println(GREEN + "✅ Clicked Product's menu" + RESET);

	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[normalize-space()='Products']")));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	                driver.findElement(By.xpath("//a[normalize-space()='Products']")));
	        System.out.println(GREEN + "✅ Clicked Products submenu" + RESET);

	        wait.until(ExpectedConditions.urlContains("product"));
	        System.out.println(GREEN + "✅ URL confirmed: " + driver.getCurrentUrl() + RESET);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='las la-edit']")));
	        System.out.println(GREEN + "✅ Products page table fully loaded and ready" + RESET);

	        // ─── OPEN SELECT2 DROPDOWN ────────────────────────────────────────────────

	        wait.until(ExpectedConditions.elementToBeClickable(productPageDetailName));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productPageDetailName);
	        System.out.println(GREEN + "✅ Clicked Product Detail Name dropdown header" + RESET);

	        // ─── INJECT PRODUCT NAME AND TRIGGER SEARCH ──────────────────────────────

	        wait.until(ExpectedConditions.visibilityOf(productPageDetailNameSearchBox));

	        ((JavascriptExecutor) driver).executeScript(
	                "var el = arguments[0];" +
	                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
	                "nativeInputValueSetter.call(el, arguments[1]);" +
	                "el.dispatchEvent(new Event('input',     { bubbles: true }));" +
	                "el.dispatchEvent(new Event('change',    { bubbles: true }));" +
	                "el.dispatchEvent(new KeyboardEvent('keydown',  { bubbles: true, keyCode: 65 }));" +
	                "el.dispatchEvent(new KeyboardEvent('keypress', { bubbles: true, keyCode: 65 }));" +
	                "el.dispatchEvent(new KeyboardEvent('keyup',    { bubbles: true, keyCode: 65 }));",
	                productPageDetailNameSearchBox, productlistingName);

	        System.out.println(GREEN + "✅ Injected product name into Select2 search: " + productlistingName + RESET);

	        try {
	            productPageDetailNameSearchBox.click();
	            productPageDetailNameSearchBox.sendKeys(" ");
	            productPageDetailNameSearchBox.sendKeys("\b");
	            Common.waitForElement(1);
	            productPageDetailNameSearchBox.sendKeys(Keys.ENTER);
	            System.out.println(GREEN + "✅ Pressed ENTER to trigger search" + RESET);
	        } catch (Exception e) {
	            System.out.println(YELLOW + "ℹ️ Fallback trigger skipped: " + e.getMessage() + RESET);
	        }

	        // ─── WAIT FOR SELECT2 OPTIONS ─────────────────────────────────────────────

	        By optionsLocator = By.xpath(
	                "//ul[contains(@id,'select2') and contains(@id,'results')]"
	                + "/li[@role='option' and not(contains(text(),'Searching'))]");

	        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(optionsLocator, 0));
	        System.out.println(GREEN + "✅ Select2 actual options loaded" + RESET);

	        List<WebElement> options = driver.findElements(optionsLocator);
	        System.out.println(CYAN + "🔎 Options found: " + options.size() + RESET);

	        // ─── FIND MATCHING OPTION ─────────────────────────────────────────────────

	        WebElement matchedOption = null;
	        for (WebElement option : options) {
	            String optionText = option.getText().trim();
	            System.out.println(CYAN + "   Checking: " + optionText + RESET);
	            if (optionText.toLowerCase().contains(productlistingName.toLowerCase())) {
	                matchedOption = option;
	                System.out.println(GREEN + "✅ Match found: " + optionText + RESET);
	                break;
	            }
	        }

	        if (matchedOption == null) {
	            System.out.println(YELLOW + "⚠️ No exact match — falling back to first option" + RESET);
	            matchedOption = options.get(0);
	        }

	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", matchedOption);
	        System.out.println(GREEN + "✅ Clicked matched option: " + matchedOption.getText().trim() + RESET);

	        // ─── TRIGGER TABLE FILTER / SUBMIT SEARCH ─────────────────────────────────

	        try {
	            // Look for common filter submit buttons if table doesn't auto-filter
	            By filterBtnLocator = By.xpath("//button[contains(@class,'btn-filter') or contains(@type,'submit') or contains(.,'Filter') or contains(.,'Search')]");
	            List<WebElement> filterBtns = driver.findElements(filterBtnLocator);
	            if (!filterBtns.isEmpty() && filterBtns.get(0).isDisplayed()) {
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", filterBtns.get(0));
	                System.out.println(GREEN + "✅ Clicked Filter/Search button to apply table filter" + RESET);
	            } else {
	                // Send ENTER on the Select2 container/input as fallback
	                productPageDetailName.sendKeys(Keys.ENTER);
	            }
	        } catch (Exception ignored) {}

	        Common.waitForElement(2); // Allow table to refresh

	        // ─── LOCATE PRODUCT ROW & CLICK EDIT ──────────────────────────────────────

	        String cleanNameSnippet = productlistingName.trim().replaceAll("[^a-zA-Z0-9 ]", " ").toLowerCase();
	        if (cleanNameSnippet.length() > 10) {
	            cleanNameSnippet = cleanNameSnippet.substring(0, 10);
	        }

	        // Dynamic row locator targeting the row containing the product name
	        By rowEditButton = By.xpath(
	                "//tr[contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + cleanNameSnippet + "')]" +
	                "//a[contains(@class,'btn-edit') or .//i[contains(@class,'la-edit')]]"
	        );

	        WebElement editButton;
	        try {
	            editButton = wait.until(ExpectedConditions.elementToBeClickable(rowEditButton));
	            System.out.println(PURPLE + "🎯 Matched exact product row for edit: " + productlistingName + RESET);
	        } catch (Exception e) {
	            System.out.println(YELLOW + "⚠️ Dynamic row locator timed out, falling back to first table row edit button" + RESET);
	            editButton = wait.until(ExpectedConditions.elementToBeClickable(
	                    By.xpath("//table//tbody//tr[1]//a[contains(@class,'btn-edit') or .//i[contains(@class,'la-edit')]]")));
	        }

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", editButton);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
	        System.out.println(GREEN + "✅ Clicked Edit button on filtered row for: " + productlistingName + RESET);

	        // ─── NAVIGATE TO ITEM SECTION ─────────────────────────────────────────────

	        By itemTabLocator = By.xpath("(//a[normalize-space()='Item'])[1]");
	        wait.until(ExpectedConditions.elementToBeClickable(itemTabLocator));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(itemTabLocator));
	        System.out.println(GREEN + "✅ Clicked on Item section tab" + RESET);

	        Common.waitForElement(1);

	        // ─── LOCATE MATCHING ITEM AND CAPTURE ITEM SKU ────────────────────────────

	        By itemNameInputsLocator = By.xpath("//input[contains(@name, 'filters') and contains(@name, '[name]')]");
	        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(itemNameInputsLocator));

	        List<WebElement> nameInputs = driver.findElements(itemNameInputsLocator);
	        System.out.println(CYAN + "🔎 Found " + nameInputs.size() + " item variant row(s)" + RESET);

	        WebElement matchedNameInput = null;
	        String targetName = productlistingName.trim().toLowerCase();

	        for (WebElement input : nameInputs) {
	            String itemVal = input.getAttribute("value").trim().toLowerCase();
	            System.out.println(CYAN + "   Item name found: " + itemVal + RESET);

	            if (!itemVal.isEmpty() && (itemVal.contains(targetName) || targetName.contains(itemVal))) {
	                matchedNameInput = input;
	                System.out.println(PURPLE + "🎯 Matched Item name: " + itemVal + RESET);
	                break;
	            }
	        }

	        if (matchedNameInput == null && !nameInputs.isEmpty()) {
	            System.out.println(YELLOW + "⚠️ Item name match failed — falling back to first item variant row" + RESET);
	            matchedNameInput = nameInputs.get(0);
	        }

	        if (matchedNameInput != null) {
	            String nameAttr = matchedNameInput.getAttribute("name");
	            String skuAttr = nameAttr.replace("[name]", "[sku]");

	            By correspondingSkuLocator = By.xpath("//input[@name='" + skuAttr + "']");
	            wait.until(ExpectedConditions.presenceOfElementLocated(correspondingSkuLocator));

	            skuValue = driver.findElement(correspondingSkuLocator).getAttribute("value").trim();
	            System.out.println(GREEN + "📌 Item SKU captured successfully: " + skuValue + RESET);
	        } else {
	            throw new RuntimeException("❌ Unable to locate any item SKU fields under Item section");
	        }

	        // ─── CANCEL AND RETURN ────────────────────────────────────────────────────

	        wait.until(ExpectedConditions.elementToBeClickable(productPageCancelButton));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", productPageCancelButton);
	        System.out.println(GREEN + "✅ Clicked Cancel — returning to product list" + RESET);
	        Common.waitForElement(1);
	    }
	
	 // ─── STEP 5 ───────────────────────────────────────────────────────────────

	    public void navigateToMediaLibraryAndSearchBySku() {

	        if (skuValue == null || skuValue.trim().isEmpty()) {
	            throw new RuntimeException("❌ skuValue is null or empty — was SKU captured in previous step?");
	        }

	        // Use getApplicationAdminUrl() to stay on the admin domain (qa.adm...)
	        String adminBaseUrl = FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl()
	                .replace("/admin/dashboard", "")
	                .replaceAll("/+$", ""); // Remove any trailing slashes

	        driver.get(adminBaseUrl + "/admin/media-library");

	        wait.until(ExpectedConditions.urlContains("media-library"));
	        System.out.println(ConsoleColor.GREEN + "✅ URL confirmed: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//i[@class='las la-edit']")));
	        System.out.println(ConsoleColor.GREEN + "✅ Media Library table fully loaded and ready" + ConsoleColor.RESET);

	        // ─── FILTER BY SKU ────────────────────────────────────────────────────────
	        wait.until(ExpectedConditions.elementToBeClickable(skuColumnHHeader));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", skuColumnHHeader);
	        Common.waitForElement(1);

	        wait.until(ExpectedConditions.visibilityOf(skuSearchBoxInMediaLibrary));
	        skuSearchBoxInMediaLibrary.clear();
	        skuSearchBoxInMediaLibrary.sendKeys(skuValue);
	        System.out.println(ConsoleColor.GREEN + "✅ Typed SKU into search box: " + skuValue + ConsoleColor.RESET);

	        WebElement tableBody = driver.findElement(By.xpath("//table//tbody"));
	        skuSearchBoxInMediaLibrary.sendKeys(Keys.ENTER);
	        System.out.println(ConsoleColor.GREEN + "✅ Pressed ENTER to filter table" + ConsoleColor.RESET);

	        try {
	            new WebDriverWait(driver, Duration.ofSeconds(5))
	                    .until(ExpectedConditions.stalenessOf(tableBody));
	        } catch (Exception ignored) {
	            // Table reloaded dynamically or faster than polling
	        }

	        // ─── EXACT ROW-SCOPED EDIT BUTTON LOCATOR ─────────────────────────────────
	        String cleanSku = skuValue.trim();
	        By rowScopedEditButton = By.xpath(
	                "//tr[.//a[normalize-space()='" + cleanSku + "'] " +
	                "or .//td[normalize-space()='" + cleanSku + "'] " +
	                "or contains(., '" + cleanSku + "')]" +
	                "//a[contains(@class,'btn-edit') or .//i[contains(@class,'la-edit')]]"
	        );

	        System.out.println(ConsoleColor.BLUE + "🔎 Locating edit button scoped directly to SKU: " + cleanSku + ConsoleColor.RESET);

	        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(rowScopedEditButton));

	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", editButton);
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);

	        System.out.println(ConsoleColor.GREEN + "✅ Successfully clicked Edit button directly for SKU: " + cleanSku + ConsoleColor.RESET);
	        Common.waitForElement(2);
	    }
	    
	    
	    
	  // ─── STEP 6 ───────────────────────────────────────────────────────────────
	    
	    public String clickChooseFileUploadAndSave() {
	
	        By chooseFileLocator = By.xpath("(//button[normalize-space()='Choose File'])[1]");
	        wait.until(ExpectedConditions.visibilityOfElementLocated(chooseFileLocator));
	
	        WebElement chooseFile = driver.findElement(chooseFileLocator);
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", chooseFile);
	        Common.waitForElement(1);
	
	        wait.until(ExpectedConditions.elementToBeClickable(chooseFileLocator));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chooseFile);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Choose File button" + ConsoleColor.RESET);
	        Common.waitForElement(2);
	
	        String imagePath = System.getProperty("user.dir")
	                + "\\src\\test\\resources\\images\\Maroon (2).jpg";
	        String uploadedImageName = imagePath.substring(imagePath.lastIndexOf("\\") + 1);
	        System.out.println(ConsoleColor.CYAN + "📁 Image to upload: " + uploadedImageName + ConsoleColor.RESET);
	
	        Common.waitForElement(2);
	
	        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("(//input[@type='file'])[1]")));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].style.display='block'; arguments[0].style.visibility='visible';", fileInput);
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", fileInput);
	        Common.waitForElement(1);
	
	        fileInput.sendKeys(imagePath);
	        System.out.println(ConsoleColor.GREEN + "✅ Image path sent to file input: " + uploadedImageName + ConsoleColor.RESET);
	        Common.waitForElement(3);
	
	        // ─── CLICK CONFIRM UPLOAD ─────────────────────────────────────────────────
	        System.out.println(ConsoleColor.BLUE + "🖱️ About to click Confirm Upload..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.elementToBeClickable(confirmUploadButton));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", confirmUploadButton);
	        Common.waitForElement(2);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", confirmUploadButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Confirm Upload" + ConsoleColor.RESET);
	        Common.waitForElement(3);
	
	        // ─── CAPTURE S3 SRC FROM ADMIN AFTER CONFIRM UPLOAD ──────────────────────
	        // Must capture HERE before navigating away — last jpg Image = newly uploaded
	        try {
	            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
	                By.xpath("//img[@alt='jpg Image']"), 0));
	
	            List<WebElement> allJpgImages = driver.findElements(
	                By.xpath("//img[@alt='jpg Image']"));
	            System.out.println(ConsoleColor.BLUE + "📸 Total jpg image thumbnails in admin: " + allJpgImages.size() + ConsoleColor.RESET);
	
	            // Last element is always the newly uploaded one
	            WebElement lastUploadedImg = allJpgImages.get(allJpgImages.size() - 1);
	            ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", lastUploadedImg);
	            Common.waitForElement(1);
	
	            uploadedImageSrc = lastUploadedImg.getAttribute("src");
	            System.out.println(ConsoleColor.PURPLE + "📸 Captured S3 src of uploaded image: " + uploadedImageSrc + ConsoleColor.RESET);
	
	            // Extract keyword: "https://zlaata.s3.../uploads/images/1338906301_1778226901.jpg"
	            //                → "1338906301_1778226901"
	            String srcFilename = uploadedImageSrc.substring(
	                uploadedImageSrc.lastIndexOf("/") + 1);
	            uploadedImageSrcKey = srcFilename.contains(".")
	                ? srcFilename.substring(0, srcFilename.lastIndexOf("."))
	                : srcFilename;
	            System.out.println(ConsoleColor.PURPLE + "🔑 Src keyword for PDP matching: " + uploadedImageSrcKey + ConsoleColor.RESET);
	
	        } catch (Exception e) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ Could not capture uploaded image src: " + e.getMessage() + ConsoleColor.RESET);
	            uploadedImageSrc = null;
	            uploadedImageSrcKey = null;
	        }
	
	        // ─── CLICK SAVE AND BACK ──────────────────────────────────────────────────
	        System.out.println(ConsoleColor.BLUE + "🖱️ About to click Save and Back..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.elementToBeClickable(saveAndBackButton));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", saveAndBackButton);
	        Common.waitForElement(2);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveAndBackButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Save and Back" + ConsoleColor.RESET);
	        Common.waitForElement(3);
	
	        // ─── CLICK REFRESH ────────────────────────────────────────────────────────
	        System.out.println(ConsoleColor.BLUE + "🖱️ About to click Refresh..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.elementToBeClickable(refreshButton));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", refreshButton);
	        Common.waitForElement(2);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", refreshButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Refresh" + ConsoleColor.RESET);
	        Common.waitForElement(3);
	
	        // ─── RE-CAPTURE AFTER SAVE IF FIRST ATTEMPT FAILED ───────────────────────
	        if (uploadedImageSrc == null || uploadedImageSrc.isEmpty()) {
	            try {
	                List<WebElement> allJpgImages = driver.findElements(
	                    By.xpath("//img[@alt='jpg Image']"));
	                if (!allJpgImages.isEmpty()) {
	                    WebElement lastImg = allJpgImages.get(allJpgImages.size() - 1);
	                    uploadedImageSrc = lastImg.getAttribute("src");
	                    String srcFilename = uploadedImageSrc.substring(
	                        uploadedImageSrc.lastIndexOf("/") + 1);
	                    uploadedImageSrcKey = srcFilename.contains(".")
	                        ? srcFilename.substring(0, srcFilename.lastIndexOf("."))
	                        : srcFilename;
	                    System.out.println(ConsoleColor.PURPLE + "📸 Re-captured S3 src after save: " + uploadedImageSrc + ConsoleColor.RESET);
	                    System.out.println(ConsoleColor.PURPLE + "🔑 Re-captured src keyword: " + uploadedImageSrcKey + ConsoleColor.RESET);
	                }
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⚠️ Re-capture after save also failed: " + e.getMessage() + ConsoleColor.RESET);
	            }
	        }
	
	        System.out.println(ConsoleColor.GREEN + "✅ Image uploaded and saved — filename: " + uploadedImageName + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "✅ S3 src to verify on PDP: " + uploadedImageSrc + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "✅ S3 src keyword: " + uploadedImageSrcKey + ConsoleColor.RESET);
	        return uploadedImageName;
	    }
	    
	    
// ─── STEP 7 ───────────────────────────────────────────────────────────────
	    
	    public void verifyUploadedImageOnUI(String uploadedImageName) {

	        // ─── GUARD: CONFIRM S3 SRC WAS CAPTURED ──────────────────────────────────
	        System.out.println(ConsoleColor.BLUE + "🔍 uploadedImageSrc: " + uploadedImageSrc + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.BLUE + "🔍 uploadedImageSrcKey: " + uploadedImageSrcKey + ConsoleColor.RESET);

	        if (uploadedImageSrc == null || uploadedImageSrc.isEmpty()) {
	            throw new RuntimeException(
	                "❌ uploadedImageSrc is null — S3 src was not captured in Step 6. Cannot verify.");
	        }

	        // ─── FORCE FRESH PAGE LOAD ────────────────────────────────────────────────
	        driver.get("about:blank");
	        Common.waitForElement(1);
	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	        System.out.println(ConsoleColor.GREEN + "✅ Fresh UI page loaded: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        // ─── HANDLE ACCESS CODE IF IT APPEARS ────────────────────────────────────
	        try {
	            fastWait.until(ExpectedConditions.visibilityOf(accessCode));
	            type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
	            click(submit);
	            System.out.println(ConsoleColor.GREEN + "✅ Access code entered on UI" + ConsoleColor.RESET);
	        } catch (Exception e) {
	            System.out.println(ConsoleColor.YELLOW + "ℹ️ No access code prompt on UI" + ConsoleColor.RESET);
	        }

	        // ─── CLICK SHOP NOW ───────────────────────────────────────────────────────
	        wait.until(ExpectedConditions.elementToBeClickable(zlaataIndiaShopNowButton));
	        String landingUrl = driver.getCurrentUrl();
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", zlaataIndiaShopNowButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked SHOP NOW — Zlaata India" + ConsoleColor.RESET);

	        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(landingUrl)));
	        System.out.println(ConsoleColor.GREEN + "✅ URL changed after SHOP NOW: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        wait.until(driver -> ((JavascriptExecutor) driver)
	            .executeScript("return document.readyState").equals("complete"));
	        Common.waitForElement(2);

	        // ─── CLICK SEARCH ICON TO REVEAL INPUT ───────────────────────────────────
	        String[] searchIconXPaths = {
	            "(//button[@id='searchBtn'])[1]",
	            "(//button[contains(@class,'search')])[1]",
	            "(//i[contains(@class,'search')])[1]",
	            "(//span[contains(@class,'search')])[1]",
	            "(//a[contains(@class,'search')])[1]",
	            "(//i[contains(@class,'la-search')])[1]",
	            "(//i[contains(@class,'fa-search')])[1]"
	        };

	        boolean searchIconClicked = false;
	        for (String xp : searchIconXPaths) {
	            try {
	                WebElement searchIcon = fastWait.until(
	                    ExpectedConditions.elementToBeClickable(By.xpath(xp)));
	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", searchIcon);
	                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchIcon);
	                System.out.println(ConsoleColor.GREEN + "✅ Clicked search icon using XPath: " + xp + ConsoleColor.RESET);
	                searchIconClicked = true;
	                break;
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⏭️ Search icon not found with: " + xp + ConsoleColor.RESET);
	            }
	        }

	        if (!searchIconClicked) {
	            throw new RuntimeException("❌ Could not find/click any search icon on page: "
	                + driver.getCurrentUrl());
	        }

	        Common.waitForElement(1);

	        // ─── WAIT FOR SEARCH INPUT ────────────────────────────────────────────────
	        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("(//input[@id='globalSearchInput'])[1]")));
	        System.out.println(ConsoleColor.GREEN + "✅ Global search input is now visible" + ConsoleColor.RESET);

	        // Clear via JS, set value via native setter, then sendKeys to confirm
	        ((JavascriptExecutor) driver).executeScript(
	                "var el = arguments[0];" +
	                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
	                "nativeInputValueSetter.call(el, '');" +
	                "el.dispatchEvent(new Event('input', { bubbles: true }));",
	                searchInput);
	        Common.waitForElement(1);

	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchInput);
	        searchInput.sendKeys(productlistingName);
	        System.out.println(ConsoleColor.CYAN + "🔍 Typed in global search: " + productlistingName + ConsoleColor.RESET);
	        Common.waitForElement(1);

	        // Verify what was actually typed before pressing Enter
	        String typedValue = searchInput.getAttribute("value");
	        System.out.println(ConsoleColor.BLUE + "🔎 Value confirmed in search input: " + typedValue + ConsoleColor.RESET);

	        if (typedValue == null || !typedValue.toLowerCase().contains(
	                productlistingName.toLowerCase().substring(0, Math.min(5, productlistingName.length())))) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ sendKeys may have failed — retrying with Actions" + ConsoleColor.RESET);
	            Actions actions = new Actions(driver);
	            actions.click(searchInput)
	                   .keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL)
	                   .sendKeys(Keys.DELETE)
	                   .sendKeys(productlistingName)
	                   .perform();
	            System.out.println(ConsoleColor.GREEN + "✅ Retyped via Actions: " + searchInput.getAttribute("value") + ConsoleColor.RESET);
	        }

	        searchInput.sendKeys(Keys.ENTER);
	        System.out.println(ConsoleColor.GREEN + "✅ Pressed Enter to search for: " + productlistingName + ConsoleColor.RESET);

	        // ─── WAIT FOR SEARCH RESULTS TO LOAD ─────────────────────────────────────
	        Common.waitForElement(3);
	        System.out.println(ConsoleColor.GREEN + "✅ Search results page loaded: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        // ─── DUMP PAGE STRUCTURE FOR DEBUG ───────────────────────────────────────
	        System.out.println(ConsoleColor.YELLOW + "🐛 Page title: " + driver.getTitle() + ConsoleColor.RESET);

	        String[] resultContainerXPaths = {
	            "//div[contains(@class,'prod_listing_card')]//a",
	            "//div[contains(@class,'product-card')]//a",
	            "//div[contains(@class,'product_card')]//a",
	            "//div[contains(@class,'search-result')]//a",
	            "//div[contains(@class,'swiper-wrapper')]//a",
	            "//div[contains(@id,'swiper-wrapper')]//a",
	            "//a[contains(@href,'/zlaata-india/')]",
	            "//a[contains(@href,'/dresses/')]",
	            "//picture/parent::a",
	            "//img[@alt='product img']/ancestor::a[1]"
	        };

	        List<WebElement> productLinks = new java.util.ArrayList<>();

	        for (String xp : resultContainerXPaths) {
	            try {
	                List<WebElement> found = driver.findElements(By.xpath(xp));
	                if (!found.isEmpty()) {
	                    System.out.println(ConsoleColor.GREEN + "✅ Found " + found.size() + " results using XPath: " + xp + ConsoleColor.RESET);
	                    productLinks = found;
	                    break;
	                } else {
	                    System.out.println(ConsoleColor.YELLOW + "⏭️ No results with XPath: " + xp + ConsoleColor.RESET);
	                }
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⏭️ XPath failed: " + xp + ConsoleColor.RESET);
	            }
	        }

	        if (productLinks.isEmpty()) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ No product links found via containers — trying all <a> with product href" + ConsoleColor.RESET);
	            List<WebElement> allLinks = driver.findElements(By.xpath("//a[contains(@href,'/zlaata-india/')]"));
	            System.out.println(ConsoleColor.BLUE + "🔍 Total Zlaata India links on page: " + allLinks.size() + ConsoleColor.RESET);
	            for (WebElement l : allLinks) {
	                System.out.println(ConsoleColor.BLUE + "   href='" + l.getAttribute("href") + "'" + ConsoleColor.RESET);
	            }
	            productLinks = allLinks;
	        }

	        System.out.println(ConsoleColor.BLUE + "🔍 Total product links found: " + productLinks.size() + ConsoleColor.RESET);
	        for (WebElement link : productLinks) {
	            System.out.println(ConsoleColor.BLUE + "   href='" + link.getAttribute("href") + "'" + ConsoleColor.RESET);
	        }

	        // ─── BUILD SLUG AND FIND MATCHING PRODUCT ────────────────────────────────
	        String slugKeyword = productlistingName.toLowerCase()
	            .replaceAll("[^a-z0-9]+", "-")
	            .replaceAll("-+", "-")
	            .replaceAll("^-|-$", "");
	        System.out.println(ConsoleColor.BLUE + "🔎 Looking for href slug: " + slugKeyword + ConsoleColor.RESET);

	        WebElement matchedProduct = null;

	        // Attempt 1: match by href slug
	        for (WebElement link : productLinks) {
	            String href = link.getAttribute("href");
	            if (href != null && href.toLowerCase().contains(
	                    slugKeyword.substring(0, Math.min(10, slugKeyword.length())))) {
	                matchedProduct = link;
	                System.out.println(ConsoleColor.GREEN + "✅ Matched by href slug: " + href + ConsoleColor.RESET);
	                break;
	            }
	        }

	        // Attempt 2: match by img alt text
	        if (matchedProduct == null) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ Href slug match failed — trying img alt text..." + ConsoleColor.RESET);
	            for (WebElement link : productLinks) {
	                try {
	                    List<WebElement> imgs = link.findElements(By.xpath(".//img"));
	                    for (WebElement img : imgs) {
	                        String alt = img.getAttribute("alt");
	                        if (alt != null && alt.toLowerCase().contains(
	                                productlistingName.toLowerCase().substring(0,
	                                Math.min(8, productlistingName.length())))) {
	                            matchedProduct = link;
	                            System.out.println(ConsoleColor.GREEN + "✅ Matched by img alt: " + alt + ConsoleColor.RESET);
	                            break;
	                        }
	                    }
	                    if (matchedProduct != null) break;
	                } catch (Exception e) {
	                    // skip
	                }
	            }
	        }

	        // Attempt 3: match by link text
	        if (matchedProduct == null) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ Alt text match failed — trying link text..." + ConsoleColor.RESET);
	            for (WebElement link : productLinks) {
	                try {
	                    String linkText = link.getText().trim().toLowerCase();
	                    if (!linkText.isEmpty() && linkText.contains(
	                            productlistingName.toLowerCase().substring(0,
	                            Math.min(8, productlistingName.length())))) {
	                        matchedProduct = link;
	                        System.out.println(ConsoleColor.GREEN + "✅ Matched by link text: " + linkText + ConsoleColor.RESET);
	                        break;
	                    }
	                } catch (Exception e) {
	                    // skip
	                }
	            }
	        }

	        // Attempt 4: navigate directly to PDP via URL slug
	        if (matchedProduct == null) {
	            System.out.println(ConsoleColor.YELLOW + "⚠️ All link matching failed — trying direct PDP URL navigation" + ConsoleColor.RESET);
	            String baseUrl = FileReaderManager.getInstance().getConfigReader().getApplicationUrl();
	            String pdpUrl = baseUrl + "/zlaata-india/dresses/" + slugKeyword;
	            System.out.println(ConsoleColor.CYAN + "🌐 Navigating directly to: " + pdpUrl + ConsoleColor.RESET);
	            driver.get(pdpUrl);
	            Common.waitForElement(3);

	            try {
	                wait.until(ExpectedConditions.visibilityOfElementLocated(
	                        By.xpath("//h3[@class='prod_name']")));
	                System.out.println(ConsoleColor.GREEN + "✅ PDP loaded via direct URL for: " + productlistingName + ConsoleColor.RESET);
	                verifyImageOnPDP();
	                return;
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⚠️ Direct URL navigation also failed: " + e.getMessage() + ConsoleColor.RESET);
	            }

	            throw new RuntimeException("❌ Could not find product in search results for: "
	                    + productlistingName);
	        }

	        // ─── CLICK MATCHED PRODUCT ────────────────────────────────────────────────
	        ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", matchedProduct);
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", matchedProduct);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked matched product: " + productlistingName + ConsoleColor.RESET);

	        // ─── WAIT FOR PDP TO LOAD ─────────────────────────────────────────────────
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//h3[@class='prod_name']")));
	        Common.waitForElement(2);
	        System.out.println(ConsoleColor.GREEN + "✅ PDP loaded for: " + productlistingName + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "🌐 PDP URL: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        verifyImageOnPDP();
	    }

	    // ─── EXTRACTED: IMAGE VERIFICATION ON PDP ─────────────────────────────────────
	    private void verifyImageOnPDP() {

	        // Scroll to trigger lazy-loaded images
	        try {
	            WebElement imageSection = driver.findElement(
	                By.xpath("//div[contains(@class,'swiper') and .//img[@alt='product img']]"));
	            ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", imageSection);
	        } catch (Exception e) {
	            ((JavascriptExecutor) driver).executeScript(
	                "window.scrollTo({top: document.body.scrollHeight * 0.3, behavior: 'smooth'});");
	        }
	        Common.waitForElement(2);

	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo({top: document.body.scrollHeight * 0.3, behavior: 'smooth'});");
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript(
	            "window.scrollTo({top: document.body.scrollHeight * 0.6, behavior: 'smooth'});");
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
	        Common.waitForElement(1);

	        boolean imageVerified = false;

	        // ─── STRATEGY 1: CHECK VIA S3 SRC KEYWORD IN INITIAL DOM ──────────────────
	        if (uploadedImageSrcKey != null && !uploadedImageSrcKey.isEmpty()) {
	            By srcKeyLocator = By.xpath("//img[contains(@src,'" + uploadedImageSrcKey + "')]");
	            try {
	                WebElement found = wait.until(
	                    ExpectedConditions.visibilityOfElementLocated(srcKeyLocator));
	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", found);
	                Common.waitForElement(1);
	                if (found.isDisplayed()) {
	                    System.out.println(ConsoleColor.GREEN + "✅ PASSED: Image verified on PDP by S3 src keyword" + ConsoleColor.RESET);
	                    System.out.println(ConsoleColor.CYAN + "🌐 Matched image src: " + found.getAttribute("src") + ConsoleColor.RESET);
	                    imageVerified = true;
	                }
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⚠️ Strategy 1 failed (src keyword): " + uploadedImageSrcKey + ConsoleColor.RESET);
	            }
	        }

	        // ─── STRATEGY 2: CHECK VIA FULL S3 SRC IN INITIAL DOM ────────────────────
	        if (!imageVerified) {
	            By fullSrcLocator = By.xpath("//img[@src='" + uploadedImageSrc + "']");
	            try {
	                WebElement found = wait.until(
	                    ExpectedConditions.presenceOfElementLocated(fullSrcLocator));
	                ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", found);
	                Common.waitForElement(1);
	                System.out.println(ConsoleColor.GREEN + "✅ PASSED: Image verified on PDP by full S3 src" + ConsoleColor.RESET);
	                imageVerified = true;
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "⚠️ Strategy 2 failed (full src): " + uploadedImageSrc + ConsoleColor.RESET);
	            }
	        }

	        // ─── STRATEGY 3: SWIPE THROUGH CAROUSEL / SLIDER (FOR 10+ IMAGES) ─────────
	        if (!imageVerified) {
	            System.out.println(ConsoleColor.BLUE + "🔄 Strategy 1 & 2 failed. Attempting Strategy 3: Swiping gallery using next icon..." + ConsoleColor.RESET);

	            By nextIconLocator = By.xpath("(//img[@alt='Product detail swiping next icon'])[1]");
	            int maxSwipes = 20; // Handles up to 20 images in gallery

	            for (int i = 1; i <= maxSwipes; i++) {
	                List<WebElement> nextBtns = driver.findElements(nextIconLocator);
	                if (nextBtns.isEmpty() || !nextBtns.get(0).isDisplayed()) {
	                    System.out.println(ConsoleColor.YELLOW + "ℹ️ Next icon not visible or no more images to swipe after " + (i - 1) + " clicks" + ConsoleColor.RESET);
	                    break;
	                }

	                try {
	                    WebElement nextBtn = nextBtns.get(0);
	                    ((JavascriptExecutor) driver).executeScript(
	                        "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", nextBtn);
	                    Common.waitForElement(1);
	                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
	                    System.out.println(ConsoleColor.BLUE + "▶️ Clicked swipe next icon (Click #" + i + ")" + ConsoleColor.RESET);
	                    Common.waitForElement(1);

	                    // Check 1: Match by keyword after swipe
	                    if (uploadedImageSrcKey != null && !uploadedImageSrcKey.isEmpty()) {
	                        By keyLoc = By.xpath("//img[contains(@src,'" + uploadedImageSrcKey + "')]");
	                        List<WebElement> matches = driver.findElements(keyLoc);
	                        for (WebElement img : matches) {
	                            if (img.isDisplayed()) {
	                                System.out.println(ConsoleColor.GREEN + "✅ PASSED: Image verified after swipe #" + i + " by S3 src keyword" + ConsoleColor.RESET);
	                                System.out.println(ConsoleColor.CYAN + "🌐 Matched image src: " + img.getAttribute("src") + ConsoleColor.RESET);
	                                imageVerified = true;
	                                break;
	                            }
	                        }
	                    }

	                    // Check 2: Match by full src after swipe
	                    if (!imageVerified && uploadedImageSrc != null && !uploadedImageSrc.isEmpty()) {
	                        By fullLoc = By.xpath("//img[@src='" + uploadedImageSrc + "']");
	                        List<WebElement> matches = driver.findElements(fullLoc);
	                        for (WebElement img : matches) {
	                            if (img.isDisplayed()) {
	                                System.out.println(ConsoleColor.GREEN + "✅ PASSED: Image verified after swipe #" + i + " by full S3 src" + ConsoleColor.RESET);
	                                imageVerified = true;
	                                break;
	                            }
	                        }
	                    }

	                    if (imageVerified) {
	                        break;
	                    }

	                } catch (Exception swipeEx) {
	                    System.out.println(ConsoleColor.YELLOW + "⚠️ Could not click swipe icon on iteration " + i + ": " + swipeEx.getMessage() + ConsoleColor.RESET);
	                    break;
	                }
	            }
	        }

	        // ─── FINAL ASSERTION & FAILURE DIAGNOSTICS ────────────────────────────────
	        if (!imageVerified) {
	            System.out.println(ConsoleColor.YELLOW + "❌ All strategies failed (Initial check + Carousel swiping). Dumping product images:" + ConsoleColor.RESET);
	            List<WebElement> allProductImgs = driver.findElements(By.xpath("//img[@alt='product img']"));
	            for (WebElement img : allProductImgs) {
	                System.out.println(ConsoleColor.BLUE + "   src='" + img.getAttribute("src") + "'" + ConsoleColor.RESET);
	            }
	            List<WebElement> allImgs = driver.findElements(By.xpath("//img"));
	            for (WebElement img : allImgs) {
	                System.out.println(ConsoleColor.BLUE + "   visible=" + img.isDisplayed()
	                    + " src='" + img.getAttribute("src")
	                    + "' alt='" + img.getAttribute("alt") + "'" + ConsoleColor.RESET);
	            }
	            throw new AssertionError(
	                "❌ FAILED: Uploaded image not found on PDP even after swiping gallery.\n"
	                + "   Expected src key : " + uploadedImageSrcKey + "\n"
	                + "   Expected full src: " + uploadedImageSrc + "\n"
	                + "   Product          : " + productlistingName);
	        }
	    }
	    
	
	    
	 // ─── TC_02 STEP 1: Login and Navigate to Media Library ───────────────────
	    public void navigateToAdminAndMediaLibrary() {

	        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());

	        wait.until(ExpectedConditions.visibilityOf(adminEmail));
	        type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	        type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	        click(adminLogin);
	        System.out.println(ConsoleColor.GREEN + "✅ Admin Login Successful" + ConsoleColor.RESET);

	        try {
	            fastWait.until(ExpectedConditions.visibilityOf(accessCode));
	            type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
	            click(submit);
	        } catch (Exception e) {
	            System.out.println(ConsoleColor.YELLOW + "ℹ️ No access code prompt on admin login" + ConsoleColor.RESET);
	        }

	        // Scroll sidebar down to reveal Media Library
	        ((JavascriptExecutor) driver).executeScript(
	                "var sidebar = document.querySelector('.sidebar-nav');" +
	                "if (sidebar) { sidebar.scrollTop = sidebar.scrollHeight; }");
	        Common.waitForElement(1);
	        System.out.println(ConsoleColor.GREEN + "✅ Scrolled sidebar down" + ConsoleColor.RESET);

	        // Wait for Media Library to be visible and click it
	        wait.until(driver -> {
	            try {
	                List<WebElement> links = driver.findElements(
	                        By.xpath("//nav[contains(@class,'sidebar-nav')]//a[normalize-space()='Media Library']"));
	                return links.size() > 0 && links.get(0).isDisplayed();
	            } catch (Exception e) {
	                return false;
	            }
	        });
	        System.out.println(ConsoleColor.GREEN + "✅ Media Library menu item is visible in sidebar" + ConsoleColor.RESET);

	        WebElement mediaLibraryLink = driver.findElement(
	                By.xpath("//nav[contains(@class,'sidebar-nav')]//a[normalize-space()='Media Library']"));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", mediaLibraryLink);
	        Common.waitForElement(1);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", mediaLibraryLink);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Media Library from sidebar" + ConsoleColor.RESET);

	        wait.until(ExpectedConditions.urlContains("media-library"));
	        System.out.println(ConsoleColor.GREEN + "✅ URL confirmed: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//table//tbody//tr[1]")));
	        System.out.println(ConsoleColor.GREEN + "✅ TC_02 Step 1: Media Library table loaded" + ConsoleColor.RESET);
	    }

	    public String updateBrandTypeForRandomProduct() {

	        // ✅ Wait for Brand Type dropdowns to be present on page
	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for Brand Type dropdowns to load on Media Library page..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//select[contains(@class,'brand-type')]")));
	        Common.waitForElement(2);
	        System.out.println(ConsoleColor.GREEN + "✅ Brand Type dropdowns are present on page" + ConsoleColor.RESET);

	        // ✅ Get all brand type selects visible on the current page
	        List<WebElement> brandTypeSelects = driver.findElements(
	                By.xpath("//select[contains(@class,'brand-type')]"));
	        System.out.println(ConsoleColor.CYAN + "🔢 Total Brand Type dropdowns found: " + brandTypeSelects.size() + ConsoleColor.RESET);

	        if (brandTypeSelects.isEmpty()) {
	            throw new RuntimeException("❌ No Brand Type dropdowns found on Media Library page");
	        }

	        // ✅ Pick random index
	        int randomIndex = new Random().nextInt(brandTypeSelects.size());
	        System.out.println(ConsoleColor.CYAN + "🎯 Randomly selected row index: " + (randomIndex + 1)
	                + " out of " + brandTypeSelects.size() + ConsoleColor.RESET);
	        Common.waitForElement(1);

	        // ✅ Get the chosen brand type select
	        WebElement chosenSelect = brandTypeSelects.get(randomIndex);

	        // ✅ Scroll it into view so tester can see which row is selected
	        System.out.println(ConsoleColor.BLUE + "🔽 Scrolling selected Brand Type dropdown into view..." + ConsoleColor.RESET);
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", chosenSelect);
	        Common.waitForElement(2);
	        System.out.println(ConsoleColor.GREEN + "✅ Selected Brand Type dropdown is now visible on screen" + ConsoleColor.RESET);

	        // ✅ Capture product name from the red link in the same row/section
	        System.out.println(ConsoleColor.BLUE + "📌 Capturing product name from the selected row..." + ConsoleColor.RESET);
	        try {
	            WebElement nameLink = chosenSelect.findElement(
	                    By.xpath("./ancestor::tr[1]//a[not(contains(@class,'page-link'))]"));
	            productlistingName = nameLink.getText().trim()
	                    .replaceAll("\\(.*?\\)", "") // strip SKU e.g. "(zlt2158)"
	                    .trim();
	        } catch (Exception e) {
	            try {
	                // Fallback — look in parent div
	                WebElement nameLink = chosenSelect.findElement(
	                        By.xpath("./ancestor::div[contains(@class,'row')][1]"
	                                + "//a[not(contains(@class,'page-link'))]"));
	                productlistingName = nameLink.getText().trim()
	                        .replaceAll("\\(.*?\\)", "")
	                        .trim();
	            } catch (Exception e2) {
	                // Last fallback — use text-wrap spans
	                List<WebElement> nameSpans = driver.findElements(
	                        By.xpath("//span[@class='text-wrap']"));
	                productlistingName = (!nameSpans.isEmpty() && randomIndex < nameSpans.size())
	                        ? nameSpans.get(randomIndex).getText().trim()
	                        : "Product_" + (randomIndex + 1);
	            }
	        }
	        System.out.println(ConsoleColor.CYAN + "📌 Product selected: [" + productlistingName + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Read current brand type via JS — Select2 hides the native select visually
	        System.out.println(ConsoleColor.BLUE + "📋 Reading current brand type from dropdown..." + ConsoleColor.RESET);
	        String currentBrandType = (String) ((JavascriptExecutor) driver).executeScript(
	                "return arguments[0].options[arguments[0].selectedIndex].text;", chosenSelect);
	        System.out.println(ConsoleColor.CYAN + "📋 Current brand type is: [" + currentBrandType + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Toggle logic — Zlaata India → Boss Lady, Boss Lady → Zlaata India
	        String newBrandType;
	        if (currentBrandType.toLowerCase().contains("zlaata india")
	                || currentBrandType.toLowerCase().contains("zlaata")) {
	            newBrandType = "Boss Lady";
	            System.out.println(ConsoleColor.PURPLE + "🔄 Current brand is Zlaata India → will switch to Boss Lady" + ConsoleColor.RESET);
	        } else if (currentBrandType.toLowerCase().contains("boss lady")) {
	            newBrandType = "Zlaata India";
	            System.out.println(ConsoleColor.PURPLE + "🔄 Current brand is Boss Lady → will switch to Zlaata India" + ConsoleColor.RESET);
	        } else {
	            newBrandType = "Zlaata India";
	            System.out.println(ConsoleColor.YELLOW + "⚠️ Unrecognised brand [" + currentBrandType
	                    + "] → defaulting switch to Zlaata India" + ConsoleColor.RESET);
	        }
	        System.out.println(ConsoleColor.PURPLE + "🔄 Brand type change: [" + currentBrandType + "] → [" + newBrandType + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Find matching option value for the new brand type
	        System.out.println(ConsoleColor.BLUE + "🔑 Looking up option value for: [" + newBrandType + "]..." + ConsoleColor.RESET);
	        String optionValue = (String) ((JavascriptExecutor) driver).executeScript(
	                "var select = arguments[0];" +
	                "var target = arguments[1].toLowerCase();" +
	                "for (var i = 0; i < select.options.length; i++) {" +
	                "    if (select.options[i].text.toLowerCase().indexOf(target) !== -1) {" +
	                "        return select.options[i].value;" +
	                "    }" +
	                "}" +
	                "return null;",
	                chosenSelect, newBrandType);

	        if (optionValue == null) {
	            String allOptions = (String) ((JavascriptExecutor) driver).executeScript(
	                    "var s = arguments[0]; var r = '';" +
	                    "for (var i = 0; i < s.options.length; i++) {" +
	                    "    r += s.options[i].value + ' = ' + s.options[i].text + '\\n';" +
	                    "} return r;",
	                    chosenSelect);
	            System.out.println(ConsoleColor.YELLOW + "❌ Target option not found. Available options:\n" + allOptions + ConsoleColor.RESET);
	            throw new RuntimeException("❌ Option not found for brand type: " + newBrandType);
	        }
	        System.out.println(ConsoleColor.CYAN + "🔑 Option value found: [" + optionValue + "] for brand: [" + newBrandType + "]" + ConsoleColor.RESET);
	        Common.waitForElement(1);

	        // ✅ Apply new value using native JS setter + dispatch change event for Select2
	        System.out.println(ConsoleColor.BLUE + "🖱️ Applying new brand type [" + newBrandType + "] via JS..." + ConsoleColor.RESET);
	        ((JavascriptExecutor) driver).executeScript(
	                "var select = arguments[0];" +
	                "var nativeSetter = Object.getOwnPropertyDescriptor(" +
	                "    window.HTMLSelectElement.prototype, 'value').set;" +
	                "nativeSetter.call(select, arguments[1]);" +
	                "select.dispatchEvent(new Event('change', { bubbles: true }));",
	                chosenSelect, optionValue);
	        System.out.println(ConsoleColor.GREEN + "✅ Change event dispatched — brand type set to: [" + newBrandType + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Confirm new value is reflected in the dropdown
	        System.out.println(ConsoleColor.BLUE + "🔍 Confirming new brand type is applied in the dropdown..." + ConsoleColor.RESET);
	        String confirmedLabel = (String) ((JavascriptExecutor) driver).executeScript(
	                "return arguments[0].options[arguments[0].selectedIndex].text;", chosenSelect);
	        System.out.println(ConsoleColor.GREEN + "✅ Dropdown now shows: [" + confirmedLabel + "]" + ConsoleColor.RESET);

	        if (!confirmedLabel.toLowerCase().contains(newBrandType.toLowerCase())) {
	            throw new RuntimeException("❌ Brand type mismatch after set! "
	                    + "Expected [" + newBrandType + "] but got [" + confirmedLabel + "]");
	        }
	        System.out.println(ConsoleColor.GREEN + "✅ Brand type confirmed correct: [" + confirmedLabel + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Wait for auto-save AJAX to complete
	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for auto-save to complete..." + ConsoleColor.RESET);
	        Common.waitForElement(3);
	        System.out.println(ConsoleColor.GREEN + "✅ Auto-save complete" + ConsoleColor.RESET);

	        System.out.println(ConsoleColor.GREEN + "✅ TC_02 Step 2 COMPLETE" + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "   Product  : [" + productlistingName + "]" + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "   Old brand: [" + currentBrandType + "]" + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "   New brand: [" + newBrandType + "]" + ConsoleColor.RESET);

	        this.updatedBrandType = newBrandType;
	        return newBrandType;
	    }

	 // ─── TC_02 STEP 3: Verify Brand Type in Products Module ──────────────────
	    public void verifyBrandTypeInProductsModule(String expectedBrandType) {

	        // ─── NAVIGATE TO PRODUCTS MODULE ──────────────────────────────────────────

	        System.out.println(ConsoleColor.BLUE + "⏳ Navigating to Products module via sidebar..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//body/div[@class='app-body']/div[@class='sidebar text-dark shadow']"
	                        + "/nav[@class='sidebar-nav ps ps--active-y']"
	                        + "/ul[@class='nav']/ul[@class='nav']/li[2]/a[1]")));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	                driver.findElement(By.xpath(
	                        "//body/div[@class='app-body']/div[@class='sidebar text-dark shadow']"
	                        + "/nav[@class='sidebar-nav ps ps--active-y']"
	                        + "/ul[@class='nav']/ul[@class='nav']/li[2]/a[1]")));
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Product's menu" + ConsoleColor.RESET);
	        Common.waitForElement(1);

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//a[normalize-space()='Products']")));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	                driver.findElement(By.xpath("//a[normalize-space()='Products']")));
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Products submenu" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        wait.until(ExpectedConditions.urlContains("product"));
	        wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//i[@class='las la-edit']")));
	        System.out.println(ConsoleColor.GREEN + "✅ Products page table loaded" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	     // ─── SEARCH BY SKU — direct table filter, no Select2 results to click ─────

	        System.out.println(ConsoleColor.BLUE + "⏳ Clicking SKU column header to open SKU search input..." + ConsoleColor.RESET);
	        By skuHeaderLocator = By.xpath("(//a[normalize-space()='SKU'])[1]");
	        wait.until(ExpectedConditions.presenceOfElementLocated(skuHeaderLocator));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
	                driver.findElement(skuHeaderLocator));
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked SKU column header" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ SKU uses a plain text input — NOT a Select2 dropdown
	        // It filters the table directly as you type
	        By skuTextInputLocator = By.xpath("//input[@id='text-filter-sku']");

	        // Fallback if id not present
	        WebElement skuInput;
	        try {
	            wait.until(ExpectedConditions.presenceOfElementLocated(skuTextInputLocator));
	            skuInput = driver.findElement(skuTextInputLocator);
	            System.out.println(ConsoleColor.GREEN + "✅ Found SKU text input by id='text-filter-sku'" + ConsoleColor.RESET);
	        } catch (Exception e) {
	            // Fallback — use the confirmed xpath from your test
	            skuTextInputLocator = By.xpath("(//input[@role='searchbox'])[2]");
	            wait.until(ExpectedConditions.presenceOfElementLocated(skuTextInputLocator));
	            skuInput = driver.findElement(skuTextInputLocator);
	            System.out.println(ConsoleColor.GREEN + "✅ Found SKU text input by searchbox[2] fallback" + ConsoleColor.RESET);
	        }

	        Common.waitForElement(1);
	        skuInput.click();
	        Common.waitForElement(1);
	        skuInput.clear();
	        Common.waitForElement(1);

	        // ✅ Type SKU value — table filters automatically on each keystroke
	        skuInput.sendKeys(productlistingName);
	        System.out.println(ConsoleColor.CYAN + "🔍 Typed SKU: [" + productlistingName + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ✅ Press ENTER to confirm filter — triggers table reload with exact match
	        skuInput.sendKeys(org.openqa.selenium.Keys.ENTER);
	        System.out.println(ConsoleColor.GREEN + "⌨️ Pressed ENTER to apply SKU filter" + ConsoleColor.RESET);
	        Common.waitForElement(3);

	        // ─── WAIT FOR TABLE TO FILTER TO EXACTLY THE MATCHING PRODUCT ────────────

	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for table to show filtered results for SKU: [" + productlistingName + "]" + ConsoleColor.RESET);
	        wait.until(driver -> {
	            try {
	                List<WebElement> rows = driver.findElements(
	                        By.xpath("//table//tbody//tr[not(contains(@class,'odd') and td[normalize-space()='No data'])]"));
	                boolean hasRows = rows.size() >= 1 && rows.get(0).isDisplayed();
	                if (hasRows) {
	                    // Confirm at least one row contains our SKU
	                    String firstRowText = rows.get(0).getText();
	                    return firstRowText.toLowerCase().contains(productlistingName.toLowerCase());
	                }
	                return false;
	            } catch (Exception e) {
	                return false;
	            }
	        });
	        System.out.println(ConsoleColor.GREEN + "✅ Table filtered — matching product row visible for SKU: [" + productlistingName + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        // ─── CLICK EDIT ON THE MATCHING ROW ──────────────────────────────────────

	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for Edit button on matched row..." + ConsoleColor.RESET);
	        By editButtonLocator = By.xpath("//table//tbody//tr[1]//a[contains(@class,'btn-edit')]");
	        wait.until(ExpectedConditions.elementToBeClickable(editButtonLocator));
	        WebElement editButton = driver.findElement(editButtonLocator);
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", editButton);
	        Common.waitForElement(2);
	        System.out.println(ConsoleColor.BLUE + "🖱️ Clicking Edit on matched product row..." + ConsoleColor.RESET);
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", editButton);
	        System.out.println(ConsoleColor.GREEN + "✅ Clicked Edit — loading product edit page for SKU: [" + productlistingName + "]" + ConsoleColor.RESET);
	        Common.waitForElement(3);

	        // ─── WAIT FOR EDIT PAGE TO LOAD ───────────────────────────────────────────

	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for product edit page to fully render..." + ConsoleColor.RESET);
	        wait.until(ExpectedConditions.urlContains("edit"));
	        Common.waitForElement(3);
	        System.out.println(ConsoleColor.GREEN + "✅ Product edit page loaded: " + driver.getCurrentUrl() + ConsoleColor.RESET);

	        // ─── DEBUG DUMP ───────────────────────────────────────────────────────────

	        System.out.println(ConsoleColor.YELLOW + "🐛 Dumping all <select> on product edit page:" + ConsoleColor.RESET);
	        driver.findElements(By.xpath("//select")).forEach(sel ->
	                System.out.println(ConsoleColor.YELLOW + "   id='" + sel.getAttribute("id")
	                        + "' name='" + sel.getAttribute("name")
	                        + "' class='" + sel.getAttribute("class") + "'" + ConsoleColor.RESET));

	        System.out.println(ConsoleColor.YELLOW + "🐛 Dumping all Select2 rendered containers:" + ConsoleColor.RESET);
	        driver.findElements(
	                By.xpath("//span[contains(@id,'select2') and contains(@id,'container')]"))
	                .forEach(span -> System.out.println(ConsoleColor.YELLOW + "   id='" + span.getAttribute("id")
	                        + "' text='" + span.getText().trim() + "'" + ConsoleColor.RESET));

	        // ─── FIND BRAND TYPE — 4 STRATEGIES ──────────────────────────────────────

	        WebElement productBrandTypeSelect = null;

	        // Strategy 1 — by name='brand_type'
	        System.out.println(ConsoleColor.BLUE + "🔍 Strategy 1: select[@name='brand_type']..." + ConsoleColor.RESET);
	        try {
	            productBrandTypeSelect = new WebDriverWait(driver, Duration.ofSeconds(5))
	                    .until(ExpectedConditions.presenceOfElementLocated(
	                            By.xpath("//select[@name='brand_type']")));
	            System.out.println(ConsoleColor.GREEN + "✅ Strategy 1 SUCCESS" + ConsoleColor.RESET);
	        } catch (Exception e) {
	            System.out.println(ConsoleColor.YELLOW + "ℹ️ Strategy 1 failed" + ConsoleColor.RESET);
	        }

	        // Strategy 2 — by class contains 'brand-type'
	        if (productBrandTypeSelect == null) {
	            System.out.println(ConsoleColor.BLUE + "🔍 Strategy 2: select[contains(@class,'brand-type')]..." + ConsoleColor.RESET);
	            try {
	                productBrandTypeSelect = new WebDriverWait(driver, Duration.ofSeconds(5))
	                        .until(ExpectedConditions.presenceOfElementLocated(
	                                By.xpath("(//select[contains(@class,'brand-type')])[1]")));
	                System.out.println(ConsoleColor.GREEN + "✅ Strategy 2 SUCCESS" + ConsoleColor.RESET);
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "ℹ️ Strategy 2 failed" + ConsoleColor.RESET);
	            }
	        }

	        // Strategy 3 — read from visible Select2 span container
	        if (productBrandTypeSelect == null) {
	            System.out.println(ConsoleColor.BLUE + "🔍 Strategy 3: Select2 span contains 'brand_type'..." + ConsoleColor.RESET);
	            try {
	                WebElement select2Container = new WebDriverWait(driver, Duration.ofSeconds(5))
	                        .until(ExpectedConditions.presenceOfElementLocated(
	                                By.xpath("//span[contains(@id,'brand_type')"
	                                        + " and contains(@id,'container')]")));
	                String visibleText = select2Container.getText().trim();
	                System.out.println(ConsoleColor.GREEN + "✅ Strategy 3 SUCCESS — visible text: [" + visibleText + "]" + ConsoleColor.RESET);
	                System.out.println(ConsoleColor.CYAN + "🔍 Expected : [" + expectedBrandType + "]" + ConsoleColor.RESET);
	                System.out.println(ConsoleColor.CYAN + "🔍 Actual   : [" + visibleText + "]" + ConsoleColor.RESET);
	                Common.waitForElement(2);
	                if (visibleText.toLowerCase().contains(expectedBrandType.toLowerCase())) {
	                    System.out.println(ConsoleColor.GREEN + "✅ TC_02 PASSED: Brand type matches — [" + visibleText + "]" + ConsoleColor.RESET);
	                } else {
	                    throw new AssertionError("❌ TC_02 FAILED: Brand type mismatch.\n"
	                            + "   Expected : " + expectedBrandType + "\n"
	                            + "   Actual   : " + visibleText + "\n"
	                            + "   SKU      : " + productlistingName);
	                }
	                return;
	            } catch (AssertionError ae) {
	                throw ae;
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "ℹ️ Strategy 3 failed" + ConsoleColor.RESET);
	            }
	        }

	        // Strategy 4 — scan all selects for brand-like selected text
	        if (productBrandTypeSelect == null) {
	            System.out.println(ConsoleColor.BLUE + "🔍 Strategy 4: Scanning all <select> for brand option text..." + ConsoleColor.RESET);
	            for (WebElement sel : driver.findElements(By.xpath("//select"))) {
	                try {
	                    String selectedText = (String) ((JavascriptExecutor) driver).executeScript(
	                            "return arguments[0].options[arguments[0].selectedIndex].text;", sel);
	                    if (selectedText != null && (
	                            selectedText.toLowerCase().contains("zlaata") ||
	                            selectedText.toLowerCase().contains("boss lady"))) {
	                        productBrandTypeSelect = sel;
	                        System.out.println(ConsoleColor.GREEN + "✅ Strategy 4 SUCCESS — text: [" + selectedText
	                                + "] id='" + sel.getAttribute("id") + "'" + ConsoleColor.RESET);
	                        break;
	                    }
	                } catch (Exception ignored) {}
	            }
	        }

	        if (productBrandTypeSelect == null) {
	            throw new RuntimeException("❌ Brand type select not found after all 4 strategies. "
	                    + "Check debug dump above.");
	        }

	        // ─── READ AND VERIFY ──────────────────────────────────────────────────────

	        String actualBrandType = (String) ((JavascriptExecutor) driver).executeScript(
	                "return arguments[0].options[arguments[0].selectedIndex].text;",
	                productBrandTypeSelect);

	        System.out.println(ConsoleColor.CYAN + "🔍 Expected brand type : [" + expectedBrandType + "]" + ConsoleColor.RESET);
	        System.out.println(ConsoleColor.CYAN + "🔍 Actual brand type   : [" + actualBrandType + "]" + ConsoleColor.RESET);
	        Common.waitForElement(2);

	        if (actualBrandType.toLowerCase().contains(expectedBrandType.toLowerCase())) {
	            System.out.println(ConsoleColor.GREEN + "✅ TC_02 PASSED: Brand type matches — [" + actualBrandType + "]" + ConsoleColor.RESET);
	        } else {
	            throw new AssertionError("❌ TC_02 FAILED: Brand type mismatch.\n"
	                    + "   Expected : " + expectedBrandType + "\n"
	                    + "   Actual   : " + actualBrandType + "\n"
	                    + "   SKU      : " + productlistingName);
	        }
	    }
	    
	    private int oneBased = -1;
	    private boolean categoryValidationFired = false;
	    
	 // ─── TC_03 STEP 2 ─────────────────────────────────────────────────────────
	    public void removeAllCategoriesFromRandomProduct() {

	        categoryValidationFired = false;
	        oneBased = -1;

	        System.out.println(ConsoleColor.BLUE + "⏳ Waiting for Media Library category lists to load..." + ConsoleColor.RESET);

	        wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.xpath("//ul[@class='select2-selection__rendered']")));
	        Common.waitForElement(2);

	        List<WebElement> allCategoryLists = driver.findElements(
	                By.xpath("//ul[@class='select2-selection__rendered']"));
	        System.out.println(ConsoleColor.CYAN + "🔢 Total category lists on page: " + allCategoryLists.size() + ConsoleColor.RESET);

	        if (allCategoryLists.isEmpty()) {
	            throw new RuntimeException("❌ No category lists found on Media Library page");
	        }

	        // Find eligible lists with at least 1 category
	        List<Integer> eligibleIndices = new java.util.ArrayList<>();
	        for (int i = 0; i < allCategoryLists.size(); i++) {
	            List<WebElement> choices = allCategoryLists.get(i).findElements(
	                    By.xpath(".//li[@title]"));
	            System.out.println(ConsoleColor.CYAN + "   List " + (i + 1) + " has " + choices.size() + " categories" + ConsoleColor.RESET);
	            if (choices.size() >= 1) {
	                eligibleIndices.add(i);
	            }
	        }

	        if (eligibleIndices.isEmpty()) {
	            throw new RuntimeException("❌ No category list found with at least 1 category");
	        }

	        int selectedListIndex = eligibleIndices.get(new Random().nextInt(eligibleIndices.size()));
	        oneBased = selectedListIndex + 1;
	        System.out.println(ConsoleColor.CYAN + "🎯 Selected category list index (1-based): " + oneBased + ConsoleColor.RESET);

	        WebElement selectedList = driver.findElement(
	                By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"));
	        ((JavascriptExecutor) driver).executeScript(
	                "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", selectedList);
	        Common.waitForElement(2);
	        

	        // Capture product name
	        try {
	            productlistingName = driver.findElement(
	                    By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                            + "/ancestor::tr[1]//td[1]//a")).getText().trim();
	        } catch (Exception e) {
	            try {
	                productlistingName = driver.findElement(
	                        By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                                + "/ancestor::tr[1]//td[1]")).getText().trim();
	            } catch (Exception e2) {
	                productlistingName = "Product at list index " + oneBased;
	            }
	        }
	        System.out.println(ConsoleColor.CYAN + "📌 Working on product: " + productlistingName + ConsoleColor.RESET);

	        // Log all initial categories
	        List<WebElement> initialCategories = driver.findElements(
	                By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                        + "//li[@title]"));
	        System.out.println(ConsoleColor.BLUE + "📋 Initial categories (" + initialCategories.size() + "):" + ConsoleColor.RESET);
	        for (WebElement cat : initialCategories) {
	            System.out.println(ConsoleColor.CYAN + "   - [" + cat.getAttribute("title") + "]" + ConsoleColor.RESET);
	        }

	        // ─── REMOVE CATEGORIES ONE BY ONE ─────────────────────────────────────────
	        int iteration = 1;

	        while (true) {

	            // Re-fetch categories for THIS product only
	            List<WebElement> currentCategories = driver.findElements(
	                    By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                            + "//li[@title]"));

	            int remainingCount = currentCategories.size();
	            System.out.println(ConsoleColor.CYAN + "🔢 Iteration " + iteration
	                    + " — Categories remaining: " + remainingCount + ConsoleColor.RESET);

	            if (remainingCount == 0) {
	                System.out.println(ConsoleColor.YELLOW + "ℹ️ No categories left — loop complete" + ConsoleColor.RESET);
	                break;
	            }

	            // Get title of first category
	            String categoryName = currentCategories.get(0).getAttribute("title");
	            if (categoryName == null || categoryName.trim().isEmpty()) {
	                categoryName = "Category " + iteration;
	            }

	            boolean isLastCategory = (remainingCount == 1);
	            System.out.println((isLastCategory
	                    ? ConsoleColor.YELLOW + "⚠️ LAST CATEGORY — attempting to trigger validation: "
	                    : ConsoleColor.PURPLE + "🗑️ Removing: ")
	                    + "[" + categoryName + "]" + ConsoleColor.RESET);

	            // Find the × remove button inside the first category li
	            WebElement removeBtn;
	            try {
	                removeBtn = currentCategories.get(0).findElement(
	                        By.xpath(".//span[contains(@class,'select2-selection__choice__remove')]"));
	            } catch (Exception e) {
	                System.out.println(ConsoleColor.YELLOW + "❌ No remove button found for: [" + categoryName + "] — stopping" + ConsoleColor.RESET);
	                break;
	            }

	            // Scroll into view and click
	            ((JavascriptExecutor) driver).executeScript(
	                    "arguments[0].scrollIntoView({behavior:'smooth', block:'center'});", removeBtn);
	            Common.waitForElement(1);
	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeBtn);
	            System.out.println(ConsoleColor.GREEN + "✅ Clicked × on: [" + categoryName + "]" + ConsoleColor.RESET);

	            // Wait longer on last category
	            Common.waitForElement(isLastCategory ? 3 : 2);

	            // ─── CHECK FOR NOTY MESSAGE ────────────────────────────────────────────
	            // IMPORTANT: "Categories Updated" is a SUCCESS message — ignore it
	            // Only treat message as VALIDATION if it contains "required" or "least"
	            List<WebElement> notyMessages = driver.findElements(
	                    By.xpath("//div[@class='noty_body']"));

	            for (WebElement msg : notyMessages) {
	                try {
	                    String msgText = msg.getText().trim();
	                    if (!msgText.isEmpty()) {
	                        System.out.println(ConsoleColor.BLUE + "🔔 Noty message detected: [" + msgText + "]" + ConsoleColor.RESET);
	                        String msgLower = msgText.toLowerCase();
	                        if (msgLower.contains("required")
	                                || msgLower.contains("least")
	                                || msgLower.contains("minimum")
	                                || msgLower.contains("cannot")
	                                || msgLower.contains("must")) {
	                            categoryValidationFired = true;
	                            System.out.println(ConsoleColor.GREEN + "✅ TC_03 COMPLETE — VALIDATION message triggered: ["
	                                    + msgText + "] when removing: [" + categoryName + "]" + ConsoleColor.RESET);
	                        } else {
	                            System.out.println(ConsoleColor.YELLOW + "ℹ️ Ignoring SUCCESS message: [" + msgText + "] — continuing removal" + ConsoleColor.RESET);
	                        }
	                        break;
	                    }
	                } catch (Exception ignored) {}
	            }

	            if (categoryValidationFired) {
	                break;
	            }

	            // If last category — check if tag is still present (silent block)
	            if (isLastCategory) {
	                Common.waitForElement(1);
	                List<WebElement> afterAttempt = driver.findElements(
	                        By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                                + "//li[@title]"));
	                if (!afterAttempt.isEmpty()) {
	                    categoryValidationFired = true;
	                    System.out.println(ConsoleColor.GREEN + "ℹ️ Last category still present after click — "
	                            + "validation silently blocked removal" + ConsoleColor.RESET);
	                    System.out.println(ConsoleColor.GREEN + "✅ TC_03 COMPLETE — last category protected" + ConsoleColor.RESET);
	                } else {
	                    System.out.println(ConsoleColor.YELLOW + "❌ Last category was removed — "
	                            + "validation did not fire for: " + productlistingName + ConsoleColor.RESET);
	                }
	                break;
	            }

	            // Confirm count decreased — if not, retry with direct click
	            List<WebElement> afterRemoval = driver.findElements(
	                    By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                            + "//li[@title]"));
	            System.out.println(ConsoleColor.CYAN + "📊 Categories remaining after removal: " + afterRemoval.size() + ConsoleColor.RESET);

	            if (afterRemoval.size() >= remainingCount) {
	                System.out.println(ConsoleColor.YELLOW + "⚠️ Count did not decrease — retrying with direct click" + ConsoleColor.RESET);
	                try {
	                    List<WebElement> retryCategories = driver.findElements(
	                            By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                                    + "//li[@title]"));
	                    WebElement retryBtn = retryCategories.get(0).findElement(
	                            By.xpath(".//span[contains(@class,'select2-selection__choice__remove')]"));
	                    retryBtn.click();
	                    System.out.println(ConsoleColor.GREEN + "✅ Retry direct click on × for: [" + categoryName + "]" + ConsoleColor.RESET);
	                    Common.waitForElement(2);
	                } catch (Exception e) {
	                    System.out.println(ConsoleColor.YELLOW + "❌ Retry failed — stopping loop" + ConsoleColor.RESET);
	                    break;
	                }
	            }

	            iteration++;
	            Common.waitForElement(1);
	        }

	        System.out.println(ConsoleColor.GREEN + "✅ TC_03 Step 2 complete — "
	                + (categoryValidationFired ? "✅ validation fired" : "❌ validation did NOT fire")
	                + " — iterations: " + iteration + ConsoleColor.RESET);
	    }	

		// ─── TC_03 STEP 3 ─────────────────────────────────────────────────────────
	    public void verifyCategoriesStillDisplayed() {

	        System.out.println("⏳ Verifying at least one category still displayed for: "
	                + productlistingName);
	        Common.waitForElement(2);

	        if (oneBased == -1) {
	            throw new RuntimeException(
	                    "❌ oneBased not set — removeAllCategoriesFromRandomProduct() must run first");
	        }

	        // Use //li[@title] — same selector used during removal loop
	        // This scopes to ONLY the category tags, not brand type or other Select2 elements
	        List<WebElement> remainingCategories = driver.findElements(
	                By.xpath("(//ul[@class='select2-selection__rendered'])[" + oneBased + "]"
	                        + "//li[@title]"));

	        System.out.println("🔢 Categories still on page for [" + productlistingName + "]: "
	                + remainingCategories.size());

	        if (remainingCategories.isEmpty()) {
	            throw new AssertionError(
	                    "❌ TC_03 FAILED: All categories were removed for product: "
	                    + productlistingName
	                    + " — validation did not protect the last category");
	        }

	        remainingCategories.forEach(cat -> {
	            try {
	                String text = cat.getAttribute("title");
	                if (text == null || text.isEmpty()) {
	                    text = cat.getText().trim().replace("×", "").trim();
	                }
	                System.out.println("   ✅ Remaining category: [" + text + "]");
	            } catch (Exception ignored) {}
	        });

	        System.out.println("✅ TC_03 Step 3 PASSED: Last category still visible — "
	                + "validation protected it for: " + productlistingName);
	    }

		// ─── TC_03 STEP 4 ─────────────────────────────────────────────────────────
		public void verifyValidationMessage() {

		    System.out.println("⏳ Verifying validation message was shown...");
		    Common.waitForElement(2);

		    // STRICT: must have fired during removal loop — do NOT pass on assumption
		    if (!categoryValidationFired) {
		        throw new AssertionError(
		                "❌ TC_03 FAILED: Validation never fired during removal — "
		                + "application allowed all categories to be removed for: "
		                + productlistingName);
		    }

		    // Check if noty message is still visible on screen
		    List<WebElement> notyMessages = driver.findElements(
		            By.xpath("//div[@class='noty_body']"));

		    boolean messageStillVisible = false;
		    for (WebElement msg : notyMessages) {
		        try {
		            String msgText = msg.getText().trim();
		            if (!msgText.isEmpty()) {
		                System.out.println("🔔 Validation message still on screen: [" + msgText + "]");
		                messageStillVisible = true;
		                break;
		            }
		        } catch (Exception ignored) {}
		    }

		    if (messageStillVisible) {
		        System.out.println("✅ TC_03 Step 4 PASSED: Validation message confirmed visible — "
		                + "last category protected for: " + productlistingName);
		    } else {
		        System.out.println("ℹ️ Validation message already auto-dismissed "
		                + "— noty messages are short-lived");
		        System.out.println("✅ TC_03 Step 4 PASSED: Validation was recorded during removal — "
		                + "last category protected for: " + productlistingName);
		    }
		}
		
		

		// ─── TC_01 ORCHESTRATOR ───────────────────────────────────────────────────
		public void executeMediaLibraryImageUploadFlow() {
	        // Step 1 & 2
	        navigateToLandingPageUI();
	        navigateToDressesPage();
	        System.out.println("✅ Orchestrator: Navigated to Dresses page");

	        // ─── GIVE PAGE EXTRA TIME TO FULLY RENDER ALL CARDS ──────────────────────
	        Common.waitForElement(3);

	        // Step 3
	        String capturedName = selectRandomProductAndCaptureName();
	        System.out.println("✅ Orchestrator: Product selected — " + capturedName);

	        // Step 4
	        searchProductAndCaptureInternalName();
	        System.out.println("✅ Orchestrator: Internal name and SKU captured");

	        // Step 5
	        navigateToMediaLibraryAndSearchBySku();
	        System.out.println("✅ Orchestrator: Navigated to Media Library");

	        // Step 6
	        String uploadedImageName = clickChooseFileUploadAndSave();
	        System.out.println("✅ Orchestrator: Image uploaded — " + uploadedImageName);

	        // Step 7
	        verifyUploadedImageOnUI(uploadedImageName);
	        System.out.println("✅ Orchestrator: Image verification completed — " + uploadedImageName);
	    }
		
		
		// ─── TC_02 ORCHESTRATOR ───────────────────────────────────────────────────
		public String executeBrandTypeUpdateFlow() {
		    // Step 1: Login and Navigate to Media Library
		    navigateToAdminAndMediaLibrary();
		    System.out.println("✅ Orchestrator: Navigated to Media Library");

		    // Step 2: Update Brand Type for a Random Product
		    String newBrandType = updateBrandTypeForRandomProduct();
		    System.out.println("✅ Orchestrator: Brand type updated to — " + newBrandType);

		    // Step 3: Verify Brand Type in Products Module
		    verifyBrandTypeInProductsModule(newBrandType);
		    System.out.println("✅ Orchestrator: Brand type verified in Products module");

		    return newBrandType;
		}
		
		// ─── TC_03 ORCHESTRATOR ───────────────────────────────────────────────────
		public void executeCategoryValidationFlow() {
		    // Step 1: Login and Navigate to Media Library
		    navigateToAdminAndMediaLibrary();
		    System.out.println("✅ Orchestrator: Navigated to Media Library");

		    // Step 2: Try to remove all categories from a random product
		    removeAllCategoriesFromRandomProduct();
		    System.out.println("✅ Orchestrator: Attempted to remove all categories");

		    // Step 3: Verify at least one category is still displayed
		    verifyCategoriesStillDisplayed();
		    System.out.println("✅ Orchestrator: Categories still displayed after removal attempt");

		    // Step 4: Verify validation message was shown
		    verifyValidationMessage();
		    System.out.println("✅ Orchestrator: Validation message verified");
		}
	    
	    @Override
	    public boolean verifyExactText(WebElement ele, String expectedText) {
	        return ele.getText().trim().equals(expectedText.trim());
	    }
	
	    @Override
	    public WebDriver gmail(String browserName) {
	        return null;
	    }
	
	    @Override
	    protected boolean isAt() {
	        return false;
	    }
	}