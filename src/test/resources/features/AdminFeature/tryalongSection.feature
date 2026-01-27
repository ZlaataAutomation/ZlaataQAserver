Feature: Try along section 


@TC_UI_Zlaata_ATL_01
Scenario Outline: TC_UI_Zlaata_ATL_01 | Verify that the Try Along section products are shuffled after refreshing the page | "<TD_ID>"

  When the user opens the application and navigates to any product details page
  Given the admin is logged in
  Then navigate to the product section  and verify that the Try Along section products are shuffled
  

Examples:
  | TD_ID                 |
  | TD_UI_Zlaata_ATL_01   |

  
@TC_UI_Zlaata_ATL_02
Scenario Outline: TC_UI_Zlaata_ATL_02 |Verify Try Along product added from Admin is displayed in application| "<TD_ID>"

  When the user opens the application and navigates to any product details page
  Given the admin is logged in
  When the admin adds a new Try Along product and the Try Along section should display the newly added product

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_ATL_02 |
  