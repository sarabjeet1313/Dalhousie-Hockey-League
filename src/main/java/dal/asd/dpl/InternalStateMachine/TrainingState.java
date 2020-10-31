package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

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
    private SeasonCalendar utility;

    public TrainingState (League leagueToSimulate, ISchedule schedule, SeasonCalendar utility, String currentDate, IUserOutput output, InternalStateContext context) {
        this.leagueToSimulate = leagueToSimulate;
        this.output = output;
        this.context = context;
        this.schedule = schedule;
        this.currentDate = currentDate;
        this.utility = utility;
        this.stateName = "Training";
    }

    public void nextState(InternalStateContext context) {
        if(anyUnplayedGames()) {
            this.nextStateName = "SimulateGame";
        }
        else {
            if (utility.isTradeDeadlinePending(this.currentDate)) {
                this.nextStateName = "Trading";
            }
            else {
                this.nextStateName = "Aging";
            }
        }
    }

    public void doProcessing() {

        // TODO training logic to be implemented.
        output.setOutput("Inside Training state");
        output.sendOutput();
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
