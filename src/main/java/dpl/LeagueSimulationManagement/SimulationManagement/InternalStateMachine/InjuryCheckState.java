package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dpl.Database.TradeDataDB;
import dpl.DplConstants.ScheduleConstants;
import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.StandingInfo;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.IInjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradePersistence;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.ITradingAbstractFactory;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;
import dpl.SystemConfig;

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
		this.tradeDb = new TradeDataDB();
		this.trade = tradingAbstractFactory.Trade(tradeDb);
		this.stateName = StateConstants.INJURY_STATE;
	}

	public ISimulationState nextState(InternalStateContext context) {
		if (schedule.anyUnplayedGame(currentDate)) {
			this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
			return this.internalStateMachineFactory.SimulateGameState(leagueToSimulate, schedule, standingsDb, standings, context, seasonCalendar, currentDate,
					endDate, season, output);
		} else {
			if (seasonCalendar.isTradeDeadlinePending(this.currentDate)) {
				this.nextStateName = StateConstants.TRADING_STATE;
				return this.internalStateMachineFactory.TradingState(leagueToSimulate, trade, context, output, seasonCalendar, currentDate, endDate,
						season, standingsDb, standings, schedule);
			} else {
				this.nextStateName = StateConstants.AGING_STATE;
				return this.internalStateMachineFactory.AgingState(leagueToSimulate, schedule, standingsDb, standings, injury, context, seasonCalendar,
						currentDate, endDate, season, output);
			}
		}
	}

	public void doProcessing() {
		List<Map<String, String>> competingList = new ArrayList<Map<String, String>>();
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
		output.setOutput("Inside Injury Check state");
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
