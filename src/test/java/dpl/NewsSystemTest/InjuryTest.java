package dpl.NewsSystemTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dpl.NewsSystem.InjuryPublisher;

public class InjuryTest {
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
        InjuryPublisher.getInstance().notify("Wayne Gretzky", 20);
        assertEquals(OutputConstants.INJURY, console.toString().trim());
    }
}
