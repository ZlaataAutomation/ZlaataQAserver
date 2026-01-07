package pages;


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.time.Duration;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import manager.FileReaderManager;
import objectRepo.HomePageObjRepo;
import utils.Common;

public final class HomePage extends HomePageObjRepo {


	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);

	}
	public void homeLaunch() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
				type(accessCode, FileReaderManager.getInstance().getJsonReader().getValueFromJson("Access"));
				click(submit);
	//	handleAccessCodeIfPresentFast();
//		popup();
//		closeDebugBarIfPresent();


	}
	public void popup() {
	    List<WebElement> popUps = driver.findElements(
	            By.xpath("//div[contains(@class,'chrismas_closebtn')]")
	    );

	    if (!popUps.isEmpty()) {
	    	 ((JavascriptExecutor) driver)
             .executeScript("arguments[0].click();", popUps.get(0));
	    }
	}
	public void closeDebugBarIfPresent() {
        try {
//            Common.waitForElement(2);

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
	public void handleAccessCodeIfPresentFast() {

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

	public void scrollToElementUsingJSE(WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
	}
	public void scrollUsingJSWindow() {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("window.scrollTo(0, 1800);");

	}

	public void bannerClick() {
		homeLaunch();
		Common.waitForElement(10);
		click(banners);
		//		WebElement bannerRedirection = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
		//		if (bannerRedirection.isDisplayed()) {
		//			String pageHeading = bannerRedirection.getText();
		//			System.out.println("Banner Redirected sucessfull: " +pageHeading);
		//			Assert.assertTrue(verifyDisplayed(bannerRedirection));
		//
		//		}
	}
	public void forAndbackButton() {
		homeLaunch();
		try {
			Actions action = new Actions(driver);
			WebElement bannerNxtBtn = driver.findElement(By.xpath("//*[@class='carousel_banner_prev_btn']"));
			WebElement bannerPrevBtn = driver.findElement(By.xpath("//*[@class='carousel_banner_next_btn']"));

			int maxClicks = 5; // Define the maximum number of clicks

			// Click next button
			for (int i = 0; i < maxClicks; i++) {
				if (bannerNxtBtn.isDisplayed()) {
					action.moveToElement(bannerNxtBtn).click().build().perform();
					Common.waitForElement(1); 
				} else {
					break;
				}
			}

			// Click previous button
			for (int i = 0; i < maxClicks; i++) {
				if (bannerPrevBtn.isDisplayed()) {
					action.moveToElement(bannerPrevBtn).click().build().perform();
					Common.waitForElement(1); 
				} else {
					break;
				}
			}

		} 
		catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
			e1.initCause(e);
			throw e1;
		}
	}

	public void verifyPauseButton() {
		homeLaunch();
		try {
			WebElement pauseButton = driver.findElement(By.xpath("//div[@class='carousel_cta']"));
			WebElement playButton = driver.findElement(By.xpath("//*[@class='carousel_pause']"));

			// Wait 20 seconds to allow the banner to start moving
			Common.waitForElement(20);
			System.out.println("Banner is moving initially.");

			// Click the pause button and wait to verify if it stops
			pauseButton.click();
			Common.waitForElement(5);
			System.out.println("Banner stopped after pause.");

			// Click the play button and wait to verify if it starts again
			playButton.click();
			Common.waitForElement(15);
			System.out.println("Banner started moving after play.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void topSelling() throws TimeoutException {
	    homeLaunch();
	    Actions action = new Actions(driver);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        // Keep scrolling until forward arrow is visible
	        int maxScrolls = 15;  // safety limit to avoid infinite loop
	        int scrollCount = 0;
	        WebElement forwardButton = null;

	        while (scrollCount < maxScrolls) {
	            try {
	                forwardButton = wait.until(ExpectedConditions
	                        .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-next top_selling_swiper_next']")));
	                if (forwardButton.isDisplayed()) {
	                    break; // stop scrolling once arrow is visible
	                }
	            } catch (Exception e) {
	                // not visible yet, scroll more
	                js.executeScript("window.scrollBy(0, 400);");
	                Common.waitForElement(1);
	            }
	            scrollCount++;
	        }

	        if (forwardButton != null) {
	            action.moveToElement(forwardButton).click().perform();
	            System.out.println("✅ Top Selling forward button clicked.");
	        }

	        // Now wait for and click the backward button
	        WebElement backwardButton = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-prev top_selling_swiper_prev']")));
	        action.moveToElement(backwardButton).click().perform();
	        System.out.println("✅ Top Selling backward button clicked.");

	    } catch (Exception e) {
	        System.out.println("❌ Caught an exception: " + e.getMessage());
	        NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException occurred");
	        e1.initCause(e);
	        throw e1;
	    }
	}


	public void topSellingProduct() {
		homeLaunch();

		try {
			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
			Common.waitForElement(2);

			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='product_img']"));
			if (clickRandomProduct.isEmpty()) {
				System.out.println("No products found in the Top Selling section.");
				return;
			}

			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
			System.out.println("Top selling product Content: " + productName);
			WebElement randomProduct = clickRandomProduct.get(0);
			Collections.shuffle(clickRandomProduct);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();

			Common.waitForElement(2);
			WebElement productDetailsPrdContent = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
			String productDetailContent = productDetailsPrdContent.getText().trim();
			System.out.println("Product details product content: " + productDetailContent);

			// Check if productDetailContent contains productName (partial match)
			if (productDetailContent.contains(productName)) {
				System.out.println("Both Contents are matching.");
			} else {
				System.out.println("Both Contents are not matching.");
			}

		} catch (Exception e) {
			System.out.println("Exception in topSellingProduct: " + e.getMessage());
			throw e;
		}
	}


	public void seeMore() {
		homeLaunch();
		Actions action = new Actions(driver);
		Common.waitForElement(2);
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2700);");
		Common.waitForElement(2);
		action.moveToElement(seeMore).click().build().perform();
		WebElement seeMorePage = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
		if (seeMorePage.isDisplayed()) {
			String pageHeading = seeMorePage.getText();
			System.out.println("See More button Redirected sucessfull: " +pageHeading);
			Assert.assertTrue(verifyDisplayed(seeMorePage));


		}

	}
	public void newArrivalArrows() throws TimeoutException {
	    homeLaunch();
	    Actions action = new Actions(driver);
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    JavascriptExecutor js = (JavascriptExecutor) driver;

	    try {
	        // Keep scrolling until forward arrow is visible
	        int maxScrolls = 15; // safety limit
	        int scrollCount = 0;
	        WebElement forwardButton = null;

	        while (scrollCount < maxScrolls) {
	            try {
	                forwardButton = wait.until(ExpectedConditions
	                        .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-next new_arrival_swiper_next']")));
	                if (forwardButton.isDisplayed()) {
	                    break; // stop scrolling once arrow is visible
	                }
	            } catch (Exception e) {
	                // not visible yet, scroll more
	                js.executeScript("window.scrollBy(0, 400);");
	                Common.waitForElement(1);
	            }
	            scrollCount++;
	        }

	        if (forwardButton != null) {
	            action.moveToElement(forwardButton).click().perform();
	            System.out.println("✅ New Arrival forward button clicked.");
	        }

	        // Now wait for and click the backward button
	        WebElement backwardButton = wait.until(ExpectedConditions
	                .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-prev new_arrival_swiper_prev']")));
	        action.moveToElement(backwardButton).click().perform();
	        System.out.println("✅ New Arrival backward button clicked.");

	    } catch (Exception e) {
	        System.out.println("❌ Caught an exception in New Arrival: " + e.getMessage());
	        NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException occurred in New Arrival");
	        e1.initCause(e);
	        throw e1;
	    }
	}


	public void newArivalProductImg() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2700);");
		try {

			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='products_contents']")); 
			if (clickRandomProduct.isEmpty()) {
				System.out.println("No products found in New arrivals section.");
				return;
			}

			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
			System.out.println("New arrivals product Content: " + productName);
			WebElement randomProduct = clickRandomProduct.get(0);
			Collections.shuffle(clickRandomProduct);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();

			Common.waitForElement(2);
			WebElement productDetailsPrdContent = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
			String productDetailContent = productDetailsPrdContent.getText().trim();
			System.out.println("Product details product content: " + productDetailContent);

			// Check if productDetailContent contains productName (partial match)
			if (productDetailContent.contains(productName)) {
				System.out.println("Both Contents are matching.");
			} else {
				System.out.println("Both Contents are not matching.");
			}

		} catch (Exception e) {
			System.out.println("Exception in New arrivals Product: " + e.getMessage());
			throw e;
		}
	}



	public void quickView() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2500);");
		try {

			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='products_quick_view_box Cls_quickview_btn']"));
			if (clickRandomProduct.isEmpty()) {
				System.out.println("Quick view Not found in New arrivals section.");
				return;
			}

			String productName = driver.findElement(By.xpath(".//div[@class='products_contents']")).getText().trim();
			System.out.println("New arrivals product Content: " + productName);
			WebElement randomProduct = clickRandomProduct.get(0);
			Collections.shuffle(clickRandomProduct);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();

			Common.waitForElement(3);
			WebElement quickViewPrdContent = driver.findElement(By.xpath("//div[@class='qv_prod_details_cont']"));
			String quickViewContent = quickViewPrdContent.getText().trim();
			System.out.println("Quick view product content: " + quickViewContent);

			// Check if productDetailContent contains productName (partial match)
			if (quickViewContent.contains(productName)) {
				System.out.println("Both Contents are matching.");
			} else {
				System.out.println("Both Contents are not matching.");
			}

		} catch (Exception e) {
			System.out.println("Exception in topSellingProduct: " + e.getMessage());
			throw e;
		}
	}

	public void inspiredBy() {	
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 2600);");
		List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//div[@class='inspired_slide_img']"));
		Collections.shuffle(clickRandomProduct);
		Common.waitForElement(15);


		if (!clickRandomProduct.isEmpty()) {
			WebElement randomProduct = clickRandomProduct.get(0);
			Actions actions = new Actions(driver);
			actions.moveToElement(randomProduct).click().build().perform();
			WebElement inspiredByRedirection = driver.findElement(By.xpath("//div[@class='prod_main_details_head']"));
			if (inspiredByRedirection.isDisplayed()) {
				String details = inspiredByRedirection.getText();
				System.out.println("Inspired by Redirecte	d sucessfull: " +details);
				Assert.assertTrue(verifyDisplayed(inspiredByRedirection));

			}
		}
	}

//	public void happy() {
//
//		homeLaunch();
//		Actions action = new Actions(driver);
//		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//
//		try {
//			((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
//			Common.waitForElement(2);
//			// Now wait for and click the forward button
//			WebElement forwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//div[@class='swiper-button-next testimonial_swiper_next']")));
//			action.moveToElement(forwardButton).click().perform();
//			System.out.println("Happy customer forward button clicked.");
//
//			// Now wait for and click the backward button
//			WebElement backwardButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
//					By.xpath("//div[@class='swiper-button-prev testimonial_swiper_prev']")));
//			action.moveToElement(backwardButton).click().perform();
//			System.out.println("Happy customer backward button clicked.");
//
//		} catch (Exception e) {
//			System.out.println("Caught an exception: " + e.getMessage());
//			NoSuchElementException e1 = new NoSuchElementException("A NoSuchElementException exception occurred");
//			e1.initCause(e);
//			throw e1;
//		}
//	}
//
//	public void happyQuickView() {
//		homeLaunch();
//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
//		try {
//
//			List<WebElement> clickRandomProduct = driver.findElements(By.xpath("//a[@class='testimonial_cards_quick_view']"));
//			if (clickRandomProduct.isEmpty()) {
//				System.out.println("No Quick view found in Happy customer section.");
//				return;
//			}
//
//			String customerContent = driver.findElement(By.xpath(".//p[@class='testimonial_cards_disc']")).getText().trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
//			System.out.println("Customer Content: " + customerContent);
//			WebElement randomProduct = clickRandomProduct.get(0);
//			Collections.shuffle(clickRandomProduct);
//			Actions actions = new Actions(driver);
//			actions.moveToElement(randomProduct).click().build().perform();
//
//			Common.waitForElement(2);
//			WebElement reviewContent = driver.findElement(By.xpath("//div[@class='customer_review_card']//p"));
//			String reviewDeatilsContent = reviewContent.getText().trim().toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "");
//			System.out.println("Review details content: " + reviewDeatilsContent);
//
//			// Check if productDetailContent contains productName (partial match)
//			if (reviewDeatilsContent.contains(customerContent)) {
//				System.out.println("Both Contents are matching.");
//			} else {
//				System.out.println("Both Contents are not matching.");
//			}
//
//		} catch (Exception e) {
//			System.out.println("Exception in Happy customer section: " + e.getMessage());
//			throw e;
//		}
//	}

	public void feedBack() {
		Actions action = new Actions(driver);
		homeLaunch();
		Common.waitForElement(2);
		click(feedBack);
		click(feedletsDoIT);
		Common.waitForElement(1);
		RandomMailId();
		action.moveToElement(continueFeed).click().build().perform();
		clickUsingJavaScript(feedCollectionYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedSearchingYES);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStruggle2);
		clickUsingJavaScript(feedNextButton);
		clickUsingJavaScript(feedStarButton);
		clickUsingJavaScript(feedFinalContinue);
		try {
			WebElement feedBackCopy = driver.findElement(By.id("copyButton"));

			if (feedBackCopy.isDisplayed()) {
				clickUsingJavaScript(feedBackCopy);
				System.out.println("Feed Back copy button is clicked.");

			}

		} 
		catch (Exception e) {
			System.err.println(e);
		}

		String feedBackCoupon = feedBackformText.getText();
		System.out.println("Feed Back form submitted: " + feedBackCoupon);

	}

	public void backtoTop() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
		Common.waitForElement(1);
		WebElement backTop = driver.findElement(By.xpath("//div[@class='bottom_icons_box']"));
		try {
			if (backTop.isDisplayed()) {
				click(backTop);
				System.out.println("The Back to top button is clicked");
			}
		} catch (Exception e) {
			System.err.println(e);
		}




	}
	public void whatsApp() {
		homeLaunch();
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 6200);");
		Common.waitForElement(1);
		WebElement whatsApp = driver.findElement(By.id("whatsappIcon"));
		try {
			if (whatsApp.isDisplayed()) {
				click(whatsApp);
				System.out.println("The Whats app button is clicked");
				Common.waitForElement(2);
				String whatsAppNumber = driver.findElement(By.xpath("(//*[contains(text(),'7305380625')])[2]")).getText();;
				System.out.println("Whats App number displayed :" + whatsAppNumber);
			}
		} catch (Exception e) {
			System.err.println(e);
		}


	}
	//	public void featureOn() {
	//
	//		homeLaunch();
	//		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 7000);");
	//		
	//		try {
	//
	//			List<WebElement> clickRandomFeatureOn = driver.findElements(By.xpath("//div[@class='featured__slider__main']//div[@class='swiper-slide']"));
	//			if (clickRandomFeatureOn.isEmpty()) {
	//				System.out.println("No Feature on section found. ");
	//				return;
	//			}
	//
	//			WebElement randomFeature = clickRandomFeatureOn.get(0);
	//			Collections.shuffle(clickRandomFeatureOn);
	//			clickUsingJavaScript(randomFeature);
	//			Common.waitForElement(5);
	//			WebElement FeatureOnRedirection = driver.findElement(By.xpath("//h3[@class='prod_list_topic']"));
	//			if (FeatureOnRedirection.isDisplayed()) {
	//				String pageHeading = FeatureOnRedirection.getText();
	//				System.out.println("Feature On Redirected sucessfull: " + pageHeading);
	//				Assert.assertTrue(verifyDisplayed(FeatureOnRedirection));
	// }
	//			else {
	//				Common.waitForElement(5);
	//				WebElement feature = driver.findElement(By.xpath("//h2[contains(text(),'Feature On')]"));
	//				String featureOn =feature.getText();
	//				System.out.println("The feature on redirecting on the same page " + featureOn);
	//				Assert.assertTrue(verifyDisplayed(feature));
	//			}
	//
	//
	//		} catch (Exception e) {
	//			System.err.println("Exception in Feature On Section : " + e.getMessage());
	//			throw e;
	//		}
	//
	//
	//	}
	public void featureOn() {
	    homeLaunch();
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	    Actions action = new Actions(driver);

	    try {
	        // Keep scrolling until any Feature On card is visible
	        int maxScrolls = 15;
	        int scrollCount = 0;
	        List<WebElement> featureCards = null;

	        while (scrollCount < maxScrolls) {
	            featureCards = driver.findElements(
	                    By.xpath("//div[@class='featured__slider__main']//div[@class='swiper-slide']"));

	            if (!featureCards.isEmpty() && featureCards.get(0).isDisplayed()) {
	                break; // stop scrolling when cards are visible
	            }

	            js.executeScript("window.scrollBy(0, 400);");
	            Common.waitForElement(1);
	            scrollCount++;
	        }

	        if (featureCards == null || featureCards.isEmpty()) {
	            System.out.println("❌ No Feature On section found.");
	            return;
	        }

	        // Pick the first (or random) card
	        WebElement randomFeature = featureCards.get(0); // can use Random if needed
	        action.moveToElement(randomFeature).click().perform();
	        Common.waitForElement(3);

	        // Check redirection
	        List<WebElement> redirectedHeading = driver.findElements(By.xpath("//h3[@class='prod_list_topic']"));
	        if (!redirectedHeading.isEmpty() && redirectedHeading.get(0).isDisplayed()) {
	            System.out.println("✅ Feature On redirected successfully: " + redirectedHeading.get(0).getText());
	            Assert.assertTrue(verifyDisplayed(redirectedHeading.get(0)));
	        } else {
	            // Still on same page
	            List<WebElement> featureHeading = driver.findElements(By.xpath("//h2[contains(text(),'Feature On')]"));
	            if (!featureHeading.isEmpty() && featureHeading.get(0).isDisplayed()) {
	                System.out.println("ℹ️ Feature On redirected on the same page: " + featureHeading.get(0).getText());
	                Assert.assertTrue(verifyDisplayed(featureHeading.get(0)));
	            }

	            // Forward arrow
	            WebElement nextBtn = wait.until(ExpectedConditions
	                    .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-next featured_next_btn']")));
	            action.moveToElement(nextBtn).click().perform();
	            System.out.println("➡️ Feature On Next Button clicked");

	            // Backward arrow
	            WebElement backBtn = wait.until(ExpectedConditions
	                    .visibilityOfElementLocated(By.xpath("//*[@class='swiper-button-prev featured_prev_btn']")));
	            action.moveToElement(backBtn).click().perform();
	            System.out.println("⬅️ Feature On Back Button clicked");
	        }

	    } catch (Exception e) {
	        System.out.println("❌ Exception in featureOn: " + e.getMessage());
	        throw e;
	    }
	}



	public void allsectionHomePage() {
		homeLaunch();
		try {

			List<WebElement> elements = driver.findElements(By.xpath("//h2"));
			for (WebElement element : elements) {
				System.out.println(element.getText());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		try {

			List<WebElement> elements = driver.findElements(By.xpath("//h3"));
			for (WebElement element : elements) {
				System.out.println(element.getText());
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public void logoDisplay() {
	    homeLaunch();  // Launch the Home Page

	    if (logo.isDisplayed()) {
	        System.out.println("✅ Logo is displayed on the Home Page.");

	        // Click the logo
	        logo.click();

	        // Wait for navigation after click
	        Common.waitForElement(3);

	        // Get current URL
	        String currentUrl = driver.getCurrentUrl();
	        System.out.println("🌐 Current URL after clicking logo: " + currentUrl);

	        // Expected URL
	        String expectedUrl = "https://www.zlaata.com/";

	        // Verify URL
	        if (currentUrl.equals(expectedUrl)) {
	            System.out.println("✅ URL validation passed. Logo redirects to: " + expectedUrl);
	        } else {
	            System.out.println("❌ URL validation failed. Expected: " + expectedUrl + " but got: " + currentUrl);
	        }

	    } else {
	        System.out.println("❌ Logo is NOT displayed on the Home Page.");
	    }
	}

 
	public void threadBanner() {
	    homeLaunch();  // Launch the Home Page
	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    int maxScrolls = 15;
	    int scrollCount = 0;

	    while (scrollCount < maxScrolls) {
	        try {
	            if (threadBanner.isDisplayed()) {
	                System.out.println("✅ Thread banner is displayed on the Home Page.");

	                // Scroll into safe position (avoid sticky header blocking click)
	                js.executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top + window.pageYOffset - 150);", threadBanner);
	                Common.waitForElement(2);

	                try {
	                    // Try normal click
	                    threadBanner.click();
	                } catch (Exception e) {
	                    System.out.println("⚠️ Normal click failed, using JS click.");
	                    js.executeScript("arguments[0].click();", threadBanner);
	                }

	                // Wait for navigation
	                Common.waitForElement(3);

	                // Get current URL
	                String currentUrl = driver.getCurrentUrl();
	                System.out.println("🌐 Navigated to URL after clicking Thread banner: " + currentUrl);

	                // Expected URL path
	                String expectedPath = "/threads";

	                // Validation: check if URL ends with or contains expected path
	                if (currentUrl.contains(expectedPath)) {
	                    System.out.println("✅ URL validation passed. Both URLs point to the same page: " 
	                                       + expectedPath + " (Actual: " + currentUrl + ")");
	                } else {
	                    System.out.println("❌ URL validation failed. Expected path: " 
	                                       + expectedPath + " but got: " + currentUrl);
	                    throw new AssertionError("URL mismatch: Expected path -> " + expectedPath + " but got -> " + currentUrl);
	                }
	                return;
	            }
	        } catch (Exception e) {
	            // Not yet visible, continue scrolling
	        }

	        js.executeScript("window.scrollBy(0, 400);");
	        Common.waitForElement(1);
	        scrollCount++;
	    }

	    System.out.println("❌ Thread banner not found after scrolling.");
	}

 public void categorySection() {
	 homeLaunch();  // Launch Home Page

	    // Scroll to category section
	    ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1400);"); 
	    Common.waitForElement(5);

	    // Track printed categories to avoid duplicates
	    Set<String> printedCategories = new HashSet<>();

	    // Locate category cards (first load)
	    List<WebElement> categories = driver.findElements(
	        By.xpath("//div[contains(@class,'outfit_cards_list_box') and contains(@class,'swiper-slide')]")
	    );

	    System.out.println("📌 Found " + categories.size() + " categories on first load.");

	    // Print and verify unique categories
	    for (WebElement category : categories) {
	        String text = category.getText().trim();
	        if (!text.isEmpty() && printedCategories.add(text)) {
	            System.out.println("✅ Category: " + text);
	        }
	    }

	    // Click arrow if more categories are available
	    try {
	        WebElement nextArrow = driver.findElement(By.xpath("//*[@class='swiper-button-next outfits_swiper_next']"));
	        if (nextArrow.isDisplayed()) {
	            nextArrow.click();
	            Common.waitForElement(2);
	            System.out.println("👉 Clicked the right arrow to see more categories.");

	            List<WebElement> moreCategories = driver.findElements(
	                By.xpath("//div[contains(@class,'outfit_cards_list_box') and contains(@class,'swiper-slide')]")
	            );

	            for (WebElement cat : moreCategories) {
	                String text = cat.getText().trim();
	                if (!text.isEmpty() && printedCategories.add(text)) {
	                    System.out.println("📌 More Category: " + text);
	                }
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("⚠️ Next arrow not found or not clickable.");
	    }
 }
 
 public void verifyMonsoonBanner() {
	    homeLaunch();

	    JavascriptExecutor js = (JavascriptExecutor) driver;
	    int maxScrolls = 15;
	    int scrollCount = 0;

	    while (scrollCount < maxScrolls) {
	        try {
	            if (monsoonBanner.isDisplayed()) {
	                System.out.println("✅ Monsoon Banner is displayed on the Home Page.");

	                // Scroll with offset to avoid sticky header
	                js.executeScript("window.scrollTo(0, arguments[0].getBoundingClientRect().top + window.pageYOffset - 150);", monsoonBanner);
	                Common.waitForElement(2);

	                try {
	                    // Try normal click first
	                    monsoonBanner.click();
	                } catch (Exception e) {
	                    // Fallback to JS click if intercepted
	                    System.out.println("⚠️ Normal click failed, using JS click.");
	                    js.executeScript("arguments[0].click();", monsoonBanner);
	                }

	                String redirectedUrl = driver.getCurrentUrl();
	                System.out.println("🔗 Redirected to URL: " + redirectedUrl);
	                return;
	            }
	        } catch (Exception e) {
	            // Element not yet in view, keep scrolling
	        }

	        js.executeScript("window.scrollBy(0, 400);");
	        Common.waitForElement(1);
	        scrollCount++;
	    }

	    System.out.println("❌ Monsoon Banner not found after scrolling.");
	}


	  

	public  void RandomMailId() {
		// Step 1: Generate a random email
		String randomEmail = generateRandomEmail();

		try {

			WebElement emailInput = driver.findElement(By.id("feedback_email"));
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

	// Utility method to generate random email
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




	
	
	
	
	
	
	//Mouse Over Product
	
	 public void mouseoverTopSelling() {
		 driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationUrl());
	        // you probably already launch the homepage in @Given step
	        Actions action = new Actions(driver);
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	        try {
	            // ✅ Wait for & scroll into the Top Selling section
	            WebElement topSellingSection = wait.until(
	                    ExpectedConditions.presenceOfElementLocated(
	                            By.cssSelector(".top_selling_container"))
	            );
	            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", topSellingSection);
	            Thread.sleep(2000);

	            // ✅ Locators
	            By productCardLocator = By.xpath("//div[contains(@class,'top_selling')]//div[contains(@class,'products_cards')]");
	            By nextButtonLocator = By.xpath("//*[@class='swiper-button-next top_selling_swiper_next']");

	            int productIndex = 1;
	            Set<String> visited = new HashSet<>();

	            while (true) {
	                // Find all product cards currently in DOM
	                List<WebElement> products = driver.findElements(productCardLocator);
	                if (products.isEmpty()) {
	                    Assert.fail("❌ No product cards found in Top Selling section!");
	                }

	                for (WebElement product : products) {
	                    // get unique index for this product
	                    String prodId = product.getAttribute("data-swiper-slide-index");
	                    if (prodId == null) {
	                        prodId = String.valueOf(product.hashCode());
	                    }
	                    if (visited.contains(prodId)) continue;

	                    try {
	                        // get main & hover image elements
	                        WebElement mainImg = product.findElement(By.cssSelector(".prod_main_img img"));
	                        WebElement hoverImg = product.findElement(By.cssSelector(".prod_hover_img img"));

	                        // scroll & hover
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", product);
	                        action.moveToElement(product).perform();
	                        Thread.sleep(1200);

	                        boolean hoverDisplayed = hoverImg.isDisplayed()
	                                && hoverImg.getAttribute("src") != null
	                                && !hoverImg.getAttribute("src").contains("placeholder-img");

	                        if (hoverDisplayed) {
	                            System.out.println("✅ Hover image displayed for product " + productIndex +
	                                    ": " + hoverImg.getAttribute("src"));
	                        } else {
	                            System.out.println("❌ Hover image NOT displayed for product " + productIndex);
	                            Assert.fail("Hover image not visible for product " + productIndex);
	                        }

	                    } catch (NoSuchElementException nse) {
	                        System.out.println("⚠️ Product " + productIndex + " is missing expected image elements.");
	                        Assert.fail("Main or hover image missing for product " + productIndex);
	                    } catch (Exception ex) {
	                        System.out.println("⚠️ Error verifying product " + productIndex + ": " + ex.getMessage());
	                        Assert.fail("Unexpected error for product " + productIndex);
	                    }

	                    visited.add(prodId);
	                    productIndex++;
	                }

	                // ✅ Pagination – click Next button if available
	                try {
	                    WebElement nextBtn = driver.findElement(nextButtonLocator);
	                    if (nextBtn.isDisplayed() && nextBtn.isEnabled()) {
	                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);
	                        Thread.sleep(1500);
	                    } else {
	                        break; // no more next button
	                    }
	                } catch (Exception e) {
	                    break; // next button not present
	                }
	            }

	            System.out.println("🎯 Total products verified: " + visited.size());

	            if (visited.isEmpty()) {
	                Assert.fail("❌ No products were verified in Top Selling section!");
	            }

	        } catch (Exception e) {
	            System.out.println("Caught an exception: " + e.getMessage());
	            throw new NoSuchElementException("Top Selling hover verification failed");
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


