package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import dal.asd.dpl.TeamManagement.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InjuryTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private InjuryPublisher publisher = new InjuryPublisher();
	private IInjuryInfo subscriber = NewsSubscriber.getInstance();
	
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
		Player player = new Player("Player1", "Defense", false, 1, 1, 2, 1, 1, true, false, 0);
		publisher.notify(player);
		//assertEquals(OutputConstants.INJURY, console.toString().trim());
	}
}
