package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import PageObject.LandingPage;
//import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetUp {
	
	public WebDriver driver;
	public LandingPage landingpage ;
	
	public WebDriver intilizeDriver() throws IOException {
		
//		Properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//resources/GlobalData.properties");
		prop.load(fis);

		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		
		
		if(browserName.equalsIgnoreCase("chrome")) {
			//WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edge")) {
//			edge
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
//			firefox
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException{
//		Reading json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		
//		String to HaspMap ---Using jackson databind
		ObjectMapper mapper = new ObjectMapper(); 
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>(){
			
		});
		return data;
	}
	
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		return System.getProperty("user.dir")+"//reports"+testCaseName+".png";
	}
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = intilizeDriver();
		landingpage = new LandingPage(driver);
		landingpage.naviagte_LandingPage();
		return landingpage;
	}
	
	@AfterMethod(alwaysRun = true)
	public void closeWindow() {
		driver.quit();
	}
	
}