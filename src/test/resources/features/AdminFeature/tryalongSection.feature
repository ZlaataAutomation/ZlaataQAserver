Feature: Try along section 


@TC_UI_Zlaata_ATL_01
Scenario Outline: TC_UI_Zlaata_ATL_01 | Verify that the Try Along section products are shuffled after refreshing the page | "<TD_ID>"

  When the user opens the application and navigates to any product details page
  Given the admin is logged in
  Then navigate to the product section  and verify that the Try Along section products are shuffled
  

Examples:
  | TD_ID                 |
  | TD_UI_Zlaata_ATL_01   |
