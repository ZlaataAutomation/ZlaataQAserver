Feature: Checkout Page

  #===========================================================================
  # Test Case ID :: TC_UI_Zlaata_COP_01
  #===========================================================================
  # Scenario Description: Complete Checkout Page
  # Expected: Checkout page sanity
  #===========================================================================
  
  @sanity
  @TC_UI_Zlaata_COP_01
  Scenario Outline: TC_UI_Zlaata_COP_01 |Verify Bag Item Count Display|"<TD_ID>"
    Given User Verifies Bag Item Count
     

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_01     |

@sanity
  @TC_UI_Zlaata_COP_02
  Scenario Outline: TC_UI_Zlaata_COP_02 |Verify Display of Wishlist Button|"<TD_ID>"
    Given User Verifies Display of Wishlist Button

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_02     |

@sanity
  @TC_UI_Zlaata_COP_03
  Scenario Outline: TC_UI_Zlaata_COP_03 |Verify Display of Delete Button|"<TD_ID>"
    Given User Verifies Display of Delete Button

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_03     |

  @TC_UI_Zlaata_COP_04
  Scenario Outline: TC_UI_Zlaata_COP_04 |Verify That User Can Change Product Size|"<TD_ID>"
    Given User Verifies That User Can Change Product Size

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_04     |

@sanity
  @TC_UI_Zlaata_COP_05
  Scenario Outline: TC_UI_Zlaata_COP_05 |Verify User Can Increase or Decrease Product Quantity|"<TD_ID>"
    Given User Verifies User Can Increase or Decrease Product Quantity

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_05     |

@sanity
   @TC_UI_Zlaata_COP_06
  Scenario Outline: TC_UI_Zlaata_COP_06 |Verify User Can Add New Product|"<TD_ID>"
    Given User Verifies User Can Add New Product

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_06     |

@sanity
   @TC_UI_Zlaata_COP_07
  Scenario Outline: TC_UI_Zlaata_COP_07 Verify That Adding Product to Bag Count is Displaying or Not Above Bag Icon|"<TD_ID>"
    Given User Verifies That Adding Product to Bag Count is Displaying or Not Above Bag Icon

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_07     |

@sanity
  @TC_UI_Zlaata_COP_08
  Scenario Outline: TC_UI_Zlaata_COP_08 |Verify That Adding New Product or Deleting Product Count Increases or Decreases|"<TD_ID>"
    Given User Verifies That Adding New Product or Deleting Product Count Increases or Decreases

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_08     |
      
      @sanity
  @TC_UI_Zlaata_COP_09
  Scenario Outline: TC_UI_Zlaata_COP_09 |Verify Checkout page calculation|"<TD_ID>"
    Given User Verifies Checkout page calculation

    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_COP_09     |
      
      @sanity
      @TC_UI_Zlaata_COP_10
Scenario Outline: TC_UI_Zlaata_COP_10 |Verify Accessories, Recently Viewed, and Top Selling buttons on Checkout page| "<TD_ID>"
  Given the user verifies that on the Checkout page the Accessories button, the Recently Viewed button, and the Top Selling button are all visible and clickable

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_COP_10 |

  @sanity
@TC_UI_Zlaata_COP_11
Scenario Outline: TC_UI_Zlaata_COP_11 | Verify Validation Message for Invalid Gift Card Number | "<TD_ID>"
  Given the user verifies that on the Checkout page, when an invalid gift card number is entered, a validation message is displayed

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_COP_11 |

   @sanity   
@TC_UI_Zlaata_COP_12
Scenario Outline: TC_UI_Zlaata_COP_12 | Verify Validation Message in Gift Wrapping Popup when Mandatory Fields Are Not Entered | "<TD_ID>"
  Given the user verifies that on the Gift Wrapping popup, the mandatory fields are left empty

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_COP_12 |

   @sanity     
@TC_UI_Zlaata_COP_13
Scenario Outline: TC_UI_Zlaata_COP_13 |Verify Validation Message in Gift Wrapping Popup if the user enters fewer characters in Description or Sender Name| "<TD_ID>"
  Given the user verifies that on the Gift Wrapping popup, the user enters fewer characters for all fields

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_COP_13 |
    
   @sanity   
 @TC_UI_Zlaata_COP_14
Scenario Outline: TC_UI_Zlaata_COP_14 | Verify Gift Card Balance Display when Correct Gift Card is Entered | "<TD_ID>"
  Given the user verifies that a valid gift card number is entered in the Gift Card field

  Examples:
    | TD_ID               |
    | TD_UI_Zlaata_COP_14 |
 

      

  