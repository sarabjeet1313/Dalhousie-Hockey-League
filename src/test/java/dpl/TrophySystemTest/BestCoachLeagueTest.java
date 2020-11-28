package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;

import dpl.LeagueSimulationManagement.TrophySystem.BestCoachLeague;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;

import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
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
        coach.setCoachName("TestCoach");
    }

    @After
    public void after() {
        BestCoachLeague.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        BestCoachLeague.getInstance().setValue("TestKey", "TestValue");
        assertEquals("TestValue", BestCoachLeague.getInstance().getValue("TestKey"));
    }

    @Test
    public void notifyAllObserversTest() {
        BestCoachLeague.getInstance().notifyCoachTraining(coach, 1);
        assertEquals("TestCoach", BestCoachLeague.getInstance().getBestCoach().getCoachName());
    }
}
