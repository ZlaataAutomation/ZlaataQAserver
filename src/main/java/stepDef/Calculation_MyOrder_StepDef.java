package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.Calculation_MyOrder_Page;

public class Calculation_MyOrder_StepDef {
	TestContext testContext;
	Calculation_MyOrder_Page calculation;
	
	public Calculation_MyOrder_StepDef(TestContext context) {
		testContext = context;
		calculation = testContext.getPageObjectManager().getCalculation_MyOrder_Page();
	}

 @Given("User adds Product P1 with Gift Wrap, Coupon, Gift Card Amount, and Thread")
		public void user_adds_product_p1_with_gift_wrap_coupon_gift_card_amount_and_thread() throws InterruptedException {
	 calculation.verify_P1_With_GW_C_GC_GA_T();
		}
	@When("Verify Razorpay popup details and My Orders price breakup after placing the order.")
		public void verify_razorpay_popup_details_and_my_orders_price_breakup_after_placing_the_order() {
		    
		}

//TC-02

	@Given("User adds Product P1 & P2 with Gift Wrap, Coupon, Gift Card Amount, and Thread")
		public void user_adds_product_p1_p2_with_gift_wrap_coupon_gift_card_amount_and_thread() throws InterruptedException {
		calculation.verify_P1_P2_With_GC_C_GW_GCA_T_E();
		}
		@When("Verify Product P1 & P2 checkout page, Razorpay popup details and My Orders price breakup after placing the order.")
		public void verify_product_p1_p2_checkout_page_razorpay_popup_details_and_my_orders_price_breakup_after_placing_the_order() {
		    
		}

//TC-03
	@Given("User adds Product P1 & CP & AP with Gift Wrap, Coupon, Gift Card Amount, and Thread")
			public void user_adds_product_p1_cp_ap_with_gift_wrap_coupon_gift_card_amount_and_thread() throws InterruptedException {
		calculation.verify_P1_CP_AP_With_GC_C_GW_GCA_T_E();
			}
 
	@When("Verify Product P1 & CP & AP checkout page, Razorpay popup details and My Orders price breakup after placing the order.")
			public void verify_product_p1_cp_ap_checkout_page_razorpay_popup_details_and_my_orders_price_breakup_after_placing_the_order() {
			
			}





	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
