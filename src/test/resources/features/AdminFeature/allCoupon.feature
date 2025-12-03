Feature: Verify coupon creation and availability in Admin and User App
Background:
    Given admin is logged in
 @Coupon
 @Regression
    @TC_UI_Zlaata_ADC_01
Scenario Outline: TC_UI_Zlaata_ADC_01 |Verify Admin can create a new coupon and it appears in Admin and User App.| "<TD_ID>" 
    When Admin clicks Add Coupon and enters random title and code.
   Then Verify the coupon is visible in Admin coupon list
   And Verify the same coupon is visible in User App coupon list

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_01   |

 @Coupon
@Regression
  @TC_UI_Zlaata_ADC_02
Scenario Outline: TC_UI_Zlaata_ADC_02 |Verify Admin can create a special coupon and it appears in Admin and User App.| "<TD_ID>" 
    When Admin clicks Add Coupon and enters random special title and code.
   Then Verify the special coupon is visible in Admin coupon list
   And Verify the same special coupon is visible in User App coupon list

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_02   |
  
   @Coupon
  @Regression
  @TC_UI_Zlaata_ADC_03
Scenario Outline: TC_UI_Zlaata_ADC_03 |Verify Admin can create a Specific Product Item coupon and it appears in Admin and User App.| "<TD_ID>" 
    When Admin Create Specific Product Item Coupon and enters random special title and code.
   Then Verify the same Specific Product Item coupon is visible in User App.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_03   |

   @Coupon
  @Regression
  @TC_UI_Zlaata_ADC_04
Scenario Outline: TC_UI_Zlaata_ADC_04 |Verify with One specific product and  with Normal product.| "<TD_ID>" 
    When specific coupon is created in admin with one associated product.
    Then they should be redirected to the listing page showing the linked product and see the coupon as SELECT after adding it to cart
Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_04   |

  @Coupon
  @Regression
  @TC_UI_Zlaata_ADC_05
Scenario Outline: TC_UI_Zlaata_ADC_05 |Verify with Two Specific Products Coupon Verification.| "<TD_ID>" 
    When Coupon displays both linked products and applies discount correctly after order placement
Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADC_05   |

