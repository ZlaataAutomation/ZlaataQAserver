Feature: Verify secondary color display for product


 @Regression
    @TC_UI_Admin_PSColor_01
Scenario Outline: TC_UI_Admin_PSColor_01 |Check Product secondary color appears across user page.| "<TD_ID>" 
    Given I Randomly choose a product and add that product with a secondary color
   Then the secondary color should display on the product details, add to cart popup, wishlist, and checkout pages

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_PSColor_01   |
  