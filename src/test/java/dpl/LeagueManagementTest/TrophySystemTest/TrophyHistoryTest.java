package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophyHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TrophyHistoryTest {

    @Before
    public void before() {
        TrophyHistory.getInstance().addTrophy(2022, TrophySystemTestConstants.PRESIDENT_TROPHY.toString(), TrophySystemTestConstants.PLAYER_TEST.toString());
        TrophyHistory.getInstance().addTrophy(2021, TrophySystemTestConstants.CALDER_MEMORIAL_TROPHY.toString(), TrophySystemTestConstants.PLAYER_TEST.toString());
    }

    @After
    public void after() {
        System.setOut(null);
    }

    @Test
    public void addTrophyTest() {
        Map<Integer, List<Map<String, String>>> history = TrophyHistory.getInstance().getTrophyHistory();
        assertEquals(TrophySystemTestConstants.PRESIDENT_TROPHY.toString(), history.get(2022).get(0).keySet().toArray()[0]);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), history.get(2021).get(0).values().toArray()[0]);
        TrophyHistory.getInstance().displayTrophyHistory();
    }
}
