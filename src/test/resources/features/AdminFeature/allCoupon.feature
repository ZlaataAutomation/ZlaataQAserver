Feature: Verify coupon creation and availability in Admin and User App
Background:
    Given admin is logged in
 
    @TC_UI_Zlaata_ADC_01
Scenario Outline: TC_UI_Zlaata_ADC_01 |Verify Admin can create a new coupon and it appears in Admin and User App.| "<TD_ID>" 
    When Admin clicks Add Coupon and enters random title and code.
   Then Verify the coupon is visible in Admin coupon list
   And Verify the same coupon is visible in User App coupon list

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_01   |

  @TC_UI_Zlaata_ADC_02
Scenario Outline: TC_UI_Zlaata_ADC_02 |Verify Admin can create a special coupon and it appears in Admin and User App.| "<TD_ID>" 
    When Admin clicks Add Coupon and enters random special title and code.
   Then Verify the special coupon is visible in Admin coupon list
   And Verify the same special coupon is visible in User App coupon list

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_02   |


