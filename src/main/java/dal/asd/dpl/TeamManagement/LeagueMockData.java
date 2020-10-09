package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class LeagueMockData implements ILeague {
	
	Players player1 = new Players("Player One", "forward", true);
	Players player2 = new Players("Player Two", "defense", false);
	Players player3 = new Players("Player Three", "goalie", false);
	Players player4 = new Players("Agent One", "forward", false);
	Players player5 = new Players("Agent Two", "defense", false);
	Players player6 = new Players("Agent Three", "goalie", false);
	ArrayList<Players> playerList = new ArrayList<Players>();
	ArrayList<Players> freePlayerList = new ArrayList<Players>();
	
	public Leagues getTestData() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		freePlayerList.add(player4);
		freePlayerList.add(player5);
		freePlayerList.add(player6);
		Teams team = new Teams("Boston", "Mister Fred", "Mary Smith", playerList);
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		teamList.add(team);
		Divisions division = new Divisions("Atlantic", teamList);
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		divisionList.add(division);
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		conferenceList.add(conference);
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freePlayerList);
		return league;
	} 
	
	@Override
	public List<Leagues> getLeagueData(String teamName) {
		Leagues league1 = null;
		List<Leagues> leagueList = new ArrayList<Leagues>();
		Leagues league = getTestData();
		List<Conferences> conferenceList = league.getConferenceList();
		List<Divisions> divisionList = conferenceList.get(0).getDivisionList();
		List<Teams> teamList = divisionList.get(0).getTeamList();
		for(int index = 0; index < teamList.size(); index++) {
			if(teamList.get(index).getTeamName().equals(teamName)) {
				leagueList.add(league);
			}
			else {
				return null;
			}
		}
		return leagueList;
	}
	
	@Override
	public int checkLeagueName(String leagueName) {
		Leagues league = getTestData();
		int rowCount = 0;
		if(league.getLeagueName().equals(leagueName)) {
			rowCount = 1;
		}
		return rowCount;
	}
	
	@Override
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, String playerName, String position, boolean captain) {
		if(teamName.equals("Empty")) {
			Players player = new Players(playerName, position, captain);
			List<Players> playerList = new ArrayList<Players>() ;
			playerList.add(player1);
		}
		
		return true;
	}
	
}
