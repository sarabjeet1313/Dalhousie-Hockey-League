package dal.asd.dpl.teammanagement;

import java.util.ArrayList;

import org.junit.Test;

import dal.asd.dpl.database.LeagueDataDB;

import org.junit.Assert;

public class LeaguesTest {
	Players player1 = new Players("Player1", "Forword", false);
	Players player2 = new Players("Player2", "Forword", false);
	Players player3 = new Players("Player3", "defence", false);
	Players player4 = new Players("Player4", "Goalie", false);
	ArrayList<Players> freeAgents = new ArrayList<Players>();
	ArrayList<Players> playerList = new ArrayList<Players>();
	ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
	ArrayList<Divisions> divisionList1 = new ArrayList<Divisions>();
	ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
	Conferences conference = new Conferences("Eastern Conference", divisionList);
	Conferences conference1 = new Conferences("Western Conference", divisionList1);
	Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freeAgents);
	ILeague object = new LeagueMockData();
	ILeague obj = new LeagueDataDB();
	
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
	public void getTeamDataTest() {
		Assert.assertTrue(league.getTeamData("Boston", object));
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
