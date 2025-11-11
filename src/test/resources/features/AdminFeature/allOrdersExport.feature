Feature: Admin Export The Excel File and Check with  Export Excel File Check Wheather its Matching .

@Regression
    @TC_UI_Zlaata_APE_01
Scenario Outline: TC_UI_Zlaata_APE_01 |Verify exported Order > Placed > Order Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Order statuss and date range, then export the orders as a random file
   Then Verify that all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_01   |
  
  @Regression
  @TC_UI_Zlaata_APE_02
Scenario Outline: TC_UI_Zlaata_APE_02 |Verify exported Order > Placed > Return Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Return Order statuss and date range, then export the orders as a random file
   Then Verify that Return Order all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_02   |
 
 @Regression
 @TC_UI_Zlaata_APE_03
Scenario Outline: TC_UI_Zlaata_APE_03 |Verify exported Order > Placed > Exchange Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Exchange Order statuss and date range, then export the orders as a random file
   Then Verify that Exchange Order all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_03   |
  
 @Regression
  @TC_UI_Zlaata_APE_04
Scenario Outline: TC_UI_Zlaata_APE_04 |Verify exported Order > CANCELED > Order Page have dates within selected range.| "<TD_ID>" 
    When Admin selects All Canceled Order statuss and date range, then export the orders as a random file
   Then Verify that Canceled Order all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_04   |
  
  @Regression
  @TC_UI_Zlaata_APE_05
Scenario Outline: TC_UI_Zlaata_APE_05 |Verify exported Order > CANCELED > Return Page have dates within selected range.| "<TD_ID>" 
    When Admin selects All Canceled Return statuss and date range, then export the orders as a random file
   Then Verify that Canceled Return all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_05   |
  
  @Regression
  @TC_UI_Zlaata_APE_06
Scenario Outline: TC_UI_Zlaata_APE_06 |Verify exported Order > CANCELED > Exchange Page have dates within selected range.| "<TD_ID>" 
    When Admin selects All Canceled Exchange statuss and date range, then export the orders as a random file
   Then Verify that Canceled Exchange all created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_06   |
  
  @Regression
  @TC_UI_Zlaata_APE_07
Scenario Outline: TC_UI_Zlaata_APE_07 |Verify exported Payment Pending Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Payment Pending statuss and date range, then export the orders as a random file
   Then Verify that Payment Pending created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_07   |
 @Regression
  @TC_UI_Zlaata_APE_08
Scenario Outline: TC_UI_Zlaata_APE_08 |Verify exported Payment Refund Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Payment Refund statuss and date range, then export the orders as a random file
   Then Verify that Payment Refund created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_08   |
   @Regression
  @TC_UI_Zlaata_APE_09
Scenario Outline: TC_UI_Zlaata_APE_09 |Verify exported Payment Failed Page have dates within selected range.| "<TD_ID>" 
    When Admin selects Payment Failed statuss and date range, then export the orders as a random file
   Then Verify that Payment Failed created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_09   |
  @Regression
  @TC_UI_Zlaata_APE_10
Scenario Outline: TC_UI_Zlaata_APE_10 |Verify exported RTO Orders Page have dates within selected range.| "<TD_ID>" 
    When Admin selects RTO Orders statuss and date range, then export the orders as a random file
   Then Verify that RTO Orders created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_10   |
  @Regression
  @TC_UI_Zlaata_APE_11
Scenario Outline: TC_UI_Zlaata_APE_11 |Verify exported All Orders Page have dates within selected range.| "<TD_ID>" 
    When Admin selects All Orders statuss and date range, then export the orders as a random file
   Then Verify that All Orders created At dates in the exported file  within the selected date ranges

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_APE_11   |