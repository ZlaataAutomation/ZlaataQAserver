package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class Admin_OrderObjRepo extends BasePage {
	
	public void waitFor(WebElement el) {
        new WebDriverWait(driver, Duration.ofSeconds(15))
            .until(ExpectedConditions.elementToBeClickable(el));
    }
    public void type(WebElement el, String value) {
        waitFor(el); el.clear(); el.sendKeys(value);
    }
    public void click(WebElement el) {
        waitFor(el); el.click();
    }


    @FindBy(xpath="//input[@id='menuSearch']")
    protected WebElement searchProductCollectionMenu;
    
    @FindBy(xpath="//a[normalize-space()='Admin Order']")
    protected WebElement clickProductCollection;
    
    
    @FindBy(xpath="//span[normalize-space(.)='Create Order']")
    protected WebElement createAdminOrder;
    
    @FindBy(xpath="//span[@id='select2-customerSelect-container' and normalize-space(.)='Select a customer']")
    protected WebElement selectCustomer;

    @FindBy(xpath="//input[contains(@class,'select2-search__field') and @role='searchbox']")
    protected WebElement customerBox;

    @FindBy(xpath="//button[@id='confirmAddressBtn' and normalize-space(.)='Confirm Selection']")
    protected WebElement confirmBtn;
  
    
    @FindBy(xpath="//select[@id='payment_type']")
    protected WebElement selectPaymentType;
    
    @FindBy(xpath="//button[@id='create-order-btn' and normalize-space(.)='Create Order']")
    protected WebElement createOrderBtn;
    
    @FindBy(xpath = "//a[@title='Cart Icon']")
	protected WebElement bagIcon;
	
    @FindBy(xpath = "//button[contains(@class,'place_order_btn') and normalize-space(text())='Place order']")
	protected WebElement placeOrderBtn;
  
    @FindBy(xpath = "//button[contains(@class,'btn-primary') and normalize-space(.)='Select Delivery Address']")
   	protected WebElement selectAddress;
    
    @FindBy(xpath = "//a[normalize-space()='Order Id']")  
	 protected WebElement orderIdbtn;
	 
	 @FindBy(xpath = "(//input[@role='searchbox'])[1]")  
	 protected WebElement orderSearchBox;
	 
	 @FindBy(xpath = "//i[@class='las la-edit']")
	    protected WebElement editBtn;
	 
	 @FindBy(xpath = "(//select[@class='form-control orderaccept courier-filed'])[1]")  
	 protected WebElement shipmentStatus;
	 
	 @FindBy(xpath = "(//select[@class='form-control courier-provider'])[1]")  
	 protected WebElement courierProvider;
	 
	 @FindBy(xpath="//span[@data-value='save_and_back']")
	    protected WebElement saveButton;
	 
	 @FindBy(xpath="//select[@class='form-control order-status']")
	    protected WebElement orderStatus;
	 
	 @FindBy(xpath="(//select[@class='form-control'])[1]")
	    protected WebElement paymentMode;
    
	 @FindBy(xpath="//ul[contains(@class,'nav-dropdown-items')]//a[.//span[contains(@class,'order-placed')]]")
	    protected WebElement ordersection;
	
}
