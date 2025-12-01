package stepDef;

import java.io.IOException;
import java.text.ParseException;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminPanelExportPage;

public class AdminPanelExportStepDef {
	
	
	TestContext testContext;
	AdminPanelExportPage adminExports;
	


	public AdminPanelExportStepDef(TestContext context) {
		testContext = context;
		adminExports = testContext.getPageObjectManager().getAdminPanelExportPage();
	}
//TC-01
//Order > Placed > Orders Page
	@When("Admin selects Order statuss and date range, then export the orders as a random file")
					public void admin_selects_order_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
		adminExports.verifyAllOrderStatusesSequentially();
					}
	@Then("Verify that all created At dates in the exported file  within the selected date ranges")
	public void verify_that_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {

	}
	
//TC-02
//Order > Placed >Return Order page

		@When("Admin selects Return Order statuss and date range, then export the orders as a random file")
		public void admin_selects_return_order_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
			adminExports.verifyAllReturnStatusesSequentially();
		}

		@Then("Verify that Return Order all created At dates in the exported file  within the selected date ranges")
		public void verify_that_return_order_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
		    
		}
		
//TC-03	
//Order > Placed > Exchange Order page 
	@When("Admin selects Exchange Order statuss and date range, then export the orders as a random file")
			public void admin_selects_exchange_order_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
		adminExports.verifyAllExchangeStatusesSequentially();
			}
			@Then("Verify that Exchange Order all created At dates in the exported file  within the selected date ranges")
			public void verify_that_exchange_order_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
			
			}

//TC-04			
// Order > Canceled >  Order page	
			@When("Admin selects All Canceled Order statuss and date range, then export the orders as a random file")
				public void admin_selects_all_canceled_order_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
				adminExports.verifyAllCancledStatusesSequentially();
				}

				@Then("Verify that Canceled Order all created At dates in the exported file  within the selected date ranges")
				public void verify_that_canceled_order_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
				   
				}

//TC-05				
//Order > Canceled > Return Cancel page
		@When("Admin selects All Canceled Return statuss and date range, then export the orders as a random file")
					public void admin_selects_all_canceled_return_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
			adminExports.verifyAllCancledReturnStatusesSequentially();  
					}

		@Then("Verify that Canceled Return all created At dates in the exported file  within the selected date ranges")
					public void verify_that_canceled_return_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
					  
					}
	
//TC-06		
//Order > Canceled > Exchange Cancel page
		@When("Admin selects All Canceled Exchange statuss and date range, then export the orders as a random file")
			public void admin_selects_all_canceled_exchange_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
			adminExports.verifyAllCancledExchangeStatusesSequentially();
			}

		@Then("Verify that Canceled Exchange all created At dates in the exported file  within the selected date ranges")
			public void verify_that_canceled_exchange_all_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
			   
			}

//TC-07		
//Payment Pending  page		
		@When("Admin selects Payment Pending statuss and date range, then export the orders as a random file")
			public void admin_selects_payment_pending_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
			adminExports.verifyPaymentPendingStatusesSequentially();
			}

		@Then("Verify that Payment Pending created At dates in the exported file  within the selected date ranges")
			public void verify_that_payment_pending_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
			  
			}
//TC-08		
//Payment Refund  page	
	@When("Admin selects Payment Refund statuss and date range, then export the orders as a random file")
			public void admin_selects_payment_refund_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
		adminExports.verifyPaymentRefundStatusesSequentially();
			}

	@Then("Verify that Payment Refund created At dates in the exported file  within the selected date ranges")
			public void verify_that_payment_refund_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
			   
			}
//TC-09	
//Payment Failed  page
		@When("Admin selects Payment Failed statuss and date range, then export the orders as a random file")
		public void admin_selects_payment_failed_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
			adminExports.verifyPaymentFailedStatusesSequentially();
		}


		@Then("Verify that Payment Failed created At dates in the exported file  within the selected date ranges")
		public void verify_that_payment_failed_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
		   
		}
//TC-10	
//RTO Orders page
			@When("Admin selects RTO Orders statuss and date range, then export the orders as a random file")
			public void admin_selects_rto_orders_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
				adminExports.verifyRTOOrdersStatusesSequentially();
			}

			@Then("Verify that RTO Orders created At dates in the exported file  within the selected date ranges")
			public void verify_that_rto_orders_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
			  
			}

//TC-11
//ALL Orders page
			@When("Admin selects All Orders statuss and date range, then export the orders as a random file")
				public void admin_selects_all_orders_statuss_and_date_range_then_export_the_orders_as_a_random_file() throws InterruptedException, IOException, ParseException {
				adminExports.verifyAllOrdersStatusesSequentially();
				}

			@Then("Verify that All Orders created At dates in the exported file  within the selected date ranges")
				public void verify_that_all_orders_created_at_dates_in_the_exported_file_within_the_selected_date_ranges() {
				  
				}








			



					











}
