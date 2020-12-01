package dpl.LeagueManagementTest.TeamManagementTest;

import java.util.ArrayList;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;

import org.junit.Assert;

public class DivisionTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	ArrayList<Player> playerList = new ArrayList<>();
	ArrayList<Player> playerList1 = new ArrayList<>();
	Coach headCoach1 = teamManagement.CoachWithParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	Coach headCoach2 = teamManagement.CoachWithParameters("Robert", 0.2, 0.3, 0.1, 0.4);
	Manager manager1 = teamManagement.ManagerWithParameters("Karen Potam", "normal");
	Manager manager2 = teamManagement.ManagerWithParameters("Joseph Squidly", "normal");
	Team team = teamManagement.TeamWithParameters("Boston", manager1, headCoach1, playerList, Boolean.FALSE);
	Team team1 = teamManagement.TeamWithParameters("Florida", manager2, headCoach2, playerList1, Boolean.FALSE);
	ArrayList<Team> teamList = new ArrayList<>();
	Division division = teamManagement.DivisionWithParameters("Atlantic", teamList);
	
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
		Division division1 = teamManagement.DivisionWithParameters("Atlantic", teamList);
		Assert.assertEquals(1, division1.getTeamList().size());
	}
	
	@Test
	public void setTeamTest() {
		teamList.add(team);
		Division division1 = teamManagement.DivisionWithParameters("Atlantic", teamList);
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
