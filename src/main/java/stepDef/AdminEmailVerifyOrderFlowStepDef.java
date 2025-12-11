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
			
		}
		@When("Order confirmation email should be received for order placed, order shipped, order delivered.")
		public void order_confirmation_email_should_be_received_for_order_placed_order_shipped_order_delivered() throws InterruptedException {
			adminEmail.verifyOrderPlacedEmail();
		}

//Order CAncelation user Side
	
	@Given("User places an order successfully for cancel.")
			public void user_places_an_order_successfully_for_cancel() {
			    
			}

	@When("Order Cancellation Confirmation email should be received for order cancel from user side.")
			public void order_cancellation_confirmation_email_should_be_received_for_order_cancel_from_user_side() throws Exception {
		
		    adminEmail.verifyOrderCancellationEmailFromUserSide();
			}
	@Then("Order Refund credited email should be received after admin given refund initiate.")
			public void order_refund_credited_email_should_be_received_after_admin_given_refund_initiate() {
						
			}
	
//Order Exchange Flow

		@Given("User exchanges a product successfully.")
		public void user_exchanges_a_product_successfully() {
		  
		}
		@When("Admin accepts the exchange request, the Order Exchange email should be received.")
		public void admin_accepts_the_exchange_request_the_order_exchange_email_should_be_received() throws InterruptedException {
			adminEmail.verifyOrderExchangeAllEmail();
		}
		@Then("After admin changes the status to Exchange Order Shipped and later to Exchange Order Delivered, respective emails should be received.")
		public void after_admin_changes_the_status_to_exchange_order_shipped_and_later_to_exchange_order_delivered_respective_emails_should_be_received() {
			
		}
		
//Order Exchange Flow
	
		@Given("User returns a product successfully.")
			public void user_returns_a_product_successfully() {
			    
			}
			@When("Admin accepts the return request, the Order Return email should be received.")
			public void admin_accepts_the_return_request_the_order_return_email_should_be_received() throws InterruptedException {
				adminEmail.verifyOrderReturnAllEmail();
			}
			@Then("After the admin initiates the refund, the Order Refund Credited email should be received.")
			public void after_the_admin_initiates_the_refund_the_order_refund_credited_email_should_be_received() {
		
			}
//Order CAncelation user Side
				@Given("Admin cancels the order, the Order Cancellation Confirmation email should be received.")
				public void admin_cancels_the_order_the_order_cancellation_confirmation_email_should_be_received() throws InterruptedException {
					adminEmail.verifyOrderCancellationEmailFromAdminSide();
				}
			
//Return Order Cancel user Side
		@Given("User cancels the return order, the Return Order Cancellation email should be received.")
					public void user_cancels_the_return_order_the_return_order_cancellation_email_should_be_received() throws InterruptedException {
			adminEmail.verifyReurnOrderCancellationEmailFromUserSide();
					}
//Return Order Cancel Admin Side
			@Given("Admin cancels the return order  status to Product Received in Damaged State, the Return Order Cancellation email should be received.")
			public void admin_cancels_the_return_order_status_to_product_received_in_damaged_state_the_return_order_cancellation_email_should_be_received() throws InterruptedException {
				adminEmail.verifyReturnOrderCancellationEmailFromAdminSide();
			}
//Return Order Rejected Admin Side
		@Given("Admin rejects the return order with reason Product Not Available, the Return Order Rejection email should be received.")
				public void admin_rejects_the_return_order_with_reason_product_not_available_the_return_order_rejection_email_should_be_received() throws InterruptedException {
			adminEmail.verifyReturnOrderRejectedEmailFromAdminSide();
				}
		
// Exchange Order Cancel by user Side
			@Given("User cancels the exchange product, the Exchange Order Cancelled email should be received.")
			public void user_cancels_the_exchange_product_the_exchange_order_cancelled_email_should_be_received() throws InterruptedException {
				adminEmail.verifyExchangeOrderCancelEmailFromUserSide(); 
			}
//  Exchange Order Cancel From Admin Side 
	@Given("Admin changes the exchange order status to Product Out of Stock, the Exchange Out of Stock Cancellation email should be received.")
				public void admin_changes_the_exchange_order_status_to_product_out_of_stock_the_exchange_out_of_stock_cancellation_email_should_be_received() throws InterruptedException {
		adminEmail.verifyExchangeOrderCancelOutOfStockEmailFromAdminSide();
				}
//  Exchange Order Cancel From Admin Side 
		@Given("Admin changes the exchange order status to Product Received Damage State, the Exchange Out of Stock Cancellation email should be received.")
		public void admin_changes_the_exchange_order_status_to_product_received_damage_state_the_exchange_out_of_stock_cancellation_email_should_be_received() throws InterruptedException {
			adminEmail.verifyExchangeOrderCancelReceivedDamageStateEmailFromAdminSide();
		}
//  Exchange Order Cancel From Admin Side 
			@Given("Admin cancels the exchange order after it has been shipped, the Exchange Order Cancellation email should be received.")
			public void admin_cancels_the_exchange_order_after_it_has_been_shipped_the_exchange_order_cancellation_email_should_be_received() throws InterruptedException {
				adminEmail.verifyExchangeOrderCancelEmailFromAdminSide();
			}














	
	
	
	
	
	
	
	
	
	

}
