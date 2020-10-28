package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.TeamManagement.Leagues;
import dal.asd.dpl.UserOutput.IUserOutput;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private Leagues leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private String currentDate;
    private ScheduleUtlity utility;
    private IUserOutput output;

    public AgingState (Leagues leagueToSimulate, ISchedule schedule, InternalStateContext context, ScheduleUtlity utility, String currentDate, IUserOutput output) {
        this.stateName = "Aging";
        this.leagueToSimulate = leagueToSimulate;
        this.schedule = schedule;
        this.context = context;
        this.utility = utility;
        this.currentDate = currentDate;
        this.output = output;
    }

    public void nextState(InternalStateContext context) {
        if(utility.getSeasonOverStatus()) {
            this.nextStateName = "NextSeason";
        }
        else {
            this.nextStateName = "Persist";
        }
    }

    public void doProcessing() {

        //TODO age process
        output.setOutput("Inside Aging state");
        output.sendOutput();
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
