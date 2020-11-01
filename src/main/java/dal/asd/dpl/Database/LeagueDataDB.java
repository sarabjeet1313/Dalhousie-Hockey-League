package dal.asd.dpl.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.ILeaguePersistance;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.TeamManagement.Manager;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Team;
import dal.asd.dpl.UserOutput.CmdUserOutput;
import dal.asd.dpl.UserOutput.IUserOutput;
import dal.asd.dpl.Util.CoachUtil;
import dal.asd.dpl.Util.ConferenceUtil;
import dal.asd.dpl.Util.DivisionUtil;
import dal.asd.dpl.Util.LeagueUtil;
import dal.asd.dpl.Util.ManagerUtil;
import dal.asd.dpl.Util.PlayerUtil;
import dal.asd.dpl.Util.StoredProcedureUtil;
import dal.asd.dpl.Util.TeamUtil;

public class LeagueDataDB implements ILeaguePersistance {

	InvokeStoredProcedure isp = null;
	InvokeStoredProcedure invoke = null;
	private String leagueName = "";
	private String rteamName = "";
	private String divisionName = "";
	private String conferenceName = "";
	private Coach headCoach = null;
	private Manager manager = null;
	IUserOutput output = new CmdUserOutput();

	private boolean loadCommonLeagueData(String league, String conference, String division, String team) {
		boolean isStored = false;
		leagueName = league;
		rteamName = team;
		divisionName = division;
		conferenceName = conference;
		return isStored;
	}

	@Override
	public League loadLeagueData(String teamName) {
		League league = null;
		List<Player> playerList = new ArrayList<Player>();
		List<Player> freeAgentList = new ArrayList<Player>();
		ResultSet result;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.LOAD_LEAGUE.getSpString());
			invoke.setParameter(1, teamName);
			result = invoke.executeQueryWithResults();

			boolean flag = false;
			while (result.next()) {
				Player player = new Player(result.getString(PlayerUtil.PLAYER_NAME.toString()),
						result.getString(PlayerUtil.PLAYER_POSITION.toString()),
						result.getBoolean(PlayerUtil.PLAYER_CAPTAIN.toString()),
						result.getInt(PlayerUtil.PLAYER_AGE.toString()), result.getInt(PlayerUtil.SKATING.toString()),
						result.getInt(PlayerUtil.SHOOTING.toString()), result.getInt(PlayerUtil.CHECKING.toString()),
						result.getInt(PlayerUtil.SAVING.toString()),
						result.getBoolean(PlayerUtil.IS_INJURED.toString()),
						result.getBoolean(PlayerUtil.RETIRED_STATUS.toString()),
						result.getInt(PlayerUtil.DAYS_INJURED.toString()));
				if (result.getString(TeamUtil.TEAM_NAME.toString()) == null) {
					freeAgentList.add(player);
				} else {
					if (result.isLast() && result.getString(TeamUtil.TEAM_NAME.toString()).equals(rteamName) == false) {
						flag = false;
					}
					if (flag == false) {
						headCoach = new Coach(result.getString(CoachUtil.COACH_NAME.toString()),
								result.getInt(CoachUtil.COACH_SKATING.toString()),
								result.getInt(CoachUtil.COACH_SHOOTING.toString()),
								result.getInt(CoachUtil.COACH_CHECKING.toString()),
								result.getInt(CoachUtil.COACH_SAVING.toString()));
						manager = new Manager(result.getString(ManagerUtil.GENERAL_MANAGER_NAME.toString()));
						flag = loadCommonLeagueData(result.getString(LeagueUtil.LEAGUE_NAME.toString()),
								result.getString(ConferenceUtil.CONFERENCE_NAME.toString()),
								result.getString(DivisionUtil.DIVISION_NAME.toString()),
								result.getString(TeamUtil.TEAM_NAME.toString()));
					}

					if (result.getString(TeamUtil.TEAM_NAME.toString()).equals(rteamName) && flag == true) {
						playerList.add(player);
					} else {
						Team team = new Team(rteamName, manager, headCoach, playerList);
						league = league.loadLeagueObject(leagueName, conferenceName, divisionName, team, league);
						manager = new Manager(result.getString(ManagerUtil.GENERAL_MANAGER_NAME.toString()));
						flag = loadCommonLeagueData(result.getString(LeagueUtil.LEAGUE_NAME.toString()),
								result.getString(ConferenceUtil.CONFERENCE_NAME.toString()),
								result.getString(DivisionUtil.DIVISION_NAME.toString()),
								result.getString(TeamUtil.TEAM_NAME.toString()));
						headCoach = new Coach(result.getString(CoachUtil.COACH_NAME.toString()),
								result.getInt(CoachUtil.COACH_SKATING.toString()),
								result.getInt(CoachUtil.COACH_SHOOTING.toString()),
								result.getInt(CoachUtil.COACH_CHECKING.toString()),
								result.getInt(CoachUtil.COACH_SAVING.toString()));
						playerList.clear();
						playerList.add(player);
					}
				}
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		league.setFreeAgents(freeAgentList);
		return league;
	}

	@Override
	public int checkLeagueName(String leagueName) {
		ResultSet result;
		int rowCount = 0;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.CHECK_LEAGUE_NAME.getSpString());
			invoke.setParameter(1, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				rowCount = result.getInt(LeagueUtil.ROW_COUNT.toString());
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		return rowCount;
	}

	@Override
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, Player player) {
		ResultSet result;
		boolean isPersisted = false;
		try {
			invoke = new InvokeStoredProcedure(StoredProcedureUtil.PERSIST_LEAGUE.getSpString());
			invoke.setParameter(1, leagueName);
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
				isPersisted = result.getBoolean(LeagueUtil.SUCCESS.toString());
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		return isPersisted;
	}

	@Override
	public boolean persisitRetiredPlayers(Player player, String teamName, League league) {
		boolean isPersisted = false;
		ResultSet result;
		try {
			isp = new InvokeStoredProcedure(StoredProcedureUtil.UPDATE_RETIRED_PLAYERS_IN_LEAGUE.getSpString());
			isp.setParameter(1, league.getLeagueName());
			isp.setParameter(2, teamName);
			isp.setParameter(3, player.getPlayerName());
			result = isp.executeQueryWithResults();
			while (result.next()) {
				isPersisted = result.getBoolean(LeagueUtil.SUCCESS.toString());
			}
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
			}
		}
		return isPersisted;
	}

}
