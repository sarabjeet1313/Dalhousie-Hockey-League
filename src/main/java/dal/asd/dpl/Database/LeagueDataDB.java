package dal.asd.dpl.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.TeamManagement.Coach;
import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;

public class LeagueDataDB implements ILeague {
	
	InvokeStoredProcedure isp = null;
	@Override
	public List<Leagues> getLeagueData(String teamName) {
		Leagues league = null;
		List<Leagues> leagueList = new ArrayList<Leagues>(); 
		ArrayList<Player> playerList = new ArrayList<Player>();
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		String tempLeagueName = "";
		ResultSet result;
		boolean flag = true;
		try  {
			isp = new InvokeStoredProcedure("spLoadLeagueData(?)");
			isp.setParameter(1, teamName);
			result = isp.executeQueryWithResults();
			while(result.next()) {
				if(!flag && !tempLeagueName.equals(result.getString("leagueName"))) {
					flag = true;
					league.getConferenceList().get(0).getDivisionList().get(0)
						.getTeamList().get(0).setPlayerList(playerList);
					leagueList.add(league);
					playerList.clear();
				}
				if(flag) {
					Coach headCoach = new Coach(result.getString("name"), result.getDouble("skating"), result.getDouble("shooting"), result.getDouble("checking"), result.getDouble("saving"));
					Teams team = new Teams(result.getString("teamName"), result.getString("generalManager"), headCoach, playerList);
					teamList.add(team);
					Divisions division = new Divisions(result.getString("divisionName"),teamList);
					divisionList.add(division);
					Conferences conference = new Conferences(result.getString("conferenceName"),divisionList);
					conferenceList.add(conference);
					List<Player> freeAgents = new ArrayList<Player>();
					List<String> managers = new ArrayList<String>(); 	
					List<Coach> coachesList = new ArrayList<Coach>();
					league = new Leagues(result.getString("leagueName"), conferenceList, freeAgents, coachesList, managers);
					tempLeagueName = result.getString("leagueName");
					flag = false;
				}
				if(result.isLast()) {
					league.getConferenceList().get(0).getDivisionList().get(0)
					.getTeamList().get(0).setPlayerList(playerList);
					leagueList.add(league);
				}
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		}
		finally {
			try {
				isp.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return leagueList;
	}
	
	@Override
	public int checkLeagueName(String leagueName) {
		ResultSet result;
		int rowCount = 0;
		try {
			isp = new InvokeStoredProcedure("spCheckLeagueName(?)");
			isp.setParameter(1, leagueName);
			result = isp.executeQueryWithResults();
			while(result.next()) {
				rowCount = result.getInt("rowCount");
			}	
		}
		catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		}
		finally {
			try {
				isp.closeConnection();
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
			isp = new InvokeStoredProcedure("spPersistLeagueData(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			isp.setParameter(1, leagueName);
			isp.setParameter(2, conferenceName);
			isp.setParameter(3, divisionName);
			isp.setParameter(4, teamName);
			isp.setParameter(5, generalManager);
			isp.setParameter(6, headCoach);
			isp.setParameter(7, player.getPlayerName());
			isp.setParameter(8, player.getPlayerPosition());
			isp.setParameter(9, player.getCaptain());
			isp.setParameter(10, player.getAge());
			isp.setParameter(11, player.getSkating());
			isp.setParameter(12, player.getShooting());
			isp.setParameter(13, player.getChecking());
			isp.setParameter(14, player.getSaving());
			result = isp.executeQueryWithResults();
			while(result.next()) {
				isPersisted = result.getBoolean("success");
			}
		}
		catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		}
		finally {
			try {
				isp.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return isPersisted;
	}

	@Override
	public boolean persisitCoaches(Coach coach, String teamName, String leagueName) {
		boolean isPersisted = false;
		ResultSet result;
		try {
			isp = new InvokeStoredProcedure("spPersistCoaches(?,?,?,?,?,?,?,?)");
			isp.setParameter(1, coach.getCoachName());
			isp.setParameter(2, coach.getSkating());
			isp.setParameter(3, coach.getShooting());
			isp.setParameter(4, coach.getChecking());
			isp.setParameter(5, coach.getSaving());
			isp.setParameter(6, teamName);
			isp.setParameter(7, leagueName);
			result = isp.executeQueryWithResults();
			while(result.next()) {
				isPersisted = result.getBoolean("success");
			}
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		}
		finally {
			try {
				isp.closeConnection();
			} catch (SQLException e) {
				System.out.println("Database Error:" + e.getMessage());
			}
		}
		return isPersisted;
	}
	
	
	
}
