package dpl.LeagueManagementTest.TrophySystemTest;


import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TrophySystem.GoalSave;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalSaveTest {
    private IObserver observer;
    private Player player;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.VEZINA_TROPHY);
        GoalSave.getInstance().attach(observer);
        player = new Player();
        player.setPlayerName(TrophySystemTestConstants.PLAYER_TEST.toString());
        player.setSaves(TrophySystemParameterTestConstants.TEST_SAVES.toInteger());
    }

    @After
    public void after() {
        GoalSave.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        GoalSave.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), GoalSave.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        GoalSave.getInstance().notifyPlayerSaveGoal(player);
        assertEquals(TrophySystemTestConstants.PLAYER_TEST.toString(), GoalSave.getInstance().getBestGoalSaver().getPlayerName());
    }
}
