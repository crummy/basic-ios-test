import org.junit.Test;
import org.openqa.selenium.html5.Location;

public class LocationTest extends AbstractTest {

	@Test
	public void LocationTest() {
		driver.setLocation(new Location(48.8583701, 2.2922926, 0.0));
	}

}