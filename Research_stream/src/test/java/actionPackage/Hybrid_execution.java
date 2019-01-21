package actionPackage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import excelIO.ExcelReader;
import pageClasses.UICommon;
import pageObjects.Readobject;
import utility.Constants;
import utility.JypersionListener;
import utility.SelectBrowser;
import utility.Waits;
import utility.CaptureScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



@Listeners(JypersionListener.class)
public class Hybrid_execution extends SelectBrowser{

	ExcelReader file = new ExcelReader();
	Readobject object = new Readobject();
	String filepath=Constants.filepath;
	String fileName=Constants.fileName;
	WebDriver webdriver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static Logger App_logs=Logger.getLogger("devpinoyLogger");
	
	
//================================Test class fetching data from testcases tab for executing corresponding test steps================================
	
	@Test(dataProvider = "Data")
	public void executionclass(String TestName, String description,String runMode,String Browser) throws Exception {
		//WebDriver webdriver = SelectBrowser.driver;
		System.out.println(TestName+ " ### " +runMode +" ### " +Browser);
		String reportKeyword=new StringBuilder().append(TestName).toString();
		Properties allObjects = object.Readobjectprop();
		
//===================================Match test case name with test step sheet and execute if Run mode=Y==============================================
		        if(runMode.equals("Y"))
		        	{
		        	test = extent.createTest(TestName,"Test Case: "+TestName+ " Running on Browser: "+Browser );
		        	SelectBrowser.selectBrowser(Browser);
		        		Sheet sheet1=file.readExcel(filepath, fileName, "Test_steps");
		        		int rowCount1 = sheet1.getLastRowNum()-sheet1.getFirstRowNum();
		        		   for (int a = 1; a < rowCount1; a++) {
		        			   Row row1 = sheet1.getRow(a);
		        			   String testcasename2=row1.getCell(0).toString();	
		        				if(TestName.equalsIgnoreCase(testcasename2) && row1.getCell(0).toString().length()!=0)
		        					{
		        					App_logs.info("\n\n" +"Started Executing testcase----> " +TestName + "\n");
		        					
		    						int x=row1.getRowNum()+1;
		    						System.out.println("Executing row " +x + " of test step sheet");
		    						System.out.println("Testcase name matches " +testcasename2);
		    						while(sheet1.getRow(x).getCell(0).toString().isEmpty()||sheet1.getRow(x).getCell(0).getCellType()==Cell.CELL_TYPE_BLANK)
		    						{
		    							Row row3=sheet1.getRow(x);
		    							x++;
		 	                         	System.out.println(row3.getCell(1).toString()+"----"+ row3.getCell(2).toString()+"----"+
		 	        			         row3.getCell(3).toString()+"----"+ row3.getCell(4).toString());
		 	                         	
			        				//	Login log=new Login(SelectBrowser.driver);
			        					UICommon operation = new UICommon(SelectBrowser.driver);
		 	                         		 	          
		 	                         	//	Call common operations function to perform operation on UI
		   			            operation.common_operations(allObjects, row3.getCell(1).toString(), row3.getCell(2).toString(),
		   			               row3.getCell(3).toString(), row3.getCell(4).toString());
		   			            
		   			            Waits wait=new Waits(SelectBrowser.driver);
		   			            
		   			                 wait.common_operations(allObjects, row3.getCell(1).toString(), row3.getCell(2).toString(),
		   			               row3.getCell(3).toString(), row3.getCell(4).toString());     
		   			                 
		   			         // Call login page functions to perform action based on keywords
		   			            
		   			    //     log.login_actions(allObjects, row3.getCell(1).toString(), row3.getCell(2).toString(),
		   			     //         row3.getCell(3).toString(), row3.getCell(4).toString());
		   			            
		 						}
		    						System.out.println("value of x is " +x);
		    	
		    							}
		        				
		        				//==========skip, If test case name not matches=======================================================
		        			/*	else if(!(TestName.equalsIgnoreCase(testcasename2))&&row1.getCell(0).toString().length()!=0)
		        				{
		        					System.out.println("Testcase is " +TestName);
		        					System.out.println("Teststep name is "+ testcasename2);
		        					throw new SkipException("Test case name not found in test steps");
		        				}
		        				else
		        				{
		        					continue;
		        				}*/
		        		}	
		        		   }
 //============================================Skip if Run mode=N======================================================
		        	else if(runMode.equals("N"))
		        	{
		        		test= extent.createTest(reportKeyword,"Test Case: "+ " Running on Browser: "+Browser );
		       		throw new SkipException("test case skipped as runmode is N");
		       	}
		        
//=============================================Skip if Invalid value is found=================================================
		        	else
		    		{
		    			throw new SkipException("test cases skipped due to invalid value");
		    		}
		}
	
	
//=============================================Data Provider calling method to get test_case data======================================	
	@DataProvider(name="Data")
	public  Object[][] data() throws Exception
	{
		return testLogin("Test_cases");
	}
	
	
//============================================Method returning object with data===========================================================
    public Object[][] testLogin(String Excelname) throws Exception {
    	
			Object[][] object1 = null;

			//Read Test_case sheet
			Sheet sheet = file.readExcel(filepath,fileName, "Test_cases");
			 int rowCount = sheet.getLastRowNum();
			 int col_count=sheet.getRow(0).getPhysicalNumberOfCells();
			 object1=new Object[rowCount][col_count];
    for (int i = 0; i<rowCount; i++) {
        Row row = sheet.getRow(i+1);
        
    							for(int j=0;j<row.getLastCellNum();j++)
    							{
    								Cell cell=row.getCell(j);
    								object1[i][j]=cell.toString();
    							}   						
    	}
	return object1;	          
	}
   
    
 //=============================Actions to be performed after every test case- generating logs, capturing screenshots, reporting, write status to excel============================================   
    @AfterMethod
    public void reporting(ITestResult result) throws IOException
    {
    	String methodname= Arrays.toString(result.getParameters());
    	String[] split = methodname.split(",");
    	String screenshotname = split[0];
    	if(ITestResult.SUCCESS==result.getStatus() ||  ITestResult.FAILURE==result.getStatus() || ITestResult.SKIP==result.getStatus())
		{
			App_logs.info("\n Test step is -> " +result.getName()+ "-" + Arrays.toString(result.getParameters()));
		}
    	
 //===================================Take logs and report in extent report when TC passed=====================================================
    	if(ITestResult.SUCCESS==result.getStatus())
		{
    		App_logs.info("PASS");
    		test.pass(MarkupHelper.createLabel(screenshotname +"Test case PASSED ", ExtentColor.GREEN));
 
		}
  
//===================================Take logs, take screenshot and report in extent report when TC failed=====================================================
    	else if (ITestResult.FAILURE==result.getStatus())	
		{
    		Throwable cause = result.getThrowable();
    		if (null != cause) {
    			App_logs.error(" *****FAIL - " +cause.getMessage());}
    		
    		CaptureScreenshot.getscreenshots(SelectBrowser.driver, screenshotname, Constants.failfilepath);
    		test.fail(MarkupHelper.createLabel(screenshotname +"Test case FAILED", ExtentColor.RED));
    		String screenshotpath = CaptureScreenshot.capture(SelectBrowser.driver, screenshotname);
    		test.addScreenCaptureFromPath(screenshotpath);
		}
    	
//===================================Take logs and report in extent report when TC skipped=====================================================    	
    	else if (ITestResult.SKIP==result.getStatus())	
		{
    		App_logs.info("Skipped");
    		test.skip(MarkupHelper.createLabel(screenshotname +"Test case FAILED", ExtentColor.ORANGE));
		}
    }
    
//=========================================Setting up extent report before suite===============================================
    @BeforeSuite
    public void reportsetting() throws UnknownHostException
    {
    	String username=System.getProperty("user.name");
    	String OS=System.getProperty("os.name");
    	String Hostname=InetAddress.getLocalHost().getHostName();
    	htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/MyOwnReport.html");
    	extent=new ExtentReports();
    	extent.attachReporter(htmlReporter);
    	htmlReporter.loadConfig((System.getProperty("user.dir") +"/extent-config.xml"));
    	extent.setSystemInfo("OS", OS);
    	extent.setSystemInfo("Host name", Hostname);
    	extent.setSystemInfo("Environment", "QA");
    	extent.setSystemInfo("User name", username);
    	htmlReporter.config().setChartVisibilityOnOpen(true);
    	htmlReporter.config().setDocumentTitle("Research Stream automation Report");
    	htmlReporter.config().setReportName("Research Stream implementation Automation Report");
    	htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
    	htmlReporter.config().setTheme(Theme.STANDARD);
    	   	
    }
  
//=========================================cleaning up extent after suite======================================================	
    @AfterSuite
    public void cleanreport()
    {
    	extent.flush();
    	//sendPDFReportByGMail("***@gmail.com", "", "chaman.preet@evalueserve.com", "PDF Report", "");
    }
    
//==============================================method to send mail using SMTP with attached pdf=================================
    private static void sendPDFReportByGMail(String from, String pass, String to, String subject, String body) {

    	Properties props = System.getProperties();
    	
    	 System.setProperty("https.proxyHost", "cpinternet");
    	    System.setProperty("https.proxyPort", "8080");
    	    
    	String host = "smtp.gmail.com";
    	/*props.setProperty("proxySet","true");
    	props.setProperty("httpProxyHost","cpinternet");
    	props.setProperty("httpProxyPort","8080");*/

    	props.put("mail.smtp.starttls.enable", "true");

    	props.put("mail.smtp.host", host);

    	props.put("mail.smtp.user", from);

    	props.put("mail.smtp.password", pass);

    	props.put("mail.smtp.port", "587");

    	props.put("mail.smtp.auth", "true");

    	Session session = Session.getDefaultInstance(props);

    	MimeMessage message = new MimeMessage(session);
    	
    	try {

    	    //Set from address

    	message.setFrom(new InternetAddress(from));

    	message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

    	//Set subject

    	message.setSubject(subject);

    	message.setText(body);

    	BodyPart objMessageBodyPart = new MimeBodyPart();

    	objMessageBodyPart.setText("Please Find The Attached Report File!");

    	Multipart multipart = new MimeMultipart();

    	multipart.addBodyPart(objMessageBodyPart);

    	objMessageBodyPart = new MimeBodyPart();

    	//Set path to the pdf report file

    	String filename = System.getProperty("user.dir")+"\\default test.pdf";

    	//Create data source to attach the file in mail

    	DataSource source = new FileDataSource(filename);

    	objMessageBodyPart.setDataHandler(new DataHandler(source));

    	objMessageBodyPart.setFileName(filename);

    	multipart.addBodyPart(objMessageBodyPart);

    	message.setContent(multipart);

    	Transport transport = session.getTransport("smtp");

    	transport.connect(host, from, pass);

    	transport.sendMessage(message, message.getAllRecipients());

    	transport.close();

    	}

    	catch (AddressException ae) {

    	ae.printStackTrace();

    	}

    	catch (MessagingException me) {

    	me.printStackTrace();

    	}

    	}

    	}
 
