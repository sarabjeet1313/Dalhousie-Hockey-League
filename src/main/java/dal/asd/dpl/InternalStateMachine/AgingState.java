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

   //     doProcessing();
    }

    public void nextState(InternalStateContext context) {
        if(/*utility.getSeasonOverStatus()*/ false) {
            this.nextStateName = "NextSeason";
            context.setState(new AdvanceToNextSeasonState(leagueToSimulate, schedule, context, utility, currentDate, output));
        }
        else {
            this.nextStateName = "Persist";
            context.setState(new PersistState(leagueToSimulate, schedule, context, utility, currentDate, output));
        }
    }

    public void doProcessing() {
        output.setOutput("Inside Aging state");
        output.sendOutput();

     //   nextState(this.context);
    }

    public String getStateName() {
        return this.stateName;
    }

    public String getNextStateName() {
        return this.nextStateName;
    }
}
