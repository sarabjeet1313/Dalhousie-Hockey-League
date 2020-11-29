package dpl.TrophySystemTest;

import dpl.LeagueSimulationManagement.TrophySystem.TrophyHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TrophyHistoryTest {

    @Before
    public void before() {
        TrophyHistory.getInstance().addTrophy(2019, "President's", "Alex");
        TrophyHistory.getInstance().addTrophy(2020, "Calder Memorial", "Peter");
    }

    @After
    public void after() {
        System.setOut(null);
    }

    @Test
    public void addTrophyTest() {
        Map<Integer, List<Map<String, String>>> history = TrophyHistory.getInstance().getTrophyHistory();
        assertEquals("President's", history.get(2019).get(0).keySet().toArray()[0]);
        assertEquals("Alex", history.get(2019).get(0).values().toArray()[0]);
        TrophyHistory.getInstance().displayTrophyHistory();
    }
}
