package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminPanelAllImortPage;
import pages.AdminPanelCouponPage;

public class AdminPanelCouponStepDef {
	TestContext testContext;
	AdminPanelCouponPage adminCoupon;
	


	public AdminPanelCouponStepDef(TestContext context) {
		testContext = context;
		adminCoupon = testContext.getPageObjectManager().getAdminPanelCouponPage();
	}
	
//TC-01
//Normal Coupon
		@When("Admin clicks Add Coupon and enters random title and code.")
		public void admin_clicks_add_coupon_and_enters_random_title_and_code() throws InterruptedException {
			adminCoupon.createCouponInAdminPanel();

		}

		@Then("Verify the coupon is visible in Admin coupon list")
		public void verify_the_coupon_is_visible_in_admin_coupon_list() {
		 adminCoupon.verifyCouponInAdmin();
		}
		@Then("Verify the same coupon is visible in User App coupon list")
		public void verify_the_same_coupon_is_visible_in_user_app_coupon_list() {
		    adminCoupon.verifyProductsInUserAppMyCouponSection();
		    adminCoupon.verifyIncheckoutCouponPage();
		}

//TC-02
//Special Coupon
			@When("Admin clicks Add Coupon and enters random special title and code.")
			public void admin_clicks_add_coupon_and_enters_random_special_title_and_code() throws InterruptedException {
				adminCoupon.createSpecialCouponInAdminPanel();
			}
			@Then("Verify the special coupon is visible in Admin coupon list")
			public void verify_the_special_coupon_is_visible_in_admin_coupon_list() {
				adminCoupon.verifySpecialCouponInAdmin();
			}
			@Then("Verify the same special coupon is visible in User App coupon list")
			public void verify_the_same_special_coupon_is_visible_in_user_app_coupon_list() throws InterruptedException {
				adminCoupon.verifySpecialCouponInUserApp();
			}

//TC-03
//Specific Product Item Coupon
				@When("Admin Create Specific Product Item Coupon and enters random special title and code.")
				public void admin_create_specific_product_item_coupon_and_enters_random_special_title_and_code() throws InterruptedException {
					adminCoupon.verifySpecificProductItemCoupon();
				}

				@Then("Verify the same Specific Product Item coupon is visible in User App.")
				public void verify_the_same_specific_product_item_coupon_is_visible_in_user_app() throws InterruptedException {
					adminCoupon.searchAndAddThatProductsToCart("Test By Auto1", "Test By Auto2");
				}





}
