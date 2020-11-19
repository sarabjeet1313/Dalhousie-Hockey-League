package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import dpl.Database.TradeDataDB;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.GameplayConfiguration.Training;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

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
	private InjuryManagement injury;
	private int season;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;

	public TrainingState(League leagueToSimulate, Training training, ISchedule schedule, SeasonCalendar utility,
			String currentDate, String endDate, IUserOutput output, InternalStateContext context,
			IStandingsPersistance standingsDb, StandingInfo standings, int season) {
		this.leagueToSimulate = leagueToSimulate;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
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
		this.tradeDb = new TradeDataDB();
		this.trade = new Trade(tradeDb);
		this.injury = new InjuryManagement();
	}

	public ISimulationState nextState(InternalStateContext context) {
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
	}

	public boolean shouldContinue() {
		return true;
	}

	public void doProcessing() {
		leagueToSimulate = training.trackDaysForTraining(leagueToSimulate);
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
