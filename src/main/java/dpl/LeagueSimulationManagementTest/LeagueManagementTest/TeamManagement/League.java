package dpl.LeagueSimulationManagementTest.LeagueManagementTest.TeamManagement;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dpl.DplConstants.GeneralConstants;
import dpl.DplConstants.TeamManagementConstants;
import dpl.LeagueSimulationManagementTest.LeagueManagementTest.GameplayConfiguration.GameplayConfig;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.CmdUserOutput;
import dpl.LeagueSimulationManagementTest.UserInputOutputTest.UserOutput.IUserOutput;

public class League implements ILeagueOperation {

    private String leagueName;
    private List<Conference> conferenceList;
    private List<Player> freeAgents;
    private List<Coach> coaches;
    private List<Manager> managerList;
    private static List<League> leagueList;
    private GameplayConfig gameConfig;
    private ILeaguePersistance leagueDb;
    IUserOutput output = new CmdUserOutput();

    public League() {

    }

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

    public boolean isValidLeagueName(String leagueName) throws SQLException{
        int rowCount = 0;
        boolean isValid = Boolean.TRUE;
        try {
            rowCount = leagueDb.checkLeagueName(leagueName);
        } 
        catch (SQLException e) {
            throw e;
        }
        if (rowCount > 0) {
            isValid = Boolean.FALSE;
        }
        return isValid;
    }

    @Override
    public League loadLeague(String teamName) throws SQLException{
        League league = null;
        try {
            league = leagueDb.loadLeagueData(teamName);
        } 
        catch (SQLException e) {
        	throw e;
        }
        return league;
    }

    @Override
    public boolean createTeam(League league) throws SQLException{
        boolean isCreated = Boolean.FALSE;
        String leagueName = league.getLeagueName();
        String conferenceName, divisionName, teamName, coachName, managerName = TeamManagementConstants.EMPTY.toString();
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
                        isCreated = generalManager.saveTeamGeneralManager(generalManager.getManagerName(), teamName,
                                leagueName);
                    }
                }
            }
            conferenceName = divisionName = teamName = coachName = TeamManagementConstants.EMPTY.toString();
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
        } catch (SQLException e) {
            throw e;
        }

        return isCreated;
    }

    @Override
    public List<List<Player>> getAvailableLeaguePlayers(League league) {
        List<List<Player>> list = new ArrayList<List<Player>>();
        List<Player> playerList = league.getFreeAgents();
        List<Player> golieList = new ArrayList<Player>();
        List<Player> forwordList = new ArrayList<Player>();
        List<Player> defenceList = new ArrayList<Player>();
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
                                   League league) throws NullPointerException{
        int conferenceIndex = -1;
        int divisionIndex = -1;
        boolean flag = Boolean.FALSE;
        List<Conference> conferenceList = league.getConferenceList();
        List<Team> teamList = null;
        List<Division> divisionList = null;
        try {
        	if (conferenceList == null) {
                conferenceList = new ArrayList<Conference>();
                divisionList = new ArrayList<Division>();
                teamList = new ArrayList<Team>();
                teamList.add(team);
                Division division = new Division(divisionName, teamList);
                divisionList.add(division);
                Conference conference = new Conference(conferenceName, divisionList);
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
                Conference conference = new Conference(conferenceName, null);
                conferenceList.add(conference);
                conferenceIndex = conferenceList.size() - 1;
            }
            divisionList = conferenceList.get(conferenceIndex).getDivisionList();
            if (divisionList == null) {
                teamList = new ArrayList<Team>();
                teamList.add(team);
                Division division = new Division(divisionName, teamList);
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
                Division division = new Division(divisionName, teamList);
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
    public boolean UpdateLeague(League league) throws SQLException{
        boolean isUpdated = Boolean.FALSE;
        String leagueName = league.getLeagueName();
        String teamName = TeamManagementConstants.EMPTY.toString();
        List<Conference> conferenceList = league.getConferenceList();
        List<Team> teamList;
        List<Division> divisionList;
        List<Player> playerList = new ArrayList<Player>();
        try {
            for (int cIndex = 0; cIndex < conferenceList.size(); cIndex++) {
                divisionList = conferenceList.get(cIndex).getDivisionList();
                for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
                    teamList = divisionList.get(dIndex).getTeamList();
                    for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
                        teamName = teamList.get(tIndex).getTeamName();
                        playerList = teamList.get(tIndex).getPlayerList();
                        for (int pIndex = 0; pIndex < playerList.size(); pIndex++) {
                            isUpdated = leagueDb.UpdateLeagueData(leagueName, teamName, playerList.get(pIndex));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return isUpdated;
    }
}
