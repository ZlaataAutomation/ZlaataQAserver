Feature: Verify product order with Gift Wrap, Coupon, Gift Card, Gift Card Amount and Thread 


@Calculation
     @TC_UI_Zlaata_Calculate_01
Scenario Outline: TC_UI_Zlaata_Calculate_01 |Verify price breakup for P1 with Gift Wrap, Coupon, Gift Card Amount, and Thread.| "<TD_ID>"  
   Given User adds Product P1 with Gift Wrap, Coupon, Gift Card Amount, and Thread
   When Verify Razorpay popup details and My Orders price breakup after placing the order.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_Calculate_01   |
  
  @Calculation
     @TC_UI_Zlaata_Calculate_02
Scenario Outline: TC_UI_Zlaata_Calculate_02 |Verify price breakup for P1 & P2 with Gift Wrap, Coupon, Gift Card Amount, and Thread.| "<TD_ID>"  
   Given User adds Product P1 & P2 with Gift Wrap, Coupon, Gift Card Amount, and Thread
   When Verify Product P1 & P2 checkout page, Razorpay popup details and My Orders price breakup after placing the order.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_Calculate_02   |
  
  @Calculation
     @TC_UI_Zlaata_Calculate_03
Scenario Outline: TC_UI_Zlaata_Calculate_03 |Verify price breakup for P1 & CP & AP with Gift Wrap, Coupon, Gift Card Amount, and Thread.| "<TD_ID>"  
   Given User adds Product P1 & CP & AP with Gift Wrap, Coupon, Gift Card Amount, and Thread
   When Verify Product P1 & CP & AP checkout page, Razorpay popup details and My Orders price breakup after placing the order.

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_Calculate_03   |