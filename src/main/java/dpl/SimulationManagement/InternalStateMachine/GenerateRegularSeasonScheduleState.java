package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.GenerateRegularConstants;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerateRegularSeasonScheduleState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private String startDate;
	private String endDate;
	private Calendar calendar;
	private League leagueToSimulate;
	private StandingInfo standings;
	private IUserOutput output;
	private InternalStateContext context;
	private ISchedule schedule;
	private SeasonCalendar seasonCalendar;
	private IStandingsPersistance standingsDb;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private IScheduleAbstractFactory scheduleAbstractFactory;
	private static final Logger log = Logger.getLogger(GenerateRegularSeasonScheduleState.class.getName());

	public GenerateRegularSeasonScheduleState(League leagueToSimulate, IUserOutput output, int season,
			InternalStateContext context, IStandingsPersistance standingsDb, StandingInfo standings, SeasonCalendar utility) {
		this.stateName = StateConstants.GENERATE_REGULAR_SEASON_SCHEDULE_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.scheduleAbstractFactory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.standings = standings;
		this.calendar = Calendar.getInstance();
		this.schedule = scheduleAbstractFactory.RegularSeasonSchedule(calendar, output);
		this.seasonCalendar = utility;
		this.standingsDb = standingsDb;
		this.output = output;
		this.context = context;
		this.season = season;
		this.startDate = seasonCalendar.getRegularSeasonStartDay();
		schedule.setCurrentDay(this.startDate);
		schedule.setFirstDay(seasonCalendar.getRegularSeasonFirstDay());
		this.endDate = seasonCalendar.getRegularSeasonLastDay();
		schedule.setLastDay(this.endDate);
		seasonCalendar.setLastSeasonDay(this.endDate);
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
		return this.internalStateMachineFactory.AdvanceTimeState(this.leagueToSimulate, this.schedule, this.seasonCalendar, this.standingsDb, this.standings, this.startDate, this.endDate, output, context, this.season);
	}

	public void doProcessing() {
		output.setOutput(GenerateRegularConstants.SCHEDULING_REGULAR.toString());
		output.sendOutput();
		log.log(Level.INFO, GenerateRegularConstants.SCHEDULING_REGULAR.toString());
		try {
			if (null == leagueToSimulate) {
				log.log(Level.SEVERE, GenerateRegularConstants.SCHEDULING_ERROR.toString());
				return;
			}
			schedule.generateSchedule(leagueToSimulate);
			log.log(Level.INFO, GenerateRegularConstants.REGULAR_SUCCESSFUL.toString());
			schedule.setCurrentDay(seasonCalendar.getRegularSeasonStartDay());
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			output.setOutput(e.getMessage());
			output.sendOutput();
			System.exit(1);
		}
	}

	public boolean shouldContinue() {
		return true;
	}

	public String getRegularSeasonEndDate() {
		return this.endDate;
	}

	public String getRegularSeasonStartDate() {
		return this.startDate;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}

	public ISchedule getSchedule() {
		return schedule;
	}
}
