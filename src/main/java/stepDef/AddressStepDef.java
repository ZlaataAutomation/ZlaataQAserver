package stepDef;


import context.TestContext;
import io.cucumber.java.en.Given;
import pages.AddressPage;
import pages.LoginPage;

public class AddressStepDef {

	TestContext testContext;
	AddressPage address;
	LoginPage login;

	public AddressStepDef(TestContext context) {
		testContext = context;
		address = testContext.getPageObjectManager().getAddressPage();
		login =testContext.getPageObjectManager().getLoginPage();
	}


	@Given("User clicks on Add New Address and verifies address can be added  on Address Page")
	public void user_clicks_on_add_new_address_and_verifies_address_can_be_added_on_address_page() {
		login.userLogin();
		address.newAddresOnSavedAddresPage();

	}

	@Given("User clicks on Save Address button without entering any data in address fields")
	public void user_clicks_on_save_address_button_without_entering_any_data_in_address_fields() {
		login.userLogin();
		address.leftAllMandatory();

	}


	@Given("User verifies the Save as Default checkbox or toggle is functional")
	public void user_verifies_the_save_as_default_checkbox_or_toggle_is_functional() {
		login.userLogin();
		address.defaultAddress();

	}


	@Given("User clicks on Add New Address on checkcout page")
	public void user_clicks_on_add_new_address_on_checkcout_page() {
		login.userLogin();
		address.addToCart();
		address.addNewAddressOnCheckoutPage();
	}



	@Given("User verifies Edit addresses fiunctionality on saved Address page")
	public void user_verifies_edit_addresses_fiunctionality_on_saved_address_page() {
		login.userLogin();
		address.editAddressfunctionalityOnSavedAddressPage();

	}	


	@Given("The Default radio button is selected on the Change Address page")
	public void the_default_radio_button_is_selected_on_the_change_address_page() {

		login.userLogin();
		address.addToCart();
		address.radioButtonIsSelectedOnchangeAddressPage();
	}


	@Given("User verifies that Estimated Delivery Date is Displayed on Address Page")
	public void user_verifies_that_estimated_delivery_date_is_displayed_on_address_page() {
		login.userLogin();
		address.addToCart();
		address.estimateDeliveryAndPriceSection();


	}

	@Given("User selects an address using the radio button on Change Address page")
	public void user_selects_an_address_using_the_radio_button_on_change_address_page() {
		login.userLogin();
		address.radioButtonfunctionality();


	}


	@Given("User Delete the address from address page")
	public void user_delete_the_address_from_address_page() {
		login.userLogin();
		address.deleteAddresfunctionalityOnBothPages();

	}



	@Given("User opens the address page")
	public void user_opens_the_address_page() {
		login.userLogin();
		address.phoneNumberverfiyOnAddressPage();
	}



	@Given("User opens the checkout page address")
	public void user_opens_the_checkout_page_address() {
		login.userLogin();
		
		address.addToCart();
		address.VerifyPhoneNumberOnCheckoutAddress();

		
		
		//Git worst gowtham jjkgfdxhuhgxduytfgy7u

		
	}






}
