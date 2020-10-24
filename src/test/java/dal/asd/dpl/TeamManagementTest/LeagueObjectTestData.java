package dal.asd.dpl.TeamManagementTest;

import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.GameplayConfiguration.Aging;
import dal.asd.dpl.GameplayConfiguration.GameResolver;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;
import dal.asd.dpl.GameplayConfiguration.Injury;
import dal.asd.dpl.GameplayConfiguration.Trading;
import dal.asd.dpl.GameplayConfiguration.Training;
import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conference;
import dal.asd.dpl.TeamManagement.Division;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;

public class LeagueObjectTestData {
	
	Player player1 = new Player("Player One", "forward", true, 1, 1, 1, 1, 1, false);
	Player player2 = new Player("Player Two", "defense", false, 1, 1, 1, 1, 1, false);
	Player player3 = new Player("Player Three", "goalie", false, 1, 1, 1, 1, 1, false);
	Player player4 = new Player("Agent One", "forward", false, 1, 1, 1, 1, 1, false);
	Player player5 = new Player("Agent Two", "defense", false, 1, 1, 1, 1, 1, false);
	Player player6 = new Player("Agent Three", "goalie", false, 1, 1, 1, 1, 1, false);
	Coach coach1 = new Coach("Coach One", 0.1, 0.2, 0.1, 0.1);
	Coach coach2 = new Coach("Coach Two", 0.1, 0.2, 0.1, 0.1);
	Coach coach3 = new Coach("Coach Three", 0.1, 0.2, 0.1, 0.1);
	Coach headCoach = new Coach("Head Coach", 0.1, 0.2, 0.1, 0.1);
	Aging aging = new Aging(35, 50);
	GameResolver gameResolver = new GameResolver(0.1);
	Injury injury = new Injury(0.05, 1, 260);
	Training training = new Training(100);
	Trading trading = new Trading(8, 0.05, 2, 0.05);
	ArrayList<Player> playerList = new ArrayList<Player>();
	ArrayList<Player> freePlayerList = new ArrayList<Player>();
	List<Coach> coachList = new ArrayList<Coach>();
	List<String> managerList = new ArrayList<String>();
	
	
	public League getLeagueData() {
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
		Team team = new Team("Boston", "Mister Fred", headCoach, playerList);
		ArrayList<Team> teamList = new ArrayList<Team>();
		teamList.add(team);
		Division division = new Division("Atlantic", teamList);
		ArrayList<Division> divisionList = new ArrayList<Division>();
		divisionList.add(division);
		Conference conference = new Conference("Eastern Conference", divisionList);
		ArrayList<Conference> conferenceList = new ArrayList<Conference>();
		conferenceList.add(conference);
		GameplayConfig config = new GameplayConfig(aging, gameResolver, injury, training, trading);
		League league = new League("Dalhousie Hockey League", conferenceList, freePlayerList, coachList, managerList, config);
		return league;
	}
	
}
