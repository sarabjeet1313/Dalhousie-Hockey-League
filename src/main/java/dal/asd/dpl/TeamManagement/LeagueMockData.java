package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class LeagueMockData implements ILeague {
	
	Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1);
	Player player2 = new Player("Player Two", "defense", false, 1, 1, 1, 1, 1);
	Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1);
	Player player4 = new Player("Agent One", "forward", false, 1, 1, 1, 1, 1);
	Player player5 = new Player("Agent Two", "defense", false, 1, 1, 1, 1, 1);
	Player player6 = new Player("Agent Three", "goalie", false, 1, 1, 1, 1, 1);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	List<Player> playerList = new ArrayList<Player>();
	List<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	
	public Leagues getTestData() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		freePlayerList.add(player4);
		freePlayerList.add(player5);
		freePlayerList.add(player6);
		coachList.add(coach1);
		coachList.add(coach2);
		coachList.add(coach3);
		Teams team = new Teams("Boston", "Mister Fred", headCoach, playerList);
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		teamList.add(team);
		Divisions division = new Divisions("Atlantic", teamList);
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		divisionList.add(division);
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		conferenceList.add(conference);
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freePlayerList, coachList);
		return league;
	} 
	
	@Override
	public List<Leagues> getLeagueData(String teamName) {
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
			String generalManager, Coach headCoach, String playerName, String position, boolean captain, int age, int skating, int shooting, int checking, int saving) {
		if(teamName.equals("Empty")) {
			List<Player> playerList = new ArrayList<Player>() ;
			playerList.add(player1);
		}
		
		return true;
	}
	
}
