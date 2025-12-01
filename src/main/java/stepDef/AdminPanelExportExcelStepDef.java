package stepDef;

import java.io.File;
import java.io.IOException;

import context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AdminPanelExportExcelFileMatchPage;
import pages.AdminPanelSortingPage;

public class AdminPanelExportExcelStepDef {
	
	TestContext testContext;
	AdminPanelExportExcelFileMatchPage adminExport;
	


	public AdminPanelExportExcelStepDef(TestContext context) {
		testContext = context;
		adminExport = testContext.getPageObjectManager().getAdminPanelExportExcelFileMatchPage();
	}
	
	

	//New Added Product Export Check
						
					
		@When("I export products from first page with date range {string} and save as {string}")
		public void i_export_products_from_first_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
			adminExport.exportFromFirstPage(dateRange, fileName);				    
		}
							
		@When("I export products from second page with date range {string} and save as {string}")
		public void i_export_products_from_second_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
			adminExport.exportFromSecondPage(dateRange, fileName);
							   
		}
		@Then("I verify both exported files {string} and {string} have matching product names")
		public void i_verify_both_exported_files_and_have_matching_product_names(String file1, String file2) throws IOException, InterruptedException {
			String downloadDir = "C:\\Users\\Sarojkumar\\Downloads\\";
		    String file1Path = downloadDir + file1;
		    String file2Path = downloadDir + file2;
		    adminExport.verifyExportedProductsMatch(file1Path, file2Path);		   
		}
		//Active Products

		@When("I export active products from first page with date range {string} and save as {string}")
		public void i_export_active_products_from_first_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
			adminExport.exportActiveProductFromFirstPage(dateRange, fileName);				    
		}
							
		@When("I export active products from second page with date range {string} and save as {string}")
		public void i_export_active_products_from_second_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
			adminExport.exportFromActiveProductSecondPage(dateRange, fileName);
							   
		}
		@Then("I verify both exported active products files {string} and {string} have matching product names")
		public void i_verify_both_exported_active_products_files_and_have_matching_product_names(String file1, String file2) throws IOException {
			String downloadDir = "C:\\Users\\Sarojkumar\\Downloads\\";
		    String file1Path = downloadDir + file1;
		    String file2Path = downloadDir + file2;
		    adminExport.verifyExportedActiveProductsMatch(file1Path, file2Path);		   
		}

		//Sold Out Products
		
			@When("I export sold out products from first page with date range {string} and save as {string}")
			public void i_export_sold_out_products_from_first_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
				adminExport.exportSoldOutProductsFromFirstPage(dateRange, fileName);
			}

			@When("I export sold out products from second page with date range {string} and save as {string}")
			public void i_export_sold_out_products_from_second_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
				adminExport.exportFromSoldOutProductsSecondPage(dateRange, fileName);
			}
			@Then("I verify both exported sold out products files {string} and {string} have matching product names")
			public void i_verify_both_exported_sold_out_products_files_and_have_matching_product_names(String file1, String file2) throws IOException {
				String downloadDir = "C:\\Users\\Sarojkumar\\Downloads\\";
			    String file1Path = downloadDir + file1;
			    String file2Path = downloadDir + file2;
			    adminExport.verifyExportedSoldOutProductsMatch(file1Path, file2Path);

			}
			
	//Inactive Product
			
			@When("I export inactive products from first page with date range {string} and save as {string}")
				public void i_export_inactive_products_from_first_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
				adminExport.exportInactiveProductsFromFirstPage(dateRange, fileName);
				}
 
			@When("I export inactive products from second page with date range {string} and save as {string}")
				public void i_export_inactive_products_from_second_page_with_date_range_and_save_as(String dateRange, String fileName) throws InterruptedException {
				adminExport.exportFromInactiveProductsSecondPage(dateRange, fileName);
				}
				@Then("I verify both exported inactive products files {string} and {string} have matching product names")
				public void i_verify_both_exported_inactive_products_files_and_have_matching_product_names(String file1, String file2) throws IOException {
					String downloadDir = "C:\\Users\\Sarojkumar\\Downloads\\";
				    String file1Path = downloadDir + file1;
				    String file2Path = downloadDir + file2;
				    adminExport.verifyExportedInactiveProductsMatch(file1Path, file2Path);
				}


		

}
