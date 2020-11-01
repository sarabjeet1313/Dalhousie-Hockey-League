package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.Trading.Trade;
import dal.asd.dpl.UserOutput.IUserOutput;

public class TradingState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private String currentDate;
    private SeasonCalendar utility;
    private Trade trade;
    private IUserOutput output;

    public TradingState (League leagueToSimulate, Trade trade, ISchedule schedule, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
        this.stateName = StateConstants.TRADING_STATE;
        this.leagueToSimulate = leagueToSimulate;
        this.trade = trade;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = StateConstants.AGING_STATE;
    }

    public void doProcessing() {
        output.setOutput("Inside Trading state");
        output.sendOutput();
        leagueToSimulate = trade.startTrade(leagueToSimulate);
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
