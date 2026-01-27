package stepDef;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.TryAlongSectionPage;

public class TryAlongstepDef {

	TestContext testContext;

	TryAlongSectionPage tryalong;

	public TryAlongstepDef(TestContext context) {
		testContext = context;
		tryalong = testContext.getPageObjectManager().getTryAlongSectionPage();

	}

	@When("the user opens the application and navigates to any product details page")
	public void the_user_opens_the_application_and_navigates_to_any_product_details_page() {

		tryalong.captureApplicationTryAlong();

	}


	@Given("the admin is logged in")
	public void the_admin_is_logged_in() {

		tryalong.adminLogin();

	}

	@Then("navigate to the product section  and verify that the Try Along section products are shuffled")
	public void navigate_to_the_product_section_and_verify_that_the_try_along_section_products_are_shuffled() {

		tryalong.captureAdminTryAlong();
	}


	//2


		@When("the admin adds a new Try Along product and the Try Along section should display the newly added product")
	public void the_admin_adds_a_new_try_along_product_and_the_try_along_section_should_display_the_newly_added_product() {
		tryalong.addNewTryalongProduct();
	}






}
