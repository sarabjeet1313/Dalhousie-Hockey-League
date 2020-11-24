package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.StateConstants;
import dpl.DplConstants.TeamManagementConstants;
import dpl.ErrorHandling.RetirementManagementException;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IRetirementManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

public class AdvanceToNextSeasonState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private IInjuryManagement injury;
	private IRetirementManagement retirement;
	private InternalStateContext context;
	private SeasonCalendar seasonCalendar;
	private String currentDate;
	private String endDate;
	private int season;
	private IUserOutput output;
	private ISchedule schedule;
	private StandingInfo standings;
	private IStandingsPersistance standingsDb;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;

	public AdvanceToNextSeasonState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			IInjuryManagement injury, IRetirementManagement retirement, InternalStateContext context,
			SeasonCalendar seasonCalendar, String currentDate, String endDate, int season, IUserOutput output) {
		this.stateName = StateConstants.NEXT_SEASON_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.injury = injury;
		this.retirement = retirement;
		this.context = context;
		this.seasonCalendar = seasonCalendar;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.schedule = schedule;
		this.output = output;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.PERSIST_STATE;
		return this.internalStateMachineFactory.PersistState(leagueToSimulate, schedule, standingsDb, standings, context, seasonCalendar, currentDate, endDate,
				season, output);
	}

	public void doProcessing() throws RetirementManagementException {
		output.setOutput(StateConstants.NEXT_SEASON_ENTRY);
		output.sendOutput();
		int days = (int) daysLapsed();
		try {
			leagueToSimulate = retirement.increaseAge(days, leagueToSimulate);
			leagueToSimulate = injury.updatePlayerInjuryStatus(days, leagueToSimulate);
		} catch (SQLException e) {
			throw new RetirementManagementException(TeamManagementConstants.RETIREMENT_EXCEPTION.toString());
		} catch (IOException e) {
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

	public boolean shouldContinue() {
		return true;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}