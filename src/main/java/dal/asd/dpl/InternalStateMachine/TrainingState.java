package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.TeamManagement.Leagues;
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
    private Leagues leagueToSimulate;
    private String currentDate;
    private IUserInput input;
    private IUserOutput output;
    private ISchedule schedule;

    public TrainingState (Leagues leagueToSimulate, ISchedule schedule, String currentDate, IUserInput input, IUserOutput output) {
        this.leagueToSimulate = leagueToSimulate;
        this.input = input;
        this.output = output;
        this.schedule = schedule;
        this.currentDate = currentDate;
        this.stateName = "Training";
    }

    public void nextState(InternalStateContext context) {
        if(anyUnplayedGames())
            this.nextStateName = "SimulatePlayoffGame";
        else
        if(/*tradeDeadlinePassed*/ true)
            this.nextStateName = "Aging";
        else
            this.nextStateName = "Trading";

    }

    public void doProcessing() {

        // TODO training logic to be implemented.
    }

    private boolean anyUnplayedGames() {
        Map< String, List<Map<String, String>>> finalSchedule = schedule.getFinalSchedule();
        if(finalSchedule.containsKey(this.currentDate)){
            return true;
        }
        else {
            return false;
        }
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
