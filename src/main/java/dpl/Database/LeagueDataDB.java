package dpl.Database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dpl.SystemConfig;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Coach;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.CoachConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ConferenceConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.DivisionConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ILeaguePersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.LeagueConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Manager;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.ManagerConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Player;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.PlayerConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.TeamConstants;

public class LeagueDataDB implements ILeaguePersistance {
	InvokeStoredProcedure invoke = null;
	private String leagueName = "";
	private String rteamName = "";
	private String divisionName = "";
	private String conferenceName = "";
	private Coach headCoach = null;
	private Manager manager = null;
	private ITeamManagementAbstractFactory teamManagement = SystemConfig.getSingleInstance()
			.getTeamManagementAbstractFactory();

	public boolean loadCommonLeagueData(String league, String conference, String division, String team) {
		boolean isStored = Boolean.FALSE;
		leagueName = league;
		rteamName = team;
		divisionName = division;
		conferenceName = conference;
		isStored = Boolean.TRUE;
		return isStored;
	}

	@Override
	public League loadLeagueData(String teamName) {
		League league = new League();
		List<Player> playerList = new ArrayList<>();
		List<Player> freeAgentList = new ArrayList<>();
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.LOAD_LEAGUE.getSpString());
			invoke.setParameter(1, teamName);
			result = invoke.executeQueryWithResults();

			boolean flag = Boolean.FALSE;
			while (result.next()) {
				boolean isUserTeam = Boolean.FALSE;
				Player player = teamManagement.PlayerWithParameters(
						result.getString(PlayerConstants.PLAYER_NAME.toString()),
						result.getString(PlayerConstants.PLAYER_POSITION.toString()),
						result.getBoolean(PlayerConstants.PLAYER_CAPTAIN.toString()),
						result.getInt(PlayerConstants.PLAYER_AGE.toString()),
						result.getInt(PlayerConstants.SKATING.toString()),
						result.getInt(PlayerConstants.SHOOTING.toString()),
						result.getInt(PlayerConstants.CHECKING.toString()),
						result.getInt(PlayerConstants.SAVING.toString()),
						result.getBoolean(PlayerConstants.IS_INJURED.toString()),
						result.getBoolean(PlayerConstants.RETIRED_STATUS.toString()),
						result.getInt(PlayerConstants.DAYS_INJURED.toString()),
						result.getBoolean(PlayerConstants.IS_ACTIVE.toString()), 20, 4, 1999, false);
				String tempResult = result.getString(TeamConstants.TEAM_NAME.toString());
				if (result.wasNull()) {
					freeAgentList.add(player);
				} else {
					if (result.isLast() && tempResult.equals(rteamName) == Boolean.FALSE) {
						flag = Boolean.FALSE;
					}
					if (flag == Boolean.FALSE) {
						headCoach = teamManagement.CoachWithParameters(
								result.getString(CoachConstants.COACH_NAME.toString()),
								result.getDouble(CoachConstants.COACH_SKATING.toString()),
								result.getDouble(CoachConstants.COACH_SHOOTING.toString()),
								result.getDouble(CoachConstants.COACH_CHECKING.toString()),
								result.getDouble(CoachConstants.COACH_SAVING.toString()));
						// temp fix
						manager = teamManagement.ManagerWithParameters(
								result.getString(ManagerConstants.GENERAL_MANAGER_NAME.toString()),
								ManagerConstants.PERSONALITY.toString());
						flag = loadCommonLeagueData(result.getString(LeagueConstants.LEAGUE_NAME.toString()),
								result.getString(ConferenceConstants.CONFERENCE_NAME.toString()),
								result.getString(DivisionConstants.DIVISION_NAME.toString()),
								result.getString(TeamConstants.TEAM_NAME.toString()));
					}

					if (tempResult.equals(rteamName) && flag == true) {
						playerList.add(player);
					} else {
						if (rteamName.equals(teamName)) {
							isUserTeam = Boolean.TRUE;
						}
						Team team = teamManagement.TeamWithParameters(rteamName, manager, headCoach, playerList,
								isUserTeam);
						league = league.loadLeagueObject(leagueName, conferenceName, divisionName, team, league);
						manager = teamManagement.ManagerWithParameters(
								result.getString(ManagerConstants.GENERAL_MANAGER_NAME.toString()),
								ManagerConstants.PERSONALITY.toString());
						flag = loadCommonLeagueData(result.getString(LeagueConstants.LEAGUE_NAME.toString()),
								result.getString(ConferenceConstants.CONFERENCE_NAME.toString()),
								result.getString(DivisionConstants.DIVISION_NAME.toString()),
								result.getString(TeamConstants.TEAM_NAME.toString()));
						headCoach = teamManagement.CoachWithParameters(
								result.getString(CoachConstants.COACH_NAME.toString()),
								result.getDouble(CoachConstants.COACH_SKATING.toString()),
								result.getDouble(CoachConstants.COACH_SHOOTING.toString()),
								result.getDouble(CoachConstants.COACH_CHECKING.toString()),
								result.getDouble(CoachConstants.COACH_SAVING.toString()));
						playerList.clear();
						playerList.add(player);
					}
				}
			}
		} catch (Exception e) {
		}
		league.setLeagueName(leagueName);
		league.setFreeAgents(freeAgentList);
		return league;
	}

	@Override
	public int checkLeagueName(League league) {
		ResultSet result;
		int rowCount = 0;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.CHECK_LEAGUE_NAME.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				rowCount = result.getInt(LeagueConstants.ROW_COUNT.toString());
			}
		} catch (Exception e) {
		}
		return rowCount;
	}

	@Override
	public boolean persisitLeagueData(League league, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player) {
		ResultSet result;
		boolean isPersisted = Boolean.FALSE;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.PERSIST_LEAGUE.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			invoke.setParameter(2, conferenceName);
			invoke.setParameter(3, divisionName);
			invoke.setParameter(4, teamName);
			invoke.setParameter(5, generalManager);
			invoke.setParameter(6, headCoach);
			invoke.setParameter(7, player.getPlayerName());
			invoke.setParameter(8, player.getPosition());
			invoke.setParameter(9, player.isCaptain());
			invoke.setParameter(10, player.getAge());
			invoke.setParameter(11, player.getSkating());
			invoke.setParameter(12, player.getShooting());
			invoke.setParameter(13, player.getChecking());
			invoke.setParameter(14, player.getSaving());
			invoke.setParameter(15, player.isInjured());
			invoke.setParameter(16, player.getDaysInjured());
			invoke.setParameter(17, player.isRetireStatus());
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(LeagueConstants.SUCCESS.toString());
			}
		} catch (Exception e) {
		}
		return isPersisted;
	}

	@Override
	public boolean updateLeagueData(League league, String teamName, Player player) {
		ResultSet result;
		boolean isPersisted = Boolean.FALSE;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureConstants.UPDATE_LEAGUE.getSpString());
			invoke.setParameter(1, league.getLeagueName());
			invoke.setParameter(2, teamName);
			invoke.setParameter(3, player.getPlayerName());
			invoke.setParameter(4, player.getPosition());
			invoke.setParameter(5, player.isCaptain());
			invoke.setParameter(6, player.getAge());
			invoke.setParameter(7, player.getSkating());
			invoke.setParameter(8, player.getShooting());
			invoke.setParameter(9, player.getChecking());
			invoke.setParameter(10, player.getSaving());
			invoke.setParameter(11, player.isInjured());
			invoke.setParameter(12, player.getDaysInjured());
			invoke.setParameter(13, player.isRetireStatus());
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(LeagueConstants.SUCCESS.toString());
			}
		} catch (Exception e) {
		}
		return isPersisted;
	}
}