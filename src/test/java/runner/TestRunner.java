package runner;


import org.junit.AfterClass;
import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import utils.EmailSendUtils;
import utils.ExcelReportUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

@RunWith(Cucumber.class)
@CucumberOptions( plugin = {"pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","html:target/cucumber-reports.html"},
features = {"src/test/resources/features/"},
glue = "stepDef",	
monochrome = true,	
dryRun = false,
tags= "@Sorting"
)

		
public class TestRunner {	
	
	static {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
        Logger.getLogger("org.apache.http").setLevel(Level.OFF);
        System.setProperty("webdriver.chrome.logfile", "NUL");
        System.setProperty("webdriver.chrome.verboseLogging", "false");
	}


        
//    }

@AfterClass
public static void generateExcelReport() {
	 ExcelReportUtil.generateExcelReport();
//	 EmailSendUtils.sendEmail();
}
}







