package dpl.LeagueManagement.TeamManagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

import dpl.SystemConfig;
import dpl.LeagueManagement.GameplayConfiguration.GameplayConfig;

public class League implements ILeagueOperation {

	@Expose(serialize = true, deserialize = true)
	private String leagueName;
	@Expose(serialize = true, deserialize = true)
	private List<Conference> conferenceList;
	@Expose(serialize = true, deserialize = true)
	private List<Player> freeAgents;
	@Expose(serialize = true, deserialize = true)
	private List<Coach> coaches;
	@Expose(serialize = true, deserialize = true)
	private List<Manager> managerList;
	private static List<League> leagueList;
	@Expose(serialize = true, deserialize = true)
	private GameplayConfig gameConfig;
	private ILeaguePersistance leagueDb;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

	public League() {
		super();
	}

	public League(String leagueName, List<Conference> conferenceList, List<Player> freeAgents, List<Coach> coaches,
			List<Manager> managerList, GameplayConfig gameConfig, ILeaguePersistance leagueDb) {
		super();
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
		super();
		this.leagueName = leagueName;
		this.conferenceList = conferenceList;
		this.freeAgents = freeAgents;
		this.coaches = coaches;
		this.managerList = managerList;
		this.gameConfig = gameConfig;
	}

	public ILeaguePersistance getLeagueDb() {
		return leagueDb;
	}

	public void setLeagueDb(ILeaguePersistance leagueDb) {
		this.leagueDb = leagueDb;
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

	public boolean isValidLeagueName(League league) throws IOException {
		int rowCount = 0;
		boolean isValid = Boolean.TRUE;
		try {
			rowCount = leagueDb.checkLeagueName(league);
		} catch (IOException e) {
			throw e;
		}
		if (rowCount > 0) {
			isValid = Boolean.FALSE;
		}
		return isValid;
	}

	@Override
	public League loadLeague(String teamName) throws IOException {
		League league = null;
		try {
			league = leagueDb.loadLeagueData(teamName);
		} catch (IOException e) {
			throw e;
		}
		return league;
	}

	@Override
	public boolean createTeam(League league) throws IOException {
		boolean isCreated = Boolean.FALSE;
		String leagueName = league.getLeagueName();
		String conferenceName, divisionName, teamName, coachName,
				managerName = TeamManagementConstants.EMPTY.toString();
		Coach headCoach = null;
		Manager generalManager = null;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList;
		List<Division> divisionList;
		List<Player> playerList = new ArrayList<>();
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
							isCreated = leagueDb.persisitLeagueData(league, conferenceName, divisionName, teamName,
									generalManager.getManagerName(), headCoach.getCoachName(), playerList.get(pIndex));
						}
						isCreated = headCoach.saveTeamCoaches(headCoach, teamName, leagueName);
						isCreated = generalManager.saveTeamGeneralManager(generalManager, teamName,
								leagueName);
					}
				}
			}
			conferenceName = divisionName = teamName = coachName = TeamManagementConstants.EMPTY.toString();
			playerList = league.getFreeAgents();
			if (!playerList.isEmpty()) {
				for (int index = 0; index < playerList.size(); index++) {
					isCreated = leagueDb.persisitLeagueData(league, conferenceName, divisionName, teamName, managerName,
							coachName, playerList.get(index));
				}
			}
			league.getGameConfig().saveGameplayConfig(league);
			headCoach.saveLeagueCoaches(league);
			generalManager.saveManagerList(league);
		} catch (IOException e) {
			throw e;
		}

		return isCreated;
	}

	@Override
	public List<List<Player>> getAvailableLeaguePlayers(League league) {
		List<List<Player>> list = new ArrayList<>();
		List<Player> playerList = league.getFreeAgents();
		List<Player> golieList = new ArrayList<>();
		List<Player> forwordList = new ArrayList<>();
		List<Player> defenceList = new ArrayList<>();
		for (int index = 0; index < playerList.size(); index++) {
			if (playerList.get(index).getPosition().equals(GeneralConstants.GOALIE.toString())) {
				golieList.add(playerList.get(index));
			} else if (playerList.get(index).getPosition().equals(GeneralConstants.FORWARD.toString())) {
				forwordList.add(playerList.get(index));
			} else {
				defenceList.add(playerList.get(index));
			}
		}
		list.add(golieList);
		list.add(forwordList);
		list.add(defenceList);
		return list;
	}

	@Override
	public League loadLeagueObject(String leagueName, String conferenceName, String divisionName, Team team,
			League league) throws NullPointerException {
		int conferenceIndex = -1;
		int divisionIndex = -1;
		boolean flag = Boolean.FALSE;
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList = null;
		List<Division> divisionList = null;
		try {
			if (conferenceList == null) {
				conferenceList = new ArrayList<>();
				divisionList = new ArrayList<>();
				teamList = new ArrayList<>();
				teamList.add(team);
				Division division = teamManagement.DivisionWithParameters(divisionName, teamList);
				divisionList.add(division);
				Conference conference = teamManagement.ConferenceWithParameters(conferenceName, divisionList);
				conferenceList.add(conference);
				flag = Boolean.TRUE;
			}
			for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
				if (conferenceList.get(cIndex).getConferenceName().equals(conferenceName)) {
					conferenceIndex = cIndex;
					break;
				}
			}
			if (conferenceIndex == -1) {
				Conference conference = teamManagement.ConferenceWithParameters(conferenceName, null);
				conferenceList.add(conference);
				conferenceIndex = conferenceList.size() - 1;
			}
			divisionList = conferenceList.get(conferenceIndex).getDivisionList();
			if (divisionList == null) {
				teamList = new ArrayList<>();
				teamList.add(team);
				Division division = teamManagement.DivisionWithParameters(divisionName, teamList);
				divisionList = new ArrayList<Division>();
				divisionList.add(division);
				conferenceList.get(conferenceIndex).setDivisionList(divisionList);
				flag = Boolean.TRUE;
			}
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				if (divisionList.get(dIndex).getDivisionName().equals(divisionName)) {
					if (flag == Boolean.FALSE) {
						divisionList.get(dIndex).getTeamList().add(team);
						conferenceList.get(conferenceIndex).setDivisionList(divisionList);
					}
					divisionIndex = dIndex;
					break;
				}
			}
			if (divisionIndex == -1) {
				teamList = new ArrayList<Team>();
				teamList.add(team);
				Division division = teamManagement.DivisionWithParameters(divisionName, teamList);
				divisionList.add(division);
				conferenceList.get(conferenceIndex).setDivisionList(divisionList);
			}

			league.setConferenceList(conferenceList);
		} catch (NullPointerException e) {
			throw e;
		}
		return league;
	}

	@Override
	public boolean updateLeague(League league) throws IOException {
		boolean isUpdated = Boolean.FALSE;
		String teamName = TeamManagementConstants.EMPTY.toString();
		List<Conference> conferenceList = league.getConferenceList();
		List<Team> teamList;
		List<Division> divisionList;
		List<Player> playerList = new ArrayList<>();
		try {
			for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
				divisionList = conferenceList.get(cIndex).getDivisionList();
				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
					teamList = divisionList.get(dIndex).getTeamList();
					for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
						teamName = teamList.get(tIndex).getTeamName();
						playerList = teamList.get(tIndex).getPlayerList();
						for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
							isUpdated = leagueDb.updateLeagueData(league, teamName, playerList.get(pIndex));
						}
					}
				}
			}
		} catch (IOException e) {
			throw e;
		}
		return isUpdated;
	}
}
