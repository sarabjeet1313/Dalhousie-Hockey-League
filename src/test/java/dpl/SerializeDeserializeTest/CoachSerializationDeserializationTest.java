package dpl.SerializeDeserializeTest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SerializeDeserialize.ISerializeDeserializeAbstractFactory;
import dpl.TeamManagementTest.CoachMockData;
import dpl.TeamManagementTest.LeagueMockData;

public class CoachSerializationDeserializationTest {

	private ISerializeDeserializeAbstractFactory facroty;
	private ITeamManagementAbstractFactory teamManagement;
	private League leagueToSimulate;
	private IUserOutput output;
	private Coach coach;
	private ICoachPersistance coachMock;
	private final String TEAM_NAME = "Boston";

	@Before
	public void setUp() throws Exception {
		coachMock = new CoachMockData();
		coach = teamManagement.CoachWithDbParameters("Coach1", 0.1, 0.1, 0.1, 0.1, coachMock);
		leagueToSimulate = new LeagueMockData().getTestData();
		teamManagement = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		output = SystemConfig.getSingleInstance().getUserOutputAbstractFactory().CmdUserOutput();
	}
	
}
