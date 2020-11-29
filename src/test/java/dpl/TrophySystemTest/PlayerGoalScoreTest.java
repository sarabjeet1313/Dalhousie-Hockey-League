package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.PlayerGoalScore;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerGoalScoreTest {
    private IObserver observer;
    private Player player;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.CALDER_MEMORIAL_TROPHY);
        PlayerGoalScore.getInstance().attach(observer);
        player = new Player();
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        player.setSaves(TrophySystemParameterTestConstants.TEST_SAVES.toInteger());
        player.setGoals(TrophySystemParameterTestConstants.TEST_GOALS.toInteger());
    }

    @After
    public void after() {
        PlayerGoalScore.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        PlayerGoalScore.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), PlayerGoalScore.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        PlayerGoalScore.getInstance().notifyWhenPlayerGoal(player);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), PlayerGoalScore.getInstance().getBestPlayer().getPlayerName());
    }
}
