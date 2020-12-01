package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PersistState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private ISchedule schedule;
	private StandingInfo standingInfo;
	private IStandingsPersistance standingsDb;
	private InternalStateContext context;
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private int season;
	private IUserOutput output;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private static final Logger log = Logger.getLogger(PersistState.class.getName());

	public PersistState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			InternalStateContext context, SeasonCalendar utility, String currentDate, String endDate, int season,
			IUserOutput output) {
		this.stateName = StateConstants.PERSIST_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.schedule = schedule;
		this.standingsDb = standingsDb;
		this.standingInfo = standings;
		this.context = context;
		this.utility = utility;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.output = output;
	}

	public ISimulationState nextState(InternalStateContext context) {
		if (utility.getSeasonOverStatus()) {
			this.nextStateName = "SeasonEndState";
			return this.internalStateMachineFactory.EndOfSeasonState(output);
		} else {
			this.nextStateName = StateConstants.ADVANCE_TIME_STATE;
			return this.internalStateMachineFactory.AdvanceTimeState(this.leagueToSimulate, this.schedule, this.utility, this.standingsDb, this.standingInfo,
					this.currentDate, this.endDate, output, context, this.season);
		}
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.PERSIST_ENTRY);
		output.setOutput("Inside persist state");
		output.sendOutput();
		if(utility.getSeasonOverStatus()) {
			try {
				standingsDb.insertToStandings(standingInfo.getStanding());
			} catch (Exception e) {
				log.log(Level.SEVERE, e.getMessage());
				output.setOutput(e.getMessage());
				output.sendOutput();
				System.exit(1);
			}
		}
		log.log(Level.INFO, StateConstants.PERSIST_ENTRY);
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
