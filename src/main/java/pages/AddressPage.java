package pages;

import org.openqa.selenium.NoSuchElementException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import objectRepo.AddressPageObjRepo;
import utils.Common;

public  final class AddressPage  extends AddressPageObjRepo

{

	public AddressPage(WebDriver driver) 
	{
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public void addToCart() {
		Common.waitForElement(5);
		click(bagIcon);
	try {
		if (!products.isEmpty()) {
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutpageContinueButton);
//
//			// Optionally wait for visibility before clicking
//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.elementToBeClickable(checkoutpageContinueButton));
//
//			// Click the button
//			checkoutpageContinueButton.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkoutpageContinueButton);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutpageContinueButton);
		}
		
		else {
		
			Common.waitForElement(1);
			Actions actions = new Actions(driver);
			actions.moveToElement(shopMenu);
			actions.moveToElement(category).click().build().perform();
			List<WebElement> addProduct = driver.findElements(By.xpath("//button[@class='product_list_cards_btn']"));
			Collections.shuffle(addProduct);

			if (!addProduct.isEmpty()) {
				WebElement randomProduct = addProduct.get(0);
				actions.moveToElement(randomProduct).click().build().perform();
				Common.waitForElement(2);
				click(addToCart);
				Common.waitForElement(5);
				click(bagIcon);
				Common.waitForElement(5);

				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkoutpageContinueButton);

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutpageContinueButton);				
			}
			
		}
	} catch (Exception e) {
		System.out.println(e);
	}
	}
	
	public void newAddressData() {

		Common.waitForElement(2);
		String value = Common.getValueFromTestDataMap("Name Address");
		System.out.println("Testing name: " + value);
		type(nameTextBox, value);
		String enteredname = nameTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered name in application : " + enteredname);

		String value1 = Common.getValueFromTestDataMap("Email Id Address");
		System.out.println("Testing Email ID: " + value1);
		type(emailIDTextBox, value1);
		String enteredname1 = emailIDTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered email id in application : " + enteredname1);
//
//		String value2 = Common.getValueFromTestDataMap("Mobile Number");
//		System.out.println("Testing Mobile number: " + value2);
//		type(phoneNumberTextBox, value2);
//		String enteredname2 = phoneNumberTextBox.getAttribute("value");  // Fixed here
//		System.out.println("Entered Phonumber in application : " + enteredname2);

		String value3 = Common.getValueFromTestDataMap("Address");
		System.out.println("Testing Address : " + value3);
		type(addressDetailsTextBox, value3);
		String enteredname3 = addressDetailsTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered Address in application : " + enteredname3);

		String value4 = Common.getValueFromTestDataMap("Street");
		System.out.println("Testing Street : " + value4);
		type(streetTextBox, value4);
		String enteredname4 = streetTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered Street in application : " + enteredname4);


		String value5 = Common.getValueFromTestDataMap("Pincode");
		System.out.println("Testing Pincode : " + value5);
		type(pinCodeTextBox, value5);
		String enteredname5 = pinCodeTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered pincode in application : " + enteredname5);
		Common.waitForElement(2);

		String value6 = Common.getValueFromTestDataMap("Locality");
		System.out.println("Testing town : " + value6);
		type(townTextBox, value6);
		String enteredname6 = townTextBox.getAttribute("value");  // Fixed here
		System.out.println("Entered town in application : " + enteredname6);

		Common.waitForElement(2);

		String enteredname7 = district.getText();  // Fixed here
		System.out.println("Entered district in application : " + enteredname7);


		String enteredname8 = state.getText();  // Fixed here
		System.out.println("Entered state in application : " + enteredname8);

		click(saveAddressButton);
		Common.waitForElement(5);
	}

	public void validateAllFields() {
	    verify("Name", validationName.getText(), "Name should be between 3 and 50 characters.");
//	    verify("Phone", validationPhoneNumber.getText(), "Please enter a valid 10-digit phone number.");
	    verify("Email", validationMailID.getText(), "Please enter a valid email address.");
	    verify("Address", validationAddressTxtBox.getText(), "Address must be between 3 and 50 characters.");
	    verify("Street", validationStreet.getText(), "Street must be between 3 and 50 characters.");
	    verify("Pincode", validationPincode.getText(), "Please enter a valid 6-digit pincode.");
	    verify("Locality", valiadtionLocality.getText(), "Locality/Town must be between 3 and 50 characters.");
	    verify("District", validationDistrict.getText(), "District is required.");
	    verify("State", validationState.getText(), "State is required.");
	}

	public void verify(String field, String actual, String expected) {
	    if (actual.equals(expected)) {
	        System.out.println("✅ " + field + ": " + actual);
	    } else {
	        System.out.println("❌ " + field + " mismatch. Expected: " + expected + ", Actual: " + actual);
	    }
	}

	public boolean isElementDisplayed(WebElement element) {
	    try {
	        return element.isDisplayed();
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
	
	// F: 01
	public void newAddresOnSavedAddresPage() 
	{
		    click(profile);

		    Actions action = new Actions(driver);
		    action.moveToElement(addressSideMenuButton).click().build().perform();
		    System.out.println("✅ Navigated to Address section.");

		    boolean isNewAddressAdded = false;

		    try {
		        if (addAddressButtonOnCheckoutPage.isDisplayed()) {
		            click(addAddressButtonOnCheckoutPage);
		            System.out.println("⚠️ No address found on address page. Adding first address from address form.");
		            newAddressData();
		            isNewAddressAdded = true;
		        }
		    } catch (NoSuchElementException e) {
		        // Element not present, continue
		    }

		    if (!isNewAddressAdded) {
		        try {
		            if (addNewAddressButtonOnAddresspage.isDisplayed()) {
		                System.out.println("✅ 'Add New Address' button is visible on Address Page.");
		                click(addNewAddressButtonOnAddresspage);
		                newAddressData();
		                isNewAddressAdded = true;
		            }
		        } catch (NoSuchElementException e) {
		            // Element not present, continue
		        }
		    }

		    if (!isNewAddressAdded) {
		        System.out.println("❌ Neither 'No address'  button nor 'Add New Address' button was visible.");
		    }
		}

	//F: 2
	public void leftAllMandatory() {
	    try {
	        click(profile);
	        new Actions(driver).moveToElement(addressSideMenuButton).click().perform();
	        System.out.println("✅ Navigated to Address section.");

	        if (isElementDisplayed(addAddressButtonOnCheckoutPage)) {
	            click(addAddressButtonOnCheckoutPage);
	        } else if (isElementDisplayed(addNewAddressButtonOnAddresspage)) {
	            click(addNewAddressButtonOnAddresspage);
	        }

	        click(saveAddressButton); // try saving empty form
	        validateAllFields(); // validate all errors

	    } catch (Exception e) {
	        System.out.println("❌ Error: " + e.getMessage());
	    }
	}



			//F: 3
			public void defaultAddress() {
			    click(profile);
			    new Actions(driver).moveToElement(addressSideMenuButton).click().perform();
//			    Common.waitForElement(2);

			    List<WebElement> addressCards = driver.findElements(By.xpath("//div[@class='address_card Cls_addr_data_section']"));
			    int addressCount = addressCards.size();
			    System.out.println("📦 Total addresses found: " + addressCount);

			    // CASE 1: No address found
			    if (addressCount == 0) {
			        if (addAddressButtonOnCheckoutPage.isDisplayed()) {
			            System.out.println("⚠️ No address found. Adding new address...");
			            click(addAddressButtonOnCheckoutPage);
			            newAddressData();
			            Common.waitForElement(2);

			            List<WebElement> updatedList = driver.findElements(By.xpath("//div[@class='address_card Cls_addr_data_section']"));
			            if (updatedList.size() == 1) {
			                System.out.println("✅ New address added. This only address is now set as default.");
			            } else {
			                System.out.println("❌ Address not added correctly. Current count: " + updatedList.size());
			            }
			        } else {
			            System.out.println("❌ 'Add Address' button is not visible.");
			        }
			        return;
			    }

			    // CASE 2: Only one address found
			    if (addressCount == 1) {
			        WebElement onlyCard = addressCards.get(0);
			        try {
			            String name = onlyCard.findElement(By.xpath(".//h4[contains(@class,'address__name')]")).getText().trim();
			            System.out.println("✅ Only one address found: " + name + ". It is considered as default.");
			        } catch (Exception e) {
			            System.out.println("⚠️ Unable to read single address name.");
			        }
			        return;
			    }

			    // CASE 3: Multiple addresses – Change default
			    WebElement oldDefaultCard = null;
			    String oldDefaultName = "";

			    for (WebElement card : addressCards) {
			        try {
			            if (card.findElement(By.xpath(".//span[contains(@class,'address__mode')]")).getText().contains("Default")) {
			                oldDefaultCard = card;
			                oldDefaultName = card.findElement(By.xpath(".//h4[contains(@class,'address__name')]")).getText().trim();
			                System.out.println("🟡 Current default address: " + oldDefaultName);
			                break;
			            }
			        } catch (Exception ignored) {}
			    }

			    // Try to change default to a different address
			    for (WebElement card : addressCards) {
			        if (card.equals(oldDefaultCard)) continue;
			        try {
			            card.findElement(By.xpath(".//button[contains(@class,'Cls_address_card_edit_btn')]")).click();
			            Common.waitForElement(2);
			            

			            WebElement checkbox = new WebDriverWait(driver, Duration.ofSeconds(5))
			                    .until(ExpectedConditions.elementToBeClickable(By.name("address_default_checkbox")));
			            if (!checkbox.isSelected()) checkbox.click();

			            Common.waitForElement(2);
			            driver.findElement(By.xpath("//button[contains(@class,'Cls_address_form_submit_btn')]")).click();
			            Common.waitForElement(2);
			            break;
			        } catch (Exception e) {
			            System.out.println("❌ Failed to change default address: " + e.getMessage());
			        }
			    }

			    // Verify new default
			    List<WebElement> updatedCards = driver.findElements(By.xpath("//div[@class='address_card Cls_addr_data_section']"));
			    for (WebElement card : updatedCards) {
			        try {
			            if (card.findElement(By.xpath(".//span[contains(@class,'address__mode')]")).getText().contains("Default")) {
			                String newDefaultName = card.findElement(By.xpath(".//h4[contains(@class,'address__name')]")).getText().trim();
			                System.out.println("✅ New default address is now: " + newDefaultName);
			                break;
			            }
			        } catch (Exception ignored) {}
			    }
			}
			
			//F:4
			public void addNewAddressOnCheckoutPage() {
				
				
				
//				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkoutpageContinueButton);
//
//				((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkoutpageContinueButton);
				

				boolean isNewAddressAdded = false;

			    try {
			        if (checkoutPageAddres.isDisplayed()) {
			            System.out.println("🟡 No address found on checkout page. Adding a new address.");
			            click(checkoutPageAddres);
			            newAddressData();
			            isNewAddressAdded = true;
			            System.out.println("✅ First address added successfully.");
			        }
			    } catch (Exception e) {
			        System.out.println("⚠️ checkoutPageAddres element not found or not visible: " + e.getMessage());
			    }

			    if (!isNewAddressAdded) {
			        try {
			            if (addNewAddressOnChekoutPage.isDisplayed()) {
			                System.out.println("🟢 Existing address found. Adding another new address.");
			                click(addNewAddressOnChekoutPage);
			                newAddressData();
			                isNewAddressAdded = true;
			                System.out.println("✅ Another address added successfully.");
			            }
			        } catch (Exception e) {
			            System.out.println("⚠️ addNewAddressOnChekoutPage not found: " + e.getMessage());
			        }
			    }

			    if (!isNewAddressAdded) {
			        System.out.println("❌ Couldn't add a new address using checkout page options.");
			    }

			    // Now handling Change Address modal open-close flow
			    try {
			       
			        System.out.println("🔄 Clicking 'Change Address' button.");
			        click(changeAddressButtonOnCheckoutpage);

			        System.out.println("➕ Clicking 'Add Address' inside Change Address modal.");
			        click(addAddressButtonOnChangAddressPage);

			        System.out.println("❌ Closing the Change Address modal.");
			        click(AddaddresscloseButton);

			        System.out.println("🔄 Re-opening Change Address modal.");
			        click(changeAddressButtonOnCheckoutpage);

			        System.out.println("✅ Clicking 'Continue' button on Change Address modal.");
			        click(continueButtonOnChangAddressPage);
			        
			        System.out.println("✅ Clicking 'Continue' button on checkout page address .");
			        click(addressPageContinueOrderButton);
			        

			        System.out.println("🎯 Address handling flow on checkout page completed.");
			    } catch (Exception e) {
			        System.out.println("❌ Error during Change Address modal flow: " + e.getMessage());
			    }
			}
			
			//F : 5
			public void  editAddressfunctionalityOnSavedAddressPage () {

				Actions action = new Actions(driver);
				newAddresOnSavedAddresPage();
				

				try {

					List <WebElement>  savedAddresspageEditButton = driver.findElements(By.xpath("//div[@class='address_card_name_row_wrap']//button[@class='address_card_edit_btn Cls_address_card_edit_btn']"));
					if (!savedAddresspageEditButton.isEmpty()) {
						Collections.shuffle(savedAddresspageEditButton);
						WebElement savedAddEditBtn = savedAddresspageEditButton.get(0);
						action.moveToElement(savedAddEditBtn).click().perform();
						System.out.println("Clicked on a random edit button.");
					} 
					else if (addAddressButtonOnCheckoutPage.isDisplayed()) {
						click(addAddressButtonOnCheckoutPage);
						System.out.println("Add New Address button clicked.");
					} 
					else {
						System.out.println("No Add or Edit buttons available.");
					}
				} catch (NoSuchElementException e) {
					System.out.println("Element not found: " + e.getMessage());
				}

				System.out.println("Existing Data in Address Field");
				System.out.println("---------------------------------------------");
				String enteredname = nameTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered name in application : " + enteredname);


				String enteredname1 = emailIDTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered email id in application : " + enteredname1);


				String enteredname2 = phoneNumberTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered Phonumber in application : " + enteredname2);


				String enteredname3 = addressDetailsTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered Address in application : " + enteredname3);


				String enteredname4 = streetTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered Street in application : " + enteredname4);



				String enteredname5 = pinCodeTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered pincode in application : " + enteredname5);
				Common.waitForElement(2);

				String enteredname6 = townTextBox.getAttribute("value");  // Fixed here
				System.out.println("Entered town in application : " + enteredname6);

				Common.waitForElement(2);

				String enteredname7 = district.getText();  // Fixed here
				System.out.println("Entered district in application : " + enteredname7);


				String enteredname8 = state.getText();  // Fixed here
				System.out.println("Entered state in application : " + enteredname8);
				System.out.println("---------------------------------------------");
				System.out.println("New Data and updated Data from excel");
				System.out.println("---------------------------------------------");
				nameTextBox.clear();
				emailIDTextBox.clear();
//				phoneNumberTextBox.clear();
				addressDetailsTextBox.clear();
				streetTextBox.clear();
				pinCodeTextBox.clear();
				townTextBox.clear();
				newAddressData();
				System.out.println("---------------------------------------------");

			}
			
			//F: 6	
			public void radioButtonIsSelectedOnchangeAddressPage() {

//				Common.waitForElement(2);
//				((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 700);");

//				
//			    click(checkoutpageContinueButton);
			    boolean isNewAddressAdded = false;

			    try {
			        if (checkoutPageAddres.isDisplayed()) {
			            System.out.println("🟡 No address found on checkout page. Adding a new address.");
			            click(checkoutPageAddres);
			            newAddressData();
			            isNewAddressAdded = true;
			            System.out.println("✅ First address added successfully.");
			        }
			    } catch (Exception e) {
			        System.out.println("⚠️ checkoutPageAddres element not found or not visible: " + e.getMessage());
			    }

			    if (!isNewAddressAdded) {
			        try {
			            if (addNewAddressOnChekoutPage.isDisplayed()) {
			                System.out.println("🟢 Existing address found. Adding another new address.");
			                click(addNewAddressOnChekoutPage);
			                newAddressData();
			                isNewAddressAdded = true;
			                System.out.println("✅ Another address added successfully.");
			            }
			        } catch (Exception e) {
			            System.out.println("⚠️ addNewAddressOnChekoutPage not found: " + e.getMessage());
			        }
			    }

				click(changeAddressButtonOnCheckoutpage);
				System.out.println("Clicked on 'Change Address' button.");


				List<WebElement> addressRadios = driver.findElements(By.xpath("//input[@class='change_address_input']"));

				boolean isDefaultAddressSelected = false;

				for (WebElement radio : addressRadios) {
					if (radio.isSelected()) {
						isDefaultAddressSelected = true;
						break;
					}
				}

				// Step 4: Print result
				if (isDefaultAddressSelected) {
					System.out.println("✅ A default address is already selected.");
				} else {
					System.out.println("❌ No default address is selected.");
				}

			}
		


			//F : 7
			public void estimateDeliveryAndPriceSection() {
//				    Common.waitForElement(2);
				    // Step 1: Check if Delivery Address heading is visible
				    try {
				        WebElement deliveryAddressHeading = driver.findElement(By.xpath("//h5[normalize-space()='Delivery address']"));
				        if (deliveryAddressHeading.isDisplayed()) {
				            System.out.println("✅ Delivery Address section is visible.");
				        }
				    } catch (NoSuchElementException e) {
				        System.out.println("🟡 Delivery Address section NOT found. Adding new address...");
				        try {
				            click(checkoutPageAddres);
				            newAddressData();
				            System.out.println("✅ Address added successfully.");
				        } catch (Exception ex) {
				            System.out.println("❌ Failed to add address: " + ex.getMessage());
				            return;
				        }
				    }

				    System.out.println("\n📦 ----- DELIVERY & PRICE DETAILS SECTION -----");

				    // Step 2: Estimated Delivery Section
				    try {
				        if (estimatedDeliveryDate.isDisplayed()) {
				        	
				            String deliveryText = estimatedDeliveryDate.getText().trim();
				            System.out.println("✅ Estimated Delivery: " + (deliveryText.split("\n")[0]));

				            if (deliveryText.toLowerCase().contains("estimated delivery by")) {
				                System.out.println("📅 Date Info: " + deliveryText);
				            } else {
				                System.out.println("ℹ️ Estimated Delivery present, but format unexpected.");
				                System.out.println("Raw: " + deliveryText);
				            }
				        } else {
				            System.out.println("❌ Estimated Delivery section not visible.");
				        }
				    } catch (Exception e) {
				        System.out.println("⚠️ Exception while checking Estimated Delivery: " + e.getMessage());
				    }

				    // Step 3: Price Details Section
				    try {
				        if (totalMRP.isDisplayed() && totalAmount.isDisplayed()) {
				            System.out.println("\n💰 PRICE DETAILS:");
				            System.out.println("Total MRP: " + totalMRP.getText().replace("Total MRP", "").trim());
				            System.out.println("Discounted MRP: " + addressPageDiscountAmount.getText().replace("Discounted MRP", "").trim());
				            System.out.println("Flat 50 off on Prepaid: " + addressPagePrepaidCouponAmount.getText().replace("Flat 50 off on Prepaid", "").trim());

				            try {
				                if (addressPageAppliedcouponAmount.isDisplayed()) {
				                    System.out.println("Applied Coupon Discount: " + addressPageAppliedcouponAmount.getText().replace("Coupon Discount", "").trim());
				                }
				            } catch (NoSuchElementException ignored) {}

				            System.out.println("You Saved: " + addressPageYouSavedAmount.getText().replace("You Saved", "").trim());

				            String totalText = totalAmount.getText().trim();
				            String[] totalLines = totalText.split("\n");
				            String totalAmountFormatted = totalLines.length >= 2 ? totalLines[1].trim() : totalText;

				            System.out.println("TOTAL AMOUNT: " + totalAmountFormatted);
				            System.out.println("📌 Note: Inclusive of all taxes");

				        } else {
				            System.out.println("❌ Whole Price Details section not visible.");
				        }
				    } catch (Exception e) {
				        System.out.println("⚠️ Exception while checking Price Details: " + e.getMessage());
				    }

				    System.out.println("✅ Finished checking Delivery Address, Delivery Date, and Price Details.");
				}

	//F: 8
	public void radioButtonfunctionality() {

		Actions action = new Actions(driver);
		click(profile);
		action.moveToElement(addressSideMenuButton).click().build().perform();
		try {
			if (addNewAddressButtonOnAddresspage.isDisplayed()) {
				click(addNewAddressButtonOnAddresspage);
				System.out.println("All radio button are verified");

			}
			else {
				click(addAddressButtonOnCheckoutPage);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		if (homeTypeRadioButton.isEnabled()) {
			homeTypeRadioButton.click();
			Common.waitForElement(2);
			click(workTypeRadioButton);
			Common.waitForElement(2);
			click(otherTypeRadioButton);

		}

	}
	
	//F: 9
	public void deleteAddresfunctionalityOnBothPages() 
	{
	    
	    try {
	        Actions action = new Actions(driver);
	        click(profile);
	        action.moveToElement(addressSideMenuButton).click().build().perform();
	        List<WebElement> savedAddressDeleteButtons = driver.findElements(
	            By.xpath("//div[@class='address_card_name_row_wrap']//button[@class='address_card_delete_btn Cls_address_card_delete_btn']"));

	        if (!savedAddressDeleteButtons.isEmpty()) {
	            System.out.println("✅ Existing address found. Proceeding to delete one at random on saved address page.");
	            Collections.shuffle(savedAddressDeleteButtons);
	            WebElement deleteBtn = savedAddressDeleteButtons.get(0);
	            action.moveToElement(deleteBtn).click().perform();
	            click(deleteAddressPopupDeleteButton);
	            System.out.println("🗑️ Clicked on a random delete button to remove existing addres from saved address page.");
	        } else if (addAddressButtonOnCheckoutPage.isDisplayed()) {
	            System.out.println("⚠️ No existing address found. Proceeding to add a new address on saved address page.");
	            click(addAddressButtonOnCheckoutPage);
	            newAddressData();
	            System.out.println("✅ New address added successfully.");

	            List<WebElement> newDeleteButtons = driver.findElements(
	                By.xpath("//div[@class='address_card_name_row_wrap']//button[@class='address_card_delete_btn Cls_address_card_delete_btn']"));

	            if (!newDeleteButtons.isEmpty()) {
	                System.out.println("🔄 Verifying added address by deleting one at randomon saved addres page.");
	                Collections.shuffle(newDeleteButtons);
	                WebElement newDeleteBtn = newDeleteButtons.get(0);
	                action.moveToElement(newDeleteBtn).click().perform();
	                click(deleteAddressPopupDeleteButton);
	                System.out.println("🗑️ Clicked on a random delete button after adding a new address on saved address page");
	            }
	        }
	    } catch (NoSuchElementException e1) {
	        System.out.println("❌ Element not found: " + e1.getMessage());
	    }
	
	    addToCart();//adding new address on checkout page

	    click(checkoutpageContinueButton);
	    boolean isNewAddressAdded = false;

	    try {
	        if (checkoutPageAddres.isDisplayed()) {
	            System.out.println("🟡 No address found on checkout page. Adding a new address.");
	            click(checkoutPageAddres);
	            newAddressData();
	            isNewAddressAdded = true;
	            System.out.println("✅ First address added successfully.");
	        }
	    } catch (Exception e) {
	        System.out.println("⚠️ checkoutPageAddres element not found or not visible: " + e.getMessage());
	    }

	    if (!isNewAddressAdded) {
	        System.out.println("❌ Couldn't add a new address using checkout page options.");
	    }
	    try {
	        Actions action = new Actions(driver);
	        System.out.println("🔄 Clicking 'Change Address' button.");
	        click(changeAddressButtonOnCheckoutpage);

	        List<WebElement> changeAddressDeleteButtons = driver.findElements(
	            By.xpath("//div[@class='address_card_name_row_wrap']//button[@class='address_card_delete_btn Cls_address_card_delete_btn']"));

	        if (!changeAddressDeleteButtons.isEmpty()) {
	            System.out.println("✅ Existing address found in Change Address popup. Proceeding to delete one at random.");
	            Collections.shuffle(changeAddressDeleteButtons);
	            WebElement deleteBtn = changeAddressDeleteButtons.get(0);
	            action.moveToElement(deleteBtn).click().perform();
	            click(deleteAddressPopupDeleteButton);
	            System.out.println("🗑️ Clicked on a random delete button to remove existing address.");
	        } else if (addAddressButtonOnCheckoutPage.isDisplayed()) {
	            System.out.println("⚠️ No existing address found in Change Address popup. Proceeding to add a new one.");
	            click(addAddressButtonOnCheckoutPage);
	            newAddressData();
	            System.out.println("✅ New address added successfully via Change Address popup.");
	        }
	    } catch (Exception e) {
	        System.out.println("❌ Exception while handling Change Address flow: " + e.getMessage());
	    }

	}  
	
	//10
	
	public void phoneNumberverfiyOnAddressPage() {
	    click(profile);
	    Common.waitForElement(2);

	    // ✅ Get phone number from Account Settings
	    click(MyprofileIconButton);

	    String accountPhoneNumber = accountsettingMobileNumber.getAttribute("value").trim();
	    System.out.println("📞 Account Settings Phone Number: " + accountPhoneNumber);

	    click(profile);
	    click(addressSideMenuButton);

	    System.out.println("✅ Navigated to Address section.");

	    String addressPhoneNumber = null;

	    try {
	        if (addAddressButtonOnCheckoutPage.isDisplayed()) {
	            System.out.println("⚠️ No address found. Adding first address.");
	            click(addAddressButtonOnCheckoutPage);
                Common.waitForElement(5);


	            addressPhoneNumber = phoneNumberTextBox.getAttribute("value").trim();
	            System.out.println("📞 Address Page Phone Number: " + addressPhoneNumber);

	            Common.waitForElement(5);
	        }
	    } catch (NoSuchElementException e) {
	        System.out.println("ℹ️ 'No Address Found' button not available.");
	    }

	    if (addressPhoneNumber == null) {
	        try {
	            if (addNewAddressButtonOnAddresspage.isDisplayed()) {
	                System.out.println("✅ 'Add New Address' button clicked.");
	                click(addNewAddressButtonOnAddresspage);
	                Common.waitForElement(5);

	                addressPhoneNumber = phoneNumberTextBox.getAttribute("value").trim();
	                System.out.println("📞 Address Page Phone Number: " + addressPhoneNumber);
	            }
	        } catch (NoSuchElementException e) {
	            System.out.println("ℹ️ 'Add New Address' button not available.");
	        }
	    }

	    if (addressPhoneNumber == null) {
	        System.out.println("❌ No Add Address button found.");
	    }

	    // ✅ Compare both phone numbers
	    if (addressPhoneNumber != null) {
	        if (accountPhoneNumber.equals(addressPhoneNumber)) {
	            System.out.println("✔️ Phone numbers MATCH (Account vs Address): " + accountPhoneNumber);
	        } else {
	            System.out.println("❌ Phone numbers DO NOT match! Account: " 
	                + accountPhoneNumber + " | Address: " + addressPhoneNumber);
	        }
	    }
	}
//11	
	public void VerifyPhoneNumberOnCheckoutAddress() {
	    click(profile);
	    Common.waitForElement(2);

	    // ✅ Step 1: Get phone number from Address Page first
	    String addressPhoneNumber = null;

	    try {
	        if (checkoutPageAddres.isDisplayed()) {
	            System.out.println("🟡 No address found on checkout page. Adding a new address.");
	            click(checkoutPageAddres);
	            addressPhoneNumber = phoneNumberTextBox.getAttribute("value").trim();
	            System.out.println("📞 Address Page Phone Number: " + addressPhoneNumber);
	        }
	    } catch (Exception e) {
	        System.out.println("⚠️ checkoutPageAddres element not found: " + e.getMessage());
	    }

	    if (addressPhoneNumber == null) { 
	        try {
	            if (addNewAddressOnChekoutPage.isDisplayed()) {
	                System.out.println("🟢 Existing address found. Adding another new address.");
	                click(addNewAddressOnChekoutPage);
	                addressPhoneNumber = phoneNumberTextBox.getAttribute("value").trim();
	                System.out.println("📞 Address Page Phone Number: " + addressPhoneNumber);
	            }
	        } catch (Exception e) {
	            System.out.println("⚠️ addNewAddressOnChekoutPage not found: " + e.getMessage());
	        }
	    }

	    // ✅ Step 2: Close popup before going to profile
	    try {
	        if (AddaddresscloseButton.isDisplayed()) {   // <-- you need locator for popup close button
	            click(AddaddresscloseButton);
	            Common.waitForElement(2);
	            System.out.println("🔒 Closed address popup before navigating to profile.");
	        }
	    } catch (Exception e) {
	        System.out.println("ℹ️ No popup to close: " + e.getMessage());
	    }
	    
	    try {
	    	Common.waitForElement(5);
	        System.out.println("🔄 Clicking 'Change Address' button.");
	        click(changeAddressButtonOnCheckoutpage);

	        Common.waitForElement(2);
	        System.out.println("➕ Clicking 'Add Address' button inside Change Address page.");
	        click(addAddressButtonOnChangAddressPage);

	        // Fetch phone number again after opening Add Address
	        addressPhoneNumber = phoneNumberTextBox.getAttribute("value").trim();
	        System.out.println("📞 Address Page Phone Number (via Change Address flow): " + addressPhoneNumber);

	    } catch (Exception e) {
	        System.out.println("⚠️ Unable to click Change Address/Add Address: " + e.getMessage());
	    }
	    try {
	        if (AddaddresscloseButton.isDisplayed()) {   // <-- you need locator for popup close button
	            click(AddaddresscloseButton);
	            Common.waitForElement(2);
	            System.out.println("🔒 Closed address popup before navigating to profile.");
	        }
	    } catch (Exception e) {
	        System.out.println("ℹ️ No popup to close: " + e.getMessage());
	    }

	    // ✅ Step 3: Now get phone number from Account Settings
	    String accountPhoneNumber = null;
	    try {
	        click(profile);
	        Common.waitForElement(1);
	        click(MyprofileIconButton);
	        accountPhoneNumber = accountsettingMobileNumber.getAttribute("value").trim();
	        System.out.println("📞 Account Settings Phone Number: " + accountPhoneNumber);
	    } catch (Exception e) {
	        System.out.println("⚠️ Unable to fetch phone number from Account Settings: " + e.getMessage());
	    }

	    // ✅ Step 4: Compare
	    if (addressPhoneNumber != null && accountPhoneNumber != null) {
	        if (accountPhoneNumber.equals(addressPhoneNumber)) {
	            System.out.println("✔️ Phone numbers MATCH ✅ (Account vs Address): " + accountPhoneNumber);
	        } else {
	            System.out.println("❌ Phone numbers DO NOT match! Account: "
	                + accountPhoneNumber + " | Address: " + addressPhoneNumber);
	        }
	    } else {
	        System.out.println("⚠️ Comparison skipped — one of the numbers is NULL.");
	    }
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public WebDriver gmail(String browserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean isAt() {
		// TODO Auto-generated method stub
		return false;
	}

}
