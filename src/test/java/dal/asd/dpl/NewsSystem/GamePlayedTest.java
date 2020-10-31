package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GamePlayedTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();
	//private final IGamesPlayedInfo subscriber = new NewsSubscriber();
	//private List<IGamesPlayedInfo> subscribers;

	@Before
	public void before() {
		System.setOut(new PrintStream(console));
		/*subscribers = GamePlayedPublisher.getInstance().getSubscribers();
		if(subscribers.size() > 0 && !(subscribers.get(0) instanceof NewsSubscriber))
			GamePlayedPublisher.getInstance().subscribe(subscriber);*/
	}

	@After
	public void after() {
		System.setOut(null);
		/*if(subscribers.size() > 0 && !(subscribers.get(0) instanceof NewsSubscriber))
			GamePlayedPublisher.getInstance().unsubscribe(subscriber);*/
	}

	@Test
	public void outputJsonTest() {
		GamePlayedPublisher.getInstance().notify("Montreal Canadiens", "Toronto Maple Leafs", "25-10-2020");
		assertEquals(OutputConstants.GAMEPLAY, console.toString().trim());
	}
}
