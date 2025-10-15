Feature: Admin Export The Excel File and Check with  Export Excel File Check Wheather its Matching .

Background:
    Given admin is logged in
    @TC_UI_Zlaata_APE_01
Scenario Outline: TC_UI_Zlaata_APE_01 |Verify exported products have dates within selected range.| "<TD_ID>" 
     When I export products from Order Placed page with date range "2025-10-01 - 2025-10-15" and save as "Allproduct.xlsx"
    Then I verify exported file "Allproduct.xlsx" has records within date range "2025-10-01 - 2025-10-15"

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_01   | 
 