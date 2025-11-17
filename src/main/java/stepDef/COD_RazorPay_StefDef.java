package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.COD_RazorPay_Page;

public class COD_RazorPay_StefDef {
	
	TestContext testContext;
	COD_RazorPay_Page cod;
	
	public COD_RazorPay_StefDef(TestContext context) {
		testContext = context;
		cod = testContext.getPageObjectManager().getCOD_RazorPay_Page();
	}

	@Given("User adds two products with Gift Wrap to the cart")
		public void user_adds_two_products_with_gift_wrap_to_the_cart() {
		 
		}
	@When("In Razor Pay Gift Wrap fee amount should be displayed and COD option should not be visible.")
		public void in_razor_pay_gift_wrap_fee_amount_should_be_displayed_and_cod_option_should_not_be_visible() {
		  
		}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
