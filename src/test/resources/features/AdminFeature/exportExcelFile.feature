Feature: Admin Export The Excel File and Check with Veiw All Export Excel File Check Wheather its Matching .


   @TC_UI_Zlaata_EEF_01
Scenario Outline: TC_UI_Zlaata_EEF_01 |Verify New Product added match between two exports.| "<TD_ID>" 
     Given admin is logged in
    When I export products from first page with date range "2025-09-18 - 2025-09-24" and save as "NewProductAdd1.xlsx"
    And I export products from second page with date range "2025-09-18 - 2025-09-24" and save as "NewProductAdd2.xlsx"
    Then I verify both exported files "NewProductAdd1.xlsx" and "NewProductAdd2.xlsx" have matching product names

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_EEF_01   | 
  
  
  @TC_UI_Zlaata_EEF_02
Scenario Outline: TC_UI_Zlaata_EEF_02 |Verify Active Products match between two exports.| "<TD_ID>" 
     Given admin is logged in
    When I export active products from first page with date range "2025-09-12 - 2025-09-24" and save as "ActiveProducts1.xlsx"
    And I export active products from second page with date range "2025-09-12 - 2025-09-24" and save as "ActiveProducts2.xlsx"
    Then I verify both exported active products files "ActiveProducts1.xlsx" and "ActiveProducts2.xlsx" have matching product names

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_EEF_02   | 
  
   @TC_UI_Zlaata_EEF_03
Scenario Outline: TC_UI_Zlaata_EEF_03 |Verify Sold Out Products match between two exports.| "<TD_ID>" 
     Given admin is logged in
    When I export sold out products from first page with date range "2025-09-10 - 2025-09-23" and save as "SoldOutProducts1.xlsx"
    And I export sold out products from second page with date range "2025-09-10 - 2025-09-23" and save as "SoldOutProducts2.xlsx"
    Then I verify both exported sold out products files "SoldOutProducts1.xlsx" and "SoldOutProducts2.xlsx" have matching product names

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_EEF_03   | 
  
  @TC_UI_Zlaata_EEF_04
Scenario Outline: TC_UI_Zlaata_EEF_04 |Verify Inactive Products match between two exports.| "<TD_ID>" 
     Given admin is logged in
    When I export inactive products from first page with date range "2025-09-16 - 2025-09-23" and save as "InactiveProducts1.xlsx"
    And I export inactive products from second page with date range "2025-09-16 - 2025-09-23" and save as "InactiveProducts2.xlsx"
    Then I verify both exported inactive products files "InactiveProducts1.xlsx" and "InactiveProducts2.xlsx" have matching product names

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_EEF_04   | 