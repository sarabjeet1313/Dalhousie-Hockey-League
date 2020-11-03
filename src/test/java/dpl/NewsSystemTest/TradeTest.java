package dpl.NewsSystemTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dpl.LeagueSimulationManagement.NewsSystem.TradePublisher;

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
        TradePublisher.getInstance().notify("Montreal Canadiens", "Winnipeg Jets", new String[][]{{"Mario Lemieux", "Patrick Roy"}, {"Wayne Gretzky"}});
        assertEquals(OutputConstants.TRADES, console.toString().trim());
    }

}
