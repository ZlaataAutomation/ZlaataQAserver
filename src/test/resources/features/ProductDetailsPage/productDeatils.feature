
Feature: Product Details Page 

  #===========================================================================
  # Test Case ID :: TC_UI_Zlaata_PDP_01
  #===========================================================================
  # Scenario Description: Complete Product details page
  # Expected: Product details page sanity
  #===========================================================================
  
  @sanity
 @TC_UI_Zlaata_PDP_01
Scenario Outline: TC_UI_Zlaata_PDP_01 | Verify Product price is displaying on product details page | "<TD_ID>"
  Given User Verifies Display of Product Price on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_01   |
    
    @sanity
     @TC_UI_Zlaata_PDP_02
Scenario Outline: TC_UI_Zlaata_PDP_02 | Verify  Discounted Price Calculating on Product details page | "<TD_ID>"
  Given User Verifies Discount price calculating  on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_02   |
    
    @sanity
  @TC_UI_Zlaata_PDP_03
Scenario Outline: TC_UI_Zlaata_PDP_03 | Verify product images are changeable using arrow button | "<TD_ID>"
  Given User Verifies images are chaneable using arrow button
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_03   |

@sanity
@TC_UI_Zlaata_PDP_04
Scenario Outline: TC_UI_Zlaata_PDP_04 | Verify Wishlist Button Functionality on Product details page | "<TD_ID>"
  Given User Verifies Wishlist Button Functionality on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_04   |

@sanity
@TC_UI_Zlaata_PDP_05
Scenario Outline: TC_UI_Zlaata_PDP_05 | Verify "Best Price" Functionality | "<TD_ID>"
  Given User Verifies "Best Price" Functionality
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_05   |

@sanity
@TC_UI_Zlaata_PDP_06
Scenario Outline: TC_UI_Zlaata_PDP_06 | Verify Color Selection Functionality | "<TD_ID>"
  Given User Verifies Color Selection Functionality
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_06   |

@sanity
@TC_UI_Zlaata_PDP_07
Scenario Outline: TC_UI_Zlaata_PDP_07 | Verify Color Section Dropdown Arrow | "<TD_ID>"
  Given User Verifies Color Section Dropdown Arrow
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_07   |

@sanity
@TC_UI_Zlaata_PDP_08
Scenario Outline: TC_UI_Zlaata_PDP_08 | Verify Size Chart Availability | "<TD_ID>"
  Given User Verifies Size Chart Availability
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_08   |

@sanity
@TC_UI_Zlaata_PDP_09
Scenario Outline: TC_UI_Zlaata_PDP_09 | Verify Size Selection Functionality | "<TD_ID>"
  Given User Verifies Size Selection Functionality
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_09   |

@sanity
@TC_UI_Zlaata_PDP_10
Scenario Outline: TC_UI_Zlaata_PDP_10 | Verify that the category name is displayed on the Product Details Page | "<TD_ID>"
 Given User verifies that the category name is displayed on the Product Details Page

  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_10   |

@sanity
@TC_UI_Zlaata_PDP_11
Scenario Outline: TC_UI_Zlaata_PDP_11 | Verify "Add to Cart" Button Functionality on Product details page | "<TD_ID>"
  Given User Verifies "Add to Cart" Button Functionality on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_11   |

@sanity
@TC_UI_Zlaata_PDP_12
Scenario Outline: TC_UI_Zlaata_PDP_12 | Verify "Buy Now" Button Functionality on Product details page | "<TD_ID>"
  Given User Verifies Buy Now  Functionality on Product details page 
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_12   |

@sanity
@TC_UI_Zlaata_PDP_13
Scenario Outline: TC_UI_Zlaata_PDP_13 | Verify Delivery Pincode Textbox Functionality | "<TD_ID>"
  Given User Verifies Delivery Pincode Textbox Functionality
  Examples:.h6
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_13   |

@sanity
@TC_UI_Zlaata_PDP_14
Scenario Outline: TC_UI_Zlaata_PDP_14 | Verify "Try Along" Section Availability | "<TD_ID>"
  Given User Verifies "Try Along" Section Availability
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_14   |

@sanity
@TC_UI_Zlaata_PDP_15
Scenario Outline: TC_UI_Zlaata_PDP_15 | Verify Selection of "Try Along" Product | "<TD_ID>"
  Given User Verifies Selection of "Try Along" Product
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_15   |

@sanity
@TC_UI_Zlaata_PDP_16
Scenario Outline: TC_UI_Zlaata_PDP_16 | Verify Eye Icon Click on "Try Along" Product | "<TD_ID>"
  Given User Verifies Eye Icon Click on "Try Along" Product
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_16   |

@sanity
@TC_UI_Zlaata_PDP_17
Scenario Outline: TC_UI_Zlaata_PDP_17 |Verify View All Button in More for You and Suggested for You Sections| "<TD_ID>"
  Given User on Product details page
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_17   |

@sanity
@TC_UI_Zlaata_PDP_18
Scenario Outline: TC_UI_Zlaata_PDP_18 | Verify Dropdown Arrows for Sections (Product Description, Composition & Care, etc.) | "<TD_ID>"
  Given User Verifies Dropdown Arrows for Sections (Product Description, Composition & Care, etc.)
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_18   |

@sanity
@TC_UI_Zlaata_PDP_19
Scenario Outline: TC_UI_Zlaata_PDP_19 | Verify "Click Here" Link in Return & Exchange Section | "<TD_ID>"
  Given User Verifies "Click Here" Link in Return & Exchange Section
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_19   |

@sanity
@TC_UI_Zlaata_PDP_20
Scenario Outline: TC_UI_Zlaata_PDP_20 | Verify "View All" Button Clickability on Product details page | "<TD_ID>"
  Given User Verifies "View All" Button Clickability on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_20   |

@sanity
@TC_UI_Zlaata_PDP_21
Scenario Outline: TC_UI_Zlaata_PDP_21 | Verify Review Calculation on Product details page | "<TD_ID>"
  Given User Verifies Review Calculation on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_21   |

@sanity
@TC_UI_Zlaata_PDP_22
Scenario Outline: TC_UI_Zlaata_PDP_22 | Verify "Write a Review" Button Clickability on Product details page | "<TD_ID>"
  Given User Verifies Write a Review Button Clickability on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_22   |

@sanity
@TC_UI_Zlaata_PDP_23
Scenario Outline: TC_UI_Zlaata_PDP_23 | Verify Display of "More for You" Section on Product details page | "<TD_ID>"
  Given User Verifies Display of "More for You" Section on Product details page #excel need to update
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_23   |

@sanity
@TC_UI_Zlaata_PDP_24
Scenario Outline: TC_UI_Zlaata_PDP_24 | Verify Display of "Suggested for You" Section | "<TD_ID>"
  Given User Verifies Display of "Suggested for You" Section
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_24   |

@sanity
@TC_UI_Zlaata_PDP_25
Scenario Outline: TC_UI_Zlaata_PDP_25 | Verify Display of "Recently Viewed" Section | "<TD_ID>"
  Given User Verifies Display of Recently Viewed Section
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_25   |

@sanity 
@TC_UI_Zlaata_PDP_26
Scenario Outline: TC_UI_Zlaata_PDP_26 | Verify that on Review Popup without Enter All Data Click on Submit | "<TD_ID>"
  Given User Verifies that on Review Popup without Enter All Data Click on Submit
  Examples:
    | TD_ID                  |
    | TD_UI_Zlaata_PDP_26   |
  
  @sanity
 @TC_UI_Zlaata_PDP_27
Scenario Outline: TC_UI_Zlaata_PDP_27 | Verify that user can submit a review after entering all data | "<TD_ID>"
  Given User on Product details page and gives a review

Examples:
  | TD_ID                |
  | TD_UI_Zlaata_PDP_27 |
 
    
