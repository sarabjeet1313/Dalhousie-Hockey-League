package dpl.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.SerializeLeague;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

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
