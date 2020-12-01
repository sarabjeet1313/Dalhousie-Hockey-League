package dpl.LeagueManagementTest.TrophySystemTest;

import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.LeagueManagement.TrophySystem.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamPointObserverTest {
    private Subject subject;
    private IObserver observer;

    @Before
    public void before() {
        subject = TeamPoint.getInstance();
        Team team = new Team();
        team.setTeamName(TrophySystemTestConstants.TEAM_TEST.toString());
        subject.setValue(TrophySystemConstants.TEAM.toString(), team);
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PRESIDENT_TROPHY);
    }

    @After
    public void after() {
        subject = null;
    }

    @Test
    public void updateTest() {
        observer.update(subject);
        assertEquals(TrophySystemTestConstants.TEAM_TEST.toString(), TeamPoint.getInstance().getBestTeam());
    }

}
