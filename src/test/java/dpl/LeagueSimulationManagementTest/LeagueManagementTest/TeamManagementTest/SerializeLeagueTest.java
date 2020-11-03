package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.SerializeLeague;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

public class SerializeLeagueTest {

	League league = new LeagueObjectTestData().getLeagueData();
	SerializeLeague serializeLeague = new SerializeLeague();
	private IUserOutput output = new CmdUserOutput();
	
	@Test
	public void serializeLeagueModelTest() {
		try {
		assertTrue(serializeLeague.serializeLeagueModel(league));
	} catch (IOException e) {
		output.setOutput(e.getMessage());
		output.sendOutput();
	}
	}

}
