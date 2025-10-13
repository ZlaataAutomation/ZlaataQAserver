Feature: Add Address Page

  #===========================================================================
  # Test Case ID :: TC_UI_Zlaata_ADDP_01
  #===========================================================================
  # Scenario Description: Complete Address page
  # Expected:Address page sanity
  #===========================================================================
  @sanity
  @TC_UI_Zlaata_ADDP_01  
Scenario Outline: TC_UI_Zlaata_ADDP_01 | Verify Add New Address functionality on Address Page | "<TD_ID>"  
  Given User clicks on Add New Address and verifies address can be added  on Address Page

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_01   |
  
@sanity
@TC_UI_Zlaata_ADDP_02  
Scenario Outline: TC_UI_Zlaata_ADDP_02 | Verify clicking Save Address button without entering data | "<TD_ID>"  
  Given User clicks on Save Address button without entering any data in address fields  

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_02   |  

@sanity
@TC_UI_Zlaata_ADDP_03  
Scenario Outline: TC_UI_Zlaata_ADDP_03 | Verify Save as Default button functionality | "<TD_ID>"  
  Given User verifies the Save as Default checkbox or toggle is functional  

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_03   |  
  
@sanity
@TC_UI_Zlaata_ADDP_04  
Scenario Outline: TC_UI_Zlaata_ADDP_04 | Verify Add New Address functionality on checkoutpage | "<TD_ID>"  
  Given User clicks on Add New Address on checkcout page  

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_04   |  
  
@sanity
@TC_UI_Zlaata_ADDP_05  
Scenario Outline: TC_UI_Zlaata_ADDP_05 | Verify Edit  address functionality on Saved address page | "<TD_ID>"  
  Given User verifies Edit addresses fiunctionality on saved Address page 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_05   |   

@sanity
@TC_UI_Zlaata_ADDP_06
Scenario Outline: TC_UI_Zlaata_ADDP_06 | Verify that the Default radio button is selected on the Change Address page | "<TD_ID>"
  Given The Default radio button is selected on the Change Address page

Examples:
  | TD_ID                  |
  | TD_UI_Zlaata_ADDP_06   |
  
  @sanity
@TC_UI_Zlaata_ADDP_07  
Scenario Outline: TC_UI_Zlaata_ADDP_07 | Verify that Estimated Delivery Date is Displayed on Address Page | "<TD_ID>"  
  Given User verifies that Estimated Delivery Date is Displayed on Address Page  

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_07   |  
  
@sanity
@TC_UI_Zlaata_ADDP_08  
Scenario Outline: TC_UI_Zlaata_ADDP_08 | Verify functionality of Address Radio Button on Change Address page | "<TD_ID>"  
  Given User selects an address using the radio button on Change Address page  

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_08   |  

@sanity
@TC_UI_Zlaata_ADDP_09  
Scenario Outline: TC_UI_Zlaata_ADDP_09 | Verify Delete  address functionality | "<TD_ID>"  
  Given User Delete the address from address page
Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADDP_09   |  
  
  @sanity
  @TC_UI_Zlaata_ADDP_10
Scenario Outline: TC_UI_Zlaata_ADDP_10 | Verify that while opening address page having phone number | "<TD_ID>"
  Given User opens the address page

Examples:
  | TD_ID                |
  | TD_UI_Zlaata_ADDP_10 |

@sanity
@TC_UI_Zlaata_ADDP_11
Scenario Outline: TC_UI_Zlaata_ADDP_11 | Verify that while opening address page having phone number on checkout Page Address | "<TD_ID>"
  Given User opens the checkout page address

Examples:
  | TD_ID                |
  | TD_UI_Zlaata_ADDP_11 |
  

    
  
  
  
      