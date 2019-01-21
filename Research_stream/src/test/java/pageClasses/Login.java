package pageClasses;

import java.util.Properties;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import pageObjects.Readobject;

public class Login extends Readobject {
	WebDriver driver;
	
	//======================================Constructor=========================================================================
		 public Login(WebDriver driver) throws Exception{
			  this.driver=driver;
			  }
		 
	//===============================================Login page specific keywords================================================	
		 public void login_actions(Properties p,String operation,String objectname,String objectType,String value) throws Exception
		 {
		 switch(operation.toUpperCase())
		 {
				
		 case "select_down":
			 driver.findElement(this.getLocator(p, objectname, objectType)).sendKeys(Keys.DOWN);
			 driver.findElement(this.getLocator(p, objectname, objectType)).sendKeys(Keys.ENTER);
			 break;
			 
		 default:
				break;
		 }
		 }
}
