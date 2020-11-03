package dpl.InternalStateMachine;

import java.sql.SQLException;

import dpl.DplConstants.StateConstants;
import dpl.Schedule.ISchedule;
import dpl.Schedule.SeasonCalendar;
import dpl.TeamManagement.League;
import dpl.Trading.Trade;
import dpl.UserOutput.IUserOutput;

public class TradingState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private InternalStateContext context;
    private Trade trade;
    private IUserOutput output;

    public TradingState(League leagueToSimulate, Trade trade, InternalStateContext context, IUserOutput output) {
        this.stateName = StateConstants.TRADING_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.trade = trade;
        this.context = context;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.AGING_STATE;
    }

    public void doProcessing() {
        output.setOutput("Inside Trading state");
        output.sendOutput();
        try {
        leagueToSimulate = trade.startTrade(leagueToSimulate);
        }catch (SQLException e) {
        	output.setOutput(e.getMessage());
            output.sendOutput();
		}
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
