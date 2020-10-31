package dal.asd.dpl.TeamManagement;

import java.util.ArrayList;
import java.util.List;
import dal.asd.dpl.GameplayConfiguration.GameplayConfig;

public class League {

	private String leagueName;
	private List<Conference> conferenceList;
	private List<Player> freeAgents;
	private List<Coach> coaches;
	private List<Manager> managerList;
	private static List<League> leagueList;
	private GameplayConfig gameConfig;
	private ILeaguePersistance leagueDb;

	public League(String leagueName, List<Conference> conferenceList, List<Player> freeAgents, List<Coach> coaches,
			List<Manager> managerList, GameplayConfig gameConfig, ILeaguePersistance leagueDb) {
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
		this.freeAgents = freeAgents;
		this.coaches = coaches;
		this.managerList = managerList;
		this.gameConfig = gameConfig;
		this.leagueDb = leagueDb;
	}
	
	public League(String leagueName, List<Conference> conferenceList, List<Player> freeAgents, List<Coach> coaches,
			List<Manager> managerList, GameplayConfig gameConfig) {
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
		this.freeAgents = freeAgents;
		this.coaches = coaches;
		this.managerList = managerList;
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

	public List<Manager> getManagerList() {
		return managerList;
	}

	public void setManagerList(List<Manager> managerList) {
		this.managerList = managerList;
	}

	public static List<League> getLeagueList() {
		return leagueList;
	}

	public static void setLeagueList(List<League> leagueList) {
		League.leagueList = leagueList;
	}

	public GameplayConfig getGameConfig() {
		return gameConfig;
	}

	public void setGameConfig(GameplayConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	public League loadLeague(String teamName) {
		League league = null;
		try {
			league = leagueDb.loadLeagueData(teamName);
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
		return league;
	}

	public boolean isValidLeagueName(String leagueName, ILeaguePersistance object) {
		int rowCount = 0;
		boolean isValid = true;
		try {
			rowCount = object.checkLeagueName(leagueName);
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}
		if (rowCount > 0) {
			isValid = false;
		}
		return isValid;
	}
	
	public boolean createTeam(League league) {
		boolean isCreated = false, captain = false;
		String leagueName = league.getLeagueName();
		String conferenceName = "Empty", divisionName = "Empty", teamName = "Empty", 
				layerName = "Empty", position = "Empty", coachName = "Empty", managerName = "Empty";
		int age = 0, skating = 0, shooting = 0, checking = 0, saving = 0;
		Coach headCoach = null;
		Manager generalManager = null;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList;
		List<Division> divisionList;
		List<Player> playerList = new ArrayList<Player>();
		try {

			for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
				conferenceName = conferenceList.get(cIndex).getConferenceName();
				divisionList = conferenceList.get(cIndex).getDivisionList();

				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					divisionName = divisionList.get(dIndex).getDivisionName();
					teamList = divisionList.get(dIndex).getTeamList();

					for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
						teamName = teamList.get(tIndex).getTeamName();
						generalManager = teamList.get(tIndex).getGeneralManager();
						headCoach = teamList.get(tIndex).getHeadCoach();
						playerList = teamList.get(tIndex).getPlayerList();
						for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
							isCreated = leagueDb.persisitLeagueData(leagueName, conferenceName, divisionName, teamName,
									generalManager.getManagerName(), headCoach.getCoachName(), playerList.get(pIndex));
						}
						isCreated = headCoach.saveTeamCoaches(headCoach, teamName, leagueName);
						isCreated = generalManager.saveTeamGeneralManager(generalManager.getManagerName(), teamName, leagueName);
					}
				}
			}
			conferenceName = divisionName = teamName = coachName = "Empty";
			playerList.clear();
			playerList = league.getFreeAgents();
			if (!playerList.isEmpty()) {
				for (int index = 0; index < playerList.size(); index++) {
					isCreated = leagueDb.persisitLeagueData(leagueName, conferenceName, divisionName, teamName,
							managerName, coachName, playerList.get(index));
				}
			}
			league.getGameConfig().saveGameplayConfig(league);
			headCoach.saveLeagueCoaches(league);
			generalManager.saveManagerList(league);
		} catch (Exception e) {
			System.out.println("error " + e.getMessage());
		}

		return isCreated;
	}
	
	public League loadLeagueObject(String leagueName, String conferenceName, String divisionName, Team team, League league) {
		int conferenceIndex = -1;
		int divisionIndex = -1;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList = null;
		List<Division> divisionList = null;
		if(conferenceList.size() == 0) {
			teamList.add(team);
			Division division = new Division(divisionName, teamList);
			divisionList.add(division);
			Conference conference = new Conference(conferenceName, divisionList);
			conferenceList.add(conference);
		}
		for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
			if(conferenceList.get(cIndex).getConferenceName().equals(conferenceName)) {
				conferenceIndex = cIndex;
			}
		}
		if(conferenceIndex == -1) {
			Conference conference = new Conference(conferenceName, null);
			conferenceList.add(conference);
			conferenceIndex = conferenceList.size()-1;
		}
		divisionList = conferenceList.get(conferenceIndex).getDivisionList();
		if(divisionList.size() == 0) {
			teamList.add(team);
			Division division = new Division(divisionName, teamList);
			divisionList.add(division);
		}
		for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
			if(divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
				divisionIndex = dIndex;
			}
		}
		if(divisionIndex == -1) {
			teamList.add(team);
			Division division = new Division(divisionName, teamList);
			divisionList.add(division);
		}
		conferenceList.get(conferenceIndex).setDivisionList(divisionList);
		league.setConferenceList(conferenceList);		
		return league;
	}
}
