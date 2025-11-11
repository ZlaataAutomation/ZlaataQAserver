Feature: Verify Order Email Flow 

@Mail
     @TC_UI_Admin_OEV_01
Scenario Outline: TC_UI_Admin_OEV_01 |Verify emails for order placed, shipped, and delivered.| "<TD_ID>"  
  Given User places an order successfully
   When Order confirmation email should be received for order placed, order shipped, order delivered.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_01   |
  
  @Mail
  @TC_UI_Admin_OEV_02
Scenario Outline: TC_UI_Admin_OEV_02 |Verify emails for cancel Order From User Side and Refund.| "<TD_ID>"  
  Given User places an order successfully for cancel.
   When Order Cancellation Confirmation email should be received for order cancel from user side.
   Then Order Refund credited email should be received after admin given refund initiate.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_02   |
  
  @Mail
  @TC_UI_Admin_OEV_03
Scenario Outline: TC_UI_Admin_OEV_03 |Verify exchange order email notifications for Exchange, Shipped, and Delivered statuses.| "<TD_ID>"  
  Given User exchanges a product successfully.  
When Admin accepts the exchange request, the Order Exchange email should be received.  
Then After admin changes the status to Exchange Order Shipped and later to Exchange Order Delivered, respective emails should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_03   |
  
  @Mail
  @TC_UI_Admin_OEV_04
Scenario Outline: TC_UI_Admin_OEV_04 |Verify return order email notifications for return statuses.| "<TD_ID>"  
  Given User returns a product successfully.  
When Admin accepts the return request, the Order Return email should be received.  
Then After the admin initiates the refund, the Order Refund Credited email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_04   |
  
  @Mail
  @TC_UI_Admin_OEV_05
Scenario Outline: TC_UI_Admin_OEV_05 |Verify emails for Order Cancelled by Admin Side.| "<TD_ID>"  
  Given Admin cancels the order, the Order Cancellation Confirmation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_05   |
  
  @Mail
    @TC_UI_Admin_OEV_06
Scenario Outline: TC_UI_Admin_OEV_06 |Verify that Return Order Cancellation email is received after the user cancels the return order.| "<TD_ID>"  
  Given User cancels the return order, the Return Order Cancellation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_06   |
  
  @Mail
  @TC_UI_Admin_OEV_07
Scenario Outline: TC_UI_Admin_OEV_07 |Verify that Return Order Cancellation email is received after the admin changes the status to Product Received in Damaged State.| "<TD_ID>"  
  Given Admin cancels the return order  status to Product Received in Damaged State, the Return Order Cancellation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_07   |
  
  @Mail
    @TC_UI_Admin_OEV_08
Scenario Outline: TC_UI_Admin_OEV_08 |Verify that Return Order Rejection email is received after the admin changes the status to Rejected with reason Product Not Available.| "<TD_ID>"  
  Given Admin rejects the return order with reason Product Not Available, the Return Order Rejection email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_08   |
  
  @Mail
  @TC_UI_Admin_OEV_09
Scenario Outline: TC_UI_Admin_OEV_09 |Verify that Exchange Order Cancelled email is received after the user cancels the exchange product.| "<TD_ID>"  
  Given User cancels the exchange product, the Exchange Order Cancelled email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_09   |
  
  @Mail
  @TC_UI_Admin_OEV_10
Scenario Outline: TC_UI_Admin_OEV_10 |Verify that Exchange Out of Stock Cancellation email is received after the admin changes the status to Product Out of Stock.| "<TD_ID>"  
  Given Admin changes the exchange order status to Product Out of Stock, the Exchange Out of Stock Cancellation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_10   |
    
    @Mail
      @TC_UI_Admin_OEV_11
Scenario Outline: TC_UI_Admin_OEV_11 |Verify that Exchange Reiceived Damage State Cancellation email is received after the admin changes the status to Product Out of Stock.| "<TD_ID>"  
  Given Admin changes the exchange order status to Product Received Damage State, the Exchange Out of Stock Cancellation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_11   |
  
  @Mail
  @TC_UI_Admin_OEV_12
Scenario Outline: TC_UI_Admin_OEV_12 |Verify that Exchange Order Cancellation email is received after the admin cancels the exchange order once it has been shipped.| "<TD_ID>"  
  Given Admin cancels the exchange order after it has been shipped, the Exchange Order Cancellation email should be received.

Examples:  
  | TD_ID                  |  
  | TD_UI_Admin_OEV_12   |