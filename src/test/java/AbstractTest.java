import io.appium.java_client.ios.IOSDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractTest {

	static final String APPIUM_SERVER = getEnvOrDefault("APPIUM_URL", "https://app.testobject.com:443/api/appium/wd/hub");

	IOSDriver driver;
	DesiredCapabilities capabilities;

	@Before
	public void setup() throws MalformedURLException {
		System.out.println("setup()");
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", getEnvOrFail("TESTOBJECT_API_KEY"));
		capabilities.setCapability("testobject_appium_version", getEnvOrDefault("TESTOBJECT_APPIUM_VERSION", "1.6.5"));

		setOptionalCapability("automationName", "AUTOMATION_NAME");
		setOptionalCapability("deviceName", "DEVICE_NAME");
		setOptionalCapability("testobject_device", "TESTOBJECT_DEVICE");
		setOptionalCapability("platformVersion", "PLATFORM_VERSION");
		setOptionalCapability("TESTOBJECT_APP_ID");
		setOptionalCapability("TESTOBJECT_CACHE_DEVICE");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_TIMEOUT");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_RETRY");

		// We generate a random UUID for later lookup in logs for debugging
		String testUUID = UUID.randomUUID().toString();
		System.out.println("TestUUID: " + testUUID);
		capabilities.setCapability("testobject_testuuid", testUUID);

		System.out.println("Allocating driver with capabilities:\n" + capabilities);
		driver = new IOSDriver(new URL(APPIUM_SERVER), capabilities);

		System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
		System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
	}

	@After
	public void tearDown() {
		if (driver != null) {
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

	private void setOptionalCapability(String var) {
		Optional.ofNullable(System.getenv(var.toUpperCase()))
				.filter(env -> !env.isEmpty())
				.ifPresent(data -> capabilities.setCapability(var, data));
	}

	private void setOptionalCapability(String desiredCapabilityName, String environmentVariableName) {
		Optional.ofNullable(System.getenv(environmentVariableName))
				.filter(env -> !env.isEmpty())
				.ifPresent(value -> capabilities.setCapability(desiredCapabilityName, value));
	}

}
