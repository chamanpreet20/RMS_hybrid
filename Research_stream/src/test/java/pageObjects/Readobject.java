package pageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class Readobject {
	Properties p;
	File src;
	FileInputStream fis;

	//===============================================Load property file=================================================
	public Properties Readobjectprop()
	{
		p=new Properties();

	    src=new File(System.getProperty("user.dir")+"\\src\\test\\java\\pageObjects\\login.properties");
		try {
			fis=new FileInputStream(src);
			p.load(fis);
		
	
		src=new File(System.getProperty("user.dir")+"\\src\\test\\java\\pageObjects\\NewRequest.properties");
			fis=new FileInputStream(src);
			p.load(fis);
		
	
		src=new File(System.getProperty("user.dir")+"\\src\\test\\java\\pageObjects\\HomePage.properties");
			fis=new FileInputStream(src);		
			p.load(fis);
		
			fis.close();
		
		System.out.println("Property class loaded");
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	//=====================================Get locator type from object repository and add to By tag==============================
	public By getLocator(Properties p,String objectname,String objectType) throws Exception {
	         
	        // retrieve the specified object from the object list
	        String locator = p.getProperty(objectname);
	      
	        // return a instance of the By class based on the type of the locator
	        // this By can be used by the browser object in the actual test
	        switch (objectType.toUpperCase()) {
			case "ID":
				return By.id(locator);
			case "NAME":
				return By.name(locator);
			case "TAGNAME":
				return By.tagName(locator);
			case "LINK":
				return By.linkText(locator);
			case "PARTIALLINK":
				return By.partialLinkText(locator);
			case "XPATH":
				return By.xpath(locator);
			case "CSS":
				return By.cssSelector(locator);
			case "CLASSNAME":
				return By.className(locator);
			default:
				return null;
			}
	    }
}
