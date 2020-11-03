package dpl.LeagueSimulationManagement.LeagueManagement.Standings;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dpl.Database.InvokeStoredProcedure;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Division;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.Team;

public class StandingInfo {

	private League leagueToSimulate;
	private int season;
	private InvokeStoredProcedure isp;
	private Map<String, Integer> teamWinMap;
	private Map<String, Integer> teamLoseMap;
	private IStandingsPersistance standingsDb;

	public StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb) {
		this.leagueToSimulate = leagueToSimulate;
		this.standingsDb = standingsDb;
		teamWinMap = new HashMap<>();
		teamLoseMap = new HashMap<>();
		this.season = season;
	}

	public void updateTeamWinMap(String teamName) {
		if (teamWinMap.containsKey(teamName)) {
			int wins = teamWinMap.get(teamName);
			teamWinMap.put(teamName, wins + 1);
		} else {
			teamWinMap.put(teamName, 1);
		}
	}

	public void updateTeamLoseMap(String teamName) {
		if (teamLoseMap.containsKey(teamName)) {
			int loses = teamLoseMap.get(teamName);
			teamLoseMap.put(teamName, loses + 1);
		} else {
			teamLoseMap.put(teamName, 1);
		}
	}

	public Map<String, Integer> getTeamWinMap() {
		return teamWinMap;
	}

	public Map<String, Integer> getTeamLoseMap() {
		return teamLoseMap;
	}

	public boolean initializeStandings() throws SQLException {

		boolean result = false;
		try {
			String leagueName = leagueToSimulate.getLeagueName();
			List<Conference> conferenceList = leagueToSimulate.getConferenceList();

			for (Conference conferences : conferenceList) {
				List<Division> divisionList = conferences.getDivisionList();
				String conferenceName = conferences.getConferenceName();

				for (Division divisions : divisionList) {
					List<Team> teamList = divisions.getTeamList();
					String divisionName = divisions.getDivisionName();

					for (Team teams : teamList) {
						String teamName = teams.getTeamName();
						result = standingsDb.insertToStandings(leagueName, conferenceName, divisionName, teamName);
						if (result) {
							continue;
						} else {
							return result;
						}
					}
				}
			}
		} catch (SQLException e) {
			throw e;
		}
		return result;
	}

	public void updateStandings() throws SQLException {
		try {
			for (Map.Entry<String, Integer> entry : this.teamWinMap.entrySet()) {
				String teamWon = entry.getKey();
				int noOfMatchesWon = entry.getValue();

				for (int i = 0; i < noOfMatchesWon; i++) {
					standingsDb.updateStandingsWin(teamWon);
				}
				teamWinMap.put(teamWon, 0);
			}

			for (Map.Entry<String, Integer> entry : this.teamLoseMap.entrySet()) {
				String teamLose = entry.getKey();
				int noOfMatchesLose = entry.getValue();

				for (int i = 0; i < noOfMatchesLose; i++) {
					standingsDb.updateStandingsLosses(teamLose);
				}
				teamLoseMap.put(teamLose, 0);
			}
		} catch (SQLException e) {
			throw e;
		}
	}
}
