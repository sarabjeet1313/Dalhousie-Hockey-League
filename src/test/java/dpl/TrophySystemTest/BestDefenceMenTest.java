package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.TrophySystem.BestCoachLeague;
import dpl.LeagueSimulationManagement.TrophySystem.BestDefenceMen;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BestDefenceMenTest {
    private IObserver observer;
    private Player player;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.ROB_HAWKEY_MEMORIAL_CUP);
        BestDefenceMen.getInstance().attach(observer);
        player = new Player();
        player.setPlayerName("DefencePlayer");
    }

    @After
    public void after() {
        BestDefenceMen.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        BestDefenceMen.getInstance().setValue("TestKey", "TestValue");
        assertEquals("TestValue", BestDefenceMen.getInstance().getValue("TestKey"));
    }

    @Test
    public void notifyAllObserversTest() {
        BestDefenceMen.getInstance().notifyWhenPlayerGoal(player);
        //assertEquals("DefencePlayer", BestDefenceMen.getInstance().getBestDefenceMen().getPlayerName());
    }
}
