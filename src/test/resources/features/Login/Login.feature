Feature: This is Login feature

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_Login_01
  #===========================================================================
  #ScenarioDescription : User Login scenario
  #Expected: Order placed successfully
  #============================================================================
  
  @positive
  @sanity
  @TC_UI_Zlaata_Login_01
  Scenario Outline: TC_UI_Zlaata_Login_01 |User Login Scenario|"<TD_ID>"
     
     Given User going to login in zlaata application "user"
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_01 |
      
      @positive
      @sanity
    @TC_UI_Zlaata_Login_02
  Scenario Outline: TC_UI_Zlaata_Login_02 |FirstBuy200 Coupon is displaying|"<TD_ID>"
     
     Given FirstBuy200 Coupon is displaying
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_02 |
  
  @negative
  @sanity
  @TC_UI_Zlaata_Login_03
  Scenario Outline: TC_UI_Zlaata_Login_03 |User left phone_number field Empty|"<TD_ID>"
     
     Given User left phone_number field Empty
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_03 |
      
      @negative
      @sanity
        @TC_UI_Zlaata_Login_04
  Scenario Outline: TC_UI_Zlaata_Login_04 |User entered phone_number with less than 10 digits|"<TD_ID>"
     
     Given User entered phone_number with less than 10 digits
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_04 |
      
      @sanity
        @TC_UI_Zlaata_Login_05
  Scenario Outline: TC_UI_Zlaata_Login_05 |User entered phone_number with more than 10 digits|"<TD_ID>"
     
     Given User entered phone_number with more than 10 digits
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_05 |
      
      @sanity
        @TC_UI_Zlaata_Login_06
  Scenario Outline: TC_UI_Zlaata_Login_06 |User entered phone_number with non-numeric characters|"<TD_ID>"
     
     Given User entered phone_number with non-numeric characters
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_06 |
      
      @sanity
        @TC_UI_Zlaata_Login_07
  Scenario Outline: TC_UI_Zlaata_Login_07 |User entered the New phone_number|"<TD_ID>"
     
     Given User entered the New phone_number
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_07 |
      
      @sanity
        @TC_UI_Zlaata_Login_08
  Scenario Outline: TC_UI_Zlaata_Login_08 |User entered phone_number starting with 1,2,3,4,5|"<TD_ID>"
     
     Given User entered phone_number starting with 1,2,3,4,5
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_08 |
      
      @sanity
        @TC_UI_Zlaata_Login_09
  Scenario Outline: TC_UI_Zlaata_Login_09 |User entered phone_number with Special symbol|"<TD_ID>"
     
     Given User entered phone_number with Special symbol
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_09 |
      
      @sanity
        @TC_UI_Zlaata_Login_10
  Scenario Outline: TC_UI_Zlaata_Login_10 |User entered phone_number along with space|"<TD_ID>"
     
     Given User entered phone_number along with space
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_10 |
      
      @sanity
        @TC_UI_Zlaata_Login_11
  Scenario Outline: TC_UI_Zlaata_Login_11 |User clicks on google link|"<TD_ID>"
     
     Given User clicks on google link
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_11 |
      
      @sanity
        @TC_UI_Zlaata_Login_12
  Scenario Outline: TC_UI_Zlaata_Login_12 |User clicks on facebook link|"<TD_ID>"
     
     Given User clicks on facebook link
    

    Examples: 
      | TD_ID                  |
      | TD_UI_Zlaata_Login_12 |