import io.appium.java_client.ios.IOSDriver;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class WebTest extends AbstractTest {
	private static String TESTOBJECT_API_KEY_WEB = getEnvOrFail("TESTOBJECT_API_KEY_WEB");
	private static String TESTOBJECT_APP_ID_WEB = getEnvOrDefault("TESTOBJECT_APP_ID_WEB", "1");

	// Credentials differ slightly for the web test so we override setup().
	@Before
	@Override
	public void setup() throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("testobject_device", TESTOBJECT_DEVICE);
		capabilities.setCapability("testobject_api_key", TESTOBJECT_API_KEY_WEB);
		capabilities.setCapability("testobject_app_id", TESTOBJECT_APP_ID_WEB);
		capabilities.setCapability("testobject_appium_version", TESTOBJECT_APPIUM_VERSION);
		capabilities.setCapability("testobject_cache_device", TESTOBJECT_CACHE_DEVICE);

		String AUTOMATION_NAME = System.getenv("AUTOMATION_NAME");
		if (AUTOMATION_NAME != null) {
			capabilities.setCapability("automationName", AUTOMATION_NAME);
		}

		String TESTOBJECT_SESSION_CREATION_TIMEOUT = System.getenv("TESTOBJECT_SESSION_CREATION_TIMEOUT");
		if (TESTOBJECT_SESSION_CREATION_TIMEOUT != null) {
			capabilities.setCapability("testobject_session_creation_timeout", TESTOBJECT_SESSION_CREATION_TIMEOUT);
		}

		String TESTOBJECT_SESSION_CREATION_RETRY = System.getenv("TESTOBJECT_SESSION_CREATION_RETRY");
		if (TESTOBJECT_SESSION_CREATION_RETRY != null) {
			capabilities.setCapability("testobject_session_creation_retry", TESTOBJECT_SESSION_CREATION_RETRY);
		}

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
}
