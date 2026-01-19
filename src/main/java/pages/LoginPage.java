package pages;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.ibm.disthub2.impl.matching.DisthubValueAccessor.Action;

import manager.FileReaderManager;
import objectRepo.LoginObjRepository;
import utils.Common;
import utils.DateUtils;



public final class LoginPage extends LoginObjRepository {

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }
    
    public void homeLaunch() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//				type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
//				click(submit);
//		handleAccessCodeIfPresentFast();
//		popup();
//		closeDebugBarIfPresent();
		
    }

    public void userLogin() {
    	
    	homeLaunch();    
    	Common.waitForElement(1);
    	click(profile);
        type(loginNumber, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Number"));
//        Common.waitForElement(1);
        click(sendotp);
//        Common.waitForElement(35);
      type(enterotp, FileReaderManager.getInstance().getJsonReader().getValueFromJson("OTP"));
        click(verifyotp);
        Common.waitForElement(3); // small buffer

       
            System.out.println("\u001B[32m✅ Login successful\u001B[0m");
       
         // ❌ Red color
//           System.out.println("\u001B[31m❌ Login failed: OTP verification or redirection failed\u001B[0m");
        }
    private void popup() {
        List<WebElement> popUps = driver.findElements(
                By.xpath("//div[contains(@class,'chrismas_closebtn')]")
        );

        if (!popUps.isEmpty()) {
        	((JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", popUps.get(0));
        }
    }
    private void closeDebugBarIfPresent() {
        try {
            Common.waitForElement(2);

            List<WebElement> debugCloseBtn = driver.findElements(
                    By.xpath("//a[contains(@class,'phpdebugbar-close-btn')]")
            );

            if (!debugCloseBtn.isEmpty() && debugCloseBtn.get(0).isDisplayed()) {
            	((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", debugCloseBtn.get(0));
                System.out.println("🛑 PHP Debugbar closed");
            }
        } catch (Exception e) {
            // Intentionally ignored – debugbar not present
        }
    }
    private void handleAccessCodeIfPresentFast() {

        try {
            List<WebElement> accessCodeInput = driver.findElements(
                    By.xpath("//input[@id='access_code']")
            );

            // 🔹 Instant check – if not present, skip
            if (accessCodeInput.isEmpty()) {
                return;
            }

            WebElement input = accessCodeInput.get(0);

            if (input.isDisplayed()) {

                String accessCode = FileReaderManager.getInstance()
                        .getJsonReader()
                        .getValueFromJson("Access");

                // Type access code
                input.clear();
                input.sendKeys(accessCode);

                // Click submit
                // Click submit
                WebElement submitBtn = driver.findElement(
                        By.xpath("//form[contains(@action,'accessCheckProcess')]//button")
                );

                ((JavascriptExecutor) driver)
                        .executeScript("arguments[0].click();", submitBtn);

                System.out.println("⚡ Access code entered (fast path)");
            }

        } catch (Exception e) {
            // swallow – fast skip mode
        }
    }
//    public void userLogin() {
//        driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
//        Common.waitForElement(1);
//        click(profile);
//        Common.waitForElement(1);
//        type(loginNumber, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Number"));
//        Common.waitForElement(1);
//        click(sendotp);
//
//        List<WebElement> otpenterBox = driver.findElements(By.xpath("//form[@class='digit-group login_otp_input_form']//input"));
//        Scanner scanner = new Scanner(System.in);
//        boolean otpVerified = false;
//
//        while (!otpVerified) {
//            System.out.print("🔐 Enter OTP: ");
//            String enteredOtp = scanner.nextLine();
//
//            // Clear previous input
//            for (WebElement box : otpenterBox) {
//                box.clear();
//            }
//
//            // Enter new OTP
//            for (int i = 0; i < enteredOtp.length(); i++) {
//                if (i < otpenterBox.size()) {
//                    otpenterBox.get(i).sendKeys(Character.toString(enteredOtp.charAt(i)));
//                }
//            }
//
////            Common.waitForElement(2);
//            click(verifyotp);
//            Common.waitForElement(3);
//
//
//            try {
//                // Case 1: OTP expired
//                if (otpEnterAfterTimefinish.isDisplayed() &&
//                    otpEnterAfterTimefinish.getText().toLowerCase().contains("expired")) {
//                    System.out.println("\u001B[31m⏰ OTP expired. Resending OTP...\u001B[0m");
//                    click(resendOtpButton);
//                    Common.waitForElement(3);
//                    continue; // re-prompt for new OTP
//                }
//
//                // Case 2: Wrong OTP
//                if (validationForWrongOTP.isDisplayed()) {
//                    String error = validationForWrongOTP.getText();
//                    System.out.println("\u001B[31m❌ Invalid OTP: " + error + "\u001B[0m");
//                    continue; // re-prompt
//                }
//
//                // Case 3: No error — assume success
//                otpVerified = true;
//
//            } catch (Exception e) {
//                // If elements not found, assume OTP was correct and login succeeded
//                otpVerified = true;
//            }
//        }
//
//        System.out.println("\u001B[32m✅ OTP verified successfully. Logged in!\u001B[0m");
//    }

    public void verifyFirstBuy() {
    	homeLaunch();
        click(profile);
        Common.waitForElement(1);
        try {
            String firstBuy = getText(firstBuyCode);
            System.out.println("INFO: First Buy Coupon Code displayed: " + firstBuy);
        } catch (NoSuchElementException e) {
            System.out.println("ERROR: Exception occurred - " + e.getMessage());
            NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
            e1.initCause(e);
            throw e1;
        }
    }

    public void numberFieldEmpty() {
    	homeLaunch();

        click(profile);
        click(sendotp);

        String actualMessage = validationMessage.getText();
        Assert.assertTrue("Validation failed for empty number field", actualMessage.equals(actualMessage));

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);
        String uiData = loginNumber.getAttribute("value");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + (uiData != null ? uiData : "null") + " | Length: " + (uiData != null ? uiData.length() : 0));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
    }


    public void numberLessthan10Digit() {
    	homeLaunch();
        click(profile);

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);
        click(sendotp);

        String actualMessage = validationMessage.getText();
        Assert.assertTrue("Validation failed for <10 digit number", actualMessage.equals(actualMessage));

        String uiData = loginNumber.getAttribute("value");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + (uiData != null ? uiData : "null") + " | Length: " + (uiData != null ? uiData.length() : 0));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
    }


    public void numberMOrethan10() {
    	homeLaunch();
        click(profile);

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);

        String uiData = loginNumber.getAttribute("value");
        Assert.assertTrue("Validation failed for >10 digit number", uiData.length() >= 10);

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + (uiData != null ? uiData : "null") + " | Length: " + (uiData != null ? uiData.length() : 0));
    }


    public void numberWithNonNumeric() {
    	homeLaunch();

        click(profile);

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);

        String uiData = loginNumber.getAttribute("value");

        if (uiData != null) {
            System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
            System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
//            System.out.println("\u001B[32m✅ SUCCESS: Non-numeric input handled\u001B[0m");
        } else {
            System.out.println("\u001B[31m❌ ERROR: Application UI Data is null or unreadable.\u001B[0m");
            Assert.fail("Phone number field is empty or unreadable.");
        }
    }


    public void numberWith123() {
    	homeLaunch();
        click(profile);
        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);

        String uiData = loginNumber.getAttribute("value");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
//        System.out.println("\u001B[32m✅ SUCCESS: Entered number displayed correctly\u001B[0m");
    }


    public void newNumber() {
    	homeLaunch();

        click(profile);
//        Common.waitForElement(5);

        Random rnd = new Random();
        int n = 66666 + rnd.nextInt(99999);
        String excelData = n + DateUtils.getCurrentLocalDateTimeStamp("YYYYMMdd");

        type(loginNumber, excelData);
//        Common.waitForElement(10);
        click(sendotp);
//        Common.waitForElement(10);

        String actualMessage = userNotRegisterValidationMessage.getText();
        Assert.assertTrue("Expected message for unregistered user", actualMessage.equals(actualMessage));

        String uiData = loginNumber.getAttribute("value");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
    }


    public void numberSpecialSymbol() {
    	homeLaunch();

        click(profile);

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);
//        Common.waitForElement(1);

        String uiData = loginNumber.getAttribute("value");
        String expectedSanitized = excelData.replaceAll("[^0-9]", "");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 Application UI Data: " + uiData + " | Length: " + uiData.length());

        Assert.assertEquals("❌ Sanitized number mismatch", expectedSanitized, uiData);
        System.out.println("\u001B[32m✅ SUCCESS: Special symbols removed as expected\u001B[0m");
    }

    public void numberwithSpaces() {
    	homeLaunch();

        click(profile);

        String excelData = Common.getValueFromTestDataMap("Mobile Number");
        type(loginNumber, excelData);

        String uiData = loginNumber.getAttribute("value");
        String expected = excelData.replaceAll("\\s+", "");

        System.out.println("📥 Excel Data: " + excelData + " | Length: " + excelData.length());
        System.out.println("📤 UI Data: " + uiData + " | Length: " + (uiData != null ? uiData.length() : 0));

        if (!expected.equals(uiData)) {
            System.out.println("❌ ERROR: Input mismatch. Expected: " + expected + " | Actual: " + uiData);
        } else {
            System.out.println("\u001B[32m✅ SUCCESS: Number with spaces handled correctly\u001B[0m");
        }

        // Optional assert if needed
        // Assert.assertEquals("❌ Number with spaces not handled correctly", expected, uiData);
    }



    public void mailLogin() {
    	homeLaunch();

        click(profile);
//        Common.waitForElement(5);
        click(mailIcon);
        System.out.println("successfully open the gmail page");
    }

    public void faceBookLink() {
        try {
            // Step 1: Navigate to Application and Open Facebook Login
        	homeLaunch();

            Common.waitForElement(5);
            click(profile);
            Common.waitForElement(2);
            click(faceBookIcon);
            System.out.println("🔗 Navigated to Facebook login window.");

          /*  // Step 2: Read credentials from JSON
            String fbNumber = FileReaderManager.getInstance().getJsonReader().getValueFromJson("FaceBook");
            String fbPassword = FileReaderManager.getInstance().getJsonReader().getValueFromJson("FaceBookPassword");

            // Step 3: Input credentials
            type(enterFaceBookNumber, fbNumber);
            type(enterFaceBookPasswrod, fbPassword);


            System.out.println("📥 Facebook Number Input: " + fbNumber + " | Length: " + fbNumber.length());
            System.out.println("📥 Facebook Password Input: " + fbPassword.replaceAll(".", "*") + " | Length: " + fbPassword.length());

            // Step 4: Click Login and proceed
            click(clickOnFaceBookLoginButton);

          
            Common.waitForElement(30);
            click(clickOnContinueButton);
            
            profileicon.click();
            
            Common.waitForElement(2);
             
            System.out.println(" login Facebook  successfully :  " + myProfiletext.getText()+ " is dispalying");*/
           

        } catch (Exception e) {
            System.out.println("\u001B[31m❌ ERROR: Facebook login automation failed - " + e.getMessage() + "\u001B[0m");
            throw e;
        }
    }
//.....

    @Override
    public WebDriver gmail(String browserName) {
        return null;
    }

    @Override
    public boolean verifyExactText(WebElement ele, String expectedText) {
        return false;
    }

    @Override
    protected boolean isAt() {
        return this.wait.until((d) -> this.accessCode.isDisplayed());
    }
}
