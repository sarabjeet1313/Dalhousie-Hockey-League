package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RetirementTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private final IRetirementInfo subscriber = new NewsSubscriber();
	
	@Before
	public void before() {
		System.setOut(new PrintStream(console));
		RetirementPublisher.getInstance().subscribe(subscriber);
	}
	
	@After
	public void after() {
		System.setOut(null);
		RetirementPublisher.getInstance().unsubscribe(subscriber);
	}
	
	@Test
	public void outputJsonTest() {
		RetirementPublisher.getInstance().notify("Wayne Gretzky", 38);
		assertEquals(OutputConstants.RETIRED, console.toString().trim());
	}
}
