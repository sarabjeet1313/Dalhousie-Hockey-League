package dpl.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ISerialize;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
