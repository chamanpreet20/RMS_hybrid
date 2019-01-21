package utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import actionPackage.Hybrid_execution;

public class SelectBrowser{
	public static WebDriver driver;
	
	
	/* public SelectBrowser(WebDriver driver){
		  this.driver=driver;
		  }*/
	 
	public static WebDriver selectBrowser(String browser){
		
		if (browser.equalsIgnoreCase("firefox"))
				{
			System.setProperty("webdriver.gecko.driver", Constants.geckodriverpath);
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			return driver;
			
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("chrome browser");
			System.setProperty("webdriver.chrome.driver",Constants.chromedriverpath);
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			return driver;
			
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.chrome.driver",Constants.IEdriverpath);
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			return driver;
		}
		return null;
	}
}
