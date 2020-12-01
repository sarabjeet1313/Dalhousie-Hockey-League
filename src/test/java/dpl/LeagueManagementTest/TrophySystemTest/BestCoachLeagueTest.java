package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TrophySystem.BestCoachLeague;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BestCoachLeagueTest {
    private IObserver observer;
    private Coach coach;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.JACK_ADAMS_AWARD);
        BestCoachLeague.getInstance().attach(observer);
        coach = new Coach();
        coach.setCoachName(TrophySystemTestConstants.COACH_TEST.toString());
    }

    @After
    public void after() {
        BestCoachLeague.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        BestCoachLeague.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), BestCoachLeague.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        BestCoachLeague.getInstance().notifyCoachTraining(coach, 1);
        assertEquals(TrophySystemTestConstants.COACH_TEST.toString(), BestCoachLeague.getInstance().getBestCoach().getCoachName());
    }
}
