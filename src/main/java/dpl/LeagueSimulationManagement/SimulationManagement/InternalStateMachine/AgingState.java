package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Aging;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.*;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AgingState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private IInjuryManagement injury;
	private InternalStateContext context;
	private String currentDate;
	private String endDate;
	private int season;
	private SeasonCalendar seasonCalendar;
	private IUserOutput output;
	private IRetirementManagement retirement;
	private ISchedule schedule;
	private StandingInfo standings;
	private IStandingsPersistance standingsDb;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITeamManagementAbstractFactory teamManagementAbstractFactory;
	private static final Logger log = Logger.getLogger(AgingState.class.getName());

	public AgingState(League leagueToSimulate, ISchedule schedule, IStandingsPersistance standingsDb, StandingInfo standings,
			IInjuryManagement injury, InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate,
			String endDate, int season, IUserOutput output) {
		this.stateName = StateConstants.AGING_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.injury = injury;
		this.context = context;
		this.seasonCalendar = seasonCalendar;
		this.retirement = teamManagementAbstractFactory.RetirementManagement();
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.output = output;
		this.schedule = schedule;
		this.standingsDb = standingsDb;
		this.standings = standings;
	}

	public ISimulationState nextState(InternalStateContext context) {
		if (seasonCalendar.getSeasonOverStatus() | seasonCalendar.isLastDayOfSeason(currentDate)) {
			this.nextStateName = StateConstants.TROPHY_STATE;
			return this.internalStateMachineFactory.TrophyState(leagueToSimulate, schedule, standingsDb, standings, injury, retirement, context,
					seasonCalendar, currentDate, endDate, season, output);
		} else {
			this.nextStateName = StateConstants.PERSIST_STATE;
			return this.internalStateMachineFactory.PersistState(leagueToSimulate, schedule, standingsDb, standings, context, seasonCalendar, currentDate,
					endDate, season, output);
		}
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.INSIDE_AGING_STATE);
		leagueToSimulate = injury.updatePlayerInjuryStatus(1, leagueToSimulate);
		output.setOutput(StateConstants.INSIDE_AGING_STATE);
		output.sendOutput();
	}

	public boolean shouldContinue() {
		return true;
	}

	public League getUpdatedLeague() {
		return leagueToSimulate;
	}

	public String getStateName() {
		return this.stateName;
	}

	public String getNextStateName() {
		return this.nextStateName;
	}
}