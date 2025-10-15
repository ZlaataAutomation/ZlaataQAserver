package pages;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import objectRepo.AdminPanelExportObjRepo;
import utils.Common;
import utils.ExcelXLSReader;
import utils.ExportValidator;

public class AdminPanelExportPage extends AdminPanelExportObjRepo {
	
	public AdminPanelExportPage(WebDriver driver) 
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

	
	
	 private ExportValidator validator = new ExportValidator();
	    private String downloadDir ="C:\\Users\\Sarojkumar\\Downloads\\";
	    
	    public void verifyOrderExportBtn(String dateRange, String fileName) throws InterruptedException {

	    	Common.waitForElement(4);
		    driver.get(Common.getValueFromTestDataMap("ExcelPath"));
		    System.out.println("✅ Successfull redirect to Adimn Order page");

	    	 // Scroll to bottom to ensure export button is visible
	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Common.waitForElement(2);
	        waitFor(orderExportBtn); // renamed for clarity
	        click(orderExportBtn);
	        System.out.println("✅ Successfully clicked Order Export button");

	        // Select Date Range
	        Common.waitForElement(2);
	        waitFor(orderCreatedAtBox);
	        click(orderCreatedAtBox);
	        System.out.println("✅ Successfully clicked Created At box");

	        Common.waitForElement(2);
	        waitFor(orderDateInput);
	        orderDateInput.clear();
	        type(orderDateInput, dateRange);
	        System.out.println("📅 Entered date range: " + dateRange);

	        Common.waitForElement(2);
	        waitFor(orderCalendarApplyBtn);
	        click(orderCalendarApplyBtn);
	        System.out.println("✅ Applied selected date range");

	        // Generate export
	        Common.waitForElement(2);
	        waitFor(orderGenerateBtn);
	        click(orderGenerateBtn);
	        System.out.println("✅ Successfully clicked Generate button");

	        // Navigate to Export Histories
	        Common.waitForElement(2);
	        click(searchMenu);
	        type(searchMenu, "Export Histories");
	        click(selectExportHistories);
	        System.out.println("✅ Opened Export Histories page");

	        // Wait for export to complete and download
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
	            System.out.println("📊 Current Status (row 1): " + statusText);

	            if ("Success".equalsIgnoreCase(statusText)) {
	                return d.findElement(By.xpath("//tbody/tr[1]//a[contains(@class,'cls_invoice_btn')]"));
	            }
	            return null; // keep waiting
	        });

	        // Click download when ready
	        Common.waitForElement(2);
	        downloadBtn.click();
	        System.out.println("✅ Order export download started");

	        Thread.sleep(10000);
	        File file = validator.waitForDownload(downloadDir, fileName, 30);
	        System.out.println("✅ Order export saved: " + file.getAbsolutePath());
	    }
	
	    
	    public void verifyExportedDateRange(String fileName, String dateRange) throws IOException, ParseException {
	        // Expected format: "2025-10-01 - 2025-10-15"
	        String[] parts = dateRange.split(" - ");
	        if (parts.length != 2) {
	            throw new IllegalArgumentException("❌ Invalid date range format. Expected: yyyy-MM-dd - yyyy-MM-dd");
	        }

	        String startDateStr = parts[0].trim();
	        String endDateStr = parts[1].trim();

	        // Excel file path
	        String excelPath = downloadDir + fileName;
	        List<Map<String, Object>> exportedData = ExcelXLSReader.readProductsWithMultipleListing(excelPath);

	        // Your Excel "Created At" values are in "yyyy-MM-dd HH:mm:ss"
	        SimpleDateFormat excelFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat rangeFormat = new SimpleDateFormat("yyyy-MM-dd");

	        Date startDate = rangeFormat.parse(startDateStr);
	        Date endDate = rangeFormat.parse(endDateStr);

	        // ✅ Make endDate inclusive (add 23:59:59)
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(endDate);
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        cal.add(Calendar.SECOND, -1);
	        Date inclusiveEndDate = cal.getTime();

	        boolean invalidFound = false;
	        System.out.println("🔍 Checking exported 'Created At' dates are between " 
	                + startDateStr + " and " + endDateStr + " (inclusive)");

	        for (Map<String, Object> row : exportedData) {
	            Object dateValue = row.get("Created At");   // column name in Excel
	            if (dateValue == null) continue;

	            String dateStr = dateValue.toString().trim();
	            if (dateStr.isEmpty()) continue;

	            try {
	                Date recordDate = excelFormat.parse(dateStr);

	                // ✅ Inclusive range check
	                if (recordDate.before(startDate) || recordDate.after(inclusiveEndDate)) {
	                    System.out.println("❌ Out-of-range date found: " + dateStr);
	                    invalidFound = true;
	                }
	            } catch (ParseException e) {
	                System.out.println("⚠️ Invalid date format in Excel: " + dateStr);
	                invalidFound = true;
	            }
	        }

	        if (invalidFound) {
	            Assert.fail("❌ One or more 'Created At' dates are outside the selected range: " + dateRange);
	        } else {
	            System.out.println("✅ All 'Created At' dates fall within the selected range: " + dateRange);
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
