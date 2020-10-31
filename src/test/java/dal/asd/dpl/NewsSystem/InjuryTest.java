package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InjuryTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private final IInjuryInfo subscriber = new NewsSubscriber();
	
	@Before
	public void before() {
		System.setOut(new PrintStream(console));
		InjuryPublisher.getInstance().getSubscribers().clear();
		InjuryPublisher.getInstance().subscribe(subscriber);
	}
	
	@After
	public void after() {
		System.setOut(null);
		InjuryPublisher.getInstance().unsubscribe(subscriber);
	}
	
	@Test
	public void outputJsonTest() {
		InjuryPublisher.getInstance().notify("Wayne Gretzky", 20);
		assertEquals(OutputConstants.INJURY, console.toString().trim());
	}
}
