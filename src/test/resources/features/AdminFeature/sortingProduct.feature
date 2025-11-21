Feature: Admin Sorting The Product in Admin Panel Verify Successful Sort In User App.

@Sorting
@Stage
@Regression
   @TC_UI_Zlaata_ASP_01
Scenario Outline: TC_UI_Zlaata_ASP_01 |Verify category product sorting between Admin Panel and User Application.| "<TD_ID>"  
 Given admin is logged in
    When I sort the products in a category
    Then I should see the same product appear first in the User Application category 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ASP_01   | 
  
@Sorting
  @Regression
  @TC_UI_Zlaata_ASP_02
Scenario Outline: TC_UI_Zlaata_ASP_02 |Verify Collection product sorting between Admin Panel and User Application.| "<TD_ID>"  
 Given admin is logged in
    When I sort the products in a Collection
    Then I should see the same product appear first in the User Application Collection 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ASP_02   | 

@Sorting  
  @Stage
  @Regression
    @TC_UI_Zlaata_ASP_03
Scenario Outline: TC_UI_Zlaata_ASP_03 |Verify Styles product sorting between Admin Panel and User Application.| "<TD_ID>"  
 Given admin is logged in
    When I sort the products in a Styles
    Then I should see the same product appear first in the User Application Styles 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ASP_03   | 
  

    @TC_UI_Zlaata_ASP_04
Scenario Outline: TC_UI_Zlaata_ASP_04 |Verify Micro Page product sorting between Admin Panel and User Application.| "<TD_ID>"  
 Given admin is logged in
    When I sort the products in a Micro Page
    Then I should see the same product appear first in the User Application Micro Page 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ASP_04   | 
  
  @Sorting
  @Stage
  @Regression
   @TC_UI_Zlaata_ASP_05
Scenario Outline: TC_UI_Zlaata_ASP_05 |Verify All product sorting between Admin Panel and User Application.| "<TD_ID>"  
 Given admin is logged in
    When I sort the products in a All Product
    Then I should see the same product appear first in the User Application All Product 

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ASP_05   | 