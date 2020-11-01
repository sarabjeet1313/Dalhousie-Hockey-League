package dal.asd.dpl.InternalStateMachine;
import dal.asd.dpl.GameplayConfiguration.Training;
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
    private Training training;

    public TrainingState (League leagueToSimulate, ISchedule schedule, SeasonCalendar utility, String currentDate, IUserOutput output, InternalStateContext context) {
        this.leagueToSimulate = leagueToSimulate;
        this.output = output;
        this.context = context;
        this.schedule = schedule;
        this.currentDate = currentDate;
        this.utility = utility;
        this.stateName = "Training";
        this.training = new Training(); 
    }

    public void nextState(InternalStateContext context) {
        if(schedule.anyUnplayedGame(currentDate)) {
            this.nextStateName = StateConstants.SIMULATE_GAME_STATE;
        }
        else {
            if (utility.isTradeDeadlinePending(this.currentDate)) {
                this.nextStateName = StateConstants.TRADING_STATE;
            }
            else {
                this.nextStateName = StateConstants.AGING_STATE;
            }
        }
    }

    public void doProcessing() {

        // TODO training logic to be implemented.
        output.setOutput("Inside Training state");
        output.sendOutput();
        leagueToSimulate = training.trackDaysForTraining(leagueToSimulate);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
