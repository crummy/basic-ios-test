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

		URL endpoint = new URL(APPIUM_SERVER);

		// We generate a random UUID for later lookup in logs for debugging
		String testUUID = UUID.randomUUID().toString();
		System.out.println("TestUUID: " + testUUID);
		capabilities.setCapability("testobject_testuuid", testUUID);

		driver = new IOSDriver(endpoint, capabilities);

		System.out.println(driver.getCapabilities().getCapability("testobject_test_report_url"));
		System.out.println(driver.getCapabilities().getCapability("testobject_test_live_view_url"));
	}

	@Test
	public void openWebpageAndTakeScreenshot() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String url = "http://www.amazon.com";
		driver.get(url);
		driver.rotate(ScreenOrientation.LANDSCAPE);
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
