Feature: COD verification in RazorPay 

@Cod
     @TC_UI_Zlaata_COD_01
Scenario Outline: TC_UI_Zlaata_COD_01 |Verify COD visibility when two products with Gift Wrap are added.| "<TD_ID>"  
   Given User adds two products with Gift Wrap to the cart
   When In Razor Pay Gift Wrap fee amount should be displayed and COD option should not be visible.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_COD_01   |