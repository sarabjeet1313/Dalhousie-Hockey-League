package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;

public class LeagueObjectTestData {
	
	Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1);
	Player player2 = new Player("Player Two", "defense", false, 1, 1, 1, 1, 1);
	Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1);
	Player player4 = new Player("Agent One", "forward", false, 1, 1, 1, 1, 1);
	Player player5 = new Player("Agent Two", "defense", false, 1, 1, 1, 1, 1);
	Player player6 = new Player("Agent Three", "goalie", false, 1, 1, 1, 1, 1);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Head Coach", 0.1, 0.2, 0.1, 0.1);
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	List<String> managerList = new ArrayList<String>();
	
	
	public Leagues getLeagueData() {
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		freePlayerList.add(player4);
		freePlayerList.add(player5);
		freePlayerList.add(player6);
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
	
}
