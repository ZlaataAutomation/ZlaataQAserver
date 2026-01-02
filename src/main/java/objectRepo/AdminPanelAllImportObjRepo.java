package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminPanelAllImportObjRepo extends BasePage {

	
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
	
//Import Categories
	
	 //Bulk categories
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
	
    @FindBy(xpath = "//button[normalize-space()='Import']")
    protected WebElement importButton;
    
    @FindBy(xpath = "//input[@id='importFile']")
    protected WebElement uploadExcelButton;
    
    @FindBy(xpath = "//input[@id='inventoryImportFile']")
    protected WebElement  uploadExcelButtonTrack;

    @FindBy(xpath = "//button[normalize-space()='Submit']")
    protected WebElement submitButton;
    
    @FindBy(xpath = "//i[@class='fa fa-refresh']") 
    protected WebElement clearCatchButton;
    
    @FindBy(xpath = "//input[@id='menuSearch']")  
    protected WebElement searchProductSortMenu;

    @FindBy(xpath = "//a[normalize-space()='Product Sorts']")  
    protected WebElement clickProductSort;

    @FindBy(xpath = "//a[normalize-space()='Category Name']")  
    protected WebElement categoryName;

    @FindBy(xpath = "//input[@id='text-filter-categoryName']")  
    protected WebElement categorySearchBox;
    
    @FindBy(xpath="//span[@data-value='save_and_back']")
    protected WebElement saveButton;
    
    @FindBy(xpath="//input[@role='searchbox']")
    protected WebElement searchTextBox;
	
  //Import Collection
    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[2]")
    protected WebElement menuButton;
    
    @FindBy(xpath = "(//input[@role='searchbox'])[3]")
    protected WebElement menuSearchBox;
    
    @FindBy(xpath="//a[normalize-space()='Status']")
    protected WebElement clickStatus;

    @FindBy(xpath = "//ul[@id='select2-filter_status-results']//li[normalize-space(.)='Active']")
    protected WebElement statusActiveOption;
    
    @FindBy(xpath="//a[contains(text(),'Collections')]")
    protected WebElement collectionButton;
    
    @FindBy(xpath = "//i[@class='las la-edit']") 
    protected WebElement editButton;
	
	//Import Product Style
    @FindBy(xpath = "//a[contains(text(),'Styles')]")  
    protected WebElement styleName;

    //Import All product
    @FindBy(xpath = "//a[normalize-space()='SKU']")
    protected WebElement clickSKU;
    
    @FindBy(xpath = "(//div[@class='col-sm-0 col-md-4 text-center'])[1]")
	 protected WebElement clickBlankSpace;
    
    @FindBy(xpath = "//input[@id='search_input']")
    protected WebElement userSearchBox;
 
    @FindBy(xpath = "//a[normalize-space()='Collection Name']")
    protected WebElement clickedCollection;
    
    @FindBy(xpath = "//input[@id='search_input']")
    protected WebElement searchBox;
    
    @FindBy(xpath = "//input[@id='text-filter-name']")
    protected WebElement collectionSearchBox;
    
    @FindBy(xpath = "//a[normalize-space()='Style Name']")
    protected WebElement clickedStyle;
    
    @FindBy(xpath = "//input[@id='text-filter-sku']")
    protected WebElement adminSearchBox;
  
    
   @FindBy(xpath = "//a[normalize-space()='Product Colour Name / SKU']")
   protected WebElement trackinventoriesSearchbardropdown;
   
  
  
 @FindBy(xpath = "//input[@role='searchbox']")
 protected WebElement trackinventoriesSearchbar;
	
}
