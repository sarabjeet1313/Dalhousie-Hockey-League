package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.GeneratePlayoffConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.IScheduleAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GeneratePlayoffScheduleState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private String startDate;
	private String endDate;
	private int season;
	private League leagueToSimulate;
	private IUserOutput output;
	private SeasonCalendar seasonCalendar;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private InternalStateContext context;
	private String currentDate;
	private String lastRegularSeasonDay;
	private ISchedule schedule;
	private Training training;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private IScheduleAbstractFactory scheduleAbstractFactory;
	private static final Logger log = Logger.getLogger(GeneratePlayoffScheduleState.class.getName());

	public GeneratePlayoffScheduleState(League leagueToSimulate, SeasonCalendar seasonCalendar,
										IStandingsPersistance standingsDb, StandingInfo standings, IUserOutput output, InternalStateContext context, int season,
										String currentDate, String endDate) {
		this.stateName = StateConstants.GENERATE_PLAYOFF_SCHEDULE_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.scheduleAbstractFactory = SystemConfig.getSingleInstance().getScheduleAbstractFactory();
		this.season = season;
		this.output = output;
		this.seasonCalendar = seasonCalendar;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.leagueToSimulate = leagueToSimulate;
		this.schedule = scheduleAbstractFactory.PlayoffSchedule(this.output, this.standingsDb, this.standings, this.season);
		this.context = context;
		this.training = new Training(output);
		this.startDate = this.seasonCalendar.getPlayoffFirstDay();
		schedule.setFirstDay(startDate);
		schedule.setCurrentDay(startDate);
		this.currentDate = currentDate;
		this.lastRegularSeasonDay = endDate;
		this.endDate = this.seasonCalendar.getPlayoffLastDay();
		schedule.setLastDay(endDate);
		seasonCalendar.setLastSeasonDay(this.endDate);
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.TRAINING_STATE;
		return this.internalStateMachineFactory.TrainingState(leagueToSimulate, training, schedule, seasonCalendar, currentDate,
				lastRegularSeasonDay, output, context, standingsDb, standings, season);
	}

	public void doProcessing() {
		output.setOutput(GeneratePlayoffConstants.SCHEDULING_PLAYOFF.toString());
		output.sendOutput();
		try {
			if (null == leagueToSimulate) {
				log.log(Level.SEVERE, GeneratePlayoffConstants.SCHEDULING_ERROR.toString());
				return;
			}
			output.setOutput("\nSeason stats after Regular season matches : \n");
			output.sendOutput();
			standings.showStats();
			standings.resetStats();
			schedule.generateSchedule(leagueToSimulate);
			log.log(Level.INFO, GeneratePlayoffConstants.PLAYOFF_SUCCESSFUL.toString());
			schedule.setCurrentDay(this.startDate);
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
		log.log(Level.INFO, GeneratePlayoffConstants.SCHEDULING_PLAYOFF.toString());
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

	public ISchedule getSchedule() {
		return this.schedule;
	}

	public void setSchedule(ISchedule schedule) {
		this.schedule = schedule;
	}
}