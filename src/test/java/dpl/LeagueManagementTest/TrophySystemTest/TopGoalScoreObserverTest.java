package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopGoalScoreObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = TopGoalScore.getInstance();
        Player player = new Player();
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        player.setGoals(TrophySystemParameterTestConstants.TEST_GOALS.toInteger());
        subject.setValue(TrophySystemConstants.PLAYER.toString(), player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.MAURICE_RICHARD_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), TopGoalScore.getInstance().getTopGoalScore().getPlayerName());
    }
}
