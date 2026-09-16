package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminFlashNotificationObjRepo extends BasePage{

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
   	
   	@FindBy(xpath = "(//input[@id='menuSearch'])[1]")
   	protected WebElement menuSearchBox;
   	
   	@FindBy(xpath="//a[normalize-space()='Flash Notifications']")
   	protected WebElement flashNotificationMenu;
   	
   	@FindBy(xpath="//span[@class='ladda-label']")
   	protected WebElement addFlashNotificationButton;
   	
   	@FindBy(xpath="//a[normalize-space()='Flash News']")
   	protected WebElement flashNewsButton;
   	
   	@FindBy(xpath="//input[@role='searchbox']")
   	protected WebElement flashNewsSearchBox;
   	
   	@FindBy(xpath="//a[normalize-space()='Status']")
   	protected WebElement statusButton;
   	
   	@FindBy(xpath="//li[@id='select2-filter_status-result-ochl-1']")
   	protected WebElement statusActiveOption;
   	
   	@FindBy(xpath="//li[@id='select2-filter_status-result-6fsu-0']")
   	protected WebElement statusInactiveOption;
   	
   	@FindBy(xpath="//a[normalize-space()='Brand Type']")
   	protected WebElement brandTypeButton;
   	
   	@FindBy(xpath="//li[@id='select2-filter_brandType-result-v55a-1']")
   	protected WebElement zlaataIndiaBrandType; 
   	 
   	@FindBy(xpath="//li[@id='select2-filter_brandType-result-vslj-2']")
   	protected WebElement bossladyBrandType;
   	
   	@FindBy(xpath="//li[@id='select2-filter_brandType-result-d571-0']")
   	protected WebElement landingpage;
   	
   	@FindBy(xpath="//a[@id='remove_filters_button']")
   	protected WebElement clearFilterButton;
   	
   	@FindBy(xpath="(//i[@class='las la-eye'])[1]")
   	protected WebElement viewFlashNptificationButton;
   	
   	@FindBy(xpath="//i[@class='las la-edit']")
   	protected WebElement editFlashNotificationButton;
   	
   	@FindBy(xpath="//i[@class='las la-trash']")
   	protected WebElement deleteFlashNotificationButton;
   	
   	//inside add flash notifcation page locators
   	
   	@FindBy(xpath="//input[@name='name']")
   	protected WebElement nameTextBox;
   	
   	@FindBy(xpath="//select[@name='brand_type']")
   	protected WebElement brandTypeDropdown;
   	
   	@FindBy(xpath = "//button[normalize-space()='Add Content']")
    protected WebElement addContentButton;
   	
   	@FindBy(xpath="//input[@name='link']")
   	protected WebElement linkTextBox;
   	
	@FindBy(xpath="//textarea[@name='styles']")
   	protected WebElement stylesTextArea;
   	
	@FindBy(xpath=" //input[@name='isactive']")
   	protected WebElement isActiveCheckbox;
   	
	@FindBy(xpath="//input[@id='from_date']")
   	protected WebElement fromDateTextBox;
   	
	@FindBy(xpath="//input[@id='end_date']")
   	protected WebElement endDateTextBox;

	@FindBy(xpath="//span[@data-value='save_and_back']")
   	protected WebElement saveButton;
   	
   	@FindBy(xpath="//a[@class='btn btn-default']")
   	protected WebElement backButton;
   	
   	@FindBy(xpath="//a[@class='d-print-none font-sm']")
   	protected WebElement backToListButton;  	
   	
   	@FindBy(xpath="(//img[@alt='zlaata_logo'])[1]")
   	protected WebElement zlaataIndiaLogo;
   	
   	@FindBy(xpath = "//input[@id='access_code']") 
   	protected WebElement accessCode;

   	@FindBy(xpath = "//button[normalize-space()='Submit']")       
   	protected WebElement submit;
   	
   	@FindBy(xpath = "(//div[@class='custom-control custom-switch'])[1]")
   	protected WebElement firstDisplayToggle;
   	
   	@FindBy(xpath = "(//button[normalize-space()='Delete'])[1]")
   	protected WebElement DeleteConfirmButton;

   	@FindBy(xpath = "(//button[@class='swal-button swal-button--cancel'])[1]")
   	protected WebElement CancelButton;
   	
   	@FindBy(xpath = "(//i[@class='fa fa-refresh'])[1]")
    protected WebElement cacheRefreshButton;

    @FindBy(xpath = "//div[@class='flash_sale_bar']")
    protected WebElement flashSaleBar;
    
    @FindBy(xpath = "//a[@href='/zlaata-india']//div[@class='landing_page_content']//span[@class='landing_page_link_btn'][normalize-space()='SHOP NOW']")
    protected WebElement zlaataIndiaShopNowButton;

    @FindBy(xpath = "//div[@class='flash_sale_bar']")
    protected WebElement zlaataIndiaFlashBar;
    
    
   	
}
