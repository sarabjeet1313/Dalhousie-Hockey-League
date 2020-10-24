package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeagueMockData implements ILeague, ITeamPlayersInfo {
	
	private Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false);
	private Player player2 = new Player("Player Two", "defense", false, 1, 1, 1, 1, 1, false);
	private Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1, false);
	private Player player4 = new Player("Agent1", "forward", false, 1, 1, 1, 1, 1, false);
	private Player player5 = new Player("Agent2", "defense", false, 1, 1, 1, 1, 1, false);
	private Player player6 = new Player("Agent3", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent1 = new Player("Agent4", "forward", true, 1, 1, 1, 1, 1, false);
	Player agent2 = new Player("Agent5", "goalie", false, 1, 1, 1, 1, 1, false);
	Player agent3 = new Player("Agent6", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent4 = new Player("Agent7", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent5 = new Player("Agent8", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent6 = new Player("Agent9", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent7 = new Player("Agent10", "forward", true, 1, 1, 1, 1, 1, false);
	Player agent8 = new Player("Agent11", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent9 = new Player("Agent12", "goalie", false, 1, 1, 1, 1, 1, false);
	Player agent10 = new Player("Agent13", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent11 = new Player("Agent14", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent12 = new Player("Agent15", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent13 = new Player("Agent16", "forward", true, 1, 1, 1, 1, 1, false);
	Player agent14 = new Player("Agent17", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent15 = new Player("Agent18", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent16 = new Player("Agent19", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent17 = new Player("Agent20", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent18 = new Player("Agent21", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent19 = new Player("Agent22", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent20 = new Player("Agent23", "goalie", false, 1, 1, 1, 1, 1, false);
	Player agent21 = new Player("Agent24", "forward", true, 1, 1, 1, 1, 1, false);
	Player agent22 = new Player("Agent25", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent23 = new Player("Agent26", "goalie", false, 1, 1, 1, 1, 1, false);
	Player agent24 = new Player("Agent27", "forward", false, 1, 1, 1, 1, 1, false);
	Player agent25 = new Player("Agent28", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent26 = new Player("Agent29", "defense", false, 1, 1, 1, 1, 1, false);
	Player agent27 = new Player("Agent30", "goalie", false, 1, 1, 1, 1, 1, false);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Mary Smith", 0.2, 0.3, 0.1, 0.4);
	List<Player> playerList = new ArrayList<Player>();
	List<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	List<String> managerList = new ArrayList<String>();
	
	public Leagues getTestData() {
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
		Teams team = new Teams("Boston", "Mister Fred", headCoach, playerList);
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		teamList.add(team);
		Divisions division = new Divisions("Atlantic", teamList);
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		divisionList.add(division);
		Conferences conference = new Conferences("Eastern Conference", divisionList);
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		conferenceList.add(conference);
		Leagues league = new Leagues("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList);
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
		Leagues league = getTestData();
		for(int index = 0; index < league.getCoaches().size(); index++) {
			if(coach.getCoachName().equals(league.getCoaches().get(index).getCoachName())) {
				isValid = true;
			}
		}
		return isValid;
	}

	@Override
	public List<Player> getPlayersByTeam(String teamName, Leagues league) {
		
		List<Player> playerList=new ArrayList<Player>();
		playerList.add(this.player1);
		playerList.add(this.player2);
		playerList.add(this.player3);

		return playerList;
	}
	
}
