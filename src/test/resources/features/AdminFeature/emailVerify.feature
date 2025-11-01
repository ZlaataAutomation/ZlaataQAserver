Feature: Verify Order Email Flow 

     @TC_UI_Admin_OEV_01
Scenario Outline: TC_UI_Admin_OEV_01 |Verify emails for order placed, shipped, and delivered.| "<TD_ID>"  
  Given User places an order successfully
   When Order confirmation email should be received for order placed, order shipped, order delivered.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_01   |