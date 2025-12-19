Feature: COD verification in RazorPay 

@AdminOrder
     @TC_UI_Zlaata_ADMINORDER_01
Scenario Outline: TC_UI_Zlaata_ADMINORDER_01 |Verify that admin can able to place the Prepaid order.| "<TD_ID>"  
   Given Verify that the admin can select customer and address successfully.
   When Verify that the admin can place a Prepaid order and complete payment successfully.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADMINORDER_01   |
  
  @AdminOrder
     @TC_UI_Zlaata_ADMINORDER_02
Scenario Outline: TC_UI_Zlaata_ADMINORDER_02 |Verify that admin can able to place the COD order.| "<TD_ID>"  
   Given Verify that the admin can select customer and address successfully for COD.
   When Verify that the admin can place a COD order and complete payment successfully.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADMINORDER_02   |