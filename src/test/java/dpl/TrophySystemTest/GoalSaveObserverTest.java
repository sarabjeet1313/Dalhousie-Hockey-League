package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalSaveObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = GoalSave.getInstance();
        Player player = new Player();
        player.setPlayerName("Alissa");
        player.setSaves(3);
        subject.setValue(TrophySystemConstants.PLAYER.toString(), player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.VEZINA_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals("Alissa", GoalSave.getInstance().getBestGoalSaver().getPlayerName());
    }
}
