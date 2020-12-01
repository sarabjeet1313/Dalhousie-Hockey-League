package dpl.LeagueManagementTest.TrophySystemTest;


import dpl.LeagueManagement.TeamManagement.Team;
import dpl.LeagueManagement.TrophySystem.IObserver;
import dpl.LeagueManagement.TrophySystem.TeamPoint;
import dpl.LeagueManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.LeagueManagement.TrophySystem.TrophySystemConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamPointTest {
    private IObserver observer;
    private Team team;
    private Team team2;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PRESIDENT_TROPHY);
        TeamPoint.getInstance().attach(observer);
        team = new Team();
        team.setTeamName(TrophySystemTestConstants.TEAM_TEST.toString());
        team2 = new Team();
        team2.setTeamName(TrophySystemTestConstants.TEAM_TEST1.toString());
    }

    @After
    public void after() {
        TeamPoint.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        TeamPoint.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), TeamPoint.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team);
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team2);
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team2);
        assertEquals(TrophySystemTestConstants.TEAM_TEST.toString(), TeamPoint.getInstance().getBestTeam());
    }
}
