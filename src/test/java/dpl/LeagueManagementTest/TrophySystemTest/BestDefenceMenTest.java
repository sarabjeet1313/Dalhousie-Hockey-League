package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TrophySystem.BestDefenceMen;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BestDefenceMenTest {
    private IObserver observer;
    private Player player;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP);
        BestDefenceMen.getInstance().attach(observer);
        player = new Player();
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
    }

    @After
    public void after() {
        BestDefenceMen.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        BestDefenceMen.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), BestDefenceMen.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        BestDefenceMen.getInstance().notifyWhenPlayerGoal(player);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), BestDefenceMen.getInstance().getBestDefenceMen().getPlayerName());
    }
}
