package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagementTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.GameplayConfig;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Coach;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Conference;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Division;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.League;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Manager;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Player;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement.Team;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

import org.junit.Assert;

public class LeagueTest {

	League leagueData = new LeagueObjectTestData().getLeagueData();
	Player player1 = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player player2 = new Player("Player2", "Forward", false, 1, 1, 1, 1, 1, false, false, 0);
	Player player3 = new Player("Player3", "defence", false, 1, 1, 1, 1, 1, false, false, 0);
	Player player4 = new Player("Player4", "Goalie", false, 1, 1, 1, 1, 1, false, false, 0);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	List<Player> freeAgents = leagueData.getFreeAgents();
	List<Player> playerList = new ArrayList<Player>();
	List<Coach> coachesList = leagueData.getCoaches();
	List<Division> divisionList = new ArrayList<Division>();
	List<Division> divisionList1 = new ArrayList<Division>();
	List<Conference> conferenceList = leagueData.getConferenceList();
	List<Manager> managerList = leagueData.getManagerList();
	Conference conference = new Conference("Eastern Conference", divisionList);
	Conference conference1 = new Conference("Western Conference", divisionList1);
	GameplayConfig config = null;
	ILeaguePersistance leagueMock = new LeagueMockData();
	League league = new League("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList, config,
			leagueMock);
	ILeaguePersistance object = new LeagueMockData();
	List<League> leagueList = new ArrayList<League>();
	private IUserOutput output = new CmdUserOutput();

	@Test
	public void parameterizedConstructorTest() {
		Assert.assertEquals("Dalhousie Hockey League", leagueData.getLeagueName());
		Assert.assertEquals(1, leagueData.getConferenceList().size());
		Assert.assertEquals(30, leagueData.getFreeAgents().size());
	}

	@Test
	public void getLeagueNameTest() {
		Assert.assertEquals("Dalhousie Hockey League", league.getLeagueName());
	}

	@Test
	public void setLeagueNameTest() {
		league.setLeagueName("Test League");
		Assert.assertEquals("Test League", league.getLeagueName());
	}

	@Test
	public void getConferenceListTest() {
		conferenceList.add(conference);
		League league = new League("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList,
				config);
		Assert.assertEquals(2, league.getConferenceList().size());
	}

	@Test
	public void setConferenceListTest() {
		conferenceList.add(conference);
		League league1 = new League("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList,
				config);
		conferenceList.add(conference1);
		league1.setConferenceList(conferenceList);
		Assert.assertEquals(3, league1.getConferenceList().size());
	}

	@Test
	public void getFreeAgentsTest() {
		freeAgents.add(player1);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(31, leagueData.getFreeAgents().size());
	}

	@Test
	public void setFreeAgentsTest() {
		freeAgents.add(player1);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(31, leagueData.getFreeAgents().size());
		freeAgents.add(player2);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(32, leagueData.getFreeAgents().size());
	}

	@Test
	public void getLeagueNamesTest() {
		Assert.assertEquals("Dalhousie Hockey League", leagueData.getLeagueName());
	}

	@Test
	public void loadLeagueTest() {
		try {
			League fetchedleague = league.loadLeague("Boston");
			Assert.assertEquals(fetchedleague.getLeagueName(), league.getLeagueName());
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void getCoachesTest() {
		coachesList.add(coach1);
		Assert.assertEquals(4, league.getCoaches().size());
	}

	@Test
	public void setCoachesTest() {
		coachesList.add(coach1);
		Assert.assertEquals(4, league.getCoaches().size());
		coachesList.add(coach2);
		league.setCoaches(coachesList);
		Assert.assertEquals(5, league.getCoaches().size());
	}

	@Test
	public void isValidLeagueNameTest() {
		try {
			Assert.assertTrue(league.isValidLeagueName("Test"));
			Assert.assertFalse(league.isValidLeagueName("Dalhousie Hockey League"));
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void createTeamTest() {
		try {
			LeagueObjectTestData leagueData = new LeagueObjectTestData();
			Assert.assertTrue(league.createTeam(leagueData.getLeagueData()));
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void loadLeagueObjectTest() {
		Team team = new Team();
		String leagueName = league.getLeagueName();
		String conferenceName = league.getConferenceList().get(0).getConferenceName();
		String divisionName = league.getConferenceList().get(0).getDivisionList().get(0).getDivisionName();
		int sizeBefore = league.getConferenceList().get(0).getDivisionList().get(0).getTeamList().size();
		League outputLeague = league.loadLeagueObject(leagueName, conferenceName, divisionName, team, league);
		int sizeAfter = outputLeague.getConferenceList().get(0).getDivisionList().get(0).getTeamList().size();
		Assert.assertEquals(sizeBefore + 1, sizeAfter);
	}

	@Test
	public void UpdateLeagueTest() {
		try {
			Assert.assertTrue(league.UpdateLeague(league));
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}
