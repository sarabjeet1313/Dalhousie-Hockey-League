package dal.asd.dpl.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dal.asd.dpl.teammanagement.Conferences;
import dal.asd.dpl.teammanagement.Divisions;
import dal.asd.dpl.teammanagement.ILeague;
import dal.asd.dpl.teammanagement.Leagues;
import dal.asd.dpl.teammanagement.Players;
import dal.asd.dpl.teammanagement.Teams;

public class LeagueDataDB implements ILeague{
	DatabaseConnection db = new DatabaseConnection();
	
	@Override
	public List<Leagues> getLeagueData(String teamName) {
		Leagues league = null;
		List<Leagues> leagueList = new ArrayList<Leagues>(); 
		ArrayList<Players> playerList = new ArrayList<Players>();
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		String tempTeamName ="", tempGeneralManager = "", tempHeadCoach = "", tempDivisionName = "", 
				tempConferenceName = "", tempLeagueName = ""; 
		String query = "{CALL get_league_data(?)}";
		ResultSet result;
		boolean flag = true;
		try (Connection connnection = db.getConnection(); ) {
			CallableStatement statement = connnection.prepareCall(query);
			statement.setString(1, teamName);
			result = statement.executeQuery();
			
			while(result.next()) {
				if(!flag && !tempLeagueName.equals(result.getString("leagueName"))) {
					flag = true;
					league.getConferenceList().get(0).getDivisionList().get(0)
						.getTeamList().get(0).setPlayerList(playerList);
					leagueList.add(league);
					playerList.clear();
				}
				Players player = new Players(result.getString("playerName"), result.getString("position"), result.getBoolean("captain"));
				playerList.add(player);
				if(result.isLast()) {
					league.getConferenceList().get(0).getDivisionList().get(0)
					.getTeamList().get(0).setPlayerList(playerList);
					leagueList.add(league);
				}
				if(flag) {
					Teams team = new Teams(result.getString("teamName"), result.getString("generalManager"), result.getString("headCoach"), playerList);
					teamList.add(team);
					Divisions division = new Divisions(result.getString("divisionName"),teamList);
					divisionList.add(division);
					Conferences conference = new Conferences(result.getString("conferenceName"),divisionList);
					conferenceList.add(conference);
					List<Players> freeAgents = new ArrayList<Players>();
					league = new Leagues(result.getString("leagueName"), conferenceList, freeAgents);
					tempLeagueName = result.getString("leagueName");
					flag = false;
				}
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
			db.disconnect();
		}
		finally {
			db.disconnect();
		}
		return leagueList;
	}
	
	@Override
	public int checkLeagueName(String leagueName) {
//		String query = "{CALL get_league_name_Test(?,?)}";
		ResultSet result;
		int rowCount = 0;
	try (Connection connnection = db.getConnection()) {
//		try {
//			Connection connnection = db.getConnection();
//			CallableStatement statement = connnection.prepareCall("{CALL get_league_name_Test(?)}");
		System.out.println(connnection);
			CallableStatement statement = connnection.prepareCall("{CALL get_league_name_Test(?)}");
			
//			System.out.println(statement);
			statement.setString(1, leagueName);
			
			result = statement.executeQuery();
			while(result.next()) {
				rowCount = result.getInt("cc");
			}
			
		}
		catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
			db.disconnect();
		}
		finally {
			db.disconnect();
		}
		
		return rowCount;
	}
	
	@Override
	public boolean persisitLeagueData(String leagueName, String conferenceName, String divisionName, String teamName,
			String generalManager, String headCoach, String playerName, String position, boolean captain) {
		String query = "{CALL persist_league_data(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		ResultSet result;
		boolean isPersisted = false;
		try (Connection connnection = db.getConnection()) {
			CallableStatement statement = connnection.prepareCall(query);
			statement.setString(1, leagueName);
			statement.setString(2, conferenceName);
			statement.setString(3, divisionName);
			statement.setString(4, teamName);
			statement.setString(5, generalManager);
			statement.setString(6, headCoach);
			statement.setString(7, playerName);
			statement.setString(8, position);
			statement.setBoolean(9, captain);
			result = statement.executeQuery();
			while(result.next()) {
				isPersisted = result.getBoolean("success");
			}
		}
		catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
			db.disconnect();
		}
		finally {
			db.disconnect();
		}
		return isPersisted;
	}
}
