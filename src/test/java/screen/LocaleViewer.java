package screen;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;
import org.openqa.selenium.support.PageFactory;

public class LocaleViewer {

	@iOSFindBy(xpath="//XCUIElementTypeStaticText[1]")
	private MobileElement language;

	@iOSFindBy(xpath="//XCUIElementTypeStaticText[2]")
	private MobileElement country;

	public LocaleViewer(IOSDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public String getLanguage() {
		return language.getText();
	}

	public String getCountry() {
		return country.getText();
	}
}
