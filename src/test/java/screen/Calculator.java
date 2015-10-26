package screen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.By;

public class Calculator {
	@iOSFindBy(name="2")
	private MobileElement button2;

	//@iOSFindBy(name="3")
	private MobileElement button3;

	@iOSFindBy(name="=")
	private MobileElement buttonEquals;

	@iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIAButton[8]")
	private MobileElement buttonMultiply;

	@iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]")
	private MobileElement resultField;

	IOSDriver driver;

	public Calculator(IOSDriver driver) {
		this.driver = driver;
		button3 = (MobileElement)driver.findElement(By.name("3"));
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
