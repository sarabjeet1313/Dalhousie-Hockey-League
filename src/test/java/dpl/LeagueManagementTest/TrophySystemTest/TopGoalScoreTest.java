package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.PlayerGoalScore;
import dpl.LeagueManagement.TrophySystem.TopGoalScore;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopGoalScoreTest {
    private IObserver observer;
    private Player player;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.MAURICE_RICHARD_TROPHY);
        TopGoalScore.getInstance().attach(observer);
        player = new Player();
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        player.setGoals(10);
    }

    @After
    public void after() {
        PlayerGoalScore.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        TopGoalScore.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), TopGoalScore.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        TopGoalScore.getInstance().notifyPlayerGoal(player);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), TopGoalScore.getInstance().getTopGoalScore().getPlayerName());
    }
}
