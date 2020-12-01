package dpl.LeagueManagement.Standings;

import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.SimulationManagement.InternalStateMachine.EndOfSeasonState;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class StandingInfo {

	private League leagueToSimulate;
	private int season;
	private IUserOutput output;
	private double totalSeasonMatches;
	private double totalGoalsInSeason;
	private double totalPenaltiesInSeason;
	private double totalShotsInSeason;
	private double totalSavesInSeason;
	private Map<String, Integer> teamWinMap;
	private Map<String, Integer> teamLoseMap;
	private Standing standing;
	private static final Logger log = Logger.getLogger(EndOfSeasonState.class.getName());

	public StandingInfo(League leagueToSimulate, int season, IStandingsPersistance standingsDb, IUserOutput output) {
		this.leagueToSimulate = leagueToSimulate;
		this.standing = SystemConfig.getSingleInstance().getStandingsAbstractFactory().Standing();
		this.standing.setSeason(season);
		this.output = output;
		teamWinMap = new HashMap<>();
		teamLoseMap = new HashMap<>();
		this.season = season;
		resetStats();
	}

	public void resetStats() {
		this.setTotalGoalsInSeason(0);
		this.setTotalPenaltiesInSeason(0);
		this.setTotalSavesInSeason(0);
		this.setTotalSeasonMatches(0);
		this.setTotalShotsInSeason(0);
	}

	public double getTotalSeasonMatches() {
		return totalSeasonMatches;
	}

	public void setTotalSeasonMatches(double totalSeasonMatches) {
		this.totalSeasonMatches = totalSeasonMatches;
	}

	public double getTotalGoalsInSeason() {
		return totalGoalsInSeason;
	}

	public void setTotalGoalsInSeason(double totalGoalsInSeason) {
		this.totalGoalsInSeason = totalGoalsInSeason;
	}

	public double getTotalPenaltiesInSeason() {
		return totalPenaltiesInSeason;
	}

	public void setTotalPenaltiesInSeason(double totalPenaltiesInSeason) {
		this.totalPenaltiesInSeason = totalPenaltiesInSeason;
	}

	public double getTotalShotsInSeason() {
		return totalShotsInSeason;
	}

	public void setTotalShotsInSeason(double totalShotsInSeason) {
		this.totalShotsInSeason = totalShotsInSeason;
	}

	public double getTotalSavesInSeason() {
		return totalSavesInSeason;
	}

	public void setTotalSavesInSeason(double totalSavesInSeason) {
		this.totalSavesInSeason = totalSavesInSeason;
	}

	public Standing getStanding() {
		return this.standing;
	}

	public void showStats() {
		double goalsPerGame = getTotalGoalsInSeason() / getTotalSeasonMatches();
		double penaltiesPerGame = getTotalPenaltiesInSeason() / getTotalSeasonMatches();
		double totalShots = getTotalShotsInSeason() / getTotalSeasonMatches();
		double totalSaves = getTotalSavesInSeason() / getTotalSeasonMatches();

		output.setOutput("Goals per Game : " + Math.round(goalsPerGame * 100.0) / 100.0);
		output.sendOutput();
		output.setOutput("Penalties per Game : " + Math.round(penaltiesPerGame * 100.0) / 100.0);
		output.sendOutput();
		output.setOutput("Shots : " + Math.round(totalShots * 100.0) / 100.0);
		output.sendOutput();
		output.setOutput("Saves : " + Math.round(totalSaves * 100.0) / 100.0 + "\n\n");
		output.sendOutput();
	}

	public void updateTeamWinMap(String teamName) {
		if (teamWinMap.containsKey(teamName)) {
			int wins = teamWinMap.get(teamName);
			teamWinMap.put(teamName, wins + 1);
		} else {
			teamWinMap.put(teamName, 1);
		}
		standing.updateStandingsWin(teamName);
	}

	public void updateTeamLoseMap(String teamName) {
		if (teamLoseMap.containsKey(teamName)) {
			int loses = teamLoseMap.get(teamName);
			teamLoseMap.put(teamName, loses + 1);
		} else {
			teamLoseMap.put(teamName, 1);
		}
		standing.updateStandingsLosses(teamName);
	}

	public Map<String, Integer> getTeamWinMap() {
		return teamWinMap;
	}

	public Map<String, Integer> getTeamLoseMap() {
		return teamLoseMap;
	}

	public List<String> getTopDivisionTeams(League leagueToSimulate, String divisionName) {
		Map<Integer, String> teamPoints = new HashMap<>();
		List<Conference> conferenceList = leagueToSimulate.getConferenceList();

		for (Conference conference : conferenceList) {
			List<Division> divisionList = conference.getDivisionList();

			for (Division division : divisionList) {
				String div = division.getDivisionName();
				if (div.equals(divisionName)) {
					List<Team> teamList = division.getTeamList();
					for (Team teams : teamList) {
						String teamName = teams.getTeamName();
						if (teamWinMap.containsKey(teamName)) {
							teamPoints.put(teamWinMap.get(teamName) * 2, teamName);
						}
					}
					break;
				}
			}
		}
		log.log(Level.INFO, "Fetching top 4 teams from division");
		return sortMap(teamPoints);
	}

	private List<String> sortMap(Map<Integer, String> teamMap) {
		List<String> teams = new ArrayList<>();

		Map<Integer, String> sortMap = new TreeMap<>(
				(Comparator<Integer>) (o1, o2) -> o2.compareTo(o1)
		);

		sortMap.putAll(teamMap);

		Iterator<Map.Entry<Integer, String>> iterator = sortMap.entrySet().iterator();
		for (int i = 0; iterator.hasNext() && i < 4; i++) {
			teams.add(iterator.next().getValue());
		}
		return teams;
	}
	
	public List<String> sortMapDraft() {
		List<String> teams = new ArrayList<>();
		Map<String, Integer> temMap = teamWinMap.entrySet().stream()
				  .sorted(Map.Entry.comparingByValue())
				  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		temMap.forEach((key, value) ->  {
			teams.add(key);
		} );
		return teams;
	}
}
