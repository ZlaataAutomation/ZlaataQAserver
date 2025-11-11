package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminPanelCouponObjRepo extends BasePage {

	
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

    @FindBy(name = "email")
   	protected WebElement adminEmail;
   	
   	@FindBy(id = "password")
   	protected WebElement adminPassword;
   	
   	@FindBy(xpath = "//button[@type='submit']")
   	protected WebElement adminLogin;
   	
   	@FindBy(xpath = "//span[@class='ladda-label']")
	protected WebElement addCouponButton;
   	
   	@FindBy(xpath = "//input[@name='title']")
	protected WebElement titleBox;
   	
   	@FindBy(xpath = "//input[@id='coupon_code']")
	protected WebElement codeBox;
   	
   	@FindBy(xpath = "//input[@name='short_description']")
	protected WebElement shortDesriptionBox;
   	
   	@FindBy(xpath = "//input[@name='maximum_discount']")
	protected WebElement maxDiscountBox;

   	@FindBy(xpath = "//input[@name='maximum_usage_limit']")
	protected WebElement maxUsageLimitBox;

   	@FindBy(xpath = "//input[@name='percentage']")
	protected WebElement percentageBox;

   	@FindBy(xpath = "//input[@name='amount_start_from']")
	protected WebElement minimumPurchaseBox;
   	
   	@FindBy(xpath = "//label[normalize-space(text())='Is Always']/preceding-sibling::input[@type='checkbox']")
	protected WebElement isAlwaysBtn;

   	@FindBy(xpath = "//label[normalize-space(text())='Status']/preceding-sibling::input[@type='checkbox']")
	protected WebElement statusBtn;

   	@FindBy(xpath = "//span[@role='combobox' and contains(@aria-labelledby,'select2-coupon_type')]")
	protected WebElement couponTypeBtn;

   	@FindBy(xpath = "(//input[@role='searchbox'])[9]")
	protected WebElement couponTypeBox;

   	@FindBy(xpath = "//span[@data-value='save_and_back']")
	protected WebElement saveAndBackButton;
   	
	@FindBy(xpath = "(//a[normalize-space()='Title'])[1]")
	protected WebElement clickTitle;
   	
   	@FindBy(xpath = "//input[@role='searchbox']")
	protected WebElement adminTitleBox;
   	
   	@FindBy(xpath = "//div[@title='User Icon']")
	protected WebElement clickProfile;
   	
   	@FindBy(xpath = "//h2[normalize-space()='My Coupons']")
	protected WebElement myCoupon;
   	
   	@FindBy(xpath = "//a[@title='Cart Icon']")
	protected WebElement clickCartBtn;
    
   	@FindBy(xpath = "//button[normalize-space()='View Coupons']")
	protected WebElement viewCoupon;
   	
   	@FindBy(xpath = "(//input[@placeholder='Enter Coupon Code'])[2]")
	protected WebElement searchBox;
  
   	@FindBy(xpath = "(//button[@type='submit'][normalize-space()='apply'])[2]")
	protected WebElement applyBtn;

   	@FindBy(xpath = "(//input[@placeholder='Select a product'])[1]")
	protected WebElement productBox;
   	
   	@FindBy(xpath = "//i[@class='fa fa-refresh']") 
    protected WebElement clearCatchButton;
    
   	
    
}
