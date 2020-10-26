package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamePlayedTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private GamePlayedPublisher publisher = new GamePlayedPublisher();
	private IGamesPlayedInfo subscriber = NewsSubscriber.getInstance();
	
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
		publisher.notify("Montreal Canadiens", "Toronto Maple Leafs", "25-10-2020");
		assertEquals(OutputConstants.GAMEPLAYED, console.toString().trim());
	}
}
