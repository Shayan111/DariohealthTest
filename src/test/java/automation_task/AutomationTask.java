package automation_task;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import junit.framework.Assert;


//Note: I have added thread.sleep after every click or action so that the 
//user/examiner can have a clear picture of what is happening.


public class AutomationTask {
	
	
	public static XSSFWorkbook wb; 
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static FileInputStream fis;
	private By input_box = By.xpath("//input[@class='new-todo']");
	private By items_left_count = By.xpath("//span[@class='todo-count']");
	private By completed = By.xpath("//*[text()='Completed']");
	private By write = By.xpath("//label[text()='Write']");
	private By r_eft_ead = By.xpath("//label[text()='R eft ead']");
	private By all = By.xpath("//a[text()='All']");
	private By write_checkbox = By.xpath("(//input[@class='toggle' and @type='checkbox'])[1]");
	private By clear_completed = By.xpath("//button[@class='clear-completed']");
	private By total_items = By.xpath("//*[@type='checkbox']");
	@Test
	public void automation_task() throws org.openqa.selenium.WebDriverException, InterruptedException, IOException
	{
		
		String projectPath = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", projectPath+"/driver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://todomvc.com/examples/react/#/");
		Thread.sleep(5000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		WebElement input = driver.findElement(input_box);
		input.sendKeys("Write");
		Thread.sleep(5000);
		input.sendKeys(Keys.RETURN);
		Thread.sleep(5000);
		input.sendKeys("R eft ead");
		Thread.sleep(5000);
		input.sendKeys(Keys.RETURN);
		Thread.sleep(5000);
		WebElement items_left_text = driver.findElement(items_left_count);
		Assert.assertEquals(items_left_text.getText(), "2 items left");
		System.out.println(items_left_text.getText());
		WebElement completed_element = driver.findElement(completed);
		completed_element.click();
		Thread.sleep(5000);
		
		int write_size = driver.findElements(write).size();
		int r_eft_ead_size = driver.findElements(r_eft_ead).size();
		Thread.sleep(5000);
		Assert.assertTrue(write_size == 0 && r_eft_ead_size == 0);
		
		driver.findElement(all).click();
		Thread.sleep(5000);
		driver.findElement(write_checkbox).click();
		Thread.sleep(5000);
		Assert.assertEquals(items_left_text.getText(), "1 item left");
		
		completed_element.click();
		Thread.sleep(5000);
		write_size = driver.findElements(write).size();
		Assert.assertTrue(write_size > 0);
		
		driver.findElement(clear_completed).click();
		Thread.sleep(5000);
		write_size = driver.findElements(write).size();
		Assert.assertTrue(write_size == 0);
		
		Thread.sleep(5000);
		
	
		List<String> actions_list = new ArrayList<String>();
		
		fis= new FileInputStream(projectPath+"/excel_sheet/to_do_actions.xlsx");
		wb = new XSSFWorkbook(fis);
        sheet= wb.getSheet("Sheet1");
        row = sheet.getRow(1);
        cell = row.getCell(1);
        actions_list.add(cell.getStringCellValue());
        row = sheet.getRow(3);
        cell = row.getCell(1);
        actions_list.add(cell.getStringCellValue());
        row = sheet.getRow(5);
        cell = row.getCell(1);
        actions_list.add(cell.getStringCellValue());
        
        
        driver.findElement(all).click();
        input.sendKeys(actions_list.get(0));
		input.sendKeys(Keys.RETURN);
		Thread.sleep(5000);
        input.sendKeys(actions_list.get(1));
		input.sendKeys(Keys.RETURN);
		Thread.sleep(5000);
        input.sendKeys(actions_list.get(2));
		input.sendKeys(Keys.RETURN);
		Thread.sleep(5000);
		
		int total_items_in_the_list = driver.findElements(total_items).size()-1;
        
		System.out.println("Total Items = "+total_items_in_the_list);
		Thread.sleep(5000);		
		driver.quit();
		
	}
}