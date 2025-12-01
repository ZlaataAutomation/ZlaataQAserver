Feature: Admin can update product influencer status and verify in User App

@Regression
@TC_UI_Zlaata_ADMI_01
Scenario Outline: TC_UI_Zlaata_ADMI_01 |Update a product as influencer and verify in User App| "<TD_ID>"  
  Given I take a random product from User App
  When I search the product in Admin, set Influencer Yes, upload image, add to collection and sort "Influ.jpg"
  Then I verify the product appears on the User App Influencer page

Examples:  
  | TD_ID                  |  
  | TD_UI_Zlaata_ADMI_01   | 