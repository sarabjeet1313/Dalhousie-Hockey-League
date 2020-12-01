package dpl.LeagueManagementTest.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.ISerialize;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class SerializeLeagueTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	League league = new LeagueObjectTestData().getLeagueData();
	ISerialize serializeLeague = teamManagement.SerializeLeague();
	private IUserOutput output = new CmdUserOutput();
	
	@Test
	public void serializeLeagueModelTest() {
		try {
		assertTrue(serializeLeague.serializeLeagueModel(league));
	} catch (Exception e) {
		output.setOutput(e.getMessage());
		output.sendOutput();
	}
	}

}
