package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FreeAgencyTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private FreeAgencyPublisher publisher = new FreeAgencyPublisher();
	private IFreeAgency subscriber = NewsSubscriber.getInstance();
	
	@Before
	public void before() {
		System.setOut(new PrintStream(console));
		publisher.subscribe(subscriber);
	}
	
	@After
	public void after() {
		System.setOut(null);
		publisher.unsubscribe(subscriber);
	}
	
	@Test
	public void outputJsonHiredTest() {
		publisher.notify("Wayne Gretzky", "hired");
		assertEquals(OutputConstants.HIRED, console.toString().trim());
	}
	
	@Test
	public void outputJsonReleasedTest() {
		publisher.notify("Wayne Gretzky", "released");
		assertEquals(OutputConstants.RELEASED, console.toString().trim());
	}
}
