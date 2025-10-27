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
import java.util.Random;
import java.util.Scanner;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import context.TestContext;
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
	        System.out.println(GREEN + "✅ Admin Login Successful" + RESET);

	    
	}

	 private ExportValidator validator = new ExportValidator();
	    private String downloadDir ="C:\\Users\\Sarojkumar\\Downloads\\";
	
// Export Botton one test case    
	    String fileName;
	    String dateRange;
	    String statusValue;
	    String CYAN;
	    String RESET;
	    String GREEN;
	    String YELLOW;
	    
	    
//TC-01		    
//Order > Placed > Orders Page	 
	    
	    public void verifyAllOrderStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Order Placed",
	            "Order Shipped",
	            "Out For Delivery",
	            "Order Delivered",
	             "Order Delay"
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";
	         
	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for ALL order Placed statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println(GREEN +"🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅"+ RESET);
	    }

	    public void runSingleStatusExport(String statusValue, String dateRange) throws InterruptedException, IOException {

	        // ✅ Go to Admin Order page
	        Common.waitForElement(3);
	        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	        System.out.println("✅ Redirected to Admin Expected Page" );

	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Common.waitForElement(2);

	        waitFor(orderExportBtn);
	        click(orderExportBtn);
	        System.out.println("✅ Clicked Order Export button");

	        // ✅ Select Status
	        Common.waitForElement(2);
	        click(statusBtn);
	        Common.waitForElement(1);
	        click(statusSearchBox);
	        type(statusSearchBox, statusValue);
	        statusSearchBox.sendKeys(Keys.ENTER);
	        System.out.println("✅ Selected Status: " + statusValue);

	        // ✅ Apply Date Range
	        Common.waitForElement(1);
	        click(orderCreatedAtBox);
	        orderDateInput.clear();
	        type(orderDateInput, dateRange);
	        click(orderCalendarApplyBtn);
	        System.out.println("✅ Applied date range: " + dateRange);

	        // ✅ Generate Export
	        waitFor(orderGenerateBtn);
	        click(orderGenerateBtn);
	        System.out.println("✅ Clicked Generate button");

	        // ✅ Go to Export Histories
	        Common.waitForElement(2);
	        click(searchMenu);
	        type(searchMenu, "Export Histories");
	        click(selectExportHistories);
	        System.out.println("✅ Opened Export Histories page");

	        // ✅ Wait until export = Success
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

	        // ✅ Click download when ready
	        Common.waitForElement(2);
	        downloadBtn.click();
	        System.out.println("✅  Export download started");

	        Thread.sleep(10000);
	        File file = validator.waitForDownload(downloadDir, fileName, 30);
	        System.out.println("✅  Export saved: " + file.getAbsolutePath());
	    }
	    

	   	    /** ✅ Verify exported Excel file date range */
	    public void verifyExportedDateranges() throws IOException, ParseException {
	        String[] parts = dateRange.split(" - ");
	        if (parts.length != 2)
	            throw new IllegalArgumentException("❌ Invalid date range format. Expected: yyyy-MM-dd - yyyy-MM-dd");

	        String startDateStr = parts[0].trim();
	        String endDateStr = parts[1].trim();

	        String excelPath = downloadDir + fileName;
	        List<Map<String, Object>> exportedData = ExcelXLSReader.readProductsWithMultipleListing(excelPath);

	        SimpleDateFormat excelFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        SimpleDateFormat rangeFormat = new SimpleDateFormat("yyyy-MM-dd");

	        Date startDate = rangeFormat.parse(startDateStr);
	        Date endDate = rangeFormat.parse(endDateStr);

	        Calendar cal = Calendar.getInstance();
	        cal.setTime(endDate);
	        cal.add(Calendar.DAY_OF_MONTH, 1);
	        cal.add(Calendar.SECOND, -1);
	        Date inclusiveEndDate = cal.getTime();

	        boolean invalidFound = false;
	        System.out.println("🔍 Checking exported 'Created At' dates between " + startDateStr + " and " + endDateStr);

	        for (Map<String, Object> row : exportedData) {
	            Object dateValue = row.get("Created At");
	            if (dateValue == null) continue;
	            String dateStr = dateValue.toString().trim();
	            if (dateStr.isEmpty()) continue;

	            try {
	                Date recordDate = excelFormat.parse(dateStr);
	                if (recordDate.before(startDate) || recordDate.after(inclusiveEndDate)) {
	                    System.out.println("❌ Out-of-range date found: " + dateStr);
	                    invalidFound = true;
	                }
	            } catch (ParseException e) {
	                System.out.println("⚠️ Invalid date format in Excel: " + dateStr);
	                invalidFound = true;
	            }
	        }

	        if (invalidFound)
	            Assert.fail("❌ One or more 'Created At' dates are outside selected range: " + dateRange);
	        else
	            System.out.println("✅ All 'Created At' dates within selected range ✅");
	    }
	
//TC-02	    
//Order > Placed >Return Order page
	
	    public void verifyAllReturnStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Return Requested",
	            "Pickup Expected",
	            "Refund Initiated",
	            "Refund Credited",
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for ALL Order Return statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	    }
	
	
	
//TC-03		
 //Order > Placed > Exchange Order page
		
	    public void verifyAllExchangeStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Exchange Requested",
	            "Product Pickup",
	            "Product Received",
	            "Exchanged",
	            "Exchange Order Shipped",
	            "Exchange Out For Delivery",
	            "Exchange Delivered",
	            "Exchange Order Delay",
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for ALL Order Exchange  statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	    }
	
//TC-04	
// Order > Canceled >  Order page	
		
	    public void verifyAllCancledStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Order Cancelled",
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for All  Canceled Order statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	    }
	
 //TC-05	
//Order > Canceled > Return Cancel page
		
	    public void verifyAllCancledReturnStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Order Delivered",
	            "Return Rejected",
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for All  Canceled Return statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	    }
	
//TC-06	
//Order > Canceled > Exchange Cancel page		
	    public void verifyAllCancledExchangeStatusesSequentially() throws InterruptedException, IOException, ParseException {

	        Scanner sc = new Scanner(System.in);

	        // ✅ All order statuses to check
	        String[] statuses = {
	            "All",
	            "Exchange Cancelled",
	            "Product Out Of Stock",
	            "Product Received in Damage State",
	        };
	        driver.manage().window().minimize();

	        // ANSI escape codes
	          CYAN = "\u001B[36;1m";  // Bright cyan
	          RESET = "\u001B[0m";    // Reset to default color
	          GREEN = "\u001B[32;1m";
	          YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for All  Canceled Exchange  statuses" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        // ✅ Login once before starting all exports
	        driver.manage().window().maximize();
	        
	        adminLoginApp();

	        // ✅ Loop through all statuses using same date range
	        for (int i = 0; i < statuses.length; i++) {
	            statusValue = statuses[i];
	            

	            System.out.println("\n-------------------------------------------------");
	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	            System.out.println("-------------------------------------------------");

	            int randomNum = new Random().nextInt(1000);
	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	            // ✅ Run export for this status
	            runSingleStatusExport(statusValue, dateRange);

	            // ✅ Verify Excel
	            verifyExportedDateranges();

	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	            System.out.println("-------------------------------------------------\n");
	        }

	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	    }
	
	
	    
//TC-07
 //Payment Pending page	    
	    public void verifyPaymentPendingStatusesSequentially() throws InterruptedException, IOException, ParseException {
	        Scanner sc = new Scanner(System.in);
	        
	        driver.manage().window().minimize();

	        // ANSI colors
	        CYAN = "\u001B[36;1m";
	        RESET = "\u001B[0m";
	        GREEN = "\u001B[32;1m";
	        YELLOW = "\u001B[33;1m";

	        System.out.println("\n=================================================");
	        System.out.println(CYAN + "📋 Please enter date range for Payment Pending export" + RESET);
	        System.out.println("=================================================");

	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	        String startDate = sc.nextLine().trim();

	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	        String endDate = sc.nextLine().trim();

	        dateRange = startDate + " - " + endDate;
	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	        driver.manage().window().maximize();
	        adminLoginApp();

	        // ✅ Single export (no status selection)
	        System.out.println(YELLOW + "\n🔹 Running Export for All Orders" + RESET);

	        int randomNum = new Random().nextInt(1000);
	        fileName = "Export_AllOrders_" + randomNum + ".xlsx";

	        runSimpleDateRangeExport(dateRange);

	        // ✅ Verify Excel
	        verifyExportedDateranges();

	        System.out.println(GREEN + "\n🎉 EXPORT COMPLETED SUCCESSFULLY ✅" + RESET);
	    }
	
	
	
	    public void runSimpleDateRangeExport(String dateRange) throws InterruptedException, IOException {

	        // ✅ Go to Admin Order page
	        Common.waitForElement(3);
	        driver.get(Common.getValueFromTestDataMap("ExcelPath"));
	        System.out.println("✅ Redirected to Admin Expected Page");

	        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
	        Common.waitForElement(2);

	        waitFor(orderExportBtn);
	        click(orderExportBtn);
	        System.out.println("✅ Clicked Order Export button");

	        // ✅ Enter Date Range only (no status)
	        Common.waitForElement(2);
	        click(orderCreatedAtBox);
	        orderDateInput.clear();
	        type(orderDateInput, dateRange);
	        click(orderCalendarApplyBtn);
	        System.out.println("✅ Applied date range: " + dateRange);

	        // ✅ Click Generate
	        waitFor(orderGenerateBtn);
	        click(orderGenerateBtn);
	        System.out.println("✅ Clicked Generate button");

	        // ✅ Open Export Histories
	        Common.waitForElement(2);
	        click(searchMenu);
	        type(searchMenu, "Export Histories");
	        click(selectExportHistories);
	        System.out.println("✅ Opened Export Histories page");

	        // ✅ Wait until export = Success
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
	            return null;
	        });

	        // ✅ Click download when ready
	        Common.waitForElement(2);
	        downloadBtn.click();
	        System.out.println("✅ Export download started");

	        Thread.sleep(10000);
	        File file = validator.waitForDownload(downloadDir, fileName, 30);
	        System.out.println("✅ Export saved: " + file.getAbsolutePath());
	    }
	    
 //TC-08	
//Payment Refund page		
	  	  	    public void verifyPaymentRefundStatusesSequentially() throws InterruptedException, IOException, ParseException {

	  	  	        Scanner sc = new Scanner(System.in);

	  	  	        // ✅ All order statuses to check
	  	  	        String[] statuses = {
	  	  	            "All",
	  	  	            "Order Cancelled",
	  	  	            "Refund Initiated",
	  	  	            "Refund  Credited",
	  	  	        };
	  	  	        driver.manage().window().minimize();

	  	  	        // ANSI escape codes
	  	  	          CYAN = "\u001B[36;1m";  // Bright cyan
	  	  	          RESET = "\u001B[0m";    // Reset to default color
	  	  	          GREEN = "\u001B[32;1m";
	  	  	          YELLOW = "\u001B[33;1m";

	  	  	        System.out.println("\n=================================================");
	  	  	        System.out.println(CYAN + "📋 Please enter date range for Payment Refund  statuses" + RESET);
	  	  	        System.out.println("=================================================");

	  	  	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	  	  	        String startDate = sc.nextLine().trim();

	  	  	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	  	  	        String endDate = sc.nextLine().trim();

	  	  	        dateRange = startDate + " - " + endDate;
	  	  	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	  	  	        // ✅ Login once before starting all exports
	  	  	        driver.manage().window().maximize();
	  	  	        
	  	  	        adminLoginApp();

	  	  	        // ✅ Loop through all statuses using same date range
	  	  	        for (int i = 0; i < statuses.length; i++) {
	  	  	            statusValue = statuses[i];
	  	  	            

	  	  	            System.out.println("\n-------------------------------------------------");
	  	  	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	  	  	            System.out.println("-------------------------------------------------");

	  	  	            int randomNum = new Random().nextInt(1000);
	  	  	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	  	  	            // ✅ Run export for this status
	  	  	            runSingleStatusExport(statusValue, dateRange);

	  	  	            // ✅ Verify Excel
	  	  	            verifyExportedDateranges();

	  	  	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	  	  	            System.out.println("-------------------------------------------------\n");
	  	  	        }

	  	  	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	  	  	    }	
	  		    
	    
//TC-09
//Payment Failed page	    
	  	  		    public void verifyPaymentFailedStatusesSequentially() throws InterruptedException, IOException, ParseException {
	  	  		        Scanner sc = new Scanner(System.in);
	  	  		        
	  	  		        driver.manage().window().minimize();

	  	  		        // ANSI colors
	  	  		        CYAN = "\u001B[36;1m";
	  	  		        RESET = "\u001B[0m";
	  	  		        GREEN = "\u001B[32;1m";
	  	  		        YELLOW = "\u001B[33;1m";

	  	  		        System.out.println("\n=================================================");
	  	  		        System.out.println(CYAN + "📋 Please enter date range for Payment Failed export" + RESET);
	  	  		        System.out.println("=================================================");

	  	  		        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	  	  		        String startDate = sc.nextLine().trim();

	  	  		        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	  	  		        String endDate = sc.nextLine().trim();

	  	  		        dateRange = startDate + " - " + endDate;
	  	  		        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	  	  		        driver.manage().window().maximize();
	  	  		        adminLoginApp();

	  	  		        // ✅ Single export (no status selection)
	  	  		        System.out.println(YELLOW + "\n🔹 Running Export for All Orders" + RESET);

	  	  		        int randomNum = new Random().nextInt(1000);
	  	  		        fileName = "Export_AllOrders_" + randomNum + ".xlsx";

	  	  		        runSimpleDateRangeExport(dateRange);

	  	  		        // ✅ Verify Excel
	  	  		        verifyExportedDateranges();

	  	  		        System.out.println(GREEN + "\n🎉 EXPORT COMPLETED SUCCESSFULLY ✅" + RESET);
	  	  		    }	  	  	    
	  	  	    
	  	  	    
	
//TC-10	
//RTO Orders page		
	  	    public void verifyRTOOrdersStatusesSequentially() throws InterruptedException, IOException, ParseException {

	  	        Scanner sc = new Scanner(System.in);

	  	        // ✅ All order statuses to check
	  	        String[] statuses = {
	  	            "All",
	  	            "RTO Initiated",
	  	            "RTO In Transit",
	  	            "RTO Out For Delivery",
	  	            "RTO Delivered",
	  	        };
	  	        driver.manage().window().minimize();

	  	        // ANSI escape codes
	  	          CYAN = "\u001B[36;1m";  // Bright cyan
	  	          RESET = "\u001B[0m";    // Reset to default color
	  	          GREEN = "\u001B[32;1m";
	  	          YELLOW = "\u001B[33;1m";

	  	        System.out.println("\n=================================================");
	  	        System.out.println(CYAN + "📋 Please enter date range for RTO Orders  statuses" + RESET);
	  	        System.out.println("=================================================");

	  	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	  	        String startDate = sc.nextLine().trim();

	  	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	  	        String endDate = sc.nextLine().trim();

	  	        dateRange = startDate + " - " + endDate;
	  	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	  	        // ✅ Login once before starting all exports
	  	        driver.manage().window().maximize();
	  	        
	  	        adminLoginApp();

	  	        // ✅ Loop through all statuses using same date range
	  	        for (int i = 0; i < statuses.length; i++) {
	  	            statusValue = statuses[i];
	  	            

	  	            System.out.println("\n-------------------------------------------------");
	  	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	  	            System.out.println("-------------------------------------------------");

	  	            int randomNum = new Random().nextInt(1000);
	  	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	  	            // ✅ Run export for this status
	  	            runSingleStatusExport(statusValue, dateRange);

	  	            // ✅ Verify Excel
	  	            verifyExportedDateranges();

	  	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	  	            System.out.println("-------------------------------------------------\n");
	  	        }

	  	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
	  	    }	
	
//TC-11
//All Orders page		
	  		  	    public void verifyAllOrdersStatusesSequentially() throws InterruptedException, IOException, ParseException {

	  		  	        Scanner sc = new Scanner(System.in);

	  		  	        // ✅ All order statuses to check
	  		  	        String[] statuses = {
	  		  	            "All",
	  		  	            "Order Placed",
	  		  	            "Order Shipped",
	  		  	            "Out For Delivery",
	  		  	            "Order Delivered",
		  		  	        "Order Cancelled",
	  		  	            "Exchange Requested",
	  		  	            "Product Pickup",
	  		  	            "Product Received",
	  		  	            "Exchanged",
		  		  	        "Exchange Order Shipped",
	  		  	            "Exchange Out For Delivery",
	  		  	            "Exchange Delivered",
	  		  	            "Exchange Cancelled",
	  		  	            "Return Requested",
		  		  	        "Pickup Expected",
	  		  	            "Refund Initiated",
	  		  	            "Refund Credited",
	  		  	            "Return Rejected",
	  		  	            "Product Out Of Stock",
		  		  	        "Product Received in Damage State",
			  		  	    "RTO Initiated",
	  		  	            "Order Delay",
	  		  	            "RTO In Transit",
	  		  	            "RTO Out For Delivery",
	  		  	            "RTO Delivered",
	  		  	            
	  		  	        };
	  		  	        driver.manage().window().minimize();

	  		  	        // ANSI escape codes
	  		  	          CYAN = "\u001B[36;1m";  // Bright cyan
	  		  	          RESET = "\u001B[0m";    // Reset to default color
	  		  	          GREEN = "\u001B[32;1m";
	  		  	          YELLOW = "\u001B[33;1m";

	  		  	        System.out.println("\n=================================================");
	  		  	        System.out.println(CYAN + "📋 Please enter date range for All Orders  statuses" + RESET);
	  		  	        System.out.println("=================================================");

	  		  	        System.out.print(CYAN + "📅 Enter Start Date (YYYY-MM-DD): " + RESET);
	  		  	        String startDate = sc.nextLine().trim();

	  		  	        System.out.print(CYAN + "📅 Enter End Date (YYYY-MM-DD): " + RESET);
	  		  	        String endDate = sc.nextLine().trim();

	  		  	        dateRange = startDate + " - " + endDate;
	  		  	        System.out.println(GREEN + "✅ Selected Date Range: " + dateRange + RESET);

	  		  	        // ✅ Login once before starting all exports
	  		  	        driver.manage().window().maximize();
	  		  	        
	  		  	        adminLoginApp();

	  		  	        // ✅ Loop through all statuses using same date range
	  		  	        for (int i = 0; i < statuses.length; i++) {
	  		  	            statusValue = statuses[i];
	  		  	            

	  		  	            System.out.println("\n-------------------------------------------------");
	  		  	            System.out.println(YELLOW + "🔹 Running Export for Status: " + statusValue + RESET);
	  		  	            System.out.println("-------------------------------------------------");

	  		  	            int randomNum = new Random().nextInt(1000);
	  		  	            fileName = "Export_" + statusValue.replace(" ", "_") + "_" + randomNum + ".xlsx";

	  		  	            // ✅ Run export for this status
	  		  	            runSingleStatusExport(statusValue, dateRange);

	  		  	            // ✅ Verify Excel
	  		  	            verifyExportedDateranges();

	  		  	            System.out.println(GREEN + "✅✅ STATUS '" + statusValue + "' completed successfully ✅✅" + RESET);
	  		  	            System.out.println("-------------------------------------------------\n");
	  		  	        }

	  		  	        System.out.println("🎉 ALL STATUSES COMPLETED SUCCESSFULLY ✅");
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
