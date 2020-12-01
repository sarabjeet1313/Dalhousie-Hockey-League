package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueManagement.TeamManagement.ITeamManagementAbstractFactory;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueManagement.Trading.Trade;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainingState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private String currentDate;
	private String endDate;
	private IUserOutput output;
	private InternalStateContext context;
	private ISchedule schedule;
	private SeasonCalendar utility;
	private Training training;
	private StandingInfo standings;
	private Trade trade;
	private ITradePersistence tradeDb;
	private IStandingsPersistance standingsDb;
	private IInjuryManagement injury;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITradingAbstractFactory tradingAbstractFactory;
	private ITeamManagementAbstractFactory teamManagementAbstractFactory;
	private static final Logger log = Logger.getLogger(TrainingState.class.getName());

	public TrainingState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility,
			String currentDate, String endDate, IUserOutput output, InternalStateContext context,
			IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		this.leagueToSimulate = leagueToSimulate;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.tradingAbstractFactory = SystemConfig.getSingleInstance().getTradingAbstractFactory();
		this.teamManagementAbstractFactory = SystemConfig.getSingleInstance().getTeamManagementAbstractFactory();
		this.output = output;
		this.context = context;
		this.schedule = schedule;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.utility = utility;
		this.stateName = "Training";
		this.training = training;
		this.season = season;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.tradeDb = tradingAbstractFactory.TradeUtility();
		this.trade = tradingAbstractFactory.Trade(tradeDb);
		this.injury = teamManagementAbstractFactory.InjuryManagement();
	}

	public ISimulationState nextState(InternalStateContext context) {
		try {
			if (schedule.anyUnplayedGame(currentDate)) {
				this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
				return this.internalStateMachineFactory.SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, utility, currentDate,
						endDate, season, output);
			} else {
				if (utility.isTradeDeadlinePending(this.currentDate)) {
					this.nextStateName = StateConstants.TRADING_STATE;
					return this.internalStateMachineFactory.TradingState(leagueToSimulate, trade, context, output, utility, currentDate, endDate, season,
							standingsDb, standings, schedule);
				} else {
					this.nextStateName = StateConstants.AGING_STATE;
					return this.internalStateMachineFactory.AgingState(leagueToSimulate, schedule, standingsDb, standings, injury, context, utility, currentDate,
							endDate, season, output);
				}
			}
		} catch (Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
			log.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}

	public boolean shouldContinue() {
		return true;
	}

	public void doProcessing() {
		output.setOutput(StateConstants.TRAINING_ENTRY);
		output.sendOutput();
		leagueToSimulate = training.trackDaysForTraining(leagueToSimulate);
		log.log(Level.INFO, StateConstants.TRAINING_ENTRY);
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
