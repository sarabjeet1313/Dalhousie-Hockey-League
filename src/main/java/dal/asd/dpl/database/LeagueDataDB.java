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
	public Leagues getLeagueData(String teamName) {
		Leagues league = null;
		ArrayList<Players> playerList = new ArrayList<Players>();
		ArrayList<Teams> teamList = new ArrayList<Teams>();
		ArrayList<Divisions> divisionList = new ArrayList<Divisions>();
		ArrayList<Conferences> conferenceList = new ArrayList<Conferences>();
		String tempTeamName ="", tempGeneralManager = "", tempHeadCoach = "", tempDivisionName = "", tempConferenceName = "", tempLeagueName = ""; 
		String query = "{CALL get_league_data(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
		ResultSet result;
		boolean flag = true;
		try (Connection connnection = db.getConnection(); CallableStatement statement = connnection.prepareCall(query)) {
			statement.setString(1, teamName);
			statement.registerOutParameter(2, Types.VARCHAR);
			statement.registerOutParameter(3, Types.VARCHAR);
			statement.registerOutParameter(4, Types.BOOLEAN);
			statement.registerOutParameter(5, Types.VARCHAR);
			statement.registerOutParameter(6, Types.VARCHAR);
			statement.registerOutParameter(7, Types.VARCHAR);
			statement.registerOutParameter(8, Types.VARCHAR);
			statement.registerOutParameter(9, Types.VARCHAR);
			statement.registerOutParameter(10, Types.VARCHAR);
			result = statement.executeQuery();
			while(result.next()) {
				if(flag) {
					tempTeamName = result.getString(5);
					tempGeneralManager = result.getString(6);
					tempHeadCoach = result.getString(7);
					tempDivisionName = result.getString(8);
					tempConferenceName = result.getString(9);
					tempLeagueName = result.getString(10);
					flag = false;
				}
				Players player = new Players(result.getString(2), result.getString(3), result.getBoolean(4));
				playerList.add(player);
			}
			if(!flag) {
				Teams team = new Teams(tempTeamName, tempGeneralManager, tempHeadCoach, playerList);
				teamList.add(team);
				Divisions division = new Divisions(tempDivisionName,teamList);
				divisionList.add(division);
				Conferences conference = new Conferences(tempConferenceName,divisionList);
				conferenceList.add(conference);
				List<Players> freeAgents = null;
				league = new Leagues(tempLeagueName, conferenceList, freeAgents);
			}
			result.close();
		} catch (Exception e) {
			System.out.println("Database Error:" + e.getMessage());
			db.disconnect();
		}
		finally {
			db.disconnect();
		}
		return league;
	}
	
	@Override
	public int checkLeagueName(String leagueName) {
		String query = "{CALL get_league_name(?, ?)}";
		ResultSet result;
		int rowCount = 0;
		try (Connection connnection = db.getConnection(); CallableStatement statement = connnection.prepareCall(query)) {
			statement.setString(1, leagueName);
			statement.registerOutParameter(2, Types.INTEGER);
			result = statement.executeQuery();
			rowCount = result.getInt(2);
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
		try (Connection connnection = db.getConnection(); CallableStatement statement = connnection.prepareCall(query)) {
			statement.setString(1, leagueName);
			statement.setString(2, conferenceName);
			statement.setString(3, divisionName);
			statement.setString(4, teamName);
			statement.setString(5, generalManager);
			statement.setString(6, headCoach);
			statement.setString(7, playerName);
			statement.setString(8, position);
			statement.setBoolean(9, captain);
			statement.registerOutParameter(10, Types.BOOLEAN);
			result = statement.executeQuery();
			isPersisted = result.getBoolean(10);
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
