package pages;


import java.time.Duration;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import groovyjarjarantlr4.v4.parse.ANTLRParser.element_return;
import manager.FileReaderManager;
import objectRepo.SignupObjRepository;
import utils.Common;
import utils.DateUtils;

public final class NegativeSignupPages extends SignupObjRepository {
	private WebDriver driver;


	public NegativeSignupPages(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	}
	private WebDriverWait wait;

	protected boolean isAt() {

		return this.wait.until((d) -> this.accessCode.isDisplayed());
	}

	public void launchZltV7() {

		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//		type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
//		click(submit);
		//        popup();
	}
	private void popup() {
		try {
			WebElement popUp = driver.findElement(By.xpath("//button[@class='close-btn']"));
			Common.waitForElement(5);


			if (popUp.isDisplayed()) {
				popUp.click();
			}

		} catch (Exception e) {

		}

	}

	private static Random rnd = new Random();

	public static String generate10DigitNumber() {
		// Generate first digit between 6-9
		int firstDigit = 6 + rnd.nextInt(4); // 6,7,8,9

		// Generate remaining 9 digits
		long remaining = (long) (rnd.nextDouble() * 1_000_000_000L); // 0 to 999999999

		// Combine first digit and remaining digits ensuring exactly 10 digits
		String number = firstDigit + String.format("%09d", remaining);

		System.out.println("Generated user number: " + number);
		return number;
	}




	public void ClickProfileIcon() {
		click(profile);

	}
	public void signupbutton() {
		click(signupButton);

	}


	public void userName() {
		type(name,Common.getValueFromTestDataMap("UserName"));

	}

	public void userNumber() {
		Common.waitForElement(1);
		String userNumber = generate10DigitNumber();
		type(number, userNumber);
	}



	public void contbtn() {
		click(continueButton);


	}
	public void signUp() throws TimeoutException {
		// Open application
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());

		// Enter access code and submit
//		type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
//		click(submit);
		Common.waitForElement(2);
		// Open profile and signup
		click(profile);
		click(signupButton);
		// Enter name
		type(name, FileReaderManager.getInstance().getJsonReader().getValueFromJson("UserName"));
		// Try entering phone number, retry once if validation fails
		userNumber();
		click(continueButton);
		otp.sendKeys(FileReaderManager.getInstance().getJsonReader().getValueFromJson("OTP"));
		click(verify);
		System.out.println("✅ OTP entered and verified");
	}


	public void nametxtBoxEmpty() {
		contbtn();
		String actualMessage = validationMsgName.getText();
		Assert.assertTrue("Name should be between 3 and 50 characters.", actualMessage.equals(actualMessage));
		System.out.println("\u001B[32m" + "The validation message displayed :" +actualMessage + "\u001B[0m");	}	


	public void phoneNumbertxtBoxEmpty() {
		contbtn();
		String actualMessage = validationMsgNumber.getText();
		Assert.assertTrue("Please enter a valid 10-digit phone number.", actualMessage.equals(actualMessage));
		System.out.println("\u001B[32m" + "The validation message displayed :" +actualMessage + "\u001B[0m");
	}
	public void nameLessthan3Char() {
		String excelData = Common.getValueFromTestDataMap("UserName");
		type(name, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String actualMessage = validationMsgName.getText();
		String uiData = name.getAttribute("value");
		Assert.assertTrue("Name should be between 3 and 50 characters.", actualMessage.equals(actualMessage));
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage);
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");

	}
	public void numberLessthan10Digit() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(number, excelData);
		contbtn();
		String actualMessage = validationMsgNumber.getText();
		String expectedMessage = "Please enter a valid 10-digit phone number.";
		Assert.assertEquals("❌ Validation message did not match!", expectedMessage, actualMessage);
		String uiData = number.getAttribute("value");
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		System.out.println("\u001B[32m✅ Validation Message: " + actualMessage + "\u001B[0m");
	}


	public void mailIdlessthan3char() {
		String excelData = Common.getValueFromTestDataMap("Email Id");
		type(signUpmail, excelData);
		contbtn();
		String actualMessage = validationMsgMail.getText();
		String uiData = signUpmail.getAttribute("value");
		Assert.assertTrue("Please enter a valid email address.", actualMessage.equals(actualMessage));
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage);
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");

	}
	public void nameMorethan50Char() {
		String excelData = Common.getValueFromTestDataMap("UserName");
		type(name, excelData);
		String uiData = name.getAttribute("value");
		Assert.assertTrue("Name entered exceeds 50 characters", uiData.length() <= 50);
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
	}


	public void numbermorethan10() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		type(number, excelData);
		String uiData = number.getAttribute("value");
		Assert.assertTrue("Number length should be >= 10", uiData.length() >= 10);
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
	}

	public void mailIdmorethan50char() {
		String excelData = Common.getValueFromTestDataMap("Email Id");
		type(signUpmail, excelData);
		String uiData = signUpmail.getAttribute("value");
		Assert.assertTrue("Mail entered exceeds 50 characters", uiData.length() <= 50);
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
	}

	public void invalidmailId() {
		String excelData = Common.getValueFromTestDataMap("Email Id");
		type(signUpmail, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String actualMessage = validationMsgMail.getText();
		String uiData = signUpmail.getAttribute("value");
		String expectedMessage = "Please enter a valid email address.";
		Assert.assertEquals("❌ Validation message mismatch", expectedMessage, actualMessage);
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage);
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");

	}

	public void numberNonNumeric() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(number, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String actualMessage = validationMsgNumber.getText();
		String uiData = number.getAttribute("value");
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage);
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");
		Assert.assertTrue("❌ Expected a validation error for non-numeric input.",
				actualMessage.toLowerCase().contains("valid") || actualMessage.toLowerCase().contains("number"));
	}

	public void namewithSpecialChar() {
		String excelData = Common.getValueFromTestDataMap("UserName");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(name, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String actualMessage = validationMsgName.getText();
		String uiData = name.getAttribute("value");
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage );
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");

		Assert.assertTrue("❌ Expected a validation message for special characters in name.",
				actualMessage.toLowerCase().contains("valid") || actualMessage.toLowerCase().contains("name"));
	}

	public void numberTwice() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(number, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String uiData = number.getAttribute("value");
		String actualMessage = validationMsgNumber.getText();
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		//	    System.out.println("📤 Validation Message: " + actualMessage);
		System.out.println("\u001B[32m📤 Validation Message: " + actualMessage + "\u001B[0m");
		String expectedMessage = "The contact number has already been taken.";
		Assert.assertEquals("❌ Validation message does not match.", expectedMessage, actualMessage);
	}

	public void numberstaringwith123() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		type(number, excelData);
		String uiData = number.getAttribute("value");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
	}
	public void nameWithNUmeric() {
		String excelData = Common.getValueFromTestDataMap("UserName");
		contbtn();
		type(name, excelData);
		String uiData = name.getAttribute("value");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
	}

	public void numberwithspecialsymbolandChar() {
		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(number, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String uiData = number.getAttribute("value");
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		String actualMessage = validationMsgNumber.getText();
		//	    System.out.println("⚠️ Validation Message: " + actualMessage);
		System.out.println("\u001B[33m⚠️ Validation Message: " + actualMessage + "\u001B[0m");

		Assert.assertTrue("❌ Validation message expected for invalid mobile number!",
				actualMessage.toLowerCase().contains("valid") || actualMessage.toLowerCase().contains("number"));
	}

	public void invalidDomainMail() {
		String excelData = Common.getValueFromTestDataMap("Email Id");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(signUpmail, excelData);
		contbtn();
		//	    Common.waitForElement(5);
		String uiData = signUpmail.getAttribute("value");
		System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
		String actualMessage = validationMsgMail.getText();
		//	    System.out.println("⚠️ Validation Message: " + actualMessage);
		System.out.println("\u001B[33m⚠️ Validation Message: " + actualMessage + "\u001B[0m");

		Assert.assertTrue("❌ Expected validation message for invalid domain email.",
				actualMessage.equals("Please enter a valid email address."));
	}

	public void mailwithMultipleSpecialSymbol() {
		String value = Common.getValueFromTestDataMap("Email Id");
		System.out.println("📥 Excel Data: " + value + " | Length: " + value.length());
		type(signUpmail, Common.getValueFromTestDataMap("Email Id"));
		contbtn();
		Common.waitForElement(1);
		String actualMessage = validationMsgMail.getText();
		Assert.assertTrue("Please enter a valid email address.", actualMessage.equals(actualMessage));
		String enteredmail = signUpmail.getAttribute("value");
		System.out.println("📤 Application UI Data: " + enteredmail + " | Length: " + enteredmail.length());
		System.out.println("✅ \u001B[32mValidation message displayed: " + actualMessage + "\u001B[0m");
	}
	public void numberwithSpaces() {
		String value = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + value + " | Length: " + value.length());

		type(number, Common.getValueFromTestDataMap("Mobile Number"));
		type(number, value);

		String enteredNumber = number.getAttribute("value");
		System.out.println("📤 Application UI Data: " + enteredNumber + " | Length: " + enteredNumber.length());
	}

	public void nameWithMixedChar() {
		String value = Common.getValueFromTestDataMap("UserName");
		System.out.println("📥 Excel Data: " + value + " | Length: " + value.length());

		type(name, Common.getValueFromTestDataMap("UserName"));
		contbtn();
		//	Common.waitForElement(5);

		String actualMessage = validationMsgName.getText();
		Assert.assertTrue("Name should be between 3 and 50 characters.", actualMessage.equals(actualMessage));

		String enteredName = name.getAttribute("value");
		System.out.println("📤 Application UI Data: " + enteredName + " | Length: " + enteredName.length());
		System.out.println("\u001B[32m" + "🛑 Validation Message Displayed: " + actualMessage + "\u001B[0m");
	}

	public void leftAllMandatory() {
//		contbtn();

		String actualMessage = validationMsgName.getText();
		Assert.assertTrue("Name should be between 3 and 50 characters.", actualMessage.equals(actualMessage));

		String actualMessage1 = validationMsgNumber.getText();
		Assert.assertTrue("Please enter a valid 10-digit phone number.", actualMessage1.equals(actualMessage1));

		System.out.println("\u001B[32m" + "🛑 Validation Message for Name: " + actualMessage + "\u001B[0m");
		System.out.println("\u001B[32m" + "🛑 Validation Message for Phone Number: " + actualMessage1 + "\u001B[0m");
	}

	public void enterOTP() {
		type(otp,Common.getValueFromTestDataMap("OTP"));

	}

	public void invalidOTP() {



		String excelData = Common.getValueFromTestDataMap("Mobile Number");
		System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
		type(number, excelData);
		String value = Common.getValueFromTestDataMap("OTP");
		contbtn();
		Common.waitForElement(5);

		System.out.println("📥 Excel Data (OTP): " + value + " | Length: " + value.length());
		type(otp, value); // This should enter digits one by one if set up right
		//	    Common.waitForElement(5);

		StringBuilder enteredOtp = new StringBuilder();
		for (WebElement input : otpenterBox) {
			String digit = input.getAttribute("value");
			if (digit != null) {
				enteredOtp.append(digit.trim());
			}
		}

		System.out.println("📤 Application UI Data (Entered OTP): " + enteredOtp.toString() + " | Length: " + enteredOtp.length());

		verifyButton();
		Common.waitForElement(1);

		String actualMessage = phoneNumberOtp.getText();
		Assert.assertTrue("Please enter a valid 5-digit OTP.", actualMessage.equals(actualMessage));
		System.out.println("\u001B[32m⚠️ Validation Message Displayed: " + actualMessage + "\u001B[0m");
	}

	public void sameMail() {
		String value = Common.getValueFromTestDataMap("Email Id");
		System.out.println("📥 Excel Data (Email Id): " + value + " | Length: " + value.length());
		type(signUpmail, value);
		contbtn();
		Common.waitForElement(1); // Adjust if needed
		String enteredEmail = signUpmail.getAttribute("value");
		System.out.println("📤 Application UI Data (Email Id): " + enteredEmail + " | Length: " + enteredEmail.length());    String actualMessage = validationMsgMail.getText();
		String expectedMessage = "The email id has already been taken.";
		System.out.println("\u001B[32m⚠️ Validation Message Displayed: " + actualMessage + "\u001B[0m");

		Assert.assertEquals("❌ Validation message mismatch!", expectedMessage, actualMessage);
	}

	public void VerifyLastSecond() {
		Common.waitForElement(59);
		verify.click();
	}

	public void verifyButton() {
		Common.waitForElement(1);
		verify.click();
	}


	//public void OTPafter60sec() {
	//	Common.waitForElement(1);
	//	type(otp,Common.getValueFromTestDataMap("OTP"));
	//
	//}

	//	public void verifybtn9Times() {
	//		for (int i = 0; i < 9; i++) {
	//            verify.click();
	//            try {
	//              
	//                Thread.sleep(500);
	//            } catch (InterruptedException e) {
	//                e.printStackTrace();
	//            }
	//        }	
	//
	//		
	//	}


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




}
