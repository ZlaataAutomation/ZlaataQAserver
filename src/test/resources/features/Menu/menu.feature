Feature:  Menus section 

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_Menu_01
  #===========================================================================
  #ScenarioDescription : Complete Menu
  #Expected: Menu sanity 
  #============================================================================
  @sanity
  @TC_UI_Zlaata_Menus_01
  Scenario Outline: TC_UI_Zlaata_Menus_01 |Verify that the user is able to click the Homepage header menu.|"<TD_ID>"
     
     Given User clicks on home page header menu
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_01 |
    
    @sanity
      @TC_UI_Zlaata_Menus_02
  Scenario Outline: TC_UI_Zlaata_Menus_02 |Verify that the user is able to click the "New Arrival" page header menu.|"<TD_ID>"
     
     Given User clicks on newAriivals header
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_02 |
    
    @sanity
      @TC_UI_Zlaata_Menus_03
  Scenario Outline: TC_UI_Zlaata_Menus_03 |Verify that the user is able to click the "New Arrival" hover image.|"<TD_ID>"
     
     Given User clicks on new arrivals suggestion
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_03 |
    
    @sanity
      @TC_UI_Zlaata_Menus_04
  Scenario Outline: TC_UI_Zlaata_Menus_04 |Verify that the user is able to click the "Sale" header menu.|"<TD_ID>"
     
     Given User clicks on sale menu
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_04 |
    
    @sanity
      @TC_UI_Zlaata_Menus_05
  Scenario Outline: TC_UI_Zlaata_Menus_05 |Verify that the user is able to click the "Boss Lady" hover image.|"<TD_ID>"
     
     Given User clicks on boss lady suggestions
	
    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_05 |
    
    @sanity
      @TC_UI_Zlaata_Menus_06
  Scenario Outline: TC_UI_Zlaata_Menus_06 |Verify that the user is able to click the "GET UPDATE" banner.|"<TD_ID>"
     
     Given User clicks on get update 
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_06 |
    
    @sanity
      @TC_UI_Zlaata_Menus_07
  Scenario Outline: TC_UI_Zlaata_Menus_07 |Verify that the user is able to click any category in the "Shop" dropdown.|"<TD_ID>"
     
     Given User clicks on shop category
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_07 |
    
    @sanity
      @TC_UI_Zlaata_Menus_08
  Scenario Outline: TC_UI_Zlaata_Menus_08 |Verify that the user is able to click any collection in the "Shop" dropdown.|"<TD_ID>"
     
     Given User clicks on shop collection
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_08 |
    
    @sanity
      @TC_UI_Zlaata_Menus_09
  Scenario Outline: TC_UI_Zlaata_Menus_09 |Verify that the user is able to click any style in the "Shop" dropdown.|"<TD_ID>"
     
     Given User clicks on shop styles
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_09 |
    
    @sanity
      @TC_UI_Zlaata_Menus_10
  Scenario Outline: TC_UI_Zlaata_Menus_10 |Verify that the user is able to click the popshop header menu.|"<TD_ID>"
     
     Given User clicks on Pop shop
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Menus_10 |
    