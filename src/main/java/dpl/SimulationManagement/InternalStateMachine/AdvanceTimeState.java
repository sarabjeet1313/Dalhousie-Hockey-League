package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.ScheduleConstants;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvanceTimeState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private String currentDate;
	private IUserOutput output;
	private InternalStateContext context;
	private String endDate;
	private Calendar calendar;
	private SeasonCalendar utility;
	private boolean isALastDay;
	private boolean isAllStarGameDay;
	private League leagueToSimulate;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private ISchedule schedule;
	private Training training;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private Logger log = Logger.getLogger(AdvanceTimeState.class.getName());

	public AdvanceTimeState(League leagueToSimulate, ISchedule schedule, SeasonCalendar utility, IStandingsPersistance standingsDb, StandingInfo standings, String startDate, String endDate, IUserOutput output, InternalStateContext context, int season) {
		this.stateName = StateConstants.ADVANCE_TIME_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.currentDate = startDate;
		this.training = SystemConfig.getSingleInstance().getGameplayConfigurationAbstractFactory().Training();
		this.endDate = endDate;
		this.output = output;
		this.context = context;
		this.isALastDay = false;
		this.isAllStarGameDay = false;
		this.calendar = Calendar.getInstance();
		this.leagueToSimulate = leagueToSimulate;
		this.utility = utility;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.season = season;
		this.schedule = schedule;
	}

	public ISimulationState nextState(InternalStateContext context) {
		if (isALastDay) {
			this.nextStateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
			return this.internalStateMachineFactory.GeneratePlayoffScheduleState(leagueToSimulate, utility, standingsDb, standings, output, context, season,
					currentDate, endDate);
		}
		if (isAllStarGameDay) {
			this.nextStateName = StateConstants.ALL_STAR_GAME_STATE;
			return this.internalStateMachineFactory.AllStarGameState(leagueToSimulate, training, schedule, utility, currentDate, endDate, output,
					context, standingsDb, standings, season);
		}
		else {
			this.nextStateName = StateConstants.TRAINING_STATE;
			return this.internalStateMachineFactory.TrainingState(leagueToSimulate, training, schedule, utility, currentDate, endDate, output,
					context, standingsDb, standings, season);
		}
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.ADVANCE_DAY_STATE);
		incrementCurrentDay();
		output.setOutput(StateConstants.ADVANCE_DAY_STATE);
		output.sendOutput();
	}

	public void incrementCurrentDay() {
		this.isALastDay = false;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ScheduleConstants.DATE_FORMAT);
		this.currentDate = LocalDate.parse(this.currentDate, formatter).plusDays(1).format(formatter).toString();
		if (this.currentDate.equals(this.endDate)) {
			this.isALastDay = true;
		}
		if(this.currentDate.equals(this.utility.getAllStartGameDay())) {
			this.isAllStarGameDay = true;
		}
	}

	public boolean shouldContinue() {
		return true;
	}

	public void setCurrentDate(String date) {
		this.currentDate = date;
	}

	public String getCurrentDate() {
		return this.currentDate;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}

	public boolean isALastDay() {
		return isALastDay;
	}
}