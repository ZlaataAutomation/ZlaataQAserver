package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminGoogleMerchantPage;
import pages.AdminPanelInfluencerPage;
import pages.HomePage;

public class AdminPanelInfluencerStepDef {
	TestContext testContext;
	AdminPanelInfluencerPage adminInflu;
	private String productName;
	HomePage home;
	


	public AdminPanelInfluencerStepDef(TestContext context) {
		testContext = context;
		adminInflu = testContext.getPageObjectManager().getAdminPanelInfluencerPage();
	}

		@Given("I take a random product from User App")
		public void i_take_a_random_product_from_user_app() {
		    productName = adminInflu.takeRandomProductName();
		}
		@When("I search the product in Admin, set Influencer Yes, upload image, add to collection and sort {string}")
		public void i_search_the_product_in_admin_set_influencer_yes_upload_image_add_to_collection_and_sort(String filePath) {
			String imagepath = System.getProperty("user.dir") + "/src/test/resources/images/" + filePath;
			adminInflu.adminLoginApp();
			adminInflu.setTheProductImageAndStatusInInfluencer(productName, imagepath);
			adminInflu.addTheProductInProductCollection();
			adminInflu.sortTheProductForIncluencer();
			

		}
		@Then("I verify the product appears on the User App Influencer page")
		public void i_verify_the_product_appears_on_the_user_app_influencer_page() {
			adminInflu.verifyFirstProductInUserAppInfluencer(productName);

		}


	



	

}
