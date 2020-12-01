package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TrophySystem.*;
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
        coach.setCoachName(TrophySystemTestConstants.COACH_TEST.toString());
        subject.setValue(TrophySystemTestConstants.STAT_PLAYER.toString(), TrophySystemParameterTestConstants.TEST_COACH_COUNT.toInteger());
        subject.setValue(TrophySystemTestConstants.COACH.toString(), coach);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.JACK_ADAMS_AWARD);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals(TrophySystemTestConstants.COACH_TEST.toString(), BestCoachLeague.getInstance().getBestCoach().getCoachName());
    }
}
