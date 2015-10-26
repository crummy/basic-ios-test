import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractTest {

	private static final String TESTOBJECT_API_KEY = getEnvOrFail("TESTOBJECT_API_KEY");
	private static final String TESTOBJECT_APP_ID = getEnvOrFail("TESTOBJECT_APP_ID");
	private static final String APPIUM_SERVER_URL = getEnvOrDefault("APPIUM_SERVER", "https://app.testobject.com:443/api/appium/wd/hub");
	private static final String TESTOBJECT_DEVICE = getEnvOrDefault("TESTOBJECT_DEVICE", "iPhone_5S_16GB_real");

	protected IOSDriver driver;

	@Before
	public void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_device", TESTOBJECT_DEVICE);
		capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		capabilities.setCapability("testobject_app_id", TESTOBJECT_APP_ID);
		capabilities.setCapability("testobject_appium_version", "1.4.13");

		URL endpoint = new URL(APPIUM_SERVER_URL);

		driver = new IOSDriver(endpoint, capabilities);

		if (driver != null) {
			System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
			System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
		}
	}

	@After
	public void tearDown() {
		if(driver != null) {
			driver.quit();
		}
	}

	private static String getEnvOrFail(String environmentVariable) {
		String value = System.getenv(environmentVariable);
		if (value == null) {
			throw new RuntimeException("Missing required environment variable " + environmentVariable);
		} else {
			return value;
		}
	}

	private static String getEnvOrDefault(String environmentVariable, String defaultValue) {
		String value = System.getenv(environmentVariable);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}


}
