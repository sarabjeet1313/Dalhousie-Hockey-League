package dpl.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.Test;

import dpl.TeamManagement.League;
import dpl.TeamManagement.SerializeLeague;
import dpl.UserOutput.CmdUserOutput;
import dpl.UserOutput.IUserOutput;

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
