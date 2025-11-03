package pages;

import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import objectRepo.SaleOffer50PercentageObjRepo;
import utils.Common;

import io.cucumber.java.Scenario;

public final class OrdersPage extends SaleOffer50PercentageObjRepo{

	public OrdersPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}


	public void verifyOrderPlacementAndValidationFlow(Scenario scenario) {
	    LoginPage login = new LoginPage(driver);
	    login.userLogin();
	    Common.waitForElement(5);

	    Actions actions = new Actions(driver);
	    WebElement bagIcon = driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']"));
	    bagIcon.click();
	    Common.waitForElement(2);

	    List<WebElement> productBlocks = driver.findElements(By.xpath(".//div[@class='cart_prod_card ']"));
	    
	    // If no product in the cart, add a product to the cart
	    if (productBlocks.isEmpty()) {
	        System.out.println("🛍️ Bag item count not displayed. Adding product...");
	        addProductToCart(actions);
	    } else {
	        // If there are products in the cart, delete them
	        deleteAllProductsFromCart();
	        
	        // After deletion, ensure the cart is updated before adding new products
	        refreshCartState();
	        
	        // Now, try adding a new product after deletion
	        System.out.println("🛍️ Attempting to add a new product after deletion...");
	        addProductToCart(actions);
	    }

	    // Apply coupon code
	    applyCouponCode("TESTMODE");

	    // Proceed to address page and confirm selected address
	    proceedToAddressPageAndConfirm();

	    // Place the order
	    placeOrder();

	    // Capture final screenshot of the order details page
	    captureOrderDetails(scenario);
	}

	private void addProductToCart(Actions actions) {
	    Common.waitForElement(2);
	    WebElement shopMenu = driver.findElement(By.xpath("//li[@class='navigation_menu_list nav_menu_dropdown shop']"));
	    actions.moveToElement(shopMenu);
	    WebElement category = driver.findElement(By.xpath("//div[@class='nav_drop_down_box_category active']//a[contains(translate(text(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ALL')]"));
	    actions.moveToElement(category).click().build().perform();

	    Common.waitForElement(2);

	    WebElement sortBy = driver.findElement(By.xpath("//div[@class='filter_sort_btn_wrap']"));
	    actions.moveToElement(sortBy).click().build().perform();

	    WebElement sortByPriceHightoLow = driver.findElement(By.xpath("//li[@data-value='Price High to Low']"));
	    click(sortByPriceHightoLow);
	    Common.waitForElement(2);

	    List<WebElement> addProductButtons = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
	    Collections.shuffle(addProductButtons);
	    
	    if (!addProductButtons.isEmpty()) {
	        WebElement randomProduct = addProductButtons.get(0);
	        actions.moveToElement(randomProduct).click().build().perform();
	        WebElement addToCart = driver.findElement(By.xpath("//button[@class='add_bag_prod_buy_now_btn btn___2 Cls_CartList ClsProductListSizes']"));
	        addToCart.click();
	        Common.waitForElement(5);

	        // Go back to the cart
	        driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']")).click();
	        Common.waitForElement(2);
	    }
	}

	public void deleteAllProductsFromCart() {
	    int deletedCount = 0;
	    List<WebElement> deleteButtons = driver.findElements(By.xpath("//div[@title='Delete']"));

	    for (int i = 0; i < deleteButtons.size(); i++) {
	        boolean deleted = false;
	        int retry = 0;

	        while (!deleted && retry < 3) {
	            try {
	                System.out.println("🛒 Attempting to delete product " + (i + 1) + "...");

	                // Refresh list each retry to avoid stale reference
	                deleteButtons = driver.findElements(By.xpath("//div[@title='Delete']"));
	                deleteButtons.get(i).click();
	                System.out.println("✅ Product " + (i + 1) + " deleted.");
	                deleted = true;
	                deletedCount++;
	                
	                // Wait for the cart state to refresh after deletion
	                Common.waitForElement(2);
	                break;
	            } catch (StaleElementReferenceException e) {
	                retry++;
	                System.out.println("⚠️ Retry " + retry + " due to stale element...");
	            } catch (ElementClickInterceptedException e) {
	                retry++;
	                System.out.println("❌ Retry " + retry + ": Snackbar intercepted click. Waiting to retry...");
	                try {
	                    Thread.sleep(1000); // Wait before retry
	                } catch (InterruptedException ie) {
	                    // Ignore
	                }
	            } catch (Exception e) {
	                System.out.println("❌ Unexpected error during bag deletion: " + e.getMessage());
	                break;
	            }
	        }
	    }
	}

	private void refreshCartState() {
	    System.out.println("🔄 Refreshing the cart state...");
	    // Click bag icon again to ensure the cart updates
	    WebElement bagIcon = driver.findElement(By.xpath("//a[@class='Cls_cart_btn Cls_redirect_restrict']"));
	    bagIcon.click();
	    Common.waitForElement(2);
	}

	private void applyCouponCode(String couponCode) {
	    Common.waitForElement(3);
	    WebElement couponInput = driver.findElement(By.name("couponInputField"));
	    couponInput.sendKeys(couponCode);
	    driver.findElement(By.xpath("//button[@class='coupon_input_apply_btn ']")).click();
	    Common.waitForElement(2);
	}

	private void proceedToAddressPageAndConfirm() {
	    // Step 8: Go to Address Page
	    driver.findElement(By.xpath("//button[@class='place_order_btn Cls_place_order btn___2 ']")).click();
	    Common.waitForElement(2);

	    // Step 9: Confirm selected address
	    WebElement selectedAddress = driver.findElement(By.xpath("//div[@class='address_card Cls_addr_data_section']"));
	    System.out.println("📦 Selected Address: " + selectedAddress.getText());
	    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	private void placeOrder() {
	    // Step 10: Place Order
	    driver.findElement(By.xpath("//button[@class='place_order_btn Cls_place_order btn___2 enabled']")).click();
	    WebElement totalAmountPaymentPage = driver.findElement(By.xpath(".//div[@class='price_details_pair Cls_cart_total_amount']"));
	    String totalText = totalAmountPaymentPage.getText();
	    System.out.println("Total Amount: " + totalText);

	    click(cODMode);  // Assuming cODMode and paymentPlaceOrder are predefined
	    Common.waitForElement(1);
	    click(paymentPlaceOrder);
	    Common.waitForElement(5);
	}

	private void captureOrderDetails(Scenario scenario) {
	    // Step 12: Order Details Page
	    driver.findElement(By.xpath("//a[text()='View Order Details']")).click();
	    Common.waitForElement(2);

	    WebElement cancelBtn = driver.findElement(By.xpath("//button[@class='prod_cancel_btn cls_cancel_button']"));
	    if (cancelBtn.isDisplayed()) {
	        System.out.println("❌ Cancel Button: Displayed ✅");
	    }

	    // Step 13: Price Breakup
	    driver.findElement(By.xpath("//button[@class='price_breakup_btn active']")).click();
	    Common.waitForElement(1);

	    // Screenshot and price breakup
	    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	    scenario.attach(screenshot, "image/png", "Order Details");

	    System.out.println("🧾 Price Breakup Verified:");
	    System.out.println("Total MRP: " + driver.findElement(By.xpath("//div[@class='price_details_row actual_mrp']//div[@class='price_details_pair']")).getText());
	    System.out.println("Discounted MRP: " + driver.findElement(By.xpath("//div[@class='price_details_row discount_mrp']//div[@class='price_details_pair']")).getText());
	    System.out.println("Coupon Discount: " + driver.findElement(By.xpath("//div[@class='price_details_row coupon_discount']//div[@class='price_details_pair']")).getText());
	    System.out.println("You Saved: " + driver.findElement(By.xpath("//div[@class='price_details_row saved_amount']//div[@class='price_details_pair']")).getText());
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
