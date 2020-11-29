package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BestDefenceMenObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = BestDefenceMen.getInstance();
        Player player = new Player();
        player.setPenalties(TrophySystemParameterTestConstants.TEST_PENALTIES.toInteger());
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        subject.setValue(TrophySystemConstants.PLAYER.toString(), player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), BestDefenceMen.getInstance().getBestDefenceMen().getPlayerName());
    }
}
