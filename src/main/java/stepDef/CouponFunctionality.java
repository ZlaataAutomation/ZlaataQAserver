package stepDef;

import java.util.concurrent.TimeoutException;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import pages.CouponPage;
import pages.LoginPage;
import pages.NegativeSignupPages;
import pages.ProductDetailsPage;

public class CouponFunctionality {
	TestContext testContext;
	CouponPage coupon;
	NegativeSignupPages signup;
	LoginPage login;
	ProductDetailsPage pDP;


	public CouponFunctionality(TestContext context) {
		testContext = context;
		coupon = testContext.getPageObjectManager().getCouponPage();
		signup = testContext.getPageObjectManager().getNegativeSignupPages();
		login = testContext.getPageObjectManager().getLoginPage();
		pDP = testContext.getPageObjectManager().getProductDetailsPage();
	}

	//1st Login popup
	@Given("User clicks on the Apply button in the coupon text box")
	public void user_clicks_on_the_apply_button_in_the_coupon_text_box() {
		try {
			coupon.clickApplyButton();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	@And("User should get login popup")
	public void user_should_get_login_popup() {
		try {

			coupon.verifyLoginPopupAppears();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	@Given("User is on the checkout page with items in the cart")
	public void user_is_on_the_checkout_page_with_items_in_the_cart() throws Exception {
		try {
			signup.signUp();
			coupon.CheckOutNavigation();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}
//this mew
	@And("User enters the Feedback coupon code")
	public void user_enters_the_feedback_coupon_code() {
		try {
			coupon.enterCouponCode();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}
	@And("User clicks on the Apply button in the coupon popup")
	public void user_clicks_on_the_apply_button_in_the_coupon_popup() {
		try {
			coupon.clickApplyButtonPop();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}
	@Then("Error message should appear")
	public void error_message_should_appear() {
		try {
			coupon.verifyErrorMessageDisplayed("You are not eligible for this coupon");
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	//3 FirstBuy before order
	@Given("User enters the FIRSTBUY coupon code")
	public void user_enters_the_firstbuy_coupon_code() throws TimeoutException {
		try {
			coupon.FirstBuy();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	@Then("Coupon should be applied successfully")
	public void coupon_should_be_applied_successfully() {
		try {
			coupon.verifyAppliedMessage("Coupon applied");
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	//4 Newletter before
	@Given("User enters the Newsletter coupon code")
	public void user_enters_the_newsletter_coupon_code() throws TimeoutException {
		try {
			coupon.newsLetterCoupon();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}
	@Then("Error message should appear indicating newsletter subscription is required")
	public void error_message_should_appear_indicating_newsletter_subscription_is_required() {
		try {
			coupon.verifyErrorMessageDisplayed("You are not eligible for this coupon");
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}
	//5 Feedback after
	@Given("User subscribes to the feedback section")
	public void user_subscribes_to_the_feedback_section() {
		try {
			coupon.feedBack();
			coupon.verifyEmailInAccountSettingsAndSubmitOTP();
			coupon.applyFeedbackCouponAtCheckout();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}

	@Given("User Logged in and navigate to checkout page")
	public void user_logged_in_and_navigate_to_checkout_page() {
		try {
			login.userLogin();
			pDP.buyNow(Hooks.getScenario());
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}

	}


	@And("User enters the coupon code")
	public void user_enters_the_coupon_code() {
		try {
			coupon.enterFirstBuyCouponCode();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}



	@And("User clicks on the Apply button")
	public void user_clicks_on_the_apply_button() {
		try {
			coupon.clickApplyButtonPop();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}




	@Then("Error message should appear indicating the coupon is not applicable after the first order")
	public void error_message_should_appear_indicating_the_coupon_is_not_applicable_after_the_first_order() {
		try {
			coupon.firstBuyAfterorderPlace("This coupon can only be applied on your very first purchase. It's no longer valid.");
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}


	@And("User has subscribed to the newsletter")
	public void user_has_subscribed_to_the_newsletter() {
		try {
			coupon.subscribeNewsLetter();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}



	@And("User has verified their email address in Account settings")
	public void user_has_verified_their_email_address_in_account_settings() {
		try {
			coupon.verifyEmailInAccountSettingsAndSubmitOTP();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}


	@When("User enters the Newsletter coupon code again")
	public void user_enters_the_newsletter_coupon_code_again() {
		try {
			coupon.entercodeAfterNewsletter();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}



	//8th test case
	//	@Given("User adding lowest product to the cart")
	//	public void user_adding_lowest_product_to_the_cart() throws Exception {
	//		try {
	//			signup.signUp();			
	//
	//		}
	//		catch (Exception e) {
	//			ExceptionTracker.capture(e); // Capture the exact exception
	//			throw e; // re-throw so test still fails
	//		}
	//	}


	@And("User try to apply normal percentage coupon and calculating")
	public void user_try_to_apply_normal_percentage_coupon_and_calculating() throws Exception {
		try {
			coupon.validateCouponPercentage();
		}
		catch (Exception e) {
			ExceptionTracker.capture(e); // Capture the exact exception
			throw e; // re-throw so test still fails
		}
	}


	//gowtham


	//14		

	@When("User enters an invalid coupon code")
	public void user_enters_an_invalid_coupon_code() {

		coupon.invalidCouponCode();

	}



	@Then("An error message should be displayed indicating the coupon code is invalid")
	public void an_error_message_should_be_displayed_indicating_the_coupon_code_is_invalid() {
		coupon.invalidcouponCodeValidationMessage("Invalid Coupon Code, Please enter Valid Code");

	}

	//18
	@And("User applies a valid coupon code")
	public void user_applies_a_valid_coupon_code() throws TimeoutException {

		coupon.appliedValidCouponCode();
	}


	@Then("The same coupon discount amount should be displayed consistently on Checkout, Address, and Payment pages")
	public void the_same_coupon_discount_amount_should_be_displayed_consistently_on_checkout_address_and_payment_pages() {

		coupon.sameCouponAmountDisplayingInAllThreePages();


	}



	//17

	@When("User refreshes the page")
	public void user_refreshes_the_page() {

		coupon.refreshThePage();


	}


	@Then("The applied coupon discount should disappear or be recalculated as per eligibility")
	public void the_applied_coupon_discount_should_disappear_or_be_recalculated_as_per_eligibility()
	{
		coupon.checkTheCouponAmountAfterRefresh();
	}



	@Given("User enters a valid coupon code in the coupon text box")
	public void user_enters_a_valid_coupon_code_in_the_coupon_text_box() {

	}



	//16



	@And("user checking apply and remove button")
	public void user_checking_apply_and_remove_button() {
		coupon.CouponAppliedValidationMessage();
		coupon.removeCouponFunctionality("Coupon removed successfully!");
		coupon.CouponRemovedOrNot();
	}





	//15


	@Given("User is on the checkout page with items in the cart before login")
	public void user_is_on_the_checkout_page_with_items_in_the_cart_before_login() {

		coupon.addPropdcutToCart();
	}




	@Given("User logs in using the coupon popup Apply button")
	public void user_logs_in_using_the_coupon_popup_apply_button() throws TimeoutException {


		coupon.loginusingApplyButtonOnCouponPopup();


	}

	//13

	@When("User click on view coupon button")
	public void user_click_on_view_coupon_button() {
		coupon.clickOnViewCouponButton();

	}

	@Then("A message should be displayed indicating the need to add more items to unlock additional coupons")
	public void a_message_should_be_displayed_indicating_the_need_to_add_more_items_to_unlock_additional_coupons() {
		coupon.unlockMoreCoupons();

	}


	//12
	@Then("Only the eligible coupons should be displayed")
	public void only_the_eligible_coupons_should_be_displayed() {
		coupon.availableCoupon();
	}
	//11



	@Given("User enters a valid Special Coupon code with a fixed amount discount")
	public void user_enters_a_valid_special_coupon_code_with_a_fixed_amount_discount() throws TimeoutException {

		coupon.specialCouponCodeWithfixedAmount();
	}



	@Then("Special Coupon with fixed amount   should be applied successfully")
	public void special_coupon_with_fixed_amount_should_be_applied_successfully() {

		coupon.fixedAmountAppliedSuccessfullForSpecialCoupon();

	}
	//10

	//	@Given("User enters a valid Special Coupon code with a percentage discount")
	//	public void user_enters_a_valid_special_coupon_code_with_a_percentage_discount() {
	//
	//		coupon.specialCouponCodeWithPercentageAmount();
	//
	//	}


	@Then("Special Coupon with percentage discount should be applied successfully")
	public void special_coupon_with_percentage_discount_should_be_applied_successfully() {

		coupon.PercentageAmountAppliedSuccessfullForSpecialCoupon();
	}


	//09


	@Given("User enters a valid Normal Coupon code with a fixed amount discount")
	public void user_enters_a_valid_normal_coupon_code_with_a_fixed_amount_discount() throws TimeoutException {

		coupon.NormalCouponCodeWithfixedAmount();
	}

	@Then("Normal Coupon with fixed amount   should be applied successfully")
	public void normal_coupon_with_fixed_amount_should_be_applied_successfully() {

		coupon.fixedAmountAppliedSuccessfullForNormalCoupon();

	}

	//19
	
	@Given("I sign up with a new phone number")
	public void i_sign_up_with_a_new_phone_number() throws TimeoutException {
		coupon.signupAllCoupon();
	}

	@When("user adds a random product to the cart.")
	public void userAddsRandomProductToCart() throws Exception {
			coupon.afterSignUpFirstBuy();
	}

	@Then("apply the FirstBuy coupon and verify if the coupon is applied or not.")
	public void applyFirstBuyCouponAndVerify() {
		try {
			coupon.verifyAppliedMessageForFirstBuy("Coupon applied");  // Verify coupon application
		} catch (Exception e) {
			ExceptionTracker.capture(e);  // Log the exception details
			throw e;  // Re-throw the exception to ensure the test fails
		}
	}

	//20


	
	@Given("I subscribe to the newsletter with a random email")
	public void i_subscribe_to_the_newsletter_with_a_random_email() {
		coupon.subscribeForNewsletter();
	}
	@Then("I add a product to the cart and apply the coupon")
	public void i_add_a_product_to_the_cart_and_apply_the_coupon() {
		coupon.verifyEmailAndNewsletterSubscription();
	}

	//21

	@Given("I subscribe to the feedback with a random email")
	public void i_subscribe_to_the_feedback_with_a_random_email() {
		
		coupon.subscribeForfeedback();
		
	}

		@Then("I add a products to the cart and apply the feedback coupon")
	public void i_add_a_products_to_the_cart_and_apply_the_feedback_coupon() {
		coupon.verifyEmailAndNewsforfeedback();
	}











}
