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

		@When("I export products from Order Placed page with date range {string} and save as {string}")
		public void i_export_products_from_order_placed_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
			adminExports.verifyOrderExportBtn(dateRange, fileName);	
		}

		@Then("I verify exported file {string} has records within date range {string}")
		public void i_verify_exported_file_has_records_within_date_range(String fileName, String dateRange) throws IOException, ParseException {
			adminExports.verifyExportedDateRange(fileName, dateRange);
		}



}
