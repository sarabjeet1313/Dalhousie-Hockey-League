package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TrophySystem.BestDefenceMen;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.Subject;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParticipantAwardObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = BestDefenceMen.getInstance();
        Player player = new Player();
        player.setPenalties(34);
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        subject.setValue(TrophySystemTestConstants.PLAYER.toString(), player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), BestDefenceMen.getInstance().getBestDefenceMen().getPlayerName());
    }
}
