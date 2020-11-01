package dal.asd.dpl.TeamManagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.SerializeLeague;

public class SerializeLeagueTest {

	League league = new LeagueObjectTestData().getLeagueData();
	SerializeLeague serializeLeague = new SerializeLeague();
	
	@Test
	public void serializeLeagueModelTest() {
		assertTrue(serializeLeague.serializeLeagueModel(league));
	}
	
//	@Test
//	public void deSerializeLeagueModelTest() {
//		String filename = "test.json";
//		League leagueTest = serializeLeague.deSerializeLeagueModel(filename);
//		assertEquals(league.getLeagueName(),leagueTest.getLeagueName());
//	}
}
