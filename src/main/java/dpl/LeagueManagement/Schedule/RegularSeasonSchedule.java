package dpl.LeagueManagement.Schedule;

import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.TeamManagement.Team;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegularSeasonSchedule implements ISchedule {

	private String currentDay;
	private String firstDay;
	private String lastDay;
	private int totalDivisions;
	private int totalTeams;
	private int matchesPerDay;
	private Calendar calendar;
	private IUserOutput output;
	private int seasonType;
	private Map<String, List<Map<String, String>>> finalSchedule;
	private Map<String, List<String>> conferenceTeamsMap;
	private Map<String, List<String>> divisionTeamsMap;
	private Map<String, List<String>> conferenceDivisionMap;
	private Map<String, Integer> matchScheduledForTeam;
	private Map<String, Integer> matchesOnADay;
	private List<String> listOfConferences;
	private List<String> teamsToBeScheduled;
	private List<String> teamsScheduled;
	private List<String> teamsCompeting;
	private static final Logger log = Logger.getLogger(RegularSeasonSchedule.class.getName());

	public RegularSeasonSchedule(Calendar calendar, IUserOutput output) {
		this.totalDivisions = 0;
		this.totalTeams = 0;
		this.seasonType = ScheduleConstants.REGULAR_SEASON;
		this.calendar = calendar;
		this.output = output;
		listOfConferences = new ArrayList<>();
		conferenceTeamsMap = new HashMap<>();
		divisionTeamsMap = new HashMap<>();
		conferenceDivisionMap = new HashMap<>();
		listOfConferences = new ArrayList<>();
		matchScheduledForTeam = new HashMap<>();
		finalSchedule = new HashMap<>();
		matchesOnADay = new HashMap<>();
		teamsScheduled = new ArrayList<>();
		teamsToBeScheduled = new ArrayList<>();
		teamsCompeting = new ArrayList<>();
	}

	public int getSeasonType() {
		return this.seasonType;
	}

	public void setSeasonType(int seasonType) {
		this.seasonType = seasonType;
	}

	public String getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
		matchesOnADay.put(this.currentDay, 0);
	}

	public String getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
		matchesOnADay.put(this.firstDay, 0);
	}

	public String getLastDay() {
		return lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public void generateSchedule(League leagueToSimulate) {
		incrementCurrentDay();
		populateInternalModel(leagueToSimulate);
		setMatchesPerDay();
		generate();
		log.log(Level.INFO, "Regular season schedule generated");
	}

	private void populateInternalModel(League leagueToSimulate) {
		if (null == leagueToSimulate) {
			log.log(Level.SEVERE, "Null league found in internal model inside regular season schedule");
			return;
		}

		List<Conference> conferenceList = leagueToSimulate.getConferenceList();

		for (int index = 0; index < conferenceList.size(); index++) {
			List<Division> divisionList = conferenceList.get(index).getDivisionList();
			String conferenceName = conferenceList.get(index).getConferenceName();
			this.listOfConferences.add(conferenceName);
			this.totalDivisions += divisionList.size();
			List<String> divisions = new ArrayList<>();
			for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {
				List<Team> teamList = divisionList.get(dIndex).getTeamList();
				String divisionName = divisionList.get(dIndex).getDivisionName();
				divisions.add(divisionName);
				this.totalTeams += teamList.size();
				List<String> teams = new ArrayList<>();
				for (int tIndex = 0; tIndex < teamList.size(); tIndex++) {
					String teamName = teamList.get(tIndex).getTeamName();
					teams.add(teamName);
					matchScheduledForTeam.put(teamName, 0);
				}
				if (conferenceTeamsMap.containsKey(conferenceName)) {
					List<String> alreadyAddedTeams = conferenceTeamsMap.get(conferenceName);
					alreadyAddedTeams.addAll(teams);
					conferenceTeamsMap.put(conferenceName, alreadyAddedTeams);
				} else {
					conferenceTeamsMap.put(conferenceName, teams);
				}
				divisionTeamsMap.put(divisionName, teams);
			}
			conferenceDivisionMap.put(conferenceName, divisions);
		}
	}

	private void setMatchesPerDay() {
		SimpleDateFormat myFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);
		try {
			Date date1 = myFormat.parse(firstDay);
			Date date2 = myFormat.parse(lastDay);
			long diff = date2.getTime() - date1.getTime();
			int totalDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			int matchCount = (totalTeams * ScheduleConstants.REGULAR_MATCH_PER_TEAM) / totalDays;
			if (matchCount < 1) {
				matchesPerDay = 1;
			} else {
				matchesPerDay = matchCount + 1;
			}
		} catch (ParseException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
			log.log(Level.SEVERE, e.getMessage());
			System.exit(1);
		}
	}

	private void generate() {
		for (String conference : listOfConferences) {
			List<String> teamsInConference = conferenceTeamsMap.get(conference);
			for (String team : teamsInConference) {
				String divisionName = "";
				for (Map.Entry<String, List<String>> entry : divisionTeamsMap.entrySet()) {
					if (entry.getValue().contains(team)) {
						divisionName = entry.getKey();
					}
				}
				scheduleIntraDivisionMatches(divisionName, team);
				scheduleInterDivisionMatches(conference, divisionName, team);
				scheduleInterConferenceMatches(conference, team);
			}
		}
	}

	private void scheduleIntraDivisionMatches(String divisionName, String teamName) {
		List<String> teamsInDivision = divisionTeamsMap.get(divisionName);

		int matchCounter = 0;
		if (teamsInDivision.size() < 2) {
			log.log(Level.SEVERE, "Less than 2 teams provided. Cannot simulate league.");
			return;
		}
		int loopCounter = ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY / (teamsInDivision.size() - 1);
		int i = 0;
		do {
			for (String team : teamsInDivision) {
				if (team.equalsIgnoreCase(teamName)) {
					continue;
				}

				if (matchScheduledForTeam.containsKey(teamName) && matchCounter < (ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY + 1)) {
					if (matchesOnADay.get(currentDay) < matchesPerDay) {
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);

						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;

					} else {
						incrementCurrentDay();
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);


						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;
					}
					int totalMatchForATeam = matchScheduledForTeam.get(teamName);
					matchScheduledForTeam.put(teamName, totalMatchForATeam + 1);
				} else {
					break;
				}
			}
			i++;
		} while (i < loopCounter + 1);
	}

	private void scheduleInterDivisionMatches(String conferenceName, String divisionName, String teamName) {
		List<String> teamsInOtherDivision = new ArrayList<>();
		List<String> divisions = conferenceDivisionMap.get(conferenceName);

		for (Map.Entry<String, List<String>> entry : divisionTeamsMap.entrySet()) {
			if (divisions.contains(entry.getKey())) {
				if (entry.getKey().equalsIgnoreCase(divisionName)) {
					continue;
				}
				teamsInOtherDivision.addAll(divisionTeamsMap.get(entry.getKey()));
			}
		}

		int matchCounter = 0;
		if (teamsInOtherDivision.size() < 2) {
			log.log(Level.SEVERE, "Less than 2 teams provided. Cannot simulate league.");
			return;
		}
		int loopCounter = ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY / (teamsInOtherDivision.size() - 1);
		int i = 0;
		do {
			for (String team : teamsInOtherDivision) {
				if (team.equalsIgnoreCase(teamName)) {
					continue;
				}

				if (matchScheduledForTeam.containsKey(teamName) && matchCounter < ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY + 1) {
					if (matchesOnADay.get(currentDay) < matchesPerDay) {
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);

						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;

					} else {
						incrementCurrentDay();
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);

						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;
					}
					int totalMatchForATeam = matchScheduledForTeam.get(teamName);
					matchScheduledForTeam.put(teamName, totalMatchForATeam + 1);
				} else {
					break;
				}
			}
			i++;
		} while (i < loopCounter + 1);
	}

	private void scheduleInterConferenceMatches(String conferenceName, String teamName) {
		List<String> teamsInOtherConferences = new ArrayList<>();
		int j = 0;

		for (Map.Entry<String, List<String>> entry : conferenceTeamsMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(conferenceName)) {
				continue;
			}
			teamsInOtherConferences.addAll(conferenceTeamsMap.get(entry.getKey()));
		}

		int matchCounter = 0;
		if (teamsInOtherConferences.size() < 2) {
			log.log(Level.SEVERE, "Less than 2 teams provided. Cannot simulate league.");
			return;
		}
		int loopCounter = ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY / (teamsInOtherConferences.size() - 1);
		int i = 0;
		do {
			for (String team : teamsInOtherConferences) {
				if (team.equalsIgnoreCase(teamName)) {
					continue;
				}

				if (matchScheduledForTeam.containsKey(teamName) && matchCounter < ScheduleConstants.REGULAR_SEASON_MAX_MATCH_PER_DAY + 1) {
					if (matchesOnADay.get(currentDay) < matchesPerDay) {
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);

						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;

					} else {
						incrementCurrentDay();
						Map<String, String> teamsCompeting = new HashMap<>();
						teamsCompeting.put(teamName, team);

						if (finalSchedule.containsKey(currentDay)) {
							List<Map<String, String>> matchList = finalSchedule.get(currentDay);
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						} else {
							List<Map<String, String>> matchList = new ArrayList<>();
							matchList.add(teamsCompeting);
							finalSchedule.put(currentDay, matchList);
						}

						if (matchesOnADay.containsKey(currentDay)) {
							int matchCount = matchesOnADay.get(currentDay);
							matchesOnADay.put(currentDay, matchCount + 1);
						} else {
							matchesOnADay.put(currentDay, 1);
						}
						matchCounter++;
					}
					int totalMatchForATeam = matchScheduledForTeam.get(teamName);
					matchScheduledForTeam.put(teamName, totalMatchForATeam + 1);
				} else {
					break;
				}
			}
			i++;
		} while (i < loopCounter + 1);
	}

	public boolean incrementCurrentDay() {
		if (currentDay.equals(lastDay)) {
			return false;
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);

			try {
				calendar.setTime(dateFormat.parse(currentDay));
			} catch (ParseException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
				log.log(Level.SEVERE, e.getMessage());
				return false;
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			currentDay = dateFormat.format(calendar.getTime());
			return true;
		}
	}

	public void setTeamsToBeScheduled(List<String> teamsToBeScheduled) {
		this.teamsToBeScheduled = teamsToBeScheduled;
	}

	public List<String> getTeamsToBeScheduled() {
		return this.teamsToBeScheduled;
	}

	public void setTeamsScheduled(List<String> teamsScheduled) {
		this.teamsScheduled = teamsScheduled;
	}

	public List<String> getTeamsScheduled() {
		return this.teamsScheduled;
	}

	public void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay) {
		for (String team : teamsToCompete) {
			this.teamsCompeting.add(team);
		}
	}

	public Map<String, List<Map<String, String>>> getFinalSchedule() {
		return this.finalSchedule;
	}

	public void setFinalSchedule(Map<String, List<Map<String, String>>> schedule) {
		this.finalSchedule = schedule;
	}

	public boolean anyUnplayedGame(String date) {
		if (finalSchedule.containsKey(date)) {
			if (finalSchedule.get(date).size() > 0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
