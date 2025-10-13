Feature: Product Listing Page 

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_PLP_01
  #===========================================================================
  #ScenarioDescription : Complete Product Listing
  #Expected: Product listing sanity 
  #============================================================================
  @sanity
  @TC_UI_Zlaata_PLP_01
  Scenario Outline: TC_UI_Zlaata_PLP_01 |Verify that the "Home" text link on the Product Listing page is clickable.|"<TD_ID>"
     
     Given User clicks on home text after landing product listing page
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_01 |
      
    @sanity
      @TC_UI_Zlaata_PLP_02
  Scenario Outline: TC_UI_Zlaata_PLP_02 |Verify that the heading is available on the Product Listing page.|"<TD_ID>"
     
     Given User verifies weather the landed page heading is same as link
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_02 |
    
    @sanity
    @TC_UI_Zlaata_PLP_03
  Scenario Outline: TC_UI_Zlaata_PLP_03 |Verify that pagination functionality is available.|"<TD_ID>"
     
     Given User verifies pagination
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_03 |
      
      @sanity
      @TC_UI_Zlaata_PLP_04
  Scenario Outline: TC_UI_Zlaata_PLP_04 |Verify that the pagination arrows are clickable.|"<TD_ID>"
     
     Given User clicks on pagination forward and backward buttons
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_04 |
      
      @sanity
      @TC_UI_Zlaata_PLP_05
  Scenario Outline: TC_UI_Zlaata_PLP_05 |Verify that when the user clicks on a page number, the pagination functionality works.|"<TD_ID>"
     
     Given User clicks on available page number in product listing page
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_05 |
      
      @sanity
      @TC_UI_Zlaata_PLP_06
  Scenario Outline: TC_UI_Zlaata_PLP_06 |Verify that the "Show Filter" button is clickable.|"<TD_ID>"
     
     Given User clicks on show filter button
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_06 |
      
      @sanity
      @TC_UI_Zlaata_PLP_07
  Scenario Outline: TC_UI_Zlaata_PLP_07 |Verify that the "Sort By" option is clickable.|"<TD_ID>"
     
     Given User clicks on sort by option
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_07 |
      
      @sanity
      @TC_UI_Zlaata_PLP_08
  Scenario Outline: TC_UI_Zlaata_PLP_08 |Verify that all basic filter options are working.|"<TD_ID>"
     
     Given User verifies basic filters are working
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_08 |
      
     @sanity
      @TC_UI_Zlaata_PLP_09
  Scenario Outline: TC_UI_Zlaata_PLP_09 |Verify that all sorting options are working.|"<TD_ID>"
     
     Given User verifies sort by filters

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_09 |
      
      @sanity
      @TC_UI_Zlaata_PLP_10
  Scenario Outline: TC_UI_Zlaata_PLP_10 |Verify that the "Wishlist" button is working.|"<TD_ID>"
     
     Given User clicks on wish list icon
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_10 |
      
      @sanity
      @TC_UI_Zlaata_PLP_11
  Scenario Outline: TC_UI_Zlaata_PLP_11 |Verify that the "Add to Cart" button is working.|"<TD_ID>"
     
     Given User clicks on add to cart button
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_PLP_11 |
    