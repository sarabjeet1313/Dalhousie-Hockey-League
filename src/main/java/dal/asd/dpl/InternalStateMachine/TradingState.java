package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

public class TradingState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private String currentDate;
    private ScheduleUtlity utility;
    private IUserOutput output;

    public TradingState (League leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "Trading";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;

       // doProcessing();
    }

    public void nextState(InternalStateContext context) {
        this.nextStateName = "Aging";
        context.setState(new AgingState(leagueToSimulate, schedule, context, utility, currentDate, output));
    }

    public void doProcessing() {

        output.setOutput("Inside Trading state");
        output.sendOutput();

        //TODO call Breej's method to do trade

      //  nextState(this.context);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }

}
