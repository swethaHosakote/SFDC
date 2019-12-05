package SalesForce_Automation;

//import SalesForce_Automation.Reusable_Utility_Functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;


public class SFDCTest 
{
		
	public static WebDriver driver;
	
	public static ExtentReports reports;
	public static ExtentTest logger;
	
	@BeforeTest
	public static void reporting()
	{
		//String fileName = new SimpleDateFormat("'SampleTestExtentDemo_'yyyyMMddHHmm'.html'").format(new Date());
		String fileName = new SimpleDateFormat("'SampleTestExtentDemo_'yyyyMMddHHmm'.html'").format(new Date());
		String reportpath="C:\\Swetha\\SalesForce_Testcases\\Report\\"+fileName;
	
		reports = new ExtentReports(reportpath);
		
	}

	public static String[][] getDataInput(String filepath,String filename, String Sheetname) throws IOException
	{

	//Get the Xl path
	File xlfile = new File(filepath+"\\"+filename);

	//access to the Xl path
	FileInputStream xlaccess = new FileInputStream(xlfile);

	//access to workbook
	HSSFWorkbook Wb = new HSSFWorkbook(xlaccess);

	//Access the sheet
	HSSFSheet sheet = Wb.getSheet(Sheetname);

	int rowCount = sheet.getLastRowNum();
	int columnCount = sheet.getRow(0).getLastCellNum();

	//System.out.println(rowCount);
	//System.out.println(columnCount);

	String [][] readData = new String [rowCount+1][columnCount];
	for(int i=0;i<=rowCount;i++)
	{
		for(int j=0;j <sheet.getRow(i).getLastCellNum();j++)
		{
			readData[i][j] = sheet.getRow(i).getCell(j).getStringCellValue();
		}
		//System.out.println();
	}
	return readData;

	}


	
	@BeforeMethod()
	public static void setForChromeDriverLaunchBrowser() throws InterruptedException
	{
		
		//set up chrome drive
		System.setProperty("webdriver.chrome.driver", "C:\\Swetha\\Selenium Jars\\chromedriver_win32\\chromedriver.exe");		
		driver = new ChromeDriver();
		//maximize the window
		driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		//Launch website
		driver.get("http://login.salesforce.com");
		Thread.sleep(5000);
		
	}
	
	@AfterTest
	public static void Report_close()
	{
		reports.endTest(logger);
		reports.flush();
	}
	
	@AfterMethod
	public static void TearDown() throws InterruptedException
	{		
		Thread.sleep(3000);
		driver.quit();
		
	}
	
	@Test
	public static void TC01_Login_Error_Message_1() throws Exception

	{	
		
		logger = reports.startTest("TC01_Login_Error_Message");
		
		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC01_Login_Error_Message_1.xls","TC01_Login_Error_Message_1");
		
		//Username textbox WebElement
		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
		String username_data = data[1][2];
		//String username_data = 
		System.out.println(username_data);
		enter_data_textbox(username,username_data,"User Name");
				
		//password textbox WebElement
		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
		password.clear();
		logger.log(LogStatus.PASS,"The password field was cleared");
				
		//Log In button WebElement
		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
		button_click(LoginButton,"Log In");
		//logger.log(LogStatus.PASS,"The LogIn button was clicked");
		
		WebElement errormessage = driver.findElement(By.xpath("//div[@id='error']"));		
		boolean b = validate_String(errormessage,"Please enter your password.");
		Assert.assertTrue(b);
	
	}

	@Test
	public static void TC02_Login_To_SalesForce() throws Exception
	{
		logger = reports.startTest("TC02_Login_To_SalesForce");
		
		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC02_Login_To_SalesForce.xls","TC02_Login_To_SalesForce");
		
		//Username textbox WebElement
		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
		
		String username_input = data[1][2];
		enter_data_textbox(username,username_input,"User Name");
		
		//password textbox WebElement
		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
		String password_input = data[1][3];
		enter_data_textbox(password,password_input,"Password");
				
		//Log In button WebElement
		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));		
		//Calling method to click a button
		button_click(LoginButton,"Log In");
					
		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
		//Calling method to click a button
		boolean b = validateDisplayedElement(usermenu,"User Menu");
		Assert.assertTrue(b);
	}
	
	@Test
	public static void TC03_Check_RemeberMe() throws Exception
	{
		logger = reports.startTest("TC03_Check_RemeberMe");
		
		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC03_Check_RemeberMe.xls","TC03_Check_RemeberMe");
		
		//Username textbox WebElement
		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
		String username_input = data[1][2];
		enter_data_textbox(username,username_input,"User Name");
				
		//password textbox WebElement
		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
		String password_input = data[1][3];
		enter_data_textbox(password,password_input,"password");
				
		//WebElement for Remember me checkBox
		WebElement rememberMeCheckBox = driver.findElement(By.xpath("//input[@id='rememberUn']"));
		checkBox(rememberMeCheckBox,"Remember Me");
				
		//Log In button WebElement
		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
		//Calling method to click a button
		button_click(LoginButton,"Log In");
				
		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
		//Calling method to click a button
		button_click(usermenu,"User Menu");
				
		WebElement logOut = driver.findElement(By.xpath("//a[@title = 'Logout']"));
		//Calling method to click a button
		button_click(logOut,"Log out");
				
		//Validating the username
		WebElement username_rememberMe = driver.findElement(By.xpath("//span[@id='idcard-identity']"));
		boolean b = validate_String(username_rememberMe,username_input);
		Assert.assertTrue(b);

	}

	@Test
	public static void TC04A_Forgot_Password() throws Exception
	{
		logger = reports.startTest("TC04A_Forgot_Password");
	
		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC04A_Forgot_Password.xls","TC04A_Forgot_Password");
		
		//forgot password WebElement
		WebElement forgotPasswordLink = driver.findElement(By.xpath("//a[@id='forgot_password_link']"));
		button_click(forgotPasswordLink,"Forgot Password Link");
				
		//Forgot password username
		WebElement forgotPassword_UserName = driver.findElement(By.xpath("//input[@name = 'un']"));
		String username_input = data[1][2];
		enter_data_textbox(forgotPassword_UserName,username_input,"Forgot password username");
				
		//forgot password WebElement
		WebElement forgotPassword_ContinueButton = driver.findElement(By.xpath("//input[@name = 'continue']"));
		button_click(forgotPassword_ContinueButton,"Forgot Password Continue Button");
				
		WebElement forgotPassword_message1 = driver.findElement(By.xpath("//p[contains(text(), 'We�ve sent you an email ')]"));
		String forgotPassword_input1 = data[1][3];
		boolean msg1 = validate_String(forgotPassword_message1,forgotPassword_input1);
		Assert.assertTrue(msg1);


		WebElement forgotPassword_message2= driver.findElement(By.xpath("//p[contains(text(), 'Can�t find the email?')]"));
		String forgotPassword_input2 = data[1][4];
		boolean msg2 = validate_String(forgotPassword_message2,forgotPassword_input2);
		Assert.assertTrue(msg2);
		
		WebElement forgotPassword_message3 = driver.findElement(By.xpath("//p[contains(text(), 'If you still can�t log')]"));
		String forgotPassword_input3 = data[1][5];
		boolean msg3 = validate_String(forgotPassword_message3,forgotPassword_input3);
		Assert.assertTrue(msg3);
	}
	
	@Test
	public static void TC04B_Forgot_Password() throws Exception
	{
		logger = reports.startTest("TC04B_Forgot_Password");
		
		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC04B_Forgot_Password.xls","TC04B_Forgot_Password");
	
		//Username textbox WebElement
		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
		String username_input = data[1][2];
		enter_data_textbox(username,username_input,"User Name");
		
		//password textbox WebElement
		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
		String password_input = data[1][3];
		enter_data_textbox(password,password_input,"Password");
			
		//Log In button WebElement
		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
		//Calling method to click a button
		button_click(LoginButton,"Log In");
			
		WebElement error_message = driver.findElement(By.xpath("//div[@id='error']"));
		String message  = data[1][4];
		boolean b = validate_String(error_message,message);
		Assert.assertTrue(b);
	}

//	//@Test
//	public static void TC05_Select_UserMenu() throws Exception
//	{
//		logger = reports.startTest("TC05_Select_UserMenu");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC05_Select_UserMenu.xls","TC05_Select_UserMenu");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//			
//
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//				
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
//		//Calling method to click a button
//		button_click(LoginButton,"Log In");
//					
//	
//		//UserMenu button WebElement 
//		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
//		//Calling method to click a button
//		button_click(usermenu,"User Menu");
//				
//		//UserMenu option
//		WebElement userMenu_option = driver.findElement(By.xpath("//div[@id = 'userNav-menuItems']"));
//		List <WebElement> options = userMenu_option.findElements(By.tagName("a"));	
//		String expected =  data[1][4]; 
//		String splitexpected[] = expected.split(",");
//		for(int i=0;i<splitexpected.length;i++)
//		{	
//			boolean flag = false;
//			for(int j=0;j<options.size();j++)
//			{
//				if (splitexpected[i].equals(options.get(i).getText()))
//				{
//					flag = true;
//					System.out.println("'"+splitexpected[i]+"' is available in the dropdown");
//					logger.log(LogStatus.PASS,"'"+splitexpected[i]+"' is available in the dropdown");
//					break;
//				}				
//			}
//			if(flag!=true)
//			{
//				System.out.println("'"+splitexpected[i]+"' is not available in the dropdown");
//				logger.log(LogStatus.PASS,"'"+splitexpected[i]+"' is not available in the dropdown");
//			}
//		}	
//		
//	}
//	
//	//@Test
//	public static void TC06_Select_My_Profile_option_from_user_menu() throws Exception
//	{
//		logger = reports.startTest("TC06_Select_My_Profile_option_from_user_menu");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC06_Select_My_Profile_option_from_user_menu.xls","TC06_Select_My_Profile_option");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input =  data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//				
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input =  data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//					
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
//		//Calling method to click a button
//		button_click(LoginButton,"Log In");
//					
//		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
//		button_click(usermenu,"User Menu");
//		
//		WebElement myProfile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
//		button_click(myProfile,"My Profile");
//				
//		Thread.sleep(3000);
//		
//		WebElement editProfile = driver.findElement(By.cssSelector(".contactInfoLaunch > img"));
//		button_click(editProfile,"Edit Profile");
//		
//		
//		WebElement profileFrame = driver.findElement(By.xpath("//iframe[@id='contactInfoContentId']"));
//		driver.switchTo().frame(profileFrame);
//				
//		WebElement aboutTab = driver.findElement(By.xpath("//li[@id='aboutTab']"));
//		button_click(aboutTab,"About Tab");
//				
//		WebElement firstNameTextbox = driver.findElement(By.xpath("//input[@id = 'firstName']"));
//		firstNameTextbox.getAttribute("value");
//		
//		WebElement lastNameTextbox = driver.findElement(By.xpath("//input[@id = 'lastName']"));
//		Date date = new Date();		
//		String toChange = "Last"+ currentdatetime();
//		//date.getDate() + date.getTime();
//		
//		lastNameTextbox.clear();
//		enter_data_textbox(lastNameTextbox,toChange,"Last Name");
//				
//		WebElement saveAll = driver.findElement(By.xpath("//input[@class='zen-btn zen-primaryBtn zen-pas']"));
//		button_click(saveAll,"Save All");
//				
//		String afterChange = firstNameTextbox.getAttribute("value") + " " + toChange + " "; 
//		driver.switchTo().defaultContent();
//		
//		Thread.sleep(3000);
//		
//		WebElement displayed_profile = driver.findElement(By.xpath("//span[@id='tailBreadcrumbNode']"));
//		boolean b = validate_String(displayed_profile,afterChange);
//		Assert.assertTrue(b);
////		if(displayed_profile.getText().equals(afterChange))
////			logger.log(LogStatus.PASS,"Name is validated after changing");
////		else
////			logger.log(LogStatus.FAIL,"Name is not updated after changing");
////		
//		WebElement post = driver.findElement(By.id("publisherAttachTextPost"));
//		button_click(post,"Post");
//			
//		WebElement inputframe = driver.findElement(By.xpath("//iframe[@class='cke_wysiwyg_frame cke_reset']"));
//		driver.switchTo().frame(inputframe);
//		
//		//WebElement input = driver.findElement(By.xpath("//html[1]/body[1]"));
//		WebElement input=driver.findElement(By.xpath("//html[1]/body[1]"));
//		//button_click(input,"Enter post");
//		//enter_data_textbox(input,toChange,"Post input");
//		input.click();
//		input.sendKeys(toChange);
//		//SalesForce_Automation.Reusable_Utility_Functions.enter_data_textbox(input,toChange,"Text area");
//		 	
//		driver.switchTo().defaultContent();
//		Thread.sleep(3000);
//		WebElement sharebutton = driver.findElement(By.id("publishersharebutton"));
//		button_click(sharebutton,"Share");
//		
//		//WebElement validateshare = driver.findElement(By.xpath("//div[@class='feedcontainer cxfeedcontainer zen actionsOnHoverEnabled']"));
//		//validate_String(validateshare,toChange);
//
//		//file uploading
//		WebElement file = driver.findElement(By.xpath("//span[contains(@class,'publisherattachtext')][contains(text(),'File')]"));
//		button_click(file,"File");
//				
//		WebElement fromComp = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));
//		button_click(fromComp,"From Computer");
//		Thread.sleep(3000);
//				
//		WebElement choosefile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
//		choosefile.sendKeys("C:\\Swetha\\SalesForce_Testcases\\Upload.txt");
//		//enter_data_textbox(choosefile,"C:\\Swetha\\SalesForce_Testcases\\Upload.txt","choose file");
//		Thread.sleep(3000);
//		
//		WebElement shareButton = driver.findElement(By.id("publishersharebutton"));
//		button_click(shareButton,"Share Button");
//		Thread.sleep(3000);
//		
//		//photo uploading
//		WebElement photo = driver.findElement(By.id("displayBadge"));
//		Actions action = new Actions(driver);
//		action.moveToElement(photo).build().perform();
//		WebElement addPhotoLink = driver.findElement(By.id("uploadLink"));
//		button_click(addPhotoLink,"add photo Link");
//		WebElement photoframe = driver.findElement(By.id("uploadPhotoContentId"));
//		driver.switchTo().frame(photoframe);
//		
//		WebElement chooseFile = driver.findElement(By.id("j_id0:uploadFileForm:uploadInputFile"));
//		//SalesForce_Automation.Reusable_Utility_Functions.button_click(chooseFile,"choose file button");
//		//logger.log(LogStatus.PASS, "Choose file button is clicked");
//		chooseFile.sendKeys("C:\\Swetha\\SalesForce_Testcases\\upload.png");
//		//enter_data_textbox(chooseFile,"C:\\Swetha\\SalesForce_Testcases\\upload.png","choose file");
//		WebElement saveButton = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadBtn']"));
//		button_click(saveButton,"save button");
//			
//		WebElement savephoto = driver.findElement(By.xpath("//input[@id='j_id0:j_id7:save']"));
//		button_click(savephoto,"Save Photo button");
//	}
//	
//	//@Test
//	public static void TC07_Select_My_settings_option_from_user_menu() throws Exception
//	{
//		logger = reports.startTest("TC07_Select_My_settings_option_from_user_menu");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC07_Select_My_settings_option_from_user_menu.xls","TC07_Select_My_settings_option");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//				
//
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//							
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@name='Login']"));
//		button_click(LoginButton,"Log In");
//	
//		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
//		button_click(usermenu,"User Menu");
//			
//		WebElement mySetting = driver.findElement(By.xpath("//a[contains(text(),'My Setting')]"));
//		button_click(mySetting,"My Setting");
//				
//		WebElement Personal = driver.findElement(By.xpath("//div[@id='PersonalInfo']//a[@class='header setupFolder']")); 
//		button_click(Personal,"Personal");
//				
//		WebElement loginHistory = driver.findElement(By.xpath("//a[@id='LoginHistory_font']"));
//		button_click(loginHistory,"Login History");
//				
//		WebElement loginHistoryPage = driver.findElement(By.xpath("//h3[@id = 'RelatedUserLoginHistoryList_title']"));
//		boolean b = validate_String(loginHistoryPage,"Login History");
//		Assert.assertTrue(b);
////		if(loginHistoryPage.getText().equals("Login History"))
////			logger.log(LogStatus.PASS, "Login History page is validated");
////		else
////			logger.log(LogStatus.FAIL, "Login History page is not validated");
////		
//		WebElement displayAndLayout = driver.findElement(By.xpath("//div[@id='DisplayAndLayout']"));
//		button_click(displayAndLayout,"Display & Layout");
//		
//		
//		WebElement customizeMyTabs = driver.findElement(By.xpath("//span[text() = 'Customize My Tabs']"));
//		button_click(customizeMyTabs,"Customize My Tabs");
//		
//		 //SelectDropDown(WebElement Element, String optiontype, String option,String dropdown_name)
//		String customApp_data = data[1][4];
//		WebElement customApp = driver.findElement(By.xpath("//select[@id='p4']"));
//		//SelectDropDown(customApp,"3",customApp_data,"Custom App dropdown");		
//		Select customAppdropdown = new Select(customApp);	
//		customAppdropdown.selectByVisibleText("Salesforce Chatter");
//		Thread.sleep(3000);
//			
//		String availableData = data[1][5];
//		WebElement selectedTab = driver.findElement(By.id("duel_select_1"));
//		if (selectedTab.getText().contains(availableData) == true)
//		{
//			SelectDropDown(selectedTab,"3",availableData,"Selected Data");
////			Select selectedTabdropdown = new Select(selectedTab);
////			selectedTabdropdown.selectByVisibleText(availableData);
//			WebElement leftArrow = driver.findElement(By.xpath("//img[@class='leftArrowIcon']"));
//			button_click(leftArrow,"Left Arrow");
//		}
//		
//		WebElement availableTab = driver.findElement(By.id("duel_select_0"));
//		SelectDropDown(availableTab,"3",availableData,"Available Data");
////		Select availableTabdropdown = new Select(availableTab);
////		availableTabdropdown.selectByVisibleText(availableData);
//		WebElement rightArrow = driver.findElement(By.xpath("//img[@class='rightArrowIcon']"));
//		button_click(rightArrow,"Right Arrow");
//		
//		if (selectedTab.getText().contains(availableData) == true)	
//			//System.out.println("Selected tab contains Report")
//			logger.log(LogStatus.PASS,"Selected tab is validated with Report");
//		else
//			//System.out.println("Selected tab does not contains Report");
//			logger.log(LogStatus.FAIL,"Selected tab is validated with Report");
//				
//		WebElement EmailSetup = driver.findElement(By.xpath("//div[@id='EmailSetup']"));
//		button_click(EmailSetup,"Email Setup");
//				
//		WebElement EmailSettings = driver.findElement(By.xpath("//span[@id='EmailSettings_font']"));
//		button_click(EmailSettings,"Email Settings"); 
//		
//		
//		WebElement sender_name = driver.findElement(By.xpath("//input[@id='sender_name']"));
//		sender_name.clear();
//		Date date = new Date();
//		String senderName_input = "Sender"+ date.getDate()+date.getTime();
//		enter_data_textbox(sender_name,senderName_input,"Sender Name");
//				
//		WebElement sender_mail = driver.findElement(By.xpath("//input[@id='sender_email']"));
//		sender_mail.clear();
//		String sendermail_input = "Sender"+  date.getDate()+date.getTime() + "@gmail.com";
//		enter_data_textbox(sender_mail,sendermail_input,"Sender mail");
//				
//		WebElement automatically = driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
//		automatically.click();
//		
//		WebElement emailsavebutton = driver.findElement(By.xpath("//input[@name='save']"));
//		button_click(emailsavebutton,"Save");
//		//Thread.sleep(3000);
//		
//		Alert alert = driver.switchTo().alert();
//		alert.accept();
//		Thread.sleep(3000);
//
//		WebElement sender_name1 = driver.findElement(By.xpath("//input[@id='sender_name']"));
//		boolean ba = validate_String(sender_name1,senderName_input);
//		Assert.assertTrue(ba);
////		if(sender_name1.getAttribute("value").equals(senderName))
////			logger.log(LogStatus.PASS, "email sender name was updated successfully");
////		else
////			logger.log(LogStatus.FAIL, "email sender name was not updated successfully");
////		
//		WebElement sender_mail1 = driver.findElement(By.xpath("//input[@id='sender_email']"));
//		boolean bc = validate_String(sender_mail1,sendermail_input);
//		Assert.assertTrue(bc);
////		if(sender_mail1.getAttribute("value").equals("abc.abc@gmail.com"))
////			logger.log(LogStatus.PASS, "Sender mail id was updated successfully");
////		else
////			logger.log(LogStatus.FAIL, "Sender mail id was not updated successfully");
////		
//		WebElement CalendarAndReminders = driver.findElement(By.xpath("//div[@id='CalendarAndReminders']"));
//		button_click(CalendarAndReminders,"Calendar And Reminders");
//				
//		WebElement activityReminder = driver.findElement(By.xpath("//a[@id='Reminders_font']"));
//		button_click(activityReminder,"Activity Reminder");
//				
//		WebElement testReminder = driver.findElement(By.xpath("//input[@id='testbtn']"));
//		button_click(testReminder,"Open a Test Reminder");
//				
//		String primaryHandler = driver.getWindowHandle();
//		System.out.println(primaryHandler);
//		for(String handle:driver.getWindowHandles())
//		{
//			System.out.println("No. of handles:"+handle);
//			driver.switchTo().window(handle);
//		}
//		
//		//driver.switchTo().alert();
//		WebElement reminderPopup = driver.findElement(By.xpath("//div[@id='summary']"));
//		if(reminderPopup.isDisplayed() == true)
//			logger.log(LogStatus.PASS, "Test Reminder pop up is displayed successfully");
//		else
//			logger.log(LogStatus.FAIL, "Test Reminder pop up is not displayed successfully");
//	}
//		
//	//@Test
//	public static void TC09_Select_Logout_option_from_user_menu() throws Exception
//	{
//		
//		logger = reports.startTest("TC09_Select_Logout_option_from_user_menu");
//
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC09_Select_Logout_option_from_user_menu.xls","TC09_Select_Logout_option");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//								
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
//		button_click(usermenu,"User Menu");
//				
//		WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
//		button_click(logout,"Logout");
//		Thread.sleep(3000);
//		
//		WebElement Login = driver.findElement(By.xpath("//input[@name='Login']"));
//		//validateDisplayedElement(Login,"Login);
//		if(Login.isDisplayed() == true)
//			logger.log(LogStatus.PASS, "The required login page is displayed");
//		else
//			logger.log(LogStatus.FAIL, "The required login page is not displayed");
//		
//	}
//	
//	//@Test
//	public static void TC10_CreateAccount() throws Exception
//	{
//		logger = reports.startTest("TC10_CreateAccount");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC10_CreateAccount.xls","TC10_CreateAccount");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement accounts = driver.findElement(By.xpath("//a[@class='listRelatedObject accountBlock title']"));
//		button_click(accounts,"Accounts");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//				
//		WebElement newAccountButton = driver.findElement(By.xpath("//input[@name='new']"));
//		button_click(newAccountButton,"New Accounts");
//				
//		Date date = new Date();
//		String accountName_input = "Account" + date.getDate()+ date.getTime(); 
//		WebElement accountName = driver.findElement(By.xpath("//input[@id='acc2']"));
//		enter_data_textbox(accountName,accountName_input,"Account Name");
//		
//		WebElement saveButton = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@name='save']"));
//		button_click(saveButton,"Save Button");
//		
//		
//		WebElement newAccount = driver.findElement(By.xpath("//h2[@class='topName']"));
//		boolean b = validate_String(newAccount, accountName_input);
//		Assert.assertTrue(b);
////		if(newAccount.getText() .equals(accountName_input))
////			logger.log(LogStatus.PASS, "New account page is displayed with account details.");
////		else
////			logger.log(LogStatus.FAIL, "New account page is not displayed with account details.");
//	}
//	
//	//@Test
//	public static void TC11_Create_new_view() throws Exception
//	{
//		logger = reports.startTest("TC11_Create_new_view");
//
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC11_Create_new_view.xls","TC11_Create_new_view");
//		
//		//Username textbox WebElement
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement accounts = driver.findElement(By.xpath("//a[@class='listRelatedObject accountBlock title']"));
//		button_click(accounts,"Accounts");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		WebElement createNewViewLink = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
//		button_click(createNewViewLink,"Create New View Link");
//				
//		Date date = new Date();
//		String viewName_input = "ViewName" + date.getDate()+ date.getTime();
//		WebElement viewName = driver.findElement(By.xpath("//input[@id='fname']"));
//		enter_data_textbox(viewName,viewName_input,"View Name");
//				
//		WebElement viewUniqueName = driver.findElement(By.xpath("//input[@id='devname']"));
//		button_click(viewUniqueName,"view unique name");
//		viewUniqueName.clear();
//		enter_data_textbox(viewUniqueName,viewName_input,"view Unique Name");
//				
//		WebElement SaveButton = driver.findElement(By.name("save"));
//		button_click(SaveButton,"Save Button");
//				
//		WebElement newViewDropDown = driver.findElement(By.name("fcf"));		
//		if (newViewDropDown.getText().contains(viewName_input))
//			logger.log(LogStatus.PASS, "Newely added View is displayed in the account view list");
//		else
//			logger.log(LogStatus.FAIL, "Newely added View is not displayed in the account view list");
//	}
//
//	//@Test
//	public static void TC12_Editview () throws Exception
//	{
//		logger = reports.startTest("TC12_Editview");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC12_Editview.xls","TC12_Editview");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement accounts = driver.findElement(By.xpath("//a[@class='listRelatedObject accountBlock title']"));
//		button_click(accounts,"Accounts");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//				
//		WebElement newViewDropDown = driver.findElement(By.name("fcf"));
//		String view_inputdata = data[1][4];
//		SelectDropDown(newViewDropDown, "3", view_inputdata, "View Name");
//		
//		WebElement editButton = driver.findElement(By.xpath("//*[@id=\"filter_element\"]/div/span/span[2]/a[1]"));
//		button_click(editButton,"Edit Button");
//				
//		WebElement viewName = driver.findElement(By.id("fname"));
//		viewName.clear();
//		Date date = new Date();
//		String viewName_input = "ViewName" + date.getDate()+ date.getTime();
//		enter_data_textbox(viewName,viewName_input,"View Name");
//				
//		WebElement filterfield = driver.findElement(By.id("fcol1"));
//		String Filter_inputdata = data[1][5];
//		SelectDropDown(filterfield, "3", Filter_inputdata, "Filter Field");
//				
//		WebElement operator = driver.findElement(By.id("fop1"));
//		String operator_inputdata = data[1][6];
//		SelectDropDown(operator, "3", operator_inputdata, "Operator Field");
//			
//		WebElement value = driver.findElement(By.id("fval1"));
//		value.clear();
//		enter_data_textbox(value,viewName_input,"value field");
//				
//		WebElement fieldsToDisplaySelected = driver.findElement(By.id("colselector_select_1"));
//		String FieldDisplay_inputdata = data[1][7];
//		if (fieldsToDisplaySelected.getText().contains(FieldDisplay_inputdata) == true)
//		{
//			SelectDropDown(fieldsToDisplaySelected, "3", FieldDisplay_inputdata, "Selected display field");
//			//Select fieldsToDisplaySelectedDropwDown = new Select(fieldsToDisplaySelected);
//			//fieldsToDisplaySelectedDropwDown.selectByVisibleText("Last activity");
//			WebElement leftArrow = driver.findElement(By.id("colselector_select_0_left"));
//			button_click(leftArrow,"Left Arrow");
//		}
//		WebElement fieldsToDisplayAvaliable = driver.findElement(By.id("colselector_select_0"));
//		SelectDropDown(fieldsToDisplayAvaliable, "3", FieldDisplay_inputdata, "Avaliable display field");
//		//Select availableTabdropdown = new Select(availableTab);
//		//availableTabdropdown.selectByVisibleText("Reports");		
//		WebElement rightArrow = driver.findElement(By.id("colselector_select_0_right"));
//		button_click(rightArrow,"Right Arrow");
//				
//		WebElement SaveButton = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//td[@class='pbButtonb']//input[@value = ' Save ']"));
//		button_click(SaveButton,"Save Button");
//		Thread.sleep(3000);
//	
//		WebElement newViewDropDown1 = driver.findElement(By.name("fcf"));
//		if (newViewDropDown1.getText().contains(viewName_input))
//			logger.log(LogStatus.PASS, "Edited View is displayed in the account view list");
//		else
//			logger.log(LogStatus.FAIL, "Edited View is not displayed in the account view list");
//		
////		Thread.sleep(5000);
////		//WebElement table = driver.findElement(By.xpath("//div[@class='x-grid3-header-offset']//table"));
////		List<WebElement> col = driver.findElements(By.xpath("//div[@class='x-grid3-header-offset']//table//thead"));
////		System.out.println(col.size());
////		for(int i =0;i<col.size();i++)
////		{
////			System.out.println(col.get(i).getText());
////		}
////				//xpath("//div[@class='x-grid3-header-offset']//table"));
////		//System.out.println(table.getText());
//
//	}
//	
//	//@Test
//	public static void TC13_MergeAccounts () throws Exception
//	{
//		logger = reports.startTest("TC13_MergeAccounts");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC13_MergeAccounts.xls","TC13_MergeAccounts");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//				
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement accounts = driver.findElement(By.xpath("//a[@class='listRelatedObject accountBlock title']"));
//		button_click(accounts,"Accounts");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//				
//		WebElement mergeAccount = driver.findElement(By.xpath("//a[contains(text(),'Merge Accounts')]"));
//		button_click(mergeAccount,"Merge Account");
//		
//		WebElement findAccounts = driver.findElement(By.id("srch"));
//		String findaccount_input = data[1][4];
//		enter_data_textbox(findAccounts,findaccount_input,"Password");
//				
//		WebElement findAccountButton = driver.findElement(By.name("srchbutton"));
//		button_click(findAccountButton,"Find Account Button");
//				
//		WebElement table = driver.findElement(By.xpath("//table[@class='list']"));
//		List<WebElement> rows = table.findElements(By.tagName("tr"));
//		System.out.println("row count is: " + rows.size());
//
//		if(rows.size() <3)
//		{
//			System.out.println("The results of account should be atleast 2 or more accounts link");
//			logger.log(LogStatus.FAIL, "The results of account should be atleast 2 or more accounts link");
//		}
//		else
//		{
//			if(rows.size() ==3)
//			{
//				//System.out.println("entered equal 3");
//				for(int i=1;i<=2;i++)
//				{
//					WebElement rowcheckbox = driver.findElement(By.id("cid"+(i-1)));
//					if(rowcheckbox.isSelected() == true)
//					{
//						System.out.println(i+" row is checked");
//						logger.log(LogStatus.INFO,i+" row is checked");
//					}
//				}
//			}
//		
//			else if(rows.size() > 3)
//			{
//				System.out.println("entered greater than 3");
//				for(int i=3;i<rows.size();i++)
//				{
//				
//					WebElement rowcheckbox = driver.findElement(By.id("cid"+(i-1)));
//					if(rowcheckbox.isSelected() == true)
//					{
//						rowcheckbox.click();
//						System.out.println("Uncheck the other account");
//						logger.log(LogStatus.PASS,"Uncheck the others account, need only 2");
//					}
//				}
//			}
//			
//			WebElement nextButton = driver.findElement(By.name("goNext"));
//			button_click(nextButton,"Next Button");
//						
//			WebElement step_2_Page = driver.findElement(By.xpath("//h2[contains(text(),'Step 2. Select the values to retain')]"));
//			if( step_2_Page.isDisplayed() == true)
//			{
//				System.out.println("Second step page is displayed");
//				logger.log(LogStatus.PASS,"Second step page is displayed");
//				
//				//WebElement accountNameRadio = driver.findElement(By.xpath("//input[@id='m40013k00002dHgoZ']"));
//				WebElement mergeButton = driver.findElement(By.name("save"));
//				button_click(mergeButton,"Merge Button");
//				logger.log(LogStatus.PASS, "Merge Button was clicked");
//				
//				Alert alert = driver.switchTo().alert();
//				alert.accept();
//				logger.log(LogStatus.PASS,"New pop up for account merge confirmation is displayed ");
//				
//			}
//			else
//			{
//				System.out.println("Second step page is not displayed");
//				logger.log(LogStatus.PASS,"Second step page is not displayed");
//			}
//		}	
//	}	
//	
//	//@Test
//	public static void TC14_Create_account_report () throws Exception
//	{
//		logger = reports.startTest("TC14_Create_account_report");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC14_Create_account_report.xls","TC14_Create_account_report");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement accounts = driver.findElement(By.xpath("//a[@class='listRelatedObject accountBlock title']"));
//		button_click(accounts,"Accounts");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//				
//		WebElement reportLink = driver.findElement(By.xpath("//a[contains(text(),'Accounts with last activity > 30 days')]"));
//		button_click(reportLink,"'Accounts with last activity > 30 days' Link");
//		
//		WebElement datefieldselect = driver.findElement(By.id("ext-gen148"));
//		button_click(datefieldselect,"Date Field select button");
//				
//		WebElement dateField = driver.findElement(By.id("ext-gen20"));
//		dateField.sendKeys(Keys.ARROW_DOWN);
//		dateField.sendKeys(Keys.ENTER);
//		
////		Select dateFielddropdown = new Select(dateField);
////		dateFielddropdown.selectByVisibleText("Created Date");
//		//SalesForce_Automation.Reusable_Utility_Functions.SelectDropDown(dateField, "3","Created Date", "Date Field");
//		logger.log(LogStatus.PASS, "Created Date is selected in Date Field");
//		
//		WebElement fromDate = driver.findElement(By.id("ext-comp-1042"));		
//		Date currentDate = new Date();  
//       // System.out.println("Current Date: "+currentDate);  
//        String dateToStr = DateFormat.getInstance().format(currentDate); 
//        String date[]= dateToStr.split(" ");
//        //System.out.println(date[0]);
//        enter_data_textbox(fromDate,date[0],"From Date");
//		
//		WebElement toDate = driver.findElement(By.id("ext-comp-1045"));
//		toDate.clear();
//		enter_data_textbox(toDate,date[0],"To Date");
//				
//		WebElement runReport = driver.findElement(By.xpath("//button[text() = 'Run Report']"));
//		button_click(runReport,"Run Report Button");
//				
//		WebElement saveAsButton = driver.findElement(By.name("memorizenew"));
//		button_click(saveAsButton,"Save as Button");
//						
//		String reportName_input = "ReportName"+ currentDate.getDate() + currentDate.getTime();
//		WebElement reportName = driver.findElement(By.id("report_name"));
//		enter_data_textbox(reportName,reportName_input,"Report Name");
//		reportName.sendKeys(Keys.TAB);
//		
//		WebElement reportUniqueName = driver.findElement(By.id("devName"));
////		reportUniqueName.clear();
////		Thread.sleep(3000);
////		enter_data_textbox(reportUniqueName,reportName_input,"Report Unique Name");
//		reportUniqueName.getText().equals(reportName_input);
//				
//		WebElement saveButton = driver.findElement(By.xpath("//div[@class='pbBottomButtons']//input[1]"));
//		button_click(saveButton,"'Save and Run Report' Button");
//			
////		WebElement table = driver.findElement(By.xpath("//div[@class='x-grid3-header-offset']//table"));
////		List<WebElement> rows = table.findElements(By.tagName("tr"));
////		System.out.println(rows.size());
////		System.out.println(rows.get(index));
////		
//	}
//	
//	//@Test
//	public static void TC15_Select_usermenu () throws Exception
//	{
//		logger = reports.startTest("TC15_Select_usermenu");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC15_Select_usermenu.xls","TC15_Select_usermenu");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement opportunitiesLink  = driver.findElement(By.xpath("//a[@class='listRelatedObject opportunityBlock title']"));
//		button_click(opportunitiesLink,"Opportunities Link");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		WebElement viewSelect = driver.findElement(By.id("fcf"));
//		List<WebElement> option = viewSelect.findElements(By.tagName("option"));
//		String dropdownoption = data[1][4];
//		String optionsdropdown[] = dropdownoption.split(",");
//		for(int i=0;i<optionsdropdown.length;i++)
//		{	
//			boolean flag = false;
//			for(int j=0;j<option.size();j++)
//			{
//				if (optionsdropdown[i].equals(option.get(i).getText()))
//				{
//					flag = true;
//					System.out.println("'"+optionsdropdown[i]+"' is available in the dropdown");
//					logger.log(LogStatus.PASS,"'"+optionsdropdown[i]+"' is available in the dropdown");
//					break;
//				}				
//			}
//			if(flag!=true)
//			{
//				System.out.println("'"+optionsdropdown[i]+"' is not available in the dropdown");
//				logger.log(LogStatus.PASS,"'"+optionsdropdown[i]+"' is not available in the dropdown");
//			}
//		}
//	}
//	
//	
//	//@Test
//	public static void TC16_Create_a_new_Opty () throws Exception
//	{
//		logger = reports.startTest("TC16_Create_a_new_Opty");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC16_Create_a_new_Opty.xls","TC16_Create_a_new_Opty");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3]; 
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement opportunitiesLink  = driver.findElement(By.xpath("//a[@class='listRelatedObject opportunityBlock title']"));
//		button_click(opportunitiesLink,"Opportunities Link");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//					
//		WebElement newButton = driver.findElement(By.name("new"));
//		button_click(newButton,"New Button");
//				
//		WebElement OpportunitiesName = driver.findElement(By.id("opp3"));
//		Date currentDate = new Date();
//		String OpportunitiesName_input = "OpportunitiesName"+ currentDate.getDate() + currentDate.getTime();
//		enter_data_textbox(OpportunitiesName,OpportunitiesName_input,"Opportunities Name");
//				
//		WebElement accountName = driver.findElement(By.id("opp4"));
//		enter_data_textbox(accountName,"Test","Account Name");
//				
//		WebElement probability = driver.findElement(By.id("opp12"));
//		probability.clear();
//		enter_data_textbox(probability,"10","probability");
//				
//		WebElement leadSource = driver.findElement(By.id("opp6"));
////		Select leadSourcedropdown = new Select(leadSource);
////		leadSourcedropdown.selectByVisibleText("Web");
//		SelectDropDown(leadSource, "3", "Web", "lead Source");
//				
//		WebElement stage = driver.findElement(By.id("opp11"));
//		SelectDropDown(stage, "3", "Prospecting", "Stage");
//				
//		WebElement closeDate = driver.findElement(By.id("opp9"));
//		String dateToStr = DateFormat.getInstance().format(currentDate); 
//        String date[]= dateToStr.split(" ");    
//        String fulldate[] = date[0].split("/");
//        String Fulldate = fulldate[0] + "/" + fulldate[1] + "/" + "20"+fulldate[2];
//        System.out.println(Fulldate);
//		enter_data_textbox(closeDate,Fulldate,"Close Date");
//				
//		WebElement saveButton = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@name='save']"));
//		button_click(saveButton,"Save Button");
//			
//		WebElement OpportunitiesNameDisplay = driver.findElement(By.id("opp3_ileinner"));
//		if(OpportunitiesNameDisplay.getText().equals(OpportunitiesName_input))
//		{
//			System.out.println("Opportunities Name is display");
//			logger.log(LogStatus.PASS, "Opportunities Name is display");
//		}
//		else
//		{
//			System.out.println("Opportunities Name is not display");
//			logger.log(LogStatus.PASS, "Opportunities Name is not display");
//		}	
//		
//		WebElement AccountNameDisplay = driver.findElement(By.id("opp4_ileinner"));
//		if(AccountNameDisplay.getText().equals("Test"))
//		{
//			System.out.println("Account Name is display");
//			logger.log(LogStatus.PASS, "Account Name is display");
//		}
//		else
//		{
//			System.out.println("Account Name is not display");
//			logger.log(LogStatus.PASS, "Account Name is not display");
//		}
//		
//		WebElement probabilityDisplay = driver.findElement(By.id("opp12_ileinner"));
//		if(probabilityDisplay.getText().equals("10%"))
//		{
//			System.out.println("probability is display");
//			logger.log(LogStatus.PASS, "probability is display");
//		}
//		else
//		{
//			System.out.println("probability is not display");
//			logger.log(LogStatus.PASS, "probability is not display");
//		}
//		
//		WebElement leadSourceDisplay = driver.findElement(By.id("opp6_ileinner"));
//		if(leadSourceDisplay.getText().equals("Web"))
//		{
//			System.out.println("Lead Source is display");
//			logger.log(LogStatus.PASS, "Lead Source is display");
//		}
//		else
//		{
//			System.out.println("Lead Source is not display");
//			logger.log(LogStatus.PASS, "Lead Source is not display");
//		}
//		
//		WebElement StageDisplay = driver.findElement(By.id("opp11_ileinner"));
//		if(StageDisplay.getText().equals("Prospecting"))
//		{
//			System.out.println("Stage is display");
//			logger.log(LogStatus.PASS, "Stage is display");
//		}
//		else
//		{
//			System.out.println("Stage is not display");
//			logger.log(LogStatus.PASS, "Stage is not display");
//		}
//		
//		WebElement CloseDateDisplay = driver.findElement(By.id("opp9_ileinner"));
//		if(CloseDateDisplay.getText().equals(Fulldate))
//		{
//			System.out.println("Close Date is display");
//			logger.log(LogStatus.PASS, "Close Date is display");
//		}
//		else
//		{
//			System.out.println("Close Date is not display");
//			logger.log(LogStatus.PASS, "Close Date is not display");
//		}
//
//	}
//	
//	//@Test
//	public static void TC17_Test_Opportunity_Pipeline_Report() throws Exception
//	{
//		logger = reports.startTest("TC17_Test_Opportunity_Pipeline_Report");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC17_Test_Opportunity_Pipeline_Report.xls","TC17_Test_Opportunity_Pipeline");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//		
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//						
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement opportunitiesLink  = driver.findElement(By.xpath("//a[@class='listRelatedObject opportunityBlock title']"));
//		button_click(opportunitiesLink,"Opportunities Link");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		
//		WebElement OpportunityPipelineLink = driver.findElement(By.xpath("//a[contains(text(),'Opportunity Pipeline')]"));
//		button_click(OpportunityPipelineLink,"Opportunities Pipeline Link");
//				
//		WebElement pipelineDisplay = driver.findElement(By.xpath("//h1[@class='noSecondHeader pageType']"));
//		if(pipelineDisplay.getText() .equals("Opportunity Pipeline"))
//		{
//			System.out.println("Report Page with the Opportunities that are pipelined is displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that are pipelined is displayed.");
//		}
//		else
//		{
//			System.out.println("Report Page with the Opportunities that are pipelined is not displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that are pipelined is not displayed.");
//		}
//
//	}
//	
//	//@Test
//	public static void TC18_Test_Stuck_Opportunities_Report() throws Exception
//	{
//		logger = reports.startTest("TC18_Test_Stuck_Opportunities_Report");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC18_Test_Stuck_Opportunities_Report.xls","TC18_Test_Stuck_Opportunities");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");				
//
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement opportunitiesLink  = driver.findElement(By.xpath("//a[@class='listRelatedObject opportunityBlock title']"));
//		button_click(opportunitiesLink,"Opportunities Link");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		
//		WebElement stuckOpportunities = driver.findElement(By.xpath("//a[contains(text(),'Stuck Opportunities')]"));
//		button_click(stuckOpportunities,"Stuck Opportunities Link");
//				
//		WebElement stuckOpportunitiesDisplay = driver.findElement(By.xpath("//h1[@class='noSecondHeader pageType']"));
//		if(stuckOpportunitiesDisplay.getText() .equals("Stuck Opportunities"))
//		{
//			System.out.println("Report Page with the Opportunities that are Stuck is displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that are Stuck is displayed.");
//		}
//		else
//		{
//			System.out.println("Report Page with the Opportunities that are Stuck is not displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that are Stuck is not displayed.");
//		}
//
//	}
//	
//	//@Test
//	public static void TC19_Test_Quarterly_Summary_Report () throws Exception
//	{
//		logger = reports.startTest("TC19_Test_Quarterly_Summary_Report");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC19_Test_Quarterly_Summary_Report.xls","TC19_Test_Quarterly_Summary");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//							
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//				
//		WebElement opportunitiesLink  = driver.findElement(By.xpath("//a[@class='listRelatedObject opportunityBlock title']"));
//		button_click(opportunitiesLink,"Opportunities Link");
//				
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//				
//		WebElement interval = driver.findElement(By.id("quarter_q"));
//		String interval_input = data[1][4];
//		SelectDropDown(interval, "3", interval_input, "interval");
//				
//		WebElement include = driver.findElement(By.id("open"));
//		String 	include_input = data[1][5];
//		SelectDropDown(include, "3", include_input, "interval");
//				
//		WebElement runReport = driver.findElement(By.xpath("//table[@class='opportunitySummary']//input[@name='go']"));
//		button_click(runReport,"Run Report Button");
//				
//		WebElement OpportunitiesReportDisplay = driver.findElement(By.xpath("//h1[@class='noSecondHeader pageType']"));
//		if(OpportunitiesReportDisplay.getText() .equals("Opportunity Report"))
//		{
//			System.out.println("Report Page with the Opportunities that satisfies the search criteria is displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that satisfies the search criteria is displayed.");
//		}
//		else
//		{
//			System.out.println("Report Page with the Opportunities that satisfies the search criteria is not displayed.");
//			logger.log(LogStatus.PASS, "Report Page with the Opportunities that satisfies the search criteria is not displayed");
//		}
//
//	}
//	
//	//@Test
//	public static void TC20_Check_Leads_tab_link  () throws Exception
//	{
//		logger = reports.startTest("TC19_Test_Quarterly_Summary_Report");
//
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC20_Check_Leads_tab_link.xls","TC20_Check_Leads_tab_link");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//	
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement leadsLink = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink,"Leads Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//						
//		WebElement leads = driver.findElement(By.xpath("//h1[@class='pageType']"));
//		WebElement Home = driver.findElement(By.xpath("//h2[@class='pageDescription']"));
//		if(leads.getText().equals("Leads") && (Home.getText().equals("Home")))
//		{
//			System.out.println("Leads Home page is displayed.");
//			logger.log(LogStatus.PASS, "Leads Home page is displayed.");
//		}
//		else
//		{
//			System.out.println("Leads Home page is not displayed.");
//			logger.log(LogStatus.PASS, "Leads Home page is not displayed.");
//		}
//
//	}
//	
//	//@Test
//	public static void TC21_Validate_LeadsView_dropdown() throws Exception
//	{
//		logger = reports.startTest("TC21_Validate_LeadsView_dropdown");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC21_Validate_LeadsView_dropdown.xls","TC21_Validate_LeadsView");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement leadsLink = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink,"Leads Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		WebElement view = driver.findElement(By.id("fcf"));
//		List<WebElement> options = view.findElements(By.tagName("option"));
//		String Option = data[1][4];
//		String ExpectedOption[] = Option.split(",");
//		for(int i=0;i<ExpectedOption.length;i++)
//		{	
//			boolean flag = false;
//			for(int j=0;j<options.size();j++)
//			{
//				if (ExpectedOption[i].equals(options.get(i).getText()))
//				{
//					flag = true;
//					System.out.println("'"+ExpectedOption[i]+"' is available in the dropdown");
//					logger.log(LogStatus.PASS,"'"+ExpectedOption[i]+"' is available in the dropdown");
//					break;
//				}				
//			}
//			if(flag!=true)
//			{
//				System.out.println("'"+ExpectedOption[i]+"' is not available in the dropdown");
//				logger.log(LogStatus.PASS,"'"+ExpectedOption[i]+"' is not available in the dropdown");
//			}
//		}
//	}
//	
//	//@Test
//	public static void TC22_Functionality_of_the_Go_Button() throws Exception
//	{
//		logger = reports.startTest("TC22_Functionality_of_the_Go_Button");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC22_Functionality_of_the_Go_Button.xls","TC22_Functionality_of_the_Go");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement leadsLink = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink,"Leads Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//		Thread.sleep(3000);	
//		
//		WebElement view = driver.findElement(By.id("fcf"));
//		String view_input = data[1][4];
//		SelectDropDown(view, "3", view_input, "View Dropdown");
//		
//		WebElement usermenu = driver.findElement(By.xpath("//div[@id ='userNavButton']"));
//		button_click(usermenu,"User Menu");
//				
//		WebElement logOut = driver.findElement(By.xpath("//a[@title = 'Logout']"));		
//		button_click(logOut,"Log out");
//		Thread.sleep(3000);
//		
//		WebElement username1 = driver.findElement(By.xpath("//input[@name ='username']"));
//		enter_data_textbox(username1,username_input,"User Name");
//		
//		WebElement password1 = driver.findElement(By.xpath("//input[@name='pw']"));
//		enter_data_textbox(password1,password_input,"Password");
//		
//		WebElement LoginButton1 = driver.findElement(By.xpath("//input[@id='Login']"));						
//		button_click(LoginButton1,"Log In");
//		
//		WebElement allTabs1 = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs1,"All Tabs");
//		
//		WebElement leadsLink1 = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink1,"Leads Link");
//		
////		WebElement close1 = driver.findElement(By.id("tryLexDialogX"));
////		button_click(close1,"Close Window");
//		
//		WebElement goButton= driver.findElement(By.xpath("//span[@class='fBody']//input[@name='go']"));
//		button_click(goButton,"Go Button");
//		
//		WebElement view1 = driver.findElement(By.xpath("//option[contains(text(),\"Today's Leads\")]"));
//		System.out.println(view1.getAttribute("selected"));
//		if (view1.getAttribute("selected").equals("true"))
//		{
//			System.out.println("The default view in the dropdown is: "+view_input );
//			logger.log(LogStatus.PASS,"The default view in the dropdown is: "+view_input);
//		}
//		else
//		{
//			System.out.println("The default view in the dropdown is not: "+view_input );
//			logger.log(LogStatus.FAIL,"The default view in the dropdown is not: "+view_input);
//		}
//	}
//	
//	//@Test
//	public static void TC23_List_item_Todays_Leads_work() throws Exception
//	{
//		logger = reports.startTest("TC23_List_item_Todays_Leads_work");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC23_List_item_Todays_Leads_work.xls","TC23_List_item_Todays_Leads");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//		
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement leadsLink = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink,"Leads Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		WebElement view = driver.findElement(By.id("fcf"));
//		SelectDropDown(view, "3", "Today's Leads", "View Dropdown");
//				
//	}
//	
//	//@Test
//	public static void TC24_Check_New_button_on_Leads_Home() throws Exception
//	{
//		logger = reports.startTest("TC24_Check_New_button_on_Leads_Home");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC24_Check_New_button_on_Leads_Home.xls","TC24_Check_New_button_on_Leads");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement leadsLink = driver.findElement(By.xpath("//a[@class='listRelatedObject leadBlock title']"));
//		button_click(leadsLink,"Leads Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//			
//		WebElement newButton = driver.findElement(By.name("new"));
//		button_click(newButton,"New Button");
//			
//		String lastname_input = "LastName" + currentdatetime();
//		System.out.println(lastname_input);
//		WebElement lastName = driver.findElement(By.id("name_lastlea2"));
//		enter_data_textbox(lastName,lastname_input,"last Name");
//		
//		String company_input = "company" + currentdatetime();
//		System.out.println(company_input);
//		WebElement company = driver.findElement(By.id("lea3"));
//		enter_data_textbox(company,company_input,"company");
//		
//		WebElement saveButton = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@name='save']"));
//		button_click(saveButton,"save button");
//		
//		WebElement topName = driver.findElement(By.xpath("//h2[@class='topName']"));
//		boolean b = validate_String(topName,lastname_input);
//		Assert.assertTrue(b);
//	}
//	
//	//@Test
//	public static void TC25_Create_new_contact() throws Exception
//	{
//		logger = reports.startTest("TC25_Create_new_contact");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC25_Create_new_contact.xls","TC25_Create_new_contact");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement contactLink = driver.findElement(By.xpath("//a[@class='listRelatedObject contactBlock title']"));
//		button_click(contactLink,"contact Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//		
//		WebElement newButton = driver.findElement(By.name("new"));
//		button_click(newButton,"New Button");
//		
//		WebElement lastname = driver.findElement(By.id("name_lastcon2"));
//		String lastname_input = "last"+ currentdatetime();
//		enter_data_textbox(lastname,lastname_input,"Last Name");
//		
//		WebElement accountName = driver.findElement(By.id("con4"));
//		String accountName_input = data[1][4];
//		enter_data_textbox(accountName,accountName_input,"Account Name");
//		
//		WebElement saveButton = driver.findElement(By.xpath("//td[@id='topButtonRow']//input[@name='save']"));
//		button_click(saveButton,"Save Button");
//		
//		WebElement createdcontact = driver.findElement(By.xpath("//h2[@class='topName']"));
//		boolean b = validate_String(createdcontact,lastname_input);
//		Assert.assertTrue(b);
//			
//	}
//	
//	//@Test
//	public static void TC26_Create_newview_in_the_Contact_Page() throws Exception
//	{
//		logger = reports.startTest("TC26_Create_newview_in_the_Contact_Page");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC26_Create_newview_in_the_Contact_Page.xls","TC26_Create_newview");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement contactLink = driver.findElement(By.xpath("//a[@class='listRelatedObject contactBlock title']"));
//		button_click(contactLink,"contact Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//		
//		WebElement newView = driver.findElement(By.xpath("//a[contains(text(),'Create New View')]"));
//		button_click(newView,"Create New  View");
//		
//		WebElement viewName = driver.findElement(By.id("fname"));
//		String viewName_input = "view"+ currentdatetime();
//		enter_data_textbox(viewName,viewName_input,"View Name");
//		
//		WebElement viewUniquename = driver.findElement(By.id("devname"));
//		Date date = new Date();
//		String viewUniquename_input = "unique"+date.getDate()+ date.getTime();
//		button_click(viewUniquename,"View unique name");
//		viewUniquename.clear();
//		enter_data_textbox(viewUniquename,viewUniquename_input,"View unique Name");
//			
//		WebElement saveButton = driver.findElement(By.name("save"));
//		button_click(saveButton,"Save Button");
//		
//		WebElement viewdropdown = driver.findElement(By.name("fcf"));
//		System.out.println(viewdropdown.getText());
//		if (viewdropdown.getText().contains(viewName_input))
//		{
//			logger.log(LogStatus.PASS,"Created View name '"+viewName_input+ "' is displayed in drop down.");
//			System.out.println("Created View name '"+viewName_input+ "' is displayed in drop down.");	
//		}
//		else
//		{
//			logger.log(LogStatus.FAIL,"Created View name '"+viewName_input+ "' is not displayed in drop down.");
//			System.out.println("Created View name '"+viewName_input+ "' is not displayed in drop down.");	
//		}
//	}
//	
//	//@Test
//	public static void TC27_Check_recently_created_contact() throws Exception
//	{
//		logger = reports.startTest("TC27_Check_recently_created_contact");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC27_Check_recently_created_contact.xls","TC27_Check_recently_created");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement contactLink = driver.findElement(By.xpath("//a[@class='listRelatedObject contactBlock title']"));
//		button_click(contactLink,"contact Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//		
//		WebElement dropdown = driver.findElement(By.id("hotlist_mode"));
//		SelectDropDown(dropdown, "3", "Recently Created", "display Selection Dropdown");
//		
//		WebElement display = driver.findElement(By.xpath("//h3[contains(text(),'Recent Contacts')]"));
//		boolean b = validate_String(display,"Recent Contacts");
//		Assert.assertTrue(b);
//	}
//	
//	
//	//@Test
//	public static void TC28_Check_My_contacts_view() throws Exception
//	{
//		logger = reports.startTest("TC28_Check_My_contacts_view");
//		
//		String data[][] = getDataInput("C:\\Swetha\\SalesForce_Testcases\\TestData","TC28_Check_My_contacts_view.xls","TC28_Check_My_contacts_view");
//		
//		WebElement username = driver.findElement(By.xpath("//input[@name ='username']"));
//		String username_input = data[1][2];
//		enter_data_textbox(username,username_input,"User Name");
//		
//		//password textbox WebElement
//		WebElement password = driver.findElement(By.xpath("//input[@name='pw']"));
//		String password_input = data[1][3];
//		enter_data_textbox(password,password_input,"Password");
//									
//		//Log In button WebElement
//		WebElement LoginButton = driver.findElement(By.xpath("//input[@id='Login']"));
//		button_click(LoginButton,"Log In");
//
//		WebElement allTabs = driver.findElement(By.xpath("//img[@class='allTabsArrow']"));
//		button_click(allTabs,"All Tabs");
//			
//		WebElement contactLink = driver.findElement(By.xpath("//a[@class='listRelatedObject contactBlock title']"));
//		button_click(contactLink,"contact Link");
//			
//		WebElement close = driver.findElement(By.id("tryLexDialogX"));
//		button_click(close,"Close Window");
//		
//		WebElement view = driver.findElement(By.name("fcf"));
//		SelectDropDown(view, "3", "My Contacts", "View dropdown");
//	
//		
//	}
//	
	
	
	public static void button_click(WebElement button, String button_name)
	{
		if (button.isEnabled() == true)
		{
			button.click();
				System.out.println("'"+button_name+ "' was clicked");
				logger.log(LogStatus.PASS,"'"+button_name+ "' was clicked");					
		}
		else
		{
			logger.log(LogStatus.FAIL,button_name + " was not enabled");
			System.out.println(button_name + " was not enabled");
		}		
	}
	
	public static void enter_data_textbox(WebElement textbox,String inputData, String textbox_name)
	{
		if (textbox.isDisplayed()== true)
		{
			if (textbox.isEnabled() == true)
			{
				textbox.sendKeys(inputData);
				
				if(textbox.getAttribute("value").equals(inputData))
				{
					logger.log(LogStatus.PASS,"'"+inputData+ "' was entered in '"+textbox_name+ "' textbox ");
					System.out.println("'"+inputData+ "' was entered in '"+textbox_name+ "' textbox ");
				}
				else
				{
					logger.log(LogStatus.FAIL,"'"+inputData+ "' was not entered in '"+textbox_name+ "' textbox ") ;
					System.out.println("'"+inputData+ "' was not entered in '"+textbox_name+ "' textbox ");			
				}
			}
			else
			{
				logger.log(LogStatus.FAIL,textbox_name + " button was not enabled");
				System.out.println(textbox_name + " textbox was not enabled");
			}
		}
		else
		{
			logger.log(LogStatus.FAIL,textbox_name + " button was not displayed");
			System.out.println(textbox_name + " textbox was not displayed");
		}
	}

	public static boolean validate_String(WebElement message, String expectedMessage)
	{
		boolean b = false;
		if(message.getText().equals(expectedMessage))
		{
			b= true;
			logger.log(LogStatus.PASS,"The required message '" +message.getText() +"' was validated successfully");
			System.out.println("The required message '" +message.getText() +"' was validated successfully");
		}
		else
		{
			logger.log(LogStatus.FAIL,"The required message '" +message.getText() +"' was not validated");
			System.out.println("The required message '" +message.getText() +"' was not validated");
		}
		//Assert.assertEquals(message.getText(), expectedMessage,"The required message '\" +message.getText() +\"' was not validated\"");
		return b;
		
		//sa.assertAll();
	}	
	
	public static boolean validate_Attribute(WebElement WebElement, String expectedMessage,String attribute,String WebElement_name) throws Exception
	{		
		boolean b = false;
		String Attribute = WebElement.getAttribute(attribute);
		//System.out.println(Attribute);
		if(Attribute == expectedMessage)
		{
			b= true;
			logger.log(LogStatus.PASS,"Validation done successfully: '"+WebElement_name+ "' have value as expected: '"+Attribute );
			System.out.println("Validation done successfully: '"+WebElement_name+ "' have value as expected: '"+Attribute);
		}
		else
		{
			logger.log(LogStatus.FAIL, "Validation was unsuccessfull: '"+WebElement_name+ "' does not have value as expected: '"+Attribute);
			System.out.println("Validation was unsuccessfull: '"+WebElement_name+ "' does not have value as expected: '"+Attribute);
		}
		return b;
	}
	
	public static void checkBox(WebElement checkBox,String checkBox_name)
	{
		if(checkBox.isDisplayed()== true)
		{
			if (checkBox.isEnabled() == true)
			{
				checkBox.click();
				if (checkBox.isSelected() == true)
				{
					logger.log(LogStatus.PASS,"'"+checkBox_name+ "' checkbox was selected successfully");
					System.out.println("'"+checkBox_name+ "' checkbox was selected successfully");
				}
				else
				{
					logger.log(LogStatus.FAIL, "'"+checkBox_name+ "' checkbox was not selected successfully");
					System.out.println("'"+checkBox_name+ "' checkbox was not selected successfully");
				}
			}
			else
			{
				logger.log(LogStatus.FAIL,checkBox_name + " CheckBox was not enabled");
				System.out.println(checkBox_name + " CheckBox was not enabled");
			}
		}
		else
		{
			logger.log(LogStatus.FAIL,"checkBox_name + \" CheckBox was not displayed\"");
			System.out.println(checkBox_name + " CheckBox was not displayed");
		}
		
	}
	
	public static void SelectDropDown(WebElement Element, String optiontype, String option,String dropdown_name)
	{
		if(Element.isEnabled() == true)
		{
			if (Element.getText().contains(option))
			{
				Select dropdown = new Select(Element);
//				if(optiontype == "1")
//					dropdown.selectByIndex(index);
				if(optiontype == "2")
					dropdown.selectByValue(option);
				else if(optiontype == "3")
					dropdown.selectByVisibleText(option);
				else
					System.out.println("Wrong options");
			
				//System.out.println(Element.getAttribute("selected"));
				System.out.println(dropdown.getFirstSelectedOption().getText());
				if (dropdown.getFirstSelectedOption().getText().equals(option))
				{
					System.out.println("The mentioned option is selected in the "+dropdown_name);
					logger.log(LogStatus.PASS, "The mentioned option '"+option+ "' is selected in the "+dropdown_name);
				}
				else
				{
					System.out.println("The mentioned option is not selected in the "+dropdown_name);
					logger.log(LogStatus.FAIL, "The mentioned option '"+option+ "' is selected in the "+dropdown_name);
				}
			}			
		}		
	}
		
	public static boolean validateDisplayedElement(WebElement element,String elementName)
	{
		boolean b = false;
		if(element.isDisplayed() == true)
		{
			b=true;
			System.out.println(elementName + "  is displayed");
			logger.log(LogStatus.PASS,elementName + "  is displayed");
		}
		else
		{
			System.out.println(elementName + "  is not displayed");
			logger.log(LogStatus.FAIL,elementName + "  is not displayed");
		}
		return b;	
			
	}

	public static String currentdatetime()
	{
		Date currentDate = new Date();
		String dateToStr = DateFormat.getInstance().format(currentDate);
		String date[]= dateToStr.split(" ");    
		String fulldate[] = date[0].split("/");
		String fulldatetime = fulldate[0] + "/" + fulldate[1] + "/" + "20"+fulldate[2] + date[1];
		return fulldatetime;
	}
		
}	


