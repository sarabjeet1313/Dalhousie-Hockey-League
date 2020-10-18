package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.LeagueMockData;
import dal.asd.dpl.TeamManagement.LeagueObjectTestData;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Player;

import org.junit.Assert;

public class LeaguesTest {
	
	Player player1 = new Player("Player1", "Forward", false, 1, 1, 1, 1, 1);
	Player player2 = new Player("Player2", "Forward", false, 1, 1, 1, 1, 1);
	Player player3 = new Player("Player3", "defence", false, 1, 1, 1, 1, 1);
	Player player4 = new Player("Player4", "Goalie", false, 1, 1, 1, 1, 1);
	ArrayList<Player> freeAgents = new ArrayList<Player>();
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
	ArrayList<Divisions> divisionList1 = new ArrayList<Divisions>();
	ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
	Conferences conference = new Conferences("Eastern Conference", divisionList);
	Conferences conference1 = new Conferences("Western Conference", divisionList1);
	Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freeAgents);
	ILeague object = new LeagueMockData();
	List<Leagues> leagueList = new ArrayList<Leagues>();
	
	@Test
	public void parameterizedConstructorTest() {
		conferenceList.add(conference);
		conferenceList.add(conference1);
		league.setConferenceList(conferenceList);
		playerList.add(player1);
		playerList.add(player2);
		freeAgents.add(player3);
		freeAgents.add(player4);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals("Dalhousie Hockey League", league.getLeagueName());
		Assert.assertEquals(2, league.getConferenceList().size());
		Assert.assertEquals(2, league.getFreeAgents().size());
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
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freeAgents);
		Assert.assertEquals(1, league.getConferenceList().size());
	}
	
	@Test
	public void setConferenceListTest() {
		conferenceList.add(conference);
		Leagues league1 = new Leagues("Dalhousie Hockey League", conferenceList, freeAgents);
		conferenceList.add(conference1);
		league1.setConferenceList(conferenceList);
		Assert.assertEquals(2, league1.getConferenceList().size());
	}
	
	@Test
	public void getFreeAgentsTest() {
		freeAgents.add(player1);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(1, league.getFreeAgents().size());
	}
	
	@Test
	public void setFreeAgentsTest() {
		freeAgents.add(player1);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(1, league.getFreeAgents().size());
		freeAgents.add(player2);
		league.setFreeAgents(freeAgents);
		Assert.assertEquals(2, league.getFreeAgents().size());
	}
	
	@Test
	public void getLeagueNamesTest() {
		Assert.assertEquals(1,league.getLeagueNames("Boston",object).size());
	}

	@Test
	public void loadLeagueDataTest() {
		Assert.assertFalse(league.loadLeagueData("Dal Hockey League"));
	}
	
	@Test
	public void isValidLeagueNameTest() {
		Assert.assertTrue(league.isValidLeagueName("Dalhousie", object));
	}
	
	@Test
	public void createTeamTest() {
		LeagueObjectTestData leagueData = new LeagueObjectTestData();
		Assert.assertTrue(league.createTeam(leagueData.getLeagueData(), object));
	}
	
}
