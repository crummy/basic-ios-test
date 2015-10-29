import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public abstract class AbstractTest {

	protected static final String TESTOBJECT_API_KEY = getEnvOrFail("TESTOBJECT_API_KEY");
	protected static final String TESTOBJECT_APP_ID = getEnvOrFail("TESTOBJECT_APP_ID");
	protected static final String APPIUM_SERVER = getEnvOrDefault("APPIUM_SERVER", "https://app.testobject.com:443/api/appium/wd/hub");
	protected static final String TESTOBJECT_DEVICE = getEnvOrDefault("TESTOBJECT_DEVICE", "iPhone_5S_16GB_real");
	protected static final String TESTOBJECT_APPIUM_VERSION = getEnvOrDefault("TESTOBJECT_APPIUM_VERSION", "1.3.7");

	protected IOSDriver driver;

	@Before
	public void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_device", TESTOBJECT_DEVICE);
		capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY);
		capabilities.setCapability("testobject_app_id", TESTOBJECT_APP_ID);
		capabilities.setCapability("testobject_appium_version", TESTOBJECT_APPIUM_VERSION);

		URL endpoint = new URL(APPIUM_SERVER);

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

	protected static String getEnvOrFail(String environmentVariable) {
		String value = System.getenv(environmentVariable);
		if (value == null) {
			throw new RuntimeException("Missing required environment variable " + environmentVariable);
		} else {
			return value;
		}
	}

	protected static String getEnvOrDefault(String environmentVariable, String defaultValue) {
		String value = System.getenv(environmentVariable);
		if (value == null) {
			return defaultValue;
		} else {
			return value;
		}
	}


}
