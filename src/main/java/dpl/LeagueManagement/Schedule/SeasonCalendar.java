package dpl.LeagueManagement.Schedule;

import dpl.UserInputOutput.UserOutput.IUserOutput;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SeasonCalendar {

	private Calendar seasonCalendar;
	private int currentSeason;
	private String currentYear;
	private int regularSeasonYear;
	private int playoffYear;
	private boolean isSeasonOver;
	private String lastSeasonDay;
	private String seasonWinner;
	private SimpleDateFormat dateFormat;

	public SeasonCalendar(int season, IUserOutput output) {
		this.currentSeason = season;
		this.dateFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);
		this.seasonCalendar = Calendar.getInstance();
		setYears();
	}

	private void setYears() {
		int startYear = this.seasonCalendar.get(Calendar.YEAR);
		startYear += this.currentSeason;
		this.regularSeasonYear = startYear;
		this.playoffYear = ++startYear;
		this.currentYear = String.valueOf(currentYear);
	}

	public String getRegularSeasonStartDay() {
		seasonCalendar.set(Calendar.DAY_OF_MONTH, ScheduleConstants.REGULAR_SEASON_START_DAY);
		seasonCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		seasonCalendar.set(Calendar.YEAR, regularSeasonYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getRegularSeasonFirstDay() {
		seasonCalendar.set(Calendar.DAY_OF_MONTH, ScheduleConstants.REGULAR_SEASON_FIRST_DAY);
		seasonCalendar.set(Calendar.MONTH, Calendar.OCTOBER);
		seasonCalendar.set(Calendar.YEAR, regularSeasonYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getPlayoffFirstDay() {
		seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, ScheduleConstants.PLAYOFF_START_WEEK);
		seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getRegularSeasonLastDay() {
		seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, ScheduleConstants.REGULAR_SEASON_LAST_WEEK);
		seasonCalendar.set(Calendar.MONTH, Calendar.APRIL);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getPlayoffLastDay() {
		seasonCalendar.set(Calendar.DAY_OF_MONTH, ScheduleConstants.PLAYOFF_FIRST_DAY);
		seasonCalendar.set(Calendar.MONTH, Calendar.JUNE);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getNextRegularSeasonStartDay() {
		seasonCalendar.set(Calendar.DAY_OF_MONTH, ScheduleConstants.NEXT_REGULAR_SEASON_START_DAY);
		seasonCalendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getAllStartGameDay() {
		seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, ScheduleConstants.LAST_WEEK);
		seasonCalendar.set(Calendar.MONTH, Calendar.JANUARY);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		return dateFormat.format(seasonCalendar.getTime());
	}

	public String getSeasonWinner() {
		return seasonWinner;
	}

	public void setSeasonWinner(String seasonWinner) {
		this.seasonWinner = seasonWinner;
	}

	public void setSeasonOverStatus(boolean over) {
		this.isSeasonOver = over;
	}

	public boolean getSeasonOverStatus() {
		return isSeasonOver;
	}

	public void setLastSeasonDay(String date) {
		this.lastSeasonDay = date;
	}

	public String getLastSeasonDay() {
		return this.lastSeasonDay;
	}

	public boolean isTradeDeadlinePending(String currentDate) throws ParseException {
		seasonCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		seasonCalendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
		seasonCalendar.set(Calendar.MONTH, Calendar.FEBRUARY);
		seasonCalendar.set(Calendar.YEAR, playoffYear);
		String tradeDeadline = dateFormat.format(seasonCalendar.getTime());
		try {
			Date start = dateFormat.parse(currentDate);
			Date end = dateFormat.parse(tradeDeadline);
			if (start.compareTo(end) < 0) {
				return true;
			}
			if (start.compareTo(end) >= 0) {
				return false;
			}
		} catch (ParseException e) {
			throw e;
		}
		return false;
	}

	public boolean isLastDayOfSeason(String currentDate) throws ParseException {
		try {
			Date start = dateFormat.parse(currentDate);
			Date end = dateFormat.parse(getPlayoffLastDay());
			if (start.compareTo(end) > 0) {
				return false;
			}
			if (start.compareTo(end) == 0) {
				return true;
			}
			if (start.compareTo(end) < 0) {
				return false;
			}
		} catch (ParseException e) {
			throw e;
		}
		return true;
	}
}
