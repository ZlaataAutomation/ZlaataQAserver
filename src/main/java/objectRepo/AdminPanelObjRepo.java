package objectRepo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract  class AdminPanelObjRepo extends BasePage {
	
	@FindBy(name = "email")
	protected WebElement adminEmail;
	
	@FindBy(id = "password")
	protected WebElement adminPassword;
	
	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement adminLogin;
	
	@FindBy(id = "menuSearch")
	protected WebElement SearchAdmin;
	
	@FindBy(xpath = "//i[@class='la la-plus']")
	protected WebElement addHomePageBanner;
	
	@FindBy(name = "title")
	protected WebElement bannerTitle;	
	
	
	@FindBy(xpath = "//input[@class='file-limitation']")
	protected WebElement uploadImageInput;
	
	@FindBy(xpath = "//span[@data-value='save_and_back']")
	protected WebElement uploadButton;
	
	@FindBy(xpath = "(//input[@type='number'])[1]")
	protected WebElement sortBy;
	
	@FindBy(xpath = "(//i[@class='las la-check-circle btn-success'])[1]")
	protected WebElement sortBySave;
	
	
	@FindBy(xpath = "//li[@filter-name='banner_type']")
	protected WebElement homePageBannerDropDown;
	
	@FindBy(xpath = "//li[@class='select2-results__option select2-results__option--highlighted']")
	protected List <WebElement> selectHomePageValue;
	
	@FindBy(xpath = "//li[@filter-name='status']")
	protected WebElement status;
	
	@FindBy(xpath = "//li[@class='select2-results__option select2-results__option--highlighted']")
	protected List <WebElement> statusFilterSelect;
	
	
	@FindBy(xpath = "//tr[@class='odd']")
	protected List <WebElement> bannerRows;
	
	@FindBy(xpath = ".//label[@class='custom-control-label']")
	protected  WebElement disableButton;
	
	@FindBy(id = "remove_filters_button")
	protected  WebElement removeFilters;
	
	//Saroj
	
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
	
		@FindBy(xpath = "//a[normalize-space()='Product Listing Name']")
		protected WebElement productListingMenu;
		
		@FindBy(xpath = "//a[normalize-space()='Product Detail Name']")
		protected WebElement productDetailMenu;
		
	    @FindBy(xpath = "(//input[@role='searchbox'])[1]") 
	    protected WebElement productSearchBox;
	    
	    @FindBy(xpath = "//i[@class='las la-edit']")
	    protected WebElement editProductButton;
	    
	    @FindBy(xpath = "//a[normalize-space()='Item']")
	    protected WebElement itemProductButton;
	    
	    @FindBy(xpath = "(//input[@name='filters[0][sku]'])[1]") 
	    protected WebElement skuField;
	    
	    // ==== Top Selling ====
	    @FindBy(xpath = "//i[@class='bi bi-gear']") 
	    protected  WebElement generalSettingsMenu;
	    
	    @FindBy(xpath = "//i[@class='las la-edit']")
	    protected  WebElement topSellingEdit;
	    
	    @FindBy(xpath = "//textarea[@name='set_value']") 
	    protected WebElement topSellingSkuInput;
	    
	    @FindBy(xpath = "//span[@data-value='save_and_back']") 
	    protected WebElement saveTopSelling;
	    
	    @FindBy(xpath = "//a[normalize-space()='Set Key']") 
	    protected WebElement clickSetKey;
	    
	    
	    @FindBy(xpath = "//i[@class='fa fa-refresh']") 
	    protected WebElement clearCatchButton;
	    
	    //New Arrival
	    public void selectDropdownByVisibleText(WebElement dropdown, String text) {
	        new Select(dropdown).selectByVisibleText(text);
	    }
	    @FindBy(xpath = "(//input[@name='filters[0][listing_name]'])[1]")
		protected WebElement productListingBoxText;
	    
	    @FindBy(xpath = "(//label[text()='Stock Status']/following-sibling::input[@type='text'])[1]")
		protected WebElement stockStatusTextbox;
	    
	    @FindBy(xpath = "(//input[contains(@class,'stock-staus-value')])[1]")
	    protected WebElement firstProductStockStatus;
	    
	    @FindBy(xpath="//a[@role='tab'][normalize-space()='Product']") 
	    protected WebElement productButton;

	    @FindBy(xpath="//input[@name='name']") 
	    protected WebElement productNameTextbox;

	    @FindBy(xpath="//input[@id='menuSearch']")
	    protected WebElement searchProductCollectionMenu;
	    
	    @FindBy(xpath="//a[normalize-space()='Product Collections']")
	    protected WebElement clickProductCollection;
	    
	    @FindBy(xpath="//a[normalize-space()='Status']")
	    protected WebElement clickStatus;

	    @FindBy(xpath = "//li[contains(@class,'select2-results__option') and normalize-space()='Active']")
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
	  
	    
	
	 // Search Product Sort Menu
	    @FindBy(xpath="(//input[@name='filters[0][sku]'])[1]") 
	    protected WebElement skuTextbox;
	
	    @FindBy(xpath = "//input[@id='menuSearch']")  
	    protected WebElement searchProductSortMenu;

	    // Product Sort line to click after typing
	    @FindBy(xpath = "//a[normalize-space()='Product Sorts']")  
	    protected WebElement clickProductSort;

	    // Category Name to click
	    @FindBy(xpath = "//a[normalize-space()='Category Name']")  
	    protected WebElement categoryName;

	    // Category search box
	    @FindBy(xpath = "//input[@id='text-filter-categoryName']")  
	    protected WebElement categorySearchBox;

	    // Next Page arrow
	    @FindBy(xpath = "//a[normalize-space()='>']")  
	    protected WebElement nextPageArrow;

	    // Plus Button to add products
	    @FindBy(xpath = "//i[@class='las la-plus-circle']")  
	    protected WebElement plusButton;

	
	//Catagory
	    @FindBy(xpath = "//a[normalize-space()='Home page banner']") 
	    protected WebElement homePageBannerMenu;

	    @FindBy(xpath = "//input[@role='searchbox']") 
	    protected WebElement catagorysearchTextBox;

	    @FindBy(xpath = "//i[@class='las la-edit']") 
	    protected WebElement editButton;

	    @FindBy(xpath = "//input[@name='title']") 
	    protected WebElement bannerTitleTextBox;

	    @FindBy(xpath = "//input[@class='file-limitation']") 
	    protected WebElement desktopBannerUpload;
	    
	    
	    
	    
	    //Bull Product 
	    @FindBy(xpath = "//button[normalize-space()='Import']")
	    protected WebElement importButton;
	    
	    @FindBy(xpath = "//input[@id='importFile']")
	    protected WebElement uploadExcelButton;

	    @FindBy(xpath = "//button[normalize-space()='Submit']")
	    protected WebElement submitButton;

	    @FindBy(xpath = "//div[@class='noty_body']")
	    protected WebElement successMessage;
	    
	    @FindBy(xpath = "//a[normalize-space()='SKU']")
	    protected WebElement clickSKU;

	    @FindBy(xpath = "(//input[@role='searchbox'])[2]")
	    protected WebElement adminSearchBox;
	  
		 @FindBy(xpath = "(//div[@class='col-sm-0 col-md-4 text-center'])[1]")
		 protected WebElement clickBlankSpace;

	    @FindBy(xpath = "//input[@id='search_input']")
	    protected WebElement userSearchBox;


	 
	    //Special Timer Product
	    @FindBy(xpath = "//input[@placeholder='Search']")
	    protected WebElement userAppSearchBox;
	    
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
	 
	    
	    //Bulk Collection
	    @FindBy(xpath = "(//span[@class='select2-selection select2-selection--single'])[2]")
	    protected WebElement menuButton;
	    
	    @FindBy(xpath = "(//input[@role='searchbox'])[4]")
	    protected WebElement menuSearchBox; 
	    
	    
	    
	
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}



