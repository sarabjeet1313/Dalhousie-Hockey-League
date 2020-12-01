package dpl.LeagueManagementTest.TeamManagementTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;
import dpl.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.UserInputOutput.UserOutput.CmdUserOutput;
import dpl.UserInputOutput.UserOutput.IUserOutput;

public class LeagueTest {

	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();
	League leagueData = new LeagueObjectTestData().getLeagueData();
	Player player1 = teamManagement.PlayerWithParameters("Player1", "Forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 11, 11, 1999, Boolean.FALSE);
	Player player2 = teamManagement.PlayerWithParameters("Player2", "Forward", false, 1, 1, 1, 1, 1, false, false, 0, false, 11, 11, 1999, Boolean.FALSE);
	Player player3 = teamManagement.PlayerWithParameters("Player3", "defence", false, 1, 1, 1, 1, 1, false, false, 0, false, 11, 11, 1999, Boolean.FALSE);
	Player player4 = teamManagement.PlayerWithParameters("Player4", "Goalie", false, 1, 1, 1, 1, 1, false, false, 0, false, 11, 11, 1999, Boolean.FALSE);
	Coach coach1 = teamManagement.CoachWithParameters("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = teamManagement.CoachWithParameters("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = teamManagement.CoachWithParameters("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = teamManagement.CoachWithParameters("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	List<Player> freeAgents = leagueData.getFreeAgents();
	List<Player> playerList = new ArrayList<>();
	List<Coach> coachesList = leagueData.getCoaches();
	List<Division> divisionList = new ArrayList<>();
	List<Division> divisionList1 = new ArrayList<>();
	List<Conference> conferenceList = leagueData.getConferenceList();
	List<Manager> managerList = leagueData.getManagerList();
	Conference conference = teamManagement.ConferenceWithParameters("Eastern Conference", divisionList);
	Conference conference1 = teamManagement.ConferenceWithParameters("Western Conference", divisionList1);
	GameplayConfig config = null;
	ILeaguePersistance leagueMock = new LeagueMockData();
	League league = teamManagement.LeagueWithDbParameters("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList, config,
			leagueMock);
	ILeaguePersistance object = new LeagueMockData();
	List<League> leagueList = new ArrayList<>();
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
		League league = teamManagement.LeagueWithParameters("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList,
				config);
		Assert.assertEquals(2, league.getConferenceList().size());
	}

	@Test
	public void setConferenceListTest() {
		conferenceList.add(conference);
		League league1 = teamManagement.LeagueWithParameters("Dalhousie Hockey League", conferenceList, freeAgents, coachesList, managerList,
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
		} catch (Exception e) {
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
		League tempLeague = leagueData;
		tempLeague.setLeagueName("Test");
		try {
			Assert.assertTrue(league.isValidLeagueName(tempLeague));
			Assert.assertTrue(league.isValidLeagueName(leagueData));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void createTeamTest() {
		try {
			LeagueObjectTestData leagueData = new LeagueObjectTestData();
			Assert.assertTrue(league.createTeam(leagueData.getLeagueData()));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	@Test
	public void loadLeagueObjectTest() {
		Team team = teamManagement.Team();
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
			Assert.assertTrue(league.updateLeague(league));
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

}
