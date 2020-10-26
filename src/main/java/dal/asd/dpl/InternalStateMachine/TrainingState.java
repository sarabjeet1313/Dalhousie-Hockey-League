package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserInput.IUserInput;
import dal.asd.dpl.UserOutput.IUserOutput;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TrainingState implements ISimulationState {

    private String stateName;
    private String nextStateName;
    private boolean finalDay;
    private League leagueToSimulate;
    private String currentDate;
    private IUserOutput output;
    private InternalStateContext context;
    private ISchedule schedule;
    private ScheduleUtlity utility;

    public TrainingState (League leagueToSimulate, ISchedule schedule, ScheduleUtlity utility, String currentDate, IUserOutput output, InternalStateContext context) {
        this.leagueToSimulate = leagueToSimulate;
        this.output = output;
        this.context = context;
        this.schedule = schedule;
        this.currentDate = currentDate;
        this.utility = utility;
        this.stateName = "Training";

       // doProcessing();
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

        // TODO training logic to be implemented.

        output.setOutput("Inside Training state");
        output.sendOutput();
    //    nextState(this.context);
    }

    public boolean anyUnplayedGames() {
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
