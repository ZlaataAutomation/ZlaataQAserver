package stepDef;

import java.io.IOException;

import context.TestContext;
import io.cucumber.java.en.*;
import pages.AdminGoogleMerchantPage;
import pages.AdminPanelAllImortPage;

public class AdminPanelAllImportStepDef {

	TestContext testContext;
	AdminPanelAllImortPage adminGoogle;



	public AdminPanelAllImportStepDef(TestContext context) {
		testContext = context;
		adminGoogle = testContext.getPageObjectManager().getAdminPanelAllImortPage();
	}

	@When("Admin upload the categories excel {string}")
	public void admin_upload_the_categories_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.uploadTheCategoriesImport(excelPath);
	}
	@Then("Admin verify categories in Admin panel with {string}")
	public void admin_verify_categories_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyCategoriesInAdmin(excelPath); 
	}
	@Then("Admin verify Categories from {string} should be visible in user app")
	public void admin_verify_categories_from_should_be_visible_in_user_app(String filePath) throws IOException, InterruptedException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyCatagoriesInUserApp(excelPath);
	}

	//Import Collection
	@When("Admin upload the Collection excel {string}")
	public void admin_upload_the_collection_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.bulkBploadCollectionExcel(excelPath); 

	}
	@Then("Admin verify Collection in Admin panel with {string}")
	public void admin_verify_collection_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyCollectionsInAdmin(excelPath); 

	}
	@Then("Admin verify Collection from {string} should be visible in user app")
	public void admin_verify_collection_from_should_be_visible_in_user_app(String filePath) throws IOException, InterruptedException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyCollectionsInUserApp(excelPath); 

	}
	//Import Product Style
	@When("Admin upload the Product Style excel {string}")
	public void admin_upload_the_product_style_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.uploadTheProductStyleImport(excelPath); 
	}
	@Then("Admin verify Product Style in Admin panel with {string}")
	public void admin_verify_product_style_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyProductStyleInAdmin(excelPath); 
	}
	@Then("Admin verify Product Style from {string} should be visible in user app")
	public void admin_verify_product_style_from_should_be_visible_in_user_app(String filePath) throws IOException, InterruptedException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyProductStyleInUserApp(excelPath); 
	}

	//Import All Product
	@When("Admin upload the All Product  excel {string}")
	public void admin_upload_the_all_product_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.ImportTheProductExcel(excelPath);
	}
	@Then("Admin verify All Product  in Admin panel with {string}")
	public void admin_verify_all_product_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyProductsInAdmin(excelPath);
	}
	@Then("Admin verify All Product  from {string} should be visible in user app")
	public void admin_verify_all_product_from_should_be_visible_in_user_app(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifyProductsInUserApp(excelPath);
	}


	//Import Search product

	@When("Admin upload the Search Keyboard Product  excel {string}")
	public void admin_upload_the_search_keyboard_product_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.ImportSearchKeyboardProductExcel(excelPath);
	}
	@Then("Admin verify Search Keyboard Product  in Admin panel with {string}")
	public void admin_verify_search_keyboard_product_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardProductsInAdmin(excelPath);
	}
	@Then("Admin verify Search Keyboard Product  from {string} should be visible in user app")
	public void admin_verify_search_keyboard_product_from_should_be_visible_in_user_app(String filePath) throws InterruptedException, IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardProductInUserApp(excelPath);   
	}

	//Import Sear keyword Collection
	@When("Admin upload the Search Keyboard Collection  excel {string}")
	public void admin_upload_the_search_keyboard_collection_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.ImportSearchKeyboardCollectionExcel(excelPath); 
	}

	@Then("Admin verify Search Keyboard Collection  in Admin panel with {string}")
	public void admin_verify_search_keyboard_collection_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardCollectionInAdmin(excelPath); 
	}
	@Then("Admin verify Search Keyboard Collection  from {string} should be visible in user app")
	public void admin_verify_search_keyboard_collection_from_should_be_visible_in_user_app(String filePath) throws InterruptedException, IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardCollectionInUserApp(excelPath);  
	}

	//Import Search Style Keywords
	@When("Admin upload the Search Keyboard Style  excel {string}")
	public void admin_upload_the_search_keyboard_style_excel(String filePath) {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.ImportSearchKeyboardStyleExcel(excelPath); 
	}

	@Then("Admin verify Search Keyboard Style  in Admin panel with {string}")
	public void admin_verify_search_keyboard_style_in_admin_panel_with(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardStyleInAdmin(excelPath); 
	}
	@Then("Admin verify Search Keyboard Style  from {string} should be visible in user app")
	public void admin_verify_search_keyboard_style_from_should_be_visible_in_user_app(String filePath) throws InterruptedException, IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.verifySearchKeyboardStyleInUserApp(excelPath); 
	}



	//track inventories import for one sku Product two size

	@When("Admin uploads the track inventory Excel file {string}")
	public void admin_uploads_the_track_inventory_excel_file(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.VerifyImporttrackinventories(excelPath);

	}


	//track  inventories import for two  sku Product one size

	@When("Admin uploads the track inventory Excel file for two items {string}")
	public void admin_uploads_the_track_inventory_excel_file_for_two_items(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.VerifyImportTrackInventoryMultipleStages(excelPath);
	}



	@When("Admin uploads the Track Inventory Excel file for new size reflecting in the application {string}")
	public void admin_uploads_the_track_inventory_excel_file_for_new_size_reflecting_in_the_application(String filePath) throws IOException {
		String excelPath = System.getProperty("user.dir") + "/src/test/resources/ImportFile/" + filePath;
		adminGoogle.VerifyImportTrackInventoryaddNewsizereflectinginapplication(excelPath);
	}


























































}
