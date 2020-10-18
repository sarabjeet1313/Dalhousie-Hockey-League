package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import org.junit.Test;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.LeagueObjectTestData;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;
import org.junit.Assert;

public class DivisionsTest {
	
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Player> playerList1 = new ArrayList<Player>();
	Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
	Teams team1 = new Teams("Florida", "Ashely", "Robert", playerList1);
	ArrayList<Teams> teamList = new ArrayList<Teams>();
	Divisions division = new Divisions("Atlantic", teamList);
	
	@Test
	public void parameterizedConstructorTest() {
		teamList.add(team);
		teamList.add(team);
		division.setTeamList(teamList);
		Assert.assertEquals("Atlantic", division.getDivisionName());
		Assert.assertEquals(2, division.getTeamList().size());
	}
	
	@Test
	public void getDivisionNameTest() {
		Assert.assertEquals("Atlantic", division.getDivisionName());
	}
	
	@Test
	public void setDivisionNameTest() {
		division.setDivisionName("Adams");
		Assert.assertEquals("Adams", division.getDivisionName());
	}
	
	@Test
	public void getTeamTest() {
		teamList.add(team);
		Divisions division1 = new Divisions("Atlantic", teamList);
		Assert.assertEquals(1, division1.getTeamList().size());
	}
	
	@Test
	public void setTeamTest() {
		teamList.add(team);
		Divisions division1 = new Divisions("Atlantic", teamList);
		teamList.add(team1);
		division1.setTeamList(teamList);
		Assert.assertEquals(2, division1.getTeamList().size());
	}
	
	@Test
	public void isValidDivisionNameTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		String conferenceName = "Eastern Conference";
		String divisionName = "Atlantic";
		Assert.assertTrue(division.isValidDivisionName(conferenceName, divisionName, leagueData.getLeagueData()));
	}
	
}
