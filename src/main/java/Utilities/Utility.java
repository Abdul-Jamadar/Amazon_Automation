package Utilities;

import static org.testng.Assert.expectThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Iterator;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

import Scenarios.ExchangeOfferValidation;
import Scenarios.Login;
import Scenarios.SearchForProducts;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Utility {

	public WebDriver driver;
	public int implicitlyWaitDuration = 30;
	public int explicitlyWaitDuration = 30;
	public WebDriverWait wait;
	public Login login;
	public SearchForProducts product;
	public ExchangeOfferValidation exchange;
	public Properties prop = new Properties();
	public String UserName;
	public String mobileNumber;
	public String urPassword;
	public String browserName;
	public String iMEI;
	public String model;

	public WebDriver driverSetup() throws Exception {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//Property.properties");
		prop = new Properties();
		prop.load(fis);
		browserName = prop.getProperty("browserName");
		UserName = prop.getProperty("username");
		mobileNumber = prop.getProperty("mobilenumber");
		urPassword = prop.getProperty("password");
		iMEI = prop.getProperty("IMEI");
		model = prop.getProperty("model");

		if (browserName.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equals("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}

		else if (browserName.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitlyWaitDuration));
		return driver;
	}

	public void waitForElement(By locator) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(explicitlyWaitDuration));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public void getScreenShot(String fileName) throws IOException {
		File SS = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		Files.copy(SS, new File("./src/test/java/ScreenCaptures/" + fileName + ".png"));
	}

	public void setup() throws Exception {
		driverSetup();
		login = new Login(driver);
		product = new SearchForProducts(driver);
		exchange = new ExchangeOfferValidation(driver);
	}

	public void setData(String productName, String orinalPrice, String exchange_value, String finalValue)
			throws Exception {

		FileInputStream fis = new FileInputStream("./src/main/resources/Product Sheet.xlsx");
		XSSFWorkbook book = new XSSFWorkbook(fis);
		XSSFSheet sheet = book.getSheet("Product details");
		Iterator<Row> itrRow = sheet.iterator();

		if (!itrRow.hasNext()) {
			System.out.println(productName + " entered if loop");
			CellIterator(0, productName, orinalPrice, exchange_value, finalValue, sheet);
		} else if (itrRow.hasNext()) {
			CellIterator(sheet.getLastRowNum() + 1, productName, orinalPrice, exchange_value, finalValue, sheet);
		}
		FileOutputStream fos = new FileOutputStream("./src/main/resources/Product Sheet.xlsx");
		book.write(fos);

	}

	public void CellIterator(int rowNumber, String productName, String orinalPrice, String exchange_value,
			String finalValue, XSSFSheet sheet) {
		Row row1 = sheet.createRow(rowNumber);
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue(productName);
		Cell cell2 = row1.createCell(1);
		cell2.setCellValue(orinalPrice);
		Cell cell3 = row1.createCell(2);
		cell3.setCellValue(exchange_value);
		Cell cell4 = row1.createCell(3);
		cell4.setCellValue(finalValue);
	}

	public void MySQL(String productName, String orinalPrice, String exchange_value, String finalValue)
			throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "student", "Abdul@1501");
		ResultSet rs = conn.createStatement().executeQuery("show tables");
		short x = 0;
		if (!rs.next()) {
			conn.createStatement().executeUpdate(
					"create table productdetails (Product_Name varchar (50), Original_value_Rs varchar(30), Old_Phone_value_Rs\r\n"
							+ "varchar(30), Final_value_after_exchange_Rs varchar(30))");
		} else if (rs.next()) {
			while (rs.next()) {
				if (rs.getString("Tables_in_demo").equalsIgnoreCase("productdetails")) {
					x = 1;
					break;
				}
			}

			if (x == 0) {
				conn.createStatement().executeUpdate(
						"create table productdetails (Product_Name varchar (50), Original_value_Rs varchar(30), Old_Phone_value_Rs\r\n"
								+ "varchar(30), Final_value_after_exchange_Rs varchar(30))");
			}
		}
		conn.createStatement().executeUpdate("insert into productdetails values ('" + productName + "','" + orinalPrice
				+ "','" + exchange_value + "','" + finalValue + "')");
	}

	public void tearDown() {
		driver.quit();
	}
}
