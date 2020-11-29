package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BestCoachLeagueObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = BestCoachLeague.getInstance();
        Coach coach = new Coach();
        coach.setCoachName("Adem");
        subject.setValue(TrophySystemConstants.STAT_PLAYER.toString(), 10);
        subject.setValue(TrophySystemConstants.COACH.toString(), coach);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.JACK_ADAMS_AWARD);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals("Adem", BestCoachLeague.getInstance().getBestCoach().getCoachName());
    }
}
