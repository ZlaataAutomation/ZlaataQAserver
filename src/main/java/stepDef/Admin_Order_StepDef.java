package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.Admin_Order_Page;

public class Admin_Order_StepDef {
	
	TestContext testContext;
	Admin_Order_Page adminorder;
	
	public Admin_Order_StepDef(TestContext context) {
		testContext = context;
		adminorder = testContext.getPageObjectManager().getAdmin_Order_Page();
	}

@Given("Verify that the admin can select customer and address successfully.")
		public void verify_that_the_admin_can_select_customer_and_address_successfully() throws InterruptedException {
	adminorder.validateAdminPlacePrepaidOrder(); 
		}
@When("Verify that the admin can place a Prepaid order and complete payment successfully.")
		public void verify_that_the_admin_can_place_a_prepaid_order_and_complete_payment_successfully() {

		}

//TC_02

@Given("Verify that the admin can select customer and address successfully for COD.")
public void verify_that_the_admin_can_select_customer_and_address_successfully_for_cod() throws InterruptedException {
	adminorder.validateAdminPlaceCODOrder();
}

@When("Verify that the admin can place a COD order and complete payment successfully.")
public void verify_that_the_admin_can_place_a_cod_order_and_complete_payment_successfully() {
    
}







	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
