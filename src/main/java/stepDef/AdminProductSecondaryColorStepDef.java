package stepDef;

import context.TestContext;
import io.cucumber.java.en.*; 
import pages.AdminProductSecondaryColorPage;

public class AdminProductSecondaryColorStepDef {
	TestContext testContext;
	AdminProductSecondaryColorPage adminSColor;
	


	public AdminProductSecondaryColorStepDef(TestContext context) {
		testContext = context;
		adminSColor = testContext.getPageObjectManager().getAdminProductSecondaryColorPage();
	}
	

	@Given("I Randomly choose a product and add that product with a secondary color")
		public void i_randomly_choose_a_product_and_add_that_product_with_a_secondary_color() throws InterruptedException {
		adminSColor.verifySecondaryColorFlow();
		}

	@Then("the secondary color should display on the product details, add to cart popup, wishlist, and checkout pages")
		public void the_secondary_color_should_display_on_the_product_details_add_to_cart_popup_wishlist_and_checkout_pages() throws InterruptedException {
		adminSColor.verifyProductColorInUserApp();
		}



	

}
