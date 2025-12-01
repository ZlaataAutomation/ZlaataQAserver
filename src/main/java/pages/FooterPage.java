package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import objectRepo.FooterObjRepo;
import utils.Common;

public  final class FooterPage  extends FooterObjRepo{
	
	public FooterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}



	public void footerShopAllLinks() {
	    Common.waitForElement(2);

	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    List<WebElement> footerLinks = driver.findElements(By.xpath("//div[@class='foot_navigation']//a"));
	    System.out.println("Total footer links: " + footerLinks.size());

	    for (int i = 0; i < footerLinks.size(); i++) {
	        WebElement link = footerLinks.get(i);
	        String linkText = link.getText();
	        String linkUrl = link.getAttribute("href");
	        System.out.println("Verifying link " + (i + 1) + ": " + linkText);

	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", link);
	        Common.waitForElement(1);

	        if (linkUrl != null && !linkUrl.isEmpty()) {
	            if (linkUrl.startsWith("mailto:")) {
	                System.out.println("Skipping email address link: " + linkText);
	            } else if (linkUrl.startsWith("tel:")) {
	                System.out.println("Skipping phone number link: " + linkText);
	            } else {
	                try {
	                    // Open link in new tab
	                    String originalWindowHandle = driver.getWindowHandle();
	                    ((JavascriptExecutor) driver).executeScript("window.open('" + linkUrl + "','_blank');");
	                    Common.waitForElement(2);

	                    // Switch to new tab
	                    ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	                    driver.switchTo().window(tabs.get(1));

	                    // Verify page title
	                    System.out.println("--------------------------------------------------------------");
	                    System.out.println("Page title after clicking link " + (i + 1) + ": " + driver.getTitle());
	                    System.out.println("--------------------------------------------------------------");
	                    // Close new tab and switch back to original tab
	                    driver.close();
	                    driver.switchTo().window(originalWindowHandle);
	                } catch (Exception e) {
	                    System.out.println("Error verifying link " + (i + 1) + ": " + e.getMessage());
	                }
	            }
	        } else {
	            System.out.println("Link URL is null or empty for link " + (i + 1) + ": " + linkText);
	        }

	        Common.waitForElement(1);
	    }
	}
	
//	public void contactUS() {
//		Common.waitForElement(2);
//        Actions actions = new Actions(driver);
//	    actions.sendKeys(Keys.END).perform();
//	    Common.waitForElement(1);
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", footerContactUsLinks);
//	    try {
//			if (footerContactUsLinks.isDisplayed()) {
//				String contactDetails = footerContactUsLinks.getText();
//				System.out.println("The contact details: ");
//				System.out.println("------------------------");
//				System.out.println(contactDetails);
//			}
//			else {
//				System.err.println("Contact Deatils not displaying");
//			}
//		} catch (Exception e) {
//			System.out.println("Error verifying link: " + e.getMessage());
//		}
//	}
//	
	
//	public void zlaataLogo() {
//		Common.waitForElement(2);
//        Actions actions = new Actions(driver);
//	    actions.sendKeys(Keys.END).perform();
//	    Common.waitForElement(1);
//	    JavascriptExecutor js = (JavascriptExecutor) driver;
//	    js.executeScript("arguments[0].scrollIntoView({block: 'center'});", footerzlaataLogo);
//	    try {
//			if (footerzlaataLogo.isDisplayed()) {
//				System.out.println("Logo is displayed");
//				
//			}
//		} catch (Exception e) {
//			System.out.println("Error verifying link: " + e.getMessage());
//		}
//	}
	
	public void paymentMethods() {
	    try {
	        // Locate payment section div
	        WebElement paymentSection = driver.findElement(
	            By.xpath("//div[@class='foot_navigation footer_payment_wrap']")
	        );

	        // Scroll to the heading inside the section
	        WebElement paymentHeading = paymentSection.findElement(
	            By.xpath(".//h4[@class='foot_nav_heading']")
	        );
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", paymentHeading);
	        Thread.sleep(500);

	        // Check if the section is visible
	        if (paymentSection.isDisplayed()) {
	            System.out.println("✅ Payment section is displayed.");

	            // Find all payment icons inside the section
	            List<WebElement> paymentIcons = paymentSection.findElements(
	                By.xpath(".//img[@alt='Cash On Delivery' or @alt='Internet Banking' or @alt='Master Card' or @alt='Visa']")
	            );

	            if (paymentIcons.isEmpty()) {
	                System.out.println("⚠️ No payment icons found in the section.");
	            } else {
	                System.out.println("✅ Found " + paymentIcons.size() + " payment icons:");
	                for (WebElement icon : paymentIcons) {
	                    System.out.println(" - " + icon.getAttribute("alt"));
	                }
	            }

	        } else {
	            System.out.println("⚠️ Payment section is present but not visible.");
	        }

	    } catch (NoSuchElementException e) {
	        System.out.println("❌ Payment section is NOT present on the page.");
	    } catch (Exception e) {
	        System.out.println("❌ An unexpected error occurred: " + e.getMessage());
	    }
	}

	public void socialMedia() {
	    Common.waitForElement(2);

	    // Scroll to bottom of page
	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);

	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    // Locate all social media icons
	    List<WebElement> socialIcons = driver.findElements(
	        By.xpath("//div[@class='foot_navigation footer_social_wrap']//div[@class='foot_nav']//a")
	    );
	    System.out.println("Total social media icons found: " + socialIcons.size());

	    String originalWindow = driver.getWindowHandle();

	    for (int i = 0; i < socialIcons.size(); i++) {
	        WebElement icon = socialIcons.get(i);
	        String iconText = icon.getText().trim();
	        System.out.println("--------------------------------------------------");
	        System.out.println("Icon " + (i + 1) + ": " + (iconText.isEmpty() ? "[No Text]" : iconText));

	        // Scroll icon into view
	        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", icon);
	        Common.waitForElement(1);

	        try {
	            // Open link in new tab
	            ((JavascriptExecutor) driver).executeScript("window.open(arguments[0].href,'_blank');", icon);
	            Common.waitForElement(2);

	            // Switch to new tab
	            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
	            tabs.remove(originalWindow); // Remove the original tab to find the new one
	            driver.switchTo().window(tabs.get(0));

	            // Print page title and current URL
	            System.out.println("✅ Opened tab title: " + driver.getTitle());
	            System.out.println("✅ Opened tab URL: " + driver.getCurrentUrl());

	            // Close new tab and switch back
	            driver.close();
	            driver.switchTo().window(originalWindow);
	            Common.waitForElement(1);

	        } catch (Exception e) {
	            System.out.println("❌ Error opening icon " + (i + 1) + ": " + e.getMessage());
	        }
	    }
	}

	
	public void copyRights() {
		Common.waitForElement(2);

	    Actions actions = new Actions(driver);
	    actions.sendKeys(Keys.END).perform();
	    Common.waitForElement(1);
	   actions.moveToElement(footerSectionEmailID).build().perform();
	    try {
			if (footerSectionEmailID.isDisplayed()) {
				String copyRightMsg = footerSectionEmailID.getText();
				System.out.println("Zlaata Copy rights: " +copyRightMsg);
				
			}
		} catch (Exception e) {
			System.out.println("Error verifying Copy rights: " + e.getMessage());
		}
	}
	
	public void footerSubscribe() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 4500);");
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
		RandomMailId();
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = mailValidationMessage.getText();
		Common.waitForElement(3);

        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
	}
	
	public void footerSubscribeAlready() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 4500);");
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
	    type(mailId, Common.getValueFromTestDataMap("Email Id"));
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = mailAlreadyValidation.getText();
        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ SUCCESS: Validation Message = " + actualMessage + "\u001B[0m");
	}
	public void invalidMail() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 4500);");
		Common.waitForElement(1);
		click(mailId);
		Common.waitForElement(1);
	    type(mailId, Common.getValueFromTestDataMap("Email Id"));
		click(subScribeBtn);
		Common.waitForElement(2);
		String actualMessage = inValid.getText();
        Assert.assertTrue("Validation failed for ", actualMessage.equals(actualMessage));
        System.out.println("\u001B[32m✅ Error : Validation Message = " + actualMessage + "\u001B[0m");
	}
	
	
	
	
	
	
	
	
	
	
	
	public  void RandomMailId() {
		// Step 1: Generate a random email
		String randomEmail = generateRandomEmail();

		try {

			WebElement emailInput = driver.findElement(By.id("subscribeletter"));
			emailInput.sendKeys(randomEmail);
			// Optionally print or use email later
			System.out.println("Random email entered: " + randomEmail);

		} finally {
			// Close the browser after a short delay for demo purposes
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 7200);");

	}
	
	
	
	
	
	private static String generateRandomEmail() {
		String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder email = new StringBuilder();
		Random rnd = new Random();
		int length = 8;

		for (int i = 0; i < length; i++) {
			email.append(chars.charAt(rnd.nextInt(chars.length())));
		}

		return email.toString() + "@example.com";
	}



	public void clickUsingJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
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
