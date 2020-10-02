package dal.asd.dpl.teammanagement;

import java.util.ArrayList;
import java.util.List;

public class LeageMockData implements ILeague {
	
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
	public Leagues getLeagueData(String teamName) {
		Leagues league1 = null;
		Leagues league = getTestData();
		List<Conferences> conferenceList = league.getConferenceList();
		List<Divisions> divisionList = conferenceList.get(0).getDivisionList();
		List<Teams> teamList = divisionList.get(0).getTeamList();
		for(int index = 0; index < teamList.size(); index++) {
			if(teamList.get(index).getTeamName().equals(teamName)) {
				return league;
			}
			else {
				return null;
			}
		}
		return league1;
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
}
