package pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import objectRepo.CouponObjRepo;
import stepDef.ExceptionTracker;
import stepDef.Hooks;
import utils.Common;

public final class CouponPage extends CouponObjRepo {




	public CouponPage(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}

	private WebDriverWait wait;

	public void CheckOutNavigation() {
		ProductDetailsPage pDP = new ProductDetailsPage(driver);
		Common.waitForElement(5);
		pDP.buyNow(Hooks.getScenario());
	}

	public void clickApplyButton() {
		HomePage home = new HomePage(driver);
		home.homeLaunch();
		CheckOutNavigation();
		wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
	}

	public void verifyLoginPopupAppears() {

		wait.until(ExpectedConditions.visibilityOf(loginPopup));
		Assert.assertTrue("❌ Login popup did not appear!", loginPopup.isDisplayed());
		System.out.println("✅ Login popup appeared successfully!");
	}


	public void enterCouponCode() {
		wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
		wait.until(ExpectedConditions.visibilityOf(couponPopupTextBox));
		type(couponPopupTextBox,FileReaderManager.getInstance().getJsonReader().getValueFromJson("FeedBack"));
	}

	public void clickApplyButtonPop() {
		wait.until(ExpectedConditions.elementToBeClickable(applyButtonInPopup)).click();
	}

	public void verifyErrorMessageDisplayed(String expectedText) {
		By eligibleValidation = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");

		// Wait until the snackbar appears (freshly locating it)
		String actualText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(eligibleValidation))
				.getText().trim();

		Assert.assertTrue("❌ Expected success text not found. Actual: " + actualText,
				actualText.toLowerCase().contains(expectedText.toLowerCase()));
		System.out.println("✅ Error message appeared: " + actualText);
	}

	public void FirstBuy() throws TimeoutException {
		// Step 1: Open coupon popup
		wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

		// Step 2: If coupon locked, keep increasing qty until unlocked
		if (isCouponLocked()) {
			System.out.println("🔒 Coupon locked — unlocking...");

			// Close popup
			wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();

			// Loop until unlocked
			while (true) {
				increaseProductQuantity();
				System.out.println("🛒 Increased qty — rechecking...");

				wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

				if (!isCouponLocked()) {
					System.out.println("🔓 Coupon unlocked!");
					break;
				}
			}
		}

		// Step 3: Apply coupon
		wait.until(ExpectedConditions.visibilityOf(couponPopupTextBox))
		.sendKeys(FileReaderManager.getInstance().getJsonReader().getValueFromJson("FirstBuy"));
		click(applyButtonInPopup);

	}

	/**
	 * Check if coupon is locked
	 */
	private boolean isCouponLocked() throws TimeoutException {
		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
		WebElement lockedElement = shortWait.until(
				ExpectedConditions.presenceOfElementLocated(
						By.xpath("//div[@class='coupon_list_wrap non_eligible_coupons']"))
				);
		return lockedElement.isDisplayed();
	}

	/**
	 * Increase product quantity
	 */
	private void increaseProductQuantity() {
		try {
			// Check if increment button is enabled and clickable
			if (increaseTheProductQunatity.isDisplayed() && increaseTheProductQunatity.isEnabled()) {
				wait.until(ExpectedConditions.elementToBeClickable(increaseTheProductQunatity)).click();
				System.out.println("✅ Increased product quantity");
			} else {
				System.out.println("⚠️ Increase button disabled — adding new product instead");
				Common.waitForElement(5);
				Actions actions = new Actions(driver);

				// Step 1: Navigate to category and sort low-to-high
				actions.moveToElement(shopMenu);
				actions.moveToElement(category).click().build().perform();
				actions.moveToElement(sortBy).click().build().perform();
				actions.moveToElement(sortByPriceLowtoHigh).click().build().perform();

				// Step 2: Pick random product
				List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
				Collections.shuffle(clickRandomProduct);

				if (!clickRandomProduct.isEmpty()) {
					WebElement randomProduct = clickRandomProduct.get(0);
					actions.moveToElement(randomProduct).click().build().perform();
					actions.moveToElement(buyNowbutton).click().build().perform();
					wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
					type(couponPopupTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalPercentage"));
					clickApplyButtonPop();
				}

			}
		} catch (Exception e) {
			System.out.println("⚠️ Could not increase qty (button missing/disabled) — adding new product instead");
			Common.waitForElement(5);
			Actions actions = new Actions(driver);

			// Step 1: Navigate to category and sort low-to-high
			actions.moveToElement(shopMenu);
			actions.moveToElement(category).click().build().perform();
			actions.moveToElement(sortBy).click().build().perform();
			actions.moveToElement(sortByPriceLowtoHigh).click().build().perform();

			// Step 2: Pick random product
			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
			Collections.shuffle(clickRandomProduct);

			if (!clickRandomProduct.isEmpty()) {
				WebElement randomProduct = clickRandomProduct.get(0);
				actions.moveToElement(randomProduct).click().build().perform();
				actions.moveToElement(buyNowbutton).click().build().perform();
				wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
				type(couponPopupTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalPercentage"));
				clickApplyButtonPop();
			}

		}
	}

	/**
	 * Verify success message
	 */
	public void verifyAppliedMessage(String expectedText) {
		By successMessageLocator = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");

		// Wait until the snackbar appears (freshly locating it)
		String actualText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(successMessageLocator))
				.getText().trim();

		Assert.assertTrue("❌ Expected success text not found. Actual: " + actualText,
				actualText.toLowerCase().contains(expectedText.toLowerCase()));
		System.out.println("✅ Applied message appeared: " + actualText);
	}


	public void newsLetterCoupon() throws TimeoutException {
		// Step 1: Open coupon popup
		wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

		// Step 2: If coupon locked, keep increasing qty until unlocked
		if (isCouponLocked()) {
			System.out.println("🔒 Coupon locked — unlocking...");

			// Close popup
			wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();

			// Loop until unlocked
			while (true) {
				increaseProductQuantity();
				System.out.println("🛒 Increased qty — rechecking...");

				wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

				if (!isCouponLocked()) {
					System.out.println("🔓 Coupon unlocked!");
					break;
				} else {
					wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();
				}
			}
		}

		// Step 3: Apply coupon
		wait.until(ExpectedConditions.visibilityOf(couponPopupTextBox))
		.sendKeys(FileReaderManager.getInstance().getJsonReader().getValueFromJson("NewLetter"));
		click(applyButtonInPopup);

	}
	public  void RandomMailId() {
		// Step 1: Generate a random email
		String randomEmail = generateRandomEmail();

		try {

			WebElement emailInput = driver.findElement(By.id("feedback_email"));
			emailInput.sendKeys(randomEmail);
			// Optionally print or use email later
			System.out.println("Random email entered: " + randomEmail);

		} finally {
			// Close the browser after a short delay for demo purposes
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Utility method to generate random email
	private static String lastGeneratedEmail;

	private static String generateRandomEmail() {
		String prefix = "testing"; // fixed name prefix
		String digits = "0123";
		Random rnd = new Random();

		StringBuilder email = new StringBuilder(prefix);

		// Add 4 random digits after the prefix
		for (int i = 0; i < 4; i++) {
			email.append(digits.charAt(rnd.nextInt(digits.length())));
		}

		// Append fixed domain
		email.append("@gmail.com");

		lastGeneratedEmail = email.toString();
		return lastGeneratedEmail;
	}

	public static String getLastGeneratedEmail() {
		return lastGeneratedEmail;
	}

	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	/**
	 * Step 1: Submit Feedback
	 */
	public void feedBack() {
		Actions action = new Actions(driver);
		click(homeMenu);
		Common.waitForElement(2);
		click(feedBack);
		click(feedletsDoIT);
		Common.waitForElement(1);

		String randomEmail = generateRandomEmail();
		type(feedMailId, randomEmail);
		System.out.println("📧 Feedback submitted with email: " + randomEmail);

		action.moveToElement(continueFeed).click().build().perform();
		clickUsingJavaScript(feedCollectionYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedSearchingYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStruggle2);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStarButton);
		clickUsingJavaScript(feedFinalContinue);
		clickUsingJavaScript(feedBack);
	}

	/**
	 * Step 2: Pass feedback email to Account Settings and verify OTP
	 */
	public void verifyEmailInAccountSettingsAndSubmitOTP() {
		click(profile);
		new Actions(driver).moveToElement(accountsSideMenuButton).click().perform();

		// Enter the same email generated during feedback
		type(accountEmailInput, getLastGeneratedEmail());
		click(verifyButton);

		// Enter OTP
		type(enterotp, FileReaderManager.getInstance().getJsonReader().getValueFromJson("OTP"));
		click(verifyOTPButton);

		// Validate
		String emailInAccount = wait.until(ExpectedConditions.visibilityOf(accountEmailField)).getText().trim();
		if (emailInAccount.equalsIgnoreCase(getLastGeneratedEmail())) {
			System.out.println("✅ Email verified in account settings: " + emailInAccount);
		} 
	}

	/**
	 * Step 3: Apply feedback coupon at checkout
	 */
	public void applyFeedbackCouponAtCheckout() {
		ProductDetailsPage pDP = new ProductDetailsPage(driver);
		Common.waitForElement(5);
		pDP.buyNow(Hooks.getScenario());
		enterCouponCode();
		clickApplyButtonPop();    

		String actualText = wait.until(ExpectedConditions.visibilityOf(successMessage)).getText().trim();
		Assert.assertTrue("❌ Feedback coupon not applied. Actual: " + actualText,
				actualText.toLowerCase().contains("applied"));
		System.out.println("✅ Feedback coupon applied successfully: " + actualText);
	}

	public void enterFirstBuyCouponCode() {
		wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
		wait.until(ExpectedConditions.visibilityOf(couponPopupTextBox));
		type(couponPopupTextBox,FileReaderManager.getInstance().getJsonReader().getValueFromJson("FirstBuy"));
	}

	public void firstBuyAfterorderPlace(String expectedText) {
		By alreadyUSed = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");

		// Wait until the snackbar appears (freshly locating it)
		String actualText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(alreadyUSed))
				.getText().trim();

		Assert.assertTrue("❌ Expected success text not found. Actual: " + actualText,
				actualText.toLowerCase().contains(expectedText.toLowerCase()));
		System.out.println("✅ Error message appeared: " + actualText);
	}

	public void subscribeNewsLetter() {

		String randomEmail = generateRandomEmail();
		type(subscribeMailId, randomEmail);
		click(subScribeBtn);
		System.out.println("📧 Newsletter submitted with email: " + randomEmail);

	}

	public void entercodeAfterNewsletter() {
		ProductDetailsPage pDP = new ProductDetailsPage(driver);
		Common.waitForElement(5);
		pDP.buyNow(Hooks.getScenario());
		wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
		wait.until(ExpectedConditions.visibilityOf(couponPopupTextBox));
		type(couponPopupTextBox,FileReaderManager.getInstance().getJsonReader().getValueFromJson("NewLetter"));

	}



	public void validateCouponPercentage() throws InterruptedException, TimeoutException {
		Common.waitForElement(5);
		Actions actions = new Actions(driver);

		// Step 1: Navigate to category and sort low-to-high
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		actions.moveToElement(sortBy).click().build().perform();
		actions.moveToElement(sortByPriceLowtoHigh).click().build().perform();

		// Step 2: Pick random product
		List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(clickRandomProduct);

		if (!clickRandomProduct.isEmpty()) {
			WebElement randomProduct = clickRandomProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			actions.moveToElement(buyNowbutton).click().build().perform();
			wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
			type(couponPopupTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalPercentage"));
			clickApplyButtonPop();
		}

		// Step 3: Handle minimum purchase validation
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

			// Check if minimum purchase message appears
			if (shortWait.until(ExpectedConditions.or(
					ExpectedConditions.textToBePresentInElement(minimumPurchase, "Minimum"),
					ExpectedConditions.visibilityOf(minimumPurchase)
					))) {
				String errorText = minimumPurchase.getText();
				System.out.println("❌ Minimum Purchase Validation Found: " + errorText);

				// Close popup safely
				try {
					click(closePopUp);
					System.out.println("Popup closed successfully after minimum purchase validation");
				} catch (Exception e) {
					System.out.println("❌ Popup close failed, using refresh as fallback");
					driver.navigate().refresh();
				}

				Common.waitForElement(2);

				// Only in this case → Add high-priced product
				actions.moveToElement(shopMenu).perform();
				actions.moveToElement(category).click().perform();
				actions.moveToElement(sortBy).click().perform();
				actions.moveToElement(sortByPriceHightoLow).click().perform();

				List<WebElement> clickRandomProduct01 = driver.findElements(
						By.xpath("//div[@class='product_list_cards_list ']"));
				Collections.shuffle(clickRandomProduct01);

				if (!clickRandomProduct01.isEmpty()) {
					WebElement randomProduct01 = clickRandomProduct01.get(0);
					actions.moveToElement(randomProduct01).click().perform();
					actions.moveToElement(buyNowbutton).click().perform();
				}
			}
		} catch (Exception e) {
			System.out.println("⚠️ Exception while checking minimum purchase: " + e.getMessage());
		}

		// Step 4: Wait for discounted MRP and coupon amount
		waitUntilTextPresent(discountedMrpLine);
		waitUntilTextPresent(couponAmountLine);

		double discountedMrp = extractAmountSafe(discountedMrpLine.getText());
		double couponAmount = extractAmountSafe(couponAmountLine.getText());

		// Step 5: Get coupon percentage from popup
		click(viewCoupon);
		wait.until(ExpectedConditions.visibilityOfAllElements(couponPercentageTexts));

		String percentageText = "";
		if (!couponPercentageTexts.isEmpty()) {
			percentageText = couponPercentageTexts.get(0).getText().trim();
		}

		if (percentageText.isEmpty()) {
			throw new IllegalArgumentException("Percentage text is empty or not found in coupon popup");
		}

		int percentage = extractPercentage(percentageText);

		click(closePopUp);

		// Step 6: Calculate expected discount
		double expectedDiscount = Math.round(discountedMrp * percentage / 100.0);

		System.out.println("Discounted MRP: ₹" + discountedMrp);
		System.out.println("Coupon Percentage: " + percentage + "%");
		System.out.println("Expected Discount: ₹" + expectedDiscount);
		System.out.println("Actual Discount Applied: ₹" + couponAmount);

		// Step 7: Assert
		Assert.assertEquals(expectedDiscount, couponAmount, 1.0);
	}
	// Utility method to extract amount from string
	private double extractAmountSafe(String text) {
		if (text == null || text.trim().isEmpty()) {
			throw new IllegalArgumentException("Amount text is null or empty");
		}
		String numberOnly = text.replaceAll("[^0-9.]", "");
		if (numberOnly.isEmpty()) {
			throw new IllegalArgumentException("No digits found in string: " + text);
		}
		return Double.parseDouble(numberOnly);
	}

	// Utility method to extract percentage from "5% OFF" kind of string
	private int extractPercentage(String text) {
		// Example input: "Save 5% off", "5 % Discount", "Coupon 10%"
		Pattern p = Pattern.compile("(\\d+)%");
		Matcher m = p.matcher(text);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		} else {
			throw new IllegalArgumentException("Percentage text is empty or not found in: " + text);
		}
	}



	private void waitUntilTextPresent(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(driver -> !element.getText().trim().isEmpty());
	}


	//gowtham 
	public void checkout() {

		ProductDetailsPage pdp = new ProductDetailsPage(driver);


		Common.waitForElement(2);
		pdp.buyNow(Hooks.getScenario());

	}
	//	public void FirstBuy() throws TimeoutException {
	//		// Step 1: Open coupon popup
	//		wait.until(ExpectedConditions.elementToBeClickable(viewCouponButton)).click();
	//		// Step 2: If coupon locked, keep increasing qty until unlocked
	//		if (isCouponLocked()) {
	//			System.out.println(":lock: Coupon locked — unlocking...");
	//			// Close popup
	//			wait.until(ExpectedConditions.elementToBeClickable(couponPopupcloseButton)).click();
	//			// Loop until unlocked
	//			while (true) {
	//				increaseProductQuantity();
	//				System.out.println(":shopping_trolley: Increased qty — rechecking...");
	//				wait.until(ExpectedConditions.elementToBeClickable(viewCouponButton)).click();
	//				if (!isCouponLocked()) {
	//					System.out.println(":unlock: Coupon unlocked!");
	//					break;
	//				} else {
	//					wait.until(ExpectedConditions.elementToBeClickable(couponPopupcloseButton)).click();
	//					ProductDetailsPage pdp = new ProductDetailsPage(driver);
	//					pdp.buyNow(Hooks.getScenario());
	//				}
	//			}
	//		}
	//		// Step 3: Apply coupon
	//		wait.until(ExpectedConditions.visibilityOf(couponCodeTextBox))
	//		.sendKeys(FileReaderManager.getInstance().getJsonReader().getValueFromJson("FirstBuy"));
	//		click(couponPopupApplyButton);
	//	}
	//	/**
	//	 * Check if coupon is locked
	//	 */
	//	private boolean isCouponLocked() throws TimeoutException {
	//		WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
	//		WebElement lockedElement = shortWait.until(
	//				ExpectedConditions.presenceOfElementLocated(
	//						By.xpath("//div[@class='coupon_list_wrap non_eligible_coupons']"))
	//				);
	//		return lockedElement.isDisplayed();
	//	}
	//	/**
	//	 * Increase product quantity
	//	 */
	//	private void increaseProductQuantity() {
	//		wait.until(ExpectedConditions.elementToBeClickable(increaseTheProductQunatity)).click();
	//	}
	//	/**
	//	 * Verify success message
	//	 */
	//	public void verifyAppliedMessage(String expectedText) {
	//		By successMessageLocator = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");
	//		// Wait until the snackbar appears (freshly locating it)
	//		String actualText = wait.until(ExpectedConditions
	//				.visibilityOfElementLocated(successMessageLocator))
	//				.getText().trim();
	//		Assert.assertTrue(":x: Expected success text not found. Actual: " + actualText,
	//				actualText.toLowerCase().contains(expectedText.toLowerCase()));
	//		System.out.println(":white_tick: Applied message appeared: " + actualText);
	//	}

	private void newAddress() {
		AddressPage address = new AddressPage(driver);
		address.newAddressData();

	}

	private String getCleanAmount(WebElement element) {
		return element.getText().replaceAll("[^0-9]", "").trim();
	}

	private String getCleanAmountWithWait(WebElement element, int seconds) {
		WebElement el = new WebDriverWait(driver, Duration.ofSeconds(seconds))
				.until(ExpectedConditions.visibilityOf(element));
		return el.getText().replaceAll("[^0-9]", "").trim();
	}



	public void invalidCouponCode() {

		click(viewCouponButton);	
		type(couponCodeTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("InvalidCouponCode"));
	}

	public void CouponPopupApplyButton() {
		click(couponPopupApplyButton);

	}

	public void invalidcouponCodeValidationMessage(String expectedText ) {

		By inValidCoupon = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");
		// Wait until the snackbar appears (freshly locating it)
		String actualText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(inValidCoupon))
				.getText().trim();
		Assert.assertTrue(":x: Expected success text not found. Actual: " + actualText,
				actualText.toLowerCase().contains(expectedText.toLowerCase()));
		System.out.println( "\u001B[0m" + "Error message appeared: " + actualText  + "\u001B[0m");
	}

	//18   
	public void appliedValidCouponCode() throws TimeoutException 
	{
		wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

		// Step 2: If coupon locked, keep increasing qty until unlocked
		if (isCouponLocked()) {
			System.out.println("🔒 Coupon locked — unlocking...");

			// Close popup
			wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();

			// Loop until unlocked
			while (true) {
				increaseProductQuantity();
				System.out.println("🛒 Increased qty — rechecking...");



				if (!isCouponLocked()) {
					System.out.println("🔓 Coupon unlocked!");
					break;
				}
			}
		}





	}
	public void sameCouponAmountDisplayingInAllThreePages() {

		Common.waitForElement(2);

		// Coupon Popup
		String popupAmount = getCleanAmount(appliedCouponAmountOnCouponsSection);
		System.out.println("Coupon Amount on Coupon Popup   : " + popupAmount);

		// Refresh the page
		driver.navigate().refresh();

		// Checkout Page
		String checkoutAmount = getCleanAmount(appliedCouponAmountOnCheckoutPage);
		System.out.println("Coupon Amount on Checkout Page  : " + checkoutAmount);

		// Continue to Address Page
		click(continueButtonOnCheckoutPage);
		newAddress();
		String addressAmount = getCleanAmount(appliedCouponAmountOnAddressPage);
		System.out.println("Coupon Amount on Address Page   : " + addressAmount);

		// Continue to Payment Page
		click(continueButtonOnCheckoutPageaddress);
		String paymentAmount = getCleanAmountWithWait(appliedCouponAmountOnPaymentPage, 10); // wait up to 10s
		System.out.println("Coupon Amount on Payment Page   : " + paymentAmount);

		// Validations
		Assert.assertEquals("Mismatch between Coupon Popup and Checkout Page", popupAmount, checkoutAmount);
		Assert.assertEquals("Mismatch between Address Page and Payment Page", addressAmount, paymentAmount);

		System.out.println("✅ Coupon amount matches on Coupon Popup & Checkout Page, and Address & Payment pages.");

	}

	//17

	public void refreshThePage() {


		Common.waitForElement(5);

		String popupAmount = getCleanAmount(appliedCouponAmountOnCouponsSection);
		System.out.println("Coupon Amount on Coupon Section before Refresh the page   : " + popupAmount);

		String checkoutAmount = getCleanAmount(appliedCouponAmountOnCheckoutPage);
		System.out.println("Coupon Amount on Checkout Page before Refresh the page  : " + checkoutAmount);



		driver.navigate().refresh();


	}

	public void checkTheCouponAmountAfterRefresh() {




		String popupAmount = getCleanAmount(appliedCouponAmountOnCouponsSection);
		System.out.println("Coupon Amount on Coupon Section after  Refresh the page   : " + popupAmount);

		String checkoutAmount = getCleanAmount(appliedCouponAmountOnCheckoutPage);
		System.out.println("Coupon Amount on Checkout Page after  Refresh the page  : " + checkoutAmount);

	}


	public void CouponAppliedValidationMessage()

	{

		Common.waitForElement(5);
		click(viewCouponButton);
		type(couponPopupTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalFixed"));
		click(applyButtonInPopup);

	}

	public void removeCouponFunctionality(String expectedText) {

		click(viewCouponButton);
		click(couponPopupRemoveButton);

		By removeMessage = By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']");
		// Wait until the snackbar appears (freshly locating it)
		String actualText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(removeMessage))
				.getText().trim();
		Assert.assertTrue(":x: Expected success text not found. Actual: " + actualText,
				actualText.toLowerCase().contains(expectedText.toLowerCase()));
		System.out.println( "\u001B[0m" + "Error message appeared: " + actualText  + "\u001B[0m");
	}

	public void CouponRemovedOrNot() {

		if ((appliedCouponAmountOnCouponsSection.isDisplayed()) || (appliedCouponAmountOnCheckoutPage.isDisplayed())) {
			System.out.println("❌ Still coupon not removed — Test Case Failed");
		} else {
			System.out.println("✅ Coupon removed successfully — Test Case Passed");
		}



	}


	//15

	public void addPropdcutToCart() {

		HomePage home = new HomePage(driver);
		home.homeLaunch();
		checkout();
		click(viewCouponButton);
		click(couponPopupApplyButton);		
		wait.until(ExpectedConditions.visibilityOf(loginPopup));
		Assert.assertTrue(" Login popup did not appear!", loginPopup.isDisplayed());
		System.out.println("Login popup appeared successfully!");
		click(signupButton);


	}


	public void loginusingApplyButtonOnCouponPopup() throws TimeoutException {


		NegativeSignupPages sign = new NegativeSignupPages(driver);
		wait.until(ExpectedConditions.visibilityOf(signupPopup));
		Assert.assertTrue(" Signup popup did not appear!", signupPopup.isDisplayed());
		System.out.println("Signup  popup appeared successfully!");


		//		 sign.signUp();



	}
	//13
	public void clickOnViewCouponButton() {

		click(viewCouponButton);
	}

	public void unlockMoreCoupons() {

		List<String> couponDetails = new ArrayList<>();

		// Extract coupons from Unlock More Coupons
		if (unlockMoreCoupon.isDisplayed() && !unlockCouponCode.isEmpty() && !unlockCouponName.isEmpty()) {
			System.out.println("=== Unlock More Coupons Section is Visible ===");
			for (int i = 0; i < unlockCouponCode.size(); i++) {
				String code = unlockCouponCode.get(i).getText().trim();
				String fullText = unlockCouponName.get(i).getText().trim();

				String[] parts = fullText.split("\n");
				String name = parts.length > 0 ? parts[0].trim() : "";
				String description = parts.length > 1 ? parts[1].trim() : "";

				String formatted = "Code: " + code + " | Name: " + name + " | Description: " + description;
				couponDetails.add(formatted);

				System.out.println(formatted);
				System.out.println("---------------------------------");
			}
		} 
		// Extract from Available Coupons if Unlock More Coupons is not visible
		else if (unlockMoreCoupon.isDisplayed() && !unlockCouponCode.isEmpty() && !unlockCouponName.isEmpty()) {
			System.out.println("=== Unlock More Coupons Section is Visible ===");
			for (int i = 0; i < unlockCouponCode.size(); i++) {
				String code = unlockCouponCode.get(i).getText().trim();
				String fullText = unlockCouponName.get(i).getText().trim();

				String[] parts = fullText.split("\n");
				String name = parts.length > 0 ? parts[0].trim() : "";
				String description = parts.length > 1 ? parts[1].trim() : "";

				String formatted = "Code: " + code + " | Name: " + name + " | Description: " + description;
				couponDetails.add(formatted);

				System.out.println(formatted);
				System.out.println("---------------------------------");
			}
		} 
		else {
			System.out.println("No Coupons section is visible.");
		}

		// Compare coupons by formatted strings
		System.out.println("===== Coupon Comparison Results =====");
		for (int i = 0; i < couponDetails.size(); i++) {
			for (int j = i + 1; j < couponDetails.size(); j++) {
				if (couponDetails.get(i).equals(couponDetails.get(j))) {
					System.out.println("Coupon " + (i + 1) + " and Coupon " + (j + 1) + " are SAME.");
				} else {
					System.out.println("Coupon " + (i + 1) + " and Coupon " + (j + 1) + " are DIFFERENT.");
				}
			}
		}

	}

	//12	

	public void availableCoupon() {

		List<String> couponDetails = new ArrayList<>();

		// Extract coupons from Unlock More Coupons
		if (availableCoupon.isDisplayed() && !availableCouponCode.isEmpty() && !availableCouponName.isEmpty()) {
			System.out.println("=== Available Coupons Section is Visible ===");
			for (int i = 0; i < availableCouponCode.size(); i++) {
				String code = availableCouponCode.get(i).getText().trim();
				String fullText = availableCouponName.get(i).getText().trim();
				String[] parts = fullText.split("\n");
				String name = parts.length > 0 ? parts[0].trim() : "";
				String description = parts.length > 1 ? parts[1].trim() : "";

				String formatted = "Code: " + code + " | Name: " + name + " | Description: " + description;
				couponDetails.add(formatted);

				System.out.println(formatted);
				System.out.println("---------------------------------");
			}
		} 
		// Extract from Available Coupons if Unlock More Coupons is not visible
		else if (availableCoupon.isDisplayed() && !availableCouponCode.isEmpty() && !availableCouponName.isEmpty()) {
			System.out.println("=== Available Coupons Section is Visible ===");
			for (int i = 0; i < availableCouponCode.size(); i++) {
				String code = availableCouponCode.get(i).getText().trim();
				String fullText = availableCouponName.get(i).getText().trim();

				String[] parts = fullText.split("\n");
				String name = parts.length > 0 ? parts[0].trim() : "";
				String description = parts.length > 1 ? parts[1].trim() : "";

				String formatted = "Code: " + code + " | Name: " + name + " | Description: " + description;
				couponDetails.add(formatted);

				System.out.println(formatted);
				System.out.println("---------------------------------");
			}
		} 
		else {
			System.out.println("No Coupons section is visible.");
		}

		System.out.println("===== Coupon Comparison Results =====");

		for (int i = 0; i < couponDetails.size(); i++) {
			for (int j = 0; j < couponDetails.size(); j++) {
				if (i != j) { // avoid comparing with itself
					if (couponDetails.get(i).equals(couponDetails.get(j))) {
						System.out.println("Coupon " + (i + 1) + " and Coupon " + (j + 1) + " are SAME.");
					} else {
						System.out.println("Coupon " + (i + 1) + " and Coupon " + (j + 1) + " are DIFFERENT.");
					}
				}
			}
		}
	}


	//11

	public void specialCouponCodeWithfixedAmount() throws TimeoutException {
		wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

		// Step 2: If coupon locked, keep increasing qty until unlocked
		if (isCouponLocked()) {
			System.out.println("🔒 Coupon locked — unlocking...");

			// Close popup
			wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();

			// Loop until unlocked
			while (true) {
				increaseProductQuantity();
				System.out.println("🛒 Increased qty — rechecking...");

				wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

				if (!isCouponLocked()) {
					System.out.println("🔓 Coupon unlocked!");
					wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();
					type(couponCodeTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalFixed"));
					break;
				}
			}
		}
	}

	public void fixedAmountAppliedSuccessfullForSpecialCoupon() {

		Common.waitForElement(5);

		String couponSectionCouponAmount = getCleanAmount(appliedCouponAmountOnCouponsSection);
		System.out.println("Coupon Amount on Coupon Section   : " + couponSectionCouponAmount);

		String checkoutAmount = getCleanAmount(appliedCouponAmountOnCheckoutPage);
		System.out.println("Coupon Amount on Checkout Page   : " + checkoutAmount);

		// Check if amounts match and print accordingly
		if (couponSectionCouponAmount.equals(checkoutAmount)) {
			System.out.println("✅ Coupon amounts match: " + couponSectionCouponAmount);
		} else {
			System.out.println("❌ Coupon amounts do NOT match!");
		}

	}

	//	public void specialCouponCodeWithPercentageAmount() {
	//		click(viewCouponButton);	
	//	     type(couponCodeTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("SpecialCouponCodeForPercentage"));
	//	     }

	public void PercentageAmountAppliedSuccessfullForSpecialCoupon() {

		Common.waitForElement(5);
		Actions actions = new Actions(driver);

		// Step 1: Navigate to category and sort low-to-high
		actions.moveToElement(shopMenu);
		actions.moveToElement(category).click().build().perform();
		actions.moveToElement(sortBy).click().build().perform();
		actions.moveToElement(sortByPriceLowtoHigh).click().build().perform();

		// Step 2: Pick random product
		List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_list_cards_list ']"));
		Collections.shuffle(clickRandomProduct);

		if (!clickRandomProduct.isEmpty()) {
			WebElement randomProduct = clickRandomProduct.get(0);
			actions.moveToElement(randomProduct).click().build().perform();
			actions.moveToElement(buyNowbutton).click().build().perform();
			wait.until(ExpectedConditions.visibilityOf(viewCoupon)).click();
			type(couponPopupTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalPercentage"));
			clickApplyButtonPop();
		}

		// Step 3: Handle minimum purchase validation
		try {
			WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));

			// Check if minimum purchase message appears
			if (shortWait.until(ExpectedConditions.or(
					ExpectedConditions.textToBePresentInElement(minimumPurchase, "Minimum"),
					ExpectedConditions.visibilityOf(minimumPurchase)
					))) {
				String errorText = minimumPurchase.getText();
				System.out.println("❌ Minimum Purchase Validation Found: " + errorText);

				// Close popup safely
				try {
					click(closePopUp);
					System.out.println("Popup closed successfully after minimum purchase validation");
				} catch (Exception e) {
					System.out.println("❌ Popup close failed, using Refresh as fallback");
					driver.navigate().refresh();
				}

				Common.waitForElement(2);

				// Only in this case → Add high-priced product
				actions.moveToElement(shopMenu).perform();
				actions.moveToElement(category).click().perform();
				actions.moveToElement(sortBy).click().perform();
				actions.moveToElement(sortByPriceHightoLow).click().perform();

				List<WebElement> clickRandomProduct01 = driver.findElements(
						By.xpath("//div[@class='product_list_cards_list ']"));
				Collections.shuffle(clickRandomProduct01);

				if (!clickRandomProduct01.isEmpty()) {
					WebElement randomProduct01 = clickRandomProduct01.get(0);
					actions.moveToElement(randomProduct01).click().perform();
					actions.moveToElement(buyNowbutton).click().perform();
				}
			}
		} catch (Exception e) {
			System.out.println("⚠️ Exception while checking minimum purchase: " + e.getMessage());
		}

		// Step 4: Wait for discounted MRP and coupon amount
		waitUntilTextPresent(discountedMrpLine);
		waitUntilTextPresent(couponAmountLine);

		double discountedMrp = extractAmountSafe(discountedMrpLine.getText());
		double couponAmount = extractAmountSafe(couponAmountLine.getText());

		// Step 5: Get coupon percentage from popup
		click(viewCoupon);
		wait.until(ExpectedConditions.visibilityOfAllElements(couponPercentageTexts));

		String percentageText = "";
		if (!couponPercentageTexts.isEmpty()) {
			percentageText = couponPercentageTexts.get(0).getText().trim();
		}

		if (percentageText.isEmpty()) {
			throw new IllegalArgumentException("Percentage text is empty or not found in coupon popup");
		}

		int percentage = extractPercentage(percentageText);

		click(closePopUp);

		// Step 6: Calculate expected discount
		double expectedDiscount = Math.round(discountedMrp * percentage / 100.0);

		System.out.println("Discounted MRP: ₹" + discountedMrp);
		System.out.println("Coupon Percentage: " + percentage + "%");
		System.out.println("Expected Discount: ₹" + expectedDiscount);
		System.out.println("Actual Discount Applied: ₹" + couponAmount);

		// Step 7: Assert
		Assert.assertEquals(expectedDiscount, couponAmount, 1.0);
	}
	public void NormalCouponCodeWithfixedAmount() throws TimeoutException {
		wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();

		type(couponCodeTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalFixed"));

		// Step 2: If coupon locked, keep increasing qty until unlocked
		if (isCouponLocked()) {
			System.out.println("🔒 Coupon locked — unlocking...");

			// Close popup
			wait.until(ExpectedConditions.elementToBeClickable(closePopUp)).click();

			// Loop until unlocked
			while (true) {
				increaseProductQuantity();
				System.out.println("🛒 Increased qty — rechecking...");



				if (!isCouponLocked()) {
					System.out.println("🔓 Coupon unlocked!");
					wait.until(ExpectedConditions.elementToBeClickable(viewCoupon)).click();
					type(couponCodeTextBox, FileReaderManager.getInstance().getJsonReader().getValueFromJson("NormalFixed"));

					break;
				}
			}
		}
	}


	public void fixedAmountAppliedSuccessfullForNormalCoupon() {


		Common.waitForElement(5);

		String couponSectionCouponAmount = getCleanAmount(appliedCouponAmountOnCouponsSection);
		System.out.println("Coupon Amount on Coupon Section   : " + couponSectionCouponAmount);

		String checkoutAmount = getCleanAmount(appliedCouponAmountOnCheckoutPage);
		System.out.println("Coupon Amount on Checkout Page   : " + checkoutAmount);

		// Check if amounts match and print accordingly
		if (couponSectionCouponAmount.equals(checkoutAmount)) {
			System.out.println("✅ Coupon amounts match: " + couponSectionCouponAmount);
		} else {
			System.out.println("❌ Coupon amounts do NOT match!");
		}



	}

	public void afterSignUpFirstBuy() throws TimeoutException {
		CheckOutNavigation();
		 List<WebElement> couponCheck = driver.findElements(
		            By.xpath("//div[@class='coupon__code__title' and text()='FIRSTBUY200']")
		    );

		    if (!couponCheck.isEmpty()) {
		        System.out.println(RED + "🚫 FIRSTBUY200 Coupon Not Present → Subscription Failed ❌" + RESET);
		    } else {
		        System.out.println(PURPLE + "🎉 FIRSTBUY200 Coupon Present → Subscription Successful ✔" + RESET);
		    }

		    bagIcon.click();

		// Step 1: Click to view coupon
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		click(searchBox);
		searchBox.sendKeys("FIRSTBUY200");

		Common.waitForElement(3);  // Wait to ensure the coupon is applied
		Common.waitForElement(2);
		wait.until(ExpectedConditions.elementToBeClickable(applyBtn));
		click(applyBtn);	}

	public void verifyAppliedMessageForFirstBuy(String expectedText) {

		try {
			// Wait for the element containing the coupon message to be visible
			By messageLocator = By.xpath("//div[@class='acc_details_wrap']"); // Adjust the locator based on the actual page structure

			// Wait until the element is visible
			WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));

			// Get the text from the element
			String messageText = messageElement.getText().trim();

			// Print the coupon applied message
			System.out.println("Captured coupon applied message: " + messageText);


		} catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exception if any error occurs
			throw e; // Re-throw the exception to ensure the test fails
		}
	}



	//20


	public void signupAllCoupon() throws TimeoutException {
//		LoginPage login = new LoginPage(driver);
//		login.userLogin();  // Assuming userLogin handles login 
		NegativeSignupPages sigu = new NegativeSignupPages(driver);
		sigu.signUp();
		
		


	}

	// Subscribe to the newsletter and verify the coupon message
	// ANSI color codes for console output
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	public static final String BLUE = "\u001B[34m";
	public static final String PURPLE = "\u001B[35m";

	public void subscribeForNewsletter() {
	    // Step 3: Apply the newsletter coupon and verify the snackbar message
	    CheckOutNavigation();

	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys("NEWSLETTER10");
	    click(applyBtn);

	    WebElement snackbarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']")));
	    String snackbarText = snackbarElement.getText().trim();

	    String expectedText = "Subscribe to our newsletter to use this coupon.";

	    System.out.println(YELLOW + "Captured Snackbar Text: " + snackbarText + RESET);
	    System.out.println(BLUE + "Expected Text: '" + expectedText + "'" + RESET);
	    System.out.println(PURPLE + "Actual Text: '" + snackbarText + "'" + RESET);

	    Assert.assertTrue(RED + "❌ The expected text was not found in the snackbar message. Actual: " + snackbarText + RESET,
	            snackbarText.trim().equalsIgnoreCase(expectedText.trim()));
	    System.out.println(GREEN + "✅ Coupon message is correct: " + snackbarText + RESET);

	    // Step 1: Enter random email in the subscription field
	    Common.waitForElement(2);
	    click(profile);
	    click(couponsideMenu);
	    List<WebElement> couponCheck = driver.findElements(
	            By.xpath("//div[@class='coupon__code__title' and text()='NEWSLETTER10']"));

	    if (couponCheck.isEmpty()) {
	        System.out.println(RED + "🚫 Newsletter coupon not present → need subscribe ..." + RESET);
	    }
	    
	    
	}
	
	  

	public void verifyEmailAndNewsletterSubscription() {
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    Common.waitForElement(2);
	    click(profile);
	    Common.waitForElement(5);
	    click(accountsSideMenuButton);
	    Common.waitForElement(5);

	    WebElement emailInput = driver.findElement(By.id("cls_emailID"));
	    WebElement verifyBtn = driver.findElement(By.id("email_verification"));

	    int maxRetries = 5;
	    String validEmail = "";

	    for (int i = 0; i < maxRetries; i++) {
	        emailInput.clear();
	        String email = generateRandomEmail1();
	        System.out.println(YELLOW + "Trying Email: " + email + RESET);
	        emailInput.sendKeys(email);
	        verifyBtn.click();
	        Common.waitForElement(2);

	        List<WebElement> errorList = driver.findElements(By.id("errorMsg"));
	        if (!errorList.isEmpty() && errorList.get(0).isDisplayed()) {
	            System.out.println(RED + "❌ Error shown. Retrying..." + RESET);
	            continue;
	        } else {
	            validEmail = email;
	            System.out.println(GREEN + "✅ No error. Proceeding to OTP for: " + validEmail + RESET);
	            break;
	        }
	    }

	    if (validEmail.isEmpty()) {
	        throw new RuntimeException(RED + "❌ Failed to generate a valid email after " + maxRetries + " attempts!" + RESET);
	    }

	    System.out.println(BLUE + "🔐 Entering OTP for: " + validEmail + RESET);
	    type(enterotp, FileReaderManager.getInstance().getJsonReader().getValueFromJson("OTP"));
	    click(verifyOTPButton);
	    System.out.println(GREEN + "🎉 SUCCESS: Email Verified → " + validEmail + RESET);

	    WebElement letterInput = driver.findElement(By.id("subscribeletter"));
	    letterInput.clear();
	    letterInput.sendKeys(validEmail);
	    System.out.println(YELLOW + "📨 Using Verified Email for Newsletter: " + validEmail + RESET);

	    WebElement subscribeButton =
	            wait.until(ExpectedConditions.elementToBeClickable(By.id("subscribeletterbtn")));
	    subscribeButton.click();
	    System.out.println(BLUE + "📬 Subscribe Button Clicked" + RESET);

	    click(profile);
	    Common.waitForElement(2);
	    click(couponsideMenu);
	    Common.waitForElement(3);

	    List<WebElement> couponCheck = driver.findElements(
	            By.xpath("//div[@class='coupon__code__title' and text()='NEWSLETTER10']")
	    );

	    if (!couponCheck.isEmpty()) {
	        System.out.println(RED + "🚫 NEWSLETTER10 Coupon Not Present → Subscription Failed ❌" + RESET);
	    } else {
	        System.out.println(PURPLE + "🎉 NEWSLETTER10 Coupon Present → Subscription Successful ✔" + RESET);
	    }

	    bagIcon.click();
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys("NEWSLETTER10");
	    click(applyBtn);

	    By messageLocator = By.xpath("//div[@class='acc_details_wrap']");
	    WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
	    String messageText = messageElement.getText().trim();
	    System.out.println(YELLOW + "Captured coupon applied message: " + messageText + RESET);
	}
              


	    
	  public void subscribeForfeedback() {
		  
		  
		  CheckOutNavigation();
	 wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys("THANKYOU100");
	    click(applyBtn);

	    WebElement snackbarElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	            By.xpath("//div[@class='snackbar-container  snackbar-pos top-right']")));
	    String snackbarText = snackbarElement.getText().trim();

	    String expectedText = "Share your feedback to use this coupon.";

	    System.out.println(YELLOW + "Captured Snackbar Text: " + snackbarText + RESET);
	    System.out.println(BLUE + "Expected Text: '" + expectedText + "'" + RESET);
	    System.out.println(PURPLE + "Actual Text: '" + snackbarText + "'" + RESET);

	    Assert.assertTrue(RED + "❌ The expected text was not found in the snackbar message. Actual: " + snackbarText + RESET,
	            snackbarText.trim().equalsIgnoreCase(expectedText.trim()));
	    System.out.println(GREEN + "✅ Coupon message is correct: " + snackbarText + RESET);

	    // Step 1: Enter random email in the subscription field
	    Common.waitForElement(2);
	    click(profile);
	    click(couponsideMenu);
	    List<WebElement> couponCheck = driver.findElements(
	            By.xpath("//div[@class='coupon__code__title' and text()='THANKYOU100']"));

	    if (couponCheck.isEmpty()) {
	        System.out.println(RED + "🚫 feedback  coupon not present → need subscribe ..." + RESET);
	    }
	}
	  public void verifyEmailAndNewsforfeedback() {
		
	  
	   
		Common.waitForElement(2);
		click(profile);
		Common.waitForElement(5);
		click(accountsSideMenuButton);
		Common.waitForElement(5);

		WebElement emailInput = driver.findElement(By.id("cls_emailID"));
		WebElement verifyBtn = driver.findElement(By.id("email_verification"));

		int maxRetries = 20;
		String validEmail = "";

		for (int i = 0; i < maxRetries; i++) {
		    emailInput.clear();
		    String email = generateRandomEmail1();
		    System.out.println(YELLOW + "Trying Email: " + email + RESET);
		    emailInput.sendKeys(email);
		    verifyBtn.click();
		    Common.waitForElement(2);

		    List<WebElement> errorList = driver.findElements(By.id("errorMsg"));
		    if (!errorList.isEmpty() && errorList.get(0).isDisplayed()) {
		        System.out.println(RED + "❌ Error shown. Retrying..." + RESET);
		        continue;
		    } else {
		        validEmail = email;  // store the verified email
		        System.out.println(GREEN + "✅ No error. Proceeding to OTP for: " + validEmail + RESET);
		        break;
		    }
		}

		if (validEmail.isEmpty()) {
		    throw new RuntimeException(RED + "❌ Failed to generate a valid email after " + maxRetries + " attempts!" + RESET);
		}

		System.out.println(BLUE + "🔐 Entering OTP for: " + validEmail + RESET);
		type(enterotp, FileReaderManager.getInstance().getJsonReader().getValueFromJson("OTP"));
		click(verifyOTPButton);
		System.out.println(GREEN + "🎉 SUCCESS: Email Verified → " + validEmail + RESET);

		// ----------------------------------------------------
		// 📝 Feedback form submission using verified email
		// ----------------------------------------------------
		Actions action = new Actions(driver);
		click(homeMenu);
		Common.waitForElement(2);
		click(feedBack);
		click(feedletsDoIT);
		Common.waitForElement(1);

		// Use the verified email in feedback
		WebElement feedbackEmailInput = driver.findElement(By.id("feedback_email")); // adjust the locator if needed
		feedbackEmailInput.sendKeys(validEmail);
		System.out.println(YELLOW + "📧 Feedback submitted with email: " + validEmail + RESET);

		action.moveToElement(continueFeed).click().build().perform();
		clickUsingJavaScript(feedCollectionYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedSearchingYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStruggle2);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStarButton);
		Common.waitForElement(2);
		clickUsingJavaScript(feedFinalContinue);
		Common.waitForElement(2);
		clickUsingJavaScript(feedBack);

	    click(profile);
	    Common.waitForElement(2);
	    click(couponsideMenu);
	    Common.waitForElement(3);

	    List<WebElement> couponCheck = driver.findElements(
	            By.xpath("//div[@class='coupon__code__title' and text()='THANKYOU100']")
	    );

	    if (!couponCheck.isEmpty()) {
	        System.out.println(RED + "🚫 feedback  Coupon Not Present → Subscription Failed ❌" + RESET);
	    } else {
	        System.out.println(PURPLE + "🎉 feedback Coupon Present → Subscription Successful ✔" + RESET);
	    }

	    bagIcon.click();
	    wait.until(ExpectedConditions.elementToBeClickable(searchBox));
	    click(searchBox);
	    searchBox.sendKeys("THANKYOU100");
	    click(applyBtn);

	    By messageLocator = By.xpath("//div[@class='acc_details_wrap']");
	    WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(messageLocator));
	    String messageText = messageElement.getText().trim();
	    System.out.println(YELLOW + "Captured coupon applied message: " + messageText + RESET);
	}

	// Generate a random email address with a fixed prefix and 4 random digits
	private static String generateRandomEmail1() {
//		String prefix = "ranjith";
//		String digits = "0123456789";
		String prefix = "hhh";
		String digits = "236";

		Random rnd = new Random();
		StringBuilder email = new StringBuilder(prefix);

		// Generate 4 random digits
		for (int i = 0; i < 4; i++) {
			email.append(digits.charAt(rnd.nextInt(digits.length())));
		}

		email.append("@gmail.com");  // Use a valid domain
		lastGeneratedEmail = email.toString();
		return lastGeneratedEmail;
	}

//	private static String generateRandomEmail1() {
//	    String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
//	    Random rnd = new Random();
//	    StringBuilder email = new StringBuilder();
//
//	    // Generate random 8-character local part
//	    for (int i = 0; i < 8; i++) {
//	        email.append(chars.charAt(rnd.nextInt(chars.length())));
//	    }
//
//	    email.append("@gmail.com");
//	    lastGeneratedEmail = email.toString();
//	    return lastGeneratedEmail;
//	}

	// Retrieve the last generated email
	public static String getLastGeneratedEmail1() {
		return lastGeneratedEmail;
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
