package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminProductSecondaryColorObjRepo extends BasePage{
	
	
	

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
	
	@FindBy(xpath = "//a[normalize-space()='Product Listing Name']")
	protected WebElement productListingMenu;
	
    @FindBy(xpath = "(//input[@role='searchbox'])[1]") 
    protected WebElement productSearchBox;
    
    @FindBy(xpath = "//i[@class='las la-edit']")
    protected WebElement editProductButton;
    
    @FindBy(xpath = "//a[normalize-space()='Item']")
    protected WebElement itemProductButton;
    
    @FindBy(xpath="(//input[@role='searchbox'])[2]")
    protected WebElement searchTextBox;
    
    @FindBy(xpath="//span[@data-value='save_and_back']")
    protected WebElement saveButton;
    
    @FindBy(xpath = "//i[@class='fa fa-refresh']") 
    protected WebElement clearCatchButton;
    
    @FindBy(xpath = "//input[@id='search_input']")
    protected WebElement userSearchBox;
    
    @FindBy(xpath = "//button[contains(@class,'add_bag_prod_buy_now_btn') and normalize-space()='Add to Bag']")
    protected WebElement addToCartBtn;
  
    
}
