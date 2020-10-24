package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;

public class League {
	
	private String leagueName;
	private List<Conference> conferenceList;
	private List<Player> freeAgents;
	private List<Coach> coaches;
	private List<String> generalManager;
	private static List<League> leagueList;
	private GameplayConfig gameConfig;

	public League(String leagueName, List<Conference> conferenceList, List<Player> freeAgents, List<Coach> coaches,
			List<String> generalManager, GameplayConfig gameConfig) {
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
		this.freeAgents = freeAgents;
		this.coaches = coaches;
		this.generalManager = generalManager;
		this.gameConfig = gameConfig;
	}
	
	public GameplayConfig getGameConfig() {
		return gameConfig;
	}

	public void setGameConfig(GameplayConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	public String getLeagueName() {
		return leagueName;
	}

	public void setLeagueName(String leagueName) {
		this.leagueName = leagueName;
	}

	public List<Conference> getConferenceList() {
		return conferenceList;
	}

	public void setConferenceList(List<Conference> conferenceList) {
		this.conferenceList = conferenceList;
	}

	public List<Player> getFreeAgents() {
		return freeAgents;
	}

	public void setFreeAgents(List<Player> freeAgents) {
		this.freeAgents = freeAgents;
	}
	
	public List<Coach> getCoaches() {
		return coaches;
	}

	public void setCoaches(List<Coach> coaches) {
		this.coaches = coaches;
	}
	
	public List<String> getGeneralManager() {
		return generalManager;
	}

	public void setGeneralManager(List<String> generalManager) {
		this.generalManager = generalManager;
	}

	public List<String> getLeagueNames(String teamName, ILeague object){
		List<String> leagueNames = new ArrayList<String>();
		try {
			leagueList = object.getLeagueData(teamName);
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}

		for(int index = 0; index < leagueList.size(); index++) {
			leagueNames.add(leagueList.get(index).getLeagueName());
		}
		return leagueNames;
	}
	
	public boolean loadLeagueData(String leagueName) {
		boolean isValidTeam = false;
		for(int index = 0; index < leagueList.size(); index++) {
			if(leagueName.equals(leagueList.get(index).getLeagueName())) {
				isValidTeam = true;
			}
		}
		return isValidTeam;
	}
	
	public boolean isValidLeagueName(String leagueName, ILeague object) {
		int rowCount = 0;
		boolean isValid = true;
		try {
			rowCount = object.checkLeagueName(leagueName);
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}
		if(rowCount > 0) {
			isValid = false;
		}
		return isValid;
	}
	
	public boolean createTeam(League league, ILeague object) {
		boolean isCreated = false, captain = false;
		String leagueName = league.getLeagueName();
		String conferenceName = "Empty", divisionName = "Empty", teamName = "Empty", generalManager = "Empty",
				playerName = "Empty", position = "Empty", coachName = "Empty";
		int age = 0, skating = 0, shooting = 0, checking = 0, saving = 0;
		Coach headCoach;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList;
		List<Division> divisionList;
		List<Player> playerList = new ArrayList<Player>();
		try {

			for(int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
				conferenceName = conferenceList.get(cIndex).getConferenceName();
				divisionList = conferenceList.get(cIndex).getDivisionList();

				for(int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					divisionName = divisionList.get(dIndex).getDivisionName();
					teamList = divisionList.get(dIndex).getTeamList();

					for(int tIndex = 0; tIndex < teamList.size(); tIndex++) {
						teamName = teamList.get(tIndex).getTeamName();
						generalManager = teamList.get(tIndex).getGeneralManager();
						headCoach = teamList.get(tIndex).getHeadCoach();
						isCreated = object.persisitCoaches(headCoach, teamName, leagueName);
						playerList = teamList.get(tIndex).getPlayerList();
						for(int pIndex = 0; pIndex < playerList.size(); pIndex++) {
							isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName, teamName, 
									generalManager, headCoach.getCoachName(), playerList.get(pIndex));
						}
					}
				}
			}
			conferenceName = divisionName = teamName = generalManager = coachName  = "Empty";
			playerList.clear();
			playerList = league.getFreeAgents();
			if(!playerList.isEmpty()) {
				for(int index = 0; index < playerList.size(); index++) {
					isCreated = object.persisitLeagueData(leagueName, conferenceName, divisionName,
							teamName, generalManager, coachName, playerList.get(index));
					}
			}
		}catch (Exception e) {
			System.out.println("error "+ e.getMessage());
		}

		return isCreated;
	}
	
	public boolean saveCoaches(League league, ILeague object) {
		List<Coach> coachList = league.getCoaches();
		String leagueName = league.getLeagueName();
		boolean isValid = false, flag =true;
		String teamName = "Empty";
		for(int index = 0; index < coachList.size(); index++) {
			Coach coach = coachList.get(index);
			isValid = object.persisitCoaches(coach, teamName, leagueName);
			if(isValid == false) {
				flag = false;
			}
		}
		return (isValid && flag);
	} 

}
