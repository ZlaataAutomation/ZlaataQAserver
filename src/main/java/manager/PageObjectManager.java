package manager;


import context.TestContext;
import pages.*;
import stepDef.AdminGoogleMerchantStepDef;
import stepDef.Hooks;


import org.openqa.selenium.WebDriver;

public class PageObjectManager {

    private WebDriver driver;
    private Hooks hooks;
    private NegativeSignupPages negsignups;
    private MyOrdersPage myorders;
    private LoginPage login;
    private HomePage home;
    private Menus menu;
    private ProductListingPage pLp;
    private CheckoutPage cOp;
    private SaleOffer50Percentage sale;
    private SaleBuy1Get1 saleB1G1;
    private OrdersPage order;
    private AddressPage address;
    private FooterPage footer;
    private ProductDetailsPage pDP;
    private SearchSectionPage search;
    private CouponPage coupon;
    private AdminPanelPage admin;
    private AdminPanelSortingPage adminSort;
    private AdminPanelExportExcelFileMatchPage adminExport;
    private AdminGoogleMerchantPage adminGoogle;
    private AdminPanelAllImortPage adminImport;
    private AdminPanelInfluencerPage adminInflu;
    private AdminPanelCouponPage adminCoupon;
    private AdminPanelExportPage adminExports;
    private AdminProductSecondaryColorPage adminSColor;
    private AdminEmailVerifyOrderFlowPage adminEmail;
   

    public PageObjectManager(WebDriver driver) {
        this.driver = driver;
    }
    public AddressPage getAddressPage() {
        return (address == null) ? address = new AddressPage(driver) : address;
    }
    
    public FooterPage getFooterPage() {
        return (footer == null) ? footer = new FooterPage(driver) : footer;
    }
    
    public ProductDetailsPage getProductDetailsPage() {
        return (pDP == null) ? pDP = new ProductDetailsPage(driver) : pDP;
    }
    public SearchSectionPage getSearchSectionPage() {
        return (search == null) ? search = new SearchSectionPage(driver) : search;
    }
    
    public NegativeSignupPages getNegativeSignupPages() {
        return (negsignups == null) ? negsignups = new NegativeSignupPages(driver) : negsignups;
    }
	public Hooks getHooks() {
        TestContext testContext = null;
		return (hooks == null) ? hooks = new Hooks(testContext) : hooks;
    }
    
	public MyOrdersPage getMyOrdersPage() {
        return (myorders == null) ? myorders = new MyOrdersPage(driver) : myorders;
    
	}
    
	public LoginPage getLoginPage() {
        return (login == null) ? login = new LoginPage(driver) : login;
    
	}
	
	public HomePage getHomePage() {
        return (home == null) ? home = new HomePage(driver) : home;
    
	}
    
	public Menus getMenus() {
        return (menu == null) ? menu = new Menus(driver) : menu;
    
	}
    
	public ProductListingPage getProductListingPage() {
        return (pLp == null) ? pLp = new ProductListingPage(driver) : pLp;
    
	}
	
	public CheckoutPage getCheckoutPage() {
        return (cOp == null) ? cOp = new CheckoutPage(driver) : cOp;
    
	}
    
	public SaleOffer50Percentage getSaleOffer50Percentage() {
        return (sale == null) ? sale = new SaleOffer50Percentage(driver) : sale;
    
	}
	public SaleBuy1Get1 getSaleBuy1Get1() {
        return (saleB1G1 == null) ? saleB1G1 = new SaleBuy1Get1(driver) : saleB1G1;
    
	}
	public OrdersPage getOrdersPage() {
        return (order == null) ? order = new OrdersPage(driver) : order;
    
	}
	public CouponPage getCouponPage() {
        return (coupon == null) ? coupon = new CouponPage(driver) : coupon;
    
	}
	
	public AdminPanelPage getAdminPanelPage() {
        return (admin == null) ? admin = new AdminPanelPage(driver) : admin;
    
	}
	
	public AdminPanelSortingPage getAdminPanelSortingPage() {
        return (adminSort == null) ? adminSort = new AdminPanelSortingPage(driver) : adminSort;
    
	}
	public AdminPanelExportExcelFileMatchPage getAdminPanelExportExcelFileMatchPage() {
		return (adminExport == null) ? adminExport = new AdminPanelExportExcelFileMatchPage(driver) : adminExport;
	}
	
	public AdminGoogleMerchantPage getAdminGoogleMerchantPage() {
		return (adminGoogle == null) ? adminGoogle = new AdminGoogleMerchantPage(driver) : adminGoogle;
	}
	public AdminPanelAllImortPage getAdminPanelAllImortPage() {
		return (adminImport == null) ? adminImport = new AdminPanelAllImortPage(driver) : adminImport;
	}
	
	public AdminPanelInfluencerPage getAdminPanelInfluencerPage() {
		return (adminInflu == null) ? adminInflu = new AdminPanelInfluencerPage(driver) : adminInflu;
	}
	
	public AdminPanelCouponPage getAdminPanelCouponPage() {
		return (adminCoupon == null) ? adminCoupon = new AdminPanelCouponPage(driver) : adminCoupon;
	}
	
	public AdminPanelExportPage getAdminPanelExportPage() {
		return (adminExports == null) ? adminExports = new AdminPanelExportPage(driver) : adminExports;
	}
	
	public AdminProductSecondaryColorPage getAdminProductSecondaryColorPage() {
		return (adminSColor == null) ? adminSColor = new AdminProductSecondaryColorPage(driver) : adminSColor;
	}
	
	public AdminEmailVerifyOrderFlowPage getAdminEmailVerifyOrderFlowPage() {
		return (adminEmail == null) ? adminEmail = new AdminEmailVerifyOrderFlowPage(driver) : adminEmail;
	}
	
	
}