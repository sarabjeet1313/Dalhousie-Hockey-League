package dal.asd.dpl.InternalStateMachine;

import dal.asd.dpl.Schedule.ISchedule;
import dal.asd.dpl.Schedule.SeasonCalendar;
import dal.asd.dpl.TeamManagement.League;
import dal.asd.dpl.UserOutput.IUserOutput;

public class AgingState implements ISimulationState {

    private static String stateName;
    private static String nextStateName;
    private League leagueToSimulate;
    private ISchedule schedule;
    private InternalStateContext context;
    private String currentDate;
    private SeasonCalendar utility;
    private IUserOutput output;

    public AgingState (League leagueToSimulate, ISchedule schedule, InternalStateContext context, SeasonCalendar utility, String currentDate, IUserOutput output) {
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
        // advance age so that injury days will be reduced.
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
