package objectRepo;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import basePage.BasePage;

public abstract class AdminPanelExportObjRepo extends BasePage {
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
     
    
   
   
     @FindBy(xpath = "//button[normalize-space()='Export-btn']")
     protected WebElement orderExportBtn;
   
     @FindBy(xpath = "//input[@name='orders.created_at']")
     protected WebElement orderCreatedAtBox;
     
     @FindBy(xpath = "//input[@name='orders.created_at']")
     protected WebElement orderDateInput;
     
     @FindBy(xpath = "(//button[@type='button'][normalize-space()='Apply'])[3]")
     protected WebElement orderCalendarApplyBtn;
     
     @FindBy(xpath = "//button[normalize-space()='generate']")
     protected WebElement orderGenerateBtn;
     
     @FindBy(xpath = "//input[@id='menuSearch']")
     protected WebElement searchMenu;
   
     @FindBy(xpath = "//a[normalize-space()='Export Histories']")
     protected WebElement selectExportHistories;
    
    
    
    
    
    
    
    
}
