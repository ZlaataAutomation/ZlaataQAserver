package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminFlashNotification;

public class AdminFlashNotificationStepDef {
	
	TestContext testContext;
	AdminFlashNotification adminFlashNotifications;
	private String addedNotificationName;
	private String deletedNotificationName;

	// ANSI Color Codes
	private static final String RESET = "\u001B[0m";
	private static final String GREEN = "\u001B[32m";
	private static final String RED = "\u001B[31m";
	private static final String YELLOW = "\u001B[33m";
	private static final String BLUE = "\u001B[34m";
	private static final String PURPLE = "\u001B[35m";

	public AdminFlashNotificationStepDef(TestContext context) {
		testContext = context;
		adminFlashNotifications = testContext.getPageObjectManager().getAdminFlashNotification();
	}	
	
	
	
// TC_UI_Zlaata_FN_01
	@When("add flash notification for {string}")
	public void add_flash_notification_for(String pageType) throws InterruptedException {
	   adminFlashNotifications.validatedLandingPageFlashNotification();
	}

	@Then("verify error message is displayed")
	public void verify_error_message_is_displayed() throws InterruptedException {
	    boolean errorsDisplayed = adminFlashNotifications.verifyValidationErrorsDisplayed();
	    if (!errorsDisplayed) {
	        throw new AssertionError("No validation error messages were displayed when submitting empty form");
	    }
	    System.out.println(GREEN + "✅ Validation errors correctly displayed for empty form submission" + RESET);
	}

	@And("fill and save flash notification for {string}")
	public void fill_and_save_flash_notification_for(String pageType) throws InterruptedException {
		addedNotificationName = adminFlashNotifications.fillFlashNotificationForm();
	    adminFlashNotifications.enableDisplayToggleForFirstRecord();
	}

	@Then("verify notification is visible on {string} UI")
	public void verify_notification_is_visible_on_ui(String pageType) throws InterruptedException {
	    adminFlashNotifications.navigateToLandingPageUI();
	    String expectedDescription = adminFlashNotifications.getSavedDescription();
	    System.out.println(BLUE + "Verifying flash notification: " + expectedDescription + RESET);
	    boolean isVisible = adminFlashNotifications.verifyFlashNotificationOnUI(expectedDescription);
	    if (!isVisible) {
	        throw new AssertionError("Flash notification '" + expectedDescription
	            + "' was NOT found on the " + pageType + " UI");
	    }
	    System.out.println(GREEN + "✅ Flash notification verified successfully on " + pageType + " UI" + RESET);
	}
	
	
	
	// TC_UI_Zlaata_FN_02 — combined Zlaata India + Boss Lady random selection
	@When("add flash notification for random brand home page")
	public void add_flash_notification_for_random_brand_home_page() throws InterruptedException {
	    adminFlashNotifications.addFlashNotificationForRandomBrand();
	    System.out.println(GREEN + "✅ Flash notification added for brand: "
	        + adminFlashNotifications.getRandomBrandForTC02() + RESET);
	}

	@Then("verify notification is visible on random brand UI")
	public void verify_notification_is_visible_on_random_brand_ui() throws InterruptedException {
	    adminFlashNotifications.navigateToRandomBrandUI();
	    boolean isVisible = adminFlashNotifications.verifyFlashNotificationOnRandomBrandUI();
	    if (!isVisible) {
	        throw new AssertionError("Flash notification '"
	            + adminFlashNotifications.getSavedDescription()
	            + "' was NOT found on "
	            + adminFlashNotifications.getRandomBrandForTC02() + " UI");
	    }
	    System.out.println(GREEN + "✅ Flash notification verified successfully on "
	        + adminFlashNotifications.getRandomBrandForTC02() + " UI" + RESET);
	}	
   
    
    
// TC_UI_Zlaata_FN_03
    @Given("a flash notification already exists")
    public void a_flash_notification_already_exists() throws InterruptedException {
        adminFlashNotifications.verifyFlashNotificationAlreadyExists1();
        System.out.println(GREEN + "✅ Verified active flash notification exists for brand: "
            + adminFlashNotifications.getSelectedBrandType() + RESET);
    }

    @When("edit the flash notification")
    public void edit_the_flash_notification() throws InterruptedException {
        adminFlashNotifications.editFirstFlashNotificationForSelectedBrand();
        System.out.println(GREEN + "✅ Flash notification edited for brand: "
            + adminFlashNotifications.getSelectedBrandType() + RESET);
    }

    @Then("verify updated notification is visible in UI")
    public void verify_updated_notification_is_visible_in_ui() throws InterruptedException {
        adminFlashNotifications.navigateToSelectedBrandUI();
        boolean isVisible = adminFlashNotifications.verifyUpdatedFlashNotificationOnUI();
        if (!isVisible) {
            throw new AssertionError("Updated flash notification '"
                + adminFlashNotifications.getSavedDescription()
                + "' was NOT found on " + adminFlashNotifications.getSelectedBrandType() + " UI");
        }
        System.out.println(GREEN + "✅ Updated flash notification verified successfully on "
            + adminFlashNotifications.getSelectedBrandType() + " UI" + RESET);
    }
    
// TC_UI_Zlaata_FN_04
    @When("add scheduled flash notification")
    public void add_scheduled_flash_notification() throws InterruptedException {
        adminFlashNotifications.validateScheduledFlashNotification();
    }

    @Then("verify notification is NOT visible on UI before toggle")
    public void verify_notification_is_not_visible_on_ui_before_toggle() throws InterruptedException {
        boolean notVisible = adminFlashNotifications.verifyFlashNotificationNotVisibleOnUI();
        if (!notVisible) {
            throw new AssertionError("Scheduled flash notification was already visible on UI before display toggle was enabled — FAIL");
        }
        System.out.println(GREEN + "✅ Scheduled notification correctly NOT visible before toggle — PASS" + RESET);
    }

    @Then("verify notification is visible on UI after toggle")
    public void verify_notification_is_visible_on_ui_after_toggle() throws InterruptedException {
        boolean isVisible = adminFlashNotifications.verifyFlashNotificationVisibleAfterStartTime();
        if (!isVisible) {
            throw new AssertionError("Scheduled flash notification was NOT visible on UI after enabling display toggle — FAIL");
        }
        System.out.println(GREEN + "✅ Scheduled notification correctly visible after toggle — PASS" + RESET);
    }

}