package dpl.LeagueManagement.Schedule;

import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.Conference;
import dpl.LeagueManagement.TeamManagement.Division;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayoffSchedule implements ISchedule {

	private Calendar calendar;
	private IUserOutput output;
	private StandingInfo standings;
	private String currentDay;
	private String firstDay;
	private String lastDay;
	private int seasonType;
	private int maxMatches = ScheduleConstants.PLAYOFF_MAX_MATCHES;
	private int matchesPerDay;
	private Map<String, List<String>> conferenceTeamList;
	private Map<String, List<Map<String, String>>> finalSchedule;
	private List<String> teamsToBeScheduled;
	private List<String> teamsScheduled;
	private SimpleDateFormat dateFormat;
	private static final Logger log = Logger.getLogger(PlayoffSchedule.class.getName());

	public PlayoffSchedule(IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		this.calendar = Calendar.getInstance();
		this.output = output;
		this.standings = standings;
		this.seasonType = ScheduleConstants.PLAYOFF_SEASON;
		conferenceTeamList = new HashMap<>();
		finalSchedule = new HashMap<>();
		teamsToBeScheduled = new ArrayList<>();
		teamsScheduled = new ArrayList<>();
		dateFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);
	}

	public int getSeasonType() {
		return this.seasonType;
	}

	public void setSeasonType(int seasonType) {
		this.seasonType = seasonType;
	}

	public String getFirstDay() {
		return this.firstDay;
	}

	public void setFirstDay(String firstDay) {
		this.firstDay = firstDay;
	}

	public String getLastDay() {
		return this.lastDay;
	}

	public void setLastDay(String lastDay) {
		this.lastDay = lastDay;
	}

	public String getCurrentDay() {
		return currentDay;
	}

	public void setCurrentDay(String currentDay) {
		this.currentDay = currentDay;
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

	public void generateSchedule(League leagueToSimulate) {
		setMatchesPerDay();
		populateInternalModel(leagueToSimulate);
		for (Map.Entry<String, List<String>> entry : conferenceTeamList.entrySet()) {
			List<String> teamsToCompete = entry.getValue();
			generateScheduleOnTheFly(teamsToCompete, this.currentDay);
		}
		log.log(Level.INFO, "PlayOff schedule generated");
	}

	private void populateInternalModel(League leagueToSimulate) {
		try {
			if (leagueToSimulate == null) {
				return;
			}
			List<Conference> conferenceList = leagueToSimulate.getConferenceList();

			for (int index = 0; index < conferenceList.size(); index++) {
				List<Division> divisionList = conferenceList.get(index).getDivisionList();
				String conferenceName = conferenceList.get(index).getConferenceName();
				List<String> divisions = new ArrayList<>();
				for (int dIndex = 0; dIndex < divisionList.size(); dIndex++) {

					List<String> teams = new ArrayList<>();
					String divisionName = divisionList.get(dIndex).getDivisionName();
					teams = standings.getTopDivisionTeams(leagueToSimulate, divisionName);

					if (conferenceTeamList.containsKey(conferenceName)) {
						List<String> alreadyAddedTeams = conferenceTeamList.get(conferenceName);
						alreadyAddedTeams.addAll(teams);
						conferenceTeamList.put(conferenceName, alreadyAddedTeams);
					} else {
						conferenceTeamList.put(conferenceName, teams);
					}

					teamsScheduled.addAll(teams);
				}
			}
		} catch (NullPointerException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
			log.log(Level.SEVERE, e.getMessage());
			System.exit(1);
		}
	}

	public void generateScheduleOnTheFly(List<String> teamsToCompete, String currentDay) {
		this.currentDay = currentDay;
		incrementCurrentDay();
		int totalTeams = teamsToCompete.size();
		for (int i = 0; i < totalTeams / 2; i++) {
			Map<String, String> teamsCompeting = new HashMap<>();
			teamsCompeting.put(teamsToCompete.get(i), teamsToCompete.get(totalTeams - 1 - i));
			List<Map<String, String>> matchList = new ArrayList<>();
			matchList.add(teamsCompeting);
			this.finalSchedule.put(this.currentDay, matchList);
			incrementCurrentDay();
		}
	}

	private void setMatchesPerDay() {
		try {
			Date date1 = dateFormat.parse(firstDay);
			Date date2 = dateFormat.parse(lastDay);
			long diff = date2.getTime() - date1.getTime();
			int totalDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

			int matchCount = (this.maxMatches) / totalDays;
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

	public boolean incrementCurrentDay() {
		if (currentDay.equals(lastDay)) {
			return false;
		} else {
			try {
				calendar.setTime(dateFormat.parse(currentDay));
			} catch (ParseException e) {
				output.setOutput(e.getMessage());
				output.sendOutput();
				log.log(Level.SEVERE, e.getMessage());
				System.exit(1);
			}
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			currentDay = dateFormat.format(calendar.getTime());
			return true;
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
