package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TopGoalScorerObserverTest {
    private Subject subject;
    private IObserver observer;
    @Before
    public void before() {
        subject = TopGoalScorer.getInstance();
        Player player=new Player();
        player.setPlayerName("Robin");
        player.setGoals(100);
        subject.setValue("player", player);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.MAURICE_RICHARD_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest(){
        observer.update(subject);
        assertEquals("Robin", TopGoalScorer.getInstance().getTopGoalScorer().getPlayerName());
    }
}
