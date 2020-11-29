package dpl.TrophySystemTest;

import dpl.DplConstants.TrophySystemConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.TrophySystem.IObserver;
import dpl.LeagueSimulationManagement.TrophySystem.ParticipantAward;
import dpl.LeagueSimulationManagement.TrophySystem.TrophySystemAbstractFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParticipantAwardTest {
    private IObserver observer;
    private Team team;
    private Team team1;

    @Before
    public void before() {
        observer = TrophySystemAbstractFactory.createObserver(TrophySystemConstants.PARTICIPATION_AWARD);
        ParticipantAward.getInstance().attach(observer);
        team = new Team();
        team.setTeamName(TrophySystemTestConstants.TEAM_TEST.toString());
        team1 = new Team();
        team1.setTeamName(TrophySystemTestConstants.TEAM1_TEST.toString());

    }

    @After
    public void after() {
        ParticipantAward.getInstance().detach(observer);
    }

    @Test
    public void setAndGetValueTest() {
        ParticipantAward.getInstance().setValue(TrophySystemTestConstants.TEST_KEY.toString(), TrophySystemTestConstants.TEST_VALUE.toString());
        assertEquals(TrophySystemTestConstants.TEST_VALUE.toString(), ParticipantAward.getInstance().getValue(TrophySystemTestConstants.TEST_KEY.toString()));
    }

    @Test
    public void notifyAllObserversTest() {
        ParticipantAward.getInstance().notifyParticipatedTeam(team);
        ParticipantAward.getInstance().notifyParticipatedTeam(team1);
        ParticipantAward.getInstance().notifyParticipatedTeam(team1);
        assertEquals(TrophySystemTestConstants.TEAM_TEST.toString(), ParticipantAward.getInstance().getTeamWithLowestPoints());
    }
}
