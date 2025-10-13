

Feature: Home Page 
  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_Home_01
  #===========================================================================
  #ScenarioDescription : Complete HomePage
  #Expected: HomePage sanity 
  #============================================================================
  
  @sanity
  @TC_UI_Zlaata_Home_01
  Scenario Outline: TC_UI_Zlaata_Home_01 |Verify if the user is able to click on the homepage banner.|"<TD_ID>"
     
     Given User going to click available banners in home page
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_01 |
    
    @sanity
    @TC_UI_Zlaata_Home_02
  Scenario Outline: TC_UI_Zlaata_Home_02 |Verify if the user is able to click on the forward and backward arrows on the homepage banner.|"<TD_ID>"
     
     Given User clicks on forward and backward button at home page banner section
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_02 |	
      
       @TC_UI_Zlaata_Home_03
  Scenario Outline: TC_UI_Zlaata_Home_03 |Verify if the user is able to click on the banner pause button.|"<TD_ID>"
     
     Given User clicks on pause button
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_03 |
      
      @sanity
       @TC_UI_Zlaata_Home_04
  Scenario Outline: TC_UI_Zlaata_Home_04 |Verify if the user is able to click on the "Top Selling" section forward and backward arrows.|"<TD_ID>"
     
     Given User verifying top selling section
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_04 |
      
      @sanity
       @TC_UI_Zlaata_Home_05
  Scenario Outline: TC_UI_Zlaata_Home_05 |Verify if the user is able to click on the product image in the "Top Selling" section.|"<TD_ID>"
     
     Given User clicks on product image at tope selling section
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_05 |
      
      @sanity
       @TC_UI_Zlaata_Home_06
  Scenario Outline: TC_UI_Zlaata_Home_06 |Verify if the user is able to click on the "New Arrival" section forward and backward arrows.|"<TD_ID>"
     
     Given User clicks on forward and backward arrows on  new arrivals
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_06 |
      
      @sanity
       @TC_UI_Zlaata_Home_07
  Scenario Outline: TC_UI_Zlaata_Home_07 |Verify if the user is able to click on the product image in the "New Arrival" section.|"<TD_ID>"
     
     Given User clicks on product image at new arrival section
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_07 |
      
      @sanity
       @TC_UI_Zlaata_Home_08
  Scenario Outline: TC_UI_Zlaata_Home_08 |Verify if the user is able to click on the "Quick View" button in the "New Arrival" section.|"<TD_ID>"
     
     Given User clicks on home page quick view icon
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_08 |
      
      @sanity 
       @TC_UI_Zlaata_Home_09
  Scenario Outline: TC_UI_Zlaata_Home_09 |Verify if the user is able to click on  See More button on New Arrival Sectioin.|"<TD_ID>"
     
     Given User clicks on see more button on New Arrival
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_09 |
      

      

@sanity
     @TC_UI_Zlaata_Home_10
  Scenario Outline: TC_UI_Zlaata_Home_10 |Verify if the user is able to give a feedback successfully|"<TD_ID>"
     
     Given User submitting feedback
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_10 |
 
      @sanity
       @TC_UI_Zlaata_Home_11
  Scenario Outline: TC_UI_Zlaata_Home_11 |Verify if the user is able to click on the WhatsApp icon on all pages.|"<TD_ID>"
     
     Given User clicks on whats app icon
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_11 |
      
      @sanity
       @TC_UI_Zlaata_Home_12
  Scenario Outline: TC_UI_Zlaata_Home_12 |Verify if the user is able to click on the forward and backward arrows in the "Feature On" section.|"<TD_ID>"
     
     Given User veridying feature on section

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_12 |
      
      @sanity
       @TC_UI_Zlaata_Home_13
  Scenario Outline: TC_UI_Zlaata_Home_13 |Verify if the all home page fields are avilable once page load|"<TD_ID>"
     
     Given User verifying all the available headings in home page
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Home_13 |
      
      
      
    @sanity         
   @TC_UI_Zlaata_Home_14
Scenario Outline: TC_UI_Zlaata_Home_14 |Verify that the Home Page logo is displayed| "<TD_ID>"

  Given the user verifies that the logo is available on the Home Page

Examples:
  | TD_ID                  |
  | TD_UI_Zlaata_Home_14   |
  
  @sanity      
 @TC_UI_Zlaata_Home_15
Scenario Outline: TC_UI_Zlaata_Home_15 |Verify that the Home Page Thread banner is available| "<TD_ID>"

  Given the user verifies that the Thread banner is available on the Home Page

Examples:
  | TD_ID                  |
  | TD_UI_Zlaata_Home_15   |
 
  @sanity 
 @TC_UI_Zlaata_Home_16
Scenario Outline: TC_UI_Zlaata_Home_16 |Verify that the Category section is available| "<TD_ID>"

  Given the user verifies that the Category section is available on the Home Page

Examples:
  | TD_ID                  |
  | TD_UI_Zlaata_Home_16   |
 
  @sanity
  @TC_UI_Zlaata_Home_17
Scenario Outline: TC_UI_Zlaata_Home_17 |Verify that the Monsoon Banner is available| "<TD_ID>"

  Given the user verifies that the Monsoon Banner section is available on the Home Page

Examples:
  | TD_ID                |
  | TD_UI_Zlaata_Home_17 |
  
 