package objectRepo;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import basePage.BasePage;

public abstract class TryAlongSectionObjRepo extends BasePage {

	
	@FindBy(xpath = "//li[@class='navigation_menu_list nav_menu_dropdown shop']")
	protected WebElement shopMenu;
	
	@FindBy(xpath = "//div[@class='nav_drop_down_box_category active']//ul/li/a[translate(normalize-space(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ') = 'DRESSES']")
	protected WebElement category;
	
//	@FindBy(xpath = "//div[@class='product_list_cards_list ']")
//	protected WebElement productIamgeClick;
	
	
	@FindBy(xpath = "(//div[contains(@class,'product_list_cards_img_box')]//img)[15]")
	protected WebElement product;

	
	@FindBy(xpath = "//h4[@class='prod_name']")
	protected WebElement mainProductName;
	
	@FindBy(xpath = "//div[@class='try_along_quickview_btn Cls_quickview_btn']")
	protected List<WebElement> tryAlongProductsViewButton;
	
	@FindBy(xpath = "//div[@class='qv_prod_details']//div[@class='prod_name_wrap']")
	protected  WebElement tryAlongProduct;
	
	@FindBy(xpath = "//div[@class='popup_containers_cls_btn']")
	protected WebElement closeTheQuickViewPopup;
	

	@FindBy(name = "email")
	protected WebElement adminEmail;

	@FindBy(id = "password")
	protected WebElement adminPassword;

	@FindBy(xpath = "//button[@type='submit']")
	protected WebElement adminLogin;

	@FindBy(xpath = "//a[normalize-space()='Product Detail Name']")
	protected WebElement productDetailsButton;
	
	@FindBy(xpath = "//span[@class='select2-search select2-search--dropdown']//input[@role='searchbox']")
	protected WebElement productDetailsButtondropdown;
	
	@FindBy(xpath = "//i[@class='las la-edit']")
	protected WebElement editbuttonOnProductSection;
	
	@FindBy(xpath = "//a[normalize-space()='Item']")
	protected WebElement itemButton;
	
	@FindBy(xpath = "//input[@name='filters[0][name]']")
	protected WebElement productDetailsName;
	
	
}
