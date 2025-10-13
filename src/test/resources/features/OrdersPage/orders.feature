Feature:  Orders Page

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_Orders_01
  #===========================================================================
  #ScenarioDescription : Complete Orders
  #Expected: Orders sanity 
  #============================================================================
  @sanity
  @TC_UI_Zlaata_Orders_01
  Scenario Outline: TC_UI_Zlaata_Orders_01 |Verify user placing order and verifies all calculations|"<TD_ID>"
     
     Given User placing an order and verifing calculations including check out page
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Orders_01 |
        