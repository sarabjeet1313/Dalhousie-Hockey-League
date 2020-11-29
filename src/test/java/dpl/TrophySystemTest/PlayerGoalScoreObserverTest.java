package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerGoalScoreObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = PlayerGoalScore.getInstance();
        Player player = new Player();
        player.setPlayerName("Alexa");
        subject.setValue(TrophySystemConstants.PLAYER.toString(), player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.CALDER_MEMORIAL_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        //assertEquals("Alexa", PlayerGoalScore.getInstance().getBestPlayer().getPlayerName());
    }
}
