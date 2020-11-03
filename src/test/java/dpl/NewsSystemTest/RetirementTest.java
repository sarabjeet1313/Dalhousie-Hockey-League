package dpl.NewsSystemTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

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

}
