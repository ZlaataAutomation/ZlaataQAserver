package stepDef;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminEmailVerifyOrderFlowPage;
import pages.AdminGoogleMerchantPage;

public class AdminEmailVerifyOrderFlowStepDef {
	
	TestContext testContext;
	AdminEmailVerifyOrderFlowPage adminEmail;
	


	public AdminEmailVerifyOrderFlowStepDef(TestContext context) {
		testContext = context;
		adminEmail = testContext.getPageObjectManager().getAdminEmailVerifyOrderFlowPage();
	}

		@Given("User places an order successfully")
		public void user_places_an_order_successfully() throws InterruptedException {
			adminEmail.verifyOrderPlacedEmail();
		}
		@When("Order confirmation email should be received for order placed, order shipped, order delivered.")
		public void order_confirmation_email_should_be_received_for_order_placed_order_shipped_order_delivered() {

		}



	
	
	
	
	
	
	
	
	
	

}
