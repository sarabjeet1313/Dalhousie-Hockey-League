package dpl.TeamManagementTest;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.AllStarGameManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IAllStarGameManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ICoachPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IPlayerDraft;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRosterManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ISerialize;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.PlayerDraft;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.RetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.RosterManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.SerializeLeague;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.TeamManagementAbstractFactory;

public class TeamManagementAbstractFactoryTest {

	private TeamManagementAbstractFactory factory;
	String coachName = "";
	double skating = 0.0, shooting = 0.0, checking = 0.0, saving = 0.0;
	ICoachPersistance coachDb = null;
	private League leagueToSimulate;

	@Before
	public void setUp() throws Exception {
		factory = new TeamManagementAbstractFactory();
		leagueToSimulate = new LeagueMockData().getTestData();
	}

	@Test
	public void CoachTest() {
		Coach coach = factory.Coach();
		assertTrue(coach instanceof Coach);
	}

	@Test
	public void CoachWithParametersTest() {
		Coach coach = factory.CoachWithParameters(coachName, skating, shooting, checking, saving);
		assertTrue(coach instanceof Coach);
	}

	@Test
	public void CoachWithDbParametersTest() {
		Coach coach = factory.CoachWithDbParameters(coachName, skating, shooting, checking, saving, coachDb);
		assertTrue(coach instanceof Coach);
	}

	@Test
	public void ConferenceTest() {
		Conference conference = factory.Conference();
		assertTrue(conference instanceof Conference);
	}

	@Test
	public void ConferenceWithParametersTest() {
		Conference conference = factory.ConferenceWithParameters("",
				leagueToSimulate.getConferenceList().get(0).getDivisionList());
		assertTrue(conference instanceof Conference);
	}

	@Test
	public void DivisionTest() {
		Division division = factory.Division();
		assertTrue(division instanceof Division);
	}

	@Test
	public void DivisionWithParametersTest() {
		Division division = factory.DivisionWithParameters("",
				leagueToSimulate.getConferenceList().get(0).getDivisionList().get(0).getTeamList());
		assertTrue(division instanceof Division);
	}

	@Test
	public void InjuryManagementTest() {
		IInjuryManagement injuryManagement = factory.InjuryManagement();
		assertTrue(injuryManagement instanceof InjuryManagement);
	}

	@Test
	public void LeagueTest() {
		League league = factory.League();
		assertTrue(league instanceof League);
	}

	@Test
	public void ManagerTest() {
		Manager manager = factory.Manager();
		assertTrue(manager instanceof Manager);
	}

	@Test
	public void ManagerWithParametersTest() {
		Manager manager = factory.ManagerWithParameters("", "");
		assertTrue(manager instanceof Manager);
	}

	@Test
	public void ManagerWithDbParametersTest() {
		Manager manager = factory.ManagerWithDbParameters("", "", null);
		assertTrue(manager instanceof Manager);
	}

	@Test
	public void PlayerTest() {
		Player player = factory.Player();
		assertTrue(player instanceof Player);
	}

	@Test
	public void RetirementManagementTest() {
		IRetirementManagement retirementManagement = factory.RetirementManagement();
		assertTrue(retirementManagement instanceof RetirementManagement);
	}

	@Test
	public void SerializeLeagueTest() {
		ISerialize serializeLeague = factory.SerializeLeague();
		assertTrue(serializeLeague instanceof SerializeLeague);
	}

	@Test
	public void TeamTest() {
		Team team = factory.Team();
		assertTrue(team instanceof Team);
	}

	@Test
	public void RosterManagementTest() {
		IRosterManagement rosterManagement = factory.RosterManagement();
		assertTrue(rosterManagement instanceof RosterManagement);
	}

	@Test
	public void AllStarGameManagementTest() {
		IAllStarGameManagement allStarGameManagement = factory.AllStarGameManagement();
		assertTrue(allStarGameManagement instanceof AllStarGameManagement);
	}

	@Test
	public void PlayerDraftTest() {
		IPlayerDraft playerDraft = factory.PlayerDraft();
		assertTrue(playerDraft instanceof PlayerDraft);
	}

}