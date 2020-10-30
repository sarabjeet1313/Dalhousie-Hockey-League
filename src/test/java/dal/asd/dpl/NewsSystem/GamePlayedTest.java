package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamePlayedTest {
	//private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	private final IGamesPlayedInfo subscriber = new NewsSubscriber();
	
	@Before
	public void before() {
		//System.setOut(new PrintStream(console));
		GamePlayedPublisher.getInstance().subscribe(subscriber);
	}
	
	@After
	public void after() {
		//System.setOut(null);
		GamePlayedPublisher.getInstance().unsubscribe(subscriber);
	}
	
	@Test
	public void outputJsonTest() {
		ByteArrayOutputStream console = new ByteArrayOutputStream();
		System.setOut(new PrintStream(console));
		GamePlayedPublisher.getInstance().notify("Montreal Canadiens", "Toronto Maple Leafs", "25-10-2020");
		//assertEquals(OutputConstants.GAMEPLAY, console.toString().trim());
	}
}
