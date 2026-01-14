package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class Calculation_MyOrder_ObjRepo extends BasePage {
	
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


    
    @FindBy(xpath = "(//a[normalize-space()='Home'])[1]")
    protected WebElement homeBtn;
    
    @FindBy(xpath = "//a[@href='/gift-card']")
    protected WebElement giftCardBanner;
    
    @FindBy(xpath = "(//button[normalize-space()='Next'])[1]")
    protected WebElement nextBtn;
    
    @FindBy(xpath = "//div[@data-amount-value='500']")
    protected WebElement choose500;
    
    @FindBy(xpath = "(//input[@id='gift__dob'])[1]")
    protected WebElement giftDOB;
    
    @FindBy(xpath = "(//button[normalize-space()='Add to Cart'])[1]")
    protected WebElement addToCartBtn;
    
    @FindBy(xpath = "//a[@title='Cart Icon']")
	protected WebElement clickCartBtn;
    
   	@FindBy(xpath = "//button[normalize-space()='View Coupons']")
	protected WebElement viewCoupon;
   	
   	@FindBy(xpath = "(//input[@placeholder='Enter Coupon Code'])[1]")
	protected WebElement searchBox;
   	
   	@FindBy(xpath = "(//button[@type='submit'][normalize-space()='apply'])[1]")
	protected WebElement applyBtn;
   	
   	@FindBy(xpath = "(//input[@id='checkout__model__gift'])[1]")
	protected WebElement clickGiftWrap;
   	
   	@FindBy(xpath = "//button[contains(@class,'place_order_btn') and normalize-space(text())='Place order']")
	protected WebElement placeOrderBtn;
   	
   	@FindBy(xpath = "//a[contains(@class,'view_details_btn') and (contains(text(),'View Order Details') or contains(text(),'View Orders'))]")
	protected WebElement viewOrderDetails;
	
   	@FindBy(xpath = "//h3[normalize-space()='price details']/following-sibling::div[contains(@class,'popup_containers_cls_btn')]")
	protected WebElement closeBtn;
   	
   	@FindBy(xpath = "//input[@id='search_input']")
	protected WebElement userSearchBox;
    
    @FindBy(xpath = "//i[@class='bi bi-gear']") 
    protected  WebElement generalSettingsMenu;
    
    @FindBy(xpath = "//i[@class='las la-edit']")
    protected  WebElement topSellingEdit;
    
    @FindBy(xpath = "//a[normalize-space()='Set Key']") 
    protected WebElement clickSetKey;
    
    @FindBy(xpath = "(//input[@role='searchbox'])[1]") 
    protected WebElement productSearchBox;
    
    @FindBy(xpath = "//i[@class='las la-edit']")
    protected WebElement editProductButton;
    
	
   	@FindBy(name = "email")
	protected WebElement adminEmail;
	
	@FindBy(id = "password")
	protected WebElement adminPassword;
	
	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement adminLogin;
	
	
	@FindBy(xpath = "//button[contains(@class,'place_order_btn') and normalize-space(text())='Continue']")
	protected WebElement continueBtn;
	
	@FindBy(xpath = "(//input[@id='net-banking'])[1]")
	protected WebElement selectNetBank;
	
	 @FindBy(xpath = "//a[normalize-space()='Order Id']")  
	 protected WebElement orderIdbtn;
	 
	 @FindBy(xpath = "(//input[@role='searchbox'])[1]")  
	 protected WebElement orderSearchBox;
	 
	 @FindBy(xpath = "//i[@class='las la-edit']")
	    protected WebElement editBtn;
	
	
	
	
	
	
	
}
