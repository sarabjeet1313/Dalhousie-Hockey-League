package dpl.NewsSystemTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dpl.NewsSystem.GamePlayedPublisher;

public class GamePlayedTest {
	private final ByteArrayOutputStream console = new ByteArrayOutputStream();

	@Before
	public void before() {
		System.setOut(new PrintStream(console));
	}

	@After
	public void after() {
		System.setOut(null);
	}

	@Test
	public void outputJsonTest() {
		GamePlayedPublisher.getInstance().notify("Montreal Canadiens", "Toronto Maple Leafs", "25-10-2020");
		assertEquals(OutputConstants.GAMEPLAY, console.toString().trim());
	}
}
