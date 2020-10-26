package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.util.List;
import java.util.Map;

public class InjuryCheckState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private Leagues leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private ScheduleUtlity utility;
    private String currentDate;
    private IUserOutput output;

    public InjuryCheckState (Leagues leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate,  IUserOutput output) {
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;

    //    doProcessing();
    }

    public void nextState(InternalStateContext context) {
        if(anyUnplayedGames()) {
            this.nextStateName = "SimulateGame";
            context.setState(new SimulateGameState(leagueToSimulate, schedule, context, utility, currentDate, output));
        }
        else {
            if (utility.isTradeDeadlinePending(this.currentDate)) {
                this.nextStateName = "Trading";
                context.setState(new TradingState(leagueToSimulate, schedule, context, utility, currentDate, output));
            }
            else {
                this.nextStateName = "Aging";
                context.setState(new AgingState(leagueToSimulate, schedule, context, utility, currentDate, output));
            }
        }
    }

    public void doProcessing() {

        output.setOutput("Inside Injury Check state");
        output.sendOutput();
    //    nextState(this.context);
    }

    private boolean anyUnplayedGames() {
        Map< String, List<Map<String, String>>> finalSchedule = schedule.getFinalSchedule();
        if(finalSchedule.containsKey(this.currentDate)) {
            if (finalSchedule.get(this.currentDate).size() > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
