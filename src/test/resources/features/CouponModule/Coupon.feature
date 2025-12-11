Feature: Coupon Module

  #===========================================================================
  # Test case ID :: TC_UI_Zlaata_CP
  #===========================================================================
  # Scenario Description: Complete Coupon functionality
  #===========================================================================

  @TC_UI_Zlaata_CP_01
  Scenario Outline: TC_UI_Zlaata_CP_01 | Verify login popup is displayed when coupon apply button is clicked before user login | "<TD_ID>"
    Given User clicks on the Apply button in the coupon text box
    And User should get login popup

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_CP_01  |

  @TC_UI_Zlaata_CP_02
  Scenario Outline: TC_UI_Zlaata_CP_02 | Verify Apply Feedback Coupon before submitting feedback | "<TD_ID>"
    Given User is on the checkout page with items in the cart
    And User enters the Feedback coupon code
    And User clicks on the Apply button in the coupon popup
    Then Error message should appear

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_CP_02  |

  @TC_UI_Zlaata_CP_03
  Scenario Outline: TC_UI_Zlaata_CP_03 | Verify Apply FIRSTBUY Coupon before placing the first order | "<TD_ID>"
    Given User is on the checkout page with items in the cart
    And User enters the FIRSTBUY coupon code
    And User clicks on the Apply button in the coupon popup
    Then Coupon should be applied successfully

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_CP_03  |

  @TC_UI_Zlaata_CP_04
  Scenario Outline: TC_UI_Zlaata_CP_04 | Verify Apply Newsletter Coupon before subscribing to newsletter | "<TD_ID>"
    Given User is on the checkout page with items in the cart
    And User enters the Newsletter coupon code
    And User clicks on the Apply button in the coupon popup
    Then Error message should appear indicating newsletter subscription is required

    Examples:
      | TD_ID               |
      | TD_UI_Zlaata_CP_04  |
      
      @TC_UI_Zlaata_CP_05
  Scenario Outline: TC_UI_Zlaata_CP_05 | Verify applying Feedback Coupon after submitting feedback | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User subscribes to the feedback section

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_05  |
      
      @TC_UI_Zlaata_CP_06
  Scenario Outline: TC_UI_Zlaata_CP_06 | Verify applying FIRSTBUY Coupon after placing the first order | "<TD_ID>"
  Given User Logged in and navigate to checkout page
  And User enters the coupon code
  And User clicks on the Apply button
  Then Error message should appear indicating the coupon is not applicable after the first order

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_06  |
    
    @TC_UI_Zlaata_CP_07
Scenario Outline: TC_UI_Zlaata_CP_07 | Verify applying Newsletter Coupon after subscribing to the newsletter | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User has subscribed to the newsletter
  And User has verified their email address in Account settings
  When User enters the Newsletter coupon code again
  And User clicks on the Apply button in the coupon popup
  Then Coupon should be applied successfully

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_07  |
    
      @TC_UI_Zlaata_CP_08
Scenario Outline: TC_UI_Zlaata_CP_08 | Verify applying a Normal Coupon with percentage discount | "<TD_ID>"
  Given User adding lowest product to the cart
  And User try to apply normal percentage coupon and calculating
  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_08  |
    
    
    
      @TC_UI_Zlaata_CP_09
Scenario Outline: TC_UI_Zlaata_CP_09 |Verify Apply Normal Coupon with fixed amount| "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User enters a valid Normal Coupon code with a fixed amount discount
  And User clicks on the Apply button in the coupon popup
   Then Normal Coupon with fixed amount   should be applied successfully

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_09  |
    
    
    
     @TC_UI_Zlaata_CP_10
Scenario Outline: TC_UI_Zlaata_CP_10 | Verify Apply Special Coupon with percentage discount | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  #And User enters a valid Special Coupon code with a percentage discount
  #And User clicks on the Apply button in the coupon popup
   Then Special Coupon with percentage discount should be applied successfully
  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_10  |
    
    
    @TC_UI_Zlaata_CP_11
Scenario Outline: TC_UI_Zlaata_CP_11 |Verify Apply Special Coupon with fixed amount | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User enters a valid Special Coupon code with a fixed amount discount
  #And User clicks on the Apply button in the coupon popup
   Then Special Coupon with fixed amount   should be applied successfully

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_11  |
    
    
@TC_UI_Zlaata_CP_12
Scenario Outline: TC_UI_Zlaata_CP_12 |Verify Available Coupons when minimum purchase is reached| "<TD_ID>"
  Given User is on the checkout page with items in the cart
  When User click on view coupon button
  Then Only the eligible coupons should be displayed

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_12  |
    
    @TC_UI_Zlaata_CP_13
Scenario Outline: TC_UI_Zlaata_CP_13 |Verify Unlock More Coupons when product does not meet minimum purchase| "<TD_ID>"
  Given User is on the checkout page with items in the cart
  When User click on view coupon button
  Then A message should be displayed indicating the need to add more items to unlock additional coupons

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_13  |
    
    @TC_UI_Zlaata_CP_14
Scenario Outline: TC_UI_Zlaata_CP_14 | Verify error message when user enters wrong coupon code | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  When User enters an invalid coupon code
  And User clicks on the Apply button in the coupon popup
  Then An error message should be displayed indicating the coupon code is invalid

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_14  |
    
@TC_UI_Zlaata_CP_15
Scenario Outline: TC_UI_Zlaata_CP_15 |Verify user can login in coupon popup| "<TD_ID>"
  Given User is on the checkout page with items in the cart before login
  And User logs in using the coupon popup Apply button

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_15  |

    
    @TC_UI_Zlaata_CP_16
Scenario Outline: TC_UI_Zlaata_CP_16 | Verify Apply and Remove button in coupon text box and in coupon pop up | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And user checking apply and remove button

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_16  |
    
    @TC_UI_Zlaata_CP_17
Scenario Outline: TC_UI_Zlaata_CP_17 | Verify that after refreshing the page, the applied coupon amount disappears or changes. | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User applies a valid coupon code
   And User clicks on the Apply button in the coupon popup
  When User refreshes the page
  Then The applied coupon discount should disappear or be recalculated as per eligibility

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_17  |
    
    
    @TC_UI_Zlaata_CP_18
Scenario Outline: TC_UI_Zlaata_CP_18 | Verify same applied coupon amount is displayed on Checkout, Address, and Payment pages | "<TD_ID>"
  Given User is on the checkout page with items in the cart
  And User applies a valid coupon code
  Then The same coupon discount amount should be displayed consistently on Checkout, Address, and Payment pages

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_CP_18  |
    
    
    
        
    @hihi
    @TC_UI_Zlaata_CP_19
 Scenario Outline: TC_UI_Zlaata_CP_19 |Verify the functionality for FirstBuy coupon in User Application.| "<TD_ID>"
		Given I sign up with a new phone number
    When user adds a random product to the cart.
    Then apply the FirstBuy coupon and verify if the coupon is applied or not.

  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_CP_19    |
      
      
      
    @hihi   
   @TC_UI_Zlaata_CP_20
 Scenario Outline: TC_UI_Zlaata_CP_20 |Verify the functionality for news letter coupon in User Application.| "<TD_ID>"   
 
   Given I sign up with a new phone number
    And I subscribe to the newsletter with a random email
    Then I add a product to the cart and apply the coupon
    
    Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_CP_20    |
      
    @hihi        
   @TC_UI_Zlaata_CP_21
 Scenario Outline: TC_UI_Zlaata_CP_21 |Verify the functionality for feedback  coupon in User Application.| "<TD_ID>"   
 
   Given I sign up with a new phone number
    And I subscribe to the feedback with a random email
    Then I add a products to the cart and apply the feedback coupon
    
    Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_CP_21    |
      
