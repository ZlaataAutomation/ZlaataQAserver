package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminPanelInfluencerObjRepo extends BasePage {

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
   	
  //Influencer
   	
   	@FindBy(xpath = "//a[normalize-space()='Product Listing Name']")
	protected WebElement productListingMenu;
   	
   	@FindBy(xpath = "(//input[@role='searchbox'])[1]") 
    protected WebElement productSearchBox;
   	
   	@FindBy(xpath = "//i[@class='las la-edit']")
    protected WebElement editProductButton;
   	
   	@FindBy(xpath = "//a[normalize-space()='Item']")
    protected WebElement itemProductButton;
   	
   	@FindBy(xpath = "(//input[@name='filters[0][listing_name]'])[1]")
	protected WebElement productListingBoxText;
    
   	@FindBy(xpath = "//input[@name='filters[0][is_influencer]' and @value='1']")
	protected WebElement influencerYes;
   	
    
    @FindBy(xpath = "(//button[contains(@type,'button')][normalize-space()='Choose File'])[1]")
    protected WebElement uploadImage;
    
    @FindBy(xpath = "//u[normalize-space()='Influencer Image uploads']")
    protected WebElement InfluImageUpload;
    
    @FindBy(xpath = "//button[@id='confirmUpload']")
    protected WebElement confirmUpload;
  
    @FindBy(xpath="//a[@role='tab'][normalize-space()='Product']") 
    protected WebElement productButton;

    @FindBy(xpath="//input[@name='name']") 
    protected WebElement productNameTextbox;

    @FindBy(xpath="//input[@id='menuSearch']")
    protected WebElement searchProductCollectionMenu;
    
    @FindBy(xpath="//a[normalize-space()='Product Collections']")
    protected WebElement clickProductCollection;
    
    @FindBy(xpath="//label[contains(@for,'listingImagesDesktopInput')]")
    protected WebElement uploadInput;
  
    
    @FindBy(xpath="//a[normalize-space()='Status']")
    protected WebElement clickStatus;

    @FindBy(xpath = "//ul[@id='select2-filter_status-results']//li[normalize-space(.)='Active']")
    protected WebElement statusActiveOption;

    @FindBy(xpath="//a[contains(text(),'Collections')]")
    protected WebElement collectionButton;

    @FindBy(xpath="//input[@role='searchbox']")
    protected WebElement searchTextBox;

    @FindBy(xpath="(//i[@class='las la-edit'])[1]")
    protected WebElement editCollectionButton;

    @FindBy(xpath="(//input[@role='searchbox'])[1]")
    protected WebElement addProductTextbox;

    @FindBy(xpath="//span[@data-value='save_and_back']")
    protected WebElement saveButton;
    
    @FindBy(xpath = "//a[normalize-space()='Category Name']")
    protected WebElement categoriesNameButton;
    
    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[1]")
    protected WebElement categoryType;
    
    @FindBy(xpath = "//span[@class='ladda-label']")
    protected WebElement addProductSort;
    
    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[2]")
    protected WebElement categoryId;
    
    @FindBy(xpath = "//input[@role='searchbox']")
    protected WebElement categorySearchTextBox;
    
    
    @FindBy(xpath = "//input[@id='menuSearch']")  
    protected WebElement searchProductSortMenu;

    @FindBy(xpath = "//div[@class='form-group col-md-3 required']//input[@name='filters[0][sku]']")  
    protected WebElement copySkuTextbox;
    
    @FindBy(xpath = "//a[normalize-space()='Product Sorts']")  
    protected WebElement clickProductSort;
    
    
    @FindBy(xpath = "//i[@class='fa fa-refresh']") 
    protected WebElement clearCatchButton;
    
    @FindBy(xpath = "//input[@id='search_input']") 
    protected WebElement searchBox;
	
    @FindBy(xpath = "//a[normalize-space()='Styled by']") 
    protected WebElement clickInfluencer;
  
    
    
    
	
	
}
