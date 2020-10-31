package dal.asd.dpl.NewsSystem;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TradeTest {
    private final ByteArrayOutputStream console = new ByteArrayOutputStream();
   // private final ITradeInfo subscriber = new NewsSubscriber();

    @Before
    public void before() {
        System.setOut(new PrintStream(console));
       // TradePublisher.getInstance().subscribe(subscriber);
    }

    @After
    public void after() {
        System.setOut(null);
        //TradePublisher.getInstance().unsubscribe(subscriber);
    }

    @Test
    public void outputJsonTest() {
        TradePublisher.getInstance().notify("Montreal Canadiens", "Winnipeg Jets", new String[][] {{"Mario Lemieux", "Patrick Roy"}, {"Wayne Gretzky"}});
        //assertEquals(OutputConstants.TRADES, console.toString().trim());
    }

}
