package screen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;

public class Calculator {

	private MobileElement button2;

	private MobileElement button3;

	private MobileElement buttonEquals;

	private MobileElement buttonMultiply;

	private MobileElement resultField;

	IOSDriver driver;

	public Calculator(IOSDriver driver) {
		this.driver = driver;
		button3 = (MobileElement)driver.findElement(By.name("3"));
		button2 = (MobileElement)driver.findElement(By.name("2"));
		buttonMultiply  = (MobileElement)driver.findElement(By.name("×"));
		buttonEquals  = (MobileElement)driver.findElement(By.name("="));
		resultField = (MobileElement)driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]"));
	}

	public Calculator press2() {
		button2.click();
		return this;
	}

	public Calculator press3() {
		button3.click();
		return this;
	}

	public Calculator pressEquals() {
		buttonEquals.click();
		return this;
	}

	public String getResult() {
		return resultField.getText();
	}

	public Calculator pressMultiply() {
		buttonMultiply.click();
		return this;
	}
}
