package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class AdvanceToNextSeasonState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private IInjuryManagement injury;
	private IRetirementManagement retirement;
	private InternalStateContext context;
	private SeasonCalendar seasonCalendar;
	private String currentDate;
	private IUserOutput output;

	public AdvanceToNextSeasonState(League leagueToSimulate, IInjuryManagement injury, IRetirementManagement retirement,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, IUserOutput output) {
		this.stateName = StateConstants.NEXT_SEASON_STATE;
		this.leagueToSimulate = leagueToSimulate;
		this.injury = injury;
		this.retirement = retirement;
		this.context = context;
		this.seasonCalendar = seasonCalendar;
		this.currentDate = currentDate;
		this.output = output;
	}

	public void nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.PERSIST_STATE;
	}

	public void doProcessing() {
		int days = (int) daysLapsed();
		try {
			leagueToSimulate = retirement.increaseAge(days, leagueToSimulate);
			leagueToSimulate = injury.updatePlayerInjuryStatus(days, leagueToSimulate);
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
	}

	public League getUpdatedLeague() {
		return leagueToSimulate;
	}

	private long daysLapsed() {
		SimpleDateFormat myFormat = new SimpleDateFormat(ScheduleConstants.DATE_FORMAT);
		String startDate = seasonCalendar.getLastSeasonDay();
		String endDate = seasonCalendar.getNextRegularSeasonStartDay();
		try {
			Date date1 = myFormat.parse(startDate);
			Date date2 = myFormat.parse(endDate);
			long difference = date2.getTime() - date1.getTime();
			long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
			return days;
		} catch (ParseException e) {
			output.setOutput(StateConstants.EXPECTING_DAYS_STATE);
			output.sendOutput();
		}
		return 0;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}