package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeagueMockData implements ILeague {
	
	Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1);
	Player player2 = new Player("Player Two", "defense", false, 1, 1, 1, 1, 1);
	Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1);
	Player player4 = new Player("Agent1", "forward", false, 1, 1, 1, 1, 1);
	Player player5 = new Player("Agent2", "defense", false, 1, 1, 1, 1, 1);
	Player player6 = new Player("Agent3", "defense", false, 1, 1, 1, 1, 1);
	Player agent1 = new Player("Agent4", "forward", true, 1, 1, 1, 1, 1);
	Player agent2 = new Player("Agent5", "goalie", false, 1, 1, 1, 1, 1);
	Player agent3 = new Player("Agent6", "forward", false, 1, 1, 1, 1, 1);
	Player agent4 = new Player("Agent7", "forward", false, 1, 1, 1, 1, 1);
	Player agent5 = new Player("Agent8", "defense", false, 1, 1, 1, 1, 1);
	Player agent6 = new Player("Agent9", "forward", false, 1, 1, 1, 1, 1);
	Player agent7 = new Player("Agent10", "forward", true, 1, 1, 1, 1, 1);
	Player agent8 = new Player("Agent11", "defense", false, 1, 1, 1, 1, 1);
	Player agent9 = new Player("Agent12", "goalie", false, 1, 1, 1, 1, 1);
	Player agent10 = new Player("Agent13", "forward", false, 1, 1, 1, 1, 1);
	Player agent11 = new Player("Agent14", "defense", false, 1, 1, 1, 1, 1);
	Player agent12 = new Player("Agent15", "defense", false, 1, 1, 1, 1, 1);
	Player agent13 = new Player("Agent16", "forward", true, 1, 1, 1, 1, 1);
	Player agent14 = new Player("Agent17", "defense", false, 1, 1, 1, 1, 1);
	Player agent15 = new Player("Agent18", "forward", false, 1, 1, 1, 1, 1);
	Player agent16 = new Player("Agent19", "forward", false, 1, 1, 1, 1, 1);
	Player agent17 = new Player("Agent20", "defense", false, 1, 1, 1, 1, 1);
	Player agent18 = new Player("Agent21", "defense", false, 1, 1, 1, 1, 1);
	Player agent19 = new Player("Agent22", "defense", false, 1, 1, 1, 1, 1);
	Player agent20 = new Player("Agent23", "goalie", false, 1, 1, 1, 1, 1);
	Player agent21 = new Player("Agent24", "forward", true, 1, 1, 1, 1, 1);
	Player agent22 = new Player("Agent25", "defense", false, 1, 1, 1, 1, 1);
	Player agent23 = new Player("Agent26", "goalie", false, 1, 1, 1, 1, 1);
	Player agent24 = new Player("Agent27", "forward", false, 1, 1, 1, 1, 1);
	Player agent25 = new Player("Agent28", "defense", false, 1, 1, 1, 1, 1);
	Player agent26 = new Player("Agent29", "defense", false, 1, 1, 1, 1, 1);
	Player agent27 = new Player("Agent30", "goalie", false, 1, 1, 1, 1, 1);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	List<Player> playerList = new ArrayList<Player>();
	List<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	List<String> managerList = new ArrayList<String>();
	
	public League getTestData() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		List<Player> agentList = Arrays.asList(player4, player5, player6, agent1, agent2, agent3, agent4, agent5,
				agent6, agent7, agent8, agent9, agent10, agent11, agent12, agent13, agent14, agent15, agent16, 
				agent17, agent18, agent19, agent20, agent21, agent22, agent23, agent24, agent25, agent26, agent27);
		freePlayerList.addAll(agentList);
		coachList.add(coach1);
		coachList.add(coach2);
		coachList.add(coach3);
		managerList.add("Karen Potam");
		managerList.add("Joseph Squidly");
		managerList.add("Tom Spaghetti");
		Team team1 = new Team("Boston", "Mister Fred", headCoach, playerList);
		Team team2 = new Team("Halifax", "Mister Fred", headCoach, playerList);
		ArrayList<Team> teamList = new ArrayList<Team>();
		teamList.add(team1);
		teamList.add(team2);
		Division division = new Division("Atlantic", teamList);
		ArrayList<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		Conference conference = new Conference("Eastern Conference", divisionList);
		ArrayList<Conference> conferenceList = new ArrayList<Conference>();
		conferenceList.add(conference);
		League league = new League("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList);
		return league;
	} 
	
	@Override
	public List<League> getLeagueData(String teamName) {
		List<League> leagueList = new ArrayList<League>();
		League league = getTestData();
		List<Conference> conferenceList = league.getConferenceList();
		List<Division> divisionList = conferenceList.get(0).getDivisionList();
		List<Team> teamList = divisionList.get(0).getTeamList();
		for(int index = 0; index < teamList.size(); index++) {
			if(teamList.get(index).getTeamName().equals(teamName)) {
				leagueList.add(league);
				return leagueList;
			}
			else {
				return null;
			}
		}
		return leagueList;
	}
	
	@Override
	public int checkLeagueName(String leagueName) {
		League league = getTestData();
		int rowCount = 0;
		if(league.getLeagueName().equals(leagueName)) {
			rowCount = 1;
		}
		return rowCount;
	}
	
	@Override
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player) {
		if(teamName.equals("Empty")) {
			List<Player> playerList = new ArrayList<Player>() ;
			playerList.add(player1);
		}
		
		return true;
	}

	@Override
	public boolean persisitCoaches(Coach coach, String teamName, String leagueName) {
		boolean isValid = false;
		League league = getTestData();
		for(int index = 0; index < league.getCoaches().size(); index++) {
			if(coach.getCoachName().equals(league.getCoaches().get(index).getCoachName())) {
				isValid = true;
			}
		}
		return isValid;
	}
	
}
