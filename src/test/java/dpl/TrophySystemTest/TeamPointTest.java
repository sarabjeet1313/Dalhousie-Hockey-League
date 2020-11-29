package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.TeamPoint;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
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
        team.setTeamName("TestTeam");
        team2 = new Team();
        team2.setTeamName("TestTeam2");
    }

    @After
    public void after() {
        TeamPoint.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        TeamPoint.getInstance().setValue("TestKey", "TestValue");
        assertEquals("TestValue", TeamPoint.getInstance().getValue("TestKey"));
    }

    @Test
    public void notifyAllObserversTest() {
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team);
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team2);
        TeamPoint.getInstance().notifyTeamWinsTheMatch(team2);
        assertEquals("TestTeam2", TeamPoint.getInstance().getBestTeam());
    }
}
