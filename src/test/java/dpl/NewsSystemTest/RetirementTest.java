package dpl.NewsSystemTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dpl.NewsSystem.RetirementPublisher;

public class RetirementTest {
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
		RetirementPublisher.getInstance().notify("Wayne Gretzky", 38);
		assertEquals(OutputConstants.RETIRED, console.toString().trim());
	}
}
