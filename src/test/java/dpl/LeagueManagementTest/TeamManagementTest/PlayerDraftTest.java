package dpl.LeagueManagementTest.TeamManagementTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dpl.SystemConfig;
import dpl.LeagueManagement.TeamManagement.IPlayerDraft;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Player;
import dpl.LeagueManagement.TeamManagement.Team;

public class PlayerDraftTest {
	private List<String> teamNameList;
	private List<Team> teamList;
	private List<Player> playerList;
	private League leagueToSimulate;
	private IPlayerDraft playerDraft;
	private ITeamManagementAbstractFactory teamManagement;
	
	@Before
	public void setUp() throws Exception {
		leagueToSimulate = new LeagueMockData().getTestData();
		teamNameList = new ArrayList<>();
		teamNameList.add("Bostan");
		teamNameList.add("Halifax");
		teamList = new ArrayList<>();
		playerList = new ArrayList<>();
		teamManagement = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		playerDraft = teamManagement.PlayerDraft();
	}
	
	@Test
	public void generateDraftingTeamsTest() {
		teamList = playerDraft.generateDraftingTeams(teamNameList, leagueToSimulate);
		assertEquals(teamNameList.size(), teamList.size());
	}
	
	@Test
	public void generateDraftingPlayersTest() {
		playerList = playerDraft.generateDraftingPlayers(teamNameList.size());
		assertEquals(teamNameList.size() * 7, playerList.size());
	}
	
	@Test
	public void startRoundDraftTest() {
		int intitialSize = 0;
		teamList = playerDraft.generateDraftingTeams(teamNameList, leagueToSimulate);
		intitialSize = teamList.get(0).getPlayerList().size();
		playerList = playerDraft.generateDraftingPlayers(teamNameList.size());
		List<Team> tempList = playerDraft.startRoundDraft(teamList, playerList, leagueToSimulate);
		assertTrue(intitialSize < tempList.get(0).getPlayerList().size());
	}
	
	@Test
	public void postDraftingTest() {
		teamList = playerDraft.generateDraftingTeams(teamNameList, leagueToSimulate);
		League tempLeague = playerDraft.postDrafting(teamList,leagueToSimulate);
		assertTrue(tempLeague instanceof League);
	}
	
}
