Feature: Search Bar 

  #===========================================================================
  # Test Case ID :: TC_UI_Zlaata_PDP_01
  #===========================================================================
  # Scenario Description: Complete Product details page
  # Expected: Product details page sanity
  #===========================================================================
  
  @sanity
  @TC_UI_Zlaata_SB_01
Scenario Outline: TC_UI_Zlaata_SB_01 | Verify that the user can click on the search bar. | "<TD_ID>"
  Given User verifies that they can click on the search bar.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_01  |

@sanity
@TC_UI_Zlaata_SB_02
Scenario Outline: TC_UI_Zlaata_SB_02 | Verify that the TRENDINGS and Related Products headings are displayed when the search bar is focused. | "<TD_ID>"
  Given User focuses on the search bar and verifies the display of TRENDING and Related Products headings.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_02  |

@sanity
@TC_UI_Zlaata_SB_03
Scenario Outline: TC_UI_Zlaata_SB_03 | Verify that all options under the TRENDINGS section are clickable. | "<TD_ID>"
  Given User verifies that all TRENDINGS section options are clickable.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_03  |

@sanity
@TC_UI_Zlaata_SB_04
Scenario Outline: TC_UI_Zlaata_SB_04 | Verify that the search keyword redirects to the correct page | "<TD_ID>"
  Given User enters a valid search keyword and verifies redirection to the correct page

Examples:
  | TD_ID               |
  | TD_UI_Zlaata_SB_04 |

@sanity
@TC_UI_Zlaata_SB_05
Scenario Outline: TC_UI_Zlaata_SB_05 | Verify that the Related Queries display and the sub Related Queries menu redirects correctly | "<TD_ID>"
  Given User verifies that Related Queries are displayed under the search results
  

Examples:
  | TD_ID                |
  | TD_UI_Zlaata_SB_05   |

@sanity
@TC_UI_Zlaata_SB_06
Scenario Outline: TC_UI_Zlaata_SB_06 | Verify that entering random keywords in the search bar navigates the user to the Search Suggestions page. | "<TD_ID>"
  Given User enters a random keyword and verifies navigation to the Search Suggestions page.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_06  |

@sanity
@TC_UI_Zlaata_SB_07
Scenario Outline: TC_UI_Zlaata_SB_07 | Verify that the Search History section is displayed in the search bar. | "<TD_ID>"
  Given User verifies that the Search History section is displayed in the search bar.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_07  |

@sanity
@TC_UI_Zlaata_SB_08
Scenario Outline: TC_UI_Zlaata_SB_08 | Verify that the user can clear individual keywords from the search history. | "<TD_ID>"
  Given User verifies the ability to clear individual keywords from search history.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_08  |

@sanity
@TC_UI_Zlaata_SB_09
Scenario Outline: TC_UI_Zlaata_SB_09 | Verify that copying and pasting a product name into the search bar displays the correct product in the suggestions. | "<TD_ID>"
  Given User pastes a product name into the search bar and verifies the correct product appears in suggestions.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_09 |

@sanity
@TC_UI_Zlaata_SB_10
Scenario Outline: TC_UI_Zlaata_SB_10 | Verify that the Recently Viewed section is displayed in the search bar. | "<TD_ID>"
  Given User verifies that the Recently Viewed section is displayed in the search bar.
  Examples:
    | TD_ID                |
    | TD_UI_Zlaata_SB_10  |
    


  