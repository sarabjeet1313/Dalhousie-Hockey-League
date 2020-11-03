package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import java.util.ArrayList;
import org.junit.Test;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Coach;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Division;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Manager;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Team;

import org.junit.Assert;

public class DivisionTest {
	
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Player> playerList1 = new ArrayList<Player>();
	Coach headCoach1 = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	Coach headCoach2 = new Coach("Robert", 0.2, 0.3, 0.1, 0.4);
	Manager manager1 = new Manager("Karen Potam");
	Manager manager2 = new Manager("Joseph Squidly");
	Team team = new Team("Boston", manager1, headCoach1, playerList, Boolean.FALSE);
	Team team1 = new Team("Florida", manager2, headCoach2, playerList1, Boolean.FALSE);
	ArrayList<Team> teamList = new ArrayList<Team>();
	Division division = new Division("Atlantic", teamList);
	
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
		Division division1 = new Division("Atlantic", teamList);
		Assert.assertEquals(1, division1.getTeamList().size());
	}
	
	@Test
	public void setTeamTest() {
		teamList.add(team);
		Division division1 = new Division("Atlantic", teamList);
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
