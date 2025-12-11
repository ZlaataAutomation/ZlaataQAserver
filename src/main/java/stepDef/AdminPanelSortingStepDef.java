package stepDef;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminPanelSortingPage;

public class AdminPanelSortingStepDef {
	TestContext testContext;
	AdminPanelSortingPage adminSort;
	

	public AdminPanelSortingStepDef(TestContext context) {
		testContext = context;
		adminSort = testContext.getPageObjectManager().getAdminPanelSortingPage();

	}
	
	@When("I sort the products in a category")
	public void i_sort_the_products_in_a_category() throws IOException {
	    adminSort.sortTheCategoriesInAdminPanel();
	}
	@Then("I should see the same product appear first in the User Application category")
	public void i_should_see_the_same_product_appear_first_in_the_user_application_category() throws IOException, InterruptedException {
	    adminSort.verifySortingCategoriesInUserApp();
	     adminSort.verifyFirstProductInUserApp(); 
	}
	    
	
	@When("I sort the products in a Collection")
		public void i_sort_the_products_in_a_collection() throws IOException {
		  adminSort.sortTheCollectionInAdminPanel();
		}

	@Then("I should see the same product appear first in the User Application Collection")
		public void i_should_see_the_same_product_appear_first_in_the_user_application_collection() throws IOException, InterruptedException {
		adminSort.verifySortingCollectionInUserApp();
		adminSort.verifyFirstProductInUserAppCollection();
		}
	
	@When("I sort the products in a Styles")
	public void i_sort_the_products_in_a_Styles() throws IOException {
	  adminSort.sortTheStylesInAdminPanel();
	}
	
	@Then("I should see the same product appear first in the User Application Styles")
		public void i_should_see_the_same_product_appear_first_in_the_user_application_Styles() throws IOException, InterruptedException {
		adminSort.verifySortingStylesInUserApp();
		adminSort.verifyFirstProductInUserAppStyles();
		}


	@When("I sort the products in a Micro Page")
	public void i_sort_the_products_in_a_Micro_Page() throws IOException {
	  adminSort.sortTheMicroPageInAdminPanel();
	}
	
	@Then("I should see the same product appear first in the User Application Micro Page")
	public void i_should_see_the_same_product_appear_first_in_the_user_application_Micro_Page() throws IOException, InterruptedException {
	adminSort.verifySortingMicroPageInUserApp();
	adminSort.verifyFirstProductInUserAppMicroPage();
	}
 

	
	@When("I sort the products in a All Product")
	public void i_sort_the_products_in_a_All_Product() throws IOException {
	  adminSort.sortTheAllProductInAdminPanel();
	}
	
	@Then("I should see the same product appear first in the User Application All Product")
		public void i_should_see_the_same_product_appear_first_in_the_user_application_All_Product() throws IOException, InterruptedException {
		adminSort.verifySortingAllProductInUserApp();
		adminSort.verifyFirstProductInUserAppAllProduct();
		}

		@When("I sort the products in a Search Collection")
		public void i_sort_the_products_in_a_search_collection() throws IOException, InterruptedException {
			adminSort.sortTheSearchCollectionInAdminPanel();
			adminSort.verifySortingSearchCollectionInUserApp();
		}

		@Then("I should see the same product appear first in the User Application Search Collection")
		public void i_should_see_the_same_product_appear_first_in_the_user_application_search_collection() throws IOException, InterruptedException {
			
			adminSort.verifyFirstProductInUserAppSearchCollection();
		}


		@When("I sort the products in a Search Style")
			public void i_sort_the_products_in_a_search_style() throws IOException {
			adminSort.sortTheSearchStyleInAdminPanel();
			}

		@Then("I should see the same product appear first in the User Application Search Style")
			public void i_should_see_the_same_product_appear_first_in_the_user_application_search_style() throws IOException, InterruptedException {
			adminSort.verifySortingSearchStyleInUserApp();
			adminSort.verifyFirstProductInUserAppSearchStyle();
			}





    	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

