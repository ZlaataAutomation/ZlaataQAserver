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
	
   	@FindBy(xpath = "(//div[contains(@class,'popup_containers_cls_btn')])[5]")
	protected WebElement closeBtn;
   	
   	@FindBy(xpath = "//input[@id='search_input']")
	protected WebElement userSearchBox;
    
    
    
	
	
	
	
	
	
	
	
	
	
	
}
