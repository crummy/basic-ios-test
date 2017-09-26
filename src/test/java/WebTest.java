import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WebTest extends AbstractTest {
	DesiredCapabilities capabilities;

	// Credentials differ slightly for the web test so we override setup().
	@Before
	@Override
	public void setup() throws MalformedURLException {
		capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_api_key", getEnvOrFail("TESTOBJECT_API_KEY_WEB"));
		capabilities.setCapability("testobject_appium_version", getEnvOrDefault("TESTOBJECT_APPIUM_VERSION", "1.6.5"));

		setOptionalCapability("automationName", "AUTOMATION_NAME");
		setOptionalCapability("TESTOBJECT_DEVICE");
		setOptionalCapability("deviceName", "DEVICE_NAME");
		setOptionalCapability("testobject_app_id", "TESTOBJECT_APP_ID_WEB");
		setOptionalCapability("TESTOBJECT_CACHE_DEVICE");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_TIMEOUT");
		setOptionalCapability("TESTOBJECT_SESSION_CREATION_RETRY");

		URL endpoint = new URL(APPIUM_SERVER);

		// We generate a random UUID for later lookup in logs for debugging
		String testUUID = UUID.randomUUID().toString();
		System.out.println("TestUUID: " + testUUID);
		capabilities.setCapability("testobject_testuuid", testUUID);
		
		System.out.println("Allocating driver with capabilities:\n" + capabilities);
		driver = new IOSDriver(endpoint, capabilities);

		System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
		System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
	}

	@Test
	public void openWebpageAndTakeScreenshot() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

		Thread.sleep(10000);
		String url = "http://www.amazon.com";
		driver.get(url);
		driver.rotate(ScreenOrientation.LANDSCAPE);
		Thread.sleep(1000);
		driver.rotate(ScreenOrientation.PORTRAIT);

		takeScreenshot();
	}

	private void takeScreenshot() {
		try {
			driver.getScreenshotAs(OutputType.FILE);
		} catch (Exception e) {
			System.out.println("Exception while saving screenshot: " + e.getMessage());
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
