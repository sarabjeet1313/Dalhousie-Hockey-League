package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.BestDefenceMen;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.Subject;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParicipantsAwardObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = BestDefenceMen.getInstance();
        Player player = new Player();
        player.setPenalties(34);
        player.setPlayerName("Alex");
        subject.setValue("player", player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals("Alex", BestDefenceMen.getInstance().getBestDefenceMen().getPlayerName());
    }
}
