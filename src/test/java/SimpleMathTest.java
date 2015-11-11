import org.junit.Test;
import screen.Calculator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SimpleMathTest extends AbstractTest {

	@Test
	public void AdditionTest() {
		Calculator calculator = new Calculator(driver)
				.press3()
				.pressMultiply()
				.press2()
				.press3()
				.pressEquals()
				.resultShouldEqual("69.0");
	}

}
