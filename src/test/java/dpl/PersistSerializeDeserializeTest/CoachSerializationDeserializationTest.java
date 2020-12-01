package dpl.PersistSerializeDeserializeTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.PersistSerializeDeserialize.ISerializeDeserializeAbstractFactory;
import dpl.LeagueManagementTest.TeamManagementTest.CoachMockData;
import dpl.LeagueManagementTest.TeamManagementTest.LeagueMockData;

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
