package utility;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class CaptureScreenshot{
		 
	//================================screenshot used for storing in folder==========================================
	public static void getscreenshots(WebDriver driver, String screenshotname,String Filepath) throws IOException
	{
	File scr=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scr, new File(Filepath+screenshotname+".png"));
	}
	
	//==============================screenshot to be used in extent report==============================================
	public static String capture(WebDriver driver,String screenShotName) throws IOException 
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"\\ErrorScreenshots\\"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
                     
        return dest;
    }
}
