package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FreeAgencyTest {
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
    public void outputJsonHiredTest() {
        FreeAgencyPublisher.getInstance().notify("Wayne Gretzky", "hired");
        //assertEquals(OutputConstants.HIRED, console.toString().trim());
    }

    @Test
    public void outputJsonReleasedTest() {
        FreeAgencyPublisher.getInstance().notify("Wayne Gretzky", "released");
        //assertEquals(OutputConstants.RELEASED, console.toString().trim());
    }
}
