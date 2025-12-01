package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminPanelSortingObjRepo extends BasePage{
	
	
	

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
    
    @FindBy(xpath="//input[@role='searchbox']")
    protected WebElement searchTextBox;
    
    @FindBy(xpath = "//input[@id='menuSearch']")  
    protected WebElement searchProductSortMenu;

    // Product Sort line to click after typing
    
    @FindBy(xpath = "//a[normalize-space()='Product Sorts']")  
    protected WebElement clickProductSort;
    
    @FindBy(xpath="//span[@data-value='save_and_back']")
    protected WebElement saveButton;
    
    @FindBy(xpath = "//i[@class='fa fa-refresh']") 
    protected WebElement clearCatchButton;
    
    @FindBy(xpath = "//input[@id='search_input']") 
    protected WebElement searchBox;
	
    
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
