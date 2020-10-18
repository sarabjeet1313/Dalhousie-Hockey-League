package dal.asd.dpl.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.TeamManagement.Conferences;
import dal.asd.dpl.TeamManagement.Divisions;
import dal.asd.dpl.TeamManagement.ILeague;
import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.TeamManagement.Player;
import dal.asd.dpl.TeamManagement.Teams;

public class LeagueDataDB implements ILeague{
	
	InvokeStoredProcedure isp = null;
	@Override
	public List<Leagues> getLeagueData(String teamName) throws SQLException {
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
					Teams team = new Teams(result.getString("teamName"), result.getString("generalManager"), result.getString("headCoach"), playerList);
					teamList.add(team);
					Divisions division = new Divisions(result.getString("divisionName"),teamList);
					divisionList.add(division);
					Conferences conference = new Conferences(result.getString("conferenceName"),divisionList);
					conferenceList.add(conference);
					List<Player> freeAgents = new ArrayList<Player>();
					league = new Leagues(result.getString("leagueName"), conferenceList, freeAgents);
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
			isp.closeConnection();
		}
		return leagueList;
	}
	
	@Override
	public int checkLeagueName(String leagueName) throws SQLException {
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
			isp.closeConnection();
		}
		return rowCount;
	}
	
	@Override
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, String playerName, String position, boolean captain, int age, int skating, int shooting, int checking, int saving) throws SQLException {
		ResultSet result;
		boolean isPersisted = false;
		try {
			isp = new InvokeStoredProcedure("spPersistLeagueData(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			isp.setParameter(1, leagueName);
			isp.setParameter(2, conferenceName);
			isp.setParameter(3, divisionName);
			isp.setParameter(4, teamName);
			isp.setParameter(5, generalManager);
			isp.setParameter(6, headCoach);
			isp.setParameter(7, playerName);
			isp.setParameter(8, position);
			isp.setParameter(9, captain);
			isp.setParameter(10, age);
			isp.setParameter(11, skating);
			isp.setParameter(12, shooting);
			isp.setParameter(13, checking);
			isp.setParameter(14, saving);
			result = isp.executeQueryWithResults();
			while(result.next()) {
				isPersisted = result.getBoolean("success");
			}
		}
		catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
		}
		finally {
			isp.closeConnection();
		}
		return isPersisted;
	}
	
}
