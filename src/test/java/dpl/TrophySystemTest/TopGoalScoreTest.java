package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.PlayerGoalScore;
import dpl.LeagueSimulationManagement.TrophySystem.TopGoalScore;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
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
        player.setPlayerName("PlayerTest");
        player.setGoals(10);
    }

    @After
    public void after() {
        PlayerGoalScore.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        TopGoalScore.getInstance().setValue("TestKey", "TestValue");
        assertEquals("TestValue", TopGoalScore.getInstance().getValue("TestKey"));
    }

    @Test
    public void notifyAllObserversTest() {
        TopGoalScore.getInstance().notifyPlayerGoal(player);
        assertEquals("PlayerTest", TopGoalScore.getInstance().getTopGoalScore().getPlayerName());
    }
}
