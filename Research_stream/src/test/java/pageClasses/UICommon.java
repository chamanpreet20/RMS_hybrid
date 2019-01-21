package pageClasses;

import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pageObjects.Readobject;

public class UICommon extends Readobject {
	WebDriver driver;
	
	
	
	//============================================Constructor==========================================================
	public UICommon(WebDriver driver) throws Exception
	{
		this.driver=driver;
	}
	
	
	//===============================================Common keywords================================================	
	 public void common_operations(Properties p,String operation,String objectname,String objectType,String value) throws Exception
	 {
	 switch(operation.toUpperCase())
	 {
	 case "GOTOURL":
			driver.get(p.getProperty(value));
			break;
			
	 case "CLICK":
	       driver.findElement(this.getLocator(p, objectname, objectType)).click();
	 break;
	 
	 case "SETTEXT":
			driver.findElement(this.getLocator(p, objectname, objectType)).sendKeys(value);
		break;
		
	 case "VERIFY":
			String element = driver.findElement(this.getLocator(p, objectname, objectType)).getAttribute("innerHTML");
			System.out.println(element);
			try
			{
			Assert.assertEquals(element, value);
			System.out.println("data match, Proceed !!!");
			}
			catch(Exception e)
			{
				System.out.println("Data do not matche and give exception- " +e.getMessage());
			}
			break;
			
		case "SCROLL":
			JavascriptExecutor jse=(JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,450)", "");
			break;
			
		case "CLOSE":
			//APP_LOGS.info("Closing browser");
			driver.close();
//			APP_LOGS.info("Closed browser");
			break;
	
			 
		
		
	 default:
			break;
	 }
	 }
}
