package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamPointsObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = TeamPoints.getInstance();
        Team  team = new Team();
        team.setTeamName("TestTeam");
        subject.setValue("team", team);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PRESIDENT_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest(){
            observer.update(subject);
        assertEquals("TestTeam", TeamPoints.getInstance().getBestTeam());
    }

}
