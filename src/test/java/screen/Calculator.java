package screen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.assertEquals;

public class Calculator {

	@iOSFindBy(accessibility="2")
	private MobileElement button2;

	@iOSFindBy(accessibility="3")
	private MobileElement button3;

	@iOSFindBy(accessibility="=")
	private MobileElement buttonEquals;

	@iOSFindBy(accessibility="×")
	private MobileElement buttonMultiply;

	@iOSFindBy(xpath="//UIAApplication[1]/UIAWindow[1]/UIAStaticText[1]")
	private MobileElement resultField;


	public Calculator(IOSDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
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

	public Calculator resultShouldEqual(String result) {
		assertEquals("checking result is " + result, result, getResult());
		return this;
	}
}
