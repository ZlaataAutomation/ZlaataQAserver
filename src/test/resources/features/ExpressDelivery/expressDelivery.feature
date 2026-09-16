Feature: This is Express Delivery feature

  #===========================================================================
  #Test case ID :: @TC_UI_Zlaata_ExpressDelivery_01
  #===========================================================================
  #ScenarioDescription : Complete  Express Delivery
  #Expected:  Express Delivery sanity 
  #============================================================================
 
 @exp
 @TC_UI_Zlaata_ExpressDelivery_01
Scenario Outline: TC_UI_Zlaata_ExpressDelivery_01|Verify that Express delivery functionality is working fine| "<TD_ID>"

Given the user verifies that Express delivery functionality is working fine

Examples:
  | TD_ID                 |
  | TD_UI_Zlaata_ExpressDelivery_01 |
  
   @exp
  
 @TC_UI_Zlaata_ExpressDelivery_02
Scenario Outline: TC_UI_Zlaata_ExpressDelivery_02|Verify Express Delivery functionality in Sort By section on Product Listing Page | "<TD_ID>"

  Given the user verifies Express Delivery functionality in the Product Listing page Sort By section

Examples:
  | TD_ID                              |
  | TD_UI_Zlaata_ExpressDelivery_02    |
  
     @exp
 @TC_UI_Zlaata_ExpressDelivery_03
Scenario Outline: TC_UI_Zlaata_ExpressDelivery_03|Verify  Delivery Day functionality on Product Listing Page.| "<TD_ID>"

  Given the user verifies  Delivery day functionality in the Product Listing page 

Examples:
  | TD_ID                              |
  | TD_UI_Zlaata_ExpressDelivery_03    |