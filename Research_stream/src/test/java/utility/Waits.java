package utility;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import pageObjects.Readobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits extends Readobject {
	WebDriver driver;
	
	//======================================Constructor=========================================================================
	 public Waits(WebDriver driver) throws Exception{
		  this.driver=driver;
		  }
	 
	//===============================================Wait keywords================================================	
		 public void common_operations(Properties p,String operation,String objectname,String objectType,String value) throws Exception
		 {
		 switch(operation.toUpperCase())
		 {
		 
		 case "IMPLICITWAIT":
				driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS);
				break;
				
			case "EXPLICITWAIT":
				//long time1 = (long)Double.parseDouble(value);
				WebDriverWait wait= new WebDriverWait(driver, 6);
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(this.getLocator(p, objectname, objectType))));
				break;
				
			case "DRIVERWAIT":
				try
				{
					  long time = (long)Double.parseDouble(value);
				        Thread.sleep(time*1000L);}
				catch (NumberFormatException e){
				       System.out.println("not a number"); }
				break;		
}
		 }
}
