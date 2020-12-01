package dpl.NewsSystemTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dpl.NewsSystem.TradePublisher;

public class TradeTest {
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
        ArrayList<String> s1 = new ArrayList<>();
        ArrayList<String> s2 = new ArrayList<>();
        s1.add("Mario Lemieux");
        s1.add("Patrick Roy");
        s2.add("Wayne Gretzky");
        TradePublisher.getInstance().notify("Montreal Canadiens", "Winnipeg Jets", s1, s2);
        assertEquals(OutputConstants.TRADES, console.toString().trim());
    }

}
