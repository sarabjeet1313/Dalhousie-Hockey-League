package dpl.TrophySystemTest;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.NewsSystem.GamePlayedPublisher;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.TeamPoints;
import dpl.LeagueSimulationManagement.TrophySystem.TeamPointsObserver;
//import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
//import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import dpl.NewsSystemTest.OutputConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TeamPointsTest {
    private IObserver observer;
    private Team team;
    @Before
//    public void before() {
//        observer = TrophySystemAbstractFactory.createObserver(TrophySystemAbstractFactory.PRESIDENTS_TROPHY);
//        TeamPoints.getInstance().attach(observer);
//        team = new Team();
//        team.setTeamName("TestTeam");
//    }

    @After
    public void after() {
        TeamPoints.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        TeamPoints.getInstance().setValue("TestKey", "TestValue");
        assertEquals("TestValue", TeamPoints.getInstance().getValue("TestKey"));
    }

//    @Test
//    public void notifyAllObserversTest() {
//        TeamPoints.getInstance().notifyWhenATeamWinsTheMatch(team);
//        assertEquals("TestTeam", TeamPoints.getInstance().getBestTeam());
//    }
}
