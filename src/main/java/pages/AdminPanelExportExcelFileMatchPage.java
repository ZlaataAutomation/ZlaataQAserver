package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import manager.FileReaderManager;
import objectRepo.AdminPanelExportExcelFileObjRepo;
import utils.Common;
import utils.ExcelXLSReader;
import utils.ExportValidator;

public class AdminPanelExportExcelFileMatchPage extends AdminPanelExportExcelFileObjRepo {
	
	public AdminPanelExportExcelFileMatchPage(WebDriver driver) 
	{
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(this.driver, this);
	}
	
	public void adminLoginApp() {
		driver.get(FileReaderManager.getInstance().getConfigReader().getApplicationAdminUrl());
	    type(adminEmail, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminName"));
	    type(adminPassword, FileReaderManager.getInstance().getJsonReader().getValueFromJson("AdminPassword"));
	    click(adminLogin);
	    System.out.println("✅ Admin Login Successfull");
	    
	}
	
	
	
	
	 // New Added Product Excel File Match
	 
	 
	 private ExportValidator validator = new ExportValidator();
	    private String downloadDir ="C:\\Users\\Sarojkumar\\Downloads\\";

	 // Export from first page
	    public void exportFromFirstPage(String dateRange, String fileName) throws InterruptedException {
	        Common.waitForElement(3);
	        waitFor(activeProductButton);
	        click(activeProductButton);
	        System.out.println("✅ Successfully clicked Active Product Button");

	        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");

	        Common.waitForElement(2);
	        waitFor(exportBtn1);
	        click(exportBtn1);
	        System.out.println("✅ Successfully clicked export Button");

	        Common.waitForElement(2);
	        waitFor(createdAtBox);
	        click(createdAtBox);
	        System.out.println("✅ Successfully clicked Created At Box");

	        Common.waitForElement(2);
	        waitFor(dateInput);
	        dateInput.clear();
	        type(dateInput, dateRange);
	        System.out.println("Typed 'DateRange'");

	        Common.waitForElement(2);
	        waitFor(calendarApplyBtn1);
	        click(calendarApplyBtn1);

	        Common.waitForElement(2);
	        waitFor(generateBtn1);
	        click(generateBtn1);
	        Thread.sleep(10000);

	        // ✅ Now always renames actual downloaded file to NewProductAdd1.xlsx (or given name)
	        File file = validator.waitForDownload(downloadDir, fileName, 30);
	        System.out.println("✅ First export saved: " + file.getAbsolutePath());
	        
	        File f = new File(downloadDir + "NewProductAdd1.xlsx");
	        System.out.println("File exists? " + f.exists());
	    }
	    
	    
	 // Export from second page
	    public void exportFromSecondPage(String dateRange, String fileName) throws InterruptedException {
	    	// Navigate to View All
	    	Common.waitForElement(5);
	        waitFor(viewAllBtn);
	        click(viewAllBtn);
	        System.out.println("✅ Navigated to View All products page.");
	        
	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Common.waitForElement(2);
	        waitFor(exportBtn2);
	        click(exportBtn2);
	        System.out.println("✅ Successfully clicked export Button");
	        
	        Common.waitForElement(2);
	        waitFor(createdAtBox1);
	        click(createdAtBox1);
	        System.out.println("✅ Successfully clicked Created At Box");

	        Common.waitForElement(2);
	        waitFor(dateInput1);
	        dateInput1.clear();
	        type(dateInput1, dateRange);
	        System.out.println("Typed 'DateRange'");

	        Common.waitForElement(2);
	        waitFor(calendarApplyBtn2);
	        click(calendarApplyBtn2);

	        Common.waitForElement(2);
	        waitFor(generateBtn2);
	        click(generateBtn2);
	        System.out.println("✅ Successfully clicked Generate Button");
	        
	     // Open Export Histories
	        Common.waitForElement(2);
	        click(searchProductMenu);
	        type(searchProductMenu, "Export Histories");
	        click(clickExportHistories);
	        System.out.println("✅ Selected Product Sorts");

	        
	        //Export Histories
	        FluentWait<WebDriver> wait = new FluentWait<>(driver)
	                .withTimeout(Duration.ofMinutes(10))
	                .pollingEvery(Duration.ofSeconds(5))
	                .ignoring(NoSuchElementException.class)
	                .ignoring(StaleElementReferenceException.class);

	        WebElement downloadBtn = wait.until(d -> {
	            driver.navigate().refresh();
	            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

	            WebElement statusElement = d.findElement(
	                By.xpath("//tbody/tr[1]/td[6]//span[@class='d-inline-flex']")
	            );
	            String statusText = statusElement.getText().trim();
	            System.out.println("Current Status (row 1): " + statusText);

	            if ("Success".equalsIgnoreCase(statusText)) {
	                return d.findElement(By.xpath("//tbody/tr[1]//a[contains(@class,'cls_invoice_btn')]"));
	            }
	            return null; // keep waiting
	        });

	        // ✅ Now click download
	        Common.waitForElement(2);
	        downloadBtn.click();
	        System.out.println("✅ Download started from first row");
	        Thread.sleep(10000);
	        File file = validator.waitForDownload(downloadDir, fileName, 30);
	        System.out.println("✅ Second export saved: " + file.getAbsolutePath());
	        File b = new File(downloadDir + "NewProductAdd2.xlsx");
	        System.out.println("File exists? " + b.exists());
	    }
	    
	    //Check
	    
	    public void verifyExportedProductsMatch(String excelPath1, String excelPath2) throws IOException {
	        List<Map<String, Object>> firstExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath1);
	        List<Map<String, Object>> secondExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath2);

	        List<String> firstNames = firstExcelProducts.stream()
	                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
	                .filter(name -> !name.isEmpty())  // remove empty names
	                .collect(Collectors.toList());

	        List<String> secondNames = secondExcelProducts.stream()
	                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
	                .filter(name -> !name.isEmpty())
	                .collect(Collectors.toList());

	        // Compare counts first
	        if (firstNames.size() != secondNames.size()) {
	            System.out.println("❌ Number of products differ. NewProductAdd1: " + firstNames.size() + " | NewProductAdd2: " + secondNames.size());
	        }

	        // Compare actual missing products
	        List<String> missingInSecond = new ArrayList<>(firstNames);
	        missingInSecond.removeAll(secondNames);

	        List<String> missingInFirst = new ArrayList<>(secondNames);
	        missingInFirst.removeAll(firstNames);

	        if (!missingInSecond.isEmpty()) {
	            System.out.println("❌ Products missing in NewProductAdd2:");
	            missingInSecond.forEach(p -> System.out.println("   - " + p));
	        }

	        if (!missingInFirst.isEmpty()) {
	            System.out.println("❌ Products missing in NewProductAdd1:");
	            missingInFirst.forEach(p -> System.out.println("   - " + p));
	        }

	        if (!missingInFirst.isEmpty() || !missingInSecond.isEmpty() || firstNames.size() != secondNames.size()) {
	            Assert.fail("Products mismatch found.");
	        } else {
	            System.out.println("✅ Both Excel exports match perfectly.");
	        }
	    }
	    
	    
	    
	    
	 // Active  Products Excel File Match
		 


		 // Export from first page
		    public void exportActiveProductFromFirstPage(String dateRange, String fileName) throws InterruptedException {
		        Common.waitForElement(3);
		        waitFor(activeProductButton);
		        click(activeProductButton);
		        System.out.println("✅ Successfully clicked Active Product Button");

		        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");

		        Common.waitForElement(2);
		        waitFor(exportBtnActiveProducts1);
		        click(exportBtnActiveProducts1);
		        System.out.println("✅ Successfully clicked export Button");

		        Common.waitForElement(2);
		        waitFor(createdAtBox);
		        click(createdAtBox);
		        System.out.println("✅ Successfully clicked Created At Box");

		        Common.waitForElement(2);
		        waitFor(dateInput);
		        dateInput.clear();
		        type(dateInput, dateRange);
		        System.out.println("Typed 'DateRange'");

		        Common.waitForElement(2);
		        waitFor(calendarApplyBtn1);
		        click(calendarApplyBtn1);

		        Common.waitForElement(2);
		        waitFor(generateBtn1);
		        click(generateBtn1);
		        Thread.sleep(10000);

		        File file = validator.waitForDownload(downloadDir, fileName, 30);
		        System.out.println("✅ First export saved: " + file.getAbsolutePath());
		        
		        File f = new File(downloadDir + "ActiveProducts1.xlsx");
		        System.out.println("File exists? " + f.exists());
		    }
		    
		    
		 // Export from second page
		    public void exportFromActiveProductSecondPage(String dateRange, String fileName) throws InterruptedException {
		    	// Navigate to View All
		    	Common.waitForElement(5);
		        waitFor(viewAllBtnActiveProducts);
		        click(viewAllBtnActiveProducts);
		        System.out.println("✅ Navigated to View All products page.");
		        
		        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
		        Common.waitForElement(2);
		        waitFor(exportBtn2);
		        click(exportBtn2);
		        System.out.println("✅ Successfully clicked export Button");
		        
		        Common.waitForElement(2);
		        waitFor(createdAtBox1);
		        click(createdAtBox1);
		        System.out.println("✅ Successfully clicked Created At Box");

		        Common.waitForElement(2);
		        waitFor(dateInput1);
		        dateInput1.clear();
		        type(dateInput1, dateRange);
		        System.out.println("Typed 'DateRange'");

		        Common.waitForElement(2);
		        waitFor(calendarApplyBtn2);
		        click(calendarApplyBtn2);

		        Common.waitForElement(2);
		        waitFor(generateBtn2);
		        click(generateBtn2);
		        System.out.println("✅ Successfully clicked Generate Button");
		        
		     // Open Export Histories
		        Common.waitForElement(2);
		        click(searchProductMenu);
		        type(searchProductMenu, "Export Histories");
		        click(clickExportHistories);
		        System.out.println("✅ Selected Product Sorts");

		        
		        //Export Histories
		        FluentWait<WebDriver> wait = new FluentWait<>(driver)
		                .withTimeout(Duration.ofMinutes(10))
		                .pollingEvery(Duration.ofSeconds(5))
		                .ignoring(NoSuchElementException.class)
		                .ignoring(StaleElementReferenceException.class);

		        WebElement downloadBtn = wait.until(d -> {
		            driver.navigate().refresh();
		            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

		            WebElement statusElement = d.findElement(
		                By.xpath("//tbody/tr[1]/td[6]//span[@class='d-inline-flex']")
		            );
		            String statusText = statusElement.getText().trim();
		            System.out.println("Current Status (row 1): " + statusText);

		            if ("Success".equalsIgnoreCase(statusText)) {
		                return d.findElement(By.xpath("//tbody/tr[1]//a[contains(@class,'cls_invoice_btn')]"));
		            }
		            return null; // keep waiting
		        });

		        // ✅ Now click download
		        Common.waitForElement(2);
		        downloadBtn.click();
		        System.out.println("✅ Download started from first row");
		        Thread.sleep(10000);
		        File file = validator.waitForDownload(downloadDir, fileName, 30);
		        System.out.println("✅ Second export saved: " + file.getAbsolutePath());
		        File b = new File(downloadDir + "ActiveProducts2.xlsx");
		        System.out.println("File exists? " + b.exists());
		    }
		    
		    //Check
		    
		    public void verifyExportedActiveProductsMatch(String excelPath1, String excelPath2) throws IOException {
		        List<Map<String, Object>> firstExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath1);
		        List<Map<String, Object>> secondExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath2);

		        List<String> firstNames = firstExcelProducts.stream()
		                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
		                .filter(name -> !name.isEmpty())  // remove empty names
		                .collect(Collectors.toList());

		        List<String> secondNames = secondExcelProducts.stream()
		                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
		                .filter(name -> !name.isEmpty())
		                .collect(Collectors.toList());

		        // Compare counts first
		        if (firstNames.size() != secondNames.size()) {
		            System.out.println("❌ Number of products differ. ActiveProducts1: " + firstNames.size() + " | ActiveProducts2: " + secondNames.size());
		        }

		        // Compare actual missing products
		        List<String> missingInSecond = new ArrayList<>(firstNames);
		        missingInSecond.removeAll(secondNames);

		        List<String> missingInFirst = new ArrayList<>(secondNames);
		        missingInFirst.removeAll(firstNames);

		        if (!missingInSecond.isEmpty()) {
		            System.out.println("❌ Products missing in ActiveProducts2:");
		            missingInSecond.forEach(p -> System.out.println("   - " + p));
		        }

		        if (!missingInFirst.isEmpty()) {
		            System.out.println("❌ Products missing in ActiveProducts1:");
		            missingInFirst.forEach(p -> System.out.println("   - " + p));
		        }

		        if (!missingInFirst.isEmpty() || !missingInSecond.isEmpty() || firstNames.size() != secondNames.size()) {
		            Assert.fail("Products mismatch found.");
		        } else {
		            System.out.println("✅ Both Excel exports match perfectly.");
		        }
		    }
		    
		    
	
		 // Sold Out  Products Excel File Match
			 
			 // Export from first page
			    public void exportSoldOutProductsFromFirstPage(String dateRange, String fileName) throws InterruptedException {
			        Common.waitForElement(3);
			        waitFor(activeProductButton);
			        click(activeProductButton);
			        System.out.println("✅ Successfully clicked Active Product Button");

			        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");

			        Common.waitForElement(2);
			        waitFor(exportBtnSoldOutProducts1);
			        click(exportBtnSoldOutProducts1);
			        System.out.println("✅ Successfully clicked export Button");

			        Common.waitForElement(2);
			        waitFor(createdAtBox);
			        click(createdAtBox);
			        System.out.println("✅ Successfully clicked Created At Box");

			        Common.waitForElement(2);
			        waitFor(dateInput);
			        dateInput.clear();
			        type(dateInput, dateRange);
			        System.out.println("Typed 'DateRange'");

			        Common.waitForElement(2);
			        waitFor(calendarApplyBtn1);
			        click(calendarApplyBtn1);

			        Common.waitForElement(2);
			        waitFor(generateBtn1);
			        click(generateBtn1);
			        Thread.sleep(10000);

			        File file = validator.waitForDownload(downloadDir, fileName, 30);
			        System.out.println("✅ First export saved: " + file.getAbsolutePath());
			        
			        File f = new File(downloadDir + "SoldOutProducts1.xlsx");
			        System.out.println("File exists? " + f.exists());
			    }
			    
			    
			 // Export from second page
			    public void exportFromSoldOutProductsSecondPage(String dateRange, String fileName) throws InterruptedException {
			    	// Navigate to View All
			    	Common.waitForElement(5);
			        waitFor(viewAllBtnSoldOutProducts);
			        click(viewAllBtnSoldOutProducts);
			        System.out.println("✅ Navigated to View All products page.");
			        
			        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
			        Common.waitForElement(2);
			        waitFor(exportBtn2);
			        click(exportBtn2);
			        System.out.println("✅ Successfully clicked export Button");
			        
			        Common.waitForElement(2);
			        waitFor(createdAtBox1);
			        click(createdAtBox1);
			        System.out.println("✅ Successfully clicked Created At Box");

			        Common.waitForElement(2);
			        waitFor(dateInput1);
			        dateInput1.clear();
			        type(dateInput1, dateRange);
			        System.out.println("Typed 'DateRange'");

			        Common.waitForElement(2);
			        waitFor(calendarApplyBtn2);
			        click(calendarApplyBtn2);

			        Common.waitForElement(2);
			        waitFor(generateBtnSoldOut2);
			        click(generateBtnSoldOut2);
			        System.out.println("✅ Successfully clicked Generate Button");
			        
			     // Open Export Histories
			        Common.waitForElement(2);
			        click(searchProductMenu);
			        type(searchProductMenu, "Export Histories");
			        click(clickExportHistories);
			        System.out.println("✅ Selected Product Sorts");

			        
			        //Export Histories
			        FluentWait<WebDriver> wait = new FluentWait<>(driver)
			                .withTimeout(Duration.ofMinutes(10))
			                .pollingEvery(Duration.ofSeconds(5))
			                .ignoring(NoSuchElementException.class)
			                .ignoring(StaleElementReferenceException.class);

			        WebElement downloadBtn = wait.until(d -> {
			            driver.navigate().refresh();
			            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

			            WebElement statusElement = d.findElement(
			                By.xpath("//tbody/tr[1]/td[6]//span[@class='d-inline-flex']")
			            );
			            String statusText = statusElement.getText().trim();
			            System.out.println("Current Status (row 1): " + statusText);

			            if ("Success".equalsIgnoreCase(statusText)) {
			                return d.findElement(By.xpath("//tbody/tr[1]//a[contains(@class,'cls_invoice_btn')]"));
			            }
			            return null; // keep waiting
			        });

			        // ✅ Now click download
			        Common.waitForElement(2);
			        downloadBtn.click();
			        System.out.println("✅ Download started from first row");
			        Thread.sleep(10000);
			        File file = validator.waitForDownload(downloadDir, fileName, 30);
			        System.out.println("✅ Second export saved: " + file.getAbsolutePath());
			        File b = new File(downloadDir + "SoldOutProducts2.xlsx");
			        System.out.println("File exists? " + b.exists());
			    }
			    
			    //Check
			    
			    public void verifyExportedSoldOutProductsMatch(String excelPath1, String excelPath2) throws IOException {
			        List<Map<String, Object>> firstExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath1);
			        List<Map<String, Object>> secondExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath2);

			        List<String> firstNames = firstExcelProducts.stream()
			                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
			                .filter(name -> !name.isEmpty())  // remove empty names
			                .collect(Collectors.toList());

			        List<String> secondNames = secondExcelProducts.stream()
			                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
			                .filter(name -> !name.isEmpty())
			                .collect(Collectors.toList());

			        // Compare counts first
			        if (firstNames.size() != secondNames.size()) {
			            System.out.println("❌ Number of products differ. SoldOutProducts1: " + firstNames.size() + " | SoldOutProducts2: " + secondNames.size());
			        }

			        // Compare actual missing products
			        List<String> missingInSecond = new ArrayList<>(firstNames);
			        missingInSecond.removeAll(secondNames);

			        List<String> missingInFirst = new ArrayList<>(secondNames);
			        missingInFirst.removeAll(firstNames);

			        if (!missingInSecond.isEmpty()) {
			            System.out.println("❌ Products missing in SoldOutProducts2:");
			            missingInSecond.forEach(p -> System.out.println("   - " + p));
			        }

			        if (!missingInFirst.isEmpty()) {
			            System.out.println("❌ Products missing in SoldOutProducts1:");
			            missingInFirst.forEach(p -> System.out.println("   - " + p));
			        }

			        if (!missingInFirst.isEmpty() || !missingInSecond.isEmpty() || firstNames.size() != secondNames.size()) {
			            Assert.fail("Products mismatch found.");
			        } else {
			            System.out.println("✅ Both Excel exports match perfectly.");
			        }
			    }
	
	
			    
			 // Inactive Products Excel File Match
				 
				 // Export from first page
				    public void exportInactiveProductsFromFirstPage(String dateRange, String fileName) throws InterruptedException {
				        Common.waitForElement(3);
				        waitFor(activeProductButton);
				        click(activeProductButton);
				        System.out.println("✅ Successfully clicked Active Product Button");

				        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,600);");

				        Common.waitForElement(2);
				        waitFor(exportBtnInactiveProducts1);
				        click(exportBtnInactiveProducts1);
				        System.out.println("✅ Successfully clicked export Button");

				        Common.waitForElement(2);
				        waitFor(createdAtBox);
				        click(createdAtBox);
				        System.out.println("✅ Successfully clicked Created At Box");

				        Common.waitForElement(2);
				        waitFor(dateInput);
				        dateInput.clear();
				        type(dateInput, dateRange);
				        System.out.println("Typed 'DateRange'");

				        Common.waitForElement(2);
				        waitFor(calendarApplyBtn1);
				        click(calendarApplyBtn1);

				        Common.waitForElement(2);
				        waitFor(generateBtn1);
				        click(generateBtn1);
				        Thread.sleep(10000);

				        File file = validator.waitForDownload(downloadDir, fileName, 30);
				        System.out.println("✅ First export saved: " + file.getAbsolutePath());
				        
				        File f = new File(downloadDir + "Inactive1.xlsx");
				        System.out.println("File exists? " + f.exists());
				    }
				    
				    
				 // Export from second page
				    public void exportFromInactiveProductsSecondPage(String dateRange, String fileName) throws InterruptedException {
				    	// Navigate to View All
				    	Common.waitForElement(5);
				        waitFor(viewAllBtnInactiveProducts);
				        click(viewAllBtnInactiveProducts);
				        System.out.println("✅ Navigated to View All products page.");
				        
				        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
				        Common.waitForElement(2);
				        waitFor(exportBtn2);
				        click(exportBtn2);
				        System.out.println("✅ Successfully clicked export Button");
				        
				        Common.waitForElement(2);
				        waitFor(createdAtBox1);
				        click(createdAtBox1);
				        System.out.println("✅ Successfully clicked Created At Box");

				        Common.waitForElement(2);
				        waitFor(dateInput1);
				        dateInput1.clear();
				        type(dateInput1, dateRange);
				        System.out.println("Typed 'DateRange'");

				        Common.waitForElement(2);
				        waitFor(calendarApplyBtn2);
				        click(calendarApplyBtn2);

				        Common.waitForElement(2);
				        waitFor(generateBtn2);
				        click(generateBtn2);
				        System.out.println("✅ Successfully clicked Generate Button");
				        
				     // Open Export Histories
				        Common.waitForElement(2);
				        click(searchProductMenu);
				        type(searchProductMenu, "Export Histories");
				        click(clickExportHistories);
				        System.out.println("✅ Selected Product Sorts");

				        
				        //Export Histories
				        FluentWait<WebDriver> wait = new FluentWait<>(driver)
				                .withTimeout(Duration.ofMinutes(10))
				                .pollingEvery(Duration.ofSeconds(5))
				                .ignoring(NoSuchElementException.class)
				                .ignoring(StaleElementReferenceException.class);

				        WebElement downloadBtn = wait.until(d -> {
				            driver.navigate().refresh();
				            try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

				            WebElement statusElement = d.findElement(
				                By.xpath("//tbody/tr[1]/td[6]//span[@class='d-inline-flex']")
				            );
				            String statusText = statusElement.getText().trim();
				            System.out.println("Current Status (row 1): " + statusText);

				            if ("Success".equalsIgnoreCase(statusText)) {
				                return d.findElement(By.xpath("//tbody/tr[1]//a[contains(@class,'cls_invoice_btn')]"));
				            }
				            return null; // keep waiting
				        });

				        // ✅ Now click download
				        Common.waitForElement(2);
				        downloadBtn.click();
				        System.out.println("✅ Download started from first row");
				        Thread.sleep(10000);
				        File file = validator.waitForDownload(downloadDir, fileName, 30);
				        System.out.println("✅ Second export saved: " + file.getAbsolutePath());
				        File b = new File(downloadDir + "InactiveProducts2.xlsx");
				        System.out.println("File exists? " + b.exists());
				    }
				    
				    //Check
				    
				    public void verifyExportedInactiveProductsMatch(String excelPath1, String excelPath2) throws IOException {
				        List<Map<String, Object>> firstExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath1);
				        List<Map<String, Object>> secondExcelProducts = ExcelXLSReader.readProductsWithMultipleListing(excelPath2);

				        List<String> firstNames = firstExcelProducts.stream()
				                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
				                .filter(name -> !name.isEmpty())  // remove empty names
				                .collect(Collectors.toList());

				        List<String> secondNames = secondExcelProducts.stream()
				                .map(p -> p.get("Product Name") == null ? "" : p.get("Product Name").toString().trim())
				                .filter(name -> !name.isEmpty())
				                .collect(Collectors.toList());

				        // Compare counts first
				        if (firstNames.size() != secondNames.size()) {
				            System.out.println("❌ Number of products differ. InactiveProducts1: " + firstNames.size() + " | InactiveProducts2: " + secondNames.size());
				        }

				        // Compare actual missing products
				        List<String> missingInSecond = new ArrayList<>(firstNames);
				        missingInSecond.removeAll(secondNames);

				        List<String> missingInFirst = new ArrayList<>(secondNames);
				        missingInFirst.removeAll(firstNames);

				        if (!missingInSecond.isEmpty()) {
				            System.out.println("❌ Products missing in InactiveProducts2:");
				            missingInSecond.forEach(p -> System.out.println("   - " + p));
				        }

				        if (!missingInFirst.isEmpty()) {
				            System.out.println("❌ Products missing in InactiveProducts1:");
				            missingInFirst.forEach(p -> System.out.println("   - " + p));
				        }

				        if (!missingInFirst.isEmpty() || !missingInSecond.isEmpty() || firstNames.size() != secondNames.size()) {
				            Assert.fail("Products mismatch found.");
				        } else {
				            System.out.println("✅ Both Excel exports match perfectly.");
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
