package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TradingState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private InternalStateContext context;
	private Trade trade;
	private IUserOutput output;
	private InjuryManagement injury = new InjuryManagement();
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private int season;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private ISchedule schedule;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private static final Logger log = Logger.getLogger(TradingState.class.getName());

	public TradingState(League leagueToSimulate, Trade trade, InternalStateContext context, IUserOutput output,
			SeasonCalendar utility, String currentDate, String endDate, int season, IStandingsPersistance standingsDb, StandingInfo standings,
			ISchedule schedule) {
		this.stateName = StateConstants.TRADING_STATE;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.leagueToSimulate = leagueToSimulate;
		this.trade = trade;
		this.context = context;
		this.output = output;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.utility = utility;
		this.schedule = schedule;
		this.injury = new InjuryManagement();
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.AGING_STATE;
		return this.internalStateMachineFactory.AgingState(leagueToSimulate, schedule, standingsDb, standings, injury, context, utility, currentDate, endDate,
				season, output);
	}

	public void doProcessing() {
		log.log(Level.INFO, StateConstants.TRADING_ENTRY);
		try {
			leagueToSimulate = trade.startTrade(leagueToSimulate, standings);
		} catch (Exception e) {
			log.log(Level.SEVERE, e.getMessage());
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
		output.setOutput(StateConstants.TRADING_ENTRY);
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
