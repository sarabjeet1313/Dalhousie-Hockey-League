package dpl.TeamManagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dpl.TeamManagement.League;
import dpl.TeamManagement.SerializeLeague;

public class SerializeLeagueTest {

	League league = new LeagueObjectTestData().getLeagueData();
	SerializeLeague serializeLeague = new SerializeLeague();
	
	@Test
	public void serializeLeagueModelTest() {
		assertTrue(serializeLeague.serializeLeagueModel(league));
	}

}
