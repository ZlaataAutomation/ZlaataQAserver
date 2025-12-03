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

//TC-04
//Specific  Item Coupon
					@When("specific coupon is created in admin with one associated product.")
					public void specific_coupon_is_created_in_admin_with_one_associated_product() throws InterruptedException {
						adminCoupon.validateOneSpecificProductWithNormalProduct();
					}

					@Then("they should be redirected to the listing page showing the linked product and see the coupon as SELECT after adding it to cart")
					public void they_should_be_redirected_to_the_listing_page_showing_the_linked_product_and_see_the_coupon_as_select_after_adding_it_to_cart() {
					    
					}
//TC-05
//Specific  Item Coupon
						@When("Coupon displays both linked products and applies discount correctly after order placement")
						public void coupon_displays_both_linked_products_and_applies_discount_correctly_after_order_placement() throws InterruptedException {
							adminCoupon.validateTwoSpecificProductCoupon();
						}







}
