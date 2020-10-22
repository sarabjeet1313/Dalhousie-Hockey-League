package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TradeTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private TradePublisher publisher = new TradePublisher();
	private ITrade subscriber = new NewsSubscriber();
	
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
	public void outputJsonTest() {
		publisher.notify("Montreal Canadiens", "Winnipeg Jets", new String[][] {{"Mario Lemieux", "Patrick Roy"}, {"Wayne Gretzky"}});
		assertEquals(OutputConstants.TRADES, console.toString().trim());
	}
		
}
