import org.apache.commons.lang3.LocaleUtils;
import org.junit.Test;
import screen.LocaleViewer;

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class LocaleTest extends AbstractTest {

	@Test
	public void localeTest() {
		Locale expectedLocale = LocaleUtils.toLocale(System.getenv("EXPECTED_LOCALE"));

		LocaleViewer localeViewer = new LocaleViewer(driver);
		assertEquals(expectedLocale.getLanguage(), localeViewer.getLanguage());
		assertEquals(expectedLocale.getCountry(), localeViewer.getCountry());
	}
}
