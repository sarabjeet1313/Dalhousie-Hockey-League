package dpl.SimulationManagement.InternalStateMachine;

import dpl.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueManagement.Schedule.ScheduleConstants;
import dpl.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueManagement.TeamManagement.League;
import dpl.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueManagement.Trading.Trade;
import dpl.SimulationManagement.StateConstants;
import dpl.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InjuryCheckState implements ISimulationState {
	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private IInjuryManagement injury;
	private ISchedule schedule;
	private InternalStateContext context;
	private SeasonCalendar seasonCalendar;
	private String currentDate;
	private String endDate;
	private int season;
	private IUserOutput output;
	private IStandingsPersistance standingsDb;
	private StandingInfo standings;
	private ITradePersistence tradeDb;
	private Trade trade;
	private IInternalStateMachineAbstractFactory internalStateMachineFactory;
	private ITradingAbstractFactory tradingAbstractFactory;
	private static final Logger log = Logger.getLogger(InjuryCheckState.class.getName());

	public InjuryCheckState(League leagueToSimulate, IInjuryManagement injury, ISchedule schedule,
			InternalStateContext context, SeasonCalendar seasonCalendar, String currentDate, String endDate, int season,
			IUserOutput output, IStandingsPersistance standingsDb, StandingInfo standings) {
		this.leagueToSimulate = leagueToSimulate;
		this.internalStateMachineFactory = SystemConfig.getSingleInstance().getInternalStateMachineAbstractFactory();
		this.tradingAbstractFactory = SystemConfig.getSingleInstance().getTradingAbstractFactory();
		this.injury = injury;
		this.schedule = schedule;
		this.context = context;
		this.seasonCalendar = seasonCalendar;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.output = output;
		this.standingsDb = standingsDb;
		this.standings = standings;
		this.tradeDb = tradingAbstractFactory.TradeUtility();
		this.trade = tradingAbstractFactory.Trade(tradeDb);
		this.stateName = StateConstants.INJURY_STATE;
	}

	public ISimulationState nextState(InternalStateContext context) {
		try {
			if (schedule.anyUnplayedGame(currentDate)) {
				this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
				return this.internalStateMachineFactory.SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, seasonCalendar, currentDate,
						endDate, season, output);
			} else {
				if (seasonCalendar.isTradeDeadlinePending(this.currentDate)) {
					log.log(Level.INFO, ScheduleConstants.TRADE_DEADLINE);
					this.nextStateName = StateConstants.TRADING_STATE;
					return this.internalStateMachineFactory.TradingState(leagueToSimulate, trade, context, output, seasonCalendar, currentDate, endDate,
							season, standingsDb, standings, schedule);
				} else {
					this.nextStateName = StateConstants.AGING_STATE;
					return this.internalStateMachineFactory.AgingState(leagueToSimulate, schedule, standingsDb, standings, injury, context, seasonCalendar,
							currentDate, endDate, season, output);
				}
			}
		} catch(Exception e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
			log.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}

	public void doProcessing() {
		output.setOutput(StateConstants.INJURY_ENTRY);
		output.sendOutput();

		List<Map<String, String>> competingList = new ArrayList<>();
		competingList = schedule.getFinalSchedule().get(currentDate);
		for (Map<String, String> teams : competingList) {
			for (Map.Entry<String, String> entry : teams.entrySet()) {
				leagueToSimulate = injury.getInjuryStatusByTeam(entry.getKey(), leagueToSimulate);
				leagueToSimulate = injury.getInjuryStatusByTeam(entry.getValue(), leagueToSimulate);
			}
		}
		if (schedule.getSeasonType() == ScheduleConstants.PLAYOFF_SEASON) {
			Map<String, List<Map<String, String>>> currentSchedule = schedule.getFinalSchedule();
			currentSchedule.remove(this.currentDate);
			schedule.setFinalSchedule(currentSchedule);
		}
		output.setOutput(StateConstants.INJURY_ENTRY);
		output.sendOutput();
		log.log(Level.INFO, StateConstants.INJURY_ENTRY);
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
