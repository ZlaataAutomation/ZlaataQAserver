Feature:  Footer Section 

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_FS_01
  #===========================================================================
  #ScenarioDescription : Complete Footer Section
  #Expected: footer section sanity 
  #============================================================================
  
  @sanity
  @TC_UI_Zlaata_FS_01   
    Scenario Outline: TC_UI_Zlaata_FS_01 | Verify that Shop All footer links are clickable. | "<TD_ID>"
    Given User going to click Shop All footer links

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FS_01  |


#@TC_UI_Zlaata_FS_02
  #Scenario Outline: TC_UI_Zlaata_FS_02 | Verify that Contact Us details are displayed in the footer. | "<TD_ID>"
    #Given User verifies Contact Us details are visible in footer
#
    #Examples:
      #| TD_ID                |
      #| TD_UI_Zlaata_FS_02  |

@sanity
  @TC_UI_Zlaata_FS_02
  Scenario Outline: TC_UI_Zlaata_FS_02 | Verify that the payment logo is displayed in the footer. | "<TD_ID>"
    Given User verifies the payment logo is visible in footer

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FS_02  |

@sanity
  @TC_UI_Zlaata_FS_03
  Scenario Outline: TC_UI_Zlaata_FS_03 | Verify that social media icons are displayed in the footer. | "<TD_ID>"
    Given User verifies social media icons are visible in footer

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FS_03  |
   @sanity   
@TC_UI_Zlaata_FS_04
  Scenario Outline: TC_UI_Zlaata_FS_04 | Verify that the Zlaata email ID is displayed in the footer. | "<TD_ID>"
    Given User verifies Zlaata email ID is visible in footer

    Examples:
      | TD_ID                |
      | TD_UI_Zlaata_FS_04  |
      
      @sanity
   @TC_UI_Zlaata_FS_05 
      Scenario Outline: TC_UI_Zlaata_FS_05 | Verify that user can subscribe to the newsletter with a valid new email ID | "<TD_ID>"
      Given User enters a new valid email ID in the newsletter subscription field

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_FS_05  |
  
  @sanity
  @TC_UI_Zlaata_FS_06
  Scenario Outline: TC_UI_Zlaata_FS_06 | Verify that user cannot subscribe to the newsletter using an already subscribed email ID | "<TD_ID>"
  Given User enters an already subscribed email ID in the newsletter subscription field

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_FS_06  |
  
  @sanity
  @TC_UI_Zlaata_FS_07
  Scenario Outline: TC_UI_Zlaata_FS_07 | Verify that user cannot subscribe to the newsletter using an invalid email ID | "<TD_ID>"
  Given User enters an invalid email ID in the newsletter subscription field

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_FS_07  |
  
    