Feature: Home Page Banner upload verification admin panel
 
 @Home
    @Regression
     @TC_UI_Zlaata_ADM_01
Scenario Outline: TC_UI_Zlaata_ADM_01 | Verify banner upload on home page section  | "<TD_ID>"  
  Given I upload an image "sample.jpg" in admin panel
  When I verify that the homepage first banner is "Home Page Banner"

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_01   |
  
  
  @Home
    @Positive
  @Regression
    @TC_UI_Zlaata_ADM_02
Scenario Outline: TC_UI_Zlaata_ADM_02 |Verify Top Selling Section Product Display on Homepage.| "<TD_ID>"  
 
  Given admin is logged in
  When amdin copies the SKU for Product
  When admin puts this SKU at first position in Top Selling
  Then the product should appear in Top Selling on the user panel
  
Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_02   |
  
  @Home
  @Regression
  @TC_UI_Zlaata_ADM_03
Scenario Outline: TC_UI_Zlaata_ADM_03 |Verify New Arrivals Section Product Display on Homepage.| "<TD_ID>"  
 
  Given admin is logged in
  When the admin verifies the colour of the product at the first position
    And the admin adds this product to the New Arrivals section
    And the admin sorts this product to the first position in New Arrivals
    Then the product should appear in the New Arrivals section on the user application
Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_03   |
 
 @Home
 @Regression
   @TC_UI_Zlaata_ADM_04
Scenario Outline: TC_UI_Zlaata_ADM_04 |Verify Category Section Display on Website.| "<TD_ID>"  
   Given admin is logged in
    When I update the category banner with image "catagory.jpg"
    Then I should see the updated banner in the user application

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_04   |
  
  
   @TC_UI_Zlaata_ADM_05
Scenario Outline: TC_UI_Zlaata_ADM_05 |Verify bulk product upload and visibility.| "<TD_ID>" 
   Given admin is logged in
    When I upload the product excel "AllProduct.xlsx"
    Then the products from "AllProduct.xlsx" should be visible in admin panel
    And the products from "AllProduct.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_05   |
  
  @Home
    @Regression
     @TC_UI_Zlaata_ADM_06
Scenario Outline: TC_UI_Zlaata_ADM_06 |Remove product SKU from Top Selling and verify on User App.| "<TD_ID>" 
     Given admin is logged in
    When I remove the product with SKU from Top Selling
    Then I should not see product  in Top Selling section on user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_06   |
  
  @Home
  @Regression
    @TC_UI_Zlaata_ADM_07
Scenario Outline: TC_UI_Zlaata_ADM_07 |Verify all uploaded Special Timer products appear in Admin and User App.| "<TD_ID>" 
     Given admin is logged in
    When I upload the special product excel "Test.xlsx"
    Then I verify products in Admin panel with "Test.xlsx"
    And verify products from "Test.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_07   |
  
  
  @TC_UI_Zlaata_ADM_08
Scenario Outline: TC_UI_Zlaata_ADM_08 |Verify uploaded categories appear in Admin and User App.| "<TD_ID>"
     Given admin is logged in
    When I upload the categories excel "CatagoriesTest5.xlsx"
    Then I verify categories in Admin panel with "CatagoriesTest5.xlsx"
     And verify Categories from "CatagoriesTest5.xlsx" should be visible in user app
Examples:
  | TD_ID                  |
  | TD_UI_Zlaata_ADM_08   |
  
  
 
 
    @TC_UI_Zlaata_ADM_09
Scenario Outline: TC_UI_Zlaata_ADM_09 |Verify bulk uploaded Collection appear in Admin and User App.| "<TD_ID>" 
     Given admin is logged in
    When I upload the Collection excel "Test5.xlsx"
    Then I verify collection in Admin panel with "Test5.xlsx"
    And verify collection from "Test5.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADM_09   |
  
  
  