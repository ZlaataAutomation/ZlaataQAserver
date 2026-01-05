Feature: Import Functionality Verify that different entities (Categories, Products, Product Styles, Search Keywords, Collections) can be successfully uploaded via Excel and are reflected in both Admin and User App.


Background:
    Given admin is logged in
    @Import
    @Regression
    @TC_UI_Zlaata_ADI_01
Scenario Outline: TC_UI_Zlaata_ADI_01 |Verify Categories import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the categories excel "Catagories1.xlsx"
    Then Admin verify categories in Admin panel with "Catagories1.xlsx"
    And Admin verify Categories from "Catagories1.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_01   |
  
  @Import
    @Regression
  @TC_UI_Zlaata_ADI_02
Scenario Outline: TC_UI_Zlaata_ADI_02 |Verify Collection import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the Collection excel "Collection1.xlsx"
    Then Admin verify Collection in Admin panel with "Collection1.xlsx"
    And Admin verify Collection from "Collection1.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_02   |

@Import
@Regression
  @TC_UI_Zlaata_ADI_03
Scenario Outline: TC_UI_Zlaata_ADI_03 |Verify Product Style import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the Product Style excel "Style5.xlsx"
    Then Admin verify Product Style in Admin panel with "Style5.xlsx"
    And Admin verify Product Style from "Style5.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_03   |
  
  @Import
  @Regression
   @TC_UI_Zlaata_ADI_04
Scenario Outline: TC_UI_Zlaata_ADI_04 |Verify All Product import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the All Product  excel "AllProduct.xlsx"
    Then Admin verify All Product  in Admin panel with "AllProduct.xlsx"
    And Admin verify All Product  from "AllProduct.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_04   |
  
 
  @TC_UI_Zlaata_ADI_05
Scenario Outline: TC_UI_Zlaata_ADI_05 |Verify Search Keyboard Product import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the Search Keyboard Product  excel "SearchProduct.xlsx"
    Then Admin verify Search Keyboard Product  in Admin panel with "SearchProduct.xlsx"
    And Admin verify Search Keyboard Product  from "SearchProduct.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_05   |
  
  @Import
  @Regression
  @TC_UI_Zlaata_ADI_06
Scenario Outline: TC_UI_Zlaata_ADI_06 |Verify Search Keyboard Collection import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the Search Keyboard Collection  excel "SearchCollection.xlsx"
    Then Admin verify Search Keyboard Collection  in Admin panel with "SearchCollection.xlsx"
    And Admin verify Search Keyboard Collection  from "SearchCollection.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_06   |
  
  @Import
  @Regression
  @TC_UI_Zlaata_ADI_07
Scenario Outline: TC_UI_Zlaata_ADI_07 |Verify Search Keyboard Style import uploaded appear in Admin and User App.| "<TD_ID>" 
    When Admin upload the Search Keyboard Style  excel "SearchStyle.xlsx"
    Then Admin verify Search Keyboard Style  in Admin panel with "SearchStyle.xlsx"
    And Admin verify Search Keyboard Style  from "SearchStyle.xlsx" should be visible in user app

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADI_07   |
  
    
@TC_UI_Zlaata_ADI_08
Scenario Outline: TC_UI_Zlaata_ADI_08 | Verify that uploaded track inventory appears in Admin and User App | "<TD_ID>"
    When Admin uploads the track inventory Excel file "Trackinventories.xlsx"

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_ADI_08 |


@TC_UI_Zlaata_ADI_09
Scenario Outline: TC_UI_Zlaata_ADI_09| Verify that uploaded track inventory product count for two Product  | "<TD_ID>"
    When Admin uploads the track inventory Excel file for two items "Trackinventories1.xlsx"

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_ADI_09 |
  
  
@TC_UI_Zlaata_ADI_10
Scenario Outline: TC_UI_Zlaata_ADI_10 |Verify that the newly uploaded size in Track Inventory for a product is reflected in the application| "<TD_ID>"
    When Admin uploads the Track Inventory Excel file for new size reflecting in the application "Trackinventories2.xlsx"

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_ADI_10 |

  
  
  

  