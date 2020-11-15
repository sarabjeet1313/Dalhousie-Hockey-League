package dpl.LeagueSimulationManagement.SimulationManagement.InternalStateMachine;

import java.sql.SQLException;

import dpl.DplConstants.StateConstants;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.ISchedule;
import dpl.LeagueSimulationManagement.LeagueManagement.Schedule.SeasonCalendar;
import dpl.LeagueSimulationManagement.LeagueManagement.Standings.IStandingsPersistance;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.InjuryManagement;
import dpl.LeagueSimulationManagement.LeagueManagement.TeamManagement.League;
import dpl.LeagueSimulationManagement.LeagueManagement.Trading.Trade;
import dpl.LeagueSimulationManagement.UserInputOutput.UserOutput.IUserOutput;

public class TradingState implements ISimulationState {

	private String stateName;
	private String nextStateName;
	private League leagueToSimulate;
	private InternalStateContext context;
	private Trade trade;
	private IUserOutput output;
	private InjuryManagement injury;
	private SeasonCalendar utility;
	private String currentDate;
	private String endDate;
	private int season;
	private IStandingsPersistance standingsDb;
	private ISchedule schedule;

	public TradingState(League leagueToSimulate, Trade trade, InternalStateContext context, IUserOutput output,
			SeasonCalendar utility, String currentDate, String endDate, int season, IStandingsPersistance standingsDb,
			ISchedule schedule) {
		this.stateName = StateConstants.TRADING_STATE;
		this.leagueToSimulate = leagueToSimulate;
		this.trade = trade;
		this.context = context;
		this.output = output;
		this.currentDate = currentDate;
		this.endDate = endDate;
		this.season = season;
		this.standingsDb = standingsDb;
		this.utility = utility;
		this.schedule = schedule;
	}

	public ISimulationState nextState(InternalStateContext context) {
		this.nextStateName = StateConstants.AGING_STATE;
		return new AgingState(leagueToSimulate, schedule, standingsDb, injury, context, utility, currentDate, endDate,
				season, output);
	}

	public void doProcessing() {
		output.setOutput("Inside Trading state");
		output.sendOutput();
		try {
			leagueToSimulate = trade.startTrade(leagueToSimulate);
		} catch (SQLException e) {
			output.setOutput(e.getMessage());
			output.sendOutput();
		}
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
