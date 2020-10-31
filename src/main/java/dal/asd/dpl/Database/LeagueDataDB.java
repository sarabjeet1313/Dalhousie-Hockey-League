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

public class LeagueDataDB implements ILeaguePersistance {

	InvokeStoredProcedure isp = null;	
	InvokeStoredProcedure invoke = null;
	private String leagueName = "";
	private String rteamName = "";
	private String divisionName = "";
	private String conferenceName = "";
	private Coach headCoach = null;
	private Manager manager = null;

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
			invoke = new InvokeStoredProcedure("spLoadLeagueData(?)");
			invoke.setParameter(1, teamName);
			result = invoke.executeQueryWithResults();
			
			boolean flag = false;
			while (result.next()) {
				Player player = new Player(result.getString("playerName"), result.getString("position"),result.getBoolean("captain"),
						result.getInt("age"), result.getInt("skating"), result.getInt("shooting"), result.getInt("checking"), 
						result.getInt("saving"), result.getBoolean("isInjured"), result.getBoolean("retiredStatus"),result.getInt("daysInjured"));
				if(result.getString("teamName") == null) {
					freeAgentList.add(player);
				}			
				else {
					if(result.isLast() && result.getString("teamName").equals(rteamName) == false) {
						flag = false;
					}
					if(flag == false) {
						headCoach = new Coach(result.getString("coachName"), result.getInt("skatingCoach"), result.getInt("shootingCoach"), 
								result.getInt("checkingCoach"), result.getInt("savingCoach"));
						manager = new Manager(result.getString("generalManagerName"));
						flag = loadCommonLeagueData(result.getString("leagueName"), result.getString("conferenceName"), 
								result.getString("divisionName"), result.getString("teamName"));
					}
					
					if(result.getString("teamName").equals(rteamName) && flag == true) {
						playerList.add(player);
					}
					else {
						Team team = new Team(rteamName, manager, headCoach, playerList);
						league =league.loadLeagueObject(leagueName, conferenceName, divisionName, team, league); 
						manager = new Manager(result.getString("generalManagerName"));
						flag = loadCommonLeagueData(result.getString("leagueName"), result.getString("conferenceName"), 
								result.getString("divisionName"), result.getString("teamName"));
						headCoach = new Coach(result.getString("coachName"), result.getInt("skatingCoach"), result.getInt("shootingCoach"), 
								result.getInt("checkingCoach"), result.getInt("savingCoach"));
						playerList.clear();
						playerList.add(player);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();	
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
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
			invoke = new InvokeStoredProcedure("spCheckLeagueName(?)");
			invoke.setParameter(1, leagueName);
			result = invoke.executeQueryWithResults();
			while (result.next()) {
				rowCount = result.getInt("rowCount");
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
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
			invoke = new InvokeStoredProcedure(
					"spPersistLeagueData(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
				isPersisted = result.getBoolean("success");
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		} finally {
			try {
				invoke.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return isPersisted;
	}

}
